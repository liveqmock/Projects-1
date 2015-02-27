//
//  MainVC.h
//  HighWay_iPhone
//  主界面
//  Created by wanggp on 14-5-6.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BaseVC.h"
#import "QueryDrivingRouteVC.h"
#import "RealTimeTrafficVC.h"
#import "MyVC.h"
#import "ReportVC.h"


@interface MainVC : BaseVC<UITabBarControllerDelegate>
{
    //自定义tabBar
    NSMutableArray *imageViewArray,*imageArray,*txtArray,*unSelectImageArray,*selectedImageArray;
    NSArray *viewControllersAry;
    UIButton *lastButton;
}

@property (nonatomic,strong) IBOutlet UITabBarController *tabBarController;
@property (nonatomic,strong) IBOutlet QueryDrivingRouteVC *queryDrivingRouteVC;
@property (nonatomic,strong) IBOutlet RealTimeTrafficVC *realTimeTrafficVC;
@property (nonatomic,strong) IBOutlet MyVC *moreVC;
@property (nonatomic,strong) IBOutlet ReportVC *reportVC;







@end
