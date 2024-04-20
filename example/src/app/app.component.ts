import { Component } from '@angular/core';

import { timer } from 'rxjs';

import { Platform } from '@ionic/angular';

import { WatchLink } from 'capacitor-plugin-watch-link';

interface SomeSharedInterface {
  answer: string;
  happy: boolean;
}

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  unsupported = false;

  watchIsConnected = false;
  messages: SomeSharedInterface[] = [];

  constructor(private platform: Platform) {
    this.initializeApp();
  }

  async initializeApp() {
    await this.platform.ready();

    this.unsupported = !this.platform.is('cordova');
    if (!this.unsupported) this.setupWatchEvents();
  }

  private async setupWatchEvents() {
    const activated = await WatchLink.activate();
    this.watchIsConnected = false;

    if (!activated.ok) {
      alert(`Couldn't activate watch session => ${activated.error}`);
      return;
    }

    // Listen to messages from the watch to this device
    WatchLink.listen(message => {
      // Only handle /test-device-path
      if (message['/test-device-path'])
        this.messages.unshift(JSON.parse(message['/test-device-path']));
    });

    // Poll every 10 seconds to see if there's a connected watch
    timer(0, 10 * 1000).subscribe(async (index: number) => {
      const result = await WatchLink.paired({ nearbyOnly: true });
      this.watchIsConnected = result.ok;

      // Send the watch a message
      if (this.watchIsConnected)
        await WatchLink.send({
          path: '/test-watch-path',
          message: `Device says ${index} is my favourite number`,
        });
    });
  }
}
