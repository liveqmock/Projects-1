//
//  HighwayInfoVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/7/1.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "HighwayInfoVC.h"
#import "AppDelegate.h"
#import "DirvingRouteDetailStationCell.h"
#import "HighwayInfoRoadCell.h"
#import "HighwayInfoAtMapVC.h"
#import "HighwayDetailStationCell.h"
#import "NewsInfoVC.h"
#import "FunctionIconButton.h"
#import "ServiceInfoVC.h"


@implementation HighwayInfoVC



- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    roadName=@"高速详情";
    if(_roadDic){
        roadId =[_roadDic valueForKeyPath:@"ROADID"];
        roadName =[_roadDic valueForKey:@"ROADNAME"];
    }
    self.title =roadName;
    //设置导航条右边按钮
    UIImage* img=[UIImage imageNamed:@"map"];
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.frame =CGRectMake(0, 0, 32, 32);
    [btn setBackgroundImage:img forState:UIControlStateNormal];
    [btn addTarget: self action: @selector(showMapMode:) forControlEvents: UIControlEventTouchUpInside];
    UIBarButtonItem* item=[[UIBarButtonItem alloc]initWithCustomView:btn];
    self.navigationItem.rightBarButtonItem=item;
    
    // 从数据库中加载站信息
    [self loadStations];
    // 查询站之间的速度
    [self loadData];
    // 获取交通事故、施工信息
    [self loadAccidentAndConstructionInfo];
    // 获取服务区信息
    [self loadServiceInfo];
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    //起始城市
    startCityLabel = [[UILabel alloc] initWithFrame:CGRectMake(SCREEN_WIDTH-57, 64, 57, 20)];
    startCityLabel.backgroundColor = RGBCOLOR(136, 199, 163);
    startCityLabel.text = startCityStr;
    startCityLabel.font = [UIFont systemFontOfSize:13];
    startCityLabel.textColor = [UIColor whiteColor];
    [app.window addSubview:startCityLabel];
    //终点城市
    endCityLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT-20, 57, 20)];
    endCityLabel.backgroundColor = RGBCOLOR(136, 199, 163);
    endCityLabel.text = endCityStr;
    endCityLabel.font = [UIFont systemFontOfSize:13];
    endCityLabel.textColor = [UIColor whiteColor];
    [app.window addSubview:endCityLabel];
}

-(void)viewWillDisappear:(BOOL)animated{
    [startCityLabel removeFromSuperview];
    [endCityLabel removeFromSuperview];
    [super viewWillDisappear:animated];
}
#pragma mark - custem method


//获取交通事故、施工信息
-(void)loadAccidentAndConstructionInfo
{
    accidentAry = [[NSMutableArray alloc] init];
    constructAry = [[NSMutableArray alloc] init];
    int pageSize=8;
    NSString *queryTime=[NSDate formateDate:[NSDate date] fromate:@"yyyy-MM-dd HH:mm:ss"];
    NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:1],@"type",[NSNumber numberWithInt:pageSize],@"pageSize",queryTime,@"queryTime",roadId,@"road",nil];
    [DataDAO getTrafficinfo:param withCompleted:^(id result) {
        
        //判断显示两天内的交通事故
        accidentAry = [ComFun selectTodayevent:result];
        [_tableView reloadData];
    } withFailure:^(id error){
        
    }];
    
    param=[[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:2],@"type",[NSNumber numberWithInt:pageSize],@"pageSize",queryTime,@"queryTime",roadId,@"road",nil];
    [DataDAO getTrafficinfo:param withCompleted:^(id result) {
        
        //判断显示两天内的道路施工
        constructAry = [ComFun selectTodayevent:result];
        [_tableView reloadData];
    } withFailure:^(id error){
        
    }];
    
    
}

// 获取服务区信息
-(void)loadServiceInfo
{
    NSMutableArray *mutableAry = [[NSMutableArray alloc] init];

    serviceAry = [[[JSONUtil readJSON:@"serviceArea.json"] objectForKey:roadId] objectForKey:@"RESTS"];
    for (NSDictionary* dic  in serviceAry) {
        if ([NSString isEmpty:[dic objectForKey:@"StartID"]]) {
            continue;
        }
        NSMutableDictionary *mutableDic = [[NSMutableDictionary alloc] initWithDictionary:dic copyItems:YES];
        [mutableDic setObject:roadName forKey:@"roadName"];
        [mutableAry addObject:mutableDic];
    }
    serviceAry = mutableAry;
}


