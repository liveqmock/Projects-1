<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="id" type="java.lang.String" required="false"%>
<%@ attribute name="checkExisting" type="java.lang.String"
	required="false"%>
<%@ attribute name="auto" type="java.lang.String" required="false"%>
<%@ attribute name="multi" type="java.lang.String" required="false"%>
<%@ attribute name="buttonText" type="java.lang.String" required="false"%>
<%@ attribute name="fileTypeDesc" type="java.lang.String"
	required="false"%>
<%@ attribute name="fileTypeExts" type="java.lang.String"
	required="false"%>
<%@ attribute name="postData" type="java.lang.String" required="false"%>
<%@ attribute name="simUploadLimit" type="java.lang.String"
	required="false"%>
<%@ attribute name="uploadType" type="java.lang.String" required="false"%>
<%@ attribute name="fileSizeLimit" type="java.lang.String"
	required="false"%>
<%@ attribute name="totalFileSizeLimit" type="java.lang.String"
	required="false"%>
<%@ attribute name="onUploadSuccess" type="java.lang.String"
	required="false"%>
<%@ attribute name="onSelect" type="java.lang.String" required="false"%>
<%@ attribute name="onQueueComplete" type="java.lang.String"
	required="false"%>
<%@ attribute name="otherOptions" type="java.lang.String"
	required="false"%>
<%@ attribute name="fileCount" type="java.lang.String" required="false"%>
<%@ attribute name="uploadUrl" type="java.lang.String" required="false"%>
<%@ attribute name="downLoadUrl" type="java.lang.String" required="false"%>
<%@ attribute name="uploadRevoke" type="java.lang.String" required="false"%>
<c:if test="${empty id}">
	<c:set var="id" value="ns_fileUploadDivId" />
</c:if>

<c:if test="${empty buttonText}">
	<c:set var="buttonText" value="选择附件" />
</c:if>

<c:if test="${empty fileTypeExts}">
	<c:set var="fileTypeExts" value="*.*" />
</c:if>
<c:if test="${empty downLoadUrl}">
<c:set var="downLoadUrl" value="${ctx}/attach/dowmloadAttach" />
</c:if>
<c:if test="${empty auto}">
	<c:set var="auto" value="true" />
</c:if>
<c:if test="${empty multi}">
	<c:set var="multi" value="true" />
</c:if>
<c:if test="${empty simUploadLimit}">
	<c:set var="simUploadLimit" value="999" />
</c:if>
<c:if test="${empty fileSizeLimit}">
	<c:set var="fileSizeLimit" value="52428800" />
</c:if>
<c:if test="${empty totalFileSizeLimit}">
	<c:set var="totalFileSizeLimit" value="52428800" />
</c:if>

<c:if test="${empty fileCount}">
	<c:set var="fileCount" value="no" />
</c:if>

<c:choose>
    <c:when test="${!empty uploadUrl}">
    	<c:set var="uploadUrl" value="${uploadUrl}" />
	</c:when>
	<c:when test="${uploadType == 'dir'}">
		<c:set var="uploadUrl" value="${ctx}/attach/uploadAttachToDir" />
	</c:when>
	<c:when test="${uploadType == 'hdfs'}">
		<c:set var="uploadUrl" value="${ctx}/attach/uploadAttachToHDFS" />
	</c:when>
	<c:when test="${uploadType == 'pshdfs'}">
		<c:set var="uploadUrl" value="${ctx}/attach/uploadPictureAttachToHDFS" />
	</c:when>
	<c:when test="${uploadType == 'db'}">
		<c:set var="uploadUrl" value="${ctx}/attach/uploadAttachToDB" />
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>

