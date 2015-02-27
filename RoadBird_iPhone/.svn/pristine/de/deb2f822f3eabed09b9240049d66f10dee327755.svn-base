//
//  HighwayListVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-27.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "HighwayListVC.h"
#import "HighwayInfoVC.h"
#import "AppDelegate.h"



@implementation HighwayListVC


- (void)viewDidLoad
{
    [super viewDidLoad];
   
    self.navigationItem.title=@"高速公路列表";
    [self loadData];
}


- (void)loadData
{
    roadArray=[JSONUtil readJSON:@"Road.json"];
    [self.tableView reloadData];
}


#pragma mark- UITableView Delegate


//Return how many rows in the table
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    int count =0;
    if(tableView==self.searchDisplayController.searchResultsTableView)
        count=(int)[searchRoadArray count];
    else
        count =(int)[roadArray count];
    return  count;
}

// Return a cell for the ith row
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    int row = (int)indexPath.row;
    static NSString *cellIdentifier = @"cell";
    UITableViewCell *cell  = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if(!cell)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault  reuseIdentifier: cellIdentifier] ;
        cell.textLabel.textAlignment=NSTextAlignmentCenter;
    }
    NSDictionary *rowData;
    if(tableView==self.searchDisplayController.searchResultsTableView)
        rowData =[searchRoadArray objectAtIndex:row];
    else
        rowData =[roadArray objectAtIndex:row];
    NSString *roadname =[rowData valueForKeyPath:@"ROADNAME"];
    cell.textLabel.text =roadname;
    cell.textLabel.textColor = RGBCOLOR(102, 102, 102);
    
    return cell;
}



- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)newIndexPath
{
    int row = (int)newIndexPath.row;
    NSDictionary *rowData ;
    if(tableView==self.searchDisplayController.searchResultsTableView)
        rowData =[searchRoadArray objectAtIndex:row];
    else
        rowData =[roadArray objectAtIndex:row];
    HighwayInfoVC *highwayInfoVC =[[HighwayInfoVC alloc] init];
    highwayInfoVC.roadDic=rowData;
    [app.nav pushViewController:highwayInfoVC animated:YES];

    // 取消选中效果
    [tableView deselectRowAtIndexPath:newIndexPath animated:NO];
}



#pragma mark -
#pragma mark UISearchBarDelegate

- (void)searchBarTextDidBeginEditing:(UISearchBar *)uisearchbar
{
    uisearchbar.showsCancelButton = YES;
    for(UIView *cc in [uisearchbar subviews])
    {
        for(UIView *ndLeveSubView in [cc subviews]){
            if([ndLeveSubView isKindOfClass:[UIButton class]])
            {
                UIButton *btn = (UIButton *)ndLeveSubView;
                [btn setTitle:@"取消"  forState:UIControlStateNormal];
                
            }}
    }
    
}



- (void)searchBarCancelButtonClicked:(UISearchBar *)sender
{
    sender.showsCancelButton = NO;
    [sender resignFirstResponder];
    sender.text = @"";
}


- (BOOL)searchDisplayController:(UISearchDisplayController *)controller shouldReloadTableForSearchString:(NSString *)searchString {
    if(self.searchDisplayController.searchBar.text.length>0) {
       isSearching=YES;
        NSString *strSearchText = self.searchDisplayController.searchBar.text;
        
        
        searchRoadArray =[[NSMutableArray alloc] init];
        for (NSDictionary *road in roadArray) {
            NSString *roadName = [road valueForKeyPath:@"ROADNAME"];
            NSRange range = [roadName rangeOfString:strSearchText];
            if(range.location !=NSNotFound)
            {
                [searchRoadArray addObject:road];
            }
        }
    } else {
       isSearching=NO;
    }
    return YES;
}


@end
