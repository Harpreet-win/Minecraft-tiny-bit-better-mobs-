package com.example.aidrivenmobs;

import com.example.aidrivenmobs.data.PlayerMemory;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Custom goal for creepers to wait until the player's shield is down before
 * attacking
 */
public class WaitForShieldDownGoal extends CustomMobGoal {
    private Creeper creeper;
    private Player targetPlayer;
    private int waitTime;
    private static final int MAX_WAIT_TIME = 100; // 5 seconds at 20 ticks per second
    private boolean isWaiting;

    public WaitForShieldDownGoal(AIDrivenMobsPlugin plugin, Creeper creeper) {
        super(plugin, creeper);
        this.creeper = creeper;
        this.waitTime = 0;
        this.isWaiting = false;
    }

    @Override
    public boolean canStart() {
        // Check if creeper has a target player
        if (creeper.getTarget() instanceof Player) {
            targetPlayer = (Player) creeper.getTarget();

            // Check if player has high shield block count in memory
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(targetPlayer);
            return memory.getStat("shield_blocks") > 10; // Only activate if player uses shield frequently
        }
        return false;
    }

    @Override
    public void start() {
        waitTime = 0;
        isWaiting = true;
        // Stop normal creeper behavior
        creeper.setTarget(null);
        plugin.getLogger().info("Creeper at " + creeper.getLocation() + " is waiting for " + targetPlayer.getName()
                + "'s shield to lower");
    }

    @Override
    public void stop() {
        // Resume normal behavior
        if (targetPlayer != null) {
            creeper.setTarget(targetPlayer);
        }
        isWaiting = false;
        waitTime = 0;
        plugin.getLogger().info("Creeper stopped waiting for shield");
    }

    @Override
    public void tick() {
        if (!isWaiting)
            return;

        waitTime++;

        // Check if player is no longer blocking
        if (targetPlayer != null && !targetPlayer.isBlocking()) {
            // Player shield is down, resume normal behavior
            creeper.setTarget(targetPlayer);
            plugin.getLogger().info("Creeper detected shield is down, resuming attack");
            isWaiting = false;
        }

        // Occasionally make a hissing sound while waiting
        if (waitTime % 20 == 0) { // Every second
            creeper.getWorld().playSound(creeper.getLocation(), "entity.creeper.hurt", 0.5f, 1.0f);
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue waiting if player is still blocking and we haven't waited too long
        if (targetPlayer != null && targetPlayer.isBlocking() && waitTime < MAX_WAIT_TIME) {
            return true;
        }

        // If we've waited too long, give up and explode
        if (waitTime >= MAX_WAIT_TIME && targetPlayer != null) {
            plugin.getLogger().info("Creeper gave up waiting and is exploding");
            creeper.setTarget(targetPlayer); // Resume targeting
            // In 1.16.5, we use explode() method instead of setIgnited()
            creeper.explode(); // Start exploding
            return false;
        }

        return false;
    }

    public boolean isWaiting() {
        return isWaiting;
    }
}