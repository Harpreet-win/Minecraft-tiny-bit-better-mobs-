package com.example.aidrivenmobs.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Data class for storing player behavior statistics
 */
public class PlayerMemory {
    private UUID playerId;
    private Map<String, Integer> stats;

    public PlayerMemory(UUID playerId) {
        this.playerId = playerId;
        this.stats = new HashMap<>();
    }

    /**
     * Get a statistic value
     * 
     * @param statName The name of the statistic
     * @return The value of the statistic (0 if not found)
     */
    public int getStat(String statName) {
        return stats.getOrDefault(statName, 0);
    }

    /**
     * Increment a statistic value
     * 
     * @param statName The name of the statistic
     */
    public void incrementStat(String statName) {
        stats.put(statName, getStat(statName) + 1);
    }

    /**
     * Set a statistic value
     * 
     * @param statName The name of the statistic
     * @param value    The value to set
     */
    public void setStat(String statName, int value) {
        stats.put(statName, value);
    }

    /**
     * Get the player ID
     * 
     * @return The player ID
     */
    public UUID getPlayerId() {
        return playerId;
    }
}