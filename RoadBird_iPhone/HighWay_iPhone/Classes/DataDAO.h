//
//  DataDAO.h
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/9.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface DataDAO : NSObject

//+ (DataDAO *)sharedDataDAO;


//查询道路交通事件信息

+(void)getTrafficinfo:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;

// 查询规划路径方案
+(void)getRoadPlan:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;

// 查询规划路径方案
+(void)getRoadPlanDetail:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;

//根据经纬度查询附近的高速路段信息
+(void)getNearroads:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;

//按路段查询道路的车速
+(void)getRoadSpeed:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;


//报料
+(void)postReport:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;
// 获得用户分享的高速资讯
+(void)getUserShareInfo:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;


// 普通用户注册
+(void)postNormalRegister:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;
// 企业用户注册
+(void)postCompanyRegister:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;


// 用户登陆
+(void)getNormalLogin:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;
// 第三方用户登录
+(void)getThirdPartLogin:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;
// 修改用户信息
+(void)postChangeUserInfo:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;

// 修改用户信息（图片上传）
+(void)postChangeUserImageInfo:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;

//获取总积分
+(void)getQueryScore:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock;
@end
