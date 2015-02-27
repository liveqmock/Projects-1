package com.newsoft.sysmanager.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.sysmanager.dao.RoleMgrDAO;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.dao.UserRoleDAO;
import com.newsoft.sysmanager.po.Operator;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.RoleOperator;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.po.UserRole;
import com.newsoft.sysmanager.service.OperatorService;
import com.newsoft.sysmanager.service.RoleOperatorService;
import com.newsoft.sysmanager.vo.RoleVo;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.PasswordEncoderUtil;

/**
 * init the base data
 * @author mengxw
 *
 */
@Service
public class InitializeDataServiceImpl {
	private static final Log logger = LogFactory.getLog(InitializeDataServiceImpl.class);
	@Autowired
	private RoleMgrDAO roleMgrDAO;
	@Autowired
	private UserMgrDAO userMgrDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private RoleOperatorService roleOperatorService;
	/**
	 * 系统启动时初始化数据
	 */

	public void initData() {
		// 初始化操作权限
		initOperation();
		// 初始系统角色
		initRoles();
		// 初始系统用户
		initSystemUser();
		// 初始化角色权限
		initRolePermission();
	}
	
	
	/**
	 * 初始化系统操作权限
	 * 
	 * 
	 */
	private void initOperation() {
		try {
			Set<Operator> xmlOperationList = operatorService.getOperatorListFromXml();
			List<Operator> dbOperationList = operatorService.getAllOperator();
			operatorService.removeDBOperatorNotInXml(dbOperationList, xmlOperationList);
			operatorService.updateOperatorToDB(dbOperationList, xmlOperationList);
		} catch (Exception e) {
			logger.error("error when init operation", e);
		}
	}

	/**
	 * 初始化角色
	 */
	private void initRoles() {
		// 创建一个超级管理员角色，不属于任何公司，拥有所有资源
		RoleVo ADMINISTRATOR = roleMgrDAO.getRoleByRoleId(Role.ROLE_ADMIN_ID);
		if (ADMINISTRATOR == null) {
			ADMINISTRATOR = new RoleVo();
			ADMINISTRATOR.setMemo("系统自动创建：超级管理员角色");
			ADMINISTRATOR.setOrgId(null);
			ADMINISTRATOR.setRoleId(Role.ROLE_ADMIN_ID);
			ADMINISTRATOR.setRoleName("超级管理员");
			ADMINISTRATOR.setRoleType(1);
			try {
				roleMgrDAO.addRole(ADMINISTRATOR);
			} catch (Exception e) {
				logger.error("error when init roles", e);
			}
		}
		
		// 创建一个超级管理员角色，不属于任何公司，拥有所有资源
		RoleVo EXECMENBER = roleMgrDAO.getRoleByRoleId(Role.ROLE_EXCE_MEMBER);
		if (EXECMENBER == null) {
			EXECMENBER = new RoleVo();
			EXECMENBER.setMemo("系统自动创建：普通会员");
			EXECMENBER.setOrgId(null);
			EXECMENBER.setRoleId(Role.ROLE_EXCE_MEMBER);
			EXECMENBER.setRoleName("普通会员");
			EXECMENBER.setRoleType(1);
			try {
				roleMgrDAO.addRole(EXECMENBER);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				logger.error("error when init roles", e);
			}
		}
		
		RoleVo recordeRoleVo = roleMgrDAO.getRoleByRoleId(Role.ROLE_COM_MEMBER);
		if (recordeRoleVo == null) {
			recordeRoleVo = new RoleVo();
			recordeRoleVo.setMemo("系统自动创建：企业会员");
			recordeRoleVo.setOrgId(null);
			recordeRoleVo.setRoleId(Role.ROLE_COM_MEMBER);
			recordeRoleVo.setRoleName("企业会员");
			recordeRoleVo.setRoleType(1);
			try {
				roleMgrDAO.addRole(recordeRoleVo);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				logger.error("error when init roles", e);
			}
		}
	}

	/**
	 * 初始化系统用户
	 */
	private void initSystemUser() {
		UserVo administrator = userMgrDAO.getUserByUserId(User.ADMINISTRATOR_ID);
		if (administrator == null) {
			administrator = new UserVo();
			administrator.setUserId(User.ADMINISTRATOR_ID);
			administrator.setAccount(User.ADMINISTRATOR_ACCOUNT);
			administrator.setDefaultOrgId("");
			administrator.setEmail("");
			administrator.setMemo("");
			administrator.setMobile("");
			administrator.setPosition("");
			administrator.setPwd(PasswordEncoderUtil.encode("1"));
			administrator.setSex(true);
			administrator.setTelephone("");
			administrator.setUserName("超级管理员");
			administrator.setUserState(true);
			try {
				userMgrDAO.addUser(administrator);
				UserRole userRole = new UserRole(User.ADMINISTRATOR_ID, Role.ROLE_ADMIN_ID);
				userRoleDAO.insertUserRoleRelation(userRole);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				logger.error("error when init system user", e);
			}
		}
	}

	/**
	 * 初始化角色权限
	 * 
	 */
	private void initRolePermission() {
		try {
			List<RoleOperator> oldList = roleOperatorService.getRoleOperatorByRoleId(Role.ROLE_ADMIN_ID);
			// 获取所有操作资源
			Set<Operator> xmlOperationList = operatorService.getOperatorListFromXml();
			// 获取到需要移除的关联
			List<RoleOperator> forRemove = new ArrayList<RoleOperator>();
			for (RoleOperator roleOperator : oldList) {
				boolean isSame = false;
				for (Operator operator : xmlOperationList) {
					if (roleOperator.getOperatorId().equals(operator.getOperatorId())) {
						isSame = true;
						break;
					}
				}
				if (!isSame) {
					forRemove.add(roleOperator);
				}
			}

			// 获取待新增的roleOperator
			List<String> list = new ArrayList<String>();
			for (Operator operator : xmlOperationList) {
				boolean isSame = false;
				for (RoleOperator roleOperator : oldList) {
					if (operator.getOperatorId().equals(roleOperator.getOperatorId())) {
						isSame = true;
						break;
					}
				}
				if (!isSame) {
					list.add(operator.getOperatorId());
				}
			}
			// 执行删除
			for (RoleOperator roleOperator : forRemove) {
				roleOperatorService.deleteRoleOperator(roleOperator);
			}
			// 处理新增
			if (list.size() > 0) {
				String[] operatorRes = new String[list.size()];
				roleOperatorService.insertRoleOperator(Role.ROLE_ADMIN_ID, list.toArray(operatorRes));
			}
		} catch (Exception e) {
			logger.error("error when init role permission", e);
		}
	}
}
