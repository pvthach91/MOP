package com.brian.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.brian.game.k3.K3Method;
import com.brian.item.CurScheduleItem;
import com.brian.item.DictItem;
import com.brian.item.IpGlobalItem;
import com.brian.item.LoginTimeItem;
import com.brian.item.ModelItem;
import com.brian.item.NewWinListItem;
import com.brian.item.OnlineItem;
import com.brian.item.ScheduleItem;
import com.brian.item.SysConfigItem;
import com.brian.item.SysMsgItem;
import com.brian.item.UserItem;
import com.brian.item.YLFXItem;
import com.brian.unit.CommonMethod;

public class InitData {
	public static int[] LOTERYALL={1,2,3,4,5,6,8,9,11,12,14,15,16,17,18,80,81,82,83,85,45};  //菲律宾1.5分彩   2016-07-06
	public static List<DictItem> dict = new ArrayList<DictItem>();
	public static List<SelectItem> lotterylist = new ArrayList<SelectItem>();
	public static List<SelectItem> statuslist = new ArrayList<SelectItem>();
	public static List<SelectItem> balanceCList = new ArrayList<SelectItem>();

	public static String pcClientUrl = "";//PC客服端下载路径
	public static String liveChatUrl = "";//在线客服链接

	public static List<ModelItem> model = new ArrayList<ModelItem>();
	public static String k3method1 ="";
	public static String k3method2="";
	public static String k3method3="";
	public static String k31fmethod ="";  //快骰一分   2016-04-08
	public static String marksixmethod ="";//六合彩   2016-04-29
	public static String marksix1fmethod ="";//六合彩1分   2016-05-23
	public static String pk10method ="";//pk10   2016-05-30
	public static String pk101fmethod ="";//pk10官彩   2016-06-15
	public static String hg1_5method ="";//韩国1.5分彩   2016-07-06
	public static List<ScheduleItem> schedule = new ArrayList<ScheduleItem>();
	public static List<CurScheduleItem> curSchedule = new ArrayList<CurScheduleItem>();
	//登录次数限制
	public static List<LoginTimeItem> loginIpList = new ArrayList<LoginTimeItem>();
	//资金密码出错次数限制
	public static List<LoginTimeItem> checkPwdIpList = new ArrayList<LoginTimeItem>();
	public static List<OnlineItem> onlinelist = new ArrayList<OnlineItem>();
	public static List<SysMsgItem> msglist = new ArrayList<SysMsgItem>();
	
	public static List<UserItem> loginlist = new ArrayList<UserItem>();
	
	public static List<YLFXItem> ylfxlist = new ArrayList<YLFXItem>();
	
	public static List<NewWinListItem> newWinList = new ArrayList<NewWinListItem>();
	public static List<SysConfigItem> configlist = new ArrayList<SysConfigItem>();
	public static List<SelectItem> ipsBankList = new ArrayList<SelectItem>();
	
