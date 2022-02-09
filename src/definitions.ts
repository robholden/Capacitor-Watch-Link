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
  connected(options?: WatchConnectedOptions): Promise<WatchLinkResult>;
  send(options: WatchSendOptions): Promise<WatchLinkResult>;
  listen(
    callback: (result: { [key: string]: string }) => any,
  ): Promise<WatchLinkResult>;
  unlisten(): Promise<void>;
}