<script type="text/css">
#queue_id{
	background-color:#FFF;
	border-radius:3px;
	height:20px;
	width:200px;
}
</script>
<script>  
	var imgageIds = "";
	var fileCount = 0;
	var totalFileSize=0;
 	$(function(){ 
 		fileCount=parseInt($("#fileListSize").val());
 	 	//if(attach_loaded)
 		//{
 		//	return;
 		//}
 	    haveUploaded_${id} = true; 
	     attachObject = $("#uploadify_${id}").uploadify({
	    	   'langFile':'${ctx}/frame/js/plugin/uploadify-v3.0.0/uploadifyLang_cn.js',
	    	   'formData': '{"jsessionid": "${pageContext.session.id}"}',
	    	   'removeCompleted': false, 
	    	   'uploader': '${uploadUrl};jsessionid=${pageContext.session.id}',
		       'method':'post',
		       'swf': '${ctx}/frame/js/plugin/uploadify-v3.1/uploadify.swf',
		       'buttonCursor' : 'pointer',
		       'buttonText':'${buttonText}',
		       'fileSizeLimit' : '${fileSizeLimit}',
		       'fileTypeDesc' : '${fileTypeDesc}',
		       'fileTypeExts':'${fileTypeExts}',
		       'cancelImage':'${ctx}/frame/js/plugin/uploadify-v3.1/uploadify-cancel.png',
		       'method': 'post',
		       'successTimeout':'99999',
		       'onUploadSuccess' : function(file,data,response) {
		    	<c:if test="${!empty uploadRevoke}">
	       			${uploadRevoke}(file);
	       		</c:if>
		       if(data=="" || data=="undefined" || data==null) {
		       	//上传失败		
		       	alert("文件：" +file.name + "上传失败");
		       	$("#uploadify_${id}").uploadify('cancel',file.id);
		       } else {
		       //上传成功
		       		  fileCount = fileCount + 1;
		       			totalFileSize = totalFileSize + file.size;
               		  $("#"+file.id).append("<input type='hidden' value='"+file.name+"' name='successUploadFileName_${id}'/>");
              		 $("#"+file.id).append("<input type='hidden' value='"+data+"' name='successUploadFileId_${id}'/>");
              		 if(totalFileSize>"${totalFileSizeLimit}") {
              		 	fileCount = fileCount - 1;
              		 	totalFileSize = totalFileSize - file.size;
               			alert("上传附件总大小超过限制" + parseInt("${totalFileSizeLimit}")/1024/1024 + "MB");
               			$("#" + file.id).nextAll().each(function(index){
               				handleCancelId($(this).attr("id"),null,false);
               				$("#uploadify_${id}").uploadify('cancel',$(this).attr("id"));
               			});
               			
               			$("#uploadify_${id}").uploadify('stop',file);
               			$("#uploadify_${id}").uploadify('cancel',file.id);
               			handleCancelId(file.id,file.size,false);
               			var deletedId = $("#" + file.id).find("input[name='successUploadFileId_${id}']");
						$("#deletedIds_${id}").val($("#deletedIds_${id}").val()+deletedId.val());
               			return;
               		}
		       }
		       
               },
               'onQueueComplete':function(data){
	                var $filesId = $("input[name='successUploadFileId_${id}']");
	                var $filesName = $("input[name='successUploadFileName_${id}']");
	             	var ids = "";
	             	var names = "";
	             	for(var i=0;i<$filesId.length;i++){
	             		ids+=$filesId[i].value;
	             		//var fileName = $($filesId[i]).parent().find(".fileName").text();
	             		//names= names + fileName.substring(0,fileName.lastIndexOf("(")) + ",";
	             	}
	             	for(var i=0;i<$filesName.length;i++){
	             		names= names + $filesName[i].value+ ",";
	             	}
	             	$("#fileIds_${id}").val(ids);
	             	$("#fileNames_${id}").val(names);
               		haveUploaded_${id}= true;
               },
               onUploadStart: function(file) {//上传开始时触发（每个文件触发一次）
               haveUploaded_${id}= false; 
               	if("no" != "${fileCount}") {
               		if(parseInt("${fileCount}")<=fileCount) {
               			alert("上传附件数超限制，最多上传" + "${fileCount}" + "个附件");
               			$("#" + file.id).nextAll().each(function(index){
               				handleCancelId($(this).attr("id"),null,false);
               				$("#uploadify_${id}").uploadify('cancel',$(this).attr("id"));
               			});
               			//取消上传
               			$("#uploadify_${id}").uploadify('stop',file);
               			$("#uploadify_${id}").uploadify('cancel',file.id);

               			handleCancelId(file.id,file.size,false);
               			return;
               		}
               	}
			   }
               //${otherOptions}
 		});     
 		//attach_loaded= true;
 		  		  //处理删除已有附件的按钮
  		  $("#${id} table tr td span.ui-icon-close").bind("click",function(item){ 
  		  	fileCount = fileCount - 1;
  		  	 var $delItem = $(this);
  		  	totalFileSize = totalFileSize - parseInt($delItem.attr("size"));
  		     $("#deletedIds_${id}").val($("#deletedIds_${id}").val()+$delItem.attr("lang"));
  		     //先删除tr下面的一个空行
  		     $delItem.parent().parent().next().remove();
  		     //删除tr
  		     $delItem.parent().parent().remove();
  		  });  
        }); 
        
        function handleCancelIduploadify_mediaAttach(swfFileId,swfFileSize,flag) {
        	handleCancelId(swfFileId,swfFileSize,flag);
        }
        
        function handleCancelId(swfFileId,swfFileSize,flag) {
        	if(swfFileId && flag==undefined && $('#' + swfFileId).find('.data').html() == ' - 完成') {
	        	fileCount = fileCount - 1;
	        	totalFileSize = totalFileSize - swfFileSize;
				var deletedId = $("#" + swfFileId).find("input[name='successUploadFileId_${id}']");
				$("#deletedIds_${id}").val($("#deletedIds_${id}").val()+deletedId.val());
        	}

        } 
