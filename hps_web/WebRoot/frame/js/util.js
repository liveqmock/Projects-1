/**
 * 去除前后空格
 */
String.prototype.trim = function() {
	return this.replace(/^\s*|\s*$/g, "");
};
/**
 * 验证是否为数字
 */
String.prototype.isNumber = function() {
	var o = this.trim();
	if (isNaN(Math.round(o)))
		return false;
	else
		return true;

};
/**
 * 验证是否为正整数
 */
String.prototype.integer = function() {
	var o = this.trim();
	var reg0 = /^[1-9]\d*$/;
	return reg0.test(o);
};
/**
 * 验证是否为手机号码
 */
String.prototype.isMobile = function() {
	var o = this.trim();
	var reg0 = /^(\+86)?13\d{9}$/; // 130--139。至少7位
	var reg1 = /^(\+86)?15\d{9}$/; // 联通153。至少7位
	var reg2 = /^(\+86)?18\d{9}$/; // 移动159。至少7位

	return (reg0.test(o) || reg1.test(o) || reg2.test(o));
};

/**
 * 验证混合电话
 */
String.prototype.mixPhone = function() {
	var o = this.trim();
	var reg0 = /^(\+86)?13/; // 130--139。至少7位
	var reg1 = /^(\+86)?15/; // 联通153。至少7位
	var reg2 = /^(\+86)?18/; // 移动159。至少7位

	if (reg0.test(o) || reg1.test(o) || reg2.test(o)) {
		var sreg0 = /^(\+86)?13\d{9}$/; // 130--139。至少7位
		var sreg1 = /^(\+86)?15\d{9}$/; // 联通153。至少7位
		var sreg2 = /^(\+86)?18\d{9}$/; // 移动159。至少7位
		return (sreg0.test(o) || sreg1.test(o) || sreg2.test(o));
	} else {
		if (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(o))
			return true;
		else
			return false;
	}
};
/**
 * 验证是否为电话号码
 */
//
String.prototype.isPhone = function() {
	var o = this.trim();
	if (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(o))
		return true;
	else
		return false;
};
/**
 * 将string转换为number，正负皆可 (如果字符串是浮点数，则小数点后建议控制在7位以内，第8位通常不能正确的四舍五入，具体边界不明)
 */
String.prototype.toNumber = function() {
	var o = this.trim();
	if (o.indexOf(".") > -1) {// 浮点数
		var a = o.split(".");
		var j = Math.round(o.replace(".", "")) / Math.pow(10, a[1].length);
		return j;
	} else {// 整数
		return Math.round(o);
	}
};
/**
 * 验证是否全为英文字母(只包含大小写字母，不包括“_”和数字)
 */
String.prototype.isLetter = function() {
	var o = this.trim();
	if (/[^a-zA-Z]/g.test(o))
		return false;
	else
		return true;
};
/**
 * 验证是否为全中文
 */
//
String.prototype.isChinese = function() {
	var o = this.trim();
	if (/[^\u4e00-\u9fa5]/g.test(o))
		return false;
	else
		return true;
};
/**
 * 验证是否为正确的价钱格式 999.99 小数点后两位小数
 */
//
String.prototype.isMoney = function() {
	var o = this.trim();
	if (/-?\d+(\.\d{0,2})?/.test(o))
		return true;
	else
		return false;
};
/**
 * 验证是否为正确的email格式
 */
//
String.prototype.isEmail = function() {
	var o = this.trim();
	if (/^[a-zA-Z0-9]([a-zA-Z0-9]*[-_.]?[a-zA-Z0-9]+)+@([\w-]+\.)+[a-zA-Z]{2,}$/
			.test(o))
		return true;
	else
		return false;
};
/**
 * 验证是否为正确的ip地址
 */
