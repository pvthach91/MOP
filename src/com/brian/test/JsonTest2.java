package com.brian.test;

import net.sf.json.JSONObject;

public class JsonTest2 {
	public static void main(String[] args){
		String json = "{'type':'digital','methodid':11,'codes':'4&5&6&7&8&9|0&2&4&6&8|0&2&4&6&8','nums':150,'times':1,"
				+ "'money':300,'mode':1,'point':'0.025','desc':'[后三直选_复式] -,-,456789,02468,02468','curtimes':'1387289108813'}";
		JSONObject jsonObject = JSONObject.fromObject(json);
		PTest pt = (PTest)JSONObject.toBean( jsonObject, PTest.class );
		System.out.print(pt.getDesc());
	}
}
