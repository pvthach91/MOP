package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class PersonMsgUserBean extends LogXY{
	private int pagesize = 10;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	
	private String loginname="";
	
	private Integer usertype=0;
	
	private List<UserItem> reslist = new ArrayList<UserItem>();
	
	public PersonMsgUserBean(){
		btnSearch();
	}
	public void btnSearch() {
		curpage = 1;
		search();
	}
	
	@SuppressWarnings("unchecked")
	public void search(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("失败：Session过期，请重新登录后再操作");
			return;
		}
		reslist.clear();
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPesrsonMsgUserList");
		olist.add(curpage);
		olist.add(pagesize);
		olist.add(loginname);
		olist.add(ui.getFatherflag());
		if(usertype==0){
			olist.add(ui.getId());
		}else{
			olist.add(-1);
		}
		
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<UserItem>)sblist.get(0);
				totalrecord = Integer.parseInt(sblist.get(1).toString());
				totalpage = Integer.parseInt(sblist.get(2).toString());
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
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

	public List<UserItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<UserItem> reslist) {
		this.reslist = reslist;
	}
	public Integer getUsertype() {
		return usertype;
	}
	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
}
