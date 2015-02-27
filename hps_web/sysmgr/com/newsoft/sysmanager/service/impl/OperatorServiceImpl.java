package com.newsoft.sysmanager.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.security.acegi.SessionUtil;
import com.newsoft.sysmanager.bean.ModelNameBean;
import com.newsoft.sysmanager.bean.OperatorBean;
import com.newsoft.sysmanager.bean.OperatorDefineBean;
import com.newsoft.sysmanager.bean.SystemNameBean;
import com.newsoft.sysmanager.cache.UserCacheSupport;
import com.newsoft.sysmanager.dao.OperatorDAO;
import com.newsoft.sysmanager.dao.RoleMgrDAO;
import com.newsoft.sysmanager.dao.UserMgrDAO;
import com.newsoft.sysmanager.po.Operator;
import com.newsoft.sysmanager.po.Role;
import com.newsoft.sysmanager.service.OperatorService;
import com.newsoft.sysmanager.service.RoleOperatorService;
import com.newsoft.sysmanager.vo.UserVo;
import com.newsoft.utils.DigesterXMLTools;
import com.newsoft.utils.StringTools;

@Service
public class OperatorServiceImpl extends DigesterXMLTools implements OperatorService {
	@Autowired
	private OperatorDAO operatorDAO;
	@Autowired
	private RoleOperatorService roleOperatorService;

	private OperatorDefineBean operatorDefineBean;

	@Autowired
	private UserMgrDAO userMgrDAO;

	@Autowired
	private RoleMgrDAO roleMgrDAO;

	@Autowired
	private UserCacheSupport cacheSupport;

	/**
	 * 从xml中解析获取操作集合
	 * 
	 * @return
	 */
	public Set<Operator> getOperatorListFromXml() {
		Set<Operator> operationList = new HashSet<Operator>(0);
		List<SystemNameBean> systemNameBeanList = getOperatorDefineBean().getSystemNameBeanList();
		for (SystemNameBean systemNameBean : systemNameBeanList) {
			List<ModelNameBean> modelNameBeanList = systemNameBean.getModelNameBeanList();
			for (ModelNameBean modelNameBean : modelNameBeanList) {
				List<OperatorBean> operatorBeanList = modelNameBean.getOperatorBeanList();
				for (OperatorBean operatorBean : operatorBeanList) {
					Operator operator = new Operator();
					operator.setOperatorId(operatorBean.getOperatorId());
					operator.setOperatorName(operatorBean.getOperatorName());
					operator.setUrlmapping(operatorBean.getUrlmapping());
					operator.setModuleName(modelNameBean.getModleName());
					operationList.add(operator);
				}
			}
		}
		return operationList;
	}

	protected String getDigesteRulesXmlPath() {
		return "digester-rules_operatorDefine.xml";
	}

	@Override
	protected InputStream getXMLInputStream() {
		try {
			return OperatorServiceImpl.class.getResourceAsStream("/operatorDefine.xml");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private OperatorDefineBean getOperationDefineBeanFromXml() {
		return (OperatorDefineBean) getXMLBean();
	}

	/**
	 * 从数据库清除多余的操作类
	 * 
	 * @param dbOperatorList
	 * @param xmlOperatorList
	 */
	public void removeDBOperatorNotInXml(List<Operator> dbOperatorList, Set<Operator> xmlOperatorList) throws Exception {
		boolean isRemove = true;
		List<Operator> deleteOperatorList = new ArrayList<Operator>();
		for (Operator dbOperator : dbOperatorList) {
			for (Operator xmlOperator : xmlOperatorList) {
				if (isSame(dbOperator, xmlOperator)) {
					isRemove = false;
					break;
				}
			}
			if (isRemove) {
				deleteOperatorList.add(dbOperator);
			}
		}
		for (Operator deleteOperator : deleteOperatorList) {
			dbOperatorList.remove(deleteOperator);
			// 调用dao删除数据
			operatorDAO.deleteOperator(deleteOperator.getOperatorId());
			// 删除角色操作关联
			roleOperatorService.deleteByOperatorId(deleteOperator.getOperatorId());
		}
	}

	/**
	 * 判断两个操作是否同一个
	 * 
	 * @param dbOperation
	 * @param xmlOperator
	 * @return
	 */
	private boolean isSame(Operator dbOperation, Operator xmlOperator) {
		return dbOperation.getOperatorId() != null && dbOperation.getOperatorId().equals(xmlOperator.getOperatorId());
	}

	/**
	 * 更新数据库内的操作类
	 * 
	 * @param dbOperatorList
	 * @param xmlOperatorList
	 */
	public void updateOperatorToDB(List<Operator> dbOperatorList, Set<Operator> xmlOperatorList) throws Exception {
		boolean isNew;
		for (Operator xmlOperator : xmlOperatorList) {
			isNew = true;
			for (Operator dbOperation : dbOperatorList) {
				if (isSame(dbOperation, xmlOperator)) {
					isNew = false;
					dbOperation.setOperatorName(xmlOperator.getOperatorName());
					dbOperation.setModuleName(xmlOperator.getModuleName());
					dbOperation.setUrlmapping(xmlOperator.getUrlmapping());
					// 调用DAO更新操作
					operatorDAO.sysUpdateOperator(dbOperation);
					break;
				}
			}
			if (isNew) {
				Operator operator = new Operator();
				operator.setOperatorId(xmlOperator.getOperatorId());
				operator.setOperatorName(xmlOperator.getOperatorName());
				operator.setUrlmapping(xmlOperator.getUrlmapping());
				operator.setModuleName(xmlOperator.getModuleName());
				// 调用DAO更新
				operatorDAO.addOperator(operator);
			}
		}
	}

	/**
	 * 获取数据库中所有操作权限
	 */
	public List<Operator> getAllOperator() {
		List<Operator> operatorList = operatorDAO.getAllOperator();
		return operatorList;
	}

	/**
	 * 获取所有操作资源
	 * 
	 * @return
	 */
	public OperatorDefineBean getOperatorDefineBean() {
		if (operatorDefineBean == null) {
			operatorDefineBean = getOperationDefineBeanFromXml();
		}
		return operatorDefineBean;
	}

	public List<SystemNameBean> getSystemNameBeanList() {
		return getOperatorDefineBean().getSystemNameBeanList();
	}

	/**
	 * 根据当前用户获取操作ids
	 * 
	 * @return
	 */
	public String getOperationIdsBySessionUser() {
		Set<Operator> operators = getOperatorsBySessionUser();
		String operatorIds = "";
		for (Operator operator : operators) {
			operatorIds = StringTools.uniteTwoStringBySemicolon(operatorIds, operator.getOperatorId(), ",");
		}
		return "," + operatorIds + ",";
	}

	/**
	 * 获取当前用户所有角色权限
	 * 
	 * @return
	 */
	private Set<Operator> getOperatorsBySessionUser() {
		UserVo user = SessionUtil.getSessionUser();

		// 获取用户所拥有的角色
		List<Role> userRoles = cacheSupport.getUserRoles(user.getUserId());

		Set<Operator> permissions = new HashSet<Operator>();
		for (Role role : userRoles) {
			permissions.addAll(cacheSupport.getOperatorByRole(role.getRoleId()));
		}
		return permissions;
	}
	
}
