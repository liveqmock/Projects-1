//
//  HighwayLifeVC.m
//  HighWay_iPhone
//
//  Created by litong on 15-1-13.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import "HighwayLifeVC.h"
#import "HighwayLifeInfoVC.h"
#import "HighWayLifePublishVC.h"
#import "AppDelegate.h"

@interface HighwayLifeVC ()

@end

@implementation HighwayLifeVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
     self.title = @"高速生活";
    UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithTitle:@"发布" style:UIBarButtonItemStylePlain target:self action:@selector(clickPublish)];
    self.navigationItem.rightBarButtonItem = item;
    
    NSString* directory =@"";
    if (SCREEN_WIDTH == 320) { //<=iphone5s
       directory = @"iPhone5s";
    }else if(SCREEN_WIDTH == 375){ //iPhone6
        directory = @"iPhone6";
    }else { //iPhone6+
        directory = @"iPhone6+";
    }

    [self.newsBtn setImage:[UIImage imageNamed:[[NSString alloc] initWithFormat:@"%@/HighWayLife-news",directory]]  forState:UIControlStateNormal];
    [self.eatBtn setImage:[UIImage imageNamed:[[NSString alloc] initWithFormat:@"%@/HighWayLife-eat",directory]]  forState:UIControlStateNormal];
    [self.lawBtn setImage:[UIImage imageNamed:[[NSString alloc] initWithFormat:@"%@/HighWayLife-law",directory]]  forState:UIControlStateNormal];
    [self.liveBtn setImage:[UIImage imageNamed:[[NSString alloc] initWithFormat:@"%@/HighWayLife-live",directory]]  forState:UIControlStateNormal];
    [self.discountsBtn setImage:[UIImage imageNamed:[[NSString alloc] initWithFormat:@"%@/HighWayLife-discount",directory]]  forState:UIControlStateNormal];
    [self.funBtn setImage:[UIImage imageNamed:[[NSString alloc] initWithFormat:@"%@/HighWayLife-fun",directory]]  forState:UIControlStateNormal];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



#pragma mark - IBActions

// 发布
-(void)clickPublish{
    HighWayLifePublishVC* publishVC = [[HighWayLifePublishVC alloc] init];
    [app.nav pushViewController:publishVC animated:YES];
}

// 新闻
- (IBAction)clickNews:(id)sender {
    [CommonData sharedCommonData].HighwayLifeType = 0;
    HighwayLifeInfoVC* infoVC = [[HighwayLifeInfoVC alloc] initWithTitle:@"新闻"];
    [app.nav pushViewController:infoVC animated:YES];
}

// 食
- (IBAction)clickEat:(id)sender {
    [CommonData sharedCommonData].HighwayLifeType = 1;
    HighwayLifeInfoVC* infoVC = [[HighwayLifeInfoVC alloc] initWithTitle:@"食"];
    [app.nav pushViewController:infoVC animated:YES];
}

// 住
- (IBAction)clickLive:(id)sender {
    [CommonData sharedCommonData].HighwayLifeType = 2;
    HighwayLifeInfoVC* infoVC = [[HighwayLifeInfoVC alloc] initWithTitle:@"住"];
    [app.nav pushViewController:infoVC animated:YES];
}

// 法规
- (IBAction)clickLaw:(id)sender {
    [CommonData sharedCommonData].HighwayLifeType = 3;
    HighwayLifeInfoVC* infoVC = [[HighwayLifeInfoVC alloc] initWithTitle:@"法规"];
    [app.nav pushViewController:infoVC animated:YES];
}

// 玩
- (IBAction)clickFun:(id)sender {
    [CommonData sharedCommonData].HighwayLifeType = 4;
    HighwayLifeInfoVC* infoVC = [[HighwayLifeInfoVC alloc] initWithTitle:@"玩"];
    [app.nav pushViewController:infoVC animated:YES];
}

// 惠
- (IBAction)clickDiscounts:(id)sender {
    [CommonData sharedCommonData].HighwayLifeType = 5;
    HighwayLifeInfoVC* infoVC = [[HighwayLifeInfoVC alloc] initWithTitle:@"惠"];
    [app.nav pushViewController:infoVC animated:YES];
}
@end
