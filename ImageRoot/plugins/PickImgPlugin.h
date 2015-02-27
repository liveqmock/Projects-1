//
//  PickImgPlugin.h
//  ImageSys
//
//  Created by xue on 14-5-26.
//
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <AudioToolbox/AudioServices.h>
#import <Cordova/CDVPlugin.h>
#import "QBImagePickerController.h"
@interface PickImgPlugin : CDVPlugin<QBImagePickerControllerDelegate>
{
    NSMutableArray * imgArr;
    NSString * folderName;
    NSString * waterStr;
    NSString * showTimeStr;//后面加的，用来上传个服务器以及展示的

}
@property(nonatomic,strong)CDVInvokedUrlCommand * theCommand;
//照片多选
-(void)pickMultiplePhotos:(CDVInvokedUrlCommand*)command;

//获取文件路径
-(void)getFileDocumentsPath:(CDVInvokedUrlCommand*)command;
@end
