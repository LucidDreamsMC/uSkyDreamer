package us.talabrek.ultimateskyblock.command.admin;

import dk.lockfuglsang.minecraft.command.AbstractCommand;
import dk.lockfuglsang.minecraft.command.CompositeCommand;
import dk.lockfuglsang.minecraft.command.completion.AbstractTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.talabrek.ultimateskyblock.Settings;
import us.talabrek.ultimateskyblock.uSkyBlock;
import dk.lockfuglsang.minecraft.util.TimeUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static dk.lockfuglsang.minecraft.po.I18nUtil.marktr;
import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

/**
 * Manages player cooldowns
 */
public class CooldownCommand extends CompositeCommand {
    public CooldownCommand(final uSkyBlock plugin) {
        super("cooldown|cd", "usb.admin.cooldown", marktr("Controls player-cooldowns"));
        add(new AbstractCommand("clear|c", null, "player command", marktr("clears the cooldown on a command (* = all)")) {
            @Override
            public boolean execute(CommandSender sender, String alias, Map<String, Object> data, String... args) {
                if (args.length < 2) {
                    return false;
                }
                //noinspection deprecation
                Player p = Bukkit.getPlayer(args[0]);
                if (p == null || !p.isOnline()) {
                    sender.sendMessage(tr("§9☀ §8» §7The player is not currently online."));
                    return false;
                }
                if ("restart|biome".contains(args[1])) {
                    if (plugin.getCooldownHandler().clearCooldown(p, args[1])) {
                        sender.sendMessage(tr("§9☀ §8» §7Cleared cooldown on §9{0} §7for §9{1}§7!", args[1], p.getDisplayName()));
                    } else {
                        sender.sendMessage(tr("§9☀ §8» §7No active cooldown on §9{0} §7for §9{1} §7detected!", args[1], p.getDisplayName()));
                    }
                    return true;
                } else {
                    sender.sendMessage(tr("§9☀ §8» §7Invalid command supplied, only restart and biome supported!"));
                    return false;
                }
            }
        });
        add(new AbstractCommand("restart|r", null, "player", marktr("restarts the cooldown on the command")) {
            @Override
            public boolean execute(CommandSender sender, String alias, Map<String, Object> data, String... args) {
                if (args.length < 2) {
                    return false;
                }
                //noinspection deprecation
                Player p = Bukkit.getPlayer(args[0]);
                if (p == null || !p.isOnline()) {
                    sender.sendMessage(tr("§9☀ §8» §7The player is not currently online."));
                    return false;
                }
                if ("restart|biome".contains(args[1])) {
                    int cooldown = getCooldown(args[1]);
                    plugin.getCooldownHandler().resetCooldown(p, args[1], cooldown);
                    sender.sendMessage(tr("§9☀ §8» §7Reset cooldown on §9{0}§7 for §9{1}§7 to §9{2}§7 seconds!", args[1], p.getDisplayName(), cooldown));
                    return true;
                } else {
                    sender.sendMessage(tr("§9☀ §8» §7Invalid command supplied, only restart and biome supported!"));
                    return false;
                }
            }
        });
        add(new AbstractCommand("list|l", null, "?player", marktr("lists all the active cooldowns")) {
            @Override
            public boolean execute(CommandSender sender, String alias, Map<String, Object> data, String... args) {
                if (args.length < 1) {
                    return false;
                }
                //noinspection deprecation
                Player p = Bukkit.getPlayer(args[0]);
                if (p == null || !p.isOnline()) {
                    sender.sendMessage(tr("§9☀ §8» §7The player is not currently online"));
                    return false;
                }
                Map<String, Long> map = plugin.getCooldownHandler().getCooldowns(p.getUniqueId());
                StringBuilder sb = new StringBuilder();
                if (map != null && !map.isEmpty()) {
                    long now = System.currentTimeMillis();
                    sb.append(tr("§9☀ §8» §7Command §8⟶ §7Cooldown") + "\n");
                    for (String cmd : map.keySet()) {
                        sb.append(tr("§9☀ §8» §9{0} §8⟶ §7{1}", cmd, TimeUtil.millisAsString(map.get(cmd) - now)) + "\n");
                    }
                } else {
                    sb.append(tr("§9☀ §8» §7No active cooldowns for §9{0}§7 found.", data.get("playerName")));
                }
                sender.sendMessage(sb.toString().split("\n"));
                return true;
            }
        });
        addTab("command", new AbstractTabCompleter() {
            @Override
            protected List<String> getTabList(CommandSender commandSender, String term) {
                return Arrays.asList("restart", "biome");
            }
        });
    }

    private int getCooldown(String cmd) {
        switch (cmd) {
            case "restart": return Settings.general_cooldownRestart;
            case "biome": return Settings.general_biomeChange;
        }
        return 0;
    }

}
