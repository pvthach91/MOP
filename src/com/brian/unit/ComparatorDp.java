package com.brian.unit;

import java.util.Comparator;

import com.brian.item.ReportUserItem;
import com.brian.item.ReportWLNewItem;

public class ComparatorDp implements Comparator<Object> {
	public int compare(Object arg0, Object arg1) {
		
		String cname = arg0.getClass().getSimpleName();//.getName();
		if("ReportWLNewItem".equals(cname)){
			ReportWLNewItem user0 = (ReportWLNewItem) arg0;
			ReportWLNewItem user1 = (ReportWLNewItem) arg1;
			int flag = user1.getDeposit().compareTo(user0.getDeposit());
			if (flag == 0) {
				return user1.getWin().compareTo(user0.getWin());
			} else {
				return flag;
			}
		}else{
			ReportUserItem user0 = (ReportUserItem) arg0;
			ReportUserItem user1 = (ReportUserItem) arg1;
			int flag = user1.getDp().compareTo(user0.getDp());
			if (flag == 0) {
				return user1.getTz().compareTo(user0.getTz());
			} else {
				return flag;
			}
		}
		
	}
}
