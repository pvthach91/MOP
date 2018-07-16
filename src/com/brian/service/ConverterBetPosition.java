package com.brian.service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * 百分比转化器
 * @author BRIAN
 *
 */
public class ConverterBetPosition implements Converter{
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
		s=s.replaceAll("&", ",");
		s=s.replaceAll("0", "万");
		s=s.replaceAll("1", "千");
		s=s.replaceAll("2", "百");
		s=s.replaceAll("3", "十");
		s=s.replaceAll("4", "个");
		return "【"+s+"】";
	}
}
