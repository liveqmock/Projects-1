<%@ page contentType="text/html; charset=UTF-8"%>
<nui:html>
<nui:head title="选择用户">
	<script type="text/javascript" src="${ctx}/frame/js/common.js"></script>
	<script type="text/javascript"> 
/**
*1、使用说明：  人员、部门、角色选择页面，用var result = window.showModalDialog(WWWROOT+"/html/sysmanager/selUserOrgRole.jsp",null,"dialogWidth:670px;dialogHeight:400px;center:yes;help:no;scroll:no;status:no;resizable:yes;");方式调用
*2、返回的result是个数组:result[0]为id，result[1]为名称。名次以分号隔开，使用时应注意。
*3、如果需要把已选的值加载到选择框的右边（已选栏），需要将已选的指传递过来，第一个参数为id字符串，多个id以分号隔开，第二个参数为名称，多个名次以分号隔开。id和名称个数以及所在位置必须一致。
**/
//一些常量    
var separator_fenHao = ";";
var limit ='${param.limit}';if(limit==''){limit=true;}

//接受传递的参数
//var params = window.dialogArguments;
  
//添加选中的
function addClick() {
	var sourceBox = document.forms[0].elements['allList'];
	var destinationBox = document.forms[0].elements['seletedList'];
	if (sourceBox.selectedIndex == -1) {
		//没有选中用户，则组装选中的角色和部门以及系统变量
		var sysRoleValue = $("#selectSysRole").attr("value");
		var roleValue = $("#selectRole").attr("value");
		var depValue = $("#selectDepartment").attr("value");
		
		var sysRoleselector = "#selectSysRole>option[value=" + sysRoleValue + "]";
		var roleselector = "#selectRole>option[value=" + roleValue + "]";
		var depSelector = "#selectDepartment>option[value=" + depValue + "]";
		
		var selectValue = "";
		var selectedText = "";
		if(sysRoleValue != "0" && sysRoleValue != "allUser") {
			selectValue = selectValue + sysRoleValue;
			selectedText = selectedText + $(sysRoleselector).text();
		}
		if(depValue != "0" && depValue != "allUser") {
			selectValue = ((selectValue!="")?(selectValue + "|"):(selectValue)) + "DEP_" + depValue;
			selectedText = ((selectedText!="")?(selectedText + "|"):(selectedText)) + $(depSelector).text();
		}
		if(roleValue != "0" && roleValue != "allUser") {
			selectValue = ((selectValue!="")?(selectValue + "|"):(selectValue)) + "ROLE_" + roleValue;
			selectedText = ((selectedText!="")?(selectedText + "|"):(selectedText)) + $(roleselector).text();
		}
		
		destinationBox.options[destinationBox.options.length] = new Option(
						selectedText, selectValue);
		distinctBox('seletedList');
	}
    addSelectedOption('allList', 'seletedList');
   // moveSelectedOption('allList');
}

//添加所有 
function addAllClick() {
    addAllOption('allList', 'seletedList');
    //removeAllOption('allList');
}

//清除选中已选  
function removeClick() { 
    //addSelectedOption('seletedList', 'allList');
    moveSelectedOption('seletedList');
}
//清除有所已选
function removeAllClick() {
	//addAllOption('seletedList','allList');
    removeAllOption('seletedList');
}
//初始化已选记录
function initSeletedList(){  
	if(window.dialogArguments){
	 var selectedUserIds = window.dialogArguments[0];
 	 var selectedUserNames = window.dialogArguments[1];
	 if( selectedUserIds && selectedUserNames ){
		var selectedUserIdsArr = selectedUserIds.split(separator_fenHao);
		var selectedUserNamesArr = selectedUserNames.split(separator_fenHao);
		var optionObj = document.getElementById("seletedList");
	    for(var i = 0; i<selectedUserIdsArr.length; i++){
		    if(selectedUserIdsArr[i]!=""&&selectedUserNamesArr[i]!=""){
		    	var option = new Option();
		        option.value = selectedUserIdsArr[i];
		        option.text = selectedUserNamesArr[i];
		        optionObj.options[optionObj.length] = option;
		    }
	    }
	  }
	}
} 

