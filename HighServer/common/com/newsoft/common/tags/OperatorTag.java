package com.newsoft.common.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.newsoft.common.spring.SpringBeanManager;
import com.newsoft.sysmanager.service.OperatorService;

/**
 * 
 * 权限验证标签
 * 
 * @author Administrator
 * 
 */
public class OperatorTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String ifAllGranted;

	private String ifAnyGranted;

	private String ifNotGranted;

	public String getIfAllGranted() {
		return ifAllGranted;
	}

	public void setIfAllGranted(String ifAllGranted) {
		this.ifAllGranted = ifAllGranted;
	}

	public String getIfAnyGranted() {
		return ifAnyGranted;
	}

	public void setIfAnyGranted(String ifAnyGranted) {
		this.ifAnyGranted = ifAnyGranted;
	}

	public String getIfNotGranted() {
		return ifNotGranted;
	}

	public void setIfNotGranted(String ifNotGranted) {
		this.ifNotGranted = ifNotGranted;
	}

	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public int doStartTag() throws JspException {
		String userOperationIds = ((OperatorService) SpringBeanManager.getBean("operatorServiceImpl"))
				.getOperationIdsBySessionUser();
		boolean sign = true;
		if (StringUtils.isNotBlank(ifAnyGranted)) {
			String[] anyOperationArr = ifAnyGranted.split(",");
			for (String anyOperation : anyOperationArr) {
				if (isUserHasOperator(anyOperation, userOperationIds)) {
					return Tag.EVAL_BODY_INCLUDE;
				}
			}
		}
		if (StringUtils.isNotBlank(ifAllGranted)) {
			sign = true;
			String[] allOperationArr = ifAllGranted.split(",");
			for (String allOperation : allOperationArr) {
				if (isUserHasOperator(allOperation, userOperationIds)) {
				} else {
					sign = false;
					break;
				}
			}
			if (sign) {
				return Tag.EVAL_BODY_INCLUDE;
			}
		}
		if (StringUtils.isNotBlank(ifNotGranted)) {
			sign = true;
			String[] notOperationArr = ifNotGranted.split(",");
			for (String notOperator : notOperationArr) {
				if (isUserHasOperator(notOperator, userOperationIds)) {
					sign = false;
					break;
				}
			}
			if (sign) {
				return Tag.EVAL_BODY_INCLUDE;
			}
		}
		return Tag.SKIP_BODY;
	}

	/**
	 * 判断用户是否有权限
	 * 
	 * @param operationId
	 * @param userOperationIds
	 * @return
	 */
	public boolean isUserHasOperator(String operatorId, String userOperatorIds) {
		userOperatorIds = StringUtils.defaultIfEmpty(userOperatorIds, "");
		if (userOperatorIds.indexOf("," + operatorId + ",") != -1) {
			return true;
		}
		return false;
	}
}
