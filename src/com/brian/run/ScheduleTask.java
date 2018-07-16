package com.brian.run;


import java.util.List;

import com.brian.item.NewWinListItem;
import com.brian.item.ScheduleItem;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;
/**
 * 2014-02-13
 * 每日5AM初始化Schedule
 * 最新开奖列表的获取
 * @author BRIAN
 *
 */
public class ScheduleTask extends java.util.TimerTask{
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		NewWinList();
		String h = CommonMethod.GetCurDate("HH");
		if(!"04".equals(h)){
			return;
		}
		try{
			//初始化Schedule
			List<Object> obj= PostDBData.DBPOST(InitServlet.WSURL, "schedule", 1);
			InitData.schedule = (List<ScheduleItem>)obj.get(0);
			//重新加载遗漏分析
			InitData.initYLFX();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	@SuppressWarnings("unchecked")
	public void NewWinList(){
		try{
			List<Object> obj= PostDBData.DBPOST(InitServlet.WSURL, "NewWinList", 1);
			InitData.newWinList = (List<NewWinListItem>)obj.get(0);
			for(NewWinListItem nwli : InitData.newWinList){
				nwli.setLotteryname(InitData.getDictKeyByValue(InitData.lotterylist, nwli.getLotteryid()+""));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
