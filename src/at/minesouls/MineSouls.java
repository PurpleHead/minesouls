package at.minesouls;
import at.minesouls.blocks.Bonfire;
import at.minesouls.commands.BonfireCommand;
import at.minesouls.player.MineSoulsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MineSouls extends JavaPlugin {

    public static final String PLUGIN_NAME = "MineSouls";

    private static final String BONFIRE_KEY = "bonfire";

    @Override
    public void onEnable() {
        super.onEnable();
        ConfigurationSerialization.registerClass(Bonfire.class, BONFIRE_KEY);
        ConfigurationSerialization.registerClass(MineSoulsPlayer.class);

        loadConfiguration();

        getServer().getPluginManager().registerEvents(new MineSoulsListener(), this);
        getCommand("bonfire").setExecutor(new BonfireCommand());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " [MINESOULS LOADED]");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        saveConfiguration();
    }

    private void saveConfiguration () {
        // Bonfires
        {
            File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), "bonfires");
            dataFolder.mkdirs();

            //Save
            AtomicInteger index = new AtomicInteger(0);

            Bonfire.getBonfires().forEach((k, v) -> {
                FileConfiguration config = new YamlConfiguration();
                config.set(BONFIRE_KEY, v);
                try {
                    config.save(new File(dataFolder, "bonfire_" + v.getName() + "_" + index.get() + ".yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                index.incrementAndGet();
            });
        }
    }

    private void loadConfiguration () {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), "bonfires");
        dataFolder.mkdirs();

        for (File f : dataFolder.listFiles()) {
            FileConfiguration config = new YamlConfiguration();
            try {
                config.load(new File(dataFolder, f.getName()));

                Bonfire b = config.getSerializable(BONFIRE_KEY, Bonfire.class);
                Bonfire.getBonfires().put(b.get(), b);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

    }

}
