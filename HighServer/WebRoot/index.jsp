<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
<nui:head title="高速行后台管理系统" plugin="menu,grid">
	<script type="text/javascript" src="${ctx}/index.js"></script>
</nui:head>
<nui:body>
	<div id="frame-north">
		<table id="frame-banner">
			<tr>
				<td style="width: 20px;"></td>
				<td width="50%">
					<div class="frame-logoText">高速行后台管理系统</div>
				</td>
				<td align="right">
					<div id="frame-baner-right">
						<div id="messageArea">
							<div
								style="height: 37px; line-height: 37px; color: white; float: left; margin-left: 15px;">${sessionUserName}</div>
						</div>
						<a href="javascript:window.location.href='${ctx}/index.jsp'"
							id="bannerRightHomeBtn" title="首页"></a> <a
							href="javascript:HomePage.refreshPage();"
							id="bannerRightRefreshBtn" title="刷新页面"></a> <a
							href="javascript:window.location.href='${ctx}/j_spring_security_logout'"
							id="bannerRightLogOutBtn" title="注销登录"></a>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<div>
		<nui:menubar id="uumMenu" openOnClick="false" menuwidth="50"
			opacity="1" hasImages="true" menuVertical="false"
			iconpath="${ctx}/frame/theme/${frameTheme}/images/index/">
			<nui:menufolder id="menu_sel_parent" title="个人资源"
				menuVertical="false" menuContext="false">
				<nui:menuitem id="menu_sel_parent" title="修改密码"
					action="HomePage.menuClick('${ctx}/sys/userMgr/userPasswordPage');"
					icon="password.png"></nui:menuitem>
			</nui:menufolder>
			<operator:authorize
				ifAnyGranted="User_Approve,Frame_User_Manager,Frame_Role_Manager,Frame_LogManager,DicMgr">
				<nui:menufolder id="menu_sel_parent1" title="系统管理"
					menuVertical="false" menuContext="false">
					<nui:menuitem id="menu_sel_parent1" title="用户管理"
						action="HomePage.menuClick('${ctx}/sys/userMgr');" icon="user.png"
						operator="Frame_User_Manager"></nui:menuitem>
					<nui:menuitem id="menu_sel_parent1" title="角色管理"
						action="HomePage.menuClick('${ctx}/sys/roleMgr');" icon="role.png"
						operator="Frame_Role_Manager"></nui:menuitem>
					<nui:menuitem id="menu_sel_parent1" title="用户审核管理"
						action="HomePage.menuClick('${ctx}/hps/userMgr');" icon="user.png"
						operator="User_Approve"></nui:menuitem>
					<nui:menuitem id="sp1" seperator="true"></nui:menuitem>
					<nui:menuitem id="menu_sel_parent1" title="字典管理"
						action="HomePage.menuClick('${ctx}/dictionaryMgr');"
						icon="dictionary.png" operator="DicMgr"></nui:menuitem>
					<nui:menuitem id="menu_sel_parent1" title="积分制度"
						action="HomePage.menuClick('${ctx}/scorelevel/main');"
						icon="medal.png" operator="Level_Mgr"></nui:menuitem>
					<nui:menuitem id="sp2" seperator="true"></nui:menuitem>
					<nui:menuitem id="menu_sel_parent1" title="日志管理"
						action="HomePage.menuClick('${ctx}/log');" icon="log.png"
						operator="Frame_LogManager"></nui:menuitem>
				</nui:menufolder>
			</operator:authorize>
			<operator:authorize ifAnyGranted="CMS_Mgr">
				<nui:menufolder id="menu_sel_parent3" title="内容管理"
					menuVertical="false" menuContext="false">
					<nui:menuitem id="menu_sel_parent3" title="文章管理"
						action="HomePage.menuClick('${ctx}/cmscontent/main');"
						icon="org.png" operator="CMS_Mgr"></nui:menuitem>
				</nui:menufolder>
			</operator:authorize>
			<operator:authorize
				ifAnyGranted="User_Exchange,User_Exchange_Handle,Score_Product,ScoreProduct_Add,ScoreProduct_Edit,ScoreProduct_Delete">
				<nui:menufolder id="menu_sel_parent4" title="积分商城"
					menuVertical="false" menuContext="false">
					<nui:menuitem id="menu_sel_parent4" title="商品管理"
						action="HomePage.menuClick('${ctx}/product/main');" icon="org.png"
						operator="Score_Product,ScoreProduct_Add,ScoreProduct_Edit,ScoreProduct_Delete"></nui:menuitem>
					<nui:menuitem id="menu_sel_parent4" title="积分兑换管理"
						action="HomePage.menuClick('${ctx}/userexchnage/main');"
						icon="org.png" operator="User_Exchange,User_Exchange_Handle"></nui:menuitem>
				</nui:menufolder>
			</operator:authorize>
		</nui:menubar>
	</div>
	<div id="iframe_div"></div>
	<div id="portal-south">
		<table>
			<tr>
				<td class="left"></td>
				<td class="center">
					<div id="rights_text">
						<span style="color: #8c959c; font-family: arial;">BEST
							VIEWED AT 1280×1024 &copy;2000-2015&nbsp;利通科技 </span>
					</div>
				</td>
				<td class="right">
					<div id="online_userCount"></div>
				</td>
			</tr>
		</table>
	</div>
</nui:body>
</nui:html>
