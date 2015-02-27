package com.newsoft.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * JSON转换类
 * 
 * @author fengmq
 * 
 */
public class JSONTool {
	/**
	 * 将List对象转换成JSON字符串
	 * 
	 * @param list
	 *            待转换成字符串的List对象
	 * @return JSON字符串
	 */
	public static String toJson(List<?> list) {
		String jsonString = JSONArray.fromObject(list).toString();
		return jsonString;
	}

	/**
	 * 讲对象转换成JSON字符串
	 * 
	 * @param object
	 *            待转换成字符串的对象
	 * @return JSON字符串
	 */
	public static String toJson(Object object) {
		String jsonString = JSONObject.fromObject(object).toString();
		return jsonString;
	}
	
	/**
	 * 选择需组装的属性,注意字段的大小写
	 * 
	 * @param list
	 * @param tArr
	 * @return
	 * @author zhouzq
	 */
	public static String toJsonSelectedAttr(List<?> list, String[] tArr) {
		final List<String> incluedPropertyList = Arrays.asList(tArr);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (incluedPropertyList.contains(name)) {
					return false;
				}
				return true;
			}
		});

		return JSONArray.fromObject(list, jsonConfig).toString();
	}

	/**
	 * 将字符串转换成JSON对象
	 * 
	 * @param json
	 *            待转换的字符串
	 * @return JSONObject
	 */
	public static JSONObject toJSONObject(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return jsonObject;
	}

	/**
	 * 将json字符串转换成bean对象
	 * 
	 * @param <T>
	 * @param json
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String json, Class<T> beanClass) {
		JSONObject jsonObject = toJSONObject(json);
		T bean = (T) JSONObject.toBean(jsonObject, beanClass);
		return bean;
	}

	/**
	 * 将字符串转换成JSON数组
	 * 
	 * @param json
	 *            待转换的字符串
	 * @return JSONArray
	 */
	public static JSONArray toJSONArray(String json) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return jsonArray;
	}

	/**
	 * 将JSON字符串转换成bean对象数组
	 * 
	 * @param json
	 *            json字符串
	 * @param classArr
	 *            bean数组
	 * @return
	 */
	public static Object[] toArray(String json, Class<?>[] classArr) {
		JSONArray jsonArray = toJSONArray(json);
		Object[] rtn = new Object[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			Object el = jsonArray.get(i);
			Object object = el;
			if (el instanceof JSONObject) {
				object = JSONObject.toBean((JSONObject) el, classArr[i]);
			}
			rtn[i] = object;
		}
		return rtn;
	}
	
	public static String updateJson(String json,String key,String value) {
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		String jsonKey ;
		Object jsonValue ;
		if (StringTools.isEmpty(json)) {
			jsonMap.put(key, value);
		} else {
			JSONObject jObject = JSONObject.fromObject(json);
			boolean flag = false;
			@SuppressWarnings("unchecked")
			Iterator<String> iterator = jObject.keys();
			while (iterator.hasNext()) {
				jsonKey = (String)iterator.next();
				jsonValue = jObject.get(jsonKey);
				if (key.equals(jsonKey)) {
					jsonValue = value;
					flag = true;
				}
				jsonMap.put(jsonKey, jsonValue);
			}
			if (!flag) {
				jsonMap.put(key, value);
			}
		}
		return JSONObject.fromObject(jsonMap).toString();
	}

}
