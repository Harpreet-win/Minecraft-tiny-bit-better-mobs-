package com.example.aidrivenmobs;

import com.example.aidrivenmobs.data.PlayerMemory;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * System for tracking villager trust levels with players
 */
public class VillagerTrustSystem {
    private AIDrivenMobsPlugin plugin;
    private Map<UUID, Map<UUID, Integer>> trustLevels; // Player UUID -> (Villager UUID -> Trust Level)

    public VillagerTrustSystem(AIDrivenMobsPlugin plugin) {
        this.plugin = plugin;
        this.trustLevels = new HashMap<>();
    }

    /**
     * Get the trust level between a player and a villager
     * 
     * @param player   The player
     * @param villager The villager
     * @return Trust level (-100 to 100)
     */
    public int getTrustLevel(Player player, Villager villager) {
        Map<UUID, Integer> playerTrust = trustLevels.get(player.getUniqueId());
        if (playerTrust != null) {
            return playerTrust.getOrDefault(villager.getUniqueId(), 0);
        }
        return 0;
    }

    /**
     * Modify the trust level between a player and a villager
     * 
     * @param player   The player
     * @param villager The villager
     * @param delta    Amount to change trust by
     */
    public void modifyTrust(Player player, Villager villager, int delta) {
        UUID playerUUID = player.getUniqueId();
        UUID villagerUUID = villager.getUniqueId();

        Map<UUID, Integer> playerTrust = trustLevels.computeIfAbsent(playerUUID, k -> new HashMap<>());
        int currentTrust = playerTrust.getOrDefault(villagerUUID, 0);
        int newTrust = Math.max(-100, Math.min(100, currentTrust + delta)); // Clamp between -100 and 100

        playerTrust.put(villagerUUID, newTrust);

        // Apply consequences based on trust level
        applyTrustConsequences(player, villager, newTrust);
    }

    /**
     * Apply consequences based on trust level
     * 
     * @param player     The player
     * @param villager   The villager
     * @param trustLevel Current trust level
     */
    private void applyTrustConsequences(Player player, Villager villager, int trustLevel) {
        if (trustLevel < -50) {
            // Low trust consequences
            // Could spawn iron golem ambush, alert pillagers, etc.
            plugin.getLogger()
                    .info("Player " + player.getName() + " has low trust with villager. Trust level: " + trustLevel);
        } else if (trustLevel > 50) {
            // High trust benefits
            // Could offer discounts, special trades, etc.
            plugin.getLogger()
                    .info("Player " + player.getName() + " has high trust with villager. Trust level: " + trustLevel);
        }
    }

    /**
     * Handle positive interactions (trading, helping)
     * 
     * @param player   The player
     * @param villager The villager
     */
    public void handlePositiveInteraction(Player player, Villager villager) {
        PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

        // Increase trust based on interaction type
        int trustIncrease = 5;

        // Bonus trust for players who protect villagers
        if (memory.getStat("kills_pillager") > 0) {
            trustIncrease += 3;
        }

        modifyTrust(player, villager, trustIncrease);
    }

    /**
     * Handle negative interactions (attacking, stealing)
     * 
     * @param player   The player
     * @param villager The villager
     */
    public void handleNegativeInteraction(Player player, Villager villager) {
        // Decrease trust significantly
        modifyTrust(player, villager, -15);
    }
}