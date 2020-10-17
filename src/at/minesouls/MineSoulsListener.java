package at.minesouls;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;

public class MineSoulsListener implements Listener {

    long lastBonfireUse = 0;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(System.currentTimeMillis() - lastBonfireUse > 5000) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(event.getClickedBlock().getType() == Material.SOUL_CAMPFIRE) {
                    Player player = event.getPlayer();
                    lastBonfireUse = System.currentTimeMillis();
                    player.setHealth(20);
                    removePotionEffects(player);
                    player.sendMessage(ChatColor.GREEN + "Healed!");
                    player.playSound(player.getLocation(), "minesouls.rest", 100.0f, 1.0f);
                }
            }
        }
    }

    private void removePotionEffects (Player player) {
        for (PotionEffect e : player.getActivePotionEffects()) {
            player.removePotionEffect(e.getType());
        }
    }

}
