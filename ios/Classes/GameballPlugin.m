#import "GameballPlugin.h"
#if __has_include(<gameball/gameball-Swift.h>)
#import <gameball/gameball-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "gameball-Swift.h"
#endif

@implementation GameballPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftGameballPlugin registerWithRegistrar:registrar];
}
@end
