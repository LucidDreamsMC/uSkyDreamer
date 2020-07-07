package us.talabrek.ultimateskyblock.command.admin;

import dk.lockfuglsang.minecraft.command.AbstractCommand;
import dk.lockfuglsang.minecraft.command.CompositeCommand;
import dk.lockfuglsang.minecraft.nbt.NBTUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import dk.lockfuglsang.minecraft.util.ItemStackUtil;

import java.util.Map;

import static dk.lockfuglsang.minecraft.po.I18nUtil.marktr;
import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

/**
 * Command for querying items reg. NBT stuff
 */
public class NBTCommand extends CompositeCommand {
    public NBTCommand() {
        super("nbt", "usb.admin.nbt", marktr("advanced info about NBT stuff"));
        add(new AbstractCommand("info|i", marktr("shows the NBTTag for the currently held item")) {
            @Override
            public boolean execute(CommandSender sender, String alias, Map<String, Object> data, String... args) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack itemStack = player.getInventory().getItemInHand();
                    if (itemStack != null) {
                        String[] msgs = new String[]{
                                tr("§9☀ §8» §7Info for §9{0}", ItemStackUtil.asString(itemStack)),
                                tr("§9☀ §8» §7Name: §9{0}", VaultHandler.getItemName(itemStack)),
                                tr("§9☀ §8» §7Nbttag: §9{0}", NBTUtil.getNBTTag(itemStack))
                        };
                        player.sendMessage(msgs);
                    } else {
                        player.sendMessage(tr("§9☀ §8» §7No item in hand!"));
                    }
                    return true;
                }
                sender.sendMessage(tr("§9☀ §8» §7Can only be executed as a player"));
                return false;
            }
        });
        add(new AbstractCommand("set|s", null, "nbttag", marktr("sets the NBTTag on the currently held item")) {
            @Override
            public boolean execute(CommandSender sender, String alias, Map<String, Object> data, String... args) {
                if (sender instanceof Player) {
                    if (args.length > 0) {
                        Player player = (Player) sender;
                        ItemStack itemStack = player.getInventory().getItemInHand();
                        if (itemStack != null) {
                            String nbtTag = join(args);
                            itemStack = NBTUtil.setNBTTag(itemStack, nbtTag);
                            player.getInventory().setItemInHand(itemStack);
                            player.sendMessage(tr("§9☀ §8» §7Set §9{0}§7 to §9{1}§7!", nbtTag, itemStack));
                        } else {
                            player.sendMessage(tr("§9☀ §8» §7No item in hand!"));
                        }
                        return true;
                    }
                }
                sender.sendMessage(tr("§9☀ §8» §7Can only be executed as a player!"));
                return false;
            }
        });
        add(new AbstractCommand("add|a", null, "nbttag", marktr("adds the NBTTag on the currently held item")) {
            @Override
            public boolean execute(CommandSender sender, String alias, Map<String, Object> data, String... args) {
                if (sender instanceof Player) {
                    if (args.length > 0) {
                        Player player = (Player) sender;
                        ItemStack itemStack = player.getInventory().getItemInHand();
                        if (itemStack != null) {
                            String nbtTag = join(args);
                            itemStack = NBTUtil.addNBTTag(itemStack, nbtTag);
                            player.getInventory().setItemInHand(itemStack);
                            player.sendMessage(tr("§9☀ §8» §7Added §9{0}§7 to §9{1}", nbtTag, itemStack));
                        } else {
                            player.sendMessage(tr("§9☀ §8» §7No item in hand!"));
                        }
                        return true;
                    }
                }
                sender.sendMessage(tr("§9☀ §8» §7Can only be executed as a player"));
                return false;
            }
        });
    }
}
