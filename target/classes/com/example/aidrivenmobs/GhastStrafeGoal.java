package com.example.aidrivenmobs;

import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;

/**
 * Custom goal for ghasts to strafe around cover instead of shooting predictable
 * fireballs
 */
public class GhastStrafeGoal extends CustomMobGoal {
    private Ghast ghast;
    private Player targetPlayer;
    private boolean isStrafing;
    private int strafeDirection;
    private int strafeCooldown;
    private static final int MAX_STRAFE_COOLDOWN = 100; // 5 seconds at 20 ticks per second

    public GhastStrafeGoal(AIDrivenMobsPlugin plugin, Ghast ghast) {
        super(plugin, ghast);
        this.ghast = ghast;
        this.isStrafing = false;
        this.strafeDirection = 1; // 1 for right, -1 for left
        this.strafeCooldown = 0;
    }

    @Override
    public boolean canStart() {
        // Check if ghast has a target player
        if (ghast.getTarget() instanceof Player) {
            targetPlayer = (Player) ghast.getTarget();

            // Check if player frequently hides behind cover
            PlayerMemory playerMemory = plugin.getMemoryManager().getPlayerMemory(targetPlayer);
            int coverUsage = playerMemory.getStat("cover_hiding");

            // If player uses cover frequently, ghasts learn to strafe
            return coverUsage > 3;
        }
        return false;
    }

    @Override
    public void start() {
        isStrafing = true;
        strafeCooldown = MAX_STRAFE_COOLDOWN;

        plugin.getLogger().info("Ghast at " + ghast.getLocation() + " is learning to strafe around cover to target "
                + targetPlayer.getName());

        // In a full implementation, this would:
        // 1. Modify fireball shooting patterns
        // 2. Add strafing movement around player cover
        // 3. Time shots to coincide with strafing movements
    }

    @Override
    public void stop() {
        isStrafing = false;
        strafeCooldown = 0;
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (strafeCooldown > 0) {
            strafeCooldown--;
        }

        if (isStrafing && targetPlayer != null) {
            // Execute strafing behavior
            executeStrafing();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue if still strafing and cooldown is active
        return isStrafing && strafeCooldown > 0 && ghast.getTarget() == targetPlayer;
    }

    private void executeStrafing() {
        // Execute strafing behavior
        // In a full implementation, this would:
        // 1. Move ghast in a strafing pattern around player cover
        // 2. Time fireball shots to coincide with strafing movements
        // 3. Adapt strafing pattern based on player movements

        plugin.getLogger().info("Ghast is strafing around player cover");
    }
}