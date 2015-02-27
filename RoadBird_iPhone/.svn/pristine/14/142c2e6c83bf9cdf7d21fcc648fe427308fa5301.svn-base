//
//  FileUtil.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/18.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "FileUtil.h"

@implementation FileUtil

+(NSString *)documentFilePath:(NSString *)dir:(NSString *)fileName{
    
	//获取文件的document文件夹的路径.
	NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
	NSString *documentDiretory = [paths objectAtIndex:0];
	//追加一个路径
    if([NSString isNotEmpty:dir])
    {
        documentDiretory=[documentDiretory stringByAppendingString:[NSString stringWithFormat:@"/%@",dir]];
        
    }
	return [documentDiretory stringByAppendingPathComponent:fileName];
}

@end
