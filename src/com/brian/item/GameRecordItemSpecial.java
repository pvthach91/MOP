package com.brian.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * //快骰一分   2016-04-08
 * @author 32RSC-V3800
 *
 */
public class GameRecordItemSpecial implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<GameRecordItem> reslist = new ArrayList<GameRecordItem>();
	private Integer freshtime=600;
	private String userbalance="0.0";
	
	public String getUserbalance() {
		return userbalance;
	}
	public void setUserbalance(String userbalance) {
		this.userbalance = userbalance;
	}
	public List<GameRecordItem> getReslist() {
		return reslist;
	}
	public void setReslist(List<GameRecordItem> reslist) {
		this.reslist = reslist;
	}
	public Integer getFreshtime() {
		return freshtime;
	}
	public void setFreshtime(Integer freshtime) {
		this.freshtime = freshtime;
	}
	
}
