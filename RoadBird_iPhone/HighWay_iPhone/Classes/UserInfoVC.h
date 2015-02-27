//
//  UserInfoVC.h
//  HighWay_iPhone
//
//  Created by litong on 15-1-13.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import "BaseVC.h"
#import "AFNetworking.h"

@interface UserInfoVC : BaseVC<UITableViewDataSource,UITableViewDelegate,UIImagePickerControllerDelegate,UIActionSheetDelegate,UITextFieldDelegate>{
    UIImagePickerController *imagePicker;
    BOOL isChangeInfo; //修改信息标识
    NSMutableArray* cellAry;
    // AFN的客户端，使用基本地址初始化，同时会实例化一个操作队列，以便于后续的多线程处理
    
}



@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UIView *headBg;
@property (weak, nonatomic) IBOutlet UIImageView *headImage;
@property (weak, nonatomic) IBOutlet UIImageView *largeHeadImage;
@property (weak, nonatomic) IBOutlet UIButton *logoutBtn;

@property (strong, nonatomic) IBOutlet UIView *largeHeadImageView;
@property (strong, nonatomic) IBOutlet UITapGestureRecognizer *headImageTap;
@property (strong, nonatomic) IBOutlet UITapGestureRecognizer *clearHeadImage;

- (IBAction)changeHeadImage:(id)sender;
- (IBAction)clicklLgout:(id)sender;
- (IBAction)clickHeadImage:(id)sender;
- (IBAction)clearHeadImageView:(id)sender;

@end
