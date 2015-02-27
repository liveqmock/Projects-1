<%@ tag pageEncoding="UTF-8"  %>
<%@tag import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag import="java.util.List"%>
<%@tag import="java.util.Iterator"%>
<%@tag import="java.util.Arrays"%>
<%@tag import="java.util.Random"%>
<%@tag import="java.util.*,net.sf.json.JSONArray,net.sf.json.JSONObject"%>

<%@ attribute name="name" type="java.lang.String" required="true"  %>
<%@ attribute name="id" type="java.lang.String" required="false"  %>
<%@ attribute name="url" type="java.lang.String" required="false"  %>
<%@ attribute name="keyname" type="java.lang.String" required="false"%><%--radio tag的json数据的key的名称--%>
<%@ attribute name="valuename" type="java.lang.String" required="false"%><%--radio tag的json数据的value的名称--%>
<%@ attribute name="content" type="java.lang.String" required="false"  %>

<%@ attribute name="style" type="java.lang.String" required="false"  %>
<%@ attribute name="onclick" type="java.lang.String" required="false"  %>
<%@ attribute name="onchange" type="java.lang.String" required="false"  %>
<%@ attribute name="value" type="java.lang.String" required="false"  %>
<%@ attribute name="noshot" type="java.lang.Boolean" required="false"  %>
<%@ attribute name="defaultname" type="java.lang.String" required="false"  %>

<script type="text/javascript">
var RadioTag = ({
	loadDataFromUrl : function(url,keyname,valuename,selectedvalue,id,name,stylestr,onclick){
		$.ajax({
			type : "GET",
			url :  url,
			dataType : "json",
			async : false,
			success : function(result) {
				//获得外层的容器
				$container = $("#${id}radiotagdiv");
				if(result && $container) {
					for(var i=0;i<result.length;i++) {
						var radiohtml = "";
						if(keyname && valuename) {
							 //传入的键值为字符串，则需以[]下标的形式取值，否则以.的形式取值
							 if(result[i][keyname] == selectedvalue) { 
								checkboxhtml = "" + "<input onclick='" + onclick + "' type='radio' checked style='" +stylestr + "' id='" + id + "_" + i + "' name='" + name + "' value='" +
							 	result[i][keyname] + "'/><label for='"+ id + "_" + i + "' style='cursor:pointer;float:none;text-align:left;font-weight:normal;'>" +
							 	result[i][valuename] + "</label>";
							 }else {
								checkboxhtml = "" + "<input onclick='" + onclick + "' type='radio' style='" + stylestr + "' id='" + id + "_" + i + "' name='" + name + "' value='" +
							 	result[i][keyname] + "'/><label for='"+ id + "_" + i + "' style='cursor:pointer;float:none;text-align:left;font-weight:normal;'>" +
							 	result[i][valuename] + "</label>";
							 }
						}else {
							if(result[i].value == selectedvalue) {
								checkboxhtml = "" + "<input onclick='" + onclick + "' type='radio' checked style='" +stylestr + "' id='" + id + "_" + i + "' name='" + name + "' value='" +
							 	result[i][keyname] + "'/><label for='"+ id + "_" + i + "' style='cursor:pointer;float:none;text-align:left;font-weight:normal;'>" +
							 	result[i][valuename] + "</label>";
							}else {
								checkboxhtml = "" + "<input onclick='" + onclick + "' type='radio' style='" +stylestr + "' id='" + id + "_" + i + "' name='" + name + "' value='" +
							 	result[i][keyname] + "'/><label for='"+ id + "_" + i + "' style='cursor:pointer;float:none;text-align:left;font-weight:normal;'>" +
							 	result[i][valuename] + "</label>";
							}
						}
						$container.append(checkboxhtml);
					}
				}
			},
			failure : function() {
			}
		});			
	}
});
</script>

<%
	if(id==null) id="";//必须处理属性值为null的情况，否则会出现空值针异常或者语法错误
	if(onclick==null) onclick="";
	if(onchange==null) onchange="";


	String noshotstr = "";
	if(noshot !=null && noshot == true)
		noshotstr = "noshot='true'";
		
	//处理style属性开始
	String styleStr="width:30px;display:inline;";
	if(style!=null) {
		styleStr = style;
	}
	//处理结束

	if (url != null && !"".equals(url)) {

		StringBuffer scriptBuffer = new StringBuffer();

		//多实利的情况下传入的id不同
		out.print("<div id='" + id + "radiotagdiv'></div>");


		scriptBuffer.append("<script type='text/javascript'>");
		scriptBuffer
				.append("$(document).ready(function(){RadioTag.loadDataFromUrl('"
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
						+ styleStr 
						+ "','"
						+ onclick + "');");

		scriptBuffer.append("});");
		scriptBuffer.append("</script>");
		out.print(scriptBuffer.toString());
	}else if (content != null) {//如果用户配置了自己的radio的内容
		StringBuffer outdiv = new StringBuffer("");
		outdiv.append("<div id='"+id+"'>");
		JSONArray jsonArray = JSONArray.fromObject("["
				+ content.replaceAll("'", "\"") + "]");
		for (int i = 0; i < jsonArray.size(); i++) {
			int ran = new Random().nextInt();
			JSONObject json = jsonArray.getJSONObject(i);
			if(value!=null && json.getString("value").equals(value)){
				outdiv.append("<input "+noshotstr+" type='radio' style='" + styleStr + "' onchange='"+onchange+"' onclick='"+onclick+"' name='"+name+"' id='"+name+i+"_"+ran+"' value='"+json.getString("value")+"' checked><label style='cursor:pointer;float:none;text-align:left;font-weight:normal;' for='"+name+i+"_"+ran+"'>"+json.getString("displayname")+"</label>");
			}else{
				outdiv.append("<input "+noshotstr+" type='radio' style='" + styleStr + "' onchange='"+onchange+"' onclick='"+onclick+"' name='"+name+"' id='"+name+i+"_"+ran+"' value='"+json.getString("value")+"'><label style='cursor:pointer;float:none;text-align:left;font-weight:normal;' for='"+name+i+"_"+ran+"'>"+json.getString("displayname")+"</label>");
			}
			outdiv.append("&nbsp;&nbsp;");
		}
		outdiv.append("</div>");
		out.print(outdiv.toString());
	}

%>