package com.newsoft.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.newsoft.sysmanager.cache.CacheFacade;
import com.newsoft.sysmanager.dao.RoleMgrDAO;
import com.newsoft.sysmanager.vo.RoleVo;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 * 
 */
@Service
public class NSInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
	private Logger logger = LoggerFactory.getLogger(NSInvocationSecurityMetadataSourceService.class);

	@Autowired
	private RoleMgrDAO roleMgrDAO;

	@Autowired
	private CacheFacade cacheFacade;

	// 根据URL，找到相关的权限配置。
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// object 是一个URL，被用户请求的url。
		String url = ((FilterInvocation) object).getRequestUrl();
		int firstQuestionMarkIndex = url.indexOf("?");
		if (firstQuestionMarkIndex != -1) {
			url = url.substring(0, firstQuestionMarkIndex);
		}
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}

		List<RoleVo> roleList = this.getRolesByUrl(url);
		if (roleList == null || roleList.isEmpty()) {
			return null;
		}

		// 最终返回的角色ID，这里主要需要过来不是当前公司的角色
		Collection<ConfigAttribute> resultCa = new ArrayList<ConfigAttribute>();
		for (RoleVo r : roleList) {
			// 加ROLE_前缀
			ConfigAttribute ca = new SecurityConfig("ROLE_" + r.getRoleId());
			resultCa.add(ca);
		}

		return resultCa;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return new ArrayList<ConfigAttribute>();
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

	private List<RoleVo> getRolesByUrl(String url) {
		if (url == null) {
			return null;
		}
		List<RoleVo> cachedRoleList = cacheFacade.getUrlMappingRoles(url);
		if (cachedRoleList != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get role list from cache, size is: {} ", cachedRoleList.size());
			}

			return cachedRoleList;
		}
		List<RoleVo> roleList = roleMgrDAO.getRolesByUrl(url + "%");
		// Cache the roles according to URL
		cacheFacade.putUrlMappingRoles(url, roleList);

		if (logger.isDebugEnabled()) {
			logger.debug("Succeed to get role list from database with size: [{}], url:[{}], put it to cache now ",
					roleList == null ? 0 : roleList.size(), url);
		}

		return roleList;
	}

}
