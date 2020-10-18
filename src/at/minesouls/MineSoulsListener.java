package at.minesouls;
import at.jojokobi.mcutil.JojokobiUtilPlugin;
import at.minesouls.blocks.Bonfire;
import at.minesouls.gui.BonfireGUI;
import at.minesouls.player.MineSoulsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class MineSoulsListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if((System.currentTimeMillis() - MineSoulsPlayer.getPlayer(event.getPlayer()).getLastBonfireUse()) > 1000) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(event.getClickedBlock().getType() == Material.SOUL_CAMPFIRE) {
                    Player player = event.getPlayer();
                    MineSoulsPlayer mineSoulsPlayer = MineSoulsPlayer.getPlayer(player);
                    Bonfire bonfire = Bonfire.getBonfire(event.getClickedBlock());

                    mineSoulsPlayer.setLastBonfireUse(System.currentTimeMillis());
                    mineSoulsPlayer.getBonfires().put(event.getClickedBlock(), bonfire);

                    player.setHealth(20);
                    removePotionEffects(player);
                    player.sendMessage(ChatColor.GREEN + "Healed!");
                    player.playSound(player.getLocation(), "minesouls.rest", 100.0f, 1.0f);

                    //Maybe own spawn on death sometime?
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
            Bonfire.remove(event.getBlock());
        }
    }

    private void removePotionEffects (Player player) {
        for (PotionEffect e : player.getActivePotionEffects()) {
            player.removePotionEffect(e.getType());
        }
    }

}
