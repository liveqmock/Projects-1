package com.newsoft.foundation.cache.impl.ehcache;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;

import com.newsoft.foundation.cache.Cache;
import com.newsoft.foundation.cache.Element;

/**
 * The ehcache implementation.
 * 
 * @author George Guo
 * 
 */
public class CacheImpl implements Cache {

	/**
	 * the ehcache instance
	 */
	private Ehcache ehCache;

	/**
	 * The default non expire cache time which is long enough (300 days here).
	 */
	private static final int LONG_ENOUGH_CAHE_TIME = 24 * 3600 * 300;

	public CacheImpl(Ehcache ehCache) {
		if (ehCache == null) {
			throw new IllegalArgumentException("ehCache instance must not be NULL.");
		}
		this.ehCache = ehCache;
	}

	@Override
	public void put(Serializable key, Serializable value) throws CacheException {
		Element element = new Element(key, value);
		this.put(element);

	}

	@Override
	public void put(Serializable key, Serializable value, int timeToLiveSeconds)
			throws com.newsoft.foundation.cache.CacheException {
		Element element = new Element(key, value);
		element.setTimeToLiveSeconds(timeToLiveSeconds);
		this.put(element);
	}

	@Override
	public void put(Element element) throws CacheException {
		long version = System.currentTimeMillis();
		net.sf.ehcache.Element e = new net.sf.ehcache.Element(element.getKey(), element.getValue(), version);

		// Fix the EHcache bug when TimeToLiveSeconds==0 should never expire
		e.setTimeToLive(element.getTimeToLiveSeconds() == 0 ? LONG_ENOUGH_CAHE_TIME : element.getTimeToLiveSeconds());

		try {
			ehCache.put(e);
		} catch (Exception ex) {
			throw new CacheException("Error when put element to cache", ex);
		}
	}

	@Override
	public Element get(Serializable key) throws CacheException {
		net.sf.ehcache.Element e = null;

		try {
			e = ehCache.get(key);
		} catch (Exception ex) {
			throw new CacheException("Error when get element from cache", ex);
		}

		if (e == null) {
			return null;
		}
		return new Element(key, e.getValue(), e.getTimeToLive());
	}

	@Override
	public Serializable getObjectValue(Serializable key) throws CacheException {
		net.sf.ehcache.Element e = null;
		try {
			e = ehCache.get(key);
		} catch (Exception ex) {
			throw new CacheException("Error when get element from cache", ex);
		}

		if (e == null) {
			return null;
		}
		return e.getValue();
	}

	@Override
	public boolean removeElement(Serializable key) throws CacheException {
		try {
			return ehCache.remove(key);
		} catch (Exception e) {
			throw new CacheException("Error when remove element from cache", e);
		}
	}

	@Override
	public void removeAll() {
		try {
			ehCache.removeAll();
		} catch (Exception e) {
			throw new CacheException("Error when remove all the element from cache", e);
		}
	}

	@Override
	public int getSize() throws CacheException {
		try {
			return ehCache.getSize();
		} catch (Exception e) {
			throw new CacheException("Error when get size of the cache", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Serializable> getKeys() throws CacheException {
		try {
			return ehCache.getKeys();
		} catch (Exception e) {
			throw new CacheException("Error when get keys of the cache", e);
		}
	}

	@Override
	public String getName() {
		return ehCache.getName();
	}

}
