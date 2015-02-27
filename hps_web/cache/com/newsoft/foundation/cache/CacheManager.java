package com.newsoft.foundation.cache;

/**
 * Define the cache manager.
 * 
 * @author George Guo
 * 
 */
public interface CacheManager {

	/**
	 * get the cache with specified name.
	 * 
	 * @param name
	 *            The cache name
	 * @return
	 * @throws CacheException
	 */
	Cache getCache(String name) throws CacheException;

	/**
	 * add a cache to the manager.
	 * 
	 * @param cache
	 * @throws CacheException
	 */
	void addCache(Cache cache) throws CacheException;

	/**
	 * get all the cache names.
	 * 
	 * @return
	 */
	String[] getCacheNames();

	/**
	 * to release the allocated resource or stop the referenced threads.
	 */
	void shutdown();
}
