package com.newsoft.foundation.cache.impl.ehcache;

import com.newsoft.foundation.cache.CacheException;
import com.newsoft.foundation.cache.CacheManager;
import com.newsoft.foundation.cache.CacheManagerFactory;

/**
 * To create the cache manager. This factory should be used as singleton for
 * better reuse.
 * 
 * @author George Guo
 * 
 */
public class CacheManagerFactoryImpl implements CacheManagerFactory {

	private net.sf.ehcache.CacheManager cacheManager;

	@Override
	public CacheManager createManager() throws CacheException {
		return new CacheManagerImpl(getEhCacheManager());
	}

	private net.sf.ehcache.CacheManager getEhCacheManager() throws CacheException {
		if (this.cacheManager == null) {
			// create a default manager according to the ehcache.xml
			try {
				cacheManager = net.sf.ehcache.CacheManager.create();
			} catch (net.sf.ehcache.CacheException e) {
				throw new CacheException("Error when create Ehcache manager", e);
			}
		}
		return this.cacheManager;
	}

}
