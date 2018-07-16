package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.brian.item.BalanceChangeItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

/**
 * 查看下线帐变报表
 * @author BRIAN
 */
public class AgentZbBean extends LogXY{
	private String starttime;
	private String endtime;
	private int type=-1;
	private String id="";
	private String wid="";
	private String ln="";
	private List<SelectItem> typelist = new ArrayList<SelectItem>();
	
	private int pagesize = 10;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	private String income="0"; 
	private String pay="0";
	
	private List<BalanceChangeItem> reslist = new ArrayList<BalanceChangeItem>();
	
	public void init(){
		starttime = CommonMethod.GetCurDate("yyyy-MM-dd")+" 00:00:00";
		endtime = CommonMethod.GetCurDate("yyyy-MM-dd")+" 23:59:59";
		reslist.clear();
		wid="";type=-1;income="0"; pay="0";
		pagesize = 10;curpage = 1;totalpage = 1;totalrecord = 0;
		AgentUserSearchBean pmub = (AgentUserSearchBean)CommonMethod.getSession("AgentUserSearchBean");
		String sid=CommonMethod.getParam("id");
		List<UserItem> ulist = pmub.getReslist();
		boolean flag = false;
		for(UserItem uis : ulist){
			if(uis.getLoginname().equals(sid)){
				ln = sid;
				flag = true;
				break;
			}
		}
		if(!flag){
			ln = "11";
			//TODO:Forbid, lock account
			this.setMsg("-1");
		}
	}
	public AgentZbBean(){
		typelist.add(new SelectItem("-1","全部"));
		typelist.addAll(InitData.balanceCList);
	}
	public void btnSearch() {
		curpage = 1;
		search();
	}
	@SuppressWarnings("unchecked")
	public void search(){
		this.setMsg(null);
		reslist.clear();
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		if(ln==null){
			this.setMsg("参数出错");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("SearchBalanceChangeNew");
		olist.add(curpage);
		olist.add(pagesize);
		olist.add(starttime);
		olist.add(endtime);
		
		olist.add(id);
		
		olist.add(ln);
		/**
		if(ln==null || ln.length()<3){
			olist.add(ui.getLoginname());
		}else{
			//确保是他的下线
			boolean b = false;
			PersonMsgUserBean pmub = (PersonMsgUserBean)CommonMethod.getSession("PersonMsgUserBean");
			for(UserItem uis:pmub.getReslist()){
				if(uis.getLoginname().equals(ln)){
					b=true;
					break;
				}
			}
			if(b){
				olist.add(ln);
			}else{
				this.setMsg("失败：请确认【"+ln+"】是否是您的下线会员？");
				return;
			}
		}
		*/
		olist.add(type);
		olist.add(wid);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<BalanceChangeItem>)sblist.get(0);
				for(BalanceChangeItem bc : reslist){
					bc.setOpttypename(InitData.getDictKeyByValue(typelist, bc.getOpttype()+""));
				}
				totalrecord = Integer.parseInt(sblist.get(1).toString());
				totalpage = Integer.parseInt(sblist.get(2).toString());
				income = sblist.get(3).toString();
				pay = sblist.get(4).toString();
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
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
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
	public List<SelectItem> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<SelectItem> typelist) {
		this.typelist = typelist;
	}
	public List<BalanceChangeItem> getReslist() {
		return reslist;
	}
	public void setReslist(List<BalanceChangeItem> reslist) {
		this.reslist = reslist;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getLn() {
		return ln;
	}
	public void setLn(String ln) {
		this.ln = ln;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
}