//从数据库中加载站信息
- (void)loadStations
{
    //    originalStationArray=[[SqliteUtil sharedSqliteUtil] queryStationByRoadId:roadId];
    originalStationArray = [[JSONUtil readJSON:@"StationByRoadID.json"] objectForKey:roadId];
    //    NSLog(@"==%@",stationArray);
    
    //把stationIndex重复的站去掉
    realStationArray = [[NSMutableArray alloc] init];
    
    NSArray *StationChangeList = [JSONUtil readJSON:@"StationChangeList.json"];
    for (NSInteger i = 0; i<[StationChangeList count]; i++) {
        NSDictionary *RoadInfo = [StationChangeList objectAtIndex:i];
        
        if ([[RoadInfo objectForKey:@"ROADID"] intValue] == [roadId intValue]) {
            compareStationDic  = [[NSDictionary alloc] initWithDictionary:RoadInfo];
            NSLog(@"路ID为%@，有站需要更名",roadId);
            break;
        }else
        {
            continue;
        }
    }
    
    if (compareStationDic) { //需要查表更名
        
        //把在表格中记录的需要改名的收费站改名
        
        NSArray *changeStationList = [compareStationDic objectForKey:@"Stations"];
        
        realStationArray = [[NSMutableArray alloc] initWithArray:originalStationArray copyItems:YES];
        
        for (NSInteger i=0; i<[changeStationList count]; i++) { //得到要修改的全部站名
            NSArray *changeID = [[changeStationList objectAtIndex:i] objectForKey:@"ID"];
            NSString *  newStationName = [[changeStationList objectAtIndex:i] objectForKey:@"name"];
            
            for (NSInteger j=0; j<[changeID count]; j++) { //从表中得到要改名的stationid
                
                for (NSInteger n =0; n<[realStationArray count]; n++) { //由stationid找到对应的记录，修改该站名
                    
                    int listStationID = [[changeID objectAtIndex:j] intValue];
                    int realStationID = [[[realStationArray objectAtIndex:n] objectForKey:@"stationid"] intValue];
                    if (listStationID == realStationID) {
                        
                        NSMutableDictionary *Mdic = [[NSMutableDictionary alloc] initWithDictionary:[realStationArray objectAtIndex:n] copyItems:YES];
                        [Mdic removeObjectForKey:@"stationname"];
                        [Mdic setObject: newStationName forKey:@"stationname"];
                        //用mutableDic替换掉dic，实现改名
                        [realStationArray replaceObjectAtIndex:n withObject:Mdic];
                        break; //拿下一个需要修改的stationid
                    }
                    else
                        continue; //未找到对应的站，继续找
                }
            }
        }
        
        NSMutableArray *dic = [[NSMutableArray alloc] init];
        //改名之后的收费站有重名，去重复的收费站
        for (NSInteger i=0; i<[realStationArray count]; i++) {
            NSDictionary* station = [realStationArray objectAtIndex:i];
            if (i==0) {
                lastStation = station;
                [dic addObject:lastStation];
            }
            else
            {
                if ([[station objectForKey:@"stationname"] isEqualToString:[lastStation objectForKey:@"stationname"]]){
                    
                }
                else
                {
                    [dic addObject:station];
                    lastStation = station;
                }
            }
        }
        realStationArray = dic;
        
    }
    else  //该路的收费站不需要更名
    {
        realStationArray = [[NSMutableArray alloc] initWithArray:originalStationArray];
    }
    
    
    stationCount =(int)[realStationArray count]; //用来决定表视图的行数
    
    // 查询方向城市
    [self queryDirectionCity];
}

