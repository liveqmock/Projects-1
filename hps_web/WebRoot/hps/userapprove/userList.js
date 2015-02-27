(function($) {
	$(function() {
		$("#roleid").change(function(){
			UserList.filterClick($(this).val());
		});
		
		treeActive = function(orgId, orgName, orgType) {
			UserList.orgId = orgId;
			UserList.orgName = orgName;
			UserList.orgType = orgType;
			
			UserList.resetSearchCondition();
			UserList.filterClick();
		};
		UserList = {
			orgId : defaultOrgId,
			orgName : defaultOrgName,
			orgType : false,
			approveUser : function(grid, ids) {
				if (!confirm("审核通过后，请电话通知相关人员，是否通过？")) {
					return;
				}
				$.ajax({
							type : "post",
							url : window.WWWROOT + "/hps/userMgr/approveUser",
							data : {
								orgId : this.orgId,
								userIds : ids
							},
							dataType : 'json',
							success : function(data) {
								if (data && data.success) {
									$.alert("审核成功");
									UserList.reloadGrid();
								} else {
									$.alert("审核失败，原因：" + data.msg);
								}
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								$.alert("审核失败，请稍后再次尝试删除！！");
							}
						});
			},
			formatUserName : function(val, cell, colpos, rwdat, _act) {
				return "<a href=\"javascript:window.open('" + WWWROOT
						+ "/hps/userMgr/viewUserFormPage?defaultOrgId="
						+ defaultOrgId + "&userId=" + cell.rowId
						+ "');void(0);\">" + val + "</a>";
			},
			formatOperate: function(val, cell, colpos, rwdat, _act) {
				return "<a style='color:blue' href=\"javascript:window.open('" + WWWROOT
				+ "/media/user_add_form?userId="
				+ cell.rowId 
				+ "');void(0);\">上传音乐</a>";
			},
			formatTime : function(val, cell, colpos, rwdat, _act) {
				if (val == null)
					return '';
				var time = new Date(val.time);
				return time.dateFormat("Y-m-d hh:mm:ss");
			},
			formateSex : function(val, cell, colpos, rwdat, _act) {
				if (val) {
					return '男';
				} else {
					return '女';
				}
			},
			reloadGrid : function() {
				$("#userListGrid").jqGrid("setGridParam", {
							page : 1,
							postData : {
								orgId : this.orgId
							}
						}).trigger("reloadGrid");
			},
			/**
			 * 格式化操作
			 */
			orderUser : function(val, cell, colpos, rwdat, _act) {
				var html = '<div style="width:40px;margin:0 auto;"><div style="float:left;"><a class="ui-icon ui-icon-arrowthick-1-n" title="向上移动" href="javascript:UserList.moveUpDown(\''
						+ cell.rowId
						+ '\',\'up\')"></a></div><div style="margin-left:20px;">';
				html += '<a class="ui-icon ui-icon-arrowthick-1-s" title="向下移动" href="javascript:UserList.moveUpDown(\''
						+ cell.rowId + '\',\'down\')"></a></div></div>';
				return html;
			},
			resetSearchCondition:function(){
				$("input[name='userName']").val('');
				$("input[name='account']").val('');
				$("#SexSelect").val('');
			},
			filterClick : function(roleId) {
				// grid_filter_
				var json = $.collectFilterData($("#filterField"));
				setProperty("orgId", this.orgId, json);
				setProperty("roleId", roleId, json);
				$("#userListGrid").jqGrid("setGridParam", {
							postData : json
						}).trigger("reloadGrid");
			}
		};
	});

})(jQuery);
