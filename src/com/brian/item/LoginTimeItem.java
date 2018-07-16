package com.brian.item;

public class LoginTimeItem {
	private long time;
	private String ip;
	private String loginname;
	
	public LoginTimeItem(){
	}
	public LoginTimeItem(String ips){
		time = new java.util.Date().getTime();
		ip = ips;
	}
	public LoginTimeItem(String ips,String ln){
		time = new java.util.Date().getTime();
		ip = ips;
		loginname = ln;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
}
