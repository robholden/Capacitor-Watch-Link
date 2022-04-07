import type { WatchLinkResult } from './result';

export interface CapabilityOption {
  /**
   * Watches capability id to use
   */
  capabilityId: string;
}

export interface PlayStoreOptions extends CapabilityOption {
  /**
   * The play store uri to open
   */
  playStoreAppUri: string;
}

export interface WearOsDefinitions {
  /**
   * [WearOs ONLY]
   * Opens the playstore on all watches that do not have the app installed
   */
  openPlayStoreOnWatchesWithoutApp(
    options: PlayStoreOptions,
  ): Promise<WatchLinkResult>;

  /**
   * [WearOs ONLY]
   * Returns whether ANY connected watch has the app installed
   */
  hasCompanionAppInstalled(
    options: CapabilityOption,
  ): Promise<{ result: boolean }>;
}
