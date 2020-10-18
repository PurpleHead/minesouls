package at.minesouls.player;

import at.minesouls.blocks.Bonfire;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Player {

    private static HashMap<UUID, Player> loadedPlayers = new HashMap<>();

    private List<Bonfire> bonfires = new LinkedList<>();
    private UUID uuid;

    private Player (UUID uuid) {
        this.uuid = uuid;
    }

    public static Player getPlayer(UUID uuid) {
        Player player = loadedPlayers.get(uuid);

        if(player == null) {
            player = new Player(uuid);
            loadedPlayers.put(uuid, player);
        }

        return player;
    }

    public static Player getPlayer (org.bukkit.entity.Player player) {
        return getPlayer(player.getUniqueId());
    }

}
