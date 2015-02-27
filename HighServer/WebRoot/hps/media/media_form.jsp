<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate,upload" title="添加音乐">
	<script type="text/javascript"
		src="${ctx}/frame/js/plugin/jquery-ui-1.8.16.custom/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="${ctx}/record/media/media_form.js"></script>

	<script type="text/javascript">
		var attach_loaded = false;
		$(document).ready(function() {
			var availableTags =${allUser};
			$("#membername").autocomplete({
				source : availableTags
			});
		});
	</script>
</nui:head>
<nui:body>
	<input type="hidden" id="id" name="id" value="${media.id}" />
	<input type="hidden" id="memberid" name="memberid"
		value="${media.memberid}" />
<input type="hidden" id="paneltitle" value="${title}" /> 
	<nui:validate formId="mediaForm" onclick="mediaFormSubmit"
		callback="mediaMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="音乐管理">
		<form id="mediaForm">
			<nui:field type="input">
				<nui:input title="歌曲名称：" size="x3" require="true">
					<input type="text" id="title" name="title" value="${media.title}"
						required="true" maxlength="50" />
				</nui:input>
			</nui:field>
			<c:choose>
				<c:when test="${operation=='view'}">
					<c:if test="${empty attachList}">
						<nui:field type="input">
							<nui:input title="附件列表：" size="x3">
								<fieldset>无附件！</fieldset>
							</nui:input>
						</nui:field>
					</c:if>
					<c:forEach items="${attachList}" var="attach">
						<tr>
							<td width="30" align="center"><img
								src="${ctx}/frame/js/plugin/uploadify-v3.0.0/attach.gif" /></td>
							<td height="23"><a
								href="${ctx}/attach/dowmloadAttach?attachId=${attach.attachId}"
								target="_blank"><span>${attach.fileName}( <fmt:formatNumber
											value="${attach.fileSize/1024}" pattern="#,###.#" />K)
								</span> </a>
						</tr>
						<tr>
							<td></td>
							<td height="5"></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<nui:field type="input">
						<nui:input title="附件：" size="x3">
							<fieldset>
								<nui:attach id="mediaAttach" uploadUrl="${ctx}/attach/uploadMediaAttachToDir"
									fileTypeExts="*.mp3;*.wav;*.wma;*.ape" fileCount="1"  uploadRevoke="mediaMgr.uploadRevoke"></nui:attach>
							</fieldset>
						</nui:input>
					</nui:field>
				</c:otherwise>
			</c:choose>
			<nui:field type="input">
				<nui:input title="录音室：" size="x3" require="true">
					<select name="roomid" id="roomid">
						<option value="">请选择--</option>
						<nui:option url="${ctx}/recordroom/roomlist"
							value="${media.roomid}" keyname="id" valuename="name">
						</nui:option>
					</select>
					<input type="hidden" id="roomname" name="roomname"
						value="${media.roomname}" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="所属会员：" size="x3" require="true">
					<input type="text" id="membername" name="membername"
						value="${media.membername}" required="true" maxlength="50" />
				</nui:input>
			</nui:field>
			<nui:field type="input">
				<nui:input title="得分：" size="x3" require="true">
					<input type="text" id="score" name="score"
						value="${media.score}" required="true" maxlength="50" integer="true"/>
				</nui:input>
			</nui:field>
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">
				<input type="hidden" id="operation" name="operation"
					value="${operation}" />
				<nui:button title="保存" onclick="mediaFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.parent.changePage();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
