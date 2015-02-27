//
//  MainVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-6.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "MainVC.h"

@interface MainVC ()

@end

@implementation MainVC


- (id)init
{
    self = [super init];
    if(self){
        }
    return  self;
}

-(void)viewWillAppear:(BOOL)animated
{
    [self hiddenNavigationBar:YES];
 
}


-(void)viewWillDisappear:(BOOL)animated
{
    [self hiddenNavigationBar:NO];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.queryDrivingRouteVC = [[QueryDrivingRouteVC alloc]init];
    self.realTimeTrafficVC = [[RealTimeTrafficVC alloc]init];
    self.moreVC = [[MyVC alloc]init];
    self.reportVC = [[ReportVC alloc]init];
    
    viewControllersAry = [[NSArray alloc] initWithObjects:self.realTimeTrafficVC,self.queryDrivingRouteVC,self.reportVC,self.moreVC,nil];
    //初始化视图数组
    self.tabBarController.viewControllers = viewControllersAry;
    
    [CommonData sharedCommonData].realTimeTrafficVC = self.realTimeTrafficVC;
    

    //自定义tabBar
    [self initTarBar];

    /**
     存在问题，在ios7 下无法实现选中时高亮效果
     */
    // 需要设置frame值，否则在ios6中无法放在底部
    [self.tabBarController.view setFrame:self.view.frame];
    [self.view addSubview:self.tabBarController.view];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(realTimeChangeToJourneyPlanning:) name:@"realTimeChangeToJourneyPlanning" object:nil]; //选择起始站或终点站
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(backToRealTime:) name:@"backToRealTime" object:nil]; //报料完回到实况页
}

//自定义tabBar
-(void)initTarBar
{
    self.tabBarController.tabBar.hidden = YES;
    //使tabbar有上边界
    UIImageView *backgroundView = [[UIImageView alloc] initWithFrame:CGRectMake(0, SCREEN_HEIGHT-49, SCREEN_WIDTH, 50)];
    backgroundView.image = [UIImage imageNamed:@"tab-bg"];
    //阴影效果
    backgroundView.layer.shadowColor = [UIColor blackColor].CGColor;
    backgroundView.layer.shadowOffset = CGSizeMake(0.8 , 0.8);
    backgroundView.layer.shadowOpacity = 0.5;
    
    [self.tabBarController.view addSubview:backgroundView];
    
    imageViewArray = [[NSMutableArray alloc]init];
    txtArray = [[NSMutableArray alloc]init];
    imageArray = [[NSMutableArray alloc]init];
    
    unSelectImageArray = [[NSMutableArray alloc]initWithObjects:[UIImage imageNamed:@"tab-live"],
                           [UIImage imageNamed:@"tab-plan"],
                           [UIImage imageNamed:@"tab-report"],
                          [UIImage imageNamed:@"tab-my"],nil];
    
    selectedImageArray = [[NSMutableArray alloc]initWithObjects:[UIImage imageNamed:@"tab-live-down"],
                           [UIImage imageNamed:@"tab-plan-down"],
                           [UIImage imageNamed:@"tab-report-down"],
                          [UIImage imageNamed:@"tab-my-down"],nil];
    
    //for循环创建tabbarItem
    for (int i=0; i<4; i++)
    {
        UIImageView *view = [[UIImageView alloc]initWithFrame:CGRectMake(SCREEN_WIDTH/4 * i, SCREEN_HEIGHT-49, SCREEN_WIDTH/4, 49)];
        view.image = [UIImage imageNamed:@"tab-bg"];
  
        UITextField *txt = [[UITextField alloc] initWithFrame:CGRectMake(0, 30, SCREEN_WIDTH/4, 15)];
        [txt setFont:[UIFont fontWithName:@"helvetica" size:15]];
        txt.textAlignment = NSTextAlignmentCenter;
        txt.textColor = RGBCOLOR(68, 68, 68);
                UIImageView *imageView = [[UIImageView alloc] initWithImage:[unSelectImageArray objectAtIndex:i]];
        imageView.frame = CGRectMake(0, 3, SCREEN_WIDTH/4, 30);
        imageView.contentMode = UIViewContentModeCenter;
        
        UIButton *button = [[UIButton alloc]init];
        button.frame = view.frame;
        button.tag = i;
        button.backgroundColor = [UIColor clearColor];
        [button addTarget:self action:@selector(tabBarClick:) forControlEvents:UIControlEventTouchUpInside];
        switch (i) {
            case 0:
                txt.text = @"附近";
                lastButton = button;
                break;
            case 1:
                txt.text = @"行程";
                break;
            case 2:
                txt.text = @"报料";
                break;
            case 3:
                txt.text = @"我的";
                break;
        }

        
        [view addSubview:imageView];
        [view addSubview:txt];
        [imageViewArray addObject:view];
        [txtArray addObject:txt];
        [imageArray addObject:imageView];
        [self.tabBarController.view addSubview:view];
        [self.tabBarController.view addSubview:button];
    }
    //默认第一个tabBarItem被选中，设置成被选中的背景
    self.tabBarController.selectedViewController = _realTimeTrafficVC;
    UIImageView *View = [imageViewArray objectAtIndex:0];
    View.image = [UIImage imageNamed:@"tab-bg-down"];
    UITextField *txt = [txtArray objectAtIndex:0];
    txt.textColor = NAV_GREEN_FONT_COLOR;
    UIImageView *imageView = [imageArray objectAtIndex:0];
    imageView.image = [selectedImageArray objectAtIndex:0];
    

}

