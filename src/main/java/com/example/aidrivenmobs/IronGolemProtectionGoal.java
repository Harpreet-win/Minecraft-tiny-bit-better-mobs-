package com.example.aidrivenmobs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;

/**
 * Custom goal for Iron Golems to protect villagers and remember players who
 * harm villagers
 */
public class IronGolemProtectionGoal extends CustomMobGoal {
    private IronGolem golem;
    private Player targetPlayer;
    private boolean isProtectingVillagers;
    private static final int REVENGE_TIME_THRESHOLD = 300000; // 5 minutes in milliseconds

    public IronGolemProtectionGoal(AIDrivenMobsPlugin plugin, IronGolem golem) {
        super(plugin, golem);
        this.golem = golem;
        this.isProtectingVillagers = true;
    }

    @Override
    public boolean canStart() {
        // Check if golem is in protection mode
        MobKnowledge knowledge = plugin.getMemoryManager().getMobKnowledge(golem.getUniqueId(), "iron_golem");
        Boolean isProtecting = (Boolean) knowledge.getKnowledge("protector_mode");

        if (isProtecting != null && isProtecting) {
            // Check if there's a target player
            if (golem.getTarget() instanceof Player) {
                targetPlayer = (Player) golem.getTarget();
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        // Start protecting behavior
        if (targetPlayer != null) {
            plugin.getLogger().info(
                    "Iron Golem at " + golem.getLocation() + " is protecting villagers from " + targetPlayer.getName());
        }
    }

    @Override
    public void stop() {
        // Stop protecting behavior
        targetPlayer = null;
    }

    @Override
    public void tick() {
        // Continue protecting villagers
        if (targetPlayer != null) {
            // Check if we should switch to revenge mode
            checkForRevengeMode();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue if still protecting and target is valid
        return isProtectingVillagers && golem.getTarget() == targetPlayer;
    }

    private void checkForRevengeMode() {
        MobKnowledge knowledge = plugin.getMemoryManager().getMobKnowledge(golem.getUniqueId(), "iron_golem");
        Boolean isVengeful = (Boolean) knowledge.getKnowledge("vengeful");

        // If the golem has been attacked by this player before, become vengeful
        if (isVengeful != null && isVengeful) {
            // In revenge mode, the golem will be more aggressive
            // This would modify the golem's attack behavior in a full implementation
            plugin.getLogger().info("Iron Golem is in revenge mode against " + targetPlayer.getName());
        }
    }
}