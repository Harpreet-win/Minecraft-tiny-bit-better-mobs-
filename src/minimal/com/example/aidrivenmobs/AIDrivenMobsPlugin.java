package com.example.aidrivenmobs;

import org.bukkit.plugin.java.JavaPlugin;

public class AIDrivenMobsPlugin extends JavaPlugin {

    private static AIDrivenMobsPlugin instance;
    private MobMemoryManager memoryManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("AI-Driven Mobs Plugin is starting...");

        // Initialize memory manager
        memoryManager = new MobMemoryManager(this);

        // Register event listeners
        getServer().getPluginManager().registerEvents(new MobBehaviorListener(this), this);

        getLogger().info("AI-Driven Mobs Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Save all memory data
        if (memoryManager != null) {
            memoryManager.saveAllData();
        }

        getLogger().info("AI-Driven Mobs Plugin has been disabled!");
    }

    public static AIDrivenMobsPlugin getInstance() {
        return instance;
    }

    public MobMemoryManager getMemoryManager() {
        return memoryManager;
    }
}