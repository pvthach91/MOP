package com.brian.item;

public class IpsInfoItem {
	private String posturl = "http://pay.ips.com.cn/ipayment.aspx";
	private String mercode="020757";//"020635";
	private String merkey = "21814924711367379179620232338575027439162600062041643070167159513523094091692917039693204627200308753895764138824012992032946779";//"cVMUqtajPGpnDSPptYQtbB24aZB7RNVBkLtphy4xU2BjP9k50lM0CfGRTHuFgTH2ld2tYa7vUTPY0JfQq56f0Vz43SLP4oF1l9xl7qWQdRVjGjQAcTQbMb1Eeyp6Ehqf";
	//交易号
	private String billno;
	//交易金额
	private String amount;
	//订单日期
	private String date;
	//订单支付接口的Md5摘要，原文=订单号+金额+日期+支付币种+商户证书 
	private String signmd5;
	//提交银行编号
	private String bankco;
	//币种
	private String currencytype="RMB";
	//支付卡种【01为借记】
	private String gatewaytype="01";
	//语言
	private String lang="GB";
	//支付结果成功返回的商户URL
	private String merchanturl="http://app.min60sec.com/page/Ipsback.php";
	//支付结果失败返回的商户URL
	private String failurl="http://app.min60sec.com/page/IpsFailback.php";
	//商户数据包
	private String attach="";//CommonMethod.getCurWebPath()+"uc_sys/win.php";//"http://www.jxf2012.com/";
	//订单支付接口加密方式 5 md5摘要 
	private String orderencodetype="5";
	//交易返回 11 md5withRsa   12 md5摘要 
	private String retencodetype="17";
	//返回方式 
	private String rettype="1";
	//Server to Server 返回页面URL(修改了服务端地址：http://check.microwaveshop.net/ips.jxf)
	//http://check.microwaveshop.net/ips.jxf
	//http://check.min60sec.com/ips.jxf
	private String serverurl="http://service.jxf2012.org/ips.jxf";
	
	private String docredit="1";
	
	public String getMercode() {
		return mercode;
	}
	public void setMercode(String mercode) {
		this.mercode = mercode;
	}
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCurrencytype() {
		return currencytype;
	}
	public void setCurrencytype(String currencytype) {
		this.currencytype = currencytype;
	}
	public String getGatewaytype() {
		return gatewaytype;
	}
	public void setGatewaytype(String gatewaytype) {
		this.gatewaytype = gatewaytype;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getMerchanturl() {
		return merchanturl;
	}
	public void setMerchanturl(String merchanturl) {
		this.merchanturl = merchanturl;
	}
	public String getFailurl() {
		return failurl;
	}
	public void setFailurl(String failurl) {
		this.failurl = failurl;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOrderencodetype() {
		return orderencodetype;
	}
	public void setOrderencodetype(String orderencodetype) {
		this.orderencodetype = orderencodetype;
	}
	public String getRetencodetype() {
		return retencodetype;
	}
	public void setRetencodetype(String retencodetype) {
		this.retencodetype = retencodetype;
	}
	public String getRettype() {
		return rettype;
	}
	public void setRettype(String rettype) {
		this.rettype = rettype;
	}
	public String getServerurl() {
		return serverurl;
	}
	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}
	public String getSignmd5() {
		return signmd5;
	}
	public void setSignmd5(String signmd5) {
		this.signmd5 = signmd5;
	}
	public String getDocredit() {
		return docredit;
	}
	public void setDocredit(String docredit) {
		this.docredit = docredit;
	}
	public String getBankco() {
		return bankco;
	}
	public void setBankco(String bankco) {
		this.bankco = bankco;
	}
	public String getMerkey() {
		return merkey;
	}
	public void setMerkey(String merkey) {
		this.merkey = merkey;
	}
	public String getPosturl() {
		return posturl;
	}
	public void setPosturl(String posturl) {
		this.posturl = posturl;
	}
}
