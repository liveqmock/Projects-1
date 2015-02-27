//
//  AppDelegate.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-5.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "AppDelegate.h"
#import "MobClick.h"
#import "MapUtil.h"
#import "ChinaWeatherUtil.h"
#import "BNCoreServices.h"
#import "BNaviSoundManager.h"

#import <ShareSDK/ShareSDK.h>
#import "WXApi.h"
#import "WeiboSDK.h"
#import <TencentOpenAPI/QQApi.h>
#import <TencentOpenAPI/TencentOAuth.h>
#import <TencentOpenAPI/QQApiInterface.h>


@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // 创建Sqlite数据库
    [SqliteUtil initSqlite];
    
    //0、引导页信息
    if ([CommonData sharedCommonData].isFirstOpenApp) { //第一次打开应用默认开引导
        [[SqliteUtil sharedSqliteUtil] insertInfo:@"DirvingRouteDetailVC-Index" state:@"show"];
        [[SqliteUtil sharedSqliteUtil] insertInfo:@"JourneyPlanning-Index" state:@"show"];
        [[SqliteUtil sharedSqliteUtil] insertInfo:@"More-Index" state:@"show"];
        [[SqliteUtil sharedSqliteUtil] insertInfo:@"RealTimeTraffic-Index" state:@"show"];
        [[SqliteUtil sharedSqliteUtil] insertInfo:@"SelStation-Index" state:@"show"];
    }
    
    // 1、初始化基础数据
    [[CommonData sharedCommonData] loadData];
    
    
    // 2、 初始化百度地图
    _mapManager = [[BMKMapManager alloc] init];
    BOOL ret = [_mapManager start:@"3pxRc8BdRTddXPaI5CktLIGv" generalDelegate:nil];
    if (!ret) {
        NSLog(@"manager start failed!");
    }
    else{
        NSLog(@"初始化百度地图成功！");
    }
    
    // 初始化导航SDK引擎
    [BNCoreServices_Instance initServices:@"3pxRc8BdRTddXPaI5CktLIGv"];
    //开启引擎，传入默认的TTS类
    // iPhone4s不能初始化
    [BNCoreServices_Instance startServicesAsyn:nil fail:nil SoundService:[BNaviSoundManager getInstance]];
    
    //3、初始化界面
    [self initViewControllers];
    
    //4、 初始化友盟统计(包括错误统计）
    [MobClick startWithAppkey:@"533d38c656240b727e04bb03" reportPolicy:SENDDAILY  channelId:nil];
    
    //5、检查网络是否存在
    [[NSNotificationCenter defaultCenter] addObserver: self selector: @selector(reachabilityChanged:) name: kReachabilityChangedNotification object: nil];
    hostReach = [Reachability reachabilityForInternetConnection] ;
	[hostReach startNotifier];
    [self updateInterfaceWithReachability:hostReach];
    
    //6、检查用户是否打开了定位服务GPS
    if ([CLLocationManager locationServicesEnabled])
    {
        [CommonData sharedCommonData].isOpenGPS = YES;
        NSLog(@"用户打开了GPS");
    }
    else
    {
        [CommonData sharedCommonData].isOpenGPS = NO;
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"温馨提示" message:@"请在系统设置中打开“定位服务”来允许确认您的位置" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil];
        [alert show];
    }
    
    //7、shareSDK（第三方登录）
    [self initShareSDK];
    
    //8、启动页延时淡出
    [self LoadingViewFade];
    
//    //9、第一期不实现推送功能： 推送注册
//	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(launchNotification:) name:@"UIApplicationDidFinishLaunchingNotification" object:nil];
//    [[UIApplication sharedApplication] registerForRemoteNotificationTypes:
//     UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeAlert | UIRemoteNotificationTypeSound];
    //	if(StrIsNull([CommonData Instance].tokenId)){
    //		NSLog(@"get tokenid from local fail.");
    //    }else {
    //		NSLog(@"%@", [CommonData Instance].tokenId);
    //	}
    
    
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
    [BMKMapView willBackGround];
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
    [BMKMapView didForeGround];
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    
    if ([CLLocationManager locationServicesEnabled]) //用户已开GPS
    {
        if (![CommonData sharedCommonData].isOpenGPS) { //用户由未开GPS到已开GPS，重新初始化window
            self.window = nil;
            NSLog(@"用户打开了GPS");
            [self initViewControllers];
        }
       
    }
    else //提示用户开启定位服务
    {
        [CommonData sharedCommonData].isOpenGPS = NO;
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"温馨提示" message:@"请在系统设置中打开“定位服务”来允许确认您的位置" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil];
        [alert show];
        
    }

}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}


#pragma mark -
#pragma mark remote notification Methods

// Retrieve the device token
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{
	NSString *tokenId=[[NSString alloc] initWithFormat:@"%@", deviceToken];
    [CommonData sharedCommonData].tokenId=tokenId;
    
    // 去掉尖括号
	if ([[CommonData sharedCommonData].tokenId length]>2) {
		
    }
	NSLog(@"deviceToken: %@",[CommonData sharedCommonData].tokenId);
}

