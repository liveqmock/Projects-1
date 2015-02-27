/**
 * 首页脚本
 */
function changePage() {
	$("#listGrid").show();
	$("#formIframe").hide();
}
var currentURL = WWWROOT+"/adminHome.jsp";
$(function() {  
	HomePage = {
		menuClick : function(url) {
			currentURL = url;
			$("#iframe_div").load(currentURL,function(){});
		},
		menuOpenClick : function(url) {
			currentURL = url;
			window.open(url);
		},
		refreshPage : function() {
			$("#iframe_div").children().remove();
			$("#iframe_div").load(currentURL,function(){});
		},
		frameResize : function() {
			var newHeight = document.documentElement.clientHeight - 150;
			$("#" + FRAME_ID).height(newHeight);
		}
	};
	$("#iframe_div").load(currentURL,function(){});
});