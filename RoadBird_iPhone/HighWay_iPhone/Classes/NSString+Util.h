//
//  NSString+Util.h
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-15.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString (Util)

+(BOOL)isEmpty:(NSString *)s;

+(BOOL)isNotEmpty:(NSString *)s;

-(int)indexOf:(NSString*)text state:(BOOL)isFirst;

@end
