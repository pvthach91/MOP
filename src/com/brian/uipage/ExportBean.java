package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

//import oracle.jdbc.OracleTypes;







//import com.brian.db.DbProc;
import com.brian.item.BalanceChangeItem;
import com.brian.item.IPResult;
import com.brian.item.LogItem;
import com.brian.item.LoginItem;
import com.brian.item.OperationItem;
import com.brian.item.UserItem;
import com.brian.service.HttpRequest;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.JsonUtil;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.service.UIPage;
import com.brian.unit.CommonMethod;
/**
 * 帐变查询
 * @author Brian
 *
 */
public class ExportBean extends LogXY implements UIPage{
	private String starttime="";
	private String endtime="";
	
	private List<LogItem> tmpslist = new ArrayList<LogItem>();
	private List<OperationItem> reslist = new ArrayList<OperationItem>();
	
	public ExportBean(){
		getOperation();
	}
	
	public void getOperation() {
		// TODO Auto-generated method stub
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UserOperationSearch");
		olist.add(ui.getLoginname());
		olist.add(starttime);
		olist.add(endtime);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				tmpslist = (List<LogItem>)sblist.get(0);
				for (LogItem litem : tmpslist) {
					OperationItem oitem = new OperationItem();
					oitem.setInserttime(litem.getInserttime());
					oitem.setLogtype(litem.getLogtype());
					String tmpip = litem.getIp();
					oitem.setIp(tmpip);
					String ipadr = "未知";
					oitem.setIpadress(ipadr);
					reslist.add(oitem);
				}
				
			}else{
				String msg = rlist.get(1).toString();
				if(msg.indexOf("Cursor is closed")>=0){
					this.setMsg("非法操作！");
				}else{
					this.setMsg(msg);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String getIPAdress(String tmpip) {
		String ipadr = "未知";
		if (tmpip!=null) {
			//掉接口，根据ip查询归属地
			String tmpadr = getAdressByIP(tmpip);
			if (tmpadr!= null && tmpadr.length()>0) {
				ipadr = tmpadr;
			}
		}
		return ipadr;
	}
	
	public String getAdressByIP(String str) {
		String ret = ""; 
		String s=HttpRequest.sendGet("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=", str);
		 if (s.length()>3) {
			IPResult ipr = JsonUtil.jsonToObject(s, IPResult.class);
			String c =ipr.getCountry();
			String p =ipr.getProvince();
			String city =ipr.getCity();
			ret = c+p+city;
			System.out.println(ret);
		}	
		 return ret;
	}

	
	
	
	@Override
	public void firstPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void proPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lastPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputPage() {
		// TODO Auto-generated method stub
		
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<LogItem> getTmpslist() {
		return tmpslist;
	}

	public void setTmpslist(List<LogItem> tmpslist) {
		this.tmpslist = tmpslist;
	}

	public List<OperationItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<OperationItem> reslist) {
		this.reslist = reslist;
	}

	
}
