//
//  DirvingRouteDetailVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/19.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "DirvingRouteDetailVC.h"
#import "DirvingRouteDetailStationCell.h"
#import "DirvingRouteDetailRoadCell.h"
#import "DirvingRouteDetailMapVC.h"
#import "AppDelegate.h"
#import "FunctionIconButton.h"
#import "NewsInfoVC.h"
#import "ServiceInfoVC.h"



@implementation DirvingRouteDetailVC



- (void)viewDidLoad
{
    [super viewDidLoad];
    
    int type = [[_planDic valueForKeyPath:@"type"] intValue];
    NSString *title=@"最短时间";
    if(type ==1)
    {
        title=@"最短距离";
    }
    else if(type==2)
    {
        title=@"最少收费";
    }
    self.title =title;
    
    self.timeWidthConstraints.constant = SCREEN_WIDTH/4;
    self.kiloWidthConstraints.constant = SCREEN_WIDTH/4;
    self.moneyWidthConstraints.constant = SCREEN_WIDTH/4;
    
    self.line1WidthConstraints.constant = 0.5;
    self.line2WidthConstraints.constant = 0.5;
    
    // 定位
    _locService = [[BMKLocationService alloc]init];
    // 语音播报
     soundManager = [BNaviSoundManager getInstance];
    
    //设置导航条右边按钮
    UIImage* img=[UIImage imageNamed:@"map"];
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.frame =CGRectMake(0, 0, 30, 30);
    [btn setBackgroundImage:img forState:UIControlStateNormal];
    [btn addTarget: self action: @selector(showMapMode:) forControlEvents: UIControlEventTouchUpInside];
    UIBarButtonItem* item=[[UIBarButtonItem alloc]initWithCustomView:btn];
    
    //语音按钮
    voiceBtn = [[UIButton alloc] initWithFrame:CGRectMake(0, 0, 30, 30)];
    [voiceBtn setBackgroundImage:[UIImage imageNamed:@"sound-start"] forState:UIControlStateNormal];
    [voiceBtn addTarget:self action:@selector(clickSound) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem* item2=[[UIBarButtonItem alloc]initWithCustomView:voiceBtn];
    
    self.navigationItem.rightBarButtonItems=[[NSArray alloc] initWithObjects:item,item2,nil];

    //导航按钮
    [self.navigationBtn addTarget:self action:@selector(clickNavigation) forControlEvents:UIControlEventTouchUpInside];
    self.navigationBtn.layer.cornerRadius=4;
    
    _moneyLabel.text=[NSString stringWithFormat:@"%@元",[_planDic valueForKey:@"totalFee"]];
    _kilometerLabel.text=[NSString stringWithFormat:@"%@公里",[_planDic valueForKey:@"totalDistance"]];
    _timeLabel.text=[NSString stringWithFormat:@"%@分钟",[_planDic valueForKey:@"totalTime"]];
    
    //系统推送
    self.pushNum.layer.cornerRadius = 16/2;
    self.pushNum.layer.masksToBounds = YES;
    
    allRoadIDs = [NSMutableArray new];
    realServiceAry = [[NSMutableArray alloc]init];
    realAccidentAry = [[NSMutableArray alloc] init];
    realConstructAry = [[NSMutableArray alloc] init];
    
    // 加载详情信息
    [self loadData];
    
    isOnHighWay = NO; //默认不在高速路上
    nextCarIndex = 0; //默认下一站为起始站
    
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    // 定位
    if([[[UIDevice currentDevice] systemVersion] floatValue] >=8.0)
    {
        locationManager = [[CLLocationManager alloc] init];
        [locationManager requestWhenInUseAuthorization];
        [locationManager startUpdatingLocation];
    }
    _locService.delegate = self;
    [_locService startUserLocationService];
    
    //初始化引导页
    if ([[SqliteUtil sharedSqliteUtil] queryIndexInfo:@"DirvingRouteDetailVC-Index"]) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(IndexViewGone) name:@"IndexViewGone" object:nil];
        [self initIndexView:@"DirvingRouteDetailVC-Index"];
    }
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(loadNearRoadIDsDone) name:@"loadNearRoadIDsDone" object:nil];
    
    //起始城市
    startCityLabel = [[UILabel alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-57, 64+34, 57, 20)];
    startCityLabel.backgroundColor = RGBCOLOR(136, 199, 163);
    startCityLabel.text = startCityStr;
    startCityLabel.font = [UIFont systemFontOfSize:13];
    startCityLabel.textColor = [UIColor whiteColor];
    [app.window addSubview:startCityLabel];
    //终点城市
    endCityLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT-20-36, 57, 20)];
    endCityLabel.backgroundColor = RGBCOLOR(136, 199, 163);
    endCityLabel.text = endCityStr;
    endCityLabel.font = [UIFont systemFontOfSize:13];
    endCityLabel.textColor = [UIColor whiteColor];
    [app.window addSubview:endCityLabel];

}

