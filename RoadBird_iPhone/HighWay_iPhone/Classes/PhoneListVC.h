//
//  PhoneListVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-16.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"

@interface PhoneListVC : BaseVC<UITableViewDelegate,UITableViewDataSource>


@property (nonatomic,strong) NSMutableArray *lstData;
@property (nonatomic,strong) IBOutlet UITableView *tableView;

@end
