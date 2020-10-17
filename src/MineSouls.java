import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MineSouls extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new MineSoulsListener(), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " [MINESOULS LOADED]");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
