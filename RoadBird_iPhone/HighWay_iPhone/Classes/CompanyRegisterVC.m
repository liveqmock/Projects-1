//
//  CompanyRegisterVC.m
//  HighWay_iPhone
//
//  Created by litong on 15-2-2.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import "CompanyRegisterVC.h"
#import "AppDelegate.h"
#import "DataDAO.h"

@interface CompanyRegisterVC ()

@end

@implementation CompanyRegisterVC

- (void)viewDidLoad {
    [super viewDidLoad];
    [self initView];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


// 初始化控件
-(void)initView{
    // 约束
    self.widthConstraint1.constant = SCREEN_WIDTH;
    self.widthConstraint2.constant = SCREEN_WIDTH;
    self.heightConstraint1.constant = SCREEN_HEIGHT;
    self.heightConstraint2.constant = SCREEN_HEIGHT;
    
    self.firstStepView.frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    self.secondStepView.frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    [self.contentView1 addSubview:self.firstStepView];
    [self.contentView2 addSubview:self.secondStepView];
    
    self.scrollView.scrollEnabled = NO;
    self.scrollView.pagingEnabled = YES;
    self.scrollView.showsHorizontalScrollIndicator = NO;
    
    // 导航栏样式
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectZero];
    titleLabel.text = @"企业注册";
    titleLabel.textColor = NAV_GREEN_FONT_COLOR;
    titleLabel.textAlignment=NSTextAlignmentCenter;
    titleLabel.font = [UIFont boldSystemFontOfSize:16.0f];
    self.navItem.titleView = titleLabel;
    [titleLabel sizeToFit];
    
    UIView *navBorder = [[UIView alloc] initWithFrame:CGRectMake(0,app.nav.navigationBar.frame.size.height,app.nav.navigationBar.frame.size.width, 0.5f)];
    [navBorder setBackgroundColor:NAV_GREEN_FONT_COLOR];
    [navBorder setOpaque:YES];
    [self.nav addSubview:navBorder];
    
    [self.nav setBarTintColor:NAVBACKGROUNDCOLOR];
    
    if (IOS_VERSION>=7.0) {
        UIView *stautsView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 20)];
        stautsView.backgroundColor = NAVBACKGROUNDCOLOR;
        [self.view addSubview:stautsView];
    }

}

#pragma mark - TextFiled Delegate
- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}

#pragma mark - IBAction
- (IBAction)goBack:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (IBAction)nextStep:(id)sender {
    [self.scrollView setContentOffset:CGPointMake(SCREEN_WIDTH,0) animated:YES];
}

- (IBAction)clickRegister:(id)sender {
    if ([self.userName.text isEqualToString:@""]||[self.password.text isEqualToString:@""]||[self.companyName.text isEqualToString:@""]||[self.companyPerson.text isEqualToString:@""]||[self.companyPhone.text isEqualToString:@""]) {
        [[[UIAlertView alloc] initWithTitle:@"提示" message:@"请完整填写信息！" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
        return;
    }else if (!([ComFun isPureInt:self.companyPhone.text]&&self.companyPhone.text.length==11)){ //手机号码填写不正确
         [[[UIAlertView alloc] initWithTitle:@"提示" message:@"请正确填写11位手机号码" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
        return;
    }
    
    NSDictionary* param = @{@"userName":self.userName.text,
                            @"passWord":self.password.text,
                            @"company":self.companyName.text,
                            @"person":self.companyPerson.text,
                            @"phone":self.companyPhone.text};
    [DataDAO postCompanyRegister:param withCompleted:^(id result){
        NSLog(@"企业用户注册成功！");
        NSDictionary *dic = (NSDictionary*)result;
        [CommonData sharedCommonData].isLogin = YES;
        [CommonData sharedCommonData].me.userName = self.userName.text;
        [CommonData sharedCommonData].me.passwrod = self.password.text;
        [CommonData sharedCommonData].me.companyName = self.companyName.text;
        [CommonData sharedCommonData].me.companyPersonName = self.companyPerson.text;
        [CommonData sharedCommonData].me.phoneNum = self.companyPhone.text;
        [CommonData sharedCommonData].me.myId = [dic objectForKey:@"userId"];
        [CommonData sharedCommonData].me.isCompany = YES;
        [self dismissViewControllerAnimated:YES completion:^{
            [app.nav popViewControllerAnimated:YES];
        }];
    } withFailure:^(id result){
        NSLog(@"%@",result);
    }];
}
@end
