package com.example.aidrivenmobs;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Wither;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Villager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Adapter class to integrate custom AI goals with mob entities
 * This class would interface with Paper's API or NMS in a real implementation
 */
public class MobAIAdapter {
    private AIDrivenMobsPlugin plugin;
    private Map<UUID, CustomMobGoal> activeGoals;

    public MobAIAdapter(AIDrivenMobsPlugin plugin) {
        this.plugin = plugin;
        this.activeGoals = new HashMap<>();
    }

    /**
     * Apply adaptive AI behaviors to a mob entity
     * 
     * @param entity The mob entity
     */
    public void applyAdaptiveBehaviors(Entity entity) {
        if (entity instanceof Mob) {
            Mob mob = (Mob) entity;

            // Remove any existing custom goals for this mob
            removeCustomGoals(mob);

            // Apply appropriate custom goals based on mob type
            if (entity instanceof Creeper) {
                applyCreeperBehaviors((Creeper) entity);
            } else if (entity instanceof Skeleton) {
                applySkeletonBehaviors((Skeleton) entity);
            } else if (entity instanceof Zombie) {
                applyZombieBehaviors((Zombie) entity);
            } else if (entity instanceof Spider) {
                applySpiderBehaviors((Spider) entity);
            } else if (entity instanceof Enderman) {
                applyEndermanBehaviors((Enderman) entity);
            } else if (entity instanceof Witch) {
                applyWitchBehaviors((Witch) entity);
            } else if (entity instanceof Slime) {
                applySlimeBehaviors((Slime) entity);
            } else if (entity instanceof Phantom) {
                applyPhantomBehaviors((Phantom) entity);
            } else if (entity instanceof Ghast) {
                applyGhastBehaviors((Ghast) entity);
            } else if (entity instanceof Blaze) {
                applyBlazeBehaviors((Blaze) entity);
            } else if (entity instanceof IronGolem) {
                applyIronGolemBehaviors((IronGolem) entity);
            } else if (entity instanceof Animals) {
                applyAnimalBehaviors((Animals) entity);
            } else if (entity instanceof Wolf) {
                applyWolfBehaviors((Wolf) entity);
            } else if (entity instanceof Bee) {
                applyBeeBehaviors((Bee) entity);
            } else if (entity instanceof Villager) {
                applyVillagerBehaviors((Villager) entity);
            } else if (entity instanceof EnderDragon) {
                applyEnderDragonBehaviors((EnderDragon) entity);
            } else if (entity instanceof Wither) {
                applyWitherBehaviors((Wither) entity);
            } else if (entity instanceof ElderGuardian) {
                applyElderGuardianBehaviors((ElderGuardian) entity);
            }
        }
    }

