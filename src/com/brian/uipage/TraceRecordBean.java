package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.GameRecordItem;
import com.brian.item.TraceRecordItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class TraceRecordBean extends LogXY{
	private TraceRecordItem trace = new TraceRecordItem();
	private List<GameRecordItem> betlist = new ArrayList<GameRecordItem>();
	private String curloginname = "-1";
	@SuppressWarnings("unchecked")
	public TraceRecordBean(){
		String sid = CommonMethod.getParam("id");
		if(sid==null){
			return;
		}
		UserItem ui = CommonMethod.getCurUser();
		if(ui!=null){
			curloginname = ui.getLoginname();
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("TraceDetail");
		olist.add(sid);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}else{
				List<Object> slist = (List<Object>)rlist.get(1);
				trace = (TraceRecordItem)slist.get(0);
				trace.setLotteryname(InitData.getDictKeyByValue(InitData.lotterylist, trace.getLotteryid()+""));
				trace.setStatusname(InitData.getDictKeyByValue(InitData.statuslist, trace.getStatus()+""));
				trace.setMethodname(InitData.getMethodNameById(trace.getMethodid()));
				betlist = (List<GameRecordItem>)slist.get(1);
				for(GameRecordItem gri:betlist){
					gri.setStatusname(InitData.getDictKeyByValue(InitData.statuslist, gri.getStatus()+""));
				}
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
		}
	}
	public TraceRecordItem getTrace() {
		return trace;
	}
	public void setTrace(TraceRecordItem trace) {
		this.trace = trace;
	}
	public List<GameRecordItem> getBetlist() {
		return betlist;
	}
	public void setBetlist(List<GameRecordItem> betlist) {
		this.betlist = betlist;
	}
	public String getCurloginname() {
		return curloginname;
	}
	public void setCurloginname(String curloginname) {
		this.curloginname = curloginname;
	}
}
