//
//  HighwayInfoAtMapVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/7/3.
//  Copyright (c) 2014年 lt. All rights reserved.
//
#import "AppDelegate.h"
#import "NewsInfoVC.h"
#import "HighwayInfoAtMapVC.h"
#import "ServiceInfoVC.h"
#import "ServicePointAnnotation.h"


@interface HighwayInfoAtMapVC ()

@end

@implementation HighwayInfoAtMapVC



- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title =_roadName;
    _routesearch = [[BMKRouteSearch alloc]init];
    
    // 设置地图显示中心位置
    CLLocationCoordinate2D coordinate;
    if(_stationArray&&[_stationArray count]>0){
        stationCount=[_stationArray count];
        NSDictionary *station = [self.stationArray objectAtIndex:stationCount/2];
        float XCODE=[[station valueForKey:@"xcode"] floatValue];
        float YCODE =[[station valueForKey:@"ycode"] floatValue];
        coordinate = {YCODE,XCODE};
    }
    else
    {
        coordinate =[CommonData sharedCommonData].currentLocationCoordinate2D;
    }
    [_mapView setCenterCoordinate:coordinate animated:YES];
    
    
}

-(void)viewWillAppear:(BOOL)animated {
    [_mapView viewWillAppear];
    _mapView.delegate = self;  // 此处记得不用的时候需要置nil，否则影响内存的释放
    _routesearch.delegate = self; // 此处记得不用的时候需要置nil，否则影响内存的释放
}



-(void)viewWillDisappear:(BOOL)animated {
    [_mapView viewWillDisappear];
    _mapView.delegate = nil; // 不用时，置nil
    _routesearch.delegate = nil; // 不用时，置nil
}

#pragma mark- custom method


//显示服务区
-(void)showServiceArea
{
    NSMutableArray *annotations = [NSMutableArray new];
    ServicePointAnnotation *pointAnnotation;
    for (NSDictionary *service in _serviceArray ) {
        for (int i=0; i<2; i++) {
            pointAnnotation = [[ServicePointAnnotation alloc] init];
            float XCODE,YCODE;
            if (i==0 && [NSString isNotEmpty:[service valueForKey:@"Coor_Lon1"]])
            {
                XCODE =[[service valueForKey:@"Coor_Lon1"] floatValue];
                YCODE =[[service valueForKey:@"Coor_Lat1"] floatValue];
            }else if([NSString isNotEmpty:[service valueForKey:@"Coor_Lon2"]])
            {
                XCODE=[[service valueForKey:@"Coor_Lon2"] floatValue];
                YCODE =[[service valueForKey:@"Coor_Lat2"] floatValue];
            }
            CLLocationCoordinate2D coordinate = {YCODE,XCODE};
            pointAnnotation.coordinate=coordinate;
            pointAnnotation.title=[service valueForKey:@"RestName"];
            pointAnnotation.serviceDic = service;
            [annotations addObject:pointAnnotation];
        }
        
    }
    [self.mapView addAnnotations:annotations];
}

//在地图上显示交通事故
- (void)showAccident
{
    NSMutableArray *annotations = [NSMutableArray new];
    AccidentPointAnnotation *pointAnnotation;
    for (NSDictionary *accident in _accidentArray ) {
        pointAnnotation = [[AccidentPointAnnotation alloc] init];
        float XCODE=[[accident valueForKey:@"coor_x"] floatValue];
        float YCODE =[[accident valueForKey:@"coor_y"] floatValue];
        CLLocationCoordinate2D coordinate = {YCODE,XCODE};
        pointAnnotation.coordinate=coordinate;
        pointAnnotation.title=[accident valueForKey:@"remark"];
        pointAnnotation.accidentDic = accident;
        [annotations addObject:pointAnnotation];
    }
    [self.mapView addAnnotations:annotations];
}

//在地图上显示道路施工
- (void)showConstuction
{
    NSMutableArray *annotations = [NSMutableArray new];
    ConstructPointAnnotation *pointAnnotation;
    for (NSDictionary *construction in _constructArray ) {
        pointAnnotation = [[ConstructPointAnnotation alloc] init];
        float XCODE=[[construction valueForKey:@"coor_x"] floatValue];
        float YCODE =[[construction valueForKey:@"coor_y"] floatValue];
        CLLocationCoordinate2D coordinate = {YCODE,XCODE};
        pointAnnotation.coordinate=coordinate;
        pointAnnotation.title=[construction valueForKey:@"remark"];
        pointAnnotation.constructDic = construction;
        [annotations addObject:pointAnnotation];
    }
    [self.mapView addAnnotations:annotations];
}

