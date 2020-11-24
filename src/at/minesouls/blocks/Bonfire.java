package at.minesouls.blocks;

import at.jojokobi.mcutil.TypedMap;
import at.minesouls.MineSouls;
import at.minesouls.player.MineSoulsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Bonfire implements ConfigurationSerializable {
    private static final String NAME_KEY = "name";
    private static final String LOCATIONS_KEY = "location";

    private static final String BONFIRE_KEY = "bonfire";
    private static final String BONFIRE_FILENAME = "bonfires.yml";
    private static final String BONFIRE_SUBFOLDER = "bonfires";

    private String name;
    private Location bonfire;

    private static HashMap<Location, Bonfire> bonfires = new HashMap<>();

    private Bonfire(String name, Location bonfire) {
        this.name = name;
        this.bonfire = bonfire;
    }

    public static Bonfire getBonfire(Location campfire, String name) {
        Bonfire bonfire = bonfires.get(campfire);

        if(bonfire == null) {
            bonfire = new Bonfire(name, campfire);
            bonfires.put(campfire, bonfire);
        }
        return bonfire;
    }

    public static Bonfire getBonfire(Location campfire) {
        Bonfire b = getBonfires().get(campfire);
        return (b == null ? getBonfire(campfire, "Bonfire" + Math.random() * 100) : b);
    }

    public static void clearAll () {
        bonfires.clear();
        MineSoulsPlayer.removeAllBonfires();
    }

    public static void remove (Location campfire) {
        bonfires.remove(campfire);
        MineSoulsPlayer.removeBonfireFromAll(campfire);
    }

    public static void disable () {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), BONFIRE_SUBFOLDER);
        dataFolder.mkdirs();

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
    }

    public static void enable () {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), BONFIRE_SUBFOLDER);
        dataFolder.mkdirs();

        FileConfiguration config = new YamlConfiguration();
        File bonfireFile = new File(dataFolder, BONFIRE_FILENAME);
        if(bonfireFile.exists()) {
            try {
                config.load(bonfireFile);

                List<?> list = config.getList(BONFIRE_KEY);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return bonfire;
    }

    public void setLocation(Location bonfire) {
        this.bonfire = bonfire;
    }

    public static HashMap<Location, Bonfire> getBonfires() {
        return bonfires;
    }

    public static void setBonfires(HashMap<Location, Bonfire> bonfires) {
        Bonfire.bonfires = bonfires;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put(NAME_KEY, getName());
        map.put(LOCATIONS_KEY, getLocation());

        return map;
    }

    public static Bonfire valueOf (Map<String, Object> map) {
        TypedMap t = new TypedMap(map);
        String name = t.getString(NAME_KEY);
        Location location = (Location) map.get(LOCATIONS_KEY);

        return new Bonfire(name, location);
    }
}
