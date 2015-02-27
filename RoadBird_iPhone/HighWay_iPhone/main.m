//
//  main.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-5.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "AppDelegate.h"

int main(int argc, char * argv[])
{
    @try{
        @autoreleasepool {
            return UIApplicationMain(argc, argv, nil, NSStringFromClass([AppDelegate class]));
        }
    }
    @catch(NSException *exception) {
        NSLog(@"异常错误是:%@", exception);
    }

   
}
