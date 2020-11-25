package at.minesouls.areas;

import at.minesouls.MineSouls;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AreaMarkerHandler {

    private static final String SUBFOLDER = "areas";
    private static final String FILENAME = "areas.yml";
    private static final String AREA_KEY = "areas";

    private static AreaMarkerHandler handler;
    private HashMap<RoundedLocation, AreaMarker> map = new HashMap<>();

    private AreaMarkerHandler() {

    }

    public static AreaMarkerHandler getInstance() {
        if(handler == null) {
            handler = new AreaMarkerHandler();
        }
        return handler;
    }

    public void addMarker (Location loc, AreaMarker area) {
        map.put(new RoundedLocation(loc), area);
    }

    public Area isInArea (Location loc) {
        Area area = null;
        if(map.containsKey(new RoundedLocation(loc)))
            area = map.get(new RoundedLocation(loc)).getArea();
        return area;
    }

    public HashMap<RoundedLocation, AreaMarker> getMap() {
        return map;
    }

    public void setMap(HashMap<RoundedLocation, AreaMarker> map) {
        this.map = map;
    }

    public static void disable () {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), SUBFOLDER);
        dataFolder.mkdirs();

        FileConfiguration config = new YamlConfiguration();
        List<AreaMarker> areas = new LinkedList<>();

        AreaMarkerHandler.getInstance().map.forEach((k, v) -> areas.add(v));

        config.set(AREA_KEY, areas);
        try {
            config.save(new File(dataFolder, FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void enable () {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), SUBFOLDER);
        dataFolder.mkdirs();

        FileConfiguration config = new YamlConfiguration();
        File areaFile = new File(dataFolder, FILENAME);
        if(areaFile.exists()) {
            try {
                config.load(areaFile);

                List<Object> list = (List<Object>) config.getList(AREA_KEY);
                HashMap<RoundedLocation, AreaMarker> areas = new HashMap<>();
                for(Object o : list) {
                    AreaMarker a = (AreaMarker) o;
                    areas.put(a.getLocation(), a);
                    Area.getAreas().put(a.getArea().getUuid(), a.getArea());
                }

                AreaMarkerHandler.getInstance().setMap(areas);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

}
