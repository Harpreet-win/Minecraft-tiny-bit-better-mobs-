package com.example.aidrivenmobs;

import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

/**
 * Custom goal for slimes to coordinate split forms and surround players
 */
public class SlimeCoordinationGoal extends CustomMobGoal {
    private Slime slime;
    private Player targetPlayer;
    private boolean isCoordinating;
    private int coordinationCooldown;
    private static final int MAX_COORDINATION_COOLDOWN = 60; // 3 seconds at 20 ticks per second

    public SlimeCoordinationGoal(AIDrivenMobsPlugin plugin, Slime slime) {
        super(plugin, slime);
        this.slime = slime;
        this.isCoordinating = false;
        this.coordinationCooldown = 0;
    }

    @Override
    public boolean canStart() {
        // Check if slime has a target player
        if (slime.getTarget() instanceof Player) {
            targetPlayer = (Player) slime.getTarget();

            // Check if slime is large enough to split
            if (slime.getSize() > 1) {
                // Check if there are other slimes nearby to coordinate with
                long nearbySlimes = slime.getNearbyEntities(10, 10, 10).stream()
                        .filter(entity -> entity instanceof Slime)
                        .count();

                // If there are nearby slimes, coordinate for surround tactics
                return nearbySlimes > 1;
            }
        }
        return false;
    }

    @Override
    public void start() {
        isCoordinating = true;
        coordinationCooldown = MAX_COORDINATION_COOLDOWN;

        plugin.getLogger().info("Slime at " + slime.getLocation() + " is coordinating with other slimes to surround "
                + targetPlayer.getName());

        // In a full implementation, this would:
        // 1. Communicate with nearby slimes
        // 2. Coordinate split timing
        // 3. Position slimes to surround player
    }

    @Override
    public void stop() {
        isCoordinating = false;
        coordinationCooldown = 0;
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (coordinationCooldown > 0) {
            coordinationCooldown--;
        }

        if (isCoordinating && targetPlayer != null) {
            // Execute coordination behavior
            executeCoordination();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue if still coordinating and cooldown is active
        return isCoordinating && coordinationCooldown > 0 && slime.getTarget() == targetPlayer;
    }

    private void executeCoordination() {
        // Execute slime coordination
        // In a full implementation, this would:
        // 1. Position slimes to surround player
        // 2. Coordinate split timing for maximum effect
        // 3. Adapt to player movements

        plugin.getLogger().info("Slime is coordinating with other slimes");
    }
}