package com.example.aidrivenmobs;

import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;

/**
 * Custom goal for phantoms to swoop lower and target shields specifically
 */
public class PhantomSwoopGoal extends CustomMobGoal {
    private Phantom phantom;
    private Player targetPlayer;
    private boolean isShieldTargeting;
    private int swoopCooldown;
    private static final int MAX_SWOOP_COOLDOWN = 80; // 4 seconds at 20 ticks per second

    public PhantomSwoopGoal(AIDrivenMobsPlugin plugin, Phantom phantom) {
        super(plugin, phantom);
        this.phantom = phantom;
        this.isShieldTargeting = false;
        this.swoopCooldown = 0;
    }

    @Override
    public boolean canStart() {
        // Check if phantom has a target player
        if (phantom.getTarget() instanceof Player) {
            targetPlayer = (Player) phantom.getTarget();

            // Check if player frequently uses shield
            PlayerMemory playerMemory = plugin.getMemoryManager().getPlayerMemory(targetPlayer);
            int shieldBlocks = playerMemory.getStat("shield_blocks");

            // If player uses shield frequently, phantoms learn to target it specifically
            return shieldBlocks > 10;
        }
        return false;
    }

    @Override
    public void start() {
        isShieldTargeting = true;
        swoopCooldown = MAX_SWOOP_COOLDOWN;

        plugin.getLogger().info("Phantom at " + phantom.getLocation() + " is learning to swoop lower to target "
                + targetPlayer.getName() + "'s shield");

        // In a full implementation, this would:
        // 1. Modify swoop attack patterns
        // 2. Target shield specifically rather than player
        // 3. Swoop at lower altitude to knock shield down
    }

    @Override
    public void stop() {
        isShieldTargeting = false;
        swoopCooldown = 0;
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (swoopCooldown > 0) {
            swoopCooldown--;
        }

        if (isShieldTargeting && targetPlayer != null) {
            // Execute shield-targeting swoop
            executeShieldTargetingSwoop();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue if still targeting shield and cooldown is active
        return isShieldTargeting && swoopCooldown > 0 && phantom.getTarget() == targetPlayer;
    }

    private void executeShieldTargetingSwoop() {
        // Execute shield-targeting swoop
        // In a full implementation, this would:
        // 1. Adjust swoop altitude to target shield
        // 2. Time swoop to coincide with shield raise
        // 3. Aim for shield rather than player directly

        plugin.getLogger().info("Phantom is executing shield-targeting swoop");
    }
}