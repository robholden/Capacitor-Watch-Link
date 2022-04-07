package com.capacitorplugin.watchlink;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.wear.remote.interactions.RemoteActivityHelper;

import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeClient;
import com.google.android.gms.wearable.Wearable;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class WatchLink
{
    private MessageClient messageClient;
    private NodeClient nodeClient;
    private CapabilityClient capabilityClient;
    private RemoteActivityHelper remoteActivityHelper;

    public WatchLink(Context context)
    {
        this.messageClient = Wearable.getMessageClient(context);
        this.nodeClient = Wearable.getNodeClient(context);
        this.capabilityClient = Wearable.getCapabilityClient(context);
        this.remoteActivityHelper = new RemoteActivityHelper(context, Executors.newSingleThreadExecutor());
    }

    public Boolean hasCompanionAppInstalled(String capabilityId)
    {
        try
        {
            CapabilityInfo capabilityInfo = Tasks.await(capabilityClient.getCapability(capabilityId, CapabilityClient.FILTER_ALL));
            return !capabilityInfo.getNodes().isEmpty();
        }
        catch (ExecutionException | InterruptedException e)
        {
            return false;
        }
    }

    public WatchResult openPlayStoreWithoutApp(String capabilityId, String playStoreAppUri)
    {
        WatchResult result = new WatchResult(false, "");
        try
        {
            CapabilityInfo capabilityInfo = Tasks.await(capabilityClient.getCapability(capabilityId, CapabilityClient.FILTER_ALL));
            Set<Node> nodesWithApp = capabilityInfo.getNodes();
            List<Node> allNodes = Tasks.await(nodeClient.getConnectedNodes());

            if (allNodes.isEmpty() || allNodes.size() == nodesWithApp.size())
            {
                return new WatchResult(true, "All connected watches have companion app installed");
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(playStoreAppUri));

            for (Node node : allNodes)
            {
                if (nodesWithApp.contains(node)) continue;

                try
                {
                    remoteActivityHelper.startRemoteActivity(intent, node.getId());
                    result.ok = true;
                }
                catch (Exception innerException)
                {
                    result.error = innerException.getLocalizedMessage();
                }
            }
        }
        catch (ExecutionException | InterruptedException exception)
        {
            result.error = exception.getLocalizedMessage();
        }

        return result;
    }

    public WatchResult connected(Boolean nearbyOnly)
    {
        List<Node> nodes = getNodes(nearbyOnly);
        return new WatchResult(nodes.size() > 0, "");
    }

    public WatchResult sendMessage(String path, String value, Boolean nearbyOnly)
    {
        List<Node> sendToNodes = getNodes(nearbyOnly);

        if (sendToNodes.size() == 0)
        {
            return new WatchResult(false, "No watch(es) not connected");
        }

        try
        {
            for (Node node : sendToNodes)
            {
                Tasks.await(messageClient.sendMessage(node.getId(), path, value.getBytes()));
            }

            return new WatchResult(true, "");
        }
        catch (Exception ex)
        {
            return new WatchResult(false, ex.getLocalizedMessage());
        }
    }

    private List<Node> getNodes(Boolean nearbyOnly)
    {
        List<Node> foundNodes = new ArrayList<>();

        try
        {
            List<Node> connectedNodes = Tasks.await(nodeClient.getConnectedNodes());
            if (!nearbyOnly)
            {
                foundNodes = connectedNodes;
            } else
            {
                Node bestNode = getNearestNode(connectedNodes);
                if (bestNode != null) foundNodes.add(bestNode);
            }
        }
        catch (Exception ignored)
        {
        }

        return foundNodes;
    }

    private Node getNearestNode(List<Node> nodes)
    {
        // Find the nearby node
        for (Node node : nodes)
        {
            if (node.isNearby()) return node;
        }

        return null;
    }
}
