package com.example.aidrivenmobs;

import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Player;

/**
 * Custom goal for hoglins to learn to avoid pits and traps after seeing others
 * die
 */
public class HoglinTrapAvoidanceGoal extends CustomMobGoal {
    private Hoglin hoglin;
    private Player targetPlayer;
    private boolean isAvoidingTraps;
    private int avoidanceCooldown;
    private static final int MAX_AVOIDANCE_COOLDOWN = 150; // 7.5 seconds at 20 ticks per second

    public HoglinTrapAvoidanceGoal(AIDrivenMobsPlugin plugin, Hoglin hoglin) {
        super(plugin, hoglin);
        this.hoglin = hoglin;
        this.isAvoidingTraps = false;
        this.avoidanceCooldown = 0;
    }

    @Override
    public boolean canStart() {
        // Check if hoglin has knowledge of trap locations
        MobKnowledge knowledge = plugin.getMemoryManager().getMobKnowledge(hoglin.getUniqueId(), "hoglin");
        Boolean hasTrapKnowledge = (Boolean) knowledge.getKnowledge("trap_aware");

        // Check if hoglin has a target player
        if (hoglin.getTarget() instanceof Player) {
            targetPlayer = (Player) hoglin.getTarget();

            // If hoglin has learned about traps, avoid them
            return hasTrapKnowledge != null && hasTrapKnowledge;
        }
        return false;
    }

    @Override
    public void start() {
        isAvoidingTraps = true;
        avoidanceCooldown = MAX_AVOIDANCE_COOLDOWN;

        plugin.getLogger().info("Hoglin at " + hoglin.getLocation() + " is avoiding known traps while targeting "
                + targetPlayer.getName());

        // In a full implementation, this would:
        // 1. Modify pathfinding to avoid known trap locations
        // 2. Remember locations where other hoglins died
        // 3. Warn other hoglins about dangerous areas
    }

    @Override
    public void stop() {
        isAvoidingTraps = false;
        avoidanceCooldown = 0;
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (avoidanceCooldown > 0) {
            avoidanceCooldown--;
        }

        if (isAvoidingTraps && targetPlayer != null) {
            // Execute trap avoidance behavior
            executeTrapAvoidance();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue if still avoiding traps and cooldown is active
        return isAvoidingTraps && avoidanceCooldown > 0 && hoglin.getTarget() == targetPlayer;
    }

    private void executeTrapAvoidance() {
        // Execute trap avoidance behavior
        // In a full implementation, this would:
        // 1. Check for known trap locations in memory
        // 2. Modify pathfinding to avoid those locations
        // 3. Remember new trap locations when discovered

        plugin.getLogger().info("Hoglin is avoiding known traps");
    }
}