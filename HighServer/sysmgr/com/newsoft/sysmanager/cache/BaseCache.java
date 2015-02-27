package com.newsoft.sysmanager.cache;

import org.springframework.beans.factory.annotation.Autowired;

import com.newsoft.foundation.cache.Cache;

/**
 * The abstract cache implementation.
 * 
 * @author guohb
 * 
 */
public abstract class BaseCache {

	@Autowired
	private CacheSupport cacheSupport;

	protected Cache getCache(String cacheName) {
		return cacheSupport.getCacheByName(cacheName);
	}

	public void setCacheSupport(CacheSupport cacheSupport) {
		this.cacheSupport = cacheSupport;
	}
}
