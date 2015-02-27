//
//  RealTimeTrafficVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-7.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "RealTimeTrafficVC.h"
#import "AppDelegate.h"
#import "NewsVC.h"
#import "HighwayListVC.h"
#import "QuartzCore/QuartzCore.h"
#import "NewsInfoVC.h"
#import "ServiceInfoVC.h"




@implementation RealTimeTrafficVC

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    //初始化BMKLocationService
    _locService = [[BMKLocationService alloc]init];

    [self initView];
    
    //显示当前位置坐标
    isFirstShowCurrentLocation = YES;
    
    [self.mapView setFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)]; // 百度地图2.6不支持autolayout!
}


-(void)viewWillAppear:(BOOL)animated {
    [_mapView viewWillAppear];
    _mapView.delegate = self;  // 此处记得不用的时候需要置nil，否则影响内存的释放
    _locService.delegate = self;
    
    [_locService startUserLocationService];
    _mapView.userTrackingMode = BMKUserTrackingModeNone;//设置定位的状态
    _mapView.showsUserLocation = NO;//不显示定位图层图标
    
    //状态栏渐变
    [self initStatus];
    
    //显示引导页
    if ([[SqliteUtil sharedSqliteUtil] queryIndexInfo:@"RealTimeTraffic-Index"]) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(IndexViewGone) name:@"IndexViewGone" object:nil];
        [self initIndexView:@"RealTimeTraffic-Index"];
    }
    
}


-(void)viewWillDisappear:(BOOL)animated {
    _isSetMapSpan = NO;
    
    [_mapView viewWillDisappear];
    _mapView.delegate = nil; // 不用时，置nil
    _locService.delegate = nil;
    
    [_locService stopUserLocationService];
    _mapView.showsUserLocation = NO;
    
    [statusView removeFromSuperview];
    statusView = nil;
    
}

#pragma mark- custom method
//初始化控件
-(void)initView
{
    //高速列表搜索框
    UIView *bottomView = [[UIView alloc] initWithFrame:CGRectMake(60, 27, SCREEN_WIDTH-120, 35)];
    bottomView.layer.borderWidth = 0.6;
    bottomView.layer.borderColor=[NAV_GREEN_FONT_COLOR CGColor];
    bottomView.layer.cornerRadius = 4;
    bottomView.backgroundColor = [UIColor clearColor];
    
    UIView *middleView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, bottomView.frame.size.width, bottomView.frame.size.height)];
    middleView.layer.cornerRadius = 4;
    middleView.alpha = 0.6;
    middleView.backgroundColor = [UIColor whiteColor];
    [bottomView addSubview:middleView];
    
    UILabel *labelView = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, bottomView.frame.size.width, bottomView.frame.size.height)];
    labelView.text = @"搜高速";
    labelView.textAlignment = NSTextAlignmentCenter;
    labelView.textColor = NAV_GREEN_FONT_COLOR;
    [bottomView addSubview:labelView];
    
    UIButton *button = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, bottomView.frame.size.width, bottomView.frame.size.height)];
    button.backgroundColor = [UIColor clearColor];
    [button addTarget:self action:@selector(goHighWayVC) forControlEvents:UIControlEventTouchUpInside];
    [bottomView addSubview:button];
    [self.view addSubview:bottomView];
    
    //左右按钮
    UIButton *messageButton = [[UIButton alloc] initWithFrame:CGRectMake(12, 25, 36, 36)];
    self.allRoadBtn = [[UIButton alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-12-36, 25, 36, 36)];
    
    [messageButton setImage:[UIImage imageNamed:@"message-prompt"] forState:UIControlStateNormal];
    [messageButton setImage:[UIImage imageNamed:@"message-prompt_down"] forState:UIControlStateHighlighted];
    
    [self.allRoadBtn setImage:[UIImage imageNamed:@"allRoad"] forState:UIControlStateNormal];
    [self.allRoadBtn setImage:[UIImage imageNamed:@"allRoad-down"] forState:UIControlStateSelected];
    
    [self.view addSubview:messageButton];
    [self.view addSubview:self.allRoadBtn];
    
    [messageButton addTarget:self action:@selector(goNewsVC) forControlEvents:UIControlEventTouchUpInside];
    [self.allRoadBtn addTarget:self action:@selector(showAllRoad) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view sendSubviewToBack:_mapView];
    [self.view sendSubviewToBack:statusView];
    
    
}


