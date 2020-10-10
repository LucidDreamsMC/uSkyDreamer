package us.talabrek.ultimateskyblock.menu.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.talabrek.ultimateskyblock.island.IslandInfo;
import us.talabrek.ultimateskyblock.menu.icon.MenuIcon;
import us.talabrek.ultimateskyblock.player.UltimateHolder;

import java.util.Set;

public class PartyMenu extends UltimateHolder {

    private static final int[] partySlots = new int[]{10, 11, 12, 13, 14, 20, 21, 22};
    
    public PartyMenu(@Nullable Player player, @NotNull String title, @NotNull MenuType menuType) {
        super(player, title, menuType);
    }

    @Override
    public void init() {
        IslandInfo islandInfo = plugin.getIslandInfo(player);
        Set<String> members = islandInfo.getMembers();

        addIcon(27, new MenuIcon(Material.ARROW) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.performCommand("is");
            }
        }.withName("&a&lBack"));

        MenuIcon islandInfoIcon = new MenuIcon(Material.OAK_SIGN) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {

            }
        }.withName("&a&lIsland Members").addLore(
            "&a&lIsland size: &e" + islandInfo.getPartySize() + "/" + islandInfo.getMaxPartySize(),
            "&7&oHover over a player's head to",
            "&7&oview their permissions.",
            "&7&oThe leader can click on a head",
            "&7&oto change that player's permissions.");

        if (islandInfo.getPartySize() < islandInfo.getMaxPartySize()) {
            islandInfoIcon.addLore("&r  &6&lMore players can be invited to this island.");
        } else {
            islandInfoIcon.addLore("&r  &c&lThis island is full.");
        }
        addIcon(4, islandInfoIcon);

    }


}
