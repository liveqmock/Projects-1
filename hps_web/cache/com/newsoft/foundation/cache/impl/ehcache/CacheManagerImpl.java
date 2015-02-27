package com.newsoft.foundation.cache.impl.ehcache;

import com.newsoft.foundation.cache.Cache;
import com.newsoft.foundation.cache.CacheException;
import com.newsoft.foundation.cache.CacheManager;

/**
 * the ehcache manager
 * 
 * @author George Guo
 * 
 */
public class CacheManagerImpl implements CacheManager {

	private net.sf.ehcache.CacheManager cacheManager;

	public CacheManagerImpl(net.sf.ehcache.CacheManager cacheManager) {
		if (cacheManager == null) {
			throw new IllegalArgumentException("cacheManager instance must not be NULL.");
		}
		this.cacheManager = cacheManager;
	}

	@Override
	public Cache getCache(String name) throws CacheException {
		net.sf.ehcache.Cache cache = null;
		try {
			cache = cacheManager.getCache(name);
		} catch (Exception e) {
			throw new CacheException("Error when get cache by name: " + name, e);
		}
		if (cache == null) {
			return null;
		}
		return new CacheImpl(cache);
	}

	@Override
	public void addCache(Cache cache) throws CacheException {
		try {
			cacheManager.addCache(cache.getName());
		} catch (Exception e) {
			throw new CacheException("Error when add cache", e);
		}
	}

	@Override
	public String[] getCacheNames() {
		try {
			return cacheManager.getCacheNames();
		} catch (Exception e) {
			throw new CacheException("Error when get all cache names", e);
		}
	}

	@Override
	public void shutdown() {
		try {
			cacheManager.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
