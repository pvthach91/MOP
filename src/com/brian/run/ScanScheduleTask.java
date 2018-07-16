package com.brian.run;

import java.util.List;

import com.brian.item.CurScheduleItem;
import com.brian.item.ScheduleItem;
import com.brian.service.InitData;
import com.brian.unit.CommonMethod;

public class ScanScheduleTask extends java.util.TimerTask{
	@Override
	public void run() {
		//扫描有没有到达时间的
		//1、当前时间大于endtime时，进行位置切换，并从InitData.schedule中找出当前期号。
		String curtime = CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss");
		List<CurScheduleItem> curlist = InitData.getCurSchedule();//.curSchedule;
		List<ScheduleItem> slist = InitData.schedule;
		
		for(CurScheduleItem c:curlist){
			if(c.getEndtime()==null){
				continue;
			}
			if(curtime.compareTo(c.getEndtime())>0){
				for(ScheduleItem si: slist){
					if(c.getLotteryid()==si.getLotteryid() && si.getEndtime().compareTo(curtime)>0 && si.getStarttime().compareTo(curtime)<=0){
//						System.out.println(curtime+","+c.getIssue()+",当前到期，交换到期数:"+si.getIssue());
						//交换
						c.setHissysopentime(c.getSysopentime());
						c.setHisendtime(c.getEndtime());
						c.setHisopentime(c.getOpentime());
						c.setHistoryissue(c.getIssue());
						
						c.setWinnumber(si.getWinnumber());
						c.setStatus(0);
						c.setCollectnum(0);
						c.setStarttime(si.getStarttime());
						c.setSysopentime(si.getSysopentime());
						c.setIssue(si.getIssue());
						c.setEndtime(si.getEndtime());
						c.setLeft(si.getLeft());
						c.setSale(si.getSale());
						c.setOpentime(si.getOpentime());
						break;
					}
				}
			}
		}
	}
}
