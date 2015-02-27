$(function() {
			OrgTree = {
				// 点击数节点后执行的方法
				active : function(node, event) {
					treeActive(node.data.id, node.data.title, node.data.orgType);
				},
				expand : function(keyId) {
					var node = $("#orgTree").dynatree("getActiveNode");
					if (node == null) {
						var newTree = $("#orgTree").dynatree("getTree");
						node = newTree.getNodeByKey(keyId);
					}
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
				initTree : function() {
					setTimeout(function() {
								var firstNodeId = defaultOrgId;
								var tree = $("#orgTree").dynatree("getTree");
								var node = tree.getNodeByKey(firstNodeId);
								if (node) {
									node.expand(true);
								} else {
									OrgTree.initTree();
								}
							}, 100);
				}
			}
			/**
			 * 延时加载使根节点展开
			 */
			OrgTree.initTree();
		});