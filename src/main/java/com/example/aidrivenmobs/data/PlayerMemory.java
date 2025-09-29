package com.example.aidrivenmobs.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMemory {
    private UUID playerId;
    private Map<String, Integer> combatStats;
    private Map<String, Object> behavioralPatterns;
    
    public PlayerMemory(UUID playerId) {
        this.playerId = playerId;
        this.combatStats = new HashMap<>();
        this.behavioralPatterns = new HashMap<>();
        
        // Initialize default combat stats
        combatStats.put("melee_hits", 0);
        combatStats.put("ranged_hits", 0);
        combatStats.put("shield_blocks", 0);
        combatStats.put("potion_usage", 0);
        combatStats.put("kills_skeleton", 0);
        combatStats.put("kills_creeper", 0);
        combatStats.put("kills_zombie", 0);
        // Add more mob types as needed
    }
    
    // Getters and setters
    public UUID getPlayerId() {
        return playerId;
    }
    
    public int getStat(String statName) {
        return combatStats.getOrDefault(statName, 0);
    }
    
    public void incrementStat(String statName) {
        combatStats.put(statName, getStat(statName) + 1);
    }
    
    public void incrementStat(String statName, int amount) {
        combatStats.put(statName, getStat(statName) + amount);
    }
    
    public Map<String, Integer> getCombatStats() {
        return new HashMap<>(combatStats);
    }
    
    public Object getBehavioralPattern(String patternName) {
        return behavioralPatterns.get(patternName);
    }
    
    public void setBehavioralPattern(String patternName, Object value) {
        behavioralPatterns.put(patternName, value);
    }
}