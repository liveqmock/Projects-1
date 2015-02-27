//
//  AwardCircleView.h
//  HighWay_iPhone
//
//  Created by litong on 15-1-7.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

#define PI 3.1415926

@interface AwardCircleView : UIView

+(AwardCircleView*)getAwardCircleViewWithString:(NSString*)text withScroe:(int)scroe;

@end