-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    //定位
    _locService.delegate = nil;
    [_locService stopUserLocationService];
    //引导页
    [[NSNotificationCenter defaultCenter]removeObserver:self name:@"loadNearRoadIDsDone" object:nil];
    
    [startCityLabel removeFromSuperview];
    [endCityLabel removeFromSuperview];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - custem method
//获得行程路ID之后再加载其他数据
-(void)loadNearRoadIDsDone
{
    //加载交通事故和道路施工信息
    [self loadAccidentAndConstructionInfo];
    //加载服务区信息
    [self loadServiceArea];
    
    //加载各路段速度信息
    sortSpeedDic = [[NSMutableDictionary alloc] init];
    roadIndex = -1;
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(loadRoadSpeed) name:@"loadRoadSpeed" object:nil];
    [self loadRoadSpeed];
}

//逐一加载各高速路速度
-(void)loadRoadSpeed
{
    int i = allRoadIDs.count-1 ;
    if(roadIndex<i)
    {
        roadIndex++;
        [self querySpeedByRoadID:[allRoadIDs objectAtIndex:roadIndex]];
    }else
    {
        [[NSNotificationCenter defaultCenter]removeObserver:self name:@"loadRoadSpeed" object:nil];
    }
}

//查询各路段的速度
- (void)querySpeedByRoadID:(NSString*)roadId
{
    NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:roadId,@"road",nil];
    [DataDAO getRoadSpeed:param withCompleted:^(id result) {
        if(result)
        {
            [[result valueForKey:@"speed"] objectAtIndex:0];
            [sortSpeedDic setObject:[[result valueForKey:@"speed"] objectAtIndex:0] forKey:roadId];
        }
        [[NSNotificationCenter defaultCenter]postNotificationName:@"loadRoadSpeed" object:nil];

    } withFailure:^(id error) {
        [self showHUD:error :FAILEDICON :YES];
        [[NSNotificationCenter defaultCenter]postNotificationName:@"loadRoadSpeed" object:nil];

    }];
    
}
// 获取服务区信息
-(void)loadServiceArea
{
    serviceDic = [[NSMutableDictionary alloc] init];
    allServiceAry = [[NSMutableArray alloc] init];
    for (NSString* roadID in allRoadIDs) {
        NSDictionary* roadDic = [[JSONUtil readJSON:@"serviceArea.json"] objectForKey:roadID];
        NSString* roadName = [roadDic objectForKey:@"RoadName"];
        NSArray* serviceArray = [roadDic objectForKey:@"RESTS"];
        NSMutableArray* serviceAryByRoad = [[NSMutableArray alloc] init];
        for (NSDictionary* service in serviceArray) { //加高速路名
            NSMutableDictionary *mutableDic = [[NSMutableDictionary alloc] initWithDictionary:service copyItems:YES];
            [mutableDic setObject:roadName forKey:@"roadName"];
            [serviceAryByRoad addObject:mutableDic];
            [allServiceAry addObject:mutableDic];
        }
        [serviceDic setObject:serviceAryByRoad forKey:roadID];
    }
    [_tableView reloadData];
}

//获取交通事故、道路施工
-(void)loadAccidentAndConstructionInfo
{
    int pageSize=8;
    accidentAry = [[NSMutableArray alloc] init];
    constructAry = [[NSMutableArray alloc] init];
    
    //每条路都拿前8条，再选出今天发生的事件
    for (NSString* roadID in allRoadIDs) {
        
        NSString *queryTime=[NSDate formateDate:[NSDate date] fromate:@"yyyy-MM-dd HH:mm:ss"];
        NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:1],@"type",[NSNumber numberWithInt:pageSize],@"pageSize",queryTime,@"queryTime",roadID,@"road",nil];
        
        [DataDAO getTrafficinfo:param withCompleted:^(id result) {
            [accidentAry addObjectsFromArray:[ComFun selectTodayevent:result]];
            [_tableView reloadData];
        } withFailure:^(id error){
            
        }];
        
        param=[[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:2],@"type",[NSNumber numberWithInt:pageSize],@"pageSize",queryTime,@"queryTime",roadID,@"road",nil];
        [DataDAO getTrafficinfo:param withCompleted:^(id result) {
            [constructAry addObjectsFromArray:[ComFun selectTodayevent:result]];
            [_tableView reloadData];
        } withFailure:^(id error){

        }];

    }
    
    
}


