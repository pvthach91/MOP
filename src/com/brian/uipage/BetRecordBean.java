package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

//import oracle.jdbc.OracleTypes;
//import com.brian.db.DbProc;
import com.brian.item.GameRecordItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class BetRecordBean{
	private String msg = null;
	private GameRecordItem betinfo = new GameRecordItem();
	
	private String curloginname="-1";
	@SuppressWarnings("unchecked")
	public BetRecordBean(){
		String sid = CommonMethod.getParam("id");
		if(sid==null){
			return;
		}
		UserItem ui = CommonMethod.getCurUser();
		if(ui!=null){
			curloginname = ui.getLoginname();
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("BetRecordDetail");
		olist.add(sid);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}else{
				List<Object> slist = (List<Object>)rlist.get(1);
				betinfo = (GameRecordItem)slist.get(0);
				betinfo.setLotteryname(InitData.getDictKeyByValue(InitData.lotterylist, betinfo.getLotteryid()+""));
				betinfo.setStatusname(InitData.getDictKeyByValue(InitData.statuslist, betinfo.getStatus()+""));
				betinfo.setCodes(betinfo.getCodes().replaceAll("@", "\r\n"));
				//六合彩和分分六合彩开奖号码格式化
				if(betinfo.getLotteryid()==81||betinfo.getLotteryid()==82){
					if(!StringUtils.isEmpty(betinfo.getWinnumber())){
						String[] arr = betinfo.getWinnumber().split("\\|");
						String winnum = arr[5].split("&")[1]+"|"+arr[0]+","+arr[1]+","+arr[2]+","+arr[3]+","+arr[4]+","+arr[5].split("&")[0];
						betinfo.setWinnumber(winnum);
					}
				}
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public BetRecordBean(String sid,String loginname) throws Exception{
		if(sid==null){
			return;
		}
		if(StringUtils.isEmpty(loginname)){
			return;
		}
		curloginname = loginname;
		List<Object> olist = new ArrayList<Object>();
		olist.add("BetRecordDetail");
		olist.add(sid);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}else{
				List<Object> slist = (List<Object>)rlist.get(1);
				betinfo = (GameRecordItem)slist.get(0);
				betinfo.setLotteryname(InitData.getDictKeyByValue(InitData.lotterylist, betinfo.getLotteryid()+""));
				betinfo.setStatusname(InitData.getDictKeyByValue(InitData.statuslist, betinfo.getStatus()+""));
				String codes = betinfo.getCodes();
				StringBuilder codes01 = new StringBuilder("");
				if(!StringUtils.isEmpty(codes)){
					String[] codesArr = codes.split("@");
					for(int i=0;i<codesArr.length;i++){
						codes01.append("<p>"+codesArr[i]+"</p>");
					}
				}
				betinfo.setCodes(codes01.toString());
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
			throw ex;
		}
	}
	public GameRecordItem getBetinfo() {
		return betinfo;
	}
	public void setBetinfo(GameRecordItem betinfo) {
		this.betinfo = betinfo;
	}
	public String getCurloginname() {
		return curloginname;
	}
	public void setCurloginname(String curloginname) {
		this.curloginname = curloginname;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
