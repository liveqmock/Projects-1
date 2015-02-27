//
//  takePicPlugin.h
//  ImageSys
//
//  Created by xue on 14-5-23.
//
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <AudioToolbox/AudioServices.h>
#import <Cordova/CDVPlugin.h>

@interface TakePicPlugin : CDVPlugin<UIImagePickerControllerDelegate,UINavigationControllerDelegate>
{
   
    int takeNumber;
    UIImagePickerController *curPicker;
    NSMutableArray * imgArr;
    NSString * folderName;
    UILabel * numberLab;
    NSString * waterStr;
    NSString * showTimeStr;//后面加的，用来上传个服务器以及展示的
}
@property(nonatomic,strong)CDVInvokedUrlCommand * theCommand;
//连续拍照
-(void)seriesTakePic:(CDVInvokedUrlCommand*)command;

    
@end