//搜索这两站之间有没有交通事故或者道路施工
-(NSDictionary*)searchAccidentFixIconAry:(NSArray*)searchAry arrayIndex:(NSInteger)index startStationName:(NSString*)startStation endStationName:(NSString*)endStation
{

    for (NSDictionary* Dic in searchAry ) {
        NSString *dicStartName = [[Dic objectForKey:@"startStation"] substringToIndex:2];

        NSString *dicEndName = [[Dic objectForKey:@"endStation"] substringToIndex:2];
        
        //起始站相同
        if ([dicStartName isEqualToString:[startStation substringToIndex:2]] || [startStation rangeOfString:dicStartName].location != NSNotFound) {
            if (index >=2 ) {
                if ([dicEndName isEqualToString:[endStation substringFromIndex:2]] || [endStation rangeOfString:dicEndName].location != NSNotFound) { //说明是逆向，不要 （检测范围为2）
                    return nil;
                }
                if ([dicEndName isEqualToString:[[[stationArray objectAtIndex:index-1] objectForKey:@"stationname"] substringToIndex:2]] || [dicEndName isEqualToString:[[[stationArray objectAtIndex:index-2] objectForKey:@"stationname"] substringToIndex:2]]) {//说明是逆向，不要
                    return nil;
                }
            }
                return Dic;
        }
        
        //结束站相同
        if ([dicEndName isEqualToString:[endStation substringFromIndex:2]] || [endStation rangeOfString:dicEndName].location != NSNotFound) {
            if ([dicStartName isEqualToString:[startStation substringFromIndex:2]] || [startStation rangeOfString:dicStartName].location != NSNotFound) { //说明是逆向，不要 （检测范围为2）
                return nil;
            }
            if (stationArray.count > index+3) {
                if ([dicStartName isEqualToString:[[[stationArray objectAtIndex:index+2] objectForKey:@"stationname"] substringToIndex:2]] || [dicStartName isEqualToString:[[[stationArray objectAtIndex:index+3] objectForKey:@"stationname"] substringToIndex:2]]) { //说明是逆向，不要 (检测范围为2)
                    return nil;
                }
            }
            return Dic;
            
        }
    
    }
    return nil;
}

//查找服务区
-(NSDictionary*)searchServiceDic:(NSDictionary*)searchDic roadID:(NSString*)roadID startID:(NSString*)startID endID:(NSString*)endID
{
    NSArray* serviceAry = [searchDic objectForKey:roadID];
    for (NSDictionary* service in serviceAry) { //得到本高速路的所有服务区
        if ([NSString isEmpty:[service objectForKey:@"StartID"]]) {
            continue;
        }
        if ([[[service objectForKey:@"StartID"] objectAtIndex:1] isEqualToString:startID] && [[[service objectForKey:@"EndID"] objectAtIndex:1] isEqualToString:endID]) {
            return service;
        }
        else if ([[[service objectForKey:@"StartID"] objectAtIndex:1] isEqualToString:endID] && [[[service objectForKey:@"EndID"] objectAtIndex:1] isEqualToString:startID])
        {
            return service;
        }
    }
    return nil;
}

// 查询起始站和终点站所在的城市
-(void)queryDirectionCity{
    CLLocationCoordinate2D startCoor,endCoor;
    startCoor.latitude = [[[stationArray objectAtIndex:0] objectForKey:@"ycode"] floatValue];
    startCoor.longitude = [[[stationArray objectAtIndex:0] objectForKey:@"xcode"] floatValue];
    endCoor.latitude = [[[stationArray lastObject] objectForKey:@"ycode"] floatValue];
    endCoor.longitude = [[[stationArray lastObject] objectForKey:@"xcode"] floatValue];
    
    [MapUtil getCityByCLLocationCoordinate2D:startCoor completedBlock:^(NSString *city) {
        startCityStr =[[NSString alloc] initWithFormat:@" %@方向 ",city];
        startCityLabel.text = startCityStr;
    }];
    
    [MapUtil getCityByCLLocationCoordinate2D:endCoor completedBlock:^(NSString *city) {
        endCityStr =[[NSString alloc] initWithFormat:@"%@方向 ",city];
        endCityLabel.text = endCityStr;
    }];
    
}

