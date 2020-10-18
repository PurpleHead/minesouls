package at.minesouls;
import at.minesouls.blocks.Bonfire;
import at.minesouls.commands.BonfireCommand;
import at.minesouls.player.MineSoulsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MineSouls extends JavaPlugin {
    @Override
    public void onEnable() {
        super.onEnable();
        ConfigurationSerialization.registerClass(Bonfire.class);
        ConfigurationSerialization.registerClass(MineSoulsPlayer.class);

        getServer().getPluginManager().registerEvents(new MineSoulsListener(), this);
        getCommand("bonfire").setExecutor(new BonfireCommand());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " [MINESOULS LOADED]");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void save () {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin("MineSouls").getDataFolder(), "players");

    }

}
