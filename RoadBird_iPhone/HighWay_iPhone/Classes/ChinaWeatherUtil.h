//
//  ChinaWeatherUtil.h
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-8.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ChinaWeatherResult.h"

@interface ChinaWeatherUtil : NSObject

@property (nonatomic,strong) NSDictionary *weatherCodeDic;


- (void)getWeatherData:(NSString *)city success:(void(^)(ChinaWeatherResult *result))success failure:(void(^)(NSError *error))failure;

+(NSString *)getWeatherImage:(NSString *)weatherInfo;
@end
