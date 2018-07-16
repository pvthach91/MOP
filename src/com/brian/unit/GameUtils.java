package com.brian.unit;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.YLFXItem;
import com.brian.service.InitData;

public class GameUtils {
	public static List<YLFXItem> getHistory(String sid,String snum) throws Exception{
		List<YLFXItem> reslist = new ArrayList<YLFXItem>();
		if(sid==null){
			return null;
		}
		if(!CommonMethod.checkInt(sid)){
			return null;
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
			return null;
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
			bk.setWinnumber(yfi.getWinnumber());
			bk.setOpentime(yfi.getOpentime());
			bk.setW(yfi.getW());
			bk.setQ(yfi.getQ());
			bk.setB(yfi.getB());
			bk.setS(yfi.getS());
			bk.setG(yfi.getG());
			bk.setL(yfi.getL());
			bk.setT(yfi.getT());
			bk.setWn(yfi.getWn());
			bk.setQn(yfi.getQn());
			bk.setBn(yfi.getBn());
			bk.setSn(yfi.getSn());
			bk.setGn(yfi.getGn());
			bk.setLn(yfi.getLn());
			bk.setTn(yfi.getTn());
			if(yfi.getIssue().indexOf("-")>0){
				bk.setIssue(yfi.getIssue().split("-")[1]);
			}else{
				bk.setIssue(yfi.getIssue());
			}
			reslist.add(bk);
		}
		return reslist;
	}
}