// 查询起始站和终点站所在的城市
-(void)queryDirectionCity{
    CLLocationCoordinate2D startCoor,endCoor;
    startCoor.latitude = [[[realStationArray objectAtIndex:0] objectForKey:@"ycode"] floatValue];
    startCoor.longitude = [[[realStationArray objectAtIndex:0] objectForKey:@"xcode"] floatValue];
    endCoor.latitude = [[[realStationArray lastObject] objectForKey:@"ycode"] floatValue];
    endCoor.longitude = [[[realStationArray lastObject] objectForKey:@"xcode"] floatValue];
    
    [MapUtil getCityByCLLocationCoordinate2D:startCoor completedBlock:^(NSString *city) {
        startCityStr =[[NSString alloc] initWithFormat:@" %@方向 ",city];
        startCityLabel.text = startCityStr;
    }];
    
    [MapUtil getCityByCLLocationCoordinate2D:endCoor completedBlock:^(NSString *city) {
        endCityStr =[[NSString alloc] initWithFormat:@"%@方向 ",city];
        endCityLabel.text = endCityStr;
    }];

}

//查询站之间的速度
- (void)loadData
{
    NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:roadId,@"road",nil];
    [DataDAO getRoadSpeed:param withCompleted:^(id result) {
        if(result)
        {
            speedArray = [[result valueForKey:@"speed"] objectAtIndex:0];
            [_tableView reloadData];
        }
    } withFailure:^(id error) {
        [self showHUD:error :FAILEDICON :YES];
    }];
}


// 地图模式展示高速公路
- (IBAction)showMapMode:(id)sender
{
    HighwayInfoAtMapVC *highwayInfoAtMap =[[HighwayInfoAtMapVC alloc] init];
    highwayInfoAtMap.roadId=roadId;
    highwayInfoAtMap.roadName=roadName;
    highwayInfoAtMap.stationArray = realStationArray;
    highwayInfoAtMap.speedArray=speedArray;
    highwayInfoAtMap.accidentArray = accidentAry;
    highwayInfoAtMap.constructArray = constructAry;
    highwayInfoAtMap.serviceArray = serviceAry;
    
    [app.nav pushViewController:highwayInfoAtMap animated:YES];
}



//搜索这两站之间有没有交通事故或者道路施工
-(NSDictionary*)searchAccidentFixIconAry:(NSArray*)searchAry startStationName:(NSString*)startStation endStationName:(NSString*)endStation
{
    for (NSDictionary* Dic in searchAry ) {
        NSString *dicStartName = [[Dic objectForKey:@"startStation"] substringToIndex:2];
        NSString *dicEndName = [[Dic objectForKey:@"endStation"] substringToIndex:2];
        if ([dicStartName isEqualToString:[startStation substringToIndex:2]] || [startStation rangeOfString:dicStartName].location != NSNotFound) {
            if ([dicEndName isEqualToString:[endStation substringToIndex:2]] || [endStation rangeOfString:dicStartName].location != NSNotFound) {
                return Dic;
            }
        }
    }
    return nil;
}

//搜索这两站之间有没有服务区
-(NSDictionary*)searchServiceArea:(NSArray*)searchAry startStationID:(NSString*)startID endStationID:(NSString*)endID
{
    for (NSDictionary* dic in serviceAry) {
        if ([NSString isEmpty:[dic objectForKey:@"StartID"]] || [NSString isEmpty:[dic objectForKey:@"EndID"]]) {
            continue;
        }
        if ([[[dic objectForKey:@"StartID"] objectAtIndex:1] isEqualToString:startID] && [[[dic objectForKey:@"EndID"] objectAtIndex:1] isEqualToString:endID]) {
            return dic;
        }
        else if ([[[dic objectForKey:@"StartID"] objectAtIndex:1] isEqualToString:endID] && [[[dic objectForKey:@"EndID"] objectAtIndex:1] isEqualToString:startID])
        {
            return dic;
        }

    }
    return nil;
}


