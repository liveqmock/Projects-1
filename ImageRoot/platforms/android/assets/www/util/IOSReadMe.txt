1.用cordova 命令生成IOS Project:cordova build ios

2.在config.xml 配置文件加入自定义的插件配置

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

3 在config.xml 配置文件修改默认配置
<preference name="DisallowOverscroll" value="true" /> 将默认可以整个页面拖动的，设置为不可以true

