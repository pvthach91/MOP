package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.brian.item.AgentPeItem;
import com.brian.item.MathBrian;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class AgentPEBean extends LogXY{
	private List<AgentPeItem> teamlist = new ArrayList<AgentPeItem>();
	private List<AgentPeItem> personlist = new ArrayList<AgentPeItem>();
	
	private List<AgentPeItem> reslist = new ArrayList<AgentPeItem>();
	private String lname = "";
	private List<SelectItem> fdlist = new ArrayList<SelectItem>();
	private Double fdvalue =null;
	private Integer areavalue=0;
	
	private int pagesize = 10;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	
	@SuppressWarnings("unchecked")
	public  AgentPEBean(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null || ui.getPointssc()<=0.07){
			//this.setMsg("请登录后重试");
			return;
		}
		fdlist.clear();
		teamlist.clear();
		personlist.clear();
		Double d = ui.getPointssc()*1000;
		for(int i=d.intValue();i>=71;i--){
			int n = (int)MathBrian.muls(i,0.1,20)+1800;
			fdlist.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i,0.1)+"【"+n+"】"));
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UIAgentPELoad");
		olist.add(ui.getId());
		olist.add(ui.getFatherflag());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				List<AgentPeItem> t1 = (List<AgentPeItem>)sblist.get(0);//use
				List<AgentPeItem> t2 = (List<AgentPeItem>)sblist.get(1);//no use
				List<AgentPeItem> p1 = (List<AgentPeItem>)sblist.get(2);//use
				List<AgentPeItem> p2 = (List<AgentPeItem>)sblist.get(3);//no use
				
				teamlist.addAll(t1);
				for(AgentPeItem ap:t2){
					boolean f = false;
					for(AgentPeItem tm:teamlist){
						if(ap.getLevels().equals(tm.getLevels())){
							f=true;
							tm.setNum2(ap.getNum());
							break;
						}
					}
					if(!f){
						ap.setNum2(ap.getNum());
						ap.setNum(0);
						teamlist.add(ap);
					}
				}
				
				personlist.addAll(p1);
				for(AgentPeItem ap:p2){
					boolean f = false;
					for(AgentPeItem pm:personlist){
						if(ap.getLevels().equals(pm.getLevels())){
							f=true;
							pm.setNum2(ap.getNum());
							break;
						}
					}
					if(!f){
						ap.setNum2(ap.getNum());
						ap.setNum(0);
						personlist.add(ap);
					}
				}
			}else{
				this.setMsg(rlist.get(1).toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void bntsearch(){
		curpage = 1;
		search();
	}
	@SuppressWarnings("unchecked")
	public void search(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null || ui.getPointssc()<=0.07){
			return;
		}
		reslist.clear();
		if(lname==null){
			lname="";
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UIAgentPESearch");
		olist.add(curpage);
		olist.add(pagesize);
		
		olist.add(ui.getFatherflag());
		olist.add(ui.getId());
		olist.add(lname);
		olist.add(fdvalue);
		olist.add(areavalue);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<AgentPeItem>)sblist.get(0);
				totalrecord = Integer.parseInt(sblist.get(1).toString());
				totalpage = Integer.parseInt(sblist.get(2).toString());
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
	public List<AgentPeItem> getTeamlist() {
		return teamlist;
	}

	public void setTeamlist(List<AgentPeItem> teamlist) {
		this.teamlist = teamlist;
	}

	public List<AgentPeItem> getPersonlist() {
		return personlist;
	}

	public void setPersonlist(List<AgentPeItem> personlist) {
		this.personlist = personlist;
	}

	public List<AgentPeItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<AgentPeItem> reslist) {
		this.reslist = reslist;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public List<SelectItem> getFdlist() {
		return fdlist;
	}

	public void setFdlist(List<SelectItem> fdlist) {
		this.fdlist = fdlist;
	}

	public Integer getAreavalue() {
		return areavalue;
	}

	public void setAreavalue(Integer areavalue) {
		this.areavalue = areavalue;
	}

	public Double getFdvalue() {
		return fdvalue;
	}

	public void setFdvalue(Double fdvalue) {
		this.fdvalue = fdvalue;
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
}
