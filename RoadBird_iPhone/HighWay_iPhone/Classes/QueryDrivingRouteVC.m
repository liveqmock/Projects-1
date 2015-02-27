//
//  QueryDrivingRouteVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-7.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "QueryDrivingRouteVC.h"
#import "AppDelegate.h"
#import "QueryDirvingRouteResultVC.h"



@implementation QueryDrivingRouteVC


- (void)viewDidLoad
{
    [super viewDidLoad];
    isFillEndStation = NO;
    isFillStartStation = NO;

    [self initTableHeadView];
    [self loadPathRecordData];
    
    //天气View阴影效果
    _weatherImageView.layer.shadowColor = [UIColor blackColor].CGColor;
    _weatherImageView.layer.shadowOffset = CGSizeMake(0.8 , 0.8);
    _weatherImageView.layer.shadowOpacity = 1;

}


-(void)viewWillAppear:(BOOL)animated {
    [self loadWeatherData];
    [self loadPathRecordData];
    //初始化引导页
    if ([[SqliteUtil sharedSqliteUtil] queryIndexInfo:@"JourneyPlanning-Index"]) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(IndexViewGone) name:@"IndexViewGone" object:nil];
        [self initIndexView:@"JourneyPlanning-Index"];
    }
    
    //若有数据，填写起始站终点站的数据
    if (self.startStationDic) {
        _startTextField.text = [self.startStationDic objectForKey:@"stationname"];
        isFillStartStation = YES;
    }
    if (self.endStationDic){
        _endTextField.text = [self.endStationDic objectForKey:@"stationname"];
        isFillEndStation = YES;
    }

}

-(void)viewWillDisappear:(BOOL)animated
{
    //当两个都被填入，且页面消失的时候清空，待下次填入新的站名
    if (isFillEndStation && isFillStartStation) {
        _startTextField.text = @"";
        _endTextField.text = @"";
        self.startStationDic = nil;
        self.endStationDic = nil;
        isFillStartStation = NO;
        isFillEndStation = NO;
    }
}