	public static  List<IpGlobalItem> ipRjlist = new ArrayList<IpGlobalItem>();
	public static List<IpGlobalItem> ipAllow = new ArrayList<IpGlobalItem>();
	public static int ids = 100000;
	@SuppressWarnings("unchecked")
	public static void init(){
		try{
			List<Object> obj= PostDBData.DBPOST(InitServlet.WSURL, "UIInit", 5);
			dict = (List<DictItem>)obj.get(0);
			model = (List<ModelItem>)obj.get(1);
			msglist = (List<SysMsgItem>)obj.get(2);
			//schedule = (List<ScheduleItem>)obj.get(3);
			configlist = (List<SysConfigItem>)obj.get(3);
			ipsBankList  = (List<SelectItem>)obj.get(4);
			
			//快三初始化
			List<K3Method> methods1 = new ArrayList<K3Method>();
			List<K3Method> methods2 = new ArrayList<K3Method>();
			List<K3Method> methods3 = new ArrayList<K3Method>();
			//快骰一分
			List<K3Method> methods4 = new ArrayList<K3Method>();
			//六合彩 
			List<K3Method> methods5 = new ArrayList<K3Method>();
			//六合彩 1分
			List<K3Method> methods6 = new ArrayList<K3Method>();
			//pk10初始化投注限额
			List<K3Method> methods7 = new ArrayList<K3Method>();
			//pk10官彩初始化投注限额
			List<K3Method> methods8 = new ArrayList<K3Method>();
			//韩国1.5分彩初始化投注限额
			List<K3Method> methods9 = new ArrayList<K3Method>();
			for(ModelItem mi:model){
				if(mi.getLotteryid().equals("60")){
					K3Method  km = new K3Method();
					km.setMax(mi.getMaxbet()+"");
					km.setName(mi.getCheckflag());
					methods1.add(km);
				}
				if(mi.getLotteryid().equals("61")){
					K3Method  km = new K3Method();
					km.setMax(mi.getMaxbet()+"");
					km.setName(mi.getCheckflag());
					methods2.add(km);
				}
				if(mi.getLotteryid().equals("62")){
					K3Method  km = new K3Method();
					km.setMax(mi.getMaxbet()+"");
					km.setName(mi.getCheckflag());
					methods3.add(km);
				}
				//快骰一分
				if(mi.getLotteryid().equals("80")){
					K3Method  km = new K3Method();
					km.setMax(mi.getMaxbet()+"");
					km.setName(mi.getCheckflag());
					methods4.add(km);
				}
				//六合彩  
				if(mi.getLotteryid().equals("81")){
					K3Method  km = new K3Method();
					km.setMax(mi.getMaxbet()+"");
					km.setName(mi.getCheckflag());
					methods5.add(km);
				}
				//六合彩 1分
				if(mi.getLotteryid().equals("82")){
					K3Method  km = new K3Method();
					km.setMax(mi.getMaxbet()+"");
					km.setName(mi.getCheckflag());
					methods6.add(km);
				}
				//pk10
				if(mi.getLotteryid().equals("83")){
					K3Method  km = new K3Method();
					km.setMax(mi.getMaxbet()+"");
					km.setName(mi.getCheckflag());
					methods7.add(km);
				}
				//pk10官彩
				if(mi.getLotteryid().equals("85")){
					K3Method  km = new K3Method();
					km.setMax(mi.getMaxbet()+"");
					km.setName(mi.getCheckflag());
					methods8.add(km);
				}
				//韩国1.5分彩
				if(mi.getLotteryid().equals("3")){
					K3Method  km = new K3Method();
					km.setMax(mi.getMaxbet()+"");
					km.setName(mi.getCheckflag());
					methods9.add(km);
				}
			}
			JSONArray jsonObject = JSONArray.fromObject(methods1.toArray());
			k3method1 = jsonObject.toString();
			
			jsonObject = JSONArray.fromObject(methods2.toArray());
			k3method2 = jsonObject.toString();
			
			jsonObject = JSONArray.fromObject(methods3.toArray());
			k3method3 = jsonObject.toString();
			
			jsonObject = JSONArray.fromObject(methods4.toArray());
			k31fmethod = jsonObject.toString();
			
			jsonObject = JSONArray.fromObject(methods5.toArray());
			marksixmethod = jsonObject.toString();
			
			jsonObject = JSONArray.fromObject(methods6.toArray());
			marksix1fmethod = jsonObject.toString();
			
			jsonObject = JSONArray.fromObject(methods7.toArray());
			pk10method = jsonObject.toString();
			
			jsonObject = JSONArray.fromObject(methods8.toArray());
			pk101fmethod = jsonObject.toString();
			
			jsonObject = JSONArray.fromObject(methods9.toArray());
			hg1_5method = jsonObject.toString();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			for(int i=0;i<LOTERYALL.length;i++){
				System.out.println("Start loading...lottery-"+LOTERYALL[i]);
				List<Object> olist = new ArrayList<Object>();
				olist.add("scheduleN");
				olist.add(LOTERYALL[i]);
				List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
				String flag = rlist.get(0).toString();
				if("0".equals(flag)){
					List<Object> sblist = (List<Object>)rlist.get(1);
					schedule.addAll((List<ScheduleItem>)sblist.get(0));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		statuslist = getDictByType("订单状态");
		lotterylist = getDictByType("彩种");
		balanceCList = getDictByType("帐变类型");


		// PC client
		List<SelectItem> pcClientUrls = getDictByType("配置参数");
		if(pcClientUrls.isEmpty()) {
			System.err.println("PC Client link is not set in the database");
			pcClientUrl = "http://baidu.com";
		} else {
			pcClientUrl = (String) pcClientUrls.get(0).getValue();
			System.out.println("PC Client ULR: " + pcClientUrl);
		}

		// Live chat
		List<SelectItem> liveChatUrls = getDictByType("在线客服");
		if(liveChatUrls.isEmpty()) {
			System.err.println("Live chat link is not set in the database");
			liveChatUrl = "http://baidu.com";
		} else {
			liveChatUrl = (String) liveChatUrls.get(0).getValue();
			System.out.println("Live chat ULR: " + liveChatUrl);
		}
		
		//初始化当前期和开奖号
		InitData.initCurrentSchedule();
		//初始化遗漏分析
		initYLFX();
	}
	//初始化字典
	public static void initDict(){
		try{
			List<Object> obj = PostDBData
					.DBPOST(InitServlet.WSURL, "DicQuery", 1);
			dict = (List<DictItem>) obj.get(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static boolean IsForbidIp(String ip){
		if(ip==null){
			return true;
		}
		
		//是否在允许列表中
		for(IpGlobalItem item:ipAllow){
			if(ip.equals(item.getSips().trim())){
				return false;
			}
		}
		long l = IPtoLong(ip);
		if(l==-1){
			return true;
		}
		for(IpGlobalItem item:ipRjlist){
			if(item.getSip()<=l && item.getEip()>=l){
				return true;
			}
		}
		return false;
	}
	public static long IPtoLong(String str){
		if(str==null)
			return -1;
		String[] arr = str.split("\\.");
		if(arr.length==4){
			String sy ="";
			for(String s:arr){
				if(s.length()==1){
					s="00"+s;
				}else if(s.length()==2){
					s="0"+s;
				}
				sy+=s;
			}
			long res = Long.parseLong(sy);
			return res;
		}else{
			return -1;
		}
	}
	@SuppressWarnings("unchecked")
	public static void loadNewMsg(){
		try{
			List<Object> obj= PostDBData.DBPOST(InitServlet.WSURL, "initmsg", 1);
			msglist = (List<SysMsgItem>)obj.get(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public static void loadConfigList(){
		try{
			List<Object> obj= PostDBData.DBPOST(InitServlet.WSURL, "InitConfigList", 1);
			configlist = (List<SysConfigItem>)obj.get(0);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static String getCheckFlagById(int methodid){
		for(ModelItem mi:model){
			if(mi.getMethodid()==methodid){
				return mi.getCheckflag();
			}
		}
		return null;
	}
	public static String getMethodNameById(int methodid){
		for(ModelItem mi:model){
			if(mi.getMethodid()==methodid){
				return mi.getMethodname();
			}
		}
		return methodid+"";
	}
	public synchronized static List<CurScheduleItem> getCurSchedule(){
		return curSchedule;
	}
	
	public static List<SelectItem> getDictByType(String type){
		List<SelectItem> list = new ArrayList<SelectItem>();
		if(type==null){
			return list;
		}
		
		for(DictItem di : dict){
			if(type.equals(di.getType())){
				list.add(new SelectItem(di.getValue(),di.getKey()));
			}
		}
		return list;
	}
	public static String getDictKeyByValue(List<SelectItem> list,String value){
		if(value==null){
			return null;
		}
		for(SelectItem si: list){
			if(value.equals(si.getValue().toString())){
				return si.getLabel();
			}
		}
		return null;
	}
	public static String getDictValue(String type,String key){
		if(type==null || key==null){
			return null;
		}
		for(DictItem di : dict){
			if(type.equals(di.getType()) && key.equals(di.getKey())){
				return di.getValue();
			}
		}
		return null;
	}
	public synchronized static int getids(){
		ids++;
		if(ids>999999){
			ids=100000;
		}
		return ids;
	}
	public static void initYLFX(){
		ylfxlist.clear();
		for(ScheduleItem item:schedule){
			if(item.getWinnumber()==null){
				continue;
			}
			if(item.getStatus()==2 && item.getWinnumber().length()>0){
				YLFXOneByOne(item);
			}
		}
	}
	public static void initCurrentSchedule(){
		//curSchedule
		for (int i = 0; i < LOTERYALL.length; i++) {
			curSchedule.add(new CurScheduleItem(LOTERYALL[i]));
		}

		//计算出当前开奖期
		String nowtime = CommonMethod.GetCurDate("yyyy-MM-dd HH:mm:ss");
		
		for(CurScheduleItem cs:curSchedule){
			for(ScheduleItem item:schedule){
				//ScheduleItem item = schedule.get(i);
				if(cs.getLotteryid()==item.getLotteryid() && item.getEndtime().compareTo(nowtime)>0 && item.getStarttime().compareTo(nowtime)<=0){
					cs.setIssue(item.getIssue());
					cs.setStarttime(item.getStarttime());
					cs.setEndtime(item.getEndtime());
					cs.setSysopentime(item.getSysopentime());
					cs.setSale(item.getSale());
					cs.setLeft(item.getLeft());
					cs.setOpentime(item.getOpentime());
					//计算出上一期
					for(ScheduleItem item2:schedule){
						if(cs.getLotteryid()==item2.getLotteryid() && cs.getStarttime().equals(item2.getEndtime())){
							cs.setStatus(item2.getStatus());
							cs.setWinnumber(item2.getWinnumber());
							cs.setHissysopentime(item2.getSysopentime());
							cs.setHisopentime(item2.getOpentime());
							cs.setHisendtime(item2.getEndtime());
							cs.setHistoryissue(item2.getIssue());
							break;
						}
					}
					break;
				}
			}
		}
	}
	public synchronized static void YLFXOneByOne(ScheduleItem si){
		for(YLFXItem y:ylfxlist){
			if(y.getLotteryid()==si.getLotteryid() && y.getIssue().equals(si.getIssue())){
				return;
			}
		}
		YLFXItem yitem = new YLFXItem();
		yitem.setLotteryid(si.getLotteryid());
		yitem.setWinnumber(si.getWinnumber());
		yitem.setIssue(si.getIssue());
		yitem.setOpentime(si.getOpentime());
		String w="0",q="0",b="0",s="0",g="0",l="0",t="0",sk="0",bk="0",qk="0";
		if(si.getWinnumber().length()==10){
			w = si.getWinnumber().substring(0, 2);
			q = si.getWinnumber().substring(2, 4);
			b = si.getWinnumber().substring(4, 6);
			s = si.getWinnumber().substring(6, 8);
			g = si.getWinnumber().substring(8, 10);
		}else if(si.getWinnumber().length()==5){
			w = si.getWinnumber().substring(0, 1);
			q = si.getWinnumber().substring(1, 2);
			b = si.getWinnumber().substring(2, 3);
			s = si.getWinnumber().substring(3, 4);
			g = si.getWinnumber().substring(4, 5);
		}else if(si.getWinnumber().length()==3){
			b = si.getWinnumber().substring(0, 1);
			s = si.getWinnumber().substring(1, 2);
			g = si.getWinnumber().substring(2, 3);
		}else if((si.getLotteryid()==81||si.getLotteryid()==82)&&!StringUtils.isEmpty(si.getWinnumber())&&si.getWinnumber().length()>0){
			String[] arr = si.getWinnumber().split("\\|");
			w = arr.length>0?arr[0]:"0";
			q = arr.length>1?arr[1]:"0";
			b = arr.length>2?arr[2]:"0";
			s = arr.length>3?arr[3]:"0";
			g = arr.length>4?arr[4]:"0";
			if(arr.length>5){
				String[] arr01 = arr[5].split("&");
				l = arr01.length>0?arr01[0]:"0";
				t = arr01.length>1?arr01[1]:"0";
			}
		}else if((si.getLotteryid()==83 || si.getLotteryid()==85)&&!StringUtils.isEmpty(si.getWinnumber())&&si.getWinnumber().length()>0){
			String[] arr = si.getWinnumber().split(",");
			l = arr.length>0?arr[0]:"0";
			t = arr.length>0?arr[1]:"0";
			qk = arr.length>0?arr[2]:"0";
			bk = arr.length>0?arr[3]:"0";
			sk = arr.length>0?arr[4]:"0";
			w= arr.length>0?arr[5]:"0";
			q = arr.length>0?arr[6]:"0";
			b = arr.length>0?arr[7]:"0";
			s = arr.length>0?arr[8]:"0";
			g = arr.length>0?arr[9]:"0";
//		}else if(si.getLotteryid()==81&&!StringUtils.isEmpty(si.getWinnumber())&&si.getWinnumber().length()>0){
//			String[] arr = si.getWinnumber().split("\\|");
//			w = arr.length>0?arr[0]:"0";
//			q = arr.length>1?arr[1]:"0";
//			b = arr.length>2?arr[2]:"0";
//			s = arr.length>3?arr[3]:"0";
//			g = arr.length>4?arr[4]:"0";
//			if(arr.length>5){
//				String[] arr01 = arr[5].split("&");
//				l = arr01.length>0?arr01[0]:"0";
//				t = arr01.length>1?arr01[1]:"0";
//			}
		}else{
			return;
		}
		yitem.setG(g);
		yitem.setS(s);
		yitem.setB(b);
		yitem.setQ(q);
		yitem.setW(w);
		yitem.setL(l);
		yitem.setT(t);
		yitem.setQk(qk);
		yitem.setBk(bk);
		yitem.setSk(sk);
		//找出前一条记录
		boolean flag = false;
		YLFXItem before = new YLFXItem();
		for(int j=ylfxlist.size()-1;j>=0;j--){
			YLFXItem tmp = ylfxlist.get(j);
			if(tmp.getLotteryid()==si.getLotteryid()){
				before = tmp;
				flag = true;
				break;
			}
		}
		int wn = Integer.parseInt(w);
		int qn = Integer.parseInt(q);
		int bn = Integer.parseInt(b);
		int sn = Integer.parseInt(s);
		int gn = Integer.parseInt(g);
		int ln = Integer.parseInt(l);
		int tn = Integer.parseInt(t);
		int qkn = Integer.parseInt(qk);
		int bkn = Integer.parseInt(bk);
		int skn = Integer.parseInt(sk);
		yitem.setWn(wn);
		yitem.setQn(qn);
		yitem.setBn(bn);
		yitem.setSn(sn);
		yitem.setGn(gn);
		yitem.setLn(ln);
		yitem.setTn(tn);
		yitem.setQkn(qkn);
		yitem.setBkn(bkn);
		yitem.setSkn(skn);
		if(si.getLotteryid()!=81 &&si.getLotteryid()!=82 && si.getLotteryid()!=83 && si.getLotteryid()!=85){
			yitem.setH3hz(bn+sn+gn);
			yitem.setQ3hz(wn+qn+bn);
			if(!flag){
				//无上一条
				yitem.getWw()[wn]=-1;
				yitem.getQw()[qn]=-1;
				yitem.getBw()[bn]=-1;
				yitem.getSw()[sn]=-1;
				yitem.getGw()[gn]=-1;
			}else{
				for(int j=0;j<before.getWw().length;j++){
					if(j==wn){
						yitem.getWw()[j] = -1;
					}else{
						if(before.getWw()[j]==-1){
							yitem.getWw()[j]=1;
						}else{
							yitem.getWw()[j]=before.getWw()[j]+1;
						}
					}
				}
				for(int j=0;j<before.getQw().length;j++){
					if(j==qn){
						yitem.getQw()[j] = -1;
					}else{
						if(before.getQw()[j]==-1){
							yitem.getQw()[j]=1;
						}else{
							yitem.getQw()[j]=before.getQw()[j]+1;
						}
					}
				}
				for(int j=0;j<before.getBw().length;j++){
					if(j==bn){
						yitem.getBw()[j] = -1;
					}else{
						if(before.getBw()[j]==-1){
							yitem.getBw()[j]=1;
						}else{
							yitem.getBw()[j]=before.getBw()[j]+1;
						}
					}
				}
				for(int j=0;j<before.getSw().length;j++){
					if(j==sn){
						yitem.getSw()[j] = -1;
					}else{
						if(before.getSw()[j]==-1){
							yitem.getSw()[j]=1;
						}else{
							yitem.getSw()[j]=before.getSw()[j]+1;
						}
					}
				}
				for(int j=0;j<before.getGw().length;j++){
					if(j==gn){
						yitem.getGw()[j] = -1;
					}else{
						if(before.getGw()[j]==-1){
							yitem.getGw()[j]=1;
						}else{
							yitem.getGw()[j]=before.getGw()[j]+1;
						}
					}
				}
			}
		}
		ylfxlist.add(yitem);
	}
	public static CurScheduleItem getCurScheduleItem(int lotteryid){
		for(CurScheduleItem csi : curSchedule){
			if(csi.getLotteryid()==lotteryid){
				return csi;
			}
		}
		return null;
	}
	public static CurScheduleItem getCurScheduleItem(int lotteryid,String issue){
		for(CurScheduleItem csi : curSchedule){
			if(csi.getLotteryid()==lotteryid && issue.equals(csi.getIssue())){
				return csi;
			}
		}
		return null;
	}
	public static CurScheduleItem getHisScheduleItem(int lotteryid,String issue){
		for(CurScheduleItem csi : curSchedule){
			if(csi.getLotteryid()==lotteryid && issue.equals(csi.getHistoryissue())){
				return csi;
			}
		}
		return null;
	}
	public static int checkLoginTimes(String ip){
		if(ip==null){
			return 0;
		}
		int i=0;
		loginIpList.add(new LoginTimeItem(ip));
		long now = new java.util.Date().getTime();
		for(LoginTimeItem item:loginIpList){
			if(ip.equals(item.getIp())){
				if((now-item.getTime())<1000*300){
					i++;
				}
			}
		}
		for(int j=(loginIpList.size()-1);j>=0;j--){
			LoginTimeItem item = loginIpList.get(j);
			if((now-item.getTime())>1000*350){
				loginIpList.remove(item);
			}
		}
		return i;
	}
	public static int checkPwdTimes(String ip,String loginname){
		if(ip==null){
			return 0;
		}
		int i=0;
		checkPwdIpList.add(new LoginTimeItem(ip,loginname));
		long now = new java.util.Date().getTime();
		for(LoginTimeItem item:checkPwdIpList){
			if(ip.equals(item.getIp()) && loginname.equals(item.getLoginname())){
				if((now-item.getTime())<1000*300){
					i++;
				}
			}
		}
		for(int j=(checkPwdIpList.size()-1);j>=0;j--){
			LoginTimeItem item = checkPwdIpList.get(j);
			if((now-item.getTime())>1000*350){
				checkPwdIpList.remove(item);
			}
		}
		return i;
	}
	public static void clearLoginTimes(String ip){
		if(ip==null){
			return;
		}
		for(int i=loginIpList.size()-1;i>=0;i--){
			LoginTimeItem item = loginIpList.get(i);
			if(ip.equals(item.getIp())){
				loginIpList.remove(item);
			}
		}
	}
}
