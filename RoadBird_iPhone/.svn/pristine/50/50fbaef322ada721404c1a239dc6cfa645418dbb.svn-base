//
//  DirvingRouteDetailMapVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14/7/8.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"
#import "BMapKit.h"
#import "StationPointAnnotation.h"
#import "StationCalloutAnnotation.h"
#import "StationCalloutAnnotationView.h"
#import "CurrentPointAnnotation.h"

@interface DirvingRouteDetailMapVC : BaseVC<BMKMapViewDelegate,BMKLocationServiceDelegate,BMKRouteSearchDelegate>
{
    int stationCount;
    StationCalloutAnnotation *_stationCalloutAnnotation;
    BMKRouteSearch *_searcher;
    CLLocationCoordinate2D endStationCoor; //终点站经纬度，用来判断路径到终点站的距离
    NSMutableArray* polylines; //高速路内部覆盖物数组
    
    CurrentPointAnnotation *currentPointAnnotation; //当前位置标注
}

@property (nonatomic,strong) NSMutableArray *stationArray;
@property (nonatomic,strong) IBOutlet BMKMapView *mapView;
@property (nonatomic,strong) NSArray* accidentAry;
@property (nonatomic,strong) NSArray* fixAry;
@property (nonatomic,strong) NSArray* serviceAry;
@property (nonatomic,strong) NSArray* wayPointsAry; //跨路段的站
@property (nonatomic,strong) NSArray* allRoadIDs;
@property (nonatomic,strong) NSDictionary* sortStationDic; //按roadid分类好的站
@property (nonatomic,strong) NSDictionary* sortSpeedDic; //按roadid分类好的速度

- (IBAction)zoomIn:(id)sender;
- (IBAction)zoomOut:(id)sender;
@end