//引导页
-(void)IndexViewGone
{
    [self showMapMode:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"IndexViewGone" object:nil];
    [[SqliteUtil sharedSqliteUtil] updateInfo:@"DirvingRouteDetailVC-Index" state:@"hide"];
}

//获取数据，行程路ID、站ID
- (void)loadData
{
    NSString *type =[_planDic valueForKeyPath:@"type"];
    NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:_requestIdentifier,@"requestIdentifier",type,@"type",nil];
    [DataDAO getRoadPlanDetail:param withCompleted:^(id result) {
        NSString *speedStr = [[result valueForKey:@"speed"] objectAtIndex:0];
        speedArray=[speedStr componentsSeparatedByString:@","];
        speedCount =(int)[speedArray count];
        NSString *stationStr = [[result valueForKey:@"station"] objectAtIndex:0];
        NSLog(@"从服务器收到的路信息:%@",stationStr);
        NSArray *roadStationIdArray =[stationStr componentsSeparatedByString:@","];
        [self convertRoadStationIdToStationDic:roadStationIdArray];
        NSLog(@"allRoadsID:%@",allRoadIDs);
        stationCount =(int )[stationArray count];
        //下一个站经纬度(默认是起始站)
        nextStationCoor.longitude = [[[stationArray objectAtIndex:0] objectForKey:@"xcode"] floatValue];
        nextStationCoor.latitude = [[[stationArray objectAtIndex:0] objectForKey:@"ycode"] floatValue];
        //通知加载实况信息
        [[NSNotificationCenter defaultCenter]postNotificationName:@"loadNearRoadIDsDone" object:nil];
        // 请求起始和终点站的城市
        [self queryDirectionCity];
    } withFailure:^(id error) {
         [self showHUD:error :FAILEDICON :YES];
    }];
    

}

//提取途经路ID以及站ID
- (void)convertRoadStationIdToStationDic:(NSArray *)roadStationAry
{
    wayPointAry = [[NSMutableArray alloc] init];
    stationArray = [[NSMutableArray alloc] init];
    NSMutableArray *roadArray = [[NSMutableArray alloc] init];
    if(roadStationAry&&[roadStationAry count]>0)
    {
        NSDictionary *stationByRoadIDDic = [JSONUtil readJSON:@"StationByRoadID.json"];
        NSString* lastRoadID = [[[roadStationAry objectAtIndex:0] componentsSeparatedByString:@"|"]objectAtIndex:0]; //起始为第一条路ID
        NSDictionary* lastStation;
        bool isNewRoad = NO;
        int index=0;
        
        for (NSString* roadStationString in roadStationAry) { //每一个roadid+stationid
            NSArray *roadStation =[roadStationString componentsSeparatedByString:@"|"];
            NSString *roadID = [roadStation objectAtIndex:0];
            NSString *stationID = [roadStation objectAtIndex:1];
            [roadArray addObject:roadID];
            
            if (![lastRoadID isEqualToString:roadID]) { //新的高速路
                isNewRoad = YES;
                lastRoadID = roadID;
            }
            
            for (NSDictionary* station in [stationByRoadIDDic objectForKey:roadID]) { //在每条路中搜站
                if ([[station objectForKey:@"stationid"] isEqualToString:stationID]) {
                    [stationArray addObject:station]; //添加站
                    //找出跨路段的途经站
                    if (isNewRoad) {
                        //去掉一条路只有一个站的途经站
                        if (roadStationAry.count>(index+1) && [[[[roadStationAry objectAtIndex:index+1] componentsSeparatedByString:@"|"]objectAtIndex:0] isEqualToString:roadID] ) {
                            //防止加入相同的站
                            if (lastStation&&![[wayPointAry lastObject] isEqualToDictionary:lastStation]) { //防止lastStation未初始化
                                [wayPointAry addObject:lastStation];
                            }
                            if (![[wayPointAry lastObject] isEqualToDictionary:station]) {
                                [wayPointAry addObject:station];
                            }
                        }
                        
                        isNewRoad = NO;
                    }
                    lastStation = station;
                }
            }
            index++;
        }
        lastRoadID = @"";
        for (NSString* roadID in roadArray) { //提取路ID集合
            if (![roadID isEqualToString:lastRoadID]) {
                [allRoadIDs addObject:roadID];
                lastRoadID = roadID;
            }
        }
    }
    
    //获得按路ID分好类的stationDic
    sortStationDic = [[NSMutableDictionary alloc] init];
    NSString* lastRoadID = [[stationArray objectAtIndex:0] objectForKey:@"roadid"];
    NSMutableArray* ary = [[NSMutableArray alloc] init];
    for (NSDictionary* stationDic in stationArray) {
        if (![[stationDic objectForKey:@"roadid"] isEqualToString:lastRoadID]) {
            NSArray* tempAry = [[NSArray alloc] initWithArray:ary copyItems:YES];
            [sortStationDic setObject:tempAry forKey:lastRoadID];
            [ary removeAllObjects];
            [ary addObject:stationDic];
            lastRoadID = [stationDic objectForKey:@"roadid"];
        }
        else if([stationDic isEqualToDictionary:[stationArray lastObject]])
        {
            [ary addObject:stationDic];
            NSArray* tempAry = [[NSArray alloc] initWithArray:ary copyItems:YES];
            [sortStationDic setObject:tempAry forKey:lastRoadID];
            NSLog(@"%d",stationArray.count);
        }else{
            [ary addObject:stationDic];
        }
        
    }
}


