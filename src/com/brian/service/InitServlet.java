package com.brian.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brian.run.ScheduleMain;

public class InitServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public static String appPath;
	public static String selfip = null; 
	//public static int AGENTID =-1;
	public static String WSURL = "";
	public static String CNAME = "";
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		doPost(request, response);
	}
	
	public void init() throws ServletException {
		try {
			appPath = this.getServletContext().getRealPath("/");
			//String agid = this.getServletConfig().getInitParameter("AGENTID");
			WSURL = this.getServletConfig().getInitParameter("WS");
			//AGENTID = Integer.parseInt(agid);
			CNAME = this.getServletConfig().getInitParameter("CNAME");
			//System.out.println("AgentId："+AGENTID);
			InetAddress localhost = InetAddress.getLocalHost();
			selfip=localhost.getHostAddress(); 
			System.out.println("前台UI IP:"+selfip);
			////////////////////////////////////////////log4j
			String file = this.getServletConfig().getInitParameter("log4j").replace("\\", File.separator);
			String logfile = this.getServletConfig().getInitParameter("logFile");
			String logfilepath = appPath + file;
			Properties props = new Properties();
			try{
				FileInputStream log4jStream = new FileInputStream(logfilepath);
				props.load(log4jStream);
				log4jStream.close();
				String log = appPath + logfile;
				props.setProperty("log4j.appender.R.File", log);
				org.apache.log4j.PropertyConfigurator.configure(props);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			/////////////////////////////////////////END log4j
			//loadConfig();
			InitData.init();
			ScheduleMain.run();
			System.out.println("Init Success!");
			new IpFileThread().start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
