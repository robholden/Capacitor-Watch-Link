import { registerPlugin } from '@capacitor/core';

import type { WatchLinkPlugin } from './definitions';

const WatchLink = registerPlugin<WatchLinkPlugin>('WatchLink', {
  web: () => import('./web').then(m => new m.WatchLinkWeb()),
});

export * from './definitions';
export { WatchLink };
