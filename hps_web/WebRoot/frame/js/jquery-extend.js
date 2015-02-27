(function($) {
	$.fn.extend({
				calendar : function() {
					return this.each(function() {
								var obj = $(this).get(0);
								var inLi = $("<p></p>");
								inLi.addClass("ui-state-default ui-corner-all")
										.click(function() {
													$(obj).focus();
												}).css({
													"position" : "absolute",
													"right" : "30px",
													"top" : "11px"
												});
								if ($.browser.msie) {
									if ($.browser.version == "7.0") {
										inLi.css("top", "7px");
									} else {
										inLi.css("top", "11px");
									}
								}
								var inSpan = $("<span></span>");
								inSpan.addClass("ui-icon ui-icon-calculator");

								inLi.append(inSpan);
								$(this).parent().prepend(inLi).hover(
										function() {
											inLi.addClass("ui-state-hover");
										}, function() {
											inLi.removeClass("ui-state-hover");
										});;
								obj.getAttribute("calendar");
								obj.readOnly = true;
								obj.onfocus = function() {
									// Import("js.util.Calendar");
									CalendarConstructor(this.id, type);
								};
							});
				}
			});

	$.fn.extend({
				/*
				 * 选择控件
				 */
				processSelect : function() {
					return this.each(function() {
								var obj = $(this).get(0);
								var inLi = $("<p></p>");
								inLi
										.addClass("ui-state-default ui-icon-search")
										.click(function() {
													$(obj).focus();
												}).css({
													"position" : "absolute",
													"right" : "28px",
													"top" : "11px",
													"cursor" : "pointer"
												});
								if ($.browser.msie) {
									if ($.browser.version == "7.0") {
										inLi.css("top", "7px");
									} else {
										inLi.css("top", "11px");
									}
								}
								var inSpan = $("<span></span>");
								inSpan.addClass("ui-icon ui-icon-search");
								inLi.append(inSpan);
								$(this).parent().prepend(inLi).hover(
										function() {
											inLi.addClass("ui-state-hover");
										}, function() {
											inLi.removeClass("ui-state-hover");
										});
								var selectFunction = obj
										.getAttribute("selectFunction")
										+ "()";
								// firefox可以多次点击，弹出多个选择框，为防止该现象的出现，新增标志判断
								var flag = true;
								inLi.bind('click', function() {
											if ($.browser.mozilla) {
												if (flag) {
													flag = false;
													eval(selectFunction);
													flag = true;
												}
											} else {
												eval(selectFunction);
											}
										});
							});
				}
			});

	/** 搜索面板搜集搜索条件数据 */
	$.collectFilterData = function(range) {
		var serialize = [];
		var json = {};
		serialize.push(range.find("input[type=hidden]"));
		serialize.push(range.find("input[type=text]"));
		serialize.push(range.find("input[type=password]"));
		serialize.push(range.find("input[type=radio]:checked"));
		serialize.push(range.find("input[type=checkbox]:checked"));
		serialize.push(range.find("select"));
		serialize.push(range.find("textarea"));
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
								v = "";
							else
								v = Number(v);
						}
						if (t && t.toLowerCase() == "integer") {
							if (!v)
								v = "";
							else
								v = Number(v);
						}
						if (t && t.toLowerCase() == "boolean") {
							if (!v)
								v = "";
							else
								v = Boolean(v);
						}
						if (cal) {
							if (v) {
								if (cal == "0")
									v += " 00:00:00";
								v = v.trim();
							} else {
								v = "";
							}
						}
						if (time) {
							if (!v)
								v = "";
						}
						if ($(this).attr("name")) {
							var filterType = $(this).attr("filterType");
							if (!filterType) {
								filterType = "like";
							}
							setProperty("grid_filter_" + $(this).attr("name")
											+ "filterTypeSeparator"
											+ filterType, v, json);
						}
					});
		}
		return json;
	};
})(jQuery);
// 页面初始化
$(function() {
			// 1、统一处理日期控件
			$("input[type=text][calendar]").calendar();
			//$("input[type=text][timepicker]").timePicker();
			$("input[type=text][selectFunction]").processSelect();
		});