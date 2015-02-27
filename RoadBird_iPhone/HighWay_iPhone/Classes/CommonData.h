//
//  CommonData.h
//  HighwayTollManage
//
//  Created by wanggp on 9/6/12.
//  Copyright (c) 2012 newsoft. All rights reserved.
//

#import <CoreLocation/CoreLocation.h>
#import "ChinaWeatherResult.h"
#import "RealTimeTrafficVC.h"

#define PageSize 20;

@class Person;
@interface CommonData : NSObject


//登录名，中文名
@property (strong, nonatomic) NSString *loginAcc,*loginName,*loginPwd;
// 记录密码
@property (unsafe_unretained, nonatomic) BOOL recordPW;

// 服务器地址
@property (strong, nonatomic) NSString *baseURL;

// 推送TokenId
@property (strong, nonatomic) NSString *tokenId;

//广告标示符（IDFA-identifierForIdentifier）
@property (nonatomic,strong) NSString *adId;

//是否具备网络链接
@property BOOL isNetworkRunning;

@property(nonatomic) CLLocationCoordinate2D currentLocationCoordinate2D;
@property (nonatomic,strong) NSString *currentCity;
@property (nonatomic,strong) ChinaWeatherResult *chinaWeatherResult;
@property (nonatomic,weak) RealTimeTrafficVC *realTimeTrafficVC; //设置代理用
@property (nonatomic,unsafe_unretained) BOOL isFirstOpenApp;
@property (nonatomic,unsafe_unretained) BOOL isLocated; //显示天气时，判断是否显示默认

@property (nonatomic,unsafe_unretained) BOOL isLogin; //判断是否已经登录

@property (strong, nonatomic) NSString *allScore;//总分数

@property (nonatomic,strong)Person *me;

@property (nonatomic) int HighwayLifeType;//高速生活类型


@property BOOL isOpenGPS;


+ (CommonData *)sharedCommonData;
- (void)loadData;
- (void)saveData;


@end
