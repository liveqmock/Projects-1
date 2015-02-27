package com.newsoft.sysmanager.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.newsoft.foundation.cache.Cache;
import com.newsoft.foundation.cache.CacheManager;

/**
 * The common cache support helper.
 * 
 * @author guohb
 * 
 */
@Component
public class CacheSupport {
	@Autowired
	private CacheManager cacheManager;

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public Cache getCacheByName(String cacheName) {
		return cacheManager.getCache(cacheName);
	}
}
