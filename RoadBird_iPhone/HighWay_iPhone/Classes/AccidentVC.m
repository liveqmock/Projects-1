//
//  AccidentVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/13.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "AccidentVC.h"
#import "AppDelegate.h"
#import "NewsInfoVC.h"
#import "NewsTableViewCell.h"


@implementation AccidentVC

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.automaticallyAdjustsScrollViewInsets=NO;
    // Do any additional setup after loading the view from its nib.
    [self loadData:[NSDate formateDate:[NSDate date] fromate:@"yyyy-MM-dd HH:mm:ss"]];
    self.title = @"交通事故";

}


- (void)loadData:(NSString *)queryTime
{
    //一次查询90条信息
    int pageSize =90;
    NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:1],@"type",[NSNumber numberWithInt:pageSize],@"pageSize",queryTime,@"queryTime",nil];
    [self showHUD:@"请求数据中.."];
    [DataDAO getTrafficinfo:param withCompleted:^(id result) {
        [self hiddenHUD];
        _accidentArray = [[NSMutableArray alloc] initWithArray:result];
        [_tableView reloadData];
    } withFailure:^(id error) {
        [self showHUD:error :FAILEDICON :YES];
    }];
    
}


#pragma mark  Table View Delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _accidentArray.count;
    
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 69.0f;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSInteger row = indexPath.row;
    NSDictionary *rowData=[_accidentArray objectAtIndex:row];
    static NSString *cellIdentifier = @"NewsTableViewCell";
    NewsTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if(cell == nil) {
        NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:cellIdentifier owner:nil options:nil];
        for(id currentObject in topLevelObjects)
        {
            if([currentObject isKindOfClass:[NewsTableViewCell class]])
            {
                cell = (NewsTableViewCell *)currentObject;
                cell.selectionStyle=UITableViewCellSelectionStyleNone;
                cell.mainLabel.textColor = RGBCOLOR(102, 102, 102);
                break;
            }
        }
        
    }
    NSString* remark = [rowData objectForKey:@"remark"];
    
    //获得路标识号
    NSString* roadNumString;

    for (int i=0; i<remark.length; ++i)
    {
        NSRange range = NSMakeRange(i, 1);
        NSString *subString = [remark substringWithRange:range];
        const char *cString = [subString UTF8String];
        if (strlen(cString) == 3)
        {
            roadNumString = [remark substringToIndex:i];
            break;
        }
    }
    cell.mainLabel.text = remark;
    cell.subLabel.text = [rowData objectForKey:@"occurTime"];
    
    //设置粗体字,调整frame
    CGSize labelSize = [roadNumString sizeWithAttributes:@{NSFontAttributeName:[UIFont boldSystemFontOfSize:17]}];
    [cell setBoldLabelSize:labelSize withString:roadNumString];
    

    return  cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    NSDictionary *rowData=nil;
    int row =(int) indexPath.row;
    if([_accidentArray count]>row)
        rowData=[_accidentArray objectAtIndex:row];
    
    
    if(rowData)
    {
        NewsInfoVC *newsInfoVC = [[NewsInfoVC alloc] init];
        newsInfoVC.type=1;
        newsInfoVC.dataDic=rowData;
        [app.nav pushViewController:newsInfoVC animated:YES];
    }
    // 取消选中效果
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    
}

- (NSIndexPath *)tableView:(UITableView *)tableView willSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (0 >= _accidentArray.count) {
        return nil;
    } else {
        return indexPath;
    }
}

//-(BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    if ([_accidentArray count]) {
//        return YES;
//    }
//    else
//        return NO;
//}
//
//-(void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    if (editingStyle == UITableViewCellEditingStyleDelete) {
//        NSLog(@"%d", indexPath.row);
//        [_accidentArray removeObjectAtIndex:[indexPath row]];
//        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObjects:indexPath, nil] withRowAnimation:UITableViewRowAnimationTop];
//    }
//}
//
//- (NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:(NSIndexPath *)indexPath{
//    return @"删除";
//}




@end
