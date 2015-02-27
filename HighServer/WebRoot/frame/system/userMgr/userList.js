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
			addUser : function() {
				this.orgId = "00000000000000000000000000000000";
				//if (!this.orgId
				//		|| /^00000000000000000000000000000000/.test(this.orgId)) {
				//	$.alert("请先选择公司或部门！");
				//	return;
				//}
				$("#listGrid").hide();
				$("#formIframe").attr("src", window.WWWROOT
						+ "/sys/userMgr/addUserFormPage?orgId=" + this.orgId);
				$("#formIframe").show();
			},
			editUser : function(grid, rowData) {
				var theUserId = rowData.userId;
				$("#listGrid").hide();
				$("#formIframe").attr("src", window.WWWROOT
						+ "/sys/userMgr/editUserFormPage?defaultOrgId="
						+ defaultOrgId + "&userId=" + theUserId);
				$("#formIframe").show();
			},
			deleteUser : function(grid, ids) {
				if (!confirm("删除用户时，会把用户从当前组织及其所有下级组织中移除，\n如果此用户不属于任何组织时，则会把此用户确实删除！\n是否继续？")) {
					return;
				}
				// 如果没有点击组织机构节点。
				if (!this.orgId) {
					// 这里要获取用户当前登录的公司，删除当前登录公司以及下级公司之间的关联。
				}
				$.ajax({
							type : "post",
							url : window.WWWROOT + "/sys/userMgr/deleteUser",
							data : {
								orgId : this.orgId,
								userIds : ids
							},
							dataType : 'json',
							success : function(data) {
								if (data && data.success) {
									$.alert("已经删除成功。");
									UserList.reloadGrid();
								} else {
									$.alert("删除失败，原因：" + data.msg);
								}
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								$.alert("删除失败，请稍后再次尝试删除！！");
							}
						});
			},
			formatUserName : function(val, cell, colpos, rwdat, _act) {
				return "<a href=\"javascript:window.open('" + WWWROOT
						+ "/sys/userMgr/viewUserFormPage?defaultOrgId="
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
			/**
			 * 排序
			 */
			moveUpDown : function(userId, orderType) {
				$.ajax({
							type : "post",
							url : WWWROOT + "/sys/userMgr/sortUser",
							cache : false,
							data : {
								userId : userId,
								orgId : this.orgId,
								orderType : orderType
							},
							dataType : 'json',
							success : function(data) {
								if (data && data.success) {
									$("#userListGrid").trigger("reloadGrid");
								}
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								$.alert("操作失败，请稍后再次尝试！");
							}
						});
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
