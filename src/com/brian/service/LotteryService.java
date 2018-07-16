package com.brian.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.brian.item.UserItem;
import com.brian.item.UserReqsInfoItem;
import com.brian.run.LogOperTask;
import com.brian.ui.LoginBean;
import com.brian.uipage.ExportBean;
import com.brian.unit.CommonMethod;


public class LotteryService extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Logger logs = Logger.getLogger(LotteryService.class); 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("LotteryService GET METHOD");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		InputStream in = request.getInputStream();
		String sourcecode = CommonMethod.inputStream2String(in);
		String strdata = sourcecode;
		
		Double dbalance=0.0;
		Double dtotalmoney=0.0;
		
		String ip = request.getRemoteAddr();
		try{
			strdata = CommonMethod.Decode(strdata);
			UserItem ui = (UserItem)request.getSession().getAttribute("USERINFO");
			if(ui==null || ui.getLoginname()==null){
				logs.error("提交注单,未登录,IP："+ip+",内容："+strdata);
				return;
			}
			//1、登录判断
			//2、判断格式是否合法
			//3、判断是追号还是投注
			//4、投注的期号是否过期，追号是否过期
			
			//   加入加密判断.  String zip = "0" (未加密) "1" (加密)
			
			
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
			String res = null;
			if(flag==null){
				return;
			}else if("gettime".equals(flag)){
				//返回数字如：49
				res = UIRequestService.gettime(list);
			}else if("save".equals(flag)){
				
				for(SelectItem si : list){
					if("lt_total_money".equals(si.getLabel())){
						String totalmoney = si.getValue().toString();
						dtotalmoney = Double.parseDouble(totalmoney);
					}
				}
				//判断
				String ubalance = ui.getBalance();
				dbalance = Double.parseDouble(ubalance);
				if (dtotalmoney>dbalance) {
					res = "{'stats':'error01','data':'"+"总金额不足，请重新投注；"+"'}";
				}else {
					logs.info("收到指令："+ui.getLoginname()+",内容："+strdata);
					res = UIRequestService.save(list,ui,strdata,ip);
				}
			}else if("gethistory".equals(flag)){
				res = UIRequestService.gethistory(list);
			}else if("read".equals(flag)){
				res = UIRequestService.read(list);
			}else if("balance".equals(flag)){
				res = UIRequestService.refBalanceNewWithNotice(list,ui,request);
			}else if("refBalanceNew".equals(flag)){
				res = UIRequestService.refBalance(list,ui,request);
			}else if("mmcwnum".equals(flag)){
				res = UIRequestService.mmcWNum(list,ui);
			}else if("UAddFavorite".equals(flag)){
				String lotteryid = strdata.substring(strdata.lastIndexOf("=")+1, strdata.length());
				LoginBean lb =(LoginBean)request.getSession().getAttribute("LoginBean");
				res = UIRequestService.addFavorite(ui,ip,lotteryid,lb);
			}else if("UDellFavorite".equals(flag)){
				String lotteryid = strdata.substring(strdata.lastIndexOf("=")+1, strdata.length());
				LoginBean lb =(LoginBean)request.getSession().getAttribute("LoginBean");
				res = UIRequestService.dellFavorite(ui,ip,lotteryid,lb);
			}else if("getIP".equals(flag)){
				String tmpip="";
				for(SelectItem si : list){
					if("ip".equals(si.getLabel())){
						tmpip = si.getValue().toString();
					}
				}
				res = new ExportBean().getIPAdress(tmpip);
			}else if("getLogInfo".equals(flag)){
				Long totalTimes = (Long)request.getSession().getServletContext().getAttribute("USER_TOTALTIMES");
				Map<String,String> ip_times = (Map<String,String>)request.getSession().getServletContext().getAttribute("USER_IP_TIMES");
				if(totalTimes==null||totalTimes<1){
					totalTimes = 0L;
				}
				request.getSession().getServletContext().setAttribute("USER_TOTALTIMES", 0L);
				if(ip_times!=null){
					request.getSession().getServletContext().setAttribute("USER_IP_TIMES", null);
					UserReqsInfoItem resObject = new UserReqsInfoItem();
					resObject.setIp_times(ip_times);
					resObject.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					resObject.setTotalTimes(totalTimes);
					res = JSONObject.fromObject(resObject).toString();
				}
			}else if("openOrCloseLog".equals(flag)){
				String openOrCloseFlag = "";
				for(SelectItem si : list){
					if("openOrCloseFlag".equals(si.getLabel())){
						openOrCloseFlag = si.getValue().toString();
						break;
					}
				}
				if(StringUtils.isEmpty(openOrCloseFlag)){
					return;
				}
				if("open".equals(openOrCloseFlag)){
					boolean timerFlag = true;
					if(LogOperTask.timer==null){
						LogOperTask.timer = new Timer();
						timerFlag = false;
					}
					if(LogOperTask.servletContext == null){
						LogOperTask.servletContext = request.getSession().getServletContext();
					}
					if(!timerFlag){
						LogOperTask logtask = new LogOperTask();
						LogOperTask.timer.schedule(logtask, 1*1000, 60*1000);
						FilterXY.logFlag = true;
					}
					res = "";
				}else{
					LogOperTask.timer.cancel();
					LogOperTask.timer=null;
					System.gc();
					FilterXY.logFlag = false;
					res = "";
				}
			}
			else{
				return;
			}
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.print(res);
			out.flush();
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
