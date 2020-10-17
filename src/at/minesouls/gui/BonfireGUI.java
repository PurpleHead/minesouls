package at.minesouls.gui;

import at.jojokobi.mcutil.gui.InventoryGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BonfireGUI extends InventoryGUI {

    public BonfireGUI (Player owner, Inventory inventory) {
        super(owner, inventory);
        initGUI();
    }

    @Override
    protected void initGUI() {

    }

    @Override
    protected void onButtonPress(ItemStack itemStack, ClickType clickType) {

    }
}
