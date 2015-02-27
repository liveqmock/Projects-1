/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var dbInfo;
var filterStructsInfo = new Array();
var structsInfo = new Array();
var imagestrucInfo = new Array();
var doInsertStruc = 'no';
var nameInfo;
var succOrerro;
function loadjscssfile(filename,filetype){

    if(filetype == "js"){
        var fileref = document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src",filename);
    }else if(filetype == "css"){
    
        var fileref = document.createElement('link');
        fileref.setAttribute("rel","stylesheet");
        fileref.setAttribute("type","text/css");
        fileref.setAttribute("href",filename);
    }
   if(typeof fileref != "undefined"){
        document.getElementsByTagName("head")[0].appendChild(fileref);
    }
    
}
var app = {
	// Application Constructor
	initialize : function() {
		this.bindEvents();
	},
	// Bind Event Listeners
	//
	// Bind any events that are required on startup. Common events are:
	// 'load', 'deviceready', 'offline', and 'online'.
	bindEvents : function() {
		document.addEventListener('deviceready', this.onDeviceReady, false);
	},
	// deviceready Event Handler
	//
	// The scope of 'this' is the event. In order to call the 'receivedEvent'
	// function, we must explicity call 'app.receivedEvent(...);'
	onDeviceReady : function() {

		app.receivedEvent('deviceready');
	},
	// Update DOM on a Received Event
	receivedEvent : function(id) {

		console.log('Received Event: ' + id);
		// window.plugins.orientationLock.lock("landscape");
		// 初始化SQLite数据库
		var db = window.sqlitePlugin.openDatabase({
			name : "LTDB"
		});

		$("#btn_login").bind("click", app.loginOnline);
		document.addEventListener("backbutton", function () {}, false);

		db.transaction(function(tx) {
			SQLiteTool.init(tx);
			/*
			 SQLiteTool.insertLog(db, {
			 userAcc : "",
			 userName : "",
			 logger : "打开软件"
			 });*/

			SQLiteTool.queryUsers(db, function(json) {

				var el, li, i, user;
				el = document.getElementById('userlist');

				for ( i = 0; i < json.length; i++) {
					user = json[i];
					li = document.createElement('li');
					li.innerHTML = "<a href=\"javascript:app.selectUser(" + user._ID + ")\">" + user.userName + "</a><a href=\"javascript:app.deleteUser(" + user._ID + ")\">删除</a>";
					el.appendChild(li, el.childNodes[0]);
				}
				$("#userlist").listview("refresh");

				if (0 < json.length) {
					app.selectUser(json[0]._ID);
				}
			});
		});
	},

	deleteUser : function(userId) {
		console.log("delete user _ID:" + userId);
		var db = window.sqlitePlugin.openDatabase({
			name : "LTDB"
		});
		SQLiteTool.deleteUser(db, userId, function(json) {
			// 清空userlist。
			$("#userlist").empty();
			var el, li, i, user;
			el = document.getElementById('userlist');

			// 增加标题
			li = document.createElement('li');
			li.setAttribute('data-role', 'list-divider');
			li.innerText = "选择用户";
			el.appendChild(li, el.childNodes[0]);

			for ( i = 0; i < json.length; i++) {
				user = json[i];
				li = document.createElement('li');
				li.innerHTML = "<a href=\"javascript:app.selectUser(" + user._ID + ")\">" + user.userAcc + "</a><a href=\"javascript:app.deleteUser(" + user._ID + ")\">删除</a>";
				el.appendChild(li, el.childNodes[0]);
			}
			$("#userlist").listview("refresh");
		});
	},
	selectUser : function(userId) {
		console.log("select User _ID:" + userId);
		var db = window.sqlitePlugin.openDatabase({
			name : "LTDB"
		});
		SQLiteTool.queryUser(db, userId, function(json) {
			console.log("选中：" + JSON.stringify(json));
			$("#userid").val(json.userAcc);

			if (true == json.autoLogin || "true" == json.autoLogin) {
				$("#autoLogin").attr("checked", true).checkboxradio("refresh");
				$("#chkRememberPwd").attr("checked", true).checkboxradio("refresh");
				$("#pwd").val(json.recordPwd);
				
				app.loginOnline();
			} else {
				$("#autoLogin").attr("checked", false).checkboxradio("refresh");
				if (true == json.rememberPwd || "true" == json.rememberPwd) {
					$("#chkRememberPwd").attr("checked", true).checkboxradio("refresh");
					$("#pwd").val(json.recordPwd);
				} else {
					$("#chkRememberPwd").attr("checked", false).checkboxradio("refresh");
					$("#pwd").val("");
				}
			}
		});
		$('#popupMenu').popup('close');
	},
	loginOnline : function() {

		if ($("#userid").val().length <= 0) {
			
            navigator.notification.confirm('请输入用户名!', // message
                                           function() {
                                           
                                           }, // callback to invoke with index of button pressed
                                           '提示', // title
                                           ['确定'] // buttonLabels
                                           );
			return;
		}
		if ($("#pwd").val().length <= 0) {
			
            navigator.notification.confirm('请输入密码!', // message
                                           function() {
                                           
                                           }, // callback to invoke with index of button pressed
                                           '提示', // title
                                           ['确定'] // buttonLabels
                                           );
			return;
		}
        
        
        var curUserName = TempCache.getItem("userid");
        
        console.log("curUserName:"+curUserName);
        
        if(curUserName)
        {
            if($("#userid").val()!=curUserName)
            {
              
                
                navigator.notification.confirm('本手机已绑定帐号:'+curUserName+',不能用其他帐号登录', // message
                                               function() {
                                               
                                               }, // callback to invoke with index of button pressed
                                               '提示', // title
                                               ['确定'] // buttonLabels
                                               );
                 return;
                
            
            }
           
        
        }
        
       
        
        
        
        
		console.log("网络状态：" + navigator.connection.type);
		if ("none" == navigator.connection.type || "unknown" == navigator.connection.type) {
			console.log("离线登录：" + $("#userid").val() + ":" + $("#pwd").val());
			app.loginOffline($("#userid").val(), $("#pwd").val());
			return;
		}
		
		

        LTLoadStart("登录中……");
        
		var user = new Object();

		user.UserCode = $("#userid").val();
		user.Password = $("#pwd").val();

		NetConnect.getServer(user, "ForUserCheck.ashx", function(json) {

            LTStopLoading();
                             
			//loadStop();
			
			if ((eval(json).success) == 'true') {

				console.log("back json" + JSON.stringify(json));
                //设置TempCache!!!!!
                TempCache.setItem("userid", $("#userid").val());
				TempCache.setItem("userName", (eval(json).msg));
				TempCache.setItem("empid", (eval(json).empid));
                TempCache.setItem("type",(eval(json).type));
        
                var typeArr = (eval(json).type).split(",");
                
                TempCache.setItem("lotImgType",typeArr[0]);
                
                console.log("default lotImgType:"+TempCache.getItem("lotImgType"));
                             
                             
				var el = document.getElementById('userlist');
				var li = document.createElement('li');
				li.innerHTML = "<a href='#'>" + $("#userid").val() + "</a><a href='#'>删除</a>";
				el.appendChild(li, el.childNodes[0]);
				$("#userlist").listview("refresh");

				//console.log("userId:" + (eval(json).empid) + ";userAcc:" + $("#userid").val() + "checked:" + $("#autoLogin").is(':checked') + $("#chkRememberPwd").is(':checked'));
				var now = new Date();
				var nowStr = now.format("yyyy-MM-dd hh:mm:ss");
				
				var db = window.sqlitePlugin.openDatabase({
					name : "LTDB"
				});
				SQLiteTool.autoInsertUser(db, {
					userId : (eval(json).empid),
					userAcc : $("#userid").val(),
					userName : (eval(json).msg),
					lstLoginTime : nowStr,
					autoLogin : $("#autoLogin").is(':checked'),
					rememberPwd : $("#chkRememberPwd").is(':checked'),
					recordPwd : $("#pwd").val()
				}, function() {
					$.mobile.changePage("main.html");
				});

				//window.location.href="main.html";

			} else {

				navigator.notification.confirm('用户名或密码错误', // message
				function() {

				}, // callback to invoke with index of button pressed
				'提示', // title
				['确定'] // buttonLabels
				);

				return;

			}

		}, function(error) {

                             
            LTStopLoading();
			//loadStop();
            console.log("error"+JSON.stringify(error));
			navigator.notification.confirm('网络异常', // message
			function() {

			}, // callback to invoke with index of button pressed
			'提示', // title
			['确定'] // buttonLabels
			);

			var str = JSON.stringify(error);

		});

	},
	loginOffline : function(username, psw) {
		var db = window.sqlitePlugin.openDatabase({
			name : "LTDB"
		});
		SQLiteTool.loginOffline(db, username, psw, function(json) {
			if (json) {
				// 登录成功.
				TempCache.setItem("userName", json.userName);
				TempCache.setItem("empid", json.userId);

				//console.log("userId:" + (eval(json).empid) + ";userAcc:" + $("#userid").val() + "checked:" + $("#autoLogin").is(':checked') + $("#chkRememberPwd").is(':checked'));
				var now = new Date();
				var nowStr = now.format("yyyy-MM-dd hh:mm:ss");
				SQLiteTool.autoInsertUser(db, {
					userId : json.userId,
					userAcc : $("#userid").val(),
					userName : json.userName,
					lstLoginTime : nowStr,
					autoLogin : $("#autoLogin").is(':checked'),
					rememberPwd : $("#chkRememberPwd").is(':checked'),
					recordPwd : $("#pwd").val()
				}, function() {
					$.mobile.changePage("main.html");
				});
			} else {

				navigator.notification.confirm('用户名或密码错误', // message
				function() {

				}, // callback to invoke with index of button pressed
				'提示', // title
				['确定'] // buttonLabels
				);

				return;

			}
		});
	}
};
