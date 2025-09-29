package com.example.aidrivenmobs;

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

import com.example.aidrivenmobs.data.PlayerMemory;

/**
 * Listener class for tracking player and mob interactions
 */
public class MobBehaviorListener implements Listener {
    private AIDrivenMobsPlugin plugin;

    public MobBehaviorListener(AIDrivenMobsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Initialize player memory when they join
        plugin.getMemoryManager().getPlayerMemory(event.getPlayer());
        plugin.getLogger().info("Tracking player: " + event.getPlayer().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Save player memory when they leave
        plugin.getLogger().info("Stopped tracking player: " + event.getPlayer().getName());
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        // Apply adaptive behaviors to newly spawned mobs
        Entity entity = event.getEntity();
        if (entity instanceof Mob) {
            // In a full implementation, we would apply custom AI behaviors here
            plugin.getLogger().info("Applying adaptive behaviors to: " + entity.getType());
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // Track player interactions with mobs
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Mob) {
            Player player = (Player) event.getDamager();
            Mob mob = (Mob) event.getEntity();

            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(player);

            // Track different types of attacks
            if (player.isBlocking()) {
                memory.incrementStat("shield_blocks");
            } else {
                memory.incrementStat("melee_hits");
            }

            plugin.getLogger().info("Player " + player.getName() + " attacked " + mob.getType() +
                    " (Shield blocks: " + memory.getStat("shield_blocks") +
                    ", Melee hits: " + memory.getStat("melee_hits") + ")");
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // Track mob deaths and player kills
        LivingEntity entity = event.getEntity(); // Use LivingEntity instead of Entity
        Player killer = entity.getKiller();

        if (killer != null && entity instanceof Mob) {
            Mob mob = (Mob) entity;
            PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(killer);
            memory.incrementStat("kills");

            plugin.getLogger().info("Player " + killer.getName() + " killed " + mob.getType() +
                    " (Total kills: " + memory.getStat("kills") + ")");
        }
    }
}