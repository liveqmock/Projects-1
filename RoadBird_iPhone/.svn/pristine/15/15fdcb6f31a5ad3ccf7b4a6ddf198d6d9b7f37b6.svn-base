//
//  SqliteUtil.m
//  GaoSu_iPhone
//
//  Created by wanggp on 14-4-30.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "SqliteUtil.h"
#import "FMDB.h"


@implementation SqliteUtil


+ (SqliteUtil *)sharedSqliteUtil
{
    static SqliteUtil *_sharedSqliteUtil = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _sharedSqliteUtil = [[SqliteUtil alloc] init];
    });
    
    return _sharedSqliteUtil;
}


+(void) initSqlite {
    // 获取数据库路径
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    if ([db open]) {
        // 先查看数据库有没有表【SERVER】
        FMResultSet *rs = [db executeQuery:@"select count(*) as 'count' from sqlite_master where type ='table' and name = ?", SQLITE_QUERYPATH_TABLE_NAME];
        if ([rs next])
        {
            // just print out what we've got in a number of formats.
            NSInteger count = [rs intForColumn:@"count"];
            
            if (0 == count)
            {
                NSLog(@"数据库中表不存在！");
                if ([db executeUpdate:SQLITE_CREATE_QUERYPATH_TABLE]) {
                    NSLog(@"成功初始化SQLite数据库！");
                } else {
                    NSLog(@"初始化SQLite数据库失败！失败！失败！");
                }
            }
            else
            {
                NSLog(@"表【LT_QueryPathRecord】存在！");
            }
        }
        
        
       rs = [db executeQuery:@"select count(*) as 'count' from sqlite_master where type ='table' and name = ?", STATION_TABLE_NAME];
        if ([rs next])
        {
            // just print out what we've got in a number of formats.
            NSInteger count = [rs intForColumn:@"count"];
            
            if (0 == count)
            {
                NSLog(@"数据库中表不存在！");
                // 导入站表
                NSString *stationPath = [[NSBundle mainBundle] pathForResource:@"station.sql" ofType:nil];
                NSString *string = [NSString stringWithContentsOfFile:stationPath encoding:NSUTF8StringEncoding error:nil];
               
              BOOL success=  [db executeStatements:string];

                if (success) {
                    NSLog(@"成功初始化station表");
                } else {
                    NSLog(@"初始化station表失败！失败！失败！");
                }
            }
            else
            {
                NSLog(@"表【station】存在！");
            }
        }
        
        //引导页表
        if (![db tableExists:@"IndexImg"]) {
            if ([db executeUpdate:SQLITE_CREATE_INDEXIMG_TABLE]) {
                NSLog(@"成功初始化IndexImg表");
                [CommonData sharedCommonData].isFirstOpenApp = YES; //改成第一次开应用
                NSLog(@"isFirstOpenApp：%d",[CommonData sharedCommonData].isFirstOpenApp);

            }else
            {
                NSLog(@"初始化IndexImg表失败");
            } 
        }

    }
    [db close];

}

//插入引导页状态
- (void)insertInfo:(NSString*)imageName state:(NSString*)state
{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    if ([db open]) {
        if ([db executeUpdate:@"insert into IndexImg(IndexName, value) values(?,?)",imageName,state])
        {
//            NSLog(@"引导页状态插入成功");
        }else
        {
//            NSLog(@"引导页状态插入失败");
        }
    }
    [db close];
}

//修改引导页状态
- (void)updateInfo:(NSString*)imageName state:(NSString*)state
{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    if ([db open]) {
        if ([db executeUpdate:@"update IndexImg set value = ? where IndexName = ? ",state,imageName])
        {
//            NSLog(@"引导页状态修改成功");
        }else
        {
//            NSLog(@"引导页状态修改失败");
        }
    }
    [db close];
}

//查询引导页状态
- (BOOL)queryIndexInfo:(NSString*)imageName
{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    if ([db open]) {
        NSString *sql = [NSString stringWithFormat:@"select value from IndexImg where IndexName = '%@'",imageName];
        FMResultSet *set = [db executeQuery:sql];
        while ([set next]) {
            NSString *value = [[set resultDictionary] objectForKey:@"value"];
            if ([value isEqualToString:@"show"]) {
                [db close];
                return YES;
            }
            else if ([value isEqualToString:@"hide"])
            {
                [db close];
                return NO;
            }

        }
    }
    [db close];
    return YES;
}

