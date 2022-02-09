package com.robholden.capacitorwatchlink;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class WatchLinkWearableListenerService extends WearableListenerService
{
    static Handler handler;

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        //This method will call while any message is posted by the watch to the phone.
        //This is message api, so if the phone is not connected message will be lost.
        //No guarantee of the message delivery
        if (this.handler == null) return;

        Bundle bundle = new Bundle();
        bundle.putString("path", messageEvent.getPath());
        bundle.putString("message", new String(messageEvent.getData()));
        Message msg = this.handler.obtainMessage();
        msg.setData(bundle);

        this.handler.sendMessage(msg);
    }
}
