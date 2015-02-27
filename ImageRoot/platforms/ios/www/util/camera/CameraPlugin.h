//
//  CameraPlugin.h
//  LTPlatform
//
//  Created by xue on 14-3-11.
//
//   摄像头 照片处理 本地原生态语言插件

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <AudioToolbox/AudioServices.h>
#import <Cordova/CDVPlugin.h>

@interface CameraPlugin : CDVPlugin
{
    
    
}
@property(nonatomic,strong)CDVInvokedUrlCommand * theCommand;
// 为图片添加水印
-(void)addWaterStrToBase64Img:(CDVInvokedUrlCommand *)command;
//为通过图片路径给图片添加水印
-(void)addWaterStrToImgWithImgPath:(CDVInvokedUrlCommand*)command;
+(NSMutableArray*)getEncodingArr;
@end
