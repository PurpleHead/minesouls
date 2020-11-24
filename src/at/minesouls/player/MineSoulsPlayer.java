package at.minesouls.player;

import at.jojokobi.mcutil.TypedMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

public class MineSoulsPlayer implements ConfigurationSerializable {

    private static final String UUID_KEY = "uuid";
    private static final String LAST_INTERACT_KEY = "lastInteract";
    private static final String BONFIRES_KEY = "bonfires";
    private static final String CURRENT_AREA_KEY = "currentArea";
    private static final String LAST_BONFIRE_USE_KEY = "lastBonfireUse";

    private static HashMap<UUID, MineSoulsPlayer> loadedPlayers = new HashMap<>();

    private List<Location> bonfires = new LinkedList<>();
    private long lastInteract = 0;
    private UUID uuid;
    private String currentArea = "";

    private MineSoulsPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public static MineSoulsPlayer getPlayer(UUID uuid) {
        MineSoulsPlayer mineSoulsPlayer = loadedPlayers.get(uuid);

        if(mineSoulsPlayer == null) {
            mineSoulsPlayer = new MineSoulsPlayer(uuid);
            loadedPlayers.put(uuid, mineSoulsPlayer);
        }
        return mineSoulsPlayer;
    }

    public static MineSoulsPlayer getPlayer (org.bukkit.entity.Player player) {
        return getPlayer(player.getUniqueId());
    }

    public static void removeBonfireFromAll (Location bonfire) {
        loadedPlayers.forEach((u, p) -> {
            p.getBonfires().remove(bonfire);
        });
    }

    public static void removeAllBonfires() {
        Bukkit.broadcastMessage(ChatColor.RED + "Bonfires cleared!");
        loadedPlayers.forEach((k, v) -> {
            v.getBonfires().clear();
        });
    }

    public static HashMap<UUID, MineSoulsPlayer> getLoadedPlayers() {
        return loadedPlayers;
    }

    public static void setLoadedPlayers(HashMap<UUID, MineSoulsPlayer> loadedPlayers) {
        MineSoulsPlayer.loadedPlayers = loadedPlayers;
    }

    public List<Location> getBonfires() {
        return bonfires;
    }

    public void setBonfires(List<Location> bonfires) {
        this.bonfires = bonfires;
    }

    public long getLastInteract() {
        return lastInteract;
    }

    public void setLastInteract(long lastInteract) {
        this.lastInteract = lastInteract;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(String currentArea) {
        this.currentArea = currentArea;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put(LAST_INTERACT_KEY, getLastInteract());
        map.put(UUID_KEY, getUuid().toString());
        map.put(BONFIRES_KEY, List.copyOf(getBonfires()));
        map.put(CURRENT_AREA_KEY, getCurrentArea());

        return map;
    }

    public static MineSoulsPlayer valueOf (Map<String, Object> map) {
        UUID uuid = UUID.fromString((String) map.get(UUID_KEY));
        MineSoulsPlayer player = new MineSoulsPlayer(uuid);
        TypedMap t = new TypedMap(map);

        player.setLastInteract(t.getLong(LAST_INTERACT_KEY));
        player.setBonfires(t.getList(BONFIRES_KEY, Location.class));
        player.setCurrentArea(t.getString(CURRENT_AREA_KEY));

        return player;
    }
}
