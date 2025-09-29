package com.example.aidrivenmobs;

import com.example.aidrivenmobs.data.PlayerMemory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DataPersistenceManager {
    private JavaPlugin plugin;
    private File playerDataFolder;

    public DataPersistenceManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerDataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    public void savePlayerMemory(PlayerMemory memory) {
        try {
            UUID playerId = memory.getPlayerId();
            File playerFile = new File(playerDataFolder, playerId.toString() + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

            // Save combat stats
            config.createSection("combatStats", memory.getCombatStats());

            // Save behavioral patterns (convert to string representations for
            // serialization)
            Map<String, Object> serializablePatterns = new HashMap<>();
            // In a full implementation, we would convert complex objects to serializable
            // formats

            config.createSection("behavioralPatterns", serializablePatterns);

            config.save(playerFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save player data for " + memory.getPlayerId() + ": " + e.getMessage());
        }
    }

    public PlayerMemory loadPlayerMemory(UUID playerId) {
        PlayerMemory memory = new PlayerMemory(playerId);
        File playerFile = new File(playerDataFolder, playerId.toString() + ".yml");

        if (playerFile.exists()) {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

                // Load combat stats
                if (config.contains("combatStats")) {
                    Set<String> keys = config.getConfigurationSection("combatStats").getKeys(false);
                    for (String key : keys) {
                        int value = config.getInt("combatStats." + key, 0);
                        // Use reflection to access the private combatStats field
                        // In a full implementation, we would have proper setters
                    }
                }

                // Load behavioral patterns
                if (config.contains("behavioralPatterns")) {
                    // In a full implementation, we would load the behavioral patterns
                }
            } catch (Exception e) {
                plugin.getLogger().severe("Could not load player data for " + playerId + ": " + e.getMessage());
            }
        }

        return memory;
    }

    public void saveAllPlayerMemories(Iterable<PlayerMemory> memories) {
        for (PlayerMemory memory : memories) {
            savePlayerMemory(memory);
        }
    }

    public void loadAllPlayerMemories(Map<UUID, PlayerMemory> memoryMap) {
        // Load all player data files
        File[] files = playerDataFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files != null) {
            for (File file : files) {
                try {
                    String fileName = file.getName();
                    String uuidString = fileName.substring(0, fileName.length() - 4); // Remove .yml extension
                    UUID playerId = UUID.fromString(uuidString);
                    PlayerMemory memory = loadPlayerMemory(playerId);
                    memoryMap.put(playerId, memory);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Invalid UUID in filename: " + file.getName());
                }
            }
        }
    }
}