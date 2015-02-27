package com.newsoft.foundation.cache;

/**
 * Define the cache manager factory.
 * 
 * @author George Guo
 * 
 */
public interface CacheManagerFactory {

	/**
	 * to create a cache manager.
	 * 
	 * @return
	 */
	CacheManager createManager() throws CacheException;
}
