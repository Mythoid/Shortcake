package io.github.mythoid.smp.shortcake.listener;

import io.github.mythoid.smp.shortcake.ServerCore;
import io.github.mythoid.smp.shortcake.messages.ChannelRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;

public class CustomChannelListener implements Listener {

    ChannelRegistry registry = ChannelRegistry.get(ServerCore.getPlugin());

    @EventHandler
    public void onPlayerRegisterChannel(PlayerRegisterChannelEvent event) {
        if (!registry.getHandlers().containsKey(event.getChannel())) return;
        registry.getHandlers().get(event.getChannel())
                .handleRegisterChannel(
                event.getChannel(), event.getPlayer()
        );
    }

    @EventHandler
    public void onPlayerUnregisterChannel(PlayerUnregisterChannelEvent event) {
        if (!registry.getHandlers().containsKey(event.getChannel())) return;
        registry.getHandlers().get(event.getChannel())
                .handleUnregisterChannel(
                event.getChannel(), event.getPlayer()
        );
    }

}
