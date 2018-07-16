package com.brian.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.brian.game.K3Logic;
import com.brian.game.K3_1fBean;
import com.brian.item.CurScheduleItem;
import com.brian.item.GameRecordItemSpecial;
import com.brian.item.UserItem;
import com.brian.item.YLFXItem;
import com.brian.ui.UINewBetBean;
import com.brian.uipage.BetRecordBean;
import com.brian.uipage.GameRecordBean;
import com.brian.unit.CommonMethod;
import com.brian.unit.GameUtils;

/**
 * 快骰一分   2016-04-08
 * @author 32RSC-V3800
 *
 */
public class K31fGameService extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Logger logs = Logger.getLogger(K31fGameService.class); 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		this.doPost(req, res);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream in = request.getInputStream();
		String sourcecode = CommonMethod.inputStream2String(in);
		String strdata = sourcecode;
		String result = "";
		try{
			UserItem ui = (UserItem)request.getSession().getAttribute("USERINFO");
			
			String ip = CommonMethod.getIpAddress(request);
			if(ui==null || ui.getLoginname()==null){
				ip = request.getRemoteAddr();
				logs.error("提交注单,未登录,IP："+ip);
				result = "用户请重新登录";
			}else{
				
				//1、登录判断k
				//2、判断格式是否合法
				//3、判断是追号还是投注
				//4、投注的期号是否过期，追号是否过期
				//String[] params = strdata.split("&");
				List<SelectItem> list = CommonMethod.parseParam(sourcecode);
				if(list==null){
					return;
				}
				String flag = null;
				for(SelectItem si : list){
					if("flag".equals(si.getLabel())){
						flag = si.getValue().toString();
						break;
					}
				}
				if("init".equals(flag)){
					//初始化
					K3_1fBean k3_1fBean = new K3_1fBean();
					String[] chips = (String[])request.getSession().getAttribute("FFK3_CHIPS");
					if(chips!=null&&chips.length>0){
						k3_1fBean.setChips(chips);
					}else{
						request.getSession().setAttribute("FFK3_CHIPS", k3_1fBean.getChips());
					}
					k3_1fBean.init(ui);
					
					JSONObject jo = JSONObject.fromObject(k3_1fBean);
					result =  toFormatResult("success",jo.toString(),false);
				}else if("bet".equals(flag)){
					result = K3Logic.K31fSave(list,ui,strdata,ip);
				}else if("getCurIssure".equals(flag)){
					K3_1fBean k3_1fBean = new K3_1fBean();
					int sid = 80;
					for(SelectItem si : list){
						if("lotteryid".equals(si.getLabel())){
							sid = Integer.parseInt(si.getValue().toString());
							break;
						}
					}
					CurScheduleItem cur = k3_1fBean.getCurIssure(sid);
					String msg = "{\"issue\":\""+cur.getIssue()+"\",\"endtime\":\""+cur.getEndtime()+"\",\"opentime\":\""+cur.getOpentime()+"\",\"servertime\":\""+(CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss"))+"\"}";
					result =  toFormatResult("success", msg,false);
				}else if("getopencode".equals(flag)){
					result =  toFormatResult("success", UIRequestService.gethistory(list),false);
				}else if("getopencodehistory".equals(flag)){
					String sid = "";
					String num = "";
					for(SelectItem si : list){
						if("sid".equals(si.getLabel())){
							sid = si.getValue().toString();
						}else if("num".equals(si.getLabel())){
							num = si.getValue().toString();
						}
					}
					List<YLFXItem> historys = GameUtils.getHistory(sid, num);
					if(historys!=null){
						result =  toFormatResult("success", JSONArray.fromObject(historys).toString(),false);
					}else{
						result =  toFormatResult("success", "",false);
					}
				}else if("getbetcodehistory".equals(flag)){
					String sid = "";
					for(SelectItem si : list){
						if("sid".equals(si.getLabel())){
							sid = si.getValue().toString();
						}
					}
					UINewBetBean b = new UINewBetBean(sid);
					GameRecordItemSpecial gr= b.search_special(ui);
					if(gr!=null){
						//获取用余额
						String balance = this.getUserBalance(ui.getLoginname());
						gr.setUserbalance(balance);
						result =  toFormatResult("success", JSONObject.fromObject(gr).toString(),false);
					}else{
						result =  toFormatResult("success", "",false);
					}
				}else if("changechips".equals(flag)){
					String[] chips = (String[])request.getSession().getAttribute("FFK3_CHIPS");
					String temp = "";
					int index01 = -1;
					int index02 = -1;
					for(SelectItem si : list){
						if("change01index".equals(si.getLabel())){
							index01 = Integer.parseInt(si.getValue().toString());
						}else if("change02index".equals(si.getLabel())){
							index02 = Integer.parseInt(si.getValue().toString());
						}
					}
					if(index01>=0&&index02>=0){
						temp = chips[index01];
						chips[index01] = chips[index02];
						chips[index02] = temp; 
						request.getSession().setAttribute("FFK3_CHIPS", chips);
					}
					result =  toFormatResult("success", JSONArray.fromObject(chips).toString(),false);
				}else if("getbetdetail".equals(flag)){
					String sid = "";
					for(SelectItem si : list){
						if("sid".equals(si.getLabel())){
							sid = si.getValue().toString();
						}
					}
					BetRecordBean b = new BetRecordBean(sid,ui.getLoginname());
					JSONObject oo = JSONObject.fromObject(b);
					result =  toFormatResult("success", oo.toString(),false);
				}else if("cancelbet".equals(flag)){
					String sid = "";
					for(SelectItem si : list){
						if("sid".equals(si.getLabel())){
							sid = si.getValue().toString();
						}
					}
					String ss = GameRecordBean.cancelBet(sid);
					if("success".equals(ss)){
						result =  toFormatResult("success", "撤单成功",false);
					}else{
						result =  toFormatResult("error", ss,false);
					}
				}
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
			result =  toFormatResult("error", "",true);
		}
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}
	public String toFormatResult(String status,String msg,boolean changeFlag){
		StringBuilder result = new StringBuilder("{\"status\":\"error\",\"data\":");
		if("success".equals(status)){
			if(msg.indexOf("{")>=0||msg.indexOf("[")>=0){
				result.append(msg);
			}else{
				result.append("\""+msg+"\"");
			}
			result.append("}");
			return result.toString().replaceAll("error", "success");
		}else{
			if(changeFlag){
				result.append("\"系统在维护，请稍后再试！\"");
				result.append("}");
			}else{
				result.append("\""+msg+" \"");
				result.append("}");
			}
			return result.toString();
		}
	}
	public String getUserBalance(String username){
		String result = "";
		//取账号总余额
		List<Object> olist01 = new ArrayList<Object>();
		olist01.add("BalanceNew");
		olist01.add(username);
		
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist01, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> list01 = (ArrayList<Object>)rlist.get(1);
				if(list01!=null&&list01.size()>0){
					result = list01.get(0).toString();
				}
			}else{
				result = "0.0";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
}
