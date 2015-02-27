//
//  ScoreShopVC.m
//  HighWay_iPhone
//
//  Created by litong on 15-1-30.
//  Copyright (c) 2015年 lt. All rights reserved.
//

#import "ScoreShopVC.h"
#import "Person.h"
#import "ScoreRuleVC.h"
#import "AppDelegate.h"
#import "GoodsTableViewCell.h"

@interface ScoreShopVC ()

@end

@implementation ScoreShopVC

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"积分商城";
    
    // 头像
    self.headBgView.layer.cornerRadius = 41;
    self.headBgView.layer.masksToBounds = YES;
    self.headImageView.layer.cornerRadius = 39;
    self.headImageView.layer.masksToBounds = YES;
    [self.headImageView setImage:[CommonData sharedCommonData].me.headImage];
    
    self.scoreLabel.layer.cornerRadius = 8;
    self.scoreLabel.layer.masksToBounds = YES;

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];

}

#pragma mark - IBAction
- (IBAction)clickScoreHistory:(id)sender {
}

- (IBAction)clickScoreRule:(id)sender {
    ScoreRuleVC* scoreRuleVC = [[ScoreRuleVC alloc] init];
    [app.nav pushViewController:scoreRuleVC animated:YES];
}

#pragma mark - tableView Delegate

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.section==0) {
        return 100.0f;
    }else{
        return 64.0f;
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    if (section==0) {
        return 25.0f;
    }else{
        return 8.0f;
    }
    
}
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    if (section==0) {
        return 17.0f;
    }else{
       return 1.0f;
    }
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 6;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.section==0) {
        UITableViewCell *cell = [[UITableViewCell alloc] init];
        [self.headView setFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
        [cell.contentView addSubview:self.headView];
        return cell;
    }else{
        
        NSString* identify = @"GoodsTableViewCell";
        GoodsTableViewCell *goodTableViewCell = (GoodsTableViewCell*)[tableView dequeueReusableCellWithIdentifier:identify];
        if (goodTableViewCell==nil) {
            goodTableViewCell = [[[NSBundle mainBundle] loadNibNamed:identify owner:nil options:nil] lastObject];
        }
        return goodTableViewCell;
        
    }
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}


@end
