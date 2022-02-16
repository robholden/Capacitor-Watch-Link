# Capacitor Watch Link

A plugin for Capacitor to link an application to WatchOs and WearOs

## Install

TODO (not ready for production yet)

<!-- ```bash
npm install capacitor-watch-link
npx cap sync
``` -->

## API

<docgen-index>

* [`connected(...)`](#connected)
* [`send(...)`](#send)
* [`listen(...)`](#listen)
* [`unlisten()`](#unlisten)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### connected(...)

```typescript
connected(options?: WatchConnectedOptions | undefined) => Promise<WatchLinkResult>
```

Returns if there's a watch connected to this device

| Param         | Type                                                                    | Description       |
| ------------- | ----------------------------------------------------------------------- | ----------------- |
| **`options`** | <code><a href="#watchconnectedoptions">WatchConnectedOptions</a></code> | connected options |

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### send(...)

```typescript
send(options: WatchSendOptions) => Promise<WatchLinkResult>
```

Sends a message to a connected watch from this device

[WearOS] =&gt; Sent via MessageClient
[WatchOS] =&gt; Sent via WCSession

| Param         | Type                                                          | Description            |
| ------------- | ------------------------------------------------------------- | ---------------------- |
| **`options`** | <code><a href="#watchsendoptions">WatchSendOptions</a></code> | configure data to send |

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### listen(...)

```typescript
listen(callback: (result: { [key: string]: string; }) => any) => Promise<WatchLinkResult>
```

Listens to messages sent from any connected watch to this device

[WearOS] =&gt; Listening via MessageClient
[WatchOS] =&gt; Listening via WCSession

| Param          | Type                                                        | Description                             |
| -------------- | ----------------------------------------------------------- | --------------------------------------- |
| **`callback`** | <code>(result: { [key: string]: string; }) =&gt; any</code> | Callback executed on a received message |

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### unlisten()

```typescript
unlisten() => Promise<void>
```

Stops listening to messages from any connected watch

--------------------


### Interfaces


#### WatchLinkResult

| Prop        | Type                 |
| ----------- | -------------------- |
| **`ok`**    | <code>boolean</code> |
| **`error`** | <code>string</code>  |


#### WatchConnectedOptions

| Prop             | Type                 | Description                                                 |
| ---------------- | -------------------- | ----------------------------------------------------------- |
| **`nearbyOnly`** | <code>boolean</code> | [WearOS ONLY] Set to [true] to only look for nearby watches |


#### WatchSendOptions

| Prop             | Type                 | Description                                                             |
| ---------------- | -------------------- | ----------------------------------------------------------------------- |
| **`path`**       | <code>string</code>  | [WearOS] =&gt; the message prefix [WatchOs] =&gt; the message key       |
| **`message`**    | <code>string</code>  | The message to send to the watch(es)                                    |
| **`nearbyOnly`** | <code>boolean</code> | [WearOS ONLY] Set to [true] to only send to the nearest connected watch |

</docgen-api>
