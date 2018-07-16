package com.brian.email;

import com.brian.service.LogXY;
import com.brian.unit.CommonMethod;


public class EmailMsgSendBean extends LogXY {
	private String email;
	private String subject;
	private String emailContent;

	private String phonenum;
	private String msgContent;

	public void EmailSend() {
		if (CommonMethod.isEmpty(email)) {
			this.setMsg("邮箱号码不能为空！");
			return;
		}
		if (CommonMethod.isEmpty(subject)) {
			this.setMsg("主题不能为空！");
			return;
		}
		if (CommonMethod.isEmpty(emailContent)) {
			this.setMsg("邮件内容不能为空！");
			return;
		}
		String[] arr = email.split(",");

		for (String str : arr) {
			DivenEmail.EMailSendHTML(str, subject, emailContent);
		}
		System.out.println("success");
		/*
		DbMsProcOp dmpop = null;
		try {
			dmpop = new DbMsProcOp(InitServlet.SqlConfigByName("bonusSys"));
			String sql = "insert into t_email(email,subject,remark,sendtime) values(?,?,?,?)";
			dmpop.setSql(sql);
			for (String str : arr) {
				dmpop.setString(1, str);
				dmpop.setString(2, subject);
				dmpop.setString(3, emailContent);
				dmpop.setTimestamp(4, new java.util.Date());
				dmpop.execute();
			}
		} catch (Exception ex) {
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (dmpop != null)
				dmpop.Close();
		}
		*/
		email = null;
		subject = null;
		emailContent = null;
	}
	/**
	public void SendMsg() {
		if (CommonMethod.isEmpty(phonenum)) {
			this.setMsg("手机号码不能为空！");
			return;
		}
		if (CommonMethod.isEmpty(msgContent)) {
			this.setMsg("短信内容不能为空！");
			return;
		}
		//MsgSend.send(phonenum, msgContent);
		try{
			PostDataSSL.SSLGetMethod("http://sms.wellbet.com/sms.jxf?tel="+phonenum+"&content="+CommonMethod.Encode(msgContent));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		DbMsProcOp dmpop = null;
		try {
			dmpop = new DbMsProcOp(InitServlet.SqlConfigByName("bonusSys"));
			String sql = "insert into t_mbmsg(rectelnum,msg,sendtime) values(?,?,?)";
			dmpop.setSql(sql);
			String[] arr = phonenum.split(",");
			for (String str : arr) {
				dmpop.setString(1, str);
				dmpop.setString(2, msgContent);
				dmpop.setTimestamp(3, new java.util.Date());
				dmpop.execute();
			}
		} catch (Exception ex) {
			this.setMsg(ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (dmpop != null)
				dmpop.Close();
		}
		
		phonenum = null;
		msgContent = null;
	}
	*/
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
}
