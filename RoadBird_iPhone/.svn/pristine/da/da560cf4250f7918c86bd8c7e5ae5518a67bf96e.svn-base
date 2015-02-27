//
//  SelStationVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-30.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "SelStationVC.h"
#import "AppDelegate.h"
#import "SelStationTableHeaderView.h"



@implementation SelStationVC

- (void)viewDidLoad
{
   
    [super viewDidLoad];
    
    [self initSearchView];
    
    _stationTableView.layer.borderWidth=0.5f;
    _stationTableView.layer.borderColor=[RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    
    //初始化城市列表
    [self initCityData];
    isFirstInit = YES;
    NSArray *tempAry =[_cityArray subarrayWithRange:NSMakeRange(0, 8)];
    NSMutableArray *ary =[[NSMutableArray alloc] initWithArray:tempAry];
    [ary addObject:@"更多"];
    [self initCityView:ary];
    
    self.automaticallyAdjustsScrollViewInsets=NO;
    _lastButtonText = @"";
    isMore = NO;
    
    //读取站的数据
    stationDic =[JSONUtil readJSON:@"StationByCity.json"];
   
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    //初始化引导页
    if ([[SqliteUtil sharedSqliteUtil] queryIndexInfo:@"SelStation-Index"]) {
        //注册通知
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(IndexViewGone) name:@"IndexViewGone" object:nil];
        [self initIndexView:@"SelStation-Index-one"];
    }
}


- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [searchBar resignFirstResponder];
    
}


#pragma mark - custom method
//初始化搜索控件
-(void)initSearchView
{
    searchBar=[[UISearchBar alloc] initWithFrame:CGRectMake(0.0f,0.0f,SCREEN_WIDTH-80,44.0f)];
    [searchBar setDelegate:self];
    [searchBar setPlaceholder:@"收费站搜索"];
    
    [self setSearchBarBackground:searchBar];
    UIView *searchView = [[UIView alloc]initWithFrame:CGRectMake(0.0f, 0.0f, SCREEN_WIDTH-80, 44.0f)];
    [searchView addSubview:searchBar];
    self.navigationItem.titleView = searchView;
}


//引导页消失
-(void)IndexViewGone
{
    [self initIndexView:@"SelStation-Index-two"];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"IndexViewGone" object:nil];
    [[SqliteUtil sharedSqliteUtil] updateInfo:@"SelStation-Index" state:@"hide"];
}

//获得城市列表信息
- (void) initCityData
{
    NSDictionary *dic=[JSONUtil readJSON:@"City.json"];
    _cityArray=[dic valueForKey:@"citys"];
    NSMutableArray *ary=[[NSMutableArray alloc] init];
    [ary addObject:[CommonData sharedCommonData].currentCity];
    for(NSString *city in _cityArray)
    {
        if(![city isEqualToString:[CommonData sharedCommonData].currentCity]) //令当前城市在最前面
        {
            [ary addObject:city];
        }
    }
    _cityArray = ary;
}


