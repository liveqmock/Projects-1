//
//  BaseVC.m
//  HighwayTollManage
//
//  Created by wanggp on 9/5/12.
//  Copyright (c) 2012 newsoft. All rights reserved.
//

#import "BaseVC.h"
#import "AppDelegate.h"
//#import "ComFun.h"
//#import "OARootVC.h"


@implementation BaseVC



- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.navigationItem setHidesBackButton:NO];
	 app = (AppDelegate *)[[UIApplication sharedApplication] delegate];
    [[UINavigationBar appearance] setTintColor:NAV_GREEN_FONT_COLOR];
    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc]
                                              initWithTitle:@"返回"
                                              style:UIBarButtonItemStylePlain
                                              target:self
                                              action:nil] ;
    
    
  
    if(IOS_VERSION>=7.0){
        [app.nav.navigationBar setBarTintColor:NAVBACKGROUNDCOLOR];
        NSDictionary *dictText = [NSDictionary dictionaryWithObjectsAndKeys:
                                  NAV_GREEN_FONT_COLOR, NSForegroundColorAttributeName,nil] ;
        [app.nav.navigationBar setTitleTextAttributes:dictText];
        
        UIView *navBorder = [[UIView alloc] initWithFrame:CGRectMake(0,app.nav.navigationBar.frame.size.height,app.nav.navigationBar.frame.size.width, 0.5f)];
        // Change the frame size to suit yours //
        [navBorder setBackgroundColor:NAV_GREEN_FONT_COLOR];
        [navBorder setOpaque:YES];
        [app.nav.navigationBar addSubview:navBorder];
    }

}


//初始化引导页
-(void)initIndexView:(NSString *)imageName
{
    IndexView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 568)];
    //更多页适配3.5寸
    if (ISIPHONE4S && [imageName isEqualToString:@"More-Index"]) {
        IndexView.frame = CGRectMake(0, 480-568, SCREEN_WIDTH, 568);
    }
    IndexView.image = [UIImage imageNamed:imageName];
    IndexView.userInteractionEnabled = YES;
    UITapGestureRecognizer *singleTap =[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(IndexButton)];
    [IndexView addGestureRecognizer:singleTap];
    [app.window addSubview:IndexView];
    //淡入
    IndexView.alpha = 0.1;
    [UIView beginAnimations:@"animations" context:nil];
    [UIView setAnimationDuration:0.3];
    IndexView.alpha = 1;
    [UIView commitAnimations];

}

//消除引导页时
-(void)IndexButton
{
    //淡出
    [UIView beginAnimations:@"animations" context:nil];
    [UIView setAnimationDuration:0.5];
    IndexView.alpha = 0;
    [UIView commitAnimations];
    IndexView = nil;
    //发送引导页消失通知
    [[NSNotificationCenter defaultCenter]postNotificationName:@"IndexViewGone" object:nil];
}


-(BOOL) shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation{
    // 只支持竖屏
    if(interfaceOrientation==UIInterfaceOrientationPortrait)
        return YES;
    else
        return NO;
}



- (IBAction)goBack:(id)sender{
	[app.nav popViewControllerAnimated:YES];
}

- (IBAction)dismissView:(id)sender
{
    [self.navigationController dismissViewControllerAnimated:YES completion:nil];
}


-(void) hiddenNavigationBar:(BOOL)hidden
{
    [app.nav setNavigationBarHidden:hidden animated:TRUE];
}

- (void)showHUD:(NSString *)info
{
    if([NSString isEmpty:info])
        return;
    hud = [[MBProgressHUD alloc] initWithView:app.nav.view];
	[app.nav.view addSubview:hud];
    hud.mode = MBProgressHUDModeText;
    hud.labelText = info;
    int length=[ComFun strLength:info];
    if(length>15)
        hud.labelFont=[UIFont systemFontOfSize:11.0f];
    hud.removeFromSuperViewOnHide=YES;
    [hud show:YES];
    [hud hide:YES afterDelay:2];
}