// 在地图上显示站
- (void)showStation
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

// 在地图上显示路
- (void)showRoad
{
    NSString *fileName=[NSString stringWithFormat:@"%@.json",_roadId];
    NSDictionary *roadCoordinateDic = [JSONUtil readJSON:fileName];
    NSArray *roadCoordinateAry =[roadCoordinateDic valueForKey:@"Line"];
    for (NSDictionary *roadSection in roadCoordinateAry) {
        NSString *startStationId= [roadSection valueForKey:@"startID"];
        NSString *endStationId= [roadSection valueForKey:@"endID"];
        NSArray *pointArray=[roadSection valueForKey:@"points"];
        
        NSInteger pointCount =[pointArray count];
        BMKMapPoint * temppoints = new BMKMapPoint[pointCount];
        for (int i =0;i<pointCount;i++) {
            NSDictionary *point=[pointArray objectAtIndex:i];
            temppoints[i].x=[[point objectForKey:@"x"] doubleValue];
            temppoints[i].y=[[point objectForKey:@"y"] doubleValue];
        }
        
        BMKPolyline* polyline = [BMKPolyline polylineWithPoints:temppoints count:pointCount];
        polyline.title=[NSString stringWithFormat:@"%d",[ComFun findSpeed:_speedArray startStationId:startStationId endStationId:endStationId]];
        [_mapView addOverlay:polyline];
        
    }
    
}

//- (void)showRoad
//{
//    startStationCoor = {[[[_stationArray objectAtIndex:0] objectForKey:@"ycode"] floatValue],[[[_stationArray objectAtIndex:0] objectForKey:@"xcode"] floatValue]};
//    int num = _stationArray.count-1;
//    while ([[[_stationArray objectAtIndex:num] objectForKey:@"stationindex"] intValue] > 900) {
//        num--;
//    }
//    endStationCoor = {[[[_stationArray objectAtIndex:num] objectForKey:@"ycode"] floatValue],[[[_stationArray objectAtIndex:num] objectForKey:@"xcode"] floatValue]};
//    
//    [self plainRoadStartCoor:endStationCoor plainRoadEndCoor:startStationCoor];
//}
//
//-(void)plainRoadStartCoor:(CLLocationCoordinate2D)startCoor plainRoadEndCoor:(CLLocationCoordinate2D)endCoor
//{
//    BMKDrivingRoutePlanOption *drivingRouteSearchOption = [[BMKDrivingRoutePlanOption alloc]init];
//    BMKPlanNode* startNode = [[BMKPlanNode alloc]init];
//    startNode.pt = startCoor;
//    startNode.name = nil;
//    
//    BMKPlanNode* endNode = [[BMKPlanNode alloc]init];
//    endNode.pt = endCoor;
//    endNode.name = nil;
//    
//    drivingRouteSearchOption.from = startNode;
//    drivingRouteSearchOption.to = endNode;
//    saveToFileAry = [[NSMutableArray alloc] init];
//    bool a = [_routesearch drivingSearch:drivingRouteSearchOption];
//    if (a) {
//        NSLog(@"成功");
//    }else
//    {
//        NSLog(@"失败");
//    }
//}

//- (void)showRoad
//{
//    //逆行
//    NSString * fileName = [[NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask,YES) objectAtIndex:0] stringByAppendingPathComponent:[[NSString alloc] initWithFormat:@"Ni/%@.plist",_roadId]];
//    NSArray* ary = [[NSArray alloc] initWithContentsOfFile:fileName];
//    int count=0,i=0;
//    for (NSArray* secAry in ary) {
//        count+=secAry.count;
//    }
//    BMKMapPoint * temppoints = new BMKMapPoint[count];
//    for (NSArray* secAry in ary) {
//        for (NSDictionary* dic in secAry) {
//             temppoints[i].x=[[dic objectForKey:@"x"] doubleValue];
//            temppoints[i].y=[[dic objectForKey:@"y"] doubleValue];
//            i++;
//        }
//    }
//    BMKPolyline* polyline = [BMKPolyline polylineWithPoints:temppoints count:count];
//    polyline.title=[NSString stringWithFormat:@"%d",20];
//    [_mapView addOverlay:polyline];
//    
//    //顺行
//    fileName = [[NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask,YES) objectAtIndex:0] stringByAppendingPathComponent:[[NSString alloc] initWithFormat:@"Shun/%@.plist",_roadId]];
//    ary = [[NSArray alloc] initWithContentsOfFile:fileName];
//    count=0,i=0;
//    for (NSArray* secAry in ary) {
//        count+=secAry.count;
//    }
//    BMKMapPoint * temppoints_ = new BMKMapPoint[count];
//    for (NSArray* secAry in ary) {
//        for (NSDictionary* dic in secAry) {
//            temppoints_[i].x=[[dic objectForKey:@"x"] doubleValue];
//            temppoints_[i].y=[[dic objectForKey:@"y"] doubleValue];
//            i++;
//        }
//    }
//    polyline = [BMKPolyline polylineWithPoints:temppoints_ count:count];
//    polyline.title=[NSString stringWithFormat:@"%d",100];
//    [_mapView addOverlay:polyline];
//}

