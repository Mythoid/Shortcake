package io.github.mythoid.smp.shortcake.messages;

import io.github.mythoid.smp.shortcake.ServerCore;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class MessageHandler implements IMessageHandler {

    public ServerCore plugin;

    public MessageHandler(ServerCore plugin) {
        this.plugin = plugin;
    }

    class MessageListener implements PluginMessageListener {
        @Override
        public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] bytes) {
            handleIncomingMessage(channel, player, bytes);
        }
    }

    MessageListener listener = new MessageListener();

}