// 选择后返回数据
function okData() { 
	var returnText = "";
	var returnValue = "";
	var selLength = document.getElementById("seletedList").options.length;

    for (var i = 0; i < selLength; i++) {
        var seletedText = document.getElementById("seletedList").options[i].text;
        var seletedId = document.getElementById("seletedList").options[i].value;
        if (seletedText != 'undefined' && seletedText != '') {
            returnText = uniteTwoStringBySemicolon( returnText , seletedText );
        }
        if (seletedId != 'undefined' && seletedId != '') {
            returnValue = uniteTwoStringBySemicolon( returnValue , seletedId );
        }
    }
    self.returnValue = [returnValue,returnText];
    self.close();
}

function cancel() {
    self.returnValue = null;
    self.close();
}

$(function() { 
    //是否显示所有公司
	var displayAllCompany = '${param.allCompany}';
	var departmentData = new Array();
	
	// 当选择公司变化
	function changeCompany(selOrgId) {
	
	   if(selOrgId!=0){
		  //把除了《所有用户》和《请选择》选项的部门移除
		  $("#selectDepartment option:not([id=plsSelect])").remove();
		  //把除了《所有用户》和《请选择》选项的部门移除
		  $("#selectRole option:not([id=plsSelect])").remove();
		  //如果已选类型为user
		  //var selType = $("#selectType").attr("value");
		  //重新加载用户列表
		  //loadUserListByOrgUnit(selOrgId);
		  	  if(selOrgId == "0") {
	  	selOrgId ="${param.orgId}";
	  }
	  //if(selOrgId != 0) {
	  	initOrgDepList(selOrgId);
	  	initRoleList(selOrgId);
	  //}
	  }

	  //loadOrgUnitListByCompanyId();
	 // loadRoleListByCompanyId();
	  //重新加载部门下拉选择列表
	  //reloadDepartment(selOrgId);
	}

	
	// 当所属部门变化
	function changeRole(roleId) {
		
		if(roleId=="0"){
			orgUnitId="${param.orgId}";
			loadUserListByOrgUnit(orgUnitId);
			return;
		}
		loadUserListByRole(roleId);
	}
	
	// 当所属部门变化
	function changeDepartment(orgUnitId) {
		
		if(orgUnitId=="0"){
			orgUnitId="${param.orgId}";
		}
		loadUserListByOrgUnit(orgUnitId);
	}
	
	//初始化公司
	function initOrgUnitList(){
         $.ajax({
				type : "GET",
				url : WWWROOT + "/process/loadOrgListData",
				cache:false,
				dataType:"json",
				success : function(result) {
				    for(var i=0;i<result.length;i++){
				       var orgUnit = result[i];
				       	if(orgUnit.orgType==1){
				       	  //将公司加到选择公司下拉列表
				          $("<option value='"+orgUnit.orgId+"'>"+orgUnit.orgName+"</option>").appendTo("#selectCompany");
				       } else {
				       }
				    } 
				},
				failure : function() {
				}
			});
	}
	
	//初始化部门列表
	function initOrgDepList(parentId){
         $.ajax({
				type : "GET",
				url : WWWROOT + "/process/loadOrgListData",
				cache:false,
				dataType:"json",
				data:{
					parentId:parentId?parentId:"${param.orgId}",
					orgType:false
				},
				success : function(result) {
				    for(var i=0;i<result.length;i++){
				       var orgUnit = result[i];
				       $("<option value='"+orgUnit.orgId+"'>"+orgUnit.orgName+"</option>").appendTo("#selectDepartment");
				    } 
				},
				failure : function() {
				}
			});
	}
	
	//初始化角色
	function initRoleList(parentId){
         $.ajax({
				type : "GET",
				url : WWWROOT + "/process/loadRoleListByOrgId",
				cache:false,
				dataType:"json",
				data:{
					parentId:parentId?parentId : "${param.orgId}"
				},
				success : function(result) {
				    for(var i=0;i<result.length;i++){
				       var role = result[i];
				          $("<option value='"+role.roleId+"'>"+role.roleName+"</option>").appendTo("#selectRole");
				    } 
				},
				failure : function() {
				}
			});
	}
	

	// 当输入名称搜索
	function search() {
		var inputNameValue = document.getElementById("search").value.Trim();
		if(inputNameValue == ""){
       	 	$.alert("请输入要查询的名称！");
       	 	return false;
    	}else{
	    	var orgId = $("#selectCompany").attr("value");
			if(orgId=="0"){
			var orgId = "${param.orgId}";
			}
	      	 //调用后台方法查询
	      	 $.ajax({
					type : "GET",
					url : WWWROOT + "/sys/userMgr/searchUserOrgRole",
					dataType : "json",
					//async: false,      //ajax同步
					data:{
						type:"user",//查询的类型:user\orgUnit\role
						name:inputNameValue,
						orgId:orgId
					},
					success : function(result) {
					    removeAllOption('allList');
					    if(result){
							var optionObj = document.getElementById("allList");
							    for(var i = 0; i<result.length; i++){
							    	var obj = result[i]; 
							    	var option = new Option();
							        option.value ="USER_" + obj.value;
							        option.text = obj.text;
							        optionObj.options[optionObj.length] = option;
							    }
						    } 
					},
					failure : function() {
					}
				}); 
   		} 
	} 
	
	//首次进来默认加载的用户列表
	function setAllListOption(){
 	  var orgUnitId = $("#selectCompany").attr("value");
 	  if(orgUnitId){
 	     loadUserListByOrgUnit(orgUnitId);
 	  }
 	}
	
	//根据用户角色加载用户列表
	function loadUserListByRole(roleId){ 
	 	if(roleId!=null&&roleId!=""){
			$.ajax({
					type : "GET",
					url : WWWROOT + "/process/loadUserListByRoleId",
					dataType : "json",
					async: false,
					data:{
					  roleId:roleId
					},
					success : function(result) { 
						removeAllOption('allList');
						if(result){
						    var optionObj = document.getElementById("allList");
						    for(var i = 0; i<result.length; i++){
						    	var userObj = result[i]; 
						    	var option = new Option();
						        option.value = "USER_" + userObj.userId;
						        option.text = userObj.userName;
						        optionObj.options[optionObj.length] = option;
						    }
					    }
					},
					failure : function() {
					}
			}); 
		}
	}
		
	//系统角色改变时
	function changeSysRole(value,text){ 
	 	if(value!="0"){
	 		removeAllOption('allList');
	 		var optionObj = document.getElementById("allList");
	 		var option = new Option();
			option.value = value;
			option.text = text;
			optionObj.options[optionObj.length] = option;
		}
	}
	//根据组织机构加载用户列表
	function loadUserListByOrgUnit(orgUnitId){ 
	 	if(orgUnitId!=null&&orgUnitId!=""){
			$.ajax({
					type : "GET",
					url : WWWROOT + "/process/loadUserListByOrgId",
					dataType : "json",
					async: false,
					data:{
					  orgId:orgUnitId
					},
					success : function(result) { 
						removeAllOption('allList');
						if(result){
						    var optionObj = document.getElementById("allList");
						    for(var i = 0; i<result.length; i++){
						    	var userObj = result[i]; 
						    	var option = new Option();
						        option.value = "USER_" + userObj.userId;
						        option.text = userObj.userName;
						        optionObj.options[optionObj.length] = option;
						    }
					    }
					},
					failure : function() {
					}
			}); 
		}
	}

	
	//处理一些按钮响应事件,选择公司时
	$("#selectCompany").change(function(){ 
	    changeCompany($("#selectCompany").attr("value"));
	}); 
	$("#selectRole").change(function(){
	    changeRole($("#selectRole").attr("value"));
	});
	$("#selectSysRole").change(function(){
		var value =$("#selectSysRole").attr("value");
		var selector = "#selectSysRole>option[value=" + value + "]";
	    changeSysRole(value,$(selector).text());
	});
	
	//部门选择改变时触发的事件
	$("#selectDepartment").change(function(){ 
	    changeDepartment($("#selectDepartment").attr("value"));
	}); 
	$("#searchBtn").click(function(){ 
	     search();
	});  
	 
	//初始化已选记录
	initSeletedList();
	
	//加载公司、部门信息
	initOrgUnitList();
	
	initOrgDepList();
	
	//加载角色信息
	initRoleList();
	
	//加载所有待选记录
	setAllListOption();
	
}); 