//
String.prototype.isIp = function() {
	var o = this.trim();
	if (/(\d+)\.(\d+)\.(\d+)\.(\d+)/g.test(o)) {
		if (Math.round(RegExp.$1) < 256 && Math.round(RegExp.$2) < 256
				&& Math.round(RegExp.$3) < 256 && Math.round(RegExp.$4) < 256)
			return true;
	}
	return false;
};
/**
 * 验证是否为正确的邮政编码
 */
//
String.prototype.isPostCode = function() {
	var o = this.trim();
	if (/\d{6}/.test(o))
		return true;
	else
		return false;
};
/**
 * 验证是否为正确的身份证格式
 */
//
String.prototype.isIdCard = function() {
	var o = this.trim();
	var area = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外"
	}
	var Y, JYM, S, M;
	var a = [];
	a = o.split("");

	if (area[parseInt(o.substr(0, 2))] == null) {
		return false;
	}

	switch (o.length) {
		case 15 :
			if ((parseInt(o.substr(6, 2)) + 1900) % 4 == 0
					|| ((parseInt(o.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(o
							.substr(6, 2)) + 1900)
							% 4 == 0)) {
				// 测试闰年出生日期的合法性
				var reg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;
			} else {
				// 测试平年出生日期的合法性
				var reg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;
			}
			if (reg.test(o)) {
				// 15位身份证验证通过
				return true;
			} else {
				// alert(Errors[2]);
				return false;
			}

		case 18 :
			if (parseInt(o.substr(6, 4)) % 4 == 0
					|| (parseInt(o.substr(6, 4)) % 100 == 0 && parseInt(o
							.substr(6, 4))
							% 4 == 0)) {
				// 闰年出生日期的合法性正则表达式
				var reg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;
			} else {
				// 平年出生日期的合法性正则表达式
				var reg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;
			}
			if (reg.test(o)) {
				S = (parseInt(a[0]) + parseInt(a[10])) * 7
						+ (parseInt(a[1]) + parseInt(a[11])) * 9
						+ (parseInt(a[2]) + parseInt(a[12])) * 10
						+ (parseInt(a[3]) + parseInt(a[13])) * 5
						+ (parseInt(a[4]) + parseInt(a[14])) * 8
						+ (parseInt(a[5]) + parseInt(a[15])) * 4
						+ (parseInt(a[6]) + parseInt(a[16])) * 2
						+ parseInt(a[7]) * 1 + parseInt(a[8]) * 6
						+ parseInt(a[9]) * 3;
				Y = S % 11;
				M = "F";
				JYM = "10X98765432";
				M = JYM.substr(Y, 1);
				if (M == a[17]) {
					// 18位身份证验证通过
					return true;
				} else {
					// alert(Errors[3]);
					return false;
				}
			} else {
				// alert(Errors[2]);
				return false;
			}
		default :
			return false;
	}
}

String.prototype.isIllegal = function() {
	// 先直接return true
	if (1 == 1)
		return true;
	var o = this.trim();
	var pattern1 = /^[\u0391-\uFFE5\w]+$/;
	if (pattern1.test(o)) {
		return true;
	} else {
		return false;
	}

};

/**
 * 验证是否不包含中文和全角字符
 */
//
String.prototype.noFullWidthStr = function() {
	var o = this.trim();
	for (i = 0; i < o.length; i++) {
		var c = o.substr(i, 1);
		var ts = escape(c);
		if (ts.substring(0, 2) == "%u") {
			return false;
		}
	}
	return true;
};
/**
 * 计算字符串长度，中文等双字节文字算2个字符
 */
//
String.prototype.count = function() {
	return this.replace(/[^\x00-\xff]/g, "mm").length;
};
/**
 * 将字符串转成xml格式
 */
