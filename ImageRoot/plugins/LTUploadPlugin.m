//
//  LTUploadPlugin.m
//  ImageSys
//
//  Created by xue on 14-5-19.
//
//

#import "LTUploadPlugin.h"
static NSString * const FORM_FLE_INPUT = @"myFile";
#define BOUNDARY @"----------cH2gL6ei4Ef1KM7cH2KM7ae0ei4gL6"
@implementation LTUploadPlugin
-(id)init
{
    self = [super init];
    if (self) {
        
    }
    return self;

}



#pragma mark
-(void)LTUploadImage:(CDVInvokedUrlCommand *)command
{
    
    
    
    

    
    
    [self.commandDelegate runInBackground:^{
        
        
        self.theCommand = command;
        
        NSMutableDictionary * bodyDic = [NSMutableDictionary dictionaryWithDictionary:[command.arguments objectAtIndex:0]];
        
        NSMutableArray * imgPathArr = [[command.arguments objectAtIndex:0] objectForKey:@"images"];
        
        
        [bodyDic removeObjectForKey:@"images"];
        
        
        NSURL * url = [NSURL URLWithString:@"http://183.62.56.27:4011/ForImgUp.ashx"];
        
        
        
        //根据url初始化request
        NSMutableURLRequest* request = [NSMutableURLRequest requestWithURL:url];
        
        
        
        
        [request  setValue:[NSString stringWithFormat:@"multipart/form-data;boundary=%@", BOUNDARY] forHTTPHeaderField:@"Content-Type"];
        
        
        
        NSMutableData * bodyData = [NSMutableData dataWithCapacity:0];
        
        
        //参数的集合的所有key的集合
        NSArray *keys= [bodyDic allKeys];
        
        //遍历参数keys
        for(int i=0;i<[keys count];i++)
        {
            //得到当前key
            NSString *key=[keys objectAtIndex:i];
            NSString * content = [bodyDic objectForKey:key];
            
            NSString *param = [NSString stringWithFormat:@"--%@\r\nContent-Disposition: form-data; name=\"%@\"\r\n\r\n%@\r\n",BOUNDARY,key,content,nil];
            
            [bodyData appendData:[param dataUsingEncoding:NSUTF8StringEncoding]];
            
            
            
            
        }
        
        
        
        
        NSString * fileName =@"";
        
        NSData * imgData = nil;
        
        
        UIImage * originImg = nil;
        
        NSString * documentPath = [NSHomeDirectory() stringByAppendingPathComponent:@"Documents"];
        
        
        
        
        
        
        for (int i =0; i<[imgPathArr count]; i++) {
            
            fileName = [[imgPathArr objectAtIndex:i] lastPathComponent];
            
            
            NSLog(@"fileName %@",fileName);
            
            NSString *param = [NSString stringWithFormat:@"--%@\r\nContent-Disposition: form-data; name=\"%@\";filename=\"%@\"\r\nContent-Type: application/octet-stream\r\n\r\n",BOUNDARY,FORM_FLE_INPUT,fileName,nil];
            [bodyData appendData:[param dataUsingEncoding:NSUTF8StringEncoding]];
            
            originImg = [UIImage imageWithContentsOfFile:[documentPath stringByAppendingPathComponent:fileName]];
            imgData = UIImageJPEGRepresentation(originImg,0.3);
            [bodyData appendData:imgData];
            [bodyData appendData:[@"\r\n" dataUsingEncoding:NSUTF8StringEncoding]];
            
            
            
            
            
        }
        
        NSString *endString = [NSString stringWithFormat:@"--%@--",BOUNDARY];
        
        [bodyData appendData:[endString dataUsingEncoding:NSUTF8StringEncoding]];
        
        
        NSString *postLength = [NSString stringWithFormat:@"%d", [bodyData
                                                                  length]];
        
        [request setValue:postLength forHTTPHeaderField:@"Content-Length"];
        
        [request setHTTPMethod:@"POST"];
        
        [request setHTTPBody:bodyData];
        
        NSHTTPURLResponse *urlResponese = nil;
        NSError *error = [[NSError alloc]init];
        NSData* resultData = [NSURLConnection sendSynchronousRequest:request returningResponse:&urlResponese error:&error];
        
        
        if (resultData==nil)
        {
            
            NSLog(@"失败");
            
            CDVPluginResult *pluginResult = [ CDVPluginResult
                                             resultWithStatus : CDVCommandStatus_ERROR
                                             ];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:self.theCommand.callbackId];
        }
        else
        {
           
            
            NSMutableDictionary * dic = [NSJSONSerialization JSONObjectWithData:resultData options:NSJSONReadingMutableContainers error:nil];
            
            
            NSLog(@"dic %@",dic);
            
            NSString * str = [[NSString alloc] initWithData:resultData encoding:NSUTF8StringEncoding];
            
            
            NSLog(@"upLoad back%@",str);
            
            
            CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dic];
            
          //  CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:str];
            
            [self.commandDelegate sendPluginResult:pluginResult callbackId:self.theCommand.callbackId];
            
            
            
            
        }
        
        
        
        
        
    }];
    
    
    
    
    
    


}



