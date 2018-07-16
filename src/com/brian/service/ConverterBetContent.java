package com.brian.service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * 百分比转化器
 * @author BRIAN
 *
 */
public class ConverterBetContent implements Converter{
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		String result = arg2.replaceAll("&", " ");
		result = result.replaceAll("@", "\r\n");
		return result;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String s = arg2.toString();
		if(s==null || "".equals(s.trim())){
			return "";
		}
		s = s.trim();
		/**
		if("1".equals(s.trim())){
			return "";
		}
		*/
		String result = s.replaceAll("&", ",");
		result = result.replaceAll("@", "\r\n");
		//System.out.println(result);
		return result;
	}
}
