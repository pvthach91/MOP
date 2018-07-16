package com.brian.item;
import java.io.Serializable;
import java.util.Map;
public class UserReqsInfoItem implements Serializable{
	private static final long serialVersionUID = -9007487814732521721L;
	private Map<String,String> ip_times;
	private Long totalTimes ;
	private String time;
	public Map<String, String> getIp_times() {
		return ip_times;
	}
	public void setIp_times(Map<String, String> ip_times) {
		this.ip_times = ip_times;
	}
	public Long getTotalTimes() {
		return totalTimes;
	}
	public void setTotalTimes(Long totalTimes) {
		this.totalTimes = totalTimes;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
