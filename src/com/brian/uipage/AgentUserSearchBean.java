package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.brian.item.AgentPeItem;
import com.brian.item.MathBrian;
import com.brian.item.UserItem;
import com.brian.service.GlobalDefine;
import com.brian.service.InitData;
import com.brian.service.InitServlet;
import com.brian.service.LogXY;
import com.brian.service.PostDBData;
import com.brian.service.UIPage;
import com.brian.unit.CommonMethod;

public class AgentUserSearchBean extends LogXY implements UIPage{
	private String loginname;
	private String samount;
	private String eamount;
	private String fspoint;
	private String fepoint;
	private Integer order;
	private Integer userarea;//直属，全部 
	
	private List<UserItem> reslist = new ArrayList<UserItem>();
	
	private int pagesize = 12;
	private int curpage = 1;
	private int totalpage = 1;
	private int totalrecord = 0;
	
	private UserItem optuser =new UserItem();
	
	private List<SelectItem> selssc = new ArrayList<SelectItem>();
	private List<SelectItem> sel115 = new ArrayList<SelectItem>();
	private List<SelectItem> seldp = new ArrayList<SelectItem>();
	private List<SelectItem> fhsel = new ArrayList<SelectItem>();
	private Integer fhnumber = 0;
	//下线充值金额
	private String tamount="0";
	private String checkpwd="";
	
	private String dptype="dp";
	private String remark="";
	//团队余额
	private String tbalance = "0.0";
	
	private Integer selfid=-1;
	
	//配额列表
	private List<AgentPeItem> pelist =new ArrayList<AgentPeItem>();
	
	//可以分配的配额
	private List<AgentPeItem> subpelist =new ArrayList<AgentPeItem>();
	//下级已有配额
	private List<AgentPeItem> xjpelist =new ArrayList<AgentPeItem>();
	
