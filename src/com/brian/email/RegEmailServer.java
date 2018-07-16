package com.brian.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.brian.item.UserItem;
import com.brian.service.InitServlet;


public class RegEmailServer extends Thread {
	public UserItem user;

	public RegEmailServer(UserItem us) {
		user = us;
	}

	public void run() {
		if (user == null)
			return;
		// 读取文件
		String fileStr = "";
		try {
			File file = new File(InitServlet.appPath + "/welcome.html");
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String content = "";
			StringBuilder sb = new StringBuilder();
			while (content != null) {
				content = bf.readLine();
				if (content == null) {
					break;
				}
				sb.append(content.trim());
			}
			bf.close();
			fileStr = sb.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 替换
		fileStr = fileStr.replaceAll("##NAME##", user.getLoginname());
		fileStr = fileStr.replaceAll("##EMAIL##", user.getEmail());
		fileStr = fileStr.replaceAll("##USERID##", user.getName());
		
		// 发送邮件
		EmailMsgSendBean ems= new EmailMsgSendBean();
		ems.setEmail(user.getEmail());
		ems.setSubject("吉祥坊-邮箱验证");
		ems.setEmailContent(fileStr);
		ems.EmailSend();
	}
}
