package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.brian.item.AutoRegItem;
import com.brian.item.MathBrian;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;
/**
 * 自动注册
 * @author Administrator
 *
 */
public class AgentAutoRegBean extends LogXY{
	private List<AutoRegItem> reslist = new ArrayList<AutoRegItem>();
	private AutoRegItem regitem = new AutoRegItem();
	private Integer listnum = 0;
	
	private List<SelectItem> pointssc = new ArrayList<SelectItem>();
	private List<SelectItem> point115 = new ArrayList<SelectItem>();
	private List<SelectItem> pointdp = new ArrayList<SelectItem>();
	
	public AgentAutoRegBean(){
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		for(int i=0;i<ui.getPointssc()*1000;i++){
			if(i<=70){
				pointssc.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i, 0.1)+""));
			}
		}
		for(int i=0;i<ui.getPoint115()*1000;i++){
			if(i<=55){
				point115.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i, 0.1)+""));
			}
			
		}
		for(int i=0;i<ui.getPointdp()*1000;i++){
			if(i<=55){
				pointdp.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i, 0.1)+""));
			}
		}
		
		search();
	}
	@SuppressWarnings("unchecked")
	public void search(){
		this.setMsg(null);
		listnum = 0;
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentAutoRegSearch");
		olist.add(ui.getLoginname());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<AutoRegItem>)sblist.get(0);
				listnum = reslist.size();
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	public void edit(){
		this.setMsg(null);
		regitem = new AutoRegItem();
		String sign = CommonMethod.getParam("sign");
		if(sign==null || sign.length()<1){
			this.setMsg("参数出错");
			return;
		}
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		
		for(AutoRegItem ari: reslist){
			if(ari.getSign().equals(sign)){
				regitem = ari;
				break;
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void save(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		if(ui.getUsertype()>=2){
			this.setMsg("失败：测试账户不能进行该操作，谢谢。");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentAutoReg");
		olist.add(ui.getId());
		olist.add(ui.getLoginname());
		if(ui.getFatherflag()==null || ui.getFatherflag().length()<1){
			olist.add(ui.getId());
		}else{
			olist.add(ui.getFatherflag());
		}
		olist.add(1800);
		olist.add(regitem.getPointssc());
		olist.add(regitem.getPoint115());
		olist.add(regitem.getPointdp());
		String sign = ui.getId()+"";
		olist.add(sign);
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<AutoRegItem>)sblist.get(0);
				listnum = reslist.size();
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	@SuppressWarnings("unchecked")
	public void update(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentAutoRegUpdate");
		olist.add(ui.getLoginname());
		olist.add(regitem.getSign());
		olist.add(1800);
		olist.add(regitem.getPointssc());
		olist.add(regitem.getPoint115());
		olist.add(regitem.getPointdp());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<AutoRegItem>)sblist.get(0);
				listnum = reslist.size();
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	
	public List<AutoRegItem> getReslist() {
		return reslist;
	}
	public void setReslist(List<AutoRegItem> reslist) {
		this.reslist = reslist;
	}
	public AutoRegItem getRegitem() {
		return regitem;
	}
	public void setRegitem(AutoRegItem regitem) {
		this.regitem = regitem;
	}
	public Integer getListnum() {
		return listnum;
	}
	public void setListnum(Integer listnum) {
		this.listnum = listnum;
	}
	public List<SelectItem> getPointssc() {
		return pointssc;
	}
	public void setPointssc(List<SelectItem> pointssc) {
		this.pointssc = pointssc;
	}
	public List<SelectItem> getPoint115() {
		return point115;
	}
	public void setPoint115(List<SelectItem> point115) {
		this.point115 = point115;
	}
	public List<SelectItem> getPointdp() {
		return pointdp;
	}
	public void setPointdp(List<SelectItem> pointdp) {
		this.pointdp = pointdp;
	}
}
