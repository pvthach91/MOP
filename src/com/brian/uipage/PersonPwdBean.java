package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.brian.item.UserItem;
import com.brian.service.GlobalDefine;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class PersonPwdBean extends LogXY{
	private String oldpwd;
	private String newpwd;
	private String newpwd2;
	
	private String oldcheckpwd;
	private String newcheckpwd;
	private String newcheckpwd2;
	
	public void loginPWD(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("Session过期，请重新登录后再操作！");
			return;
		}
		if(oldpwd==null || oldpwd.length()<1){
			this.setMsg("旧密码不能为空！");
			return;
		}
		if(!newpwd.equals(newpwd2)){
			this.setMsg("两次输入的登录密码不一样！");
			return;
		}
		if(newpwd==null || newpwd.length()<1){
			this.setMsg("新密码不能为空！");
			return;
		}
		if(newpwd.length()<6){
			this.setMsg("密码长度不能小于6位！");
			return;
		}
		if(!checkPassword(newpwd)){
			this.setMsg("新登录密码必须包含大写字母+小写字母+数字");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		String ip = CommonMethod.getIpAddress();
		olist.add("UPersonPwdNEW");
		olist.add("1");
		olist.add(ui.getLoginname());
		olist.add(oldpwd);
		olist.add(newpwd);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}else{
				oldpwd="";
				newpwd="";
				newpwd2="";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	public void checkPWD(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("Session过期，请重新登录后再操作！");
			return;
		}
		if(oldcheckpwd==null || oldcheckpwd.length()<1){
			this.setMsg("旧密码不能为空！");
			return;
		}
		if(!newcheckpwd.equals(newcheckpwd2)){
			this.setMsg("两次输入的登录密码不一样！");
			return;
		}
		if(newcheckpwd==null || newcheckpwd.length()<1){
			this.setMsg("新密码不能为空！");
			return;
		}
		if(newcheckpwd.length()<6){
			this.setMsg("密码长度不能小于6位！");
			return;
		}
		if(InitData.checkPwdTimes(CommonMethod.getIpAddress(),ui.getLoginname())>GlobalDefine.optFrequently){
			this.getLog().warn("资金密码使用频繁："+ui.getLoginname());
			this.setMsg("您的操作过于频繁，请休息下，5分钟后再试！");
			return;
		}
//		if(!oldcheckpwd.equals(ui.getCheckpwd())){
//			this.setMsg("失败：您输入的资金密码不正确。");
//			return;
//		}
//		if(!checkPassword(newcheckpwd)){
//			this.setMsg("新资金密码必须包含大写字母+小写字母+数字");
//			return;
//		}
		List<Object> olist = new ArrayList<Object>();
		String ip = CommonMethod.getIpAddress();
		olist.add("UPersonPwdNEW");
		olist.add("2");
		olist.add(ui.getLoginname());
		olist.add(oldcheckpwd);
		olist.add(newcheckpwd);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}else{
				oldcheckpwd="";
				newcheckpwd="";
				newcheckpwd2="";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	/**
	 * 验证密码必须有大写字母+小写字母+数字
	 * @param password
	 * @return
	 */
    public static boolean checkPassword(String password){
        String regex = "([A-Z]+[a-z]+[0-9]+[\\d\\w]*)|([A-Z]+[0-9]+[a-z]+[\\d\\w]*)|([0-9]+[a-z]+[A-Z]+[\\d\\w]*)" +
                         "|([0-9]+[A-Z]+[a-z]+[\\d\\w]*)|([a-z]+[0-9]+[A-Z]+[\\d\\w]*)|([a-z]+[A-Z]+[0-9]+[\\d\\w]*)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);        
        if (m.find()) {
            if (password.equals(m.group())){
                return true;
            }else {
            	return false;
            }
        }else {
        	return false;
        }
    }
	public String getOldpwd() {
		return oldpwd;
	}
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public String getNewpwd2() {
		return newpwd2;
	}
	public void setNewpwd2(String newpwd2) {
		this.newpwd2 = newpwd2;
	}
	public String getOldcheckpwd() {
		return oldcheckpwd;
	}
	public void setOldcheckpwd(String oldcheckpwd) {
		this.oldcheckpwd = oldcheckpwd;
	}
	public String getNewcheckpwd() {
		return newcheckpwd;
	}
	public void setNewcheckpwd(String newcheckpwd) {
		this.newcheckpwd = newcheckpwd;
	}
	public String getNewcheckpwd2() {
		return newcheckpwd2;
	}
	public void setNewcheckpwd2(String newcheckpwd2) {
		this.newcheckpwd2 = newcheckpwd2;
	}
}