//状态栏渐变
-(void)initStatus
{
    statusView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 30 )];
    for(int i=0;i<=30;i++)
    {
        UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, i, SCREEN_WIDTH, 1)];
        view.alpha = 1 - i/30.0;
        view.backgroundColor = [UIColor whiteColor];
        [statusView addSubview:view];
    }
    [self.view addSubview:statusView];
}

//引导页消失
-(void)IndexViewGone
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"IndexViewGone" object:nil];
    [[SqliteUtil sharedSqliteUtil] updateInfo:@"RealTimeTraffic-Index" state:@"hide"];
}


// 显示当前位置
-(IBAction)locateCurrentPosition:(id)sender
{
    //先移除
    [_mapView removeAnnotations:tollStationAnnotationAry];
    [_mapView removeAnnotations:accidentPointAnnotationAry];
    [_mapView removeAnnotations:constructPointAnnotationAry];
    [_mapView removeAnnotations:serviceStationAnnotationArray];
    [_mapView removeAnnotation:currentPointAnnotation];
    
    // 显示当前位置图标
    [_mapView setCenterCoordinate:[CommonData sharedCommonData].currentLocationCoordinate2D animated:YES];
    // 附近的高速路
    [self queryNearRoadID];
    isFirstShowCurrentLocation = YES;
    [self ShowCurrentPointAnnotation];
}

// 显示当前位置PointAnnotation
-(void)ShowCurrentPointAnnotation
{
    if (!isFirstShowCurrentLocation) {
        currentPointAnnotation.coordinate = [CommonData sharedCommonData].currentLocationCoordinate2D;
        [ComFun getDetailLocation:currentPointAnnotation];
    }
    else //第一次显示
    {
        isFirstShowCurrentLocation = NO;
        currentPointAnnotation = [[CurrentPointAnnotation alloc] init];
        currentPointAnnotation.coordinate = [CommonData sharedCommonData].currentLocationCoordinate2D;
        
        //获取当前位置的详细信息
        [ComFun getDetailLocation:currentPointAnnotation];
        [_mapView setCenterCoordinate:currentPointAnnotation.coordinate animated:YES];
        [_mapView addAnnotation:currentPointAnnotation];
        
    }
}





// 放大地图按钮
- (IBAction)ZoomIn:(id)sender {
    [_mapView zoomIn];
}

// 缩小地图按钮
- (IBAction)ZoomOut:(id)sender {
    [_mapView zoomOut];
}



#pragma mark - NearRoadInfo 显示附近的高速信息
//查询附近的高速路ID
- (void)queryNearRoadID
{
    float xcode= [CommonData sharedCommonData].currentLocationCoordinate2D.longitude;
    float ycode= [CommonData sharedCommonData].currentLocationCoordinate2D.latitude;
    NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:[NSString stringWithFormat:@"%f", xcode],@"xcode",[NSString stringWithFormat:@"%f",ycode],@"ycode",nil];
    
    [DataDAO getNearroads:param withCompleted:^(id result){
        if([NSString isNotEmpty:result])
        {
            
            NSString *road = [result valueForKey:@"road"];
            nearroadIds=[road componentsSeparatedByString:@","];
            
            //显示附近的高速信息
            [self showNearRoadInfo];
        }
        
    } withFailure:^(id error){
        [self showHUD:error :FAILEDICON :YES];
    }];
    
}

//显示附近的高速信息
-(void)showNearRoadInfo{
    [self queryRoadSpeed]; //请求附近路速度并显示附近高速
    [self showToll];
    [self showServiceArea];
    [self showAccident];
    [self showFix];
}

// 查询附近路的速度
- (void)queryRoadSpeed
{
    NSString *roadIdStr = [nearroadIds componentsJoinedByString:@","];
    NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:roadIdStr,@"road",nil];
    [DataDAO getRoadSpeed:param withCompleted:^(id result) {
        speedArray=result;
        //显示附近的路
        [self showNearRoad];
    } withFailure:^(id error) {
        [self showHUD:error :FAILEDICON :YES];
    }];
    
}

