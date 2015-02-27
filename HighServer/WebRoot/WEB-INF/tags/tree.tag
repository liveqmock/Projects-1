<%@ tag pageEncoding="UTF-8"%>
<%@tag import="java.util.*,net.sf.json.JSONArray,net.sf.json.JSONObject"%>

<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<%@ attribute name="width" type="java.lang.String" required="false"%>
<%@ attribute name="buttons" type="java.lang.String" required="false"%><%--tree 面板的按钮 --%>
<%@ attribute name="checkbox" type="Boolean" required="false"%><%--tree节点是否显示checkbox--%>
<%@ attribute name="selectmode" type="Integer" required="false"%>
<%@ attribute name="checkboxclass" type="java.lang.String"
	required="false"%><%--checkbox的样式类，可以是checkbox，也可以是radio--%>
<%@ attribute name="onclick" type="java.lang.String" required="false"%><%--拦截树的点击事件--%>
<%@ attribute name="showroot" type="Boolean" required="false"%>
<%@ attribute name="imagePath" type="java.lang.String" required="false"%><%--节点图标的父路径--%>
<%@ attribute name="children" type="java.lang.String" required="false"%><%--传递children字符串来初始化树--%>
<%@ attribute name="showfolder" type="Boolean" required="false"%>
<%@ attribute name="url" type="java.lang.String" required="false"%>
<%@ attribute name="onselect" type="java.lang.String" required="false"%>
<%@ attribute name="onlazyRead" type="java.lang.String" required="false"%>
<%@ attribute name="onactivate" type="java.lang.String" required="false"%>
<%@ attribute name="ondeactivate" type="java.lang.String"
	required="false"%>
<%@ attribute name="onexpand" type="java.lang.String" required="false"%>
<%@ attribute name="rootid" type="java.lang.String" required="false"%>
<%@ attribute name="onready" type="java.lang.String" required="false"%>

<script type='text/javascript'>
var TreeTag = ({
newNode : function(node){
	var tnode = {};   
	tnode = node;
	if(tnode.isleaf=="0")
		tnode.isFolder = true;
	else 
		tnode.isFolder = false;
	tnode.isLazy = false;
	tnode.expand = true;
    return tnode;
}, 

createDyNodes : function(url, parentid, newnodefnc) {
	return {
    	type : "post",
        url: url,
        dataType:"json",
        data: {id:parentid},
        onPostInit: function(dtnode, json){
            var nodes = json.list;   
            var children = []; 
            var node = {}; 
            var i;
            for(i=0; nodes && i<nodes.length;i++){
                if ( newnodefnc ){
                	node  = newnodefnc(nodes[i]);
                }
                else      
                	node  = TreeTag.newNode(nodes[i]);
				<%if(showfolder==null || !showfolder){%>
               		node.isFolder = false;
                <%}%>
                
            	if(node.isLazy != false) node.isLazy = true;
                children.push(node);   
            }
            if(children.length>0)
                dtnode.append(children);
        },
        success: function(dtnode) {
        },   
        error: function(dtnode, XMLHttpRequest, textStatus, errorThrown) {   
        },   
        cache: false
    };    
}
	}
);
</script>

<%
	StringBuffer outerDiv = new StringBuffer();
	Map<String, String> eventMap = new HashMap<String, String>();
	//tree panel 最外层的div
	//样式的宽度和高度不能设置为100%,因为div的边框占据像素
	outerDiv.append("<div class='ui-accordion-content ui-widget-content ui-corner-all ui-accordion-content-active' style='height:97%;padding:0;margin:0;overflow:auto;'>");
	//tree panel 工具栏的div 工具栏用来摆放按钮
	//如果用户配置了自己的button
	if (buttons != null) {
		outerDiv.append("<div style='height:98.5%'>");
		outerDiv.append("<table class='ui-state-default ui-th-column ui-th-ltr' style='width:100%;height:10px;'><tbody><tr>");
		JSONArray jsonArray = JSONArray.fromObject("["
				+ buttons.replaceAll("'", "\"") + "]");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);

			outerDiv.append("<td>");
			outerDiv.append("<button style='width:100%' title='")
					.append(json.getString("title")).append("' id='")
					.append(json.getString("id"))
					.append("' class='ui-state-default ui-corner-all'");
			outerDiv.append("onmouseout=\"$(this).removeClass('ui-state-hover')\" ");
			outerDiv.append("onmouseover=\"$(this).addClass('ui-state-hover')\" ");
			if (json.containsKey("onclick")
					&& json.getString("onclick") != null) {
				outerDiv.append("onclick='")
						.append(json.getString("onclick"))
						.append("(this)'>");
			}
			outerDiv.append("<span class='")
					.append(json.getString("className"))
					.append("'></span>");
			outerDiv.append("<span class=\"ui-inputHead_t\">")
					.append(json.getString("name"))
					.append("</span></button>");
			outerDiv.append("</td>");
		}
		outerDiv.append("</tr></tbody></table>");
	}
	outerDiv.append("<div id='").append(id).append("' style='");//删除样式overflow:auto;，否则横向滚动条在IE8下的位置不对
	if (height != null) {
		outerDiv.append("height:").append(height).append("px;");
	} else {
		//outerDiv.append("height:98.5%").append(";");//获取外层div高度的值
	}
	if (width != null) {
		outerDiv.append("width:").append(width).append("px;'");
	} else {
		outerDiv.append(";'");//自适应外层的宽度
	}
	outerDiv.append(">");

	outerDiv.append("</div>");
	outerDiv.append("</div>");
	if (buttons != null) {
		outerDiv.append("</div>");
	}
	out.print(outerDiv.toString());

	StringBuffer scriptBuffer = new StringBuffer();

	scriptBuffer.append("<script type='text/javascript'>");
	scriptBuffer.append("$(function(){");
	for (Map.Entry<String, String> entry : eventMap.entrySet()) {
		scriptBuffer.append(" $('#").append(entry.getKey())
				.append("').click(function(){")
				.append(entry.getValue()).append("(this);")
				.append("});");
	}
	scriptBuffer.append("});");
	scriptBuffer.append("</script>");
	out.print(scriptBuffer.toString());
