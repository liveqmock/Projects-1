var attach_loaded = false;
$(function() {
	
	// 页面操作类型，add(添加)？edit(编辑)？view(查看)
	var operation = $("#operation").val();
	userMgr = {
		// 初始化页面信息
		init : function() {
			// 编辑状态
			if (operation != 'view') {
				// 所属角色按钮
				$("#selRoles").bind("dblclick", function() {
					var hiddenSelRoles = $("#hiddenSelRoles").val();
					var result = window
							.showModalDialog(
									WWWROOT
											+ "/frame/system/selRole.jsp?limit=true",
									[hiddenSelRoles, $("#selRoles").val()],
									"dialogWidth:670px;dialogHeight:350px;center:yes;help:no;scroll:no;status:no;resizable:yes;");
					$("#selRoles").focus();
										
					$("#selRoles").removeClass("gray_font");
					if (!result) {
						return;
					}
					$("#selRoles").val(result[1]);
					var hiddenSelRoles = result[0];
					$("#hiddenSelRoles").val(hiddenSelRoles);
				});
								
				this.initTips();
			} else {
				// 查看的时候不显示保存按钮
				$("input").attr("readOnly", "readOnly");
				$("input[type='radio']").attr("disabled", "disabled");
				$("textarea").attr("readOnly", "readOnly");
				$("#btnCommit").hide();
			}
		},
		// 提交按钮事件处理
		submitForm : function(form) {
			this.removeTips();					
			var url = WWWROOT + "/sys/userMgr/submitUserForm";
			AjaxSubmit(url, function() {
						try {
							window.parent.$.alert("提交成功");
							window.parent.changePage();
							if (window.parent.UserList) {
								window.parent.UserList.reloadGrid();
							}
						} catch (e) {
							alert("提交成功,刷新页面后方可看到最新数据。");
						}
						window.close();
					},"userFormId");
		},
		//初始化选择组织和选择角色的提示信息
		initTips:function(){
			var selectedOrgs=$("#selOrgUnits").val();
					
			if(selectedOrgs==''){
				$("#selOrgUnits").addClass("gray_font");
				$("#selOrgUnits").val("请双击选择所属组织");
			}
			
			var selectedRoles=$("#selRoles").val();					
			
			if(selectedRoles==''){
				$("#selRoles").addClass("gray_font");
				$("#selRoles").val("请双击选择所属角色");
			}
		},
		//去除掉提示信息
		removeTips:function(){
			var selectedOrgs=$("#selOrgUnits").val();
			if(selectedOrgs=='请双击选择所属组织'){				
				$("#selOrgUnits").val("");
			}
			
			var selectedRoles=$("#selRoles").val();					
			if(selectedRoles=='请双击选择所属角色'){				
				$("#selRoles").val("");
			}
		},
		uploadRevoke:function() {
			//上传图片成功后的回调：
			var id = $("#fileIds_memberImage").val();
			var index = id.indexOf(",");
			if(index != -1) {
				id=id.substring(0,index);
			}
			$("#fileId").val(id);
			var imageHtml = '<img class="imagePre" src="' +  WWWROOT + '/attach/dowmloadAttach?attachId=' +  id + '" id="targetImage" />';
			$("#imageArea").append(imageHtml);
			$('#targetImage').Jcrop({
				onSelect: updateCoords,
				onDblClick:submitCut,
				allowResize:false
			});
		}
	};
	// 执行初始化方法
	userMgr.init();
	
});
function updateCoords(c)
{
  $('#x').val(c.x);
  $('#y').val(c.y);
  $('#w').val(c.w);
  $('#h').val(c.h);
};

function submitCut() {
	$.ajax({
		type : "POST",
		url : WWWROOT + "/sys/userMgr/cutImage",
		dataType : "json",
		//async: false,      //ajax同步
		data:{
			x:$('#x').val(),
			y:$('#y').val(),
			w:$('#w').val(),
			h:$('#h').val(),
			fileId:$("#fileId").val()
		},
		success : function(result) {
			//$("#targetImage").remove();
			
			$(".imagePre").remove();
			$(".jcrop-holder").remove();
			//$(".imagePre").attr("src",WWWROOT + '/attach/dowmloadAttach?' +  Math.random());
			//$("#targetImage").attr("src",WWWROOT + '/attach/dowmloadAttach?attachId=' +  $("#fileId").val());
			var imageHtml = '<img src="' +  WWWROOT + '/attach/dowmloadAttach?' +  Math.random() + '&attachId=' +  $("#fileId").val() + '" id="targetImage" />';
			$("#imageArea").append(imageHtml);
		},
		failure : function() {
		}
	}); 
}