package com.brian.game;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.brian.item.UserItem;
import com.brian.unit.CommonMethod;

public class GameService extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Logger logs = Logger.getLogger(GameService.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("GameService GET METHOD");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream in = request.getInputStream();
		String sourcecode = CommonMethod.inputStream2String(in);
		String strdata = sourcecode;
		try{
			strdata = CommonMethod.Decode(strdata);
			
			UserItem ui = (UserItem)request.getSession().getAttribute("USERINFO");
			String ip = CommonMethod.getIpAddress(request);
			if(ui==null || ui.getLoginname()==null){
				//String ip = request.getRemoteAddr();
				logs.error("Game提交注单,未登录,IP："+ip+",内容："+strdata);
				return;
			}
			//1、登录判断
			//2、判断格式是否合法
			//3、投注的期号是否过期，追号是否过期
			//String[] params = strdata.split("&");
			List<SelectItem> list = CommonMethod.parseParam(sourcecode);
			if(list==null){
				return;
			}
			String res = null;
			//res = K3Logic.save(list);
			String flag = null;
			for(SelectItem si : list){
				if("action".equals(si.getLabel())){
					flag = si.getValue().toString();
					break;
				}
			}
			//System.out.println("收到指令："+ui.getLoginname()+",内容："+strdata);
			if(flag==null){
				return;
			}else if("dice_bet".equals(flag)){
				//快三投注，骰宝投注
				res = K3Logic.K3Save(list,ui,strdata,ip);
			}else if("showhand_buybase".equals(flag)){
				//港式五张
				//res = G5Logic.K3Save(list,ui,strdata,ip);
			}else{
				return;
			}
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println(res);
			out.flush();
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public Logger getLogs() {
		return logs;
	}
	public void setLogs(Logger logs) {
		this.logs = logs;
	}
}
