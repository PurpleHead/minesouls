package at.minesouls;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import at.jojokobi.mcutil.entity.spawns.CustomSpawnsHandler;
import at.jojokobi.mcutil.entity.spawns.FunctionSpawn;
import at.minesouls.entity.WalkingColossusBoss;

public class MineSouls extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new MineSoulsListener(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " [MINESOULS LOADED]");
        
        CustomSpawnsHandler.getInstance().addItem(new FunctionSpawn(getName().toLowerCase(), "walking_colossus", l -> new WalkingColossusBoss(l, null)));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
