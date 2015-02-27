<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>高速行后台管理</title>
		<link rel="shortcut icon" href="favicon.ico" />
		<%-- jquery核心库 --%>
		<script type="text/javascript"
			src="${ctx}/frame/js/jquery/jquery-1.7.min.js"></script>
		<script type="text/javascript">
$(function() {
	if (window.top !== window.self){
		window.top.location = window.self.location;
	} 
	CheckForm = function()
	{
	   var username = document.getElementById('loginForm').j_username.value;
	   var password = document.getElementById('loginForm').j_password.value;
	//   var validatecode = document.getElementById('loginForm').validateCode.value;
	   if(username==""||password==""){
	   	  document.getElementById("errorTip").innerHTML ='系统提示：请输入用户名密码';	
	   	  return false;
	   }
	  
	   
	   /**
	   if(validatecode==""){
	   	  document.getElementById("errorTip").innerHTML ='系统提示：请输入验证码';	
	   	  return false;
	   }
	   */
	   
	   return true;
	} 
	
	//根据url的参数提示。
	var errorMsg = "${param.login_error}";
	if(errorMsg=='1'){
		document.getElementById("errorTip").innerHTML ='登录失败：用户名或密码错误';	
	}
	if(errorMsg=='2'){
		document.getElementById("errorTip").innerHTML ='登录失败：验证码错误,请重新输入';		
	}
	if(errorMsg=='3'){
		document.getElementById("errorTip").innerHTML ='登录失败：用户不存在';		
	} 
	if(errorMsg=='4'){
		document.getElementById("errorTip").innerHTML ='登录失败：';		
	} 
	if(errorMsg=='5'){
		document.getElementById("errorTip").innerHTML ='登录失败：在线用户数超出限制';		
	} 
	if(errorMsg=='6'){
		document.getElementById("errorTip").innerHTML ='登录失败：账户已被限制登录';		
	} 
	if(errorMsg=='7'){
		document.getElementById("errorTip").innerHTML ='登录失败：账户已经被禁用';		
	} 
	if(errorMsg=='8'){
		document.getElementById("errorTip").innerHTML ='登录失败：';		
	} 
	if(errorMsg=='9'){
		document.getElementById("errorTip").innerHTML ='系统提示：你的会话超时了,请重新登录';		
	} 
	if(errorMsg=='10'){
		document.getElementById("errorTip").innerHTML ='警告：正在使用未授权或授权已过期的系统';		
	}
	if(errorMsg=='11'){
		document.getElementById("errorTip").innerHTML ='您是普通会员，无法登录后台管理';		
	} 
	if(errorMsg=='12'){
		document.getElementById("errorTip").innerHTML ='您的账号暂时未启用，请联系管理员';		
	} 
});
</script>
<style type="text/css">
html {
}
body {
    color: #666666;
    font: 12px tahoma,Arial,Verdana,sans-serif;
}

body {
    background-color: #ffffff;
    color: #666666;
    font: 12px tahoma,Arial,Verdana,sans-serif;
    min-width: 1000px;
}
div.login {
    background: url("login.png") no-repeat scroll 0 0;
    height: 302px;
    margin: 60px auto 0;
    overflow: hidden;
    padding: 110px 160px 0;
    width: 520px;
}
html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, dd, dl, dt, li, ol, ul, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td {
    border: 0 none;
    margin: 0;
    outline: 0 none;
    padding: 0;
}
table {
    border-collapse: collapse;
    border-spacing: 0;
}
table tr{
	margin-top:5px;
}
input.text {
    border-color: #00b8cd;
    border-radius: 1px;
    border-style: solid;
    border-width: 1px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1) inset;
    color: #666666;
    height: 24px;
    line-height: 24px;
    padding: 0 4px;
    width: 180px;
}
input {
    outline: medium none;
    vertical-align: middle;
}
div.login th {
    font-weight: normal;
    height: 30px;
    padding-right: 6px;
    text-align: right;
    color:#7c807c;
}
div.login .loginButton {
    background: url("login.png") no-repeat scroll 0px -420px;
    border: medium none;
    color: white;
    cursor: pointer;
    height: 30px;
    font-family:微软雅黑;
    font-size:15px;
    line-height: 30px;
    margin-top:10px;
    outline: medium none;
    width: 190px;
}
div.login .powered {
    color: #cac7c9;
    font-size: 11px;
    font-family:微软雅黑;
    height: 30px;
    line-height: 30px;
    text-align: center;
    margin-top:25px;
}
div.login .error_msg {
    color: RGB(223,27,34);
    font-size: 12px;
    height: 30px;
    line-height: 30px;
    padding-right: 110px;
    text-align: right;
}
a {
    color: #666666;
    text-decoration: none;
}
#isRememberUsername{
	border:1px solid #00b8cd;
	border-color:#00b8cd;
	width:11px;height:11px;
	overflow:hidden;
}
</style>
	</head>
	<body
		onload="javascript:document.getElementById('loginForm').j_username.focus();">
		<div class="login">
			<form method="post" action="${ctx}/j_spring_security_check" id="loginForm">
				<table>
					<tbody><tr>
						<td width="190" valign="bottom" align="center" rowspan="2">
						</td>
						<th>
							账号:
						</th>
						<td>
							<input type="text" maxlength="20" class="text" name="j_username" id="j_username">
						</td>
					</tr>
					<tr>
						<th>
							密&nbsp;码:
						</th>
						<td>
							<input type="password"  maxlength="20" class="text" name="j_password" id="j_password">
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
						<th>
							&nbsp;
						</th>
						<td>
							<label>
								<div id="isRememberUsername" style="float:left;">
									<input type="checkbox" value="true" style="margin: -9px -1px -1px;">
								</div>
								<div style="float:left;margin-top:-3px;margin-left:5px;">记住账号</div>
							</label>
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
						<th>
							&nbsp;
						</th>
						<td>
							<input type="submit" value="登录" class="loginButton">
						</td>
					</tr>
				</tbody></table>
				<div class="error_msg">
					<span id="errorTip"></span>
				</div>
				<div class="powered">COPYRIGHT &copy; 2014-2015 利通科技  ALL RIGHTS RESERVED.</div>
			</form>
		</div>
	</body>
</html>