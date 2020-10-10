package us.talabrek.ultimateskyblock.menu.icon;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuIcon {

    private final ItemStack item;

    public MenuIcon(Material material) {
        item = new ItemStack(material);
    }

    public MenuIcon withName(String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return this;
    }

    public MenuIcon addLore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return this;
        List<String> originalLore = meta.getLore();
        if (originalLore == null) originalLore = new ArrayList<>();
        for (String l : lore) {
            originalLore.add(ChatColor.translateAlternateColorCodes('&', l));
        }
        meta.setLore(originalLore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStack getItem() {
        return item;
    }

    public abstract void onClick(InventoryClickEvent event, Player player);
}