//显示附近的高速路
-(void)showNearRoad
{
    //先删除之前的覆盖物
    NSArray* array = [NSArray arrayWithArray:_mapView.overlays];
    [_mapView removeOverlays:array];
    for(int k=0; k<nearroadIds.count; k++)
    {
        NSString *roadId = [nearroadIds objectAtIndex:k];
        NSString *fileName=[NSString stringWithFormat:@"%@.json",roadId];
        NSDictionary *roadCoordinateDic = [JSONUtil readJSON:fileName];
        NSArray *roadCoordinateAry =[roadCoordinateDic valueForKey:@"Line"];
        NSArray *speedAry;
        for(NSDictionary *roadSpeed in speedArray)
        {
            if([roadId isEqualToString:[roadSpeed valueForKey:@"road"]])
            {
                speedAry=[roadSpeed valueForKey:@"speed"];
                break;
            }
        }
        for (NSDictionary *roadSection in roadCoordinateAry) {
            NSString *startStationId= [roadSection valueForKey:@"startID"];
            NSString *endStationId= [roadSection valueForKey:@"endID"];
            NSArray *pointArray=[roadSection valueForKey:@"points"];
            
            NSInteger pointCount =[pointArray count];
            BMKMapPoint * temppoints = new BMKMapPoint[pointCount];
            for (int i =0;i<pointCount;i++) {
                NSDictionary *point=[pointArray objectAtIndex:i];
                temppoints[i].x = [[point objectForKey:@"x"] floatValue];
                temppoints[i].y = [[point objectForKey:@"y"] floatValue];
            }
            
            BMKPolyline* polyline = [BMKPolyline polylineWithPoints:temppoints count:pointCount];
            polyline.title=[NSString stringWithFormat:@"%d",[ComFun findSpeed:speedAry startStationId:startStationId endStationId:endStationId]];
            [_mapView addOverlay:polyline];
        }
    }
}

//显示交通事故
- (void)showAccident
{
    if(nearroadIds&&[nearroadIds count]>0)
    {
        int pageSize =20;
        NSString *roadIdStr =[nearroadIds componentsJoinedByString:@","];
        NSString *queryTime=[NSDate formateDate:[NSDate date] fromate:@"yyyy-MM-dd HH:mm:ss"];
        NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:1],@"type",[NSNumber numberWithInt:pageSize],@"pageSize",queryTime,@"queryTime",roadIdStr,@"road",nil];
        [DataDAO getTrafficinfo:param withCompleted:^(id result) {
            _accidentArray = [ComFun selectTodayevent:result];
            NSLog(@"获得的交通事故数目：%d",[_constructArray count]);
            
            if(accidentPointAnnotationAry&&[accidentPointAnnotationAry count]>0)
                [self.mapView removeAnnotations:accidentPointAnnotationAry];
            accidentPointAnnotationAry =[NSMutableArray new];
            AccidentPointAnnotation *accidentPointAnnotation;
            if([_accidentArray count]>0)
            {
                for (NSDictionary *accidentDic in _accidentArray) {
                    accidentPointAnnotation=[[AccidentPointAnnotation alloc] init];
                    float XCODE=[[accidentDic valueForKey:@"coor_x"] floatValue];
                    float YCODE =[[accidentDic valueForKey:@"coor_y"] floatValue];
                    CLLocationCoordinate2D coordinate = {YCODE,XCODE};
                    // 从高速通获取数据，需要转为百度地图数据
                    NSDictionary* testdic = BMKConvertBaiduCoorFrom(coordinate,BMK_COORDTYPE_COMMON);
                    coordinate =BMKCoorDictionaryDecode(testdic) ;
                    
                    accidentPointAnnotation.coordinate=coordinate;
                    accidentPointAnnotation.title=[accidentDic valueForKey:@"remark"];
                    accidentPointAnnotation.accidentDic = accidentDic;
                    [accidentPointAnnotationAry addObject:accidentPointAnnotation];
                }
                
                [self.mapView addAnnotations:accidentPointAnnotationAry];
                
            }
        } withFailure:^(id error) {
            
        }];
    }
    
}


