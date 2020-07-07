package us.talabrek.ultimateskyblock.command.island;

import org.bukkit.entity.Player;
import us.talabrek.ultimateskyblock.island.IslandInfo;
import us.talabrek.ultimateskyblock.player.PlayerInfo;
import us.talabrek.ultimateskyblock.uSkyBlock;

import java.util.Map;

import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

/**
 * Common command for all the /is commands that require an island.
 */
public abstract class RequireIslandCommand extends RequirePlayerCommand {
    protected final uSkyBlock plugin;

    protected RequireIslandCommand(uSkyBlock plugin, String name, String description) {
        this(plugin, name, null, description);
    }

    protected RequireIslandCommand(uSkyBlock plugin, String name, String perm, String description) {
        this(plugin, name, perm, null,description);
    }

    protected RequireIslandCommand(uSkyBlock plugin, String name, String perm, String param, String description) {
        super(name, perm, param, description);
        this.plugin = plugin;
    }

    protected uSkyBlock getPlugin() {
        return plugin;
    }

    protected abstract boolean doExecute(String alias, Player player, PlayerInfo pi, IslandInfo island, Map<String, Object> data, String... args);

    @Override
    protected boolean doExecute(String alias, Player player, Map<String, Object> data, String... args) {
        PlayerInfo playerInfo = plugin.getPlayerInfo(player);
        if (playerInfo != null && playerInfo.getHasIsland()) {
            IslandInfo islandInfo = plugin.getIslandInfo(playerInfo);
            if (islandInfo != null) {
                return doExecute(alias, player, playerInfo, islandInfo, data, args);
            }
        }
        player.sendMessage(tr("§9☀ §8» §7No Island. Use §9/is create §7to get one."));
        return false;
    }
}
