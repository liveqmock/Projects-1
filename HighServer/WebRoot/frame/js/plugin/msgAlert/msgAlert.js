(function($) {
	$.extend({
				alert : function(msg, time, title) {
					var randNum = Math.random().toString();
					var id = randNum.substring(randNum.indexOf('.') + 1,
							randNum.length)
							+ "alert_dialog";
					var msgCt;
					if (!title) {
						title = "提示:";
					}
					if (!time) {
						time = 2;
					}
					var divMsg = "<div id='"
							+ id
							+ "' style='width:300px;display:none;z-index: 9999;'>"
							+ title + "</div>";
					if (!msgCt) {
						msgCt = $(divMsg).appendTo('body');
					}
					var html = createBox(title, msg, 290);
					msgCt.html(html);
					var windowWidth = document.documentElement.clientWidth;
					var windowHeight = document.documentElement.clientHeight;
					var popupHeight = msgCt.height();
					var popupWidth = msgCt.width();
					msgCt.css({
								"position" : "absolute",
								"left" : windowWidth / 2 - popupWidth / 2,
								"top" : windowHeight / 2 - popupHeight / 2
							}).slideDown("fast", function() {
								window.setTimeout(function() {
											msgCt.slideUp("fast");
										}, time * 1000);
							});
				},
				// 若提示框遮盖了其下层的控件，可调用此方法解决问题
				hide : function() {
					var root = document.getElementById(id);
					root.style.visibility = "hidden";
				}
			});
	function createBox(t, s, w) {
		return [
				'<div class="msg">',
				'<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
				'<div class="x-box-ml" style="background-color:#eee"><div class="x-box-mr"><div class="x-box-mc"><h3 style="padding-left:6px;">',
				t,
				'</h3><h2 style="text-align:center;font-size:17px;',
				w,
				'px;">',
				s,
				'</h2></div></div></div>',
				'<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
				'</div>'].join('');
	}
})(jQuery);