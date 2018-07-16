package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.brian.item.GameRecordItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.service.UIPage;
import com.brian.unit.CommonMethod;

public class GameRecordBean extends LogXY implements UIPage{
	private String starttime;
	private String endtime;
	private int status=-1;
	private int lotteryid=-1;
	private String betid;
	private boolean tracecheck = false;
	private List<SelectItem> statuslist = new ArrayList<SelectItem>();
	private List<SelectItem> lotterylist = new ArrayList<SelectItem>();
	
	private int pagesize = 9;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	private String ln = "";
	private String tamount="0";
	private String twin="0";
	
	private List<GameRecordItem> reslist = new ArrayList<GameRecordItem>();
	
	public GameRecordBean(){
		starttime = CommonMethod.GetCurDate("yyyy-MM-dd")+" 00:00:00";
		endtime = CommonMethod.GetCurDate("yyyy-MM-dd")+" 23:59:59";
		statuslist.add(new SelectItem("-1","全部"));
		statuslist.add(new SelectItem("-2","流水"));
		statuslist.addAll(InitData.statuslist);
		lotterylist.add(new SelectItem("-1","全部"));
		lotterylist.addAll(InitData.lotterylist);
	}
	public void btnSearch() {
		this.setMsg(null);
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
		List<Object> olist = new ArrayList<Object>();
		olist.add("SearchGameRecordNew");
		olist.add(curpage);
		olist.add(pagesize);
		olist.add(starttime);
		olist.add(endtime);
		
		olist.add(status);
		olist.add(lotteryid);
		olist.add(betid);
		if(tracecheck){
			olist.add(2);
		}else{
			olist.add(0);
		}
		
		if(ln==null || ln.length()<3){
			olist.add(ui.getLoginname());
		}else{
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
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<GameRecordItem>)sblist.get(0);
				for(GameRecordItem bc : reslist){
					bc.setLotteryname(InitData.getDictKeyByValue(InitData.lotterylist, bc.getLotteryid()+""));
					if(bc.getLotteryid()>=80){
						bc.setMethodname("-");
					}else{
						bc.setMethodname(InitData.getMethodNameById(bc.getMethodid()));
					}
					bc.setStatusname(InitData.getDictKeyByValue(InitData.statuslist, bc.getStatus()+""));
				}
				totalrecord = Integer.parseInt(sblist.get(1).toString());
				totalpage = Integer.parseInt(sblist.get(2).toString());
				tamount = sblist.get(3).toString();
				twin = sblist.get(4).toString();
			}else{
				this.setMsg(rlist.get(1).toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * 撤单
	 */
	public void cancelBet(){
		this.setMsg(null);
		String sid = CommonMethod.getParam("id");
		if(sid==null){
			this.setMsg("参数出错");
			return;
		}
		//if()
		List<Object> olist = new ArrayList<Object>();
		olist.add("CancelBet");
		olist.add(sid);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
			}else{
				this.setMsg("success");
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
		}
	}
	/**
	 * 撤单 六合彩 分分快3
	 */
	public static String cancelBet(String sid) throws Exception{
		if(sid==null){
			return "参数出错";
		}
		//if()
		List<Object> olist = new ArrayList<Object>();
		olist.add("CancelBet");
		olist.add(sid);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				return rlist.get(1).toString();
			}else{
				return "success";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	/**
	 * 一键撤单
	 */
	public void cancelBetAll(){
		this.setMsg(null);
		String tid = CommonMethod.getParam("id");
		String curloginname = CommonMethod.getParam("curloginname");
		String lotteryid = CommonMethod.getParam("lotteryid");
		String methodid = CommonMethod.getParam("methodid");
		
		if(tid==null||curloginname==null||lotteryid==null||methodid==null){
			this.setMsg("参数出错");
			return;
		}
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		//if()
		List<Object> olist = new ArrayList<Object>();
		olist.add("CancelBetALL");
		olist.add(ui.getLoginname());
		olist.add(tid);
		olist.add("6");
		olist.add(lotteryid);
		olist.add(methodid);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
//			List<Object> rlist = null;
//			String flag = "1";
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
			}else{
				this.setMsg("success");
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
		}
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(int lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getBetid() {
		return betid;
	}
	public void setBetid(String betid) {
		this.betid = betid;
	}
	public List<SelectItem> getStatuslist() {
		return statuslist;
	}
	public void setStatuslist(List<SelectItem> statuslist) {
		this.statuslist = statuslist;
	}
	public List<SelectItem> getLotterylist() {
		return lotterylist;
	}
	public void setLotterylist(List<SelectItem> lotterylist) {
		this.lotterylist = lotterylist;
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
	public List<GameRecordItem> getReslist() {
		return reslist;
	}
	public void setReslist(List<GameRecordItem> reslist) {
		this.reslist = reslist;
	}
	public boolean isTracecheck() {
		return tracecheck;
	}
	public void setTracecheck(boolean tracecheck) {
		this.tracecheck = tracecheck;
	}
	public String getLn() {
		return ln;
	}
	public void setLn(String ln) {
		this.ln = ln;
	}
	public String getTamount() {
		return tamount;
	}
	public void setTamount(String tamount) {
		this.tamount = tamount;
	}
	public String getTwin() {
		return twin;
	}
	public void setTwin(String twin) {
		this.twin = twin;
	}
	
}
