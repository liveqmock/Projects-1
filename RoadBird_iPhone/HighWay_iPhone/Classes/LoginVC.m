//
//  LoginVC.m
//  HighWay_iPhone
//
//  Created by litong on 14-12-30.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "LoginVC.h"
#import "CompanyRegisterVC.h"
#import "NormalRegisterVC.h"
#import "AppDelegate.h"
#import "Person.h"
#import <ShareSDK/ShareSDK.h>

@interface LoginVC ()

@end

@implementation LoginVC

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"登录";
    
    self.passwordTxt.layer.borderWidth=0.5;
    self.passwordTxt.layer.borderColor=[RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    
    self.userNameTxt.layer.borderWidth=0.5;
    self.userNameTxt.layer.borderColor=[RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    
    self.LoginBtn.layer.borderWidth=0.5;
    self.LoginBtn.layer.borderColor=[NAV_GREEN_FONT_COLOR CGColor];
 
    [self.view addGestureRecognizer:bgTap]; //点击背景的手势
    
    [self.waitBgView setFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
    self.waitBgView.layer.cornerRadius = 5;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - IBAction
// 企业注册
- (IBAction)companyRegister:(id)sender {
    CompanyRegisterVC *companyRegisterVC = [[CompanyRegisterVC alloc] init];
    [self presentViewController:companyRegisterVC animated:YES completion:nil];
}

// 普通注册
- (IBAction)normalRegister:(id)sender {
    NormalRegisterVC *normalRegisterVC = [[NormalRegisterVC alloc] init];
    [self presentViewController:normalRegisterVC animated:YES completion:nil];
}

// 登录
- (IBAction)clickLoginBtn:(id)sender {
    if ([self.userNameTxt.text isEqualToString:@""]||[self.passwordTxt.text isEqualToString:@""]) {
        [[[UIAlertView alloc] initWithTitle:@"提示" message:@"请完整填写信息！" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
        return;
    }
    NSDictionary *param = @{@"userName":self.userNameTxt.text,
                            @"passWord":self.passwordTxt.text};
    [DataDAO getNormalLogin:param withCompleted:^(id result){
        
        NSLog(@"result===%@",result);
        NSDictionary* dic = (NSDictionary*)result;
        int type = [[dic objectForKey:@"type"] integerValue];
        if (type == 0) { //普通用户
            [CommonData sharedCommonData].isLogin = YES;
            [CommonData sharedCommonData].me.isCompany = NO;
            [CommonData sharedCommonData].me.userName = self.userNameTxt.text;
            [CommonData sharedCommonData].me.passwrod = self.passwordTxt.text;
            [CommonData sharedCommonData].me.phoneNum = [[dic objectForKey:@"userInfo"] objectForKey:@"phone"];
            NSString *image=[[dic objectForKey:@"userInfo"] objectForKey:@"image"];
            if([NSString isEmpty:image]){
                image =[[NSBundle mainBundle] pathForResource:@"head-default.png" ofType:nil];
            }
            [CommonData sharedCommonData].me.headImage = [UIImage imageWithContentsOfFile:image];
            [CommonData sharedCommonData].me.myId = [dic objectForKey:@"userId"];
            //[app.nav popViewControllerAnimated:YES];
        }else if (type == 1){ //企业用户
            [CommonData sharedCommonData].isLogin = YES;
            [CommonData sharedCommonData].me.isCompany = YES;
            [CommonData sharedCommonData].me.userName = self.userNameTxt.text;
            [CommonData sharedCommonData].me.passwrod = self.passwordTxt.text;
            [CommonData sharedCommonData].me.phoneNum = [[dic objectForKey:@"userInfo"] objectForKey:@"phone"];
            [CommonData sharedCommonData].me.comPhone = [[dic objectForKey:@"userInfo"] objectForKey:@"comPhone"];
//            [CommonData sharedCommonData].me.headImage = [[dic objectForKey:@"userInfo"] objectForKey:@"image"];
            NSString *image=[[dic objectForKey:@"userInfo"] objectForKey:@"image"];
            if([NSString isEmpty:image]){
                image =[[NSBundle mainBundle] pathForResource:@"head-default.png" ofType:nil];
            }
            [CommonData sharedCommonData].me.headImage = [UIImage imageWithContentsOfFile:image];
            [CommonData sharedCommonData].me.companyName = [[dic objectForKey:@"userInfo"] objectForKey:@"company"];
            [CommonData sharedCommonData].me.companyPersonName = [[dic objectForKey:@"userInfo"] objectForKey:@"comPerson"];
            [CommonData sharedCommonData].me.myId = [dic objectForKey:@"userId"];
            //[app.nav popViewControllerAnimated:YES];

        }
        [self getAllScoreAndPopView];
    } withFailure:^(id error){
        NSLog(@"%@",error);
    }];
}

-(void) getAllScoreAndPopView{
    //获取并设置总积分
    NSDictionary *param = @{@"userId":[CommonData sharedCommonData].me.myId};
    NSLog(@"[CommonData sharedCommonData] param:%@",param);
    [DataDAO getQueryScore:param withCompleted:^(id result){
        NSLog(@"[CommonData getQueryScore] result:%@",result);
        NSDictionary* dic = (NSDictionary*)result;
        [CommonData sharedCommonData].allScore = [dic objectForKey:@"totalScore"];
        NSLog(@"[CommonData sharedCommonData].allScore:%@",[CommonData sharedCommonData].allScore);
        [app.nav popViewControllerAnimated:YES];
    } withFailure:^(id error){
        NSLog(@"getQueryScore error2:%@",error);
    }];
}


// 点击了背景
- (IBAction)clickBg:(id)sender {
    [self.userNameTxt resignFirstResponder];
    [self.passwordTxt resignFirstResponder];
    
    //恢复视图
    [UIView animateWithDuration:0.3 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        CGAffineTransform pTransform = CGAffineTransformMakeTranslation(0, 0);
        self.view.transform = pTransform;
    } completion:nil];

}

#pragma mark - TextFieldDelegate

- (void)textFieldDidBeginEditing:(UITextField *)textField{
    if (SCREEN_HEIGHT==480) { //3.5寸需要移动视图
        [UIView animateWithDuration:0.3 delay:0.1 options:UIViewAnimationOptionCurveEaseInOut animations:^{
            CGAffineTransform pTransform = CGAffineTransformMakeTranslation(0, -30);
            self.view.transform = pTransform;
        } completion:nil];
    }
}

#pragma mark - 第三方登录
/*
 *  第三方登录的userName，不是昵称，而是uId（各家平台提供）
 *  而myId是自己后台提供，用来积分管理，二者不一样
 */

//QQ
- (IBAction)QQLogin:(id)sender {
    [self.view addSubview:self.waitBgView]; //等待背景
    ShareType type = ShareTypeQQSpace;
    id<ISSAuthOptions> options = [ShareSDK authOptionsWithAutoAuth:YES
                        allowCallback:YES
                        authViewStyle:SSAuthViewStyleFullScreenPopup
                         viewDelegate:nil
              authManagerViewDelegate:nil];
    
    [ShareSDK getUserInfoWithType:type
                      authOptions:options
                           result:^(BOOL result,id<ISSPlatformUser>userInfo,id<ICMErrorInfo>error){
                               NSLog(@"%@",userInfo);
                               if (result)
                               {
                                   [app.nav popViewControllerAnimated:YES];
                                   NSLog(@"QQ授权登录成功");
                                   NSLog(@"name = %@",[userInfo nickname]);
                                   NSLog(@"icon = %@",[userInfo profileImage]);
                                   [CommonData sharedCommonData].isLogin = YES;
                                   [CommonData sharedCommonData].me.isCompany = NO;
                                   [CommonData sharedCommonData].me.userName = [userInfo nickname];
                                   [CommonData sharedCommonData].me.headImage = [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:[userInfo profileImage]]]];
                                   [self thirdPartLoginByType:0 uId:[userInfo uid] imageURL:[userInfo profileImage]]; //给后台传送信息
                               }else{
                                   NSLog(@"QQ授权失败！！");
                                   [self.waitBgView removeFromSuperview];
                               }
                           }];
}

////微信
//- (IBAction)WeChatLogin:(id)sender {
//    ShareType type = ShareTypeWeixiSession;
//    id<ISSAuthOptions> options = [ShareSDK authOptionsWithAutoAuth:YES
//                                                     allowCallback:YES
//                                                     authViewStyle:SSAuthViewStyleFullScreenPopup
//                                                      viewDelegate:nil
//                                           authManagerViewDelegate:nil];
//    
//    [ShareSDK getUserInfoWithType:type
//                      authOptions:options
//                           result:^(BOOL result,id<ISSPlatformUser>userInfo,id<ICMErrorInfo>error){
//                               
//                           }];
//    [ShareSDK cancelAuthWithType:ShareTypeSinaWeibo];
//
//}

//新浪微博
- (IBAction)SinaWeboLogin:(id)sender {
    [self.view addSubview:self.waitBgView]; //等待背景
    ShareType type = ShareTypeSinaWeibo;
    id<ISSAuthOptions> options = [ShareSDK authOptionsWithAutoAuth:YES
                                                     allowCallback:YES
                                                     authViewStyle:SSAuthViewStyleFullScreenPopup
                                                      viewDelegate:nil
                                           authManagerViewDelegate:nil];
    
    [ShareSDK getUserInfoWithType:type
                      authOptions:options
                           result:^(BOOL result,id<ISSPlatformUser>userInfo,id<ICMErrorInfo>error){
                               NSLog(@"%@",userInfo);
                               if (result)
                               {
                                   [app.nav popViewControllerAnimated:YES];
                                   NSLog(@"新浪微博授权成功");
                                   NSLog(@"name:%@",[userInfo nickname]);
                                   NSLog(@"headImage:%@",[userInfo profileImage]);
                                   [CommonData sharedCommonData].isLogin = YES;
                                   [CommonData sharedCommonData].me.isCompany = NO;
                                   [CommonData sharedCommonData].me.userName = [userInfo nickname];
                                   [CommonData sharedCommonData].me.headImage = [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:[userInfo profileImage]]]];
                                   [self thirdPartLoginByType:2 uId:[userInfo uid] imageURL:[userInfo profileImage]];//给后台传送信息
                               }else{
                                   NSLog(@"新浪微博授权失败！！");
                                   [self.waitBgView removeFromSuperview];
                               }

                           }];

}

// 第三方登录信息传送到后台
-(void)thirdPartLoginByType:(int)type uId:(NSString*)uId imageURL:(NSString*)url{
    
    NSDictionary *param = @{@"type":[[NSString alloc] initWithFormat:@"%d",type], //QQ:0 weChat:1 webo:2
                            @"userName":uId,
                            @"image":url};
    [DataDAO getThirdPartLogin:param withCompleted:^(id result){
        NSLog(@"第三方登录后台传送成功！");
        NSDictionary *dic = (NSDictionary*)result;
        [CommonData sharedCommonData].me.myId = [dic objectForKey:@"userId"];
        [CommonData sharedCommonData].me.phoneNum = [dic objectForKey:@"phoneNum"];
    } withFailure:^(id error){
        NSLog(@"第三方登录后台传送失败，%@",error);
    }];
}

@end