- (NSDictionary *)findStation:(NSString *)roadId:(NSString *)stationId
{
    NSDictionary *station;
    //读取站的数据
    NSDictionary  *stationDic =[JSONUtil readJSON:@"StationByCity.json"];
    NSDictionary *dic=[JSONUtil readJSON:@"City.json"];
    NSArray *cityArray=[dic valueForKey:@"citys"];
    for(NSString *city in cityArray)
    {
        NSArray *stationAry = [stationDic valueForKeyPath:city];
        BOOL find =NO;
        for(NSDictionary *temp in stationAry)
        {
            if([roadId isEqualToString:[temp valueForKeyPath:@"roadid"]]&&[stationId isEqualToString:[temp valueForKeyPath:@"stationid"]])
            {
                station=temp;
                find=YES;
                break;
            }
        }
        if(find)
        {
            break;
        }
        
    }
    
    return station;
    
}

#pragma mark - chagneVC 导航
// 点击了语音按钮
-(void)clickSound{
    static BOOL isOpenSound = NO;
    if (isOpenSound) { //开启语音
        [voiceBtn setBackgroundImage:[UIImage imageNamed:@"sound-start"] forState:UIControlStateNormal];
        isOpenSound = NO;
    }else{ // 关闭语音
        [voiceBtn setBackgroundImage:[UIImage imageNamed:@"sound-stop"] forState:UIControlStateNormal];
        isOpenSound = YES;
    }
    
}

