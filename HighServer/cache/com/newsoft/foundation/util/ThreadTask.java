package com.newsoft.foundation.util;

/**
 * Define a task operation to be execute in thread pool.
 * 
 * @author George Guo
 * 
 */
public interface ThreadTask {
	/**
	 * Do the task really.
	 */
	void doTask();
}
