import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(WatchLinkPlugin)
public class WatchLinkPlugin: CAPPlugin {
    private let implementation = WatchLink()
    private var call: CAPPluginCall?

    @objc func activate(_ call: CAPPluginCall) {
        call.resolve(implementation.activate().toDict())
    }
    
    @objc func send(_ call: CAPPluginCall) {
        let type = call.getString("type") ?? "message"
        let message = call.getString("message") ?? ""
        call.resolve(implementation.send(message: message, type: type).toDict())
    }
    
    @objc func listen(_ call: CAPPluginCall) {
        call.keepAlive = true
        self.call = call
        
        _ = implementation.listen() { (message: [String: Any]) -> Void in
            if let callback = self.call {
                callback.resolve(message)
            }
        }
    }
    
    @objc func unlisten(_ call: CAPPluginCall) {
        if let callback = self.call {
            callback.keepAlive = false
            self.call = nil;
        }
        
        call.resolve();
    }
}
