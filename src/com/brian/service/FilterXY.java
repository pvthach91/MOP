package com.brian.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.brian.unit.CusAccessObjectUtil;

/**
 * Filter(system power)
 * 
 * @author XY
 * 
 */
public class FilterXY implements Filter {
	public static boolean logFlag = false;
	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(final ServletRequest req, final ServletResponse res,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hres = (HttpServletResponse) res;
		String currentURL = hreq.getRequestURI();
		/**
		 * 追踪每个ip用户每分钟访问的次数2016-05-09
		 */
		if(logFlag){
			
			Map<String,String> ip_times = (Map<String,String>)hreq.getSession().getServletContext().getAttribute("USER_IP_TIMES");
			Long totalTimes = (Long)hreq.getSession().getServletContext().getAttribute("USER_TOTALTIMES");
			String curIp = CusAccessObjectUtil.getIpAddress(hreq);
			if(totalTimes==null||totalTimes<1L){
				totalTimes = 1L;
			}
			if(ip_times==null){
				ip_times = new HashMap<String,String>();
				ip_times.put(curIp, "1|");
				String times = ip_times.get(curIp);
			}else{
				String times = ip_times.get(curIp);
				if(StringUtils.isEmpty(times)){
					ip_times.put(curIp, "1|");
				}else{
					String arr[] = times.split("\\|");
					String last = arr[arr.length-1];
					int lastlength = last.length();
					last = (Integer.parseInt(last)+1)+"";
					times = times.substring(0,times.length()-lastlength-1)+last+"|";
					ip_times.put(curIp, times);
				}
			}
			hreq.getSession().getServletContext().setAttribute("USER_IP_TIMES", ip_times);
			hreq.getSession().getServletContext().setAttribute("USER_TOTALTIMES", totalTimes+1);
		}
		if(currentURL.indexOf("/a4j/")>=0){
			chain.doFilter(req, res);
			return;
		}
		if(currentURL.indexOf("/loginReg.shtml")>=0){
			chain.doFilter(req, res);
			return;
		}
		// 忘记密码
		if(currentURL.indexOf("/loginForgetPwd.shtml")>=0){
			chain.doFilter(req, res);
			return;
		}
		if(currentURL.indexOf("/reg.shtml")>=0){
			chain.doFilter(req, res);
			return;
		}
		if(currentURL.indexOf("/YLFX")>=0){
			chain.doFilter(req, res);
			return;
		}
		if(currentURL.indexOf("/login.shtml")>=0){
			/**
			Object obj = hreq.getSession().getAttribute("LoginBean");
			if(obj==null){
				if(hreq.getParameterNames().hasMoreElements()){
					String uString = "http://" + hreq.getServerName() + ":" + hreq.getServerPort() + "/login.shtml";
					hres.sendRedirect(uString);
					return;
				}
			}
			*/
			chain.doFilter(req, res);
			return;
		}
		Object _user = hreq.getSession().getAttribute("USERINFO");
		if (_user == null) {
				String uString = "http://" + hreq.getServerName() + ":" + hreq.getServerPort() + "/login.shtml";
				hres.sendRedirect(uString);
				return;
		}
		chain.doFilter(req, res);
	}
	public void destroy() {
	}
}