    /**
     * Apply creeper-specific behaviors
     */
    private void applyCreeperBehaviors(Creeper creeper) {
        // Check if player uses shield frequently
        if (creeper.getTarget() != null) {
            WaitForShieldDownGoal goal = new WaitForShieldDownGoal(plugin, creeper);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(creeper.getUniqueId(), goal);
                plugin.getLogger().info("Applied WaitForShieldDownGoal to creeper at " + creeper.getLocation());
            }
        }
    }

    /**
     * Apply skeleton-specific behaviors
     */
    private void applySkeletonBehaviors(Skeleton skeleton) {
        // Check if player uses melee frequently
        if (skeleton.getTarget() != null) {
            SquadFormationGoal goal = new SquadFormationGoal(plugin, skeleton);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(skeleton.getUniqueId(), goal);
                plugin.getLogger().info("Applied SquadFormationGoal to skeleton at " + skeleton.getLocation());
            }
        }
    }

    /**
     * Apply zombie-specific behaviors
     */
    private void applyZombieBehaviors(Zombie zombie) {
        // Check if player hides indoors frequently
        if (zombie.getTarget() != null) {
            ZombieHordeGoal goal = new ZombieHordeGoal(plugin, zombie);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(zombie.getUniqueId(), goal);
                plugin.getLogger().info("Applied ZombieHordeGoal to zombie at " + zombie.getLocation());
            }
        }
    }

    /**
     * Apply spider-specific behaviors
     */
    private void applySpiderBehaviors(Spider spider) {
        // Check if player uses torches frequently
        if (spider.getTarget() != null) {
            SpiderAmbushGoal goal = new SpiderAmbushGoal(plugin, spider);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(spider.getUniqueId(), goal);
                plugin.getLogger().info("Applied SpiderAmbushGoal to spider at " + spider.getLocation());
            }
        }
    }

    /**
     * Apply enderman-specific behaviors
     */
    private void applyEndermanBehaviors(Enderman enderman) {
        // Check if player stares frequently
        if (enderman.getTarget() != null) {
            EndermanTeleportGoal goal = new EndermanTeleportGoal(plugin, enderman);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(enderman.getUniqueId(), goal);
                plugin.getLogger().info("Applied EndermanTeleportGoal to enderman at " + enderman.getLocation());
            }
        }
    }

    /**
     * Apply witch-specific behaviors
     */
    private void applyWitchBehaviors(Witch witch) {
        // Analyze player combat style
        if (witch.getTarget() != null) {
            WitchPotionStrategyGoal goal = new WitchPotionStrategyGoal(plugin, witch);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(witch.getUniqueId(), goal);
                plugin.getLogger().info("Applied WitchPotionStrategyGoal to witch at " + witch.getLocation());
            }
        }
    }

    /**
     * Apply slime-specific behaviors
     */
    private void applySlimeBehaviors(Slime slime) {
        // Coordinate with other slimes
        if (slime.getTarget() != null) {
            SlimeCoordinationGoal goal = new SlimeCoordinationGoal(plugin, slime);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(slime.getUniqueId(), goal);
                plugin.getLogger().info("Applied SlimeCoordinationGoal to slime at " + slime.getLocation());
            }
        }
    }

    /**
     * Apply phantom-specific behaviors
     */
    private void applyPhantomBehaviors(Phantom phantom) {
        // Target shields specifically
        if (phantom.getTarget() != null) {
            PhantomSwoopGoal goal = new PhantomSwoopGoal(plugin, phantom);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(phantom.getUniqueId(), goal);
                plugin.getLogger().info("Applied PhantomSwoopGoal to phantom at " + phantom.getLocation());
            }
        }
    }

    /**
     * Apply ghast-specific behaviors
     */
    private void applyGhastBehaviors(Ghast ghast) {
        // Strafe around cover
        if (ghast.getTarget() != null) {
            GhastStrafeGoal goal = new GhastStrafeGoal(plugin, ghast);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(ghast.getUniqueId(), goal);
                plugin.getLogger().info("Applied GhastStrafeGoal to ghast at " + ghast.getLocation());
            }
        }
    }

    /**
     * Apply blaze-specific behaviors
     */
    private void applyBlazeBehaviors(Blaze blaze) {
        // Coordinate fireball attacks
        if (blaze.getTarget() != null) {
            BlazeFireballWallGoal goal = new BlazeFireballWallGoal(plugin, blaze);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(blaze.getUniqueId(), goal);
                plugin.getLogger().info("Applied BlazeFireballWallGoal to blaze at " + blaze.getLocation());
            }
        }
    }

    /**
     * Apply iron golem-specific behaviors
     */
    private void applyIronGolemBehaviors(IronGolem golem) {
        // Protect villagers and remember harmful players
        if (golem.getTarget() != null) {
            IronGolemProtectionGoal goal = new IronGolemProtectionGoal(plugin, golem);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(golem.getUniqueId(), goal);
                plugin.getLogger().info("Applied IronGolemProtectionGoal to iron golem at " + golem.getLocation());
            }
        }
    }

    /**
     * Apply animal-specific behaviors
     */
    private void applyAnimalBehaviors(Animals animal) {
        // Learn to avoid aggressive players
        AnimalAvoidanceGoal goal = new AnimalAvoidanceGoal(plugin, animal);
        if (goal.canStart()) {
            goal.start();
            activeGoals.put(animal.getUniqueId(), goal);
            plugin.getLogger()
                    .info("Applied AnimalAvoidanceGoal to " + animal.getType().name() + " at " + animal.getLocation());
        }
    }

    /**
     * Apply wolf-specific behaviors
     */
    private void applyWolfBehaviors(Wolf wolf) {
        // Seek revenge for owner deaths
        if (wolf.getOwner() != null && wolf.getTarget() != null) {
            WolfRevengeGoal goal = new WolfRevengeGoal(plugin, wolf);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(wolf.getUniqueId(), goal);
                plugin.getLogger().info("Applied WolfRevengeGoal to wolf at " + wolf.getLocation());
            }
        }
    }

    /**
     * Apply bee-specific behaviors
     */
    private void applyBeeBehaviors(Bee bee) {
        // Swarm tactics
        if (bee.getTarget() != null) {
            BeeSwarmGoal goal = new BeeSwarmGoal(plugin, bee);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(bee.getUniqueId(), goal);
                plugin.getLogger().info("Applied BeeSwarmGoal to bee at " + bee.getLocation());
            }
        }
    }

    /**
     * Apply villager-specific behaviors
     */
    private void applyVillagerBehaviors(Villager villager) {
        // Trust system is handled by VillagerTrustSystem
        plugin.getLogger().info("Applied trust system behaviors to villager at " + villager.getLocation());
    }

    /**
     * Apply ender dragon-specific behaviors
     */
    private void applyEnderDragonBehaviors(EnderDragon dragon) {
        // Adapt to player attack patterns
        if (dragon.getTarget() != null) {
            EnderDragonAdaptationGoal goal = new EnderDragonAdaptationGoal(plugin, dragon);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(dragon.getUniqueId(), goal);
                plugin.getLogger().info("Applied EnderDragonAdaptationGoal to ender dragon");
            }
        }
    }

    /**
     * Apply wither-specific behaviors
     */
    private void applyWitherBehaviors(Wither wither) {
        // Mutate attack phases
        if (wither.getTarget() != null) {
            WitherMutationGoal goal = new WitherMutationGoal(plugin, wither);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(wither.getUniqueId(), goal);
                plugin.getLogger().info("Applied WitherMutationGoal to wither");
            }
        }
    }

    /**
     * Apply elder guardian-specific behaviors
     */
    private void applyElderGuardianBehaviors(ElderGuardian elderGuardian) {
        // Remember raiders
        if (elderGuardian.getTarget() != null) {
            ElderGuardianMemoryGoal goal = new ElderGuardianMemoryGoal(plugin, elderGuardian);
            if (goal.canStart()) {
                goal.start();
                activeGoals.put(elderGuardian.getUniqueId(), goal);
                plugin.getLogger().info("Applied ElderGuardianMemoryGoal to elder guardian");
            }
        }
    }

    /**
     * Update all active custom goals
     */
    public void updateGoals() {
        // Create a copy of the key set to avoid ConcurrentModificationException
        for (UUID mobId : new HashMap<>(activeGoals).keySet()) {
            CustomMobGoal goal = activeGoals.get(mobId);
            if (goal != null) {
                if (goal.shouldContinue()) {
                    goal.tick();
                } else {
                    goal.stop();
                    activeGoals.remove(mobId);
                }
            }
        }
    }

    /**
     * Remove custom goals for a specific mob
     */
    public void removeCustomGoals(Mob mob) {
        CustomMobGoal goal = activeGoals.remove(mob.getUniqueId());
        if (goal != null) {
            goal.stop();
        }
    }

    /**
     * Remove all custom goals
     */
    public void removeAllGoals() {
        for (CustomMobGoal goal : activeGoals.values()) {
            goal.stop();
        }
        activeGoals.clear();
    }

    /**
     * Check if a mob has an active custom goal
     */
    public boolean hasActiveGoal(Mob mob) {
        return activeGoals.containsKey(mob.getUniqueId());
    }
}