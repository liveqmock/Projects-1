//
//  ReportVC.h
//  HighWay_iPhone
//
//  Created by litong on 14-12-15.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AwardCircleView.h"

@interface ReportVC : BaseVC<UIAlertViewDelegate>{
    UIView* bgView;
    AwardCircleView* awardView;
    BOOL isClickReport;
}

@property (nonatomic,weak) IBOutlet UIView *mainView;
@property (nonatomic,weak) UIView *realTimeView;

- (IBAction)trafficJam:(id)sender; //堵车
- (IBAction)accident:(id)sender; //事故
- (IBAction)fix:(id)sender; //施工
- (IBAction)control:(id)sender; //管制
- (IBAction)water:(id)sender; //积水
- (IBAction)landslides:(id)sender; //塌方

@end
