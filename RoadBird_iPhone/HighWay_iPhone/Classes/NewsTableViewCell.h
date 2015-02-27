//
//  NewsTableViewCell.h
//  HighWay_iPhone
//
//  Created by litong on 14-9-18.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface NewsTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *mainLabel;
@property (weak, nonatomic) IBOutlet UILabel *subLabel;

-(void)setBoldLabelSize:(CGSize)labelSize withString:(NSString*)string;
@end
