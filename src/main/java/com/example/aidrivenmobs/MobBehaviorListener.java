package com.example.aidrivenmobs;

import com.example.aidrivenmobs.data.PlayerMemory;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class MobBehaviorListener implements Listener {
    private AIDrivenMobsPlugin plugin;

    public MobBehaviorListener(AIDrivenMobsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Creeper) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track melee hits against creepers
            memory.incrementStat("melee_hits");
            memory.incrementStat("hits_creeper");

            // Check if player is blocking with shield
            if (player.isBlocking()) {
                memory.incrementStat("shield_blocks");
            }
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Skeleton) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track melee hits against skeletons
            memory.incrementStat("melee_hits");
            memory.incrementStat("hits_skeleton");
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Zombie) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track hits against zombies
            memory.incrementStat("hits_zombie");
            
            // Track if player is hiding indoors
            if (player.getWorld().getBlockAt(player.getLocation()).getLightFromSky() < 15) {
                memory.incrementStat("indoor_hiding");
            }
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Spider) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track hits against spiders
            memory.incrementStat("hits_spider");
            
            // Track torch usage (simplified detection)
            if (player.getInventory().getItemInMainHand().getType().name().contains("TORCH")) {
                memory.incrementStat("torch_spam");
            }
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Enderman) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track staring at endermen
            memory.incrementStat("enderman_stares");
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Witch) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track combat with witches
            memory.incrementStat("hits_witch");
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Slime) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track combat with slimes
            memory.incrementStat("hits_slime");
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Phantom) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track combat with phantoms
            memory.incrementStat("hits_phantom");
            
            // Track shield usage against phantoms
            if (player.isBlocking()) {
                memory.incrementStat("shield_blocks");
            }
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Piglin) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track interactions with piglins
            memory.incrementStat("piglin_interactions");
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Hoglin) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track combat with hoglins
            memory.incrementStat("hits_hoglin");
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Ghast) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track combat with ghasts
            memory.incrementStat("hits_ghast");
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof Blaze) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track combat with blazes
            memory.incrementStat("hits_blaze");
        } else if (event.getDamager() instanceof Player && event.getEntity() instanceof WitherSkeleton) {
            Player player = (Player) event.getDamager();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track combat with wither skeletons
            memory.incrementStat("hits_wither_skeleton");
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track kills by mob type
            EntityType entityType = event.getEntityType();
            String killStat = "kills_" + entityType.name().toLowerCase();
            memory.incrementStat(killStat);
            
            // Track specific boss kills for global AI memory
            if (entityType == EntityType.WITHER) {
                plugin.getGlobalAIManager().recordBossInteraction("wither_killed", 10);
            } else if (entityType == EntityType.ENDER_DRAGON) {
                plugin.getGlobalAIManager().recordBossInteraction("ender_dragon_killed", 10);
            } else if (entityType == EntityType.ELDER_GUARDIAN) {
                plugin.getGlobalAIManager().recordBossInteraction("elder_guardian_killed", 8);
            }
        }
        
        // Remove mob knowledge when mob dies
        plugin.getMemoryManager().removeMobKnowledge(event.getEntity().getUniqueId());
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        // Apply adaptive behaviors to newly spawned mobs
        plugin.getAIManager().applyAdaptiveBehaviors(event.getEntity());
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);
        ItemStack item = event.getItem();

        // Track potion usage
        if (item.hasItemMeta() && item.getItemMeta() instanceof PotionMeta) {
            memory.incrementStat("potion_usage");

            // Track specific potion effects
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
            for (PotionEffect effect : potionMeta.getCustomEffects()) {
                String effectStat = "potion_" + effect.getType().getName().toLowerCase();
                memory.incrementStat(effectStat);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Load player memory when they join
        Player player = event.getPlayer();
        plugin.getMemoryManager().getPlayerMemory(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Save player memory when they quit
        Player player = event.getPlayer();
        // In a full implementation, we would save the player's data here
    }
}