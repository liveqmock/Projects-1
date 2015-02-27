//
//  LoginVC.h
//  HighWay_iPhone
//
//  Created by litong on 14-12-30.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"

@interface LoginVC : BaseVC<UITextFieldDelegate>
{

    IBOutlet UITapGestureRecognizer *bgTap;
}

@property (strong, nonatomic) IBOutlet UIView *waitBgView;

@property (weak, nonatomic) IBOutlet UITextField *passwordTxt;
@property (weak, nonatomic) IBOutlet UITextField *userNameTxt;
@property (weak, nonatomic) IBOutlet UIButton *LoginBtn;


- (IBAction)clickLoginBtn:(id)sender;
- (IBAction)normalRegister:(id)sender;
- (IBAction)companyRegister:(id)sender;
- (IBAction)clickBg:(id)sender;

- (IBAction)QQLogin:(id)sender;
- (IBAction)WeChatLogin:(id)sender;
- (IBAction)SinaWeboLogin:(id)sender;

@end
