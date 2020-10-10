package us.talabrek.ultimateskyblock.menu.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.talabrek.ultimateskyblock.island.IslandInfo;
import us.talabrek.ultimateskyblock.menu.icon.MenuIcon;
import us.talabrek.ultimateskyblock.player.UltimateHolder;
import us.talabrek.ultimateskyblock.uSkyBlock;

import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

public class MainMenu extends UltimateHolder {

    public MainMenu(@Nullable Player player, @NotNull String title, @NotNull MenuType menuType) {
        super(player, title, menuType);
    }

    public void init() {

        addIcon(10, new MenuIcon(Material.OAK_DOOR) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.closeInventory();
                player.performCommand("is home");
            }
        }.withName("&a&lReturn Home").addLore("&7&oReturn to your island's home",
            "&7&opoint. You can change your",
            "&7&ohome point to any location",
            "&7&ousing &b/is sethome&7.",
            "&r  &6Click here to return home."));

        IslandInfo islandInfo = plugin.getIslandInfo(player);

        addIcon(11, new MenuIcon(Material.DIAMOND_ORE) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.closeInventory();
                player.performCommand("c");
            }
        }.withName("&a&lChallenges").addLore("&7&oView a list of challenges",
            "&7&othat you can complete on",
            "&7&oyour island to earn items",
            "&7&owhich will help expand your island.",
            "&r  &6Click here to view challenges."));

        addIcon(12, new MenuIcon(Material.EXPERIENCE_BOTTLE) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.closeInventory();
                player.performCommand("is level");
            }
        }.withName("&a&lIsland Level")
            .addLore(tr("&eCurrent Level: {0,number,##.#}", islandInfo.getLevel()),
                "&7&oGain island levels by expanding",
                "&7&oyour island and completing certain",
                "&7&ochallenges. Rarer blocks will add",
                "&7&oto your levels."));

        addIcon(13, new MenuIcon(Material.PLAYER_HEAD) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.closeInventory();
                player.performCommand("is party");
            }
        }.withName("&a&lIsland Group")
            .addLore(tr("&eMembers: &a{0}/{1}", islandInfo.getPartySize(), islandInfo.getMaxPartySize()),
                "&7&oView the members of your island",
                "&7&oand what permissions they have.",
                "&7&oIf you're the island leader, you",
                "&7&ocan change these permissions.",
                "&r  &6Click here to view island members."));

        addIcon(14, new MenuIcon(Material.JUNGLE_SAPLING) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.closeInventory();
                player.performCommand("is biome");
            }
        }.withName("&a&lIsland Biome")
            .addLore("&7&oThe island biome has an impact",
                "&7&oon mob/animal spawns as well as",
                "&7&ograss colour.")
            .addLore(islandInfo.hasPerm(player, "canChangeBiome")
                ? "&r  &6Click here to change biomes." : "&r  &cYou can't change the biome."));

        addIcon(15, new MenuIcon(Material.IRON_BARS) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.closeInventory();
                if (plugin.getIslandInfo(player).isLocked()) {
                    player.performCommand("is unlock");
                } else {
                    player.performCommand("is lock");
                }
                player.performCommand("is");
            }
        }.withName("&a&lIsland Lock")
            .addLore("&eLock Status: " + (islandInfo.isLocked() ? "&aActive" : "&cInactive"),
                "&7&oWhen the island lock is",
                "&7&oactive, players who are not",
                "&7&omembers can not enter your",
                "&7&oisland."));

        if (plugin.getIslandInfo(player).hasWarp()) {
            addIcon(16, new MenuIcon(Material.END_PORTAL_FRAME) {
                @Override
                public void onClick(InventoryClickEvent event, Player player) {
                    player.closeInventory();
                    player.performCommand("is togglewarp");
                    player.performCommand("is");
                }
            }.withName("&a&lIsland Warp")
                .addLore("&eWarp Status: &aActive",
                    "&7&oOther players may warp to",
                    "&7&oyour island at any time",
                    "&7&ousing the warp you set.",
                    islandInfo.hasPerm(player, "canToggleWarp")
                        && player.hasPermission("usb.island.toggle") ? "&r  &6Click here to deactivate it."
                        : "&r  &cYou can't toggle the warp."));


        } else {
            addIcon(16, new MenuIcon(Material.END_STONE) {
                @Override
                public void onClick(InventoryClickEvent event, Player player) {
                    player.closeInventory();
                    player.performCommand("is togglewarp");
                    player.performCommand("is");
                }
            }.withName("&a&lIsland Warp").addLore("&eWarp Status: &cInactive",
                "&7&oOther players may warp to",
                "&7&oyour island at any time",
                "&7&ousing the warp you set.",
                islandInfo.hasPerm(player, "canToggleWarp")
                    && player.hasPermission("usb.island.toggle") ? "&r  &6Click here to activate it."
                    : "&r  &cYou can't toggle the warp."));
        }

        addIcon(20, new MenuIcon(Material.GRASS) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.performCommand("is spawn");
            }
        }.withName("&a&lReturn to Spawn").addLore("&r  &6Teleport to the spawn area."));

        addIcon(21, new MenuIcon(Material.WRITABLE_BOOK) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {
                player.performCommand("island log");
            }
        }.withName("&a&lIsland Log").addLore("&7&oView a log of events from",
            "&7&oyour island such as members",
            "&7&obiomes and warp changes.",
            "&r  &6Click to view the log."));

        addIcon(22, new MenuIcon(Material.RED_BED) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {

            }
        }.withName("&a&lGo Home").addLore("&7&oHead back to your island's",
            "&7&ohome.",
            "&r  &6Click here to teleport to your",
            "&6island's home."));

        addIcon(23, new MenuIcon(Material.HOPPER) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {

            }
        }.withName("&a&lChange Warp Location").addLore("&7&oWhen your warp is activated",
            "&7&oother players will be taken to",
            "&7&othis point when they teleport",
            "&7&oto your island.",
            "&r  &6Click here to change your warp",
            "&6location."));

        addIcon(24, new MenuIcon(Material.BARRIER) {
            @Override
            public void onClick(InventoryClickEvent event, Player player) {

            }
        }.withName("&c&l???"));
        if (islandInfo.isLeader(player)) {
            if (plugin.getConfig().getBoolean("island-schemes-enabled", true)) {

                addIcon(35, new MenuIcon(Material.PODZOL) {
                    @Override
                    public void onClick(InventoryClickEvent event, Player player) {

                    }
                }.withName("&c&lRestart Island").addLore("&7&oRestarts your island.",
                    "&cWARNING: This will remove your",
                    "&citems and island!"));
            }
        } else {
            addIcon(35, new MenuIcon(Material.IRON_DOOR) {
                @Override
                public void onClick(InventoryClickEvent event, Player player) {

                }
            }.withName("&c&lLeave Island").addLore("&7&oMakes you leave your island.",
                "&cWARNING: This will remove your items!"));
            long millisLeft = plugin.getConfirmHandler().millisLeft(player, "/is leave");
            if (millisLeft > 0) {
                //   updateLeaveMenuItemTimer(player, menu, menuItem);
            }
        }
    }
}
