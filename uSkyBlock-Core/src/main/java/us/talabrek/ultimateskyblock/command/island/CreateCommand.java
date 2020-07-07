package us.talabrek.ultimateskyblock.command.island;

import org.bukkit.entity.Player;
import us.talabrek.ultimateskyblock.Settings;
import us.talabrek.ultimateskyblock.api.event.CreateIslandEvent;
import us.talabrek.ultimateskyblock.player.PlayerInfo;
import us.talabrek.ultimateskyblock.uSkyBlock;

import java.util.Map;

import static dk.lockfuglsang.minecraft.po.I18nUtil.marktr;
import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

public class CreateCommand extends RequirePlayerCommand {
    private final uSkyBlock plugin;

    public CreateCommand(uSkyBlock plugin) {
        super("create|c", "usb.island.create", "?schematic", marktr("create an island"));
        this.plugin = plugin;
        addFeaturePermission("usb.exempt.cooldown.create", tr("exempt player from create-cooldown"));
    }

    @Override
    protected boolean doExecute(String alias, Player player, Map<String, Object> data, String... args) {
        PlayerInfo pi = plugin.getPlayerInfo(player);
        int cooldown = plugin.getCooldownHandler().getCooldown(player, "restart");
        if (!pi.getHasIsland() && cooldown == 0) {
            String cSchem = args != null && args.length > 0 ? args[0] : Settings.island_schematicName;
            plugin.getServer().getPluginManager().callEvent(new CreateIslandEvent(player, cSchem));
        } else if (pi.getHasIsland()) {
            us.talabrek.ultimateskyblock.api.IslandInfo island = plugin.getIslandInfo(pi);
            if (island.isLeader(player)) {
                player.sendMessage(tr("§9☀ §8» §7Island found!\n" +
                        "§9☀ §8» §7You already have an island. If you want a fresh island, type §9/is restart§7 to get one."));
            } else {
                player.sendMessage(tr("§9☀ §8» §7Island found!" +
                        "§9☀ §8» §7You are already a member of an island. To start your own, first type §9/is leave§7."));
            }
        } else {
            player.sendMessage(tr("§9☀ §8» §7You can create a new island in §9{0,number,#} §7seconds.", cooldown));
        }
        return true;
    }
}
