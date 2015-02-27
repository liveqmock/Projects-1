//
//  UserLoginTableViewCell.h
//  HighWay_iPhone
//
//  Created by litong on 15-1-8.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UserLoginTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *nameLabel;
@property (weak, nonatomic) IBOutlet UILabel *subLabel;
@property (weak, nonatomic) IBOutlet UIImageView *userHeadLogo;
@property (weak, nonatomic) IBOutlet UIView *headBgView;

@property (weak, nonatomic) IBOutlet UIImageView *diamondImage;
@property (weak, nonatomic) IBOutlet UILabel *jifenLabel;
@property (weak, nonatomic) IBOutlet UILabel *scoreLabel;


@end