#pragma mark - custom method
//初始化表头
-(void) initTableHeadView
{
    _tableHeaderView.frame = CGRectMake(25, 180, SCREEN_WIDTH-50, 132);
    
    self.startTextField.textColor = RGBCOLOR(102.0f,102.0f,102.0f);
    self.endTextField.textColor = RGBCOLOR(102.0f,102.0f,102.0f);
    
    _tableHeaderView.layer.borderColor = [RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    _tableHeaderView.layer.borderWidth= 0.5;
    UIView *view1 = [[UIView alloc] initWithFrame:CGRectMake(0, 44, _tableHeaderView.frame.size.width, 0.5)];
    view1.backgroundColor = RGBCOLOR(229.0f,229.0f,229.0f);
    UIView *view2 = [[UIView alloc] initWithFrame:CGRectMake(0, 88, _tableHeaderView.frame.size.width, 0.5)];
    view2.backgroundColor = RGBCOLOR(229.0f,229.0f,229.0f);
    
    self.queryBtn.layer.borderWidth=1;
    self.queryBtn.layer.borderColor=[NAV_GREEN_FONT_COLOR CGColor];
    [self.queryBtn setBackgroundColor:RGBCOLOR(211.0f, 235.0f, 224.0f)];
    
    [_tableHeaderView addSubview:view1];
    [_tableHeaderView addSubview:view2];
    
    
    [self.view addSubview:_tableHeaderView];
    
    self.tableView.layer.borderWidth=0.5;
    self.tableView.layer.borderColor = [RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
}

//引导页消失
-(void)IndexViewGone
{
    [self selStation:_startTextField]; //选中起始站
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"IndexViewGone" object:nil];
    [[SqliteUtil sharedSqliteUtil] updateInfo:@"JourneyPlanning-Index" state:@"hide"];

}

- (void)loadWeatherData
{
    ChinaWeatherResult *chinaWeatherResult=  [CommonData sharedCommonData].chinaWeatherResult;
    if(chinaWeatherResult){
        //显示天气
        [CommonData sharedCommonData].isLocated = YES;
        [self initWeatherView:chinaWeatherResult];
    }
    else
    {
        ChinaWeatherUtil *chinaWeatherUtil=[[ChinaWeatherUtil alloc] init];
        [chinaWeatherUtil getWeatherData:[CommonData sharedCommonData].currentCity success:^(ChinaWeatherResult *result) {
            [CommonData sharedCommonData].isLocated = YES;
            [self initWeatherView:chinaWeatherResult];
        } failure:^(NSError *error) {
            
        }];
    }
 
}

-(void)initWeatherView:(ChinaWeatherResult *)result
{
    NSString *tempratureDay=result.temperatureDay;
    if([NSString isNotEmpty:tempratureDay]){
        int DayTemprature = [[result.temperatureDay  stringByReplacingOccurrencesOfString:@"℃" withString:@""] intValue];
        int NightTemprature = [[result.temperatureNight  stringByReplacingOccurrencesOfString:@"℃" withString:@""] intValue];
        
        int littleTemprature = DayTemprature < NightTemprature ? DayTemprature : NightTemprature;
        int biggerTemprature = DayTemprature > NightTemprature ? DayTemprature : NightTemprature;
        
        self.temperatureLabel.text=[NSString stringWithFormat:@"%d - %d℃",littleTemprature,biggerTemprature];
        NSString *currDate=[NSDate formateDate:[NSDate date] fromate:@"YYYY年MM月dd日"];
        self.weatherDetialLabel.text=[[NSString alloc] initWithFormat:@"%@  %@",currDate,result.weatherInfo];
        
        //是否已经定位了城市
        if ([CommonData sharedCommonData].isLocated) {
            self.cityLabel.text=result.cityName;
        }else
        {
            NSString *cityName = [[NSString alloc] initWithFormat:@"%@ (默认)",result.cityName];
            self.cityLabel.text=cityName;
        }
        
        NSString *imageName=[ChinaWeatherUtil getWeatherImage:result.weatherInfo];
        self.weatherImageView.image=[UIImage imageNamed:imageName];
        
        //设置字体阴影
        self.temperatureLabel.shadowOffset = CGSizeMake(0.0f, 2.0f);
        self.temperatureLabel.shadowColor = [UIColor colorWithWhite:0.0f alpha:0.75f];
        self.temperatureLabel.shadowBlur = 5.0f;
        
        self.weatherDetialLabel.shadowOffset = CGSizeMake(0.0f, 1.0f);
        self.weatherDetialLabel.shadowColor = [UIColor colorWithWhite:0.0f alpha:0.5f];
        self.weatherDetialLabel.shadowBlur = 1.5f;
        
        self.cityLabel.shadowOffset = CGSizeMake(0.0f, 1.0f);
        self.cityLabel.shadowColor = [UIColor colorWithWhite:0.0f alpha:0.5f];
        self.cityLabel.shadowBlur = 1.5f;
    }
}

- (void) loadPathRecordData
{
    pathRecords = [[NSMutableArray alloc] initWithArray:[[SqliteUtil sharedSqliteUtil] queryAllPathRecords]];
    [_tableView reloadData];
}

// 选择起始站或终点站
- (IBAction)selStation:(id)sender
{
    if(sender==_startTextField)
        selStart=YES;
    else
        selStart=NO;
    SelStationVC *selStationVC =[[SelStationVC alloc] init];
    selStationVC.delegate =self;
    selStationVC.isStart = selStart;
    [app.nav pushViewController:selStationVC animated:YES];
}

// 手动更新天气
- (IBAction)refreshWeather:(id)sender {
    
    [self showHUD:@"刷新天气中..."];
    if (![CommonData sharedCommonData].isOpenGPS) { //没有开启GPS直接返回
        [self hiddenHUD];
        return;
    }
    
     [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(WaitForCityRefresh) name:@"WaitForCityRefresh" object:nil];

    _delegate = [CommonData sharedCommonData].realTimeTrafficVC;
    
     //重新请求当前城市，由于调用百度地图API，请求会有延迟
    [_delegate QueryWeatherCity];
  
}

// 等待更新天气的当前城市返回
- (void)WaitForCityRefresh
{
    ChinaWeatherUtil *chinaWeatherUtil=[[ChinaWeatherUtil alloc] init];
    [chinaWeatherUtil getWeatherData:[CommonData sharedCommonData].currentCity success:^(ChinaWeatherResult *result) {
        [self initWeatherView:result]; //刷新天气
    } failure:^(NSError *error) {
        
    }];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"WaitForCityRefresh" object:nil];
    [self performSelector:@selector(delayMethod) withObject:nil afterDelay:0.7f];
}

// 行程规划
- (IBAction)doQueryDriving:(id)sender
{
    if(!self.startStationDic || !self.endStationDic)
    {
        UIAlertView* alert = [[UIAlertView alloc] initWithTitle:@"温馨提示" message:@"请输入起始站或终点站！" delegate:self cancelButtonTitle:@"确定" otherButtonTitles: nil];
        [alert show];
        return;
    }
    
    [self queryDriving:[self.startStationDic valueForKey:@"roadid"] :[self.startStationDic valueForKey:@"stationid"] :[self.startStationDic valueForKey:@"stationname"] :[self.endStationDic valueForKey:@"roadid"] :[self.endStationDic valueForKey:@"stationid"] :[self.endStationDic valueForKey:@"stationname"]];
}

