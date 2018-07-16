package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.UserBankCardItem;
import com.brian.item.UserItem;
import com.brian.service.GlobalDefine;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.ui.LoginBean;
import com.brian.unit.CommonMethod;

public class PersonBankCardBean extends LogXY{
	private List<UserBankCardItem> bankcardlist = new ArrayList<UserBankCardItem>();
	private Integer banknumber = 0;
	
	private String bankcard2;
	private String checkpwd;
	private UserBankCardItem newbankcard = new UserBankCardItem();
	public PersonBankCardBean(){
		searchBankcard();
	}
	public void addBankcardCheck(){
		this.setMsg(null);
		UserItem ui= CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("失败：Session失效，请重新登录。");
			return;
		}
		if(banknumber>=5){
			this.setMsg("失败：您最多只能绑定5张银行卡.");
			return;
		}
		if(newbankcard.getBankname().length()<2){
			this.setMsg("失败：请选择【开户银行】.");
			return;
		}
		if("请选择 ...".equals(newbankcard.getProvince())){
			this.setMsg("失败：请选择【开户银行省份】.");
			return;
		}
		if("请选择 ...".equals(newbankcard.getCity())){
			this.setMsg("失败：请选择【开户银行城市】.");
			return;
		}
		
		if("农业银行".equals(newbankcard.getBankname())){
			if(newbankcard.getBranchname().length()<4){
				this.setMsg("失败：【开户支行名称】长度至少4个字符.");
				return;
			}
		}
		if(newbankcard.getName().length()<2){
			this.setMsg("失败：【开户人姓名】长度至少2个字符，请重试");
			return;
		}
		/**
		if(!newbankcard.getName().equals(ui.getName())){
			this.setMsg("失败：【开户人姓名】与注册时填写的姓名不一致，请重试");
			return;
		}
		*/
		if(!bankcard2.equals(newbankcard.getBankcard())){
			this.setMsg("失败：两次输入的【银行卡号码】不一致。");
			return;
		}
		if(newbankcard.getBankcard().length()<16 || newbankcard.getBankcard().length()>19){
		   this.setMsg("失败：【银行卡号码】长度位数为16位到19位之间。");
		   return;
		}
		if(InitData.checkPwdTimes(CommonMethod.getIpAddress(),ui.getLoginname())>GlobalDefine.optFrequently){
			this.getLog().warn("资金密码使用频繁："+ui.getLoginname());
			this.setMsg("您的资金密码输入过于频繁，请休息下，5分钟后再试！");
			return;
		}
//		if(!checkpwd.equals(ui.getCheckpwd())){
//			this.setMsg("失败：资金密码不正确，请重试。");
//			return;
//		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("userChechPwd");
		olist.add(ui.getLoginname());
		olist.add(checkpwd);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	//添加银行卡
	public void addBankcard(){
		addBankcardCheck();
		if(this.getMsg()!=null){
			return;
		}
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("Session失效，请重新登录");
			return;
		}
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPersonBankBinding");
		olist.add(ui.getLoginname());
		olist.add(newbankcard.getBankname());
		olist.add(newbankcard.getProvince());
		olist.add(newbankcard.getCity());
		olist.add(newbankcard.getName());
		olist.add(newbankcard.getBankcard());
		olist.add(newbankcard.getBranchname());
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				if(ui.getBankcheck()==0){
					LoginBean lb = (LoginBean)CommonMethod.getSession("LoginBean");
					lb.getUser().setBankcheck(1);
					ui.setBankcheck(1);
				}
				newbankcard = new UserBankCardItem();
				bankcard2="";
				searchBankcard();
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
	//查询银行卡
	@SuppressWarnings("unchecked")
	public void searchBankcard(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("Session失效，请重新登录");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPersonBankCardSearch");
		olist.add(ui.getLoginname());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				bankcardlist = (List<UserBankCardItem>)sblist.get(0);
				banknumber = bankcardlist.size();
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
	//锁定银行卡
	public void lockBankcard(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("Session失效，请重新登录");
			return;
		}
		if(ui.getEmail()==null){
			this.setMsg("失败：请先完善用户资料后再操作");
			return;
		}
		if(banknumber==0){
			this.setMsg("失败：请先添加银行卡之后再进行银行卡锁定。");
			return;
		}
		String ip = CommonMethod.getIpAddress();
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPersonBankCardLock");
		olist.add(ui.getLoginname());
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				LoginBean lb = (LoginBean)CommonMethod.getSession("LoginBean");
				lb.getUser().setBankcheck(2);
				ui.setBankcheck(2);
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			return;
		}
	}
	
	public List<UserBankCardItem> getBankcardlist() {
		return bankcardlist;
	}
	public void setBankcardlist(List<UserBankCardItem> bankcardlist) {
		this.bankcardlist = bankcardlist;
	}
	public UserBankCardItem getNewbankcard() {
		return newbankcard;
	}
	public void setNewbankcard(UserBankCardItem newbankcard) {
		this.newbankcard = newbankcard;
	}
	public Integer getBanknumber() {
		return banknumber;
	}
	public void setBanknumber(Integer banknumber) {
		this.banknumber = banknumber;
	}
	public String getBankcard2() {
		return bankcard2;
	}
	public void setBankcard2(String bankcard2) {
		this.bankcard2 = bankcard2;
	}
	public String getCheckpwd() {
		return checkpwd;
	}
	public void setCheckpwd(String checkpwd) {
		this.checkpwd = checkpwd;
	}
}
