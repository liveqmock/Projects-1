//
//  MapPlugin.m
//  LTPlatform
//
//  Created by xue on 14-3-14.
//
//

#import "MapPlugin.h"

@implementation MapPlugin
-(id)init
{
    self = [super init];
    if (self) {
        
    }
    
    return self;

}
//用高德地图获取用户经纬度
-(void)getGDMapUserCoordinate:(CDVInvokedUrlCommand*)command
{
    
    self.gdCoordinateCommand = command;
    
    if (self.gaodemapVC==nil) {
        self.gaodemapVC = [[GaodeMapViewController alloc] init];
        
    }
    [self.gaodemapVC showUserLocation];
    
    
}
//用高德地图获取用户位置信息
-(void)getGDMapUserAddrInfo:(CDVInvokedUrlCommand*)command
{
    
    self.gdAddrInfoCommand = command;
    
    if (self.gaodemapVC==nil) {
        self.gaodemapVC = [[GaodeMapViewController alloc] init];
        
    }
    [self.gaodemapVC showUserLocation];

}


//用百度地图获取用户经纬度
-(void)getBaiduMapUserCoordinate:(CDVInvokedUrlCommand*)command
{
   
    
    
    NSLog(@"getBaiduMapUserCoordinate 用百度地图获取用户经纬度");
    
    self.baiduCoordinateCommand = command;
    
    
    if (self.baiduMapVC==nil) {
        self.baiduMapVC = [[BaiDuMapVC alloc] init];
        self.baiduMapVC.delegate = self;
        
    }
    self.baiduMapVC.isReturnChinese = NO;
    [self.baiduMapVC showLocation];


}
//用百度地图获取用户位置信息
-(void)getBaiduMapUserAddrInfo:(CDVInvokedUrlCommand*)command
{
    
    
    NSLog(@"用百度地图获取用户位置信息");
    
    self.baiduAddrInfoCommand = command;

    if (self.baiduMapVC==nil) {
        self.baiduMapVC = [[BaiDuMapVC alloc] init];
        self.baiduMapVC.delegate = self;
    }
    
    self.baiduMapVC.isReturnChinese = YES;
    [self.baiduMapVC showLocation];

}
#pragma mark 获取用户经纬度信息成功回调
-(void)getUserCLLocation:(CLLocation*)location
{
    
    
    NSLog(@"getUserCLLocation success");
    
    
    NSMutableDictionary * locationDic = [NSMutableDictionary dictionaryWithCapacity:0];
    [locationDic setValue:[NSString stringWithFormat:@"%f",location.coordinate.latitude] forKey:@"latitude"];
    [locationDic setValue:[NSString stringWithFormat:@"%f",location.coordinate.longitude] forKey:@"longitude"];
    
    CDVPluginResult *pluginResult = [ CDVPluginResult
                                     resultWithStatus    : CDVCommandStatus_OK
                                     messageAsDictionary : locationDic
                                     ];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.baiduCoordinateCommand.callbackId];

}
#pragma mark 获取用户经纬度信息失败回调
-(void)getUserCLLocationError:(NSError*)error
{
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageToErrorObject:error.code];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.baiduCoordinateCommand.callbackId];

}
#pragma mark 反地理编码成功
-(void)reverseGeocodeUserLocation:(BMKAddrInfo*)addrInfo
{
    
    
    
    NSLog(@"strAddr   %@",addrInfo.strAddr);
    
    
    NSMutableDictionary * addrInfoDic = [NSMutableDictionary dictionaryWithCapacity:0];
    
    for (int i =0; i<[addrInfo.poiList count]; i++) {
        
        
        NSLog(@"address %@",((BMKPoiInfo*)[addrInfo.poiList   objectAtIndex:i]).address);
        
        
        if (i==[addrInfo.poiList count]-1) {
            [addrInfoDic setValue:((BMKPoiInfo*)[addrInfo.poiList   objectAtIndex:i]).address forKey:@"strAddr"];
        }
        
        

    }
    

 //   [addrInfoDic setValue:[NSJSONSerialization dataWithJSONObject:addrInfo.poiList options:NSJSONWritingPrettyPrinted error:nil] forKey:@"poiList"];
    
    
    NSLog(@"description %@",[addrInfo.addressComponent description]);
    
    /*
    [addrInfo setValue:[MapPlugin isVaildStr:addrInfo.addressComponent.streetNumber] forKey:@"streetNumber"];
    [addrInfo setValue:[MapPlugin isVaildStr:addrInfo.addressComponent.streetName] forKey:@"streetName"];
    [addrInfo setValue:[MapPlugin isVaildStr:addrInfo.addressComponent.district] forKey:@"district"];
    [addrInfo setValue:[MapPlugin isVaildStr:addrInfo.addressComponent.city] forKey:@"city"];
    [addrInfo setValue:[MapPlugin isVaildStr:addrInfo.addressComponent.province] forKey:@"province"];
    */

    CDVPluginResult *pluginResult = [ CDVPluginResult
                                     resultWithStatus    : CDVCommandStatus_OK
                                     messageAsDictionary : addrInfoDic
                                     ];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.baiduAddrInfoCommand.callbackId];
}
#pragma mark 反地理编码失败回调
-(void)reverseGeocodeUserLocationError:(NSError*)error
{
    CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageToErrorObject:error.code];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.baiduAddrInfoCommand.callbackId];

}
+(NSString*)isVaildStr:(NSString*)value
{
    if ([value isKindOfClass:[NSNumber class]]) {
        return [NSString stringWithFormat:@"%i",[value intValue]];
    }
    
    if (value && ![value isEqual:[NSNull null]] && value != nil && ((NSString*)value).length != 0) {
        return value;
    }
    return @"";

}
@end