//选中tabBar单元
-(void)tabBarClick:(id)sender
{
    //修改上一个被选中的button为未选中的背景
    NSInteger lastTag = lastButton.tag;
    UIImageView *View = [imageViewArray objectAtIndex:lastTag];
    View.image = [UIImage imageNamed:@"tab-bg"];
    UITextField *txt = [txtArray objectAtIndex:lastTag];
    txt.textColor = RGBCOLOR(68, 68, 68);
    UIImageView *imageView = [imageArray objectAtIndex:lastTag];
    imageView.image = [unSelectImageArray objectAtIndex:lastTag];
    
    //修改这次被选中的button为选中的背景
    UIButton *button = (UIButton*)sender;
    lastButton = button;
    View = [imageViewArray objectAtIndex:button.tag];
    View.image = [UIImage imageNamed:@"tab-bg-down"];
    txt = [txtArray objectAtIndex:button.tag];
    txt.textColor = NAV_GREEN_FONT_COLOR;
    imageView = [imageArray objectAtIndex:button.tag];
    imageView.image = [selectedImageArray objectAtIndex:button.tag];
    
    self.tabBarController.selectedViewController = [viewControllersAry objectAtIndex:button.tag];
  
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - notification Method

//通知处理方法，把当前页面显示为行程规划页
-(void)realTimeChangeToJourneyPlanning:(NSNotification *)notification{
    
    NSDictionary* dic = notification.object;
    if ([[dic objectForKey:@"type"] isEqualToString:@"start"]) { //作为起始站
        self.queryDrivingRouteVC.startStationDic = [dic objectForKey:@"stationDic"];
    }else{
        self.queryDrivingRouteVC.endStationDic = [dic objectForKey:@"stationDic"];
    }
    [self tabBarClick:[self.view viewWithTag:1]];
    
}

//报料完回到实况页
-(void)backToRealTime:(NSNotification *)notification{
    [self tabBarClick:[self.view viewWithTag:0]];
    
    AwardCircleView* circleView = notification.object;
    [UIView animateWithDuration:0.3 delay:0.8 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        if (circleView.tag!=0) {
            circleView.alpha=0;
        }
    } completion:^(BOOL finish){
        [circleView removeFromSuperview];
    }];
    
}

@end