#pragma  mark- BMKMapViewDelegate
-(void)mapViewDidFinishLoading:(BMKMapView *)mapView{
    // 显示站
    [self showStation];
    // 显示交通事故
    [self showAccident];
    // 显示道路施工
    [self showConstuction];
    // 显示road
    [self showRoad];
    // 显示服务区
    [self showServiceArea];
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
        
        //该标注点与当前位置的直线距离
        int distance =  BMKMetersBetweenMapPoints(BMKMapPointForCoordinate([CommonData sharedCommonData].currentLocationCoordinate2D),BMKMapPointForCoordinate(annotation.coordinate));
        calloutannotationview.stationNameLabel.text=[[NSString alloc] initWithFormat:@"%@\n",[ann.locationInfo objectForKey:@"stationname"]];
        calloutannotationview.distanceLabel.text=[[NSString alloc] initWithFormat:@"离我直线行程%0.1fkm",distance/1000.0];

        //显示站名
        return calloutannotationview;
        
        
    }
    else if([annotation isKindOfClass:[AccidentPointAnnotation class]])
    {
        static NSString *annotaionIdentifier = @"accidentPointAnnotaion";
        BMKAnnotationView *annotationView=[self.mapView dequeueReusableAnnotationViewWithIdentifier:annotaionIdentifier];
        if(!annotationView)
            annotationView = [[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:annotaionIdentifier];
        
        annotationView.image=[UIImage imageNamed:@"accident-map"];
        annotationView.canShowCallout=YES;
        return annotationView;
    }
    else if([annotation isKindOfClass:[ConstructPointAnnotation class]])
    {
        static NSString *annotaionIdentifier = @"constructPointAnnotaion";
        BMKAnnotationView *annotationView=[self.mapView dequeueReusableAnnotationViewWithIdentifier:annotaionIdentifier];
        if(!annotationView)
            annotationView = [[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:annotaionIdentifier];
        
        annotationView.image=[UIImage imageNamed:@"fix-map"];
        annotationView.canShowCallout=YES;
        return annotationView;
        
    }else if ([annotation isKindOfClass:[ServicePointAnnotation class]])
    {
        static NSString *annotaionIdentifier = @"servicePointAnnotation";
        BMKAnnotationView *annotationView=[self.mapView dequeueReusableAnnotationViewWithIdentifier:annotaionIdentifier];
        if(!annotationView)
            annotationView = [[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:annotaionIdentifier];
        
        annotationView.image=[UIImage imageNamed:@"service-map"];
        annotationView.canShowCallout=YES;
        return annotationView;
    }
    
    
    return nil;
}


- (void)mapView:(BMKMapView *)mapView didSelectAnnotationView:(BMKAnnotationView *)view
{
    StationPointAnnotation *annn = (StationPointAnnotation *)view.annotation;
    if ([view.annotation isKindOfClass:[StationPointAnnotation class]]) {
        
        //如果点到了这个marker点，什么也不做
        if (_stationCalloutAnnotation.coordinate.latitude == view.annotation.coordinate.latitude&&
            _stationCalloutAnnotation.coordinate.longitude == view.annotation.coordinate.longitude) {
            return;
        }
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
        
    }
    
}

-(void)mapView:(BMKMapView *)mapView didDeselectAnnotationView:(BMKAnnotationView *)view
{
    
    if(_stationCalloutAnnotation&&[view isKindOfClass:[StationCalloutAnnotationView class]])
    {
        if ((_stationCalloutAnnotation.coordinate.latitude == view.annotation.coordinate.latitude)&&
            ( _stationCalloutAnnotation.coordinate.longitude == view.annotation.coordinate.longitude))
        {
            [mapView removeAnnotation:_stationCalloutAnnotation];
            _stationCalloutAnnotation = nil;
        }
    }
    
}


- (BMKOverlayView *)mapView:(BMKMapView *)mapView viewForOverlay:(id <BMKOverlay>)overlay{
    if ([overlay isKindOfClass:[BMKPolyline class]]){
        BMKPolylineView* polylineView = [[BMKPolylineView alloc] initWithOverlay:overlay];
        int speed=[polylineView.polyline.title intValue];
        UIColor *lineColor;
        if(SpeedFast(speed))
            lineColor=[[UIColor greenColor] colorWithAlphaComponent:0.5];
        else if (SpeedSlow(speed))
            lineColor= [[UIColor yellowColor] colorWithAlphaComponent:0.5];
        else
            lineColor= [[UIColor redColor] colorWithAlphaComponent:0.5];
        
        polylineView.strokeColor = lineColor;
        polylineView.lineWidth = 4.5;
        return polylineView;
    }
    
    return nil;
    
}

//选中了气泡
- (void)mapView:(BMKMapView *)mapView annotationViewForBubble:(BMKAnnotationView *)view
{
    if ([view.annotation isKindOfClass:[AccidentPointAnnotation class]]) { //交通事故气泡
        AccidentPointAnnotation* accidentPointAnnotation = (AccidentPointAnnotation*)view.annotation;
        NewsInfoVC* newsInfo = [[NewsInfoVC alloc] init];
        newsInfo.type = 1;
        newsInfo.dataDic = accidentPointAnnotation.accidentDic;
        [app.nav pushViewController:newsInfo animated:YES];
    }
    else if ([view.annotation isKindOfClass:[ConstructPointAnnotation class]]) { //道路施工气泡
        ConstructPointAnnotation* constructPointAnnotation = (ConstructPointAnnotation*)view.annotation;
        NewsInfoVC* newsInfo = [[NewsInfoVC alloc] init];
        newsInfo.type = 2;
        newsInfo.dataDic = constructPointAnnotation.constructDic;
        [app.nav pushViewController:newsInfo animated:YES];
    }
    else if ([view.annotation isKindOfClass:[ServicePointAnnotation class]]){ //服务区气泡
        ServicePointAnnotation *servicePointAnnotation = (ServicePointAnnotation*)view.annotation;
        ServiceInfoVC* serviceInfo = [[ServiceInfoVC alloc] init];
        serviceInfo.dataDic = servicePointAnnotation.serviceDic;
        [app.nav pushViewController:serviceInfo animated:YES];
    }
}

- (IBAction)zoomIn:(id)sender {
    [_mapView zoomIn];
}

- (IBAction)zoomOut:(id)sender {
    [_mapView zoomOut];
}

//- (void)onGetDrivingRouteResult:(BMKRouteSearch*)searcher result:(BMKDrivingRouteResult*)result errorCode:(BMKSearchErrorCode)error
//{
//	if (error == BMK_SEARCH_NO_ERROR) {
//        BMKDrivingRouteLine* plan = (BMKDrivingRouteLine*)[result.routes objectAtIndex:0];
//        // 计算路线方案中的路段数目
//		int size = [plan.steps count];
//		int planPointCounts = 0;
//		for (int i = 0; i < size; i++) {
//            BMKDrivingStep* transitStep = [plan.steps objectAtIndex:i];
//            //轨迹点总数累计
//            planPointCounts += transitStep.pointsCount;
//        }
//        //轨迹点
//        BMKMapPoint * temppoints = new BMKMapPoint[planPointCounts];
//
//        int i = 0;
//        for (int j=0; j < size; j++) {
//            BMKDrivingStep* transitStep = [plan.steps objectAtIndex:j];
//            NSMutableArray* ary = [[NSMutableArray alloc] init];
//            for(int k=0;k<transitStep.pointsCount;k++) {
//                NSMutableDictionary* dic = [NSMutableDictionary new];
//                temppoints[i].x = transitStep.points[k].x;
//                temppoints[i].y = transitStep.points[k].y;
//                [dic setObject:[[NSString alloc] initWithFormat:@"%f",temppoints[i].x] forKey:@"x"];
//                [dic setObject:[[NSString alloc] initWithFormat:@"%f",temppoints[i].y] forKey:@"y"];
//                [ary addObject:dic];
//                i++;
//            }
//            [saveToFileAry addObject:ary];
//        }
//        
//        NSString* filePath = [[NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0] stringByAppendingPathComponent:@"/109.plist"];
//        [saveToFileAry writeToFile:filePath atomically:YES];
//        // 通过points构建BMKPolyline
//		BMKPolyline* polyLine = [BMKPolyline polylineWithPoints:temppoints count:planPointCounts];
//        polyLine.title=[NSString stringWithFormat:@"%d",0]; //路径规划的速度设为0
//		[_mapView addOverlay:polyLine]; // 添加路线overlay
//		delete []temppoints;
//        [self hiddenHUD];
//	}
//    
//}


@end
