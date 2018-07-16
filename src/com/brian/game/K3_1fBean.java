package com.brian.game;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.brian.game.k3.K3history;
import com.brian.item.CurScheduleItem;
import com.brian.item.GameRecordItem;
import com.brian.item.MathBrian;
import com.brian.item.ModelItem;
import com.brian.item.ScheduleItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class K3_1fBean {
	private int lotteryid=80;
	private String issuecount = "1380";
	private String lotteryname="一分快骰";
	private JSONArray issueliststr;
	private String servertime = "";
	private JSONObject lastopenissue;
	private JSONObject curissue;
	private String userprize = "";
	private JSONArray k31fmethod;
	private JSONArray history;
	private double maxbetmoney = 100000;   //最高投注金额10W
	private double bonusgroup = 0.0;
	private String[] chips={"1.00", "2.00", "5.00", "20.00","50.00","100.00", "200.00", "500.00", "2000.00","5000.00","1000.00", "2000.00", "5000.00", "10000.00","20000.00"};
	private CurScheduleItem cursitem = new CurScheduleItem();
	private String userbalance;
	@SuppressWarnings("unchecked")
	public void init(UserItem ui)throws Exception{
		if(ui==null){
			return;
		}
		servertime = CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss");
		
		cursitem = InitData.getCurScheduleItem(lotteryid);
		//{issue:'131211042',endtime:'2013-12-11 12:59:00',opentime:'2013-12-11 13:00:45'};
		String curissuestr = "{\"issue\":\""+cursitem.getIssue()+"\",\"endtime\":\""+cursitem.getEndtime()+"\",\"opentime\":\""+cursitem.getOpentime()+"\"}";
		curissue = JSONObject.fromObject(curissuestr);
		//{issue:'131211041',endtime:'2013-12-11 12:50:45',opentime:'2013-12-11 12:50:45',statuscode:'2'}
		String et = cursitem.getHisendtime();
		String ot = cursitem.getHisopentime();
		if(et!=null&&ot!=null){
			if(servertime.compareTo(et)>0 && servertime.compareTo(ot)<0){
				et=servertime;
			}else if(servertime.compareTo(ot)>0){
				et = ot;
			}
			String lastopenissuestr = "{\"issue\":\""+cursitem.getHistoryissue()+"\",\"endtime\":\""
					+et+"\",\"opentime\":\""+cursitem.getHisopentime()+"\",\"statuscode\":\""+cursitem.getStatus()+"\"}";
			lastopenissue = JSONObject.fromObject(lastopenissuestr);
		}
		//取两天的记录
		String issueliststr01 = "";
		StringBuffer sb = new StringBuffer("[");
		List<ScheduleItem> silist = InitData.schedule;
		String ttemp = cursitem.getHisendtime();
		int num = 0;
		for(ScheduleItem si:silist){
			if(si.getLotteryid()==lotteryid && si.getEndtime().compareTo(ttemp)>=0){
				num++;
				if(num>200){
					break;
				}
				sb.append("{\"issue\":\""+si.getIssue()+"\",\"opentime\":\""+si.getOpentime()+"\",\"endtime\":\""+si.getEndtime()+"\"},");
			}
		}
		issueliststr01 = sb.toString();
		issueliststr01 = issueliststr01.substring(0, issueliststr01.length()-1);
		issueliststr01 = issueliststr01+"]";
		issueliststr = JSONArray.fromObject(issueliststr01);
		
		
		String k31fmethodstr = InitData.k31fmethod;
		k31fmethod = JSONArray.fromObject(k31fmethodstr);
		//投注历史
		List<Object> olist = new ArrayList<Object>();
		olist.add("MBetSearch");
		olist.add(1); //curpage
		olist.add(20);     //pagesize
		olist.add(lotteryid);
		olist.add(ui.getLoginname());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				List<GameRecordItem> reslist = (List<GameRecordItem>)sblist.get(0);
				List<K3history> stmp = new ArrayList<K3history>();
				for(GameRecordItem gr:reslist){
					K3history k = new K3history();
					k.setDetail(gr.getWinnumber());
					stmp.add(k);
				}
				history = JSONArray.fromObject(stmp.toArray());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex; 
		}
		//取账号总余额
		List<Object> olist01 = new ArrayList<Object>();
		olist01.add("BalanceNew");
		olist01.add(ui.getLoginname());
		
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist01, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> list01 = (ArrayList<Object>)rlist.get(1);
				if(list01!=null&&list01.size()>0){
					userbalance = list01.get(0).toString();
				}
			}else{
				userbalance = "0.0";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex; 
		}
		userprize = "";
		//参数
//		double point = ui.getPointdp();
//		int usermode = ui.getModel();
//		userprize = makeprize(70,point,usermode);
	}
	public CurScheduleItem getCurIssure(int lotteryid)throws Exception{
		CurScheduleItem cur = new CurScheduleItem();
		cur = InitData.getCurScheduleItem(lotteryid);
		return cur;
	}
