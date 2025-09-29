package com.example.aidrivenmobs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Class to store knowledge and learning data for individual mobs
 */
public class MobKnowledge {
    private UUID mobId;
    private String mobType;
    private Map<String, Object> knowledge;
    private Map<String, Integer> playerInteractions; // Track interactions with specific players
    private long lastInteractionTime;

    public MobKnowledge(UUID mobId, String mobType) {
        this.mobId = mobId;
        this.mobType = mobType.toLowerCase();
        this.knowledge = new HashMap<>();
        this.playerInteractions = new HashMap<>();
        this.lastInteractionTime = System.currentTimeMillis();

        // Initialize default knowledge based on mob type
        initializeDefaultKnowledge();
    }

    private void initializeDefaultKnowledge() {
        // Base knowledge traits based on mob type
        switch (mobType) {
            case "villager":
                knowledge.put("trust_threshold", 0);
                knowledge.put("gossip_spread", false);
                break;
            case "iron_golem":
                knowledge.put("protector_mode", true);
                knowledge.put("vengeful", false);
                break;
            case "zombie":
                knowledge.put("horde_mentality", false);
                knowledge.put("door_breaker", false);
                break;
            case "creeper":
                knowledge.put("stealth_approach", true);
                knowledge.put("flank_strategy", false);
                break;
            case "skeleton":
                knowledge.put("squad_coordinator", false);
                knowledge.put("ambush_specialist", false);
                break;
            case "spider":
                knowledge.put("ceiling_climber", true);
                knowledge.put("drop_ambusher", false);
                break;
            default:
                // Default neutral behavior
                knowledge.put("aggressive", false);
                knowledge.put("defensive", false);
                break;
        }
    }

    public UUID getMobId() {
        return mobId;
    }

    public String getMobType() {
        return mobType;
    }

    public Object getKnowledge(String key) {
        return knowledge.get(key);
    }

    public void setKnowledge(String key, Object value) {
        knowledge.put(key, value);
    }

    public boolean hasKnowledge(String key) {
        return knowledge.containsKey(key);
    }

    public int getPlayerInteractionCount(UUID playerId) {
        return playerInteractions.getOrDefault(playerId.toString(), 0);
    }

    public void incrementPlayerInteraction(UUID playerId) {
        String playerKey = playerId.toString();
        playerInteractions.put(playerKey, playerInteractions.getOrDefault(playerKey, 0) + 1);
        lastInteractionTime = System.currentTimeMillis();
    }

    public long getLastInteractionTime() {
        return lastInteractionTime;
    }

    public Map<String, Object> getAllKnowledge() {
        return new HashMap<>(knowledge);
    }

    public Map<String, Integer> getAllPlayerInteractions() {
        return new HashMap<>(playerInteractions);
    }
}