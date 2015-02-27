#import <UIKit/UIKit.h>



@interface ComFun :NSObject




+ (void) alertWithMessage:(NSString *)message;
+ (void)alertWithMessageAndDelegate:(NSString *)title:(NSString *)message:(id)delegate;
+ (void)alertWithMessageAndDelegateAndTitle:(NSString *)title:(NSString *)message:(id )delegate:(NSString *)cancelButtonTitle:(NSString *)otherButtonTitles;





+(NSString *)nil2defaultvalue:(NSString *)s;
+(int)IndexOfAry:(NSArray *)ary:(NSString *)s;

+(id)getObjOfAry:(NSArray *)ary:(int)idx;

+(NSString*) getStrOfAry:(NSArray *)ary:(int)idx;

+(int)getCharAtWithDefault:(NSString *)s:(int)i:(int)defValue;

+(int)getCharAt:(NSString *)s:(int)i;

+(NSString*) getJsonString:(NSDictionary *)ary:(NSString *)fld;

+(NSString*)getJsonStringWithDefaultValue:(NSDictionary *)ary:(NSString *)fld:(NSString *)defValue;

+(NSString*)removeWithSpanceFromNSString:(NSString *)s;




+(int)indexOf:(NSString *)s:(NSString *)subStr;

+(NSString*)getFileIcon:(NSString *)sExt;



+(NSString*)documentFilePath:(NSString *)fileName ;


+(NSString*)bundleFilePath:(NSString *)fileName;


+(NSString *)getFileType:(NSString *)fileName;


+(NSString *) getURLTypeParam:(NSString *)url:(NSString *)paramName;

+(NSUInteger)unicodeLengthOfString:(NSString *)text;



+(NSString*) uuid;




+(int)strLength:(NSString *)strtemp;

+(BOOL)StrIsNullByTrim:(NSString *)strtemp;


#pragma mark -

//查询今天发生的事件（交通事故、道路施工）
+(NSMutableArray*)selectTodayevent:(id)result;

//查找两个站之间的具体速度
+(int)findSpeed:(NSArray *)speedAry startStationId:(NSString *)startStationId endStationId:(NSString *)endStationId;

//获取详细街道信息
+(void)getDetailLocation:(BMKPointAnnotation*)PointAnnotation;

//判断字符串是否是纯数字
+ (BOOL)isPureInt:(NSString *)string;
@end

