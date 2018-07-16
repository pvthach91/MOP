package com.brian.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class PostData {
	public void test(){
		String urlStr = "http://www.ph158nb.com:999/highgame/?controller=game&action=play";
		String params = "lotteryid=1&flag=read";
		String x = sendUrl(urlStr, params);
		System.out.println(x);
	}
	public String sendUrl(String urlStr, String params) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(urlStr);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				//log.error(this.getClass().getName(), ex);
			}
		}
		return result.toString();
	}

	public static void main(String args[]) {
		//String urlStr = "http://www.ph158nb.com:999/highgame/?controller=game&action=play";
		//String params = "lotteryid=1&flag=read";
		String urlStr = "http://www.ph158nb.com:999/highgame/?controller=game&action=play&curmid=50";
		String params = "lotteryid=1&flag=gethistorycached";
		String x = new PostData().sendUrl(urlStr, params);
		System.out.print(x);
	}
}
