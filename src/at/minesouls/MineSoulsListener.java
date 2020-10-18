package at.minesouls;
import at.minesouls.blocks.Bonfire;
import at.minesouls.gui.BonfireGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;

public class MineSoulsListener implements Listener {

    private long lastBonfireUse = 0;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(System.currentTimeMillis() - lastBonfireUse > 5000) {
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(event.getClickedBlock().getType() == Material.SOUL_CAMPFIRE) {
                    Player player = event.getPlayer();
                    BonfireGUI bonfireGUI = new BonfireGUI(player, Bukkit.createInventory(null, InventoryType.PLAYER, Bonfire.getBonfire(event.getClickedBlock()).getName()));

                    lastBonfireUse = System.currentTimeMillis();
                    player.setHealth(20);
                    removePotionEffects(player);
                    player.sendMessage(ChatColor.GREEN + "Healed!");
                    player.playSound(player.getLocation(), "minesouls.rest", 100.0f, 1.0f);

                    bonfireGUI.show();
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
