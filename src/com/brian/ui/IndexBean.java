package com.brian.ui;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.DictItem;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class IndexBean extends LogXY{
	private List<DictItem> saftlist = new ArrayList<DictItem>();
	
	private String email;
	private String phone;
	private String qq;
	private String pwd;
	private String pwd2;
	private String checkpwd;
	private String checkpwd2;
	private String name;
	
	public static void main(String[] args){
		//System.out.println(IndexBean.checkEmail("163yahoo@com"));
	}
	public void FirstSave(){
		this.setMsg(null);
		//UPersonFirstEnter
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("Session已经过期，请重新登录后再操作");
			return;
		}
		if(name==null){
			this.setMsg("姓名不能为空，请重试！");
			return;
		}
		if(name.length()<2 || name.length()>10){
			this.setMsg("姓名不能小于2个或者大于10个字符！");
			return;
		}
		if(ui.getEmail()!=null){
			this.setMsg("您已经修改过用户资料，请勿重复提交！");
			return;
		}
		if(!checkEmail(email)){
			this.setMsg("邮箱输入格式不正确，请重试");
			return;
		}
		if(phone==null || phone.length()!=11){
			this.setMsg("手机号码长度为11位字符，请重试");
			return;
		}
		if(qq.length()<4){
			this.setMsg("联系QQ输入不正确，请重试");
			return;
		}
		if(pwd.length()<5||pwd.length()>30){
			this.setMsg("登录密码不能小于5个或大于30个字符。请重试");
			return;
		}
		if(checkpwd.length()<5||checkpwd.length()>30){
			this.setMsg("资金密码不能小于5个或大于30个字符。请重试");
			return;
		}
		if(!pwd.equals(pwd2)){
			this.setMsg("两次输入的登录密码不一致。请核实");
			return;
		}
		if(!checkpwd.equals(checkpwd2)){
			this.setMsg("两次输入的资金密码不一致。请核实");
			return;
		}
		if(pwd.equals(checkpwd)){
			this.setMsg("登录密码与资金密码不能相同。请重试");
			return;
		}
		
		List<Object> olist = new ArrayList<Object>();
		
		String ip = CommonMethod.getIpAddress();
		olist.add("UPersonFirstEnter");
		olist.add(ui.getLoginname());
		olist.add(email);
		olist.add(phone);
		olist.add(qq);
		olist.add(pwd);
		olist.add(checkpwd);
		olist.add(ip);
		olist.add(name);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				//更改用户信息
				ui.setEmail(email);
				ui.setPhone(phone);
				ui.setQq(qq);
				ui.setPwd(pwd);
				ui.setCheckpwd(checkpwd);
				ui.setName(name);
				//ui.setBankcheck(1);
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
	
	public IndexBean(){
		saftlist.clear();
		UserItem ui = CommonMethod.getCurUser();
		if(ui.getCheckpwd()== null || ui.getCheckpwd().length()<2){
			DictItem di1 = new DictItem();
			di1.setId("1");
			di1.setKey("严重");
			di1.setValue("未【完善个人资料】。");
			
			di1.setType("#bd1333");
			saftlist.add(di1);
		}
		if(ui.getBankcheck()==0){
			DictItem di2 = new DictItem();
			di2.setId("2");
			di2.setKey("严重");
			di2.setValue("未【绑定银行卡号】。");
			di2.setType("#bd1333");
			saftlist.add(di2);
		}
		/**
		if(ui.getEmail()==null || ui.getEmail().length()<1){
			DictItem di2 = new DictItem();
			di2.setId("3");
			di2.setKey("警告");
			di2.setValue("未【设置资金密码】。");
			di2.setType("#ff651a");
			saftlist.add(di2);
		}
		*/
		if(ui.getBankcheck()==1){
			DictItem di2 = new DictItem();
			di2.setId("3");
			di2.setKey("建议");
			di2.setValue("【锁定提款银行】。");
			di2.setType("#0f902a");
			saftlist.add(di2);
		}
	}

	public boolean checkEmail(String email){
		if(email==null){
			return false;
		}
		//String eg = "(?=^[\\w.@]{6,50}$)\\w+@\\w+(?:\\.[\\w]{2,3}){1,2}";OLD
		//String eg ="^[a-zA-Z0-9_.]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]{1,}$";NEW
		String eg ="^[a-zA-Z0-9_.]+@[a-zA-Z0-9_]+(?:\\.[\\w]{2,3}){1,2}";
		//String eg2 = "[^ \t]{3,50}+@";
		return email.matches(eg);
	}
	public List<DictItem> getSaftlist() {
		return saftlist;
	}

	public void setSaftlist(List<DictItem> saftlist) {
		this.saftlist = saftlist;
	}

	public String getEmail() {
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			return email;
		}
		if(ui.getEmail()!=null){
			//跳转到Baidu
			CommonMethod.PageJump("http://www.baidu.com");
		}
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

	public String getCheckpwd() {
		return checkpwd;
	}

	public void setCheckpwd(String checkpwd) {
		this.checkpwd = checkpwd;
	}

	public String getCheckpwd2() {
		return checkpwd2;
	}

	public void setCheckpwd2(String checkpwd2) {
		this.checkpwd2 = checkpwd2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
