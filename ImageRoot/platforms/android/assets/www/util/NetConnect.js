var NetConnect = {
	
    getServer:function(args,serverName,successCallBack,failCallBack)
    {
        
    
        $.ajax({ //一个Ajax过程
               type: "get", //以post方式与后台沟通
//              url : "http://183.62.56.27:4011/"+serverName, //测试url
               url : "http://119.146.239.82:8082/"+serverName, //实际环境url
                data: args,
               timeout:300000,
               dataType:'json',//从php返回的值以 JSON方式 解释
               success: successCallBack,
               error:failCallBack
               
               });
        
        
    },
    
	upload : function (args, image) {
		
	},
	
	download : function (args) {
		
	}
    

};

//显示加载器
function showLoader(message) {
   
    console.log("loading 进来");
    
    //显示加载器.for jQuery Mobile 1.2.0
    $.mobile.loading('show', {
                     text: message, //加载器中显示的文字
                     textVisible: true, //是否显示文字
                     theme: 'a',        //加载器主题样式a-e
                     textonly: false,   //是否只显示文字
                     html: ""           //要显示的html内容，如图片等
                     });
}

//隐藏加载器.for jQuery Mobile 1.2.0
function hideLoader()  
{  
    //隐藏加载器  
    $.mobile.loading('hide');  
}



function LTLoadStart(message)
{
    if(!message)
    {
        message ="加载中……";
    }
    var _width = window.innerWidth;
    var _height = window.innerHeight;
    var htmlstr = '<div  id="loadingdiv" style="width:'+_width+'px;height:'+_height+'px;position:fixed;top:0px;left:0px;opacity:0.1;z-index:99999" class="loader-bg"></div>';
    $("body").append(htmlstr);
    
    $.mobile.loading("show",{text: message, textVisible: true });


}
function LTStopLoading()
{
    

    $("#loadingdiv").remove();
    $(".loader-bg").remove();
    $.mobile.loading('hide');
    //$.mobile.loading("hide",{text: message, textVisible: true });
    
}

function loadStart(message,str,flag)
{
    $.mobile.loading('show', {
                     text: message, //加载器中显示的文字
                     textVisible: true, //是否显示文字
                     theme: 'a',        //加载器主题样式a-e
                     textonly: false,   //是否只显示文字
                     html: ""           //要显示的html内容，如图片等
                     });


}

//结束loading组件
function loadStop(){
    //隐藏加载器
    $.mobile.loading('hide');
}

/*
//打开loading组件
//text(string): 加载提示文字
//str(string): load的背景颜色样式(取值:a,b,c,d)
//flag(boolean): 背景是否透明(取值:true透明,false不透明)//源码来源 http://www.cnblogs.com/wshiqtb/p/3477145.html
function loadStart(text,str,flag){
        if(!text){
              text = "加载中...";
             }
         $(".ui-loader h1").html(text);
        var _width = window.innerWidth;
        var _height = window.innerHeight;
        var htmlstr = '<div style="width:'+_width+'px;height:'+_height+'px;position:fixed;top:0px;left:0px;opacity:0.1;z-index:99999" class="loader-bg"></div>';
         $("body").append(htmlstr);
        if(flag){
                 $(".ui-loader").removeClass("ui-loader-verbose").addClass("ui-loader-default");
            }
         else{
               $(".ui-loader").removeClass("ui-loader-default").addClass("ui-loader-verbose");
             }
         var cla = "ui-body-"+str;
         $("html").addClass("ui-loading");
         var arr = $(".ui-loader").attr("class").split(" ");
        var reg = /ui-body-[a-f]/;
        for(var i in arr){
                 if(reg.test(arr[i])){
                         $(".ui-loader").removeClass(arr[i]);
                     }
             }
        $(".ui-loader").addClass(cla);
     }
 //结束loading组件
 function loadStop(){
         $("html").removeClass("ui-loading");
         $(".loader-bg").remove();
    }
*/
//数据缓存
var TempCache = {

 setItem:function(key,value){
     
     window.localStorage.setItem(key, value);
  
 },
 getItem:function(key){
     
    
   return window.localStorage.getItem(key);
     
    
 },
removeItem:function(key){
    window.localStorage.removeItem(key);
},

    
 
clearAllItems:function (){
        window.localStorage.clear();
    },
    //测试更新方法
updateItem: function (key,value){
        window.localStorage.removeItem(key);
        window.localStorage.setItem(key, value);
    }
    
    
};


///参数设置：
/*
 scaling 是否等比例自动缩放
 width 图片最大高
 height 图片最大宽
 loadpic 加载中的图片路径
 */
