package us.talabrek.ultimateskyblock.command.island;

import org.bukkit.entity.Player;
import us.talabrek.ultimateskyblock.island.IslandInfo;
import us.talabrek.ultimateskyblock.menu.PartyPermissionMenuItem;
import us.talabrek.ultimateskyblock.player.PlayerInfo;
import us.talabrek.ultimateskyblock.uSkyBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dk.lockfuglsang.minecraft.po.I18nUtil.marktr;
import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

public class PermCommand extends RequireIslandCommand {
    public PermCommand(uSkyBlock plugin) {
        super(plugin, "perm", "usb.island.perm", "member ?perm", marktr("changes a members island-permissions"));
    }

    @Override
    protected boolean doExecute(String alias, Player player, PlayerInfo pi, IslandInfo island, Map<String, Object> data, String... args) {
        String playerName = args.length > 0 ? args[0] : null;
        String perm = args.length > 1 ? args[1] : null;
        if (playerName != null && island.getMembers().contains(playerName) && perm == null) {
            String msg = tr("§9☀ §8» §7Permissions for §9{0}§7:", playerName) + "\n";
            for (String validPerm : getValidPermissions()) {
                boolean permValue = island.hasPerm(playerName, validPerm);
                msg += tr("§9☀ §8» §9{0} §8⟶ §9{1}", validPerm, permValue ? tr("ON") : tr("OFF")) + "\n";
            }
            player.sendMessage(msg.trim().split("\n"));
            return true;
        }
        if (playerName == null || perm == null || perm.isEmpty() || playerName.isEmpty()) {
            return false;
        }
        if (!isValidPermission(perm)) {
            player.sendMessage(tr("§9☀ §8» §7Invalid permission {0}. Must be one of {1}", perm, getValidPermissions()));
            return true;
        }
        if (island.togglePerm(playerName, perm)) {
            boolean permValue = island.hasPerm(playerName, perm);
            player.sendMessage(tr("§9☀ §8» §7Toggled permission §9{0} §7for §9{1} §7to §9{2}", perm, playerName, permValue ? tr("ON") : tr("OFF")));
        } else {
            player.sendMessage(tr("§9☀ §8» §7Unable to toggle permission §9{0}§7 for §9{1}§7!", perm, playerName));
        }
        return true;
    }

    private boolean isValidPermission(String perm) {
        return getValidPermissions().contains(perm);
    }

    private List<String> getValidPermissions() {
        List<String> list = new ArrayList<>();
        for (PartyPermissionMenuItem item : plugin.getMenu().getPermissionMenuItems()) {
            list.add(item.getPerm());
        }
        return list;
    }
}
