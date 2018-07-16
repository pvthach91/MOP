package com.brian.test;

import java.util.Random;

public class RandomUtil {

	public static final String NUMBERCHAR = "0123456789";
	
    /** 
     * 返回一个定长的随机字符串(只包含大小写字母、数字) 
     *  
     * @param length 
     *            随机字符串长度 
     * @return 随机字符串 
     */  
    public static String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));  
        }  
        return sb.toString();  
    }
    
    public static void main(String[] args) {
    	StringBuffer sb = new StringBuffer();  
    	for(int i = 0 ; i < 5000; i++){
    		String str = generateString(2);
    		sb.append(str + ",");
    	}
    	String result = sb.toString();
	}
}
