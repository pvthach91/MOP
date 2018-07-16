package com.brian.ui;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.YLFXItem;
import com.brian.service.InitData;
import com.brian.service.LogXY;
import com.brian.unit.CommonMethod;
import com.yinhe.iei.flashChart.CBasicXML;
import com.yinhe.iei.flashChart.CDataType;

/**
 * 开奖结果分布
 * @author BRIAN
 *
 */
public class WinNumLayoutBean extends LogXY{
	private String xmls = "";
	private String lotteryid = "";

	public WinNumLayoutBean(){
		lotteryid = CommonMethod.getParam("id");
		if(lotteryid==null){
			return;
		}
		List<YLFXItem> list = InitData.ylfxlist;
		System.out.print(list.size());
	}
	public void view(){
		this.setMsg(null);
		//位置，最近多少期，展示方式
		String ltid = CommonMethod.getParam("lotid");
		String nums = CommonMethod.getParam("nums");
		String types = CommonMethod.getParam("types");
		if(!CommonMethod.checkInt(ltid)){
			this.setMsg("失败：参数出错");
			return;
		}
		if(!CommonMethod.checkInt(nums)){
			this.setMsg("失败：参数出错");
			return;
		}
		if(!CommonMethod.checkInt(types)){
			this.setMsg("失败：参数出错");
			return;
		}
		String lotteryName="";
		if("15".equals(ltid)){
			lotteryName ="合一分分彩";
		}else if("16".equals(ltid)){
			lotteryName ="合一两分彩";
		}else if("17".equals(ltid)){
			lotteryName ="合一3D";
		}else if("1".equals(ltid)){
			lotteryName ="重庆时时彩";
		}else if("11".equals(ltid)){
			lotteryName ="福彩3D";
		}else if("45".equals(ltid)){
			lotteryName ="新加坡45秒";
		}else{
			this.setMsg("失败：参数出错");
			return;
		}
		Integer numsInt = Integer.parseInt(nums);
		Integer lottid = Integer.parseInt(ltid);
		Integer tps = Integer.parseInt(types);
		if(numsInt<=0){
			this.setMsg("失败：期数应大于0.");
			return;
		}
		List<YLFXItem> list = InitData.ylfxlist;
		List<YLFXItem> tmplist = new ArrayList<YLFXItem>();
		for(YLFXItem yfi:list){
			if(yfi.getLotteryid()==lottid){
				tmplist.add(yfi);
			}
		}
		if(tmplist.size()<numsInt){
			numsInt = tmplist.size();
		}
		int[] fbarr={0,0,0,0,0,0,0,0,0,0};

		String stime = "",etime="";
		int ss = tmplist.size()-numsInt;
		int ee = tmplist.size();
		for(int i=ss;i<ee;i++){
			YLFXItem yf=tmplist.get(i);
			if(i==ss){
				stime=yf.getIssue();
			}else if(i==ee-1){
				etime =yf.getIssue();
			}
			if(tps==0){
				//全部
				if(yf.getWinnumber().length()>4){
					fbarr[yf.getWn()]++;
					fbarr[yf.getQn()]++;
				}
				fbarr[yf.getBn()]++;
				fbarr[yf.getSn()]++;
				fbarr[yf.getGn()]++;
			}else if(tps==1){
				fbarr[yf.getWn()]++;
			}else if(tps==2){
				fbarr[yf.getQn()]++;
			}else if(tps==3){
				fbarr[yf.getBn()]++;
			}else if(tps==4){
				fbarr[yf.getSn()]++;
			}else if(tps==5){
				fbarr[yf.getGn()]++;
			}
		}
		//String title="最近"+nums+"期【"+lotteryName+"】开奖号码分布图";
		String title="";
		if(numsInt==1){
			title="【"+lotteryName+"】第"+stime+"期开奖号码分布图";
		}else{
			title="【"+lotteryName+"】最近("+numsInt+")期，从第"+stime+"期至第"+etime+"期开奖号码分布图";
		}
		xmls = makeXML(title,fbarr);
	}
	private String makeXML(String title,int[] arr){
		CBasicXML bx = new CBasicXML();
		//初始化参数
		CDataType dtInit = new CDataType();
		dtInit.addItem(title, "caption");
		dtInit.addItem(CommonMethod.getRandomNubmer(1), "palette");
		dtInit.addItem("开奖号码分布", "xAxisName");
		dtInit.addItem("出现次数", "yAxisName");
		dtInit.addItem("1", "showValues");
		dtInit.addItem("0", "decimals");
		dtInit.addItem("0", "formatNumberScale");
		dtInit.addItem("1", "useRoundEdges");
		bx.setChartParams(dtInit);
		// 数据(前面两项不能为空)
		for(int i=0;i<arr.length;i++){
			bx.addData(arr[i]+"","号码【"+i+"】",  null,null);
		}
		//System.out.println(bx.getXML());
		return bx.getXML();
	}
	public String getXmls() {
		return xmls;
	}
	public void setXmls(String xmls) {
		this.xmls = xmls;
	}
	public String getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(String lotteryid) {
		this.lotteryid = lotteryid;
	}
}
