package com.brian.ui;

import java.text.DecimalFormat;
import java.util.List;

import com.brian.item.CurScheduleItem;
import com.brian.item.MathBrian;
import com.brian.item.ModelItem;
import com.brian.item.ScheduleItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.LogXY;
import com.brian.unit.CommonMethod;

/**
 * 上海时时乐
 * @author Brian
 * 2013-12-22
 */
public class ShSSLBean extends LogXY{
	private String issuecount = "23";
	private String lotteryname="上海";
	private String issueliststr="";
	private String servertime = "";
	private String lastopenissue = "";
	private String curissue = "";
	private int lotteryid=4;
	private String userprize = "";
	private CurScheduleItem cursitem = new CurScheduleItem();
	public ShSSLBean(){
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			return;
		}
		servertime = CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss");
		
		cursitem = InitData.getCurScheduleItem(lotteryid);
		//{issue:'131211042',endtime:'2013-12-11 12:59:00',opentime:'2013-12-11 13:00:45'};
		curissue = "{issue:'"+cursitem.getIssue()+"',endtime:'"+cursitem.getEndtime()+"',opentime:'"+cursitem.getOpentime()+"'}";
		//{issue:'131211041',endtime:'2013-12-11 12:50:45',opentime:'2013-12-11 12:50:45',statuscode:'2'}
		String et = cursitem.getHisendtime();
		String ot = cursitem.getHisopentime();
		if(servertime.compareTo(et)>0 && servertime.compareTo(ot)<0){
			et=servertime;
		}else if(servertime.compareTo(ot)>0){
			et = ot;
		}
		lastopenissue = "{issue:'"+cursitem.getHistoryissue()+"',endtime:'"
				+et+"',opentime:'"+cursitem.getHisopentime()+"',statuscode:'"+cursitem.getStatus()+"'}";
		
		//取两天的记录
		issueliststr = "";
		StringBuffer sb = new StringBuffer("[");
		List<ScheduleItem> silist = InitData.schedule;
		String ttemp = cursitem.getHisendtime();
		for(ScheduleItem si:silist){
			if(si.getLotteryid()==lotteryid && si.getEndtime().compareTo(ttemp)>=0){
				sb.append("{issue:'"+si.getIssue()+"',endtime:'"+si.getEndtime()+"'},");
			}
		}
		issueliststr = sb.toString();
		issueliststr = issueliststr.substring(0, issueliststr.length()-1);
		issueliststr = issueliststr+"]";
		
		userprize = "";
		//参数
		double point = ui.getPointssc();
		int usermode = ui.getModel();
		userprize = makeprize(lotteryid,point,usermode);
	}
	public String makeprize(int lotid,double point,int usermode){
		List<ModelItem> tempmodel = InitData.model;
		StringBuffer sb2 = new StringBuffer("[");
		for(ModelItem mi : tempmodel){
			if(!(lotid+"").equals(mi.getLotteryid())){
				continue;
			}
			if(!(mi.getModel()==usermode)){
				continue;
			}
			sb2.append("{methodid:"+mi.getMethodid()+",prize:{");
			int n = mi.getLevelnum();
			String tmp = "";
			String dyprize="";
			for(int i=1;i<=n;i++){
				if(i==1){
					tmp += i+":'"+mi.getLevel1()+"',";
					double d = mi.getLevel1();
					double max = mi.getMaxvalue1();
					double r = MathBrian.mul(max, point);
					r = MathBrian.add(d, r);
					DecimalFormat df = new DecimalFormat("#.00");
					dyprize+="{'level':"+i+",'prize':[{'point':0,'prize':"+df.format(r)+"},{'point':'"+point+"','prize':"+d+"}]},";
				}else if(i==2){
					tmp += i+":'"+mi.getLevel2()+"',";
					
					double d = mi.getLevel2();
					double max = mi.getMaxvalue2();
					double r = MathBrian.mul(max, point);
					r = MathBrian.add(d, r);
					DecimalFormat df = new DecimalFormat("#.00");
					dyprize+="{'level':"+i+",'prize':[{'point':'"+point+"','prize':"+d+"},{'point':0,'prize':"+df.format(r)+"}]},";
				}else if(i==3){
					tmp += i+":'"+mi.getLevel3()+"',";
					
					double d = mi.getLevel3();
					double max = mi.getMaxvalue3();
					double r = MathBrian.mul(max, point);
					r = MathBrian.add(d, r);
					DecimalFormat df = new DecimalFormat("#.00");
					dyprize+="{'level':"+i+",'prize':[{'point':'"+point+"','prize':"+d+"},{'point':0,'prize':"+df.format(r)+"}]},";
				}else if(i==4){
					tmp += i+":'"+mi.getLevel4()+"',";
					
					double d = mi.getLevel4();
					double max = mi.getMaxvalue4();
					double r = MathBrian.mul(max, point);
					r = MathBrian.add(d, r);
					DecimalFormat df = new DecimalFormat("#.00");
					dyprize+="{'level':"+i+",'prize':[{'point':'"+point+"','prize':"+d+"},{'point':0,'prize':"+df.format(r)+"}]},";
				}else if(i==5){
					tmp += i+":'"+mi.getLevel5()+"',";
					
					double d = mi.getLevel5();
					double max = mi.getMaxvalue5();
					double r = MathBrian.mul(max, point);
					r = MathBrian.add(d, r);
					DecimalFormat df = new DecimalFormat("#.00");
					dyprize+="{'level':"+i+",'prize':[{'point':'"+point+"','prize':"+d+"},{'point':0,'prize':"+df.format(r)+"}]},";
				}else if(i==6){
					tmp += i+":'"+mi.getLevel6()+"',";
					
					double d = mi.getLevel6();
					double max = mi.getMaxvalue6();
					double r = MathBrian.mul(max, point);
					r = MathBrian.add(d, r);
					DecimalFormat df = new DecimalFormat("#.00");
					dyprize+="{'level':"+i+",'prize':[{'point':'"+point+"','prize':"+d+"},{'point':0,'prize':"+df.format(r)+"}]},";
				}
			}
			if(tmp.length()==0){
				return null;
			}
			tmp = tmp.substring(0, tmp.length()-1)+"},";
			dyprize = dyprize.substring(0,dyprize.length()-1);
			sb2.append(tmp);
			sb2.append("dyprize:[");
			sb2.append(dyprize+"]},");
		}
		String j = sb2.toString();
		j = j.substring(0, j.length()-1)+"]";
		return j;
	}
	public String getIssueliststr() {
		return issueliststr;
	}

	public void setIssueliststr(String issueliststr) {
		this.issueliststr = issueliststr;
	}

	public String getServertime() {
		return servertime;
	}

	public void setServertime(String servertime) {
		this.servertime = servertime;
	}

	public String getLastopenissue() {
		return lastopenissue;
	}

	public void setLastopenissue(String lastopenissue) {
		this.lastopenissue = lastopenissue;
	}

	public String getCurissue() {
		return curissue;
	}

	public void setCurissue(String curissue) {
		this.curissue = curissue;
	}

	public int getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(int lotteryid) {
		this.lotteryid = lotteryid;
	}

	public CurScheduleItem getCursitem() {
		return cursitem;
	}

	public void setCursitem(CurScheduleItem cursitem) {
		this.cursitem = cursitem;
	}

	public String getUserprize() {
		return userprize;
	}

	public void setUserprize(String userprize) {
		this.userprize = userprize;
	}
	public String getLotteryname() {
		return lotteryname;
	}
	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}
	public String getIssuecount() {
		return issuecount;
	}
	public void setIssuecount(String issuecount) {
		this.issuecount = issuecount;
	}
}