//点击了导航按钮
-(void)clickNavigation{
    
    if(isOnHighWay){ //已经在高速
        
        [[[UIAlertView alloc] initWithTitle:@"温馨提示" message:@"您已经在高速路附近了！" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
        
    }else{
        
        UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:@"请选择导航的类型" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"模拟导航",@"真实导航", nil];
        [actionSheet showInView:self.view];
        
    }
}

- (void)startNavi
{
    NSMutableArray *nodesArray = [[NSMutableArray alloc]initWithCapacity:2];
    //起点 传入的是原始的经纬度坐标，若使用的是百度地图坐标，可以使用BNTools类进行坐标转化
    BNRoutePlanNode *startNode = [[BNRoutePlanNode alloc] init];
    startNode.pos = [[BNPosition alloc] init];
    startNode.pos.x = [CommonData sharedCommonData].currentLocationCoordinate2D.longitude;
    startNode.pos.y = [CommonData sharedCommonData].currentLocationCoordinate2D.latitude;
    startNode.pos.eType = BNCoordinate_BaiduMapSDK;
    [nodesArray addObject:startNode];
    
    //也可以在此加入1到3个的途经点
    
//    BNRoutePlanNode *midNode = [[BNRoutePlanNode alloc] init];
//    midNode.pos = [[BNPosition alloc] init];
//    midNode.pos.x = 116.12;
//    midNode.pos.y = 39.05087;
//    midNode.pos.eType = BNCoordinate_BaiduMapSDK;
//    [nodesArray addObject:midNode];
    
    //终点
    BNRoutePlanNode *endNode = [[BNRoutePlanNode alloc] init];
    endNode.pos = [[BNPosition alloc] init];
    endNode.pos.x = [[[stationArray objectAtIndex:0] objectForKey:@"xcode"] floatValue];
    endNode.pos.y = [[[stationArray objectAtIndex:0] objectForKey:@"ycode"] floatValue];
    endNode.pos.eType = BNCoordinate_BaiduMapSDK;
    [nodesArray addObject:endNode];
    
    [BNCoreServices_RoutePlan startNaviRoutePlan:BNRoutePlanMode_Recommend naviNodes:nodesArray time:nil delegete:self userInfo:nil];
}


// 地图模式展示高速公路
- (IBAction)showMapMode:(id)sender
{
    DirvingRouteDetailMapVC *dirvingRouteDetailMapVC =[[DirvingRouteDetailMapVC alloc] init];
    dirvingRouteDetailMapVC.stationArray=stationArray;
    dirvingRouteDetailMapVC.title=self.title;
    dirvingRouteDetailMapVC.accidentAry = realAccidentAry;
    dirvingRouteDetailMapVC.fixAry = realConstructAry;
    dirvingRouteDetailMapVC.serviceAry = realServiceAry;
    dirvingRouteDetailMapVC.wayPointsAry = wayPointAry;
    dirvingRouteDetailMapVC.sortSpeedDic = sortSpeedDic;
    dirvingRouteDetailMapVC.allRoadIDs = allRoadIDs;
    dirvingRouteDetailMapVC.sortStationDic = sortStationDic;
    [app.nav pushViewController:dirvingRouteDetailMapVC animated:YES];
}

#pragma mark - actionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    switch (buttonIndex) {
        case 0: //模拟导航
            _naviType = BN_NaviTypeSimulator; //这两个必须放在一起才能进入导航界面
            [self startNavi];
            break;
        case 1: //真实导航
            _naviType = BN_NaviTypeReal;
            [self startNavi];
            break;
        default: //取消
            return;
    }
    //[self startNavi];
}

#pragma  mark- UITableView Delegate

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
//    return  20.0f;
    return 34.0f;
}
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return 0.0f;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

//Return how many rows in the table
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if(stationCount>0)
        return (stationCount*2-1);
    else
        return 0;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    int  row =(int)indexPath.row;
    if(row%2==0)
        return 43.0f;
    else
        return 77.0f;
}

