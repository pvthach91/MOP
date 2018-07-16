package com.brian.ui;

import javax.servlet.http.HttpServletRequest;

import com.brian.service.InitData;

/**
 * Provide access to the pc Client URL to web page.
 */
public class PCClientUrlProvider {
	public PCClientUrlProvider() {
	}
	public PCClientUrlProvider(HttpServletRequest req) {
		// TODO Auto-generated constructor stub
		String pcURL = req.getParameter("pcURL");
		if(pcURL!=null){
			getPcClientUrl();
		}
		
	}

	public String getPcClientUrl() {
		return InitData.pcClientUrl;
	}
}
