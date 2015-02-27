//
//  HighWayLifePublishVC.m
//  HighWay_iPhone
//
//  Created by litong on 15-2-2.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import "HighWayLifePublishVC.h"

@interface HighWayLifePublishVC ()

@end

@implementation HighWayLifePublishVC

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"发布监控信息";
    [self.view addGestureRecognizer:self.tap];
    
    self.publishBtn.layer.borderWidth = 0.5;
    self.publishBtn.layer.borderColor = [NAV_GREEN_FONT_COLOR CGColor];
    
    self.titleTxt.layer.borderWidth = 0.5;
    self.titleTxt.layer.borderColor = [RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    self.detailTxt.layer.borderWidth = 0.5;
    self.detailTxt.layer.borderColor = [RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - UITextFiled Delegate

- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    return YES;
}

- (BOOL)textViewShouldBeginEditing:(UITextView *)textView{
    self.detailHolder.hidden = YES;
    
    if(ISIPHONE4S){
        [UIView animateWithDuration:0.3 delay:0.1 options:UIViewAnimationOptionCurveEaseInOut animations:^{
            CGAffineTransform transform = CGAffineTransformMakeTranslation(0, -75);
            self.view.transform = transform;
        } completion:nil];
    }
    return YES;
}

- (void)textViewDidEndEditing:(UITextView *)textView{
    if ([textView.text isEqual:@""]) {
        self.detailHolder.hidden = NO;
    }
    if(ISIPHONE4S){
        [UIView animateWithDuration:0.3 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
            CGAffineTransform transform = CGAffineTransformMakeTranslation(0, 0);
            self.view.transform = transform;
        } completion:nil];
    }

}
#pragma mark - IBAction
- (IBAction)publishBtn:(id)sender {
    
}

- (IBAction)tapGesture:(id)sender {
    [self.detailTxt resignFirstResponder];
    [self.titleTxt resignFirstResponder];
}

@end
