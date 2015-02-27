//
//  QueryDrivingRouteMoreList.m
//  HighWay_iPhone
//
//  Created by litong on 14-9-1.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "QueryDrivingRouteMoreList.h"
#import "QueryDirvingRouteResultVC.h"
#import "AppDelegate.h"

@interface QueryDrivingRouteMoreList ()

@end

@implementation QueryDrivingRouteMoreList

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
    self.title = @"历史搜索记录";
    UIBarButtonItem *clearbutton = [[UIBarButtonItem alloc] initWithTitle:@"清空" style:UIBarButtonItemStylePlain target:self action:@selector(clearHistory)];
    self.navigationItem.rightBarButtonItem = clearbutton;
    
    // Do any additional setup after loading the view from its nib.
}

-(void)viewWillDisappear:(BOOL)animated
{
    //不添加会出现出异常
    [_tableView removeFromSuperview];
}

-(void)viewWillAppear:(BOOL)animated
{
//    [self.navigationItem setHidesBackButton:YES];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - custem method
//清除历史消息
-(void)clearHistory
{
    [_recordAry removeAllObjects];
    [[SqliteUtil sharedSqliteUtil] deleteAllPathRecord];
    [_tableView reloadData];
}

#pragma mark - UITableview Delegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (_recordAry.count==0) {
        if (isDeleteTheLastOne) {
            return 0;
        }
        return 1;
    }else
    {
        return _recordAry.count;
    }
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSString *identifier = @"identifier";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if(!cell)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault  reuseIdentifier: identifier] ;
        cell.textLabel.textAlignment=NSTextAlignmentCenter;
        cell.textLabel.textColor=RGBCOLOR(102.0f,102.0f,102.0f);
    }
    if (_recordAry.count==0) {
        cell.textLabel.text =@"无查询记录";
    }else
    {
        NSDictionary *rowData =[_recordAry objectAtIndex:indexPath.row];
        NSString *startStationName =[rowData valueForKeyPath:@"startStationName"];
        NSString *endStationName = [rowData valueForKeyPath:@"endStationName"];
        cell.textLabel.text =[NSString stringWithFormat:@"%@ - %@",startStationName,endStationName];

    }
    return cell;
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    int row = (int)indexPath.row;
    NSDictionary *rowData =[_recordAry objectAtIndex:row];
    [_delegate changeToDetailVC:rowData];
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    return YES;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        
        int row =(int)indexPath.row;
        NSDictionary *rowData = [_recordAry objectAtIndex:row];
        int _id = [[rowData valueForKeyPath:@"_ID"] intValue];
        [[SqliteUtil sharedSqliteUtil] deletePathRecordById:_id];
        
        [_recordAry removeObjectAtIndex:indexPath.row];
        if (_recordAry.count==0) {
            isDeleteTheLastOne = YES;
        }
        // Delete the row from the data source.
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
        if (isDeleteTheLastOne) {
            isDeleteTheLastOne = NO;
            [tableView reloadData];
        }
        
    }
}
- (NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:(NSIndexPath *)indexPath{
    return @"删除";
}

@end
