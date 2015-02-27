//
//  HighwayInfoAtMapVC.h
//  HighWay_iPhone
//
//  地图模式显示高速公路信息
// 
//  Created by wanggp on 14/7/3.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"
#import "BMapKit.h"
#import "StationPointAnnotation.h"
#import "StationCalloutAnnotation.h"
#import "StationCalloutAnnotationView.h"


@interface HighwayInfoAtMapVC : BaseVC<BMKMapViewDelegate,BMKRouteSearchDelegate>
{
     StationCalloutAnnotation *_stationCalloutAnnotation;
    NSInteger stationCount;
    BMKRouteSearch* _routesearch;
    CLLocationCoordinate2D startStationCoor;
    CLLocationCoordinate2D endStationCoor;
    BOOL isFirstWrite;
    
    NSMutableArray *saveToFileAry;
}



@property (nonatomic,strong) NSString *roadId;
@property (nonatomic,strong) NSString *roadName;
@property (nonatomic,strong) NSArray *stationArray;
@property (nonatomic,strong) NSArray *speedArray;
@property (nonatomic,strong) NSArray *accidentArray;
@property (nonatomic,strong) NSArray *constructArray;
@property (nonatomic,strong) NSArray *serviceArray;


@property (nonatomic,strong) IBOutlet BMKMapView *mapView;
- (IBAction)zoomIn:(id)sender;
- (IBAction)zoomOut:(id)sender;

@end
