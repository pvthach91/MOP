/*//  启动时加载
$(document).ready(function() {
$('.data-url').click(function(e){
		var $iframeDom = $("#mainiframe"),
			urlText = $(this).attr('data-url') || '';
		urlText && $iframeDom.attr("src", urlText);
		alert(urlText);
	});
});*/


$(".game").hover(function(){
	$(this).children(".sub").show();
},function(){
	$(this).children(".sub").hide();
});


$(".Account_management").hover(function(){
	$(this).children(".Account_management_sub").show();
},function(){
	$(this).children(".Account_management_sub").hide();
});
$(".phone").hover(function(){
	$(".equipment_sub").show();
},function(){
	$(".equipment_sub").hide();
});

function exitLogin(){
	window.top.location="./login.shtml";
}
