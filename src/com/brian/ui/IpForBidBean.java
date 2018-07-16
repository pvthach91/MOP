package com.brian.ui;

import com.brian.service.InitData;
import com.brian.unit.CommonMethod;

public class IpForBidBean {
	private String ipfb;

	public String getIpfb() {
		String ip = CommonMethod.getIpAddress();
		boolean r = InitData.IsForbidIp(ip);
		if(r){
			CommonMethod.PageJump("/forbidden.htm");
			CommonMethod.sessionRemove("LoginBean");
		}
		return ipfb;
	}

	public void setIpfb(String ipfb) {
		this.ipfb = ipfb;
	}
}
