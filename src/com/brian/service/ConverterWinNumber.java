package com.brian.service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * 开奖结果转化器
 * @author BRIAN
 *
 */
public class ConverterWinNumber implements Converter{
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
}
