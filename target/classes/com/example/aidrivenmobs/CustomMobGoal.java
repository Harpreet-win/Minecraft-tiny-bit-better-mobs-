package com.example.aidrivenmobs;

/**
 * Base class for custom mob AI goals
 */
public abstract class CustomMobGoal {
    protected Object entity;
    protected AIDrivenMobsPlugin plugin;

    public CustomMobGoal(AIDrivenMobsPlugin plugin, Object entity) {
        this.plugin = plugin;
        this.entity = entity;
    }

    /**
     * Check if this goal should start
     * 
     * @return true if the goal should start
     */
    public abstract boolean canStart();

    /**
     * Called when the goal starts
     */
    public abstract void start();

    /**
     * Called when the goal stops
     */
    public abstract void stop();

    /**
     * Called each tick while the goal is active
     */
    public abstract void tick();

    /**
     * Check if the goal should continue
     * 
     * @return true if the goal should continue
     */
    public abstract boolean shouldContinue();
}