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

public class AgentRegBean extends LogXY{
	private Integer type=0;
	private String loginname;
	private String pwd;
	private String pwdre;
	private String nickname;
	private Double pointssc;
	private Integer fhnumber=0;
	private Double point115;
	private Double pointdp;
	
	private UserItem curuser = new UserItem();
	private List<SelectItem> selssc = new ArrayList<SelectItem>();
	private List<SelectItem> selfh = new ArrayList<SelectItem>();
	
	private List<SelectItem> sel115 = new ArrayList<SelectItem>();
	private List<SelectItem> seldp = new ArrayList<SelectItem>();
	
	private List<AgentPeItem> pelist =new ArrayList<AgentPeItem>();
	
	public AgentRegBean(){
		curuser = CommonMethod.getCurUser(); 
		Double d = curuser.getPointssc()*1000;
//		for(int i=d.intValue();i>=0;i--){
//			int n = (int)MathBrian.muls(i,0.1,20)+1800;
//			selssc.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i,0.1)+"("+n+")"));
//		}
		for(int i=0;i<=curuser.getPointssc()*1000; i++){
			int n = (int)MathBrian.muls(i,0.1,20)+1800;
			selssc.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i,0.1)+"("+n+")"));
		}
		d=curuser.getPoint115()*1000;
		for(int i=0;i<=curuser.getPoint115()*1000;i++){
			sel115.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i, 0.1)+""));
		}
		d=curuser.getPointdp()*1000;
		for(int i=0;i<=curuser.getPointdp()*1000;i++){
			seldp.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i, 0.1)+""));
		}
		
		int fh = curuser.getParam8();
		if(curuser.getFatherid()<=1){
			for(int i=0;i<=fh-7;i++){
				selfh.add(new SelectItem(i,i+"%"));
			}
		}else{
			for(int i=0;i<=fh;i++){
				selfh.add(new SelectItem(i,i+"%"));
			}
		}
		//加载配额
		loadPe();
	}
	@SuppressWarnings("unchecked")
	public void loadPe(){
		if(curuser==null){
			this.setMsg("请重新登录后再进行操作。");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPEList");
		olist.add(curuser.getLoginname());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> oslist= (List<Object>)rlist.get(1);
				pelist = (List<AgentPeItem>)oslist.get(0);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * 保存
	 */
	public void save(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请重新登录后再进行操作。");
			return;
		}
		if(ui.getUsertype()>=2){
			this.setMsg("失败：测试账户不能进行该操作，谢谢。");
			return;
		}
		if(loginname==null || loginname.length()<1){
			this.setMsg("失败：登录名不能为空。");
			return;
		}
		if(!pwd.equals(pwdre)){
			this.setMsg("失败：两次输入的密码不相同，请重新输入。");
			return;
		}
		if(pwd==null || pwd.length()<1){
			this.setMsg("失败：密码不能为空。");
			return;
		}
		if(pwd.length()<6){
			this.setMsg("失败：密码长度不够，至少6个字符。");
			return;
		}
		
		if(loginname.length()<5 || loginname.length()>30){
			this.setMsg("失败：登录名长度太不满足条件，最少需要5个字符，最多30个字符。");
			return;
		}
		if(!CommonMethod.checkString(loginname)){
			this.setMsg("失败：登录名不能包含特殊符号，只能是数字和大小写字符。");
			return;
		}
		if(nickname==null || nickname.length()<1){
			this.setMsg("失败：昵称不能为空。");
			return;
		}
		
		if(!CommonMethod.checkFloat(pointssc+"",3)){
			this.setMsg("失败：时时彩返点输入不正确。");
			return;
		}
		if(!CommonMethod.checkFloat(point115+"",3)){
			this.setMsg("失败：11选5返点输入不正确。");
			return;
		}
		if(!CommonMethod.checkFloat(pointdp+"",3)){
			this.setMsg("失败：低频返点输入不正确。");
			return;
		}
		if(fhnumber>30){
			this.setMsg("失败：分红设置不正确。不能大于30。");
			return;
		}
		if(fhnumber<0){
			this.setMsg("失败：分红设置不正确。不能小于0。");
			return;
		}
		if(fhnumber>0){
			//直属
			if(ui.getFatherid()==1){
				if(ui.getParam8()-fhnumber<7){
					this.setMsg("失败：分红设置不正确。直属必须保留7个点的分红。");
					return;
				}
			}
		}
		if(pointssc>ui.getPointssc()){
			this.setMsg("失败：时时彩返点不能超过您的返点。");
			return;
		}
		if(point115>ui.getPoint115()){
			this.setMsg("失败：11选5返点不能超过您的返点。");
			return;
		}
		
		if(pointdp>ui.getPointdp()){
			this.setMsg("失败：低频返点不能超过您的返点。");
			return;
		}
		if(point115>0.06){
			this.setMsg("失败：11选5返点不能超过6%。");
			return;
		}
		if(pointdp>0.06){
			this.setMsg("失败：低频返点不能超过6%。");
			return;
		}
//		Double point115 = MathBrian.sub(pointssc, 0.02);
//		if(point115<0){
//			point115=0.0;
//		}
//		if(point115>0.06){
//			point115 = 0.06;
//		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentUserAdd");
		olist.add(ui.getId());
		olist.add(ui.getLoginname());
		olist.add(loginname);
		olist.add(pwd);
		
		olist.add(1);//type
		olist.add(nickname);
		if(ui.getFatherflag()==null){
			olist.add(ui.getId());
		}else{
			olist.add(ui.getFatherflag());
		}
		
		olist.add(pointssc);
		olist.add(point115);
		olist.add(pointdp);
		olist.add(fhnumber);
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				loginname="";
				pwd="";
				pwdre="";
				nickname="";
				pointssc=0.0;
				fhnumber=0;
				point115=0.0;
				pointdp=0.0;
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			return;
		}
	}
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwdre() {
		return pwdre;
	}

	public void setPwdre(String pwdre) {
		this.pwdre = pwdre;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public UserItem getCuruser() {
		return curuser;
	}
	public void setCuruser(UserItem curuser) {
		this.curuser = curuser;
	}
	public List<SelectItem> getSelssc() {
		return selssc;
	}
	public void setSelssc(List<SelectItem> selssc) {
		this.selssc = selssc;
	}
	public Double getPointssc() {
		return pointssc;
	}
	public void setPointssc(Double pointssc) {
		this.pointssc = pointssc;
	}
	public List<AgentPeItem> getPelist() {
		return pelist;
	}
	public void setPelist(List<AgentPeItem> pelist) {
		this.pelist = pelist;
	}
	public List<SelectItem> getSelfh() {
		return selfh;
	}
	public void setSelfh(List<SelectItem> selfh) {
		this.selfh = selfh;
	}
	public Integer getFhnumber() {
		return fhnumber;
	}
	public void setFhnumber(Integer fhnumber) {
		this.fhnumber = fhnumber;
	}
	public Double getPoint115() {
		return point115;
	}
	public void setPoint115(Double point115) {
		this.point115 = point115;
	}
	public Double getPointdp() {
		return pointdp;
	}
	public void setPointdp(Double pointdp) {
		this.pointdp = pointdp;
	}
	public List<SelectItem> getSel115() {
		return sel115;
	}
	public void setSel115(List<SelectItem> sel115) {
		this.sel115 = sel115;
	}
	public List<SelectItem> getSeldp() {
		return seldp;
	}
	public void setSeldp(List<SelectItem> seldp) {
		this.seldp = seldp;
	}
}
