package com.newsoft.sysmanager.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newsoft.sysmanager.dao.OperatorDAO;
import com.newsoft.sysmanager.dao.RoleMgrDAO;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.po.Operator;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.vo.UserVo;

/**
 * 
 * @author mengxw
 * 
 */
@Component
public class UserCacheSupport {

	private Logger logger = LoggerFactory.getLogger(UserCacheSupport.class);

	@Autowired
	private UserMgrDAO userMgrDAO;
	
	@Autowired
	private RoleMgrDAO roleMgrDAO;
	
	@Autowired
	private OperatorDAO operatorDAO;

	@Autowired
	private CacheFacade cacheFacade;

	public UserVo getUserByAccount(String account) {
		if (account == null) {
			return null;
		}
		UserVo cachedUserVo = cacheFacade.getUserAccountInfo(account);
		if (cachedUserVo != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get user detail from cache, userInfo: {}", cachedUserVo);
			}
			return cachedUserVo;
		}

		UserVo userVo = userMgrDAO.getUserByAccount(account);
		if (userVo == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("Can't find user detail with account: {}", account);
			}
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Succeed to get user detail from database, userInfo: {} , put it to cache now", userVo);
		}
		cacheFacade.putUserAccountInfo(account, userVo);

		return userVo;
	}
	
	public UserVo getUserById(String userId) {
		if (userId == null) {
			return null;
		}
		UserVo cachedUserVo = cacheFacade.getSessionUserInfo(userId);
		if (cachedUserVo != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get user detail from cache, userInfo: {}", cachedUserVo);
			}
			return cachedUserVo;
		}

		UserVo userVo = userMgrDAO.getUserByUserId(userId);
		if (userVo == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("Can't find user detail with userId: {}", userId);
			}
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Succeed to get user detail from database, userInfo: {} , put it to cache now", userVo);
		}
		cacheFacade.putSessionUserInfo(userId, userVo);

		return userVo;
	}
	
	public List<User> getAllUser() {
		List<User> cacheList = cacheFacade.getAllUsers();
		if (cacheList != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get all user from cache");
			}
			return cacheList;
		}

		List<User> userList = userMgrDAO.getAllUsers();
		if (logger.isDebugEnabled()) {
			logger.debug("Succeed to get all user from database, put it to cache now");
		}
		cacheFacade.putAllUsers(userList);

		return userList;
	}
	
	public List<Role> getUserRoles(String userId) {
		if (userId == null) {
			return null;
		}
		List<Role> cachedRoles = cacheFacade.getUserRoles(userId);
		if (cachedRoles != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get roles from cache for userId: [{}], size:{}", userId, cachedRoles.size());
			}

			return cachedRoles;
		}

		List<Role> roles = roleMgrDAO.getRoleListByUserId(userId);
		if (roles != null) {
			cacheFacade.putUserRoles(userId, roles);

			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get roles from database for userId: [{}], size:{}, put it to cache now",
						userId, roles.size());
			}
		}
		return roles;
	}
	
	public List<Operator> getOperatorByRole(String roleId) {
		if (roleId == null) {
			return null;
		}

		List<Operator> cachedOperators = cacheFacade.getOperations(roleId);
		if (cachedOperators != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get operators from cache for roleId: [{}], size:{}", roleId,
						cachedOperators.size());
			}

			return cachedOperators;
		}

		List<Operator> operationList = operatorDAO.getOperatorByRole(roleId);
		if (operationList != null) {
			cacheFacade.putOperations(roleId, operationList);

			if (logger.isDebugEnabled()) {
				logger.debug("Succeed to get operators from database for roleId: [{}], size:{}, put it to cache now",
						roleId, operationList.size());
			}
		}
		return operationList;
	}
}
