package com.newsoft.foundation.util;

import java.util.UUID;

/**
 * To generate UUID string.
 * 
 * @author George Guo
 * 
 */
public class UUIDHelper {
	private static final String APP_PREFIX = "app";

	private static final String TASK_PREFIX = "task";

	private static final String LOG_PREFIX = "log";

	/**
	 * Generate unique application id.
	 * 
	 * @return
	 */
	public static String generateAppId() {
		return generateUniqueId(APP_PREFIX);
	}

	/**
	 * Generate unique task id.
	 * 
	 * @return
	 */

	public static String generateTaskId() {
		return generateUniqueId(TASK_PREFIX);
	}

	/**
	 * Generate unique log id.
	 * 
	 * @return
	 */

	public static String generateLogId() {
		return generateUniqueId(LOG_PREFIX);
	}

	public static String generateUniqueId(String prefix) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");

		return prefix == null ? uuid : (prefix + uuid).substring(0, 32);
	}
}
