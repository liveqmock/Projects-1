var LTUploadPlugin = {
	
    createEvent: function(json, successCallback, errorCallback) {
    	console.log("LTUpload");
        cordova.exec( 
            successCallback, // success callback function 
            errorCallback, // error callback function 
            'LTUploadPlugin', // mapped to our native Java class called "CalendarPlugin" 
            'LTUploadImage', // with this action name 
            [json] 
        );
        
     }
};