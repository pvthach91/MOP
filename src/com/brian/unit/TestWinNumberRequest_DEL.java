package com.brian.unit;

import java.util.Date;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class TestWinNumberRequest_DEL {
	public static void main(String[] args) throws Exception{
		//jxssc();
		//shssl();
		//fc3D();
		//String s = CommonMethod.Encode("选择奖金");
		//System.out.println(s);
		String s = "02 03 01";
		if(s.split(" ").length!=3){
			System.out.print("Error");
		}else{
			System.out.print("OK");
		}
	}
	//排列三五
	public static void pl35(){
		//http://www.lottery.gov.cn/lottery/pls/Detail.aspx
		
	}
	//七乐彩
	public static void qlc(){
		//http://www.cwl.gov.cn/kjxx/qlc/hmhz/
	}
	//双色球
	public static void ssq(){
		//http://www.cwl.gov.cn/kjxx/ssq/hmhz/
	}
	//福彩3D
	public static void fc3D(){
		//http://www.cwl.gov.cn/kjxx/fc3d/hmhz/
		String c = TestWinNumberRequest_DEL.getWebContentByURL("http://www.cwl.gov.cn/kjxx/fc3d/hmhz/");
		String res = betweenOpt(c.substring(356),"2013327","<td><a href=");
		System.out.println(res);
	}
	//重庆十一选五
	public static void cqshiyixuanwu(){
		//http://trend.baidu.lecai.com/cq11x5Zst!cq11x5jbzs.jhtml
		String c = TestWinNumberRequest_DEL.getWebContentByURL("http://trend.baidu.lecai.com/cq11x5Zst!cq11x5jbzs.jhtml");
		//System.out.println(c.substring(356));
		String res = betweenOpt(c.substring(356)," <tbody>"," </tbody>");
		System.out.println(res);
	}
	//广东11选5
	public static void gd11x5(){
		//http://www.gdlottery.cn/zst11xuan5.jspx
	}
	//多乐彩（江西11选5）
	public static void jx11x5(){
		//http://chart.icaile.com/jx11x5.php
	}
	//十一运夺金(山东11选5)
	public static void syydj11x5(){
		//http://chart.icaile.com/
	}
	public static void tjssc(){
		//方法1：优先
		long d = new Date().getTime();
		String url = "http://www.tjflcpw.com/xml/sscPrize.xml?"+d;
		String c = TestWinNumberRequest_DEL.getWebContentByURL(url);
		System.out.println(c);
		//方法2：http://www.tjflcpw.com/cadeFrame/ssc/fivestars.html
	}
	//上海时时乐
	public static void shssl(){
		//
		//每天销售23期，每天上午10：30至晚上22：00，每半小时开奖一次
		String c = TestWinNumberRequest_DEL.getWebContentByURL("http://cp.swlc.sh.cn/cams/xml/award_02.xml");
		System.out.println(c);
	}
	//江西时时彩
	public static void jxssc(){
		//时时彩开奖时间【 9：00至23:10，每天开奖84次】
		//http://www.shishicai.cn/jxssc/
		String c = TestWinNumberRequest_DEL.getWebContentByURL("http://www.jxfczx.cn/report/SSC_WinMessage.aspx");
		//System.out.println(c.substring(356));
		String res = betweenOpt(c.substring(356),"<span>开奖详细</span>",">>> 今天的详细数据");
		System.out.println(res);
	}
	//新疆时时彩
	public static void xjshishicai(){
		String c = TestWinNumberRequest_DEL.getWebContentByURL("http://www.xjflcp.com/ssc");
		//System.out.println(c.substring(356));
		String res = betweenOpt(c.substring(356),"<td width=\"60\">开奖号码</td","rget=\"_blank\">开奖号码走势图</a");
		System.out.println(res);
	}
	
	//重庆时时彩
	public static void cqshishicai(){
		String c = TestWinNumberRequest_DEL.getWebContentByURL("http://www.cqcp.net/ajaxhttp/game/getopennumber.aspx?idMode=3002&iCount=1");
		// 131130117期
		System.out.println(c.substring(356));
		String res = betweenOpt(c.substring(356),"131201017</li><li style='width:80px;'>","</li><li style='width:50px;'>");
		
		System.out.println(res);
	}
	public static String getWebContentByURL(String url) {
		HttpClient httpClient = new HttpClient();
		HttpClientParams clientParams = new HttpClientParams();
		clientParams.setParameter("http.useragent",
				"Mozilla/4.0 (compatible; FIREFOX 9.0; IBM AIX 5)");
		// httpClient.getHttpConnectionManager().getParams().setSoTimeout(30 *
		// 1000);
		clientParams.setHttpElementCharset("UTF-8");
		HttpState httpState = new HttpState();
		httpClient.setParams(clientParams);
		httpClient.getParams().setParameter(
				HttpClientParams.HTTP_CONTENT_CHARSET, "UTF-8");
		httpClient.setState(httpState);
		clientParams.setVersion(HttpVersion.HTTP_1_1);
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		try {
			int statusCode = httpClient.executeMethod(getMethod);
			// System.out.println("获取图片状态："+statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			// 读取内容
			String resStr = getMethod.getResponseBodyAsString();
			//System.out.println(resStr);
			return resStr;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String betweenOpt(String source, String s, String e) {
		String[] arr1 = source.split(e);
		int p = arr1[0].lastIndexOf(s);
		if (p != -1) {
			return arr1[0].substring(p + s.length());
		} else {
			return arr1[0];
		}
	}
}
