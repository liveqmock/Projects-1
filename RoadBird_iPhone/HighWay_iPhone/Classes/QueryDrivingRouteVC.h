//
//  QueryDrivingRouteVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-7.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BaseVC.h"
#import "SelStationVC.h"
#import "FXLabel.h"
#import "QueryDrivingRouteMoreList.h"
@protocol QueryWeatherCityDelegate <NSObject>

-(void) QueryWeatherCity;

@end

@interface QueryDrivingRouteVC : BaseVC<UITextFieldDelegate,UITableViewDataSource,UITableViewDelegate,SelStationDelegate,QueryDrivingRouteMoreListDelegate>

{
    BOOL selStart;
    NSMutableArray *pathRecords;
    BOOL isFillStartStation;
    BOOL isFillEndStation;
    BOOL isDeleteRow;
    
}

@property (nonatomic,strong) NSDictionary* startStationDic,*endStationDic;
@property (nonatomic,strong) IBOutlet UIImageView *weatherImageView;
@property (nonatomic,strong) IBOutlet FXLabel *temperatureLabel;
@property (nonatomic,strong) IBOutlet FXLabel *weatherDetialLabel;
@property (nonatomic,strong) IBOutlet FXLabel *cityLabel;
@property (nonatomic,strong) IBOutlet UITableView *tableView;

@property (nonatomic,strong) IBOutlet UIView *tableHeaderView;
@property (nonatomic,strong) IBOutlet UITextField *startTextField,*endTextField;
@property (nonatomic,strong) IBOutlet UIButton *queryBtn;
@property (nonatomic,weak) id<QueryWeatherCityDelegate>delegate;


// 选择起始站或终点站
- (IBAction)selStation:(id)sender;

- (IBAction)refreshWeather:(id)sender;


@end
