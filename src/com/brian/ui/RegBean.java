package com.brian.ui;

import java.util.ArrayList;
import java.util.List;

import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 * 推广自动注册
 * @author BRIAN
 *
 */
public class RegBean extends LogXY{
	private String sign;
	private String loginname="";
	private String pwd="";
	private Integer type=0;
	private String nickname="";
	
	private String zjpwd;
	private String email;
	
	public RegBean(){
		
	}
	public void regNew(){
		this.setMsg(null);
		loginname = CommonMethod.getParam("lname");
		pwd = CommonMethod.getParam("pwd");
		zjpwd = CommonMethod.getParam("checkpwd");
		email = CommonMethod.getParam("email");
		nickname = CommonMethod.getParam("nickname");
		sign=CommonMethod.getParam("id");
		if(sign==null){
			sign="";
		}
		if(loginname==null || loginname.length()<1){
			this.setMsg("失败：登录名不能为空。");
			return;
		}
		if(loginname.length()<5 || loginname.length()>30){
			this.setMsg("失败：登录名长度太不满足条件，最少需要5个字符，最多30个字符。");
			return;
		}
		if(!CommonMethod.checkString(loginname)){
			this.setMsg("失败：登录名只能包含大小写字母和数字。");
			return;
		}
		if(pwd==null || pwd.length()<1){
			this.setMsg("失败：登录密码不能为空。");
			return;
		}
		if(pwd.length()<6){
			this.setMsg("失败：登录密码长度不能小于6个字符。");
			return;
		}
		
		if(zjpwd==null || zjpwd.length()<1){
			this.setMsg("失败：资金密码不能为空。");
			return;
		}
		if(zjpwd.length()<6){
			this.setMsg("失败：资金密码长度不能小于6个字符。");
			return;
		}
		if(zjpwd.equals(pwd)){
			this.setMsg("失败：资金密码不能和登录相同。");
			return;
		}
		if(nickname==null || nickname.length()<1){
			this.setMsg("失败：昵称不能为空。");
			return;
		}
		if(!CommonMethod.checkEmail(email)){
			this.setMsg("失败：邮箱输入格式不正确，请重试");
			return;
		}
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentAdvUserRegNew");
		olist.add(sign);
		olist.add(loginname);
		olist.add(pwd);
		olist.add(zjpwd);
		olist.add(nickname);
		olist.add(email);
		olist.add(""); // for the "phone" field, legacy issue 
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}else{
				loginname="";
				pwd="";
				nickname="";
				email="";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	public void reg(){
		this.setMsg(null);
		
		if(sign==null){
			sign="";
		}
		if(loginname==null || loginname.length()<1){
			this.setMsg("失败：登录名不能为空。");
			return;
		}
		if(loginname.length()<5 || loginname.length()>30){
			this.setMsg("失败：登录名长度太不满足条件，最少需要5个字符，最多30个字符。");
			return;
		}
		if(!CommonMethod.checkString(loginname)){
			this.setMsg("失败：登录名只能包含大小写字母和数字。");
			return;
		}
		if(pwd==null || pwd.length()<1){
			this.setMsg("失败：密码不能为空。");
			return;
		}
		if(pwd.length()<6){
			this.setMsg("失败：密码长度不能小于6个字符。");
			return;
		}
		if(nickname==null || nickname.length()<1){
			this.setMsg("失败：昵称不能为空。");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentAdvUserReg");
		olist.add(sign);
		olist.add(loginname);
		olist.add(pwd);
		olist.add(type);
		olist.add(nickname);
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}else{
				loginname="";
				pwd="";
				nickname="";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}

	public void regNew(HttpServletRequest request, List<SelectItem> list){
		this.setMsg(null);
		/*String checkpwd = "";
		UserItem ui = CommonMethod.getCurUser(request);*/
		loginname = CommonMethod.getParam(list,"loginname");
		pwd = CommonMethod.getParam(list,"pwd");
		zjpwd = CommonMethod.getParam(list,"zjpwd");
		email = CommonMethod.getParam(list,"email");
//		phone = CommonMethod.getParam("phone");
		nickname = CommonMethod.getParam(list,"nickname");
		sign=CommonMethod.getParam(list,"id");
		if(sign==null){
			sign="";
		}
		if(loginname==null || loginname.length()<1){
			this.setMsg("失败：登录名不能为空。");
			return;
		}
		if(loginname.length()<5 || loginname.length()>30){
			this.setMsg("失败：登录名长度太不满足条件，最少需要5个字符，最多30个字符。");
			return;
		}
		if(!CommonMethod.checkString(loginname)){
			this.setMsg("失败：登录名只能包含大小写字母和数字。");
			return;
		}
		if(pwd==null || pwd.length()<1){
			this.setMsg("失败：登录密码不能为空。");
			return;
		}
		if(pwd.length()<6){
			this.setMsg("失败：登录密码长度不能小于6个字符。");
			return;
		}

		if(zjpwd==null || zjpwd.length()<1){
			this.setMsg("失败：资金密码不能为空。");
			return;
		}
		if(zjpwd.length()<6){
			this.setMsg("失败：资金密码长度不能小于6个字符。");
			return;
		}
		if(zjpwd.equals(pwd)){
			this.setMsg("失败：资金密码不能和登录相同。");
			return;
		}
		if(nickname==null || nickname.length()<1){
			this.setMsg("失败：昵称不能为空。");
			return;
		}
		if(!CommonMethod.checkEmail(email)){
			this.setMsg("失败：邮箱输入格式不正确，请重试");
			return;
		}
		/*if(phone==null || phone.trim().length()!=11){
		this.setMsg("失败：手机号码长度为11位字符，请重试");
			return;
			phone = "";
		}*/

		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentAdvUserRegNew");
		olist.add(sign);
		olist.add(loginname);
		olist.add(pwd);
		olist.add(zjpwd);
		olist.add(nickname);
		olist.add(email);
		//olist.add(phone);
		olist.add(""); // for the "phone" field, legacy issue
		String ip = CommonMethod.getIpAddress(request);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}else{
				loginname="";
				pwd="";
				nickname="";
				//phone="";
				email="";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return;
		}
	}
	public void reg(HttpServletRequest request){
		this.setMsg(null);

		if(sign==null){
			sign="";
		}
		if(loginname==null || loginname.length()<1){
			this.setMsg("失败：登录名不能为空。");
			return;
		}
		if(loginname.length()<5 || loginname.length()>30){
			this.setMsg("失败：登录名长度太不满足条件，最少需要5个字符，最多30个字符。");
			return;
		}
		if(!CommonMethod.checkString(loginname)){
			this.setMsg("失败：登录名只能包含大小写字母和数字。");
			return;
		}
		if(pwd==null || pwd.length()<1){
			this.setMsg("失败：密码不能为空。");
			return;
		}
		if(pwd.length()<6){
			this.setMsg("失败：密码长度不能小于6个字符。");
			return;
		}
		if(nickname==null || nickname.length()<1){
			this.setMsg("失败：昵称不能为空。");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentAdvUserReg");
		olist.add(sign);
		olist.add(loginname);
		olist.add(pwd);
		olist.add(type);
		olist.add(nickname);
		String ip = CommonMethod.getIpAddress(request);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}else{
				loginname="";
				pwd="";
				nickname="";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return;
		}
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getZjpwd() {
		return zjpwd;
	}
	public void setZjpwd(String zjpwd) {
		this.zjpwd = zjpwd;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
