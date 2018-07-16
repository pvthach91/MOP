package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.brian.item.GameRecordItem;
import com.brian.item.MathBrian;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class ReportWLBean extends LogXY{
	private String stime;
	private int lotteryid=-1;
	private List<GameRecordItem> reslist = new ArrayList<GameRecordItem>();
	private List<SelectItem> lotterylist = new ArrayList<SelectItem>();
	
	public ReportWLBean(){
		stime = CommonMethod.GetCurDate("yyyy-MM-dd");
		lotterylist.addAll(InitData.lotterylist);
	}
	
	/**
	 * 查询
	 */
	@SuppressWarnings("unchecked")
	public void search(){
		this.setMsg(null);
		reslist.clear();
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		if(stime==null || stime.length()<10){
			this.setMsg("日期选择不正确！");
			return;
		}
		if(lotteryid==-1){
			this.setMsg("请选择游戏种类！");
			return;
		}
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("SearchReportWL");
		olist.add(ui.getLoginname());
		olist.add(stime+" 00:00:00");
		olist.add(stime+" 23:59:59");
		olist.add(lotteryid);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<GameRecordItem>)sblist.get(0);
				/**
				for(GameRecordItem bc : reslist){
					bc.setMethodname(InitData.getMethodNameById(bc.getMethodid()));
					bc.setStatusname(InitData.getDictKeyByValue(InitData.statuslist, bc.getStatus()+""));
				}
				*/
				for(GameRecordItem rl : reslist){
					rl.setLotteryid(lotteryid);
					rl.setPoint(MathBrian.sub(rl.getBonus(), rl.getAmount()));
					rl.setLotteryname(InitData.getDictKeyByValue(InitData.lotterylist, rl.getLotteryid()+""));
				}
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
			dp.setSql("call PG_Lottery_Brian.P_UI_Bet_Report(?,?,?,?,?,?)");
			dp.setOutParam(1, OracleTypes.CURSOR);
			dp.setString(2, ui.getLoginname());
			dp.setInt(3, InitServlet.AGENTID);
			dp.setString(4, stime+" 00:00:00");
			dp.setString(5, stime+" 23:59:59");
			dp.setInt(6, lotteryid);
			dp.exec();
			reslist = dp.getResult(GameRecordItem.class, 1);
			for(GameRecordItem rl : reslist){
				rl.setLotteryid(lotteryid);
				rl.setPoint(MathBrian.sub(rl.getBonus(), rl.getAmount()));
			}
		}catch(Exception ex){
			this.setMsg("查询出错："+ex.getMessage());
			return;
		}finally{
			if(dp!=null)
				dp.close();
		}
		*/
	}
	
	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public List<GameRecordItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<GameRecordItem> reslist) {
		this.reslist = reslist;
	}

	public List<SelectItem> getLotterylist() {
		return lotterylist;
	}

	public void setLotterylist(List<SelectItem> lotterylist) {
		this.lotterylist = lotterylist;
	}

	public int getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(int lotteryid) {
		this.lotteryid = lotteryid;
	}
}
