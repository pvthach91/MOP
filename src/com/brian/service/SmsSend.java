package com.brian.service;

//import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SmsSend {
	public static void main(String[] args) {
		try{
			String rs = sendSmsNew("15501164015","测试看看结果");
			System.out.println(rs);
			//System.out.println(MD5.Encrypt("123456").toUpperCase());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public static String sendSmsNew(String mobies, String content)
			throws Exception {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("Uid", "brian"),
				new NameValuePair("Key", "cb026b8ed7f7618b037b"),
				new NameValuePair("smsMob", mobies),
				new NameValuePair("smsText", content) };
		post.setRequestBody(data);
		client.executeMethod(post);
		/**
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		*/
		String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
		//System.out.println(result);
		post.releaseConnection();
		return result;
	}
}
