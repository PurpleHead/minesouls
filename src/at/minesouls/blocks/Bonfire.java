package at.minesouls.blocks;

import at.minesouls.player.MineSoulsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.util.HashMap;

public class Bonfire {
    private String name;
    private Block bonfire;

    private static HashMap<Block, Bonfire> bonfires = new HashMap<>();

    private Bonfire(String name, Block bonfire) {
        this.name = name;
        this.bonfire = bonfire;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Block get() {
        return bonfire;
    }

    public void set(Block bonfire) {
        this.bonfire = bonfire;
    }

    public static Bonfire getBonfire(Block campfire, String name) {
        Bonfire bonfire = bonfires.get(campfire);

        if(bonfire == null) {
            bonfire = new Bonfire(name, campfire);
            bonfires.put(campfire, bonfire);
        }
        Bukkit.broadcastMessage(bonfires.size() + "");
        return bonfire;
    }

    public static Bonfire getBonfire(Block campfire) {
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
}
