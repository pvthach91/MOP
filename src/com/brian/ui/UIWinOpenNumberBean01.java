package com.brian.ui;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.YLFXItem;
import com.brian.service.InitData;
import com.brian.unit.CommonMethod;

public class UIWinOpenNumberBean01 {
	private List<YLFXItem> reslist = new ArrayList<YLFXItem>();
	
	public UIWinOpenNumberBean01(){
		reslist.clear();
		String sid = CommonMethod.getParam("id");
		String snum = CommonMethod.getParam("num");
		if(sid==null){
			return;
		}
		if(!CommonMethod.checkInt(sid)){
			return;
		}
		int lotteryid = Integer.parseInt(sid);
		int nums=0;
		if(snum!=null){
			if(CommonMethod.checkInt(snum)){
				nums = Integer.parseInt(snum);
			}
		}
		List<YLFXItem> slist = InitData.ylfxlist;
		List<YLFXItem> tmplist = new ArrayList<YLFXItem>();
		for(YLFXItem si:slist){
			if(si.getLotteryid()!=lotteryid){
				continue;
			}
			tmplist.add(si);
		}
		if(tmplist.size()<1){
			return;
		}
		
		int t = tmplist.size();
		int e=0;
		if(nums>tmplist.size()){
			e=0;
		}else{
			e=tmplist.size()-nums;
		}
		
		for(int i=t;i>e;i--){
			YLFXItem yfi = tmplist.get(i-1);
			YLFXItem bk = new YLFXItem();
			String winnumber = yfi.getWinnumber();
			if (winnumber!=null) {
				String[] winarr = winnumber.split(",");
				if (winarr.length==10) {
					bk.setW(winarr[0]);
					bk.setQ(winarr[1]);
					bk.setB(winarr[2]);
					bk.setS(winarr[3]);
					bk.setG(winarr[4]);
					bk.setL(winarr[5]);
					bk.setT(winarr[6]);
					bk.setQk(winarr[7]);
					bk.setBk(winarr[8]);
					bk.setSk(winarr[9]);
				}
			}

			if(yfi.getIssue().indexOf("-")>0){
				bk.setIssue(yfi.getIssue().split("-")[1]+"期");
			}else{
				bk.setIssue(yfi.getIssue()+"期");
			}
			reslist.add(bk);
		}
	}
	public void init(){
		
	}
	public List<YLFXItem> getReslist() {
		return reslist;
	}
	public void setReslist(List<YLFXItem> reslist) {
		this.reslist = reslist;
	}
}
