//
//  RegisterVC.h
//  HighWay_iPhone
//
//  Created by litong on 14-12-31.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"

@interface NormalRegisterVC : BaseVC<UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UINavigationItem *navItem;
@property (weak, nonatomic) IBOutlet UINavigationBar *navBar;
@property (strong, nonatomic) IBOutlet UITapGestureRecognizer *bgTap;

// 信息textfield
@property (weak, nonatomic) IBOutlet UITextField *phoneTextField;
@property (weak, nonatomic) IBOutlet UITextField *codeTextField;
@property (weak, nonatomic) IBOutlet UITextField *passwordTextField;
@property (weak, nonatomic) IBOutlet UITextField *userNameTextField;

@property (weak, nonatomic) IBOutlet UIButton *codeBtn;
@property (weak, nonatomic) IBOutlet UIButton *registerBtn;

// 点击背景，隐藏键盘
- (IBAction)clickBg:(id)sender;
- (IBAction)clickReturn:(id)sender;
- (IBAction)clickRegister:(id)sender;
@end