// Return a cell for the ith row
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    int  row =(int)indexPath.row;
    if(row%2==0)
    {
        static NSString * identifier =@"DirvingRouteDetailStationCell";
        DirvingRouteDetailStationCell *cell =(DirvingRouteDetailStationCell *)[tableView dequeueReusableCellWithIdentifier:identifier];
        if(!cell)
        {
            NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:identifier owner:nil options:nil];
            for(id currentObject in topLevelObjects)
            {
                if([currentObject isKindOfClass:[DirvingRouteDetailStationCell class]])
                {
                    cell = (DirvingRouteDetailStationCell *)currentObject;
                    cell.backgroundColor=[UIColor clearColor];
                    cell.selectionStyle=UITableViewCellSelectionStyleNone;
                    break;
                }
            }
        }
        NSDictionary *station=[stationArray objectAtIndex:row/2];
        cell.stationLabel.text=[station valueForKeyPath:@"stationname"];
        return cell;
    }
    else
    {
        static NSString * identifier =@"DirvingRouteDetailRoadCell";
        DirvingRouteDetailRoadCell *cell =(DirvingRouteDetailRoadCell *)[tableView dequeueReusableCellWithIdentifier:identifier];
        if(!cell)
        {
            NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:identifier owner:nil options:nil];
            for(id currentObject in topLevelObjects)
            {
                if([currentObject isKindOfClass:[DirvingRouteDetailRoadCell class]])
                {
                    cell = (DirvingRouteDetailRoadCell *)currentObject;
                    cell.backgroundColor=[UIColor clearColor];
                    cell.selectionStyle=UITableViewCellSelectionStyleNone;
                    break;
                }
            }
        }
        
        //获得该cell前后的站名、站ID、路ID
        NSDictionary *station=[stationArray objectAtIndex:row/2];
        NSString* startStation = [station valueForKeyPath:@"stationname"];
        NSString* startID = [station valueForKey:@"stationid"];
        NSString* roadID = [station valueForKey:@"roadid"];
        
        if ((row/2+1)<stationArray.count) {
            station=[stationArray objectAtIndex:row/2+1];
        }
        NSString* endStation = [station valueForKeyPath:@"stationname"];
        NSString* endID = [station valueForKey:@"stationid"];
        
        NSDictionary *accidentDic;
        NSDictionary *constructDic;
        NSDictionary *service;
        
        //交通事故
        if ((accidentDic = [self searchAccidentFixIconAry:accidentAry arrayIndex:row/2 startStationName:startStation endStationName:endStation])!=nil) {
            cell.accidentButton.hidden = NO;
            cell.accidentButton.infoDic = accidentDic;
            [cell.accidentButton addTarget:self action:@selector(functionIconSelect:) forControlEvents:UIControlEventTouchUpInside];
            [realAccidentAry addObject:accidentDic];
        }
        
        //道路施工 
        if ((constructDic = [self searchAccidentFixIconAry:constructAry arrayIndex:row/2 startStationName:startStation endStationName:endStation])!=nil) {
            cell.fixButton.hidden = NO;
            cell.fixButton.infoDic = constructDic;
            [cell.fixButton addTarget:self action:@selector(functionIconSelect:) forControlEvents:UIControlEventTouchUpInside];
            [realConstructAry addObject:constructDic];
        }
        
        //服务区
        if ((service = [self searchServiceDic:serviceDic roadID:roadID startID:startID endID:endID]) != nil) {
            cell.serviceButton.hidden = NO;
            cell.serviceButton.infoDic = service;
            [cell.serviceButton addTarget:self action:@selector(serviceIconSelect:) forControlEvents:UIControlEventTouchUpInside];
            [realServiceAry addObject:service];
        }
        
        //显示小车位置（当前位置）
        if(isOnHighWay){
            if(row/2 == nextCarIndex-1){
                cell.car.hidden = NO;
                 // 播报信息
                if(accidentDic!=nil) isAccidentOccur = YES;
                if(constructDic!=nil) isFixOccur = YES;
                if(service!=nil) isHaveService = YES;
            }
        }
        
        UIImage *image;
        int speed =[[speedArray objectAtIndex:row/2] intValue];
        if(speed>60)
        {
            image =[UIImage imageNamed:@"Road-Green"];
            cell.kilometreLabel.textColor=RGBCOLOR(80.0f, 182.0f, 124.0f);
            cell.speedLabel.textColor=RGBCOLOR(80.0f, 182.0f, 124.0f);
        }
        else if(30<speed && speed<=60)
        {
            image = [UIImage imageNamed:@"Road-Yellow"];
            
            cell.kilometreLabel.textColor=RGBCOLOR(254.0f, 201.0f, 118.0f);
            cell.speedLabel.textColor=RGBCOLOR(254.0f, 201.0f, 118.0f);
        }
        else
        {
            image = [UIImage imageNamed:@"Road-Red"];
            cell.kilometreLabel.textColor=RGBCOLOR(255.0f, 120.0f, 98.0f);
            cell.speedLabel.textColor=RGBCOLOR(255.0f, 120.0f, 98.0f);
        }
        cell.roadImageView.image =image;
        cell.speedLabel.text=[speedArray objectAtIndex:row/2];
        return cell;
    }
}

//选中交通事故图标
-(void)functionIconSelect:(id)sender
{
    FunctionIconButton* btn = (FunctionIconButton*)sender;
    NSDictionary* dic = btn.infoDic;
    NewsInfoVC *newsInfoVC = [[NewsInfoVC alloc] init];
    newsInfoVC.type= [[dic objectForKey:@"type"] intValue];
    newsInfoVC.dataDic=dic;
    [app.nav pushViewController:newsInfoVC animated:YES];
}

//点中服务区图标
-(void)serviceIconSelect:(id)sender
{
    FunctionIconButton* btn = (FunctionIconButton*)sender;
    ServiceInfoVC *serviceInfo = [[ServiceInfoVC alloc] init];
    serviceInfo.dataDic = btn.infoDic;
    [app.nav pushViewController:serviceInfo animated:YES];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)newIndexPath
{
    // 取消选中效果
    [tableView deselectRowAtIndexPath:newIndexPath animated:NO];
}


#pragma mark - BNNaviRoutePlanDelegate
//算路成功回调
-(void)routePlanDidFinished:(NSDictionary *)userInfo
{
    NSLog(@"算路成功");
    //路径规划成功，开始导航
    [BNCoreServices_UI showNaviUI:_naviType delegete:self isNeedLandscape:YES];
}