// 显示施工
- (void)showFix
{
    if(nearroadIds&&[nearroadIds count]>0)
    {
        int pageSize =20;
        NSString *roadIdStr =[nearroadIds componentsJoinedByString:@","];
        NSString *queryTime=[NSDate formateDate:[NSDate date] fromate:@"yyyy-MM-dd HH:mm:ss"];
        
        NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:2],@"type",[NSNumber numberWithInt:pageSize],@"pageSize",queryTime,@"queryTime",roadIdStr,@"road",nil];
        
        [DataDAO getTrafficinfo:param withCompleted:^(id result) {
            
            _constructArray = [ComFun selectTodayevent:result];
            NSLog(@"获得的施工数目：%d",[_constructArray count]);
            
            
            if(constructPointAnnotationAry&&[constructPointAnnotationAry count]>0){
                [self.mapView removeAnnotations:constructPointAnnotationAry];
            }
            constructPointAnnotationAry =[NSMutableArray new];
            ConstructPointAnnotation *constructPointAnnotation;
            if([_constructArray count]>0)
            {
                for (NSDictionary *constructDic in _constructArray) {
                    constructPointAnnotation=[[ConstructPointAnnotation alloc] init];
                    float XCODE=[[constructDic valueForKey:@"coor_x"] floatValue];
                    float YCODE =[[constructDic valueForKey:@"coor_y"] floatValue];
                    CLLocationCoordinate2D coordinate = {YCODE,XCODE};
                    // 从高速通获取数据，需要转为百度地图数据
                    NSDictionary* testdic = BMKConvertBaiduCoorFrom(coordinate,BMK_COORDTYPE_COMMON);
                    coordinate =BMKCoorDictionaryDecode(testdic) ;
                    
                    constructPointAnnotation.coordinate=coordinate;
                    constructPointAnnotation.title=[constructDic valueForKey:@"remark"];
                    constructPointAnnotation.constructDic=constructDic;
                    [constructPointAnnotationAry addObject:constructPointAnnotation];
                }
                
                [self.mapView addAnnotations:constructPointAnnotationAry];
                
            }
            
        } withFailure:^(id error) {
            
        }];
    }
    
    
}

// 服务区
- (void)showServiceArea
{
    
    if(nearroadIds&&[nearroadIds count]>0){ //有查到周围的高速路
        NSMutableArray* serviceArray = [[NSMutableArray alloc] init];
        NSDictionary *allServiceDic = [JSONUtil readJSON:@"serviceArea.json"];
        serviceStationAnnotationArray = [[NSMutableArray alloc] init];
        for (int i=0; i<nearroadIds.count; i++) {
            NSString* roadID = [nearroadIds objectAtIndex:i];
            if ([allServiceDic objectForKey:roadID]) {
                [serviceArray addObject:[allServiceDic objectForKey:roadID]];
            }
        }
        
        for (NSDictionary *serviceDicByRoadID in serviceArray) { //每条附近的高速路
            ServicePointAnnotation *servicePointAnnotation;
            NSString* roadName = [serviceDicByRoadID objectForKey:@"RoadName"];
            for (NSDictionary *serviceDic in [serviceDicByRoadID objectForKey:@"RESTS"]) { //每条路的服务区
                if ([NSString isNotEmpty:[serviceDic objectForKey:@"Coor_Lat1"]]) { //有经纬度1
                    servicePointAnnotation = [[ServicePointAnnotation alloc] init];
                    float XCODE=[[serviceDic valueForKey:@"Coor_Lon1"] floatValue];
                    float YCODE =[[serviceDic valueForKey:@"Coor_Lat1"] floatValue];
                    CLLocationCoordinate2D coordinate = {YCODE,XCODE};
                    
                    NSMutableDictionary *serviceDicPlusRoadName = [[NSMutableDictionary alloc] initWithDictionary:serviceDic];
                    [serviceDicPlusRoadName setObject:roadName forKey:@"roadName"]; //添加高速路的路名
                    servicePointAnnotation.coordinate = coordinate;
                    servicePointAnnotation.title = [serviceDic objectForKey:@"RestName"];
                    servicePointAnnotation.serviceDic = serviceDicPlusRoadName;
                    [serviceStationAnnotationArray addObject:servicePointAnnotation];
                }
                if ([NSString isNotEmpty:[serviceDic objectForKey:@"Coor_Lat2"]]) { //有经纬度2
                    servicePointAnnotation = [[ServicePointAnnotation alloc] init];
                    float XCODE=[[serviceDic valueForKey:@"Coor_Lon2"] floatValue];
                    float YCODE =[[serviceDic valueForKey:@"Coor_Lat2"] floatValue];
                    CLLocationCoordinate2D coordinate = {YCODE,XCODE};
                    
                    NSMutableDictionary *serviceDicPlusRoadName = [[NSMutableDictionary alloc] initWithDictionary:serviceDic];
                    [serviceDicPlusRoadName setObject:roadName forKey:@"roadName"]; //添加高速路的路名
                    servicePointAnnotation.coordinate = coordinate;
                    servicePointAnnotation.title = [serviceDic objectForKey:@"RestName"];
                    servicePointAnnotation.serviceDic = serviceDicPlusRoadName;
                    [serviceStationAnnotationArray addObject:servicePointAnnotation];
                }
            }
        }
        [self.mapView addAnnotations:serviceStationAnnotationArray];
    }
    
    
}


