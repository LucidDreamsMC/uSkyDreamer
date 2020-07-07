package us.talabrek.ultimateskyblock.command.island;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.talabrek.ultimateskyblock.island.IslandInfo;
import us.talabrek.ultimateskyblock.player.PlayerInfo;
import us.talabrek.ultimateskyblock.uSkyBlock;

import java.util.Map;

import static dk.lockfuglsang.minecraft.po.I18nUtil.marktr;
import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

@SuppressWarnings("deprecation")
public class LeaveCommand extends RequireIslandCommand {

    public LeaveCommand(uSkyBlock plugin) {
        super(plugin, "leave", "usb.party.leave", marktr("leave your party"));
    }

    @Override
    protected boolean doExecute(String alias, Player player, PlayerInfo pi, IslandInfo island, Map<String, Object> data, String... args) {
        if (player.getWorld().getName().equalsIgnoreCase(plugin.getWorldManager().getWorld().getName())) {
            if (!island.isParty()) {
                player.sendMessage(tr("§9☀ §8» §7You can't leave your island if you are the only person. Try using /island restart if you want a new one!"));
                return true;
            }
            if (island.isLeader(player)) {
                player.sendMessage(tr("§9☀ §8» §7You own this island, use §9/island remove <player> §7instead."));
                return true;
            }
            if (plugin.getConfirmHandler().checkCommand(player, "/is leave")) {
                island.removeMember(pi);
                plugin.getTeleportLogic().spawnTeleport(player, true);
                player.sendMessage(tr("§9☀ §8» §7You have left the island and returned to the player spawn."));
                if (Bukkit.getPlayer(island.getLeader()) != null) {
                    Bukkit.getPlayer(island.getLeader()).sendMessage(tr("§9☀ §8» §9{0} §7has left your island!", player.getName()));
                }
            }
        } else {
            player.sendMessage(tr("§9☀ §8» §7You must be in the skyblock world to leave your party!"));
        }
        return true;
    }
}
