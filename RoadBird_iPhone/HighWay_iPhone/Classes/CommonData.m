//
//  CommonData.m
//  HighwayTollManage
//
//  Created by wanggp on 9/6/12.
//  Copyright (c) 2012 newsoft. All rights reserved.
//

#import "CommonData.h"
#import <AdSupport/ASIdentifierManager.h> 
#import "Person.h"

@implementation CommonData


+ (CommonData *)sharedCommonData {
    static CommonData *_sharedCommonData = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _sharedCommonData =[ [CommonData alloc] init];
        _sharedCommonData.me = [[Person alloc] init];
    });
    
    return _sharedCommonData;
}


- (void)loadData
{
    [self addObserver:self forKeyPath:@"currentCity" options:NSKeyValueObservingOptionNew|NSKeyValueObservingOptionOld context:NULL];
     _currentCity=@"广州";
    
//  _adId = [[[ASIdentifierManager sharedManager] advertisingIdentifier] UUIDString];
    // 受苹果审批的限时，暂时先使用uuid作为唯一标识码
    _adId=[ComFun uuid];
}
- (void)saveData
{
    
}

-(void)dealloc
{
    [self removeObserver:self forKeyPath:@"currentCity"];
}



-(void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    if([keyPath isEqualToString:@"currentCity"])
    {
        // 获取天气预报
        ChinaWeatherUtil *chinaWeatherUtil=[[ChinaWeatherUtil alloc] init];
        [chinaWeatherUtil getWeatherData:[CommonData sharedCommonData].currentCity success:^(ChinaWeatherResult *result) {
            _chinaWeatherResult=result;
        } failure:^(NSError *error) {
            
        }];
    }
}



@end
