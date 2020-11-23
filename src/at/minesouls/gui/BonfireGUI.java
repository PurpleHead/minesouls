package at.minesouls.gui;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.minesouls.blocks.Bonfire;
import at.minesouls.player.MineSoulsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BonfireGUI extends InventoryGUI {

    private HashMap<ItemStack, Bonfire> bonfires = new HashMap<>();
    private Runnable onClose;

    public BonfireGUI(Player owner, String name) {
        super(owner, Bukkit.createInventory(owner, InventoryType.PLAYER, name));
        initGUI();
    }

    @Override
    protected void initGUI() {
        MineSoulsPlayer mineSoulsPlayer = MineSoulsPlayer.getPlayer(getOwner());
        AtomicInteger index = new AtomicInteger(0);

        mineSoulsPlayer.getBonfires().forEach((b) -> {
            ItemStack item = new ItemStack(Material.SOUL_CAMPFIRE);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(Bonfire.getBonfire(b).getName());
            meta.setLocalizedName(Math.random()*100 + "");
            item.setItemMeta(meta);

            bonfires.put(item, Bonfire.getBonfire(b));
            addButton(item, index.get());
            index.incrementAndGet();
        });
    }

    @Override
    protected void onButtonPress(ItemStack itemStack, ClickType clickType) {
        if (clickType == ClickType.LEFT) {
            Location location = bonfires.get(itemStack).getLocation().clone();
            location.setZ(location.getZ() - 1);
            getOwner().teleport(location);
            close();
        }
    }

    public void setOnClose (Runnable r) {
        this.onClose = r;
    }

    @Override
    protected InventoryGUI onInventoryClose(InventoryCloseEvent event) {
        if(this.onClose != null)
            onClose.run();
        return super.onInventoryClose(event);
    }
}
