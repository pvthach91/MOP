package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.brian.item.UserBankCardItem;
import com.brian.item.UserItem;
import com.brian.service.GlobalDefine;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.ui.LoginBean;
import com.brian.unit.CommonMethod;
/**
 * 提款申请
 * @author BRIAN
 * 
 */
public class WithdrawalBean extends LogXY{
	private String pageinit;
	
	private Integer wtimes;
	private List<SelectItem> banklist = new ArrayList<SelectItem>();
	private List<UserBankCardItem> wlist = new ArrayList<UserBankCardItem>();
	private String wamount="0";
	private String cardid="-1";
	private String checkpwd = "";
	
	private int wmin=100;
	private int wmax=49999;
	private int mrtxcs = 5;	//每日提现次数
	private int wdhours = 6;//申请卡满多少小时后才能提款
	
	public WithdrawalBean(){
		//init();
	}
	@SuppressWarnings("unchecked")
	public void init(){
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			return;
		}
		mrtxcs = ui.getParam7();
		String btime =CommonMethod.GetCurDate("yyyy-MM-dd");
		String stime = btime+" 00:00:00";
		String etime = btime+" 23:59:59";
		banklist.clear();
		wlist.clear();
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("UWithDrawlInit");
		olist.add(ui.getLoginname());
		olist.add(stime);
		olist.add(etime);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> oslist= (List<Object>)rlist.get(1);
				wtimes = Integer.parseInt(oslist.get(0).toString());
				String b = oslist.get(1).toString();
				if(!b.equals(ui.getBalance())){
					LoginBean lb = (LoginBean)CommonMethod.getSession("LoginBean");
					lb.getUser().setBalance(b);
					ui.setBalance(b);
				}
				wlist = (List<UserBankCardItem>)oslist.get(2);
				if(wlist.size()==0){
					CommonMethod.PageJump("/page/PersonBank1.shtml");
					return;
				}
				for(UserBankCardItem wi:wlist){
					banklist.add(new SelectItem(wi.getId(),wi.getBankname()+" | 银行卡尾数："+wi.getBankcard().substring(wi.getBankcard().length()-4)));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void apply(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("失败：Session失效，请重新登录。");
			return;
		}
		//时间为：2am-8am不能提现
		/**
		String h = CommonMethod.GetCurDate("H");
		int ih = Integer.parseInt(h);
		if(ih>=2 && ih<8){
			this.setMsg("失败：提现申请时间为早上 08:30 至次日凌晨 02:00。");
			return;
		}
		*/
		////////////////////////////
		
		if(ui.getUsertype()>1){
			this.setMsg("【测试帐号】不能进行提现操作，有问题请咨询在线客服");
			return;
		}
		if(wtimes>=mrtxcs){
			this.setMsg("失败：您今天已经提现次数为"+wtimes+"笔，请明天申请提现。");
			return;
		}
		if("-1".equals(cardid)){
			this.setMsg("失败：请选择提款卡后再操作。");
			return;
		}
		for(UserBankCardItem wi:wlist){
			if(cardid.equals(wi.getId()+"")){
				try{
					int n = CommonMethod.getTimeDiffe(wi.getSavetime(), CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss", "HH");
					if(n<wdhours){
						this.setMsg("失败：您新绑定的银行卡需要"+wdhours+"小时后才能申请提款。");
						return;
					}
				}catch(Exception ex){}
				break;
			}
		}
		if(!CommonMethod.checkFloat(wamount, 2)){
			this.setMsg("失败：提款金额不合法，小数点后请最多保留2位数字。");
			return;
		}
		Float f = Float.parseFloat(wamount);
		if(f<wmin){
			this.setMsg("失败：提款金额不能低于最小提现金额。");
			return;
		}
		if(f>wmax){
			this.setMsg("失败：提款金额不能高于最大提现金额。");
			return;
		}
		if(InitData.checkPwdTimes(CommonMethod.getIpAddress(),ui.getLoginname())>GlobalDefine.optFrequently){
			this.getLog().warn("使用频繁："+ui.getLoginname());
			this.setMsg("您的操作过于频繁，请休息下，5分钟后再试！");
			return;
		}
//		if(!checkpwd.equals(ui.getCheckpwd())){
//			this.setMsg("失败：您输入的资金密码不正确。");
//			return;
//		}
		String id = "W"+CommonMethod.GetCurDate("yyMMddHHmmss")+CommonMethod.getRandomLetter(5);
		List<Object> olist = new ArrayList<Object>();
		olist.add("UWithDrawlApply");
		olist.add(id);
		olist.add(ui.getLoginname());
		olist.add(wamount);
		olist.add(cardid);
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		olist.add(checkpwd);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				wamount="0";
				//init();
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
	public Integer getWtimes() {
		return wtimes;
	}

	public void setWtimes(Integer wtimes) {
		this.wtimes = wtimes;
	}

	public List<SelectItem> getBanklist() {
		return banklist;
	}

	public void setBanklist(List<SelectItem> banklist) {
		this.banklist = banklist;
	}

	public String getWamount() {
		return wamount;
	}

	public void setWamount(String wamount) {
		this.wamount = wamount;
	}

	public String getCheckpwd() {
		return checkpwd;
	}

	public void setCheckpwd(String checkpwd) {
		this.checkpwd = checkpwd;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public int getWmin() {
		return wmin;
	}
	public void setWmin(int wmin) {
		this.wmin = wmin;
	}
	public int getWmax() {
		return wmax;
	}
	public void setWmax(int wmax) {
		this.wmax = wmax;
	}
	public int getMrtxcs() {
		return mrtxcs;
	}
	public void setMrtxcs(int mrtxcs) {
		this.mrtxcs = mrtxcs;
	}
	public List<UserBankCardItem> getWlist() {
		return wlist;
	}
	public void setWlist(List<UserBankCardItem> wlist) {
		this.wlist = wlist;
	}
	public int getWdhours() {
		return wdhours;
	}
	public void setWdhours(int wdhours) {
		this.wdhours = wdhours;
	}
	public String getPageinit() {
		init();
		return pageinit;
	}
	public void setPageinit(String pageinit) {
		this.pageinit = pageinit;
	}
}