- (void)showHUD:(NSString *)info:(NSString *)imageName:(BOOL)autoHidden
{
    [self showHUD:app.nav.view :info :imageName  :autoHidden];
}



- (void)showHUD:(UIView *)view:(NSString *)info:(NSString *)imageName:(BOOL)autoHidden
{
    if([NSString isEmpty:info])
        return;
    if(view)
        hud=[MBProgressHUD showHUDAddedTo:view animated:YES] ;
    else
        hud=[MBProgressHUD showHUDAddedTo:app.nav.view animated:YES] ;
    if([NSString isNotEmpty:imageName]){
        hud.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:imageName]];
        hud.mode = MBProgressHUDModeCustomView;
    }
    hud.labelText =info;
    int length=[ ComFun strLength:info];
    if(length>15)
        hud.labelFont=[UIFont systemFontOfSize:11.0f];
    hud.removeFromSuperViewOnHide=YES;
    [hud show:YES];
    if(autoHidden){
        [hud hide:YES afterDelay:2];
    }
}




- (void)hiddenHUD
{
    if(hud){
        [hud hide:YES];
    }
}


- (void) setSearchBarBackground:(UISearchBar *)_searchBar
{
    float version = IOS_VERSION;
    if ([_searchBar respondsToSelector:@selector(barTintColor)]) {
        float iosversion7_1 = 7.1;
        if (version >= iosversion7_1)
        {
            //iOS7.1
            [[[[_searchBar.subviews objectAtIndex:0] subviews] objectAtIndex:0] removeFromSuperview];
            [_searchBar setBackgroundColor:[UIColor clearColor]];
        }
        else
        {
            //iOS7.0
            [_searchBar setBarTintColor:[UIColor clearColor]];
            [_searchBar setBackgroundColor:[UIColor clearColor]];
        }
    }
    else
    {
        //iOS7.0以下
        [[_searchBar.subviews objectAtIndex:0] removeFromSuperview];
        [_searchBar setBackgroundColor:[UIColor clearColor]];
    }
}
//-  (void)setSearchBarStyle:(UISearchBar *)searchBar:(NSString *)bg_image
//{
//    if(searchBar==nil||StrIsNull(bg_image))
//        return;
////    [[searchBar.subviews objectAtIndex:0] setHidden:YES];
////    [[searchBar.subviews objectAtIndex:0] removeFromSuperview];
//    for (UIView *subview in searchBar.subviews)
//    {
////          NSLog(@"%@",[subview description]);
//        if ([subview isKindOfClass:NSClassFromString(@"UISearchBarBackground")])
//        {
//            [subview removeFromSuperview];
//            break;
//        }
//    }
//    
////    searchBar.text
////    UITextField *searchField=nil;
////    NSUInteger numViews = [searchBar.subviews count];
////    for(int i = 0; i < numViews; i++) {
////        
////        if([[searchBar.subviews objectAtIndex:i] isKindOfClass:[UITextField class]]) {
////            searchField = [searchBar.subviews objectAtIndex:i];
////        }
////    }
//    
//    UITextField *searchField = [searchBar valueForKey:@"_searchField"];
//    if(searchField) {
//        searchField.textColor = [UIColor whiteColor];
//        [searchField setBackground: [UIImage imageNamed:bg_image] ];
//        [searchField setBorderStyle:UITextBorderStyleNone];
//    }
//    
//    
//    searchBar.backgroundImage =[UIImage imageNamed:@"transparent.png"];
////    searchBar.backgroundColor=[UIColor clearColor];
//}
//
//- (void)setSearchBarBackgroundImage:(UISearchBar *)searchBar :(NSString *)bg_image {
//    
//    UITextField *searchField = [searchBar valueForKey:@"_searchField"];
//    if(searchField)
//        [searchField setBackground: [UIImage imageNamed:bg_image] ];
////    for(int i = 0; i < [searchBar.subviews count]; i++) {
////        if([[searchBar.subviews objectAtIndex:i] isKindOfClass:[UITextField class]]) {
////            UITextField *searchField = [searchBar.subviews objectAtIndex:i];
////            [searchField setBackground: [UIImage imageNamed:bg_image] ];
////            return;
////        }
////    }
//}
//- (void)setSearchBarFontStyle:(UISearchBar *)searchBar
//{
//    UITextField *searchField=nil;
//    searchField = [searchBar valueForKey:@"_searchField"];
////    NSUInteger numViews = [searchBar.subviews count];
////    for(int i = 0; i < numViews; i++) {
////        if([[searchBar.subviews objectAtIndex:i] isKindOfClass:[UITextField class]]) {
////            searchField = [searchBar.subviews objectAtIndex:i];
////        }
////    }
//    if(searchField) {
//        searchField.textColor = [UIColor whiteColor];
//    }
//    
//}
//
//- (void)setSearBar_CancelButtonToTitle:(UISearchBar *)searchBar:(NSString *)title {
//    for(id cc in [searchBar subviews])
//    {
//
//        if([cc isKindOfClass:[UIButton class]])
//        {
//            UIButton *btn = (UIButton *)cc;
//            [btn setTitle:title  forState:UIControlStateNormal];
//        }
//    }
//}
//- (void)setSearBar_enableCancelButton:(UISearchBar *)searchBar:(BOOL)enabled {
//    for (UIView *view in searchBar.subviews) {
//               if ([view isKindOfClass:[UIButton class]]) {
//            [(UIButton *)view setEnabled:enabled];
//        }
//    }
//}
//
////- (void)setSearchBar_TextFieldDelegate:(UISearchBar *)searchBar{
////    for (UIView* v in searchBar.subviews)
////    {
////        if ( [v isKindOfClass: [UITextField class]] )
////        {
////            UITextField *tf = (UITextField *)v;
////            tf.delegate = self;
////            break;
////        }
////    }
////}
//
//
//- (void)processError:(UIView *)view:(NSDictionary *)dic {
//    
//    BOOL suc = [[dic valueForKey:@"success"] boolValue];
//    if (suc) {
//        return;
//    }
//    NSString *msg = [dic objectForKey:@"msg"];
//    int cmd = [[dic objectForKey:@"cmd"] integerValue];
//    NSLog(@"OA responded with an error: %@", msg);
//    NSString *errorDetail;
//    if(StrIsNull(msg)) {
//       errorDetail  =@"连接服务器失败！";
//    }
//    else if([msg isEqualToString:@"101"]){
//        if(StrIsNull(app.oaCommon.sessionID)){
//            errorDetail  =@"登录失败！帐号或者密码错误。";
//        }
//        else{
//            if (app.oaRootVC&&app.oaRootVC.heartBeatTimer) {
//                [app.oaRootVC.heartBeatTimer invalidate];
//                app.oaRootVC.heartBeatTimer=nil;
//            }
//           errorDetail  =@"登录信息已过期，请注销后重新登录。";
//            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示"
//                                                            message:errorDetail
//                                                           delegate:app.oaRootVC
//                                                  cancelButtonTitle:@"取消" 
//                                                  otherButtonTitles: @"注销", nil];
//            [alert show];
//          
//            
//            return;
//            
//        }
//    }else if([msg isEqualToString:@"102"]){
//        errorDetail  =@"请填写OA服务器";
//    }else if([msg isEqualToString:@"103"]){
//        errorDetail  =@"登录人数已满，请稍候再登录。";
//    }else if([msg isEqualToString:@"104"]){
//        errorDetail  =@"服务器授权信息过期。";
//    }else if([msg isEqualToString:@"105"]){
//        errorDetail  =@"OA服务器或者账户未授权。";
//    }else if([msg isEqualToString:@"106"]){
//        errorDetail  =@"帐号已被冻结，请联系管理员。";
//    }else if([msg isEqualToString:@"107"]){
//        errorDetail  =@"手机已经和帐号绑定，您不能用该手机登录当前帐号。";
//    }else if([msg isEqualToString:@"108"]){
//        errorDetail  =@"连接OA服务器失败。";
//    }else if([msg isEqualToString:@"109"]){
//        errorDetail  =@"OA服务器名称错误。";
//    }else if([msg isEqualToString:@"110"]){
//        errorDetail  =@"您的帐号未开通移动OA。";
//    }else if([msg isEqualToString:@"201"]){
//        errorDetail  =@"查询发生未知错误。";
//    }else if([msg isEqualToString:@"202"]){
//        errorDetail  =@"您没有权限执行该操作。";
//    }else if([msg isEqualToString:@"203"]){
//        errorDetail  =@"找不到指定的文件。";
//    }else if([msg isEqualToString:@"204"]){
//        if(cmd==27 || cmd==25 || cmd==18) //send,delete,commit doc
//        {
//            errorDetail  =@"提交失败，可能是因为网络阻塞，请再次重复您刚才的操作。";
//        }
//        else{
//            errorDetail  =@"参数错误。";
//        }
//    }else if([msg isEqualToString:@"205"]){
//        errorDetail  =@"XML文件错误。";
//    }else if([msg isEqualToString:@"301"]){
//        errorDetail  =@"公文已被删除或者其他人处理过。";
//    }else if([msg isEqualToString:@"302"]){
//        errorDetail  =@"敏感流程,请登录web OA处理!";
//    }else if([msg isEqualToString:@"401"]){
//        errorDetail  =@"邮件不存在或者已被删除。";
//    }else if([msg isEqualToString:@"402"]){
//        errorDetail  =@"附件不存在或者没有权限。";
//    }else if([msg isEqualToString:@"403"]){
//        errorDetail  =@"邮件发送失败。";
//    }else if([msg isEqualToString:@"404"]){
//        errorDetail  =@"请输入有效的邮箱地址!";
//    }else if([msg isEqualToString:@"701"]){
//        errorDetail  =@"删除个人委托失败!";
//    }else if([msg isEqualToString:@"801"]){
//        errorDetail  =@"附件不存在或者没有权限!";
//    }
//    else if([msg isEqualToString:@"0"]){
//        errorDetail  =@"连接异常，可能是网络堵塞，您可以重试一下。";   //未知错误
//    }
//    else if(StrIsNotNUll(msg)){
//        errorDetail=msg;
//    }
//    if(view)
//        [self showHUD:view :errorDetail :FAILEDICON  :YES];
//    else
//        [self showHUD:app.nav.view :errorDetail :FAILEDICON  :YES];
//}
//
//- (NSString *)processError:(NSDictionary *)dic {
//    
//    BOOL suc = [[dic valueForKey:@"success"] boolValue];
// 
//    if (suc) {
//    
//        return nil;
//    }
//    NSString *msg = [dic objectForKey:@"msg"];
//    int cmd = [[dic objectForKey:@"cmd"] integerValue];
//    NSLog(@"OA responded with an error: %@", msg);
//    NSString *errorDetail=@"";
//    if(StrIsNull(msg)) {
//        errorDetail  =@"连接服务器失败！";
//    }
//    else if([msg isEqualToString:@"101"]){
//        if(StrIsNull(app.oaCommon.sessionID)){
//            errorDetail  =@"登录失败！帐号或者密码错误。";
//        }
//        else{
//            if (app.oaRootVC&&app.oaRootVC.heartBeatTimer) {
//                [app.oaRootVC.heartBeatTimer invalidate];
//                app.oaRootVC.heartBeatTimer=nil;
//            }
//            errorDetail  =@"登录信息已过期，请注销后重新登录。";
//            
//        }
//    }else if([msg isEqualToString:@"102"]){
//        errorDetail  =@"请填写OA服务器";
//    }else if([msg isEqualToString:@"103"]){
//        errorDetail  =@"登录人数已满，请稍候再登录。";
//    }else if([msg isEqualToString:@"104"]){
//        errorDetail  =@"服务器授权信息过期。";
//    }else if([msg isEqualToString:@"105"]){
//        errorDetail  =@"OA服务器或者账户未授权。";
//    }else if([msg isEqualToString:@"106"]){
//        errorDetail  =@"帐号已被冻结，请联系管理员。";
//    }else if([msg isEqualToString:@"107"]){
//        errorDetail  =@"手机已经和帐号绑定，您不能用该手机登录当前帐号。";
//    }else if([msg isEqualToString:@"108"]){
//        errorDetail  =@"连接OA服务器失败。";
//    }else if([msg isEqualToString:@"109"]){
//        errorDetail  =@"OA服务器名称错误。";
//    }else if([msg isEqualToString:@"110"]){
//        errorDetail  =@"您的帐号未开通移动OA。";
//    }else if([msg isEqualToString:@"201"]){
//        errorDetail  =@"查询发生未知错误。";
//    }else if([msg isEqualToString:@"202"]){
//        errorDetail  =@"您没有权限执行该操作。";
//    }else if([msg isEqualToString:@"203"]){
//        errorDetail  =@"找不到指定的文件。";
//    }else if([msg isEqualToString:@"204"]){
//        if(cmd==27 || cmd==25 || cmd==18) //send,delete,commit doc
//        {
//            errorDetail  =@"提交失败，可能是因为网络阻塞，请再次重复您刚才的操作。";
//        }
//        else{
//            errorDetail  =@"参数错误。";
//        }
//    }else if([msg isEqualToString:@"205"]){
//        errorDetail  =@"XML文件错误。";
//    }else if([msg isEqualToString:@"301"]){
//        errorDetail  =@"公文已被删除或者其他人处理过。";
//    }else if([msg isEqualToString:@"302"]){
//        errorDetail  =@"敏感流程,请登录web OA处理!";
//    }else if([msg isEqualToString:@"401"]){
//        errorDetail  =@"邮件不存在或者已被删除。";
//    }else if([msg isEqualToString:@"402"]){
//        errorDetail  =@"附件不存在或者没有权限。";
//    }else if([msg isEqualToString:@"403"]){
//        errorDetail  =@"邮件发送失败。";
//    }else if([msg isEqualToString:@"404"]){
//        errorDetail  =@"请输入有效的邮箱地址!";
//    }else if([msg isEqualToString:@"701"]){
//        errorDetail  =@"删除个人委托失败!";
//    }else if([msg isEqualToString:@"801"]){
//        errorDetail  =@"附件不存在或者没有权限!";
//    }
//    else if([msg isEqualToString:@"0"]){
//        errorDetail  =@"连接异常，可能是网络堵塞，您可以重试一下。";   //未知错误
//    }
//    else if(StrIsNotNUll(msg)){
//        errorDetail=msg;
//    }
//    return  errorDetail;
//}
//
//- (UIStatusBarStyle)preferredStatusBarStyle
//{
//    return UIStatusBarStyleLightContent;
//}
//- (BOOL)prefersStatusBarHidden
//{
//    return NO;
//}
//
//- (void)presentOAViewController:(UIViewController *)vc {
//    CATransition *animation = [CATransition animation];
//    //animation.delegate = self;
//    animation.duration = 0.3f;       //动画执行时间
//    animation.timingFunction = UIViewAnimationCurveEaseInOut;
//    animation.type = kCATransitionMoveIn;
//    animation.subtype = kCATransitionFromTop;
//    [app.nav pushViewController:vc animated:NO];
//    [[app.nav.view layer] addAnimation:animation forKey:@"animation"];
//}
//
//- (void)dismissOAViewController {
//    CATransition *animation = [CATransition animation];
//    //animation.delegate = self;
//    animation.duration = 0.3f;       //动画执行时间
//    animation.timingFunction = UIViewAnimationCurveEaseInOut;
//    animation.type = kCATransitionReveal;
//    animation.subtype = kCATransitionFromBottom;
//    [app.nav popViewControllerAnimated:NO];
//    [[app.nav.view layer] addAnimation:animation forKey:@"animation"];
//}
@end
