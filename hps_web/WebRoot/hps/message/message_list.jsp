<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/message/message_list.js"></script>
<nui:grid id="messageListGrid" title="消息列表" offsetHeight="-15"
	url="${ctx}/message/load_list" pageId="messageListPageId"
	pkName="id"
	delOperator="Message_Mgr" 
	delClick="MessageManager.deleteMessage"
	otherButtons="">
	<nui:gridCell name="title" title="标题"  align="center"></nui:gridCell>
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="memberid" title="memberid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="membername" title="接收者"  align="center"></nui:gridCell>
  	<nui:gridCell name="messagetype" title="类型"  align="center"></nui:gridCell>
  	<nui:gridCell name="content" title="内容"  align="center"></nui:gridCell>
  	<nui:gridCell name="fromid" title="fromid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="fromname" title="发送者"  align="center"></nui:gridCell>
  	<nui:gridCell name="cratetime" title="发送时间"  align="center" formatter="MessageManager.formatCreateTime"></nui:gridCell>
  	<nui:gridCell name="viewtime" title="阅读时间"  align="center" hidden="true"></nui:gridCell>
 </nui:grid>