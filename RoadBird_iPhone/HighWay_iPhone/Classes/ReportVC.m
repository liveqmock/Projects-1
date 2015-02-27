//
//  ReportVC.m
//  HighWay_iPhone
//
//  Created by litong on 14-12-15.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "ReportVC.h"
#import "BaseVC.h"
#import "LoginVC.h"
#import "AppDelegate.h"

@interface ReportVC (){

}

@end

@implementation ReportVC

- (void)viewDidLoad {
    [super viewDidLoad];
    //背景
    bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
    bgView.backgroundColor = [UIColor blackColor];
    bgView.alpha=0;
    [bgView addGestureRecognizer:[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(clickBgView)]];
    [self.view addSubview:bgView];

}

- (void)viewWillAppear:(BOOL)animated{
    //初始化
    self.realTimeView = [CommonData sharedCommonData].realTimeTrafficVC.view;
    [self.view addSubview:self.realTimeView];
    [self.view bringSubviewToFront:bgView];
    self.mainView.frame = CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, 220);
    [self.view addSubview:self.mainView];
    //阴影
    self.mainView.layer.shadowColor = [UIColor blackColor].CGColor;
    self.mainView.layer.shadowOffset = CGSizeMake(0.5 , 0);
    self.mainView.layer.shadowOpacity = 0.5;
    self.mainView.layer.shadowRadius = 8;
    
    [self showMainView];
}

-(void)viewWillDisappear:(BOOL)animated{
    bgView.alpha=0;
}

#pragma mark - report

// 显示报料界面（动画）
-(void)showMainView{
    [UIView beginAnimations:nil context:nil];
    bgView.alpha=0.5;
    [UIView setAnimationDuration:0.3];
    self.mainView.frame = CGRectMake(0, SCREEN_HEIGHT-220-49, SCREEN_WIDTH, 220);
    [UIView commitAnimations];
}

// 点击了背景 隐藏报料界面（动画）
-(void)clickBgView{
    
    [UIView animateWithDuration:0.2 animations:^{
        self.mainView.frame = CGRectMake(0, SCREEN_HEIGHT, SCREEN_WIDTH, 220);
        if (isClickReport) {
            awardView.tag=1;
            awardView.alpha=1;
        }else{
            awardView.tag=0;
        }
        bgView.alpha=0;
        
    } completion:^(BOOL finish){ // 动画完成block
        [[NSNotificationCenter defaultCenter] postNotificationName:@"backToRealTime" object:awardView];
    }];
    
    isClickReport = NO;
}

//显示奖励视图，同时隐藏报料视图
-(void)awardViewShow{
    isClickReport = YES; //点击了上报
    [[[UIApplication sharedApplication].delegate window] addSubview:awardView];
     awardView.alpha=0.8;
    [self clickBgView];
}

#pragma mark - 报料触发事件
- (IBAction)trafficJam:(id)sender {
    [self postReportByTpye:TrafficJam];
}

- (IBAction)accident:(id)sender {
    [self postReportByTpye:Accident];
}

- (IBAction)fix:(id)sender {
    [self postReportByTpye:Fix];
}

- (IBAction)control:(id)sender {
    [self postReportByTpye:Control];
}

- (IBAction)water:(id)sender {
    [self postReportByTpye:Water];
}

- (IBAction)landslides:(id)sender {
    [self postReportByTpye:Landslide];
}

// 上报获取积分
-(void)postReportByTpye:(ReportType)reportType{
//    [CommonData sharedCommonData].me.myId = @"00000000111111111111111100000000"; //测试
    if ([CommonData sharedCommonData].me.myId!=nil) { //用户已登录(必须要具有myId)
        NSDictionary *param = @{@"userId":[CommonData sharedCommonData].me.myId,
                                @"xcode":[[NSString alloc] initWithFormat:@"%f", [CommonData sharedCommonData].currentLocationCoordinate2D.longitude],
                                @"ycode":[[NSString alloc] initWithFormat:@"%f", [CommonData sharedCommonData].currentLocationCoordinate2D.latitude],
                                @"occtime":[NSDate formateDate:[NSDate date] fromate:@"yyyy-MM-dd HH:mm:ss"],
                                @"type":[[NSString alloc] initWithFormat:@"%d",reportType]};
        
        
        [DataDAO postReport:param withCompleted:^(id result){ // 提交上报
            NSDictionary *json = (NSDictionary*)result;
            awardView = [AwardCircleView getAwardCircleViewWithString:@"奖励积分" withScroe:[[json objectForKey:@"score"] intValue]];
            [self awardViewShow];
            int intString = [[CommonData sharedCommonData].allScore intValue];
            intString += 20;
            [CommonData sharedCommonData].allScore = [NSString stringWithFormat:@"%d",intString];
        } withFailure:^(id error){
            NSLog(@"%@",error);
            awardView = [AwardCircleView getAwardCircleViewWithString:@"谢谢报料" withScroe:0];
            [self awardViewShow];
        }];
        
    }else{ //未登录
        [[[UIAlertView alloc] initWithTitle:@"提示" message:@"请先登录！" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
    }
}

#pragma mark - alertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
    LoginVC *loginVC = [[LoginVC alloc] init];
    [app.nav pushViewController:loginVC animated:YES];
}

@end
