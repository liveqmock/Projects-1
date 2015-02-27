package com.newsoft.sysmanager.cache;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.newsoft.foundation.cache.Cache;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.vo.UserVo;

/**
 * The user detail cache implementation.
 * 
 * @author guohb
 * 
 */
@Component
public class UserCache extends BaseCache {

	private static final String All_User_KEY = "AllUser";

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return (List<User>) getAllUserCache().getObjectValue(All_User_KEY);
	}

	public void putAllUsers(List<User> userList) {
		if (userList == null) {
			return;
		}
		getAllUserCache().put(All_User_KEY, (Serializable) userList);
	}

	public void discardAllUsers() {
		getAllUserCache().removeAll();
	}

	
	public UserVo getSessionUserInfo(String userId) {
		if (userId == null) {
			return null;
		}

		return (UserVo) getSessionUserCache().getObjectValue(userId);
	}

	public void putSessionUserInfo(String userId, UserVo user) {
		if (userId == null || user == null) {
			return;
		}
		getSessionUserCache().put(userId, user);
	}

	private Cache getSessionUserCache() {
		return getCache(CacheEntityType.SESSION_USER.getCacheKey());
	}
	
	private Cache getAllUserCache() {
		return getCache(CacheEntityType.ALLUSER.getCacheKey());
	}
}
