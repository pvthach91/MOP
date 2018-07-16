package com.brian.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.brian.unit.CommonMethod;

public class BasicTest {
	public static void main(String[] args)throws Exception{
		//System.out.println(CommonMethod.Encode("任选玩法"));
		
		System.out.print(CommonMethod.GetCurDate("H"));
		/**
		System.out.println(CommonMethod.Decode("\u4e07\u4f4d"));
		//1386855376287
		Date d = new Date();
		d.setTime(1386855376287l);
		String s = CommonMethod.strToDate(d, "yyyy-MM-dd HH:mm:ss");
		System.out.println(s);
		
		String wn = "123";
		wn = wn.substring(wn.length()-2);
		System.out.print(wn);
		*/
		/**
		int x=1;
		List<String> arrs = new ArrayList<String>();
		for(int i=0;i<=9;i++){
			for(int j=0;j<=9;j++){
				for(int k=0;k<=9;k++){
					if(check(i,j,k)){
						//if(check2(i,j,k,arrs)){
							arrs.add(i+""+j+""+k);
							System.out.print(i+""+j+""+k+"  ");
							x++;
						//}
						
					}
				}
			}
		}
		*/
	}
	public static boolean check(int i,int j,int k){
		if(i==j || j==k || i==k){
			return false;
		}else{
			return true;
		}
	}
	public static boolean check2(int i,int j,int k,List<String> ars){
		for(String str:ars){
			int x = 0;
			if(str.indexOf(i+"")>=0){
				x++;
			}
			if(str.indexOf(j+"")>=0){
				x++;
			}
			if(str.indexOf(k+"")>=0){
				x++;
			}
			if(x>=3){
				return false;
			}
		}
		return true;
	}
}
