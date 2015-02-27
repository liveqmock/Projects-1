//
//  UserShareVC.m
//  HighWay_iPhone
//
//  Created by litong on 14-12-25.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "UserShareVC.h"
#import "CurrentPointAnnotation.h"
#import "ReportPointAnnotation.h"

@implementation UserShareVC

-(void)viewDidLoad{
    [super viewDidLoad];
    [self.mapView setFrame:CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT)];
    self.mapView.delegate = self;
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    [_mapView viewWillAppear];
    _mapView.delegate = self;  // 此处记得不用的时候需要置nil，否则影响内存的释放

}

-(void)viewWillDisappear:(BOOL)animated{
    [_mapView viewWillDisappear];
    _mapView.delegate = nil; // 不用时，置nil
    
    [super viewWillDisappear:animated];
}

#pragma mark - custem method
// 网络请求数据
-(void)loadData{
    NSDictionary *param = @{@"xcode":[[NSString alloc] initWithFormat:@"%f",[CommonData sharedCommonData].          currentLocationCoordinate2D.longitude],
                            @"ycode":[[NSString alloc] initWithFormat:@"%f",[CommonData sharedCommonData].currentLocationCoordinate2D.latitude]};
    [DataDAO getUserShareInfo:param withCompleted:^(id result){
        NSLog(@"%@",@"请求用户分享资讯成功");
        dataAry = [[NSMutableArray alloc] initWithArray:result]; //用户分享资讯数组
        [self addAnnotation];
    } withFailure:^(id result){
        NSLog(@"%@",result);
    }];
}

// 在地图上添加标注
-(void)addAnnotation{

    NSMutableArray* reportAry = [NSMutableArray new];
    for (NSDictionary* dic in dataAry) {
        float XCODE=[[dic valueForKey:@"coor_x"] floatValue];
        float YCODE =[[dic valueForKey:@"coor_y"] floatValue];
        CLLocationCoordinate2D coordinate = {YCODE,XCODE};
        ReportPointAnnotation* ann = [[ReportPointAnnotation alloc] init];
        ann.data = dic;
        ann.coordinate = coordinate;
        ann.title = [dic objectForKey:@"createTime"];
        [reportAry addObject:ann];
    }
    [self.mapView addAnnotations:reportAry];
    
}

#pragma  mark - BMK Delegate
- (void)mapViewDidFinishLoading:(BMKMapView *)mapView{
    [self.mapView setCenterCoordinate:[CommonData sharedCommonData].currentLocationCoordinate2D animated:YES];
    CurrentPointAnnotation *currentAnn = [[CurrentPointAnnotation alloc] init];
    currentAnn.coordinate = [CommonData sharedCommonData].currentLocationCoordinate2D;
    [self.mapView addAnnotation:currentAnn];
    [self loadData]; //请求用户分享的数据
}

- (BMKAnnotationView *)mapView:(BMKMapView *)mapView viewForAnnotation:(id <BMKAnnotation>)annotatio{
    if ([annotatio isKindOfClass:[CurrentPointAnnotation class]]) { //当前位置
        
        BMKAnnotationView *currentAnnView = [[BMKAnnotationView alloc] initWithAnnotation:annotatio reuseIdentifier:nil];
        currentAnnView.image = [UIImage imageNamed:@"GPS-Station"];
        return currentAnnView;
        
    }else if ([annotatio isKindOfClass:[ReportPointAnnotation class]]){ //上报的标注
        
        static NSString *annotaionIdentifier = @"reportPointAnnotaion";
        ReportPointAnnotation *ann = (ReportPointAnnotation*)annotatio;
        BMKAnnotationView *annotationView=[self.mapView dequeueReusableAnnotationViewWithIdentifier:annotaionIdentifier];
        if(!annotationView)
            annotationView = [[BMKPinAnnotationView alloc] initWithAnnotation:annotatio reuseIdentifier:annotaionIdentifier];
        
        //根据类型来显示
        int type = [[ann.data objectForKey:@"type"] intValue];
        switch (type) {
            case TrafficJam:
                annotationView.image=[UIImage imageNamed:@"trafficJam-map"];
                break;
            case Accident:
                annotationView.image=[UIImage imageNamed:@"accident-map"];
                break;
            case Fix:
                annotationView.image=[UIImage imageNamed:@"fix-map"];
                break;
            case Control:
                annotationView.image=[UIImage imageNamed:@"control-map"];
                break;
            case Water:
                annotationView.image=[UIImage imageNamed:@"water-map"];
                break;
            case Landslide:
                annotationView.image=[UIImage imageNamed:@"landslide-map"];
                break;
        }
        annotationView.canShowCallout=YES;
        return annotationView;

    
    }else{
        return nil;
    }
}

@end
