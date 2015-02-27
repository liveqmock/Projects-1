//
//  ServiceInfoVC.h
//  HighWay_iPhone
//
//  Created by litong on 14-9-3.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "BaseVC.h"

@interface ServiceInfoVC : BaseVC
{
    UIScrollView *scrollView;
    int contentHeight; //VC的高度
    
    UILabel *serviceNameLabel;
    UILabel *roadNameLabel;
    UIImageView *serviceIcon;
}


@property (strong,nonatomic)NSDictionary *dataDic;

@property (weak, nonatomic) IBOutlet UIImageView *serviceImg;
@end
