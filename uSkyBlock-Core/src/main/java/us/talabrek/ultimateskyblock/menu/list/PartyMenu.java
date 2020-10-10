package us.talabrek.ultimateskyblock.menu.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.talabrek.ultimateskyblock.menu.icon.MenuIcon;
import us.talabrek.ultimateskyblock.player.UltimateHolder;

public class PartyMenu extends UltimateHolder {
    public PartyMenu(@Nullable Player player, @NotNull String title, @NotNull MenuType menuType) {
        super(player, title, menuType);
    }

    @Override
    public void init() {
        addIcon(27, new MenuIcon(Material.ARROW) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.performCommand("is");
            }
        }.withName("&a&lBack"));
    }


}
