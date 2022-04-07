package com.capacitorplugin.watchlink;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class WatchLinkWearableListenerService extends WearableListenerService {

    static Handler handler;

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        //This method will call while any message is posted by the watch to the phone.
        //This is message api, so if the phone is not connected message will be lost.
        //No guarantee of the message delivery
        Bundle bundle = new Bundle();
        bundle.putString("path", messageEvent.getPath());
        bundle.putString("message", new String(messageEvent.getData()));
        Message msg = handler.obtainMessage();
        msg.setData(bundle);

        Log.d("WatchLinkWearableListenerService.Received", msg.toString());

        if (handler != null) handler.sendMessage(msg);
    }
}
