# watch-link

Watch link

## Install

```bash
npm install watch-link
npx cap sync
```

## API

<docgen-index>

* [`activate()`](#activate)
* [`send(...)`](#send)
* [`listen(...)`](#listen)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### activate()

```typescript
activate() => Promise<WatchLinkResult>
```

**Returns:** <code>Promise&lt;<a href="#watchlinkresult">WatchLinkResult</a>&gt;</code>

--------------------


### send(...)

```typescript
send(options: { message: string; type?: string; }) => Promise<WatchLinkResult>
```

| Param         | Type                                             |
| ------------- | ------------------------------------------------ |
| **`options`** | <code>{ message: string; type?: string; }</code> |

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


### Interfaces


#### WatchLinkResult

| Prop        | Type                 |
| ----------- | -------------------- |
| **`ok`**    | <code>boolean</code> |
| **`error`** | <code>string</code>  |

</docgen-api>
