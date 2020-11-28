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

    private static final String NEXT_PAGE = "Next Page";
    private static final String PREV_PAGE = "Previous Page";

    private static final int COLS = 9;
    private static final int ROWS = 4;
    private static final int FIELDS = COLS * ROWS;

    private String name;

    private HashMap<ItemStack, Bonfire> bonfires = new HashMap<>();
    private Runnable onClose;
    private int currentPage = 0;

    public BonfireGUI(Player owner, String name, int... page) {
        super(owner, Bukkit.createInventory(owner, InventoryType.PLAYER, name));
        this.name = name;
        if(page.length > 0) {
            this.currentPage = page[0];
        }
        initGUI();
    }

    @Override
    protected void initGUI() {
        MineSoulsPlayer mineSoulsPlayer = MineSoulsPlayer.getPlayer(getOwner());
        AtomicInteger index = new AtomicInteger(0);

        mineSoulsPlayer.getBonfires().forEach((b) -> {
            if(index.get() >= (3 * COLS) * currentPage && (FIELDS * (currentPage + 1)) - COLS > index.get()) {
                ItemStack item = new ItemStack(Material.SOUL_CAMPFIRE);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(Bonfire.getBonfire(b).getName());
                meta.setLocalizedName(Math.random()*100 + "");
                item.setItemMeta(meta);

                bonfires.put(item, Bonfire.getBonfire(b));
                addButton(item, index.get() - ((3 * COLS) * currentPage));
            }
            index.incrementAndGet();
        });
        if (mineSoulsPlayer.getBonfires().size() > (3 * COLS) * (currentPage + 1)) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(NEXT_PAGE);
            item.setItemMeta(meta);
            addButton(item, FIELDS - 1);
        }
        if (currentPage > 0) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(PREV_PAGE);
            item.setItemMeta(meta);
            addButton(item, FIELDS - COLS);
        }
    }

    @Override
    protected void onButtonPress(ItemStack itemStack, ClickType clickType) {
        if (clickType == ClickType.LEFT) {
            if(itemStack.getType() == Material.SOUL_CAMPFIRE) {
                Location location = bonfires.get(itemStack).getLocation().clone();
                location.setZ(location.getZ() - 1);
                getOwner().teleport(location);
                close();
            } else if(itemStack.getType() == Material.PAPER) {
                if(itemStack.getItemMeta().getDisplayName().equals(NEXT_PAGE)) {
                    setNext(new BonfireGUI(getOwner(), name, currentPage + 1));
                    close();
                } else if(itemStack.getItemMeta().getDisplayName().equals(PREV_PAGE)) {
                    setNext(new BonfireGUI(getOwner(), name, (currentPage > 0) ? currentPage - 1 : 0));
                    close();
                }
            }
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
