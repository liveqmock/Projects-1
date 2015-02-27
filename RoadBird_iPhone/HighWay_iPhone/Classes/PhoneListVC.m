//
//  PhoneListVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-16.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "PhoneListVC.h"
#import "PhoneTableViewCell.h"


@implementation PhoneListVC


- (void)viewDidLoad
{
    [super viewDidLoad];
    self.navigationItem.title=@"常用电话";
    
    //加载电话号码列表
    NSDictionary *dic= [JSONUtil readJSON:@"Phone.json"];
    self.lstData=[dic objectForKey:@"data"];
    [self.tableView reloadData];
}




#pragma mark -
#pragma mark Table delegation

// Return how many rows in the table
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.lstData count];
}

// Return a cell for the ith row
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"PhoneTableViewCell";
    PhoneTableViewCell *cell =[tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if(cell == nil) {
        NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:cellIdentifier owner:nil options:nil];
        for(id currentObject in topLevelObjects)
        {
            if([currentObject isKindOfClass:[PhoneTableViewCell class]])
            {
                cell = (PhoneTableViewCell *)currentObject;
                cell.selectionStyle=UITableViewCellSelectionStyleNone;
                cell.nameLabel.textColor = RGBCOLOR(102, 102, 102);
                break;
            }
        }
    }

    NSInteger row = indexPath.row;
    NSDictionary *data = [self.lstData objectAtIndex:row];
    cell.nameLabel.text=[data objectForKey:@"remark"];
    cell.phoneLabel.text=[data objectForKey:@"phonenumber"];
    cell.phoneBtn.tag=row;
    [cell.phoneBtn addTarget:self action:@selector(makeCall:) forControlEvents:UIControlEventTouchDown];
    return  cell;
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)newIndexPath
{
    // 选取效果
    [tableView deselectRowAtIndexPath:newIndexPath animated:NO];
    
}

#pragma  mark custom method
-(void)makeCall:(id)sender
{
    
    UIButton *btn =sender;
    NSInteger tag =btn.tag;
    NSDictionary *data = [self.lstData objectAtIndex:tag];
    NSString *phonenumber = [data valueForKey:@"phonenumber"];
    [ ComFun alertWithMessageAndDelegateAndTitle:@"":phonenumber:self:@"取消":@"呼叫"];

    
}

#pragma AlertDelegate

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
	[alertView dismissWithClickedButtonIndex:0 animated:YES];
	if(buttonIndex==1){ //ok
        NSString *number=alertView.message;
        if([NSString isNotEmpty:number])
            [PhoneUtil makeCall:number];
        else
             [self showHUD:@"电话号码为空" :FAILEDICON :YES];
	}
}







@end
