/**
 * 
 */
package com.ltkj.highway.common;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author chenzy
 * @version 创建时间：2014-10-9 上午10:47:21
 * 
 */

public class CommonFun {
	
	/**
	 * 返回字符串中汉字的位置
	 * */
	public static int ChineseWordLoc(String str)
	 {
		if (str == null || str.length() <= 0) {
			return 0;
			}
			int location = 0;
			char c;
			for (int i = 0; i <str.length(); i++) {
				c = str.charAt(i);
				if ((c == ' ') || (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
						|| (c >= 'A' && c <= 'Z')) {
					//字母, 数字
					location++;
				} else {
					return location;
				}
			}
			return 0;
	 }
	
	/**
	 * 查询两个站之间是否有交通事故/道路施工
	 * 
	 * searchAry:搜索的List
	 * startStationName:起始站
	 * endStationName:终点站
	 * */
	public static Map<String,Object> searchAccidentFixIconAryByName(List<Map<String,Object>>searchAry,String startStationName, String endStationName, boolean isSingleRoad){
		if(isSingleRoad){ //不跨高速路，匹配严格
			for(int i=0; i<searchAry.size(); i++){
				String dicStartName = searchAry.get(i).get("startStation").toString().substring(0, 2);
				String dicEndName = searchAry.get(i).get("endStation").toString().substring(0, 2);
				if(dicStartName.equals(startStationName.substring(0, 2)) && dicEndName.equals(endStationName.substring(0, 2))){
					return searchAry.get(i);
				}
			}
		}else{ //跨高速路，匹配松弛
			for(int i=0; i<searchAry.size(); i++){
				String dicStartName = searchAry.get(i).get("startStation").toString().substring(0, 2);
				String dicEndName = searchAry.get(i).get("endStation").toString().substring(0, 2);
				if(dicStartName.equals(startStationName.substring(0, 2)) || dicEndName.equals(endStationName.substring(0, 2))){
					return searchAry.get(i);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 查询两个站之间是否有服务区
	 * 
	 * searchDic:搜索的dic
	 * startStationID:起始站
	 * endStationID:终点站
	 * */
	public static Map<String,Object> searchServiceIconAryByID(Map<String,List<Map<String,Object>>>searchDic,String roadID,String startStationID, String endStationID){
		 
		List<Map<String,Object>> list = searchDic.get(roadID);
		if(list == null)
			return null;
		for(int i=0; i<list.size(); i++){
			try {
				if(list.get(i).get("StartID")==null || list.get(i).get("EndID")==null)
					continue;
				JSONArray array = new JSONArray(list.get(i).get("StartID").toString());
				String dicStartID = array.get(1).toString();
				array = new JSONArray(list.get(i).get("EndID").toString());
				String dicEndID = array.get(1).toString();
				if(dicStartID.equals(startStationID) || dicEndID.equals(endStationID)){
					return list.get(i);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 查询两个站之间的速度
	 * */
	public static String searchSpeed(List<String> speedList, String startID, String endID){
		String speed = "100";
		for(int i=0; i<speedList.size(); i++){
			String[] ary = speedList.get(i).split("\\|");
			if(startID.equals(ary[0]) && endID.equals(ary[1]))
				speed = ary[2];
		}
		return speed;
	}
	
	public static boolean SpeedFast(String speed){
		return Integer.parseInt(speed)>=60?true:false;
	}
	
	public static boolean SpeedSlow(String speed){
		return Integer.parseInt(speed)<30?true:false;
	}
	
	public static boolean isNull(Object object) {
		if(object==null || object.equals("null"))
			return true;
		else 
			return false;
		
	}
}
