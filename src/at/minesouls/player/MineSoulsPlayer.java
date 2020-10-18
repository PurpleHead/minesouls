package at.minesouls.player;

import at.minesouls.blocks.Bonfire;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.UUID;

public class MineSoulsPlayer {

    private static HashMap<UUID, MineSoulsPlayer> loadedPlayers = new HashMap<>();

    private HashMap<Block, Bonfire> bonfires = new HashMap<>();
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

    public HashMap<Block, Bonfire> getBonfires() {
        return bonfires;
    }

    public void setBonfires(HashMap<Block, Bonfire> bonfires) {
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
}
