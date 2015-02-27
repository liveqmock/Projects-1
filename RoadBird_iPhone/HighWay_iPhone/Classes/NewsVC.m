//
//  NewsVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-19.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "NewsVC.h"
#import "NewsInfoVC.h"
#import "AppDelegate.h"


@implementation NewsVC

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.navigationItem.title=@"高速资讯";
    self.scrollView.delegate = self;
   
    [self initView];
    [self btnClick:_accidentBtn];
}

- (void) initView
{
    _accidentBtn.tag = 0;
    _construcionBtn.tag = 1;
    _userShareBtn.tag = 2;
    
    UIView *line = [[UIView alloc] initWithFrame:CGRectMake(0, 108, SCREEN_WIDTH, 0.5)];
    line.backgroundColor = RGBCOLOR(229.0f,229.0f,229.0f);
    [self.view addSubview:line];
    
    accidentVC = [[AccidentVC alloc] init];
    constructVC = [[ConstructVC alloc] init];
    userShareVC = [[UserShareVC alloc] init];
    
    
    
    // 重设高度约束
    self.accidentHeight.constant = SCREEN_HEIGHT-108;
    self.fixHeight.constant = SCREEN_HEIGHT-108;
    self.userShareHeight.constant = SCREEN_HEIGHT-108;
    
    self.accidentWidth.constant = SCREEN_WIDTH;
    self.fixWidth.constant = SCREEN_WIDTH;
    self.userShareWidth.constant = SCREEN_WIDTH;
    
    [accidentVC.view setFrame:CGRectMake(0, 0, self.accidentView.frame.size.width,  self.accidentView.frame.size.height)];
    [constructVC.view setFrame:CGRectMake(0, 0, self.fixView.frame.size.width, self.fixView.frame.size.height)];
    [userShareVC.view setFrame:CGRectMake(0, 0, self.userShareView.frame.size.width, self.userShareView.frame.size.height)];
    
    //scrollView 初始化
    [self.accidentView addSubview:accidentVC.view];
    [self.fixView addSubview:constructVC.view];
    [self.userShareView addSubview:userShareVC.view];
    self.scrollView.pagingEnabled = YES;
    self.scrollView.showsHorizontalScrollIndicator = NO;
    
    //按键重设约束
    self.accidentBtnWidth.constant = SCREEN_WIDTH/3;
    self.fixBtnWidth.constant = SCREEN_WIDTH/3;
    self.userShareBtnWidth.constant = SCREEN_WIDTH/3;
}

//按键点击事件
-(IBAction)btnClick:(id)sender
{
    UIButton *btn =sender;
    int tag =(int)btn.tag;
    [self.scrollView setContentOffset:CGPointMake((tag)*SCREEN_WIDTH,0) animated:YES];
    [self changeButtonColor:tag];
}

//修改按键颜色样式
-(void)changeButtonColor:(int)flag{
    
    //复原颜色
    _accidentBtn.backgroundColor=[UIColor whiteColor];
    [_accidentBtn setTitleColor:NAV_GREEN_FONT_COLOR forState:UIControlStateNormal];
    _construcionBtn.backgroundColor=[UIColor whiteColor];
    [_construcionBtn setTitleColor:NAV_GREEN_FONT_COLOR forState:UIControlStateNormal];
    _userShareBtn.backgroundColor=[UIColor whiteColor];
    [_userShareBtn setTitleColor:NAV_GREEN_FONT_COLOR forState:UIControlStateNormal];

     self.scrollView.scrollEnabled = YES;
    
    switch (flag) {
        case 0: //交通事故
            _accidentBtn.backgroundColor=NAV_GREEN_FONT_COLOR;
            [_accidentBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            break;
        case 1: //道路施工
            _construcionBtn.backgroundColor=NAV_GREEN_FONT_COLOR;
            [_construcionBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            break;
        case 2: //用户分享
            _userShareBtn.backgroundColor=NAV_GREEN_FONT_COLOR;
            [_userShareBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            self.scrollView.scrollEnabled = NO; //将焦点转移给地图
            break;
    }
    

}

#pragma mark - scollView Delegate
-(void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView{
    int index = fabs(scrollView.contentOffset.x)/self.view.frame.size.width;
    [self changeButtonColor:index];
    
}


@end