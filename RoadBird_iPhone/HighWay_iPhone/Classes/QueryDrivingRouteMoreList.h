//
//  QueryDrivingRouteMoreList.h
//  HighWay_iPhone
//
//  Created by litong on 14-9-1.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"
@protocol QueryDrivingRouteMoreListDelegate <NSObject>
-(void)changeToDetailVC:(NSDictionary*)dic;
@end

@interface QueryDrivingRouteMoreList : BaseVC<UITableViewDataSource,UITableViewDelegate>
{
    BOOL isDeleteTheLastOne;
}

@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (nonatomic,strong) NSMutableArray* recordAry;
@property (nonatomic,weak) id<QueryDrivingRouteMoreListDelegate> delegate;
@end