#pragma  mark- UITableView Delegate

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] init];
    view.backgroundColor = _tableView.backgroundColor;
    return view;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return  20.0f;
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
        return (2*stationCount-1);
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
        static NSString * identifier =@"HighwayDetailStationCell";
        HighwayDetailStationCell *cell =(HighwayDetailStationCell *)[tableView dequeueReusableCellWithIdentifier:identifier];
        if(!cell)
        {
            NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:identifier owner:nil options:nil];
            for(id currentObject in topLevelObjects)
            {
                if([currentObject isKindOfClass:[HighwayDetailStationCell class]])
                {
                    cell = (HighwayDetailStationCell *)currentObject;
                    cell.backgroundColor=[UIColor clearColor];
                    cell.selectionStyle=UITableViewCellSelectionStyleNone;
                    break;
                }
            }
        }
        NSDictionary *station=[realStationArray objectAtIndex:row/2];
        cell.stationLabel.text=[station valueForKeyPath:@"stationname"];
        
        return cell;
    }
    else
    {
        static NSString * identifier =@"HighwayInfoRoadCell";
        HighwayInfoRoadCell *cell =(HighwayInfoRoadCell *)[tableView dequeueReusableCellWithIdentifier:identifier];
        if(!cell)
        {
            NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:identifier owner:nil options:nil];
            for(id currentObject in topLevelObjects)
            {
                if([currentObject isKindOfClass:[HighwayInfoRoadCell class]])
                {
                    cell = (HighwayInfoRoadCell *)currentObject;
                    cell.backgroundColor=[UIColor clearColor];
                    cell.selectionStyle=UITableViewCellSelectionStyleNone;
                    break;
                }
            }
        }
        
        int  index = row/2;
        
        //获得该cell前后的站名
        NSDictionary *station=[realStationArray objectAtIndex:index];
        NSString* startStation = [station valueForKeyPath:@"stationname"];
        NSString* startStationID = [station valueForKey:@"stationid"];
        
        station=[realStationArray objectAtIndex:index+1];
        NSString* endStation = [station valueForKeyPath:@"stationname"];
        NSString* endStationID = [station valueForKey:@"stationid"];
        
        
        NSDictionary *accidentDic;
        NSDictionary *constructDic;
        NSDictionary *serviceDic;
        
        //查看是否有交通事故
        if ((accidentDic = [self searchAccidentFixIconAry:accidentAry startStationName:startStation endStationName:endStation]) != nil) { //顺向
            cell.leftAccidentButton.hidden = NO;
            cell.leftAccidentButton.infoDic = accidentDic;
            [cell.leftAccidentButton addTarget:self action:@selector(accidentFixIconSelect:) forControlEvents:UIControlEventTouchUpInside];
        }else if ((accidentDic = [self searchAccidentFixIconAry:accidentAry startStationName:endStation endStationName:startStation]) != nil){ //逆向
            cell.rightAccidentButton.hidden = NO;
            cell.rightAccidentButton.infoDic = accidentDic;
            [cell.rightAccidentButton addTarget:self action:@selector(accidentFixIconSelect:) forControlEvents:UIControlEventTouchUpInside];
        }
        //查看是否有道路施工
        if ((constructDic = [self searchAccidentFixIconAry:constructAry startStationName:startStation endStationName:endStation]) != nil) {//顺向
            cell.leftFixButton.hidden = NO;
            cell.leftFixButton.infoDic = constructDic;
            [cell.leftFixButton addTarget:self action:@selector(accidentFixIconSelect:) forControlEvents:UIControlEventTouchUpInside];
        }else if ((constructDic = [self searchAccidentFixIconAry:constructAry startStationName:endStation endStationName:startStation]) != nil){//逆向
            cell.rightFixButton.hidden = NO;
            cell.rightFixButton.infoDic = constructDic;
            [cell.rightFixButton addTarget:self action:@selector(accidentFixIconSelect:) forControlEvents:UIControlEventTouchUpInside];
        }
        //查看是否有服务区
        if ((serviceDic = [self searchServiceArea:serviceAry startStationID:startStationID endStationID:endStationID]) != nil) {
            cell.leftServiceButton.hidden = NO;
            cell.rightServiceButton.hidden = NO;
            cell.leftServiceButton.infoDic = serviceDic;
            cell.rightServiceButton.infoDic = serviceDic;
            [cell.leftServiceButton addTarget:self action:@selector(serviceAreaIconSelect:) forControlEvents:UIControlEventTouchUpInside];
            [cell.rightServiceButton addTarget:self action:@selector(serviceAreaIconSelect:) forControlEvents:UIControlEventTouchUpInside];
            
        }
        
        NSDictionary *fristStation = [realStationArray objectAtIndex:index];
        NSDictionary *nextStation = [realStationArray objectAtIndex:index+1];
        NSString *fristStationId = [fristStation valueForKey:@"stationid"];
        NSString *nextStationId = [nextStation valueForKey:@"stationid"];
        
        //左边路
        UIImage *leftImage;
        UIColor *leftKmColor ;
        UIColor *leftSpeedColor;
        int leftSpeed =[ComFun findSpeed:speedArray startStationId:fristStationId endStationId:nextStationId];
        if(SpeedFast(leftSpeed))
        {
            leftImage =[UIImage imageNamed:@"Road-Green-Down"];
            leftKmColor=RGBCOLOR(80.0f, 182.0f, 124.0f);
            leftSpeedColor=RGBCOLOR(80.0f, 182.0f, 124.0f);
        }
        else if(SpeedFast(leftSpeed))
        {
            leftImage = [UIImage imageNamed:@"Road-Yellow-Down"];
            leftKmColor=RGBCOLOR(254.0f, 201.0f, 118.0f);
            leftSpeedColor=RGBCOLOR(254.0f, 201.0f, 118.0f);
        }
        else
        {
            leftImage = [UIImage imageNamed:@"Road-Red-Down"];
            leftKmColor=RGBCOLOR(255.0f, 120.0f, 98.0f);
            leftSpeedColor=RGBCOLOR(255.0f, 120.0f, 98.0f);
        }
        
        cell.leftImageView.image=leftImage;
        cell.leftKmLabel.textColor = leftKmColor;
        cell.leftSpeedLabel.text=[ NSString stringWithFormat:@"%d",leftSpeed];
        cell.leftSpeedLabel.textColor = leftSpeedColor;
        
        
        
        //右边路
        UIImage *rightImage=nil;
        UIColor *rightKmColor=nil ;
        UIColor *rightSpeedColor=nil;
        int rightSpeed = [ComFun findSpeed:speedArray startStationId:nextStationId endStationId:fristStationId];
        if(SpeedFast(rightSpeed))
        {
            rightImage =[UIImage imageNamed:@"Road-Green-Up"];
            rightKmColor=RGBCOLOR(80.0f, 182.0f, 124.0f);
            rightSpeedColor=RGBCOLOR(80.0f, 182.0f, 124.0f);
        }
        else if(SpeedFast(rightSpeed))
        {
            rightImage = [UIImage imageNamed:@"Road-Yellow-Up"];
            rightKmColor=RGBCOLOR(254.0f, 201.0f, 118.0f);
            rightSpeedColor=RGBCOLOR(254.0f, 201.0f, 118.0f);
        }
        else
        {
            rightImage = [UIImage imageNamed:@"Road-Red-Up"];
            rightKmColor=RGBCOLOR(255.0f, 120.0f, 98.0f);
            rightSpeedColor=RGBCOLOR(255.0f, 120.0f, 98.0f);
        }
        
        cell.rightImageView.image=rightImage;
        cell.rightKmLabel.textColor = rightKmColor;
        cell.rightSpeedLabel.text=[ NSString stringWithFormat:@"%d",rightSpeed];
        cell.rightSpeedLabel.textColor = rightSpeedColor;
        
        return cell;
    }
}

//选中交通事故或道路施工Icon图标，跳转视图
-(void)accidentFixIconSelect:(id)sender
{
    FunctionIconButton *btn = (FunctionIconButton*)sender;
    NSDictionary *dic = btn.infoDic;
    NewsInfoVC *newsInfoVC = [[NewsInfoVC alloc] init];
    newsInfoVC.type= [[dic objectForKey:@"type"] intValue];
    newsInfoVC.dataDic=dic;
    [app.nav pushViewController:newsInfoVC animated:YES];
}

//选中服务区Icon图标
-(void)serviceAreaIconSelect:(id)sender
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

@end
