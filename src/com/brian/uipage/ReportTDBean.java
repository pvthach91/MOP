package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.BalanceChangeItem;
import com.brian.item.MathBrian;
import com.brian.item.ReportUserItem;
import com.brian.item.ReportWLNewItem;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class ReportTDBean extends LogXY{
	private String stime2;
	private String etime2;
	private String member="0";
	private String newreg="0";
	private ReportUserItem res2list = new ReportUserItem();
	
	public ReportTDBean() throws Exception{
		//String s = CommonMethod.getTimeSpace(CommonMethod.GetCurDate("yyyy-MM-dd"), "yyyy-MM-dd", 1, "dd");
		String s = CommonMethod.GetCurDate("yyyy-MM-dd");
		stime2 = s;
		etime2 = s;
	}
	@SuppressWarnings("unchecked")
	public void mainSearch(){
		this.setMsg(null);
		//String ln = CommonMethod.getParam("ln");
		res2list = new ReportUserItem();
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("Session失效，请重新登录！");
			return;
		}
		if(stime2==null || stime2.length()<8){
			this.setMsg("日期选择不正确！");
			return;
		}
		if(etime2==null || etime2.length()<8){
			this.setMsg("日期选择不正确！");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("SMGReportUserMainNew");
		olist.add(ui.getLoginname());
		olist.add(stime2);
		olist.add(etime2);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				List<ReportUserItem> tmplist = (List<ReportUserItem>)sblist.get(0);
				if(tmplist.size()==1){
					res2list = tmplist.get(0);
					member = sblist.get(1).toString();
					newreg = sblist.get(2).toString();
					
					Double a = MathBrian.adds(res2list.getFd(), res2list.getWin(),res2list.getBonus());
					Double b = MathBrian.sub(a,res2list.getTz());
					res2list.setSaletz(b);
				}else{
					member = "0";
					newreg = "0";
				}
				//Double t = MathBrian.adds(res2list.getActive(), res2list.getWin(),res2list.getBonus());
				//res2list.setTotal(MathBrian.sub(res2list.getBet(),t));
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
	
	public void deal3(ReportWLNewItem rwi,BalanceChangeItem bci){
		if(bci.getOpttype()==1){
			//投注扣款
			rwi.setBet(MathBrian.add(rwi.getBet(),bci.getPay()));
		}else if(bci.getOpttype()==2){
			//充值
			rwi.setDeposit(MathBrian.add(rwi.getDeposit(),bci.getIncome()));
		}else if(bci.getOpttype()==3){
			//提现
			rwi.setWithdrawl(MathBrian.add(rwi.getWithdrawl(),bci.getPay()));
		}else if(bci.getOpttype()==4){
			//提现
			rwi.setWithdrawl(MathBrian.sub(rwi.getWithdrawl(),bci.getIncome()));
		}else if(bci.getOpttype()==5){
			//追号扣款
			rwi.setBet(MathBrian.add(rwi.getBet(),bci.getPay()));
		}else if(bci.getOpttype()==6){
			//追号返款
			rwi.setBet(MathBrian.sub(rwi.getBet(),bci.getIncome()));
		}else if(bci.getOpttype()==7){
			//游戏返点
			rwi.setBonus(MathBrian.add(rwi.getBonus(),bci.getIncome()));
		}else if(bci.getOpttype()==8){
			//奖金派发
			rwi.setWin(MathBrian.add(rwi.getWin(),bci.getIncome()));
		}else if(bci.getOpttype()==9){
			//撤单返款
			rwi.setBet(MathBrian.sub(rwi.getBet(),bci.getIncome()));
		}else if(bci.getOpttype()==10){
			//充值转出
			rwi.setXjdeposit(MathBrian.add(rwi.getXjdeposit(),bci.getPay()));
		}else if(bci.getOpttype()==11){
			//撤消返点
			rwi.setBonus(MathBrian.sub(rwi.getBonus(),bci.getPay()));
		}else if(bci.getOpttype()==12){
			//撤消派奖
			rwi.setWin(MathBrian.sub(rwi.getWin(),bci.getPay()));
		}else if(bci.getOpttype()==13){
			//赠送礼金
			rwi.setActive(MathBrian.add(rwi.getActive(),bci.getIncome()));
		}else if(bci.getOpttype()==15){
			//中幸运奖
			rwi.setActive(MathBrian.add(rwi.getActive(),bci.getIncome()));
		}else if(bci.getOpttype()==17){
			//充值转入(上级转过来的金额)
			rwi.setJsdeposit(MathBrian.add(rwi.getJsdeposit(), bci.getIncome()));
		}else if(bci.getOpttype()==16){
			//手工充值
			rwi.setDeposit(MathBrian.add(rwi.getDeposit(),bci.getIncome()));
		}
		
	}
	public String getStime2() {
		return stime2;
	}
	public void setStime2(String stime2) {
		this.stime2 = stime2;
	}
	public String getEtime2() {
		return etime2;
	}
	public void setEtime2(String etime2) {
		this.etime2 = etime2;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	public String getNewreg() {
		return newreg;
	}
	public void setNewreg(String newreg) {
		this.newreg = newreg;
	}
	public ReportUserItem getRes2list() {
		return res2list;
	}
	public void setRes2list(ReportUserItem res2list) {
		this.res2list = res2list;
	}
}
