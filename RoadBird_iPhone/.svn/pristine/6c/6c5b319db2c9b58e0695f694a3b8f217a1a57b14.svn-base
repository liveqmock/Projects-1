//
//  AwardCircleView.m
//  HighWay_iPhone
//
//  Created by litong on 15-1-7.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import "AwardCircleView.h"

@implementation AwardCircleView


// 自定义初始方法
-(id)initWithFrame:(CGRect)frame{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = NAV_GREEN_FONT_COLOR;
        [self.layer setCornerRadius:CGRectGetHeight([self bounds])/2];
        self.layer.masksToBounds = NO; //不隐藏边框

        self.layer.shadowColor = [UIColor blackColor].CGColor;
        self.layer.shadowOffset = CGSizeMake(0 , 0);
        self.layer.shadowOpacity = 0.8;
        self.layer.shadowRadius = 1;
        
    }
    return self;
}

+(AwardCircleView*)getAwardCircleViewWithString:(NSString*)text withScroe:(int)scroe{
    
    AwardCircleView* circleView = [[[self class] alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-64, (SCREEN_HEIGHT-44)/2-64, 128, 128)];
    if (scroe!=0) { //显示两行
        UILabel* mainLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 25, circleView.bounds.size.width, circleView.bounds.size.height/2)];
        mainLabel.textAlignment = NSTextAlignmentCenter;
        mainLabel.textColor = [UIColor whiteColor];
        mainLabel.text = text;
        
        UILabel* subLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, circleView.bounds.size.height/2-15, circleView.bounds.size.width, circleView.bounds.size.height/2)];
        subLabel.textAlignment = NSTextAlignmentCenter;
        subLabel.text = [[NSString alloc] initWithFormat:@"%d分",scroe];
        subLabel.textColor = [UIColor whiteColor];
        
        [circleView addSubview:mainLabel];
        [circleView addSubview:subLabel];
    }else{ //显示一行
    
        UILabel* mainLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, circleView.bounds.size.width, circleView.bounds.size.height)];
        mainLabel.textAlignment = NSTextAlignmentCenter;
        mainLabel.text = text;
        mainLabel.textColor = [UIColor whiteColor];
        
        [circleView addSubview:mainLabel];
        
    }
    
    return circleView;
}


@end
