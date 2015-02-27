package com.newsoft.security.acegi;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import com.newsoft.common.spring.SpringBeanManager;

/**
 * AOP切面类，拦截使用acl权限控制的实体更新的方法
 * 
 * @author fengmq
 * 
 */
@Aspect
@Service
public class AclAspect {
	@After("execution(* com.newsoft..*DAO.*(..)) && args(aclFieldsObject)")
	public void updateDomainObjectAces(JoinPoint jp,
			AclFieldsObject aclFieldsObject) {
		String signature = jp.getSignature().toString();// 获取目标方法签名
		String methodName = signature.substring(signature.lastIndexOf(".") + 1,
				signature.indexOf("("));
		// System.out.println("方法名称：" + methodName);
		if (methodName.startsWith("add") || methodName.startsWith("insert")
				|| methodName.startsWith("update")
				|| methodName.startsWith("edit")) {
			// System.out.println("执行该方法被拦截，需要更新acl表读者信息:"
			// + aclFieldsObject.getReaders());
			// System.out.println("执行该方法被拦截，需要更新acl表管理者信息:"
			// + aclFieldsObject.getAdministrators());
			AclManager aclManager = (AclManager) SpringBeanManager
					.getBean("aclManagerImpl");
			aclManager.updateReaders(aclFieldsObject);
			aclManager.updateAdministrators(aclFieldsObject);
		}
	}
}