//	public String makeprize(int lotid,double point,int usermode){
//		List<ModelItem> tempmodel = InitData.model;
//		StringBuffer sb2 = new StringBuffer("[");
//		for(ModelItem mi : tempmodel){
//			if(!(lotid+"").equals(mi.getLotteryid())){
//				continue;
//			}
//			if(!(mi.getModel()==usermode)){
//				continue;
//			}
//			sb2.append("{\"methodid\":"+mi.getMethodid()+",\"prize\":{");
//			int n = mi.getLevelnum();
//			String tmp = "";
//			String dyprize="";
//			for(int i=1;i<=n;i++){
//				if(i==1){
//					tmp += i+":\""+mi.getLevel1()+"\",";
//					double d = mi.getLevel1();
//					double max = mi.getMaxvalue1();
//					double r = MathBrian.mul(max, point);
//					r = MathBrian.add(d, r);
//					DecimalFormat df = new DecimalFormat("#.00");
//					dyprize+="{\"level\":"+i+",\"prize\":[{\"point\":0,\"prize\":"+df.format(r)+"},{\"point\":\""+point+"\",\"prize\":"+d+"}]},";
//				}else if(i==2){
//					tmp += i+":\""+mi.getLevel2()+"\",";
//					
//					double d = mi.getLevel2();
//					double max = mi.getMaxvalue2();
//					double r = MathBrian.mul(max, point);
//					r = MathBrian.add(d, r);
//					DecimalFormat df = new DecimalFormat("#.00");
//					dyprize+="{\"level\":"+i+",\"prize\":[{\"point\":\""+point+"\",\"prize\":"+d+"},{\"point\":0,\"prize\":"+df.format(r)+"}]},";
//				}else if(i==3){
//					tmp += i+":\""+mi.getLevel3()+"\",";
//					
//					double d = mi.getLevel3();
//					double max = mi.getMaxvalue3();
//					double r = MathBrian.mul(max, point);
//					r = MathBrian.add(d, r);
//					DecimalFormat df = new DecimalFormat("#.00");
//					dyprize+="{\"level\":"+i+",\"prize\":[{\"point\":\""+point+"\",\"prize\":"+d+"},{\"point\":0,\"prize\":"+df.format(r)+"}]},";
//				}else if(i==4){
//					tmp += i+":\""+mi.getLevel4()+"\",";
//					
//					double d = mi.getLevel4();
//					double max = mi.getMaxvalue4();
//					double r = MathBrian.mul(max, point);
//					r = MathBrian.add(d, r);
//					DecimalFormat df = new DecimalFormat("#.00");
//					dyprize+="{\"level\":"+i+",\"prize\":[{\"point\":\""+point+"\",\"prize\":"+d+"},{\"point\":0,\"prize\":"+df.format(r)+"}]},";
//				}else if(i==5){
//					tmp += i+":\""+mi.getLevel5()+"\",";
//					
//					double d = mi.getLevel5();
//					double max = mi.getMaxvalue5();
//					double r = MathBrian.mul(max, point);
//					r = MathBrian.add(d, r);
//					DecimalFormat df = new DecimalFormat("#.00");
//					dyprize+="{\"level\":"+i+",\"prize\":[{\"point\":\""+point+"\",\"prize\":"+d+"},{\"point\":0,\"prize\":"+df.format(r)+"}]},";
//				}else if(i==6){
//					tmp += i+":\""+mi.getLevel6()+"\",";
//					
//					double d = mi.getLevel6();
//					double max = mi.getMaxvalue6();
//					double r = MathBrian.mul(max, point);
//					r = MathBrian.add(d, r);
//					DecimalFormat df = new DecimalFormat("#.00");
//					dyprize+="{\"level\":"+i+",\"prize\":[{\"point\":\""+point+"\",\"prize\":"+d+"},{\"point\":0,\"prize\":"+df.format(r)+"}]},";
//				}
//			}
//			if(tmp.length()==0){
//				return null;
//			}
//			tmp = tmp.substring(0, tmp.length()-1)+"},";
//			dyprize = dyprize.substring(0,dyprize.length()-1);
//			sb2.append(tmp);
//			sb2.append("\"dyprize\":[");
//			sb2.append(dyprize+"]},");
//		}
//		String j = sb2.toString();
//		j = j.substring(0, j.length()-1)+"]";
//		return j;
//	}
	public int getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(int lotteryid) {
		this.lotteryid = lotteryid;
	}


	public String[] getChips() {
		return chips;
	}
	public void setChips(String[] chips) {
		this.chips = chips;
	}
	public String getIssuecount() {
		return issuecount;
	}
	public void setIssuecount(String issuecount) {
		this.issuecount = issuecount;
	}
	public String getLotteryname() {
		return lotteryname;
	}
	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}

	public String getServertime() {
		return servertime;
	}
	public void setServertime(String servertime) {
		this.servertime = servertime;
	}

	public String getUserprize() {
		return userprize;
	}
	public void setUserprize(String userprize) {
		this.userprize = userprize;
	}

	public JSONArray getIssueliststr() {
		return issueliststr;
	}
	public void setIssueliststr(JSONArray issueliststr) {
		this.issueliststr = issueliststr;
	}
	public JSONObject getLastopenissue() {
		return lastopenissue;
	}
	public void setLastopenissue(JSONObject lastopenissue) {
		this.lastopenissue = lastopenissue;
	}
	public JSONObject getCurissue() {
		return curissue;
	}
	public void setCurissue(JSONObject curissue) {
		this.curissue = curissue;
	}
	public JSONArray getK31fmethod() {
		return k31fmethod;
	}
	public void setK31fmethod(JSONArray k31fmethod) {
		this.k31fmethod = k31fmethod;
	}
	public JSONArray getHistory() {
		return history;
	}
	public void setHistory(JSONArray history) {
		this.history = history;
	}
	public double getMaxbetmoney() {
		return maxbetmoney;
	}
	public void setMaxbetmoney(double maxbetmoney) {
		this.maxbetmoney = maxbetmoney;
	}
	public double getBonusgroup() {
		return bonusgroup;
	}
	public void setBonusgroup(double bonusgroup) {
		this.bonusgroup = bonusgroup;
	}
	public String getUserbalance() {
		return userbalance;
	}
	public void setUserbalance(String userbalance) {
		this.userbalance = userbalance;
	}
	public CurScheduleItem getCursitem() {
		return cursitem;
	}
	public void setCursitem(CurScheduleItem cursitem) {
		this.cursitem = cursitem;
	}
	
}
