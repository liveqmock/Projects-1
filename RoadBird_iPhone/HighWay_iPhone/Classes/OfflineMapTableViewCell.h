//
//  OfflineMapTableViewCell.h
//  HighWay_iPhone
//
//  Created by wanggp on 14/7/7.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"

@interface OfflineMapTableViewCell : UITableViewCell

@property (nonatomic,strong) IBOutlet UILabel *cityNameLabel;
@property (nonatomic,strong) IBOutlet UILabel *sizeLabel1,*sizeLabel2;
@property (nonatomic,strong) IBOutlet UIProgressView *progressView;
@property (nonatomic,strong) IBOutlet UIButton *downloadBtn;

@end
