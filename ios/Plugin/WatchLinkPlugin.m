#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(WatchLinkPlugin, "WatchLink",
           CAP_PLUGIN_METHOD(activate, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(paired, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(send, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(listen, CAPPluginReturnCallback);
           CAP_PLUGIN_METHOD(unlisten, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(hasCompanionAppInstalled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(openPlayStoreOnWatchesWithoutApp, CAPPluginReturnPromise);
)
