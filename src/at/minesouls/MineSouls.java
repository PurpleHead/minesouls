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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MineSouls extends JavaPlugin {

    public static final String PLUGIN_NAME = "MineSouls";

    private static final String BONFIRE_KEY = "bonfire";
    private static final String BONFIRE_FILENAME = "bonfires.yml";
    private static final String BONFIRE_SUBFOLDER = "bonfires";

    @Override
    public void onEnable() {
        super.onEnable();
        ConfigurationSerialization.registerClass(Bonfire.class);
        ConfigurationSerialization.registerClass(MineSoulsPlayer.class);

        loadConfiguration();

        getServer().getPluginManager().registerEvents(new MineSoulsListener(), this);
        getCommand(BonfireCommand.COMMAND).setExecutor(new BonfireCommand());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " [MINESOULS LOADED]");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        saveConfiguration();
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " [MINESOULS DISABLED]");
    }

    private void saveConfiguration () {
        // Bonfires
        {
            File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), BONFIRE_SUBFOLDER);
            dataFolder.mkdirs();

            //Save
            AtomicInteger index = new AtomicInteger(0);

            FileConfiguration config = new YamlConfiguration();
            List<Bonfire> bonfires = new LinkedList<>();

            Bonfire.getBonfires().forEach((k, v) -> {
                bonfires.add(v);
            });

            config.set(BONFIRE_KEY, bonfires);
            try {
                config.save(new File(dataFolder, BONFIRE_FILENAME));
            } catch (IOException e) {
                e.printStackTrace();
            }

            index.incrementAndGet();
        }
    }

    private void loadConfiguration () {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), BONFIRE_SUBFOLDER);
        dataFolder.mkdirs();

        FileConfiguration config = new YamlConfiguration();
        File bonfireFile = new File(dataFolder, BONFIRE_FILENAME);
        if(bonfireFile.exists()) {
            try {
                config.load(bonfireFile);

                List<Object> list = (List<Object>) config.getList(BONFIRE_KEY);
                HashMap<Location, Bonfire> bonfires = new HashMap<>();
                for(Object o : list) {
                    Bonfire b = (Bonfire) o;
                    bonfires.put(b.getLocation(), b);
                }

                Bonfire.setBonfires(bonfires);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

    }

}
