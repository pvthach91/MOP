package com.brian.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileReader;
import java.io.InputStreamReader;

import com.brian.item.IpGlobalItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;

/**
 * 读取IP文件
 * @author BRIAN
 *
 */
public class IpFileThread extends Thread{
	public void run() {
		//File file = new File(InitServlet.appPath+"xml"+File.separator+"Ip.txt");
		String fileName = InitServlet.appPath+"xml"+File.separator+"ipRejList.txt";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
			String tempString = null;
			//IpGlobalItem
			System.out.println("IP Loading Start.");
			while ((tempString = reader.readLine()) != null) {
				if(tempString.length()<5)
					continue;
				String[] arr= tempString.split(" ");
				int s=0;
				String sip="",eip="";
				for(int j=0;j<arr.length;j++){
					if(arr[j].length()>0){
						s++;
						if(s==1){
							sip = arr[j].trim();
						}else if(s==2){
							eip=arr[j];
						}
					}
				}
				//System.out.println("第"+i+"个："+sip+"  -  "+eip);
				IpGlobalItem igi = new IpGlobalItem();
				igi.setSip(IPtoLong(sip));
				igi.setEip(IPtoLong(eip));
				InitData.ipRjlist.add(igi);
			}
			System.out.println("Reject IP Loading Finish.");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public long IPtoLong(String str){
		if(str==null)
			return -1;
		String[] arr = str.split("\\.");
		if(arr.length==4){
			String sy ="";
			for(String s:arr){
				if(s.length()==1){
					s="00"+s;
				}else if(s.length()==2){
					s="0"+s;
				}
				sy+=s;
			}
			long res = Long.parseLong(sy);
			return res;
		}else{
			return -1;
		}
	}
}
