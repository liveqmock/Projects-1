
#import <Foundation/Foundation.h>

@interface ChinaWeatherResult : NSObject

@property(nonatomic,strong)NSString*temperatureDay;
@property(nonatomic,strong)NSString*temperatureNight;

@property(nonatomic,strong)NSString*weatherInfo;
@property(nonatomic,strong)NSString*cityName;
@property(nonatomic,strong)NSString*imgNameDay;
@property(nonatomic,strong)NSString*imgNameNight;





-(NSURL*)getDayUrl;
-(NSURL*)getNightUrl;
-(NSURL*)getNowUrl;

@end
