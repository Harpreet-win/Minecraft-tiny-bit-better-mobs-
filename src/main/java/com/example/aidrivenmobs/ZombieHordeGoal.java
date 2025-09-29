package com.example.aidrivenmobs;

import com.example.aidrivenmobs.data.PlayerMemory;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom goal for zombies to form hordes and break into player shelters
 */
public class ZombieHordeGoal extends CustomMobGoal {
    private Zombie zombie;
    private Player targetPlayer;
    private boolean isInHorde;
    private boolean shouldBreakDoors;

    public ZombieHordeGoal(AIDrivenMobsPlugin plugin, Zombie zombie) {
        super(plugin, zombie);
        this.zombie = zombie;
        this.isInHorde = false;
        this.shouldBreakDoors = false;
    }

    @Override
    public boolean canStart() {
        // Check if zombie has a target player
        if (zombie.getTarget() instanceof Player) {
            targetPlayer = (Player) zombie.getTarget();

            // Check if player frequently hides indoors
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(targetPlayer);
            return memory.getStat("indoor_time") > 30; // Only activate if player spends significant time indoors
        }
        return false;
    }

    @Override
    public void start() {
        isInHorde = true;
        shouldBreakDoors = true;
        targetPlayer = (Player) zombie.getTarget();

        plugin.getLogger()
                .info("Zombie at " + zombie.getLocation() + " is joining a horde to break into player's shelter");

        // Enable door breaking for this zombie (1.16.5 compatible approach)
        try {
            // Try the newer method first
            zombie.setCanBreakDoors(true);
        } catch (NoSuchMethodError e) {
            // If that fails, use reflection or skip (1.16.5 compatibility)
            plugin.getLogger().info("Skipping door breaking enable for 1.16.5 compatibility");
        }

        // Coordinate with nearby zombies
        coordinateWithNearbyZombies();
    }

    @Override
    public void stop() {
        isInHorde = false;
        shouldBreakDoors = false;
        targetPlayer = null;

        // Disable door breaking (1.16.5 compatible approach)
        try {
            zombie.setCanBreakDoors(false);
        } catch (NoSuchMethodError e) {
            // If that fails, just continue (1.16.5 compatibility)
            plugin.getLogger().info("Skipping door breaking disable for 1.16.5 compatibility");
        }
    }

    @Override
    public void tick() {
        if (isInHorde && targetPlayer != null) {
            // Maintain horde behavior
            maintainHordeFormation();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue if still in horde and targeting the player
        return isInHorde && zombie.getTarget() == targetPlayer;
    }

    private void coordinateWithNearbyZombies() {
        // Find nearby zombies and coordinate with them
        List<Zombie> nearbyZombies = zombie.getNearbyEntities(15, 15, 15).stream()
                .filter(entity -> entity instanceof Zombie && entity != zombie)
                .map(entity -> (Zombie) entity)
                .collect(Collectors.toList());

        // Enable door breaking for nearby zombies (1.16.5 compatible approach)
        for (Zombie nearbyZombie : nearbyZombies) {
            try {
                nearbyZombie.setCanBreakDoors(true);
            } catch (NoSuchMethodError e) {
                // If that fails, just continue (1.16.5 compatibility)
                plugin.getLogger().info("Skipping door breaking for nearby zombie for 1.16.5 compatibility");
            }
        }

        plugin.getLogger().info("Coordinating with " + nearbyZombies.size() + " nearby zombies");
    }

    private void maintainHordeFormation() {
        // Maintain horde formation and coordinate attacks
        // In a full implementation, this would:
        // 1. Keep zombies in formation
        // 2. Assign roles within the horde
        // 3. Adapt to player movements and shelter locations

        plugin.getLogger().info("Maintaining zombie horde formation");
    }
}