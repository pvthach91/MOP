package com.brian.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

public class Testa {
	
	public static void main01(String[] arg) throws Exception{
		
		String jsonString = "[{\"author\":\"7\",\"id\":358,\"title\":\"More of us\",\"pictures\":[{\"description\":\"\",\"imgPath\":\"/cms/u/cms/www/201203/05150720ii67.jpg\"}],\"path\":\"ip\"}]";

		JSONArray ja = JSONArray.fromObject(jsonString);

		Map<String, Class<Pictures>> classMap = new HashMap<String, Class<Pictures>>(); 
		classMap.put("pictures", Pictures.class); 
		List<Content> list = JSONArray.toList(ja, Content.class, classMap);
		List<Pictures> picList = new ArrayList<Pictures>();
		if(list.size() > 0){
			for(Content cont : list){
				List<Pictures> pic = cont.getPictures();
				picList.addAll(pic);
			}
		}
		
		System.out.println(picList.size());
		
		if( picList.size()> 0 ){
			for(Pictures pic : picList){
				System.out.println(pic.getDescription());
				System.out.println(pic.getImgPath());
			}
		}
	}
	public static void main02(String[] arg) throws Exception{
		String str = "a_-+?=a`~!@#$%^&*()+=|{}':;',[].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’\"\"。，、？";
		System.out.println(filter(str));
	}
	public static void main03(String[] arg) throws Exception{
		String str = "a_-+?=a`~!@#$%^&*()+=|{}';:',[].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’\"\"。，、？";
		String regEx="[`~!@#$%^&*()+=|{}';',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’\"\"。，、？]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(str);
		String result = m.replaceAll("").trim();
		System.out.println(result);
	}
	public static String filter(String s){
		String regEx="[`~!@#$%^&*()+={}';',\\[\\].<>/?~！@#￥%……&*（）——+{}【】‘；：”“’。，、？]";
		Pattern   p   =   Pattern.compile(regEx);
		Matcher   m   =   p.matcher(s);     
		return m.replaceAll("").trim();
	}
	public static void main(String[] arg) throws Exception{
		String str = "|1";
		String arr[] = str.split("\\|");
		System.out.println(arr.length);
		String str01 = "1|2000|10000|";
		String arr01[] = str01.split("\\|");
		String last = arr01[arr01.length-1];
		System.out.println(str01.substring(0,str01.length()-last.length()-1)+"qqq|");
	}
}
