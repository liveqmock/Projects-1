package com.newsoft.security.acegi;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.common.spring.SpringBeanManager;
import com.newsoft.security.dao.AclDAO;
import com.newsoft.security.po.AclEntry;
import com.newsoft.sysmanager.dao.UserRoleDAO;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.po.User;
import com.newsoft.sysmanager.po.UserRole;
import com.newsoft.utils.StringTools;

/**
 * acl权限管理服务类
 * 
 * @author fengmq
 * 
 */
@Service
public class AclManagerImpl implements AclManager {
	private static final Logger logger = Logger.getLogger(AclManagerImpl.class);

	@Autowired
	private AcegiHelper acegiHelper;
	@Autowired
	private AclDAO aclDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;

	/**
	 * 添加一个权限
	 */
	public void addPermission(String objectId, String securityId,
			int powerType, String moduleName) {
		AclEntry aclEntry = new AclEntry();
		aclEntry.setObjectId(objectId);
		aclEntry.setSecurityId(securityId);
		aclEntry.setPowerType(powerType);
		aclEntry.setModuleName(moduleName);
		// 判断是否已经存在该授权
		List<AclEntry> list = aclDAO.getAclEntry(aclEntry);
		try {
			if (list.size() > 0) {
				aclEntry = list.get(0);
				aclEntry.setGrantCount(aclEntry.getGrantCount() + 1);
				aclDAO.updateAclEntry(aclEntry);
			} else {
				aclEntry.setGrantCount(1);
				aclDAO.addAclEntry(aclEntry);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 根据数据ID删除权限信息
	 */
	public void deletePermissionByObjectId(String objectId) {
		AclEntry aclEntry = new AclEntry();
		aclEntry.setObjectId(objectId);
		try {
			aclDAO.deleteAclEntry(aclEntry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断用户是否有权限，包括查看和管理
	 */
	public boolean judgeUserPower(String objectId) {
		User user = acegiHelper.getSessionUser();
		AclEntry aclEntry = new AclEntry();
		aclEntry.setObjectId(objectId);
		aclEntry.setSecurityId(user.getUserId());
		List<AclEntry> list = aclDAO.judgeUserPower(aclEntry);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断用户是否有某个类型的权限
	 */
	public boolean judgeUserPower(String objectId, int powerType) {
		User user = acegiHelper.getSessionUser();
		AclEntry aclEntry = new AclEntry();
		aclEntry.setObjectId(objectId);
		aclEntry.setPowerType(powerType);
		aclEntry.setSecurityId(user.getUserId());
		List<AclEntry> list = aclDAO.judgeUserPower(aclEntry);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断用户是否有查看权限
	 */
	public boolean judgeUserViewPower(String objectId) {
		User user = acegiHelper.getSessionUser();
		AclEntry aclEntry = new AclEntry();
		aclEntry.setObjectId(objectId);
		// 1为查看权限
		aclEntry.setPowerType(1);
		aclEntry.setSecurityId(user.getUserId());
		List<AclEntry> list = aclDAO.judgeUserPower(aclEntry);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断用户是否有管理权限
	 */
	public boolean judgeUserManagePower(String objectId) {
		User user = acegiHelper.getSessionUser();
		AclEntry aclEntry = new AclEntry();
		aclEntry.setObjectId(objectId);
		// 2为管理权限
		aclEntry.setPowerType(2);
		aclEntry.setSecurityId(user.getUserId());
		List<AclEntry> list = aclDAO.judgeUserPower(aclEntry);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 更新授权次数
	 */
	public void updateReaders(String objectId, String securityIds,
			String moduleName) {
		AclEntry aclEntry = new AclEntry();
		aclEntry.setObjectId(objectId);
		aclEntry.setPowerType(1);
		aclEntry.setModuleName(moduleName);
		try {
			// 首先删除原有的查看者权限信息
			aclDAO.deleteAclEntry(aclEntry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!StringTools.isEmpty(securityIds)) {
			// 查看权限信息
			String[] readerArr = securityIds
					.split(AclFieldsObject.ACL_Field_Values_Separator);
			for (String reader : readerArr) {
				addPermission(objectId, reader, 1, moduleName);
			}
		}
	}

	/**
	 * 更新管理者范围
	 */
	public void updateAdministrators(String objectId, String securityIds,
			String moduleName) {
		AclEntry aclEntry = new AclEntry();
		aclEntry.setObjectId(objectId);
		aclEntry.setPowerType(2);
		aclEntry.setModuleName(moduleName);
		try {
			// 首先删除原有的管理者权限信息
			aclDAO.deleteAclEntry(aclEntry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!StringTools.isEmpty(securityIds)) {
			// 查看权限信息
			String[] readerArr = securityIds
					.split(AclFieldsObject.ACL_Field_Values_Separator);
			for (String reader : readerArr) {
				addPermission(objectId, reader, 2, moduleName);
			}
		}
	}

	/**
	 * 更新授权次数
	 */
	public boolean updateGrantCount(String objectId, String securityId,
			int powerType, int offsetCount) {
		AclEntry aclEntry = new AclEntry();
		aclEntry.setObjectId(objectId);
		aclEntry.setSecurityId(securityId);
		aclEntry.setPowerType(powerType);
		List<AclEntry> list = aclDAO.getAclEntry(aclEntry);
		if (list != null && list.size() > 0) {
			try {
				for (AclEntry acl : list) {
					int count = acl.getGrantCount() + offsetCount;
					if (count <= 0) {
						aclDAO.deleteAclEntry(acl);
					} else {
						acl.setGrantCount(count);
						aclDAO.updateAclEntry(acl);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 更新读者范围
	 */
	public void updateReaders(AclFieldsObject aclFieldsObject) {
		// 数据Id
		String objectId = aclFieldsObject.getObjectId();
		// 获取新的读者范围
		String readers = aclFieldsObject.getReaders();
		//
		String moduleName = aclFieldsObject.getModuleName();

		if (readers == null) {
			readers = "";
		}
		if (readers.indexOf(Role.ROLE_ADMIN_ID) == -1) {
			readers += AclFieldsObject.ACL_Field_Values_Separator
					+ Role.ROLE_ADMIN_ID;
		}
		updateReaders(objectId, readers, moduleName);
	}

	/**
	 * 更新管理者范围
	 */
	public void updateAdministrators(AclFieldsObject aclFieldsObject) {
		// 数据Id
		String objectId = aclFieldsObject.getObjectId();
		String moduleName = aclFieldsObject.getModuleName();
		// 管理者范围
		String administrators = aclFieldsObject.getAdministrators();
		if (administrators == null) {
			administrators = "";
		}
		if (administrators.indexOf(Role.ROLE_ADMIN_ID) == -1) {
			// 增加系统管理员角色
			administrators += AclFieldsObject.ACL_Field_Values_Separator
					+ Role.ROLE_ADMIN_ID;
		}
		updateAdministrators(objectId, administrators, moduleName);
	}

	/**
	 * 获取用户权限ID，包括用户ID,用户所在组织机构Id，用户所拥有角色Id，供acl查询使用
	 * 
	 * @return
	 */
	public String getUserSecurityIds() {
		String userId = SessionUtil.getSessionUserId();
		String securityIds = "'" + userId + "'";
		List<UserRole> userRoleList = userRoleDAO
				.getUserRoleRelationByUserId(userId);
		if (userRoleList.size() > 0) {
			for (UserRole userRole : userRoleList) {
				securityIds = StringTools.uniteTwoStringBySemicolon(
						securityIds, "'" + userRole.getRoleId() + "'", ",");
			}
		}
		return securityIds;
	}

	/**
	 * added by mengxw 调整获取ACL SQL语句策略，在执行DAO方法前单独获取
	 * 
	 * @return
	 */
	public String getAclSql(String moduleName) {
		AclManager aclManager = (AclManager) SpringBeanManager
				.getBean("aclManagerImpl");
		String sql = "select  distinct objectId from FRAME_ACL_ENTRY aclEntry "
				+ " where aclEntry.SECURITYID in("
				+ aclManager.getUserSecurityIds()
				+ ") and aclEntry.ModuleName='" + moduleName + "'";
		return sql;
	}
}
