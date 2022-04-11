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
