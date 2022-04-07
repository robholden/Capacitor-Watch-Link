import { registerPlugin } from '@capacitor/core';

import type { WatchLinkPlugin } from './definitions';

const WatchLink = registerPlugin<WatchLinkPlugin>('WatchLink', {});

export * from './android';
export * from './definitions';
export * from './ios';
export * from './result';
export { WatchLink };
