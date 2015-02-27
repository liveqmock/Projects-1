$(function() {
	KindTree = {
		// 点击数节点后执行的方法
		active : function(node, event) {
			treeActive(node.data.parentId, node.data.id, node.data.title,
					node.data.kindDes);
		},
		expand : function(keyId) {
			var newTree = $("#kindTree").dynatree("getTree");
			node = newTree.getNodeByKey(keyId);
			if (node != null) {
				try {
					node.reloadChildren();
				} catch (e) {
					node.data.isLazy = true;
					node.render();
					node.reloadChildren();
				}
				node.expand(true);
			}
		},
		addKind : function() {
			if (DictionaryMgr.kindId == 0) {
				$.alert("只能添加子类别，请先选择父类别。");
				return false;
			}
			$("#kindMgrWindow #parentKindName").val(DictionaryMgr.kindName);
			$("#kindMgrWindow #parentKindId").val(DictionaryMgr.kindId);
			$("#kindMgrWindow #kindName").val("");
			$("#kindMgrWindow #kindDes").val("");
			$("#kindMgrWindow").dialog("option", "title", '添加子类别')
					.dialog("open");
		},
		editKind : function() {
			if (DictionaryMgr.parentId == 0) {
				$.alert("一级类别不能修改");
				return false;
			}
			var tree = $("#kindTree").dynatree("getTree");
			node = tree.getNodeByKey(DictionaryMgr.parentId);
			$("#kindMgrWindow #parentKindName").val(node.data.title);
			$("#kindMgrWindow #parentKindId").val(DictionaryMgr.parentId);
			$("#kindMgrWindow #kindId").val(DictionaryMgr.kindId);
			$("#kindMgrWindow #kindName").val(DictionaryMgr.kindName);
			$("#kindMgrWindow #kindDes").val(DictionaryMgr.kindDes);
			$("#kindMgrWindow").dialog("option", "title", '修改子类别')
					.dialog("open");
		},
		deleteKind : function() {
			if (DictionaryMgr.parentId == 0) {
				$.alert("一级类别不能删除");
				return false;
			}
			if (!confirm("将同时删除其子类别以及相关类型，确定删除？")) {
				return;
			}
			var url = WWWROOT + "/dictionaryMgr/deleteDictionaryKind?kindId="
					+ DictionaryMgr.kindId;
			$.ajax({
						type : "POST",
						url : url,
						dataType : "json",
						timeout : 60000,
						success : function(result) {
							$.alert("删除成功。");
							KindTree.expand(DictionaryMgr.parentId);
						},
						error : function(xhr, str, err) {
							alert("删除失败，请重试。");
						}
					});
		},
		submitForm : function() {
			var url = WWWROOT + "/dictionaryMgr/submitDictionaryKind";
			AjaxSubmit(url, function() {
						$.alert("提交成功。");
						KindTree
								.expand($("#kindMgrWindow #parentKindId").val());
						$("#kindMgrWindow").dialog("close");
					},null, false, 'json', $("#kindMgrWindow"));
		}
	}
});