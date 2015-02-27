package com.newsoft.sysmanager.cache;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.newsoft.foundation.cache.Cache;
import com.newsoft.sysmanager.po.Operator;

/**
 * The operation cache implementation.
 * 
 * @author guohb
 * 
 */
@Component
public class OperationCache extends BaseCache {

	@SuppressWarnings("unchecked")
	public List<Operator> getOperations(String roleId) {
		if (roleId == null) {
			return null;
		}

		return (List<Operator>) getRoleMappingOperationCache().getObjectValue(roleId);
	}

	public void putOperations(String roleId, List<Operator> operationList) {
		if (roleId == null || operationList == null) {
			return;
		}
		getRoleMappingOperationCache().put(roleId, (Serializable) operationList);
	}

	public void discardRoleOperations(String roleId) {
		getRoleMappingOperationCache().removeElement(roleId);
	}

	private Cache getRoleMappingOperationCache() {
		return getCache(CacheEntityType.ROLE_OPERATION.getCacheKey());
	}
}
