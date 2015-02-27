//
//  takePicPlugin.m
//  ImageSys
//
//  Created by xue on 14-5-23.
//
//

#import "TakePicPlugin.h"

@implementation TakePicPlugin
-(id)init
{
        
        self = [super init];
        if (self) {
            
        }
        
        return self;
}
-(void)seriesTakePic:(CDVInvokedUrlCommand*)command{

    
    self.theCommand = command;
    
   
    takeNumber = 0;
    

   
    folderName  = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex: 0];
    
    
    NSDateFormatter * formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"yyyy-MM-dd"];
    NSDate * curDate = [NSDate date];
    
    waterStr = [formatter stringFromDate:curDate];
    
    
    NSDateFormatter * otherFormatter = [[NSDateFormatter alloc] init];
    [otherFormatter setDateFormat:@"yyyy-MM-dd HH:mm"];
    showTimeStr = [otherFormatter stringFromDate:curDate];
    
    
    imgArr = [[NSMutableArray  alloc] initWithCapacity:0];
    curPicker = [[UIImagePickerController alloc] init];
    curPicker.delegate = self;
    curPicker.allowsEditing = NO;
    curPicker.sourceType = UIImagePickerControllerSourceTypeCamera;
    curPicker.showsCameraControls = NO;
    [self.viewController presentModalViewController:curPicker animated:YES];
    
    
    UIView * functionView = [[UIView alloc] initWithFrame:CGRectMake(0, self.viewController.view.frame.size.height-44, self.viewController.view.frame.size.width, 44)];
    [functionView setBackgroundColor:[UIColor blackColor]];
    numberLab = [[UILabel alloc] initWithFrame:CGRectMake(10, 7, 100, 30)];
    numberLab.textColor  = [UIColor whiteColor];
    numberLab.text = @"拍摄 0 张";
   
    [functionView addSubview:numberLab];
    
    
    
    UIButton  * takepicBtn = [[UIButton alloc] initWithFrame:CGRectMake(110, 7, 100, 30)];
    [takepicBtn setTitle:@"拍照" forState:0];
    [takepicBtn setTitleColor:[UIColor blueColor] forState:0];
    [takepicBtn addTarget:self action:@selector(clickTake:) forControlEvents:UIControlEventTouchUpInside];
    [functionView addSubview:takepicBtn];
    
    
    UIButton  * CancleBtn = [[UIButton alloc] initWithFrame:CGRectMake(210, 7, 100, 30)];
    [CancleBtn setTitle:@"完成" forState:0];
    [CancleBtn setTitleColor:[UIColor blueColor] forState:0];
    [CancleBtn addTarget:self action:@selector(clickCancle:) forControlEvents:UIControlEventTouchUpInside];
    [functionView addSubview:CancleBtn];
    
    
    
    
    
     curPicker.cameraOverlayView = functionView;
    


}
    
-(void)clickTake:(UIButton*)sender
{
    
    [curPicker takePicture];
    
}
-(void)clickCancle:(UIButton*)sender
{
    
    
    NSLog(@"imgArr:%@",imgArr);
    

    
    NSMutableDictionary * dic = [NSMutableDictionary dictionaryWithCapacity:0];
    [dic setValue:imgArr forKey:@"imgArr"];
    
    CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dic];
    
    
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.theCommand.callbackId];
    
    [curPicker dismissModalViewControllerAnimated:YES];
    


}
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingImage:(UIImage *)image editingInfo:(NSDictionary *)editingInfo
{
    
    
    takeNumber =  takeNumber +1;
    
    numberLab.text  =[NSString stringWithFormat:@"拍摄 %i 张",takeNumber];
    
    
    
    UIImage *saveImg = image;
    
    
    
    /*
    float strWidth = [waterStr sizeWithFont:[UIFont systemFontOfSize:30.0]].width;
    float strHeight = [waterStr sizeWithFont:[UIFont systemFontOfSize:30.0]].height;
    float oringX = saveImg.size.width - strWidth-30;
    float oringY = saveImg.size.height - strHeight-30;
    
    
    
    
    
    UIGraphicsBeginImageContextWithOptions(saveImg.size, NO, 0.0f);
    [saveImg drawAtPoint:CGPointMake(0.0f, 0.0f)];
    [[UIColor redColor] set];
    [waterStr drawAtPoint:CGPointMake(oringX, oringY) withFont:[UIFont systemFontOfSize:30.0]];
    UIImage *saveWaterImg = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
     NSData * saveImgData = UIImageJPEGRepresentation(saveWaterImg,0.1);
     
     
    */
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    
    //判断temp文件夹是否存在
    
    BOOL fileExists = [fileManager fileExistsAtPath:folderName];
    
    if (!fileExists) {//如果不存在时就创建,因为下载时,不会自动创建文件夹
        
        [fileManager createDirectoryAtPath:folderName
         
               withIntermediateDirectories:YES
         
                                attributes:nil
         
                                     error:nil];
        
    }
    
    
   
    
    NSData * saveImgData = UIImageJPEGRepresentation(saveImg,0.1);
    
    //NSLog(@"saveImgData %@",saveImgData);
    
    NSString * imgName = [NSString stringWithFormat:@"%@IOS.jpg",[TakePicPlugin getUniqueId]];
    
    BOOL isSuccess = [saveImgData writeToFile:[folderName stringByAppendingPathComponent:imgName] atomically:YES];
    
    
    NSMutableDictionary * imgInfoDic = [NSMutableDictionary dictionaryWithCapacity:0];
   // [imgInfoDic setValue:[folderName stringByAppendingPathComponent:imgName] forKey:@"ImgPath"];
    
    [imgInfoDic setValue:imgName forKey:@"ImgPath"];
    
    [imgInfoDic setValue:waterStr forKey:@"TakeTime"];
    [imgInfoDic setValue:showTimeStr forKey:@"showTime"];
    
    NSLog(@"isSuccess %i",isSuccess);
    
    //成功
    if (isSuccess) {
        [imgArr addObject:imgInfoDic];
    }
    else
    {
    
    
    
    }
   
    
}


+(NSString*)getUniqueId
{
    
    
    CFUUIDRef uuidRef =CFUUIDCreate(NULL);
    
    CFStringRef uuidStringRef =CFUUIDCreateString(NULL, uuidRef);
    
    CFRelease(uuidRef);
    
    NSString *uniqueId = (__bridge NSString *)uuidStringRef;
    
    
    
    return uniqueId;
    
  
    
}

@end
