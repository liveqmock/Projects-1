package com.newsoft.sysmanager.cache;

/**
 * Define the entity type of cache.
 * 
 * @author guohb
 * 
 */
public enum CacheEntityType {
	ALLUSER("UserCache"), URL_ROLE("UrlRoleCache"), SESSION_USER("SessionUserCache"), USER_ROLES(
			"UserRolesCache"), ROLE_OPERATION("RoleOperationCache"), USER_ORGANIZATION("UserOrganizationCache");

	// OPERATION("OperationCache")

	/**
	 * The key of the cache.
	 */
	private String cacheKey;

	private CacheEntityType(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public String getCacheKey() {
		return cacheKey;
	}

}
