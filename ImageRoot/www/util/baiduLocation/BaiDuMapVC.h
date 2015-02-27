//
//  BaiDuMapVC.h
//  LTPlatform
//
//  Created by xue on 14-3-14.
//
// 百度地图

#import <UIKit/UIKit.h>
#import "BMapKit.h"
#include <MapKit/MapKit.h>

@protocol BaiDuMapVCDelegate <NSObject>

-(void)getUserCLLocation:(CLLocation*)sender;
-(void)getUserCLLocationError:(NSError*)error;
-(void)reverseGeocodeUserLocation:(BMKAddrInfo*)addrInfo;
-(void)reverseGeocodeUserLocationError:(NSError*)error;
@end




@interface BaiDuMapVC : UIViewController<BMKMapViewDelegate,BMKSearchDelegate>
{
    

}
@property(nonatomic,assign)id<BaiDuMapVCDelegate>delegate;
@property(nonatomic,assign)BOOL isReturnChinese;//是否返回中文位置信息
@property(nonatomic,strong)BMKMapView * BMapView;
@property(nonatomic,strong)BMKSearch * searcher;
-(void)showLocation;

@end
