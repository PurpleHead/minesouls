package at.minesouls.blocks;

import at.minesouls.player.MineSoulsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Bonfire implements ConfigurationSerializable {
    private static final String NAME_KEY = "name";
    private static final String LOCATIONS_KEY = "bonfire";
    private static final String BONFIRES_KEY = "bonfires";

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
        Bukkit.broadcastMessage(bonfires.size() + "");
        return bonfire;
    }

    public static Bonfire getBonfire(Location campfire) {
        return getBonfire(campfire, "Bonfire");
    }

    public static void clearAll () {
        bonfires.clear();
        Bukkit.broadcastMessage(bonfires.size() + "");
    }

    public static void remove (Block campfire) {
        bonfires.remove(campfire);
        MineSoulsPlayer.removeBonfireFromAll(campfire);
        Bukkit.broadcastMessage(bonfires.size() + "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location get() {
        return bonfire;
    }

    public void set(Location bonfire) {
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
        map.put(LOCATIONS_KEY, get());
        map.put(BONFIRES_KEY, getBonfires());

        return map;
    }

    public Bonfire valueOf (HashMap<String, Object> map) {
        String name = (String) map.get(NAME_KEY);
        Location location = (Location) map.get(LOCATIONS_KEY);

        return new Bonfire(name, location);
    }
}
