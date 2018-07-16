package com.brian.game.k3;

import java.util.ArrayList;
import java.util.List;

public class GameConfig {
	private int gameid;
	private String beturl;
	private List<K3Method> methods = new ArrayList<K3Method>();
	private List<K3history> history = new ArrayList<K3history>();
	private String balance="0.00";
	
	public int getGameid() {
		return gameid;
	}
	public void setGameid(int gameid) {
		this.gameid = gameid;
	}
	public String getBeturl() {
		return beturl;
	}
	public void setBeturl(String beturl) {
		this.beturl = beturl;
	}
	public List<K3Method> getMethods() {
		return methods;
	}
	public void setMethods(List<K3Method> methods) {
		this.methods = methods;
	}
	public List<K3history> getHistory() {
		return history;
	}
	public void setHistory(List<K3history> history) {
		this.history = history;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
