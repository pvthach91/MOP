package com.brian.uipage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.brian.item.MathBrian;
import com.brian.item.ReportUserItem;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;
import com.brian.unit.ComparatorAct;
import com.brian.unit.ComparatorBet;
import com.brian.unit.ComparatorDp;
import com.brian.unit.ComparatorRet;
import com.brian.unit.ComparatorWd;
import com.brian.unit.ComparatorWin;

/**
 * 盈亏报表
 * @author BRIAN
 *
 */
public class ReportWinLossBean extends LogXY{
	private String stime;
	private String etime;
	private String loginname="";
	
	private String uptime;
	//new version(DIVEN)
	private List<ReportUserItem> reportmain = new ArrayList<ReportUserItem>();
	private ReportUserItem ruitem =new ReportUserItem();
	private ReportUserItem totallist =new ReportUserItem();
	
	private String order = "0";
	public ReportWinLossBean() throws Exception{
		String s = CommonMethod.GetCurDate("yyyy-MM-dd");//CommonMethod.getTimeSpace(CommonMethod.GetCurDate("yyyy-MM-dd"), "yyyy-MM-dd", -1, "dd");
		stime = s;//CommonMethod.GetCurDate("yyyy-MM-dd");//+" 00:00:00";
		etime = s;//+" 23:59:59";
		totallist.setLoginname("合计");
		//loginname = CommonMethod.getCurUser().getLoginname();
		ruitem.setLoginname(loginname);
	}
	public void bntSearch(){
		this.setMsg(null);
		String s = "";
		if(loginname==null || loginname.trim().length()==0){
			s = CommonMethod.getCurUser().getLoginname();
			mainSearch(s);
		}else{
			mainSearch(loginname);
		}
		
	}
	public void searchNew(){
		mainSearch(CommonMethod.getParam("ln"));
	}
	@SuppressWarnings("unchecked")
	public void mainSearch(String ln){
		this.setMsg(null);
		reportmain.clear();
		ruitem =new ReportUserItem();
		totallist =new ReportUserItem();
		totallist.setLoginname("合计");
		if(ln==null || ln.length()<2){
			this.setMsg("参数出错");
			return;
		}
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		if(stime==null || stime.length()<10){
			this.setMsg("日期选择不正确！");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UITeamReport3NEW");
		olist.add(ln);
		olist.add(stime);
		olist.add(etime);
		olist.add(ui.getFatherflag());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				//自己
				List<ReportUserItem> selflist = (List<ReportUserItem>)sblist.get(0);
				//下级
				List<ReportUserItem> xjlist = (List<ReportUserItem>)sblist.get(1);
				if(selflist.size()==0){
					ReportUserItem rwn = new ReportUserItem();
					olist.add(ln);
					ruitem =rwn;
				}else{
					ruitem=selflist.get(0);
					ruitem.setLoginname(ln);
					ruitem.setSaletz(ruitem.getWin()+ruitem.getBonus()+ruitem.getFd()-ruitem.getTz());
				}
				{
					totallist.setAcdp(MathBrian.adds(totallist.getAcdp(),ruitem.getAcdp()));
					totallist.setBonus(MathBrian.add(totallist.getBonus(), ruitem.getBonus()));
					totallist.setCdfk(MathBrian.add(totallist.getCdfk(), ruitem.getCdfk()));
					totallist.setDj(MathBrian.add(totallist.getDj(), ruitem.getDj()));
					totallist.setDp(MathBrian.add(totallist.getDp(), ruitem.getDp()));
					totallist.setDpsg(MathBrian.add(totallist.getDpsg(), ruitem.getDpsg()));
					totallist.setDpsxf(MathBrian.add(totallist.getDpsxf(), ruitem.getDpsxf()));
					totallist.setDpxj(MathBrian.add(totallist.getDpxj(), ruitem.getDpxj()));
					totallist.setFd(MathBrian.add(totallist.getFd(), ruitem.getFd()));
					totallist.setLuck(MathBrian.add(totallist.getLuck(), ruitem.getLuck()));
					totallist.setSaletz(MathBrian.add(totallist.getSaletz(), ruitem.getSaletz()));
					totallist.setSalezh(MathBrian.add(totallist.getSalezh(), ruitem.getSalezh()));
					totallist.setWd(MathBrian.add(totallist.getWd(), ruitem.getWd()));
					totallist.setWdfail(MathBrian.add(totallist.getWdfail(), ruitem.getWdfail()));
					totallist.setWin(MathBrian.add(totallist.getWin(), ruitem.getWin()));
					totallist.setTz(MathBrian.add(totallist.getTz(), ruitem.getTz()));
				}
				if(xjlist!=null && xjlist.size()>0){
					reportmain = xjlist;
					for(ReportUserItem rui:reportmain){
						totallist.setAcdp(MathBrian.adds(totallist.getAcdp(),rui.getAcdp()));
						totallist.setBonus(MathBrian.add(totallist.getBonus(), rui.getBonus()));
						totallist.setCdfk(MathBrian.add(totallist.getCdfk(), rui.getCdfk()));
						totallist.setDj(MathBrian.add(totallist.getDj(), rui.getDj()));
						totallist.setDp(MathBrian.add(totallist.getDp(), rui.getDp()));
						totallist.setDpsg(MathBrian.add(totallist.getDpsg(), rui.getDpsg()));
						totallist.setDpsxf(MathBrian.add(totallist.getDpsxf(), rui.getDpsxf()));
						totallist.setDpxj(MathBrian.add(totallist.getDpxj(), rui.getDpxj()));
						totallist.setFd(MathBrian.add(totallist.getFd(), rui.getFd()));
						totallist.setLuck(MathBrian.add(totallist.getLuck(), rui.getLuck()));
						totallist.setSaletz(MathBrian.add(totallist.getSaletz(), rui.getSaletz()));
						totallist.setSalezh(MathBrian.add(totallist.getSalezh(), rui.getSalezh()));
						totallist.setWd(MathBrian.add(totallist.getWd(), rui.getWd()));
						totallist.setWdfail(MathBrian.add(totallist.getWdfail(), rui.getWdfail()));
						totallist.setWin(MathBrian.add(totallist.getWin(), rui.getWin()));
						totallist.setTz(MathBrian.add(totallist.getTz(), rui.getTz()));
					}
					totallist.setSaletz(totallist.getWin()+totallist.getBonus()+totallist.getFd()-totallist.getTz());
				}
				for(ReportUserItem uitem:reportmain){
					uitem.setSaletz(uitem.getWin()+uitem.getBonus()+uitem.getFd()-uitem.getTz());
				}
				if("0".equals(order)){
					ComparatorBet cb = new ComparatorBet();
					Collections.sort(reportmain, cb);
				}else if("1".equals(order)){
					ComparatorRet cb = new ComparatorRet();
					Collections.sort(reportmain, cb);
				}else if("2".equals(order)){
					ComparatorWin cb = new ComparatorWin();
					Collections.sort(reportmain, cb);
				}else if("3".equals(order)){
					ComparatorAct cb = new ComparatorAct();
					Collections.sort(reportmain, cb);
				}else if("4".equals(order)){
					ComparatorDp cb = new ComparatorDp();
					Collections.sort(reportmain, cb);
				}else if("5".equals(order)){
					ComparatorWd cb = new ComparatorWd();
					Collections.sort(reportmain, cb);
				}
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
	
	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}


	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<ReportUserItem> getReportmain() {
		return reportmain;
	}

	public void setReportmain(List<ReportUserItem> reportmain) {
		this.reportmain = reportmain;
	}
	public ReportUserItem getRuitem() {
		return ruitem;
	}
	public void setRuitem(ReportUserItem ruitem) {
		this.ruitem = ruitem;
	}
	public ReportUserItem getTotallist() {
		return totallist;
	}
	public void setTotallist(ReportUserItem totallist) {
		this.totallist = totallist;
	}
	public String getUptime() {
		try{
			String s = CommonMethod.GetCurDate("HH");
			if("03".compareTo(s)>=0){
				String bf = CommonMethod.getTimeSpace(CommonMethod.GetCurDate("yyyy-MM-dd"), "yyyy-MM-dd", -2, "dd");
				uptime =bf + " 23:59:59";
			}else{
				String bf = CommonMethod.getTimeSpace(CommonMethod.GetCurDate("yyyy-MM-dd"), "yyyy-MM-dd", -1, "dd");
				uptime =bf + " 23:59:59";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
}
