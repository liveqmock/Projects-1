//
//  SelStationByMapVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/5.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "SelStationByMapVC.h"
#import "AppDelegate.h"



@implementation SelStationByMapVC

- (void)viewDidLoad
{
    [super viewDidLoad];
   self.navigationItem.title=@"选择站点";
    // 设置地图显示中心位置
    NSDictionary *station = [self.stationArray objectAtIndex:0];
    float XCODE=[[station valueForKey:@"xcode"] floatValue];
    float YCODE =[[station valueForKey:@"ycode"] floatValue];
    CLLocationCoordinate2D coordinate = {YCODE,XCODE};
    [_mapView setCenterCoordinate:coordinate animated:YES];
    
    [self.mapView setFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
    [self.mapView becomeFirstResponder];

}

-(void)viewWillAppear:(BOOL)animated {
    [_mapView viewWillAppear];
    _mapView.delegate = self;  // 此处记得不用的时候需要置nil，否则影响内存的释放
    
}

-(void)viewWillDisappear:(BOOL)animated {
    [_mapView viewWillDisappear];
    _mapView.delegate = nil; // 不用时，置nil
    
    [self.mapView removeAnnotation:currentPointAnnotation];
}

//添加标注
- (void)addAnnotation
{
    NSMutableArray *annotations =[NSMutableArray new];
    StationPointAnnotation *pointAnnotaion;
    for (NSDictionary *station in _stationArray ) {
        pointAnnotaion = [[StationPointAnnotation alloc] init];
        float XCODE=[[station valueForKey:@"xcode"] floatValue];
        float YCODE =[[station valueForKey:@"ycode"] floatValue];
        CLLocationCoordinate2D coordinate = {YCODE,XCODE};
        pointAnnotaion.coordinate=coordinate;
        pointAnnotaion.title=[station valueForKey:@"stationname"];
        pointAnnotaion.stationCalloutInfo=station;
        [annotations addObject:pointAnnotaion];
    }
    
    [self.mapView addAnnotations:annotations];
}

- (IBAction)zoomIn:(id)sender {
    [_mapView zoomIn];
}

- (IBAction)zoomOut:(id)sender {
    [_mapView zoomOut];
}

#pragma  mark - BMKMapViewDelegate

-(void)mapViewDidFinishLoading:(BMKMapView *)mapView{
    [self addAnnotation];
    //当前位置标注
    currentPointAnnotation = [[CurrentPointAnnotation alloc] init];
    currentPointAnnotation.coordinate = [CommonData sharedCommonData].currentLocationCoordinate2D;
    [ComFun getDetailLocation:currentPointAnnotation];
    [self.mapView addAnnotation:currentPointAnnotation];
}

- (BMKAnnotationView *)mapView:(BMKMapView *)mapView viewForAnnotation:(id <BMKAnnotation>)annotation
{
    if([annotation isKindOfClass:[StationPointAnnotation class]])
    {
        static NSString *annotaionIdentifier = @"pointAnnotaion";
        BMKAnnotationView *annotationView=[self.mapView dequeueReusableAnnotationViewWithIdentifier:annotaionIdentifier];
        if(!annotationView)
            annotationView = [[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:annotaionIdentifier];
        
        annotationView.image=[UIImage imageNamed:@"toll-map"];
        annotationView.canShowCallout=NO;
        return annotationView;
    }
    else if([annotation isKindOfClass:[StationCalloutAnnotation class]])
    {
        //此时annotation就是我们calloutview的annotation
        StationCalloutAnnotation *ann = (StationCalloutAnnotation*)annotation;
        
        static NSString *stationCalloutAnnotationViewIdentifier = @"StationCalloutAnnotationView";
        //如果可以重用
        StationCalloutAnnotationView *calloutannotationview = (StationCalloutAnnotationView *)[mapView dequeueReusableAnnotationViewWithIdentifier:stationCalloutAnnotationViewIdentifier];
        
        //否则创建新的calloutView
        if (!calloutannotationview) {
            calloutannotationview = [[StationCalloutAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:stationCalloutAnnotationViewIdentifier] ;
        }
        
        //显示站名
        selStationDic =ann.locationInfo;
        
        //该标注点与当前位置的直线距离
        int distance =  BMKMetersBetweenMapPoints(BMKMapPointForCoordinate([CommonData sharedCommonData].currentLocationCoordinate2D),BMKMapPointForCoordinate(annotation.coordinate));
        calloutannotationview.stationNameLabel.text=[[NSString alloc] initWithFormat:@"%@\n",[ann.locationInfo objectForKey:@"stationname"]];
        calloutannotationview.distanceLabel.text=[[NSString alloc] initWithFormat:@"离我直线行程%0.1fkm",distance/1000.0];

        return calloutannotationview;
        
    }
    else if([annotation isKindOfClass:[CurrentPointAnnotation class]])//当前位置的PointAnnotation
    {
        static NSString *annotaionIdentifier = @"currentPointAnnotation";
        BMKAnnotationView *annotationView=[self.mapView dequeueReusableAnnotationViewWithIdentifier:annotaionIdentifier];
        if(!annotationView)
            annotationView = [[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:annotaionIdentifier];
        
        annotationView.image=[UIImage imageNamed:@"GPS-Station"];
        annotationView.canShowCallout=YES;
        return annotationView;
        
    }

    
    return nil;
}


- (void)mapView:(BMKMapView *)mapView didSelectAnnotationView:(BMKAnnotationView *)view
{
    StationPointAnnotation *annn = (StationPointAnnotation *)view.annotation;
    if ([view.annotation isKindOfClass:[StationPointAnnotation class]]) {
        
        //如果当前显示着calloutview，又触发了select方法，删除这个calloutview annotation
        if (_stationCalloutAnnotation) {
            [mapView removeAnnotation:_stationCalloutAnnotation];
            _stationCalloutAnnotation=nil;
            
        }
        //创建搭载自定义calloutview的annotation
        _stationCalloutAnnotation = [[StationCalloutAnnotation alloc] initWithLatitude:view.annotation.coordinate.latitude andLongitude:view.annotation.coordinate.longitude] ;
        
        //把通过marker(ZNBCPointAnnotation)设置的pointCalloutInfo信息赋值给CalloutMapAnnotation
        _stationCalloutAnnotation.locationInfo = annn.stationCalloutInfo;
        
        [mapView addAnnotation:_stationCalloutAnnotation];
        [mapView setCenterCoordinate:view.annotation.coordinate animated:YES];
        
    }
    else if([view isKindOfClass:[StationCalloutAnnotationView class]]){ //收费站气泡(只能响应一次)
        NSString *selTitle;
        if (self.isStart)
            selTitle = @"起始站";
        else
            selTitle = @"终点站";
        UIActionSheet* actionSheet = [[UIActionSheet alloc] initWithTitle:@"把该收费站设置为：" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:selTitle, nil]; //下钻页
        [actionSheet showInView:self.view];
    }
    
}

-(void)mapView:(BMKMapView *)mapView didDeselectAnnotationView:(BMKAnnotationView *)view
{
    if([view.annotation isKindOfClass:[StationCalloutAnnotation class]]){
        
        if (_stationCalloutAnnotation&&(_stationCalloutAnnotation.coordinate.latitude == view.annotation.coordinate.latitude)&&
            ( _stationCalloutAnnotation.coordinate.longitude == view.annotation.coordinate.longitude))
        {
            [mapView removeAnnotation:_stationCalloutAnnotation];
            _stationCalloutAnnotation = nil;
        }
    }
    
}

#pragma mark - actionSheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    [_mapView removeAnnotation:_stationCalloutAnnotation]; //清除收费站的气泡
    if (buttonIndex==0) {
        if (_delegate && [_delegate respondsToSelector:@selector(selStationCallBack:)]) {
            [_delegate selStationCallBack:selStationDic];
        }
    }
}

@end