	private double pssc = 0.0;
	public AgentUserSearchBean(){
		UserItem ui = CommonMethod.getCurUser();
		if(ui!=null){
			selfid = ui.getId();
		}
		//加载配额信息
		//loadPe();
	}
	@SuppressWarnings("unchecked")
	public void loadPe(){
		UserItem curuser=CommonMethod.getCurUser();
		if(curuser==null){
			this.setMsg("请重新登录后再进行操作。");
			return;
		}
		{
			List<Object> olist = new ArrayList<Object>();
			olist.add("UPEList");
			olist.add(curuser.getLoginname());
			try{
				List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
				String flag = rlist.get(0).toString();
				if("0".equals(flag)){
					List<Object> oslist= (List<Object>)rlist.get(1);
					pelist = (List<AgentPeItem>)oslist.get(0);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		{
			//加载选中玩家的配额
			List<Object> olist = new ArrayList<Object>();
			olist.add("UPEList");
			olist.add(optuser.getLoginname());
			try{
				List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
				String flag = rlist.get(0).toString();
				if("0".equals(flag)){
					List<Object> oslist= (List<Object>)rlist.get(1);
					xjpelist = (List<AgentPeItem>)oslist.get(0);
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	/**
	 * 配额保存
	 */
	public void savePe(){
		//1、减一边
		//2、加一边
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		List<AgentPeItem> tmplist = new ArrayList<AgentPeItem>();
		for(AgentPeItem api:subpelist){
			if(api.getNum()<api.getNum2()){
				this.setMsg("【"+api.getNum2()+"】设置不正确,不能大于您本身拥有的配额数【"+api.getNum()+"】。");
				return;
			}
			if(api.getNum2()>0){
				tmplist.add(api);
			}
			
		}
		
		if(tmplist==null || tmplist.size()==0){
			this.setMsg("失败：您未分配任何配额给您的下级会员。");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UPEMove2");
		olist.add(ui.getLoginname());
		olist.add(optuser.getLoginname());
		olist.add(tmplist);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if(!"0".equals(flag)){
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setMsg(ex.getMessage());
			return;
		}
		//loadPe();
	}
	public void edit(){
		optuser = null;
		this.setMsg(null);
		String sid = CommonMethod.getParam("id");
		if(sid==null){
			this.setMsg("传入参数出错");
			return;
		}
		for(UserItem ui:reslist){
			if(sid.equals(ui.getId()+"")){
				optuser = ui;
				break;
			}
		}
	}
	public void pointEdit(){
		edit();
		if(this.getMsg()!=null){
			return;
		}
		if(optuser==null){
			this.setMsg("没找到需要编辑的对象。");
			return;
		}
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		selssc.clear();
		sel115.clear();
		seldp.clear();
		subpelist.clear();
		xjpelist.clear();
		
		pssc = optuser.getPointssc();
		
		double n = MathBrian.mul(optuser.getPointssc(),1000);
		for(double i=n;i<=ui.getPointssc()*1000;i++){
			selssc.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i, 0.1)+""));
		}
		
		n = MathBrian.mul(optuser.getPoint115(),1000);
		for(double i=n;i<=ui.getPoint115()*1000;i++){
			sel115.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i, 0.1)+""));
		}
		n = MathBrian.mul(optuser.getPointdp(),1000);
		for(double i=n;i<=ui.getPointdp()*1000;i++){
			seldp.add(new SelectItem(MathBrian.mul(i, 0.001),MathBrian.mul(i, 0.1)+""));
		}
		
		int fh = ui.getParam8();
		fhsel.clear();
		int cfh = optuser.getParam8();
		if(ui.getFatherid()<=1){
			//直属级别保留7个点
			for(int i=cfh;i<=fh-7;i++){
				fhsel.add(new SelectItem(i,i+"%"));
			}
		}else{
			//一代后
			for(int i=cfh;i<=fh;i++){
				fhsel.add(new SelectItem(i,i+"%"));
			}
		}
		loadPe();
		//加载可以分配的配额
		for(AgentPeItem pi:pelist){
			if(pi.getLevels()<=optuser.getPointssc()){
				subpelist.add(pi);
			}
		}
	}
	/**
	 * 提升返点
	 */
	public void update(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		if(optuser==null){
			this.setMsg("请先选择要编辑的会员再操作。");
			return;
		}
		if(optuser.getPointssc()>ui.getPointssc()){
			this.setMsg("会员的时时彩返点不能大于自己的返点。");
			return;
		}
		if(optuser.getPointssc()<pssc){
			this.setMsg("会员的时时彩返点设置不正确。");
			return;
		}
		if(optuser.getPoint115()>ui.getPoint115()){
			this.setMsg("会员的11选5返点不能大于自己的返点。");
			return;
		}
		if(optuser.getPointdp()>ui.getPointdp()){
			this.setMsg("会员的低频返点不能大于自己的返点。");
			return;
		}
		/**
		Double point115 = MathBrian.sub(optuser.getPointssc(), 0.02);
		if(point115<0){
			point115=0.0;
		}
		if(point115>0.06){
			point115 = 0.06;
		}
		*/
		if(optuser.getParam8()>ui.getParam8() || optuser.getParam8()>35){
			this.setMsg("分红设置不正确");
			return;
		}
		
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentUserUpdate");
		olist.add(optuser.getLoginname());
		olist.add(1);
		olist.add(optuser.getPointssc());
		olist.add(optuser.getPoint115());
		olist.add(optuser.getPointdp());
		olist.add(optuser.getParam8());
		olist.add(ui.getLoginname());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				optuser =new UserItem();
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
			return;
		}
	}
	/**
	 * 上线对下线充值(最小50),只能直属上级给直属下级进行充值，不能跳级充值
	 */
	public void deposit(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("失败：当前Session失效，请重新登录。");
			return;
		}
		if(!"4".equals(ui.getParam4())){
			this.setMsg("失败：您没有对直属下级充值的权限，请联系客服开通。");
			return;
		}
		if(optuser.getFatherflag().indexOf(ui.getFatherflag())!=0){
			this.setMsg("失败：只能给团队下级会员进行充值。");
			return;
		}
		/**
		if(ui.getId()!=optuser.getFatherid()){
			this.setMsg("失败：只能给直属会员进行充值，不能跨级充值。");
			return;
		}
		*/
		if(!CommonMethod.checkFloat(tamount, 4)){
			this.setMsg("转账金额格式不正确，不能包含其他符号。");
			return;
		}
		Double d = Double.parseDouble(tamount);
		if(d<5){
			this.setMsg("转账金额不能小于单笔最低转账5RMB。");
			return;
		}
		if(optuser==null){
			this.setMsg("没找到需要编辑的会员。");
			return;
		}
		if(InitData.checkPwdTimes(CommonMethod.getIpAddress(),ui.getLoginname())>GlobalDefine.optFrequently){
			this.getLog().warn("资金密码使用频繁："+ui.getLoginname());
			this.setMsg("您的资金密码输入过于频繁，请休息下，5分钟后再试！");
			return;
		}
		String dptype01 = dptype;
		if (dptype01.equals("dp")) {
			dptype01 = "10";
		}else if (dptype01.equals("ac")) {
			dptype01 = "52";
		}else if (dptype01.equals("fh")) {
			dptype01 = "51";
			
		}
//		if(!checkpwd.equals(ui.getCheckpwd())){
//			this.setMsg("失败：资金密码不正确，请重试。");
//			return;
//		}
		String id = "Z"+CommonMethod.GetCurDate("yyMMddHHmmss")+CommonMethod.getRandomNubmer(6);
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentDeposit_v2");
		olist.add(id);
		olist.add(ui.getLoginname());
		olist.add(optuser.getLoginname());
		olist.add(tamount);
		String ip = CommonMethod.getIpAddress();
		olist.add(ip);
		olist.add(checkpwd);
		olist.add(dptype01);
		olist.add(remark);
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				optuser =new UserItem();
				tamount = "0";
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
			return;
		}
	}
	/**
	 * 团队余额
	 */
	public void tbalance(){
		this.setMsg(null);
		edit();
		tbalance="0.0";
		if(this.getMsg()!=null){
			return;
		}
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentTeamBalance");
		olist.add(optuser.getFatherflag());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				tbalance = rlist.get(1).toString();
			}else{
				this.setMsg(rlist.get(1).toString());
				return;
			}
		}catch(Exception ex){
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
			return;
		}
	}
	/**
	 * 帐变
	 */
	public void changebalance(){
		this.setMsg(null);
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("当前Session失效，请重新登录。");
			return;
		}
		String sid = CommonMethod.getParam("id");
		if(sid==null){
			this.setMsg("传入参数出错");
			return;
		}
		for(UserItem ui2:reslist){
			if(sid.equals(ui2.getLoginname())){
				//optuser = ui2;
				//跳到对应的页面
				PersonMsgUserBean sbcn=new PersonMsgUserBean();
				sbcn.setLoginname(sid);
				sbcn.setUsertype(1);
				sbcn.btnSearch();
				CommonMethod.putToSession("PersonMsgUserBean", sbcn);
				
				BalanceChangeBean bcb = new BalanceChangeBean();
				bcb.setLn(sid);
				bcb.btnSearch();
				CommonMethod.putToSession("BalanceChangeBean", bcb);
				CommonMethod.PageJump("/page/ReportZb.shtml");
				break;
			}
		}
	}
	public void btnSearch() {
		curpage = 1;
		search();
	}
	@SuppressWarnings("unchecked")
	public void search(){
		this.setMsg(null);
		reslist.clear();
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("请登录后再操作");
			return;
		}
		List<Object> olist = new ArrayList<Object>();
		olist.add("UAgentUserSearch");
		olist.add(curpage);
		olist.add(pagesize);
		olist.add(loginname);
		String sa = samount,se=eamount,fa=fspoint,fe=fepoint;
		if(sa==null || sa.length()<1){
			sa = "-1";
		}else{
			if(!CommonMethod.checkFloat(sa,4)){
				this.setMsg("账户余额输入不正确");
				return;
			}
		}
		if(se==null || se.length()<1){
			se = "-1";
		}else{
			if(!CommonMethod.checkFloat(se,4)){
				this.setMsg("账户余额输入不正确");
				return;
			}
		}
		if(fa==null || fa.length()<1){
			fa = "-1";
		}else{
			if(!CommonMethod.checkFloat(fa,1)){
				this.setMsg("返点范围输入不正确");
				return;
			}
		}
		if(fe==null || fe.length()<1){
			fe = "-1";
		}else{
			if(!CommonMethod.checkFloat(fe,1)){
				this.setMsg("返点范围输入不正确");
				return;
			}
		}
		olist.add(sa);
		olist.add(se);
		olist.add(fa);
		olist.add(fe);
		if(ui.getFatherflag()==null || ui.getFatherflag().length()<1){
			olist.add(ui.getId());
		}else{
			olist.add(ui.getFatherflag());
		}
		olist.add(order);
		olist.add(userarea);
		olist.add(ui.getId());
		try{
			List<Object> rlist = PostDBData.DBPOST(InitServlet.WSURL, olist, 2);
			String flag = rlist.get(0).toString();
			if("0".equals(flag)){
				List<Object> sblist = (List<Object>)rlist.get(1);
				reslist = (List<UserItem>)sblist.get(0);
				totalrecord = Integer.parseInt(sblist.get(1).toString());
				totalpage = Integer.parseInt(sblist.get(2).toString());
			}else{
				this.setMsg(rlist.get(1).toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void firstPage() {
		this.setMsg(null);
		if (curpage <= 1) {
			this.setMsg("当前已经是第一页！");
			return;
		}
		curpage = 1;
		search();
	}

	public void proPage() {
		this.setMsg(null);
		if (curpage <= 1) {
			this.setMsg("当前已经是第一页！");
			return;
		}
		curpage--;
		search();
	}

	public void nextPage() {
		this.setMsg(null);
		if (curpage >= totalpage) {
			this.setMsg("当前已经是最后一页！");
			return;
		}
		curpage++;
		search();
	}

	public void lastPage() {
		this.setMsg(null);
		if (curpage >= totalpage) {
			this.setMsg("当前已经是最后一页！");
			return;
		}
		curpage = totalpage;
		search();
	}

	public void inputPage() {
		this.setMsg(null);
		try {
			String num = CommonMethod.getParam("num");
			int n = Integer.parseInt(num);
			if (n < 1 || n > totalpage) {
				this.setMsg("输入的页码不正确，请重试！");
				return;
			}
			curpage = n;
			search();
		} catch (Exception ex) {
			this.setMsg(ex.getMessage());
		}
	}
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getSamount() {
		return samount;
	}

	public void setSamount(String samount) {
		this.samount = samount;
	}

	public String getEamount() {
		return eamount;
	}

	public void setEamount(String eamount) {
		this.eamount = eamount;
	}

	public String getFspoint() {
		return fspoint;
	}

	public void setFspoint(String fspoint) {
		this.fspoint = fspoint;
	}

	public String getFepoint() {
		return fepoint;
	}

	public void setFepoint(String fepoint) {
		this.fepoint = fepoint;
	}

	public List<UserItem> getReslist() {
		return reslist;
	}

	public void setReslist(List<UserItem> reslist) {
		this.reslist = reslist;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurpage() {
		return curpage;
	}

	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public int getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getUserarea() {
		return userarea;
	}
	public void setUserarea(Integer userarea) {
		this.userarea = userarea;
	}
	public UserItem getOptuser() {
		return optuser;
	}
	public void setOptuser(UserItem optuser) {
		this.optuser = optuser;
	}
	public List<SelectItem> getSelssc() {
		return selssc;
	}
	public void setSelssc(List<SelectItem> selssc) {
		this.selssc = selssc;
	}
	public String getTamount() {
		return tamount;
	}
	public void setTamount(String tamount) {
		this.tamount = tamount;
	}
	public String getTbalance() {
		return tbalance;
	}
	public void setTbalance(String tbalance) {
		this.tbalance = tbalance;
	}
	public Integer getSelfid() {
		return selfid;
	}
	public void setSelfid(Integer selfid) {
		this.selfid = selfid;
	}
	public List<AgentPeItem> getPelist() {
		return pelist;
	}
	public void setPelist(List<AgentPeItem> pelist) {
		this.pelist = pelist;
	}
	public String getCheckpwd() {
		return checkpwd;
	}
	public void setCheckpwd(String checkpwd) {
		this.checkpwd = checkpwd;
	}
	public List<AgentPeItem> getSubpelist() {
		return subpelist;
	}
	public void setSubpelist(List<AgentPeItem> subpelist) {
		this.subpelist = subpelist;
	}
	public List<AgentPeItem> getXjpelist() {
		return xjpelist;
	}
	public void setXjpelist(List<AgentPeItem> xjpelist) {
		this.xjpelist = xjpelist;
	}
	public List<SelectItem> getFhsel() {
		return fhsel;
	}
	public void setFhsel(List<SelectItem> fhsel) {
		this.fhsel = fhsel;
	}
	public Integer getFhnumber() {
		return fhnumber;
	}
	public void setFhnumber(Integer fhnumber) {
		this.fhnumber = fhnumber;
	}
	public List<SelectItem> getSel115() {
		return sel115;
	}
	public void setSel115(List<SelectItem> sel115) {
		this.sel115 = sel115;
	}
	public List<SelectItem> getSeldp() {
		return seldp;
	}
	public void setSeldp(List<SelectItem> seldp) {
		this.seldp = seldp;
	}
	public double getPssc() {
		return pssc;
	}
	public void setPssc(double pssc) {
		this.pssc = pssc;
	}
	public String getDptype() {
		return dptype;
	}
	public void setDptype(String dptype) {
		this.dptype = dptype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
