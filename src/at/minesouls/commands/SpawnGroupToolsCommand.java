package at.minesouls.commands;

import at.minesouls.MineSouls;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnGroupToolsCommand implements CommandExecutor {

    public static final String COMMAND = "sgtool";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length >= 2) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                ItemStack shear = new ItemStack(Material.SHEARS);
                ItemMeta meta = shear.getItemMeta();

                meta.setDisplayName("Spawn::" + strings[0] + " " + MineSouls.stringRange(strings, 1, strings.length));
                shear.setItemMeta(meta);

                player.getInventory().addItem(shear);

                return true;
            }
        }

        return false;
    }
}