- (int)insertQueryPath:(NSDictionary *)path{
    int ret = -1;
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    if ([db open]) {
        if ([db executeUpdate:@"insert into LT_QueryPathRecord(startRoadID, startStationID ,startStationName, endRoadID, endStationID, endStationName,queryTime) values(:startRoadID,:startStationID,:startStationName,:endRoadID,:endStationID,:endStationName,:queryTime)" withParameterDictionary:path]){
           
            ret = (int)[db lastInsertRowId];
        }
    }
    [db close];
    return ret;
}


- (NSArray *)queryAllPathRecords {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    NSMutableArray *arr = [[NSMutableArray alloc] init];
    if ([db open]) {
        NSString *sql = [NSString stringWithFormat:@"select * from %@ order by _ID desc",SQLITE_QUERYPATH_TABLE_NAME];
        FMResultSet *set = [db executeQuery:sql];
        while ([set next]) {
            NSDictionary *r = [set resultDictionary];
            [arr addObject:r];
        }
    }
    [db close];
    return arr;
}

- (BOOL)deletePathRecordById:(int)_id
{
    BOOL ret = NO;
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    if ([db open]) {
        ret = [db executeUpdate:@"delete from LT_QueryPathRecord where _ID=?", [NSNumber numberWithInteger:_id]];
    }
    [db close];
    return ret;
}

- (BOOL)deleteAllPathRecord
{
    BOOL ret = NO;
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    if ([db open]) {
        ret = [db executeUpdate:@"delete from LT_QueryPathRecord "];
    }
    [db close];
    return ret;
}

// 根据roadId查询站
- (NSArray *)queryStationByRoadId:(NSString *)roadIds {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    NSMutableArray *arr = [[NSMutableArray alloc] init];
    @try {
        if ([db open]) {
            NSString *sql = [NSString stringWithFormat:@"select * from %@ where roadid in (%@) and stationtype=0 order by stationindex asc",STATION_TABLE_NAME,roadIds];
            FMResultSet *set = [db executeQuery:sql];
            while ([set next]) {
                NSDictionary *r = [set resultDictionary];
                [arr addObject:r];
            }
        }
        
    }
    @catch (NSException *exception) {
    }
    @finally {
        [db close];
        
    }
   
    return arr;
}

/**
 *根据roadId查询stationindex
 */
- (NSArray *)queryStationIndexByRoadId:(NSString *)roadId
{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    NSMutableArray *arr = [[NSMutableArray alloc] init];
    @try {
        if ([db open]) {
            NSString *sql = [NSString stringWithFormat:@"select DISTINCT stationindex from %@ where roadid=? and stationtype=0 order by stationindex asc",STATION_TABLE_NAME];
            FMResultSet *set = [db executeQuery:sql,roadId];
            while ([set next]) {
                NSDictionary *r = [set resultDictionary];
                [arr addObject:r];
            }
        }
        
    }
    @catch (NSException *exception) {
    }
    @finally {
        [db close];
        
    }
    
    return arr;
}



// 根据roadId和StationId 查询站
-(NSDictionary *)queryStationByRoadIdAndStationId:(NSString *)roadId:(NSString *)stationId {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentsDirectory = [paths objectAtIndex:0];
    NSString *filePath = [documentsDirectory stringByAppendingPathComponent:DBNAME];
    FMDatabase *db = [FMDatabase databaseWithPath:filePath];
    NSMutableArray *arr = [[NSMutableArray alloc] init];
    @try {
        if ([db open]) {
            NSString *sql = [NSString stringWithFormat:@"select * from %@ where roadid=? and stationtype=0 and stationid=?",STATION_TABLE_NAME];
            FMResultSet *set = [db executeQuery:sql,roadId,stationId];
            while ([set next]) {
                NSDictionary *r = [set resultDictionary];
                [arr addObject:r];
            }
        }
        
    }
    @catch (NSException *exception) {
    }
    @finally {
        [db close];
        
    }
    
    if([arr count]>0)
        return [arr objectAtIndex:0];
    else
        return nil;
}




@end
