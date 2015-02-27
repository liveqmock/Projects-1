//
//  MapUtil.m
//  HighWay_iPhone
//
//  Created by wanggp on 14-5-8.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "MapUtil.h"
#import <CoreLocation/CoreLocation.h>

@implementation MapUtil

+(void)getCityByCLLocationCoordinate2D:(CLLocationCoordinate2D)coordinate2D completedBlock:(void(^)(NSString *))completedBlock{
    // 默认为广州

    CLLocation *location = [[CLLocation alloc] initWithLatitude:coordinate2D.latitude longitude:coordinate2D.longitude];
    CLGeocoder *geocoder =[[CLGeocoder alloc] init];
    [geocoder reverseGeocodeLocation:location completionHandler:^(NSArray *placemarks,NSError *error){
        if(error)
        {
            return ;
        }
        if(placemarks.count>0)
        {
            NSString *city  = @"";
            CLPlacemark *placemark = [placemarks objectAtIndex:0];
            
            if([placemark.addressDictionary objectForKey:@"SubAdministrativeArea"] != NULL)
                city = [placemark.addressDictionary objectForKey:@"SubAdministrativeArea"];
            else if([placemark.addressDictionary objectForKey:@"City"] != NULL)
                city = [placemark.addressDictionary objectForKey:@"City"];
            else if([placemark.addressDictionary objectForKey:@"Country"] != NULL)
                city = [placemark.addressDictionary objectForKey:@"Country"];
            else
                city = @"City Not founded";
            // 去掉“市”
            city=[ city stringByReplacingOccurrencesOfString:@"市" withString:@""];
            completedBlock(city);
        }
    }];
//    return  city;
    
    
}

@end
