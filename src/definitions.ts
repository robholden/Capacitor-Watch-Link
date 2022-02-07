export interface WatchLinkResult {
  ok: boolean;
  error: string;
}

export interface WatchLinkPlugin {
  activate(): Promise<WatchLinkResult>;
  send(options: { message: string; type?: string }): Promise<WatchLinkResult>;
  listen(
    callback: (result: { [key: string]: string }) => any,
  ): Promise<WatchLinkResult>;
  unlisten(): Promise<void>;
}
