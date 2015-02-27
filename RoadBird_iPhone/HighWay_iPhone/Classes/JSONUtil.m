//
//  JSONUtil.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-30.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "JSONUtil.h"

@implementation JSONUtil

// 读取本地JSON数据

+(id)readJSON:(NSString *)fileName
{
    
//    NSFileManager *fileManager = [NSFileManager defaultManager];
////    NSString *documentDir = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
//    NSString *documentDir =[NSBundle mainBundle].bundlePath;
//    
//    NSError *error = nil;
//    NSArray *fileList = [[NSArray alloc] init];
//    
//    //fileList便是包含有该文件夹下所有文件的文件名及文件夹名的数组
//    fileList = [fileManager contentsOfDirectoryAtPath:documentDir error:&error];
//    
//    //以下这段代码则可以列出给定一个文件夹里的所有子文件夹名
//    
//    NSMutableArray *dirArray = [[NSMutableArray alloc] init];
//    NSMutableArray *fileArray = [[NSMutableArray alloc] init];
//    BOOL isDir = NO;
//    //在上面那段程序中获得的fileList中列出文件夹名
//    for (NSString *file in fileList) {
//        NSString *path = [documentDir stringByAppendingPathComponent:file];
//        [fileManager fileExistsAtPath:path isDirectory:(&isDir)];
//        if (isDir) {
//            [dirArray addObject:file];
//        }else{
//            [fileArray addObject:file];
//        }
//        isDir = NO;
//    }
//    NSLog(@"文件夹下面的所有内容:%@",fileList);
//    NSLog(@"所有文件夹:%@",dirArray);
//    NSLog(@"所有文件:%@",fileArray);
    
    
    
    NSData *data=nil;
    @try {
        NSString *filePath = [[NSBundle mainBundle] pathForResource:fileName ofType:nil];
        data = [NSData dataWithContentsOfFile:filePath];
//        NSString *string = [NSString stringWithContentsOfFile:filePath encoding:NSUTF8StringEncoding error:nil];
//        data = [string dataUsingEncoding:NSUTF8StringEncoding];
        return  [NSJSONSerialization JSONObjectWithData:data options:0 error:nil];
        
    }
    @catch (NSException *exception) {
        
        return nil;
    }
    
    
    
   
 
   
}

@end
