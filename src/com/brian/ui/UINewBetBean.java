package com.brian.ui;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.GameRecordItem;
import com.brian.item.GameRecordItemSpecial;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class UINewBetBean {
	private List<GameRecordItem> reslist = new ArrayList<GameRecordItem>();
	/**
	 * 单位秒，前台*1000
	 */
	private Integer freshtime=600;
	private String sid;
	public UINewBetBean(){
		sid = CommonMethod.getParam("id");
	}
	/**
	 * 快骰一分   2016-04-08
	 * 六合彩      2016-04-29
	 * 六合彩1分      2016-05-23
	 */
	public UINewBetBean(String sid){
		this.sid = sid;
	}
	@SuppressWarnings("unchecked")
	/**
	 * 快骰一分   2016-04-08
	 * 六合彩      2016-04-29
	 */
	public GameRecordItemSpecial search_special(UserItem ui) throws Exception{
		GameRecordItemSpecial sris = null;
		List<GameRecordItem> reslist01 = new ArrayList<GameRecordItem>();
		Integer freshtime01=600;
		try{
			if(sid==null){
				return null;
			}
			List<Object> olist = new ArrayList<Object>();
			if("81".equals(sid)){
				olist.add("MBetSearch_lhc");
			}else{
				olist.add("MBetSearch");
				olist.add(1);
				olist.add(20);
				olist.add(sid);
			}
			if(ui==null){
				return null;
			}
			olist.add(ui.getLoginname());
			
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist01 = (List<GameRecordItem>)sblist.get(0);
				boolean b = false;
				int fs=-1;
				for(GameRecordItem bc : reslist01){
//					if(bc.getIssue().indexOf("-")>0){
//						bc.setIssue(bc.getIssue().split("-")[1]);
//					}else if(bc.getIssue().length()>3){
//						bc.setIssue(bc.getIssue().substring(bc.getIssue().length()-3));
//					}
					//分分六合彩格式化一下
					if("82".equals(sid)){
						bc.setIssue(bc.getIssue().split("-")[1]);
					}
					bc.setLotteryname(InitData.getDictKeyByValue(InitData.lotterylist, bc.getLotteryid()+""));
					bc.setMethodname(InitData.getMethodNameById(bc.getMethodid()));
					bc.setStatusname(InitData.getDictKeyByValue(InitData.statuslist, bc.getStatus()+""));
					if(bc.getStatus()<3){
						if(bc.getStatus()>fs){
							fs=bc.getStatus();
						}
						b=true;
					}
				}
				if(b){
					Integer n = Integer.parseInt(sid);
					if(n==11 || n==12){
						freshtime01=600;
					}else if((n>=15 && n<50)||n==80||n==82){
						if(fs==1){
							freshtime01 = 10;
						}else if(fs==2){
							freshtime01 = 5;
						}else{
							freshtime01 = 15;
						}
					}else if(n>=50&&n<80){
						freshtime01 = 5;
					}else if(n==81){
						freshtime01 = 60*60*24;
					}else{
						freshtime01 = 30;
					}
				}
				
			}
			sris =new GameRecordItemSpecial();
			sris.setFreshtime(freshtime01);
			sris.setReslist(reslist01);
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
		return sris;
	}
	@SuppressWarnings("unchecked")
	public void search(){
		reslist.clear();
		try{
			sid = CommonMethod.getParam("id");
			if(sid==null){
				return;
			}
			
			List<Object> olist = new ArrayList<Object>();
			olist.add("MBetSearch");
			olist.add(1);
			olist.add(8);
			//olist.add(CommonMethod.getTimeSpace(CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss", -12, "HH"));
			//olist.add(CommonMethod.GetCurDate("yyyy-MM-dd")+" 23:59:59");
			
			olist.add(sid);
			UserItem ui = CommonMethod.getCurUser();
			if(ui==null){
				return;
			}
			olist.add(ui.getLoginname());
			
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<GameRecordItem>)sblist.get(0);
				boolean b = false;
				int fs=-1;
				for(GameRecordItem bc : reslist){
					if(bc.getIssue().indexOf("-")>0){
						bc.setIssue(bc.getIssue().split("-")[1]);
					}else if(bc.getIssue().length()>3){
						bc.setIssue(bc.getIssue().substring(bc.getIssue().length()-3));
					}
					bc.setLotteryname(InitData.getDictKeyByValue(InitData.lotterylist, bc.getLotteryid()+""));
					bc.setMethodname(InitData.getMethodNameById(bc.getMethodid()));
					bc.setStatusname(InitData.getDictKeyByValue(InitData.statuslist, bc.getStatus()+""));
					if(bc.getStatus()<3){
						if(bc.getStatus()>fs){
							fs=bc.getStatus();
						}
						b=true;
					}
				}
				if(b){
					Integer n = Integer.parseInt(sid);
					if(n==11 || n==12){
						freshtime=600;
					}else if(n>=15 && n<50){
						if(fs==1){
							freshtime = 10;
						}else if(fs==2){
							freshtime = 5;
						}else{
							freshtime = 15;
						}
					}else if(n>=50){
						freshtime = 5;
					}else{
						freshtime = 30;
					}
				}
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public List<GameRecordItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<GameRecordItem> reslist) {
		this.reslist = reslist;
	}

	public Integer getFreshtime() {
		return freshtime;
	}

	public void setFreshtime(Integer freshtime) {
		this.freshtime = freshtime;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
}
