package com.brian.run;

import java.util.Timer;

/**
 * EA启动的抽取服务
 * @author DIVEN
 *
 */
public class ScheduleMain {
	public static void run() {
		//程序启动后10秒运行，每天执行一次,生成数据库表schedule计划3天的任务
		Timer scheduleTimer = new Timer();
		scheduleTimer.schedule(new ScheduleTask(), 1*1000, 60*60*1000);
		
		//扫描时刻表
		Timer ScanTimer = new Timer();
		ScanTimer.schedule(new ScanScheduleTask(), 2*1000, 1*1000);
		
		//Clear Login Info
		Timer LoginTimer = new Timer();
		LoginTimer.schedule(new LoginListTask(), 30*1000, 60*1000);
		
	}
}
