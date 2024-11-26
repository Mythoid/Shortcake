package io.github.mythoid.smp.shortcake.messages;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface IMessageHandler {

    void handleRegisterChannel(@NotNull String channel, @NotNull Player player);
    void handleUnregisterChannel(@NotNull String channel, @NotNull Player player);
    void handleIncomingMessage(@NotNull String channel, @NotNull Player player, @NotNull byte[] message);

}
