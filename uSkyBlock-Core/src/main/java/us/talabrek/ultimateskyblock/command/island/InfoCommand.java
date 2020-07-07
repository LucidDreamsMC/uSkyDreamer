package us.talabrek.ultimateskyblock.command.island;

import dk.lockfuglsang.minecraft.util.ItemStackUtil;
import org.bukkit.entity.Player;
import us.talabrek.ultimateskyblock.Settings;
import us.talabrek.ultimateskyblock.api.async.Callback;
import us.talabrek.ultimateskyblock.api.model.BlockScore;
import us.talabrek.ultimateskyblock.island.IslandInfo;
import us.talabrek.ultimateskyblock.player.PatienceTester;
import us.talabrek.ultimateskyblock.player.PlayerInfo;
import us.talabrek.ultimateskyblock.uSkyBlock;
import us.talabrek.ultimateskyblock.util.LogUtil;

import java.util.Map;
import java.util.logging.Level;

import static dk.lockfuglsang.minecraft.po.I18nUtil.marktr;
import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

public class InfoCommand extends RequireIslandCommand {
    public InfoCommand(uSkyBlock plugin) {
        super(plugin, "info", "usb.island.info", "?island", marktr("check your or another''s island info"));
        addFeaturePermission("usb.island.info.other", tr("allows user to see others island info"));
    }

    @Override
    protected boolean doExecute(String alias, Player player, PlayerInfo pi, IslandInfo island, Map<String, Object> data, String... args) {
        if (!Settings.island_useIslandLevel) {
            player.sendMessage(tr("§9☀ §8» §7Island level has been disabled, contact an administrator."));
            return true;
        }
        if (PatienceTester.isRunning(player, "usb.island.info.active")) {
            return true;
        }
        if (player.hasMetadata("usb.island.info.active")) {
            player.sendMessage(tr("§9☀ §8» §7Hold your horses! You have to be patient..."));
            return true;
        }
        if (args.length == 0 || (args.length == 1 && args[0].matches("\\d*"))) {
            if (!plugin.playerIsOnIsland(player)) {
                player.sendMessage(tr("§9☀ §8» §7You must be on your island to use this command."));
                return true;
            }
            if (!island.isParty() && !pi.getHasIsland()) {
                player.sendMessage(tr("§9☀ §8» §7You do not have an island!"));
            } else {
                getIslandInfo(player, player.getName(), alias, args.length == 1 ? Integer.parseInt(args[0], 10) : 1);
            }
            return true;
        } else if (args.length == 1 || (args.length == 2 && args[1].matches("\\d*"))) {
            if (hasPermission(player, "usb.island.info.other")) {
                getIslandInfo(player, args[0], alias, args.length == 2 ? Integer.parseInt(args[1], 10) : 1);
            } else {
                player.sendMessage(tr("§9☀ §8» §7You do not have access to that command!"));
            }
            return true;
        }
        return false;
    }

    public boolean getIslandInfo(final Player player, final String islandPlayer, final String cmd, final int page) {
        PlayerInfo info = plugin.getPlayerInfo(islandPlayer);
        if (info == null || !info.getHasIsland()) {
            player.sendMessage(tr("§9☀ §8» §7That player is invalid or does not have an island!"));
            return false;
        }
        final PlayerInfo playerInfo = islandPlayer.equals(player.getName()) ? plugin.getPlayerInfo(player) : plugin.getPlayerInfo(islandPlayer);
        final Callback<us.talabrek.ultimateskyblock.api.model.IslandScore> showInfo = new Callback<us.talabrek.ultimateskyblock.api.model.IslandScore>() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    int maxPage = ((getState().getSize() - 1) / 10) + 1;
                    int currentPage = page;
                    if (currentPage < 1) {
                        currentPage = 1;
                    }
                    if (currentPage > maxPage) {
                        currentPage = maxPage;
                    }
                    player.sendMessage(tr("§9☀ §8» §7Blocks on §9{0}§7's Island (page §9{1,number} §7of §9{2,number}§7):", islandPlayer, currentPage, maxPage));
                    if (cmd.equalsIgnoreCase("info") && getState() != null) {
                        player.sendMessage(tr("§9☀ §8» §7Score §8⟶ §7Count §8⟶ §7Block"));
                        for (BlockScore score : getState().getTop((currentPage - 1) * 10, 10)) {
                            player.sendMessage(score.getState().getColor() + tr("§9{0,number,00.00} §8⟶ §9{1,number,#} §8⟶ §9{2}",
                                    score.getScore(), score.getCount(),
                                    ItemStackUtil.getItemName(score.getBlock())));
                        }
                        player.sendMessage(tr("§9☀ §8» §7Island level is §9{0,number,###.##}", getState().getScore()));
                    }
                }
                PatienceTester.stopRunning(player, "usb.island.info.active");
            }
        };
        plugin.sync(() -> {
            try {
                PatienceTester.startRunning(player, "usb.island.info.active");
                plugin.calculateScoreAsync(player, playerInfo.locationForParty(), showInfo);
            } catch (Exception e) {
                LogUtil.log(Level.SEVERE, "Error while calculating Island Level", e);
            }
        }, 1L);
        return true;
    }


}
