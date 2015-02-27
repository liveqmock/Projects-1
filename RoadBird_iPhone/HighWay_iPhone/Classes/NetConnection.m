//
//  NetConnection.m
//  HighwayTollManage
//
//  Created by wanggp on 9/18/12.
//  Copyright (c) 2012 newsoft. All rights reserved.
//

#import "NetConnection.h"
#import "CommonData.h"


@implementation NetConnection

+ (NetConnection *)sharedClient {
    static NetConnection *_sharedClient = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _sharedClient = [[NetConnection alloc] initWithBaseURL:[NSURL URLWithString:BaseURL]];
//        _sharedClient=[NetConnection manager];
    });
    
    return _sharedClient;
}

// GET
- (void)asynGet:(NSString *)URLString
                     parameters:(id)parameters
                        success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
                        failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure
{
    // 封装头
    [self.requestSerializer setValue:XAPPToken forHTTPHeaderField:@"X-APP-Token"];
    [self.requestSerializer setValue:[CommonData sharedCommonData].adId forHTTPHeaderField:@"X-APP-DeviceId"];
    [self.requestSerializer setValue:@"" forHTTPHeaderField:@"X-APP-UserId"];
    
    [self GET:URLString parameters:parameters success:success failure:failure];
}


// POST
- (void)asynPost:(NSString *)URLString
     parameters:(id)parameters
        success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
        failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure
{
    // 封装头
    [self.requestSerializer setValue:XAPPToken forHTTPHeaderField:@"X-APP-Token"];
    [self.requestSerializer setValue:[CommonData sharedCommonData].adId forHTTPHeaderField:@"X-APP-DeviceId"];
    [self.requestSerializer setValue:@"" forHTTPHeaderField:@"X-APP-UserId"];
    
    [self POST:URLString parameters:parameters success:success failure:failure];
}




@end
