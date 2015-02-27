function execute(action, successCallback, errorCallback, timeout) {
	var timer = null;
	if (timeout != undefined && !isNaN(timeout)) {
		timer = createTimeout(errorCallback, timeout);
	}
	cordova.exec(function(pos) {
		var errcode = pos.code;
		if (errcode == 61 || errcode == 65 || errcode == 161) {
			if (timer != undefined )
				clearTimeout(timer);
			successCallback(pos);
		} else {
			if ( typeof (errorCallback) != "undefined") {
				if (errcode >= 162) {
					errcode = 162;
				}
				errorCallback(pos);
			};
		}
	}, function(err) {
	}, "BaiduLocationPlugin", action, []);

}

function BDLocGetCurrentPosition(successCallback, errorCallback, timeout) {
	this.execute("getCurrentPosition", successCallback, errorCallback, timeout);
};

function BDLocStop(action, successCallback, errorCallback, timeout) {
	this.execute("stop", successCallback, errorCallback, timeout);
}

function createTimeout(errorCallback, timeout) {
	var t = setTimeout(function() {
		clearTimeout(t);
		t = null;
		errorCallback();
	}, timeout);
	return t;
}