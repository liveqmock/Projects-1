package com.newsoft.foundation.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A simple concurrent hash map with fixed capacity.
 * 
 * @author George Guo
 * 
 */
public class FixedConcurrentMap<K, V> implements Map<K, V> {

	private Map<K, V> backMap;

	private final int maxCapacity;

	public FixedConcurrentMap(final int capacity) {
		maxCapacity = capacity;
		this.backMap = Collections.synchronizedMap(new LinkedHashMap<K, V>() {
			private static final long serialVersionUID = -1895942088232627708L;

			@Override
			protected boolean removeEldestEntry(Entry<K, V> eldest) {
				return super.size() > maxCapacity;
			}

		});
	}

	@Override
	public int size() {
		return backMap.size();
	}

	@Override
	public boolean isEmpty() {
		return backMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return backMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return backMap.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return backMap.get(key);
	}

	@Override
	public V put(K key, V value) {
		return backMap.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return backMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		backMap.putAll(m);

	}

	@Override
	public void clear() {
		backMap.clear();
	}

	@Override
	public Set<K> keySet() {
		return backMap.keySet();
	}

	@Override
	public Collection<V> values() {
		return backMap.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return backMap.entrySet();
	}

}
