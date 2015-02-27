//
//  GaodeMapViewController.m
//  LTPlatform
//
//  Created by xue on 14-3-14.
//
//

#import "GaodeMapViewController.h"

@interface GaodeMapViewController ()

@end

@implementation GaodeMapViewController


-(id)init
{
    self = [super init];
    if (self) {
        NSLog(@"GaodeMapViewController init");
        if (_appleMap==nil) {
            
            
            _appleMap = [[MKMapView alloc] initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)];
            _appleMap.delegate = self;
            
            [self.view addSubview:_appleMap];
            
            
            
            
            
        }
        
        
    }
    return self;
}

-(void)showUserLocation
{
    
    _locationTime = 1;
    
    
    if (_manager==nil) {
        _manager = [[CLLocationManager alloc] init];
        _manager.delegate = self;
        _manager.desiredAccuracy = kCLLocationAccuracyBest;
        _manager.distanceFilter = 50.0;
    }
    
    
    [_manager startUpdatingLocation];
    
    
}

-(void)mapView:(MKMapView *)mapView didUpdateUserLocation:(MKUserLocation *)userLocation
{
    
    
    
    _appleMap.showsUserLocation = NO;
    
    if (!self.isReturnInfo) {
        
        if (self.delegate&&[self.delegate respondsToSelector:@selector(getGaoDeMKUserLocation:)]) {
            [self.delegate getGaoDeMKUserLocation:userLocation];
        }
        
        
    }
    else
    {
        _geocoder  = [[CLGeocoder alloc] init];
        [_geocoder reverseGeocodeLocation:userLocation.location completionHandler:^(NSArray *placemarksArray,NSError*error){
            
            
            for (int i = 0; i<[placemarksArray count]; i++) {
                NSLog(@"Name  %@",[((CLPlacemark*)[placemarksArray objectAtIndex:i]).addressDictionary  objectForKey:@"Name"]);
            }
            
            
            
            CLPlacemark *placemark = [placemarksArray objectAtIndex:0];
            if (!placemark.administrativeArea && !placemark.locality)
            {
                NSLog(@"error");
            }
            else
            {
                
                NSLog(@"第%i次: 地名:%@",_locationTime,placemark.name);
                
            }
            if (self.delegate&&[self.delegate respondsToSelector:@selector(getGaoDePlacemarksArray:)]) {
                [self.delegate getGaoDePlacemarksArray:placemarksArray];
            }
            
        }];
        
    }
    
    
    
    
    
    
    
}



#pragma mark IOS6 以前
-(void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation
{
    
    
    NSLog(@"newLocation %@",newLocation);
    
    
    
    [self performSelector:@selector(delayShowLocation:) withObject:nil afterDelay:1.0f];
    
    
    
    
    
    
    
    
}

-(void)delayShowLocation:(id)sender
{
    NSLog(@"delayShowLocation");
    
    _appleMap.showsUserLocation = YES;
    
}
#pragma mark IOS6 以后
- (void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *)locations
{
    
    if ([locations count]>0) {
        _appleMap.showsUserLocation = YES;
    }
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
