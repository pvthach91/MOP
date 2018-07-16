package com.brian.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brian.ui.PCClientUrlProvider;
import com.brian.unit.CommonMethod;

import net.sf.json.JSONObject;

public class PCclientService extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setDateHeader("Expires", 0);
		PCClientUrlProvider pcClient = new PCClientUrlProvider(req);
		System.out.println(pcClient);
		String[] sel= new String[]{"log"};
		JSONObject PCClientReportJSon=CommonMethod.beanToJSON(pcClient,sel);
		CommonMethod.printData(resp, PCClientReportJSon.toString());
		//super.doGet(req, resp);
	}

}
