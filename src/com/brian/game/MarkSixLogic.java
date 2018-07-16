package com.brian.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;

import com.brian.item.BuyDetailItem;
import com.brian.item.BuyItem;
import com.brian.item.BuyTraceItem;
import com.brian.item.CurScheduleItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.PostDBData;

public class MarkSixLogic {
	//六合彩   2016-04-29
	@SuppressWarnings("unchecked")
	public static String markSixSave(List<SelectItem> params,UserItem user,String betcontent,String ip,int lotteryid) throws Exception{
		BuyItem buy=new BuyItem();
//		BuyItem:lottryid,issur,numbers,money,trace;
		buy.setLotteryid(lotteryid);
		StringBuilder contents = new StringBuilder();
		for(SelectItem si : params){
			if("betdata[YMZT][]".equals(si.getLabel())){
				contents.append("betdata[YMZT][]="+si.getValue().toString()+"&");  
			}else if("betdata[TMX]".equals(si.getLabel())){
				contents.append("betdata[TMX]="+si.getValue().toString()+"&");  
			}else if("betdata[TMD]".equals(si.getLabel())){
				contents.append("betdata[TMD]="+si.getValue().toString()+"&");  
			}else if("betdata[TMDAN]".equals(si.getLabel())){
				contents.append("betdata[TMDAN]="+si.getValue().toString()+"&");  
			}else if("betdata[TMS]".equals(si.getLabel())){
				contents.append("betdata[TMS]="+si.getValue().toString()+"&");  
			}else if("betdata[WSD]".equals(si.getLabel())){
				contents.append("betdata[WSD]="+si.getValue().toString()+"&");  
			}else if("betdata[WSX]".equals(si.getLabel())){
				contents.append("betdata[WSX]="+si.getValue().toString()+"&");  
			}else if("betdata[WSDAN]".equals(si.getLabel())){
				contents.append("betdata[WSDAN]="+si.getValue().toString()+"&");  
			}else if("betdata[WSS]".equals(si.getLabel())){
				contents.append("betdata[WSS]="+si.getValue().toString()+"&");  
			}else if("betdata[SB_H]".equals(si.getLabel())){
				contents.append("betdata[SB_H]="+si.getValue().toString()+"&");  
			}else if("betdata[SB_L]".equals(si.getLabel())){
				contents.append("betdata[SB_L]="+si.getValue().toString()+"&");  
			}else if("betdata[SB_LV]".equals(si.getLabel())){
				contents.append("betdata[SB_LV]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_H]".equals(si.getLabel())){
				contents.append("betdata[SX_H]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_J]".equals(si.getLabel())){
				contents.append("betdata[SX_J]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_G]".equals(si.getLabel())){
				contents.append("betdata[SX_G]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_Z]".equals(si.getLabel())){
				contents.append("betdata[SX_Z]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_S]".equals(si.getLabel())){
				contents.append("betdata[SX_S]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_N]".equals(si.getLabel())){
				contents.append("betdata[SX_N]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_HU]".equals(si.getLabel())){
				contents.append("betdata[SX_HU]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_T]".equals(si.getLabel())){
				contents.append("betdata[SX_T]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_L]".equals(si.getLabel())){
				contents.append("betdata[SX_L]="+si.getValue().toString()+"&");   
			}else if("betdata[SX_SHE]".equals(si.getLabel())){
				contents.append("betdata[SX_SHE]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_12]".equals(si.getLabel())){
				contents.append("betdata[hezhi_12]="+si.getValue().toString()+"&");   
			}else if("betdata[SX_MA]".equals(si.getLabel())){
				contents.append("betdata[SX_MA]="+si.getValue().toString()+"&");  
			}else if("betdata[SX_Y]".equals(si.getLabel())){
				contents.append("betdata[SX_Y]="+si.getValue().toString()+"&");  
			}else if("betdata[TMWS_0]".equals(si.getLabel())){
				contents.append("betdata[TMWS_0]="+si.getValue().toString()+"&");  
			}else if("betdata[TMWS_2]".equals(si.getLabel())){
				contents.append("betdata[TMWS_2]="+si.getValue().toString()+"&");  
			}else if("betdata[TMWS_3]".equals(si.getLabel())){
				contents.append("betdata[TMWS_3]="+si.getValue().toString()+"&");  
			}else if("betdata[TMWS_4]".equals(si.getLabel())){
				contents.append("betdata[TMWS_4]="+si.getValue().toString()+"&");  
			}else if("betdata[TMWS_5]".equals(si.getLabel())){
				contents.append("betdata[TMWS_5]="+si.getValue().toString()+"&");  
			}else if("betdata[TMWS_6]".equals(si.getLabel())){
				contents.append("betdata[TMWS_6]="+si.getValue().toString()+"&");   
			}else if("betdata[TMWS_7]".equals(si.getLabel())){
				contents.append("betdata[TMWS_7]="+si.getValue().toString()+"&");  
			}else if("betdata[TMWS_8]".equals(si.getLabel())){
				contents.append("betdata[TMWS_8]="+si.getValue().toString()+"&");  
			}else if("betdata[TMWS_9]".equals(si.getLabel())){
				contents.append("betdata[TMWS_9]="+si.getValue().toString()+"&");  
			}else if("betdata[TMWS_1]".equals(si.getLabel())){
				contents.append("betdata[TMWS_1]="+si.getValue().toString()+"&");   
			}else if("betdata[ZYMD]".equals(si.getLabel())){
				contents.append("betdata[ZYMD]="+si.getValue().toString()+"&");  
			}else if("betdata[ZYMX]".equals(si.getLabel())){
				contents.append("betdata[ZYMX]="+si.getValue().toString()+"&");  
			}else if("betdata[ZYMDAN]".equals(si.getLabel())){
				contents.append("betdata[ZYMDAN]="+si.getValue().toString()+"&");  
			}else if("betdata[ZYMS]".equals(si.getLabel())){
				contents.append("betdata[ZYMS]="+si.getValue().toString()+"&");  
			}else if("betdata[YMBDW][]".equals(si.getLabel())){
				contents.append("betdata[YMBDW][]="+si.getValue().toString()+"&");  
			}else if("total_money".equals(si.getLabel())){
				buy.setTotal_money(Double.parseDouble(si.getValue().toString()));
			}else if("trace_total_money".equals(si.getLabel())){
				buy.setTrace_money(Double.parseDouble(si.getValue().toString()));
			}else if("trace_if".equals(si.getLabel())){
				buy.setTrace_if(si.getValue().toString());  //1代表追号 
			}else if("total_nums".equals(si.getLabel())){
				buy.setTotal_nums(Integer.parseInt(si.getValue().toString()));  //注数
			}else if("issue_start".equals(si.getLabel())){
				buy.setIssue_start(si.getValue().toString());  //最小期
			}else if("trace_count_input".equals(si.getLabel())){
				buy.setTrace_count_input(Integer.parseInt(si.getValue().toString()));  //期的数目
			}else if("trace_stop".equals(si.getLabel())){
				buy.setTrace_stop(si.getValue().toString());  //追中即停yes no
			}else if("buyitem[]".equals(si.getLabel())){  //购彩具体
				String buyitemStr = si.getValue().toString();
				BuyDetailItem buyitem=new BuyDetailItem();
				Object bean = JSONObject.toBean(JSONObject.fromObject(buyitemStr));
				try {
					buyitem.setMoney(Double.parseDouble((PropertyUtils.getProperty(bean, "money")).toString()));
					buyitem.setNums(Integer.parseInt((PropertyUtils.getProperty(bean, "nums")).toString()));
					buyitem.setTimes(Integer.parseInt((PropertyUtils.getProperty(bean, "times")).toString()));
					buy.addDetailitem(buyitem);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				} 
			}else if("buytrace[]".equals(si.getLabel())){  //追号
				String buytraceStr = si.getValue().toString();
				BuyTraceItem buytrace=new BuyTraceItem();
				Object bean = JSONObject.toBean(JSONObject.fromObject(buytraceStr));
				try {
					buytrace.setIssue((PropertyUtils.getProperty(bean, "issue")).toString());
					buytrace.setTimes(Integer.parseInt((PropertyUtils.getProperty(bean, "times")).toString()));
					buy.addTraceItem(buytrace);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
		//判断是否投注已经过期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<CurScheduleItem> curList = InitData.curSchedule;
		for(CurScheduleItem curItem:curList){
			if(curItem.getLotteryid()==lotteryid){
				Long endtime = format.parse(curItem.getEndtime()).getTime();
				if(buy.getIssue_start().compareTo(curItem.getIssue())<0||new Date().getTime()>=endtime){
					String res = "{\"status\":\"error\",\"data\":\"投注期号已经过期！\"}";
					return res;
				}
			}
		}
		String cont = contents.length()>0?contents.substring(0,contents.length()-1):"";
		List<Object> olist = new ArrayList<Object>();
		olist.add("lhcbet");
		olist.add(buy);
		olist.add(user);
		olist.add(cont);
		olist.add(ip);
		try{	
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				String balance = rlist.get(1).toString();
				user.setBalance(balance);
				String res = "{\"status\":\"success\",\"data\":\""+balance+"\"}";	
				return res;
			}else{
				String ms = rlist.get(1).toString();
				String res = "{\"status\":\"error\",\"data\":\""+ms+"\"}";
//				String res = "{\"status\":\"error\",\"data\":\"温馨提示：直属总代不可投注。\"}";
				return res;
			}
			
			//return rlist.get(1).toString();
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
}
