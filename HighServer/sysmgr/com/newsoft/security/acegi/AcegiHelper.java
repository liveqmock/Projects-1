package com.newsoft.security.acegi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.newsoft.sysmanager.cache.UserCacheSupport;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.vo.UserVo;

@Component
public class AcegiHelper {
	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private UserMgrDAO userMgrDAO;

	@Autowired
	private UserCacheSupport cacheSupport;

	/**
	 * 获取当前session的用户信息
	 * 
	 * @return
	 */
	public String getSessionUserId() {
		UserVo user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object obj = auth.getPrincipal();
			user = getUserByPrincipal(obj);
		}
		return user.getUserId();
	}

	/**
	 * 获取当前session的用户信息
	 * 
	 * @return
	 */
	public UserVo getSessionUser() {
		UserVo user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			Object obj = auth.getPrincipal();
			user = getUserByPrincipal(obj);
		}
		return user;

	}

	/**
	 * 获取principal用户（可扩展，暂时只采用UserDetails的session用户）
	 * 
	 * @param principal
	 * @return
	 */
	private UserVo getUserByPrincipal(Object principal) {
		if (principal == null) {
			return null;
		}

		if (principal instanceof UserDetails) {
			UserDetails acegiUser = (UserDetails) principal;
			return cacheSupport.getUserById(acegiUser.getUsername());
		} else if (principal.equals("anonymousUser") || principal.equals("roleAnonymous")) {
			UserVo user = new UserVo();
			user.setUserName("匿名用户");
			return user;
		}
		return null;
	}

	/**
	 * 根据sessionId获取session用户
	 * 
	 * @param sessionId
	 * @return
	 */
	public UserVo getSessionUserBySessionId(String sessionId) {
		UserVo user = null;
		SessionInformation si = sessionRegistry.getSessionInformation(sessionId);
		Object principal = si.getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails acegiUser = (UserDetails) principal;
			return cacheSupport.getUserById(acegiUser.getUsername());
		} else if (principal.equals("anonymousUser") || principal.equals("roleAnonymous")) {
			user = new UserVo();
		}
		return user;
	}

}