- (void)initCityView:(NSArray *)array
{
    int rowNumber =3;
    NSInteger cityNumber  = array.count;
    if(cityNumber>0)
    {
        // 横向
        int horizontalViewCount=cityNumber%rowNumber==0?cityNumber/rowNumber+1:cityNumber/rowNumber+2;
        // 竖向
        int verticalViewCount=rowNumber+1;
        if(_cityView)
            [_cityView removeFromSuperview];
        
        _cityView=[[UIView alloc] initWithFrame:CGRectMake(15.0f,64.0f+12.0f,SCREEN_WIDTH-15.0f*2, 44.0f*(horizontalViewCount-1))];
        _cityView.backgroundColor=[UIColor whiteColor];
        
        for (int i=0;i<horizontalViewCount;i++) {
            UIView *view  = [[UIView alloc] initWithFrame:CGRectMake(0,i*44.0f, _cityView.frame.size.width,0.5f)];
            view.backgroundColor=RGBCOLOR(229.0f,229.0f,229.0f);
            [_cityView addSubview:view];
        }
        for(int i =0;i<verticalViewCount;i++)
        {
            UIView *view =[[UIView alloc] initWithFrame:CGRectMake((_cityView.frame.size.width/3)*i,0.0f, 0.5f, 44.0f*(horizontalViewCount-1)) ];
            //画线，粗为0.5
            view.backgroundColor=RGBCOLOR(229.0f,229.0f,229.0f);
            [_cityView addSubview:view];
        }
        

        //让按键填满格子
        
        for(int i =0;i<horizontalViewCount;i++)
        {
            for(int j=0;j<rowNumber;j++){
                if([array count]>i*rowNumber+j){
                    UIButton  *btn = [UIButton buttonWithType:UIButtonTypeCustom];
                    
                    btn.tag=i*rowNumber+j;
                    btn.titleLabel.font = [UIFont boldSystemFontOfSize:18];
                    btn.frame = CGRectMake((_cityView.frame.size.width/3)*j+1, i*44.0f+1, _cityView.frame.size.width/3-1, 43.0f);
                    
                    if (isFirstInit && i==0 && j==0) {
                        isFirstInit = NO;
                        btn.backgroundColor = RGBCOLOR(223, 253, 241);
                        _lastButton = btn;
                    }
                    NSString *city =[array objectAtIndex:i*rowNumber+j];
                    if([city isEqualToString:@"更多"]||[city isEqualToString:@"隐藏"])
                    {
                        [btn setTitleColor:NAV_GREEN_FONT_COLOR forState:UIControlStateNormal];
                    }
                    else
                    {
                        [btn setTitleColor:RGBCOLOR(102.0f,102.0f,102.0f) forState:UIControlStateNormal];
                    }
                    [btn setTitle:city forState:UIControlStateNormal];
                    if(_lastButtonText==city) //城市列表已展开时调用，现在收回城市列表，让之前选择的button背景变化
                    {
                        btn.backgroundColor = RGBCOLOR(223, 253, 241);
                        _lastButton = btn;
                    }
                    
                    [btn addTarget:self action:@selector(citySelect:) forControlEvents:UIControlEventTouchUpInside];
                    [_cityView addSubview:btn];
                }
            }
        }
        UIImageView *locationImageView =[[UIImageView alloc] initWithFrame:CGRectMake(_cityView.frame.size.width/(3*5)-2, (44.0f-17.0f)/2, 13.0f, 17.0f)];
        locationImageView.image=IMAGE(@"GPS-SelStation@2x.png");
        [_cityView addSubview:locationImageView];
        
        [self.view addSubview:_cityView];
        
    }

}


//选中了某个城市
-(IBAction)citySelect:(id)sender
{
    //防止在搜索状态下点击城市列表抛出异常
    isSearching=NO;
    [self.stationTableView reloadData];
    
    searchBar.text = @"";
    if (_lastButton!=nil) { //上次选中的button
        [_lastButton setBackgroundColor:[UIColor whiteColor]];
    }
    UIButton *btn =sender;
    _lastButton = btn;
    [btn setBackgroundColor:RGBCOLOR(223, 253, 241)];
    
    NSString *cityName =btn.titleLabel.text;
    if([cityName isEqualToString:@"更多"])
    {
        NSMutableArray *ary =[[NSMutableArray alloc] initWithArray:_cityArray];
        [ary addObject:@"隐藏"];
        [self initCityView:ary];
        self.stationTableView.hidden=YES;
        isMore = YES;
        
    }
    else if([cityName isEqualToString:@"隐藏"])
    {
        NSArray *tempAry =[_cityArray subarrayWithRange:NSMakeRange(0, 8)];
        NSMutableArray *ary =[[NSMutableArray alloc] initWithArray:tempAry];
        [ary addObject:@"更多"];
        [self initCityView:ary];
        self.stationTableView.hidden=NO;
        isMore = NO;
    }
    else
    {
        self.stationTableView.hidden=NO;
       
        int section =0;
        for (int i=0; i<[_cityArray count]; i++) {
            if([cityName isEqualToString:[_cityArray objectAtIndex:i]])
            {
                section=i;
                if (isMore) { //若城市列表展开，更新button，否则不更新并且显示背景颜色
                    isMore = NO;
                    
                    NSArray *tempAry = [[NSArray alloc] init];
                     //把选中的城市提到第二位
                    if (i>=8) {
                        NSMutableArray *newArray = [[NSMutableArray alloc] init];
                        [newArray addObject:[CommonData sharedCommonData].currentCity];
                        [newArray addObject:cityName];
                        for (int i=1; i<[_cityArray count]; i++) {
                            if ([[_cityArray objectAtIndex:i]isEqualToString:cityName]) {
                                continue;
                            }else
                            {
                                [newArray addObject:[_cityArray objectAtIndex:i]];
                            }
                        }
                       tempAry = [newArray subarrayWithRange:NSMakeRange(0, 8)];
                        
                    }else{
                        tempAry =[_cityArray subarrayWithRange:NSMakeRange(0, 8)];
                    }
                    
                    
                    NSMutableArray *ary =[[NSMutableArray alloc] initWithArray:tempAry];
                    [ary addObject:@"更多"];
                    _lastButtonText = cityName;
                    NSLog(@"选中的城市:%@",cityName);
                    [self initCityView:ary];
                    _lastButtonText = @"";
                }
                break;
            }
        }
        
        NSIndexPath *indexPath=[NSIndexPath indexPathForRow:0 inSection:section];
        [_stationTableView scrollToRowAtIndexPath:indexPath atScrollPosition:UITableViewScrollPositionTop animated:YES];
    }
    
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
    isSearching=NO;
    [self.stationTableView reloadData];
}

