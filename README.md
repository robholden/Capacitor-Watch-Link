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

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#watchconnectedoptions">WatchConnectedOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### send(...)

```typescript
send(options: WatchSendOptions) => Promise<WatchLinkResult>
```

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#watchsendoptions">WatchSendOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### listen(...)

```typescript
listen(callback: (result: { [key: string]: string; }) => any) => Promise<WatchLinkResult>
```

| Param          | Type                                                        |
| -------------- | ----------------------------------------------------------- |
| **`callback`** | <code>(result: { [key: string]: string; }) =&gt; any</code> |

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### unlisten()

```typescript
unlisten() => Promise<void>
```

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
