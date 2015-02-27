<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head plugin="validate,upload" title="编辑广告图片">
<script type="text/javascript" src="${ctx}/record/adphoto/adphoto_form.js"></script>
<script type="text/javascript">
	var attach_loaded = false;
</script>
</nui:head>
<nui:body>
	<input type="hidden" id="id" name="id" value="${adphoto.id}"/>
	<nui:validate formId="adphotoForm" onclick="adphotoFormSubmit"
		callback="adphotoMgr.submitForm"></nui:validate>
	<nui:panel style="width:800px;margin:20px auto;" title="编辑广告图片">
		<form id="adphotoForm">
            <nui:field type="input">
				<nui:input title="标题：" size="x3" require="true">
					<input type="text" id="title" name="title"
						value="${adphoto.title}" required="true" maxlength="50"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="类型：" size="x3" require="true">
					<select id="adtype" name="adtype">
						<option value="1" <c:if test="${adphoto.adtype==1}"> selected </c:if>>顶部广告</option>
						<option value="2"<c:if test="${adphoto.adtype==2}"> selected </c:if>>左侧广告</option>
						<option value="3" <c:if test="${adphoto.adtype==3}"> selected </c:if>>活动广告</option>
					</select>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="链接地址：" size="x3" require="true">
					<input type="text" id="url" name="url"
						value="${adphoto.url}" required="true" maxlength="5000"/>
				</nui:input>
            </nui:field>	
            <nui:field type="input">
				<nui:input title="描述信息：" size="x3">
					<input type="text" id="addesc" name="addesc"
						value="${adphoto.addesc}" maxlength="50"/>
				</nui:input>
            </nui:field>		
           	<c:choose>
				<c:when test="${operation=='view'}">
					<c:if test="${empty attachList}">
						<nui:field type="input">
							<nui:input title="图片列表：" size="x3">
								<fieldset>
									无图片！
								</fieldset>
							</nui:input>
						</nui:field>
					</c:if>
					<c:forEach items="${attachList}" var="attach">
						<tr>
							<td width="30" align="center">
								<img src="${ctx}/frame/js/plugin/uploadify-v3.0.0/attach.gif" />
							</td>
							<td height="23">
								<a
									href="${ctx}/attach/dowmloadAttach?attachId=${attach.attachId}"
									target="_blank"><span>${attach.fileName}( <fmt:formatNumber
											value="${attach.fileSize/1024}" pattern="#,###.#" />K)</span> </a>
						</tr>
						<tr>
							<td></td>
							<td height="5"></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<nui:field type="input">
						<nui:input title="图片：" size="x3">
							<fieldset>
								<nui:attach id="topicAttach" fileSizeLimit="4MB" totalFileSizeLimit="2097152"
									uploadUrl="${ctx}/attach/uploadAttachToDBWithCut"
									fileTypeExts="*.jpg;*.bmp;*.jpeg;*.png;*.gif;*.JPG;*.BMP;*.JPEG;*.PNG;*.GIF" fileCount="1"></nui:attach>
							</fieldset>
						</nui:input>
					</nui:field>
				</c:otherwise>
			</c:choose>
			<nui:field type="button"
				style="margin-left: auto;margin-right: auto;margin-top:20px;width:150px;text-align:center">		
				<input type="hidden" id="operation" name="operation" value="${operation}" />
				<nui:button title="保存" onclick="adphotoFormSubmit()" id="btnCommit"></nui:button>
				<nui:button title="关闭" type="button" id="btnCancel"
					onclick="window.parent.changePage();"></nui:button>
			</nui:field>
		</form>
	</nui:panel>
</nui:body>
</nui:html>
