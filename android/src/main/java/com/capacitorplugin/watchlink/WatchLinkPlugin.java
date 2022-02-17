package com.capacitorplugin.watchlink;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import org.json.JSONException;

@CapacitorPlugin(name = "WatchLink")
public class WatchLinkPlugin extends Plugin {

    private WatchLink implementation;
    private PluginCall call;

    @Override
    public void load() {
        implementation = new WatchLink(getContext());
    }

    @PluginMethod
    public void connected(PluginCall call) {
        Boolean nearbyOnly = call.hasOption("nearbyOnly") ? call.getBoolean("nearbyOnly") : false;
        WatchResult result = implementation.connected(nearbyOnly);
        call.resolve(result.ToJson());
    }

    @PluginMethod
    public void send(PluginCall call) {
        String path = call.getString("path");
        String message = call.getString("message");
        Boolean nearbyOnly = call.hasOption("nearbyOnly") ? call.getBoolean("nearbyOnly") : false;

        WatchResult result = implementation.sendMessage(path, message, nearbyOnly);
        call.resolve(result.ToJson());
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void listen(PluginCall call) throws JSONException {
        call.setKeepAlive(true);

        this.call = call;
        WatchLinkWearableListenerService.handler =
            new Handler(
                msg -> {
                    Bundle stuff = msg.getData();
                    String path = stuff.getString("path");
                    String message = stuff.getString("message");

                    Log.d("WatchLinkPlugin.Listen", path + " => " + message);

                    JSObject ret = new JSObject();
                    ret.put(path, message);

                    call.resolve(ret);

                    return true;
                }
            );

        call.resolve(new WatchResult(true, "").ToJson());
    }

    @PluginMethod
    public void unlisten(PluginCall call) {
        WatchLinkWearableListenerService.handler = null;

        if (this.call != null) {
            this.call.setKeepAlive(false);
            this.call = null;
        }

        call.resolve(new WatchResult(true, "").ToJson());
    }
}
