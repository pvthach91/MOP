package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;
import com.brian.item.ReportUserItem;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

/**
 * 每日下级报表
 * @author BRIAN
 *
 */
public class ReportWinLoss2Bean extends LogXY{
	private String loginname="";
	private List<ReportUserItem> reslist =new ArrayList<ReportUserItem>();
	private String order = "0";
	private String dates = "";
	
	//代理用户名
	private String agentname = "";
	
	private int pagesize = 10;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	
	public ReportWinLoss2Bean(){
		dates = CommonMethod.GetCurDate("yyyy-MM-dd");
	}
	public void bntSearch(){
		curpage = 1;
		search();
	}
	@SuppressWarnings("unchecked")
	public void search(){
		this.setMsg(null);
		reslist.clear();
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("失败：请登录后再操作。");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UITeamReportDayNEW");
		olist.add(curpage);
		olist.add(pagesize);
		if(loginname==null)loginname="";
		olist.add(loginname.trim());
		olist.add(dates);
		olist.add(order);
		olist.add(ui.getFatherflag());
		if(agentname==null)agentname="";
		olist.add(agentname.trim());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<ReportUserItem>)sblist.get(0);
				totalrecord = Integer.parseInt(sblist.get(1).toString());
				totalpage = Integer.parseInt(sblist.get(2).toString());
			}else{
				if(rlist.get(1)!=null)
					this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	public void firstPage() {
		this.setMsg(null);
		if (curpage <= 1) {
			this.setMsg("当前已经是第一页！");
			return;
		}
		curpage = 1;
		search();
	}

	public void proPage() {
		this.setMsg(null);
		if (curpage <= 1) {
			this.setMsg("当前已经是第一页！");
			return;
		}
		curpage--;
		search();
	}

	public void nextPage() {
		this.setMsg(null);
		if (curpage >= totalpage) {
			this.setMsg("当前已经是最后一页！");
			return;
		}
		curpage++;
		search();
	}

	public void lastPage() {
		this.setMsg(null);
		if (curpage >= totalpage) {
			this.setMsg("当前已经是最后一页！");
			return;
		}
		curpage = totalpage;
		search();
	}

	public void inputPage() {
		this.setMsg(null);
		try {
			String num = CommonMethod.getParam("num");
			int n = Integer.parseInt(num);
			if (n < 1 || n > totalpage) {
				this.setMsg("输入的页码不正确，请重试！");
				return;
			}
			curpage = n;
			search();
		} catch (Exception ex) {
			this.setMsg(ex.getMessage());
		}
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}

	public List<ReportUserItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<ReportUserItem> reslist) {
		this.reslist = reslist;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getCurpage() {
		return curpage;
	}
	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	public int getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}
	public String getAgentname() {
		return agentname;
	}
	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
}
