import { WebPlugin } from '@capacitor/core';

import type { WatchLinkPlugin } from './definitions';

export class WatchLinkWeb extends WebPlugin implements WatchLinkPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
