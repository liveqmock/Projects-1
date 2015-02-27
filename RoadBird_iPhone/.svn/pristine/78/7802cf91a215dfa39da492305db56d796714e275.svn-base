//
//  HighwayLifeInfoVC.m
//  HighWay_iPhone
//
//  Created by litong on 15-1-14.
//  Copyright (c) 2015年 lt. All rights reserved.
//


#import "HighwayLifeInfoVC.h"

@interface HighwayLifeInfoVC ()

@end

@implementation HighwayLifeInfoVC

- (id)initWithTitle:(NSString*)titleStr{
    self = [super init];
    if (self!=nil) {
        self.titleStr = titleStr;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = self.titleStr;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - tableView Delegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    return [[UITableViewCell alloc] init];
}

@end
