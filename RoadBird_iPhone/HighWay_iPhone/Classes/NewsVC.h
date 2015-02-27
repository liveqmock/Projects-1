//
//  NewsVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-19.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"
#import "AccidentVC.h"
#import "ConstructVC.h"
#import "UserShareVC.h"

@interface NewsVC : BaseVC<UIScrollViewDelegate>
{
    BOOL loading;
    int type;  // 1:交通事故 2:道路施工信息
    
    AccidentVC *accidentVC;
    ConstructVC *constructVC;
    UserShareVC *userShareVC;
}


@property (nonatomic,weak) IBOutlet UIButton *accidentBtn,*construcionBtn,*userShareBtn;
@property (nonatomic,weak) IBOutlet UIScrollView *scrollView;
@property (weak, nonatomic) IBOutlet UIView *accidentView;
@property (weak, nonatomic) IBOutlet UIView *fixView;
@property (weak, nonatomic) IBOutlet UIView *userShareView;

// 约束
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *accidentHeight;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *fixHeight;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *userShareHeight;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *accidentWidth;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *fixWidth;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *userShareWidth;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *accidentBtnWidth;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *fixBtnWidth;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *userShareBtnWidth;

@end
