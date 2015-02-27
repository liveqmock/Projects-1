<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	request.setAttribute("id", id);
%>
<nui:html>
<nui:head title="试听音乐">
	<script type="text/javascript" src="${ctx}/record/media/media_form.js"></script>
	<link rel="stylesheet" type="text/css"
		href="${ctx}/frame/js/plugin/jQuery.jPlayer.2.7.0/blue.monday/jplayer.blue.monday.css" />
	<script type="text/javascript"
		src="${ctx}/frame/js/plugin/jQuery.jPlayer.2.7.0/jquery.jplayer.min.js"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$("#playTest")
									.jPlayer(
											{
												ready : function() {
													$(this)
															.jPlayer(
																	"setMedia",
																	{
																		title : "试听音乐",
																		mp3 : "${ctx}/attach/playAttach?mediaId=${id}"
																	}).jPlayer("play");
												},
												solution : "flash,html",
												swfPath : "${ctx}/frame/js/plugin/jQuery.jPlayer.2.7.0/",
												supplied : "mp3",
												wmode : "window",
												smoothPlayBar : true,
												keyEnabled : true,
												preload : "auto",
												remainingDuration : true,
												toggleDuration : true,
												cssSelectorAncestor:"#jp_container_1"
											});
						});
	</script>
</nui:head>
<nui:body>
	<div id="playTest"
		style="width: 10px; height: 10px; font-size: 1.25em;"></div>
	<div id="jp_container_1">
		<div class="jp-type-single">
			<div class="jp-gui jp-interface">
				<ul class="jp-controls">
					<li><a href="javascript:;" class="jp-play" tabindex="1">play</a></li>
					<li><a href="javascript:;" class="jp-pause" tabindex="1">pause</a></li>
				</ul>
			</div>
		</div>
	</div>
</nui:body>
</nui:html>
