import type { WatchLinkResult } from './result';

export interface WatchOsDefinitions {
  /**
   * [WatchOs ONLY]
   *
   * Will activate and resolve when the WCSession has been activated
   */
  activate(): Promise<WatchLinkResult>;
}
