// pc客户端链接 pc client download
function getPcUrl () {
    var pclink = '';
    jQuery.ajax({
        url: '/PCclientService.aspx',
        type:'GET',
        async: false,
        data:{flag:'PCClientUrlProvider'},
    }).done(function(data) {
        var obj = eval('(' + data + ')');
        window.location.href=obj.pcClientUrl;
    })
}

// 在线客服 online service
function openChat() {
    jQuery.ajax({
        url: '/LiveChatService.aspx',
        type:'GET',
        async: false,
        data:{flag:'LiveChatUrlProvider'},
    }).done(function(data) {
        if (data) {
            var obj = eval('(' + data + ')');
            var link = obj.liveChatUrl;
            window.open(link,'chatwindow','height=510,width=850');
        } else {
            window.open('https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=17803&configID=7800&jid=&s=1','chatwindow','height=510,width=850');
        }
    })
}