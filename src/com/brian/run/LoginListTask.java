package com.brian.run;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.unit.CommonMethod;

public class LoginListTask extends java.util.TimerTask{
	@Override
	public void run() {
		//System.out.println("Clear");
		String curtime = CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss");
		List<UserItem> userlist = InitData.loginlist;
		List<UserItem> tmplist = new ArrayList<UserItem>();
		for(UserItem ui : userlist){
			try{
				long n = CommonMethod.getTimeDiffe(ui.getLastlogintime(),curtime, "yyyy-MM-dd HH:mm:ss", "ss");
				if(n<60){
					tmplist.add(ui);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		InitData.loginlist = tmplist;
	}
}
