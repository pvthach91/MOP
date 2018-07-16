package com.brian.run;
import java.util.Map;
import java.util.Timer;

import javax.servlet.ServletContext;

import com.brian.service.FilterXY;

public class LogOperTask extends java.util.TimerTask{
	public static Timer timer = null;
	public static ServletContext servletContext = null;
	@Override
	public void run() {
		boolean flag = FilterXY.logFlag;
		if(flag){
			if(servletContext!=null){
				Map<String,String> ip_times = (Map<String,String>)servletContext.getAttribute("USER_IP_TIMES");
				if(ip_times!=null){
					for (String key : ip_times.keySet()) {
						ip_times.put(key, ip_times.get(key)+"0|");
					}
				}
			}
			
		}
	}

}
