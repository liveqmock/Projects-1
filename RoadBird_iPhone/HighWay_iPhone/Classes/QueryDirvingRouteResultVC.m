//
//  QueryDirvingRouteResultVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/19.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "QueryDirvingRouteResultVC.h"
#import "QueryDirvingRouteResultCell.h"
#import "DirvingRouteDetailVC.h"
#import "AppDelegate.h"

@implementation QueryDirvingRouteResultVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title=@"方案选择";

    UILabel *label = [[UILabel alloc] init];
    label.frame = CGRectMake(0, _textView.frame.size.height-11, SCREEN_WIDTH, 11);
    label.textAlignment = NSTextAlignmentCenter;
    label.textColor = RGBCOLOR(255, 117, 117);
    [label setFont:[UIFont systemFontOfSize:11.0f]];
    label.text = @"以上资费仅供参考，以实际定价为准。";
    [_textView addSubview:label];
    self.tableView.scrollEnabled = NO;
    
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc]
                                             initWithTitle:@"返回"
                                             style:UIBarButtonItemStylePlain
                                             target:self
                                             action:@selector(goBack:)] ;

    
    [self loadData];
}

- (IBAction)goBack:(id)sender{
    for (UIViewController *temp in app.nav.viewControllers) {
        if ([temp isKindOfClass:[MainVC class]]) {
            [app.nav popToViewController:temp animated:YES];
        }
    }
}

- (void) loadData
{
    //1-最短路径，2-最少费用，3-最少耗时。默认type=0，表示查询所有类型
    [self showHUD:@"查询中..."];
    NSString *type =@"0";
    NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:type,@"type",_startStationID,@"startStation",_startRoad,@"startRoad",_endStationID,@"endStation",_endRoad,@"endRoad",nil];
    [DataDAO getRoadPlan:param withCompleted:^(id result) {
        [self hiddenHUD];
        requestIdentifier =[result valueForKey:@"requestIdentifier"];
        planArray = [result valueForKey:@"plan"];
        
        [_tableView reloadData];
        /*
         * 查询成功后，保存查询记录
         */
        [self addQueryPathRecord];
        
    } withFailure:^(id error) {
        [self hiddenHUD];
        //查询失败，显示提示
        UILabel* errorLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, SCREEN_HEIGHT/2-40, SCREEN_WIDTH-20, 50)];
        errorLabel.numberOfLines = 0;
        errorLabel.text = error;
        errorLabel.textAlignment = NSTextAlignmentCenter;
        errorLabel.textColor = RGBCOLOR(102, 102, 102);
        [self.view addSubview:errorLabel];
        _textView.hidden = YES;
        [self showHUD:error :FAILEDICON :YES];
    }];
}

//保存查找记录
- (void)addQueryPathRecord
{
    NSDictionary *pathRecordDic =[[NSDictionary alloc] initWithObjectsAndKeys:_startRoad,@"startRoadID", _startStationID,@"startStationID" ,_startStationName,@"startStationName",_endRoad,@"endRoadID",_endStationID,@"endStationID", _endStationName,@"endStationName",[NSDate date],@"queryTime",nil];
    [[SqliteUtil sharedSqliteUtil] insertQueryPath:pathRecordDic];
}


#pragma  mark- UITableView Delegate


- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return  5.0f;
}
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{

    return 10.0f;
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
//    return  [planArray count];
    return 2; //目前返回最短路径，最少收费方案
}

//Return how many rows in the table
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 110.0f;
}

// Return a cell for the ith row
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSInteger section = indexPath.section;
    static NSString *cellIdentifier = @"QueryDirvingRouteResultCell";
    QueryDirvingRouteResultCell *cell  =(QueryDirvingRouteResultCell *) [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if(!cell)
    {
        
        NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:cellIdentifier owner:nil options:nil];
        for(id currentObject in topLevelObjects)
        {
            if([currentObject isKindOfClass:[QueryDirvingRouteResultCell class]])
            {
                cell = (QueryDirvingRouteResultCell *)currentObject;
                CGRect frame1 =cell.lineView1.frame;
                cell.lineView1.frame=CGRectMake(frame1.origin.x, frame1.origin.y, frame1.size.width, 0.3f);
                CGRect frame2 =cell.lineView2.frame;
                cell.lineView2.frame=CGRectMake(frame2.origin.x, frame2.origin.y, 0.3f, frame2.size.height);
                CGRect frame3 =cell.lineView3.frame;
               cell.lineView3.frame=CGRectMake(frame3.origin.x, frame3.origin.y, 0.3f, frame3.size.height);
                break;
            }
        }
    }
    NSDictionary *rowData = [planArray objectAtIndex:section];
    int type = [[rowData valueForKeyPath:@"type"] intValue];
    NSString *totalFee=[rowData valueForKeyPath:@"totalFee"];
    NSString *totalDistance =[rowData valueForKeyPath:@"totalDistance"];
    NSString *totalTime =[rowData valueForKeyPath:@"totalTime"];
    
    if(type == 1)
    {
        cell.titleLabel.text=@"最短距离";
        [cell.titleLabel setFont:[UIFont fontWithName:@"Helvetica" size:18.0]];

    }
    else if(type ==2)
    {
        cell.titleLabel.text=@"最少收费";
        [cell.titleLabel setFont:[UIFont fontWithName:@"Helvetica" size:18.0]];
    }
    else
    {
        cell.titleLabel.text =@"最短时间";
        [cell.titleLabel setFont:[UIFont fontWithName:@"Helvetica" size:18.0]];

    }
    cell.moneyLabel.text=[NSString stringWithFormat:@"%@元",totalFee];
    cell.timeLabel.text=[NSString stringWithFormat:@"%@分钟",totalTime];
    cell.kilomertreLabel.text =[NSString stringWithFormat:@"%@公里",totalDistance];
    cell.infoBtn.tag =section;
    [cell.infoBtn addTarget:self action:@selector(btnClicked:) forControlEvents:UIControlEventTouchUpInside];
  
    
    return cell;
    
}

//选中了某一行
- (IBAction)btnClicked:(id)sender
{
    UIButton *btn =sender;
    int tag = (int)btn.tag;
    if(tag>=[planArray count])
        return;
    NSDictionary *dic =[planArray objectAtIndex:tag];
    [self showDetail:dic];
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)newIndexPath
{
    int section =(int) newIndexPath.section;
    if(section>=[planArray count])
        return;
    NSDictionary *dic =[planArray objectAtIndex:section];
    [self showDetail:dic];
       // 取消选中效果
    [tableView deselectRowAtIndexPath:newIndexPath animated:NO];
}


// 显示详情
- (void)showDetail:(NSDictionary *)planDic
{
    DirvingRouteDetailVC *dirvingRouteDetailVC = [[DirvingRouteDetailVC alloc] init];
    dirvingRouteDetailVC.requestIdentifier=requestIdentifier;
    dirvingRouteDetailVC.planDic=planDic;
    [app.nav pushViewController:dirvingRouteDetailVC animated:YES];
}

@end
