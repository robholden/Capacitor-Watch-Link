# Capacitor Watch Link

A plugin for Capacitor to link an application to WatchOs and WearOs

## TODO: Install

```bash
#npm install capacitor-plugin-watch-link
#npx cap sync
```

## AndroidManifest Setup

- You must ensure the package id matches both your `App` and `Watch` application
- Register plugin's listener service with defined path prefixes (App `AndroidManifest.xml`)

```
<service android:name="com.capacitorplugin.watchlink.WatchLinkWearableListenerService" android:exported="true">
    <intent-filter>
    <action android:name="com.google.android.gms.wearable.MESSAGE_RECEIVED" />
    <data android:scheme="wear" android:host="*" android:pathPrefix="/test-device-path" />
    </intent-filter>
</service>
```

## Example

You can view an example integration in Ionic Angular between both WearOS and WatchOS in `/example` directory

```typescript
// Listen to messages from the watch to this device
WatchLink.listen(data => {

    // Only handle /test-device-path
    if (data['/test-device-path']) {
        var message = dats['/test-device-path']));
        console.log(message);
    }

});

// Activate connection
WatchLink.activate().then((result) => {

    // Check activation result
    if (!result.ok) {
        alert(`Couldn't activate watch session => ${result.error}`);
        return;
    }

    // Poll every 10 seconds to see if there's a connected watch
    timer(0, 10 * 1000).subscribe(async (index: number) => {
        const result = await WatchLink.paired({ nearbyOnly: true });

        // Send the watch a message
        if (result.ok) {
            await WatchLink.send({
                path: '/test-watch-path',
                message: `Device says ${index} is my favourite number`,
            });
        }
    });

});
```

## API

<docgen-index>

* [`activate()`](#activate)
* [`paired(...)`](#paired)
* [`reachable()`](#reachable)
* [`send(...)`](#send)
* [`listen(...)`](#listen)
* [`unlisten()`](#unlisten)
* [`openPlayStoreOnWatchesWithoutApp(...)`](#openplaystoreonwatcheswithoutapp)
* [`hasCompanionAppInstalled(...)`](#hascompanionappinstalled)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### activate()

```typescript
activate() => Promise<WatchLinkResult>
```

[WatchOs ONLY]

Will activate and resolve when the WCSession has been activated

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### paired(...)

```typescript
paired(options?: WatchConnectedOptions | undefined) => Promise<WatchLinkResult>
```

Returns if there's a watch paired to this device

| Param         | Type                                                                    | Description       |
| ------------- | ----------------------------------------------------------------------- | ----------------- |
| **`options`** | <code><a href="#watchconnectedoptions">WatchConnectedOptions</a></code> | connected options |

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### reachable()

```typescript
reachable() => Promise<WatchLinkResult>
```

[WatchOS ONLY]
Returns if the watch is reachable to this device

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


### openPlayStoreOnWatchesWithoutApp(...)

```typescript
openPlayStoreOnWatchesWithoutApp(options: PlayStoreOptions) => Promise<WatchLinkResult>
```

[WearOs ONLY]
Opens the playstore on all watches that do not have the app installed

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#playstoreoptions">PlayStoreOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### hasCompanionAppInstalled(...)

```typescript
hasCompanionAppInstalled(options: CapabilityOption) => Promise<{ result: boolean; }>
```

Returns whether ANY connected watch has the app installed

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#capabilityoption">CapabilityOption</a></code> |

**Returns:** <code>Promise&lt;{ result: boolean; }&gt;</code>

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


#### PlayStoreOptions

| Prop                  | Type                | Description                |
| --------------------- | ------------------- | -------------------------- |
| **`playStoreAppUri`** | <code>string</code> | The play store uri to open |


#### CapabilityOption

| Prop               | Type                | Description                                |
| ------------------ | ------------------- | ------------------------------------------ |
| **`capabilityId`** | <code>string</code> | [WearOs ONLY] Watches capability id to use |

</docgen-api>
