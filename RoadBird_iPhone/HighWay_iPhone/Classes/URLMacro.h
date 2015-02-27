//
//  URLConfig.h
//  GST_iPhone
//  URL地址以及接口定义
//  Created by wanggp on 14-4-2.
//  Copyright (c) 2014年 com.lt. All rights reserved.
//




#define ChinaWeatherURL @"http://www.weather.com.cn/data/cityinfo/"

//#define BaseURL @"http://hps.gznewsoft.com:18080/hps_appsvc/service" //上线版 URL
#define BaseURL @"http://128.8.39.244:8080/hps_web/hps" //蔡枭 URL




//#define BaseURL @"http://128.8.38.140:8080/hps_appsvc/service" //测试



//HTTP头存储认证信息
#define XAPPToken @"AT123456789"


//查询道路交通事件信息
#define  trafficinfo @"trafficinfo"
// 查询规划路径方案
#define roadplan @"roadplan"
// 规划路径方案详情
#define roadplandetail @"roadplandetail"
//根据经纬度查询附近的高速路段信息
#define nearroads @"nearroads"
// 按路段查询道路车速
#define roadspeed @"roadspeed"

// 报料
#define report @"report/addReport"
// 高速资讯列表，获得用户分享
//#define usershareinfo @"report/usershareinfo"
#define usershareinfo @"report/usershareinfo"

// 普通用户注册
//#define normalregister @"normalregister"
#define normalregister @"auth/register"
// 企业用户注册
//#define companyregister @"companyregister"
#define companyregister @"auth/register"

// 普通登陆
#define normallogin @"auth/login"
//#define normallogin @"/auth/login?username=%@&password=%@"
// 第三方登陆
//#define thirdpartlogin @"thirdpartlogin"
#define thirdpartlogin @"auth/thirdPartLogin"
// 修改用户信息
//#define changeuserinfo @"changeuserinfo"
#define changeuserinfo @"auth/changeUserInfo"
//头像上传
#define changeuserimage @"/auth/changeUserImage"
// 修改用户密码
//#define changepassword @"changepassword"
#define changepassword @"auth/changePassWord"

// 查询总积分
//#define queryscore @"queryscore"
#define queryscore @"auth/queryScore"
// 查询积分明细事件
//#define queryscoreinfo @"queryscoreinfo"
#define queryscoreinfo @"userscore/queryScoreInfo"

// 查询积分商城列表
//#define goodsstore @"goodsstore"
#define goodsstore @"goodsstore"
// 购买商品
#define buygoods @"buygoods"
// 商品订单列表
#define ordergoods @"ordergoods"

// 高速生活列表
#define highwaylife @"highWayLife"
// 高速生活信息内容
#define highwaylifeinfo @"highWayLifeInfo"
// 发布高速生活信息
#define publishhighwaylife @"publishHighWayLife"




