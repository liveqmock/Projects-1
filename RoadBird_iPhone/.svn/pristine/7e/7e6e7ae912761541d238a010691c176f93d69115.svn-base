//
//  ConstructVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/13.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "ConstructVC.h"
#import "AppDelegate.h"
#import "NewsInfoVC.h"
#import "NewsTableViewCell.h"



@implementation ConstructVC



- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [self loadData:[NSDate formateDate:[NSDate date] fromate:@"yyyy-MM-dd HH:mm:ss"]];
}

- (void)loadData:(NSString *)queryTime
{
    
    int pageSize =90;
    NSDictionary *param=[[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:2],@"type",[NSNumber numberWithInt:pageSize],@"pageSize",queryTime,@"queryTime",nil];
    
    [DataDAO getTrafficinfo:param withCompleted:^(id result) {
        _constructArray = [[NSMutableArray alloc] initWithArray:result];
        [_tableView reloadData];
    } withFailure:^(id error) {
        [self showHUD:error :FAILEDICON :YES];
    }];
    
}


#pragma mark  Table View Delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _constructArray.count;
    
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 69.0f;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSInteger row = indexPath.row;
    NSDictionary *rowData=[_constructArray objectAtIndex:row];
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
    cell.subLabel.text = [rowData objectForKey:@"occurTime"];
    //去除前面那段时间
    NSArray *tipTextAry = [[rowData objectForKey:@"remark"] componentsSeparatedByString:@"，"];
    if (tipTextAry.count==1) {
        tipTextAry = [[rowData objectForKey:@"remark"] componentsSeparatedByString:@","];
    }
    NSString *tipText = @"";
    for (int i=1; i<tipTextAry.count; i++) {
        NSString *string = [tipTextAry objectAtIndex:i];
        tipText = [tipText stringByAppendingPathComponent:string];
    }
    cell.mainLabel.text = tipText;
    
    
    //获得路标识号
    NSString* roadNumString;
    for (int i=0; i<tipText.length; ++i)
    {
        NSRange range = NSMakeRange(i, 1);
        NSString *subString = [tipText substringWithRange:range];
        const char *cString = [subString UTF8String];
        if (strlen(cString) == 3)
        {
            roadNumString = [tipText substringToIndex:i];
            break;
        }
    }
    //设置粗体字,调整frame
    CGSize labelSize = [roadNumString sizeWithAttributes:@{NSFontAttributeName:[UIFont boldSystemFontOfSize:17]}];
    [cell setBoldLabelSize:labelSize withString:roadNumString];

    return  cell;
    
    
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    NSDictionary *rowData=nil;
    int row = (int)indexPath.row;
    if([_constructArray count]>row)
        rowData=[_constructArray objectAtIndex:row];
    else
        return;
    if(rowData)
    {
        NewsInfoVC *newsInfoVC = [[NewsInfoVC alloc] init];
        newsInfoVC.type=2;
        newsInfoVC.dataDic=rowData;
        [app.nav pushViewController:newsInfoVC animated:YES];
    }
    // 取消选中效果
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    
}

- (NSIndexPath *)tableView:(UITableView *)tableView willSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (0 >= _constructArray.count) {
        return nil;
    } else {
        return indexPath;
    }
}

//-(BOOL) tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    if ([_constructArray count]) {
//        return YES;
//    }else
//    {
//        return NO;
//    }
//}
//
//-(void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    if (editingStyle == UITableViewCellEditingStyleDelete) {
//        [_constructArray removeObjectAtIndex:[indexPath row]];
//        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
//    }
//}
//
//- (NSString *)tableView:(UITableView *)tableView titleForDeleteConfirmationButtonForRowAtIndexPath:(NSIndexPath *)indexPath{
//    return @"删除";
//}

@end
