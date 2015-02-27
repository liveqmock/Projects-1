//
//  GaodeMapViewController.h
//  LTPlatform
//
//  Created by xue on 14-3-14.
//
// 高德地图

#import <UIKit/UIKit.h>
#include <MapKit/MapKit.h>


@protocol GaodeMapViewControllerDelegat <NSObject>

-(void)getGaoDeMKUserLocation:(MKUserLocation*)userLocation;

-(void)getGaoDePlacemarksArray:(NSArray*)placemarksArray;

@end





@interface GaodeMapViewController : UIViewController<CLLocationManagerDelegate,MKMapViewDelegate>
{
    
    MKMapView * _appleMap;
    CLGeocoder * _geocoder;
    CLLocationManager * _manager;
    
    int _locationTime;
    
}
@property(nonatomic,assign)id<GaodeMapViewControllerDelegat>delegate;
@property(nonatomic,assign)BOOL isReturnInfo;
-(void)showUserLocation;
@end

