package com.brian.service;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * 百分比转化器
 * @author BRIAN
 *
 */
public class ConverterPercent implements Converter{
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String s = arg2.toString();
		if(s==null || "".equals(s.trim())){
			return "";
		}
		if("1".equals(s.trim())){
			return "";
		}
		Double db = Double.parseDouble(s);
		db = db*100;
		DecimalFormat df = new DecimalFormat();  
		df.setMaximumFractionDigits(4);
		String result = df.format(db);  
		//System.out.println(result);
		return result;
	}
}
