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
							+ "' class='outerdiv'>"
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
				'<div class="title">',
				t,
				'</div><div class="msg">',
				s,
				'</div>'].join('');
	}
})(jQuery);