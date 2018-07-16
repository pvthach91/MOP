package com.brian.unit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

public class MarkSixUtils {
	private static List<String[]> list = new ArrayList<String[]> ();
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static int bonuspoint = 42;
	static{
		String[] arr01 = {"一码中特","49",bonuspoint+""};
		String[] arr02 = {"特码大","2.04","1.75"};
		String[] arr03 = {"特码小","2.04","1.75"};
		String[] arr04 = {"特码单","2.04","1.75"};
		String[] arr05 = {"特码双","2.04","1.75"};
		String[] arr06 = {"特码尾数大","1.96","1.68"};
		String[] arr07 = {"特码尾数小","2.04","1.75"};
		String[] arr08 = {"特码尾数单","1.96","1.68"};
		String[] arr09 = {"特码尾数双","2.04","1.75"};
		String[] arr10 = {"特码色波红","2.88","2.47"};
		String[] arr11 = {"特码色波蓝","3.06","2.63"};
		String[] arr12 = {"特码色波绿","3.06","2.63"};
		
		String [] point01 = new String[12];
		String [] point02 = new String[12];
		//生肖每年对应的号码都不一样，赔率也会变
		int year = new Date().getYear();
		int year_base = 2016-1900;
		int be = year-year_base;
		int add = be%12;
		point01[add] = "9.80";
		point02[add] = "8.40";
		for(int i=0;i<12;i++){
			if(i==add){
				continue;
			}
			point01[i] = "12.25";
			point02[i] = "10.50";
		}
		
		String[] arr13 = {"特码生肖猴",point01[0],point02[0]};
		String[] arr14 = {"特码生肖鸡",point01[1],point02[1]};
		String[] arr15 = {"特码生肖狗",point01[2],point02[2]};
		String[] arr16 = {"特码生肖猪",point01[3],point02[3]};
		String[] arr17 = {"特码生肖鼠",point01[4],point02[4]};
		String[] arr18 = {"特码生肖牛",point01[5],point02[5]};
		String[] arr19 = {"特码生肖虎",point01[6],point02[6]};
		String[] arr20 = {"特码生肖兔",point01[7],point02[7]};
		String[] arr21 = {"特码生肖龙",point01[8],point02[8]};
		String[] arr22 = {"特码生肖蛇",point01[9],point02[9]};
		String[] arr23 = {"特码生肖马",point01[10],point02[10]};
		String[] arr24 = {"特码生肖羊",point01[11],point02[11]};
		
		String[] arr25 = {"特码尾数0","12.25","10.50"};
		String[] arr26 = {"特码尾数1","9.80","8.40"};
		String[] arr27 = {"特码尾数2","9.80","8.40"};
		String[] arr28 = {"特码尾数3","9.80","8.40"};
		String[] arr29 = {"特码尾数4","9.80","8.40"};
		String[] arr30 = {"特码尾数5","9.80","8.40"};
		String[] arr31 = {"特码尾数6","9.80","8.40"};
		String[] arr32 = {"特码尾数7","9.80","8.40"};
		String[] arr33 = {"特码尾数8","9.80","8.40"};
		String[] arr34 = {"特码尾数9","9.80","8.40"};
		String[] arr35 = {"一码不定位","8.17","7.00"};
		String[] arr36 = {"正一码大","2.04","1.75"};
		String[] arr37 = {"正一码小","2.04","1.75"};
		String[] arr38 = {"正一码单","2.04","1.75"};
		String[] arr39 = {"正一码双","2.04","1.75"};
		
		list.add(arr01);
		list.add(arr02);
		list.add(arr03);
		list.add(arr04);
		list.add(arr05);
		list.add(arr06);
		list.add(arr07);
		list.add(arr08);
		list.add(arr09);
		list.add(arr10);
		list.add(arr11);
		list.add(arr12);
		list.add(arr13);
		list.add(arr14);
		list.add(arr15);
		list.add(arr16);
		list.add(arr17);
		list.add(arr18);
		list.add(arr19);
		list.add(arr20);
		list.add(arr21);
		list.add(arr22);
		list.add(arr23);
		list.add(arr24);
		list.add(arr25);
		list.add(arr26);
		list.add(arr27);
		list.add(arr28);
		list.add(arr29);
		list.add(arr30);
		list.add(arr31);
		list.add(arr32);
		list.add(arr33);
		list.add(arr34);
		list.add(arr35);
		list.add(arr36);
		list.add(arr37);
		list.add(arr38);
		list.add(arr39);
	}
	public static String[] getBunousGroup(double pointssc) throws Exception{
		String[] result = new String[list.size()];
		for(int i=0;i<result.length;i++){
			String[] arr = list.get(i);
			double f = ((Double.parseDouble(arr[1])*pointssc)+Double.parseDouble(arr[2]));
			BigDecimal   b   =   new   BigDecimal(f);
			double   f1   =   b.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue();
			result[i] = arr[0]+":"+f1;
		}
		return result;
	}
	public static void main(String[] args){
		System.out.println(JSONArray.fromObject(list).toString());
	}

}
