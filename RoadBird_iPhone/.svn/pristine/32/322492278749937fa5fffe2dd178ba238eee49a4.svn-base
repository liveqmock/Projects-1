//
//  NSString+Util.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-15.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "NSString+Util.h"

@implementation NSString (Util)

+(BOOL)isEmpty:(NSString *)s
{
    BOOL ret = NO;
    if (s==nil||s==NULL||[@"" isEqualToString:s]||[@"null" isEqualToString:s]|| [s isKindOfClass:[NSNull class]]) {
        ret=YES;
    }
    
    return ret;
}

+(BOOL)isNotEmpty:(NSString *)s
{
    return  ![NSString isEmpty:s];
}


-(int)indexOf:(NSString*)text state:(BOOL)isFirst{
    
    if (isFirst) {
        if ([[self substringToIndex:1] isEqualToString:[text substringToIndex:1]])  {
            return 1;
        }
        else
        {
            return -1;
        }
        
        
    }else
    {
        NSRange range=[self rangeOfString:text];
        if(range.length>0){
            return range.location;
        }
        else{
            return -1;
        }
    }
    

}




@end
