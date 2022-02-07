export interface WatchLinkPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
