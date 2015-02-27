package com.hps.rest.login;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.hps.rest.common.RestBase;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.service.UserMgrService;
import com.newsoft.sysmanager.vo.UserVo;
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
	
	/**
	 * 登录
	 * @param request
	 * @param writer
	 * @param username
	 * @param password
	 */
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	public void login(HttpServletRequest request,PrintWriter writer,@RequestParam("username") String username,@RequestParam("password") String password){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserVo user = userMgrService.getUserByAccount(username);
			if (user==null) {
				user = userMgrDAO.getUserByEmail(username);
				if (user==null) {
					user = userMgrDAO.getUserByMobile(username);
					if (user==null) {
						map.put("success", false);
						map.put("msg", "账号不存在");
						writer.write(JSONTool.toJson(map));
						return;
					} else if(!user.getUserState()){
						map.put("success", false);
						map.put("msg", "账号不存在");
						writer.write(JSONTool.toJson(map));
						return;
					}
				}
			}
			//验证密码
			String encodePasss = PasswordEncoderUtil.encode(password);
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
				logger.debug("User " + username + " login success!");
				
				map.put("success", true);
				writer.write(JSONTool.toJson(map));
				return;
			} else {
				logger.debug("User " + username + " login fail!");
				map.put("success", false);
				map.put("msg", "密码错误");
				writer.write(JSONTool.toJson(map));
				return;
			}
		} catch (Exception e) {
			logger.debug("User " + username + " login error!");
			logger.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * new member register
	 * @param user
	 * @param writer
	 */
	@RequestMapping("/reg")
	public void register(HttpServletRequest request,Integer type,String mobile,String email,String regpass,String authcode,String authcodeHidden,PrintWriter writer) {
		//check if the email or the moblile has already been reg
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int remoblie = userMgrDAO.countUserBymobile(mobile);
			if (remoblie>0) {
				map.put("success", false);
				map.put("msg", "手机号已注册");
				writer.write(JSONTool.toJson(map));
				return;
			}
			int reemail = userMgrDAO.countUserByEmail(email);
			if (reemail>0) {
				map.put("success", false);
				map.put("msg", "邮箱号已注册");
				writer.write(JSONTool.toJson(map));
				return;
			}
			
			if (!authcode.equals(authcodeHidden)) {
				map.put("success", false);
				map.put("msg", "您输入的验证码有误");
				writer.write(JSONTool.toJson(map));
				return;
			}
			
			User user = new User();
			user.setAccount(email);
			user.setCurrentOrgId("");
			user.setDefaultOrgId("");
			user.setEmail(email);
			user.setExtend("");
			user.setLastModifyTime(new Date());
			user.setMemo("");
			user.setMobile(mobile);
			user.setPoint(0);
			user.setPosition("");
			user.setPwd(PasswordEncoderUtil.encode(regpass));
			user.setUserId(UUIDTool.getUUID());
			user.setUserName("新歌手");
			user.setUserState(true);
			user.setCreateTime(new Date());
			
			userMgrDAO.addUser(user);
			
			request.getSession().setAttribute("loginMember", user);
			map.put("success", true);
			map.put("msg", "注册成功");
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 登录
	 * @param request
	 * @param writer
	 * @param username
	 * @param password
	 */
	@RequestMapping("/loginuser")
	public void login(HttpServletRequest request,PrintWriter writer){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserVo user = checklogin();
			if (user==null) {
				map.put("success", false);
				map.put("msg", "账号不存在");
				writer.write(JSONTool.toJson(map));
				return;
			} else {
				map.put("login user", user);
				writer.write(JSONTool.toJson(map));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
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
}
