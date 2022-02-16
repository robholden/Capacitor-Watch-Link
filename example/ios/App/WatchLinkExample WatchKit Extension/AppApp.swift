//
//  AppApp.swift
//  WatchLinkExample WatchKit Extension
//
//  Created by Robert on 16/02/2022.
//

import SwiftUI

@main
struct AppApp: App {
    private(set) var events: EventManager
    private(set) var connectivityProvider: ConnectivityProvider
    
    var body: some Scene {
        WindowGroup {
            NavigationView {
                ContentView(viewModel: ViewModel(connectivityProvider: self.connectivityProvider, events: self.events))
            }
        }
    }
    
    init() {
        self.events = EventManager()
        self.connectivityProvider = ConnectivityProvider(events: self.events)
    }
}
