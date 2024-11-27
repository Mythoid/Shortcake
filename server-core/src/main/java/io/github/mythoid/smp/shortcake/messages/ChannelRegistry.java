package io.github.mythoid.smp.shortcake.messages;

import io.github.mythoid.smp.shortcake.ServerCore;
import io.github.mythoid.smp.shortcake.messages.handlers.JourneyMapMessageHandler;
import io.github.mythoid.smp.shortcake.messages.handlers.VoxelMapMessageHandler;
import io.github.mythoid.smp.shortcake.messages.handlers.XaeroMessageHandler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ChannelRegistry {

    private static ChannelRegistry instance;

    private HashMap<String, MessageHandler> channelsToHandlers = new HashMap<>();

    private ChannelRegistry(@NotNull ServerCore plugin) {
        channelsToHandlers.put("xaeroworldmap:main", new XaeroMessageHandler(plugin));
        channelsToHandlers.put("xaerominimap:main", new XaeroMessageHandler(plugin));
        channelsToHandlers.put("voxelmap:settings", new VoxelMapMessageHandler(plugin));
        channelsToHandlers.put("journeymap:perm_req", new JourneyMapMessageHandler(plugin));
    }

    public HashMap<String, MessageHandler> getHandlers() {
        return channelsToHandlers;
    }

    public static synchronized ChannelRegistry get(@NotNull ServerCore plugin) {
        if (instance == null) {
            instance = new ChannelRegistry(plugin);
        }
        return instance;
    }



}
