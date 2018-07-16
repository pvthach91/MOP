package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.MessageItem;
import com.brian.item.UserItem;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.unit.CommonMethod;

public class PersonMsgSendBean extends LogXY{
	private int pagesize = 10;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	//我发送的信息
	private List<MessageItem> reslist = new ArrayList<MessageItem>();
	//查看信息
	private MessageItem viewmsg = new MessageItem();
	
	//新信息
	private MessageItem newmsg = new MessageItem();
	
	public PersonMsgSendBean(){
		btnSearch();
	}
	
	public void save(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("失败：Session失效，请重新登录。");
			return;
		}
		if(newmsg.getRecipient().length()<1){
			this.setMsg("失败：接收人不能为空。");
			return;
		}
		if(newmsg.getTitle().length()<1){
			this.setMsg("失败：主题不能为空。");
			return;
		}
		if(newmsg.getContent().length()<1){
			this.setMsg("失败：内容不能为空。");
			return;
		}
		if(newmsg.getRecipient().equals(ui.getLoginname())){
			this.setMsg("失败：不能给自己发送信息");
			return;
		}
		if("上级代理".equals(newmsg.getRecipient())){
			newmsg.setRecipient(ui.getParam2());
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPesrsonMessageWrite");
		olist.add(newmsg.getTitle());
		olist.add(newmsg.getContent());
		olist.add(ui.getLoginname());
		olist.add(newmsg.getRecipient());
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				newmsg = new MessageItem();
				btnSearch();
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	
	public void btnSearch() {
		curpage = 1;
		search();
	}
	
	public void view(){
		this.setMsg(null);
		viewmsg = new MessageItem();
		String id= CommonMethod.getParam("id");
		if(id==null){
			this.setMsg("失败：参数出错");
			return;
		}
		for(MessageItem mi: reslist){
			if(id.equals(mi.getId()+"")){
				viewmsg = mi;
				break;
			}
		}
	}
	public void del(){
		this.setMsg(null);
		viewmsg = new MessageItem();
		String id= CommonMethod.getParam("id");
		if(id==null){
			this.setMsg("失败：参数出错");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPesrsonMessageDel");
		olist.add(id);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void search(){
		reslist.clear();
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPesrsonMessageSendList");
		olist.add(curpage);
		olist.add(pagesize);
		olist.add(ui.getLoginname());
		
		try{
			String stime = CommonMethod.getTimeSpace(CommonMethod.GetCurDate("yyyy-MM-dd"), "yyyy-MM-dd", -30, "dd")+" 00:00:00";
			String etime = CommonMethod.GetCurDate("yyyy-MM-dd")+" 23:59:59";
			olist.add(stime);
			olist.add(etime);
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<MessageItem>)sblist.get(0);
				for(MessageItem mi: reslist){
					if(mi.getRecipient().equals(ui.getParam2())){
						mi.setRecipient("上级代理");
					}
				}
				totalrecord = Integer.parseInt(sblist.get(1).toString());
				totalpage = Integer.parseInt(sblist.get(2).toString());
			}else{
				this.setMsg(rlist.get(1).toString());
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
		}
	}
	public void firstPage() {
		this.setMsg(null);
		if (curpage <= 1) {
			this.setMsg("当前已经是第一页！");
			return;
		}
		curpage = 1;
		search();
	}

	public void proPage() {
		this.setMsg(null);
		if (curpage <= 1) {
			this.setMsg("当前已经是第一页！");
			return;
		}
		curpage--;
		search();
	}

	public void nextPage() {
		this.setMsg(null);
		if (curpage >= totalpage) {
			this.setMsg("当前已经是最后一页！");
			return;
		}
		curpage++;
		search();
	}

	public void lastPage() {
		this.setMsg(null);
		if (curpage >= totalpage) {
			this.setMsg("当前已经是最后一页！");
			return;
		}
		curpage = totalpage;
		search();
	}

	public void inputPage() {
		this.setMsg(null);
		try {
			String num = CommonMethod.getParam("num");
			int n = Integer.parseInt(num);
			if (n < 1 || n > totalpage) {
				this.setMsg("输入的页码不正确，请重试！");
				return;
			}
			curpage = n;
			search();
		} catch (Exception ex) {
			this.setMsg(ex.getMessage());
		}
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

	public List<MessageItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<MessageItem> reslist) {
		this.reslist = reslist;
	}

	public MessageItem getViewmsg() {
		return viewmsg;
	}

	public void setViewmsg(MessageItem viewmsg) {
		this.viewmsg = viewmsg;
	}

	public MessageItem getNewmsg() {
		return newmsg;
	}

	public void setNewmsg(MessageItem newmsg) {
		this.newmsg = newmsg;
	}
}
