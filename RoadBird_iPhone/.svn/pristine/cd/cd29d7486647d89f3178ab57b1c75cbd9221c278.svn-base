//
//  NewsInfoVC.m
//  HighWay_iPhone
//
//  Created by wanggp on 14/6/10.
//  Copyright (c) 2014年 lt. All rights reserved.
//

#import "NewsInfoVC.h"

@interface NewsInfoVC ()

@end

@implementation NewsInfoVC


- (void)viewDidLoad
{
    [super viewDidLoad];
    if(self.type ==1)
        self.title=@"交通事件";
    else
        self.title=@"道路施工";
    [self displayContent];
}

- (void) displayContent
{
    if(_dataDic)
    {
        NSString *infoStr = [NSString stringWithContentsOfFile: [ ComFun bundleFilePath:@"NewsInfo.html" ] encoding:NSUTF8StringEncoding error:NULL];
        NSString *title = [NSString stringWithFormat:@"%@(%@)",[_dataDic valueForKey:@"roadName"],[_dataDic valueForKey:@"occurPlace"]];
        infoStr=[infoStr stringByReplacingOccurrencesOfString:@"!$title$!" withString:title];
        NSString *occurTime= [_dataDic valueForKey:@"occurTime"];
        infoStr=[infoStr stringByReplacingOccurrencesOfString:@"!$occurTime$!" withString:occurTime];
        infoStr=[infoStr stringByReplacingOccurrencesOfString:@"!$remark$!" withString:[_dataDic valueForKey:@"remark"]];
        
        NSString *path = [[NSBundle mainBundle] resourcePath];
        NSURL *baseURL = [NSURL fileURLWithPath:path];
        [self.webView loadHTMLString:infoStr baseURL:baseURL];
    }
    
    
}


@end
