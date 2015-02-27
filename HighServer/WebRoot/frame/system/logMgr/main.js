$(function() {
			TypeTree = {
				// 点击数节点后执行的方法
				active : function(node, event) {
					$("#logList").attr("src",
							WWWROOT + "/log/logList?logType=" + node.data.key);
				}
			}
		});