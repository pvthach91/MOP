package com.brian.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.brian.item.CurScheduleItem;
import com.brian.item.ScheduleItem;
import com.brian.item.UserItem;
import com.brian.unit.CommonMethod;

public class BackService extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("BackService GET METHOD");
	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		InputStream in = request.getInputStream();
		String strdata = CommonMethod.inputStream2String(in);
		System.out.println("BackService POST METHOD："+strdata);
		
		if(strdata==null)
			return;
		String[] args = strdata.split("&");
		if(args.length<1){
			return;
		}
		if("opennumber".equals(args[0])){
			//开奖号码
			//"opennumber"+"&"+lotteryid+"&"+issue+"&"+winnumber;
			int lotteryid = Integer.parseInt(args[1]);
			String issue = args[2];
			String wnum = args[3];
			if(lotteryid==81||lotteryid==82){
				wnum = wnum+"&"+args[4];
			}
			CurScheduleItem csi = InitData.getHisScheduleItem(lotteryid, issue);
			if(csi!=null){
				csi.setWinnumber(wnum);
				csi.setStatus(2);
			}else{
				System.out.print(lotteryid+":"+issue+"开奖记录不属于当前期");
				for(CurScheduleItem csi2 : InitData.curSchedule){
					if(csi2.getLotteryid()==lotteryid){
						System.out.println(",上一期为："+csi2.getHistoryissue()+",当前期为："+csi2.getIssue());
						break;
					}
				}
				
			}
			
			//UPDATE Schedule
			List<ScheduleItem> slist = InitData.schedule;
			ScheduleItem scheitem = null;
			for(ScheduleItem si:slist){
				if(si.getLotteryid()==lotteryid && si.getIssue().equals(issue)){
					//if(si.getStatus()<2){
					si.setStatus(2);
					si.setWinnumber(wnum);
					//}
					scheitem = si;
					break;
				}
			}
			//UPDATE YLFX遗漏分析
			if(scheitem!=null){
				InitData.YLFXOneByOne(scheitem);
			}
			PostOut(response,"success");
			return;
		}else if("login".equals(args[0])){
			//userid,agentid,ticket
			UserItem ui = new UserItem();
			ui.setLoginname(args[1]);
			ui.setFatherid(Integer.parseInt(args[2]));
			ui.setTicket(args[3]);
			ui.setLastlogintime(CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss"));
			InitData.loginlist.add(ui);
			PostOut(response,"success");
			return;
		}else if("wsurl".equals(args[0])){
			//get
			PostOut(response,InitServlet.WSURL);
			return;
		}else if("settingWsUrl".equals(args[0])){
			InitServlet.WSURL = args[1];
			return;
		}else if("monitor".equals(args[0])){
			//监控是否正常
			PostOut(response,"success");
			return;
		}else if("init".equals(args[0])){
			InitData.init();
			return;
		}else if("initdict".equals(args[0])){
			InitData.initDict();
			return;
		}else if("initmsg".equals(args[0])){
			InitData.loadNewMsg();
		}else if("GameMg".equals(args[0])){
			InitData.loadConfigList();
		}else if("addnewissue".equals(args[0])){
			if(args.length<7){
				return ;
			}
			int lotteryId = Integer.parseInt(args[1]);
			String issue = args[2];
			String startTime = args[3];
			String endTime = args[4];
			String openTime = args[5];
			int status = Integer.parseInt(args[6]);
			ScheduleItem ss = new ScheduleItem();
			ss.setLotteryid(lotteryId);
			ss.setIssue(issue);
			ss.setStarttime(startTime);
			ss.setEndtime(endTime);
			ss.setOpentime(openTime);
			ss.setStatus(status);
			boolean addflag = true;
			for(ScheduleItem sch:InitData.schedule){
				if(sch.getLotteryid() == lotteryId&&sch.getIssue().equals(issue)){
					addflag = false;
					break;
				}
			}
			if(addflag){
				System.out.println("backservice准备 在InitData.schedule  添加这一期："+ss.getIssue());
				InitData.schedule.add(ss);
			}
			//当为第一次手工录录数据时要初始化当前期
			List<CurScheduleItem> curlist = InitData.getCurSchedule();//.curSchedule;
			for(CurScheduleItem cur:curlist){
				if(cur.getLotteryid()==81){
					if(StringUtils.isEmpty(cur.getIssue())||StringUtils.isEmpty(cur.getStarttime())||StringUtils.isEmpty(cur.getEndtime())||StringUtils.isEmpty(cur.getOpentime())){
						System.out.println("backservice准备 在curSchedule添加这一期："+ss.getIssue());
						cur.setIssue(issue);
						cur.setStarttime(startTime);
						cur.setEndtime(endTime);
						cur.setOpentime(openTime);
						cur.setStatus(status);
					}
				}
			}
		}

	}
	private void PostOut(HttpServletResponse hsr, String str)throws IOException {
		PrintWriter out = hsr.getWriter();
		out.println(str);
		out.flush();
		out.close();
	}
}
