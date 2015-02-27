<%@ tag pageEncoding="UTF-8"%>
<%@tag import="java.util.List"%>
<%@tag import="java.util.Iterator"%>
<%@tag import="java.util.Arrays"%>
<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@tag import="java.util.ArrayList"%>
<%@tag import="java.util.*,net.sf.json.JSONArray,net.sf.json.JSONObject"%>

<%@ attribute name="value" type="java.lang.String" required="false"%>
<%@ attribute name="url" type="java.lang.String" required="false"%>
<%@ attribute name="options" type="java.lang.String" required="false"%>
<%@ attribute name="keyname" type="java.lang.String" required="false"%>
<%@ attribute name="valuename" type="java.lang.String" required="false"%>
<%@ attribute name="plzSelect" type="java.lang.Boolean" required="false"%>

<script type='text/javascript'>
	var OptionTag = ({
		loadOptions : function(url,keyname,valuename,selectedvalue){
			$.ajax({
				type : "GET",
				url :  url,
				dataType : "json",
				async : false,
				success : function(result) {
					var $select = $("#test1").parent();
					if(result && $select) {
						for(var i=0;i<result.length;i++) {
							var optionhtml = "";
							if(keyname && valuename) {
								 //传入的键值为字符串，则需以[]下标的形式取值，否则以.的形式取值
								 if(result[i][keyname] == selectedvalue) {
								 	optionhtml = "<option selected value='" + result[i][keyname] + "'>" + result[i][valuename] +  "</option>";
								 }else {
								 	optionhtml = "<option value='" + result[i][keyname] + "'>" + result[i][valuename] +  "</option>";
								 }
							}else {
								if(result[i].value == selectedvalue) {
									optionhtml = "<option selected value='" + result[i].value + "'>" + result[i].name +  "</option>";
								}else {
									optionhtml = "<option value='" + result[i].value + "'>" + result[i].name +  "</option>";
								}
								 
							}
							$select.append(optionhtml);
						}
						$("#test1").remove();
					}
				},
				failure : function() {
				}
			});			
		}
	});
</script>


<%
	//是否显示请选择的option
	if (plzSelect == null)
		plzSelect = false;

	if (plzSelect) {
		out.print("<option value=''>--请选择--</option>");
	}

	if (url != null && !"".equals(url)) {

		out.print("<option id='test1' value=''></option>");
		StringBuffer scriptBuffer = new StringBuffer();

		scriptBuffer.append("<script type='text/javascript'>");
		scriptBuffer
				.append("$(document).ready(function(){OptionTag.loadOptions('"
						+ url
						+ "','"
						+ keyname
						+ "','"
						+ valuename
						+ "','" + value + "');");

		scriptBuffer.append("});");
		scriptBuffer.append("</script>");
		out.print(scriptBuffer.toString());
		return;
	} else if (options != null) {
		StringBuffer optionStr = new StringBuffer("");
		JSONArray jsonArray = JSONArray.fromObject("["
				+ options.replaceAll("'", "\"") + "]");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			if (value != null && json.getString("value").equals(value)) {
				optionStr.append("<option selected value='"
						+ json.getString("value") + "'>"
						+ json.getString("displayname") + "</option>");
			} else {
				optionStr.append("<option value='"
						+ json.getString("value") + "'>"
						+ json.getString("displayname") + "</option>");
			}
		}
		out.print(optionStr.toString());
	}
%>