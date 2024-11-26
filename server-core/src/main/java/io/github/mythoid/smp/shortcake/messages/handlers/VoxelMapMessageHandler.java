package io.github.mythoid.smp.shortcake.messages.handlers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.mythoid.smp.shortcake.ServerCore;
import io.github.mythoid.smp.shortcake.messages.MessageHandler;
import io.github.mythoid.smp.shortcake.messages.util.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class VoxelMapMessageHandler extends MessageHandler {

    public VoxelMapMessageHandler(ServerCore plugin) {
        super(plugin);
    }

    @Override
    public void handleRegisterChannel(@NotNull String channel, @NotNull Player player) {
        player.sendMessage(Component.text("VoxelMap is not supported on this server.")
                .color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
        player.sendPluginMessage(plugin, channel, settings());
    }

    @Override
    public void handleUnregisterChannel(@NotNull String channel, @NotNull Player player) {}

    @Override
    public void handleIncomingMessage(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {}

    byte[] settings() {
        Map<String, Object> settingsMap = new HashMap<>();
        settingsMap.put("minimapAllowed", false);
        settingsMap.put("worldmapAllowed", false);
        settingsMap.put("waypointsAllowed", false);
        settingsMap.put("deathWaypointAllowed", false);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(settingsMap);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(0);
        out.write(MessageUtil.toMCProtocolString(json));

        return out.toByteArray();
    }

}
