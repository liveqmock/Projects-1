

#import "ChinaWeatherResult.h"
#define kPicRoot @"http://www.weather.com.cn/m/i/weatherpic/29x20/"


@implementation ChinaWeatherResult
{
     
}
@synthesize temperatureDay=temperatureDay;
@synthesize temperatureNight=temperatureNight;

@synthesize weatherInfo=_weatherInfo;
@synthesize cityName=_cityName;
@synthesize imgNameDay=_imgNameDay;
@synthesize imgNameNight=_imgNameNight;





-(NSURL*)getDayUrl{
    NSString*stringUrl=[NSString stringWithFormat:@"%@%@",kPicRoot,_imgNameDay];
    NSURL*returnUrl=[NSURL URLWithString:stringUrl];
    return returnUrl;
}
-(NSURL*)getNightUrl{
    NSString*stringUrl=[NSString stringWithFormat:@"%@%@",kPicRoot,_imgNameNight];
    NSURL*returnUrl=[NSURL URLWithString:stringUrl];
    return returnUrl;
}
-(NSURL*)getNowUrl{
    return [self getDayUrl];
}
@end
