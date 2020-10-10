package us.talabrek.ultimateskyblock.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.talabrek.ultimateskyblock.menu.icon.MenuIcon;
import us.talabrek.ultimateskyblock.uSkyBlock;

public abstract class UltimateHolder implements InventoryHolder {

	protected final Player player;
	private final String title;
	private final MenuType menuType;
	private final MenuIcon[] slots;
    protected static final uSkyBlock plugin = uSkyBlock.getInstance();

	public UltimateHolder(@Nullable Player player, @NotNull String title, @NotNull MenuType menuType) {
		this.player = player;
		this.title = title;
		this.menuType = menuType;
		slots = new MenuIcon[54];
	}

	public abstract void init();

	@NotNull
	@Override
	public Inventory getInventory() {
		return player.getInventory();
	}

	@Nullable
	public Player getPlayer() {
		return player;
	}

	@NotNull
	public String getTitle() {
		return title;
	}

	@NotNull
	public MenuType getMenuType() {
		return menuType;
	}

	public void addIcon(int slot, MenuIcon icon) {
	    slots[slot] = icon;
    }

	public Inventory buildInventory() {
	    Inventory inv = Bukkit.createInventory(this, 36, title);
	    for (int i = 0; i < 36; i++) {
	        if (slots[i] != null) {
	            inv.setItem(i, slots[i].getItem());
            }
        }
	    return inv;
    }

    public MenuIcon getIcon(int slot) {
	    return slots[slot];
    }

	public enum MenuType {
		CONFIG,
		DEFAULT
	}
}
