package us.talabrek.ultimateskyblock.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Island implements IslandInfo {
    @Override
    public int getMaxPartySize() {
        return 0;
    }

    @Override
    public int getMaxAnimals() {
        return 0;
    }

    @Override
    public int getMaxMonsters() {
        return 0;
    }

    @Override
    public int getMaxVillagers() {
        return 0;
    }

    @Override
    public int getMaxGolems() {
        return 0;
    }

    @Override
    public Map<Material, Integer> getBlockLimits() {
        return null;
    }

    @Override
    public String getLeader() {
        return null;
    }

    @Override
    public Set<String> getMembers() {
        return null;
    }

    @Override
    public String getBiome() {
        return null;
    }

    @Override
    public int getPartySize() {
        return 0;
    }

    @Override
    public boolean isLeader(Player player) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isBanned(Player player) {
        return false;
    }

    @Override
    public List<String> getBans() {
        return null;
    }

    @Override
    public boolean banPlayer(@NotNull OfflinePlayer target) {
        return false;
    }

    @Override
    public boolean banPlayer(@NotNull OfflinePlayer target, @Nullable OfflinePlayer initializer) {
        return false;
    }

    @Override
    public boolean unbanPlayer(@NotNull OfflinePlayer target) {
        return false;
    }

    @Override
    public boolean unbanPlayer(@NotNull OfflinePlayer target, @Nullable OfflinePlayer initializer) {
        return false;
    }

    @Override
    public boolean isBanned(@NotNull OfflinePlayer target) {
        return false;
    }

    @Override
    public List<String> getTrustees() {
        return null;
    }

    @Override
    public boolean trustPlayer(@NotNull OfflinePlayer target) {
        return false;
    }

    @Override
    public boolean trustPlayer(@NotNull OfflinePlayer target, @Nullable OfflinePlayer initializer) {
        return false;
    }

    @Override
    public boolean untrustPlayer(@NotNull OfflinePlayer target) {
        return false;
    }

    @Override
    public boolean untrustPlayer(@NotNull OfflinePlayer target, @Nullable OfflinePlayer initializer) {
        return false;
    }

    @Override
    public boolean isTrusted(@NotNull OfflinePlayer target) {
        return false;
    }

    @Override
    public double getLevel() {
        return 0;
    }

    @Override
    public List<String> getLog() {
        return null;
    }

    @Override
    public boolean isParty() {
        return false;
    }

    @Override
    public Location getWarpLocation() {
        return null;
    }

    @Override
    public Location getIslandLocation() {
        return null;
    }

    @Override
    public boolean hasOnlineMembers() {
        return false;
    }

    @Override
    public List<Player> getOnlineMembers() {
        return null;
    }

    @Override
    public boolean contains(Location loc) {
        return false;
    }

    @Override
    public String getSchematicName() {
        return null;
    }

    @Override
    public double getScoreMultiplier() {
        return 0;
    }

    @Override
    public double getScoreOffset() {
        return 0;
    }
}
