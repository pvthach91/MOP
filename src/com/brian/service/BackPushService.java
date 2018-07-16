package com.brian.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 推送对象数据包
 * @author Brian
 * 后台主动推送【系统消息列表】,字典更新后【字典表】，任务表【Schedule对象】,【关闭某个子系统】
 */
public class BackPushService extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		out.println("success");
		out.flush();
		out.close();
		//System.out.println("LotteryService GET METHOD");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("success");
		out.flush();
		out.close();
	}
}
