package com.example.aidrivenmobs;

import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.Player;

/**
 * Custom goal for Elder Guardians to remember players who raided their monuments
 */
public class ElderGuardianMemoryGoal extends CustomMobGoal {
    private ElderGuardian elderGuardian;
    private Player targetPlayer;
    private boolean hasPlayerRaided;
    private int miningFatigueLevel;
    private static final int MAX_MINING_FATIGUE = 5;
    
    public ElderGuardianMemoryGoal(AIDrivenMobsPlugin plugin, ElderGuardian elderGuardian) {
        super(plugin, elderGuardian);
        this.elderGuardian = elderGuardian;
        this.hasPlayerRaided = false;
        this.miningFatigueLevel = 1;
    }
    
    @Override
    public boolean canStart() {
        // Check if elder guardian has a target player
        if (elderGuardian.getTarget() instanceof Player) {
            targetPlayer = (Player) elderGuardian.getTarget();
            
            // Check if player has raided this monument before
            MobKnowledge knowledge = plugin.getMemoryManager().getMobKnowledge(elderGuardian.getUniqueId(), "elder_guardian");
            String raiderKey = "raider_" + targetPlayer.getUniqueId().toString();
            Boolean hasRaided = (Boolean) knowledge.getKnowledge(raiderKey);
            
            if (hasRaided != null && hasRaided) {
                hasPlayerRaided = true;
                
                // Increase mining fatigue level for repeat raiders
                Integer previousFatigue = (Integer) knowledge.getKnowledge("fatigue_level_" + targetPlayer.getUniqueId().toString());
                if (previousFatigue != null) {
                    miningFatigueLevel = Math.min(MAX_MINING_FATIGUE, previousFatigue + 1);
                }
                
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void start() {
        plugin.getLogger().info("Elder Guardian at " + elderGuardian.getLocation() + " remembers " + targetPlayer.getName() + " raided the monument before");
        
        // In a full implementation, this would:
        // 1. Apply stronger mining fatigue to repeat raiders
        // 2. Increase aggression toward this player
        // 3. Alert other guardians about this player
    }
    
    @Override
    public void stop() {
        targetPlayer = null;
        hasPlayerRaided = false;
    }
    
    @Override
    public void tick() {
        if (hasPlayerRaided && targetPlayer != null) {
            // Apply stronger effects to repeat raiders
            applyStrongerEffects();
        }
    }
    
    @Override
    public boolean shouldContinue() {
        // Continue if player is still a known raider
        return hasPlayerRaided && elderGuardian.getTarget() == targetPlayer;
    }
    
    private void applyStrongerEffects() {
        // Apply stronger mining fatigue and other effects to repeat raiders
        // In a full implementation, this would:
        // 1. Apply mining fatigue based on raid history
        // 2. Increase damage output against this player
        // 3. Use special attacks more frequently
        
        plugin.getLogger().info("Elder Guardian is applying stronger effects (level " + miningFatigueLevel + ") to raider " + targetPlayer.getName());
    }
}