//显示附近高速路上的收费站
- (void)showToll
{
    if(nearroadIds&&[nearroadIds count]>0){
        
        tollStationAnnotationAry=[[NSMutableArray alloc] init];
        NSLog(@"附近的公路ID：%@",nearroadIds);
        
        //获取附近公路的所有收费站
        _tollArray = [NSMutableArray new];
        NSDictionary *stationDic = [JSONUtil readJSON:@"StationByRoadID.json"];
        for (NSInteger i=0; i<[nearroadIds count]; i++) {
            NSString *nearroadID = [nearroadIds objectAtIndex:i];
            [_tollArray addObjectsFromArray:[stationDic objectForKey:nearroadID]];
        }
        
        StationPointAnnotation *pointAnnotaion;
        for (NSDictionary *station in _tollArray) {
            pointAnnotaion = [[StationPointAnnotation alloc] init];
            float XCODE=[[station valueForKey:@"xcode"] floatValue];
            float YCODE =[[station valueForKey:@"ycode"] floatValue];
            CLLocationCoordinate2D coordinate = {YCODE,XCODE};
            pointAnnotaion.coordinate=coordinate;
            pointAnnotaion.title=[station valueForKey:@"stationname"];
            pointAnnotaion.stationCalloutInfo=station;
            [tollStationAnnotationAry addObject:pointAnnotaion];
        }
        if([tollStationAnnotationAry count]>0)
            [self.mapView addAnnotations:tollStationAnnotationAry];
    }
    
    
    
}

// 全省交通路况
- (void)showAllRoad
{
    if(!allRoadFlag){
        allRoadFlag=YES;
        [self.allRoadBtn setImage:[UIImage imageNamed:@"allRoad-down"] forState:UIControlStateNormal];
        [self showHUD:@"显示全省交通路况.."];
        
        //多线程
        dispatch_async(dispatch_get_global_queue(0, 0), ^{
            roadOverlayAry =[[NSMutableArray alloc] init];
            NSArray *roadArray=[JSONUtil readJSON:@"Road.json"];
            for (NSDictionary *roadDic in roadArray) {
                NSString *roadId = [roadDic valueForKey:@"ROADID"];
                int index = [ComFun IndexOfAry:nearroadIds :roadId];
                //未标识交通路况
                if(index==-1)
                {
                    NSString *fileName=[NSString stringWithFormat:@"%@.json",roadId];
                    NSDictionary *roadCoordinateDic = [JSONUtil readJSON:fileName];
                    NSArray *roadCoordinateAry =[roadCoordinateDic valueForKey:@"Line"];
                    NSArray *speedAry;
                    for(NSDictionary *roadSpeed in speedArray)
                    {
                        if([roadId isEqualToString:[roadSpeed valueForKey:@"road"]])
                        {
                            speedAry=[roadSpeed valueForKey:@"speed"];
                            break;
                        }
                    }
                    
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
                        polyline.title=[NSString stringWithFormat:@"%d",[ComFun findSpeed:speedAry startStationId:startStationId endStationId:endStationId]];
                        [roadOverlayAry addObject:polyline];
                    }
                    
                }
            }
            
            //主线程更新UI
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.mapView addOverlays:roadOverlayAry];
                [self hiddenHUD];
            });
            
        });
        
        
    }
    else
    {
        if(roadOverlayAry&&[roadOverlayAry count]>0)
        {
            [self.mapView removeOverlays:roadOverlayAry];
            [roadOverlayAry removeAllObjects];
        }
        allRoadFlag=NO;
        [self.allRoadBtn setImage:[UIImage imageNamed:@"allRoad"] forState:UIControlStateNormal];
    }
    
}



#pragma mark- BMKLocationServiceDelegate method