- (void)queryDriving:(NSString *)startRoadId:(NSString *)startStationId:(NSString *) startStationName:(NSString *)endRoadId:(NSString *)endStationId:(NSString *)endStationName
{
    QueryDirvingRouteResultVC *resultVC = [[QueryDirvingRouteResultVC alloc] init];
    resultVC.startRoad=startRoadId;
    resultVC.startStationID=startStationId;
    resultVC.startStationName=startStationName;
    
    resultVC.endRoad=endRoadId;
    resultVC.endStationID=endStationId;
    resultVC.endStationName=endStationName;
    
    self.startStationDic = nil;
    self.endStationDic = nil;
    
    [app.nav pushViewController:resultVC animated:YES];
}

-(void)delayMethod
{
    [self hiddenHUD];
}

#pragma UITextField Delegation
- (void)textFieldDidBeginEditing:(UITextField *)textField;
{
    [textField resignFirstResponder];
    [self selStation:textField];
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [textField resignFirstResponder];
    return YES;
}

#pragma mark - UITableView Delegate



//Return how many rows in the table
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    int count =(int)[pathRecords count];

    if(count>0 && count<5)
        return  count+1;
    else if(count>0 && count>=5)
    {
        return 5;
    }

    else
        return 1;
}

// Return a cell for the ith row
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
   
    int row = (int)indexPath.row;
    static NSString *cellIdentifier = @"cell";
    UITableViewCell *cell  = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if(!cell)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault  reuseIdentifier: cellIdentifier] ;
    }
        cell.textLabel.textAlignment=NSTextAlignmentCenter;
        cell.textLabel.textColor=RGBCOLOR(102.0f,102.0f,102.0f);
    
    
    int count =(int)[pathRecords count];
    if(count>0)
    {
        if (row<4) {
            if (row == pathRecords.count) {
                cell.textLabel.text=@"显示更多";
                return cell;
            }
            NSDictionary *rowData =[pathRecords objectAtIndex:row];
            NSString *startStationName =[rowData valueForKeyPath:@"startStationName"];
            NSString *endStationName = [rowData valueForKeyPath:@"endStationName"];
            cell.textLabel.text =[NSString stringWithFormat:@"%@ - %@",startStationName,endStationName];
        }
        else
             cell.textLabel.text=@"显示更多";
    }
    else
    {
        cell.textLabel.text=@"无查询记录";
    }
  
    
    return cell;
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)newIndexPath
{
    
    if([pathRecords count]>0){
        if (([pathRecords count]>=5 && newIndexPath.row==4) || ([pathRecords count]<5 && newIndexPath.row==pathRecords.count)) { //点击了更多
            
            QueryDrivingRouteMoreList *moreListVC = [[QueryDrivingRouteMoreList alloc] init];
            moreListVC.recordAry = pathRecords;
            moreListVC.delegate = self;
            [app.nav pushViewController:moreListVC animated:YES];
            
        }else
        {
            int row = (int)newIndexPath.row;
            NSDictionary *rowData =[pathRecords objectAtIndex:row];
            [self queryDriving:[rowData valueForKeyPath:@"startRoadID"] :[rowData valueForKeyPath:@"startStationID"] :[rowData valueForKeyPath:@"startStationName"] :[rowData valueForKeyPath:@"endRoadID"] :[rowData valueForKeyPath:@"endStationID"] :[rowData valueForKeyPath:@"endStationName"]];
        }
        
    }
    // 取消选中效果
    [tableView deselectRowAtIndexPath:newIndexPath animated:NO];
}


- (NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:(NSIndexPath *)indexPath{
    return @"删除";
}


#pragma SelStationDelegate
- (void)selStationCallBack:(NSDictionary *)stationDic
{
    if(selStart)
    {
        self.startStationDic=stationDic;
    }
    else
    {
        self.endStationDic=stationDic;
    }
}

#pragma QueryDrivingRouteMoreListDelegate 
//从历史记录跳转到查询页面
-(void)changeToDetailVC:(NSDictionary *)dic
{
    [self queryDriving:[dic valueForKeyPath:@"startRoadID"] :[dic valueForKeyPath:@"startStationID"] :[dic valueForKeyPath:@"startStationName"] :[dic valueForKeyPath:@"endRoadID"] :[dic valueForKeyPath:@"endStationID"] :[dic valueForKeyPath:@"endStationName"]];
    [_tableView reloadData];
}
@end
