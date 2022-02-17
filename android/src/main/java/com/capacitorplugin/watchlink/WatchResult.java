package com.capacitorplugin.watchlink;

import com.getcapacitor.JSObject;

public class WatchResult {

    public boolean ok;
    public String error;

    public WatchResult(boolean ok, String error) {
        this.ok = ok;
        this.error = error;
    }

    public JSObject ToJson() {
        JSObject ret = new JSObject();

        ret.put("ok", this.ok);
        ret.put("error", this.error);

        return ret;
    }
}
