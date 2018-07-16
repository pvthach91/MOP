function pageto(url){
	window.location=url;
}
function pageNumTo(num){
	var url = window.location.href;
	var uArr = url.split("&");
	window.location = uArr[0]+"&num="+num;
}