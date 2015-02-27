var SQLiteTool = {
	init : function(tx) {
		tx.executeSql("CREATE TABLE IF NOT EXISTS server (_ID INTEGER PRIMARY KEY autoincrement, serverName VARCHAR(50), serverIP VARCHAR(50), encryptLogin BOOLEAN DEFAULT FALSE, loginConfig VARCHAR(8) DEFAULT '111')");

		tx.executeSql("CREATE TABLE IF NOT EXISTS user (_ID INTEGER PRIMARY KEY autoincrement, userId VARCHAR(50), userName VARCHAR(50), userAcc VARCHAR(50), createTime DATETIME DEFAULT (datetime('now', 'localtime')), lstLoginTime DATETIME DEFAULT (datetime('now', 'localtime')), autoLogin BOOLEAN, rememberPwd BOOLEAN, recordPwd VARCHAR(50) DEFAULT '', refreshBDTime DATETIME, cleanPeriod INTEGER)");

		tx.executeSql("CREATE TABLE IF NOT EXISTS biaoduan(_ID INTEGER PRIMARY KEY autoincrement, BD_ID VARCHAR(64), Code VARCHAR(256), Name VARCHAR(256), Serial INTEGER)");

		tx.executeSql("CREATE TABLE IF NOT EXISTS imagestruc(_ID INTEGER PRIMARY KEY autoincrement, LotImgStruc_ID VARCHAR(64), LotImgStruc_ID_Parent VARCHAR(64), BD_ID VARCHAR(32), LotImgStrucCode VARCHAR(32), LotImgStrucName VARCHAR(64), SortCode VARCHAR(64), FullPath VARCHAR(512), NodeType VARCHAR(16), coordination VARCHAR(32), FOREIGN KEY(BD_ID) REFERENCES biaoduan(BD_ID))");
        
            

		tx.executeSql("CREATE TABLE IF NOT EXISTS imageform(_ID INTEGER PRIMARY KEY autoincrement, title VARCHAR(64), remark VARCHAR(256), time DATETIME, imgStrucId VARCHAR(64), imgStrucFullPath VARCHAR(512), coordination VARCHAR(64), uploadState INTEGER DEFAULT 0, uploadTime DATETIME, FOREIGN KEY(imgStrucId) REFERENCES imagestruc(LotImgStruc_ID))");

		tx.executeSql("CREATE TABLE IF NOT EXISTS imagedata(_ID INTEGER PRIMARY KEY autoincrement, imagePath VARCHAR(64), formID INTEGER, coordination VARCHAR(64), FOREIGN KEY(formID) REFERENCES imageform(_ID))");
        //本地照片数据库表
		tx.executeSql("CREATE TABLE IF NOT EXISTS imgData(_ID INTEGER PRIMARY KEY autoincrement, imagePath VARCHAR(64), formID VARCHAR(64),latitude VARCHAR(64),longitude VARCHAR(64),takeTime  VARCHAR(64),showTime VARCHAR(64),upLoader VARCHAR(64),FOREIGN KEY(formID) REFERENCES imageform(_ID))");

		tx.executeSql("CREATE TABLE IF NOT EXISTS log (_ID INTEGER PRIMARY KEY autoincrement, userName VARCHAR(50), userAcc VARCHAR(50), logger VARCHAR(50), createTime DATETIME DEFAULT (datetime('now', 'localtime')))");
        
        //纪录每个标段的更新时间表
        tx.executeSql("CREATE TABLE IF NOT EXISTS BDUpDateTime (_ID INTEGER PRIMARY KEY autoincrement, BD_ID VARCHAR(64),lastUpDateTime VARCHAR(64))");
		
		//服务器所有的标段信息结构表
		 tx.executeSql("CREATE TABLE IF NOT EXISTS CIM_LotImgStruc (_ID INTEGER PRIMARY KEY autoincrement,  LotImgStruc_ID VARCHAR(64), LotImgStruc_ID_Parent VARCHAR(64), BD_ID VARCHAR(32), Partial_ID VARCHAR(32), ConsFlowTempl_ID VARCHAR(32), ConsTechTempl_ID VARCHAR(32), NodeType VARCHAR(16), BDP_ID VARCHAR(32), BDP_Code VARCHAR(32),BDP_Name VARCHAR(32), LotImgStrucCode VARCHAR(32), LotImgStrucName VARCHAR(64), SortCode VARCHAR(64), Memo VARCHAR(32), FullPath VARCHAR(512), IsPartialImp INTEGER, longitude VARCHAR(64), latitude VARCHAR(64), UpdateTime DATETIME, UpdateFlag VARCHAR(16))");
	},
	/**
	 * 新增服务器
	 * @param {Object} db
	 * @param {Object} json
	 */
	insertServer : function(db, json) {
		// var db = window.sqlitePlugin.openDatabase({name: "LTDB"});
		db.transaction(function(tx) {
			tx.executeSql("INSERT INTO server (serverName, serverIP, encryptLogin) VALUES (?,?,?)", [json.serverName, json.serverIP, json.encryptLogin], function(tx, res) {
				console.log("成功插入一条【server】记录，insertId: " + res.insertId);

			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 更新服务器
	 * @param {Object} db
	 * @param {Object} json
	 */
	updateServer : function(db, json) {
		// var db = window.sqlitePlugin.openDatabase({name: "LTDB"});
		db.transaction(function(tx) {
			tx.executeSql("UPDATE server set serverName=?, serverIP=?, encryptLogin=?, loginConfig=? where _ID=?", [json.serverName, json.serverIP, json.encryptLogin, json.loginConfig, json._ID], function(tx, res) {
				console.log("成功更新一条【server】记录，updateId: " + json._ID);

			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 删除服务器
	 * @param {Object} db
	 * @param {Object} json
	 */
	deleteServer : function(db, _id) {
		db.transaction(function(tx) {
			tx.executeSql("DELETE FROM server where _ID=?", [_id], function(tx, res) {
				console.log("成功删除一条【server】记录，deleteId: " + _id);

			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 获得服务器
	 */
	queryServer : function(db, _id, callback) {

		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM server where _ID=?", [_id], function(tx, res) {
				console.log("获得【server】记录: " + res);
				callback(res.rows.item(0));
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 活的服务器列表
	 */
	queryServers : function(db, callback) {
		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM server", [], function(tx, res) {
				console.log("获得【server】记录: " + res);
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 插入用户
	 * @param {Object} db
	 * @param {Object} json
	 */
	insertUser : function(db, json, callback) {

		db.transaction(function(tx) {
			tx.executeSql("INSERT INTO user (userId, userAcc, userName, lstLoginTime, autoLogin, rememberPwd, recordPwd) VALUES (?,?,?,?,?,?,?)", [json.userId, json.userAcc, json.userName, json.lstLoginTime, json.autoLogin, json.rememberPwd, json.recordPwd], function(tx, res) {
				console.log("成功插入一条【user】记录，insertId: " + res.insertId);
				callback();
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 更新用户
	 * @param {Object} db
	 * @param {Object} json
	 */
	updateUser : function(db, json, callback) {

		db.transaction(function(tx) {
			//var now =
			console.log("update......");
			tx.executeSql("UPDATE user set userAcc=?, userName=?, lstLoginTime=?, autoLogin=?, rememberPwd=?, recordPwd=? where userId=?", [json.userAcc, json.userName, json.lstLoginTime, json.autoLogin, json.rememberPwd, json.recordPwd, json.userId], function(tx, res) {
				console.log("成功更新一条【user】记录，updateId: " + res.insertId);
				callback();
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 离线登录用户
	 * @param {Object} db
	 * @param {Object} json
	 */
	loginOffline : function(db, userAcc, pwd, callback) {

		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM user where userAcc=? and recordPwd=?", [userAcc, pwd], function(tx, res) {
				console.log("获得【user】记录: " + res.rows.length + "条记录。");
				if (0 < res.rows.length) {
					callback(res.rows.item(0));
				} else {
					callback(null);
				}
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 更新或插入用户
	 * @param {Object} db
	 * @param {Object} json
	 */
	autoInsertUser : function(db, json, callback) {

		db.transaction(function(tx) {
			console.log("获得【json.userId】记录: " + json.userId);
			tx.executeSql("SELECT * FROM user where userId=?", [json.userId], function(tx, res) {
				console.log("获得【user】记录: " + res.rows.length + "条记录。");
				if (0 < res.rows.length) {
					console.log("更新【user】记录 :" + JSON.stringify(json));
					SQLiteTool.updateUser(db, json, callback);
				} else {
					console.log("新增【user】记录");
					SQLiteTool.insertUser(db, json, callback);
				}
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 *
	 * @param {Object} db
	 * @param {Object} _id
	 */
	deleteUser : function(db, _id, callback) {
		console.log("here");
		db.transaction(function(tx) {
			tx.executeSql("DELETE FROM user where _ID=?", [_id], function(tx, res) {
				console.log("成功删除一条【user】记录，deleteId: " + _id);
				SQLiteTool.queryUsers(db, callback);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 获得用户
	 */
	queryUser : function(db, _id, callback) {

		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM user where _ID=?", [_id], function(tx, res) {
				console.log("获得【user】记录: " + res);
				callback(res.rows.item(0));
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 获得用户列表
	 */
	queryUsers : function(db, callback) {
		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM user order by lstLoginTime desc", [], function(tx, res) {
				console.log("获得【user】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 *插入标段
	 */
	insertBD : function(db, jsonArr) {
		
		console.log("length:::" + jsonArr.length);
		db.transaction(function(tx) {
			for (var i = 0; i < jsonArr.length; i++) {
				var json = jsonArr[i];
				if (0 == json.UpdateFlag) {
					// 插入
					tx.executeSql("INSERT INTO biaoduan (BD_ID, Code, Name, Serial) VALUES (?,?,?,?)", [json.BD_ID, json.Code, json.Name, json.Serial], function(tx, res) {
  
						console.log("成功插入一条【标段】记录，insertId: " + res.insertId);
                                  console.log();
					}, function(e) {
						console.log("ERROR: " + e.message);
					});
				} else if (1 == json.UpdateFlag) {
					// 更新
                       //更新的操作有误，改成先删除后插入！
                       tx.executeSql("DELETE FROM biaoduan where BD_ID=?", [json.BD_ID], function(tx, res) {
                                     console.log("成功删除一条【标段】记录，insertId: " + res.insertId);
                                     
                                     }, function(e) {
                                     console.log("ERROR: " + e.message);
                                     });
                       
                       tx.executeSql("INSERT INTO biaoduan (BD_ID, Code, Name, Serial) VALUES (?,?,?,?)", [json.BD_ID, json.Code, json.Name, json.Serial], function(tx, res) {
                                     
                                     console.log("成功插入一条【标段】记录，insertId: " + res.insertId);
                                     console.log();
                                     }, function(e) {
                                     console.log("ERROR: " + e.message);
                                     });

                       
//					tx.executeSql("UPDATE biaoduan SET Code=?, Name=?, Serial=? where BD_ID=?", [json.Code, json.Name, json.Serial, json.BD_ID], function(tx, res) {
//                                  console.log("更新？");
//						console.log("成功更新一条【标段】记录，insertId: " + res.insertId);
//
//					}, function(e) {
//						console.log("ERROR: " + e.message);
//					});
				} else if (2 == json.UpdateFlag) {

					tx.executeSql("DELETE FROM biaoduan where BD_ID=?", [json.BD_ID], function(tx, res) {
						console.log("成功删除一条【标段】记录，insertId: " + res.insertId);

					}, function(e) {
						console.log("ERROR: " + e.message);
					});
				}
			}
		});
	},
	/**
	 *获取标段
	 */
	queryBD : function(db, callback) {

		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM biaoduan", [], function(tx, res) {
				console.log("获得【biaoduan】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},

	/**
	 *插入图片信息
	 */
	insertImgData : function(db, imageJson,successCallBack) {
		//console.l
		
		db.transaction(function(tx) {

			//图片路径 表单ID 纬度 经度 拍照时间
			tx.executeSql("INSERT INTO imgData (imagePath, formID, latitude, longitude,takeTime,showTime,upLoader) VALUES (?,?,?,?,?,?,?)", [imageJson.imagePath, imageJson.formID, imageJson.latitude, imageJson.longitude, imageJson.takeTime,imageJson.showTime,imageJson.upLoader], function(tx, res) {
				console.log("成功插入一条图片记录，insertId: " + res.insertId);
                    successCallBack();

			}, function(e) {
				console.log("ERROR: 插入图片" + e.message);
			});

		});
	},
	/**
	 *获取图片
	 */
	getImgData : function(db, callback) {

		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM imgData", [], function(tx, res) {
				console.log("获得【biaoduan】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	//获取已经归档的图片
	getImgDataHadFile : function(db, callback) {
		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM imgData WHERE formID  <>''", [], function(tx, res) {
				console.log("获得【归档】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});

	},
	//获取未归档的图片
	getImgDataNotFile : function(db, callback) {
		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM imgData WHERE formID=?", [''], function(tx, res) {
				console.log("获得 未归档 记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});

	},
	/**
	 *根据时间段删除图片
	 */
	deletImgData : function(db, beginDate, endDate) {
		db.transaction(function(tx) {
			tx.executeSql("delete FROM imgData WHERE takeTime between ? and ?", [beginDate, endDate], function(tx, res) {

				console.log("delete success!!");
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
    /**
	 *删除某段时间前的图片
	 */
    deletImgDataBeforeTheDate: function(db, theDate,success,failuer)
    {
        
        db.transaction(function(tx) {
                       tx.executeSql("delete FROM imgData WHERE takeTime < ?", [theDate], function(tx, res) {
                                     
                                     success();
                                     
                                     console.log("delete success!!");
                                     }, function(e) {
                                     console.log("ERROR: " + e.message);
                                     failuer(e);
                                     });
                       });
    
    },
    
    /**
	 *根据图片路径删除图片
	 */
    deletImgFormDataWithPath:function(db,imgPath,callback){
    db.transaction(function(tx){
                   tx.executeSql("delete FROM imgData WHERE imagePath=?",[imgPath],function(tx,r){
                                 callback();
                                 
                                 },function(e){
                                 
                                 console.log("ERROR: " + e.message);
                                 
                                 })
                   
                   
                   });
    
    },
    /**
	 *删除多个图片根据图片路径数组
	 */
    deletImgDataWithPaths:function(db,imgPaths,callback){
    db.transaction(function(tx){
                   
                   
                   var ids = "";
                   var sql = "delete FROM imgData WHERE imagePath in (";
                   var params = new Array();
                   
                   for (var i = 0; i < imgPaths.length; i++)
                   {
                    params[i] = imgPaths[i].imagePath;
                   
                    if(i==(imgPaths.length-1))
                   {
                    sql = sql + "?)";
                   }
                   else
                   {
                     sql = sql + "?,";
                   }
                   
                   
                   }
                   
                   console.log("deletImgDataWithPaths sql:"+sql);
                   
                   tx.executeSql(sql,params,function(tx,r){
                                 callback();
                                 
                                 },function(e){
                                 
                                 console.log("ERROR: " + e.message);
                                 
                                 })
                   
                   
                   });
    
    },
	/**
	 /**
	 * 获取分项结构
	 * db : 数据库
	 * bdid : 标段id，为null时输出全部
	 * parent ： 不能为空，跟结构请传值""
	 */
	queryStrucByBDAndParent : function(db, bdid, parent, callback) {

		db.transaction(function(tx) {
			var sql1;
			var sql11 = new Array();
			console.log(sql1 + "::::" + sql11);
			if (bdid) {
				sql1 = "SELECT * FROM imagestruc where BD_ID = ? and LotImgStruc_ID_Parent = ? order by SortCode";
				sql11[0] = bdid;
				sql11[1] = parent;
			} else {
				sql1 = "SELECT * FROM imagestruc order by SortCode";
			}
			console.log(sql1 + "::::" + sql11);
			tx.executeSql(sql1, sql11, function(tx, res) {
				console.log("获得【imagestruc】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 获取分项结构
	 * db : 数据库
	 * bdid : 标段id，为null时输出全部
	 * sortCode ：排序号，匹配“sortCode%”，为空是，输出bdid的全部数据
	 */
	queryStrucBySortCode : function(db, bdid, sortCode, callback) {

		db.transaction(function(tx) {
			var sql1;
			var sql11 = new Array();
			
			if (bdid && sortCode) {
				sql1 = "SELECT * FROM imagestruc where BD_ID = ? and SortCode like ? order by SortCode";
				sql11[0] = bdid;
				sql11[1] = sortCode + "%";
			} else if (bdid) {
				sql1 = "SELECT * FROM imagestruc where BD_ID = ? order by SortCode";
				sql11[0] = bdid;
			} else if (sortCode) {
				sql1 = "SELECT * FROM imagestruc where SortCode like ? order by SortCode";
				sql11[0] = sortCode + "%";
			} else {
				sql1 = "SELECT * FROM imagestruc order by SortCode";
                       
                
                       
                       
			}

			console.log(sql1 + "::::" + sql11);
			tx.executeSql(sql1, sql11, function(tx, res) {
				console.log("获得【imagestruc】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 获取分项结构
	 * db : 数据库
	 * bdid : 标段id，为null时输出全部
	 * sortCode ：排序号，匹配“sortCode%”，为空是，输出bdid的全部数据
	 */
	queryUploadableStrucBySortCode : function(db, bdid, sortCode, callback) {

		db.transaction(function(tx) {
			var sql1 = "SELECT * FROM imagestruc where NodeType != '350_01'";
			var sql11 = new Array();
			
			if (bdid && sortCode) {
				sql1 = sql1 + " and BD_ID = ? and SortCode like ? order by SortCode";
				sql11[0] = bdid;
				sql11[1] = sortCode + "%";
			} else if (bdid) {
				sql1 = sql1 + " and BD_ID = ? order by SortCode";
				sql11[0] = bdid;
			} else if (sortCode) {
				sql1 = sql1 + " and SortCode like ? order by SortCode";
				sql11[0] = sortCode + "%";
			} else {
				sql1 = sql1 + " order by SortCode";
			}

			console.log(sql1 + "::::" + sql11);
			tx.executeSql(sql1, sql11, function(tx, res) {
				console.log("获得【imagestruc】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 获取分项结构
	 * db : 数据库
	 * keyword ：关键字搜索
	 */
	queryStrucByString : function(db, keyword, callback) {

		db.transaction(function(tx) {
			var sql = "SELECT * FROM imagestruc where FullPath like ? order by SortCode";
			tx.executeSql(sql, ["%" + keyword + "%"], function(tx, res) {
				console.log("获得【imagestruc】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 获取分项结构
	 * db : 数据库
	 * keyword ：关键字搜索
	 */
	queryUploadableStrucByString : function(db, keyword, callback) {

		db.transaction(function(tx) {
			var sql = "SELECT * FROM imagestruc where NodeType != '350_01' and FullPath like ? order by SortCode";
			tx.executeSql(sql, ["%" + keyword + "%"], function(tx, res) {
				console.log("获得【imagestruc】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 *插入分项结构
	 */
	insertStruc : function(db, jsonArr) {
		db.transaction(function(tx) {
			for (var i = 0; i < jsonArr.length; i++) {
				var json = jsonArr[i];
                console.log("FullPath为：：" + json.FullPath);
				/*if(0 == 0){
					//查询
					console.log("insertStruc-----------: ");
					// 插入
					tx.executeSql("INSERT INTO imagestruc (LotImgStruc_ID, LotImgStruc_ID_Parent, BD_ID, LotImgStrucCode, LotImgStrucName, SortCode, FullPath, NodeType) VALUES (?,?,?,?,?,?,?,?)", [json.LotImgStruc_ID, json.LotImgStruc_ID_Parent, json.BD_ID, json.LotImgStrucCode, json.LotImgStrucName, json.SortCode, json.FullPath, json.NodeType], function(tx, res) {
						console.log("成功插入一条【分项结构】记录，insertId: " + res.insertId);
                        
					}, function(e) {
						console.log("ERROR: " + e.message);
					});
						
				}*/
				if (0 == json.UpdateFlag) {
					// 插入
					tx.executeSql("INSERT INTO imagestruc (LotImgStruc_ID, LotImgStruc_ID_Parent, BD_ID, LotImgStrucCode, LotImgStrucName, SortCode, FullPath, NodeType) VALUES (?,?,?,?,?,?,?,?)", [json.LotImgStruc_ID, json.LotImgStruc_ID_Parent, json.BD_ID, json.LotImgStrucCode, json.LotImgStrucName, json.SortCode, json.FullPath, json.NodeType], function(tx, res) {
						console.log("成功插入一条【分项结构】记录，insertId: " + res.insertId);
                        
					}, function(e) {
						console.log("ERROR: " + e.message);
					});
				} else if (1 == json.UpdateFlag) {
					// 更新
					tx.executeSql("UPDATE imagestruc set LotImgStruc_ID_Parent=?, BD_ID=?, LotImgStrucCode=?, LotImgStrucName=?, SortCode=?, FullPath=?, NodeType=? where LotImgStruc_ID=?", [json.LotImgStruc_ID_Parent, json.BD_ID, json.LotImgStrucCode, json.LotImgStrucName, json.SortCode, json.FullPath, json.NodeType, json.LotImgStruc_ID], function(tx, res) {
						console.log("成功更新一条【分项结构】记录，insertId: " + res.insertId);

					}, function(e) {
						console.log("ERROR: " + e.message);
					});
				} else if (2 == json.UpdateFlag) {

					tx.executeSql("DELETE FROM imagestruc where LotImgStruc_ID=?", [json.LotImgStruc_ID], function(tx, res) {
						console.log("成功删除一条【分项结构】记录，insertId: " + res.insertId);

					}, function(e) {
						console.log("ERROR: " + e.message);
					});
				}
			}
		});
	},
	/**
	 *更新图片信息
	 */
	updateImgData : function(db, formId, imageJsons, callback) {

		db.transaction(function(tx) {

			var ids = "";
			var sql = "UPDATE imgData set formID=? where 1=0";
			var params = new Array();
			params[0] = formId;
			for (var i = 0; i < imageJsons.length; i++) {
				var imageJson = imageJsons[i];
				//ids[i] = imageJson.imagePath;
				sql = sql + " or imagePath=?";
				params[i + 1] = imageJson.imagePath;
			}
			console.log("sql:" + sql);
			tx.executeSql(sql, params, function(tx, res) {
				// console.log("更新插入一条图片记录，insertId: " + res.insertId);
				callback();
			}, function(e) {
				console.log("ERROR: 插入图片" + e.message);
			});

		});
	},
	/**
	 *插入图片表单信息
	 */
	insertImgForm : function(db, structId, structFullPath, imageJsons, callback) {
		//var arrJsonStr = JSON.stringify(selectImgArr);
		if (0 < imageJsons.length) {
			var coordination = "";
			coordination = (imageJsons[0]).latitude + "," + (imageJsons[0]).longitude;
			console.log("form coordination:" + coordination);
			var now = new Date();
			var nowStr = now.format("yyyy-MM-dd");

			db.transaction(function(tx) {

				//图片路径 表单ID 纬度 经度 拍照时间
				tx.executeSql("INSERT INTO imageform (time, imgStrucId, imgStrucFullPath, coordination) VALUES (?,?,?,?)", [nowStr, structId, structFullPath, coordination], function(tx, res) {
					console.log("成功插入一条图片记录，insertId: " + res.insertId);
					SQLiteTool.updateImgData(db, res.insertId, imageJsons, callback);
				}, function(e) {
					console.log("ERROR: 插入图片" + e.message);
				});

			});
		} else {

		}
	},
		/**
	 * 获得影像表單列表
     * 用于显示未提交的数目
	 */
	queryImageFormsCount : function(db, callback) {

		db.transaction(function(tx) {
			//tx.executeSql("SELECT MIN(d.imagePath) as imagePath, f.* FROM imageform f, imgData d where f._ID = d.formID and f.uploadState = ? GROUP BY d.formID", [status], function(tx, res) {
			tx.executeSql("SELECT count(*) as count FROM imageform where uploadState=?", [0], function(tx, res) {
				//tx.executeSql("SELECT * FROM imgData", [], function(tx, res) {
				//console.log("获得【imageform】记录: " + res.rows.length);
				var result = res.rows.item(0).count;
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 *更新图片信息
     *上传图片时，点击提交时调用
	 */ 
	updateImageForm : function(db, formId, callback) {

		db.transaction(function(tx) {

			var now = new Date();
			var nowStr = now.format("yyyy-MM-dd");

			tx.executeSql("UPDATE imageform set uploadState=?, uploadTime=? where _ID=?", [1, nowStr, formId], function(tx, res) {
				console.log("更新插入一条表单记录，insertId: " + res.insertId);
				callback();
			}, function(e) {
				console.log("ERROR: 更新表单" + e.message);
			});
		});
	},
	/**
	 * 获得影像表單列表
     * 查看影像时调用
	 */
	queryImages : function(db, formId, callback) {

		db.transaction(function(tx) {
			//tx.executeSql("SELECT MIN(d.imagePath) as imagePath, f.* FROM imageform f, imgData d where f._ID = d.formID GROUP BY d.formID", [], function(tx, res) {
			tx.executeSql("SELECT * FROM imageform where _ID=?", [formId], function(tx0, res) {
				//tx.executeSql("SELECT * FROM imgform where _ID=?", [formId], function(tx2, res) {
				console.log("获得【imageform】记录: " + res.rows.length);
				if (0 < res.rows.length) {
					var result = res.rows.item(0);
					tx.executeSql("SELECT * FROM imgData where formId=?", [formId], function(tx, res2) {

						var result2 = new Array();
						for (var i = 0; i < res2.rows.length; i++) {
							result2[i] = res2.rows.item(i);
						}
						result.images = result2;
                                  
						callback(result);
					}, function(e) {
						console.log("ERROR: " + e.message);
					});
				} else {
					console.log("ERROR: 没有找到表单。" + e.message);
				}

			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
    /**
     * 删除影像表單列表
     * 查看影像时调用
     */
    deleteImages : function(db, formId) {
        
        db.transaction(function(tx) {
                       //tx.executeSql("SELECT MIN(d.imagePath) as imagePath, f.* FROM imageform f, imgData d where f._ID = d.formID GROUP BY d.formID", [], function(tx, res) {
                       tx.executeSql("DELETE  FROM imageform where _ID=?", [formId]);
//                                     , function(tx0, res) {
//                                     //tx.executeSql("SELECT * FROM imgform where _ID=?", [formId], function(tx2, res) {
//                                     console.log("删除： " + res.rows.length);
//                                     if (0 < res.rows.length) {
//                                     var result = res.rows.item(0);
                       tx.executeSql("UPDATE imgData set formId='' where formId=?", [formId]);
//                                                   function(tx, res2) {
//                                                   
//                                                   var result2 = new Array();
//                                                   for (var i = 0; i < res2.rows.length; i++) {
//                                                   result2[i] = res2.rows.item(i);
//                                                   }
//                                                   result.images = result2;
//                                                   
//                                                   callback(result);
//                                                   }, function(e) {
//                                                   console.log("ERROR: " + e.message);
//                                                   });
//                                     } else {
//                                     console.log("ERROR: 没有找到表单。" + e.message);
//                                     }
//                                     
//                                     }, function(e) {
//                                     console.log("ERROR: " + e.message);
//                                     });
                       });
    },
    /**
	 * 获得影像表單列表
	 */
	queryImageForms : function(db, status, callback) {
        
		db.transaction(function(tx) {
                       tx.executeSql("SELECT MIN(d.imagePath) as imagePath, f.* FROM imageform f, imgData d where f._ID = d.formID and f.uploadState = ? GROUP BY d.formID", [status], function(tx, res) {
                                     //tx.executeSql("SELECT * FROM imageform", [], function(tx, res) {
                                     //tx.executeSql("SELECT * FROM imgData", [], function(tx, res) {
                                     console.log("获得【imageform】记录: " + res.rows.length);
                                     var result = new Array();
                                     for (var i = 0; i < res.rows.length; i++) {
                                     result[i] = res.rows.item(i);
                                     }
                                     
                                     callback(result);
                                     }, function(e) {
                                     console.log("ERROR: " + e.message);
                                     });
                       });
	},

	/**
	 * 插入日志
	 */
	insertLog : function(db, json) {

		db.transaction(function(tx) {
			tx.executeSql("INSERT INTO log (userAcc, userName, logger) VALUES (?,?,?)", [json.userAcc, json.userName, json.logger], function(tx, res) {
				console.log("成功插入一条【logger】记录，insertId: " + res.insertId);

			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	/**
	 * 获得日志列表
	 */
	queryLog : function(db, callback) {

		db.transaction(function(tx) {
			tx.executeSql("SELECT * FROM log LIMIT 50", [], function(tx, res) {
				console.log("获得【log】记录: " + res);
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
   
    
    //更新标段更新时间
    upBDTable:function(db,insertBD,insertUpDateTime,callback)
    {
        db.transaction(function(tx) {
                       tx.executeSql("SELECT lastUpDateTime FROM BDUpDateTime Where BD_ID=?", [insertBD], function(tx, res) {
                                     
                                     
                                 
                                     
                                     if(res.rows.length<=0)
                                     {
                                     
                                     console.log("upBDTable /更新标段更新时间");
                                     
                                     
                                     
                                     SQLiteTool.insertBDUpDateTime(db,insertBD,insertUpDateTime,callback);
     
                                     
                                     }
                                     else
                                     {
                                     
                                     console.log("更新标段更新时间");
                                     SQLiteTool.upDateBDUpDateTimeWithBDID(db,insertBD,insertUpDateTime,callback);
                                     
                                     }
                                     
                                     
                                     
                                     }, function(e) {
                                     console.log("ERROR: " + e.message);
                                     });
                       });
        
        
        
    },
    
    insertBDUpDateTime:function(db,tempID,tempTime,successCallback){
        db.transaction(function(tx) {
                       
                      
                       
                       tx.executeSql("INSERT INTO BDUpDateTime (BD_ID,lastUpDateTime) VALUES (?,?)", [tempID,tempTime], function(tx, res){
                                     
                                     console.log("插入标段更新 插入成功:");
                                     successCallback();
                                     
                                     },function(e){
                                     
                                     console.log("插入失败: " + e.message);
                                     
                                     });
                       
                       
                       
                       
                       });
       

    },
    upDateBDUpDateTimeWithBDID:function(db,insertBD,insertBDUpDateTime,callback){
        db.transaction(function(tx) {
                       
                       tx.executeSql("UPDATE BDUpDateTime set lastUpDateTime=? where BD_ID=?", [insertBDUpDateTime,insertBD], function(tx, res){
                                     
                                     
                                     callback();
                                     
                                     
                                     },function(e){
                                     
                                     console.log("更新插入失败: " + e.message);
                                     
                                     });
                       });
        
    },
    
    //获取某标段的最新更新时间
    getBDUpDateTimeWithID:function(db,BDId,callback){
        db.transaction(function(tx) {
                       tx.executeSql("SELECT lastUpDateTime FROM BDUpDateTime Where BD_ID=?", [BDId], function(tx, res) {
                                     
                                     if(res.rows.length>=1)
                                     {
                                        callback(res.rows.item(0).lastUpDateTime);
                                     }
                                     else
                                     {
                                        callback('');
                                     }
                                     
                                     
                                     
                                     },
                                     function(e){
                                     
                                     });});
    },
   
    //获取更新时间不为空的标段
    getBDsWhichUpTimeNoEmpty:function(db,callback){
    
        db.transaction(function(tx) {
                       tx.executeSql("SELECT * FROM BDUpDateTime Where lastUpDateTime !=?", [''], function(tx, res) {
                                     
                                     var result = new Array();
                                     for (var i = 0; i < res.rows.length; i++) {
                                     result[i] = res.rows.item(i);
                                     }
                                     callback(result);
                                     
                                     
                                     },
                                     function(e){
                                     
                                     });});
    
    },
	
	/**
	 * 获取部分标段的信息结构（根据用户标段权限）
	 * db : 数据库
	 * bdid : 标段id，为null时输出全部
	 */
	getCIM_LotImgStrucWithBdId : function(db, bdid, callback) {

		db.transaction(function(tx) {
			var sql1;
			var sql11 = new Array();
			
			if (bdid) {
				sql1 = "SELECT * FROM CIM_LotImgStruc where BD_ID = ? order by SortCode";
				sql11[0] = bdid;
			}  else {
				sql1 = "SELECT * FROM CIM_LotImgStruc order by SortCode";                            
			}

			console.log(sql1 + "::::" + sql11);
			tx.executeSql(sql1, sql11, function(tx, res) {
				console.log("获得【CIM_LotImgStruc】记录: " + res.rows.length + "条记录。");
				var result = new Array();
				for (var i = 0; i < res.rows.length; i++) {
					result[i] = res.rows.item(i);
				}
				callback(result);
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	},
	 /**
	 *执行刷新数据 如果CIM_LotImgStruc数据库表为空则执行插入所有标段信息结构操作，
	 *如果CIM_LotImgStruc不为空则不做任何操作
	 */
	 insertCIM_LotImgStruc : function(db, callback) {
		 db.transaction(function(tx) {
			var sql1 = "SELECT * FROM CIM_LotImgStruc";                            
            
			console.log(sql1 + "::::::::::::::::");
			tx.executeSql(sql1, [], function(tx, res) {
				console.log("获得【CIM_LotImgStruc】记录: " + res.rows.length + "条记录。");
				if(res.rows.length <= 0){
					SQLiteTool.operateCIM_LotImgStruc(db, [], 0, callback);
				}else{
					SQLiteTool.operateCIM_LotImgStruc(db, [], 1, callback);
					//callback('所有标段已经存在---------------');
				}
				
			}, function(e) {
				console.log("ERROR: " + e.message);
			});
		});
	 },
     /**
	 *新增标段的信息结构
	 */
	operateCIM_LotImgStruc : function(db, jsonArr, changeflag, callback) {
		db.transaction(function(tx) {
			txonapp = tx;
			if(changeflag == 0){
				//载入数据库js
				loadjscssfile("../util/g1.js","js");	
				//callback('完毕！');
			}else {
				SQLiteTool.getCIM_LotImgStrucWithBdId(db, bdID, function(backJson) {
													  console.log("标段结构信息:"+JSON.stringify(backJson));
													  var strucs = backJson.LotImgStruc;
                                                                      
													  SQLiteTool.insertStruc(db, strucs);												  
													  
													  var d = new Date();
													  var YMDHMS = d.getFullYear() + "-" +(d.getMonth()+1) + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
													  
													  SQLiteTool.upBDTable(db,bdID,YMDHMS,function()
																		   {
																		   
																		   console.log("插入获取标段的结构最新数据");
																		   LTStopLoading();
																		   
																		   });
													});
			}
			/*for (var i = 0; i < jsonArr.length; i++) {
				var json = jsonArr[i];
                console.log("FullPath为：：" + json.FullPath);
				if (0 == changeflag) {
					
					// 插入
					/*tx.executeSql("Insert Into CIM_LotImgStruc (LotImgStruc_ID,LotImgStruc_ID_Parent,BD_ID,Partial_ID,ConsFlowTempl_ID,ConsTechTempl_ID,NodeType,BDP_ID,BDP_Code,BDP_Name,LotImgStrucCode,LotImgStrucName,SortCode,Memo,FullPath,IsPartialImp,Longitude,Latitude,UpdateTime,UpdateFlag)  Values('f4669c6d-2de7-4ce7-9429-00dfe8f53300','ccd1f0a9-ee4a-4ed7-aa82-7a258b1da2d6','ab03de36-91c4-467d-9910-42ff42dab779','7201e0d9-04b8-4045-bf60-082f4dc20c45','b6405e18-47cb-4765-871a-ac6173edbdf4','a0295c3e-c1ce-4b12-b5ea-58a4ddbff73f','350_04',null,null,null,'01010203','井间距、长度、竖直度、砂井直径、灌砂量、软土路基/井间距、长度、竖直度、砂井直径、灌砂量',0,null,null,'2014-01-01 10:38:46','0')", [], function(tx, res) {console.log("成功插入一条【分项结构】记录，insertId: " + res.insertId);}, function(e) {console.log("ERROR: " + e.message);});
					*/
					/*tx.executeSql("INSERT INTO CIM_LotImgStruc (LotImgStruc_ID, LotImgStruc_ID_Parent, BD_ID, LotImgStrucCode, LotImgStrucName, SortCode, FullPath, NodeType) VALUES (?,?,?,?,?,?,?,?)", [json.LotImgStruc_ID, json.LotImgStruc_ID_Parent, json.BD_ID, json.LotImgStrucCode, json.LotImgStrucName, json.SortCode, json.FullPath, json.NodeType], function(tx, res) {
						console.log("成功插入一条【分项结构】记录，insertId: " + res.insertId);
                        
					}, function(e) {
						console.log("ERROR: " + e.message);
					});*/
					
					//载入数据库js
					
					//loadjscssfile("CIM_LotImgStruc.js","js");
					
				/*} else if (1 == changeflag) {
					// 更新
					tx.executeSql("UPDATE CIM_LotImgStruc set LotImgStruc_ID_Parent=?, BD_ID=?, LotImgStrucCode=?, LotImgStrucName=?, SortCode=?, FullPath=?, NodeType=? where LotImgStruc_ID=?", [json.LotImgStruc_ID_Parent, json.BD_ID, json.LotImgStrucCode, json.LotImgStrucName, json.SortCode, json.FullPath, json.NodeType, json.LotImgStruc_ID], function(tx, res) {
						console.log("成功更新一条【分项结构】记录，insertId: " + res.insertId);

					}, function(e) {
						console.log("ERROR: " + e.message);
					});
				} else if (2 == changeflag) {

					tx.executeSql("DELETE FROM CIM_LotImgStruc where LotImgStruc_ID=?", [json.LotImgStruc_ID], function(tx, res) {
						console.log("成功删除一条【分项结构】记录，insertId: " + res.insertId);

					}, function(e) {
						console.log("ERROR: " + e.message);
					});
				}
			}
			var changeType;
			if(changeflag == 0){
				changeType = '插入';
			}else if(changeflag == 1){
				changeType = '更新';
			}else{
				changeType = '删除';
			}*/
			
			//callback(changeType+'完毕！');
		});
	},
    //更新标段的信息结构（慎用）
    upCIM_LotImgStruc:function(db,insertBD,insertUpDateTime,callback)
    {
        db.transaction(function(tx) {
                       tx.executeSql("SELECT lastUpDateTime FROM BDUpDateTime Where BD_ID=?", [insertBD], function(tx, res) {
                                     
                                     
                                 
                                     
                                     if(res.rows.length<=0)
                                     {
                                     
                                     console.log("upBDTable /更新标段更新时间");
                                     
                                     
                                     
                                     SQLiteTool.insertBDUpDateTime(db,insertBD,insertUpDateTime,callback);
     
                                     
                                     }
                                     else
                                     {
                                     
                                     console.log("更新标段更新时间");
                                     SQLiteTool.upDateBDUpDateTimeWithBDID(db,insertBD,insertUpDateTime,callback);
                                     
                                     }
                                     
                                     
                                     
                                     }, function(e) {
                                     console.log("ERROR: " + e.message);
                                     });
                       });
        
        
        
    },
    
	//重置标段的信息结构
    resetCIM_LotImgStruc:function(db,tempID,tempTime,successCallback){
        db.transaction(function(tx) {
                       
                      
                       
                       tx.executeSql("INSERT INTO BDUpDateTime (BD_ID,lastUpDateTime) VALUES (?,?)", [tempID,tempTime], function(tx, res){
                                     
                                     console.log("插入标段更新 插入成功:");
                                     successCallback();
                                     
                                     },function(e){
                                     
                                     console.log("插入失败: " + e.message);
                                     
                                     });
                       
                       
                       
                       
                       });
       

    },
    
    


};
