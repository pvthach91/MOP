package com.brian.service;

import com.brian.ui.LiveChatUrlProvider;
import com.brian.unit.CommonMethod;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LiveChatService extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setDateHeader("Expires", 0);
		LiveChatUrlProvider liveChat = new LiveChatUrlProvider(req);
		System.out.println(liveChat);
		String[] sel= new String[]{"log"};
		JSONObject LiveChatReportJSon=CommonMethod.beanToJSON(liveChat,sel);
		CommonMethod.printData(resp, LiveChatReportJSon.toString());
	}

}