//处理位置坐标更新
- (void)didUpdateBMKUserLocation:(BMKUserLocation *)userLocation
{
    [_mapView updateLocationData:userLocation];
    
    CLLocationCoordinate2D coordinate =userLocation.location.coordinate;
    // 记录当前位置
    [CommonData sharedCommonData].currentLocationCoordinate2D=coordinate;
    
    [self setMapRegionWithCoordinate:coordinate];
    
    //当前位置图标是隐藏的，调用自定义的PointAnnotation
    [self ShowCurrentPointAnnotation];
    
}


//传入经纬度,将baiduMapView 锁定到以当前经纬度为中心点的显示区域和合适的显示范围
- (void)setMapRegionWithCoordinate:(CLLocationCoordinate2D)coordinate
{
    if (!_isSetMapSpan) //从别的界面切换回来的时候会置位这个标志（重新确定一下所在城市）
    {
        // 获取所在城市
        [MapUtil getCityByCLLocationCoordinate2D:coordinate completedBlock:^(NSString *city) {
            [CommonData sharedCommonData].currentCity=city;
            NSLog(@"当前城市：%@",city);
            [[NSNotificationCenter defaultCenter] postNotificationName:@"WaitForCityRefresh" object:nil];
        }];
        _isSetMapSpan=YES;
        
        // 第一次显示附近的高速信息
        if(!nearroadIds){
            
            // 显示地图显示级别
            BMKCoordinateRegion region = BMKCoordinateRegionMake([CommonData sharedCommonData].currentLocationCoordinate2D, BMKCoordinateSpanMake(0.3, 0.3));//越小地图显示越详细
            [_mapView setRegion:region animated:YES];//执行设定显示范围
            [_mapView setCenterCoordinate:coordinate animated:YES];
            [self queryNearRoadID];
            [self ShowCurrentPointAnnotation];
            //根据提供的经纬度为中心原点 以动画的形式移动到该区域
        }
    }
    
}

#pragma mark- BMKMapViewDelegate

