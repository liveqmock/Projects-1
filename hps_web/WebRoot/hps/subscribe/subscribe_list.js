$(function() {
	SubscribeManager = {				
				addCheck : function() {					
					return true;
				},
				
				addSubscribe : function() {
					window.open(WWWROOT + "/subscribe/add_form");
				},
				
				confirmSub : function(grid) {
					var selectedIds = grid.jqGrid("getGridParam", "selarrrow");
					if (selectedIds == '') {
						$.alert("请选择需要通过的预约");
						return false;
					}
					if ((selectedIds + "").indexOf(",") != -1) {
						$.alert("每次只能选择一条记录");
						return false;
					}
					var rowData = grid.jqGrid("getRowData", selectedIds);
					if(rowData.confirm=="已确认") {
						$.alert("该预约已经确认，无需重复处理");
						return false;
					}
					
					$.ajax({
						type : "post",
						url : WWWROOT + "/subscribe/confirm",
						data : {
							subscribeIds: selectedIds + ''
						},
						cache : false,
						dataType : 'json',
						success : function(data) {
							if (data!=null && data.success) {
								$.alert("提交成功");
								SubscribeManager.reloadGrid();
							} else {
								$.alert("处理失败，原因：" + data.msg);
							}
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							$.alert("处理失败，请稍后再次尝试删除！");
						}
					});
				},
				refuseOpen:function(grid) {
					var selectedIds = grid.jqGrid("getGridParam", "selarrrow");
					if (selectedIds == '') {
						$.alert("请选择需要拒绝的预约");
						return false;
					}
					if ((selectedIds + "").indexOf(",") != -1) {
						$.alert("每次只能选择一条记录");
						return false;
					}
					var rowData = grid.jqGrid("getRowData", selectedIds);
					if(rowData.confirm=="已确认") {
						$.alert("该预约已经确认，无需重复处理");
						return false;
					}
					
					$("#selectedIdHidden").val(selectedIds + '');
					$("#subMsgWindow").dialog('open');
				},
				
				refuse : function(grid) {
					$.ajax({
						type : "post",
						url : WWWROOT + "/subscribe/refuse",
						data : {
							subscribeIds: $("#selectedIdHidden").val(),
							message:$("#messageHidden").val()
						},
						cache : false,
						dataType : 'json',
						success : function(data) {
							if (data!=null && data.success) {
								$.alert("提交成功");
								$("#subMsgWindow").dialog('close');
								SubscribeManager.reloadGrid();
							} else {
								$.alert("处理失败，原因：" + data.msg);
							}
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							$.alert("处理失败，请稍后再次尝试删除！");
						}
					});
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/subscribe/view_form?subscribeId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteSubscribe : function(grid, subscribeIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/subscribe/delete",
								data : {
									subscribeIds: subscribeIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										SubscribeManager.reloadGrid();
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
				reloadGrid : function() {
					$("#subscribeListGrid").jqGrid("setGridParam", {
								page : 1,
								postData : {
									
								}
							}).trigger("reloadGrid");
				},
				callBack : function() {
					$.alert("提交成功");
				},
				formatTime : function(val, cell, colpos, rwdat, _act) {
					if (val == null)
						return '';
		 
					var time = new Date(val.time);
					return time.dateFormat("Y-m-d H:m:s");

				},
				formatStatus :function(val, cell, colpos, rwdat, _act) {
					var confirmer = colpos.confirmemeber;
					if (val == null || confirmer.length<1)
						return '<font color="blue">未处理</font>';
					if(val==true) {
						return '通过';
					} else {
						return '<font color="red">未通过</font>';
					}

				}
			};
		});