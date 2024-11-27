package io.github.mythoid.smp.shortcake.messages.handlers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.mythoid.smp.shortcake.ServerCore;
import io.github.mythoid.smp.shortcake.messages.MessageHandler;
import io.github.mythoid.smp.shortcake.messages.util.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class JourneyMapMessageHandler extends MessageHandler {

    public JourneyMapMessageHandler(ServerCore plugin) {
        super(plugin);
    }

    @Override
    public void handleRegisterChannel(@NotNull String channel, @NotNull Player player) {}

    @Override
    public void handleUnregisterChannel(@NotNull String channel, @NotNull Player player) {}

    @Override
    public void handleIncomingMessage(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        // Ignore incoming message, send back valid message
        player.sendMessage(Component.text("JourneyMap is not supported on this server.")
                .color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
        player.sendPluginMessage(plugin, channel, settings());
    }

    byte[] settings() {
        Map<String, Object> settingsMap = new HashMap<>();
        settingsMap.put("journeymapEnabled", false);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(settingsMap);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(42);
        out.writeBoolean(false);
        out.write(MessageUtil.toMCProtocolString(json));

        return out.toByteArray();
    }

}
