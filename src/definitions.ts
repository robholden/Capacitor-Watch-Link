export interface WatchLinkResult {
  ok: boolean;
  error: string;
}

export interface WatchConnectedOptions {
  /**
   * [WearOS ONLY]
   *
   * Set to [true] to only look for nearby watches
   */
  nearbyOnly?: boolean;
}

export interface WatchSendOptions {
  /**
   * [WearOS] => the message prefix
   * [WatchOs] => the message key
   */
  path: string;

  /**
   * The message to send to the watch(es)
   */
  message: string;

  /**
   * [WearOS ONLY]
   *
   * Set to [true] to only send to the nearest connected watch
   */
  nearbyOnly?: boolean;
}

export interface WatchLinkPlugin {
  /**
   * Returns if there's a watch connected to this device
   *
   * @param options connected options
   */
  connected(options?: WatchConnectedOptions): Promise<WatchLinkResult>;

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
}
