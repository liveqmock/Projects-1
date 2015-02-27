<%@ tag pageEncoding="UTF-8"%>
<%@tag import="java.util.List"%>
<%@tag import="java.util.Iterator"%>
<%@tag import="java.util.Arrays"%>
<%@tag import="java.util.ArrayList"%>
<%@tag import="java.util.*,net.sf.json.JSONArray,net.sf.json.JSONObject"%>

<%@ attribute name="id" type="java.lang.String" required="true" %><%--处理checkbox必须用到的属性，设为必选--%>
<%@ attribute name="name" type="java.lang.String" required="true" %><%--处理checkbox必须用到的属性，设为必选--%>
<%@ attribute name="url" type="java.lang.String" required="false" %>
<%@ attribute name="value" type="java.lang.String" required="false"%><%--checkbox的选中的value--%>
<%--@ attribute name="namespace" type="java.lang.Boolean" required="false"%><%--为门户的多实例服务--%>
<%@ attribute name="keyname" type="java.lang.String" required="false"%><%--checkbox的json数据的key的名称--%>
<%@ attribute name="valuename" type="java.lang.String" required="false"%><%--checkbox的json数据的value的名称--%>
<%@ attribute name="all" type="java.lang.Boolean" required="false"%><%--是否显示所有的勾选项--%>		
<%@ attribute name="allvalue" type="java.lang.String" required="false"%><%--所有的勾选项的值--%>
<%@ attribute name="items" type="java.lang.String" required="false"%><%--用户硬编码的勾选项--%>

<script type="text/javascript">
var CheckBoxTag = ({
	loadDataFromUrl : function(url,keyname,valuename,selectedvalue,id,name,all,allvalue){
		$.ajax({
			type : "GET",
			url :  url,
			dataType : "json",
			async : false,
			success : function(result) {
				//var jsonResult = $.toJSON(result);
				//判断是否启用命名空间机制
				//var usenamespace = false;
				var $container;
			 	//if("${namespace}" == "") {
			 	//	usenamespace = true;
			 	//}
				//if(usenamespace) {
				var divid="#" + id + "checkboxtagdiv";
				$container = $(divid);
				//$container = $("#${id}checkboxtagdiv");//${id}获取ID不准确？
				//} else {
				//	$container = $("#checkboxtagdiv");
				//}
				
				if(result && $container) {
					if(all!=null && all!="null" && all != "undefined") {
						allhtml = "" + "<input type='checkbox' id='" + id + "_all" + "' name='" + id + "_all" + "' value='" +
					 	allvalue + "' onclick='thisClickAll(this);'/><label for='"+ id + "_all" + "' style='cursor: pointer; float: none; text-align: left;margin-top:0px;'>" +
					 	"所有" + "</label>";		
					 	$container.append(allhtml);				
					}
					for(var i=0;i<result.length;i++) {
						var checkboxhtml = "";
						if(keyname && valuename) {
							 //传入的键值为字符串，则需以[]下标的形式取值，否则以.的形式取值
							 /*if(result[i][keyname] == selectedvalue) { 
								checkboxhtml = "" + "<input type='checkbox' id='" + id + "_" + i + "' name='" + name + "' value='" +
							 	result[i][keyname] + "'/><label for='"+ id + "_" + i + "' style='cursor: pointer; float: none; text-align: left;margin-top:0px;'>" +
							 	result[i][valuename] + "</label>";
							 }else {*/
								checkboxhtml = "" + "<input type='checkbox' id='" + id + "_" + i + "' name='" + name + "' value='" +
							 	result[i][keyname] + "'/><label for='"+ id + "_" + i + "' style='cursor: pointer; float: none; text-align: left;margin-top:0px;'>" +
							 	result[i][valuename] + "</label>";
							 //}
						}else {
							/*if(result[i].value == selectedvalue) {
								checkboxhtml = "" + "<input type='checkbox' id='" + id + "_" + i + "' name='" + name + "' value='" +
							 	result[i][keyname] + "'/><label for='"+ id + "_" + i + "' style='cursor: pointer; float: none; text-align: left;margin-top:0px;'>" +
							 	result[i][valuename] + "</label>";
							}else {*/
								checkboxhtml = "" + "<input type='checkbox' id='" + id + "_" + i + "' name='" + name + "' value='" +
							 	result[i][keyname] + "'/><label for='"+ id + "_" + i + "' style='cursor: pointer; float: none; text-align: left;margin-top:0px;'>" +
							 	result[i][valuename] + "</label>";
							//}
						}
						
						$container.append(checkboxhtml);
					}
					
					//初始化选中的checkbox
					var Type = selectedvalue;
					 if(Type!="null"){
						 if(Type==allvalue){
						 	$container.find('input[type=checkbox]').attr("checked",true);
						 }else{
						    var typeArr = Type.split(",");
						    if(typeArr.length>0){
						        for(var i=0;i<typeArr.length;i++){
						 			$container.find('input[value="'+typeArr[i]+'"]').attr("checked",true); 
						 		}
						 	}
						 }	
					 }
					//初始化结束
				}
			},
			failure : function() {
			}
		});			
	}
});

 function thisClickAll(obj,$outerDiv) {

 	//if("${namespace}" == "") {
 	//	usenamespace = true;
 	//}

 	//TODO:外层div多实例的情况未做处理(考虑直接用传入的id作为外层的div的id)
 	//if(usenamespace) {
 	//var $outerDiv = $("#${id}checkboxtagdiv");
 	var $outerDiv = $(obj).parent();
 	//} else {
 	//	$outerDiv = $("#checkboxtagdiv");
 	//}
	if ($(obj)[0].checked) {
		//$(obj).nextAll().attr("checked","checked"); 
		$outerDiv.find('input[type=checkbox]:gt(0)').attr("checked",true);
	} else {
		$outerDiv.find('input[type=checkbox]').attr("checked",null); 
	}
 }
 
 /*function thisClick(val) {
	 var ff = "";
	 $("#${id}_div").find('input[type=checkbox]:checked').each(function(){
		 if(ff == "")ff = $(this).val();
		 else ff+=","+$(this).val();
	});
	 $("#${id}").val(ff);
 }*/
 </script>

