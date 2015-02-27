//
//  DirvingRouteDetailVC.h
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/19.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"
#import "BNaviSoundManager.h"

@interface DirvingRouteDetailVC : BaseVC<BNNaviRoutePlanDelegate,BNNaviUIManagerDelegate,BMKLocationServiceDelegate,UIActionSheetDelegate>
{
    int speedCount;
    NSArray *speedArray;
    int stationCount;
    NSMutableArray *stationArray;
    NSMutableArray *allRoadIDs; //经过的所有高速路，用于查询交通事故
    int roadIndex; //请求高速路速度标识
    
    //经过路的全部交通事故信息
    NSMutableArray *accidentAry;
    NSMutableArray *constructAry;
    NSMutableDictionary *serviceDic;
    NSMutableArray *allServiceAry;
    
    //真正在途径路段发生的事故
    NSMutableArray *wayPointAry;
    NSMutableArray *realServiceAry;
    NSMutableArray *realAccidentAry;
    NSMutableArray *realConstructAry;
    
    NSMutableDictionary *sortStationDic; //按路ID分好类的站
    NSMutableDictionary *sortSpeedDic;
    
    CLLocationManager *locationManager;
    BMKLocationService* _locService; // 定位服务
    BMKPointAnnotation *currentPointAnnotation; //当前位置
    BNaviSoundManager *soundManager; //语音控制器
    
    UIButton *voiceBtn; //语音按钮
    
    BOOL isOnHighWay; //是否在高速路上 （百度导航是否成功）
    CLLocationCoordinate2D nextStationCoor; //下一个站的经纬度
    NSInteger nextCarIndex; //当前小车下一个站
    
    //语音提示标志
    BOOL isAccidentOccur; //发生车祸
    BOOL isFixOccur; //发现施工
    BOOL isHaveService; //发现服务区
    
    UILabel *startCityLabel,*endCityLabel;
    NSString *startCityStr,*endCityStr;
}

// 唯一标识，用于终端APP进行后续的规划方案明细信息查询
@property (nonatomic,strong) NSString *requestIdentifier;
@property (nonatomic,strong) NSDictionary *planDic;
@property (assign, nonatomic) BN_NaviType naviType;

@property (nonatomic,strong) IBOutlet UITableView *tableView;
@property (nonatomic,strong) IBOutlet UILabel *kilometerLabel,*moneyLabel,*timeLabel;
@property (weak, nonatomic) IBOutlet UIButton *navigationBtn;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *timeWidthConstraints;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *kiloWidthConstraints;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *moneyWidthConstraints;

// 分隔线宽度
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *line1WidthConstraints;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *line2WidthConstraints;

// 系统推送
@property (weak, nonatomic) IBOutlet UILabel *pushNum;
@property (weak, nonatomic) IBOutlet UILabel *pushInfo;

@end
