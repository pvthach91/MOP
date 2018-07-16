function searchfinish(data){
	if(data){
		alert(data);
	}else{
		window.location.reload();
	}
}
function userSel(){
	TopImageSign = '/images/setting.png';
	var diag = new Dialog();
	diag.Width = 450;
	diag.Height = 450;
	diag.Title = "选择会员";
	diag.URL = "/page/PersonMsgUserSel.shtml";
	diag.show();
}
function userInput(ln){
	document.getElementById("GameRecordForm:recname").value=ln;
}
function traceRecordOpt(sid){
	_SCROLLINGJXF = "yes";
	var diag = new Dialog();
	diag.Width = 700;
	diag.Height = 400;
	diag.Title = "查看追号记录";
	diag.URL = "/page/TraceSubRecord.shtml?id="+sid;
	diag.show();
}
		
function betRecordOpt(sid){
	_SCROLLINGJXF = "yes";
	var diag = new Dialog();
	diag.Width = 600;
	diag.Height = 405;
	diag.Title = "查看投注记录";
	diag.URL = "/page/BetSubRecord.shtml?id="+sid;
	diag.show();
}