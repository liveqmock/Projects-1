//
//  SelStationVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-30.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"
#import "SelStationByMapVC.h"

@protocol SelStationDelegate <NSObject>

-(void) selStationCallBack:(NSDictionary *) stationDic;

@end

@interface SelStationVC : BaseVC<UITableViewDelegate,UITableViewDataSource,UISearchBarDelegate,SelStationByMapDelegate>
{
    BOOL isSearching;
    BOOL isMore; //城市列表是否全部打开
    UISearchBar *searchBar;
    NSString *selCityName;
    NSDictionary *stationDic;
    SelStationByMapVC *selStationByMapVC;
    NSMutableArray *searchStationArray;
    BOOL isFirstInit;
    
    
}

@property (nonatomic,strong) NSArray *cityArray;
@property (nonatomic,strong) UIView *cityView;
@property (nonatomic,strong) IBOutlet UIView *stationView;
@property (nonatomic,strong) UIButton *lastButton;
@property (nonatomic,strong) NSString *lastButtonText;
@property (nonatomic) BOOL isStart; //判断这次是要选择起始站，还是终点站

@property (nonatomic,strong) IBOutlet UITableView *stationTableView;

@property (nonatomic,weak) id<SelStationDelegate> delegate;


@end
