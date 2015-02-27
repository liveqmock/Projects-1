//
//  NetConnection.h
//  HighwayTollManage
//
//  Created by wanggp on 9/18/12.
//  Copyright (c) 2012 newsoft. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFHTTPRequestOperationManager.h"

@interface NetConnection : AFHTTPRequestOperationManager

+ (NetConnection *)sharedClient;



- (void)asynGet:(NSString *)URLString
     parameters:(id)parameters
        success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
        failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure;

- (void)asynPost:(NSString *)URLString
      parameters:(id)parameters
         success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
         failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure;

@end
