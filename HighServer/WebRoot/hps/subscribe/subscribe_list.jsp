<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/record/subscribe/subscribe_list.js"></script>
<nui:grid id="subscribeListGrid" title="预约列表" offsetHeight="-10"
	url="${ctx}/subscribe/load_list" pageId="subscribeListPageId"
	pkName="id" 
	delOperator="Subscribe_Manager" 
	delClick="SubscribeManager.deleteSubscribe"
	otherButtons="{'operator':'Subscribe_Manager','name':'通过预约','onClick':'SubscribeManager.confirmSub'},{'operator':'Subscribe_Manager','name':'拒绝预约','onClick':'SubscribeManager.refuseOpen'}">
    <nui:gridCell name="id" title="id" align="center" hidden="true"></nui:gridCell>
    <nui:gridCell name="membername" title="预约会员"  align="center"></nui:gridCell>
  	<nui:gridCell name="memberid" title="memberid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="subtime" title="预约时间"  align="center" formatter="SubscribeManager.formatTime"></nui:gridCell>
  	<nui:gridCell name="recordroom" title="预约录音室"  align="center"></nui:gridCell>
  	
  	<nui:gridCell name="roomid" title="roomid"  align="center" hidden="true"></nui:gridCell>
  	<nui:gridCell name="confirm" title="状态"  align="center" formatter="SubscribeManager.formatStatus"></nui:gridCell>
  	<nui:gridCell name="comment" title="备注"  align="center"></nui:gridCell>
  	<nui:gridCell name="subMediaName" title="预约歌曲"  align="center"></nui:gridCell>
  	
  	<nui:gridCell name="confirmemeber" title="confirmemeber"  align="center" hidden="true" ></nui:gridCell>
 </nui:grid>
 <nui:validate formId='typeFormId' onclick="checkTypeForm"
	callback="SubscribeManager.refuse"></nui:validate>
<nui:dialog id="subMsgWindow" title="拒绝原因" height="260" width="400"
	saveClick="checkTypeForm">
		<form id="typeFormId">
				<textarea style="height: 130px;width:360px;" id="messageHidden" name="messageHidden"
						maxlength="255"></textarea>
				<input type="hidden" id="selectedIdHidden" value=""></input>
	</form>
</nui:dialog>