package com.capacitorplugin.watchlinkexample;

import android.content.Intent;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class WatchWearableListenerService extends WearableListenerService {

    public static final String TEST_ACTION = "test-action";
    public static final String TEST_PATH = "/test-watch-path";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        //This method will call while any message is posted by the watch to the phone.
        //This is message api, so if the phone is not connected message will be lost.
        //No guarantee of the message delivery
        Log.d("onMessageReceived: ", messageEvent.getPath());

        //check path of the message
        if (messageEvent.getPath().equalsIgnoreCase(TEST_PATH)) {
            //Extract the values
            String value = new String(messageEvent.getData());

            //send broadcast to update the UI in MainActivity based on the tracking status
            Intent intent = new Intent(TEST_ACTION);
            intent.putExtra("value", value);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}
