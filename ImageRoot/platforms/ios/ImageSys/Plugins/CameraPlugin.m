//
//  CameraPlugin.m
//  LTPlatform
//
//  Created by xue on 14-3-11.
//
//

#import "CameraPlugin.h"

@implementation CameraPlugin
-(id)init
{
    
    self = [super init];
    if (self) {
        
    }

    return self;
}
#pragma mark 给照片添加水印
-(void)addWaterStrToBase64Img:(CDVInvokedUrlCommand *)command
{
    
    
    self.theCommand = command;
    NSString * imgBase64Str = [command.arguments objectAtIndex:0]; //图片 base64位的str
    NSString * waterStr = [command.arguments objectAtIndex:1];    //水印字符串
    NSData * imgData = [[NSData alloc] initWithBase64EncodedString:imgBase64Str options:NSDataBase64DecodingIgnoreUnknownCharacters];
    UIImage * img = [[UIImage alloc] initWithData:imgData];
    
    
    float strWidth = [waterStr sizeWithFont:[UIFont systemFontOfSize:30.0]].width;
    float strHeight = [waterStr sizeWithFont:[UIFont systemFontOfSize:30.0]].height;
    float oringX = img.size.width - strWidth-10;
    float oringY = img.size.height - strHeight-10;
    
    
    UIGraphicsBeginImageContextWithOptions(img.size, NO, 0.0f);
    [img drawAtPoint:CGPointMake(0.0f, 0.0f)];
    [[UIColor redColor] set];
    [waterStr drawAtPoint:CGPointMake(oringX, oringY) withFont:[UIFont systemFontOfSize:30.0]];
    UIImage *waterImg = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    
    NSData * backImgData = UIImageJPEGRepresentation(waterImg,0.4);//转换为NSData
    NSString * backImgStr = [backImgData base64Encoding];
    NSMutableDictionary * imgDic = [NSMutableDictionary dictionaryWithCapacity:0];
    [imgDic setValue:backImgStr forKey:@"waterImg"];
    CDVPluginResult *pluginResult = [ CDVPluginResult
                                     resultWithStatus    : CDVCommandStatus_OK
                                     messageAsDictionary : imgDic
                                     ];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.theCommand.callbackId];
    
    
    
}
//为通过图片路径给图片添加水印
-(void)addWaterStrToImgWithImgPath:(CDVInvokedUrlCommand*)command
{
    
    
    self.theCommand = command;
    
    NSString * documentPath = [NSHomeDirectory() stringByAppendingPathComponent:@"Documents"];
    
    NSString * imgPath =   [command.arguments objectAtIndex:0]; //图片名字
    
    
    NSString * imgName = [imgPath lastPathComponent];
    
    NSString * waterStr = [command.arguments objectAtIndex:1];
    
    
    UIImage * originImg = [UIImage imageWithContentsOfFile:[documentPath stringByAppendingPathComponent:imgName]];
    
    
    
    NSLog(@"originImg:%@",originImg);
    
    
    float strWidth = [waterStr sizeWithFont:[UIFont systemFontOfSize:30.0]].width;
    float strHeight = [waterStr sizeWithFont:[UIFont systemFontOfSize:30.0]].height;
    float oringX = originImg.size.width - strWidth-10;
    float oringY = originImg.size.height - strHeight-30;
    
    
    UIGraphicsBeginImageContextWithOptions(originImg.size, NO, 0.0f);
    [originImg drawAtPoint:CGPointMake(0.0f, 0.0f)];
    [[UIColor redColor] set];
    [waterStr drawAtPoint:CGPointMake(oringX, oringY) withFont:[UIFont systemFontOfSize:30.0]];
    UIImage *waterImg = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    
     NSData * backImgData = UIImageJPEGRepresentation(waterImg,0.4);
    
    
    
    NSLog(@"documentPath %@",[documentPath stringByAppendingPathComponent:imgName]);
    
    
     BOOL isSuccess = [backImgData writeToFile:[documentPath stringByAppendingPathComponent:imgName] atomically:YES];
    
    NSLog(@"isSuccess %i",isSuccess);
    
    CDVPluginResult *pluginResult = [ CDVPluginResult
                                     resultWithStatus    : CDVCommandStatus_OK
                                     ];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.theCommand.callbackId];
   
    
}
    
    
#pragma mark  获取各种编码
+(NSMutableArray*)getEncodingArr
{

    
    NSMutableArray * backArr = [NSMutableArray arrayWithObjects:[NSNumber numberWithInt:NSASCIIStringEncoding],[NSNumber numberWithInt:NSNEXTSTEPStringEncoding],[NSNumber numberWithInt:NSJapaneseEUCStringEncoding],[NSNumber numberWithInt:NSUTF8StringEncoding],[NSNumber numberWithInt:NSISOLatin1StringEncoding],[NSNumber numberWithInt:NSSymbolStringEncoding],[NSNumber numberWithInt:NSNonLossyASCIIStringEncoding],[NSNumber numberWithInt:NSShiftJISStringEncoding],[NSNumber numberWithInt:NSISOLatin2StringEncoding],[NSNumber numberWithInt:NSUnicodeStringEncoding],[NSNumber numberWithInt:NSWindowsCP1251StringEncoding],[NSNumber numberWithInt:NSWindowsCP1252StringEncoding], [NSNumber numberWithInt:NSWindowsCP1253StringEncoding],[NSNumber numberWithInt:NSWindowsCP1254StringEncoding],[NSNumber numberWithInt:NSWindowsCP1250StringEncoding],[NSNumber numberWithInt:NSISO2022JPStringEncoding],[NSNumber numberWithInt:NSMacOSRomanStringEncoding],[NSNumber numberWithInt:NSUTF16StringEncoding],[NSNumber numberWithInt:NSUTF16BigEndianStringEncoding],[NSNumber numberWithInt:NSUTF16LittleEndianStringEncoding],[NSNumber numberWithInt:NSUTF32StringEncoding],[NSNumber numberWithInt:NSUTF32BigEndianStringEncoding],[NSNumber numberWithInt:NSUTF32LittleEndianStringEncoding],nil];

    return backArr;

}


@end
