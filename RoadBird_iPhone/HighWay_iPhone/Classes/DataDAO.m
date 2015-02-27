//
//  DataDAO.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/9.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "DataDAO.h"
#import "AFNetworking.h"


@implementation DataDAO


//+ (DataDAO *)sharedDataDAO
//{
//    static DataDAO *_dataDAO=nil;
//    static dispatch_once_t onceToken;
//    dispatch_once(&onceToken, ^{
//        _dataDAO =[ [DataDAO alloc] init];
//    });
//    return _dataDAO;
//}

//
+(void)getTrafficinfo:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock
{
    
    [[NetConnection sharedClient] asynGet:trafficinfo parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSString *errorMessage=@"获取交通事故数据失败!";
        NSDictionary *json =responseObject;
        if(json){
            int responseCode= [[json valueForKeyPath:@"responseCode"] intValue] ;
            if(responseCode==0)
            {
                NSArray *list = [json valueForKeyPath:@"trafficInfo"];
                completedBlock(list);
            }
            else
            {
                
                if(responseCode==1)
                {
                    errorMessage=@"获取交通事故数据失败!";
                }
                else if(responseCode==5)
                {
                    errorMessage=@"无权限访问!";
                    
                }
                else if(responseCode==-1)
                {
                    errorMessage=@"连接服务器失败!";
                }
                failureBlock(errorMessage);
            }
        }
        else
        {
            // 返回空数组
            failureBlock(errorMessage);
        }
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        failureBlock([error localizedDescription]);
    }];
    
}

// 查询规划路径方案
+(void)getRoadPlan:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock
{
    [[NetConnection sharedClient] asynGet:roadplan parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSString *errorMessage=@"查询路径规划失败!";
        NSDictionary *json =responseObject;
        if(json){
            int responseCode= [[json valueForKeyPath:@"responseCode"] intValue] ;
            if(responseCode==0)
            {
                
                completedBlock(json);
            }
            else
            {
                if(responseCode==1)
                {
                    errorMessage=@"查询失败!由于高速路网无连通,需要绕行国道,导致无法规划！";
                }
                else if(responseCode==5)
                {
                    errorMessage=@"无权限访问!";
                    
                }
                else if(responseCode==-1)
                {
                    errorMessage=@"连接服务器失败!";
                }
                
                failureBlock(errorMessage);
            }
        }
        else
        {
            // 返回空数组
            completedBlock(errorMessage);
        }
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        failureBlock([error localizedDescription]);
    }];
    
}



// 查询规划路径方案roadplandetail
+(void)getRoadPlanDetail:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock
{
    [[NetConnection sharedClient] asynGet:roadplandetail parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSString *errorMessage=@"查询不到行程规划方案信息！";
        NSDictionary *json =responseObject;
        if(json){
            int responseCode= [[json valueForKeyPath:@"responseCode"] intValue] ;
            if(responseCode==0)
            {
                NSDictionary *planDetail = [json valueForKey:@"planDetail"];
                completedBlock(planDetail);
            }
            else
            {
                
                if(responseCode==1)
                {
                    errorMessage=@"查询不到行程规划方案信息！";
                }
                else if(responseCode==5)
                {
                    errorMessage=@"无权限访问!";
                    
                }
                else if(responseCode==-1)
                {
                    errorMessage=@"连接服务器失败!";
                }
                
                failureBlock(errorMessage);
            }
        }
        else
        {
            // 返回空数组
            completedBlock(nil);
        }
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        failureBlock([error localizedDescription]);
    }];
    
}


//根据经纬度查询附近的高速路段信息
+(void)getNearroads:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock
{
    [[NetConnection sharedClient] asynGet:nearroads parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSString *errorMessage=@"查找不到附近的高速公路数据！";
        NSDictionary *json =responseObject;
        if(json){
            int responseCode= [[json valueForKeyPath:@"responseCode"] intValue] ;
            if(responseCode==0)
            {
                completedBlock(json);
            }
            else
            {
                if(responseCode==1)
                {
                    errorMessage=@"查找不到附近的高速公路数据！";
                }
                else if(responseCode==5)
                {
                    errorMessage=@"无权限访问!";
                    
                }
                else if(responseCode==-1)
                {
                    errorMessage=@"连接服务器失败!";
                }
                
                failureBlock(errorMessage);
            }
        }
        else
        {
            // 返回空数组
            completedBlock(errorMessage);
        }
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"getNearroads failure!");
        failureBlock([error localizedDescription]);
    }];
    
}


//按路段查询道路的车速
+(void)getRoadSpeed:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock
{
    [[NetConnection sharedClient] asynGet:roadspeed parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSString *errorMessage=@"查询路段车速失败！";
        NSDictionary *json =responseObject;
        if(json){
            int responseCode= [[json valueForKeyPath:@"responseCode"] intValue] ;
            if(responseCode==0)
            {
                NSDictionary *roadSpeed = [json valueForKey:@"roadSpeed"];
                completedBlock(roadSpeed);
            }
            else
            {
                if(responseCode==1)
                {
                    errorMessage=@"查询路段车速失败！";
                }
                else if(responseCode==5)
                {
                    errorMessage=@"无权限访问!";
                    
                }
                else if(responseCode==-1)
                {
                    errorMessage=@"连接服务器失败!";
                }
                
                failureBlock(errorMessage);
            }
        }
        else
        {
            // 返回空数组
            completedBlock(errorMessage);
        }
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        failureBlock([error localizedDescription]);
    }];
    
}

