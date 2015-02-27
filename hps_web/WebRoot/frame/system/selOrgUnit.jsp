<%@ page contentType="text/html; charset=UTF-8"%>
<nui:html>
<nui:head title="选择组织机构">
	<script type="text/javascript" src="${ctx}/frame/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/frame/js/plugin/chosen/chosen.jquery.js"></script>
	<link rel="stylesheet" href="${ctx}/frame/js/plugin/chosen/chosen.css" />
	<script type="text/javascript"> 
/**
//配置参数1：（type：company只选公司、dept只选部门、all或不加此参数就都选）。参数2：（limit:true表示只能选择有权限的公司，即用户所在公司以及下级公司；false表示可以选择所有组织,默认为true）
*
*1、使用说明：  部门选择页面，用var result = window.showModalDialog(WWWROOT+"/html/sysmanager/selUserOrgRole.jsp",null,"dialogWidth:670px;dialogHeight:350px;center:yes;help:no;scroll:no;status:no;resizable:yes;");方式调用
*2、返回的result是个数组:result[0]为id，result[1]为名称。id,名称以分号隔开，使用时应注意。
*3、如果需要把已选的值加载到选择框的右边（已选栏），需要将已选的指传递过来，第一个参数为id字符串，多个id以分号隔开，第二个参数为名称，多个名次以分号隔开。id和名称个数以及所在位置必须一致。
**/ 
var separator_fenHao = ";";
//只能添加一个
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
	 	alert('只能选一个');
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
    //根据右边选中的内容组织成字符串数组返回
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
	//配置参数：type：company只选公司、dept只选部门、all或不加此参数就都选
	var selType = '${param.type}'; 
	//是否添加限制，显示显示公司为用户所属公司以及下属公司，如果为false，表示显示所有公司
	var limit ='${param.limit}';if(limit==''){limit=true;}
	//通过url传递过来的参数，是否允许多选。
	onlyOne = '${param.onlyOne}';if(onlyOne==''){onlyOne=false;}
  	//缓存所有公司
	var companyData = new Array();  
	//缓存所有部门
	var departmentData = new Array();  
	//初始化公司、部门列表
	function initOrgUnitList(){ 
         $.ajax({
				type : "GET",
				url : WWWROOT + "/sys/orgUnitMgr/getOrgUnitJson",
				dataType : "json",
				cache:false,
				async:false,
				data:{
					defaultOrgId:defaultOrgId,
					limit:limit
				},
				success : function(result) {
				 if(result&&result.length>0){
					 var firstCompanyId ="";
					 var firstCompanyName ="";
					    for(var i=0;i<result.length;i++){
					       var orgUnit = result[i];
					       //如果是公司
					       if(orgUnit.orgType==1){					    	  					    	 
					       	   companyData.push(orgUnit);
					       	   if(firstCompanyId==""){
					              firstCompanyId = orgUnit.orgId;
					              firstCompanyName = orgUnit.orgName;
					           }
					       }else{
					         //如果是部门，保存到department对象中。
					      	  departmentData.push(orgUnit); 
					       }
					     } 
					//根据url传递的参数，只能选公司
				 	if(selType=="company"){
				 	    //隐藏下拉和搜索
					  	$("#selectCompany").parent().hide();
					  //	$("#search").parent().hide();
					  	var optionObj = document.getElementById("allList");
						for(var i = 0; i<companyData.length; i++){
							  var orgUnit = companyData[i]; 
							  var orgId = orgUnit.orgId;
							  if(!isSelected(orgId)){ 
								 var option = new Option();
								 option.value = orgId;
								 option.text = orgUnit.orgName;
								 optionObj.options[optionObj.length] = option;
							  } 
						}  
					//可以选择部门，如果setType=dept：只能选部分，否则部门公司均可以选择
				 	}else{
				 		 for(var i=0;i<companyData.length;i++){
				 	    	 var com = companyData[i];				 	    	 				 	    	 
				 	    	 //将公司加到选择公司下拉列表
					          $("<option value='"+com.orgId+"'>"+com.orgName+"</option>").appendTo("#selectCompany");
				 	    } 
				 	    loadOrgUnitListByCompanyId(firstCompanyId,firstCompanyName); 
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
       	 	alert("请输入要查询的名称！");
       	 	return false;
    	}else{ 
	    	var  orgId = $("#selectCompany").attr("value"); 
	      	 //调用后台方法查询
	      	 $.ajax({
					type : "GET",
					url : WWWROOT + "/sys/userMgr/searchUserOrgRole",
					dataType : "json",
					//async: false,      //ajax同步
					data:{
						type:"orgUnit",//查询的类型:user\orgUnit\role
						name:inputNameValue,
						orgId:orgId
					},
					success : function(result) {
					    removeAllOption('allList');
					    if(result){
							var optionObj = document.getElementById("allList");
							    for(var i = 0; i<result.length; i++){
							     	var obj = result[i]; 
							    	var value = obj.value; 
							    	if(!isSelected(value)){
								    {
								    	var option = new Option();
								        option.value =value;
								        option.text = obj.text;
								        optionObj.options[optionObj.length] = option;
								    }
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
	function loadOrgUnitListByCompanyId(companyId, companyName) {
		removeAllOption('allList');
		var companyId = $("#selectCompany").val();
		var optionObj = document.getElementById("allList");
		if (selType != "dept" && selType != "company" && !isSelected(companyId)) {
			var option = new Option();
			option.value = companyId;
			option.text = companyName;
			optionObj.options[optionObj.length] = option;
		}

		(function getSubDept(parentId) {
			for ( var i = 0; i < departmentData.length; i++) {
				var orgUnit = departmentData[i];
				var subId = orgUnit.orgId;
				if (orgUnit.parentOrgId == parentId && !isSelected(subId)) {
					var option = new Option();
					option.value = subId;
					option.text = orgUnit.orgName;
					optionObj.options[optionObj.length] = option;
					getSubDept(subId);
				}
			}
		})(companyId);
	} 
	
 
	$("#searchBtn").click(function(){ 
	     search();
	});  
	 
	//初始化已选记录
	initSeletedList();
	
	//加载公司、部门信息
	initOrgUnitList(); 
	
	//加载选择的公司下的机构列表
	$(".chzn-select").chosen({
		"no_results_text" : "没有相关搜索项"
	}).change(function() {
		loadOrgUnitListByCompanyId($(this).val(), $(this).find("option:selected").text());
	});
	
}); 
</script>
</nui:head>
<nui:body>
	<p style="margin: 8px; height: 30px">
		<select name="selectCompany" id="selectCompany" data-placeholder="请选择公司" style="width: 350px; height: 20px" class="chzn-select">
			<option value=""></option>
		</select>
	</p>
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