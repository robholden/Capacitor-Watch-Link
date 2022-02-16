package com.robholden.capacitorwatchlinkexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import com.robholden.capacitorwatchlinkexample.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity
{

  private TextView mTextView;
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    mTextView = binding.text;

    this.listenForMessages();
  }

  private void listenForMessages()
  {
    Receiver messageReceiver = new Receiver();
    IntentFilter newFilter = new IntentFilter();
    newFilter.addAction(WatchWearableListenerService.TEST_ACTION);
    LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, newFilter);
  }

  class Receiver extends BroadcastReceiver
  {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      switch (intent.getAction())
      {
        case WatchWearableListenerService.TEST_ACTION:
          String deviceMessage = intent.getStringExtra("value");
          mTextView.setText(deviceMessage);

          int max = 2;
          int min = 1;
          int range = max - min + 1;
          int rand = (int)(Math.random() * range) + min;
          Boolean happy = rand == 1;
          new SendMessage("/test-device-path", "{\"answer\": \"" + deviceMessage  + "\", \"happy\": " + (happy ? "true": "false") + "}").start();
          break;
      }
    }
  }

  class SharedInterface {
    String answer;
    Boolean happy;
  }

  class SendMessage extends Thread
  {
    private boolean sending = false;

    String path;
    String message;

    SendMessage(String p, String m)
    {
      path = p;
      message = m;
    }

    public void run()
    {
      if (this.sending)
      {
        return;
      }

      this.sending = true;

      Task<List<Node>> nodeListTask = Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
      try
      {
        List<Node> nodes = Tasks.await(nodeListTask);
        for (Node node : nodes)
        {
          Task<Integer> sendMessageTask = Wearable.getMessageClient(MainActivity.this).sendMessage(node.getId(), path, message.getBytes());

          try
          {
            Integer result = Tasks.await(sendMessageTask);
          }
          catch (ExecutionException exception)
          {
            Log.e("sendMessageInner", exception.getMessage());
          }
          catch (InterruptedException exception)
          {
            Log.e("sendMessageInner", exception.getMessage());
          }
        }

      }
      catch (ExecutionException exception)
      {
        Log.e("sendMessage", exception.getMessage());
      }
      catch (InterruptedException exception)
      {
        Log.e("sendMessage", exception.getMessage());
      }

      this.sending = false;
    }
  }
}
