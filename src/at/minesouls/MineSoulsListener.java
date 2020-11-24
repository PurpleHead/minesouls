package at.minesouls;
import at.jojokobi.mcutil.JojokobiUtilPlugin;
import at.minesouls.blocks.Bonfire;
import at.minesouls.gui.BonfireGUI;
import at.minesouls.player.MineSoulsPlayer;
import at.minesouls.spawngroups.SpawnGroupHandler;
import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class MineSoulsListener implements Listener {

    private static final String PLAYER_KEY = "player";
    private static final String PLAYER_SUBFOLDER = "players";

    private SpawnGroupHandler handler = SpawnGroupHandler.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if((System.currentTimeMillis() - MineSoulsPlayer.getPlayer(event.getPlayer()).getLastInteract()) > 1000) {
            Player player = event.getPlayer();
            MineSoulsPlayer mineSoulsPlayer = MineSoulsPlayer.getPlayer(player);
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(event.getClickedBlock().getType() == Material.SOUL_CAMPFIRE) {
                    Bonfire bonfire = Bonfire.getBonfire(event.getClickedBlock().getLocation());

                    mineSoulsPlayer.setLastInteract(System.currentTimeMillis());
                    if(!mineSoulsPlayer.getBonfires().contains(event.getClickedBlock().getLocation())) {
                        mineSoulsPlayer.getBonfires().add(event.getClickedBlock().getLocation());
                    }

                    player.setHealth(20);
                    removePotionEffects(player);
                    player.playSound(player.getLocation(), "minesouls.rest", 100.0f, 1.0f);

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + player.getName()+ " " + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY()  + " " + player.getLocation().getBlockZ());

                    BonfireGUI bonfireGUI = new BonfireGUI(player, bonfire.getName());
                    JavaPlugin.getPlugin(JojokobiUtilPlugin.class).getGuiHandler().addGUI(bonfireGUI);
                    bonfireGUI.setOnClose(() -> handler.spawnGroup(mineSoulsPlayer.getCurrentArea()));
                    handler.despawnAll();
                    bonfireGUI.show();
                    handler.setAllRested();
                }
                if(event.getPlayer().getInventory().getItemInMainHand().getType() == (Material.SHEARS)) {
                    ArmorStand armorStand;
                    ItemStack shears = event.getPlayer().getInventory().getItemInMainHand();
                    String displayName = shears.getItemMeta().getDisplayName();
                    String[] split = displayName.split("::");

                    if(displayName.startsWith("Area::") && split.length > 1) {
                        Location loc = event.getClickedBlock().getLocation();
                        loc.add(0.5, 0, 0.5);

                        event.getClickedBlock().setType(Material.AIR);

                        armorStand = (ArmorStand) event.getClickedBlock().getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
                        armorStand.setVisible(false);
                        armorStand.setGravity(false);
                        armorStand.setInvulnerable(true);
                        armorStand.setCustomName(displayName);
                        armorStand.setCustomNameVisible(false);
                    } else if(displayName.startsWith("Spawn::") && split.length > 1) {
                        String[] spaceSplit = split[1].split(" ");
                        Location loc = event.getClickedBlock().getLocation().clone();
                        loc.add(0, 1, 0);
                        handler.addSpawn(MineSouls.stringRange(spaceSplit, 1, spaceSplit.length), spaceSplit[0], loc);
                        event.getClickedBlock().getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 100);
                        event.getClickedBlock().getWorld().playSound(loc, Sound.BLOCK_BARREL_OPEN, 10, 1);
                        mineSoulsPlayer.setLastInteract(System.currentTimeMillis());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath (PlayerDeathEvent event) {
        handler.despawnAll();
        handler.setAllRested();
        handler.spawnGroup(MineSoulsPlayer.getPlayer(event.getEntity()).getCurrentArea());
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

            MineSoulsPlayer.getLoadedPlayers().remove(event.getPlayer().getUniqueId());
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

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Collection<Entity> entities = event.getPlayer().getWorld().getNearbyEntities(event.getPlayer().getLocation(), 0.5, 0.5, 0.5);
        MineSoulsPlayer player = MineSoulsPlayer.getPlayer(event.getPlayer());
        entities.forEach(entity -> {
            if(entity.getType() == EntityType.ARMOR_STAND && entity.getCustomName() != null && entity.getCustomName().startsWith("Area::")) {
                String split = entity.getCustomName().split("Area::")[1];
                if(!player.getCurrentArea().equalsIgnoreCase(split)) {
                    player.setCurrentArea(split);
                    event.getPlayer().sendTitle(split, null, 10, 20, 10);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), "minesouls.area", 100.0f, 1.0f);
                    if (handler.hasRested(split)) {
                        handler.spawnGroup(split);
                    }
                }
            }
        });
        //TODO Activate
        /*if(player.getCurrentArea().equals("Darkwood") && event.getPlayer().getLocation().getBlock().getLightLevel() < 9) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
        }*/
    }

    private void removePotionEffects (Player player) {
        for (PotionEffect e : player.getActivePotionEffects()) {
            player.removePotionEffect(e.getType());
        }
    }

}
