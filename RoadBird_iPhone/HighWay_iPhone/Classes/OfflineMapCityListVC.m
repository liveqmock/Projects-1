//
//  OfflineMapCityListVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/7/4.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "OfflineMapCityListVC.h"
#import "OfflineMapTableViewCell.h"

@implementation OfflineMapCityListVC


- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title=@"下载离线地图";
    [self loadCity];
}

-(void)loadCity
{
    BMKOfflineMap *offlineMap = [[BMKOfflineMap alloc] init];
    NSArray* records = [offlineMap searchCity:@"广东省"];
    BMKOLSearchRecord* oneRecord = [records objectAtIndex:0];
    cityArray =oneRecord.childCities;
    [self.tableView reloadData];
   
}

#pragma mark- UITableView Delegate


//Return how many rows in the table
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    int count =0;
    if(cityArray)
        count=(int)[cityArray count];
    return  count;
}

// Return a cell for the ith row
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    int row = (int)indexPath.row;
    static NSString *cellIdentifier = @"OfflineMapTableViewCell";
    OfflineMapTableViewCell *cell  = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if(!cell)
    {
        if(!cell)
        {
            NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:cellIdentifier owner:nil options:nil];
            for(id currentObject in topLevelObjects)
            {
                if([currentObject isKindOfClass:[OfflineMapTableViewCell class]])
                {
                    cell = (OfflineMapTableViewCell *)currentObject;
                    cell.backgroundColor=[UIColor clearColor];
                    cell.selectionStyle=UITableViewCellSelectionStyleNone;
                    break;
                }
            }
        }

        
    }
    BMKOLSearchRecord *record =[cityArray objectAtIndex:row];
    cell.cityNameLabel.text=record.cityName;
    cell.sizeLabel1.text=[NSString stringWithFormat:@"%.1fM",record.size/(1024*1024.0f)];
    cell.progressView.hidden=YES;
    cell.downloadBtn.tag=row;
    cell.downloadBtn.layer.borderWidth=0.8f;
    cell.downloadBtn.layer.borderColor=[NAV_GREEN_FONT_COLOR CGColor];
    cell.downloadBtn.layer.cornerRadius=4;
    [cell.downloadBtn addTarget:self action:@selector(downloadMap:) forControlEvents:UIControlEventTouchDown];
    
    return cell;
}



- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)newIndexPath
{
  
    // 取消选中效果
    [tableView deselectRowAtIndexPath:newIndexPath animated:NO];
}


-(IBAction)downloadMap:(id)sender
{
    UIButton *btn =sender;
    NSInteger tag =btn.tag;
    BMKOLSearchRecord *record =[cityArray objectAtIndex:tag];
    BMKOfflineMap *_offlineMap = [[BMKOfflineMap alloc] init];
    _offlineMap.delegate = self;
    [_offlineMap start:record.cityID];
}


#pragma mark -baidumap

/**
 *返回通知结果
 *@param type 事件类型： TYPE_OFFLINE_UPDATE,TYPE_OFFLINE_ZIPCNT,TYPE_OFFLINE_UNZIP, TYPE_OFFLINE_ERRZIP, TYPE_VER_UPDATE, TYPE_OFFLINE_UNZIPFINISH, TYPE_OFFLINE_ADD
 *@param state 事件状态，当type为TYPE_OFFLINE_UPDATE时，表示正在下载或更新城市id为state的离线包，当type为TYPE_OFFLINE_ZIPCNT时，表示检测到state个离线压缩包，当type为TYPE_OFFLINE_ADD时，表示新安装的离线地图数目，当type为TYPE_OFFLINE_UNZIP时，表示正在解压第state个离线包，当type为TYPE_OFFLINE_ERRZIP时，表示有state个错误包，当type为TYPE_VER_UPDATE时，表示id为state的城市离线包有更新，当type为TYPE_OFFLINE_UNZIPFINISH时，表示扫瞄完成，成功导入state个离线包
 */
- (void)onGetOfflineMapState:(int)type withState:(int)state
{
    NSLog(@"====%d===%d",type,state);
}

@end
