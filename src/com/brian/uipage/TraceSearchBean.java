package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.TraceRecordItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class TraceSearchBean extends LogXY{
	private String starttime;
	private String endtime;
	private String traceid;
	
	private int pagesize = 9;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	
	private List<TraceRecordItem> reslist = new ArrayList<TraceRecordItem>();
	
	public TraceSearchBean(){
		starttime = CommonMethod.GetCurDate("yyyy-MM-dd")+" 00:00:00";
		endtime = CommonMethod.GetCurDate("yyyy-MM-dd")+" 23:59:59";
	}
	public void btnSearch() {
		curpage = 1;
		search();
	}
	@SuppressWarnings("unchecked")
	public void search(){
		reslist.clear();
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("SearchTraceRecord");
		olist.add(curpage);
		olist.add(pagesize);
		olist.add(starttime);
		olist.add(endtime);
		
		olist.add(traceid);
		olist.add(ui.getLoginname());
		//olist.add(InitServlet.AGENTID);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<TraceRecordItem>)sblist.get(0);
				for(TraceRecordItem bc : reslist){
					bc.setLotteryname(InitData.getDictKeyByValue(InitData.lotterylist, bc.getLotteryid()+""));
					bc.setMethodname(InitData.getMethodNameById(bc.getMethodid()));
					bc.setStatusname(InitData.getDictKeyByValue(InitData.statuslist, bc.getStatus()+""));
				}
				totalrecord = Integer.parseInt(sblist.get(1).toString());
				totalpage = Integer.parseInt(sblist.get(2).toString());
			}else{
				this.setMsg(rlist.get(1).toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		/**
		DbProc dp = null;
		try{
			dp = new DbProc();
			dp.setSql("call PG_Lottery_Brian.P_UI_TraceRecord_Search(?,?,?,?,?,?,?,?,?)");
			dp.setOutParam(1, OracleTypes.CURSOR);
			dp.setOutParam(2, OracleTypes.INTEGER);
			dp.setInt(3, curpage);
			dp.setInt(4, pagesize);
			dp.setString(5, starttime);
			dp.setString(6, endtime);
			dp.setString(7, traceid);
			dp.setString(8, ui.getLoginname());
			dp.setInt(9, InitServlet.AGENTID);
			dp.execute();
			reslist = dp.getResult(TraceRecordItem.class, 1);
			totalrecord = Integer.parseInt(dp.getObject(2).toString());
			if(totalrecord%pagesize==0){
				totalpage = totalrecord/pagesize;
			}else{
				totalpage = totalrecord/pagesize + 1;
			}
		}catch(Exception ex){
			//ex.printStackTrace();
			this.setMsg("查询出错："+ex.getMessage());
		}finally{
			dp.Close();
		}
		*/
	}
	public static void main(String[] args){
		/**
		int totalrecord = 40;
		int pagesize = 20;
		int totalpage = 0 ;
		if(totalrecord%pagesize==0){
			totalpage = totalrecord/pagesize;
		}else{
			totalpage = totalrecord/pagesize + 1;
		}
		System.out.print(totalpage);
		*/
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
	public String getTraceid() {
		return traceid;
	}
	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}
	public List<TraceRecordItem> getReslist() {
		return reslist;
	}
	public void setReslist(List<TraceRecordItem> reslist) {
		this.reslist = reslist;
	}
}
