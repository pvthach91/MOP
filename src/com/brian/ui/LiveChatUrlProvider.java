package com.brian.ui;

import com.brian.service.InitData;

import javax.servlet.http.HttpServletRequest;

/**
 * Provide access to the live chat URL to web page.
 */
public class LiveChatUrlProvider {
	public LiveChatUrlProvider() {
	}
	
	public LiveChatUrlProvider(HttpServletRequest req) {
		getLiveChatUrl();
	}

	public String getLiveChatUrl() {
		return InitData.liveChatUrl;
	}
}