</script>
</nui:head>
<nui:body>
	<nui:panel style="width:600px;" title="选择用户">
		<nui:field type="input">
			<nui:input title="所属公司:" size="x1">
				<select name="selectCompany" id="selectCompany"
					style="width: 230px;">
					<option id="plsSelect" value="0" selected>
						---请选择---
					</option>
				</select>
			</nui:input>
		</nui:field>
		<nui:field type="input">
			<nui:input title="系统角色：" size="x1">
				<select name="selectSysRole" id="selectSysRole"
					style="width: 230px;">
					<option id="plsSelect" value="0" selected>
						---请选择---
					</option>
					<option value="VAR_Creator">
						流程发起人
					</option>
					<option value="VAR_CreatorDept">
						流程发起人所在部门
					</option>
					<option value="VAR_CreatorManager">
						流程发起人直接领导
					</option>
					<option value="VAR_CreatorLeader">
						流程发起人分管领导
					</option>
					<option value="VAR_CreatorUpDept">
						流程发起人所在部门的上级主管部门
					</option>
					<option value="VAR_Commitor">
						流程提交人
					</option>
					<option value="VAR_CommitorDept">
						流程提交人所在部门
					</option>
					<option value="VAR_CommitorManager">
						流程提交人直接领导
					</option>
					<option value="VAR_CommitorLeader">
						流程提交人分管领导
					</option>
					<option value="VAR_CommitorUpDept">
						流程提交人所在部门的上级主管部门
					</option>
				</select>
			</nui:input>
		</nui:field>
		<nui:field type="input">
			<nui:input title="所属部门:" size="x1">
				<select name="selectDepartment" id="selectDepartment"
					style="width: 230px;">
					<option id="plsSelect" value="0" selected>
						---请选择---
					</option>
				</select>
			</nui:input>
		</nui:field>
		<nui:field type="input">
			<nui:input title="所属角色:" size="x1">
				<select name="selectRole" id="selectRole" style="width: 230px;">
					<option id="plsSelect" value="0" selected>
						---请选择---
					</option>
				</select>
			</nui:input>
		</nui:field>
		<nui:field type="input">
			<nui:input title="输入名称:" size="x2">
				<input id="search" name="search" style="width: 175px;"></input>
				<input id="searchBtn" type="button" value="查询" style="width: 50px;" />
			</nui:input>
		</nui:field>
		<form style="margin: 15px 0 10px 10px;">
			<table width="560px;" border="0" style="border-collapse: collapse;"
				bordercolor="#111111" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<select size="15" name="allList" id="allList"
							ondblclick="addClick();" style="width: 200px; height: 220px;"
							multiple="multiple"></select>
					</td>
					<td style="width: 150px; height: 220px;" valign="middle">
						<p align="center" style="margin: 15px 0">
							<input type="button" value="添加>>" onclick="addClick();">
						</p>
						<p align="center" style="margin: 15px 0">
							<input type="button" value="删除&lt&lt" onclick="removeClick();">
						</p>
						<p align="center" style="margin: 15px 0">
							<input type="button" value="添加所有>>" onclick="addAllClick();">
						</p>
						<p align="center" style="margin: 15px 0">
							<input type="button" value="删除所有&lt&lt"
								onclick="removeAllClick();">
						</p>
					</td>
					<td>
						<select size="15" name="seletedList" id="seletedList"
							ondblclick="removeClick();" style="width: 200px; height: 220px;"
							multiple="multiple"></select>
					</td>
				</tr>
				<tr>
					<td align="center"></td>
					<td align="center">
						<input style="width: 60px; margin-top: 10px;" id="btnCommit"
							type="button" value="确定" onClick="okData();" />
						<input style="width: 60px;" id="btnCancel" type="button"
							value="取消" onClick="cancel();" />
					</td>
					<td align="center"></td>
				</tr>
			</table>
		</form>
	</nui:panel>

</nui:body>
</nui:html>