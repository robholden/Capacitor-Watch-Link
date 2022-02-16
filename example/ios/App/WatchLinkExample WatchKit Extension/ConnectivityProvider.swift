//
//  ConnectivityProvider.swift
//  Watch WatchKit Extension
//
//  Created by Robert on 05/02/2022.
//

import WatchConnectivity

protocol WatchMessageListener: AnyObject {
    func onReceive(message: String)
}

class ConnectivityProvider: NSObject, WCSessionDelegate {
    private(set) var events: EventManager
    private let session: WCSession
    
    init(events: EventManager, session: WCSession = .default) {
        self.events = events
        self.session = session
        super.init()
        self.session.delegate = self
        self.connect()
    }
    
    func connect() {
        guard WCSession.isSupported() else {
            print("WCSession is not supported")
            return
        }
       
        session.activate()
    }
    
    func connected() -> Bool {
        return session.isReachable;
    }
    
    func send(path: String, message: String) -> Void {
        session.sendMessage([path:message], replyHandler: nil, errorHandler: nil)
    }
    
    func session(_ session: WCSession, activationDidCompleteWith activationState: WCSessionActivationState, error: Error?) {
        // code
    }
    
    func session(_ session: WCSession, didReceiveMessage message: [String : Any]) {
        parse(message: message);
    }
    
    func session(_ session: WCSession, didReceiveMessage message: [String : Any], replyHandler: @escaping ([String : Any]) -> Void) {
        parse(message: message);
    }
    
    func parse(message: [String : Any]) {
        print(message);
        
        for key in message.keys {
            events.trigger(eventName: key, information: message[key])
        }
    }
}
