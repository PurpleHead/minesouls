package at.minesouls.spawngroups;

import at.minesouls.MineSouls;
import at.minesouls.blocks.Bonfire;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.world.SpawnChangeEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SpawnGroupHandler {

    private Map<String, SpawnGroup> spawnGroupMap = new HashMap<>();

    public void enable () {
        File dataFolder = Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder();
        dataFolder.mkdirs();

        FileConfiguration config = new YamlConfiguration();

        try {
            config.load(new File(dataFolder, "spawn_groups.yml"));
            for (Object o : config.getList("spawnGroups")) {
                if (o instanceof SpawnGroup) {
                    SpawnGroup group = (SpawnGroup) o;
                    spawnGroupMap.put(group.getName(), group);
                }
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void disable () {
        File dataFolder = Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder();
        dataFolder.mkdirs();

        FileConfiguration config = new YamlConfiguration();
        List<SpawnGroup> spawns = new LinkedList<>();

        spawnGroupMap.forEach((k, v) -> {
            spawns.add(v);
        });

        config.set("spawnGroups", spawns);
        try {
            config.save(new File(dataFolder, "spawn_groups.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, SpawnGroup> getSpawnGroupMap() {
        return spawnGroupMap;
    }

    public void setSpawnGroupMap(Map<String, SpawnGroup> spawnGroupMap) {
        this.spawnGroupMap = spawnGroupMap;
    }
}
