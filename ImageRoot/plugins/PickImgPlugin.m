//
//  PickImgPlugin.m
//  ImageSys
//
//  Created by xue on 14-5-26.
//
//

#import "PickImgPlugin.h"

@implementation PickImgPlugin

#pragma mark 照片多选
-(void)pickMultiplePhotos:(CDVInvokedUrlCommand*)command
{
    
    
    self.theCommand = command;
    
    
    folderName  = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex: 0];
   
    
    
   // folderName = [[NSHomeDirectory() stringByAppendingPathComponent:@"Documents"]stringByAppendingPathComponent:@"ImgSysPic"];
    
     
    
    NSDateFormatter * formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"yyyy-MM-dd"];
    NSDate * curDate = [NSDate date];
    waterStr = [formatter stringFromDate:curDate];
    imgArr = [[NSMutableArray  alloc] initWithCapacity:0];
    
    
    NSDateFormatter * otherFormatter = [[NSDateFormatter alloc] init];
    [otherFormatter setDateFormat:@"yyyy-MM-dd HH:mm"];
    showTimeStr = [otherFormatter stringFromDate:curDate];
    
    
    
    QBImagePickerController *imagePickerController = [[QBImagePickerController alloc] init];
    imagePickerController.delegate = self;
    imagePickerController.allowsMultipleSelection = YES;
    
    UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:imagePickerController];
    [self.viewController presentViewController:navigationController animated:YES completion:NULL];
    
}
#pragma mark - QBImagePickerControllerDelegate
//多选结束回调
- (void)imagePickerController:(QBImagePickerController *)imagePickerController didFinishPickingMediaWithInfo:(id)info
{
    if(imagePickerController.allowsMultipleSelection) {
        NSArray *mediaInfoArray = (NSArray *)info;
        
        NSLog(@"Selected %d photos and mediaInfoArray==%@", mediaInfoArray.count,mediaInfoArray);
        
        
        
        NSFileManager *fileManager = [NSFileManager defaultManager];
        
        //判断temp文件夹是否存在
        
        BOOL fileExists = [fileManager fileExistsAtPath:folderName];
        
        if (!fileExists) {//如果不存在时就创建,因为下载时,不会自动创建文件夹
            
            [fileManager createDirectoryAtPath:folderName
             
                   withIntermediateDirectories:YES
             
                                    attributes:nil
             
                                         error:nil];
            
        }
        
        
        
        
        
        UIImage * tempImg;
        
        for (int i=0; i<[mediaInfoArray count]; i++) {
            
            tempImg = [[mediaInfoArray objectAtIndex:i] objectForKey:@"UIImagePickerControllerOriginalImage"];
            
            NSData * saveImgData = UIImageJPEGRepresentation(tempImg,0.1);
            
            //NSLog(@"saveImgData %@",saveImgData);
            
            NSString * imgName = [NSString stringWithFormat:@"%@IOS.jpg",[PickImgPlugin getUniqueId]];
            
            
            
            BOOL isSuccess = [saveImgData writeToFile:[folderName stringByAppendingPathComponent:imgName] atomically:YES];
            
            if (isSuccess) {
                NSMutableDictionary * imgInfoDic = [NSMutableDictionary dictionaryWithCapacity:0];
              
               // [imgInfoDic setValue:[folderName stringByAppendingPathComponent:imgName] forKey:@"ImgPath"];
                [imgInfoDic setValue:imgName forKey:@"ImgPath"];
               
                
                [imgInfoDic setValue:waterStr forKey:@"TakeTime"];
                [imgInfoDic setValue:showTimeStr forKey:@"showTime"];
                [imgArr addObject:imgInfoDic];
            }
            
            
            NSLog(@"imgArr count %i",[imgArr count]);
            
            
            
        }
        
        NSMutableDictionary * dic = [NSMutableDictionary dictionaryWithCapacity:0];
        [dic setValue:imgArr forKey:@"imgArr"];
        
        CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dic];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:self.theCommand.callbackId];
        
        [self.viewController dismissViewControllerAnimated:YES completion:NULL];
        
        
        
    } else {
        NSDictionary *mediaInfo = (NSDictionary *)info;
        NSLog(@"Selected: %@", mediaInfo);
    }
    
    [self.viewController dismissViewControllerAnimated:YES completion:NULL];
}

- (void)imagePickerControllerDidCancel:(QBImagePickerController *)imagePickerController
{
    NSLog(@"取消选择");
    
    [self.viewController dismissViewControllerAnimated:YES completion:NULL];
    
}
+(NSString*)getUniqueId
{
    
    
    CFUUIDRef uuidRef =CFUUIDCreate(NULL);
    
    CFStringRef uuidStringRef =CFUUIDCreateString(NULL, uuidRef);
    
    CFRelease(uuidRef);
    
    NSString *uniqueId = (__bridge NSString *)uuidStringRef;
    
    
    NSLog(@"uniqueId:%@",uniqueId);

    return uniqueId;
    
    
    
}
//获取文件路径 Documents
-(void)getFileDocumentsPath:(CDVInvokedUrlCommand*)command
{
    self.theCommand = command;
    NSString * docPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex: 0];
    
    
    CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:docPath];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.theCommand.callbackId];
    
    
    
}
@end
