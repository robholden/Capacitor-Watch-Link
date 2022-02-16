//
//  ViewModel.swift
//  WatchLinkExample WatchKit Extension
//
//  Created by Robert on 16/02/2022.
//

import Foundation

final class ViewModel: ObservableObject {
    private(set) var connectivityProvider: ConnectivityProvider
    private(set) var events: EventManager
    
    @Published var message: String = "Hello World."

    init(connectivityProvider: ConnectivityProvider, events: EventManager) {
        self.connectivityProvider = connectivityProvider
        self.events = events
        
        connectivityProvider.connect()
        
        listen()
    }
    
    func listen() {
        events.listenTo(eventName: "/test-watch-path") { (information: Any?) ->() in
            if let message = information as? String {
                DispatchQueue.main.async {
                    self.message = message
                    self.reply(message: message)
                }
            }
        }
    }

    func reply(message: String) -> Void {
        let happy = round(Float.random(in: 1..<2))
        connectivityProvider.send(path: "/test-device-path", message: "{ \"answer\": \"\(message)\", \"happy\": \(happy == 1) }")
    }
}
