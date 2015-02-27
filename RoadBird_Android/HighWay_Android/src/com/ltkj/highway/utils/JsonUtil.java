package com.ltkj.highway.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author wanggp
 * 
 *         2014年9月23日 下午3:50:04
 * 
 */
public class JsonUtil {

	public static String getJSONbyKey(String JSONString, String key) {
		String tempString = null;
		try {
			JSONObject jsonObj = new JSONObject(JSONString);
			tempString = jsonObj.optString(key);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tempString;
	}

	public static String getJSONStringValue(JSONObject json, String key)
			throws JSONException {
		if (json != null && !json.isNull(key)) {
			return json.getString(key);
		} else {
			return "";
		}
	}

	public static int getJSONIntegerValue(JSONObject json, String key)
			throws JSONException {
		if (json != null && !json.isNull(key)) {
			return json.getInt(key);
		} else {
			return 0;
		}
	}

	public static double getJSONDoubleValue(JSONObject json, String key)
			throws JSONException {
		if (json != null && !json.isNull(key)) {
			return json.getDouble(key);
		} else {
			return 0.0f;
		}
	}

	public static boolean getJSONBooleanValue(JSONObject json, String key)
			throws JSONException {
		if (json != null && !json.isNull(key)) {
			return json.getBoolean(key);
		} else {
			return false;
		}
	}
	
	/** 
     *   将json 数组转换为Map 对象 
     * @param jsonString 
     * @return 
     */  
    public static Map<String, Object> getMap(String jsonString)  
    {  
      JSONObject jsonObject; 
      if(jsonString==null || jsonString.length()==0) return null;
      try  
      {  
       jsonObject = new JSONObject(jsonString);   @SuppressWarnings("unchecked")  
       Iterator<String> keyIter = jsonObject.keys();  
       String key;  
       Object value;  
       Map<String, Object> valueMap = new HashMap<String, Object>();  
       while (keyIter.hasNext())  
       {  
        key = (String) keyIter.next();  
        value = jsonObject.get(key);  
        valueMap.put(key, value);  
       }  
       return valueMap;  
      }  
      catch (JSONException e)  
      {  
       e.printStackTrace();  
      }  
      return null;  
    }  
  
    /** 
     * 把json 转换为 ArrayList<Map<String,Object>> 形式 
     * @return 
     */  
    public static List<Map<String, Object>> getList(String jsonString)  
    {  
      List<Map<String, Object>> list = null;  
      try  
      {  
       JSONArray jsonArray = new JSONArray(jsonString);  
       JSONObject jsonObject;  
        list = new ArrayList<Map<String, Object>>();  
       for (int i = 0; i < jsonArray.length(); i++)  
       {  
        jsonObject = jsonArray.getJSONObject(i);  
        list.add(getMap(jsonObject.toString()));  
       }  
      }  
      catch (Exception e)  
      {  
       e.printStackTrace();  
      }  
      return list;  
    }  
    
    /** 
     * 把json 转换为 ArrayList<String> 形式 
     * @return 
     */  
    public static List<String> getListString(String string)  
    {  
      List<String> list = null;  
      try  
      {  
       String[] strs = string.split(",");   
        list = new ArrayList<String>();  
       for (int i = 0; i < strs.length; i++)  
       {   
        list.add(strs[i]);  
       }  
      }  
      catch (Exception e)  
      {  
       e.printStackTrace();  
      }  
      return list;  
    }  
}
