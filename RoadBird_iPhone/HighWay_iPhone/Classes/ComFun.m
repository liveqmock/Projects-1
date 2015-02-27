#import "ComFun.h"
#import <MessageUI/MessageUI.h>
#import "MBProgressHUD.h"


@implementation ComFun


+ (void)alertWithMessage:(NSString *)message
{
	/* open an alert with an OK button */
	UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"" 
													message:message
												   delegate:nil 
										  cancelButtonTitle:@"确定" 
										  otherButtonTitles: nil];
	[alert show];
}


+ (void)alertWithMessageAndDelegate:(NSString *)title:(NSString *)message:(id)delegate
{
    if([NSString isEmpty:title])
        title=@"";
	/* open an alert with OK and Cancel buttons */
	UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title 
													message:message
												   delegate:delegate 
										  cancelButtonTitle:@"取消" 
										  otherButtonTitles: @"确定", nil];
    
	[alert show];
}

+ (void)alertWithMessageAndDelegateAndTitle:(NSString *)title:(NSString *)message:(id )delegate:(NSString *)cancelButtonTitle:(NSString *)otherButtonTitles
{
    if([NSString isEmpty:title])
        title=@"";
    if([NSString isEmpty:cancelButtonTitle])
        cancelButtonTitle=@"取消";
    if([NSString isEmpty:otherButtonTitles])
        otherButtonTitles=@"确定";
	/* open an alert with OK and Cancel buttons */
	UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title
													message:message
												   delegate:delegate
										  cancelButtonTitle:cancelButtonTitle
										  otherButtonTitles: otherButtonTitles, nil];
    
	[alert show];
}





+(int)IndexOfAry:(NSArray *)ary:(NSString *)s
{
	for (int i = 0; i < ary.count; i++) {
		if([s isEqualToString:[ary objectAtIndex:i]])
			return i;
	}
    return -1;
}

+(NSString *)nil2defaultvalue:(NSString *)s
{
    if(!s)
        return @"";
    else
        return s;
}

+(id)getObjOfAry:(NSArray *)ary:(int)idx
{
	if(idx<ary.count)
		return [ary objectAtIndex:idx];
	else
		return nil;
}

+(NSString*) getStrOfAry:(NSArray *)ary:(int)idx{
	if(idx<ary.count)
		return [ary objectAtIndex:idx];
	else
		return @"";	
}

+(int)getCharAtWithDefault:(NSString *)s:(int)i:(int)defValue{
	if(i<[s length])
		return [s characterAtIndex:i]-'0';
	else
		return defValue;
}

+(int)getCharAt:(NSString *)s:(int)i
{
	return [ComFun getCharAtWithDefault:s:i:'0'];
}

+(NSString*) getJsonString:(NSDictionary *)ary:(NSString *)fld{
	return [ComFun getJsonStringWithDefaultValue:ary:fld:@""];
}

+(NSString*)getJsonStringWithDefaultValue:(NSDictionary *)ary:(NSString *)fld:(NSString *)defValue{
	if([ary valueForKey:fld]==NULL || [ary valueForKey:fld]==[NSNull null])
		return defValue;
	else
		return [ary valueForKey:fld];
}

