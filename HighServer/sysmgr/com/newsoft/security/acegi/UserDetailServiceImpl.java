package com.newsoft.security.acegi;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.newsoft.sysmanager.cache.CacheFacade;
import com.newsoft.sysmanager.service.UserMgrService;
import com.newsoft.sysmanager.vo.UserVo;

/**
 * @author fengmq
 * 
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
	private static final Log logger = LogFactory.getLog(UserDetailServiceImpl.class);

	@Autowired
	private UserMgrService userMgrService;

	@Autowired
	private CacheFacade cacheFacade;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		UserVo user = userMgrService.getUserByAccount(username);
		if (user == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("查找不到用户名为: [" + username + "] 的用户数据!");
			}
			throw new UsernameNotFoundException(username);
		}
		
		Set<GrantedAuthority> grantedAuthoritys = new HashSet<GrantedAuthority>();
		grantedAuthoritys.add(new GrantedAuthorityImpl("ROLE_USER"));
		UserDetails acegiUser = new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPwd(),
				user.getUserState(), true, true, true, grantedAuthoritys);

		if (logger.isInfoEnabled()) {
			logger.info("用户: [" + user.getUserName() + "] 登录认证成功");
		}

		// 重置与该用户相关的缓存信息
		resetUserCache(user.getUserId());

		return acegiUser;
	}

	private void resetUserCache(String userId) {
		cacheFacade.discardUserRole(userId);
	}
}
