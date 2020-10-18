package at.minesouls.player;

import at.minesouls.blocks.Bonfire;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MineSoulsPlayer implements ConfigurationSerializable {

    private static final String LOADED_PLAYERS_KEY = "loadedPlayers";
    private static final String UUID_KEY = "uuid";
    private static final String LAST_BONFIRE_USE_KEY = "lastBonfireUse";
    private static final String BONFIRES_KEY = "bonfires";

    private static HashMap<UUID, MineSoulsPlayer> loadedPlayers = new HashMap<>();

    private HashMap<Location, Bonfire> bonfires = new HashMap<>();
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

    public static void removeBonfireFromAll (Block bonfire) {
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

    public HashMap<Location, Bonfire> getBonfires() {
        return bonfires;
    }

    public void setBonfires(HashMap<Location, Bonfire> bonfires) {
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
        map.put(UUID_KEY, getUuid());
        map.put(BONFIRES_KEY, getBonfires());
        map.put(LOADED_PLAYERS_KEY, getLoadedPlayers());

        return map;
    }

    public static MineSoulsPlayer valueOf (Map<String, Object> map) {
        UUID uuid = (UUID) map.get(UUID_KEY);
        MineSoulsPlayer player = new MineSoulsPlayer(uuid);

        player.setLastBonfireUse((int) map.get(LAST_BONFIRE_USE_KEY));
        player.setBonfires((HashMap<Location, Bonfire>) map.get(BONFIRES_KEY));
        MineSoulsPlayer.setLoadedPlayers((HashMap<UUID, MineSoulsPlayer>) map.get(LOADED_PLAYERS_KEY));

        return player;
    }
}
