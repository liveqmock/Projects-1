<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../common/includeAll.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" " http://www.w3.org/tr/xhtml1/Dtd/xhtml1-strict.dtd">
<nui:html>
<nui:head title="查看日志信息">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>查看日志信息</title>
</nui:head>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	margin: 0;
	padding: 0;
}

td {
	font: 12px Arial, Helvetica, sans-serif;
}

p {
	text-indent: 2em;
}

a:link {
	text-decoration: none;
}

a.grey {
	color: #444444;
	font-size: 12px;
	text-decoration: none;
}

a.grey_2 {
	color: #444444;
	font-size: 12px;
	line-height: 2em;
	text-decoration: none;
	text-indent: 2em;
}

a.grey:hover {
	color: #F9A200;
	text-decoration: none;
}

a.grey2:hover {
	color: #F9A200;
	text-decoration: none;
}

a.grey_more {
	color: #717172;
	font-size: 10px;
	text-decoration: none;
}

a.grey_more:hover {
	text-decoration: none;
}

a.black {
	color: #000000;
	font-size: 12px;
	text-decoration: none;
}

a.black:hover {
	color: #F9A200;
	text-decoration: none;
}

.title {
	color: #000000;
	font-size: 14px;
	font-weight: bold;
}

.font_info {
	color: #666666;
	font-size: 14px;
	line-height: 200%;
	margin: 0;
	padding: 0 12px;
}

.bigfont {
	color: #0076AE;
	font-size: 15px;
	font-weight: bold;
}

.bg_btn {
	background-repeat: no-repeat;
	border-style: none;
	height: 23px;
	margin: 0;
	padding: 0;
	width: 40px;
}
</style>
<body>
	<table width="1002" cellspacing="0" cellpadding="0" border="0"
		align="center" style="margin: 0 auto;">
		<tbody>
			<tr>
				<td>
					<img src="${ctx}/frame/theme/default/images/theme_w.jpg" height="80" width="100%"/>
				</td>
			</tr>
		</tbody>
	</table>
	<table width="1002" border="0" align="center" cellpadding="0"
		cellspacing="0" style="margin: 0 auto;">
		<tr>
			<td colspan="2" valign="top">
				<table border="0" cellSpacing="0" cellPadding="0" width="930"
					height="83">
					<tbody>
						<tr>
							<td vAlign="top" width="50">
								&nbsp;
							</td>
							<td width="889">
								<table border="0" cellSpacing="0" cellPadding="0" width="97%"
									align="center">
									<tbody>
										<tr>
											<td align="center" colspan="2">
												<h3>日志详细信息</h3>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<hr />
											</td>
										</tr>
										<tr>
											<td align="center"></td>
										</tr>
										<tr>
											<td></td>
										</tr>
										<tr>
											<td align="center">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td colspan="2" height="3"
												align="center"></td>
										</tr>
										<tr>
											<td class="font_info">
												<h5>用户名：</h5>
												<c:if test="${!empty operateLog}">${log.userName}</c:if>
											</td>
										</tr>
										<tr>
											<td class="font_info">
												<h5>日期：</h5>${log.logDate}
											</td>
										</tr>
										<tr>
											<td class="font_info">
												<h5>模块：</h5>${log.operateModule}
											</td>
										</tr>
										<tr>
											<td colspan="2" height="3"
												align="center"></td>
										</tr>
										<tr>
											<td colspan="2" class="font_info">
											<h5>详细信息：</h5>${log.logDes}
											</td>
										</tr>
										<tr>
											<td colspan="2" height="3"
												align="center">:</td>
										</tr>
										<tr>
											<td colspan="2" align="center">
												<INPUT class="bg_btn" style="cursor: pointer"
													onclick="window.close();" name="Submit2" value="关闭"
													type="button" />
											</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td width="48">
								&nbsp;
							</td>
						</tr>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<td height="20" colspan="1" valign="top"
				class="bg_pic">
				&nbsp;
			</td>
		</tr>
	</table>
</body>
</nui:html>

