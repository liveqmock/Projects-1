//
//  SqliteUtil.h
//  GaoSu_iPhone
//
//  Created by wanggp on 14-4-30.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SqliteUtil : NSObject


+ (SqliteUtil *)sharedSqliteUtil;

// 初始化数据库
+(void) initSqlite;

- (int)insertQueryPath:(NSDictionary *)path;
- (NSArray *)queryAllPathRecords;
- (BOOL)deletePathRecordById:(int)_id;
- (BOOL)deleteAllPathRecord;

// 根据roadId查询站
- (NSArray *)queryStationByRoadId:(NSString *)roadId;

 //根据roadId查询stationindex
- (NSArray *)queryStationIndexByRoadId:(NSString *)roadId;

// 根据roadId和StationId 查询站
-(NSDictionary *)queryStationByRoadIdAndStationId:(NSString *)roadId:(NSString *)stationId;

//插入引导页状态
- (void)insertInfo:(NSString*)imageName state:(NSString*)state;

//修改引导页状态
- (void)updateInfo:(NSString*)imageName state:(NSString*)state;

//查询引导页状态
- (BOOL)queryIndexInfo:(NSString*)imageName;

@end
