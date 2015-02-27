//
//  StationCalloutAnnotationView.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/18.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "StationCalloutAnnotationView.h"
#define  Arror_height 6

@implementation StationCalloutAnnotationView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}


-(id)initWithAnnotation:(id<BMKAnnotation>)annotation reuseIdentifier:(NSString *)reuseIdentifier{
    
    
    self = [super initWithAnnotation:annotation reuseIdentifier:reuseIdentifier];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
        self.canShowCallout = NO;
        self.centerOffset = CGPointMake(65,-37);
        self.frame = CGRectMake(0, 0, 110, 40); //显示的frame
        
        self.contentView = [[UIView alloc] initWithFrame:self.frame];
        self.contentView.backgroundColor=[UIColor clearColor];
        UIImageView *bgImageView = [[UIImageView alloc] initWithFrame:self.contentView.bounds];
        UIImage *bgImage = [UIImage imageNamed:@"Station-Callout-bg"];
        bgImage =[bgImage stretchableImageWithLeftCapWidth:20 topCapHeight:15];
        bgImageView.image=bgImage;
        [self.contentView addSubview:bgImageView];
        
        
        self.stationNameLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 5, self.contentView.bounds.size.width, self.contentView.bounds.size.height*2/5)];
        self.stationNameLabel.backgroundColor=[UIColor clearColor];
        self.stationNameLabel.textAlignment=NSTextAlignmentCenter;
        self.stationNameLabel.textColor=[UIColor whiteColor];
        self.stationNameLabel.font = [UIFont fontWithName:@"Helvetica" size:11];
        [self.contentView addSubview:self.stationNameLabel];
        
        
        self.distanceLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, self.contentView.bounds.size.height*2/5+5, self.contentView.bounds.size.width, self.contentView.bounds.size.height*2/5)];
        self.distanceLabel.backgroundColor=[UIColor clearColor];
        self.distanceLabel.textAlignment=NSTextAlignmentCenter;
        self.distanceLabel.textColor=[UIColor whiteColor];
        self.distanceLabel.font = [UIFont fontWithName:@"Helvetica" size:9];
        [self.contentView addSubview:self.distanceLabel];
               
        [self addSubview:_contentView];
    }
    return self;
    
}

//-(void)drawRect:(CGRect)rect{
//    
//    [self drawInContext:UIGraphicsGetCurrentContext()];
//    
//    self.layer.shadowColor = [[UIColor blackColor] CGColor];
//    self.layer.shadowOpacity = 1.0;
//    self.layer.shadowOffset = CGSizeMake(0.0f, 0.0f);
//    
//    
//}

-(void)drawInContext:(CGContextRef)context
{
	
    CGContextSetLineWidth(context, 2.0);
    CGContextSetFillColorWithColor(context, [UIColor colorWithRed:255.0/255.0 green:255.0/255.0 blue:255.0/255.0 alpha:1.0].CGColor);
    
    [self getDrawPath:context];
    CGContextFillPath(context);
    
}
- (void)getDrawPath:(CGContextRef)context
{
    CGRect rrect = self.bounds;
	CGFloat radius = 6.0;
    
	CGFloat minx = CGRectGetMinX(rrect),
    midx = CGRectGetMidX(rrect),
    maxx = CGRectGetMaxX(rrect);
	CGFloat miny = CGRectGetMinY(rrect),
    // midy = CGRectGetMidY(rrect),
    maxy = CGRectGetMaxY(rrect)-Arror_height;
    CGContextMoveToPoint(context, midx+Arror_height, maxy);
    CGContextAddLineToPoint(context,midx, maxy+Arror_height);
    CGContextAddLineToPoint(context,midx-Arror_height, maxy);
    
    CGContextAddArcToPoint(context, minx, maxy, minx, miny, radius);
    CGContextAddArcToPoint(context, minx, minx, maxx, miny, radius);
    CGContextAddArcToPoint(context, maxx, miny, maxx, maxx, radius);
    CGContextAddArcToPoint(context, maxx, maxy, midx, maxy, radius);
    CGContextClosePath(context);
}


@end
