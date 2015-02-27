//
//  RegisterVC.m
//  HighWay_iPhone
//
//  Created by litong on 14-12-31.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "NormalRegisterVC.h"
#import "AppDelegate.h"

@interface NormalRegisterVC ()

@end

@implementation NormalRegisterVC

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self initNavView];
    [self initTextView];
    [self.view addGestureRecognizer:self.bgTap]; //背景手势
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - customMethod

// 设置自定义navigationBar的样式
- (void)initNavView{
    UILabel *titleLabel = [[UILabel alloc]initWithFrame:CGRectZero];
    titleLabel.text = @"用户注册";
    titleLabel.textColor = NAV_GREEN_FONT_COLOR;
    titleLabel.textAlignment=NSTextAlignmentCenter;
    titleLabel.font = [UIFont boldSystemFontOfSize:16.0f];
    self.navItem.titleView = titleLabel;
    [titleLabel sizeToFit];
    
    UIView *navBorder = [[UIView alloc] initWithFrame:CGRectMake(0,app.nav.navigationBar.frame.size.height,app.nav.navigationBar.frame.size.width, 0.5f)];
    [navBorder setBackgroundColor:NAV_GREEN_FONT_COLOR];
    [navBorder setOpaque:YES];
    [self.navBar addSubview:navBorder];
    
    [self.navBar setBarTintColor:NAVBACKGROUNDCOLOR];
    
    if (IOS_VERSION>=7.0) {
        UIView *stautsView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 20)];
        stautsView.backgroundColor = NAVBACKGROUNDCOLOR;
        [self.view addSubview:stautsView];
    }

}

// 添加边框
-(void) initTextView{
    self.codeBtn.layer.borderColor = [NAV_GREEN_FONT_COLOR CGColor];
    self.codeBtn.layer.borderWidth = 0.5;
    self.registerBtn.layer.borderColor = [NAV_GREEN_FONT_COLOR CGColor];
    self.registerBtn.layer.borderWidth = 0.5;
    
    self.phoneTextField.layer.borderColor = [RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    self.phoneTextField.layer.borderWidth = 0.5;
    self.codeTextField.layer.borderColor = [RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    self.codeTextField.layer.borderWidth = 0.5;
    self.passwordTextField.layer.borderColor = [RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    self.passwordTextField.layer.borderWidth = 0.5;
    self.userNameTextField.layer.borderColor = [RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    self.userNameTextField.layer.borderWidth = 0.5;

}

#pragma mark - IBAction
// 点击背景
- (IBAction)clickBg:(id)sender {
    [self.phoneTextField resignFirstResponder];
    [self.passwordTextField resignFirstResponder];
    [self.codeTextField resignFirstResponder];
    [self.userNameTextField resignFirstResponder];
    
    [UIView animateWithDuration:0.3 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        CGAffineTransform pTransform = CGAffineTransformMakeTranslation(0, 0);
        self.view.transform = pTransform;
    } completion:nil];
}

// 后退键
- (IBAction)clickReturn:(id)sender {
    [self dismissViewControllerAnimated:YES completion:nil];
}

// 注册键
- (IBAction)clickRegister:(id)sender {
    if ([self.userNameTextField.text isEqualToString:@""]||[self.passwordTextField.text isEqualToString:@""]||[self.phoneTextField.text isEqualToString:@""]) {
        [[[UIAlertView alloc] initWithTitle:@"提示" message:@"请完整填写信息！" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
        return;
    }else if (!([ComFun isPureInt:self.phoneTextField.text]&&self.phoneTextField.text.length==11)){ //手机号码填写不正确
        [[[UIAlertView alloc] initWithTitle:@"提示" message:@"请正确填写11位手机号码" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
        return;
    }
    
    NSDictionary *param = @{@"userName":self.userNameTextField.text,
                            @"passWord":self.passwordTextField.text,
                            @"phone":self.phoneTextField.text,
                            @"person":@"",
                            @"company":@""};
    [DataDAO postNormalRegister:param withCompleted:^(id result){
        NSLog(@"注册成功！");
        NSDictionary* dic = (NSDictionary*)result;
        [CommonData sharedCommonData].isLogin = YES;
        [CommonData sharedCommonData].me.userName = self.userNameTextField.text;
        [CommonData sharedCommonData].me.phoneNum = self.phoneTextField.text;
        [CommonData sharedCommonData].me.passwrod = self.passwordTextField.text;
        [CommonData sharedCommonData].me.myId = [dic objectForKey:@"userId"];
        [CommonData sharedCommonData].me.isCompany = NO;
        [self dismissViewControllerAnimated:YES completion:^{
            [app.nav popViewControllerAnimated:YES];
        }];
    } withFailure:^(id error){
        NSLog(@"%@",error);
    }];
}

#pragma mark - textFieldDelegate
-(void)textFieldDidBeginEditing:(UITextField*)textField{
    [UIView animateWithDuration:0.3 delay:0.1 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        
        if (SCREEN_HEIGHT==480) { //3.5寸
            CGAffineTransform pTransform = CGAffineTransformMakeTranslation(0, -170);
            self.view.transform = pTransform;
        }else{ //4寸
            CGAffineTransform pTransform = CGAffineTransformMakeTranslation(0, -70);
            self.view.transform = pTransform;
        }
        
    } completion:nil];
}

@end
