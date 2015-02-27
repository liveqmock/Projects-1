package com.newsoft.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.newsoft.sysmanager.cache.CacheFacade;
import com.newsoft.sysmanager.dao.RoleMgrDAO;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.po.Role;

/**
 * AccessdecisionManager在Spring security中是很重要的。
 * 
 * 在验证部分简略提过了，所有的Authentication实现需要保存在一个GrantedAuthority对象数组中。 这就是赋予给主体的权限。
 * GrantedAuthority对象通过AuthenticationManager 保存到
 * Authentication对象里，然后从AccessDecisionManager读出来，进行授权判断。
 * 
 * Spring Security提供了一些拦截器，来控制对安全对象的访问权限，例如方法调用或web请求。
 * 一个是否允许执行调用的预调用决定，是由AccessDecisionManager实现的。 这个 AccessDecisionManager
 * 被AbstractSecurityInterceptor调用， 它用来作最终访问控制的决定。
 * 这个AccessDecisionManager接口包含三个方法：
 * 
 * void decide(Authentication authentication, Object secureObject,
 * List<ConfigAttributeDefinition> config) throws AccessDeniedException; boolean
 * supports(ConfigAttribute attribute); boolean supports(Class clazz);
 * 
 * 从第一个方法可以看出来，AccessDecisionManager使用方法参数传递所有信息，这好像在认证评估时进行决定。
 * 特别是，在真实的安全方法期望调用的时候，传递安全Object启用那些参数。 比如，让我们假设安全对象是一个MethodInvocation。
 * 很容易为任何Customer参数查询MethodInvocation，
 * 然后在AccessDecisionManager里实现一些有序的安全逻辑，来确认主体是否允许在那个客户上操作。
 * 如果访问被拒绝，实现将抛出一个AccessDeniedException异常。
 * 
 * 这个 supports(ConfigAttribute) 方法在启动的时候被
 * AbstractSecurityInterceptor调用，来决定AccessDecisionManager
 * 是否可以执行传递ConfigAttribute。 supports(Class)方法被安全拦截器实现调用，
 * 包含安全拦截器将显示的AccessDecisionManager支持安全对象的类型。
 */
public class NSAccessDecisionManager implements AccessDecisionManager {
	private Logger logger = LoggerFactory.getLogger(NSAccessDecisionManager.class);

	@Autowired
	private RoleMgrDAO roleMgrDAO;

	@Autowired
	private UserMgrDAO userMgrDAO;

	@Autowired
	private CacheFacade cacheFacade;

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
		String userId = "";
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			// 用户所拥有的角色
			userId = ((org.springframework.security.core.userdetails.User) principal).getUsername();

			List<Role> userRoleList = getUserRoles(userId);

			// authentication.getAuthorities()
			Iterator<ConfigAttribute> ite = configAttributes.iterator();
			while (ite.hasNext()) {
				ConfigAttribute ca = ite.next();
				String needRole = ((SecurityConfig) ca).getAttribute();
				// needRole 为访问相应的资源应该具有的权限。
				for (Role role : userRoleList) {
					if (logger.isTraceEnabled()) {
						logger.trace("(needRole: " + needRole + ", current user role:" + role.getRoleId());
					}

					if (needRole.trim().equals("ROLE_" + role.getRoleId())) {
						return;
					}
				}
			}
		}
		if (logger.isWarnEnabled()) {
			logger.warn("User [" + userId + "] is not granted to access object: " + object);
		}
		throw new AccessDeniedException("User [" + userId + "] is not granted to access object: " + object);
	}

	private List<Role> getUserRoles(String userId) {
		if (userId == null) {
			return null;
		}

		List<Role> cachedRoles = cacheFacade.getUserRoles(userId);
		if (cachedRoles != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get role list from cache, size is: {} , userId:{}", cachedRoles.size(), userId);
			}

			return cachedRoles;
		}

		List<Role> roleList = roleMgrDAO.getRoleListByUserId(userId);
		if (roleList != null && !roleList.isEmpty()) {
			cacheFacade.putUserRoles(userId, roleList);

			if (logger.isDebugEnabled()) {
				logger.debug(
						"Succeed to get role list from database with size: [{}], userId:[{}], put it to cache now ",
						roleList == null ? 0 : roleList.size(), userId);
			}
		}
		return roleList;
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}
