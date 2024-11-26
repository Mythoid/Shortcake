package io.github.mythoid.smp.shortcake;

import io.github.mythoid.smp.shortcake.listener.CustomChannelListener;
import io.github.mythoid.smp.shortcake.messages.ChannelRegistry;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ServerCore extends JavaPlugin {

    @Getter
    private static ServerCore plugin;

    @Override
    public void onDisable() {
        plugin.getServer().getMessenger().unregisterIncomingPluginChannel(plugin);
        plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(plugin);

        plugin = null;
    }

    @Override
    public void onEnable() {
        plugin = this;

        // Plugin Messages
        // Register I/O plugin channels
        ChannelRegistry.get(plugin).getHandlers().forEach(
                (channel, handler) -> {
                    plugin.getLogger().log(Level.INFO, "Registering plugin channel: " + channel);
                    plugin.getServer().getMessenger().registerIncomingPluginChannel(
                            plugin, channel, handler.getListener()
                    );
                    plugin.getServer().getMessenger().registerOutgoingPluginChannel(
                            plugin, channel
                    );
                }
        );

        // Register Listeners
        plugin.getServer().getPluginManager().registerEvents(new CustomChannelListener(), plugin);

        plugin.getLogger().log(Level.INFO, "Shortcake Server Core is enabled!");

    }
}