/*
-(void)LTUploadImage:(CDVInvokedUrlCommand *)command
{
    
    self.theCommand = command;
    
    NSMutableDictionary * bodyDic = [NSMutableDictionary dictionaryWithDictionary:[command.arguments objectAtIndex:0]];
    
    
    NSLog(@"bodyDic:%@",bodyDic);
    
    
    NSMutableArray * imgPathArr = [[command.arguments objectAtIndex:0] objectForKey:@"images"];

    
    [bodyDic removeObjectForKey:@"images"];
    
    NSLog(@"bodyDic after%@",bodyDic);
    
    
    
    
    
    //根据url初始化request
    NSMutableURLRequest* request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:[@"http://183.62.56.27:4011/ForImgUp.ashx" stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding]]];
    
    [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    
    
    NSMutableData * bodyData = [NSMutableData data];
    
    
    
    //参数的集合的所有key的集合
    NSArray *keys= [bodyDic allKeys];
    
    //遍历参数keys
    for(int i=0;i<[keys count];i++)
    {
        //得到当前key
        NSString *key=[keys objectAtIndex:i];
        
        
        
        NSLog(@"key %@",key);
        
        
        NSString * content = [bodyDic objectForKey:key];
        
        NSString *param = [NSString stringWithFormat:@"--%@\r\nContent-Disposition: form-data; name=\"%@\"\r\n\r\n%@\r\n",BOUNDARY,key,content,nil];
        
        [bodyData appendData:[param dataUsingEncoding:NSUTF8StringEncoding]];
        
        
    }
    
    
    NSString * fileName =@"";
    
    NSData * imgData = nil;
    
    
    UIImage * originImg = nil;
    
    NSString * documentPath = [NSHomeDirectory() stringByAppendingPathComponent:@"Documents"];
 
    for (int i =0; i<[imgPathArr count]; i++) {
        
        
        
        NSDateFormatter * formatter = [[NSDateFormatter alloc] init];
        
        [formatter setDateFormat:@"yyyyMMDDHHmmss"];
        
        
        NSDate * date = [NSDate date];
         //声明pic字段，文件名由时间确保唯一
        fileName = [NSString stringWithFormat:@"%@",[formatter stringFromDate:date]];
        
        
        
        NSLog(@"fileName %@",fileName);
     
        
        
        NSString *param = [NSString stringWithFormat:@"--%@\r\nContent-Disposition: form-data; name=\"%@\";filename=\"%@\"\r\nContent-Type: application/octet-stream\r\n\r\n",BOUNDARY,FORM_FLE_INPUT,fileName,nil];
        
        [bodyData appendData:[param dataUsingEncoding:NSUTF8StringEncoding]];
        
        
        fileName = [[imgPathArr objectAtIndex:i] lastPathComponent];
        originImg = [UIImage imageWithContentsOfFile:[documentPath stringByAppendingPathComponent:fileName]];
  
        imgData = UIImageJPEGRepresentation(originImg,0.3);
        
        
        [bodyData appendData:imgData];
       
        [bodyData appendData:[@"\r\n" dataUsingEncoding:NSUTF8StringEncoding]];
        
      
        
    }
 
    NSString *endString = [NSString stringWithFormat:@"--%@--",BOUNDARY];
   
    [bodyData appendData:[endString dataUsingEncoding:NSUTF8StringEncoding]];
    
    
    NSString * bodyStr = [[NSString alloc] initWithData:bodyData encoding:NSUTF8StringEncoding];
    
    
    NSLog(@"bodyStr %@",bodyStr);
    
    
    
    [request setHTTPBody:bodyData];
    [request setHTTPMethod:@"POST"];
    
    
   
    
   
        //http method
    
    
    
    NSHTTPURLResponse *urlResponese = nil;
    NSError *error = [[NSError alloc]init];
    NSData* resultData = [NSURLConnection sendSynchronousRequest:request returningResponse:&urlResponese error:&error];
    
    
    if (resultData==nil) {
        
        NSLog(@"str  失败");
        
        CDVPluginResult *pluginResult = [ CDVPluginResult
                                         resultWithStatus    : CDVCommandStatus_ERROR
                                         ];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:self.theCommand.callbackId];
    }
    else
    {
        NSString * str = [[NSString alloc] initWithData:resultData encoding:NSUTF8StringEncoding];
        
        
        NSLog(@"str  %@",str);
        
//        CDVPluginResult * pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:str];
//        
//        [self.commandDelegate sendPluginResult:pluginResult callbackId:self.theCommand.callbackId];
        
        
    
    
    }
    
   
    
}
*/



@end