//算路失败回调
- (void)routePlanDidFailedWithError:(NSError *)error andUserInfo:(NSDictionary *)userInfo
{
    NSLog(@"算路失败");
    if ([error code] == BNRoutePlanError_LocationFailed) {
        NSLog(@"获取地理位置失败");
    }
    else if ([error code] == BNRoutePlanError_LocationServiceClosed)
    {
        NSLog(@"定位服务未开启");
    }
}

//算路取消回调
-(void)routePlanDidUserCanceled:(NSDictionary*)userInfo {
    NSLog(@"算路取消");
}

#pragma mark - BNNaviUIManagerDelegate

//退出导航回调
-(void)onExitNaviUI:(NSDictionary*)extraInfo
{
    NSLog(@"退出导航");
    
    int distance = BMKMetersBetweenMapPoints(BMKMapPointForCoordinate([CommonData sharedCommonData].currentLocationCoordinate2D),BMKMapPointForCoordinate(nextStationCoor));
    
    //判断是否在高速路
    if (distance < 800) {
        isOnHighWay = YES;
        [[[UIAlertView alloc]initWithTitle:@"温馨提示" message:[[NSString alloc] initWithFormat:@"您现在距离起始站还有：%dm，请注意观察路标，进入高速路",distance] delegate:self cancelButtonTitle:@"知道了" otherButtonTitles:nil] show];
    }else{
        isOnHighWay = NO;
        
        [[[UIAlertView alloc]initWithTitle:@"温馨提示" message:[[NSString alloc] initWithFormat:@"您现在距离起始站还有：%0.2fkm，建议您再次使用导航功能",distance/1000.0] delegate:self cancelButtonTitle:@"知道了" otherButtonTitles:nil] show];
    }
}

//退出导航声明页面回调
- (void)onExitexitDeclarationUI:(NSDictionary*)extraInfo
{
    NSLog(@"退出导航声明页面");
}


#pragma mark - BMKLocationServiceDelegate 定位
- (void)didUpdateBMKUserLocation:(BMKUserLocation *)userLocation{
   
    [CommonData sharedCommonData].currentLocationCoordinate2D = userLocation.location.coordinate;
//    NSLog(@"%f,%f",userLocation.location.coordinate.longitude,userLocation.location.coordinate.latitude);
    
    while (1) { //保证每次进来只提交一次语音请求（提交多次不会依次播报）
        if (![soundManager isTTSPlaying]) { //当前麦克风空闲
            if (isAccidentOccur) {
                [soundManager playTTSText:@"前方路段有交通事故，请绕道慢行"];
                isAccidentOccur = NO;
                break;
            }
            if (isFixOccur) {
                [soundManager playTTSText:@"前方路段有道路施工"];
                isFixOccur = NO;
                break;
            }
            if (isHaveService) {
                [soundManager playTTSText:@"前方路段有服务区"];
                isHaveService = NO;
                break;
            }

        }
        break;
    }
    
    // 计算当前位置到下一个收费站的距离
    int distance = BMKMetersBetweenMapPoints(BMKMapPointForCoordinate(userLocation.location.coordinate),BMKMapPointForCoordinate(nextStationCoor));
//    NSLog(@"距离下一站：%d",distance);
    
    if (!isOnHighWay) { //还不在高速路上 (距离高速路起始站还很远)
        if(distance<1000){
            isOnHighWay = YES;
        }
    }else{ //已经在高速路上，判断小车的位置
        if(distance<1000){
            nextCarIndex++;
            if (nextCarIndex>=[stationArray count]) { //说明已经到达终点站了
                [soundManager playTTSText:[[NSString alloc] initWithFormat:@"您已经到达目的地附近，请注意观看路牌，本次行程费率大概为%@，感谢您的使用！",[_planDic valueForKey:@"totalFee"]]];
                [[[UIAlertView alloc] initWithTitle:@"温馨提示" message:[[NSString alloc] initWithFormat:@"您已经到达目的地附近，请注意观看路牌，本次行程费率大概为%@，感谢您的使用！",[_planDic valueForKey:@"totalFee"]]delegate:self cancelButtonTitle:@"好评" otherButtonTitles: nil] show];
                [_locService stopUserLocationService]; //结束定位
                
                return;
            }
            //下一个站 ,更改小车位置
            nextStationCoor.longitude = [[[stationArray objectAtIndex:nextCarIndex] objectForKey:@"xcode"] floatValue];
            nextStationCoor.latitude = [[[stationArray objectAtIndex:nextCarIndex] objectForKey:@"ycode"] floatValue];
            [_tableView reloadData];
        }
    }

}

@end