jQuery.fn.LoadImage=function(scaling,width,height,loadpic){
    if(loadpic==null)loadpic="../img/login.png";
    return this.each(function(){
                     var curImg=$(this);
                     var src=$(this).attr("src")
                     var img=new Image();
                     //alert("Loading")
                     img.src=src;
                     //自动缩放图片
                     var autoScaling=function(){
                     if(scaling){
                     
                     if(img.width>0 && img.height>0)
                     {
                        if(img.width/img.height>=width/height){
                            if(img.width>width)
                            {
                            curImg.width(width);
                            curImg.height((img.height*width)/img.width);
                            }
                            else
                            {
                            curImg.width(img.width);
                            curImg.height(img.height);
                            }
                     
                        }
                        else
                        {
                            if(img.height>height)
                            {
                            curImg.height(height);
                            curImg.width((img.width*height)/img.height);
                            }
                            else
                            {
                            curImg.width(img.width);
                            curImg.height(img.height);
                            }
                        }
                     }
                     }
                     }
                     //处理ff下会自动读取缓存图片
                     if(img.complete){
                     //alert("getToCache!");
                     autoScaling();
                     return;
                     }
                     $(this).attr("src","");
                     var loading=$("<img alt=\"加载中\" title=\"图片加载中\" src=\""+loadpic+"\" />");
                     
                     curImg.hide();
                     curImg.after(loading);
                     $(img).load(function(){
                                 autoScaling();
                                 loading.remove();
                                 curImg.attr("src",this.src);
                                 curImg.show();
                                 //alert("finally!")
                                 });
                     
                     });
}
var adjustImgSize = function(img, boxWidth, boxHeight) {
    

    // 上面这种取得img的宽度和高度的做法有点儿bug
    // src如果由两个大小不一样的图片相互替换时，上面的写法就有问题了，换过之后的图片的宽度和高度始终取得的还是换之前图片的值。
    // 解决方法：创建一个新的图片类，并将其src属性设上。
    var tempImg = new Image();
    tempImg.src = img.attr('src');
    var imgWidth=tempImg.width;
    var imgHeight=tempImg.height;
    if(imgWidth == 0)
    {
        img.parent().css({"width":250,"height":220,"margin":'0 auto'}); //div 的大小重置
    }
    
    
    
    //比较imgBox的长宽比与img的长宽比大小
    //boxWidth/boxHeight ＝ 1
    if((boxWidth/boxHeight)>=(imgWidth/imgHeight))
    {
        //重新设置img的width和height
        img.width((boxHeight*imgWidth)/imgHeight);
        img.height(boxHeight);
        //让图片居中显示
        var margin=(boxWidth-img.width())/2;
        //img.css("margin-left",margin);
        
        img.css({"margin-left":margin,"margin-top":0});
        img.parent().css({"width":boxWidth,"height":boxHeight,"margin":'0 auto'}); //div 的大小重置
       
        
        
    }
    else
    {
        
        //图片宽度大于图片高度时
        //重新设置img的width和height
        img.width(boxWidth);
        img.height((boxWidth*imgHeight)/imgWidth);
        //让图片居中显示
        var margin=(boxHeight-img.height())/2;
        
        
        
        //console.log("上下的居中位置:"+margin);
        
        img.css({"margin-left":0});
        
        
        console.log("img.height:"+(boxWidth*imgHeight)/imgWidth); //div 的高度缩小 div 的大小重置
        
    
        
        
        img.parent().css({"width":boxWidth,"height":(boxWidth*imgHeight)/imgWidth,"margin":'0 auto'});
        
        // img.css("margin-top",margin);
    }
    
    boxWidth = 250;
    boxHeight = 250;
    
    
};

function checkAppConnection() {
    
    var networkState = navigator.connection.type;
    
    var states = {};
    states[Connection.UNKNOWN]  = 'Unknown connection';
    states[Connection.ETHERNET] = 'Ethernet connection';
    states[Connection.WIFI]     = 'WiFi connection';
    states[Connection.CELL_2G]  = 'Cell 2G connection';
    states[Connection.CELL_3G]  = 'Cell 3G connection';
    states[Connection.CELL_4G]  = 'Cell 4G connection';
    states[Connection.CELL]     = 'Cell generic connection';
    states[Connection.NONE]     = 'No network connection';
    
    
    if(states[networkState]=='No network connection')
    {
        return false;
    }
    else
    {
        return true;
    }
    
    
   
}

function getCurDeviceIsIOSPlatform()
{
    var myDeviceOS = device.platform;
    console.log("当前系统：" + myDeviceOS);
    
    if ("Android" == myDeviceOS)
    {
        return false;
        
    }
    else
    {
        return true;
    }
}



/**
 * 时间对象的格式化
 */
Date.prototype.format = function(format)
{
    /*
     * format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    }
    
    if (/(y+)/.test(format))
    {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
                                                                            - RegExp.$1.length));
    }
    
    for (var k in o)
    {
        if (new RegExp("(" + k + ")").test(format))
        {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                                    ? o[k]
                                    : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

/**
 * 时间对象的格式化
 */
Date.prototype.format = function(format)
{
    /*
     * format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    }
    
    if (/(y+)/.test(format))
    {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
                                                                            - RegExp.$1.length));
    }
    
    for (var k in o)
    {
        if (new RegExp("(" + k + ")").test(format))
        {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                                    ? o[k]
                                    : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

/**
 * 类似android的toast提示框，usage: toast('hello word!!', 1000);
 * @param msg 显示的消息
 * @param time 滞留的时间，默认1000毫秒
 */
var toast = function (msg, time) {
    $("<div class='ui-loader ui-overlay-shadow ui-body-e ui-corner-all'><h3>"
      + msg + "</h3></div>")
    .css({
         display: "block",
         opacity: 0.90,
         position: "fixed",
         padding: "7px",
         "text-align": "center",
         background: '#555',
         color: '#fff',
         width: "270px",
         left: ($(window).width() - 284) / 2,
         top: $(window).height() / 2
         })
    .appendTo($.mobile.pageContainer).delay(time ? time : 1000)
    .fadeOut(400,
             function () {
             $(this).remove();
             }
             );
};

