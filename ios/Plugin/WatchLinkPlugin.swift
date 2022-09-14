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
        implementation.activate() { (result: WatchLinkResult) -> Void in
            call.resolve(result.toDict())
        }
    }

    @objc func paired(_ call: CAPPluginCall) {
        call.resolve(implementation.paired().toDict())
    }

    @objc func reachable(_ call: CAPPluginCall) {
        call.resolve(implementation.reachable().toDict())
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
                print("Watch says \(message)")
                callback.resolve(message)
            }
        }
    }

    @objc func unlisten(_ call: CAPPluginCall) {
        if let callback = self.call {
            callback.keepAlive = false
            self.call = nil;
        }

        call.resolve()
    }

    @objc func hasCompanionAppInstalled(_ call: CAPPluginCall) {
        call.resolve(["result": implementation.installed()])
    }

    @objc func openPlayStoreOnWatchesWithoutApp(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }
}
