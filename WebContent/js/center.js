$(".game").hover(function(){
	$(this).children(".sub").show();
},function(){
	$(this).children(".sub").hide();
})


$(".Account_management").hover(function(){
	$(this).children(".Account_management_sub").show();
},function(){
	$(this).children(".Account_management_sub").hide();
})

$(".tab_nav li").click(function(){
	$(this).addClass("on").siblings().removeClass("on");
	$(".tab_in>div").eq($(this).index()).show().siblings().hide();
})
