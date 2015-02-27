//
//  BaseVC.h
//  HighwayTollManage
//
//  Created by wanggp on 9/5/12.
//  Copyright (c) 2012 newsoft. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MBProgressHUD.h"
#import <Foundation/Foundation.h>

@class AppDelegate;

@interface BaseVC : UIViewController
{
    AppDelegate  *app;
    MBProgressHUD *hud;
    UIImageView *IndexView;
//    UIView* blackBG;
}

- (IBAction)goBack:(id)sender;
- (IBAction)dismissView:(id)sender;
- (void) hiddenNavigationBar:(BOOL)hidden;
- (void)showHUD:(NSString *)info;
- (void)showHUD:(NSString *)info:(NSString *)imageName:(BOOL)autoHidden;
- (void)showHUD:(UIView *)view:(NSString *)info:(NSString *)imageName:(BOOL)autoHidden;
- (void)hiddenHUD;
//
//-  (void)setSearchBarStyle:(UISearchBar *)searchBar:(NSString *)bg_image;
//- (void)setSearchBarFontStyle:(UISearchBar *)searchBar;
//- (void)setSearBar_CancelButtonToTitle:(UISearchBar *)searchBar:(NSString *)title;
//- (void)setSearBar_enableCancelButton:(UISearchBar *)searchBar:(BOOL)enabled;
////- (void)setSearchBar_TextFieldDelegate:(UISearchBar *)searchBar;
//- (void)setSearchBarBackgroundImage:(UISearchBar *)searchBar :(NSString *)bg_image;
//
//// 错误处理
//- (NSString *)processError:(NSDictionary *)dic;
//- (void)processError:(UIView *)view:(NSDictionary *)dic;
//
//- (void)presentOAViewController:(UIViewController *)vc;
//- (void)dismissOAViewController;

- (void) setSearchBarBackground:(UISearchBar *)_searchBar;
//初始化引导页
-(void)initIndexView:(NSString *)imageName;
////初始化黑布
//-(void)initBlackBGWithString:(NSString*)string;
@end
