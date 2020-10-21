package at.minesouls.player;

import at.jojokobi.mcutil.TypedMap;
import at.minesouls.blocks.Bonfire;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.*;

public class MineSoulsPlayer implements ConfigurationSerializable {

    private static final String UUID_KEY = "uuid";
    private static final String LAST_BONFIRE_USE_KEY = "lastBonfireUse";
    private static final String BONFIRES_KEY = "bonfires";

    private static HashMap<UUID, MineSoulsPlayer> loadedPlayers = new HashMap<>();

    private List<Location> bonfires = new LinkedList<>();
    private long lastBonfireUse = 0;
    private UUID uuid;

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

    public long getLastBonfireUse() {
        return lastBonfireUse;
    }

    public void setLastBonfireUse(long lastBonfireUse) {
        this.lastBonfireUse = lastBonfireUse;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "MineSoulsPlayer{" +
                "bonfires=" + bonfires.size() +
                ", lastBonfireUse=" + lastBonfireUse +
                ", uuid=" + uuid +
                '}';
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put(LAST_BONFIRE_USE_KEY, getLastBonfireUse());
        map.put(UUID_KEY, getUuid().toString());
        map.put(BONFIRES_KEY, getBonfires());

        return map;
    }

    public static MineSoulsPlayer valueOf (Map<String, Object> map) {
        UUID uuid = UUID.fromString((String) map.get(UUID_KEY));
        MineSoulsPlayer player = new MineSoulsPlayer(uuid);
        TypedMap t = new TypedMap(map);

        player.setLastBonfireUse(t.getLong(LAST_BONFIRE_USE_KEY));
        player.setBonfires(t.getList(BONFIRES_KEY, Location.class));

        return player;
    }
}
