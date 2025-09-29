package com.example.aidrivenmobs;

import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

/**
 * Custom goal for wolves to become aggressive against mobs that kill their
 * owner repeatedly
 */
public class WolfRevengeGoal extends CustomMobGoal {
    private Wolf wolf;
    private Player owner;
    private String targetMobType;
    private int ownerDeathCount;
    private boolean isRevengeMode;

    public WolfRevengeGoal(AIDrivenMobsPlugin plugin, Wolf wolf) {
        super(plugin, wolf);
        this.wolf = wolf;
        this.owner = (Player) wolf.getOwner(); // Assuming the wolf is tamed
        this.targetMobType = null;
        this.ownerDeathCount = 0;
        this.isRevengeMode = false;
    }

    @Override
    public boolean canStart() {
        // Check if wolf has an owner
        if (owner == null) {
            return false;
        }

        // Check if wolf has learned to seek revenge
        MobKnowledge knowledge = plugin.getMemoryManager().getMobKnowledge(wolf.getUniqueId(), "wolf");
        String learnedTarget = (String) knowledge.getKnowledge("revenge_target");
        Integer deathCount = (Integer) knowledge.getKnowledge("owner_death_count");

        if (learnedTarget != null && deathCount != null && deathCount > 2) {
            targetMobType = learnedTarget;
            ownerDeathCount = deathCount;
            return true;
        }

        return false;
    }

    @Override
    public void start() {
        isRevengeMode = true;
        plugin.getLogger().info("Wolf at " + wolf.getLocation() + " is seeking revenge against " + targetMobType);

        // In a full implementation, this would modify the wolf's targeting behavior
        // to prioritize the revenge target
    }

    @Override
    public void stop() {
        isRevengeMode = false;
        targetMobType = null;
    }

    @Override
    public void tick() {
        if (isRevengeMode) {
            // Actively seek out the target mob type
            seekRevengeTarget();
        }
    }

    @Override
    public boolean shouldContinue() {
        // Continue if in revenge mode and owner is still dead
        return isRevengeMode && (owner == null || !owner.isOnline());
    }

    private void seekRevengeTarget() {
        // In a full implementation, this would:
        // 1. Scan for entities of the target type
        // 2. Prioritize targeting those entities
        // 3. Show increased aggression toward them

        plugin.getLogger().info("Wolf is seeking revenge against " + targetMobType);
    }
}