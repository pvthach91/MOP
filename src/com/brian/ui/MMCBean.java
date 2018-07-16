package com.brian.ui;

import java.text.DecimalFormat;
import java.util.List;

import com.brian.item.CurScheduleItem;
import com.brian.item.MathBrian;
import com.brian.item.ModelItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.unit.CommonMethod;

/**
 * 秒秒彩
 * @author BRIAN
 *
 */
public class MMCBean {
	//private String issuecount = "1380";
	private String lotteryname="新生秒秒彩";
	//private String issueliststr="";
	//private String servertime = "";
	//private String lastopenissue = "";
	//private String curissue = "";
	private int lotteryid = 50;
	private String userprize = "";
	private CurScheduleItem cursitem = new CurScheduleItem();
	public MMCBean(){
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			return;
		}
		userprize = "";
		//参数
		double point = ui.getPointssc();
		int usermode = ui.getModel();
		userprize = makeprize(1,point,usermode);
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
}
