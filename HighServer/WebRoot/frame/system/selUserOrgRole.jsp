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
//指定公司，此时需要隐藏公司选择框
var companyId = '${param.companyId}';

//接受传递的参数
//var params = window.dialogArguments;
  
//添加选中的
function addClick() {
    addSelectedOption('allList', 'seletedList');
}

//添加所有 
function addAllClick() {
    addAllOption('allList', 'seletedList');
}

//清除选中已选  
function removeClick() { 
    moveSelectedOption('seletedList');
}
//清除有所已选
function removeAllClick() {
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
	  //把除了《所有用户》选项的部门移除
	  $("#selectDepartment option:not([id=allUser])").remove();
	  //如果已选类型为user
	  var selType = $("#selectType").attr("value");
	  if(selType=="user"){ 
		  //重新加载用户列表
		  loadUserListByOrgUnit(selOrgId);
	  }else if(selType=="orgUnit"){
	  	  loadOrgUnitListByCompanyId();
	  }else if(selType=="role"){
	  	  loadRoleListByCompanyId();
	  }
	  //重新加载部门下拉选择列表
	  reloadDepartment(selOrgId);
	}

	//当选择类别变化
	function changeType(selectedType) {
	    var orgSelCombo = $("#selectDepartment");
		if (selectedType == "user") {
			orgSelCombo.removeAttr('disabled'); 
		} else {
			orgSelCombo.attr('disabled', 'disabled'); 
		}
		if (selectedType == "user") { 
			var selDep = $("#selectDepartment").attr("value");
			if(selDep=="allUser"){
				selDep = $("#selectCompany").attr("value");
			}
			loadUserListByOrgUnit(selDep);
			
		} else if (selectedType == "orgUnit") { 
			loadOrgUnitListByCompanyId(); 
		} else if (selectedType == "role") { 
			loadRoleListByCompanyId();
		}
	}
	// 当所属部门变化
	function changeDepartment(orgUnitId) {
		if(orgUnitId=="allUser"){
			orgUnitId=$("#selectCompany").attr("value");
		}
		loadUserListByOrgUnit(orgUnitId);
	}
	//初始化公司、部门列表
	function initOrgUnitList(){
         $.ajax({
				type : "GET",
				url : WWWROOT + "/sys/orgUnitMgr/getOrgUnitJson",
				cache:false,
				dataType:'json',
				async: false,      //ajax同步
				data:{
					defaultOrgId:defaultOrgId,
					limit:limit
				},
				success : function(result) {
				    var firstCompanyId ="";
				    for(var i=0;i<result.length;i++){
				       var orgUnit = result[i];
				       //如果是公司
				       if(orgUnit.orgType==1){
				       	  //将公司加到选择公司下拉列表
				          $("<option value='"+orgUnit.orgId+"'>"+orgUnit.orgName+"</option>").appendTo("#selectCompany"); 
				          if(firstCompanyId==""){
				             firstCompanyId = orgUnit.orgId;
				          }
				       }else{
				         //如果是部门，保存到department对象中。
				      	  departmentData.push(orgUnit); 
				       } 
				    } 
				     //将第一个公司的部门加到部门下拉列表中
				       for(var j=0;j<departmentData.length;j++)
				       {
				         var orgUnit = departmentData[j]; 
				         if(orgUnit.parentOrgId==firstCompanyId){
				       	   $("<option value='"+orgUnit.orgId+"'>"+orgUnit.orgName+"</option>").appendTo("#selectDepartment");
				         } 
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
	    	var searchType = $("#selectType").attr("value"); 
	    	var orgId = $("#selectDepartment").attr("value");
			if(orgId=="allUser"){
			    orgId = $("#selectCompany").attr("value");
			}
	      	 //调用后台方法查询
	      	 $.ajax({
					type : "GET",
					url : WWWROOT + "/sys/userMgr/searchUserOrgRole",
					dataType : "json",
					//async: false,      //ajax同步
					data:{
						type:searchType,//查询的类型:user\orgUnit\role
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
							        option.value =obj.value;
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
	
	//根据组织机构加载用户列表
	function loadUserListByOrgUnit(orgUnitId){ 
	 	if(orgUnitId!=null&&orgUnitId!=""){
			$.ajax({
					type : "GET",
					url : WWWROOT + "/sys/userMgr/loadUserListJson",
					dataType : "json",
					async: false,      //ajax同步
					data:{
					  orgUnitId:orgUnitId
					},
					success : function(result) { 
						removeAllOption('allList');
						if(result){
						    var optionObj = document.getElementById("allList");
						    for(var i = 0; i<result.length; i++){
						    	var userObj = result[i]; 
						    	var option = new Option();
						        option.value = userObj.userId;
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
	/**
	 *根据公司ID重新加载左边选择列表
	*/
	function loadOrgUnitListByCompanyId(companyId){
		 removeAllOption('allList');
		 var companyId =  $("#selectCompany").attr("value");
		 var optionObj = document.getElementById("allList");
		 for(var j=0;j<departmentData.length;j++)
		  {
			  var orgUnit = departmentData[j]; 
			  if(companyId==orgUnit.parentOrgId){ 
				 var option = new Option();
				 option.value = orgUnit.orgId;
				 option.text = orgUnit.orgName;
				 optionObj.options[optionObj.length] = option;
			  } 
		  } 
	}
	/**
	 *根据公司ID重新加载左边选择列表
	*/
	function loadRoleListByCompanyId(){
		 removeAllOption('allList');
		 var orgUnitId =  $("#selectCompany").attr("value");
		 	$.ajax({
					type : "GET",
					url : WWWROOT + "/sys/roleMgr/loadRolesByOrgUnitId",
					dataType : "json",
					async: false,      //ajax同步
					data:{
					  orgUnitId:orgUnitId
					},
					success : function(result) {  
						if(result){
						    var optionObj = document.getElementById("allList");
						    for(var i = 0; i<result.length; i++){
						    	var roleObj = result[i]; 
						    	var option = new Option();
						        option.value = roleObj.roleId;
						        option.text = roleObj.roleName;
						        optionObj.options[optionObj.length] = option;
						    }
					    }
					},
					failure : function() {
					}
			}); 
	}
	
	//重新加载部门下拉列表
	function reloadDepartment(selOrgId){ 
		for(var j=0;j<departmentData.length;j++)
		{
			  var orgUnit = departmentData[j]; 
			  if(orgUnit.parentOrgId==selOrgId){
			     $("<option value='"+orgUnit.orgId+"'>"+orgUnit.orgName+"</option>").appendTo("#selectDepartment");
			  } 
		} 
	}
	
	//处理一些按钮响应事件
	$("#selectCompany").change(function(){ 
	    changeCompany($("#selectCompany").attr("value"));
	}); 
	$("#selectType").change(function(){
	    changeType($("#selectType").attr("value"));
	});
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
	//加载所有待选记录
	setAllListOption();
}); 

</script>
</nui:head>
<nui:body>
	<body>
		<p style="margin: 8px;${empty param.companyId?'':'display:none;'}">
			<label>
				所属公司:
			</label>
			<select name="selectCompany" id="selectCompany" style="width: 230px;">
			</select>
		</p>
		<p style="margin: 8px;">
			<label>
				选择类别:
			</label>
			<select name="selectType" id="selectType" style="width: 230px;">
				<option value="user">
					按用户
				</option>
				<option value="orgUnit">
					按组织
				</option>
				<option value="role">
					按角色
				</option>
			</select>
		</p>
		<p style="margin: 8px;">
			<label>
				选择部门:
			</label>
			<select name="selectDepartment" id="selectDepartment"
				style="width: 230px;">
				<option id="allUser" value="allUser">
					所有用户
				</option>
			</select>
		</p>
		<p style="margin: 8px;">
			<label>
				输入名称:
			</label>
			<input id="search" name="search" style="width: 175px;"></input>
			<input id="searchBtn" type="button" value="查询" style="width: 50px;" />
		</p>
		<form style="margin: 15px 0 10px 10px;">
			<table width="650px;" border="0" style="border-collapse: collapse;"
				bordercolor="#111111" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<select size="15" name="allList" id="allList"
							ondblclick="addClick();" style="width: 250px; height: 220px;"
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
							ondblclick="removeClick();" style="width: 250px; height: 220px;"
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
</nui:body>
</nui:html>