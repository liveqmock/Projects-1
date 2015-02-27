/**
 * @class AjaxSubmit 整合jquery ajax的页面提交方式，能自动把页面所有字段打包发送到后台
 */
var AjaxSubmit = function(url, callback, maskObjtId, async, dataType, range) {
	// 返回的数据格式
	if (maskObjtId) {
		var loading = new ol.loading({
					id : maskObjtId
				});
		loading.show();
	}
	var dataType = dataType ? dataType : "json";
	var async = (async == false) ? async : true;
	var json = $.collectData(range);
	var timer = setInterval(function() {
				clearInterval(timer);
			}, 500);
	$.ajax({
				type : "POST",
				async : async,
				url : url,
				data : json,
				dataType : dataType,
				timeout : 60000,
				success : function(result) {
					clearInterval(timer);
					if (callback)
						callback(result);
					if (loading) {
						loading.remove();
					}
				},
				error : function(xhr, str, err) {
					if (loading) {
						loading.remove();
					}
					clearInterval(timer);
					var html = xhr.responseText.replace(
							/(^.*<body>|<\/body>.*$)/gmi, "");
					var sim_html = html.replace(/^.*<pre>/gmi, "");
					sim_html = sim_html.replace(/^.*?\:/gmi, "");
					alert(sim_html);
				}
			});
};

(function($) {
	/**
	 * 收集页面各个字段数据，返回字符串，格式为：abc=1&cde=222&ef=8
	 * 
	 * @return json object
	 */
	$.collectData = function(range) {
		var serialize = [];
		var json = {};
		if (range) {
			serialize.push(range.find("input[type=hidden]"));
			serialize.push(range.find("input[type=text]"));
			serialize.push(range.find("input[type=password]"));
			serialize.push(range.find("input[type=radio]:checked"));
			serialize.push(range.find("input[type=checkbox]:checked"));
			serialize.push(range.find("select"));
			serialize.push(range.find("textarea"));
		} else {
			serialize.push($("input[type=hidden]"));
			serialize.push($("input[type=text]"));
			serialize.push($("input[type=password]"));
			serialize.push($("input[type=radio]:checked"));
			serialize.push($("input[type=checkbox]:checked"));
			serialize.push($("select"));
			serialize.push($("textarea"));
		}
		// 将表单数据转成object
		for (var i = 0; i < serialize.length; i++) {
			serialize[i].each(function() {
						var t = $(this).attr("dataType");
						var cal = $(this).attr("calendar");
						var time = $(this).attr("timepicker");
						var v = $(this).val();
						// 去掉空格
						if (v && !(v instanceof Array)) {
							v = v.trim();
						}
						// ie重置bug，下拉菜单被重置后，val()取值返回的是数组
						if (v && typeof v == "object") {
							v += "";
							v = v.trim();
						}
						var c = $(this).attr("name");
						if (c && c == "pks") {
							if (!json.pks)
								json.pks = [];
							json.pks.push($(this).val());
							return;
						}
						if (t && t.toLowerCase() == "number") {
							if (!v)
								return;
							else
								v = Number(v);
						}
						if (t && t.toLowerCase() == "integer") {
							if (!v)
								return;
							else
								v = Number(v);
						}
						if (t && t.toLowerCase() == "boolean") {
							if (!v)
								return;
							else
								v = Boolean(v);
						}
						if (cal) {
							if (!v)
								return;
							if (v && cal == "0")
								v += " 00:00:00";
							v = v.trim();
						}
						if (time) {
							if (!v)
								return;
						}
						if ($(this).attr("name")) {
							setProperty($(this).attr("name"), v, json);
						}
					});
		}
		return json;
	};
})(jQuery);