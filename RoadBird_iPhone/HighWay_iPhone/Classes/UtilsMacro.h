//
//  UtilsMacro.h
//  GST_iPhone
// 常用宏定义
//
//  Created by wanggp on 14-4-24.
//  Copyright (c) 2014年 com.lt. All rights reserved.
//

#ifndef GST_iPhone_UtilsMacro_h
#define GST_iPhone_UtilsMacro_h

// Color helpers
#define RGBCOLOR(r,g,b) [UIColor colorWithRed:(r)/255.0f green:(g)/255.0f blue:(b)/255.0f alpha:1]
#define RGBACOLOR(r,g,b,a) [UIColor colorWithRed:(r)/255.0f green:(g)/255.0f blue:(b)/255.0f \
alpha:(a)]
//清除背景色
#define CLEARCOLOR [UIColor clearColor]

// 导航条背景
#define NAVBACKGROUNDCOLOR RGBCOLOR(211.0f, 236.0f, 224.0f)
//绿色、导航字体颜色
#define NAV_GREEN_FONT_COLOR RGBCOLOR(17.0f,178.0f,100.0f)


//-------------------获取设备大小-------------------------
//NavBar高度
#define NavigationBar_HEIGHT 44
//获取屏幕 宽度、高度
#define SCREEN_WIDTH ([UIScreen mainScreen].bounds.size.width)
#define SCREEN_HEIGHT ([UIScreen mainScreen].bounds.size.height)
#define ISIPHONE4S SCREEN_HEIGHT == 480

//----------------------系统----------------------------


#define APP_Version [[NSBundle mainBundle] objectForInfoDictionaryKey:(NSString*)kCFBundleVersionKey]

//获取系统版本
#define IOS_VERSION [[[UIDevice currentDevice] systemVersion] floatValue]
#define CurrentSystemVersion [[UIDevice currentDevice] systemVersion]

//获取当前语言
#define CurrentLanguage ([[NSLocale preferredLanguages] objectAtIndex:0])


//判断是否 Retina屏、设备是否%fhone 5、是否是iPad
#define isRetina ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 960), [[UIScreen mainScreen] currentMode].size) : NO)
#define iPhone5 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size) : NO)
#define isPad (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad)


//判断是真机还是模拟器
#if TARGET_OS_IPHONE
//iPhone Device
#endif

#if TARGET_IPHONE_SIMULATOR
//iPhone Simulator
#endif


//检查系统版本
#define SYSTEM_VERSION_EQUAL_TO(v)                  ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] == NSOrderedSame)
#define SYSTEM_VERSION_GREATER_THAN(v)              ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] == NSOrderedDescending)
#define SYSTEM_VERSION_GREATER_THAN_OR_EQUAL_TO(v)  ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] != NSOrderedAscending)
#define SYSTEM_VERSION_LESS_THAN(v)                 ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] == NSOrderedAscending)
#define SYSTEM_VERSION_LESS_THAN_OR_EQUAL_TO(v)     ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] != NSOrderedDescending)

//----------------------系统----------------------------


//----------------------图片----------------------------

//读取本地图片
#define LOADIMAGE(file,ext) [UIImage imageWithContentsOfFile:[[NSBundle mainBundle]pathForResource:file ofType:ext]]

//定义UIImage对象
#define IMAGE(A) [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:A ofType:nil]]

//定义UIImage对象
#define ImageNamed(_pointer) [UIImage imageNamed:[UIUtil imageName:_pointer]]

//建议使用前两种宏定义,性能高于后者
//----------------------图片----------------------------


// 页面条数
#define PAGESIZE 25;

// -------------判断拥堵状况-------

// 顺畅
#define SpeedFast(v) v>60?YES:NO
// 缓慢
#define SpeedSlow(v) v<60&v>30?YES:NO


#define SUCCESSICON    @"checkmark.png"
#define FAILEDICON     @"tips_failed.png"

// 交通事故类别
typedef NS_OPTIONS(NSInteger, ReportType) {
    TrafficJam = 0,
    Accident = 1,
    Fix = 2,
    Control = 3,
    Water = 4,
    Landslide = 5
};

#endif