</script>
<div id="${id}" style="${style}">
	<table width="100%" cellspacing="0" cellpadding="0" border="0"
		style="padding: 0 5px" align="center">
		<input type="hidden" id="fileListSize"
			value="${fn:length(attachList)}"></input>
		<c:forEach items="${attachList}" var="attach">
			<script type="text/javascript">
			totalFileSize = totalFileSize + parseInt("${attach.fileSize}");
		</script>
			<c:if
				test="${attach.contentType == 'image/pjpeg' || attach.contentType=='image/bmp' }">
				<script type="text/javascript">
			imgageIds = imgageIds + "${attach.attachId}" + ",";
		</script>
			</c:if>
			<tr>
				<td bgcolor="#fcfcfc" width="30" align="center">
					<img src="${ctx}/frame/js/plugin/uploadify-v3.0.0/attach.gif" />
				</td>
				<td bgcolor="#fcfcfc" height="23">
					<a style="float: left; margin: 0 10px 0 0" target="_blank"
						href="${downLoadUrl}?attachId=${attach.attachId}"><span>${attach.fileName}
							(<fmt:formatNumber value="${attach.fileSize/1024}"
								pattern="#,###.#" />K)</span> </a>
					<span lang="${attach.attachId}" size="${attach.fileSize}"
						class="ui-icon ui-icon-close" title="删除"
						onmouseout="$(this).removeClass('ui-icon-closethick').addClass('ui-icon-close')"
						onmouseover="$(this).removeClass('ui-icon-close').addClass('ui-icon-closethick').css('cursor','pointer')"></span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td height="5"></td>
			</tr>
		</c:forEach>
	</table>
	<input type="file" id="uploadify_${id}" />
	<input type="hidden" name="fileIds_${id}" id="fileIds_${id}" value="" />
	<input type="hidden" name="fileNames" id="fileNames_${id}" value="" />
	<input type="hidden" name="deletedIds_${id}" id="deletedIds_${id}" value="" />
</div>
