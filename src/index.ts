import { registerPlugin } from '@capacitor/core';

import type { WatchLinkPlugin } from './definitions';

const WatchLink = registerPlugin<WatchLinkPlugin>('WatchLink', {});

export * from './definitions';
export { WatchLink };
