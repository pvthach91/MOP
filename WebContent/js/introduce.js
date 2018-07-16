(function($){

	//彩种介绍信息
	//可自行修改
	lotteryIntroduce = {
    	//彩种信息
    	"bnmmc":{
    		title: '新生秒秒彩',
    		logo: '/images/logo/desc/lottery-logo_08.png',
    		desc: '开奖时间：每日08:00-次日07:00<br/>频率：即时开（下注成功即可开奖）<br/>彩种介绍：下注即开奖，期期带来超快感。新生秒秒彩是【新生娱乐】自主研发的数字型快开彩票，经菲律宾FIRST CAGAYAN官方认证，开奖设备通过了德国莱茵技术监督顾问有限公司的安全认证，具有世界领先水平。游戏的运营由世界彩票协会进行严格监管，游戏的公开及公平性具有绝对保障。'
    	},
    	"bnffc":{
    		title: '新生分分彩',
    		logo: '/images/logo/desc/lottery-logo_24.png',
    		desc: '开奖时间：24小时全天<br/>频率：每期1分钟，共1380期；5秒封单，5秒开奖<br/>彩种介绍：每分钟一期，期期带来超快感。新生分分彩是【新生娱乐】自主研发的数字型快开彩票，经菲律宾FIRST CAGAYAN官方认证，开奖设备通过了德国莱茵技术监督顾问有限公司的安全认证，具有世界领先水平。游戏的运营由世界彩票协会进行严格监管，游戏的公开及公平性具有绝对保障。。'
    	},
    	"bnlfc":{
    		title: '新生两分彩',
    		logo: '/images/logo/desc/lottery-logo_30.png',
    		desc: '开奖时间：24小时全天<br/>频率：每期2分钟，共计690期；5秒封单，5秒开奖<br/>彩种介绍：每2分钟一期，期期带来超快感。新生两分彩是【新生娱乐】自主研发的数字型快开彩票，经菲律宾FIRST CAGAYAN官方认证，开奖设备通过了德国莱茵技术监督顾问有限公司的安全认证，具有世界领先水平。游戏的运营由世界彩票协会进行严格监管，游戏的公开及公平性具有绝对保障。'
    	},
    	"cqssc":{
    		title: '重庆时时彩',
    		logo: '/images/logo/desc/ssc-logo.png',
    		desc: '开奖时间：每日10：00-22：00（10分钟一期，共72期）22：00-次日01：55（5分钟一期，共48期）全天120期<br/>彩种介绍：“重庆时时彩投注”区分为万位、千位、百位、十位和个位，各位号码范围为0～9。每期从各位上开出1个号码作为中奖号码，即开奖号码为5位数。时时彩玩法即是竞猜5位开奖号码的全部号码、部分号码或部分号码特征。重庆时时彩是国内第一个快开彩票，也是目前购买人数最多的彩种。'
    	},
    	"xjssc":{
    		title: '新疆时时彩',
    		logo: '/images/logo/desc/lottery-logo_32.png',
    		desc: '开奖时间：开奖时间：每日10：00-02:00<br/>频率：10分钟/期，全天96期<br/>彩种介绍：“新疆时时彩”投注区分为万位、千位、百位、十位和个位，各位号码范围为0～9。每期从各位上开出1个号码作为中奖号码，即开奖号码为5位数。时时彩玩法即是竞猜5位开奖号码的全部号码、部分号码或部分号码特征。每日多期开奖快，玩法多样，中奖率高。'
    	},
    	"tjssc":{
    		title: '天津时时彩',
    		logo: '/images/logo/desc/lottery-logo_33.png',
    		desc: '开奖时间：每日9:00-23:00<br/>频率：10分钟/期，全天84期<br/>彩种介绍：“天津时时彩”投注区分为万位、千位、百位、十位和个位，各位号码范围为0～9。每期从各位上开出1个号码作为中奖号码，即开奖号码为5位数。时时彩玩法即是竞猜5位开奖号码的全部号码、部分号码或部分号码特征。每日多期开奖快，玩法多样，中奖率高。'
    	},
    	"bn115":{
    		title: '新生11选5',
    		logo: '/images/logo/desc/lottery-logo_03.png',
    		desc: '开奖时间：24小时全天<br/>频率：1分钟/期<br/>彩种介绍：为平台自有彩种，玩法与官方十一选五相同，随机开奖，相对官方彩种开奖频率 更高更刺激与娱乐性，回报率也更高。'
    	},
    	"sd115":{
    		title: '山东11选5',
    		logo: '/images/logo/desc/lottery-logo_14.png',
    		desc: '开奖时间：每日09:05-21:55<br/>频率：10分钟/期，全天78期<br/>彩种介绍：“十一运夺金”又称山东11选5，是全国第一个上市销售的"11选5"类型快开彩票,投注区号码范围为01～11，每一个号码分别代表一种运动项目。每期开出5个号码作为中奖号码。十一运夺金玩法即是竞猜5位开奖号码的全部号码或部分号码。也是最广为人知的快开彩票，十一运夺金为第十一届全运会的胜利举行做出了巨大的贡献，是国内首个11选5玩法。'
    	},
    	"jx115":{
    		title: '江西11选5',
    		logo: '/images/logo/desc/lottery-logo_22.png',
    		desc: '开奖时间：每日09:10-22:00<br/>频率：10分钟/期，全天78期<br/>彩种介绍：江西11选5是从01-11共11个号码中任选1-8个号码进行投注，每期开出5个号码为中 奖号码，竞猜5位开奖号码的全部或部分号码。投注方式灵活，开奖频率高。'
    	},
    	"gd115":{
    		title: '广东11选5',
    		logo: '/images/logo/desc/lottery-logo_29.png',
    		desc: '开奖时间：每日09:10-23:00<br/>频率：10分钟/期，全天84期<br/>彩种介绍：“广东11选5”投注区号码范围为01～11，每期开出5个号码作为中奖号码，为在线即开型彩票玩法，属于基诺型彩票范畴，玩法即是竞猜5位开奖号码的全部或部分号码。返奖率高、开奖快、投注灵活、玩法多样。'
    	},
    	"bn3D":{
    		title: '新生3D',
    		logo: '/images/logo/desc/lottery-logo_20.png',
    		desc: '开奖时间：24小时全天<br/>频率：1分钟/期<br/>彩种介绍：新生3D是为迎合广大玩家所自主研发的相同于高频福彩3D，解决了福彩3D每天一期的弊端，让更多玩家能更好的参与享受高频购彩的快乐与刺激。'
    	},
    	"fc3D":{
    		title: '福彩3D',
    		logo: '/images/logo/desc/lottery-logo_13.png',
    		desc: '开奖时间：1期/天<br/>频率：1期/天<br/>彩种介绍：“3D”是指以三个号码排列或组合为一注进行单式投注，投注号码由000-999组成，三个位置从左至右分别为“百位”、“十位”、“个位”，一组三个号码的排列或组合称为一注。'
    	},
    	"pl35":{
    		title: '体彩P3/5',
    		logo: '/images/logo/desc/lottery-logo_06.png',
    		desc: '开奖时间：1期/天<br/>频率：1期/天<br/>彩种介绍：“排列五”投注区分为（百位、十位和个位/万位、千位、百位、十位和个位），各位号码范围为0～9。每期从各位上开出1个号码作为中奖号码，即开奖号码为3位数/5位数。排列三即开奖号码的前三位，排列五即开奖号码为5位数。 属于智力型彩种。'
    	}
    }

	var ifrm = jQuery("#mainiframe")[0].contentWindow.document; 
	var $body = $(ifrm).find('body');

	$body.find('.activity-nav li').click(function(){
		var name = $.trim($(this).attr('data-group'));
		$body.find('.nav-chlid').hide();
		$body.find('.group-' + name).show();
		$body.find('.activity-nav li').removeClass('current');
		$(this).addClass('current');
	});

	$body.find('.nav-chlid > li').click(function(){
		var mark = $(this).attr('data-lottery');
		var $parentDom = $body.find('.lottery-introduce');
		var currentData = lotteryIntroduce[mark];
		$body.find('.nav-chlid li').removeClass('current');
		$(this).addClass('current');

		$parentDom.find('.logo img').attr('src', currentData['logo']);
		$parentDom.find('.lottery-name').html(currentData['title']);
		$parentDom.find('.lottery-desc').html(currentData['desc']);
	});


})(jQuery);