<%
	//默认模式下使用namespace
	//if(namespace == null) {
	//	namespace = true;
	//}

	//如果配置了url属性，则根据url的路径加载checkbox的数据
	if (url != null && !"".equals(url)) {

		StringBuffer scriptBuffer = new StringBuffer();
		//多实例的情况下，前面要加上传入的id作为前缀
		//if(namespace) {
			out.print("<div id='" + id + "checkboxtagdiv'></div>");		
		//} else {
		//	out.print("<div id='checkboxtagdiv'></div>");			
		//}

		scriptBuffer.append("<script type='text/javascript'>");
		scriptBuffer
				.append("$(document).ready(function(){CheckBoxTag.loadDataFromUrl('"
						+ url
						+ "','"
						+ keyname
						+ "','"
						+ valuename
						+ "','"
						+ value
						+ "','"
						+ id
						+ "','"
						+ name
						+ "','"
						+ all
						+ "','" + allvalue + "');");

		scriptBuffer.append("});");
		scriptBuffer.append("</script>");
		out.print(scriptBuffer.toString());
		return;
	} else if(items!=null) {//如果硬编码了相应的项，则解析硬编码的值
		JSONArray jsonArray = JSONArray.fromObject("["
				+ items.replaceAll("'", "\"") + "]");
		
		StringBuffer domBuffer = new StringBuffer();
		//if(namespace) {
			//domBuffer.append("<div id='" + id + "checkboxtagdiv'>");		
		//} else {
		//	domBuffer.append("<div id='checkboxtagdiv'>");			
		//}
		if(all) {
			domBuffer.append("" + "<input type='checkbox' id='" + id + "_all" + "' name='" + name + "' value='" +
		 	allvalue + "' onclick='thisClickAll(this);'/><label for='"+ id + "_all" + "' style='cursor: pointer; float: none; text-align: left;margin-top:-1px;'>" +
		 	"所有" + "</label>");			
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			List selectedValueList = new ArrayList();
			if (value != null) {
					selectedValueList = Arrays.asList(value.split(","));
			}
			
			domBuffer.append("<input type='checkbox' id='" + id + "_" + i + "' name='" + name + "' value='" + 
			json.getString("value")).append("'");
			if(selectedValueList.contains(json.getString("value"))) {
				domBuffer.append(" checked ");
			}
			domBuffer.append("/><label for='"+ id + "_" + i + 
			"' style='cursor: pointer; float: none; text-align: left;margin-top:0px;'>" + json.getString("displayname") + "</label>");
			
		}
		//domBuffer.append("</div>");
		out.print(domBuffer.toString());
	}		

%>