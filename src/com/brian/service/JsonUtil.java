package com.brian.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JsonUtil {
	
	/*
	 * 数组转json
	 */
	public static JSONArray arrayToJSON(String[] sarray ) {
		// TODO Auto-generated method stub
		return JSONArray.fromObject(sarray);
	}
	
	/*
	 * list转json
	 */
	public static JSONArray listToJSON(ArrayList alist) {
		// TODO Auto-generated method stub
		return JSONArray.fromObject(alist);
	}
	
	/*
	 * map转json
	 */
	public static JSONArray mapToJSON(HashMap hmap) {
		// TODO Auto-generated method stub
		return JSONArray.fromObject(hmap);
	}
	
	
	/*
	 * json字符串转json
	 */
	public static JSONObject jsonStrToJSON(String str) {
		// TODO Auto-generated method stub
		return JSONObject.fromObject(str);
	}
	
	
	/*
	 * 普通json字符串转bean
	 */
	public static <T> T jsonToObject(String json,Class<T> clazz) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSONObject.fromObject(json);
		return (T)JSONObject.toBean(jsonObject,clazz);
	}
	
	/*
	 * json转bean含复杂对象（list）
	 */
//	public static void JsonToBeanHaveList() {
//		String json = "{\"error\":0,\"status\":\"success\",\"date\":\"2015-01-15\","
//				+ "\"results\":[{\"currentCity\":\"南京\",\"pm25\":\"83\","
//				+ "\"weather_data\":[{\"date\":\"周日\",\"dayPictureUrl\":\"eee\",\"nightPictureUrl\":\"aaa\",\"weather\":\"晴\",\"wind\":\"西风微风\",\"temperature\":\"10 ~ -1℃\"}]}]}";
//
//		JSONObject jsonObj = JSONObject.fromObject(json);  
//		Map<String, Class> classMap = new HashMap<String, Class>();  
//		 classMap.put("results", WeatherBean_Baidu_City.class);  
//         classMap.put("weather_data", WeatherBean_Baidu_City_Weatherdata.class);  
//         // 将JSON转换成WeatherBean_Baidu    
//         WeatherBean_Baidu weather = (WeatherBean_Baidu) JSONObject.toBean(jsonObj,    
//                WeatherBean_Baidu.class, classMap);   
//         System.out.println(weather.getResults().get(0).getCurrentCity()); 
//         System.out.println("=================测试结束；"); 
//	}
	
	/*
	 * json转bean含复杂对象（map）
	 */
	public static void JsonToBeanHaveMap() {
		//把Map看成一个对象
    
		
	}

	
	
//	public static void main(String[] args) {
//		JsonToBeanHaveList();
//	}
	
}
