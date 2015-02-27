//
//  AboutVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-19.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "AboutVC.h"

@interface AboutVC ()

@end

@implementation AboutVC



- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.title=@"关于";
    self.appNameLabel.text=[NSString stringWithFormat:@"%@ V%@",_appNameLabel.text,APP_Version];

    self.view1.layer.borderWidth=0.5f;
    self.view1.layer.borderColor=[RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    self.view2.layer.borderWidth=0.5f;
    self.view2.layer.borderColor=[RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
   
    UIView *line1 = [[UIView alloc] initWithFrame:CGRectMake(0, 40, self.view1.frame.size.width, 0.5)];
    UIView *line2 = [[UIView alloc] initWithFrame:CGRectMake(0, 80, self.view1.frame.size.width, 0.5)];
    line2.backgroundColor = RGBCOLOR(229.0f,229.0f,229.0f);
    line1.backgroundColor = RGBCOLOR(229.0f,229.0f,229.0f);
    [self.view1 addSubview:line1];
    [self.view1 addSubview:line2];
    
}



- (IBAction)phoneCall:(id)sender {
    [ ComFun alertWithMessageAndDelegateAndTitle:@"":self.phoneNum.text:self:@"取消":@"呼叫"];
}
@end
