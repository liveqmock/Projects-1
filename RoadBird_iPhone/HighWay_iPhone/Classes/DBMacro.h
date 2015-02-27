//
//  DBMacro.h
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/23.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#ifndef HighWay_iPhone_DBMacro_h
#define HighWay_iPhone_DBMacro_h



#define DBNAME @"HighWay.db"
//行程查询历史记录表
#define SQLITE_QUERYPATH_TABLE_NAME @"LT_QueryPathRecord"
#define SQLITE_CREATE_QUERYPATH_TABLE @"CREATE TABLE IF NOT EXISTS LT_QueryPathRecord (_ID INTEGER PRIMARY KEY autoincrement,startRoadID VARCHAR(50),startStationID VARCHAR(50),startStationName VARCHAR(50),endRoadID VARCHAR(50),endStationID VARCHAR(50),endStationName VARCHAR(50),queryTime DATE);"

#define SQLITE_CREATE_INDEXIMG_TABLE @"CREATE TABLE IndexImg (IndexName VARCHAR(50),value VARCHAR(50));"


#define STATION_TABLE_NAME @"station"


#endif
