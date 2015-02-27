$(function() {
			treeActive = function(key) {
				LogManager.logType = key;
				LogManager.filterClick();
			}
			LogManager = {
				logType : "operate",
				deleteLog : function(grid, logIds) {
					$.ajax({
								type : "post",
								url : WWWROOT + "/log/deleteLog",
								cache : false,
								data : {
									logIds : logIds
								},
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("删除成功!");
										grid.trigger("reloadGrid");
									} else {
										$.alert("删除失败，原因：" + data.msg);
									}
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									$.alert("删除失败，请稍后再次尝试删除！");
								}
							});
				},
				formateLogDate : function(val, cell, colpos, rwdat, _act) {
					if (val) {
						var time = new Date(val.time);
						return time.dateFormat("Y-m-d hh:mm:ss");
					} else {
						return "";
					}
				},
				formatLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/log/viewLog?logId=" + colpos.logId
							+ "&logType=operate" + "');void(0);\">" + val
							+ "</a>";
				},
				formatDetail : function(val, cell, colpos, rwdat, _act) {
					if (val.length > 100) {
						return val.substr(0, 100) + "....";
					} else {
						return val;
					}
				},
				/**
				 * 刷新grid
				 */
				reloadGrid : function() {
					$("#logListGrid").jqGrid("setGridParam", {
								postData : {
									logType : "operate"
								}
							}).trigger("reloadGrid");
				},
				filterClick : function() {
					// grid_filter_
					var json = $.collectFilterData($("#filterField"));
					setProperty("logType", "operate", json);
					$("#logListGrid").jqGrid("setGridParam", {
								page : 1,
								postData : json
							}).trigger("reloadGrid");
				}
			}
		});