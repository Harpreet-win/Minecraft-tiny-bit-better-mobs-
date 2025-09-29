package com.example.aidrivenmobs;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;

/**
 * Custom goal for animals to learn avoidance behavior based on player
 * interactions
 */
public class AnimalAvoidanceGoal extends CustomMobGoal {
    private Animals animal;
    private Player targetPlayer;
    private boolean shouldAvoidPlayer;
    private int avoidanceCooldown;
    private static final int MAX_AVOIDANCE_COOLDOWN = 200; // 10 seconds at 20 ticks per second

    public AnimalAvoidanceGoal(AIDrivenMobsPlugin plugin, Animals animal) {
        super(plugin, animal);
        this.animal = animal;
        this.shouldAvoidPlayer = false;
        this.avoidanceCooldown = 0;
    }

    @Override
    public boolean canStart() {
        // Check if animal has learned to avoid players
        MobKnowledge knowledge = plugin.getMemoryManager().getMobKnowledge(animal.getUniqueId(),
                animal.getType().name().toLowerCase());

        // Check interaction count with players
        // If player has interacted aggressively too many times, animal learns to avoid
        int aggressiveInteractions = 0;

        // In a full implementation, we would check all players in the area
        // For now, we'll use a simplified approach

        return aggressiveInteractions > 5; // Avoid if player has been aggressive more than 5 times
    }

    @Override
    public void start() {
        shouldAvoidPlayer = true;
        avoidanceCooldown = MAX_AVOIDANCE_COOLDOWN;
        plugin.getLogger()
                .info(animal.getType().name() + " at " + animal.getLocation() + " is learning to avoid players");
    }

    @Override
    public void stop() {
        shouldAvoidPlayer = false;
        avoidanceCooldown = 0;
    }

    @Override
    public void tick() {
        if (avoidanceCooldown > 0) {
            avoidanceCooldown--;
        }

        if (shouldAvoidPlayer) {
            // Implement avoidance behavior
            // In a full implementation, this would modify the animal's pathfinding
            avoidPlayers();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue avoiding if cooldown is active
        return avoidanceCooldown > 0 && shouldAvoidPlayer;
    }

    private void avoidPlayers() {
        // In a full implementation, this would:
        // 1. Find nearby players
        // 2. Calculate escape routes
        // 3. Modify the animal's pathfinding to avoid players
        plugin.getLogger().info(animal.getType().name() + " is avoiding players");
    }
}