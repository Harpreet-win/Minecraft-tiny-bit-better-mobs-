package com.example.aidrivenmobs;

import org.bukkit.plugin.java.JavaPlugin;

public class AIDrivenMobsPlugin extends JavaPlugin {

    private static AIDrivenMobsPlugin instance;
    private MobMemoryManager memoryManager;
    private VillagerTrustSystem trustSystem;
    private MobAIManager aiManager;
    private GlobalAIManager globalAIManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("AI-Driven Mobs Plugin is starting...");

        // Initialize memory manager
        memoryManager = new MobMemoryManager(this);

        // Initialize trust system
        trustSystem = new VillagerTrustSystem(this);

        // Initialize AI manager
        aiManager = new MobAIManager(this);

        // Initialize global AI manager
        globalAIManager = new GlobalAIManager(this);

        // Register event listeners
        getServer().getPluginManager().registerEvents(new MobBehaviorListener(this), this);

        // Register commands
        this.getCommand("mobmemory").setExecutor(new MobMemoryCommand(this));

        getLogger().info("AI-Driven Mobs Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Save all memory data
        if (memoryManager != null) {
            memoryManager.saveAllData();
        }

        // Save global AI data
        if (globalAIManager != null) {
            globalAIManager.saveGlobalData();
        }

        getLogger().info("AI-Driven Mobs Plugin has been disabled!");
    }

    public static AIDrivenMobsPlugin getInstance() {
        return instance;
    }

    public MobMemoryManager getMemoryManager() {
        return memoryManager;
    }

    public VillagerTrustSystem getTrustSystem() {
        return trustSystem;
    }

    public MobAIManager getAIManager() {
        return aiManager;
    }

    public GlobalAIManager getGlobalAIManager() {
        return globalAIManager;
    }
}