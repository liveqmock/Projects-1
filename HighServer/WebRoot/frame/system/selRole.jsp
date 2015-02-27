<%@ page contentType="text/html; charset=UTF-8"%>
<nui:html>
<nui:head title="选择角色">
	<script type="text/javascript" src="${ctx}/frame/js/common.js"></script>
	<script type="text/javascript"> 
/**
参数1：companyId，如果指定了companyId，则只显示该公司内的角色，如果未指定，看是否指定了参数2，根据参数2过滤，如果均未指定，可以选择所有公司的角色
参数2：（limit:true表示只能选择有权限的公司，即用户所在公司以及下级公司；false表示可以选择所有组织,默认为true）
*使用说明：1、角色选择页面，用var result = window.showModalDialog(WWWROOT+"/html/sysmanager/selUserOrgRole.jsp",null,"dialogWidth:670px;dialogHeight:350px;center:yes;help:no;scroll:no;status:no;resizable:yes;");方式调用
*2、返回的result是个数组:result[0]为id，result[1]为名称。名次以分号隔开，使用时应注意。
*3、如果需要把已选的值加载到选择框的右边（已选栏），需要将已选的指传递过来，第一个参数为id字符串，多个id以分号隔开，第二个参数为名称，多个名次以分号隔开。id和名称个数以及所在位置必须一致。
**/ 
//分号分隔符
var separator_fenHao = ";";

var onlyOne = false;
  
//接受传递的参数
//var params = window.dialogArguments;
  
//添加选中的
function addClick() {
	if(validateSelected('allList', 'seletedList',onlyOne)){
	    addSelectedOption('allList', 'seletedList');
	    moveSelectedOption('allList');
    }
}

//添加所有 
function addAllClick() {
	if(onlyOne&&document.forms[0].elements['allList'].options.length>1){
	 	$.alert('只能选一个');
	 	return;
	}
    if(validateSelected('allList', 'seletedList',onlyOne)){
	    addAllOption('allList', 'seletedList');
	    removeAllOption('allList');
    }
}

//清除选中已选  
function removeClick() { 
    addSelectedOption('seletedList', 'allList');
    moveSelectedOption('seletedList');
}
//清除有所已选
function removeAllClick() {
	addAllOption('seletedList','allList');
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
	//是否添加限制，显示显示公司为用户所属公司以及下属公司，如果为false，表示显示所有公司
	var limit ='${param.limit}';if(limit==''){limit=true;}
	//通过url传递过来的参数，是否允许多选。
	onlyOne = '${param.onlyOne}';if(onlyOne==''){onlyOne=false;}
	

	// 当输入名称搜索
	function search() {
		var inputNameValue = document.getElementById("search").value.Trim();
		if(inputNameValue == ""){
       	 	alert("请输入要查询的名称！");
       	 	return false;
    	}else{ 
	      	 //调用后台方法查询
	      	 $.ajax({
					type : "GET",
					url : WWWROOT + "/sys/userMgr/searchUserOrgRole",
					dataType : "json",
					//async: false,      //ajax同步
					data:{
						type:"role",//查询的类型:user\orgUnit\role
						name:inputNameValue
					},
					success : function(result) {
					    removeAllOption('allList');
					    if(result){
							var optionObj = document.getElementById("allList");
							    for(var i = 0; i<result.length; i++){
								    var obj = result[i]; 
							    	var value = obj.value; 
							    	if(!isSelected(value)){
								    	var option = new Option();
								        option.value =value;
								        option.text = obj.text;
								        optionObj.options[optionObj.length] = option;
							    	}
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
	function loadRoleListByCompanyId(){
		 removeAllOption('allList');
		 $.ajax({
					type : "GET",
					url : WWWROOT + "/sys/roleMgr/loadRolesByOrgUnitId",
					dataType : "json",
					async: false,      //ajax同步
					data:{
					},
					success : function(result) {  
						if(result){
						    var optionObj = document.getElementById("allList");
						    for(var i = 0; i<result.length; i++){
							    var roleObj = result[i]; 
						     	var roleId = roleObj.roleId; 
							    if(!isSelected(roleId)){
							    	var option = new Option();
							        option.value = roleId;
							        option.text = roleObj.roleName;
							        optionObj.options[optionObj.length] = option;
							    }
						    }
					    }
					},
					failure : function() {
					}
			}); 
	}
	
	$("#searchBtn").click(function(){ 
	     search();
	});  
	 
	//初始化已选记录
	initSeletedList();
	loadRoleListByCompanyId(); 
	
}); 
</script>
</nui:head>
<nui:body>
	<p style="margin:8px;">
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
					<input style="width: 60px;" id="btnCancel" type="button" value="取消"
						onClick="cancel();" />
				</td>
				<td align="center"></td>
			</tr>
		</table>
	</form>
</nui:body>
</nui:html>