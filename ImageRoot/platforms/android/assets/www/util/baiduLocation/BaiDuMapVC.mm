//
//  BaiDuMapVC.m
//  LTPlatform
//
//  Created by xue on 14-3-14.
//
//

#import "BaiDuMapVC.h"

@interface BaiDuMapVC ()

@end

@implementation BaiDuMapVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
-(id)init
{
    self = [super init];
    if (self) {
        
        self.BMapView = [[BMKMapView alloc] init];
        self.BMapView.delegate = self;
        self.BMapView.showsUserLocation = YES;
        [self.BMapView viewWillAppear];
        self.view = self.BMapView;
     
        
        
        self.searcher = [[BMKSearch alloc] init];
        self.searcher.delegate = self;
        
    }
    
    return self;
}
-(void)viewWillAppear:(BOOL)animated
{
    [_BMapView viewWillAppear];
    _BMapView.delegate = self; // 此处记得不用的时候需要置nil，否则影响内存的释放
}
-(void)viewWillDisappear:(BOOL)animated
{
    [_BMapView viewWillDisappear];
    _BMapView.delegate = nil; // 不用时，置nil
}
- (void)viewDidLoad
{
    [super viewDidLoad];
    

    
	// Do any additional setup after loading the view.
}

-(void)showLocation
{
    
    
    self.BMapView.showsUserLocation = YES;
    
    
    
}

- (void)mapView:(BMKMapView *)mapView didUpdateUserLocation:(BMKUserLocation *)userLocation
{
	if (userLocation != nil) {
		NSLog(@"%f %f", userLocation.location.coordinate.latitude, userLocation.location.coordinate.longitude);
        
        
        
        if (!self.isReturnChinese) {
            
            if (self.delegate&&[self.delegate respondsToSelector:@selector(getUserCLLocation:)]) {
                [self.delegate getUserCLLocation:userLocation.location];
                
            }
           
        }
        else
        {
            CLLocationCoordinate2D  Coordinate2D = CLLocationCoordinate2DMake(userLocation.location.coordinate.latitude, userLocation.location.coordinate.longitude);
                    
            [self performSelector:@selector(geoRevers:) withObject:[NSValue valueWithMKCoordinate:Coordinate2D] afterDelay:2.0];
            
            
        }
        mapView.showsUserLocation = NO;
        
        
	}
	
}

//BMKErrorOk = 0,	///< 正确，无错误
//BMKErrorConnect = 2,	///< 网络连接错误
//BMKErrorData = 3,	///< 数据错误
//BMKErrorRouteAddr = 4, ///<起点或终点选择(有歧义)
//BMKErrorResultNotFound = 100,	///< 搜索结果未找到
//BMKErrorLocationFailed = 200,	///< 定位失败
//BMKErrorPermissionCheckFailure = 300,	///< 百度地图API授权Key验证失败
//BMKErrorParse = 310		///< 数据解析失败

/**
 *在地图View停止定位后，会调用此函数
 *@param mapView 地图View
 */
- (void)mapViewDidStopLocatingUser:(BMKMapView *)mapView
{
    NSLog(@"stop locate");
}

/**
 *定位失败后，会调用此函数
 *@param mapView 地图View
 *@param error 错误号，参考CLError.h中定义的错误号
 */
- (void)mapView:(BMKMapView *)mapView didFailToLocateUserWithError:(NSError *)error
{
    NSLog(@"location error");
    
    if (self.delegate&&[self.delegate respondsToSelector:@selector(getUserCLLocationError:)]) {
        [self.delegate getUserCLLocationError:error];
        
    }
    
    
}

- (void)onGetPoiResult:(NSArray*)poiResultList searchType:(int)type errorCode:(int)error
{


    NSLog(@"error %i",error);



}

/**
 *返回地址信息搜索结果
 *@param result 搜索结果
 *@param error 错误号，@see BMKErrorCode
 */

- (void)onGetAddrResult:(BMKAddrInfo*)result errorCode:(int)error
{
    
    
    if (error==BMKErrorOk) {
       
        
        NSLog(@"返回地址信息搜索结果 result.strAddr %@",result.strAddr);
        
        if (self.delegate&&[self.delegate respondsToSelector:@selector(reverseGeocodeUserLocation:)]) {
            [self.delegate reverseGeocodeUserLocation:result];
        }
    }
    else
    {
        if (self.delegate&&[self.delegate respondsToSelector:@selector(reverseGeocodeUserLocationError:)]) {
            
            NSError * passError = [NSError errorWithDomain:@"反地理编码失败" code:error userInfo:nil];
            
            [self.delegate reverseGeocodeUserLocationError:passError];
        }
        
        
    }
    self.BMapView.showsUserLocation = NO;
    
    
    
}

-(void)geoRevers:(id)sender
{

    CLLocationCoordinate2D  Coordinate2D = [(NSValue*)sender MKCoordinateValue];
    
    BOOL isSuccess = [self.searcher reverseGeocode:Coordinate2D];
    if (!isSuccess) {
        NSLog(@"反编码失败？？？");
    }

}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
