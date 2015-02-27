//
//  OpenFilePlugin.h
//  LTPlatform
//
//  Created by xue on 14-3-28.
//
//

#import <Cordova/CDVPlugin.h>
#import <QuickLook/QuickLook.h>
@interface OpenFilePlugin : CDVPlugin<QLPreviewControllerDataSource,QLPreviewControllerDelegate>
{

}
@property(nonatomic,strong)CDVInvokedUrlCommand * theCommand;
@property(nonatomic,strong)NSString * filePath;
@property(nonatomic,strong)NSArray * filePathArr;
//打开附件
-(void)openFile:(CDVInvokedUrlCommand *)command;


@end
