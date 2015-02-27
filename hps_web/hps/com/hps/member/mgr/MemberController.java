package com.hps.member.mgr;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.newsoft.sysmanager.cache.CacheFacade;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.service.UserMgrService;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.JSONTool;
import com.newsoft.utils.PasswordEncoderUtil;
import com.newsoft.utils.StringTools;
import com.newsoft.utils.UUIDTool;

/**
 * member mgr class
 * @author mengxw
 *
 */
@Controller
@RequestMapping("/member")
public class MemberController {

	Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private UserMgrService userMgrService;
	@Autowired
	private UserMgrDAO userMgrDAO;
	@Autowired
	private CacheFacade cacheFacade;
	
	/**
	 * new member register
	 * @param user
	 * @param writer
	 */
	@RequestMapping("/reg")
	public void register(HttpServletRequest request,String mobile,String email,String regpass,String authcode,String authcodeHidden,PrintWriter writer) {
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
	
	@RequestMapping("/login")
	public void login(PrintWriter writer,HttpServletRequest request,String memberName,String memberPass){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserVo user = userMgrService.getUserByAccount(memberName);
			if (user==null) {
				user = userMgrDAO.getUserByEmail(memberName);
				if (user==null) {
					user = userMgrDAO.getUserByMobile(memberName);
					if (user==null) {
						map.put("success", false);
						map.put("msg", "账号不存在");
						writer.write(JSONTool.toJson(map));
						return;
					}
				}
			}
			//验证密码
			String encodePasss = PasswordEncoderUtil.encode(memberPass);
			if (encodePasss.equals(user.getPwd())) {
				//记录session信息
				request.getSession().setAttribute(User.LOGIN_MEMBER, user);
				map.put("success", true);
				writer.write(JSONTool.toJson(map));
				return;
			} else {
				map.put("success", false);
				map.put("msg", "密码错误");
				writer.write(JSONTool.toJson(map));
				return;
			}
		} catch (Exception e) {
			logger.error("login error");
			logger.error(e.getMessage(),e);
		}
	}
	
	
	@RequestMapping("/saveSet")
	public void saveSet(PrintWriter writer,HttpServletRequest request,String email,String userId,String mobile,String userName,String position,String fileId,String qq) {
		User user = userMgrService.getUserById(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		
		user.setUserName(userName);
		user.setMobile(mobile);
		user.setPosition(position);
		user.setQq(qq);
		if (!StringTools.isEmpty(fileId)) {
			user.setFileId(fileId);
		}
		try {
			if (!StringTools.isEmpty(email)) {
				int emailCount = userMgrDAO.countUserByEmail(email);
				if (user.getEmail().equals(email) && emailCount>1) {
					map.put("success", false);
					map.put("msg", "该邮箱已被注册");
					writer.write(JSONTool.toJson(map));
					return;
				} else if (!(user.getEmail().equals(email)) && emailCount>0) {
					map.put("success", false);
					map.put("msg", "该邮箱已被注册");
					writer.write(JSONTool.toJson(map));
					return;
				}
			}
			
			if (!StringTools.isEmpty(mobile)) {
				int mobileCount = userMgrDAO.countUserBymobile(mobile);
				if (user.getMobile().equals(mobile) && mobileCount>1) {
					map.put("success", false);
					map.put("msg", "该手机号已被注册");
					writer.write(JSONTool.toJson(map));
					return;
				} else if (!(user.getMobile().equals(mobile)) && mobileCount>0) {
					map.put("success", false);
					map.put("msg", "该手机号已被注册");
					writer.write(JSONTool.toJson(map));
					return;
				}
			}
			
			userMgrService.updateUser(user);
			cacheFacade.putSessionUserInfo(user.getUserId(), new UserVo(user));
			request.getSession().setAttribute(User.LOGIN_MEMBER, user);
			map.put("success", true);
			map.put("msg", "修改成功");
			writer.write(JSONTool.toJson(map));
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/bindWeixin")
	public void bindWeixin(HttpServletRequest request,PrintWriter writer,String userId,String weixinNo) {
		try {
			if (!StringTools.isEmpty(userId)) {
				User user = userMgrService.getUserById(userId);
				if (!StringTools.isEmpty(weixinNo)) {
					user.setWeixin(weixinNo);
					userMgrService.updateUser(user);
					cacheFacade.putSessionUserInfo(user.getUserId(), new UserVo(user));
				}
				
			}
			
			writer.print(1);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			writer.print(0);
		}
	}
	
	@RequestMapping("/repasswordSubmit")
	public void repasswordSubmit(HttpServletRequest request,PrintWriter writer, String oldPwd, String newPwd) {
		String enOldPwd = PasswordEncoderUtil.encode(oldPwd);
		String enNewPwd = PasswordEncoderUtil.encode(newPwd);
		try {
			User user = (User)request.getSession().getAttribute(User.LOGIN_MEMBER);
			if (!user.getPwd().equals(enOldPwd)) {
				writer.print(-1);
				return;
			}
			user.setPwd(enNewPwd);
			userMgrService.updateUser(user);
			
			writer.print(1);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			writer.print(0);
		}
	}
	
	/**
	 * new member register
	 * @param user
	 * @param writer
	 */
	@RequestMapping("/qqreg")
	public void qqreg(HttpServletRequest request,String qqOpenId,String nikeName,PrintWriter writer) {
		//check if the email or the moblile has already been reg
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserVo user = userMgrDAO.getUserByAccount(qqOpenId);
			if (user==null) {
				user = new UserVo();
				user.setAccount(qqOpenId);
				user.setCurrentOrgId("");
				user.setDefaultOrgId("");
				user.setEmail("");
				user.setExtend("");
				user.setLastModifyTime(new Date());
				user.setMemo("");
				user.setMobile("");
				user.setPoint(0);
				user.setPosition("");
				user.setCreateTime(new Date());
				user.setPwd(PasswordEncoderUtil.encode("1"));
				user.setUserId(UUIDTool.getUUID());
				if (StringTools.isEmail(nikeName)) {
					user.setUserName("新歌手");
				} else{
					user.setUserName(nikeName);
				}
				
				user.setUserState(true);
				userMgrDAO.addUser(user);
			}
			request.getSession().setAttribute("loginMember", user);
			map.put("success", true);
			map.put("msg", "登录成功");
			writer.write(JSONTool.toJson(map));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
	}
	
}
