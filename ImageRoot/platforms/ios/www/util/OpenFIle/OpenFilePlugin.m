//
//  OpenFilePlugin.m
//  LTPlatform
//
//  Created by xue on 14-3-28.
//
//

#import "OpenFilePlugin.h"

@implementation OpenFilePlugin
-(id)init
{
    self = [super init];
    if (self) {
        
    }
    return self;
}

-(void)openFile:(CDVInvokedUrlCommand *)command
{
    
    
    self.theCommand = command;
    
    self.filePath = [command.arguments objectAtIndex:0];
    
    self.filePathArr = [command.arguments objectAtIndex:1];
    
    QLPreviewController * previewController = [[QLPreviewController alloc] init];
    previewController.dataSource = self;
    previewController.delegate = self;
    previewController.currentPreviewItemIndex = [self.filePathArr indexOfObject:self.filePath];
    [self.viewController presentViewController:previewController animated:YES completion:nil];
    
}
- (NSInteger)numberOfPreviewItemsInPreviewController:(QLPreviewController *)controller
{
    
    return [self.filePathArr count];
    
   // return 1;
}
- (id <QLPreviewItem>)previewController:(QLPreviewController *)controller previewItemAtIndex:(NSInteger)index
{
    
    return [NSURL fileURLWithPath:[self.filePathArr objectAtIndex:index]];
    
}
@end
