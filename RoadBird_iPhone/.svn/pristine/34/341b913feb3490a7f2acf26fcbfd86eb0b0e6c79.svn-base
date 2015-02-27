//
//  ChinaWeatherUtil.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-8.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "ChinaWeatherUtil.h"


@implementation ChinaWeatherUtil

-(void)readWeatherCodeDic{
    NSString *local = [[NSBundle mainBundle] pathForResource:@"ChinaWeatherCodeDic" ofType:@"json"];
    NSString *string = [NSString stringWithContentsOfFile:local encoding:NSUTF8StringEncoding error:nil];
    NSData *data = [string dataUsingEncoding:NSUTF8StringEncoding];
    self.weatherCodeDic = [NSJSONSerialization JSONObjectWithData:data options:0 error:nil];
    
}

//
//- (void)asyncCommand:(NSInteger)command
//          parameters:(NSDictionary *)parameters
//             success:(void (^)(AFHTTPRequestOperation *operation, id responseObject))success
//             failure:(void (^)(AFHTTPRequestOperation *operation, NSError *error))failure;

- (void)getWeatherData:(NSString *)city
               success:(void (^)(ChinaWeatherResult *))success
               failure:(void(^)(NSError *error))failure
{
    [self readWeatherCodeDic];
    NSString *code = [self.weatherCodeDic valueForKey:city];
    NSString*urlstring=[NSString stringWithFormat:@"%@%@.html",ChinaWeatherURL,code];
    NSURL*url=[NSURL URLWithString:urlstring];
    
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        NSString*resultString=[NSString stringWithContentsOfURL:url encoding:NSUTF8StringEncoding error:nil];
        if(resultString){
            dispatch_async(dispatch_get_main_queue(), ^{
                NSData *data = [resultString dataUsingEncoding:NSUTF8StringEncoding];
                NSDictionary *dic= [NSJSONSerialization JSONObjectWithData:data options:0 error:nil];
                NSDictionary*subdic=[dic objectForKey:@"weatherinfo"];
                NSString*weather=[subdic objectForKey:@"weather"];
                NSString*city=[subdic objectForKey:@"city"];
                
                NSString*img1=[subdic objectForKey:@"img1"];
                NSString*img2=[subdic objectForKey:@"img2"];
                
                NSString*temp1=[subdic objectForKey:@"temp1"];
                NSString*temp2=[subdic objectForKey:@"temp2"];
                
                ChinaWeatherResult*result=[[ChinaWeatherResult alloc]init];
                result.cityName=city;
                result.imgNameDay=img1;
                result.imgNameNight=img2;
                result.temperatureDay=temp1;
                result.temperatureNight=temp2;
                result.weatherInfo=weather;
                success(result);
//
//                if([_mydelegate respondsToSelector:@selector(getDataCompleted:withSender:)]){
//                    [_mydelegate getDataCompleted:result withSender:self];
//                }
//
            });
        }
        else{
            dispatch_async(dispatch_get_main_queue(), ^{
//                if([_mydelegate respondsToSelector:@selector(getDataFailwithSender:)]){
//                    [_mydelegate getDataFailwithSender:self];
//                }
            });
        }
        
    });
}

+(NSString *)getWeatherImage:(NSString *)weatherInfo
{
    NSString *imageName= @"weather-default";
    if([NSString isEmpty:weatherInfo])
        return  imageName;
    BOOL isFirst = YES;
    
    for (int i=0; i<2; i++) {
    
        if([weatherInfo indexOf:@"雷电" state:isFirst]!=-1)
        {
            imageName =@"weather-lightning"; break;
        }
        else if([weatherInfo indexOf:@"台风" state:isFirst]!=-1)
        {
            imageName =@"weather-typhoon"; break;
        }
        else if([weatherInfo indexOf:@"晴" state:isFirst]!=-1)
        {
            imageName =@"weather-fine"; break;
        }
        else if([weatherInfo indexOf:@"雾" state:isFirst]!=-1||[weatherInfo indexOf:@"霾" state:isFirst]!=-1)
        {
            imageName =@"weather-haze"; break;
        }
        else if([weatherInfo indexOf:@"小雨" state:isFirst]!=-1||[weatherInfo indexOf:@"中雨" state:isFirst]!=-1 || [weatherInfo indexOf:@"阵雨" state:isFirst]!=-1)
        {
            imageName =@"weather-lightrain-moderaterain"; break;
        }
        else if([weatherInfo indexOf:@"多云" state:isFirst]!=-1||[weatherInfo indexOf:@"阴天" state:isFirst]!=-1||[weatherInfo indexOf:@"云" state:isFirst]!=-1)
        {
            imageName =@"weather-cloudy"; break;
        }
        else if([weatherInfo indexOf:@"大雨" state:isFirst]!=-1||[weatherInfo indexOf:@"暴雨" state:isFirst]!=-1)
        {
            imageName =@"weather-heavyain-stormy"; break;
        }
        if (i==0) {
            isFirst = NO;
            
        }else
        {
            NSLog(@"天气算法没找到，给个多云");
            imageName =@"weather-cloudy";
        }
}
    
    
    return imageName;
    
    
    
}




@end
