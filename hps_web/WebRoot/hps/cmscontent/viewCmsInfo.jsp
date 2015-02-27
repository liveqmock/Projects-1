<%@ page language="java" pageEncoding="UTF-8"%>
<nui:html>
	<nui:head title="查看信息">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${cmscontent.title}</title>
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
					<td width="36" valign="top">
						&nbsp;
					</td>
					<td>
						<img src="${ctx}/hps/cmscontent/image/title_xx.jpg" />
					</td>
					<td width="36">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="36" height="10">
						&nbsp;
					</td>
					<td></td>
					<td width="36">
						&nbsp;
					</td>
				</tr>
			</tbody>
		</table>
		<table width="1002" border="0" align="center" cellpadding="0"
			cellspacing="0" style="margin: 0 auto;">
			<tr>
				<td width="37" rowspan="3">
					&nbsp;
				</td>
				<td width="929" height="44" valign="bottom"
					background="${ctx}/hps/cmscontent/image/bg_top3.jpg"
					class="bg_pic">
				</td>
				<td width="36">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="2" valign="top"
					background="${ctx}/hps/cmscontent/image/line-1.jpg">
					<table border="0" cellSpacing="0" cellPadding="0" width="930"
						height="83">
						<tbody>
							<tr>
								<td vAlign="top" width="73">
									&nbsp;
								</td>
								<td width="889">
									<table border="0" cellSpacing="0" cellPadding="0" width="97%"
										align="center">
										<tbody>
											<tr>
												<td colspan="2" class="bigfont" height="36" width="819"
													align="center">
													${cmscontent.title}
												</td>
											</tr>
											<tr>
												<td colspan="2" class="font_info" height="31" align="center">
													<nui:date date="${cmscontent.cratetime}"></nui:date>
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
												<td colspan="2">
													<p>
														<span style="line-height: 200%"> ${cmscontent.content}</span>
													</p>
												</td>
											</tr>
											<tr>
												<td></td>
											</tr>
											<tr>
												<td height="3" align="center"></td>
											</tr>
											<tr>
												<td align="center">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td colspan="2" height="3"
													background="${ctx}/hps/cmscontent/image/dot-2.jpg"
													align="center"></td>
											</tr>
											<tr>
												<td width="50%" class="font_info" id="creator">
													${cmscontent.authorname}
												</td>
												<td width="50%" class="font_info">
													
												</td>
											</tr>
											<tr>
												<td colspan="2" height="3"
													background="${ctx}/hps/cmscontent/image/dot-2.jpg"
													align="center"></td>
											</tr>
											
											<tr>
												<td colspan="2" height="3"
													background="${ctx}/hps/cmscontent/image/dot-2.jpg"
													align="center"></td>
											</tr>
											<tr>
												<td colspan="2" align="center">
													<INPUT class="bg_btn" style="cursor:pointer" onclick="window.close();"
														name="Submit2" value="关闭" type="button" />
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
					background="${ctx}/hps/cmscontent/image/bg_bottom.jpg"
					class="bg_pic">
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</nui:html>

