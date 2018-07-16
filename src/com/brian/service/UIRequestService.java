package com.brian.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.brian.item.BuyDetailItem;
import com.brian.item.BuyItem;
import com.brian.item.BuyTraceItem;
import com.brian.item.CurScheduleItem;
import com.brian.item.FavoriteItemSon;
import com.brian.item.GameRecordItem;
import com.brian.item.LotteryInfoEnum;
import com.brian.item.MathBrian;
import com.brian.item.MessageItem;
import com.brian.item.MessageTipItem;
import com.brian.item.UserItem;
import com.brian.ui.LoginBean;
import com.brian.unit.CommonMethod;

public class UIRequestService {
	public synchronized static String save(List<SelectItem> _list,UserItem ui,String contents,String ip){
		BuyItem bitem = SubSave(_list);
		List<Object> olist = new ArrayList<Object>();
		olist.add("bet");
		olist.add(bitem);
		olist.add(ui);
		olist.add(contents);
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				String res = "success";
				return res;
			}else{
				String ms = (String)rlist.get(1);
				String res = "{'stats':'error','data':'"+ms+"'}";
				return res;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	public static BuyItem SubSave(List<SelectItem> list){
		BuyItem bi = new BuyItem();
		for(SelectItem si: list){
			if(si.getLabel().equals("lotteryid")){
				bi.setLotteryid(Integer.parseInt(si.getValue().toString()));
			}else if(si.getLabel().equals("lt_issue_start")){
				bi.setIssue_start(si.getValue().toString());
			}else if(si.getLabel().equals("lt_total_nums")){
				bi.setTotal_nums(Integer.parseInt(si.getValue().toString()));
			}else if(si.getLabel().equals("lt_total_money")){
				//
				bi.setTotal_money(MathBrian.round(Double.parseDouble(si.getValue().toString()), 4));
			}else if(si.getLabel().equals("lt_trace_if")){
				bi.setTrace_if(si.getValue().toString());
			}else if(si.getLabel().equals("lt_trace_stop")){
				bi.setTrace_stop(si.getValue().toString());
			}else if(si.getLabel().equals("lt_trace_count_input")){
				//追号期数
				bi.setTrace_count_input(Integer.parseInt(si.getValue().toString()));
			}else if(si.getLabel().equals("lt_trace_money")){
				//MathBrian.add(Double.parseDouble(si.getValue().toString()),0);
				bi.setTrace_money(MathBrian.round(Double.parseDouble(si.getValue().toString()), 4));
			}else if(si.getLabel().equals("lt_trace_issues[]")){
				//追号期号
				BuyTraceItem bti = new BuyTraceItem();
				bti.setIssue(si.getValue().toString());
				String bsstr = "lt_trace_times_"+si.getValue().toString();
				for(SelectItem subsi: list){
					if(subsi.getLabel().equals(bsstr)){
						bti.setTimes(Integer.parseInt(subsi.getValue().toString()));
						break;
					}
				}
				bi.addTraceItem(bti);
			}else if(si.getLabel().equals("lt_project[]")){
				//投注资料
				String jsons = si.getValue().toString();
				JSONObject jsonObject = JSONObject.fromObject(jsons);
				BuyDetailItem bdi = (BuyDetailItem)JSONObject.toBean(jsonObject,BuyDetailItem.class);
				bi.addDetailitem(bdi);
			}
		}
		return bi;
	}
	/**
	 * 秒秒彩最近开奖记录
	 * @param _list
	 * @param ui
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String mmcWNum(List<SelectItem> _list, UserItem ui){
		//ui.getLoginname();
		List<Object> olist = new ArrayList<Object>();
		olist.add("MBetWinN");
		olist.add(ui.getLoginname());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				List<GameRecordItem> restlist = (List<GameRecordItem>)sblist.get(0);
				List<String> temp = new ArrayList<String>();
				for(GameRecordItem gri:restlist){
					boolean b = false;
					for(String s:temp){
						if(s==null)continue;
						if(s.equals(gri.getWinnumber())){
							b=true;
						}
					}
					if(!b){
						temp.add(gri.getWinnumber());
						if(temp.size()==5)break;
					}
				}
				String res="[{'winnumber':''}]";
				if(temp.size()>0){
					res="[";
					for(String s:temp){
						if(s==null){
							continue;
						}
						if(s.length()==5){
							s = s.subSequence(0, 1)+","+s.subSequence(1, 2)+","+s.subSequence(2, 3)+","+s.subSequence(3, 4)+","+s.subSequence(4, 5);
						}
						res+="{'winnumber':'"+s+"'},";
					}
					res = res.substring(0, res.length()-1)+"]";
					return res;
				}else{
					return res;
				}
			}else{
				return "[{'winnumber':'网络问题请刷新页面'}]";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return "[{'winnumber':'"+ex.getMessage()+"'}]";
		}
	}
	public static String betlist(List<SelectItem> _list, UserItem ui){
		String res = "[{'iscancel':'1','projectid':'B140208220616100401','writetime':'2014-02-09 00:23:10','methodname':'五星直选',"
				+ "'issue':'20140209-005','code':'3,4,5','multiple':'2','modes':'元','totalprice':'2304.00','bonus':'0','statusdesc':'未中奖'},"
				+ "{'iscancel':'10','projectid':'B140208220616100402','writetime':'2014-02-09 00:23:20','methodname':'五星直选',"
				+ "'issue':'20140209-005','code':'3,5','multiple':'1','modes':'分','totalprice':'234.00','bonus':'0','statusdesc':'未中奖'}]";
		//System.out.println(res);
		return res;
	}
	public static String refBalance(List<SelectItem> _list,UserItem ui,HttpServletRequest request){
		List<Object> olist = new ArrayList<Object>();
		olist.add("balance");
		olist.add(ui.getLoginname());
		olist.add(ui.getFatherid());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				String b = (String)rlist.get(1);
				LoginBean lb = (LoginBean)request.getSession().getAttribute("LoginBean");
				//Double balance = Double.parseDouble(b);
				if(lb==null){
					HttpSession session= request.getSession(true);
					session.invalidate();
					return "0";
				}
				lb.getUser().setBalance(b);
				return b;
			}else{
				//String ms = (String)rlist.get(1);
				//return ms;
				return "请重试";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			//return ex.getMessage();
			return "请重试";
		}
	}
	
	
	public static String refBalanceNewWithNotice(List<SelectItem> _list,UserItem ui,HttpServletRequest request){
		if(!StringUtils.isEmpty(ui.getLoginname())&&ui.getLoginname().length()>4&&ui.getLoginname().length()<50){
			List<Object> olist = new ArrayList<Object>();
			olist.add("BalanceNewWithNotice");
			olist.add(ui.getLoginname());
			try{
				List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
 				String flag = rlist.get(0).toString();
				if("0".equals(flag)){
					//消息处理
					List<Object> list01 = (ArrayList<Object>)rlist.get(1);
					String balance = "0";
					String content = "";
					String content01 = "";
					if(list01!=null&&list01.size()>0){
						balance = list01.get(0).toString();
						if(list01.size()>1){
							List<MessageItem> list02 = (ArrayList<MessageItem>)list01.get(1);
							if(list02!=null&&list02.size()>0){
								for(int i=0;i<list02.size();i++){
									content+=(i+1)+"."+((MessageItem)list02.get(i)).getContent()+"<br/>";
								}
							}
							List<MessageTipItem> list03 = (ArrayList<MessageTipItem>)list01.get(2);
							if(list03!=null&&list03.size()>0){
								for(int i=0;i<list03.size();i++){
									content01+=(i+1)+"."+((MessageTipItem)list03.get(i)).getContent()+"<br/>";
								}
							}
							
						}
					}
					LoginBean lb = (LoginBean)request.getSession().getAttribute("LoginBean");
					if(lb==null){
						HttpSession session= request.getSession(true);
						session.invalidate();
					}
					lb.getUser().setBalance(balance);
					
//					推送消息
					return balance+"-"+content+"-"+content01;
				}else{
					return rlist.get(1).toString();
				}
			}catch(Exception ex){
				ex.printStackTrace();
				//return ex.getMessage();
				return "请重试";
			}
		}else{
			return "用户名长度应该大于5小于50";
		}
		
	}
	
	
	public static String read(List<SelectItem> _list){
		//返回类似
		//{issue:'131213109',nowtime:'2013-12-13 22:59:14',opentime:'2013-12-13 23:05:30',saleend:'2013-12-13 23:04:10',sale:'120',left:'120'}
		int lotteryid = -1;
		for(SelectItem si:_list){
			if("lotteryid".equals(si.getLabel())){
				lotteryid = Integer.parseInt(si.getValue().toString());
			}
		}
		if(lotteryid==-1){
			return "";
		}
		CurScheduleItem csi = InitData.getCurScheduleItem(lotteryid);
		String nowtime = CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss");
		String res = "{issue:'"+csi.getIssue()+"',nowtime:'"+nowtime+"',opentime:'"+csi.getOpentime()+
				"',saleend:'"+csi.getEndtime()+"',sale:'"+csi.getSale()+"',left:'"+csi.getLeft()+"'}";
		return res;
	}
	public static String gethistory(List<SelectItem> _list){
		//开奖结果，lotteryid，issue
		//返回类似：{"code":["8","0","4","3","6"],"issue":"131213108","statuscode":"2"}
		int lotteryid = -1;
		String issue = null;
		for(SelectItem si:_list){
			if("lotteryid".equals(si.getLabel())){
				lotteryid = Integer.parseInt(si.getValue().toString());
			}else if("issue".equals(si.getLabel())){
				issue = si.getValue().toString();
			}
		}
		if(issue==null||lotteryid==-1){
			return "";
		}
		CurScheduleItem csi = InitData.getHisScheduleItem(lotteryid, issue);
		if(csi==null || csi.getStatus()<2){
			return "";
		}
		String wn = csi.getWinnumber();
		if(wn==null || wn.length()<1){
			return "";
		}else if(wn.length()==3){
			//解决时时乐开彩后面添0的BUG。
			//wn = "xx"+wn;
		}
		String tmp = "";
		if(wn.length()==10){
			tmp="'"+wn.substring(0, 2)+"','"+wn.substring(2, 4)+"','"+wn.substring(4, 6)+"','"+wn.substring(6, 8)+"','"+wn.substring(8, 10)+"'";
		}else{
			if(lotteryid==81||lotteryid==82){
				tmp = wn;
				String res01 = "{\"code\":\""+tmp+"\",\"issue\":\""+csi.getHistoryissue()+"\",\"statuscode\":\"2\",\"opentime\":\""+csi.getOpentime()+"\"}";
				return res01;
			}else{
				for(int i=1;i<=wn.length();i++){
					tmp += "'"+wn.substring(i-1, i)+"',";
				}
				tmp = tmp.substring(0, tmp.length()-1);
			}
		}
		String res = "{'code':["+tmp+"],'issue':'"+csi.getHistoryissue()+"','statuscode':'2'}";
		return res;
	}
	/**
	 * 页面倒计时时间和服务器同步
	 * @param _list
	 * @return
	 */
	public static String gettime(List<SelectItem> _list){
		int lotteryid = -1;
		String issue = null;
		for(SelectItem si:_list){
			if("lotteryid".equals(si.getLabel())){
				lotteryid = Integer.parseInt(si.getValue().toString());
			}else if("issue".equals(si.getLabel())){
				issue = si.getValue().toString();
			}
		}
		if(issue==null){
			return "-1";
		}
		CurScheduleItem csi = InitData.getCurScheduleItem(lotteryid, issue);
		if(csi==null){
			return "-1";
		}
		String nowtime = CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss");
		String etime = csi.getEndtime();
		
		String res = "-1";
		try{
			int n = CommonMethod.getTimeDiffe(nowtime, etime, "yyyy-MM-dd HH:mm:ss", "ss");
			return n+"";
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return res;
	}
	public static void main(String[] args)throws Exception{
		long l = 1387860713306l;
		Date d = new Date();
		d.setTime(l);
		String bettime = CommonMethod.strToDate(d, "yyyy-MM-dd HH:mm:ss");
		System.out.print(bettime);
		/**
		String wn="0106080911";
		String tmp="";
		if(wn.length()==10){
			tmp="'"+wn.substring(0, 2)+"','"+wn.substring(2, 4)+"','"+wn.substring(4, 6)+"','"+wn.substring(6, 8)+"','"+wn.substring(8, 10)+"'";
		}
		System.out.println(tmp);
		*/
		/**
		String tmp = "";
		String wn="123";
		for(int i=1;i<=wn.length();i++){
			tmp += "'"+wn.substring(i-1, i)+"',";
		}
		tmp = tmp.substring(0, tmp.length()-1);
		System.out.print(tmp);
		*/
		/**
		String nowtime = CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss");
		String etime = "2013-12-14 20:40:40";
		int n = CommonMethod.getTimeDiffe(nowtime, etime, "yyyy-MM-dd HH:mm:ss", "ss");
		System.out.println(n);
		*/
	}
	public static String addFavorite(UserItem ui,String ip,String lotteryid,LoginBean lb) {
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAddFavorite");
		olist.add(ui.getLoginname());
		olist.add(lotteryid);
		// 彩种名字
		olist.add(LotteryInfoEnum.Lottery_Info.getOutername(lotteryid));
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				String res = "[{\"code\":\"success\"}]";
				// 取loginBean
				FavoriteItemSon son = new FavoriteItemSon();
				son.setLotteryname(LotteryInfoEnum.Lottery_Info.getOutername(lotteryid));
				son.setUrl(LotteryInfoEnum.Lottery_ID_URL.getOutername(lotteryid));
				son.setLotteryid(Integer.parseInt(lotteryid));
				if(lb != null){
					if(lb.getSonList().size() >= 5){
						lb.getSonList().remove(0);
					}
					lb.getSonList().add(son);
					lb.getFavoriteLottIdList().add(lotteryid);
				}
				return res;
			}else{
				String ms = rlist.get(1).toString();
				StringBuffer res = new StringBuffer();
				res.append("[{\"code\":\"abc\"},{\"ms\":");
				res.append("\""+ ms + "\"}]");
				return res.toString();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	public static String dellFavorite(UserItem ui, String ip, String lotteryid,
			LoginBean lb) {
		List<Object> olist = new ArrayList<Object>();
		olist.add("UDellFavorite");
		olist.add(ui.getLoginname());
		olist.add(lotteryid);
		// 彩种名字
/*		olist.add(LotteryInfoEnum.Lottery_Info.getOutername(lotteryid));
		olist.add(ip);*/
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				String res = "[{\"code\":\"success\"}]";
				// 取loginBean
				FavoriteItemSon son = new FavoriteItemSon();
				son.setLotteryname(LotteryInfoEnum.Lottery_Info.getOutername(lotteryid));
				son.setUrl(LotteryInfoEnum.Lottery_ID_URL.getOutername(lotteryid));
				son.setLotteryid(Integer.parseInt(lotteryid));
				if(lb != null){
					lb.getSonList().remove(son);
					lb.getFavoriteLottIdList().remove(lotteryid);
				}
				return res;
			}else{
				String ms = rlist.get(1).toString();
				StringBuffer res = new StringBuffer();
				res.append("[{\"code\":\"abc\"},{\"ms\":");
				res.append("\""+ ms + "\"}]");
				return res.toString();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
}
