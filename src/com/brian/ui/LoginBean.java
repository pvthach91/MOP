package com.brian.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.brian.item.FavoriteItem;
import com.brian.item.FavoriteItemSon;
import com.brian.item.LotteryInfoEnum;
import com.brian.item.OnlineItem;
import com.brian.item.SysMsgItem;
import com.brian.item.UserItem;
import com.brian.service.GlobalDefine;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class LoginBean extends LogXY{
	private UserItem user = new UserItem();
	private String ubalance="0.00";	//2015-09-13添加
	
	private Integer msgnum = 0;
	private String webname = "";
	
	private UserItem updateuser = new UserItem();
	private String pwd2;
	private String checkpwd2;
	
	private String notice="";
	private Integer randompic = 0;
	
	private List<FavoriteItemSon> favList;
	
	private List<FavoriteItemSon> sonList;
	
	private List<String> favoriteLottIdList;
	
	private String oldpwd;
	private String newpwd;
	private String newpwd2;
	
	private String oldcheckpwd;
	private String newcheckpwd;
	private String newcheckpwd2;
	
	
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
	public LoginBean(){
		webname = InitServlet.CNAME;
		user.setLoginname("会员帐号");
		
		List<SysMsgItem> msglist= InitData.msglist;
		notice = "";
		StringBuffer sb = new StringBuffer("");
		int i=1;
		for(SysMsgItem not:msglist){
			sb.append("<a href='#' onclick='noticeClick("+not.getId()+")'>"+i+++"、"+not.getTitle()+"</a><br>");
		}
		notice = sb.toString();
	}
	@SuppressWarnings("unchecked")
	public String loginnew(){
		this.setMsg(null);
		String u = CommonMethod.getParam("un");
		String p = CommonMethod.getParam("pwd");
		/**
		if(u==null || p ==null){
			this.setMsg("登录名或者密码不能为空");
			return null;
		}
		if(u.trim().length()<4|| "".equals(p.trim())){
			this.setMsg("登录名或者密码有误。请重新输入！");
			return null;
		}
		*/
		//user.setLoginname(u);
		//user.setPwd(p);
		
		if(u==null || "".equals(u)){
			this.setMsg("登录名不能为空！");
			return null;
		}
		if(p==null || "".equals(p)){
			this.setMsg("密码不能为空！");
			return null;
		}
		if(InitData.checkLoginTimes(CommonMethod.getIpAddress())>6){
			this.getLog().warn("频繁登录："+user.getLoginname());
			//写INPORT
			this.setMsg("您的登录操作过于频繁，请休息下，5分钟后再试！");
			return null;
		}
		String ip = CommonMethod.getIpAddress();
		List<Object> olist = new ArrayList<Object>();
		olist.add("UILoginMainNEW");
		olist.add(u);
		olist.add(p);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> oslist= (List<Object>)rlist.get(1);
				UserItem ui = (UserItem)oslist.get(0);
				favList = (List<FavoriteItemSon>) oslist.get(1);
				sonList = new ArrayList<FavoriteItemSon>();
				favoriteLottIdList = new ArrayList<String>();
				if( favList.size() > 0 ){
					for(FavoriteItem temp :favList){
						FavoriteItemSon itemSon = new FavoriteItemSon();
						// 枚举类
						itemSon.setUrl(LotteryInfoEnum.Lottery_ID_URL.getOutername(temp.getLotteryid().toString()));
						itemSon.setLotteryname(temp.getLotteryname());
						itemSon.setLotteryid(temp.getLotteryid());
						sonList.add(itemSon);
						favoriteLottIdList.add(temp.getLotteryid().toString());
						InitData.clearLoginTimes(CommonMethod.getIpAddress());
					}
				}
				
				user = ui;
				msgnum = Integer.parseInt(oslist.get(2).toString());
//				//测试
//				ui.setStatus(10);
				if(ui.getStatus()==8){
					//修改登录密码
					CommonMethod.putToSession("USERINFO", ui);
					this.setMsg("-2");
					return null;
				}else if(ui.getStatus()==9){
					//修改资金密码
					CommonMethod.putToSession("USERINFO", ui);
					this.setMsg("-3");
					return null;
				}else if(ui.getStatus()==10){
					//修改登录密码和资金密码
					CommonMethod.putToSession("USERINFO", ui);
					this.setMsg("-4");
					return null;
				}
				if(ui.getEmail()!=null){
					CommonMethod.putToSession("USERINFO", ui);
					return "INDEXMAIN";
				}else{
					this.setMsg("-1");
					return null;
				}
			}else{
				String rs = (String)rlist.get(1);
				this.setMsg(rs);
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	public void updateUserInfo(){
		this.setMsg(null);
		//UPersonFirstEnter
		UserItem usi = CommonMethod.getCurUser();
		if(usi!=null){
			//您已经修改过用户资料，请勿重复提交！\r\n或者您已经登录了另外一个账号，请先退出另外那个账号!
			this.setMsg("-1");
			return;
		}
		if(!CommonMethod.checkEmail(updateuser.getEmail())){
			this.setMsg("邮箱输入格式不正确，请重试");
			return;
		}
		if(updateuser.getPhone()==null){
			updateuser.setPhone("");
		}
		
		if(updateuser.getQq()==null){
			//this.setMsg("联系QQ输入不正确，请重试");
			//return;
			updateuser.setQq("");
		}
		
		if(updateuser.getPwd().length()<5||updateuser.getPwd().length()>30){
			this.setMsg("登录密码不能小于5个或大于30个字符。请重试");
			return;
		}
		if(updateuser.getCheckpwd().length()<5||updateuser.getCheckpwd().length()>30){
			this.setMsg("资金密码不能小于5个或大于30个字符。请重试");
			return;
		}
		if(updateuser.getPwd().equals(updateuser.getCheckpwd())){
			this.setMsg("登录密码与资金密码不能相同。请重试");
			return;
		}
		
		List<Object> olist = new ArrayList<Object>();
		
		String ip = CommonMethod.getIpAddress();
		olist.add("UPersonFirstEnter");
		olist.add(user.getLoginname());
		olist.add(updateuser.getEmail());
		olist.add(updateuser.getPhone());
		olist.add(updateuser.getQq());
		olist.add(updateuser.getPwd());
		olist.add(updateuser.getCheckpwd());
		olist.add(ip);
		olist.add(updateuser.getNickname());//updateuser.getName()
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				//更改用户信息
				user.setEmail(updateuser.getEmail());
				user.setPhone(updateuser.getPhone());
				user.setQq(updateuser.getQq());
				user.setPwd(updateuser.getPwd());
				user.setCheckpwd(updateuser.getCheckpwd());
				//user.setName(updateuser.getName());
				user.setNickname(updateuser.getNickname());
				CommonMethod.putToSession("USERINFO", user);
				//CommonMethod.PageJump("/main.shtml");
				//return "INDEXMAIN";
				/**
				try{
					SmsSend.sendSmsNew(updateuser.getPhone(), "尊敬的客户，感谢您注册成为华彩会员，华人彩票客服部祝您：工作顺利、身体健康。");
				}catch(Exception ex){
					ex.printStackTrace();
				}
				*/
				//new ThreadSms(updateuser.getPhone(),"【华彩-诚创未来】尊敬的客户，感谢您注册华彩，华彩全体工作人员祝您：工作顺利、身体健康。退订回复TD。").start();
				return;
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
	
	/**
	 * 解决Session过期问题
	 */
	public void Online(){
		try{
			UserItem ui = CommonMethod.getCurUser();
			if(ui==null || ui.getLoginname()==null){
				CommonMethod.PageJump("/login.shtml");
				return;
			}
			String ip = CommonMethod.getIpAddress();
			String userid = CommonMethod.getCurUser().getLoginname();
			List<OnlineItem> olist = InitData.onlinelist;
			boolean flag = false;
			long curtime = new Date().getTime();
			for(OnlineItem item : olist){
				if(item.getUserid().equals(userid)){
					flag = true;
					item.setTimes(curtime);
					break;
				}
			}
			if(!flag){
				OnlineItem  oitem = new OnlineItem();
				oitem.setIp(ip);
				oitem.setTimes(curtime);
				oitem.setUserid(userid);
				olist.add(oitem);
			}
			//Delete OffTime
			for(OnlineItem item : olist){
				//超过7分钟没有操作的直接删除
				if(curtime-item.getTimes()>420*1000){
					olist.remove(item);
				}
			}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	/**
	 * 修改昵称
	 */
	public void changeNickName(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null || ui.getLoginname()==null){
			this.setMsg("失败：Session失效，请重新登录");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPersonNickName");
		olist.add(ui.getLoginname());
		olist.add(user.getNickname());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				String rs = rlist.get(1).toString();
				this.setMsg(rs);
				return;
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			return;
		}
	}
	public void loginout(){
		user = new UserItem();
		HttpSession session= (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.invalidate();
	}
	@SuppressWarnings("unchecked")
	public String loginAG(){
		this.setMsg(null);
		if(user.getLoginname()==null || "".equals(user.getLoginname())){
			this.setMsg("用户名不能为空！");
			return null;
		}
		if(user.getFatherid()==-1){
			this.setMsg("商家编号不能为空！");
			return null;
		}
		String ip = CommonMethod.getIpAddress();
		List<Object> olist = new ArrayList<Object>();
		olist.add("loginagent");
		olist.add(user.getLoginname());
		olist.add(user.getFatherid());
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = (String)rlist.get(0);
			if("0".equals(flag)){
				List<Object> oslist= (List<Object>)rlist.get(1);
				UserItem ui = (UserItem)oslist.get(0);
				user = ui;
				CommonMethod.putToSession("USERINFO", ui);
				return "INDEXMAIN";
			}else{
				String rs = (String)rlist.get(1);
				this.setMsg(rs);
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
		}
		return null;
	}
	
	public String forgetPwd(){
		if(updateuser.getLoginname() == null || updateuser.getLoginname() == "" ){
			this.setMsg("用户名为空,请重试");
			return null;
		}
		
		if(!CommonMethod.checkEmail(updateuser.getEmail())){
			this.setMsg("邮箱输入格式不正确，请重试");
			return null;
		}
		if(updateuser.getPwd().length()<5 || updateuser.getPwd().length()>30){
			this.setMsg("登录密码不能小于5个或大于30个字符。请重试");
			return null;
		}
		if(updateuser.getCheckpwd().length()<5 || updateuser.getCheckpwd().length()>30){
			this.setMsg("资金密码不能小于5个或大于30个字符。请重试");
			return null;
		}
		if(updateuser.getCheckpwd().equals(updateuser.getPwd())){
			this.setMsg("登录密码与资金密码不能相同。请重试");
			return null;
		}
		
		String ip = CommonMethod.getIpAddress();
		List<Object> olist = new ArrayList<Object>();
		olist.add("UGetPWDByEmail");
		olist.add(updateuser.getLoginname());
		olist.add(updateuser.getCheckpwd());
		olist.add(updateuser.getEmail());
		olist.add(updateuser.getPwd());
		olist.add(ip);
		
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				this.setMsg("0");
				return null;
			}else{
				String rs = (String)rlist.get(1);
				this.setMsg(rs);
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	
	
	public String loginPWD01(){
		this.setMsg(null);
//		UserItem ui = CommonMethod.getCurUser();
//		if(ui==null){
//			this.setMsg("Session过期，请重新登录后再操作！");
//			return null;
//		}
		if(user==null){
			this.setMsg("Session过期，请重新登录后再操作！");
			return null;
		}
		if(oldpwd==null || oldpwd.length()<1){
			this.setMsg("旧密码不能为空！");
			return null;
		}
		if(!newpwd.equals(newpwd2)){
			this.setMsg("两次输入的登录密码不一样！");
			return null;
		}
		if(newpwd==null || newpwd.length()<1){
			this.setMsg("新密码不能为空！");
			return null;
		}
		if(newpwd.length()<6){
			this.setMsg("密码长度不能小于6位！");
			return null;
		}
		String ip = CommonMethod.getIpAddress();
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPersonPwdNEW");
		olist.add(1);
		olist.add(user.getLoginname());
		olist.add(oldpwd);
		olist.add(newpwd);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return null;
			}else{
//				ui.setPwd(newpwd);
				oldpwd="";
				newpwd="";
				newpwd2="";
//				CommonMethod.putToSession("USERINFO", ui);
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return null;
		}
	}
	
	
	public String checkPWD01(){
		this.setMsg(null);
//		UserItem ui = CommonMethod.getCurUser();
//		if(ui==null){
//			this.setMsg("Session过期，请重新登录后再操作！");
//			return null;
//		}
		if(user==null){
			this.setMsg("Session过期，请重新登录后再操作！");
			return null;
		}
		if(oldcheckpwd==null || oldcheckpwd.length()<1){
			this.setMsg("旧密码不能为空！");
			return null;
		}
		if(!newcheckpwd.equals(newcheckpwd2)){
			this.setMsg("两次输入的登录密码不一样！");
			return null;
		}
		if(newcheckpwd==null || newcheckpwd.length()<1){
			this.setMsg("新密码不能为空！");
			return null;
		}
		if(newcheckpwd.length()<6){
			this.setMsg("密码长度不能小于6位！");
			return null;
		}
		if(InitData.checkPwdTimes(CommonMethod.getIpAddress(),user.getLoginname())>GlobalDefine.optFrequently){
			this.getLog().warn("资金密码使用频繁："+user.getLoginname());
			this.setMsg("您的操作过于频繁，请休息下，5分钟后再试！");
			return null;
		}
//		if(!oldcheckpwd.equals(user.getCheckpwd())){
//			this.setMsg("失败：您输入的资金密码不正确。");
//			return null;
//		}
		String ip = CommonMethod.getIpAddress();
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPersonPwdNEW");
		olist.add(2);
		olist.add(user.getLoginname());
		olist.add(oldcheckpwd);
		olist.add(newcheckpwd);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return null;
			}else{
//				ui.setCheckpwd(newcheckpwd);
				oldcheckpwd="";
				newcheckpwd="";
				newcheckpwd2="";
//				CommonMethod.putToSession("USERINFO", ui);
				return null; 
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return null;
		}
	}
	public String loginPWDALL01(){
		this.setMsg(null);
//		UserItem ui = CommonMethod.getCurUser();
//		if(ui==null){
//			this.setMsg("Session过期，请重新登录后再操作！");
//			return null;
//		}
		if(user==null){
			this.setMsg("Session过期，请重新登录后再操作！");
			return null;
		}
		if(oldpwd==null || oldpwd.length()<1){
			this.setMsg("旧登录密码不能为空！");
			return null;
		}
		if(!newpwd.equals(newpwd2)){
			this.setMsg("两次输入的登录密码不一样！");
			return null;
		}
		if(newpwd==null || newpwd.length()<1){
			this.setMsg("新登录密码不能为空！");
			return null;
		}
		if(newpwd.length()<6){
			this.setMsg("新登录密码长度不能小于6位！");
			return null;
		}
//		if(!this.checkPassword(newpwd)){
//			this.setMsg("新登录密码必须包含大写字母+小写字母+数字");
//			return null;
//		}
		if(oldcheckpwd==null || oldcheckpwd.length()<1){
			this.setMsg("旧资金密码不能为空！");
			return null;
		}
		if(!newcheckpwd.equals(newcheckpwd2)){
			this.setMsg("两次输入的资金密码不一样！");
			return null;
		}
		if(newcheckpwd==null || newcheckpwd.length()<1){
			this.setMsg("新资金密码不能为空！");
			return null;
		}
		if(newcheckpwd.length()<6){
			this.setMsg("新资金密码长度不能小于6位！");
			return null;
		}
		if(InitData.checkPwdTimes(CommonMethod.getIpAddress(),user.getLoginname())>GlobalDefine.optFrequently){
			this.getLog().warn("资金密码使用频繁："+user.getLoginname());
			this.setMsg("您的操作过于频繁，请休息下，5分钟后再试！");
			return null;
		}
//		if(!oldcheckpwd.equals(user.getCheckpwd())){
//			this.setMsg("失败：您输入的资金密码不正确。");
//			return null;
//		}
//		if(!this.checkPassword(newcheckpwd)){
//			this.setMsg("新资金密码必须包含大写字母+小写字母+数字");
//			return null;
//		}
		String ip = CommonMethod.getIpAddress();
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPersonPwdBoth");
		olist.add(user.getLoginname());
		olist.add(oldpwd);
		olist.add(newpwd);
		olist.add(oldcheckpwd);
		olist.add(newcheckpwd);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return null;
			}else{
//				ui.setPwd(newpwd);
				oldpwd="";
				newpwd="";
				newpwd2="";
				oldcheckpwd="";
				newcheckpwd="";
				newcheckpwd2="";
//				CommonMethod.putToSession("USERINFO", ui);
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return null;
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
	public static void main(String[] args){
		Random r = new Random();
		for(int i=0;i<100;i++){
			System.out.println(r.nextInt(11));
		}
		
	}
	public UserItem getUser() {
		return user;
	}

	public void setUser(UserItem user) {
		this.user = user;
	}
	public String getWebname() {
		/**
		if(CommonMethod.getCurUser()==null){
			CommonMethod.PageJump("/login.shtml");
		}
		*/
		return webname;
	}
	public void setWebname(String webname) {
		this.webname = webname;
	}
	public UserItem getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(UserItem updateuser) {
		this.updateuser = updateuser;
	}
	public String getPwd2() {
		return pwd2;
	}
	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}
	public String getCheckpwd2() {
		return checkpwd2;
	}
	public void setCheckpwd2(String checkpwd2) {
		this.checkpwd2 = checkpwd2;
	}
	public Integer getMsgnum() {
		return msgnum;
	}
	public void setMsgnum(Integer msgnum) {
		this.msgnum = msgnum;
	}


	public String getNotice() {
		return notice;
	}


	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Integer getRandompic() {
		Random r = new Random();
		randompic = r.nextInt(12);
		return randompic;
	}

	public void setRandompic(Integer randompic) {
		this.randompic = randompic;
	}
	public String getUbalance() {
		double d = Double.parseDouble(CommonMethod.getCurUser().getBalance());
		DecimalFormat df = new DecimalFormat("#,###.0000");
		ubalance = df.format(d);
		return ubalance;
	}
	public void setUbalance(String ubalance) {
		this.ubalance = ubalance;
	}
	public List<FavoriteItemSon> getFavList() {
		return favList;
	}
	public void setFavList(List<FavoriteItemSon> favList) {
		this.favList = favList;
	}
	public List<FavoriteItemSon> getSonList() {
		return sonList;
	}
	public void setSonList(List<FavoriteItemSon> sonList) {
		this.sonList = sonList;
	}
	public List<String> getFavoriteLottIdList() {
		return favoriteLottIdList;
	}
	public void setFavoriteLottIdList(List<String> favoriteLottIdList) {
		this.favoriteLottIdList = favoriteLottIdList;
	}
	
	
	
}
