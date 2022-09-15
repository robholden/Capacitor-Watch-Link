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
    public void activate(PluginCall call) {
        WatchResult result = new WatchResult(true, "");
        call.resolve(result.toJson());
    }

    @PluginMethod
    public void reachable(PluginCall call) {
        WatchResult result = implementation.reachable();
        call.resolve(result.toJson());
    }

    @PluginMethod
    public void paired(PluginCall call) {
        Boolean nearbyOnly = call.hasOption("nearbyOnly") ? call.getBoolean("nearbyOnly") : false;
        WatchResult result = implementation.paired(nearbyOnly);
        call.resolve(result.toJson());
    }

    @PluginMethod
    public void send(PluginCall call) {
        String path = call.getString("path");
        String message = call.getString("message");
        Boolean nearbyOnly = call.hasOption("nearbyOnly") ? call.getBoolean("nearbyOnly") : false;

        WatchResult result = implementation.sendMessage(path, message, nearbyOnly);
        Log.d("WatchLinkPlugin.Send", path + " => " + message);
        call.resolve(result.toJson());
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void listen(PluginCall call) throws JSONException {
        call.setKeepAlive(true);
        Log.d("WatchLinkPlugin.Listen", "Handler received. Listening for messages");

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

        call.resolve(new WatchResult(true, "").toJson());
    }

    @PluginMethod
    public void unlisten(PluginCall call) {
        WatchLinkWearableListenerService.handler = null;
        Log.d("WatchLinkPlugin.Listen", "Stopped listening for messages");

        if (this.call != null) {
            this.call.setKeepAlive(false);
            this.call = null;
        }

        call.resolve(new WatchResult(true, "").toJson());
    }

    @PluginMethod
    public void hasCompanionAppInstalled(PluginCall call) {
        String id = call.getString("capabilityId");

        Boolean result = implementation.hasCompanionAppInstalled(id);
        Log.d("WatchLinkPlugin.HasCompanionAppInstalled", "Check received for Id: " + id);

        JSObject ret = new JSObject();
        ret.put("result", result);
        call.resolve(ret);
    }

    @PluginMethod
    public void openPlayStoreOnWatchesWithoutApp(PluginCall call) {
        String id = call.getString("capabilityId");
        String uri = call.getString("playStoreAppUri");

        WatchResult result = implementation.openPlayStoreWithoutApp(id, uri);
        Log.d("WatchLinkPlugin.OpenPlayStoreOnWatchesWithoutApp", "Play Store request sent. Id: " + id + ", URI: " + uri);
        call.resolve(result.toJson());
    }
}
