//
//  ServiceInfoVC.m
//  HighWay_iPhone
//
//  Created by litong on 14-9-3.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "ServiceInfoVC.h"
#import "Categories.h"

@interface ServiceInfoVC ()

@end

@implementation ServiceInfoVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = [_dataDic objectForKey:@"RestName"];
    self.view.backgroundColor = RGBCOLOR(240, 247, 244);
    scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
    
    [self initView];
    scrollView.contentSize = CGSizeMake(SCREEN_WIDTH, contentHeight);
    [self.view addSubview:scrollView];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - custem method
//初始化视图
-(void)initView
{
//    _serviceImg.image = [UIImage imageNamed:@"serviceImg-system"];
    serviceIcon = [[UIImageView alloc] initWithFrame:CGRectMake(20, 99, 30, 30)];
    serviceIcon.image = [UIImage imageNamed:@"service-Info-Icon"];
    
    serviceNameLabel = [[UILabel alloc] initWithFrame:CGRectMake(58, 98, SCREEN_WIDTH-58, 25)];
    serviceNameLabel.textColor = RGBCOLOR(14, 179, 98);
    serviceNameLabel.text = [_dataDic objectForKey:@"RestName"];
    
    roadNameLabel = [[UILabel alloc] initWithFrame:CGRectMake(58, 122, SCREEN_WIDTH-58, 23)];
    roadNameLabel.textColor = RGBCOLOR(102, 102, 102);
    roadNameLabel.text = [_dataDic objectForKey:@"roadName"];
    
    [scrollView addSubview:serviceIcon];
    [scrollView addSubview:serviceNameLabel];
    [scrollView addSubview:roadNameLabel];
    
    //json的key
    NSArray* labelNameAry = [[NSArray alloc] initWithObjects:@"Adress",@"Park",@"Gas",@"Repast",@"Conven",@"Lav",@"Repair",@"Others", nil];
    //显示的关键字
    NSArray* showNameAry = [[NSArray alloc] initWithObjects:@"地址",@"停车位",@"加油站",@"餐饮",@"便利店",@"洗手间",@"汽修场",@"其他", nil];
    
    int cellNum =0;
    for (int i=0; i<8; i++) {
//        if (![[_dataDic objectForKey:[labelNameAry objectAtIndex:i]] isKindOfClass:[NSNull class]] ) { //判断是否有值
        if ([NSString isNotEmpty:[_dataDic objectForKey:[labelNameAry objectAtIndex:i]]]) { //判断是否有值
            if ([[_dataDic objectForKey:[labelNameAry objectAtIndex:i]] isEqualToString:@"无"]) { //详细信息为无，不显示
                continue;
            }
            [self createCellFrame:cellNum labelName:[showNameAry objectAtIndex:i] detail:[_dataDic objectForKey:[labelNameAry objectAtIndex:i]]];
            cellNum++;
        }else
        {
            continue;
        }
    }
    
}

//创建表格单元
-(void)createCellFrame:(int)cellNum labelName:(NSString*)labelName detail:(NSString*)labelDetail
{
    if ([NSString isEmpty:labelDetail]) {
        return;
    }
    UIView* cellView = [[UIView alloc] initWithFrame:CGRectMake(25, 160+cellNum*44, SCREEN_WIDTH-50, 45)];
    cellView.backgroundColor = [UIColor whiteColor];
    cellView.layer.borderWidth=0.5f;
    cellView.layer.borderColor=[RGBCOLOR(229.0f,229.0f,229.0f) CGColor];
    
    UILabel *name = [[UILabel alloc] initWithFrame:CGRectMake(21, 0, 180, 44)];
    name.textColor = RGBCOLOR(102, 102, 102);
    name.text = labelName;
    
    UILabel *detail = [[UILabel alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2-70, 44)];
    detail.lineBreakMode = NSLineBreakByWordWrapping;
    detail.numberOfLines = 0; //允许换行
    detail.textColor = RGBCOLOR(157, 157, 157);
    detail.textAlignment = NSTextAlignmentRight;
    detail.text = labelDetail;
    detail.font = [UIFont systemFontOfSize:14.0];
    
    if ([labelName isEqualToString:@"其他"]) {
        
        //label自适应
        CGSize labelSize = [detail.text boundingRectWithSize:CGSizeMake(detail.frame.size.width, SCREEN_HEIGHT) options:NSStringDrawingUsesLineFragmentOrigin attributes:@{NSFontAttributeName: [UIFont fontWithName:@"HelveticaNeue" size:14]} context:nil].size;

        if (labelSize.height<45) {
            labelSize.height = 45;
        }
        labelSize.height = labelSize.height + 10;
        detail.frame = CGRectMake(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2-70, labelSize.height);
        name.frame = CGRectMake(21, 0, 180, labelSize.height); //对齐
        cellView.frame = CGRectMake(25, 160+cellNum*44, SCREEN_WIDTH-50, labelSize.height);
    }
   
    
    if (cellNum==0) { //地址
        name.text = labelDetail;
        detail.text = @"";
    }else
    {
        if ([labelDetail isEqualToString:@"有"]) {
            detail.text = @"";
        }
    }
    
    contentHeight = cellView.frame.origin.y + cellView.frame.size.height+30;
    
    [cellView addSubview:name];
    [cellView addSubview:detail];
    [scrollView addSubview:cellView];
}




@end
