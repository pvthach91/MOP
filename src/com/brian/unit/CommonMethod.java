package com.brian.unit;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brian.item.UserItem;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Timestamp;

public class CommonMethod {
	public static String deftime = null;
	public static void PageJump(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static String GetCookies(String name){
		ExternalContext  excontext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> cookies = excontext.getRequestCookieMap();
		Cookie cookie = (Cookie) cookies.get(name);
		if(cookie==null){
			return null;
		}else{
			return cookie.getValue();
		}
	}
	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	public static void AddCookies(String name,String value){
		try{
			ExternalContext  excontext = FacesContext.getCurrentInstance().getExternalContext();
			HttpServletResponse  response  = (HttpServletResponse)excontext.getResponse();
			Cookie cookie = new Cookie(name, value);
			cookie.setMaxAge(3600*72);
			cookie.setPath("/");
			response.addCookie(cookie);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * get post request parameter
	 *
	 * @param str
	 * @return
	 */
	public static String getParam(String str) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(str);
	}

	public static boolean putToSession(String str, Object obj) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(str, obj);
		return true;
	}

	public static boolean existSession(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey(key);
	}

	public static void sessionRemove(String key) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove(key);
	}
	public static UserItem getCurUser() {
		UserItem ui = (UserItem) getFromSession("USERINFO");
		return ui;
	}
	/**
	 * 从session中获�?
	 *
	 * @param key
	 * @return
	 */
	public static Object getFromSession(String key) {
		if(FacesContext.getCurrentInstance()==null){
			return null;
		}
		/**
		if(FacesContext.getCurrentInstance().getExternalContext()==null){
			return null;
		}
		*/
		if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey(key)){
			return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
		}else{
			return null;
		}
	}
	/**
	 * 从session里面获取参数
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object getFromSession(HttpServletRequest request,String key) {
		Object obj=request.getSession().getAttribute(key);
		return obj;
	}

	public static Object getSession(String key) {
		return getFromSession(key);
	}

	public static boolean SessionExist(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey(key);
	}

	public static String Encode(String str) throws Exception {
		if (CommonMethod.isEmpty(str)) {
			return "";
		} else {
			return java.net.URLEncoder.encode(str, "utf8");
		}
	}

	public static String Decode(String str) throws Exception {
		if (CommonMethod.isEmpty(str)) {
			return "";
		} else {
			return java.net.URLDecoder.decode(str, "utf8");
		}
	}

	/**
	 * 获得IP地址
	 *
	 * @return
	 */
	public static String getIpAddress() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
		// return ((HttpServletRequest)
		// FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
	}
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * Old Method(not commend) date conversion string
	 *
	 * @param date
	 * @param type
	 * @return
	 */
	public static String strToDate(Date date, String type) {
		try {
			Format formatter = new SimpleDateFormat(type);
			return formatter.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * date conversion string
	 *
	 * @param date
	 * @param type
	 * @return
	 */
	public static String dateToStr(Date date, String type) {
		try {
			Format formatter = new SimpleDateFormat(type);
			return formatter.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * String format to date type default time format : yyyyMMddHHmmss
	 *
	 * @param date
	 * @return
	 */
	public static Date strToDate(String date) {
		try {
			return strToDate(date, "yyyyMMddHHmmss");
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Date strToDate(String date, String type) {
		try {
			Date result = null;
			String parse = type;
			DateFormat format = new SimpleDateFormat(parse);
			result = format.parse(date);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/*
	 * public static void pageJump(String url) { try{ String uString =
	 * "http://"+
	 * InitServlet.getServerIP()+":"+InitServlet.getPort()+InitServlet.
	 * getAppName()+url;
	 * FacesContext.getCurrentInstance().getExternalContext().redirect(uString);
	 * } catch(Exception ex) { ex.printStackTrace(); } }
	 */
	/**
	 * format switch time to time
	 *
	 * @param time
	 * @param sourceType
	 * @param resultType
	 * @return
	 */
	public static String TimeToTime(String time, String sourceType,
			String resultType) {
		return strToDate(strToDate(time, sourceType), resultType);
	}

	/**
	 * string array to string List
	 *
	 * @param arr
	 * @return
	 */
	public static List<String> arrToList(String[] arr) {
		if (arr == null)
			return null;
		List<String> al = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			al.add(arr[i]);
		}
		return al;
	}

	public static String GetCurDateSub3s(String type) {
		try {
			String strdate = "";
			Date currentTime = new Date();
			SimpleDateFormat format0 = new SimpleDateFormat(type);
			strdate = format0.format(currentTime.getTime()-3000);
			return strdate;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	/**
	 * get current date
	 *
	 * @param type
	 * @return
	 */
	public static String GetCurDate(String type) {
		try {
			String strdate = "";
			Date currentTime = new Date();
			SimpleDateFormat format0 = new SimpleDateFormat(type);
			strdate = format0.format(currentTime);
			return strdate;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static long getMilliSecond(Date d1, Date d2) throws Exception {
		// System.out.println(new Date(System.currentTimeMillis()).getTime());
		Date date1 = d1;// new SimpleDateFormat("yyyyMMddHHmmss").parse(d1);
		Date date2 = d2;// new SimpleDateFormat("yyyyMMddHHmmss").parse(d2);
		long l = date1.getTime() - date2.getTime() > 0 ? date1.getTime()
				- date2.getTime() : date2.getTime() - date1.getTime();
		return l;
	}

	/**
	 * 判断是否为null
	 *
	 * @param str
	 * @return
	 */
	public static String isNull(String str) {
		if (str == null)
			return "";
		else
			return str;
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 自定义函�? 函数功能:得到两数值直接对比后的百分比. Num1/Num2-->百分�?
	 *
	 * @param String
	 *            Num1(数�?1),String Num2(数�?2),int point(保留小数�?
	 * @return float flPer
	 */
	public static float getPerCent(String Num1, String Num2, int point) {
		if (Float.parseFloat(Num1) == 0f)
			return 0;
		float flPer = 0, flTemp = 0;
		int intTemp = 1, intIsRound = 0;
		if (Float.parseFloat(Num2) != 0f)
			flTemp = Float.parseFloat(Num1) / Float.parseFloat(Num2);
		flPer = flTemp * 100;// 得出百分比�?

		intIsRound = (int) flPer;
		if ((flPer - intIsRound) != 0) {
			while (point != 0) {
				intTemp *= 10;
				point--;
			}
			flPer = (float) Math.round(flPer * intTemp) / intTemp;// 四舍五入,保留point位小�?
			return flPer;
		} else {
			return (int) intIsRound;
		}
	}

	/**
	 * 两个时间的差�?
	 *
	 * @param sTime
	 *            �?��时间(2010-)
	 * @param eTime
	 *            结束时间
	 * @param inTimeType
	 *            输入时间格式
	 * @param rsTimeType
	 *            返回值类�?
	 * @return
	 * @throws Exception
	 */
	public static int getTimeDiffe(String sTime, String eTime,
			String inTimeType, String rsType) throws Exception {
		DateFormat format = new SimpleDateFormat(inTimeType);
		Date d1 = format.parse(sTime);
		Date d2 = format.parse(eTime);
		long between = (d2.getTime() - d1.getTime()) / 1000;
		if (rsType.toLowerCase().equals("dd")) {
			return (int) (between / (24 * 3600));
		} else if (rsType.toUpperCase().equals("HH")) {
			return (int) (between / 3600);
		} else if (rsType.equals("mm")) {
			return (int) (between / 60);
		} else if (rsType.toLowerCase().equals("ss")) {
			return (int) between;
		}
		// 默认返回天数
		return (int) (between / (24 * 3600));
	}

	/**
	 * 计算时间差，时间间隔（输出时间格式和输入时间格式�?���?
	 *
	 * @param inTime
	 *            输入时间�?010-06-10 11:22:44�?
	 * @param inTimeType
	 *            输入时间格式(yyyy-MM-dd HH:mm:ss)
	 * @param space
	 *            间隔(-2)
	 * @param type
	 *            时间差时间间隔类�?dd)
	 * @return
	 * @throws Exception
	 */
	public static String getTimeSpace(String inTime, String inTimeType,
			int space, String type) throws Exception {
		return getTimeSpace(inTime, inTimeType, space, type, inTimeType);
	}

	/**
	 * 计算时间差，时间间隔
	 *
	 * @param inTime
	 *            输入时间�?010-06-10 11:22:44�?
	 * @param inTimeType
	 *            输入时间格式(yyyy-MM-dd HH:mm:ss)
	 * @param space
	 *            间隔(-2)
	 * @param type
	 *            时间差时间间隔类�?dd)
	 * @param outTimeType
	 *            输出时间格式(MM-dd)
	 * @return (06-08)
	 * @throws Exception
	 */
	public static String getTimeSpace(String inTime, String inTimeType,
			int space, String type, String outTimeType) throws Exception {
		Date curDate = null;
		DateFormat format = new SimpleDateFormat(inTimeType);
		curDate = format.parse(inTime);
		Calendar CurCalendar = Calendar.getInstance();
		CurCalendar.setTime(curDate);
		if (type.toLowerCase().equals("yyyy"))
			CurCalendar.add(Calendar.YEAR, space);
		else if (type.equals("MM"))
			CurCalendar.add(Calendar.MONTH, space);
		else if (type.toLowerCase().equals("dd"))
			CurCalendar.add(Calendar.DATE, space);
		else if (type.toUpperCase().equals("HH"))
			CurCalendar.add(Calendar.HOUR, space);
		else if (type.equals("mm"))
			CurCalendar.add(Calendar.MINUTE, space);
		else if (type.toLowerCase().equals("mi"))
			CurCalendar.add(Calendar.MINUTE, space);
		else if (type.toLowerCase().equals("ss"))
			CurCalendar.add(Calendar.SECOND, space);
		else
			// 默认�?�?
			CurCalendar.add(Calendar.DATE, space);
		CurCalendar.getTime();

		Format formatter = new SimpleDateFormat(outTimeType);
		return formatter.format(CurCalendar.getTime());
	}

	/**
	 * 文件导出
	 *
	 * @param filetype
	 * @param context
	 */
	public static void expFile(String fileName, String context) {
		expFile(fileName, "application/x-download", context);
	}

	public static void expFile(String fileName, String fileType, String context) {
		try {
			byte[] y = context.getBytes("utf-8");
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.resetBuffer();
			ServletOutputStream servletOutputStream = httpServletResponse
					.getOutputStream();
			httpServletResponse.setCharacterEncoding("GB2312");
			// httpServletResponse.setHeader("Content-disposition","attachment; filename=tempfile.xls");
			httpServletResponse.setHeader("Content-disposition",
					"attachment; filename=" + fileName);
			httpServletResponse.setContentLength(y.length);
			httpServletResponse.setContentType(fileType);
			servletOutputStream.write(y);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void expFile(String fileName, String fileType, byte[] context) {
		try {
			byte[] y = context;
			if (context == null)
				return;
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.resetBuffer();
			ServletOutputStream servletOutputStream = httpServletResponse
					.getOutputStream();
			httpServletResponse.setCharacterEncoding("GB2312");
			// httpServletResponse.setHeader("Content-disposition","attachment; filename=tempfile.xls");
			httpServletResponse.setHeader("Content-disposition",
					"attachment; filename=" + fileName);
			httpServletResponse.setContentLength(y.length);
			httpServletResponse.setContentType(fileType);
			servletOutputStream.write(y);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void expFile(String fileName, String fileType, Blob context) {
		try {
			if (context == null)
				return;
			InputStream input = context.getBinaryStream();
			// byte[] y = context;
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.resetBuffer();
			ServletOutputStream servletOutputStream = httpServletResponse
					.getOutputStream();
			httpServletResponse.setCharacterEncoding("GB2312");
			// httpServletResponse.setHeader("Content-disposition","attachment; filename=tempfile.xls");
			fileName = fileName.substring(8);
			httpServletResponse.setHeader("Content-disposition",
					"attachment; filename=" + fileName);
			// httpServletResponse.setContentLength(y.length);
			httpServletResponse.setContentType(fileType);

			byte[] b = new byte[1024];
			int len = 0;
			while ((len = input.read(b)) != -1) {
				servletOutputStream.write(b, 0, len);// .write(y);
			}
			servletOutputStream.close();
			input.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String md5(String str) {
		String s = str;
		if (s == null) {
			return "";
		} else {
			String value = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
				// Logger.getLogger(md5.class.getName()).log(Level.SEVERE, null,
				// ex);
			}
			sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
			try {
				value = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
			} catch (Exception ex) {
			}
			return value;
		}
	}

	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static Date fristDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// cal.roll(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	public static String getRandomNubmer(int num) {
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };
		Random random = new Random();
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < num; i++) {
			String strRand = String.valueOf(codeSequence[random
					.nextInt(codeSequence.length)]);
			str.append(strRand);
		}
		return str.toString();
	}

	public static String getRandomLetter(int num) {
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9' };
		Random random = new Random();
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < num; i++) {
			String strRand = String.valueOf(codeSequence[random
					.nextInt(codeSequence.length)]);
			str.append(strRand);
		}
		return str.toString();
	}

	public static String TimestampFormat(Timestamp ts, String type) {
		Date dt = new Date(ts.getTime());
		return CommonMethod.dateToStr(dt, type);
	}

	public static String[] getDateOfThisWeek() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int index = cal.get(Calendar.DAY_OF_WEEK); // 今天是本周的第几�?
		// 转成中国的习�?如果是第�?��,则转化为第七�?星期天外国为�?��的第�?��,而中国为每周的最后一�?
		if (index == 1)
			index = 8;
		cal.add(Calendar.DATE, -(index - 2));
		String start = (sdf.format(cal.getTime()));
		cal.add(Calendar.DATE, 6);
		String end = (sdf.format(cal.getTime()));
		String[] result = new String[] { start, end };
		return result;
	}

	public static boolean checkInt(String str) {
		String NUMBER_PATTERN = "[0-9]*";
		if (str.matches(NUMBER_PATTERN)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test
	 * @param str
	 * @return
	 */
	public static String FilterStr(String str){
		str = str.replaceAll("'", "”");
		str = str.replaceAll("--", "－－");
		str = str.replaceAll("%", "\\%");
		str = str.replaceAll("<", "《");
		str = str.replaceAll(">", "》");
		str = str.replaceAll("&", "＆");
		str = str.replaceAll("\\\\", "＼");
		return str;
	}

	public static boolean checkFloat(String str) {
		//String NUMBER_PATTERN = "^[0-9]+(.[0-9]{0,1})?$";
		String NUMBER_PATTERN = "^[0-9]+(.[0-9]{0,2})?$";
		if (str.matches(NUMBER_PATTERN)) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean checkString(String str){
		String PATTERN="^[A-Za-z0-9]+$";
		if (str.matches(PATTERN)) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean checkFloat(String str,int n) {
		String NUMBER_PATTERN = "^[0-9]+(.[0-9]{0,"+n+"})?$";
		if (str.matches(NUMBER_PATTERN)) {
			return true;
		} else {
			return false;
		}
	}
	public static List<SelectItem> parseParam(String reqStr)throws Exception{
		if(reqStr==null || reqStr.length()<1){
			return null;
		}
		String[] args = reqStr.split("&");
		List<SelectItem> list = new ArrayList<SelectItem>();
		for(int i=0;i<args.length;i++){
			String s = args[i];
			String[] a = s.split("=");
			if(a.length!=2){
				continue;
			}
			//key:a[0]  value:a[1]
			a[1] = CommonMethod.Decode(a[1]);
			a[0] = CommonMethod.Decode(a[0]);
			list.add(new SelectItem(a[1],a[0]));
		}
		return list;
	}
	public static String getParam(HttpServletRequest request,String str) {
		return request.getParameter(str);
	}

	/**
	 * 获取post请求参数
	 * @param list
	 * @param key
	 * @return
	 */
	public static String getParam(List<SelectItem> list,String key) {
		for(SelectItem s:list){
			if(key.equals(s.getLabel())){
				return s.getValue().toString();
			}
		}
		return null;
	}

	public static void sessionRemove(HttpServletRequest request,String key) {
		request.getSession().removeAttribute(key);
	}
	public static void main(String[] args) {
		/**
		for (int i = 0; i < 10; i++) System.out.println(getRandomLetter(5));
		String[] arr = getDateOfThisWeek();
		System.out.println(arr[0]);
		System.out.println(arr[1]);
		*/
		//Date d = lastDayOfMonth(new Date());
		//System.out.println(dateToStr(d,"yyyy-MM-dd HH:mm:ss"));
		//System.out.print(checkFloat("12.05"));
		//System.out.print(FilterStr("\\select"));
		System.out.print(checkEmail("asf-wefa@126.com"));
	}
	public static boolean checkEmail(String email){
		if(email==null){
			return false;
		}
		//String eg = "(?=^[\\w.@]{6,50}$)\\w+@\\w+(?:\\.[\\w]{2,3}){1,2}";OLD
		//String eg ="^[a-zA-Z0-9_.]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]{1,}$";NEW
		String eg ="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_]+(?:\\.[\\w]{2,3}){1,2}";
		//String eg2 = "[^ \t]{3,50}+@";
		return email.matches(eg);
	}
	/**备用：有CheckString代替
	public static boolean checkUserId(String userid){
		//String eg = "([A-Fa-f]([\\d]|1[01]))|(([\\d]|1[01])[A-Fa-f])";
		//String eg="[a-fA-F]([0-9]|1[0-1])|([0-9]|1[0-1])[a-fA-F]";
		//String eg="[a-zA-Z]\\d";
		String eg = "^[\\da-zA-Z]*$";
		return userid.matches(eg);
	}
	*/
	public static String winNumberToString(Object arg2) {
		if(arg2==null){
			return "";
		}
		String s = arg2.toString();
		if(s==null || "".equals(s.trim())){
			return "";
		}
		if(s.indexOf(",")!=-1){
			return s;
		}
		String result =s;
		if(s.length()==10){
			result = s.substring(0, 2)+","+s.substring(2, 4)+","+s.substring(4, 6)+","+s.substring(6, 8)+","+s.substring(8, 10);
		}else if(s.length()==5){
			result = s.substring(0, 1)+","+s.substring(1, 2)+","+s.substring(2, 3)+","+s.substring(3, 4)+","+s.substring(4, 5);
		}else if(s.length()==3){
			result = s.substring(0, 1)+","+s.substring(1, 2)+","+s.substring(2, 3);
		}
		return result;
	}

	/**
	 * Object转换成JSON数据 cf数组是不转的属性
	 * @param bean
	 * @param cf
	 * @return
	 * @throws MapperException
	 */
	public static JSONObject objToJSON(Object obj,String[] cf){
		JSONObject jsonObject=new JSONObject();

		if(cf!=null&&cf.length>0){
			JsonConfig config = new JsonConfig();
			config.setExcludes(cf);
			jsonObject = JSONObject.fromObject(obj,config);
		}else{
			jsonObject = JSONObject.fromObject(obj);
		}
		return jsonObject;
	}

	public static JSONObject beanToJSON(Object bean,String[] cf){
		   JSONObject jsonObject=new JSONObject();

		   if(cf!=null&&cf.length>0){
			   JsonConfig config = new JsonConfig();
			   config.setExcludes(cf);
			   jsonObject = JSONObject.fromObject(bean,config);
		   }else{
			   jsonObject = JSONObject.fromObject(bean);
		   }
		   return jsonObject;
	   }

	public static void printData(HttpServletResponse response,String jsonStr) throws IOException{
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jsonStr);
		out.flush();
		out.close();
	}
}
