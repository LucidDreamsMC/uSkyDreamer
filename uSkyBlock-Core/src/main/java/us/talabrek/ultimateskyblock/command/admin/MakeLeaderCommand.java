package us.talabrek.ultimateskyblock.command.admin;

import dk.lockfuglsang.minecraft.command.AbstractCommand;
import dk.lockfuglsang.minecraft.po.I18nUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import us.talabrek.ultimateskyblock.handler.WorldGuardHandler;
import us.talabrek.ultimateskyblock.island.IslandInfo;
import us.talabrek.ultimateskyblock.player.PlayerInfo;
import us.talabrek.ultimateskyblock.uSkyBlock;

import java.util.Map;

import static dk.lockfuglsang.minecraft.po.I18nUtil.marktr;

/**
 * Allows transfer of leadership to another player.
 */
public class MakeLeaderCommand extends AbstractCommand {
    private final uSkyBlock plugin;

    public MakeLeaderCommand(uSkyBlock plugin) {
        super("makeleader|transfer", "usb.admin.makeleader", "leader oplayer", marktr("transfer leadership to another player"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(final CommandSender sender, String alias, Map<String, Object> data, final String... args) {
        if (args.length == 2) {
            plugin.async(new Runnable() {
                @Override
                public void run() {
                    String islandPlayerName = args[0];
                    String playerName = args[1];
                    PlayerInfo currentLeader = plugin.getPlayerInfo(islandPlayerName);
                    PlayerInfo newLeader = plugin.getPlayerInfo(playerName);

                    if (currentLeader == null || !currentLeader.getHasIsland()) {
                        sender.sendMessage(I18nUtil.tr("§9☀ §8» §7Player §9{0} §7has no island to transfer!", islandPlayerName));
                        return;
                    }
                    IslandInfo islandInfo = plugin.getIslandInfo(currentLeader);
                    if (islandInfo == null) {
                        sender.sendMessage(I18nUtil.tr("§9☀ §8» §7Player §9{0} §7has no island to transfer!", islandPlayerName));
                        return;
                    }
                    if (newLeader != null && newLeader.getHasIsland() && !newLeader.locationForParty().equals(islandInfo.getName())) {
                        sender.sendMessage(I18nUtil.tr("§9☀ §8» §7Player §9{0} §7already has an island. Use §9/usb island remove <name> §7to remove him first.", playerName));
                        return;
                    }
                    newLeader.setJoinParty(islandInfo.getIslandLocation());
                    Location homeLocation = currentLeader.getHomeLocation();
                    islandInfo.removeMember(currentLeader); // Remove leader
                    islandInfo.setupPartyLeader(newLeader.getPlayerName()); // Promote member
                    islandInfo.addMember(currentLeader);
                    newLeader.setHomeLocation(homeLocation);
                    currentLeader.save();
                    newLeader.save();
                    WorldGuardHandler.updateRegion(islandInfo);
                    plugin.getEventLogic().fireIslandLeaderChangedEvent(islandInfo, currentLeader, newLeader);
                    islandInfo.sendMessageToIslandGroup(true, marktr("§9☀ §8» §7Leadership transferred by §9{0} §7to §9{1}§7!"), sender.getName(), playerName);
                }
            });
            return true;
        }
        return false;
    }
}
