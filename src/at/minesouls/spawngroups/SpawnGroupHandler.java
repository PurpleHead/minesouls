package at.minesouls.spawngroups;

import at.minesouls.MineSouls;
import at.minesouls.blocks.Bonfire;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.world.SpawnChangeEvent;

import java.io.File;
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
        List<SpawnChangeEvent> bonfires = new LinkedList<>();

        Bonfire.getBonfires().forEach((k, v) -> {
            //bonfires.add(v);
        });

        //config.set(BONFIRE_KEY, bonfires);
        //config.save(new File(dataFolder, BONFIRE_FILENAME));

        // index.incrementAndGet();
    }

    public void disable () {

    }

    public Map<String, SpawnGroup> getSpawnGroupMap() {
        return spawnGroupMap;
    }

    public void setSpawnGroupMap(Map<String, SpawnGroup> spawnGroupMap) {
        this.spawnGroupMap = spawnGroupMap;
    }
}
