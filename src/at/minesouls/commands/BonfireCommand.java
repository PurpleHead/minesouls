package at.minesouls.commands;

import at.minesouls.blocks.Bonfire;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BonfireCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            if(strings.length > 0 && strings[0].equals("clear")) {
                Bonfire.clearAll();
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " " + Arrays.toString(strings));
                Player player = (Player) commandSender;

                if (strings.length == 4) {
                    try {
                        int x = (strings[0].equals("~") ? player.getLocation().getBlockX() : Integer.parseInt(strings[0]) - 1);
                        int y = (strings[1].equals("~") ? player.getLocation().getBlockY() : Integer.parseInt(strings[1]));
                        int z = (strings[2].equals("~") ? player.getLocation().getBlockZ() : Integer.parseInt(strings[2]) - 1);
                        Bukkit.broadcastMessage(x + " " + y + " " + z);
                        player.getWorld().getBlockAt(x, y, z).setType(Material.SOUL_CAMPFIRE);
                        Bonfire.getBonfire(player.getWorld().getBlockAt(x, y, z), strings[3]);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                } else
                    return false;
            }
        }
        return false;
    }

}