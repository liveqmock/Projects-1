
1 在command中，进入工程目录下，用cordova 命令添加IOS工程 :cordova platform add ios
请先把/ImageSys/plugins/QBImagePicker文件夹剪切到文件夹中，然后再调用cordova platform add ios

2 用cordova 命令生成IOS Project:cordova build ios

3.在config.xml 配置文件加入自定义的插件配置

    <feature name="PickImgPlugin">
        <param name="ios-package" value="PickImgPlugin" />
    </feature>
    <feature name="TakePicPlugin">
        <param name="ios-package" value="TakePicPlugin" />
    </feature>
    <feature name="LTUploadPlugin">
        <param name="ios-package" value="LTUploadPlugin" />
    </feature>
    <feature name="CameraPlugin">
        <param name="ios-package" value="CameraPlugin" />
    </feature>

4 在config.xml 配置文件修改默认配置
<preference name="DisallowOverscroll" value="true" /> 将默认可以整个页面拖动的，设置为不可以true

5 工程添加照片多选第三库QBImagePicker

6 在other Source文件夹下的 ImageSys-Prefix.pch 文件里面添加代码 #define isIOS7	( [[[UIDevice currentDevice] systemVersion] compare:@"7.0"] != NSOrderedAscending )

7 图标更改是在Resources 文件夹下的icons 文件夹里面，分别替换 icon-72@2x.png icon-72.png icon@2x.png icon.png，图片名不能更改

8 服务器地址修改：为util 文件下的NetConnect.js 文件里面的getServer 方法里面。

9 自定义插件方法对应的js文件名：PickImgPlugin.h TakePicPlugin.h 对应LTCamera.js 里面的pickMultiplePhotos 和nativeTakePicture 方法 LTUploadPlugin.h 对应LTUploadPlugin.js

10 启动动画 在Resources 下的splash文件下修改 Default-568h@2x~iphone.png Default@2x~iphone.png Default~iphone.png  存储ImageSys/www/res/IOS 目录下

11 在MainViewController.m中webViewDidFinishLoad方法后面增加


//IOS 7 之后状态榄的适配
- (void)viewDidLayoutSubviews{
    
    if ([self respondsToSelector:@selector(topLayoutGuide)]) // iOS 7 or above
    {
        CGFloat top = self.topLayoutGuide.length;
        if(self.webView.frame.origin.y == 0){
            // We only want to do this once, or if the view has somehow been "restored" by other code.
            self.webView.frame = CGRectMake(self.webView.frame.origin.x, self.webView.frame.origin.y + top, self.webView.frame.size.width, self.webView.frame.size.height - top);
        }
    }
}


