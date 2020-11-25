package at.minesouls.spawngroups;

import at.minesouls.MineSouls;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpawnGroupHandler {

    private static SpawnGroupHandler instance = null;

    private static final String SUBFOLDER = "spawngroups";
    private static final String FILENAME = "spawn-groups.yml";
    private static final String SPAWNGROUP_KEY = "spawngroup";

    private Map<UUID, SpawnGroup> spawnGroupMap = new HashMap<>();

    private SpawnGroupHandler () {}

    public static SpawnGroupHandler getInstance() {
        if(instance == null) {
            instance = new SpawnGroupHandler();
        }
        return instance;
    }

    public void spawnGroup(UUID uuid) {
        exists(uuid);
        SpawnGroup group = spawnGroupMap.get(uuid);
        group.setRested(false);
        group.spawnAll();
    }

    public void despawnAll() {
        spawnGroupMap.forEach((k, v) -> v.removeAll());
    }

    public void setAllRested () {
        spawnGroupMap.forEach((k, v) -> v.setRested(true));
    }

    public boolean hasRested (UUID uuid) {
        exists(uuid);
        return spawnGroupMap.get(uuid).hasRested();
    }

    public void addSpawn (UUID uuid, String key, Location loc) {
        exists(uuid);
        spawnGroupMap.get(uuid).addSpawn(key, loc);
    }

    private void exists(UUID uuid) {
        if(!spawnGroupMap.containsKey(uuid))
            spawnGroupMap.put(uuid, new SpawnGroup(uuid));
    }

    public void enable () {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), SUBFOLDER);
        dataFolder.mkdirs();

        FileConfiguration config = new YamlConfiguration();

        File spawngroupFile = new File(dataFolder, FILENAME);

        if(spawngroupFile.exists()) {
            try {
                config.load(spawngroupFile);

                List<Object> list = (List<Object>) config.getList(SPAWNGROUP_KEY);
                Map<UUID, SpawnGroup> spawnGroupMap = new HashMap<>();
                for(Object o : list) {
                    SpawnGroup s = (SpawnGroup) o;
                    spawnGroupMap.put(s.getUuid(), s);
                }

                setSpawnGroupMap(spawnGroupMap);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public void disable () {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), SUBFOLDER);
        dataFolder.mkdirs();

        FileConfiguration config = new YamlConfiguration();
        List<SpawnGroup> spawnGroups = new LinkedList<>();

        getSpawnGroupMap().forEach((k, v) -> spawnGroups.add(v));

        config.set(SPAWNGROUP_KEY, spawnGroups);
        try {
            config.save(new File(dataFolder, FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<UUID, SpawnGroup> getSpawnGroupMap() {
        return spawnGroupMap;
    }

    public void setSpawnGroupMap(Map<UUID, SpawnGroup> spawnGroupMap) {
        this.spawnGroupMap = spawnGroupMap;
    }
}
