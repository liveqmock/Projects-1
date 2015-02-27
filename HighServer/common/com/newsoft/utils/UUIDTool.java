package com.newsoft.utils;

import java.util.UUID;

/**
 * 
 * UUID工具类
 * 
 * @author fengmq
 * 
 */
public class UUIDTool {

	public UUIDTool() {

	}

	/**
	 * 
	 * 返回32位的UUID信息
	 * 
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
