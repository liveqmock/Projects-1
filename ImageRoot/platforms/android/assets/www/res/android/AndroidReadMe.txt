1，用Cordova命令行生成Project；
2，将www\util\camera\MultipleImagePick中的文件加入到Android project中；
3，将www\util\camera\CameraPlugin.java, LTCameraActivity.java, PickImgPlugin.java, TakePicPlugin.java 复制到com.ltkj.platform包中；
4，将www\util\upload\LTUploadPlugin.java复制到com.ltkj.platform包中；
     将www/util/baiduLocation/BaiduLocationPlugin.java复制到com.ltkj.platform包中
5，导入www\res\android\lib中的jar包；
6，删除Android project中的res\文件夹，并将www\res\android\res\文件夹复制到Android project中；（该步骤可以根据错误提示手工添加修改）
7，在AndroidManifest.xml中加入Activity配置：
        <activity android:name="com.luminous.pick.CustomGalleryActivity">
            <intent-filter>
                <action android:name="luminous.ACTION_PICK" />
                <action android:name="luminous.ACTION_MULTIPLE_PICK" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
	<activity
		android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale"
		android:theme="@android:style/Theme.Black.NoTitleBar"
            	android:name="LTCameraActivity">
        </activity>
8，在AndroidManifest.xml中加入权限配置：
	<uses-permission android:name="android.permission.CAMERA" />
	<uses-feature android:name="android.hardware.camera" />
 	<uses-feature android:name="android.hardware.camera.autofocus" />
 	
 	<!-- 这个权限用于进行网络定位-->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"></uses-permission>
<!-- 这个权限用于访问GPS定位-->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>
<!-- 用于访问wifi网络信息，wifi信息会用于进行网络定位-->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
<!-- 获取运营商信息，用于支持提供运营商信息相关的接口-->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
<!-- 这个权限用于获取wifi的获取权限，wifi信息会用来进行网络定位-->
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
<!-- 用于读取手机当前的状态-->
<uses-permission android:name="android.permission.READ_PHONE_STATE"></uses-permission>
<!-- 写入扩展存储，向扩展卡写入数据，用于写入离线定位数据-->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
<!-- 访问网络，网络定位需要上网-->
<uses-permission android:name="android.permission.INTERNET" />
<!—SD卡读取权限，用户写入离线定位数据-->
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"></uses-permission>
<!--允许应用读取低级别的系统日志文件 -->
<uses-permission android:name="android.permission.READ_LOGS"></uses-permission>
9，在AndroidManifest.xml的application中添加百度开发密钥： 
   <meta-data
            android:name="com.baidu.lbsapi.API_KEY"
            android:value="e0nymu9Tl2uGfLIvVG3d9jGa" />

  <service
            android:name="com.baidu.location.f"
            android:enabled="true"
            android:process=":remote" >
        </service>    
            
            
            

 