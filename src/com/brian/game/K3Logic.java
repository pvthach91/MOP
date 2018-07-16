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

public class K3Logic {
	@SuppressWarnings("unchecked")
	public static String K3Save(List<SelectItem> params,UserItem user,String betcontent,String ip){
		List<Object> olist = new ArrayList<Object>();
		olist.add("k3bet");
		olist.add(params);
		olist.add(user);
		olist.add(betcontent);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				String res = sblist.get(0).toString();
				String balance = sblist.get(1).toString();
				user.setBalance(balance);
				return res;
			}else{
				String ms = rlist.get(1).toString();
				//String res = "{'stats':'error','data':'"+ms+"'}";
				return ms;
			}
			
			//return rlist.get(1).toString();
		}catch(Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	//快骰一分   2016-04-08
	@SuppressWarnings("unchecked")
	public static String K31fSave(List<SelectItem> params,UserItem user,String betcontent,String ip) throws Exception{
		BuyItem buy=new BuyItem();
		buy.setLotteryid(80);
		StringBuilder contents = new StringBuilder();
		for(SelectItem si : params){
			if("betdata[da]".equals(si.getLabel())){
				contents.append("betdata[da]="+si.getValue().toString()+"&");  
			}else if("betdata[duizi_66]".equals(si.getLabel())){
				contents.append("betdata[duizi_66]="+si.getValue().toString()+"&");  
			}else if("betdata[duizi_55]".equals(si.getLabel())){
				contents.append("betdata[duizi_55]="+si.getValue().toString()+"&");  
			}else if("betdata[duizi_44]".equals(si.getLabel())){
				contents.append("betdata[duizi_44]="+si.getValue().toString()+"&");  
			}else if("betdata[baozi_666]".equals(si.getLabel())){
				contents.append("betdata[baozi_666]="+si.getValue().toString()+"&");  
			}else if("betdata[baozi_555]".equals(si.getLabel())){
				contents.append("betdata[baozi_555]="+si.getValue().toString()+"&");  
			}else if("betdata[baozi_444]".equals(si.getLabel())){
				contents.append("betdata[baozi_444]="+si.getValue().toString()+"&");  
			}else if("betdata[baozi_333]".equals(si.getLabel())){
				contents.append("betdata[baozi_333]="+si.getValue().toString()+"&");  
			}else if("betdata[baozi_222]".equals(si.getLabel())){
				contents.append("betdata[baozi_222]="+si.getValue().toString()+"&");  
			}else if("betdata[baozi_111]".equals(si.getLabel())){
				contents.append("betdata[baozi_111]="+si.getValue().toString()+"&");  
			}else if("betdata[baozi_nnn]".equals(si.getLabel())){
				contents.append("betdata[baozi_nnn]="+si.getValue().toString()+"&");  
			}else if("betdata[duizi_33]".equals(si.getLabel())){
				contents.append("betdata[duizi_33]="+si.getValue().toString()+"&");  
			}else if("betdata[duizi_22]".equals(si.getLabel())){
				contents.append("betdata[duizi_22]="+si.getValue().toString()+"&");  
			}else if("betdata[duizi_11]".equals(si.getLabel())){
				contents.append("betdata[duizi_11]="+si.getValue().toString()+"&");  
			}else if("betdata[xiao]".equals(si.getLabel())){
				contents.append("betdata[xiao]="+si.getValue().toString()+"&");  
			}else if("betdata[shuang]".equals(si.getLabel())){
				contents.append("betdata[shuang]="+si.getValue().toString()+"&");  
			}else if("betdata[dan]".equals(si.getLabel())){
				contents.append("betdata[dan]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_17]".equals(si.getLabel())){
				contents.append("betdata[hezhi_17]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_16]".equals(si.getLabel())){
				contents.append("betdata[hezhi_16]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_15]".equals(si.getLabel())){
				contents.append("betdata[hezhi_15]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_14]".equals(si.getLabel())){
				contents.append("betdata[hezhi_14]="+si.getValue().toString()+"&");   
			}else if("betdata[hezhi_13]".equals(si.getLabel())){
				contents.append("betdata[hezhi_13]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_12]".equals(si.getLabel())){
				contents.append("betdata[hezhi_12]="+si.getValue().toString()+"&");   
			}else if("betdata[hezhi_11]".equals(si.getLabel())){
				contents.append("betdata[hezhi_11]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_10]".equals(si.getLabel())){
				contents.append("betdata[hezhi_10]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_9]".equals(si.getLabel())){
				contents.append("betdata[hezhi_9]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_8]".equals(si.getLabel())){
				contents.append("betdata[hezhi_8]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_7]".equals(si.getLabel())){
				contents.append("betdata[hezhi_7]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_6]".equals(si.getLabel())){
				contents.append("betdata[hezhi_6]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_5]".equals(si.getLabel())){
				contents.append("betdata[hezhi_5]="+si.getValue().toString()+"&");  
			}else if("betdata[hezhi_4]".equals(si.getLabel())){
				contents.append("betdata[hezhi_4]="+si.getValue().toString()+"&");   
			}else if("betdata[erma_25]".equals(si.getLabel())){
				contents.append("betdata[erma_25]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_56]".equals(si.getLabel())){
				contents.append("betdata[erma_56]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_46]".equals(si.getLabel())){
				contents.append("betdata[erma_46]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_45]".equals(si.getLabel())){
				contents.append("betdata[erma_45]="+si.getValue().toString()+"&");   
			}else if("betdata[erma_36]".equals(si.getLabel())){
				contents.append("betdata[erma_36]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_35]".equals(si.getLabel())){
				contents.append("betdata[erma_35]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_34]".equals(si.getLabel())){
				contents.append("betdata[erma_34]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_26]".equals(si.getLabel())){
				contents.append("betdata[erma_26]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_24]".equals(si.getLabel())){
				contents.append("betdata[erma_24]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_23]".equals(si.getLabel())){
				contents.append("betdata[erma_23]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_16]".equals(si.getLabel())){
				contents.append("betdata[erma_16]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_14]".equals(si.getLabel())){
				contents.append("betdata[erma_14]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_13]".equals(si.getLabel())){
				contents.append("betdata[erma_13]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_12]".equals(si.getLabel())){
				contents.append("betdata[erma_12]="+si.getValue().toString()+"&");  
			}else if("betdata[erma_15]".equals(si.getLabel())){
				contents.append("betdata[erma_15]="+si.getValue().toString()+"&");  
			}else if("betdata[yima_6]".equals(si.getLabel())){
				contents.append("betdata[yima_6]="+si.getValue().toString()+"&");  
			}else if("betdata[yima_5]".equals(si.getLabel())){
				contents.append("betdata[yima_5]="+si.getValue().toString()+"&");  
			}else if("betdata[yima_4]".equals(si.getLabel())){
				contents.append("betdata[yima_4]="+si.getValue().toString()+"&");  
			}else if("betdata[yima_3]".equals(si.getLabel())){
				contents.append("betdata[yima_3]="+si.getValue().toString()+"&");  
			}else if("betdata[yima_2]".equals(si.getLabel())){
				contents.append("betdata[yima_2]="+si.getValue().toString()+"&");  
			}else if("betdata[yima_1]".equals(si.getLabel())){
				contents.append("betdata[yima_1]="+si.getValue().toString()+"&");  
			}else if("trace_total_money".equals(si.getLabel())){
				buy.setTrace_money(Double.parseDouble(si.getValue().toString()));
			}else if("total_money".equals(si.getLabel())){
				buy.setTotal_money(Double.parseDouble(si.getValue().toString()));
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
			}else if("buyitem[]".equals(si.getLabel())){
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
			}else if("buytrace[]".equals(si.getLabel())){
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
		//判断投注期是否过期
		List<CurScheduleItem> curSch = InitData.getCurSchedule();
		String issue = buy.getIssue_start();
		for(CurScheduleItem item:curSch){
			if(80==item.getLotteryid()){
				if(issue.compareTo(item.getIssue())<0){
					return "{\"status\":\"error\",\"data\":\"投注期号已经过期！\"}";
				}
			}
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long now = new Date().getTime();
		for(CurScheduleItem item:curSch){
			if(80==item.getLotteryid()){
				if(issue.equals(item.getIssue())){
					if(now>format.parse(item.getEndtime()).getTime()){
						return "{\"status\":\"error\",\"data\":\"投注期号已经过期！\"}";
					}
				}
			}
		}
		String cont = contents.length()>0?contents.substring(0,contents.length()-1):"";
		List<Object> olist = new ArrayList<Object>();
		olist.add("ffk3bet");
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
				return res;
			}
			
			//return rlist.get(1).toString();
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
}
