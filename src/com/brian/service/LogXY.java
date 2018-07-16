package com.brian.service;

import org.apache.log4j.Logger;
/**
 * Log Txt
 * @author Administrator
 *
 */
public class LogXY {
	private String msg = null;
	private Logger log = Logger.getLogger(LogXY.class); 
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
}
