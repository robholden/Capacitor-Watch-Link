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
    private var callback: ([String: Any]) -> Void = { arg in }

    init(session: WCSession = .default) {
        self.session = session
        super.init()
        self.session.delegate = self
    }
    
    func activate() -> WatchLinkResult {
        guard WCSession.isSupported() else {
            return WatchLinkResult(ok: false, error: "WCSession is not supported")
        }
       
        session.activate()
        return WatchLinkResult(ok: true)
    }
    
    func send(message: String, type: String = "message") -> WatchLinkResult {
        if (self.session.activationState != WCSessionActivationState.activated) {
            return WatchLinkResult(ok: false, error: "WCSession is not activated (\(self.session.activationState)")
        }
        
        else if (!self.session.isReachable) {
            return WatchLinkResult(ok: false, error: "Watch is not reachable")
        }
        
        print("Sending [\(type):\(message)] to watch")
        session.sendMessage([type:message], replyHandler: nil, errorHandler: nil)
        return WatchLinkResult(ok: true)
    }
    
    func listen(callback: @escaping ([String: Any]) -> Void) -> WatchLinkResult {
        self.callback = callback;
        
        return WatchLinkResult(ok: true, error: "FYI: this method will never error")
    }
    
    func session(_ session: WCSession, activationDidCompleteWith activationState: WCSessionActivationState, error: Error?) {
        // code
    }
    
    func session(_ session: WCSession, didReceiveMessage message: [String : Any]) {
        print("session 1 => \(message)")
        self.callback(message)
    }
    
    func session(_ session: WCSession, didReceiveMessage message: [String : Any], replyHandler: @escaping ([String : Any]) -> Void) {
        print("session 2 => \(message)")
        self.callback(message)
    }
    
    func sessionDidBecomeInactive(_ session: WCSession) {
        
    }
    
    func sessionDidDeactivate(_ session: WCSession) {
        
    }
}

