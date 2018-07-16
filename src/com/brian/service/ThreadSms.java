package com.brian.service;

public class ThreadSms extends Thread{
	public String phone="";
	public String content = "";
	public ThreadSms(String p, String c){
		phone = p;
		content = c;
	}
	public void run(){
		try{
			SmsSend.sendSmsNew(phone, content);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
