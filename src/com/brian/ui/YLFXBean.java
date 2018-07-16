package com.brian.ui;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.YLFXItem;
import com.brian.service.InitData;
import com.brian.service.LogXY;
import com.brian.unit.CommonMethod;

public class YLFXBean extends LogXY{
	private Integer lotteryid = -1;
	private String lotteryname = "";
	private Integer nums = 15;
	
	private List<YLFXItem> reslist = new ArrayList<YLFXItem>();
	//出现次数
	private YLFXItem appearTimes = new YLFXItem();
	//最大遗漏
	private YLFXItem maxyl = new YLFXItem();
	
	public YLFXBean(){
		reslist.clear();
		String sid = CommonMethod.getParam("id");
		String snum = CommonMethod.getParam("num");
		if(sid==null){
			return;
		}
		if(!CommonMethod.checkInt(sid)){
			return;
		}
		lotteryname = InitData.getDictKeyByValue(InitData.lotterylist,sid);
		lotteryid = Integer.parseInt(sid);
		if(snum!=null){
			if(CommonMethod.checkInt(snum)){
				nums = Integer.parseInt(snum);
			}
		}
		List<YLFXItem> slist = InitData.ylfxlist;
		List<YLFXItem> tmplist = new ArrayList<YLFXItem>();
		/*for(YLFXItem si:slist){
			if(si.getLotteryid()!=lotteryid){
				continue;
			}
			tmplist.add(si);
		}*/
		if(nums>100){
			String strNums=nums.toString();
			for(YLFXItem si:slist){
				if(si.getLotteryid()==lotteryid && si.getIssue().contains(strNums )){
					tmplist.add(si);
				}
				else
					continue;
			}
			nums=tmplist.size();
		}
		else {
			if(nums>0){
				for(YLFXItem si:slist){
					if(si.getLotteryid()==lotteryid){
						tmplist.add(si);
					}
					else
						continue;
				}
			}
		}
		int sp = 0;
		if(tmplist.size()>nums){
			sp = tmplist.size()-nums;
		}
		//////////////////////////出现次数
		int[] r1={0,0,0,0,0,0,0,0,0,0,0,0};
		int[] r2={0,0,0,0,0,0,0,0,0,0,0,0};
		int[] r3={0,0,0,0,0,0,0,0,0,0,0,0};
		int[] r4={0,0,0,0,0,0,0,0,0,0,0,0};
		int[] r5={0,0,0,0,0,0,0,0,0,0,0,0};
		appearTimes.setWw(r1);
		appearTimes.setQw(r2);
		appearTimes.setBw(r3);
		appearTimes.setSw(r4);
		appearTimes.setGw(r5);
		
		for(int i=sp;i<tmplist.size();i++){
			YLFXItem yfi = tmplist.get(i);
			reslist.add(yfi);
			
			//出现次数
			appearTimes.getWw()[yfi.getWn()] +=1; 
			appearTimes.getQw()[yfi.getQn()] +=1;
			appearTimes.getBw()[yfi.getBn()] +=1;
			appearTimes.getSw()[yfi.getSn()] +=1;
			appearTimes.getGw()[yfi.getGn()] +=1;
			//最大遗漏
			//maxyl
			for(int j=0;j<yfi.getWw().length;j++){
				int s = yfi.getWw()[j];
				int n = maxyl.getWw()[j];
				if(s>n){
					maxyl.getWw()[j] = s;
				}
			}
			for(int j=0;j<yfi.getQw().length;j++){
				int s = yfi.getQw()[j];
				int n = maxyl.getQw()[j];
				if(s>n){
					maxyl.getQw()[j] = s;
				}
			}
			for(int j=0;j<yfi.getBw().length;j++){
				int s = yfi.getBw()[j];
				int n = maxyl.getBw()[j];
				if(s>n){
					maxyl.getBw()[j] = s;
				}
			}
			for(int j=0;j<yfi.getSw().length;j++){
				int s = yfi.getSw()[j];
				int n = maxyl.getSw()[j];
				if(s>n){
					maxyl.getSw()[j] = s;
				}
			}
			for(int j=0;j<yfi.getGw().length;j++){
				int s = yfi.getGw()[j];
				int n = maxyl.getGw()[j];
				if(s>n){
					maxyl.getGw()[j] = s;
				}
			}
		}
	}
	public Integer getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Integer lotteryid) {
		this.lotteryid = lotteryid;
	}
	public Integer getNums() {
		return nums;
	}
	public void setNums(Integer nums) {
		this.nums = nums;
	}
	public List<YLFXItem> getReslist() {
		return reslist;
	}
	public void setReslist(List<YLFXItem> reslist) {
		this.reslist = reslist;
	}
	public YLFXItem getAppearTimes() {
		return appearTimes;
	}
	public void setAppearTimes(YLFXItem appearTimes) {
		this.appearTimes = appearTimes;
	}
	public YLFXItem getMaxyl() {
		return maxyl;
	}
	public void setMaxyl(YLFXItem maxyl) {
		this.maxyl = maxyl;
	}
	public String getLotteryname() {
		return lotteryname;
	}
	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}
}
