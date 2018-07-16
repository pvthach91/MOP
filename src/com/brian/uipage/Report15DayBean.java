package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.ReportUserItem;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class Report15DayBean {
	private List<ReportUserItem> personlist = new ArrayList<ReportUserItem>();
	private List<ReportUserItem> teamlist = new ArrayList<ReportUserItem>();
	
	private String loginname = "";
	@SuppressWarnings("unchecked")
	public Report15DayBean(){
		personlist.clear();
		teamlist.clear();
		String lname = CommonMethod.getParam("lname");
		if(lname==null){
			return;
		}
		loginname = lname;
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null || ui.getPointssc()<=0.07){
			return;
		}
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("UIReport15Day");
		olist.add(ui.getFatherflag());
		olist.add(lname);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				personlist = (List<ReportUserItem>)sblist.get(0);
				teamlist = (List<ReportUserItem>)sblist.get(1);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public List<ReportUserItem> getPersonlist() {
		return personlist;
	}

	public void setPersonlist(List<ReportUserItem> personlist) {
		this.personlist = personlist;
	}

	public List<ReportUserItem> getTeamlist() {
		return teamlist;
	}

	public void setTeamlist(List<ReportUserItem> teamlist) {
		this.teamlist = teamlist;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
}