+(NSString*)removeWithSpanceFromNSString:(NSString *)s
{
	if([NSString isEmpty:s])
	{
		return @"";
	}
	else
	{
		NSArray *comps = [s componentsSeparatedByCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
		NSMutableArray *array = [NSMutableArray array];
		for(NSString *comp in comps)
		{
			if([comp length]>0)
				[array addObject:comp];
		}
		return [array componentsJoinedByString:@""];
	}
}




+(int)indexOf:(NSString *)s:(NSString *)subStr
{
	NSRange r=[s rangeOfString:subStr];
	if (r.location!=NSNotFound) {
		return r.location;
	}else {
		return -1;
	}
    
}

+(NSString*)getFileIcon:(NSString *)sExt{
	NSString *fn;
	if([sExt isEqualToString:@"jpg"] || [sExt isEqualToString:@"jpeg"])
		fn=@"fJpg.png";
	else if([sExt isEqualToString:@"gif"])
		fn=@"fGif.png";
	else if([sExt isEqualToString:@"png"])
		fn=@"fPng.png";
	else if([sExt isEqualToString:@"tiff"] || [sExt isEqualToString:@"tif"])
		fn=@"fTif.png";
	else if([sExt isEqualToString:@"doc"] || [sExt isEqualToString:@"docx"])
		fn=@"fWord.png";
	else if([sExt isEqualToString:@"xls"] || [sExt isEqualToString:@"xlsx"])
		fn=@"fXls.png";
	else if([sExt isEqualToString:@"pdf"])
		fn=@"fPdf.png";
	else if([sExt isEqualToString:@"ppt"])
		fn=@"fPpt.png";
	else if([sExt isEqualToString:@"zip"] || [sExt isEqualToString:@"rar"])
		fn=@"fZip.png";
	else if([sExt isEqualToString:@"txt"])
		fn=@"fTxt.png";
	else if([sExt isEqualToString:@"html"] || [sExt isEqualToString:@"htm"]
            || [sExt isEqualToString:@"jsp"] || [sExt isEqualToString:@"php"]
            || [sExt isEqualToString:@"asp"] || [sExt isEqualToString:@"aspx"])
		fn=@"fHtm.png";
	else if([sExt isEqualToString:@"xml"] || [sExt isEqualToString:@"xls"])
		fn=@"fXml.png";
	else if([sExt isEqualToString:@"bmp"])
		fn=@"fBmp.png";
	else
		fn=@"fOther.png";
	return fn;
}



+(NSString*)documentFilePath:(NSString *)fileName {
    
	//获取文件的document文件夹的路径.
	NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
	NSString *documentDiretory = [paths objectAtIndex:0];
	//追加一个路径
	return [documentDiretory stringByAppendingPathComponent:fileName];
}


+(NSString*)bundleFilePath:(NSString *)fileName
{
    NSString *resourcePath = [[NSBundle mainBundle] resourcePath];
    NSString *fpath = [resourcePath stringByAppendingPathComponent:fileName];
    return  fpath;
}


+(NSString *)getFileType:(NSString *)fileName
{
    if([NSString isEmpty:fileName])
        return @"";
    NSRange r=[fileName rangeOfString:@"." options:NSBackwardsSearch];
	r.location++;
	NSString *fileType=[fileName substringWithRange:NSMakeRange(r.location, [fileName length]-r.location)];
	fileType = [fileType lowercaseString];
    return fileType;
}


+(NSString *) getURLTypeParam:(NSString *)url:(NSString *)paramName{
    if([NSString isEmpty:url]) return @"";
    NSString *ret=@"";
    NSString *parten=[NSString stringWithFormat:@"&%@=",paramName];
    NSRange range=[url rangeOfString:parten];
    int i=range.location;
    if(i!=NSNotFound){
        i+=parten.length;
        NSString *tmp=[url substringFromIndex:i];
        range=[tmp rangeOfString:@"&"];
        if(range.location!=NSNotFound)
            ret=[tmp substringToIndex:range.location];
        else
            ret=tmp;
    }
    return ret;
}

+(NSUInteger)unicodeLengthOfString:(NSString *)text  {
    NSUInteger asciiLength = 0;
    
    for (NSUInteger i = 0; i < text.length; i++) {
        unichar uc = [text characterAtIndex: i];
        
        asciiLength += isascii(uc) ? 1 : 2;
    }
    
    NSUInteger unicodeLength = asciiLength / 2;
    
    if(asciiLength % 2) {
        unicodeLength++;
    }
    
    return unicodeLength;
}



+(NSString*) uuid
{
    CFUUIDRef uuidRef = CFUUIDCreate(kCFAllocatorDefault);
    CFStringRef sRef = CFUUIDCreateString(kCFAllocatorDefault, uuidRef);
    NSString *uuid=[NSString stringWithFormat:@"%@", sRef];
    CFRelease(uuidRef);
    CFRelease(sRef);
    return uuid;
}




+(int)strLength:(NSString *)strtemp{
    
    int strlength = 0;
    char* p = (char*)[strtemp cStringUsingEncoding:NSUnicodeStringEncoding];
    for (int i=0 ; i<[strtemp lengthOfBytesUsingEncoding:NSUnicodeStringEncoding] ;i++) {
        if (*p) {
            p++;
            strlength++;
        }
        else {
            p++;
        }
    }
    return (strlength+1)/2;
    
}

+(BOOL)StrIsNullByTrim:(NSString *)strtemp{
    if ([NSString isEmpty:strtemp]) {
        return YES;
    }
    strtemp = [strtemp stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]];
    return [NSString isEmpty:strtemp];
}

#pragma mark - 


//查询今天发生的事件
+(NSMutableArray*)selectTodayevent:(id)result
{
    NSMutableArray* returnAry = [[NSMutableArray alloc] init];
    NSString *queryTime=[NSDate formateDate:[NSDate date] fromate:@"yyyy-MM-dd HH:mm:ss"];
    //判断显示两小时的交通事故
    for (NSDictionary* dic in result) {
        NSDateFormatter *dateForm = [[NSDateFormatter alloc] init];
        [dateForm setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
        NSDate *occurTime = [dateForm dateFromString:[dic objectForKey:@"occurTime"]];
        NSDate *nowTime = [dateForm dateFromString:queryTime];
        float timeDiff = [nowTime timeIntervalSinceDate:occurTime];
        if (timeDiff<7200) {
            [returnAry addObject:dic];
        }
    }
    return returnAry;
}

//查找两个站之间的具体速度
+(int)findSpeed:(NSArray *)speedAry startStationId:(NSString *)startStationId endStationId:(NSString *)endStationId
{
    int speed = 100;
    for (NSString *stationSpeedStr in speedAry) {
        NSArray *stationSpeedAry =[stationSpeedStr componentsSeparatedByString:@"|"];
        if([stationSpeedAry count]==3&&[startStationId isEqualToString:[stationSpeedAry objectAtIndex:0] ]&&[endStationId isEqualToString:[stationSpeedAry objectAtIndex:1] ])
            speed = [[stationSpeedAry objectAtIndex:2] intValue];
        
    }
    return speed;
}

// 获取当前位置的详细信息（定位到设备的街道信息）
+(void)getDetailLocation:(BMKPointAnnotation*)PointAnnotation
{
    CLLocation *location = [[CLLocation alloc] initWithLatitude:PointAnnotation.coordinate.latitude longitude:PointAnnotation.coordinate.longitude];
    CLGeocoder *geocoder =[[CLGeocoder alloc] init];
    [geocoder reverseGeocodeLocation:location completionHandler:^(NSArray *placemarks,NSError *error){
        if(error)
        {
            return;
        }
        if(placemarks.count>0)
        {
            
            CLPlacemark *placemark = [placemarks objectAtIndex:0];
            if([placemark.addressDictionary objectForKey:@"Name"] != nil )
            {
                PointAnnotation.subtitle = [placemark.addressDictionary objectForKey:@"Name"];
            }
            else
            {
                PointAnnotation.subtitle = @"找不到当前位置";
            }
        }
    }];
    PointAnnotation.title = @"当前位置";
}

// 判断字符串是否是纯数字
+ (BOOL)isPureInt:(NSString *)string{
    NSScanner* scan = [NSScanner scannerWithString:string];
    int val;
    return [scan scanInt:&val] && [scan isAtEnd];
}

@end
