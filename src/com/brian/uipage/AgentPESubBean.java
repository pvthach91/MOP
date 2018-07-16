package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.AgentPeItem;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class AgentPESubBean extends LogXY {
	//查看下级配额信息
	private List<AgentPeItem> vteamlist = new ArrayList<AgentPeItem>();
	private List<AgentPeItem> vpersonlist = new ArrayList<AgentPeItem>();
	private String loginname = "";
	@SuppressWarnings("unchecked")
	public AgentPESubBean(){
		this.setMsg(null);
		String lname = CommonMethod.getParam("lname");
		if(lname==null){
			return;
		}
		loginname = lname;
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null || ui.getPointssc()<=0.07){
			return;
		}
		vteamlist.clear();
		vpersonlist.clear();
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("UIAgentPESub");
		olist.add(ui.getFatherflag());
		olist.add(lname);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				List<AgentPeItem> t1 = (List<AgentPeItem>)sblist.get(0);//use
				List<AgentPeItem> t2 = (List<AgentPeItem>)sblist.get(1);//no use
				List<AgentPeItem> p1 = (List<AgentPeItem>)sblist.get(2);//use
				List<AgentPeItem> p2 = (List<AgentPeItem>)sblist.get(3);//no use
				
				vteamlist.addAll(t1);
				for(AgentPeItem ap:t2){
					boolean f = false;
					for(AgentPeItem tm:vteamlist){
						if(ap.getLevels().equals(tm.getLevels())){
							f=true;
							tm.setNum2(ap.getNum());
							break;
						}
					}
					if(!f){
						ap.setNum2(ap.getNum());
						ap.setNum(0);
						vteamlist.add(ap);
					}
				}
				
				vpersonlist.addAll(p1);
				for(AgentPeItem ap:p2){
					boolean f = false;
					for(AgentPeItem pm:vpersonlist){
						if(ap.getLevels().equals(pm.getLevels())){
							f=true;
							pm.setNum2(ap.getNum());
							break;
						}
					}
					if(!f){
						ap.setNum2(ap.getNum());
						ap.setNum(0);
						vpersonlist.add(ap);
					}
				}
			}else{
				this.setMsg(rlist.get(1).toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public List<AgentPeItem> getVteamlist() {
		return vteamlist;
	}

	public void setVteamlist(List<AgentPeItem> vteamlist) {
		this.vteamlist = vteamlist;
	}

	public List<AgentPeItem> getVpersonlist() {
		return vpersonlist;
	}

	public void setVpersonlist(List<AgentPeItem> vpersonlist) {
		this.vpersonlist = vpersonlist;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
}
