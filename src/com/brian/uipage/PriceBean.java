package com.brian.uipage;

import java.util.ArrayList;
import java.util.List;

import com.brian.item.ModelItem;
import com.brian.item.UserItem;
import com.brian.service.InitData;
import com.brian.service.LogXY;
import com.brian.unit.CommonMethod;

public class PriceBean extends LogXY{
	private List<ModelItem> ssclist = new ArrayList<ModelItem>();
	private List<ModelItem> ssllist = new ArrayList<ModelItem>();
	private List<ModelItem> sywlist = new ArrayList<ModelItem>();
	private List<ModelItem> fclist = new ArrayList<ModelItem>();
	private List<ModelItem> p35list = new ArrayList<ModelItem>();
	private String maxgp="300,000";
	private String maxdp = "100,000";
	public PriceBean(){
		UserItem ui = CommonMethod.getCurUser();
		if(ui==null){
			this.setMsg("Session超时，请重新登录");
			return;
		}
		List<ModelItem> list = InitData.model;
		for(ModelItem mi:list){
			if(mi.getMethodname()==null)
				continue;
			if("1".equals(mi.getLotteryid())){
				//SSC
				ModelItem ms = (ModelItem)mi.clone();
				ms.putUserPoint(ui.getPointssc());
				/*
				List<Double> slist = mi.getPrizelist();
				List<Double> tmplist = new ArrayList<Double>();
				for(Double d:slist){
					double s = MathDouble.mul(d, ui.getPointssc());
					s = MathDouble.add(s, d);
					tmplist.add(s);
				}
				mi.setPrizelist(tmplist);
				*/
				//ssclist.addAll(c)
				ssclist.add(ms);
			}else if("2".equals(mi.getLotteryid())){
				//11选5
				/*
				List<Double> slist = mi.getPrizelist();
				List<Double> tmplist = new ArrayList<Double>();
				for(Double d:slist){
					double s = MathDouble.mul(d, ui.getPoint115());
					s = MathDouble.add(s, d);
					tmplist.add(s);
				}
				mi.setPrizelist(tmplist);
				*/
				ModelItem ms = (ModelItem)mi.clone();
				ms.putUserPoint(ui.getPoint115());
				sywlist.add(ms);
			}else if("4".equals(mi.getLotteryid())){
				//SSL
				/**
				List<Double> slist = mi.getPrizelist();
				List<Double> tmplist = new ArrayList<Double>();
				for(Double d:slist){
					double s = MathDouble.mul(d, ui.getPointssc());
					s = MathDouble.add(s, d);
					tmplist.add(s);
				}
				mi.setPrizelist(tmplist);
				*/
				ModelItem ms = (ModelItem)mi.clone();
				ms.putUserPoint(ui.getPointssc());
				ssllist.add(ms);
			}else if("11".equals(mi.getLotteryid())){
				//fc3d
				/**
				List<Double> slist = mi.getPrizelist();
				List<Double> tmplist = new ArrayList<Double>();
				for(Double d:slist){
					double s = MathDouble.mul(d, ui.getPointdp());
					s = MathDouble.add(s, d);
					tmplist.add(s);
				}
				mi.setPrizelist(tmplist);
				*/
				ModelItem ms = (ModelItem)mi.clone();
				ms.putUserPoint(ui.getPointdp());
				fclist.add(ms);
			}else if("12".equals(mi.getLotteryid())){
				//p3p5
				/**
				List<Double> slist = mi.getPrizelist();
				List<Double> tmplist = new ArrayList<Double>();
				for(Double d:slist){
					double s = MathDouble.mul(d, ui.getPointdp());
					s = MathDouble.add(s, d);
					tmplist.add(s);
				}
				mi.setPrizelist(tmplist);
				*/
				ModelItem ms = (ModelItem)mi.clone();
				ms.putUserPoint(ui.getPointdp());
				p35list.add(ms);
			}
		}
	}
	
	public List<ModelItem> getSsclist() {
		return ssclist;
	}
	public void setSsclist(List<ModelItem> ssclist) {
		this.ssclist = ssclist;
	}
	public List<ModelItem> getSsllist() {
		return ssllist;
	}
	public void setSsllist(List<ModelItem> ssllist) {
		this.ssllist = ssllist;
	}
	public List<ModelItem> getSywlist() {
		return sywlist;
	}
	public void setSywlist(List<ModelItem> sywlist) {
		this.sywlist = sywlist;
	}
	public List<ModelItem> getFclist() {
		return fclist;
	}
	public void setFclist(List<ModelItem> fclist) {
		this.fclist = fclist;
	}
	public List<ModelItem> getP35list() {
		return p35list;
	}
	public void setP35list(List<ModelItem> p35list) {
		this.p35list = p35list;
	}

	public String getMaxgp() {
		return maxgp;
	}

	public void setMaxgp(String maxgp) {
		this.maxgp = maxgp;
	}

	public String getMaxdp() {
		return maxdp;
	}

	public void setMaxdp(String maxdp) {
		this.maxdp = maxdp;
	}
}
