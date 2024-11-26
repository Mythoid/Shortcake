package io.github.mythoid.smp.shortcake.messages.handlers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.mythoid.smp.shortcake.ServerCore;
import io.github.mythoid.smp.shortcake.messages.MessageHandler;
import net.kyori.adventure.nbt.BinaryTagIO;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class XaeroMessageHandler extends MessageHandler {

    /*  Packet IDs
        0 = LevelMapProperties
        1 = Handshake
        2 = ClientboundTrackedPlayerPacket
        3 = ClientboundPlayerTrackerResetPacket
        4 = ClientboundRulesPacket
     */

    public XaeroMessageHandler(ServerCore plugin) {
        super(plugin);
    }

    @Override
    public void handleRegisterChannel(@NotNull String channel, @NotNull Player player) {
        player.sendMessage(
                Component.text("You have " + channel.split(":")[0] + " installed!")
                        .color(NamedTextColor.YELLOW)
        );

        if (channel.startsWith("xaerominimap")) {
            // Do something with minimap?
        }

        if (channel.startsWith("xaeroworldmap")) {
            // Do something with worldmap?
        }
    }

    @Override
    public void handleUnregisterChannel(@NotNull String channel, @NotNull Player player) {

    }

    @Override
    public void handleIncomingMessage(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {

    }

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

}
