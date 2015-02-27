//
//  UserLoginTableViewCell.m
//  HighWay_iPhone
//
//  Created by litong on 15-1-8.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import "UserLoginTableViewCell.h"

@implementation UserLoginTableViewCell

- (void)awakeFromNib {
    // Initialization code
    [self.headBgView.layer setCornerRadius:41];
    self.userHeadLogo.layer.masksToBounds = YES;
    
    [self.userHeadLogo.layer setCornerRadius:39];
    self.userHeadLogo.layer.masksToBounds = YES;
    
    [self.scoreLabel.layer setCornerRadius:6];
    self.scoreLabel.layer.masksToBounds = YES;
    

    
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
