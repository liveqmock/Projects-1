<%@ page language="java" pageEncoding="UTF-8"%>
#set ($context="${ctx}")
<script type="text/javascript"
	src="$context/record/${params.moduleName}/${params.moduleName}_list.js"></script>
<nui:grid id="${params.moduleName}ListGrid" title="${params.moduleName}列表" height="400"
	url="$context/${params.moduleName}/load_list" pageId="${params.moduleName}ListPageId"
	pkName="${params.moduleName}Id" addCheck="${classNamePrefix}Manager.addCheck" addClick="${classNamePrefix}Manager.add${classNamePrefix}" 
	addOperator="Frame_${classNamePrefix}_Add" editOperator="Frame_${classNamePrefix}_Edit"
	delOperator="Frame_${classNamePrefix}_Delete" 
	editClick="${classNamePrefix}Manager.edit${classNamePrefix}" 
	delClick="${classNamePrefix}Manager.delete${classNamePrefix}"
	otherButtons="">
#set ($count=0)	
#foreach ($fld in $params.fieldList)
 #if(${fld.fieldName} != (${params.moduleName}+"Id") && ($count==0))
   <nui:gridCell name="${fld.fieldName}" title="${fld.fieldName}" align="center"
		formatter="${classNamePrefix}Manager.formatViewLink"></nui:gridCell>
    #set ($count=$count+1)	
 #else		
	<nui:gridCell name="${fld.fieldName}" title="${fld.fieldName}"  align="center"></nui:gridCell>
 #end	
#end	
</nui:grid>