// Provide a user explanation for when the registration fails
- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{
	NSString *status = [NSString stringWithFormat:@"Registration failed.\n\nError: %@", [error localizedDescription]];
    NSLog(@"APNS registration fail. Error: %@", status);
}

//receive notification
- (void) launchNotification: (NSNotification *) notification
{
	NSLog(@"launchNotification:%@",[[notification userInfo] description]);
}

// Handle an actual notification
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
	NSString *s=[NSString stringWithFormat: @"Notification received:\n%@",[userInfo description]];
    NSLog(@"%@",s);
}


//Called by Reachability whenever status changes.
- (void) reachabilityChanged: (NSNotification* )note
{
	Reachability* curReach = [note object];
	NSParameterAssert([curReach isKindOfClass: [Reachability class]]);
    [self updateInterfaceWithReachability:curReach];
    
}

- (void) updateInterfaceWithReachability: (Reachability*) curReach
{
    BOOL isExistenceNetwork;
    switch ([curReach currentReachabilityStatus]) {
        case NotReachable:
			isExistenceNetwork=NO;
            NSLog(@"无网络连接");
            break;
        case ReachableViaWWAN:
			isExistenceNetwork=YES;
            NSLog(@"使用3G/GPRS网络连接");
            break;
        case ReachableViaWiFi:
			isExistenceNetwork=YES;
            NSLog(@"使用WiFi网络连接");
            break;
    }
    [CommonData sharedCommonData].isNetworkRunning=isExistenceNetwork;
}




-(void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation {
//    [CommonData Instance].currentLocationCoordinate2D=newLocation.coordinate;
//    [MapUtil getCityByCLLocationCoordinate2D:newLocation.coordinate completedBlock:^(NSString *city) {
//        [CommonData Instance].currentCity=city;
//        NSLog(@"当前城市：%@",city);
//        ChinaWeatherUtil *weatherUtil=[[ChinaWeatherUtil alloc] init];
//        [weatherUtil getWeatherData:city];
//        
//    }];
//
//    [self.locationManager stopUpdatingHeading];
//    
    
}

#pragma mark - 第三方登录跳转
- (BOOL)application:(UIApplication *)application
      handleOpenURL:(NSURL *)url
{
    return [ShareSDK handleOpenURL:url
                        wxDelegate:self];
}

- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation
{
    return [ShareSDK handleOpenURL:url
                 sourceApplication:sourceApplication
                        annotation:annotation
                        wxDelegate:self];
}

#pragma mark - custem method
-(void) initViewControllers
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    self.window.backgroundColor = [UIColor whiteColor];
    
    _mainVC =[[MainVC alloc] init];
    _nav = [[UINavigationController alloc] initWithRootViewController:self.mainVC];
    
    self.window.rootViewController=_nav;
    [self.window makeKeyAndVisible];
    
    [CommonData sharedCommonData].isFirstOpenApp = NO; //默认不是第一次开APP
}

//启动页延时淡出
-(void)LoadingViewFade
{
    UIImageView *imageView = [[UIImageView alloc] init];
    imageView.frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    if (ISIPHONE4S) {
        imageView.image = [UIImage imageNamed:@"Default"];
    }else if(SCREEN_HEIGHT == 568){
        imageView.image = [UIImage imageNamed:@"Default-4"];
    }else if(SCREEN_HEIGHT == 667){
        imageView.image = [UIImage imageNamed:@"Default-4.7"];
    }else{
        imageView.image = [UIImage imageNamed:@"Default-5.5"];
    }
    [self.window addSubview:imageView];
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:1];
    [UIView setAnimationDelay:1];
    imageView.alpha=0;
    [UIView commitAnimations];
}

// 初始化第三方登录
-(void)initShareSDK{
    
    [ShareSDK registerApp:@"539a3ec64eb8"]; //ShareSDK的应用密钥
    
//    //微信
//    [ShareSDK connectWeChatWithAppId:@""
//                           appSecret:@""
//                           wechatCls:[WXApi class]];
    
    //新浪微博
    [ShareSDK connectSinaWeiboWithAppKey:@"2313703128"
                               appSecret:@"c94d7ba3445cba8e1d13799c3fda3b36"
                             redirectUri:@"http://www.sharesdk.cn"
                             weiboSDKCls:[WeiboSDK class]];
    //QQ
    [ShareSDK connectQQWithQZoneAppKey:@"1104160762"
                     qqApiInterfaceCls:[QQApiInterface class]
                       tencentOAuthCls:[TencentOAuth class]];
    //QQ空间
    [ShareSDK connectQZoneWithAppKey:@"1104160762"
                           appSecret:@"jr7FHb1AJyBVKLfn"
                   qqApiInterfaceCls:[QQApiInterface class]
                     tencentOAuthCls:[TencentOAuth class]];
    
}
@end