// called when Search (in our case “Done”) button pressed
- (void)searchBarSearchButtonClicked:(UISearchBar *)sender
{
    
    [sender resignFirstResponder];
    NSString *searchTerm = [[sender text] stringByTrimmingCharactersInSet:
							[NSCharacterSet whitespaceAndNewlineCharacterSet]];
	if([searchTerm isEqualToString:@""]) {
        [self showHUD:@"请输入关键字" :@"" :YES];
	} else {
        isSearching=YES;
        searchStationArray =[[NSMutableArray alloc] init];
        for ( NSString *ctiyName in _cityArray) {
            NSArray *stationArray =[stationDic valueForKey:ctiyName];
            for (NSDictionary *station in stationArray) {
                NSString *stationName =[station valueForKey:@"stationname"];
                NSRange range = [stationName rangeOfString:searchTerm];
                if(range.location !=NSNotFound)
                {
                    [searchStationArray addObject:station];
                }
                
            }
            [self.stationTableView reloadData];
        }
        
	}
    
}


//- (BOOL)searchDisplayController:(UISearchDisplayController *)controller shouldReloadTableForSearchString:(NSString *)searchString {
//    if(self.searchDisplayController.searchBar.text.length>0) {
//        isSearching=YES;
//        NSString *strSearchText = self.searchDisplayController.searchBar.text;
//
//
//        searchStationArray =[[NSMutableArray alloc] init];
//        for ( NSString *ctiyName in _cityArray) {
//             NSArray *stationArray =[stationDic valueForKey:ctiyName];
//            for (NSDictionary *station in stationArray) {
//                NSString *stationName =[station valueForKey:@"stationname"];
//                NSRange range = [stationName rangeOfString:strSearchText];
//                if(range.location !=NSNotFound)
//                {
//                    [searchStationArray addObject:station];
//                }
//
//            }
//        }
//    } else {
//        isSearching=NO;
//    }
//    return YES;
//}




#pragma  mark- UITableView Delegate


- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    if(isSearching)
        return 0.0f;
    else
        return  36.0f;
}
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    if(isSearching)
        return 0.0f;
    else
        return 0.00000001f;
}


- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    if(isSearching)
        return 1;
    else
        return  [self.cityArray count];
}
- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section;
{
    if (isSearching) {
        return NULL;
    }
    else
    {
        static NSString *identifier = @"SelStationTableHeaderView";
        SelStationTableHeaderView *headerView = [tableView dequeueReusableHeaderFooterViewWithIdentifier:identifier];
        if(!headerView)
        {
            //        headerView=[[UITableViewHeaderFooterView alloc] initWithReuseIdentifier:identifier];
            NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:identifier owner:self options:nil];
            for(id currentObject in topLevelObjects)
            {
                if([currentObject isKindOfClass:[SelStationTableHeaderView class]])
                {
                    headerView = (SelStationTableHeaderView *)currentObject;
                    break;
                }
            }
            
        }
        
        headerView.cityLabel.text=[self.cityArray objectAtIndex:section];
        headerView.mapBtn.tag=section;
        headerView.imageBtn.tag=section;
        [headerView.mapBtn addTarget:self action:@selector(goMap:) forControlEvents:UIControlEventTouchDown];
        [headerView.imageBtn addTarget:self action:@selector(goMap:) forControlEvents:UIControlEventTouchDown];
        return headerView;

    }
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    static NSString * identifier = @"footerView";
    UITableViewHeaderFooterView *footerView =[tableView dequeueReusableHeaderFooterViewWithIdentifier:identifier];
    if(!footerView)
    {
        footerView =[[UITableViewHeaderFooterView alloc] initWithReuseIdentifier:identifier];
    }
    return footerView;
}

- (IBAction)goMap:(id)sender
{
    UIButton *btn =sender;
    NSInteger tag =btn.tag;
    NSString *ctiyName= [self.cityArray objectAtIndex:tag];
    NSArray *stationArray =[stationDic valueForKey:ctiyName];
    selStationByMapVC = [[SelStationByMapVC alloc] init];
    selStationByMapVC.stationArray=stationArray;
    selStationByMapVC.delegate=self;
    selStationByMapVC.isStart = self.isStart;
    [app.nav pushViewController:selStationByMapVC animated:YES];
}


//Return how many rows in the table
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if(isSearching)
    {
        return [searchStationArray count];
    }
    else
    {
        NSString *ctiyName= [self.cityArray objectAtIndex:section];
        return  [[stationDic valueForKey:ctiyName] count];
    }
    
}

// Return a cell for the ith row
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSInteger section = indexPath.section;
    NSInteger row = indexPath.row;
    static NSString *cellIdentifier = @"cell";
    UITableViewCell *cell  = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if(!cell)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault  reuseIdentifier: cellIdentifier] ;
    }
    cell.textLabel.frame=cell.frame;
    cell.textLabel.textColor=NAV_GREEN_FONT_COLOR;
    cell.textLabel.textAlignment=NSTextAlignmentCenter;
    
    if(isSearching)
    {
        NSDictionary *stationInfo =[searchStationArray objectAtIndex:row];
        cell.textLabel.text=[stationInfo valueForKey:@"stationname"];
    }
    else
    {
        
        NSString *ctiyName= [self.cityArray objectAtIndex:section];
        NSArray *stationArray =[stationDic valueForKey:ctiyName];
        NSDictionary *stationInfo =[stationArray objectAtIndex:row];
        cell.textLabel.text=[stationInfo valueForKey:@"stationname"];
    }
    
    return cell;
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)newIndexPath
{
    NSInteger section = newIndexPath.section;
    NSInteger row = newIndexPath.row;
    NSDictionary *stationInfo;
    if(isSearching)
    {
        stationInfo =[searchStationArray objectAtIndex:row];
    }
    else
    {
        NSString *ctiyName= [self.cityArray objectAtIndex:section];
        NSArray *stationArray =[stationDic valueForKey:ctiyName];
        stationInfo =[stationArray objectAtIndex:row];
    }
    [self selStationCallBack:stationInfo];
    // 取消选中效果
    [tableView deselectRowAtIndexPath:newIndexPath animated:NO];
}

#pragma SelStationByMapDelegate


- (void)selStationCallBack:(NSDictionary *)dic
{
    if(!dic)
    {
        [self showHUD:@"请选择站点！" :FAILEDICON :YES];
        return;
    }
    if (_delegate && [_delegate respondsToSelector:@selector(selStationCallBack:)]) {
        [_delegate selStationCallBack:dic];
        if(selStationByMapVC)
            [app.nav popToViewController:self animated:NO];
        [self goBack:nil];
    }
    
}



@end
