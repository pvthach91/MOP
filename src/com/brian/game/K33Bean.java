package com.brian.game;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.brian.game.k3.K3history;
import com.brian.item.GameRecordItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class K33Bean {
	private int lotteryid=62;
	private String methodstr = "";
	private String history = "";
	private String chips="['1000.00', '2000.00', '5000.00', '10000.00','20000.00']";
	@SuppressWarnings("unchecked")
	public K33Bean(){
		methodstr = InitData.k3method3;
		//投注历史
		List<Object> olist = new ArrayList<Object>();
		olist.add("MBetSearch");
		olist.add(1);
		olist.add(30);
		olist.add(lotteryid);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			return;
		}
		olist.add(ui.getLoginname());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				List<GameRecordItem> reslist = (List<GameRecordItem>)sblist.get(0);
				List<K3history> stmp = new ArrayList<K3history>();
				for(GameRecordItem gr:reslist){
					K3history k = new K3history();
					k.setDetail(gr.getWinnumber());
					stmp.add(k);
				}
				JSONArray jsonObject2 = JSONArray.fromObject(stmp.toArray());
				history = jsonObject2.toString();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	public int getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(int lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getMethodstr() {
		return methodstr;
	}
	public void setMethodstr(String methodstr) {
		this.methodstr = methodstr;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public String getChips() {
		return chips;
	}
	public void setChips(String chips) {
		this.chips = chips;
	}
}
