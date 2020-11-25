package at.minesouls.commands;

import at.minesouls.MineSouls;
import at.minesouls.areas.Area;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;
import java.util.stream.Collectors;

public class AreaToolsCommand implements CommandExecutor {

    public static String COMMAND = "area";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length > 0) {
            if(strings[0].equals("list")) {
                commandSender.sendMessage(Area.getAreas().values().stream().map(a -> a.getUuid() + " | " + a.getName()).collect(Collectors.joining("\n")));
                return true;
            } else if (commandSender instanceof Player) {
                String name = MineSouls.stringRange(strings, 0, strings.length);
                UUID uuid = Area.getUUIDbyName(name);

                ItemStack shear = new ItemStack(Material.SHEARS);
                ItemMeta meta = shear.getItemMeta();
                Player player = (Player) commandSender;

                meta.setDisplayName("Area::" + uuid.toString());
                shear.setItemMeta(meta);
                player.getInventory().addItem(shear);
                return true;
            }
        }

        return false;
    }
}
