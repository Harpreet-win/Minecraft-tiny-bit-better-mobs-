package com.example.aidrivenmobs;

import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;

/**
 * Custom goal for spiders to learn ambush tactics and avoid lit areas
 */
public class SpiderAmbushGoal extends CustomMobGoal {
    private Spider spider;
    private Player targetPlayer;
    private boolean isAmbushing;
    private boolean isAvoidingLight;
    private int ambushCooldown;
    private static final int MAX_AMBUSH_COOLDOWN = 100; // 5 seconds at 20 ticks per second

    public SpiderAmbushGoal(AIDrivenMobsPlugin plugin, Spider spider) {
        super(plugin, spider);
        this.spider = spider;
        this.isAmbushing = false;
        this.isAvoidingLight = false;
        this.ambushCooldown = 0;
    }

    @Override
    public boolean canStart() {
        // Check if spider has a target player
        if (spider.getTarget() instanceof Player) {
            targetPlayer = (Player) spider.getTarget();

            // Check if player frequently uses torches
            PlayerMemory playerMemory = plugin.getMemoryManager().getPlayerMemory(targetPlayer);
            int torchUsage = playerMemory.getStat("torch_spam");

            // If player uses torches a lot, spiders learn to ambush and avoid light
            return torchUsage > 5;
        }
        return false;
    }

    @Override
    public void start() {
        isAmbushing = true;
        isAvoidingLight = true;
        ambushCooldown = MAX_AMBUSH_COOLDOWN;

        plugin.getLogger().info(
                "Spider at " + spider.getLocation() + " is using ambush tactics against " + targetPlayer.getName());

        // In a full implementation, this would:
        // 1. Modify pathfinding to prefer dark areas
        // 2. Enable ceiling climbing behavior
        // 3. Set up drop ambush positions
    }

    @Override
    public void stop() {
        isAmbushing = false;
        isAvoidingLight = false;
        ambushCooldown = 0;
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (ambushCooldown > 0) {
            ambushCooldown--;
        }

        if (isAmbushing && targetPlayer != null) {
            // Execute ambush behavior
            executeAmbush();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue if still ambushing and cooldown is active
        return isAmbushing && ambushCooldown > 0 && spider.getTarget() == targetPlayer;
    }

    private void executeAmbush() {
        // Execute ambush tactics
        // In a full implementation, this would:
        // 1. Check for ceiling access points
        // 2. Position spider above player
        // 3. Drop down when player is in position
        // 4. Avoid well-lit areas

        plugin.getLogger().info("Spider is executing ambush tactics");
    }
}