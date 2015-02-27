/**
 * @author fengmq
 */
(function($) {
	$(function() {
		treeActive = function(parentId, kindId, kindName, kindDes) {
			DictionaryMgr.parentId = parentId;
			DictionaryMgr.kindId = kindId;
			DictionaryMgr.kindName = kindName;
			DictionaryMgr.kindDes = kindDes;
			DictionaryMgr.reloadGrid();
		}
		DictionaryMgr = {
			parentId : 0,
			kindId : 0,
			kindName : '',
			kindDes : '',
			operatorType : 'add',
			addType : function() {
				if (this.kindId == 0) {
					$.alert("请先选择类别目录！");
					return;
				}
				this.operatorType = "add";
				$("#typeMgrWindow #kindName_t").val(this.kindName);
				$("#typeMgrWindow #kindId_t").val(this.kindId);
				$("#typeMgrWindow #typeId").val("");
				$("#typeMgrWindow #typeName").val("");
				$("#typeMgrWindow #typeDes").val("");
				$("#typeMgrWindow").parent().find(".ui-button-text:eq(0)")
						.parent().show();
				$("#typeMgrWindow").dialog("option", "title", '添加类型')
						.dialog("open");
			},
			editType : function(grid, rowData) {
				this.operatorType = "edit";
				var typeId = rowData.typeId;
				var typeName = rowData.typeName;
				var typeDes = rowData.typeDes;
				var sortIndex = rowData.sortIndex;
				$("#typeMgrWindow #kindName").val(this.kindName);
				$("#typeMgrWindow #kindId_t").val(this.kindId);
				$("#typeMgrWindow #typeId").val(typeId);
				$("#typeMgrWindow #typeName").val($(typeName).text());
				$("#typeMgrWindow #typeDes").val(typeDes);
				$("#typeMgrWindow #sortIndex").val(sortIndex);
				$("#typeMgrWindow").parent().find(".ui-button-text:eq(0)")
						.parent().show();
				$("#typeMgrWindow").dialog("option", "title", '修改类型')
						.dialog("open");
			},
			viewType : function(typeId, typeName, typeDes) {
				$("#typeMgrWindow #kindName").val(this.kindName);
				$("#typeMgrWindow #kindId").val(this.kindId);
				$("#typeMgrWindow #typeId").val(typeId);
				$("#typeMgrWindow #typeName").val(typeName);
				$("#typeMgrWindow #typeDes").val(typeDes);
				$("#typeMgrWindow").parent().find(".ui-button-text:eq(0)")
						.parent().hide();
				$("#typeMgrWindow").dialog("option", "title", '类型信息')
						.dialog("open");
			},
			deleteType : function(grid, typeIds) {
				if (!confirm("确定要删除选中的类型？")) {
					return;
				}
				$.ajax({
							type : "post",
							url : WWWROOT
									+ "/dictionaryMgr/deleteDictionaryType",
							data : {
								typeIds : typeIds
							},
							dataType : 'json',
							success : function(data) {
								if (data && data.success) {
									$.alert("类型删除成功。");
									DictionaryMgr.reloadGrid();
								} else {
									$.alert("删除失败，请重试。");
								}
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								$.alert("删除失败，请稍后再次尝试删除！！");
							}
						});
			},
			submitTypeForm : function() {
				var url = WWWROOT
						+ "/dictionaryMgr/submitDictionaryType?operatorType="
						+ this.operatorType;
				AjaxSubmit(url, function(result) {
							if (result.success == true) {
								$.alert("提交成功。");
								DictionaryMgr.reloadGrid();
								$("#typeMgrWindow").dialog("close");
							} else {
								alert(result.msg);
							}
						}, null, false, 'json', $("#typeMgrWindow"));
			},
			formatTypeName : function(val, cell, colpos, rwdat, _act) {
				return "<a href=\"javascript:DictionaryMgr.viewType('"
						+ cell.rowId + "','" + colpos.typeName + "','"
						+ colpos.typeDes + "');void(0);\">" + val + "</a>";
			},
			reloadGrid : function() {
				$("#dictionaryTypeList").jqGrid("setGridParam", {
							page : 1,
							postData : {
								kindId : this.kindId
							}
						}).trigger("reloadGrid");
			},
			/**
			 * 格式化操作
			 */
			sortIndex : function(val, cell, colpos, rwdat, _act) {
				var html = '<div style="width:40px;margin:0 auto;"><div style="float:left;"><a class="ui-icon ui-icon-arrowthick-1-n" title="向上移动" href="javascript:DictionaryMgr.moveUpDown(\''
						+ cell.rowId
						+ '\',\'up\')"></a></div><div style="margin-left:20px;">';
				html += '<a class="ui-icon ui-icon-arrowthick-1-s" title="向下移动" href="javascript:DictionaryMgr.moveUpDown(\''
						+ cell.rowId + '\',\'down\')"></a></div></div>';
				return html;
			},
			/**
			 * 排序
			 */
			moveUpDown : function(typeId, orderType) {
				$.ajax({
							type : "post",
							url : WWWROOT + "/dictionaryMgr/sortIndex",
							cache : false,
							data : {
								typeId : typeId,
								kindId : DictionaryMgr.kindId,
								orderType : orderType
							},
							dataType : 'json',
							success : function(data) {
								if (data && data.success) {
									DictionaryMgr.reloadGrid();
								}
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								$.alert("操作失败，请稍后再次尝试！");
							}
						});
			}
		};
	});
})(jQuery);
