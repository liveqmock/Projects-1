<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="attachId" type="java.lang.String" required="true"%>
<%@ attribute name="mediaName" type="java.lang.String" required="true"%>
<%@ attribute name="mediaid" type="java.lang.String" required="true"%>

<script>
	$(function() {
		$("#${id}").bind($.jPlayer.event.play, function() {
			$(this).jPlayer("pauseOthers"); // pause all players except this one.
			});
		$("#${id}")
				.jPlayer(
						{
							ready : function() {
								$(this)
										.jPlayer(
												"setMedia",
												{
													title : '${mediaName}',
													mp3 : "${ctx}/attach/playAttach?mediaId=${mediaid}"
												});
							},
							solution : "flash,html",
							swfPath : "${ctx}/frame/js/plugin/jQuery.jPlayer.2.7.0/",
							supplied : "mp3",
							wmode : "window",
							smoothPlayBar : true,
							keyEnabled : true,
							preload : "none",
							remainingDuration : true,
							toggleDuration : true,
							cssSelectorAncestor : "#jp_container_${id}",
							play: function() {
								//add the play his
								$.ajax({
									type : "POST",
									url : "${ctx}/mediaplay/saveMediaPlay",
									dataType : "json",
									async : true, //
									data : {
										mediaid : "${mediaid}",
										medianame : "${mediaName}"
									},
									success : function(result) {
									},
									failure : function() {
									}
									});
								 $(this).jPlayer("pauseOthers", 0); // stop all players except this one.
							}
						});
	});
</script>
<div id="${id}" style="width: 10px; height: 10px; font-size: 1.25em;"></div>
<div id="jp_container_${id}" class="playcontainer">
	<div class="jp-type-single">
		<div class="jp-gui jp-interface">
			<ul class="jp-controls">
				<li><a href="javascript:;" class="jp-play" tabindex="1">play</a></li>
				<li><a style="display:none;" href="javascript:;" class="jp-pause" tabindex="1">pause</a></li>
			</ul>
		</div>
	</div>
</div>
