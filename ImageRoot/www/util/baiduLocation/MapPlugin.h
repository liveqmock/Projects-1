//
//  MapPlugin.h
//  LTPlatform
//
//  Created by xue on 14-3-14.
//
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <AudioToolbox/AudioServices.h>
#import <Cordova/CDVPlugin.h>
#import "GaodeMapViewController.h"
#import "BaiDuMapVC.h"
@interface MapPlugin : CDVPlugin<BaiDuMapVCDelegate,BMKSearchDelegate>
{



}


@property(nonatomic,strong)CDVInvokedUrlCommand * gdCoordinateCommand;
@property(nonatomic,strong)CDVInvokedUrlCommand * gdAddrInfoCommand;
@property(nonatomic,strong)CDVInvokedUrlCommand * baiduCoordinateCommand;
@property(nonatomic,strong)CDVInvokedUrlCommand * baiduAddrInfoCommand;
@property(nonatomic,strong)GaodeMapViewController * gaodemapVC;
@property(nonatomic,strong)BaiDuMapVC * baiduMapVC;
//用高德地图获取用户经纬度
-(void)getGDMapUserCoordinate:(CDVInvokedUrlCommand*)command;
//用高德地图获取用户位置信息
-(void)getGDMapUserAddrInfo:(CDVInvokedUrlCommand*)command;

//用百度地图获取用户经纬度
-(void)getBaiduMapUserCoordinate:(CDVInvokedUrlCommand*)command;
//用百度地图获取用户位置信息
-(void)getBaiduMapUserAddrInfo:(CDVInvokedUrlCommand*)command;
+(NSString*)isVaildStr:(NSString*)value;
@end
