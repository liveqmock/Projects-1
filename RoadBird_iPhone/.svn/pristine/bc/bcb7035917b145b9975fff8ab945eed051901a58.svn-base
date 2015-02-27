//
//  PhoneUtil.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-19.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "PhoneUtil.h"

@implementation PhoneUtil

// 拨打电话
+(void)makeCall:(NSString *)phone
{
    if([NSString isNotEmpty:phone])
    {
         [[UIApplication sharedApplication] openURL:[NSURL URLWithString:[NSString stringWithFormat:@"tel://%@",phone]]];
    }
    else{
       
    }
}

@end
