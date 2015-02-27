function changePage() {
	$("#listGrid").show();
	$("#formIframe").hide();
}
$(function() {
	AdphotoManager = {				
				addCheck : function() {					
					return true;
				},
				
				addAdphoto : function() {
					$("#listGrid").hide();
					$("#formIframe").attr("src", WWWROOT + "/adphoto/add_form");
					$("#formIframe").show();
				},
				
				editAdphoto : function(grid, rowData) {
					$("#listGrid").hide();
					$("#formIframe").attr("src", WWWROOT+ "/adphoto/edit_form?adphotoId="+ rowData.id);
					$("#formIframe").show();
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/adphoto/view_form?adphotoId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				showtupian : function(val, cell, colpos, rwdat, _act) {
					return '<img style="width:100px;height:50px;" src="' + WWWROOT + '/attach/dowmloadAttach?attachId=' + colpos.fileId + '"></img>';
				},
				formatFinish: function(val, cell, colpos, rwdat, _act) {
					if(val!=null) {
						if(val=="1") {
							return "已结束";
						}	
					}
					return "未结束";
				},
				formatOperate: function(val, cell, colpos, rwdat, _act) {
					return "<a style='color:blue' href=\"javascript:AdphotoManager.finishAd('"
					+ colpos.id 
					+ "');void(0);\">结束活动</a>";
				},
				finishAd : function(id) {
					if (!confirm("确认要结束广告活动吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/adphoto/finish",
								data : {
									id: id
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("结束广告成功");
										AdphotoManager.reloadGrid();
									} else {
										$.alert("结束失败：" + data.msg);
									}
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									$.alert("操作失败，请稍后再次尝试删除！");
								}
							});
				},	
				formatType : function(val, cell, colpos, rwdat, _act) {
					if(val!=null) {
						if(val=="1") {
							return "顶部广告";
						}else if(val=="2") {
							return "左侧广告";
						}else if(val=="3") {
							return "活动广告";
						}else {
							return val;
						}
					}
					
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteAdphoto : function(grid, adphotoIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/adphoto/delete",
								data : {
									adphotoIds: adphotoIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										AdphotoManager.reloadGrid();
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
					$("#adphotoListGrid").jqGrid("setGridParam", {
								page : 1,
								postData : {
									
								}
							}).trigger("reloadGrid");
				},
				formatCreateTime : function(val, cell, colpos, rwdat, _act) {
					var time = new Date(val.time);
					return time.dateFormat("Y-m-d hh:mm:ss");
				},
				callBack : function() {
					$.alert("提交成功");
				}
			};
		});