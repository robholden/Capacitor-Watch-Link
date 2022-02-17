package com.capacitorplugin.watchlink;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.ChannelClient;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeClient;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WatchLink {

    private MessageClient messageClient;
    private NodeClient nodeClient;

    public WatchLink(Context context) {
        this.messageClient = Wearable.getMessageClient(context);
        this.nodeClient = Wearable.getNodeClient(context);
    }

    public WatchResult connected(Boolean nearbyOnly) {
        List<Node> nodes = getNodes(nearbyOnly);
        return new WatchResult(nodes.size() > 0, "");
    }

    public WatchResult sendMessage(String path, String value, Boolean nearbyOnly) {
        List<Node> sendToNodes = getNodes(nearbyOnly);

        if (sendToNodes.size() == 0) {
            return new WatchResult(false, "No watch(es) not connected");
        }

        try {
            for (Node node : sendToNodes) {
                Integer result = Tasks.await(messageClient.sendMessage(node.getId(), path, value.getBytes()));
            }

            return new WatchResult(true, "");
        } catch (Exception ex) {
            return new WatchResult(false, ex.getLocalizedMessage());
        }
    }

    private List<Node> getNodes(Boolean nearbyOnly) {
        List<Node> foundNodes = new ArrayList<>();

        try {
            List<Node> connectedNodes = Tasks.await(nodeClient.getConnectedNodes());
            if (!nearbyOnly) {
                foundNodes = connectedNodes;
            } else {
                Node bestNode = getNearestNode(connectedNodes);
                if (bestNode != null) foundNodes.add(bestNode);
            }
        } catch (Exception ex) {}

        return foundNodes;
    }

    private Node getNearestNode(List<Node> nodes) {
        // Find the nearby node
        for (Node node : nodes) {
            if (node.isNearby()) return node;
        }

        return null;
    }
}
