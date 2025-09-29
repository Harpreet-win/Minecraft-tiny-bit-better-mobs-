package com.example.aidrivenmobs;

import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom goal for bees to coordinate swarm attacks
 */
public class BeeSwarmGoal extends CustomMobGoal {
    private Bee bee;
    private Player targetPlayer;
    private boolean isInSwarm;
    private static final int SWARM_SIZE_THRESHOLD = 3;

    public BeeSwarmGoal(AIDrivenMobsPlugin plugin, Bee bee) {
        super(plugin, bee);
        this.bee = bee;
        this.isInSwarm = false;
    }

    @Override
    public boolean canStart() {
        // Check if bee has a target player
        if (bee.getTarget() instanceof Player) {
            targetPlayer = (Player) bee.getTarget();

            // Check if there are other bees nearby to form a swarm
            List<Bee> nearbyBees = bee.getNearbyEntities(10, 10, 10).stream()
                    .filter(entity -> entity instanceof Bee && entity != bee)
                    .map(entity -> (Bee) entity)
                    .limit(SWARM_SIZE_THRESHOLD)
                    .collect(Collectors.toList());

            // If there are enough bees nearby, form a swarm
            return nearbyBees.size() >= SWARM_SIZE_THRESHOLD;
        }
        return false;
    }

    @Override
    public void start() {
        isInSwarm = true;
        plugin.getLogger()
                .info("Bee at " + bee.getLocation() + " is joining a swarm attack against " + targetPlayer.getName());

        // Notify nearby bees to join the swarm
        coordinateWithNearbyBees();
    }

    @Override
    public void stop() {
        isInSwarm = false;
        targetPlayer = null;
    }

    @Override
    public void tick() {
        if (isInSwarm && targetPlayer != null) {
            // Coordinate swarm behavior
            maintainSwarmFormation();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue swarming if still targeting the player and in swarm mode
        return isInSwarm && bee.getTarget() == targetPlayer;
    }

    private void coordinateWithNearbyBees() {
        // Find nearby bees and coordinate with them
        List<Bee> nearbyBees = bee.getNearbyEntities(10, 10, 10).stream()
                .filter(entity -> entity instanceof Bee && entity != bee)
                .map(entity -> (Bee) entity)
                .collect(Collectors.toList());

        // In a full implementation, we would:
        // 1. Communicate with nearby bees
        // 2. Assign roles within the swarm
        // 3. Coordinate attack patterns

        plugin.getLogger().info("Coordinating with " + nearbyBees.size() + " nearby bees");
    }

    private void maintainSwarmFormation() {
        // Maintain swarm formation and coordinate attacks
        // In a full implementation, this would:
        // 1. Keep bees in formation
        // 2. Rotate attackers to prevent exhaustion
        // 3. Adapt to player movements

        plugin.getLogger().info("Maintaining bee swarm formation");
    }
}