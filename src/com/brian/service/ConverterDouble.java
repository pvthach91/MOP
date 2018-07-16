package com.brian.service;

import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * 金额转换器
 * @author Brian
 *
 */
public class ConverterDouble implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		/*
		Double db = Double.parseDouble(arg2);
		DecimalFormat df = new DecimalFormat();  
		df.setMaximumFractionDigits(4);
		String result = df.format(db);  
		
		return result;
		*/
		return arg2;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2==null){
			return null;
		}
		Double db = Double.parseDouble(arg2.toString());
		DecimalFormat df = new DecimalFormat("###,###,##0.0000");  
		df.setMaximumFractionDigits(4);
		String result = df.format(db);  
		//System.out.println(result);
		return result;
	}

}
