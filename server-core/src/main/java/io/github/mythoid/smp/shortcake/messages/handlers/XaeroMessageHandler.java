package io.github.mythoid.smp.shortcake.messages.handlers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.mythoid.smp.shortcake.ServerCore;
import io.github.mythoid.smp.shortcake.messages.MessageHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class XaeroMessageHandler extends MessageHandler {

    /*  Packet IDs
        0 = LevelMapProperties
        1 = Handshake
        2 = ClientboundTrackedPlayerPacket
        3 = ClientboundPlayerTrackerResetPacket
        4 = ClientboundRulesPacket
     */

    @Getter
    private List<Player> allowlist = new ArrayList<>();

    private final String CHERRY_CHANNEL = "cherryontop:xaero";

    private final TextComponent noMinimap = Component
            .text("\u00A7n\u00A7o\u00A7m\u00A7i\u00A7n\u00A7i\u00A7m\u00A7a\u00A7p");
    private final TextComponent fairXaero = Component
            .text("\u00A7f\u00A7a\u00A7i\u00A7r\u00A7x\u00A7a\u00A7e\u00A7r\u00A7o");
    private final TextComponent resetXaero = Component
            .text("\u00A7r\u00A7e\u00A7s\u00A7e\u00A7t\u00A7x\u00A7a\u00A7e\u00A7r\u00A7o");

    public XaeroMessageHandler(ServerCore plugin) {
        super(plugin);
    }

    @Override
    public void handleRegisterChannel(@NotNull String channel, @NotNull Player player) {
        player.sendMessage(noMinimap);
        player.sendMessage(fairXaero);

        if (player.getListeningPluginChannels().contains(CHERRY_CHANNEL)) {
            allowlist.add(player);
            player.sendMessage(resetXaero);
        } else {
            Bukkit.getScheduler().runTaskTimer(plugin,
                    new VerifyCherryOnTopTask(5, player, allowlist),
                    0L, 20L);
        }

        if (channel.startsWith("xaerominimap")) {
            // Do something with minimap?
        }

        if (channel.startsWith("xaeroworldmap")) {
            // Do something with worldmap?
        }
    }

    @Override
    public void handleUnregisterChannel(@NotNull String channel, @NotNull Player player) {
        allowlist.remove(player);
    }

    @Override
    public void handleIncomingMessage(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {}

    int initializeXaeroLevelId() {
        return 0;
    }

    byte[] constructMinimapClientBoundRules(boolean caveMode, boolean netherCaveMode, boolean radar) {
        boolean ncm = false;
        if (caveMode) ncm = netherCaveMode;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(4);

        CompoundBinaryTag rulesNbt = CompoundBinaryTag.empty()
                .putBoolean("cm", caveMode)
                .putBoolean("ncm", ncm)
                .putBoolean("r", radar);

        try {
            BinaryTagIO.writer().writeNameless(rulesNbt, out);
        } catch (IOException e) {
            plugin.getLogger().severe("Error occurred while trying to send NBT data into serializable format");
        }

        return out.toByteArray();
    }

    @RequiredArgsConstructor
    private class VerifyCherryOnTopTask implements Consumer<BukkitTask> {

        private int n = 0;
        private final int maxTries;
        private final Player player;
        private final List<Player> allowlist;

        @Override
        public void accept(BukkitTask task) {
            if (allowlist.contains(player)) {
                player.sendMessage(resetXaero);
                task.cancel();
            }

            if (n < maxTries) {
                if (player.getListeningPluginChannels().contains(CHERRY_CHANNEL)) allowlist.add(player);
                n++;
                return;
            } else {
                player.sendMessage(Component.text()
                                .content("You do not have CherryOnTop installed! ")
                                .color(NamedTextColor.YELLOW)
                                .append(
                                        Component.text("Xaero's minimap is locked and cave mode for world map is disabled!")
                                                .color(NamedTextColor.RED)
                                                .decorate(TextDecoration.BOLD)
                                ));
            }

            task.cancel();
        }

    }

}
