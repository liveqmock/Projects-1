package com.newsoft.foundation.cache;

import java.io.Serializable;
import java.util.List;

/**
 * Represent a simple cache.
 * 
 * @author George Guo
 * 
 */
public interface Cache {

	/**
	 * put a serializable object which will never expire with the given key.
	 * 
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	void put(Serializable key, Serializable value) throws CacheException;

	/**
	 * put a serializable object which will expire with the given key and time
	 * to live seconds.
	 * 
	 * @param key
	 * @param value
	 * @throws CacheException
	 */
	void put(Serializable key, Serializable value, int timeToLiveSeconds) throws CacheException;

	/**
	 * put a element
	 * 
	 * @param element
	 * @throws CacheException
	 */
	void put(Element element) throws CacheException;

	/**
	 * get a element by the key
	 * 
	 * @param key
	 * @return
	 * @throws CacheException
	 */
	Element get(Serializable key) throws CacheException;

	/**
	 * get the value object with the specified key
	 * 
	 * @param key
	 * @return
	 * @throws CacheException
	 */
	Serializable getObjectValue(Serializable key) throws CacheException;

	/**
	 * remove a element
	 * 
	 * @param key
	 * @return
	 * @throws CacheException
	 */
	boolean removeElement(Serializable key) throws CacheException;

	/**
	 * get the element counts in the cache
	 * 
	 * @return
	 * @throws CacheException
	 */
	int getSize() throws CacheException;

	/**
	 * get the list of all cached elements keys.
	 * 
	 * @return
	 * @throws CacheException
	 */
	List<Serializable> getKeys() throws CacheException;

	/**
	 * get the name of the cache
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Remove all the elements.
	 */
	void removeAll();

}
