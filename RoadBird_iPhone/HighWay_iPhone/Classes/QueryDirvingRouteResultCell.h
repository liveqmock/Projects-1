//
//  QueryDirvingRouteResultCell.h
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/19.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface QueryDirvingRouteResultCell : UITableViewCell

@property (nonatomic,strong) IBOutlet UIView *lineView1,*lineView2,*lineView3;

@property (nonatomic,strong) IBOutlet UILabel *titleLabel;
@property (nonatomic,strong) IBOutlet UILabel *moneyLabel;
@property (nonatomic,strong) IBOutlet UILabel *timeLabel;
@property (nonatomic,strong) IBOutlet UILabel *kilomertreLabel;

@property (nonatomic,strong) IBOutlet UIButton *infoBtn;

@end
