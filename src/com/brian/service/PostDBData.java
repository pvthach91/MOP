package com.brian.service;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.brian.item.DictItem;
import com.brian.item.ModelItem;
import com.brian.item.SysMsgItem;

public class PostDBData {
	public static void main(String[] args){
		try{
			List<Object> obj= DBPOST("http://112.121.163.154:8000/UI.cfm","init",3);
			@SuppressWarnings("unchecked")
			List<DictItem> list1 = (List<DictItem>)obj.get(0);
			@SuppressWarnings("unchecked")
			List<ModelItem> list2 = (List<ModelItem>)obj.get(1);
			@SuppressWarnings("unchecked")
			List<SysMsgItem> list3 = (List<SysMsgItem>)obj.get(2);
			//@SuppressWarnings("unchecked")
			//List<DictItem> list = (List<DictItem>)obj;
			System.out.println(obj.size());
			System.out.println(list1.size());
			System.out.println(list2.size());
			System.out.println(list3.size());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static List<Object> DBPOST(String webpath,List<Object> arr,int num)throws Exception{
		URL url = null;
		try {
			url = new URL(webpath);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setConnectTimeout(40000);
		connection.setReadTimeout(40000);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-type", "application/x-java-serialized-object");
		OutputStream outStrm = connection.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(outStrm);
		for(int i=0;i<arr.size();i++){
			oos.writeObject(arr.get(i));
		}
		oos.flush();
		oos.close();
		//==Send End
		//==Receive start
		List<Object> objt = new ArrayList<Object>(); 
        InputStream in = connection.getInputStream();
        ObjectInputStream objInStream = new ObjectInputStream(in);
		for(int i=0;i<num;i++){
			Object obj = objInStream.readObject();
			objt.add(obj);
		}
		in.close();
		objInStream.close();
		return objt;
	}
	public static List<Object> DBPOST(String webpath,String type,int num)throws Exception{
		List<Object> list = new ArrayList<Object>();
		list.add(type);
		return DBPOST(webpath,list,num);
	}
}
