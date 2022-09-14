import WatchConnectivity
import Capacitor

class WatchLinkResult {
    let ok: Bool;
    let error: String!;

    init(ok: Bool, error: String! = nil) {
        self.ok = ok;
        self.error = error;
    }

    func toDict() -> [String: Any] {
        return [
            "ok": self.ok,
            "error": self.error ?? ""
        ]
    }
}

class WatchLink: NSObject, WCSessionDelegate {
    private let session: WCSession
    private var listenCallback: ([String: Any]) -> Void = { arg in }
    private var activateCallback: ((WatchLinkResult) -> Void)?

    init(session: WCSession = .default) {
        self.session = session
        super.init()
        self.session.delegate = self
    }

    func activate(callback: @escaping (WatchLinkResult) -> Void) -> Void {
        if (self.session.activationState != .activated) {
            print("Added activation callback")
            self.activateCallback = callback
            self.session.activate()
        }

        else {
            print("Session already activated")
            callback(WatchLinkResult(ok: true, error: ""))
        }
    }

    func reachable() -> WatchLinkResult {
        if (self.session.activationState != WCSessionActivationState.activated) {
            return WatchLinkResult(ok: false, error: "WCSession is not activated")
        }

        return WatchLinkResult(ok: self.session.isReachable)
    }

    func paired() -> WatchLinkResult {
        if (self.session.activationState != WCSessionActivationState.activated) {
            return WatchLinkResult(ok: false, error: "WCSession is not activated")
        }

        return WatchLinkResult(ok: self.session.isPaired)
    }

    func installed() -> Bool {
        if (self.session.activationState != WCSessionActivationState.activated) {
            return false
        }

        return self.session.isWatchAppInstalled
    }

    func send(message: String, path: String) -> WatchLinkResult {
        if (self.session.activationState != WCSessionActivationState.activated) {
            return WatchLinkResult(ok: false, error: "WCSession is not activated")
        }

        else if (self.session.isPaired == false) {
            return WatchLinkResult(ok: false, error: "Watch is not paired")
        }

        else if (self.session.isWatchAppInstalled == false) {
            return WatchLinkResult(ok: false, error: "Watch app is not installed")
        }

        else if (self.session.isReachable == false) {
            return WatchLinkResult(ok: false, error: "Watch app is not in the foreground")
        }

        session.sendMessage([path:message], replyHandler: nil) { (error: Error) -> Void in
            print ("Device failed to say \(path) => \(message) (\(error.localizedDescription)")
        }

        print("Device says \(path) => \(message)")
        return WatchLinkResult(ok: true)
    }

    func listen(callback: @escaping ([String: Any]) -> Void) -> WatchLinkResult {
        self.listenCallback = callback;

        return WatchLinkResult(ok: true, error: "This method will never error")
    }

    func session(_ session: WCSession, activationDidCompleteWith activationState: WCSessionActivationState, error: Error?) {
        if let callback = self.activateCallback {
            if let e = error {
                print("Session activation error \(e.localizedDescription)")
                callback(WatchLinkResult(ok: false, error: e.localizedDescription))
            } else {
                print("Session activated")
                callback(WatchLinkResult(ok: true, error: ""))
            }
        }
        else {
            print("Activated without callback")
        }
    }

    func session(_ session: WCSession, didReceiveMessage message: [String : Any]) {
        self.listenCallback(message)
    }

    func session(_ session: WCSession, didReceiveMessage message: [String : Any], replyHandler: @escaping ([String : Any]) -> Void) {
        self.listenCallback(message)
    }

    func sessionDidBecomeInactive(_ session: WCSession) {

    }

    func sessionDidDeactivate(_ session: WCSession) {

    }
}

