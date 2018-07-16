package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.brian.item.NewWinListItem;
import com.brian.item.SysMsgItem;
import com.brian.service.InitData;
import com.brian.service.LogXY;
import com.brian.unit.CommonMethod;

public class SysMsgBean extends LogXY{
	private List<NewWinListItem> newwinlist = new ArrayList<NewWinListItem>();
	private List<SysMsgItem> msglist = new ArrayList<SysMsgItem>();
	private List<SysMsgItem> msgnoticelist = new ArrayList<SysMsgItem>();
	private List<SysMsgItem> msgdiscoutlist = new ArrayList<SysMsgItem>();
	private List<SysMsgItem> indexList = new ArrayList<SysMsgItem>();
	private SysMsgItem showitem = new SysMsgItem();
	
	private int pagesize = 10;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	
	
	
	public SysMsgItem getShowitem() {
		return showitem;
	}
	public void setShowitem(SysMsgItem showitem) {
		this.showitem = showitem;
	}
	private int picnums=0;
	private SysMsgItem sysmsg = new SysMsgItem();
	
	public SysMsgBean(){
		curpage = 1;
		newwinlist = InitData.newWinList;
		
		msglist = InitData.msglist;
		int i = 0;
		
		// 主页显示用
		for(SysMsgItem temp :msglist){
			indexList.add(temp);
			i++;
			if(i == 8){
				break;
			}
		}
		// 优惠活动和平台公告分开  0:优惠  1：公告
		int j = 0;
		int k = 0;
		int h = 0;
		for(SysMsgItem temp :msglist){
			//5条优惠
			if(0==temp.getParam1()&&j<6){
				msgdiscoutlist.add(temp);  //优惠
				j++;
			}else if(1==temp.getParam1()&&k<20){
				msgnoticelist.add(temp);   //活动
				k++;
			}
			h++;
			if(h==25){
				break;
			}
		}
		
		for(SysMsgItem smi:msglist){
			if(smi.getStatus()==2){
				showitem = smi;
				break;
			}
		}
		Random random = new Random();
		picnums = random.nextInt(11);
	}
	public void showmsg(){
		sysmsg = new SysMsgItem();
		this.setMsg(null);
		String ids = CommonMethod.getParam("id");
		if(ids==null){
			this.setMsg("参数无效");
			return;
		}
		for(SysMsgItem smi : msglist){
			if(ids.equals(smi.getId()+"")){
				sysmsg = smi;
			}
		}
	}
	public static void main(String[] args){
		Random random = new Random();
		for(int i=0;i<100;i++){
			int n = random.nextInt(11);
			System.out.println(n);
		}
	}
	
	public List<SysMsgItem> getMsglist() {
		return msglist;
	}
	public void setMsglist(List<SysMsgItem> msglist) {
		this.msglist = msglist;
	}
	public int getPicnums() {
		return picnums;
	}
	public void setPicnums(int picnums) {
		this.picnums = picnums;
	}
	public SysMsgItem getSysmsg() {
		return sysmsg;
	}
	public void setSysmsg(SysMsgItem sysmsg) {
		this.sysmsg = sysmsg;
	}
	public List<NewWinListItem> getNewwinlist() {
		return newwinlist;
	}
	public void setNewwinlist(List<NewWinListItem> newwinlist) {
		this.newwinlist = newwinlist;
	}
	public List<SysMsgItem> getIndexList() {
		return indexList;
	}
	public void setIndexList(List<SysMsgItem> indexList) {
		this.indexList = indexList;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getCurpage() {
		return curpage;
	}
	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	public int getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}

	public List<SysMsgItem> getMsgnoticelist() {
		return msgnoticelist;
	}
	public void setMsgnoticelist(List<SysMsgItem> msgnoticelist) {
		this.msgnoticelist = msgnoticelist;
	}
	public List<SysMsgItem> getMsgdiscoutlist() {
		return msgdiscoutlist;
	}
	public void setMsgdiscoutlist(List<SysMsgItem> msgdiscoutlist) {
		this.msgdiscoutlist = msgdiscoutlist;
	}
	
	
	
	
}
