//
//  DirvingRouteDetailMapVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/7/8.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "DirvingRouteDetailMapVC.h"
#import "NewsInfoVC.h"
#import "AppDelegate.h"
#import "ServiceInfoVC.h"


@implementation DirvingRouteDetailMapVC


- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    _searcher = [[BMKRouteSearch alloc]init];
    // 设置地图显示中心位置
    CLLocationCoordinate2D coordinate;
    if(_stationArray&&[_stationArray count]>0){
        stationCount=(int)[_stationArray count];
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
    _searcher.delegate = self;
    
    //当前位置标注
    currentPointAnnotation = [[CurrentPointAnnotation alloc] init];
    currentPointAnnotation.coordinate = [CommonData sharedCommonData].currentLocationCoordinate2D;
    [ComFun getDetailLocation:currentPointAnnotation];
    [self.mapView addAnnotation:currentPointAnnotation];
}


-(void)viewWillDisappear:(BOOL)animated {
    [_mapView viewWillDisappear];
    _mapView.delegate = nil; // 不用时，置nil
    _searcher.delegate = nil;
    
    [self.mapView removeAnnotation:currentPointAnnotation];
}

//显示服务区
-(void)showService
{
    NSMutableArray *annotations = [NSMutableArray new];
    ServicePointAnnotation *pointAnnotation;
    for (NSDictionary *service in _serviceAry ) {
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

//显示收费站
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

//显示交通事故
-(void)showAccident
{
    NSMutableArray *annotations =[NSMutableArray new];
    AccidentPointAnnotation *accidentPointAnnotation;
    for (NSDictionary *station in _accidentAry ) {
        accidentPointAnnotation = [[AccidentPointAnnotation alloc] init];
        float XCODE=[[station valueForKey:@"coor_x"] floatValue];
        float YCODE =[[station valueForKey:@"coor_y"] floatValue];
        CLLocationCoordinate2D coordinate = {YCODE,XCODE};
        accidentPointAnnotation.coordinate=coordinate;
        accidentPointAnnotation.title=[station valueForKey:@"remark"];
        accidentPointAnnotation.accidentDic = station;
        [annotations addObject:accidentPointAnnotation];
    }
    [self.mapView addAnnotations:annotations];
}

//显示道路施工
-(void)showConstruct
{
    NSMutableArray *annotations =[NSMutableArray new];
    ConstructPointAnnotation *constructPointAnnotation;
    for (NSDictionary *station in _fixAry ) {
        constructPointAnnotation = [[ConstructPointAnnotation alloc] init];
        float XCODE=[[station valueForKey:@"coor_x"] floatValue];
        float YCODE =[[station valueForKey:@"coor_y"] floatValue];
        CLLocationCoordinate2D coordinate = {YCODE,XCODE};
        constructPointAnnotation.coordinate=coordinate;
        constructPointAnnotation.title=[station valueForKey:@"remark"];
        constructPointAnnotation.constructDic = station;
        [annotations addObject:constructPointAnnotation];
    }
    [self.mapView addAnnotations:annotations];
}


//显示路径，使用json数据 + 起始终点站的路径规划（途径点使用各高速路的中点）
-(void)showRoad
{
    [self showHUD:@"规划路径中.."];
    //起始站与终点站的经纬度
    CLLocationCoordinate2D startCoor,endCoor;
    startCoor.latitude = [[[_stationArray objectAtIndex:0] objectForKey:@"ycode"]floatValue];
    startCoor.longitude = [[[_stationArray objectAtIndex:0] objectForKey:@"xcode"]floatValue];
    endCoor.latitude = [[[_stationArray objectAtIndex:_stationArray.count-1] objectForKey:@"ycode"]floatValue];
    endCoor.longitude = [[[_stationArray objectAtIndex:_stationArray.count-1] objectForKey:@"xcode"]floatValue];
    
    NSMutableArray* middlePointsAry = [[NSMutableArray alloc] init];
    polylines = [[NSMutableArray alloc] init]; //覆盖物数组
    
    //画同一条高速路的路径
    //每一个roadID
    for (int i=0; i<_allRoadIDs.count; i++) {
        NSArray* roadAry = [_sortStationDic objectForKey:[_allRoadIDs objectAtIndex:i]]; //途径的站
        if (roadAry.count<2) { //只有一个站，不画线
            continue;
        }
        NSArray* lineAry = [[JSONUtil readJSON:[[NSString alloc] initWithFormat:@"%@.json",[_allRoadIDs objectAtIndex:i]]] objectForKey:@"Line"]; //待查找的表
        
        //获得同一条路上两两站的路径坐标点
        NSMutableArray* roadPointAry = [[NSMutableArray alloc] init]; //显示在界面的直角坐标点
        
        for (int staNum=0; staNum<roadAry.count; staNum++) {
            if (staNum+1==roadAry.count-1 ) { //最后一个站
                break;
            }
            NSArray* points;
            NSString* startID = [[roadAry objectAtIndex:staNum] objectForKey:@"stationid"];
            NSString* endID = [[roadAry objectAtIndex:staNum+1] objectForKey:@"stationid"];
            for (int j=0 ;j<lineAry.count;j++) {
                NSDictionary* dic = [lineAry objectAtIndex:j];
                if ([[dic objectForKey:@"startID"]isEqualToString:startID] && [[dic objectForKey:@"endID"]isEqualToString:endID]) {
                    [roadPointAry addObjectsFromArray:[dic objectForKey:@"points"]];
                    points = [dic objectForKey:@"points"];
                    break;
                }
                else if ([[dic objectForKey:@"startID"]isEqualToString:startID] && [[dic objectForKey:@"points"] count]==0 && j+1<lineAry.count && [[_allRoadIDs objectAtIndex:i] isEqualToString:@"1"] ) { //防止出现新塘南，新塘北无路径（即stationIndex相同）
                    [roadPointAry addObjectsFromArray:[[lineAry objectAtIndex:j+1] objectForKey:@"points"]];
                    points = [dic objectForKey:@"points"];
                    break;
                }
                
            }
            //两两站之间画线
            BMKMapPoint * temppoints = new BMKMapPoint[points.count];
            for (int i =0;i<points.count;i++) {
                NSDictionary *point=[points objectAtIndex:i];
                temppoints[i].x=[[point objectForKey:@"x"] doubleValue];
                temppoints[i].y=[[point objectForKey:@"y"] doubleValue];
            }
            BMKPolyline* polyline = [BMKPolyline polylineWithPoints:temppoints count:points.count];
            polyline.title=[NSString stringWithFormat:@"%d",[self findSpeed:startID :endID roadID:[_allRoadIDs objectAtIndex:i]]];
            [polylines addObject:polyline];

        }

        if (roadPointAry.count==0) { //可能基础数据中两个站之间没有经纬度
            continue;
        }else
        {
            [middlePointsAry addObject:[roadPointAry objectAtIndex:roadPointAry.count/2]]; //加载途经点
        }

    }
    
    //途经点选取
    NSLog(@"途经点数目：%d",middlePointsAry.count);
    NSLog(@"经过的站总数:%d",_stationArray.count);
    if (middlePointsAry.count<=2 && _stationArray.count>15) { //不合理，使用【途径站】经纬度代替【途径点】
        if (_wayPointsAry.count>10) {
            int startNum = _wayPointsAry.count-10/2;
            NSRange range = NSMakeRange(startNum, 10);
            NSArray* tempAry = [_wayPointsAry subarrayWithRange:range];
            _wayPointsAry = (NSMutableArray*)tempAry;
        }
        NSMutableArray* PointsAry = [[NSMutableArray alloc] init];
        for (NSDictionary* wayPointDic in _wayPointsAry) {
            
            CLLocationCoordinate2D wayPoint;
            wayPoint.longitude=[[wayPointDic objectForKey:@"xcode"] doubleValue];
            wayPoint.latitude=[[wayPointDic objectForKey:@"ycode"] doubleValue];
            BMKPlanNode* wayPointItem = [[BMKPlanNode alloc] init];
            wayPointItem.pt = wayPoint;
            wayPointItem.name = nil;
            [PointsAry addObject:wayPointItem];
        }
        endStationCoor = endCoor;
        [self plainRoadStartCoor:startCoor plainRoadEndCoor:endCoor wayPointAry:PointsAry];
    }
    else //途经点数与站总数对应合理
    {
        if (middlePointsAry.count>10) {
            int startNum = middlePointsAry.count-10/2;
            NSRange range = NSMakeRange(startNum, 10);
            NSArray* tempAry = [middlePointsAry subarrayWithRange:range];
            middlePointsAry = (NSMutableArray*)tempAry;
        }
        NSMutableArray* PointsAry = [[NSMutableArray alloc] init];
        for (NSDictionary* wayPointDic in middlePointsAry) {
            
            CLLocationCoordinate2D wayPoint;
            BMKMapPoint point;
            point.x=[[wayPointDic objectForKey:@"x"] doubleValue];
            point.y=[[wayPointDic objectForKey:@"y"] doubleValue];
            wayPoint = BMKCoordinateForMapPoint(point);
            BMKPlanNode* wayPointItem = [[BMKPlanNode alloc] init];
            wayPointItem.pt = wayPoint;
            wayPointItem.name = nil;
            [PointsAry addObject:wayPointItem];
        }
        endStationCoor = endCoor;
        [self plainRoadStartCoor:startCoor plainRoadEndCoor:endCoor wayPointAry:PointsAry];

    }
    
}


//路径规划
-(void)plainRoadStartCoor:(CLLocationCoordinate2D)startCoor plainRoadEndCoor:(CLLocationCoordinate2D)endCoor wayPointAry:(NSArray*)pointsAry
{
    BMKDrivingRoutePlanOption *drivingRouteSearchOption = [[BMKDrivingRoutePlanOption alloc]init];
    BMKPlanNode* startNode = [[BMKPlanNode alloc]init];
    startNode.pt = startCoor;
    startNode.name = nil;
    
    BMKPlanNode* endNode = [[BMKPlanNode alloc]init];
    endNode.pt = endCoor;
    endNode.name = nil;
    
    drivingRouteSearchOption.from = startNode;
    drivingRouteSearchOption.to = endNode;
    drivingRouteSearchOption.wayPointsArray = pointsAry;
    bool a = [_searcher drivingSearch:drivingRouteSearchOption];
    if (a) {
        NSLog(@"成功");
    }else
    {
        NSLog(@"失败");
    }
}

//查找路段速度
- (int)findSpeed:(NSString *)startStationId:(NSString *)endStationId roadID:(NSString*)roadID
{
    
    NSArray* speedArray = [_sortSpeedDic objectForKey:roadID];
    return [ComFun findSpeed:speedArray startStationId:startStationId endStationId:endStationId];
}

#pragma mark- BMKMapViewDelegate

// 地图初始化完成
- (void)mapViewDidFinishLoading:(BMKMapView *)mapView{
    // 显示收费站
    [self showStation];
    // 显示交通事故
    [self showAccident];
    // 显示道路施工
    [self showConstruct];
    // 显示road
    [self showRoad];
    //显示服务区
    [self showService];
}

- (BMKAnnotationView *)mapView:(BMKMapView *)mapView viewForAnnotation:(id <BMKAnnotation>)annotation
{
    if([annotation isKindOfClass:[CurrentPointAnnotation class]])//当前位置的PointAnnotation
    {
        static NSString *annotaionIdentifier = @"currentPointAnnotation";
        BMKAnnotationView *annotationView=[self.mapView dequeueReusableAnnotationViewWithIdentifier:annotaionIdentifier];
        if(!annotationView)
            annotationView = [[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:annotaionIdentifier];
        
        annotationView.image=[UIImage imageNamed:@"GPS-Station"];
        annotationView.canShowCallout=YES;
        return annotationView;
        
    }
    else if([annotation isKindOfClass:[StationPointAnnotation class]])
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
    if ([view.annotation isKindOfClass:[StationPointAnnotation class]]) {
        StationPointAnnotation *annn = (StationPointAnnotation *)view.annotation;
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
        [mapView setCenterCoordinate:view.annotation.coordinate animated:YES];
    }
    
}

-(void)mapView:(BMKMapView *)mapView didDeselectAnnotationView:(BMKAnnotationView *)view
{
    if([view.annotation isKindOfClass:[StationPointAnnotation class]]){
        
        if (_stationCalloutAnnotation&&(_stationCalloutAnnotation.coordinate.latitude == view.annotation.coordinate.latitude)&&
            ( _stationCalloutAnnotation.coordinate.longitude == view.annotation.coordinate.longitude))
        {
            [mapView removeAnnotation:_stationCalloutAnnotation];
            _stationCalloutAnnotation = nil;
        }
    }
}


/**
 *返回驾乘搜索结果
 *@param searcher 搜索对象
 *@param result 搜索结果，类型为BMKDrivingRouteResult
 *@param error 错误号，@see BMKSearchErrorCode
 */
- (void)onGetDrivingRouteResult:(BMKRouteSearch*)searcher result:(BMKDrivingRouteResult*)result errorCode:(BMKSearchErrorCode)error
{
    bool isNearEnd = NO;
	if (error == BMK_SEARCH_NO_ERROR) {
        BMKDrivingRouteLine* plan = (BMKDrivingRouteLine*)[result.routes objectAtIndex:0];
        // 计算路线方案中的路段数目
		int size = [plan.steps count];
		int planPointCounts = 0;
		for (int i = 0; i < size; i++) {
            BMKDrivingStep* transitStep = [plan.steps objectAtIndex:i];
            //轨迹点总数累计
            planPointCounts += transitStep.pointsCount;
        }
        //轨迹点
        BMKMapPoint * temppoints = new BMKMapPoint[planPointCounts];
        
        BMKMapPoint endPoint = BMKMapPointForCoordinate(endStationCoor);
        int i = 0;
        for (int j=0; j < size; j++) {
            BMKDrivingStep* transitStep = [plan.steps objectAtIndex:j];
            
            for(int k=0;k<transitStep.pointsCount;k++) {
//                if (isNearEnd) { //靠近终点了，剩下的点原地踏步
//                    temppoints[i].x = temppoints[i-1].x;
//                    temppoints[i].y = temppoints[i-1].y;
//                }else
//                {
                    temppoints[i].x = transitStep.points[k].x;
                    temppoints[i].y = transitStep.points[k].y;
                    if (BMKMetersBetweenMapPoints(temppoints[i], endPoint)<400) {//判断到终点的距离小于200mi
                        isNearEnd = YES;
//                    }
                }
                i++;
            }
        }
        // 通过points构建BMKPolyline
		BMKPolyline* polyLine = [BMKPolyline polylineWithPoints:temppoints count:planPointCounts];
        polyLine.title=[NSString stringWithFormat:@"%d",0]; //路径规划的速度设为0
		[_mapView addOverlay:polyLine]; // 添加路线overlay
        [_mapView addOverlays:polylines]; //添加高速路内部覆盖物，内部的在最上面
		delete []temppoints;
        [self hiddenHUD];
	}
    
}

//选中了气泡
- (void)mapView:(BMKMapView *)mapView annotationViewForBubble:(BMKAnnotationView *)view
{
    if ([view.annotation isKindOfClass:[AccidentPointAnnotation class]]) {
        AccidentPointAnnotation* accidentPointAnnotation = (AccidentPointAnnotation*)view.annotation;
        NewsInfoVC* newsInfo = [[NewsInfoVC alloc] init];
        newsInfo.type = 1;
        newsInfo.dataDic = accidentPointAnnotation.accidentDic;
        [app.nav pushViewController:newsInfo animated:YES];
    }
    else if ([view.annotation isKindOfClass:[ConstructPointAnnotation class]]) {
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

- (BMKOverlayView *)mapView:(BMKMapView *)mapView viewForOverlay:(id <BMKOverlay>)overlay{
    if ([overlay isKindOfClass:[BMKPolyline class]]){
        BMKPolylineView* polylineView = [[BMKPolylineView alloc] initWithOverlay:overlay];
        int speed=[polylineView.polyline.title intValue];
        UIColor *lineColor;
        if(SpeedFast(speed))
            lineColor=[[UIColor greenColor] colorWithAlphaComponent:1];
        else if (SpeedSlow(speed))
            lineColor= [[UIColor yellowColor] colorWithAlphaComponent:1];
        else if(speed==0) //跨路段
            lineColor= [[UIColor greenColor] colorWithAlphaComponent:1];
        else
            lineColor= [[UIColor redColor] colorWithAlphaComponent:1];
        
        polylineView.strokeColor = lineColor;
        polylineView.lineWidth = 4.5;
        return polylineView;
    }
    
    return nil;
    
}

- (IBAction)zoomIn:(id)sender {
    [_mapView zoomIn];
}

- (IBAction)zoomOut:(id)sender {
    [_mapView zoomOut];
}

@end
