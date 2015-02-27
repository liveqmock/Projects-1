//
//  NSDate+Util.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-15.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "NSDate+Util.h"

@implementation NSDate (Util)


+(NSString *)formateDate:(NSDate *)date fromate:(NSString *)fromateStr{
    NSDateFormatter  *dateformatter=[[NSDateFormatter alloc] init];
    [dateformatter setDateFormat:fromateStr];
    NSString *  dateStr=[dateformatter stringFromDate:date];
    return dateStr;
}

@end
