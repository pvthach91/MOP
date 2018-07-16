package com.brian.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class DivenEmail extends Authenticator {
	String userName = null;
	String password = null;
	
	public DivenEmail() {
	}
	
	public DivenEmail(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
	//不再使用。（DEL）
	public static boolean EMailSend(String email, String subject, String content) {
		/*
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("edm.bestedm.org");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("weike@bestedm.org");
		mailInfo.setPassword("mailJXF2012com");// 您的邮箱密码
		mailInfo.setFromAddress("cs@jxf2012.net");
		// mailInfo.setToAddress("diven.manila@gmail.com");
		mailInfo.setToAddress(email);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		*/
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.hrcp8.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("admin@hrcp8.com");
		mailInfo.setPassword("Hao123baidu");// 您的邮箱密码
		mailInfo.setFromAddress("admin@hrcp8.com");
		// mailInfo.setToAddress("diven.manila@gmail.com");
		mailInfo.setToAddress(email);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		return true;
	}

	public static void main(String[] args) {
		// 这个类主要是设置邮件
		EMailSendHTML("diven.manila@gmail.com", "密码修改", "尊敬的客户，您的新密码为：ABC123。【华彩-诚创未来】");//yi0731@126.com/diven.manila@gmail.com/
		System.out.println("OK");
		//EMailSendHTML("diven.manila@gmail.com", "邮件测试", "您的账户为：TEST,密码为：嘻嘻嘻");
	}
	/**
	 * 目前使用的【重要】
	 * @param email
	 * @param subject
	 * @param content
	 * @return
	 */
	public static boolean EMailSendHTML(String email, String subject, String content) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("edm.bestedm.org");//("edm.bestedm.org");"smtp.bestedm.org"
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("admin@hrcp8.com");
		mailInfo.setPassword("Hao123baidu");
		try{
			mailInfo.setFromAddress(MimeUtility.encodeText("【华彩-诚创未来】")+"<admin@hrcp8.com>");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//mailInfo.setUserName("weike@bestedm.org");
		//mailInfo.setPassword("mailJXF2012com");
		//mailInfo.setFromAddress("weike@bestedm.org");
		mailInfo.setToAddress(email);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		sendHtmlMail(mailInfo);
		/**
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("mail.jxf2012.net");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("system@jxf2012.net");
		mailInfo.setPassword("jxf20120518");
		mailInfo.setFromAddress("system@jxf2012.net");
		mailInfo.setToAddress(email);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		sendHtmlMail(mailInfo);
		*/
		return true;
	}
	public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		DivenEmail authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new DivenEmail(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			MimeMessage mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject(), "utf-8");
			//mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart,"text/plain;charset=big5");
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;

	}
}
