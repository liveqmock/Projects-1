//
//  HighwayInfoVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14/7/1.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"

@interface HighwayInfoVC : BaseVC<UITableViewDelegate,UITableViewDataSource>
{
    NSString *roadId;
    NSString *roadName;
    int stationCount;
    NSArray *originalStationArray;
    NSArray *speedArray;
    NSMutableArray *realStationArray;
    
    NSDictionary *lastStation;
    
    //找到要更改的字典
    NSDictionary *compareStationDic;
    
    NSMutableArray *accidentAry;
    NSMutableArray *constructAry;
    NSMutableArray *serviceAry;
    
    // 方向label
    UILabel *startCityLabel,*endCityLabel;
    NSString *startCityStr,*endCityStr;
}

@property (nonatomic,strong) NSDictionary *roadDic;

@property (nonatomic,strong) IBOutlet UITableView *tableView;

@end
