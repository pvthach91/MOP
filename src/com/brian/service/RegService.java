package com.brian.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brian.ui.RegBean;
import org.apache.log4j.Logger;

import com.brian.unit.CommonMethod;

//供站外注册
public class RegService extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logs = Logger.getLogger(RegService.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req,res);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("123123");
//		System.out.println(request.getAttribute("flag"));
		InputStream in = request.getInputStream();
		String sourcecode = CommonMethod.inputStream2String(in);
		String strdata = sourcecode;
		//String currentURL=request.getRequestURI();
		//HttpServletResponse response = (HttpServletResponse) res; 
		//TODO: Test
        response.setHeader("Access-Control-Allow-Origin", "*");  
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with"); 
        
		try{
			//2、判断格式是否合法
			List<SelectItem> list = CommonMethod.parseParam(sourcecode);
			if(list==null){
				CommonMethod.printData(response, " {\"msg\":\"参数出错！\"}");
				return;
			}
			String flag ="";
			for(SelectItem si : list){
				if("flag".equals(si.getLabel())){
					flag = si.getValue().toString();
					break;
				}
			}
			//System.out.println("收到指令："+ui.getLoginname()+",内容："+strdata);
			if(flag==null){
				CommonMethod.printData(response, " {\"msg\":\"参数出错！\"}");
				return;
			}

			String[] f=flag.split("_");
			String flag1="";
			String flag2="";
			if(f.length==2){
				flag1=f[0];
				flag2=f[1];
			}else{
				flag1=f[0];
			}
			if("RegBean".equals(flag1)&&flag2.startsWith("regNew")){
				regBeanControl(request, response, list, flag2);
			}else{
				CommonMethod.printData(response, " {\"msg\":\"参数出错！\"}");
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public static void regBeanControl(HttpServletRequest req,HttpServletResponse res,List<SelectItem> list,String flag) throws Exception{
		RegBean regBean=(RegBean)CommonMethod.getFromSession(req,"RegBean");
		if(regBean==null){
			regBean=new RegBean();
		}
		String[] sel= new String[]{"log"};
		//属性赋值
		regBean.setMsg(null);
		for(SelectItem si:list){
			if(si.getLabel().equals("loginname")){
				regBean.setLoginname(si.getValue().toString());
			}else if(si.getLabel().equals("pagesize")){
				regBean.setPwd(si.getValue().toString());
			}else if(si.getLabel().equals("zjpwd")){
				regBean.setZjpwd(si.getValue().toString());
			}else if(si.getLabel().equals("email")){
				regBean.setEmail(si.getValue().toString());
			}
			else if(si.getLabel().equals("nickname")){
				regBean.setNickname(si.getValue().toString());
			}else if(si.getLabel().equals("type")){
				regBean.setType(Integer.parseInt(si.getValue().toString()));
			}else if(si.getLabel().equals("pwd")){
				regBean.setPwd(si.getValue().toString());
			}
		}
		/*if("regNew".equals(flag)){
			regBean.regNew(req,list);
		}else if("reg".equals(flag)){
			regBean.reg(req);
		}else{
		}*/

		regBean.regNew(req,list);
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setDateHeader("Expires", 0);

		CommonMethod.printData(res, CommonMethod.beanToJSON(regBean,sel).toString());
	}
}
