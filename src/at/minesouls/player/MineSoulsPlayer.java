package at.minesouls.player;

import at.jojokobi.mcutil.TypedMap;
import at.minesouls.areas.Area;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ZombieVillager;

import java.util.*;

public class MineSoulsPlayer implements ConfigurationSerializable {

    private static final String UUID_KEY = "uuid";
    private static final String LAST_INTERACT_KEY = "lastInteract";
    private static final String BONFIRES_KEY = "bonfires";
    private static final String CURRENT_AREA_KEY = "currentArea";
    private static final String STATS_KEY = "stats";
    private static final String SPAWN_KEY = "spawn";

    private static HashMap<UUID, MineSoulsPlayer> loadedPlayers = new HashMap<>();

    private List<Location> bonfires = new LinkedList<>();
    private long lastInteract = 0;
    private UUID uuid;
    private Area currentArea = null;

    private Location spawn;

    private MineSoulsPlayerStats stats;

    private MineSoulsPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public static MineSoulsPlayer getPlayer(UUID uuid) {
        MineSoulsPlayer mineSoulsPlayer = loadedPlayers.get(uuid);

        if(mineSoulsPlayer == null) {
            mineSoulsPlayer = new MineSoulsPlayer(uuid);
            loadedPlayers.put(uuid, mineSoulsPlayer);
        }
        if(mineSoulsPlayer.getStats() == null)
            mineSoulsPlayer.setStats(new MineSoulsPlayerStats(uuid));
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

    public void addKill (LivingEntity e) {
        if(e instanceof ZombieVillager) {
            getStats().setSouls(getStats().getSouls() + 70);
        }
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

    public Area getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(Area currentArea) {
        this.currentArea = currentArea;
    }


    public MineSoulsPlayerStats getStats() {
        return stats;
    }

    public void setStats(MineSoulsPlayerStats stats) {
        this.stats = stats;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put(LAST_INTERACT_KEY, getLastInteract());
        map.put(UUID_KEY, getUuid().toString());
        map.put(BONFIRES_KEY, List.copyOf(getBonfires()));
        map.put(CURRENT_AREA_KEY, getCurrentArea());
        map.put(STATS_KEY, getStats());
        map.put(SPAWN_KEY, getSpawn());

        return map;
    }

    public static MineSoulsPlayer valueOf (Map<String, Object> map) {
        UUID uuid = UUID.fromString((String) map.get(UUID_KEY));
        MineSoulsPlayer player = new MineSoulsPlayer(uuid);
        TypedMap t = new TypedMap(map);

        player.setLastInteract(t.getLong(LAST_INTERACT_KEY));
        player.setBonfires(t.getList(BONFIRES_KEY, Location.class));
        player.setCurrentArea(t.get(CURRENT_AREA_KEY, Area.class, null));
        player.setStats(t.get(STATS_KEY, MineSoulsPlayerStats.class, null));
        player.setSpawn(t.get(SPAWN_KEY, Location.class, null));

        return player;
    }
}
