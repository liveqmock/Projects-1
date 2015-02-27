//
//  NewsTableViewCell.m
//  HighWay_iPhone
//
//  Created by litong on 14-9-18.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "NewsTableViewCell.h"

@implementation NewsTableViewCell

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

//设置粗体字的位置
-(void)setBoldLabelSize:(CGSize)labelSize withString:(NSString*)string
{
    UILabel* label = [[UILabel alloc] init];
    [label setFrame:CGRectMake(20, 7, labelSize.width, 28)];
    label.backgroundColor = [UIColor whiteColor];
    label.text = string;
    label.font = [UIFont boldSystemFontOfSize:17];
    label.textColor = NAV_GREEN_FONT_COLOR;
    //去除不知名的灰色线
    UIView* view = [[UIView alloc] initWithFrame:CGRectMake(20+labelSize.width-1, 7, 2, 28)];
    view.backgroundColor = [UIColor whiteColor];
    [self.contentView addSubview:label];
    [self.contentView addSubview:view];
}

@end
