/*投注动画组件*/
;
(function($) {

	//组件构造函数
	betAnimate = function(config) {

		this.config = {

			//绑定DOM元素
			bindDom: '',
			//动画元素
			animateDom: '',
			//移动目标dom
			purposeDom: ''

		}

		//参数
		this.init($.extend(this.config, config));
	};

	//原型方法
	$.extend(betAnimate.prototype, {

		init: function(config) {
			var me = this;

			me.config = config;

			//绑定事件
			me.bindEvent(config)
		},

		bindEvent: function(config) {
			var me = this;

			$(config['bindDom'] + ',' + config['animateDom']).live('click', function(e) {
				var action = $(this).attr('data-action'),
					actionObject = me.parseRoute(action);

				if (action) {
					
					try{
						//doaction
						me[actionObject['action']](e, actionObject);
					}catch(e){
						console && console.log(e);
					}
				};
			});
		},

		parseRoute: function(url) {
			if (null == url || url.indexOf("?") == -1) {
				return null;
			}

			var argsUrl = url.split("?")[1];

			//if (argsUrl.split("=").length < 2)  
			if (argsUrl.indexOf("=") == -1) {
				return null;
			}

			var properties = argsUrl.replace(/&/g, "',").replace(/=/g, ":'") + "'";
			var obj = null;
			var template = "obj = {p}";
			eval(template.replace(/p/g, properties));
			return obj;
		},

		//选择号码
		selectedBall: function(e, data){
			var $dom = $(e.target),
				position = {
					top: $dom.offset().top,
					left: $dom.offset().left
				},
				html = $('<div data-action="?action=removeDom" class="animate-ball">'+data['ballId']+'</div>');

				//位置
				html.css({
					top: position['top'],
					left: position['left']
				});

			$('body').append(html);
		},

		removeDom : function(e, data){
			var $dom = $(e.target);

			$dom.remove();
		},

		//开始动画
		doAnimate: function(){
			var me = this,
				$dom = $(me.config['purposeDom']),
				position = {
					top: $dom.offset().top,
					left: $dom.offset().left
				},
				dom = $('body').find('.animate-ball');

			for (var i = dom.length - 1; i >= 0; i--) {
				
				(function(s){
					setTimeout(function(){
						
						$(dom[s]).animate({
							opacity: 0,
							top: position['top'] + 30,
							left: position['left'] + 30
						}, 500, function(){
							$(dom[s]).remove();
						});
					}, 100 * s);
				})(i);
			};
		}
	});
	
	//实例化组件
	window.betAnimate = new betAnimate({
		bindDom: 'li',
		animateDom: 'div',
		purposeDom: '.order-panel'
	});

})(jQuery);