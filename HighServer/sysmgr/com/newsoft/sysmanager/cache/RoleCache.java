package com.newsoft.sysmanager.cache;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.newsoft.foundation.cache.Cache;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.vo.RoleVo;

/**
 * The role cache implementation.
 * 
 * @author guohb
 * 
 */
@Component
public class RoleCache extends BaseCache {

	@SuppressWarnings("unchecked")
	public List<RoleVo> getRolesByUrl(String url) {
		if (url == null || url.trim().equals("")) {
			return null;
		}
		return (List<RoleVo>) getUrlRolesMappingCache().getObjectValue(url);
	}

	public void putUrlMappingRoles(String url, List<RoleVo> roleList) {
		if (url == null || url.trim().equals("") || roleList == null) {
			return;
		}

		getUrlRolesMappingCache().put(url, (Serializable) roleList);
	}

	public void discardUrlMappingRoles() {
		getUrlRolesMappingCache().removeAll();
	}

	@SuppressWarnings("unchecked")
	public List<Role> getUserRoles(String userId) {
		if (userId == null || userId.trim().equals("")) {
			return null;
		}

		return (List<Role>) getUserRolesCache().getObjectValue(userId);
	}

	public void putUserRoles(String userId, List<Role> roleList) {
		if (userId == null || userId.trim().equals("") || roleList == null) {
			return;
		}

		getUserRolesCache().put(userId, (Serializable) roleList);
	}

	public void discardUserRole(String userId) {
		getUserRolesCache().removeElement(userId);
	}

	private Cache getUrlRolesMappingCache() {
		return getCache(CacheEntityType.URL_ROLE.getCacheKey());
	}

	private Cache getUserRolesCache() {
		return getCache(CacheEntityType.USER_ROLES.getCacheKey());
	}
}
