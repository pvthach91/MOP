package com.brian.uipage;

import java.util.*;

import javax.faces.model.SelectItem;

import com.brian.item.AdminBankCardItem;
import com.brian.item.BankPayTypeItem;
import com.brian.item.IpsInfoItem;
import com.brian.item.UserItem;
import com.brian.service.EnDecryption;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class DepositBean extends LogXY{
	private List<AdminBankCardItem> bankcardlist = new ArrayList<AdminBankCardItem>();
	private String amount;
	private String type;
	private int nums = 0;

	private String dpname;
	private String dpamount;
	private String dptime;
	private String dpmethod;
	private String remark;

	//IPS
	private String ipsparam = "";
	private List<SelectItem> ipsbank = new ArrayList<SelectItem>();
	private String ipsamount="0";
	private String bankcode;
	private int ipsmin = 100;
	private int ipsmax = 50000;

	//Alipay
	private String aliname;
	private String alimoney;
	//region Jack add 20180611
	// 按支付类别
	//每个Icon分类分别对应的支付项集合
	private List<BankPayTypeItem> bankcardPaylist= new  ArrayList<BankPayTypeItem>();

	public List<BankPayTypeItem> getBankcardPaylist() {
		return bankcardPaylist;
	}

	public void setBankcardPaylist(List<BankPayTypeItem> bankcardPaylist) {
		this.bankcardPaylist = bankcardPaylist;
	}
//endregion

	@SuppressWarnings("unchecked")
	public DepositBean(){
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			return;
		}
		//ipsbank = InitData.ipsBankList;

		dptime = CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss");
		int gid = ui.getBankgroup();
		List<Object> olist = new ArrayList<Object>();
		olist.add("UBankCardById");
		olist.add(gid);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> oslist= (List<Object>)rlist.get(1);

				List<AdminBankCardItem> templist = (List<AdminBankCardItem>)oslist.get(0);

				bankcardlist = new ArrayList<AdminBankCardItem>();

				for(AdminBankCardItem abc: templist){
					if(abc.getType()<=200) {
						bankcardlist.add(abc);
					}
				}


				//region Jack add 20180611

				//nums = bankcardlist.size();
				//按照AdminBankCardItem的 Ord 进行升序排列
				Collections.sort(bankcardlist, new Comparator<AdminBankCardItem>(){
					/*
					 * int compare(AdminBankCardItem p1, AdminBankCardItem p2) 返回一个基本类型的整型，
					 * 返回正数表示：p1大于p2； 返回0 表示：p1和p2相等；返回负数表示：p1小于p2。
					 */
					public int compare(AdminBankCardItem p1, AdminBankCardItem p2) {
						if(p1.getOrd() > p2.getOrd()){
							return 1;
						}
						if(p1.getOrd() == p2.getOrd()){
							return 0;
						}
						return -1;
					}
				});

				//组装符合前端Jsf页面的数据结构
				bankcardPaylist =new ArrayList<BankPayTypeItem>();
				boolean bFlag;
				for(AdminBankCardItem abc: bankcardlist){
					if(abc.getType()<=200) {
						bFlag=false;
						BankPayTypeItem bptTemp=null;
						if(bankcardPaylist.size()>0){
							for(BankPayTypeItem bpt:bankcardPaylist){
								if(bpt.getIcon().trim().equals(abc.getIcon().trim())){
									bFlag=true;
									bptTemp=bpt;
									break;
								}
							}
						}
						if(bFlag){
							bptTemp.getAdminBankCardItemList().add(abc);
						}else {
							bptTemp=new BankPayTypeItem();
							bptTemp.setIcon(abc.getIcon());
							bptTemp.setTab(abc.getTab());
							bptTemp.setIconlable(showIconLabel(bptTemp.getIcon()));
							bptTemp.getAdminBankCardItemList().add(abc);
							bankcardPaylist.add(bptTemp);
						}
					}
				}
				//按照bankcardPaylist的 Icon 进行升序排列
				Collections.sort(bankcardPaylist, new Comparator<BankPayTypeItem>(){
					/*
					 * int compare(BankPayTypeItem p1, BankPayTypeItem p2) 返回一个基本类型的整型，
					 * 返回正数表示：p1大于p2； 返回0 表示：p1和p2相等；返回负数表示：p1小于p2。
					 */
					public int compare(BankPayTypeItem p1, BankPayTypeItem p2) {
						int a=0;
						int b=0;
						try {
							a=Integer.parseInt(p1.getIcon());
						} catch(NumberFormatException e){}
						try {
							b=Integer.parseInt(p2.getIcon());
						} catch(NumberFormatException e){}
						if( a> b){
							return 1;
						}
						if(a==b){
							return 0;
						}
						return -1;
					}
				});
				nums = bankcardPaylist.size();
				bFlag=true;
				//endregion

			}else{
				String rs = (String)rlist.get(1);
				this.setMsg(rs);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	//region Jack add 20180611 按照ICON编号分类
	private String showIconLabel(String icon){
		String strIcon="";
		switch (icon) {
			case "1":strIcon="推荐";break;
			case "2":strIcon="网银转账";break;
			case "3":strIcon="在线支付";break;
			case "4":strIcon="扫码支付";break;
			case "5":strIcon="微信-支付宝";break;
			case "6":strIcon="快捷支付";break;
			default:break;
		}
		return strIcon;
	}
	//endregion

	/**
	 * 财付通
	 */
	public void tenpay(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		if(ui.getUsertype()>1){
			this.setMsg("【测试帐号】不能进行充值操作，有问题请咨询在线客服");
			return;
		}
		if(aliname==null || aliname.length()<1){
			this.setMsg("财付通姓名不能为空，请重新输入.");
			return;
		}
		if(!CommonMethod.checkFloat(alimoney)){
			this.setMsg("充值金额输入不正确，请重新输入.");
			return;
		}
		String cid = CommonMethod.getParam("cid");
		if(cid==null){
			this.setMsg("参数出错");
			return;
		}
		AdminBankCardItem recinfo = new AdminBankCardItem();
		for(AdminBankCardItem abc : bankcardlist){
			if(cid.equals(abc.getId()+"")){
				recinfo = abc;
				Double am = Double.parseDouble(alimoney);
				if(am>abc.getMaxamount()){
					this.setMsg("失败：最大存款金额为"+abc.getMaxamount()+" RMB，请重新输入");
					return;
				}
				if(am<abc.getMinamount()){
					this.setMsg("失败：最小存款金额为"+abc.getMinamount()+" RMB，请重新输入");
					return;
				}
				break;
			}
		}
		String dpid = "D"+CommonMethod.GetCurDate("yyMMddHHmmss")+CommonMethod.getRandomLetter(4);
		List<Object> olist = new ArrayList<Object>();
		olist.add("UOfflineDeposit");
		olist.add(ui.getFatherid());
		olist.add(dpid);
		olist.add(ui.getLoginname());
		olist.add(alimoney);
		olist.add(remark);
		olist.add("");//附言码
		olist.add(CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss"));
		olist.add(aliname);
		olist.add("");
		olist.add("");//卡号最后4位数
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		olist.add(cid);
		olist.add(recinfo.getName());
		olist.add(recinfo.getTab());
		olist.add(recinfo.getBankcard());
		olist.add("6");
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				String ms = rlist.get(1).toString();
				this.setMsg(ms);
				this.getLog().error("财付通充值失败："+ms);
				return;
			}else{
				alimoney = "";
				remark = "";
				aliname = "";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	public void alipay(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		if(ui.getUsertype()>1){
			this.setMsg("【测试帐号】不能进行充值操作，有问题请咨询在线客服");
			return;
		}
		if(aliname==null || aliname.length()<1){
			this.setMsg("支付宝姓名不能为空，请重新输入.");
			return;
		}
		if(!CommonMethod.checkFloat(alimoney)){
			this.setMsg("充值金额输入不正确，请重新输入.");
			return;
		}
		String cid = CommonMethod.getParam("cid");
		if(cid==null){
			this.setMsg("参数出错");
			return;
		}
		AdminBankCardItem recinfo = new AdminBankCardItem();
		for(AdminBankCardItem abc : bankcardlist){
			if(cid.equals(abc.getId()+"")){
				recinfo = abc;
				Double am = Double.parseDouble(alimoney);
				if(am>abc.getMaxamount()){
					this.setMsg("失败：最大存款金额为"+abc.getMaxamount()+" RMB，请重新输入");
					return;
				}
				if(am<abc.getMinamount()){
					this.setMsg("失败：最小存款金额为"+abc.getMinamount()+" RMB，请重新输入");
					return;
				}
				break;
			}
		}
		String dpid = "D"+CommonMethod.GetCurDate("yyMMddHHmmss")+CommonMethod.getRandomLetter(4);
		List<Object> olist = new ArrayList<Object>();
		olist.add("UOfflineDeposit");
		olist.add(ui.getFatherid());
		olist.add(dpid);
		olist.add(ui.getLoginname());
		olist.add(alimoney);
		olist.add(remark);
		olist.add("");//附言码
		olist.add(CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss"));
		olist.add(aliname);
		olist.add("");
		olist.add("");//卡号最后4位数
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		olist.add(cid);
		olist.add(recinfo.getName());
		olist.add(recinfo.getTab());
		olist.add(recinfo.getBankcard());
		olist.add("3");
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				String ms = rlist.get(1).toString();
				this.setMsg(ms);
				this.getLog().error("支付宝充值失败："+ms);
				return;
			}else{
				alimoney = "";
				remark = "";
				aliname = "";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	public void offlineDeposit(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		if(ui.getUsertype()>1){
			this.setMsg("【测试帐号】不能进行充值操作，有问题请咨询在线客服");
			return;
		}
		if(dpname==null || dpname.length()<1){
			this.setMsg("汇款卡户名不能为空，请重新输入.");
			return;
		}
		if(!CommonMethod.checkFloat(dpamount)){
			this.setMsg("汇款金额输入不正确，请重新输入.");
			return;
		}
		String cid = CommonMethod.getParam("cid");
		if(cid==null){
			this.setMsg("参数出错");
			return;
		}
		AdminBankCardItem recinfo = new AdminBankCardItem();
		for(AdminBankCardItem abc : bankcardlist){
			if(cid.equals(abc.getId()+"")){
				recinfo = abc;
				Double am = Double.parseDouble(dpamount);
				if(am>abc.getMaxamount()){
					this.setMsg("失败：最大存款金额为"+abc.getMaxamount()+" RMB，请重新输入");
					return;
				}
				if(am<abc.getMinamount()){
					this.setMsg("失败：最小存款金额为"+abc.getMinamount()+" RMB，请重新输入");
					return;
				}
				break;
			}
		}

		String dpid = "D"+CommonMethod.GetCurDate("yyMMddHHmmss")+CommonMethod.getRandomLetter(4);
		List<Object> olist = new ArrayList<Object>();
		olist.add("UOfflineDeposit");
		olist.add(ui.getFatherid());
		olist.add(dpid);
		olist.add(ui.getLoginname());
		olist.add(dpamount);
		olist.add(remark);
		olist.add("");//附言码
		olist.add(dptime);
		olist.add(dpname);
		olist.add(dpmethod);
		olist.add("");//卡号最后4位数
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		olist.add(cid);
		olist.add(recinfo.getName());
		olist.add(recinfo.getTab());
		olist.add(recinfo.getBankcard());
		olist.add("1");
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				String ms = rlist.get(1).toString();
				this.setMsg(ms);
				this.getLog().error("支付宝充值失败："+ms);
				return;
			}else{
				dpname = "";
				dpamount = "";
				remark = "";
				dpmethod = "";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	public void IPSDeposit2(){
		this.setMsg(null);
		ipsparam = "";
		ipsamount = CommonMethod.getParam("amount");
		bankcode = CommonMethod.getParam("bankcode");
		IPSDeposit();
	}
	public void IPSDeposit(){
		this.setMsg(null);
		ipsparam = "";
		if(ipsamount==null || bankcode==null){
			this.setMsg("失败:金额或者存款银行不正确.！");
			return;
		}
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("失败:Session失效,请重新登录！");
			return;
		}
		if(ui.getUsertype()>1){
			this.setMsg("失败:【测试帐号】不能进行充值操作，有问题请咨询在线客服");
			return;
		}
		if(bankcode==null || bankcode.length()<2){
			this.setMsg("失败:请选择充值银行后再操作！");
			return;
		}
		String loginname = ui.getLoginname();
		if(!CommonMethod.checkFloat(ipsamount)){
			this.setMsg("失败:汇款金额不能包含其他字符！");
			return;
		}
		Float f = Float.parseFloat(ipsamount);
		if(!"brian".equals(loginname)){
			if(f<ipsmin){
				this.setMsg("失败:汇款金额不能小于 "+ipsmin+" RBM！");
				return;
			}
			if(f>ipsmax){
				this.setMsg("失败:汇款金额不能大于 "+ipsmax+" RBM！");
				return;
			}
		}
		String ip = CommonMethod.getIpAddress();
		try{
			IpsInfoItem item = new IpsInfoItem();
			item.setAmount(new java.text.DecimalFormat("0.00").format(f));
			item.setMercode("026716");
			item.setMerkey("DNsnmnrEstlCusp69NmRa5TmZEGX3Nj8uNTOWZESjSpC4csNqo2Mg3u7t5CXVJAVAcHWPRZZ7Jj8GFhxNuhFKNRrTMUQMhxQGK2SKLyPDSDNKmrcOFAbavAoAQ33w8CQ");
			String sbillno = "DI"+CommonMethod.GetCurDate("yyMMddHHmmss")+CommonMethod.getRandomLetter(6);
			item.setBillno(sbillno);
			item.setFailurl("http://app.aichaowu.com/page/IpsFailback.php");
			item.setMerchanturl("http://app.aichaowu.com/page/Ipsback.php");
			item.setServerurl("http://check.aichaowu.com/ips.cfm");
			//item.setMerchanturl(CommonMethod.getCurWebPath()+"uc_sys/uc/Ipsback.php");
			//item.setFailurl(CommonMethod.getCurWebPath()+"uc_sys/uc/IpsFailback.php");
			EnDecryption ed = new EnDecryption();
			item.setAttach(ed.encrypt("DIVENXY##1"));
			item.setBankco(bankcode);

			List<Object> olist = new ArrayList<Object>();
			olist.add("UDepositIpsPending");
			olist.add(sbillno);
			olist.add(item.getAmount());
			olist.add(loginname);
			olist.add(ip);

			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}
			ipsparam = "http://app.aichaowu.com/page/depositOnlineSub.php?sid="+DealParam(item);
			this.setMsg(ipsparam);
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}

	}
	private String DealParam(IpsInfoItem ips) throws Exception{
		if(ips==null){
			return null;
		}
		String sb =new Date().getTime()+"##"+ips.getBillno()+"##"+ips.getMerkey()+"##"+ips.getMercode()+"##"+ips.getAmount()+"##"+ips.getMerchanturl()+"##"+ips.getFailurl()+"##"+ips.getAttach()+"##"+ips.getBankco()+"##"+ips.getServerurl();
		EnDecryption ed = new EnDecryption();
		return ed.encrypt(sb);
	}
	public List<AdminBankCardItem> getBankcardlist() {
		return bankcardlist;
	}
	public void setBankcardlist(List<AdminBankCardItem> bankcardlist) {
		this.bankcardlist = bankcardlist;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public String getDpname() {
		return dpname;
	}
	public void setDpname(String dpname) {
		this.dpname = dpname;
	}
	public String getDpamount() {
		return dpamount;
	}
	public void setDpamount(String dpamount) {
		this.dpamount = dpamount;
	}
	public String getDptime() {
		return dptime;
	}
	public void setDptime(String dptime) {
		this.dptime = dptime;
	}
	public String getDpmethod() {
		return dpmethod;
	}
	public void setDpmethod(String dpmethod) {
		this.dpmethod = dpmethod;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIpsparam() {
		return ipsparam;
	}

	public void setIpsparam(String ipsparam) {
		this.ipsparam = ipsparam;
	}

	public List<SelectItem> getIpsbank() {
		return ipsbank;
	}

	public void setIpsbank(List<SelectItem> ipsbank) {
		this.ipsbank = ipsbank;
	}

	public String getIpsamount() {
		return ipsamount;
	}

	public void setIpsamount(String ipsamount) {
		this.ipsamount = ipsamount;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public int getIpsmin() {
		return ipsmin;
	}

	public void setIpsmin(int ipsmin) {
		this.ipsmin = ipsmin;
	}

	public int getIpsmax() {
		return ipsmax;
	}

	public void setIpsmax(int ipsmax) {
		this.ipsmax = ipsmax;
	}
	public String getAliname() {
		return aliname;
	}
	public void setAliname(String aliname) {
		this.aliname = aliname;
	}
	public String getAlimoney() {
		return alimoney;
	}
	public void setAlimoney(String alimoney) {
		this.alimoney = alimoney;
	}
}
