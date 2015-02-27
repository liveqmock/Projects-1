//
//  CompanyRegisterVC.h
//  HighWay_iPhone
//
//  Created by litong on 15-2-2.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import "BaseVC.h"

@interface CompanyRegisterVC : BaseVC <UITextFieldDelegate>


@property (weak, nonatomic) IBOutlet UINavigationBar *nav;
@property (weak, nonatomic) IBOutlet UINavigationItem *navItem;

@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UIView *contentView1;
@property (weak, nonatomic) IBOutlet UIView *contentView2;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *widthConstraint1;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *heightConstraint1;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *widthConstraint2;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *heightConstraint2;

@property (strong, nonatomic) IBOutlet UIView *firstStepView;
@property (strong, nonatomic) IBOutlet UIView *secondStepView;

@property (weak, nonatomic) IBOutlet UITextField *userName;
@property (weak, nonatomic) IBOutlet UITextField *password;
@property (weak, nonatomic) IBOutlet UITextField *passwordEnsure;

@property (weak, nonatomic) IBOutlet UITextField *companyName;
@property (weak, nonatomic) IBOutlet UITextField *companyPerson;
@property (weak, nonatomic) IBOutlet UITextField *companyPhone;
@property (weak, nonatomic) IBOutlet UITextField *companyAddress;


- (IBAction)goBack:(id)sender;
- (IBAction)nextStep:(id)sender;
- (IBAction)clickRegister:(id)sender;



@end
