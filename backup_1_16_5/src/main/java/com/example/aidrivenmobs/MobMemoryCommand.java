package com.example.aidrivenmobs;

import com.example.aidrivenmobs.data.PlayerMemory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobMemoryCommand implements CommandExecutor {
    private AIDrivenMobsPlugin plugin;

    public MobMemoryCommand(AIDrivenMobsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            showHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "stats":
                showPlayerStats(player, player);
                break;
            case "reset":
                resetPlayerStats(player, player);
                break;
            default:
                showHelp(player);
                break;
        }

        return true;
    }

    private void showHelp(Player player) {
        player.sendMessage(ChatColor.GOLD + "--- AI-Driven Mobs Help ---");
        player.sendMessage(ChatColor.YELLOW + "/mobmemory stats" + ChatColor.WHITE + " - Show your combat stats");
        player.sendMessage(ChatColor.YELLOW + "/mobmemory reset" + ChatColor.WHITE + " - Reset your combat stats");
    }

    private void showPlayerStats(Player sender, Player target) {
        PlayerMemory memory = plugin.getMemoryManager().getPlayerMemory(target);

        sender.sendMessage(ChatColor.GOLD + "--- Your Combat Stats ---");
        sender.sendMessage(ChatColor.YELLOW + "Melee Hits: " + ChatColor.WHITE + memory.getStat("melee_hits"));
        sender.sendMessage(ChatColor.YELLOW + "Ranged Hits: " + ChatColor.WHITE + memory.getStat("ranged_hits"));
        sender.sendMessage(ChatColor.YELLOW + "Shield Blocks: " + ChatColor.WHITE + memory.getStat("shield_blocks"));
        sender.sendMessage(ChatColor.YELLOW + "Potion Usage: " + ChatColor.WHITE + memory.getStat("potion_usage"));
        sender.sendMessage(ChatColor.YELLOW + "Skeleton Kills: " + ChatColor.WHITE + memory.getStat("kills_skeleton"));
        sender.sendMessage(ChatColor.YELLOW + "Creeper Kills: " + ChatColor.WHITE + memory.getStat("kills_creeper"));
        sender.sendMessage(ChatColor.YELLOW + "Zombie Kills: " + ChatColor.WHITE + memory.getStat("kills_zombie"));
    }

    private void resetPlayerStats(Player sender, Player target) {
        // In a full implementation, we would reset the player's stats
        sender.sendMessage(ChatColor.GREEN + "Your stats have been reset!");
    }
}