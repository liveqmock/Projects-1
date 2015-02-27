function changePage() {
	$("#listGrid").show();
	$("#formIframe").hide();
}

$(function() {
	MediaManager = {				
				addCheck : function() {					
					return true;
				},
				
				addMedia : function() {
					$("#listGrid").hide();
					$("#formIframe").attr("src", WWWROOT + "/media/add_form");
					$("#formIframe").show();
				},
				
				editMedia : function(grid, rowData) {
					$("#listGrid").hide();
					$("#formIframe").attr("src", WWWROOT+ "/media/edit_form?mediaId="+ rowData.id);
					$("#formIframe").show();
				},
				
				formatViewLink : function(val, cell, colpos, rwdat, _act) {
					return "<a href=\"javascript:window.open('" + WWWROOT
							+ "/media/view_form?mediaId="
							+ cell.rowId + "');void(0);\">" + val + "</a>";
				},
				
				delCheck : function() {
					return true;
				},
				
				deleteMedia : function(grid, mediaIds) {
					if (!confirm("确认要删除选中记录吗？")) {
						return;
					}
					$.ajax({
								type : "post",
								url : WWWROOT + "/media/delete",
								data : {
									mediaIds: mediaIds
								},
								cache : false,
								dataType : 'json',
								success : function(data) {
									if (data && data.success) {
										$.alert("已经删除成功");
										MediaManager.reloadGrid();
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
					$("#mediaListGrid").jqGrid("setGridParam", {
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
					return time.dateFormat("Y-m-d hh:mm:ss");
				},
				tryListen : function(val, cell, colpos, rwdat, _act) {
					return "<a style='color:blue;' target='_blank' href='" + WWWROOT+ "/record/media/media_form_bk.jsp?id=" + colpos.id + "'>试听</a>" ;
				}
			};
		});