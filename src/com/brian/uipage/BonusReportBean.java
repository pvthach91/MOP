package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.BalanceChangeItem;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;


public class BonusReportBean extends LogXY{
	private String stime="";
	private String etime="";
	private String type="0";
	private String loginname="";
	private List<BalanceChangeItem> reslist = new ArrayList<BalanceChangeItem>();
	
	private int pagesize = 10;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	
	public BonusReportBean(){
		String s = CommonMethod.GetCurDate("yyyy-MM-dd");
		stime = s;
		etime = s;
	}
	
	public void bntSearch() {
		curpage = 1;
		search();
	}
	
	@SuppressWarnings("unchecked")
	public void search(){
		reslist.clear();
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		if (ui.getLoginname() == null) {
			this.setMsg("请登录后再操作");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("balanceRecordSearch");
		olist.add(curpage);
		olist.add(pagesize);
		olist.add(ui.getLoginname());
		olist.add(type);
		olist.add(stime);
		olist.add(etime);
		olist.add(loginname);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<BalanceChangeItem>)sblist.get(0);
				totalrecord = Integer.parseInt(sblist.get(1).toString());
				totalpage = Integer.parseInt(sblist.get(2).toString());
			}else{
				this.setMsg(rlist.get(1).toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
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
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<BalanceChangeItem> getReslist() {
		return reslist;
	}
	public void setReslist(List<BalanceChangeItem> reslist) {
		this.reslist = reslist;
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

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
}
