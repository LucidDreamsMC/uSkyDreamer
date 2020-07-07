package us.talabrek.ultimateskyblock.command.island;

import org.bukkit.entity.Player;
import us.talabrek.ultimateskyblock.island.IslandInfo;
import us.talabrek.ultimateskyblock.player.PlayerInfo;
import us.talabrek.ultimateskyblock.uSkyBlock;

import java.util.Map;

import static dk.lockfuglsang.minecraft.po.I18nUtil.marktr;
import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

public class WarpCommand extends RequirePlayerCommand {

    private final uSkyBlock plugin;

    public WarpCommand(uSkyBlock plugin) {
        super("warp|w", "usb.island.warp", "island", marktr("warp to another player''s island"));
        this.plugin = plugin;
    }

    @Override
    protected boolean doExecute(String alias, Player player, Map<String, Object> data, String... args) {
        if (args.length == 0) {
            IslandInfo island = plugin.getIslandInfo(player);
            if (island != null && hasPermission(player, "usb.island.setwarp")) {
                if (island.hasWarp()) {
                    player.sendMessage(tr("§9☀ §8» §7Your incoming warp is active, players may warp to your island."));
                } else {
                    player.sendMessage(tr("§9☀ §8» §7Your incoming warp is inactive, players may not warp to your island."));
                }
                player.sendMessage(tr("§9☀ §8» §7Set incoming warp to your current location using §9/island setwarp§7."));
                player.sendMessage(tr("§9☀ §8» §7Toggle your warp on/off using §9/island togglewarp§7."));
            } else {
                player.sendMessage(tr("§9☀ §8» §7You do not have permission to create a warp on your island!"));
            }
            if (hasPermission(player, "usb.island.warp")) {
                player.sendMessage(tr("§9☀ §8» §7Warp to another island using §9/island warp <player>§7."));
            } else {
                player.sendMessage(tr("§9☀ §8» §7You do not have permission to warp to other islands!"));
            }
            return true;
        } else if (args.length == 1) {
            if (hasPermission(player, "usb.island.warp")) {
                PlayerInfo senderPlayerInfo = plugin.getPlayerInfo(player);
                if (senderPlayerInfo.isIslandGenerating()) {
                    player.sendMessage(tr("§9☀ §8» §7Your island is in the process of generating, you cannot warp to other players islands right now."));
                    return true;
                }

                PlayerInfo targetPlayerInfo = plugin.getPlayerInfo(args[0]);
                if (targetPlayerInfo == null || !targetPlayerInfo.getHasIsland()) {
                    player.sendMessage(tr("§9☀ §8» §7That player does not exist!"));
                    return true;
                }
                IslandInfo island = plugin.getIslandInfo(targetPlayerInfo);
                if (island == null || (!island.hasWarp() && !island.isTrusted(player))) {
                    player.sendMessage(tr("§9☀ §8» §7That player does not have an active warp."));
                    return true;
                }
                if (targetPlayerInfo.isIslandGenerating()) {
                    player.sendMessage(tr("§9☀ §8» §7That players island is in the process of generating, you cannot warp to it right now."));
                    return true;
                }
                if (!island.isBanned(player)) {
                    if (plugin.getConfig().getBoolean("options.protection.visitors.warn-on-warp", true)) {
                        island.sendMessageToOnlineMembers(tr("§9☀ §8» §9{0} §7is warping to your island!", player.getDisplayName()));
                    }
                    plugin.getTeleportLogic().warpTeleport(player, targetPlayerInfo, false);
                } else {
                    player.sendMessage(tr("§9☀ §8» §7That player has forbidden you from warping to their island."));
                }
            } else {
                player.sendMessage(tr("§9☀ §8» §7You do not have permission to warp to other islands!"));
            }
            return true;
        }
        return false;
    }
}
