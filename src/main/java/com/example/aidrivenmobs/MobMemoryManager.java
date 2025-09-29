package com.example.aidrivenmobs;

import com.example.aidrivenmobs.data.PlayerMemory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobMemoryManager {
    private JavaPlugin plugin;
    private Map<UUID, PlayerMemory> playerMemories;
    private Map<String, MobPersonality> mobPersonalities;
    private Map<UUID, MobKnowledge> mobKnowledgeMap; // New: Store knowledge for individual mobs
    private DataPersistenceManager dataManager;

    public MobMemoryManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerMemories = new HashMap<>();
        this.mobPersonalities = new HashMap<>();
        this.mobKnowledgeMap = new HashMap<>();
        this.dataManager = new DataPersistenceManager(plugin);
        loadData();
    }

    public PlayerMemory getPlayerMemory(UUID playerId) {
        return playerMemories.computeIfAbsent(playerId, this::loadPlayerMemory);
    }

    public PlayerMemory getPlayerMemory(Player player) {
        return getPlayerMemory(player.getUniqueId());
    }

    public MobPersonality getMobPersonality(String mobType) {
        return mobPersonalities.computeIfAbsent(mobType, MobPersonality::new);
    }

    // New method to get or create mob knowledge
    public MobKnowledge getMobKnowledge(UUID mobId, String mobType) {
        return mobKnowledgeMap.computeIfAbsent(mobId, id -> new MobKnowledge(id, mobType));
    }

    // New method to remove mob knowledge (when mob dies)
    public void removeMobKnowledge(UUID mobId) {
        mobKnowledgeMap.remove(mobId);
    }

    public void saveAllData() {
        dataManager.saveAllPlayerMemories(playerMemories.values());
        plugin.getLogger().info("Saving all mob memory data...");
    }

    private void loadData() {
        // In a full implementation, we would load all player data
        plugin.getLogger().info("Loading mob memory data...");
    }

    private PlayerMemory loadPlayerMemory(UUID playerId) {
        return dataManager.loadPlayerMemory(playerId);
    }
}