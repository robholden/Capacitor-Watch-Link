import type {
  CapabilityOption,
  PlayStoreOptions,
  WatchConnectedOptions,
  WatchSendOptions,
} from './options';
import type { WatchLinkResult } from './result';

export interface WatchLinkPlugin {
  /**
   * [WatchOs ONLY]
   *
   * Will activate and resolve when the WCSession has been activated
   */
  activate(): Promise<WatchLinkResult>;

  /**
   * Returns if there's a watch paired to this device
   *
   * @param options connected options
   */
  paired(options?: WatchConnectedOptions): Promise<WatchLinkResult>;

  /**
   * Sends a message to a connected watch from this device
   *
   * [WearOS] => Sent via MessageClient
   * [WatchOS] => Sent via WCSession
   *
   * @param options configure data to send
   */
  send(options: WatchSendOptions): Promise<WatchLinkResult>;

  /**
   * Listens to messages sent from any connected watch to this device
   *
   * [WearOS] => Listening via MessageClient
   * [WatchOS] => Listening via WCSession
   *
   * @param callback Callback executed on a received message
   */
  listen(
    callback: (result: { [key: string]: string }) => any,
  ): Promise<WatchLinkResult>;

  /**
   * Stops listening to messages from any connected watch
   */
  unlisten(): Promise<void>;

  /**
   * [WearOs ONLY]
   * Opens the playstore on all watches that do not have the app installed
   */
  openPlayStoreOnWatchesWithoutApp(
    options: PlayStoreOptions,
  ): Promise<WatchLinkResult>;

  /**
   * Returns whether ANY connected watch has the app installed
   */
  hasCompanionAppInstalled(
    options: CapabilityOption,
  ): Promise<{ result: boolean }>;
}
