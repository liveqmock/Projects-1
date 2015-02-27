//
//  SelStationByMapVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/5.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"
#import "BMapKit.h"
#import "StationPointAnnotation.h"
#import "StationCalloutAnnotation.h"
#import "StationCalloutAnnotationView.h"
#import "CurrentPointAnnotation.h"

@protocol SelStationByMapDelegate <NSObject>

- (void)selStationCallBack:(NSDictionary *)stationDic;

@end



@interface SelStationByMapVC : BaseVC<BMKMapViewDelegate,UIActionSheetDelegate>
{
    StationCalloutAnnotation *_stationCalloutAnnotation;
    NSArray *roadArray; // 高速公路列表
    NSDictionary *selStationDic;
    
    CurrentPointAnnotation *currentPointAnnotation;
}

@property (nonatomic,strong) NSArray *stationArray;
@property (nonatomic,strong) IBOutlet BMKMapView *mapView;
@property (nonatomic) BOOL isStart; //判断这次是要选择起始站，还是终点站


@property (nonatomic,weak) id<SelStationByMapDelegate> delegate;


- (IBAction)zoomIn:(id)sender;
- (IBAction)zoomOut:(id)sender;

@end