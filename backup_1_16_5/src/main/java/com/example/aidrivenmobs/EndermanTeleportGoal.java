package com.example.aidrivenmobs;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;

/**
 * Custom goal for endermen to teleport more unpredictably and steal torches
 * when stared at
 */
public class EndermanTeleportGoal extends CustomMobGoal {
    private Enderman enderman;
    private Player targetPlayer;
    private int stareCount;
    private boolean isUnpredictable;
    private int teleportCooldown;
    private static final int MAX_TELEPORT_COOLDOWN = 40; // 2 seconds at 20 ticks per second

    public EndermanTeleportGoal(AIDrivenMobsPlugin plugin, Enderman enderman) {
        super(plugin, enderman);
        this.enderman = enderman;
        this.stareCount = 0;
        this.isUnpredictable = false;
        this.teleportCooldown = 0;
    }

    @Override
    public boolean canStart() {
        // Check if enderman has a target player
        if (enderman.getTarget() instanceof Player) {
            targetPlayer = (Player) enderman.getTarget();

            // Check if player frequently stares at endermen
            PlayerMemory playerMemory = plugin.getMemoryManager().getPlayerMemory(targetPlayer);
            int stareCount = playerMemory.getStat("enderman_stares");

            // If player stares a lot, endermen become more unpredictable
            return stareCount > 3;
        }
        return false;
    }

    @Override
    public void start() {
        isUnpredictable = true;
        teleportCooldown = 0;

        plugin.getLogger()
                .info("Enderman at " + enderman.getLocation() + " is becoming unpredictable due to player staring");

        // In a full implementation, this would:
        // 1. Modify teleportation patterns to be less predictable
        // 2. Enable torch stealing behavior
        // 3. Increase aggression
    }

    @Override
    public void stop() {
        isUnpredictable = false;
        teleportCooldown = 0;
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (teleportCooldown > 0) {
            teleportCooldown--;
        }

        if (isUnpredictable && targetPlayer != null) {
            // Execute unpredictable teleportation
            executeUnpredictableTeleport();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue if still unpredictable and targeting the player
        return isUnpredictable && enderman.getTarget() == targetPlayer;
    }

    private void executeUnpredictableTeleport() {
        // Execute unpredictable teleportation
        // In a full implementation, this would:
        // 1. Teleport to more random locations
        // 2. Teleport more frequently
        // 3. Attempt to steal torches if player is spamming them

        if (teleportCooldown <= 0) {
            // In a full implementation, we would actually teleport the enderman
            plugin.getLogger().info("Enderman is teleporting unpredictably");
            teleportCooldown = MAX_TELEPORT_COOLDOWN;
        }
    }
}