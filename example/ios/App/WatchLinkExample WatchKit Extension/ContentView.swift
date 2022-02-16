//
//  ContentView.swift
//  WatchLinkExample WatchKit Extension
//
//  Created by Robert on 16/02/2022.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject var viewModel: ViewModel
    
    var body: some View {
        Text(viewModel.message)
            .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        let events = EventManager()
        let viewModel = ViewModel(connectivityProvider: ConnectivityProvider(events: events), events: events)
        
        viewModel.message = "Just a test :)"
        
        return ContentView(viewModel: viewModel)
    }
}