#pragma make - second vender
// 报料
+(void)postReport:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock{
    [[NetConnection sharedClient] asynPost:report parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject){
        
        NSString* errorMessage = @"报料失败";
        NSDictionary *json = responseObject;
        int responseCode = [[json objectForKey:@"responseCode"] integerValue];
        if (responseCode==0) {
            completedBlock(json);
        }else{
            failureBlock(errorMessage);
        }
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error){
        failureBlock([error localizedDescription]);
    }];
}

// 获得用户分享的高速资讯
+(void)getUserShareInfo:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock{
    [[NetConnection sharedClient] asynPost:usershareinfo parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject){
        NSString* errorMessage = @"获取用户分享资讯失败";
        NSDictionary *json = responseObject;
        int responseCode = [[json objectForKey:@"responseCode"] integerValue];
        if (responseCode == 0) {
            completedBlock([json objectForKey:@"traffic"]); //得到一个数组
        }else{
            failureBlock(errorMessage);
        }
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error){
        failureBlock([error localizedDescription]);
    }];
}

// 普通用户注册
+(void)postNormalRegister:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock{
    [[NetConnection sharedClient] asynPost:normalregister parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject){
        
        NSString* errorMessage = @"普通用户注册失败";
        NSDictionary *json = responseObject;
        int responseCode = [[json objectForKey:@"responseCode"] integerValue];
        if (responseCode == 0) {
            completedBlock(json);
        }else{
            failureBlock(errorMessage);
        }
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error){
        NSLog(@"postNormalRegister：%@",@"出错");
        failureBlock([error localizedDescription]);
        
    }];
}

// 企业用户注册
+(void)postCompanyRegister:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock{
    [[NetConnection sharedClient] asynPost:companyregister parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject){
        
        NSString* errorMessage = @"企业用户注册失败";
        NSDictionary *json = responseObject;
        int responseCode = [[json objectForKey:@"responseCode"] integerValue];
        if (responseCode == 0) {
            completedBlock(json);
        }else{
            failureBlock(errorMessage);
        }
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error){
        failureBlock([error localizedDescription]);
        
    }];
}

// 用户登陆
+(void)getNormalLogin:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock{
    [[NetConnection sharedClient] asynPost:normallogin parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject){
        NSLog(@"asynGet complete");
        NSString* errorMessage = @"用户登录失败";
        NSDictionary *json = responseObject;
        NSLog(@"===%@",json);
        int responseCode = [[json objectForKey:@"responseCode"] integerValue];
        if (responseCode == 0) {
            completedBlock(json);
        }else{
            failureBlock(errorMessage);
        }
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error){
        NSLog(@"asynGet Failure");
        failureBlock([error localizedDescription]);
        
    }];
}

//查询总积分
+(void)getQueryScore:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock{
    
    [[NetConnection sharedClient] asynGet:queryscore parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject){
        
        NSString* errorMessage = @"查询总积分失败";
        NSDictionary *json = responseObject;
        
        int responseCode = [[json objectForKey:@"responseCode"] integerValue];
        if (responseCode == 0) {
            completedBlock(json);
        }else{
            failureBlock(errorMessage);
        }
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error){
        failureBlock([error localizedDescription]);
        
    }];
}

// 第三方用户登录
+(void)getThirdPartLogin:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock{
    [[NetConnection sharedClient] asynGet:thirdpartlogin parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject){
        
        NSString* errorMessage = @"第三方用户登录失败";
        NSDictionary *json = responseObject;
        
        int responseCode = [[json objectForKey:@"responseCode"] integerValue];
        if (responseCode == 0) {
            completedBlock(json);
        }else{
            failureBlock(errorMessage);
        }
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error){
        failureBlock([error localizedDescription]);
        
    }];
    
}

// 修改用户信息
+(void)postChangeUserInfo:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock{
    [[NetConnection sharedClient] asynPost:changeuserinfo parameters:param success:^(AFHTTPRequestOperation *operation, id responseObject){
        
        NSString* errorMessage = @"修改用户信息失败";
        NSDictionary *json = responseObject;
        int responseCode = [[json objectForKey:@"responseCode"] integerValue];
        if (responseCode == 0) {
            completedBlock(json);
        }else{
            failureBlock(errorMessage);
        }
        
    } failure:^(AFHTTPRequestOperation *operation, NSError *error){
        failureBlock([error localizedDescription]);
        
    }];
    
}
// 修改用户信息(图片上传)
+(void)postChangeUserImageInfo:(NSDictionary *)param withCompleted:(void(^)(id))completedBlock withFailure:(void(^)(id))failureBlock{

   
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    
    [manager.requestSerializer setValue:XAPPToken forHTTPHeaderField:@"X-APP-Token"];
    [manager.requestSerializer setValue:[CommonData sharedCommonData].adId forHTTPHeaderField:@"X-APP-DeviceId"];
    [manager.requestSerializer setValue:@"" forHTTPHeaderField:@"X-APP-UserId"];
    
    NSData *imageData = UIImageJPEGRepresentation([CommonData sharedCommonData].me.headImage, 1.0);
    
    NSString *urlForImage = [BaseURL stringByAppendingString:changeuserimage];
    NSLog(@"urlForImage: %@", urlForImage);
    [manager POST:urlForImage parameters:param constructingBodyWithBlock:^(id<AFMultipartFormData> formData) {
        [formData appendPartWithFileData:imageData name:@"picAttach" fileName:[param objectForKey:@"image"] mimeType:@"image/png"];
    } success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSLog(@"Success: %@", responseObject);
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"Error: %@", error);
    }];
    
   
    
}

@end
