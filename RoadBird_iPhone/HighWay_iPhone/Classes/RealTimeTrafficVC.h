//
//  RealTimeTrafficVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-7.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"
#import "BMapKit.h"
#import "StationPointAnnotation.h"
#import "StationCalloutAnnotation.h"
#import "StationCalloutAnnotationView.h"
#import "AccidentPointAnnotation.h"
#import "ConstructPointAnnotation.h"
#import "QueryDrivingRouteVC.h"
#import "ServicePointAnnotation.h"
#import "CurrentPointAnnotation.h"


@interface RealTimeTrafficVC : BaseVC<BMKMapViewDelegate,BMKLocationServiceDelegate,QueryWeatherCityDelegate,UIActionSheetDelegate>
{
    // 定位服务
    BMKLocationService* _locService;
    //当前位置
    CurrentPointAnnotation *currentPointAnnotation;
    
    // 附近路的ID
    NSArray *nearroadIds;
    // 附近高速公路
    NSArray *nearStationIds;
    StationCalloutAnnotation *_stationCalloutAnnotation;
    
    // 速度
    NSArray *speedArray;
    BMKPolyline *routeLine;
    
    NSMutableArray *_tollArray; //收费站
    NSArray *_accidentArray; // 交通事故
    NSArray *_constructArray; // 施工信息

    // 路
    BOOL allRoadFlag;
    NSMutableArray *roadOverlayAry;

    //判断是否要清除上一次location
    BOOL isFirstShowCurrentLocation;
    
    UIView *statusView; //

    CLLocationManager *locationManager;

    NSMutableArray *serviceStationAnnotationArray;
    NSMutableArray *constructPointAnnotationAry;
    NSMutableArray *tollStationAnnotationAry;
    NSMutableArray *accidentPointAnnotationAry;
    NSMutableArray *nearRoadLine;
    
    NSDictionary *tempStationDic; //设置给行程页的站信息

}


@property (nonatomic,strong) IBOutlet BMKMapView *mapView;
@property (nonatomic,strong) UIButton *allRoadBtn;

@property BOOL isSetMapSpan;



// 显示当前位置
-(IBAction)locateCurrentPosition:(id)sender;


//放大地图
- (IBAction)ZoomIn:(id)sender;

//缩小地图
- (IBAction)ZoomOut:(id)sender;

@end
