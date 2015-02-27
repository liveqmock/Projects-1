package com.newsoft.foundation.cache;

import java.io.Serializable;

/**
 * Represent a cached object.
 * 
 * @author George Guo
 * 
 */
public class Element {

	/**
	 * the cache key.
	 */
	private Serializable key;

	/**
	 * the cached object.
	 */
	private Serializable value;

	/**
	 * the time to live in seconds.
	 */
	private int timeToLiveSeconds;

	/**
	 * The default timeToLiveSeconds is 0, which means never expire.
	 * 
	 * @param key
	 * @param value
	 */
	public Element(Serializable key, Serializable value) {
		this(key, value, 0);
	}

	public Element(Serializable key, Serializable value, int timeToLiveSeconds) {
		this.key = key;
		this.value = value;
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	/**
	 * @return the key
	 */
	public Serializable getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(Serializable key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public Serializable getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Serializable value) {
		this.value = value;
	}

	/**
	 * @return the timeToLiveSeconds
	 */
	public int getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	/**
	 * @param timeToLiveSeconds
	 *            the timeToLiveSeconds to set
	 */
	public void setTimeToLiveSeconds(int timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

}
