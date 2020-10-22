package at.minesouls;
import at.jojokobi.mcutil.JojokobiUtilPlugin;
import at.minesouls.blocks.Bonfire;
import at.minesouls.gui.BonfireGUI;
import at.minesouls.player.MineSoulsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MineSoulsListener implements Listener {

    private static final String PLAYER_KEY = "player";
    private static final String PLAYER_SUBFOLDER = "players";

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if((System.currentTimeMillis() - MineSoulsPlayer.getPlayer(event.getPlayer()).getLastBonfireUse()) > 1000) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(event.getClickedBlock().getType() == Material.SOUL_CAMPFIRE) {
                    Player player = event.getPlayer();
                    MineSoulsPlayer mineSoulsPlayer = MineSoulsPlayer.getPlayer(player);
                    Bonfire bonfire = Bonfire.getBonfire(event.getClickedBlock().getLocation());

                    mineSoulsPlayer.setLastBonfireUse(System.currentTimeMillis());
                    if(!mineSoulsPlayer.getBonfires().contains(event.getClickedBlock().getLocation())) {
                        mineSoulsPlayer.getBonfires().add(event.getClickedBlock().getLocation());
                    }

                    player.setHealth(20);
                    removePotionEffects(player);
                    player.sendMessage(ChatColor.GREEN + "Healed!");
                    player.playSound(player.getLocation(), "minesouls.rest", 100.0f, 1.0f);

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + player.getName()+ " " + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY()  + " " + player.getLocation().getBlockZ());

                    BonfireGUI bonfireGUI = new BonfireGUI(player, bonfire.getName());
                    JavaPlugin.getPlugin(JojokobiUtilPlugin.class).getGuiHandler().addGUI(bonfireGUI);
                    bonfireGUI.show();
                }
            }
        }
    }

    @EventHandler
    public void onDestroy(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.SOUL_CAMPFIRE) {
            Bonfire.remove(event.getBlock().getLocation());
        }
    }

    @EventHandler
    public void onDisconnect (PlayerQuitEvent event) {
        {
            File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), PLAYER_SUBFOLDER);
            dataFolder.mkdirs();
            //Save
            FileConfiguration config = new YamlConfiguration();
            config.set(PLAYER_KEY, MineSoulsPlayer.getPlayer(event.getPlayer()));

            MineSoulsPlayer.getLoadedPlayers().remove(event.getPlayer());
            try {
                config.save(new File(dataFolder, event.getPlayer().getUniqueId() + ".yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        File dataFolder = new File(Bukkit.getPluginManager().getPlugin(MineSouls.PLUGIN_NAME).getDataFolder(), PLAYER_SUBFOLDER);
        dataFolder.mkdirs();

        FileConfiguration config = new YamlConfiguration();
        File playerFile = new File(dataFolder, event.getPlayer().getUniqueId() + ".yml");
        try {
            if(playerFile.exists()) {
                config.load(playerFile);

                MineSoulsPlayer.getLoadedPlayers().put(event.getPlayer().getUniqueId(), config.getSerializable(PLAYER_KEY, MineSoulsPlayer.class));
                MineSoulsPlayer player = MineSoulsPlayer.getPlayer(event.getPlayer());

                List<Location> bonfiresCopy = List.copyOf(player.getBonfires());
                bonfiresCopy.forEach(b -> {
                    if (!Bonfire.getBonfires().containsKey(b)) {
                        player.getBonfires().remove(b);
                    }
                });
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void removePotionEffects (Player player) {
        for (PotionEffect e : player.getActivePotionEffects()) {
            player.removePotionEffect(e.getType());
        }
    }

}
