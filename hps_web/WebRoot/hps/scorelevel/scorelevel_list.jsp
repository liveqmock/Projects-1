<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/hps/scorelevel/scorelevel_list.js"></script>
<nui:grid id="medialevelListGrid" title="积分等级列表" offsetHeight="-15"
	url="${ctx}/scorelevel/load_list" pageId="medialevelListPageId"
	pkName="levelkey" editOperator="Level_Mgr"
	editClick="MedialevelManager.editMedialevel" 
	otherButtons="">
    <nui:gridCell name="levelkey" title="积分项" align="center"></nui:gridCell>
    <nui:gridCell name="score" title="点数"  align="center"></nui:gridCell>
  	<nui:gridCell name="memo" title="备注"  align="center"></nui:gridCell>
 </nui:grid>