%>

<%
	try {
		Boolean isLazy = null;
		if (rootid == null)
			rootid = "";
		if (checkbox == null)
			checkbox = false;

		if (url == null)
			isLazy = true;

		//1.为单选2.为多选3.继承式的多选
		if (selectmode == null)
			selectmode = 3;

		StringBuffer sb = new StringBuffer();

		sb.append("<script type='text/javascript'>var hadLoadTree_"
				+ id + " = false;");
		sb.append("$(document).ready(function(){if(hadLoadTree_" + id
				+ "){return;}");
		sb.append("  $('#");
		sb.append(id);
		sb.append("').dynatree({");
		sb.append("    key:'" + rootid + "',");
		sb.append("    keyborad:true,");
		sb.append("    selectMode:" + selectmode + ",");
		if (children != null) {
			sb.append("    children: " + children + ",");
		}
		if (checkbox == false) {
			sb.append("    checkbox:false,");
		} else {
			sb.append("    checkbox:true,");
		}
		if (imagePath != null) {
			sb.append("    imagePath:'" + imagePath + "',");
		}
		if (url != null) {
			sb.append("    initAjax: TreeTag.createDyNodes('" + url
					+ "','" + rootid + "'");
			sb.append("),");
			//绑定异步加载开始
			if (onlazyRead == null) {
				sb.append("    onLazyRead: function(dtnode){");
				sb.append("      dtnode.appendAjax(TreeTag.createDyNodes('"
						+ url + "',dtnode.data.id,dtnode.data");
				sb.append("));");
				sb.append("    },");
			} else {
				sb.append("    onLazyRead: function(dtnode){");
				sb.append("      dtnode.appendAjax("+ onlazyRead +"('"
						+ url + "',dtnode");
				sb.append("));");
				sb.append("    },");
			}

			//绑定异步加载结束
		}

		//拦截onclick事件开始
		if (onclick != null) {
			sb.append("    onClick: function(node,event){");
			sb.append("      " + onclick + "(node,event);");
			sb.append("    },");
		}
		//拦截onclick事件结束

		//拦截onActive事件开始
		if (onactivate != null) {
			sb.append("    onActivate: function(node){");
			sb.append("      " + onactivate + "(node);");
			sb.append("    },");
		}
		//拦截onActive事件结束

		//拦截onDeactive事件开始
		if (ondeactivate != null) {
			sb.append("    onDeactivate: function(node){");
			sb.append("      " + ondeactivate + "(node);");
			sb.append("    },");
		}
		//拦截onDeactive事件结束

		if (onselect != null) {
			sb.append("    onSelect: function(select,dtnode){");
			sb.append("      " + onselect + "(select,dtnode);");
			sb.append("    },");
		}

		//拦截onexpand事件开始(加载树的结构时会触发该事件)
		if (onexpand != null) {
			sb.append("    onExpand: function(select,node){");
			sb.append("      " + onexpand + "(select,node);");
			sb.append("    },");
		}
		//拦截onexpand事件结束

		sb.append("    cookieId: 'ui-dynatree-" + id + "',");
		sb.append("    cookieId: 'ui-dynatree-" + id + "-',");

		//添加classNames参数开始
		sb.append("  classNames:{");
		if (checkboxclass != null) {
			if ("checkbox".equals(checkboxclass)) {
				sb.append("checkbox:'").append("dynatree-checkbox")
						.append("'");
			} else if ("radio".equals(checkboxclass)) {
				sb.append("checkbox:'").append("dynatree-radio")
						.append("'");
			} else {
			}

		}
		sb.append("}");
		//添加classNames参数结束
		sb.append("  });");
		if (onready != null)
			sb.append("  " + onready + "($('#" + id
					+ "').dynatree('getTree'));");
		//jquery很多地方使用其api方法append或appendTo来处理dom节点，如果处理的dom节点里面包含该脚本，会重复执行，因此设置hadLoadTree_+id标志位判定页面是否已经加载该true，例如dialog API
		sb.append("  hadLoadTree_" + id + " = true;});");
		sb.append("</script>");
		out.print(sb.toString());
	} catch (Exception ex) {
		out.print("");
	}
%>