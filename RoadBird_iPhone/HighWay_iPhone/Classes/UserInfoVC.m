//
//  UserInfoVC.m
//  HighWay_iPhone
//
//  Created by litong on 15-1-13.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import "UserInfoVC.h"
#import "UserInfoTableViewCell.h"
#import "ScoreShopTableViewCell.h"
#import "AppDelegate.h"
#import "Person.h"
#import "ScoreShopVC.h"
#import <ShareSDK/ShareSDK.h>

@interface UserInfoVC (){
    int cellCount;
}

@end

@implementation UserInfoVC

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"个人信息";
    
    // 头像
    [self.headBg.layer setCornerRadius:53];
    self.headBg.layer.masksToBounds = YES;
    [self.headImage.layer setCornerRadius:50];
    self.headImage.layer.masksToBounds = YES;
    [self.headBg addGestureRecognizer:self.headImageTap];
    
    self.logoutBtn.layer.borderColor = [RGBCOLOR(254, 68, 66) CGColor];
    self.logoutBtn.layer.borderWidth = 0.5f;
   
    UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(editInfo)];
    self.navigationItem.rightBarButtonItem = item;
    
    [self.largeHeadImageView setFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
    self.tableView.scrollEnabled = NO; //不让列表滑动
    
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    
    cellAry = [NSMutableArray new]; //装载能修改内容的单元
    cellCount = 1; //只有3个cell
    
    [self.tableView reloadData];
    if([CommonData sharedCommonData].me.headImage!=nil){ //显示头像
        self.headImage.image = [CommonData sharedCommonData].me.headImage;
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

// 保存信息按钮
-(void)editInfo{
    for (int i=0; i<[cellAry count]; i++) {
        UserInfoTableViewCell* cell = [cellAry objectAtIndex:i];
        switch (i) {
            case 0:
            {
                if (![cell.detail.text isEqualToString:@""]) {
                    [CommonData sharedCommonData].me.userName = cell.detail.text;
                }else{
                    [[[UIAlertView alloc] initWithTitle:@"提示" message:@"用户名不能为空" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
                    return;
                }
            }
                break;
            case 1:
            {
                if ([ComFun isPureInt:cell.detail.text]&&cell.detail.text.length==11) { //判断手机号是否输入正确
                    [CommonData sharedCommonData].me.phoneNum = cell.detail.text;
                }
                else{
                    [[[UIAlertView alloc] initWithTitle:@"提示" message:@"请正确填写11位手机号码" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
                    return;
                }
            }
                break;
            case 2:
                if ([CommonData sharedCommonData].me.isCompany) {
                    [CommonData sharedCommonData].me.companyName = cell.detail.text;
                }else{
                    [CommonData sharedCommonData].me.address = cell.detail.text;
                }
                break;
            default:
                [CommonData sharedCommonData].me.companyPersonName = cell.detail.text;
                break;
        }
    }
//    [[[UIAlertView alloc] initWithTitle:@"提示" message:@"修改用户信息成功！" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
    
    // 提交后台修改用户信息
    int type;
    NSDictionary *info, *info2;
    NSLog(@"[CommonData sharedCommonData].me.userName： %@",[CommonData sharedCommonData].me.userName);
    NSLog(@"[CommonData sharedCommonData].me.headImage： %@",[CommonData sharedCommonData].me.headImage);
    NSLog(@"[CommonData sharedCommonData].me.phoneNum： %@",[CommonData sharedCommonData].me.phoneNum);
    NSLog(@"[CommonData sharedCommonData].me.companyName： %@",[CommonData sharedCommonData].me.companyName);
    NSLog(@"[CommonData sharedCommonData].me.comPhone： %@",[CommonData sharedCommonData].me.comPhone);
    NSLog(@"[CommonData sharedCommonData].me.companyPersonName： %@",[CommonData sharedCommonData].me.companyPersonName);
    if ([CommonData sharedCommonData].me.isCompany) { //企业用户
        type=1;
        

        info = @{
                 @"phone":[CommonData sharedCommonData].me.phoneNum,
                 @"comPhone":[CommonData sharedCommonData].me.comPhone,
                 @"comPerson":[CommonData sharedCommonData].me.companyPersonName};
    }else{ //普通用户
        type=0;
        info = @{
                 @"phone":[CommonData sharedCommonData].me.phoneNum};
    }
    
    //info2 = @{@"image":[CommonData sharedCommonData].me.headImage};
    NSLog(@"info： %@",info);
   
    NSDictionary *param = @{@"userId":[CommonData sharedCommonData].me.myId,
                            @"type":[[NSString alloc] initWithFormat:@"%d",type],
                            @"info":info};
    
    // 可以在上传时使用当前的系统事件作为文件名
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    // 设置时间格式
    formatter.dateFormat = @"yyyyMMddHHmmss";
    NSString *str = [formatter stringFromDate:[NSDate date]];
    NSString *fileName = [NSString stringWithFormat:@"%@.png", str];
    NSDictionary *param2 = @{@"userId":[CommonData sharedCommonData].me.myId,
                            @"fileName":fileName};
    //头像上传
    [DataDAO postChangeUserImageInfo:param2 withCompleted:^(id result){
        NSLog(@"上传头像成功！");
    } withFailure:^(id error){
        NSLog(@"postChangeUserImageInfo 2： %@",error);
        [[[UIAlertView alloc] initWithTitle:@"提示" message:@"后台同步头像失败！" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
    }];
    
//    [DataDAO postChangeUserInfo:param withCompleted:^(id result){
//        NSLog(@"修改用户信息成功！");
//    } withFailure:^(id error){
//        NSLog(@"postChangeUserInfo： %@",error);
//        [[[UIAlertView alloc] initWithTitle:@"提示" message:@"后台同步用户信息失败！" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles: nil] show];
//    }];
}

#pragma mark - IBoutlet
// 更改头像
- (IBAction)changeHeadImage:(id)sender {
    imagePicker = [[UIImagePickerController alloc] init];
    imagePicker.delegate = self;
    
    UIActionSheet * actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"拍照",@"从手机相册选择",nil];
    [actionSheet showInView:self.view];
}

// 注销
- (IBAction)clicklLgout:(id)sender {
    [CommonData sharedCommonData].isLogin = NO;
    [[CommonData sharedCommonData].me clearMyInfo]; //清除个人信息
    
    //第三方取消授权
    [ShareSDK cancelAuthWithType:ShareTypeSinaWeibo];
    [ShareSDK cancelAuthWithType:ShareTypeQQSpace];
//    [ShareSDK cancelAuthWithType:ShareTypeWeixiSession];
    [app.nav popViewControllerAnimated:YES];
}

//点击头像
- (IBAction)clickHeadImage:(id)sender {
    self.largeHeadImage.image = self.headImage.image;
    [app.window addSubview:self.largeHeadImageView];
    [self.largeHeadImageView addGestureRecognizer:self.clearHeadImage];
    [UIView animateWithDuration:0.5 animations:^{
        self.largeHeadImageView.alpha=1;
    } completion:nil];
}

//清除大头像界面
- (IBAction)clearHeadImageView:(id)sender {
    [UIView animateWithDuration:0.3 animations:^{
        self.largeHeadImageView.alpha=0;
    } completion:^(BOOL finish){
        [self.largeHeadImageView removeFromSuperview];
    }];
}

#pragma mark - ActionSheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    
    switch (buttonIndex) {
        case 0: //相机
            imagePicker.sourceType = UIImagePickerControllerSourceTypeCamera;
            [self presentViewController:imagePicker animated:YES completion:nil];
            break;
        case 1: //图库
            imagePicker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
            [self presentViewController:imagePicker animated:YES completion:nil];
            break;
        default:
            break;
    }

}

#pragma mark - ImagePicker Delegate
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingImage:(UIImage *)image editingInfo:(NSDictionary *)editingInfo{
    self.headImage.image = image;
    [CommonData sharedCommonData].me.headImage = image;
    [picker dismissViewControllerAnimated:YES completion:nil];
}

#pragma mark - TextField Delegate
// 有新的信息修改
- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    [textField resignFirstResponder];
    textField.enabled = NO; //恢复不可修改状态
    
    if (ISIPHONE4S) {
        [UIView animateWithDuration:0.3 animations:^{
            CGAffineTransform pTransform = CGAffineTransformMakeTranslation(0, 0); //恢复
            self.view.transform = pTransform;
        } completion:nil];
    }
    return YES;
}

- (void)textFieldDidBeginEditing:(UITextField *)textField{
    if (ISIPHONE4S) {//3.5寸屏
        [UIView animateWithDuration:0.3 delay:0.1 options:UIViewAnimationOptionCurveEaseInOut animations:^{
            CGAffineTransform pTransform = CGAffineTransformMakeTranslation(0, -120); //仿射变换
            self.view.transform = pTransform;
        } completion:nil];
    }
}


#pragma mark - TableView Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 2;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 44;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    if (section==0) {
        return 1;
    }else{
        return 0;
    }
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (section==0) {
        if([CommonData sharedCommonData].me.isCompany){ //企业用户
            return 4;
        }else{
             return 3;
        }
    }else{
        return 1;
    }
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.section == 0) {
        return YES;
    }else
        return NO;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    if (indexPath.section==0) {
        UserInfoTableViewCell *userInfoTableViewCell = [[[NSBundle mainBundle] loadNibNamed:@"UserInfoTableViewCell" owner:nil options:nil] lastObject];
        
        if (![CommonData sharedCommonData].me.isCompany) { //个人用户
            switch (indexPath.row) {
                case 0:
                    userInfoTableViewCell.title.text = @"用户";
                    userInfoTableViewCell.detail.text = [CommonData sharedCommonData].me.userName;
                    break;
                case 1:
                {
                    userInfoTableViewCell.title.text = @"手机";
                    if ([CommonData sharedCommonData].me.phoneNum!=nil) {
                        userInfoTableViewCell.detail.text = [CommonData sharedCommonData].me.phoneNum;
                    }
                }
                    break;
                case 2:
                    userInfoTableViewCell.title.text = @"地址";
                    if ([CommonData sharedCommonData].me.address!=nil) {
                        userInfoTableViewCell.detail.text = [CommonData sharedCommonData].me.address;
                    }
                    break;
            }
            userInfoTableViewCell.detail.enabled = NO; //不可修改内容
            userInfoTableViewCell.detail.delegate = self; //设置代理
            if (cellCount<=3) {
                [cellAry addObject:userInfoTableViewCell]; //保持只有3个cell
                cellCount++;
            }

        }
        else{ //企业用户
            switch (indexPath.row) {
                case 0:
                    userInfoTableViewCell.title.text = @"用户";
                    userInfoTableViewCell.detail.text = [CommonData sharedCommonData].me.userName;
                    break;
                case 1:
                {
                    userInfoTableViewCell.title.text = @"电话";
                    if ([CommonData sharedCommonData].me.phoneNum!=nil) {
                        userInfoTableViewCell.detail.text = [CommonData sharedCommonData].me.phoneNum;
                    }
                }
                 break;
                case 2:
                    userInfoTableViewCell.title.text = @"企业";
                    if ([CommonData sharedCommonData].me.companyName!=nil) {
                        userInfoTableViewCell.detail.text = [CommonData sharedCommonData].me.companyName;
                        
                    }
                    break;
                case 3:
                    userInfoTableViewCell.title.text = @"联系人";
                    if ([CommonData sharedCommonData].me.companyPersonName!=nil) {
                        userInfoTableViewCell.detail.text = [CommonData sharedCommonData].me.companyPersonName;
                    }
                    break;
            }
            userInfoTableViewCell.detail.enabled = NO; //不可修改内容
            userInfoTableViewCell.detail.delegate = self; //设置代理
            if (cellCount<=4) {
                [cellAry addObject:userInfoTableViewCell]; //保持只有4个cell
                cellCount++;
            }
        }
        
        if ([userInfoTableViewCell.detail.text isEqualToString:@""]) { //某些信息没有填写完整
             userInfoTableViewCell.detail.placeholder = @"滑动填写";
        }
        return userInfoTableViewCell;
    
    }else{
        
        ScoreShopTableViewCell* scoreShopTableViewCell = [[[NSBundle mainBundle] loadNibNamed:@"ScoreShopTableViewCell" owner:nil options:nil]lastObject];
//        cell.scoreLabel.text = [[NSString alloc] initWithFormat:@"  %@  ",[CommonData sharedCommonData].allScore];
        scoreShopTableViewCell.scoreLabel.text = [[NSString alloc] initWithFormat:@" %@ ", [CommonData sharedCommonData].allScore];
        return scoreShopTableViewCell;
    }
    
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    //取消选择效果
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section==1) {
        ScoreShopVC *scoreShopVC = [[ScoreShopVC alloc] init];
        [app.nav pushViewController:scoreShopVC animated:YES];
    }
}

-(void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath{
}

// 滑动修改
- (NSArray *)tableView:(UITableView *)tableView editActionsForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewRowAction *changeRowAction = [UITableViewRowAction rowActionWithStyle:UITableViewRowActionStyleDefault title:@"修改" handler:^(UITableViewRowAction *action,NSIndexPath *indexPath)
    {
        UserInfoTableViewCell *cell = [cellAry objectAtIndex:indexPath.row];
        cell.detail.enabled = YES;
        [cell.detail becomeFirstResponder];
    }];
    changeRowAction.backgroundColor = RGBCOLOR(38, 178, 98);
    return @[changeRowAction];
}

@end
