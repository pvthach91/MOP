var mwInterval = null;
var mbInterval = null;
$(document).ready(function() {
	//top click
	$("#enav").children("li").click(function() {
		$("#enav").children("li").each(function() {
			var s = $(this).attr("class");
			if(s.indexOf("current")>0){
				s = s.replace('current','');
				$(this).attr("class", s);
			}
		});
		var f =$(this).attr("class");
		f+=' current';
		$(this).attr("class", f);
		$("#mainiframe").attr("src", $(this).attr("data"));
		$("#eleftMenu").children("ul").children("li").each(function() {
			$(this).attr("style", "");
		});
	});
	$("#eleftMenu").children("ul").children("li").click(function() {
		$("#eleftMenu").children("ul").children("li").each(function() {
			$(this).attr("style", "");
		});
		$("#enav").children("li").each(function() {
			var s = $(this).attr("class");
			if(s.indexOf("current")>0){
				s = s.replace('current','');
				$(this).attr("class", s);
			}
		});
		$(this).attr("style", "background: #303842;color:#90ff00;");
		var urls = $(this).attr("data");
		$("#mainiframe").attr("src", urls);
	});
	$("#eDpIcon").click(function() {
		$("#eleftMenu").children("ul").children("li").each(function() {
			$(this).attr("style", "");
		});
		$("#enav").children("li").each(function() {
			var s = $(this).attr("class");
			if(s.indexOf("current")>0){
				s = s.replace('current','');
				$(this).attr("class", s);
			}
		});
		//mBank
		var f =$("#mBank").attr("class");
		f+=' current';
		$("#mBank").attr("class", f);
		$("#mainiframe").attr("src", "/page/DWDeposit.shtml");
	});
	
	
	$("#eHiddenMan").click(function() {
		$("#e_hiddenbalance").hide("fast");
		$("#e_showbalance").show("fast");
	});
	$("#e_showbalance").click(function() {
		$("#e_hiddenbalance").show("slow");
		$("#e_showbalance").hide("slow");
	});
	$("#aMsg").click(function() {
		$("#top_submenu2").children(".top_menu_sel").each(function() {
			$(this).attr("class", "top_menu_float");
		});
		$("#left_ban").children(".left_items").each(function() {
			$(this).attr("style", "");
		});
		$("#ePersonInfo").attr("class", "top_menu_sel");
		$("#mainiframe").attr("src", "/page/PersonMsg.shtml");
	});
	$("#depositBnt").click(function() {
		$("#top_submenu2").children(".top_menu_sel").each(function() {
			$(this).attr("class", "top_menu_float");
		});
		$("#left_ban").children(".left_items").each(function() {
			$(this).attr("style", "");
		});
		$("#ebankitem").attr("class", "top_menu_sel");
		$("#mainiframe").attr("src", "/page/DWDeposit.shtml");
	});
	$("#withDrawalBnt").click(function() {
		$("#top_submenu2").children(".top_menu_sel").each(function() {
			$(this).attr("class", "top_menu_float");
		});
		$("#left_ban").children(".left_items").each(function() {
			$(this).attr("style", "");
		});
		$("#ebankitem").attr("class", "top_menu_sel");
		$("#mainiframe").attr("src", "/page/DWWithDrawl.shtml");
	});
	
	$("#left_ban").children(".left_items").click(function() {
		$("#left_ban").children(".left_items").each(function() {
			$(this).attr("style", "");
		});
		$("#top_submenu2").children(".top_menu_sel").each(function() {
			$(this).attr("class", "top_menu_float");
		});
		$(this).attr("style", "background:#444444;color:#fecb5e");
		$("#mainiframe").attr("src", $(this).attr("data"));
		$("#mainiframe").attr("height", "750px");
	});
	$('.data-url').click(function(e){
		var $iframeDom = $("#mainiframe"),
			urlText = $(this).attr('data-url') || '';
		urlText && $iframeDom.attr("src", urlText);
	});
	$("#financeOpt").click(function(){
		//alert("商家未提供财务转账地址！");
		$("#mainiframe").attr("src", "/page/MifTest.html");
	});
	$("#bnt_refreshbalance").click(function(arg) {
		var evnt=window.event?window.event:arg; 
		if (evnt.stopPropagation) {
			evnt.stopPropagation();
		}else{
			evnt.cancelBubble = true;
		}
		$.blockUI({
			message : '<div style="width:200px;padding:10px 100px;background-color:#fff;border:4px #666 solid;"><img src="/images/loading2.gif" style="margin-right:10px;">正在查询余额中...</div>',
			overlayCSS : {
				backgroundColor : "#000000",
				opacity : 0.3,
				cursor : "wait"
			}
		});
//		$.ajax({
//			type : "POST",
//			url : "LotteryService.aspx",
//			data : "flag=balance",
//			success : function(data) {
//				$.unblockUI({
//							fadeInTime : 0,
//							fadeOutTime : 0
//						});
//				try {
//					$("#ebalance").html(data);
//				} catch (e) {
//					$.alert('<img src="/images/comm/t.gif" class=icons_mb5_e style="margin:5px 15px 0 0;">查询失败，请重试.');
//				}
//			}
//		});
//		loadbalance();
	});
//	mwInterval = window.setInterval("loadInfor();",60*1000);
//	mbInterval = window.setInterval("loadbalance();",15*1000);
});
function loadInfor(){
	SysOp();
}
//function loadbalance(){
//	alert("main");
//	$.ajax({
//		type : "POST",
//		url : "/LotteryService.aspx",
//		data : "flag=balance",
//		success : function(data) {
//			try {
//				$("#ebalance").html(data);
//			} catch (e) {
//				$.alert('查询失败，请重试.');
//			}
//		}
//	});
//}

function menuToMain(){
	$("#top_submenu").children("div").each(function() {
		$(this).attr("class", "top_menu_float");
	});
	$("#left_ban").children(".left_items").each(function() {
		$(this).attr("style", "");
	});
	$("#eMain").attr("class", "top_menu_sel");
	$("#mainiframe").attr("src", "/page/Index.shtml");
	//$("#mainiframe").attr("height", "610px");
}
function helpCenter(){
	$("#top_submenu").children("div").each(function() {
		$(this).attr("class", "top_menu_float");
	});
	$("#left_ban").children(".left_items").each(function() {
		$(this).attr("style", "");
	});
	$("#mainiframe").attr("src", "/page/help.shtml");
	//$("#mainiframe").attr("height", "610px");
}
function exitLogin(){
	window.top.location="./login.shtml";
}

//窗口大小
$(function(){
	$(window).resize(function(){
		var winWidth = $(window).width();
		if(winWidth < 1200){
			$('#mainiframe').css('width', 1200);
		}else{
			$('#mainiframe').css('width', '100%');
		}
	});
});
