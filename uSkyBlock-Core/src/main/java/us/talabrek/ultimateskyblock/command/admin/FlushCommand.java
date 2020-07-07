package us.talabrek.ultimateskyblock.command.admin;

import dk.lockfuglsang.minecraft.command.AbstractCommand;
import org.bukkit.command.CommandSender;
import us.talabrek.ultimateskyblock.uSkyBlock;

import java.util.Map;

import static dk.lockfuglsang.minecraft.po.I18nUtil.marktr;
import static dk.lockfuglsang.minecraft.po.I18nUtil.tr;

public class FlushCommand extends AbstractCommand {
    private final uSkyBlock plugin;

    public FlushCommand(uSkyBlock plugin) {
        super("flush", "usb.admin.cache", marktr("flushes all caches to files"));
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String alias, Map<String, Object> data, String... args) {
        long flushedIslands = plugin.getIslandLogic().flushCache();
        long flushedPlayers = plugin.getPlayerLogic().flushCache();
        long flushedChallenges = plugin.getChallengeLogic().flushCache();
        sender.sendMessage(tr("§9☀ §8» §7Flushed §9{0} §7islands, §9{1} §7players and §9{2} §7challenge-completions.", flushedIslands, flushedPlayers, flushedChallenges));
        return true;
    }
}
