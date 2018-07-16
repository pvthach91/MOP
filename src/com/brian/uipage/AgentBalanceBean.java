package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;
/**
 * 团队余额
 * @author Administrator
 *
 */
public class AgentBalanceBean extends LogXY{
	private String tbalance="0.0";
	public AgentBalanceBean(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentTeamBalance");
		if(ui.getFatherflag()==null || ui.getFatherflag().length()<1){
			olist.add(ui.getId());
		}else{
			olist.add(ui.getFatherflag());
		}
		
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				tbalance = rlist.get(1).toString();
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
			return;
		}
	}
	public String getTbalance() {
		return tbalance;
	}
	public void setTbalance(String tbalance) {
		this.tbalance = tbalance;
	}
}
