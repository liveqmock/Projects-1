//
//  HighwayListVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-27.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"

@interface HighwayListVC : BaseVC<UITableViewDelegate,UITableViewDataSource,UISearchBarDelegate,UISearchDisplayDelegate>
{
    BOOL isSearching;
    NSArray *roadArray;
    NSMutableArray *searchRoadArray;
}

@property (nonatomic,strong) IBOutlet UITableView *tableView;
@property (nonatomic,strong) IBOutlet UISearchBar *searchBar;

@end
