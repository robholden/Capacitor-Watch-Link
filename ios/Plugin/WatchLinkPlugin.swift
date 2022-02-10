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

    @objc func connected(_ call: CAPPluginCall) {
        call.resolve(implementation.connected().toDict())
    }

    @objc func send(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.resolve(WatchLinkResult(ok: false, error: "Path is required").toDict())
            return
        }

        guard let message = call.getString("message") else {
            call.resolve(WatchLinkResult(ok: false, error: "Message is required").toDict())
            return
        }

        call.resolve(implementation.send(message: message, path: path).toDict())
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
