//
//  LTUploadPlugin.h
//  ImageSys
//
//  Created by xue on 14-5-19.
//
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <AudioToolbox/AudioServices.h>
#import <Cordova/CDVPlugin.h>

@interface LTUploadPlugin : CDVPlugin
{

}
@property(nonatomic,strong)CDVInvokedUrlCommand * theCommand;
// 附件上传
-(void)LTUploadImage:(CDVInvokedUrlCommand *)command;
@end