- (void)mapViewDidFinishLoading:(BMKMapView *)mapView {
    //适配iOS8，解决iOS8不能定位bug
    if([[[UIDevice currentDevice] systemVersion] floatValue] >=8.0)
    {
        locationManager = [[CLLocationManager alloc] init];
        [locationManager requestWhenInUseAuthorization];
        [locationManager startUpdatingLocation];
    }

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
        
    }
    else if ([annotation isKindOfClass:[ServicePointAnnotation class]])
    {
        static NSString *annotaionIdentifier = @"servicePointAnnotation";
        BMKAnnotationView *annotationView=[self.mapView dequeueReusableAnnotationViewWithIdentifier:annotaionIdentifier];
        if(!annotationView)
            annotationView = [[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:annotaionIdentifier];
        
        annotationView.image=[UIImage imageNamed:@"service-map"];
        annotationView.canShowCallout=YES;
        return annotationView;
    }
    else if([annotation isKindOfClass:[CurrentPointAnnotation class]]) //当前位置的PointAnnotation
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

// 点击了标注
- (void)mapView:(BMKMapView *)mapView didSelectAnnotationView:(BMKAnnotationView *)view
{
    StationPointAnnotation *annn = (StationPointAnnotation *)view.annotation;
    if ([view.annotation isKindOfClass:[StationPointAnnotation class]]) { //
        
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
        tempStationDic = annn.stationCalloutInfo; //拿到这个收费站的信息
    }
    else if([view isKindOfClass:[StationCalloutAnnotationView class]]){ //收费站气泡(只能响应一次)
        NSLog(@"%@",[tempStationDic objectForKey:@"stationname"]);
        UIActionSheet* actionSheet = [[UIActionSheet alloc] initWithTitle:@"把该收费站设置为：" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"起始站",@"终点站", nil]; //下钻页
        [actionSheet showInView:self.view];
    }

    if (view.annotation != nil) {
        [mapView setCenterCoordinate:view.annotation.coordinate animated:YES]; //地图中心改变
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
            lineColor= [[UIColor yellowColor]colorWithAlphaComponent:0.5 ];
        else
            lineColor= [[UIColor redColor] colorWithAlphaComponent:0.5];
        polylineView.strokeColor = lineColor;
        polylineView.lineWidth = 4.5;
        return polylineView;
    }
    
    return nil;
    
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

//当点击annotation view弹出的泡泡时，调用此接口（系统默认的气泡款式才回调）
- (void)mapView:(BMKMapView *)mapView annotationViewForBubble:(BMKAnnotationView *)view
{
    if ([view.annotation isKindOfClass:[AccidentPointAnnotation class]]) { //交通事故
        AccidentPointAnnotation* accidentPointAnnotation = (AccidentPointAnnotation*)view.annotation;
        NewsInfoVC* newsInfo = [[NewsInfoVC alloc] init];
        newsInfo.type = 1;
        newsInfo.dataDic = accidentPointAnnotation.accidentDic;
        [app.nav pushViewController:newsInfo animated:YES];
    }else if ([view.annotation isKindOfClass:[ConstructPointAnnotation class]]) { //道路施工
        ConstructPointAnnotation* constructPointAnnotation = (ConstructPointAnnotation*)view.annotation;
        NewsInfoVC* newsInfo = [[NewsInfoVC alloc] init];
        newsInfo.type = 2;
        newsInfo.dataDic = constructPointAnnotation.constructDic;
        [app.nav pushViewController:newsInfo animated:YES];
    }else if([view.annotation isKindOfClass:[ServicePointAnnotation class]]){ //服务区
        ServicePointAnnotation* servicePointAnnotation = (ServicePointAnnotation*)view.annotation;
        ServiceInfoVC* serviceInfo = [[ServiceInfoVC alloc] init];
        serviceInfo.dataDic = servicePointAnnotation.serviceDic;
        [app.nav pushViewController:serviceInfo animated:YES];
    }
}


/**
 *点中底图空白处会回调此接口
 */
- (void)mapView:(BMKMapView *)mapView onClickedMapBlank:(CLLocationCoordinate2D)coordinate{
     [mapView removeAnnotation:_stationCalloutAnnotation]; //清除收费站的气泡
}

#pragma mark - actionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    [_mapView removeAnnotation:_stationCalloutAnnotation]; //清除收费站的气泡
    NSMutableDictionary *dataDic = [NSMutableDictionary new];
    [dataDic setValue:tempStationDic forKey:@"stationDic"];
    switch (buttonIndex) {
        case 0:
            NSLog(@"作为起始站");
            [dataDic setValue:@"start" forKey:@"type"];
            break;
            
        case 1:
            NSLog(@"作为终点站");
            [dataDic setValue:@"end" forKey:@"type"];
            break;
            
        case 2: //取消键
            return;
    }
    [[NSNotificationCenter defaultCenter] postNotificationName:@"realTimeChangeToJourneyPlanning" object:dataDic];
    
}

#pragma mark- changeVC 界面跳转

//进入高速列表界面
-(void)goHighWayVC
{
    HighwayListVC *highwayListVC =[[HighwayListVC alloc] init];
    [app.nav pushViewController:highwayListVC animated:YES];
}

// 进入高速资讯列表界面
- (void)goNewsVC
{
    NewsVC *newsVC = [[NewsVC alloc] init];
    [app.nav pushViewController:newsVC animated:YES];
}

#pragma mark - QueryWeatherCityDelegate 请求天气城市
-(void)QueryWeatherCity
{
    _isSetMapSpan = NO;
    if (!_isSetMapSpan)//这里用一个变量判断一下,只在第一次锁定显示区域时
    {
        if (![CommonData sharedCommonData].currentLocationCoordinate2D.latitude) { //当用户从第二页进入的时候，即此时并没有存储到用户经纬度
            CLLocationCoordinate2D coordinate; //默认给个广州的经纬度
            coordinate.latitude = 23.161455;
            coordinate.longitude = 113.358039;
            [CommonData sharedCommonData].currentLocationCoordinate2D = coordinate;
            [ComFun alertWithMessageAndDelegate:@"温馨提示" :@"请到“实况”页中获取您的位置" :self];
            [CommonData sharedCommonData].isLocated = NO;
        }
        else
        {
            [CommonData sharedCommonData].isLocated = YES;
        }
        // 获取所在城市
        [MapUtil getCityByCLLocationCoordinate2D:[CommonData sharedCommonData].currentLocationCoordinate2D completedBlock:^(NSString *city) {
            [CommonData sharedCommonData].currentCity=city;
            NSLog(@"当前城市：%@",city);
            [[NSNotificationCenter defaultCenter] postNotificationName:@"WaitForCityRefresh" object:nil];
        }];
        _isSetMapSpan=YES;
    }
}
@end
