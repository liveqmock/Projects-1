//
//  HighwayInfoRoadCell.h
//  HighWay_iPhone
//
//  Created by wanggp on 14/7/3.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"
#import "FunctionIconButton.h"

@interface HighwayInfoRoadCell : UITableViewCell


@property (nonatomic,strong) IBOutlet UIImageView *leftImageView;
@property (nonatomic,strong) IBOutlet UILabel *leftSpeedLabel;
@property (nonatomic,strong) IBOutlet UILabel *leftKmLabel;


@property (nonatomic,strong) IBOutlet UIImageView *rightImageView;
@property (nonatomic,strong) IBOutlet UILabel *rightSpeedLabel;
@property (nonatomic,strong) IBOutlet UILabel *rightKmLabel;

@property (weak, nonatomic) IBOutlet FunctionIconButton *leftAccidentButton;
@property (weak, nonatomic) IBOutlet FunctionIconButton *leftFixButton;
@property (weak, nonatomic) IBOutlet FunctionIconButton *leftServiceButton;
@property (weak, nonatomic) IBOutlet FunctionIconButton *rightAccidentButton;
@property (weak, nonatomic) IBOutlet FunctionIconButton *rightFixButton;
@property (weak, nonatomic) IBOutlet FunctionIconButton *rightServiceButton;





@end