String.prototype.parseXML = function() {
	var s = this.trim();
	s = s.replace(/(^[\r\n\t ]+)|([\r\n\t ]+$)/gmi, "")
	if (window.ActiveXObject) {// ie
		var doc = new ActiveXObject("Microsoft.XMLDOM");
		doc.async = "false";
		doc.loadXML(s);
	} else {// firefox
		var parser = new DOMParser();
		var doc = parser.parseFromString(s, "text/xml");
	}
	return doc;
};
/**
 * 扩展Object类，实现的是使一个Object bean对象转换为字符串形式，如：{a:1,b:"av"}
 */
//
// -----------------------------------------------------------------------------
Date.prototype.dateFormat = function(format, obj) {
	if (!format) { // the default date format to use - can be customized to the
					// current locale
		format = 'Y-m-d';
	}
	LZ = function(x) {
		return (x < 0 || x > 9 ? '' : '0') + x
	};
	var MONTH_NAMES = new Array('一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月',
			'九月', '十月', '十一月', '十二月', '一', '二', '三', '四', '五', '六', '日', '八',
			'九', '十', '十一', '十二');
	var DAY_NAMES = new Array('星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六',
			'日', '一', '二', '三', '四', '五', '六');
	var result = "";
	var i_format = 0;
	var c = "";
	var token = "";
	// if(obj)alert(obj.displayYears+":"+obj.displayMonths+":"+obj.displayDates);
	var y = (obj && obj.displayYears) ? obj.displayYears : (this.getFullYear()
			.toString());
	var M = (obj && obj.displayMonths)
			? obj.displayMonths
			: (this.getMonth() + 1);
	var d = (obj && obj.displayDates) ? obj.displayDates : this.getDate();
	var E = this.getDay();
	var H = (obj && obj.displayHour >= 0) ? obj.displayHour : this.getHours();
	var m = (obj && obj.displayMinute >= 0) ? obj.displayMinute : this
			.getMinutes();
	var s = (obj && obj.displaySecond >= 0) ? obj.displaySecond : this
			.getSeconds();
	var value = {
		Y : y,
		y : y.substring(2),
		n : M,
		m : LZ(M),
		F : MONTH_NAMES[M - 1],
		M : MONTH_NAMES[M + 11],
		j : d,
		d : LZ(d),
		D : DAY_NAMES[E + 7],
		l : DAY_NAMES[E],
		G : H,
		H : LZ(H),
		hh : LZ(H),
		mm : LZ(m),
		ss : LZ(s)
	};
	if (H == 0) {
		value['g'] = 12;
	} else if (H > 12) {
		value['g'] = H - 12;
	} else {
		value['g'] = H;
	}
	value['h'] = LZ(value['g']);
	if (H > 11) {
		value['a'] = 'pm';
		value['A'] = 'PM';
	} else {
		value['a'] = 'am';
		value['A'] = 'AM';
	}
	value['i'] = LZ(m);
	value['s'] = LZ(s);
	// construct the result string
	while (i_format < format.length) {
		c = format.charAt(i_format);
		token = "";
		while ((format.charAt(i_format) == c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
		}
		if (value[token] != null) {
			result = result + value[token];
		} else {
			result = result + token;
		}
	}

	return result;
};
/**
 * 为object内的对象设值
 */
var setProperty = function(name, value, object) {
	if (name.indexOf(".") > -1) {
		field = name.substring(0, name.indexOf("."));
		var subo = object[field];
		if (!subo) {
			subo = {};
			object[field] = subo;
		}
		setProperty(name.substring(name.indexOf(".") + 1), value, subo);
	} else {
		object[name] = value;
	}
};
var object2String = function(o) {
	var str = "";
	for (var key in o) {
		if (key == "toJSONString")
			continue;
		if (!o[key] || o[key] == true) {
			str += "," + key + ":" + o[key];
		} else if (typeof o[key] == "number") {
			str += "," + key + ":" + o[key] + "";
		} else if (typeof o[key] == "string") {
			str += "," + key + ":\"" + o[key] + "\"";
		} else if (typeof o[key] == "function") {
			str += "," + key + ":" + o[key].toString();
		} else if (typeof o[key] == "object") {
			if (o[key].constructor == Array) {
				str += "," + key + ":" + array2String(o[key]);
			} else if (o[key].constructor == Object) {
				str += "," + key + ":" + object2String(o[key]);
			}
		}

	}
	return "{" + str.substring(1) + "}";
};
/**
 * 扩展Array类，使一个Array对象转换为JSON字符串形式，如：[1,2,3]
 */
var array2String = function() {
	var str = "";
	for (var key = 0; key < this.length; key++) {
		if (typeof this[key] == "number") {
			str += "," + this[key];
		} else if (typeof this[key] == "string") {
			str += ",\"" + this[key] + "\"";
		} else if (!this[key] || this[key] == true) {
			str += "," + this[key];
		} else if (typeof this[key] == "function") {
			str += "," + this[key].toString();
		} else if (typeof this[key] == "object") {
			if (this[key].constructor == Array) {
				str += "," + array2String(this[key]);
			} else if (this[key].constructor == Object) {
				str += "," + object2String(this[key]);
			}
		}

	}
	return "[" + str.substring(1) + "]";
};

/**
 * 日期对比方法
 * 
 * @param:匿名 type:object
 *           samples:DateValidator({value:$("id1").value,title:"开始时间"},{value:$("id2").value,title:"结束时间"});
 */
var DateValidator = function() {
	var d1 = arguments[0];
	var d2 = arguments[1];
	// 如果日期输入不完整则不进行日期对比
	if (d1.value.trim() == "" || d2.value.trim() == "") {
		// alert();
		return true;
	}
	// 如果日期2早于日期2
	if (d1.value.trim().replace(/\D/g, '').toNumber() > d2.value.trim()
			.replace(/\D/g, '').toNumber()) {
		alert("\"" + d2.title + "\"不能早于\"" + d1.title + "\"");
		return false;
	} else {
		return true;
	}
};
/**
 * import方法，实现js代码随需下载
 */
var Import = function(url, fn) {
	var name = url.replace(/.*\./gi, "");
	url = WWWROOT + "/" + (url.replace(/\./g, "/")) + ".js";// +(new
															// Date().getTime());
	// 模块已存在
	if (window[name]) {
		return;
	}

	var modal = new AjaxModal();
	modal.url = url;
	var xhr = IO.common.get(modal);
	// function(){
	try {
		window.execScript(xhr.responseText);
	} catch (ex) {
		window.eval(xhr.responseText);
	}
	// }();
	if (fn)
		fn();
};

/**
 * ie js错误自动打印功能，如果浏览器有其他调试工具，则不调用该功能
 */
var AutoLog = function() {
	// 非ie勿进
	if (!window.attachEvent) {
		return;
	}
	// 错误日志打印功能
	window.attachEvent("onerror", function(msg, url, l) {
				var date = new Date();
				var obj = {};
				obj.e = msg;
				obj.u = url;
				obj.l = l;
				obj.t = date.getHours() + ":" + date.getMinutes() + ":"
						+ date.getSeconds();
				try {
					obj.p = arguments.callee.caller.toString();
				} catch (e) {
					obj.p = "未能捕捉到程序片段。";
				}
				if (top.maywide) {
					try {
						top.maywide.log.add(obj);
					} catch (e) {
						top.maywide.log();// 加载
						top.maywide.log.add(obj);
					}
				}
				date = obj = null;
			});

	// 为系统添加打印及清空日志的快捷键
	document.attachEvent("onkeyup", function(evt) {
				var evt = evt || window.event;
				if (evt.ctrlKey && evt.keyCode == 85) {// ctrl+u
					try {
						top.maywide.log.print();
					} catch (e) {
					}
				} else if (evt.ctrlKey && evt.keyCode == 46) {// ctrl+del
					try {
						top.maywide.log.clear();
					} catch (e) {
					}
				}
			});
}();