package com.hps.rest.login;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hps.level.mgr.Scorelevel;
import com.hps.level.mgr.ScorelevelService;
import com.hps.rest.common.RestBase;
import com.hps.userscore.mgr.Userscoredetail;
import com.hps.userscore.mgr.UserscoredetailService;
import com.newsoft.common.attach.po.Attach;
import com.newsoft.common.attach.service.FrameAttachService;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.service.UserMgrService;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.FileTools;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.PasswordEncoderUtil;
import com.newsoft.utils.UUIDTool;

@Controller
@RequestMapping("/hps/auth")
public class AuthrizeServer extends RestBase{

	private static final Log logger = LogFactory.getLog(AuthrizeServer.class);
	
	@Autowired
	private UserMgrService userMgrService;
	
	@Autowired
	private UserMgrDAO userMgrDAO;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private ScorelevelService scorelevelService;
	
	@Autowired
	private UserscoredetailService userscoredetailService;
	
	@Autowired
	private FrameAttachService frameAttachService;
	
	@Value("${imagePath}")
	private String imagePath = "";
	
	/**
	 * 登录
	 * @param request
	 * @param writer
	 * @param username
	 * @param password
	 */
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public void login(HttpServletRequest request,PrintWriter writer,@RequestParam("userName") String userName,@RequestParam("passWord") String passWord){
		Map<String, Object> map = new HashMap<String, Object>();
		if(!super.validate(request.getHeader("X-APP-Token"))){
			map.put("msg", "验证错误，非法请求");
			map.put("success", true);
			map.put("responseCode", 5);
			writer.write(JSONTool.toJson(map));
			return;
		}
		try {
			UserVo user = userMgrService.getUserByAccount(userName);
			if(user==null){
				map.put("msg", "用户不存在");
				map.put("success", true);
				map.put("responseCode", 5);
				writer.write(JSONTool.toJson(map));
				return;
			}
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String time=df.format(new Date());
			time=time.substring(0, 10)+" 00:00:00";
			Date date=df.parse(time);
			if(user.getLastModifyTime().after(date)){
				user.setLastModifyTime(date);
				userMgrDAO.updateUser(user);
			} else {
				Scorelevel sc=new Scorelevel();
				sc=scorelevelService.getScorelevelById("LOGIN");
				user.setLastModifyTime(date);
				user.setPoint(user.getPoint()+sc.getScore());
				userMgrDAO.updateUser(user);
			}
			user = userMgrService.getUserByAccount(userName);
			if (user==null) {
				user = userMgrDAO.getUserByEmail(userName);
				if (user==null) {
					user = userMgrDAO.getUserByMobile(userName);
					if (user==null) {
						map.put("success", false);
						map.put("msg", "账号不存在");
						writer.write(JSONTool.toJson(map));
						return;
					} else if(!user.getUserState()){
						map.put("success", false);
						map.put("msg", "账号未激活");
						writer.write(JSONTool.toJson(map));
						return;
					}
				}
			}
			//验证密码
			String encodePasss = PasswordEncoderUtil.encode(passWord);
			if (encodePasss.equals(user.getPwd())) {
				//记录Session 信息
				String sessionID = request.getSession().getId();
				sessionRegistry.registerNewSession(sessionID, user.getUserId());
				
				Set<GrantedAuthority> grantedAuthoritys = new HashSet<GrantedAuthority>();
				grantedAuthoritys.add(new GrantedAuthorityImpl("ROLE_USER"));
				UserDetails acegiUser = new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPwd(),
						true, true, true, true, grantedAuthoritys);
				
				Authentication authRequest = new UsernamePasswordAuthenticationToken(
						acegiUser, user.getPwd(), grantedAuthoritys);
				SecurityContextHolder.getContext().setAuthentication(
						authRequest);
				logger.debug("User " + userName + " login success!");
				
				String imageDir=new String();
				if(user.getFileId()!=null && !user.getFileId().equals("")){
					Attach attachDir = new Attach();
					attachDir=frameAttachService.getAttachById(user.getFileId());
					String dir=attachDir.getFileDir().replace(imagePath, "\\image");
					imageDir=dir+attachDir.getFileName();
				}
				
				StringBuilder json=new StringBuilder();
				json.append("{\"responseCode\":").append(0).append(",");
				json.append("\"type\":").append(user.getType()).append(",");
				json.append("\"userId\":\"").append(user.getUserId()).append("\",");
				json.append("\"userInfo\":").append("{");
				json.append("\"userName\":\"").append(user.getUserName()).append("\",");
				json.append("\"phone\":\"").append(user.getMobile()).append("\",");
				json.append("\"image\":\"").append(imageDir).append("\",");
				json.append("\"company\":\"").append("\",");
				json.append("\"comPhone\":\"").append("\",");
				json.append("\"comPerson\":\"").append("\"").append("}}");
				writer.write(json.toString());
				return;
			} else {
				logger.debug("User " + userName + " login fail!");
				map.put("success", false);
				map.put("msg", "密码错误");
				writer.write(JSONTool.toJson(map));
				return;
			}
		} catch (Exception e) {
			logger.debug("User " + userName + " login error!");
			logger.error(e.getMessage(),e);
		}
	}
	
	@RequestMapping(value = "/thirdPartLogin",method=RequestMethod.POST)
	public void thirdPartLogin(HttpServletRequest request,PrintWriter writer,@RequestParam("userName")String userName,@RequestParam("type")int type,@RequestParam("image")String image){
		Map<String, Object> map = new HashMap<String, Object>();
		if(type!=0 && type!=1 && type!=2){
			map.put("success", false);
			map.put("responseCode", 5);
			map.put("msg", "非法第三方登陆");
			writer.write(JSONTool.toJson(map));
			return;
		}
		if(!super.validate(request.getHeader("X-APP-Token"))){
			map.put("success", false);
			map.put("responseCode", 5);
			writer.write(JSONTool.toJson(map));
			return;
		}
		try {
			//image
			int BUFFER_SIZE = 1024;  
			byte[] buf = new byte[BUFFER_SIZE];  
			int size = 0; 
			String name=image.substring(image.lastIndexOf("/")+1);
			Date currentTime = new Date();
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String folder = new Integer(year).toString() + new Integer(month).toString();
			long prefix = currentTime.getTime();
			String storPath = imagePath + File.separator + folder + File.separator;
			String originalFileName = prefix + "_" + name;
			
			URL url = new URL(image);
			HttpURLConnection  httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
			File file = new File(storPath + originalFileName);
			FileOutputStream fileOutputStream = new FileOutputStream(file);  
			while ((size = bis.read(buf)) != -1) {  
				fileOutputStream.write(buf, 0, size);  
			}  
			fileOutputStream.flush();
			fileOutputStream.close();  
			bis.close();
			httpUrl.disconnect();
			
			Attach attachDir = new Attach();
			attachDir.setAttachId(UUIDTool.getUUID());
			attachDir.setFileName(originalFileName);
			attachDir.setFileSize(file.length());
			attachDir.setContentType(FileTools.getFileExtentionByOriginalFileName(originalFileName));
			attachDir.setFileDir(storPath);
			attachDir.setCreateTime(currentTime);
			attachDir.setFileType(2);
			frameAttachService.addAttach(attachDir);
			
			UserVo userVo = userMgrService.getUserByAccount(userName);
			if (userVo==null) {
				Scorelevel sc=new Scorelevel();
				Scorelevel ss=new Scorelevel();
				sc=scorelevelService.getScorelevelById("REG");
				ss=scorelevelService.getScorelevelById("LOGIN");
				User user = new User();
				user.setAccount(userName);
				user.setPwd("1");
				user.setLastModifyTime(new Date());
				user.setPoint(sc.getScore()+ss.getScore());
				user.setUserId(UUIDTool.getUUID());
				user.setUserName(userName);
				user.setUserState(true);
				user.setCreateTime(new Date());
				user.setType(0);
				user.setFileId(attachDir.getAttachId());
				userMgrDAO.addUser(user);
				
				Userscoredetail userscoredetail=new Userscoredetail();
				userscoredetail.setId(UUIDTool.getUUID());
				userscoredetail.setUserid(user.getUserId());
				userscoredetail.setLevelkey(sc.getLevelkey());
				userscoredetail.setScore(sc.getScore());
				userscoredetail.setCratetime(new Date());
				userscoredetailService.addUserscoredetail(userscoredetail);
				
				//记录Session 信息
				String sessionID = request.getSession().getId();
				sessionRegistry.registerNewSession(sessionID, user.getUserId());
				
				Set<GrantedAuthority> grantedAuthoritys = new HashSet<GrantedAuthority>();
				grantedAuthoritys.add(new GrantedAuthorityImpl("ROLE_USER"));
				UserDetails acegiUser = new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPwd(),
						true, true, true, true, grantedAuthoritys);
				Authentication authRequest = new UsernamePasswordAuthenticationToken(
						acegiUser, user.getPwd(), grantedAuthoritys);
				SecurityContextHolder.getContext().setAuthentication(
						authRequest);
				logger.debug("User " + userName + " login success!");
				
				map.put("responseCode", 0);
				map.put("userId", user.getUserId());
				map.put("phoneNum", user.getMobile());
				writer.write(JSONTool.toJson(map));
			} else {
				Scorelevel ss=new Scorelevel();
				ss=scorelevelService.getScorelevelById("LOGIN");
				userVo.setPoint(ss.getScore());
				userVo.setLastModifyTime(new Date());
				userVo.setFileId(attachDir.getAttachId());
				userMgrDAO.updateUser(userVo);
				//记录Session 信息
				String sessionID = request.getSession().getId();
				sessionRegistry.registerNewSession(sessionID, userVo.getUserId());
				
				Set<GrantedAuthority> grantedAuthoritys = new HashSet<GrantedAuthority>();
				grantedAuthoritys.add(new GrantedAuthorityImpl("ROLE_USER"));
				UserDetails acegiUser = new org.springframework.security.core.userdetails.User(userVo.getUserId(), userVo.getPwd(),
						true, true, true, true, grantedAuthoritys);
				Authentication authRequest = new UsernamePasswordAuthenticationToken(
						acegiUser, userVo.getPwd(), grantedAuthoritys);
				SecurityContextHolder.getContext().setAuthentication(
						authRequest);
				logger.debug("User " + userName + " login success!");
				
				map.put("responseCode", 0);
				map.put("userId", userVo.getUserId());
				map.put("phoneNum", userVo.getMobile());
				writer.write(JSONTool.toJson(map));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return;
	}
	
	@RequestMapping(value = "/register",method=RequestMethod.POST)
	public void register(HttpServletRequest request,PrintWriter writer,@RequestParam("userName") String userName,@RequestParam("passWord") String passWord
			,@RequestParam("company") String company,@RequestParam("person") String person,@RequestParam("phone") String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!super.validate(request.getHeader("X-APP-Token"))){
			map.put("success", true);
			map.put("responseCode", 5);
			writer.write(JSONTool.toJson(map));
			return;
		}
		try {
			if(phone!=null && !phone.equals("")){
				int remoblie = userMgrDAO.countUserBymobile(phone);
				if (remoblie>0) {
					map.put("success", false);
					map.put("msg", "手机号已注册");
					writer.write(JSONTool.toJson(map));
					return;
				}
			}
			int account = userMgrDAO.countUserByAccout(userName);
			if (account>0) {
				map.put("success", false);
				map.put("msg", "用户名已注册");
				writer.write(JSONTool.toJson(map));
				return;
			}
			
			Scorelevel sc=new Scorelevel();
			sc=scorelevelService.getScorelevelById("REG");
			
			User user = new User();
			user.setCurrentOrgId("");
			user.setDefaultOrgId("");
			user.setAccount(userName);
			user.setExtend("");
			user.setLastModifyTime(new Date());
			user.setMemo("");
			user.setMobile(phone);
			user.setPoint(sc.getScore());
			user.setPosition("");
			user.setPwd(PasswordEncoderUtil.encode(passWord));
			user.setUserId(UUIDTool.getUUID());
			user.setUserName(userName);
			int type=0;
			if(company!=null && !company.equals("")){
				type=1;
				user.setUserState(false);
			} else {
				type=0;
				user.setUserState(true);
			}
			user.setCreateTime(new Date());
			user.setType(type);
			user.setContactPerson(person);
			user.setContactOrgName(company);
			userMgrDAO.addUser(user);
			
			Userscoredetail userscoredetail=new Userscoredetail();
			userscoredetail.setId(UUIDTool.getUUID());
			userscoredetail.setUserid(user.getUserId());
			userscoredetail.setLevelkey(sc.getLevelkey());
			userscoredetail.setScore(sc.getScore());
			userscoredetail.setCratetime(new Date());
			userscoredetailService.addUserscoredetail(userscoredetail);
			
			request.getSession().setAttribute("loginMember", user);
			map.put("responseCode", 0);
			map.put("userId", user.getUserId());
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 修改用户头像
	 */
	@RequestMapping(value = "/changeUserImage",method=RequestMethod.POST)
	public void changeUserImage(HttpServletRequest request,PrintWriter writer,@RequestParam("userId") String userId,
			@RequestParam("images") MultipartFile[] images) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!super.validate(request.getHeader("X-APP-Token"))){
			map.put("success", true);
			map.put("responseCode", 5);
			writer.write(JSONTool.toJson(map));
			return;
		}
		try {
			String id=UUIDTool.getUUID();
			for(MultipartFile image:images){
				String imageName = image.getOriginalFilename();
				if(image.isEmpty()){
					map.put("success", false);
					map.put("msg", "image is null");
					map.put("responseCode", 5);
					writer.write(JSONTool.toJson(map));
					return;
				}
				
				
//					int BUFFER_SIZE = 1024;  
//					byte[] buf = new byte[BUFFER_SIZE];  
//					int size = 0; 
//					InputStream input = request.getInputStream();
				Date currentTime = new Date();
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				String folder = new Integer(year).toString() + new Integer(month).toString();
				long prefix = currentTime.getTime();
				String storPath = imagePath + File.separator + folder + File.separator;
				String originalFileName = prefix + "_" + imageName;
					
					//FileInputStream bis=new FileInputStream(image);
				File file = new File(storPath + originalFileName);
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(image.getBytes());
				fileOutputStream.flush();
					
//					while ((size = input.read(buf)) != -1) {  
//						fileOutputStream.write(buf, 0, size);  
//					}  
//					fileOutputStream.flush();
//					fileOutputStream.close();  
//					input.close(); 
					//bis.close();
					
					Attach attachDir = new Attach();
					attachDir.setAttachId(id);
					attachDir.setFileName(originalFileName);
					attachDir.setFileSize(file.length());
					attachDir.setContentType(FileTools.getFileExtentionByOriginalFileName(originalFileName));
					attachDir.setFileDir(storPath);
					attachDir.setCreateTime(currentTime);
					attachDir.setFileType(2);
					frameAttachService.addAttach(attachDir);
				
				
				User user = userMgrService.getUserById(userId);
				user.setLastModifyTime(new Date());
				if(imageName!=null && !imageName.equals("")){
					user.setFileId(id);
				}
				userMgrDAO.updateUser(user);
			}
			map.put("responseCode", 0);
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 修改用户信息
	 */
	@RequestMapping(value = "/changeUserInfo",method=RequestMethod.POST)
	public void changeUserInfo(HttpServletRequest request,PrintWriter writer,@RequestParam("userId") String userId,@RequestParam("type") String type
			,@RequestParam("phone") String phone,@RequestParam("comPhone") String comPhone,@RequestParam("comPerson") String comPerson) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!super.validate(request.getHeader("X-APP-Token"))){
			map.put("success", true);
			map.put("responseCode", 5);
			writer.write(JSONTool.toJson(map));
			return;
		}
		try {
			if(phone!=null && !phone.equals("")){
				int remoblie = userMgrDAO.countUserBymobile(phone);
				if (remoblie>0) {
					map.put("success", false);
					map.put("msg", "手机号已注册");
					writer.write(JSONTool.toJson(map));
					return;
				}
			}
			
			User user = userMgrService.getUserById(userId);
			user.setLastModifyTime(new Date());
			if(phone!=null && !phone.equals("")){
				user.setMobile(phone);
			}
			if(comPhone!=null && !comPhone.equals("")){
				user.setTelephone(comPhone);
			}
			if(type!=null && !type.equals("")){
				user.setType(Integer.parseInt(type));
			}
			if(comPerson!=null && !comPerson.equals("")){
				user.setContactPerson(comPerson);
			}
			userMgrDAO.updateUser(user);
			
			request.getSession().setAttribute("loginMember", user);
			map.put("responseCode", 0);
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 退出登录
	 * @param writer
	 * @param request
	 */
	@RequestMapping("/logout")
	public void logout(PrintWriter writer,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		session.setAttribute("sessionUser", null);
		sessionRegistry.removeSessionInformation(session.getId());
		map.put("success", true);
		writer.write(JSONTool.toJson(map));
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/changePassWord",method=RequestMethod.POST)
	public void changePassWord(HttpServletRequest request,PrintWriter writer,@RequestParam("oldPassWord") String oldPassWord,@RequestParam("newPassWord") String newPassWord,@RequestParam("userName") String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!super.validate(request.getHeader("X-APP-Token"))){
			map.put("success", true);
			map.put("responseCode", 5);
			writer.write(JSONTool.toJson(map));
			return;
		}
		try {
			UserVo user = userMgrService.getUserByAccount(userName);
			if (user==null) {
				user = userMgrDAO.getUserByEmail(userName);
				if (user==null) {
					user = userMgrDAO.getUserByMobile(userName);
					if (user==null) {
						map.put("success", false);
						map.put("msg", "账号不存在");
						writer.write(JSONTool.toJson(map));
						return;
					} else if(!user.getUserState()){
						map.put("success", false);
						map.put("msg", "账号未激活");
						writer.write(JSONTool.toJson(map));
						return;
					}
				}
			}
			String encodePasss = PasswordEncoderUtil.encode(oldPassWord);
			if (encodePasss.equals(user.getPwd())) {
				String passWord = PasswordEncoderUtil.encode(newPassWord);
				user.setPwd(passWord);
				user.setLastModifyTime(new Date());
				userMgrDAO.updateUser(user);
				map.put("success", true);
				map.put("responseCode", 0);
			} else {
				map.put("msg", "密码错误");
				map.put("success", false);
				map.put("responseCode", 5);
			}
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/queryScore")
	public void queryScore(PrintWriter writer,HttpServletRequest request,String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		if(!super.validate(request.getHeader("X-APP-Token"))){
			map.put("success", true);
			map.put("responseCode", 5);
			writer.write(JSONTool.toJson(map));
			return;
		}
		User user = userMgrService.getUserById(userId);
		map.put("responseCode", 0);
		map.put("totalScore", user.getPoint());
		writer.write(JSONTool.toJson(map));
		return;
	}
}
