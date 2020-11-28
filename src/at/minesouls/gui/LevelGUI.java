package at.minesouls.gui;

import at.jojokobi.mcutil.gui.InventoryGUI;
import at.minesouls.player.MineSoulsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LevelGUI extends InventoryGUI {

    private static final int COLS = 9;
    private static final int ROWS = 4;
    private static final int FIELDS = COLS * ROWS;

    private static final Material HEALTH_MATERIAL = Material.SPIDER_EYE;
    private static final Material STAMINA_MATERIAL = Material.NETHERITE_BOOTS;
    private static final Material STRENGTH_MATERIAL = Material.NETHERITE_SWORD;
    private static final Material DEXTERITY_MATERIAL = Material.GOLDEN_CARROT;
    private static final Material VITALITY_MATERIAL = Material.RABBIT_FOOT;

    private MineSoulsPlayer mineSoulsPlayer = MineSoulsPlayer.getPlayer(getOwner());

    public LevelGUI(Player owner) {
        super(owner, Bukkit.createInventory(owner, InventoryType.PLAYER, owner.getDisplayName() + "'s Levels"));
        initGUI();
    }

    @Override
    protected void initGUI() {
        // Health
        {
            ItemStack healthItem = new ItemStack(HEALTH_MATERIAL);
            ItemMeta meta = healthItem.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add("Required souls: "+ mineSoulsPlayer.calcSouls());
            meta.setLore(lore);
            meta.setDisplayName("Vigor");
            healthItem.setItemMeta(meta);
            healthItem.setAmount(mineSoulsPlayer.getHealthLevel() + 1);
            addButton(healthItem, 0);
        }
        // Stamina
        {
            ItemStack staminaItem = new ItemStack(STAMINA_MATERIAL);
            ItemMeta meta = staminaItem.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add("Required souls: " + mineSoulsPlayer.calcSouls());
            meta.setLore(lore);
            meta.setDisplayName("Endurance");
            staminaItem.setItemMeta(meta);
            staminaItem.setAmount(mineSoulsPlayer.getStaminaLevel() + 1);
            addButton(staminaItem, 2);
        }
        // Strength
        {
            ItemStack strengthItem = new ItemStack(STRENGTH_MATERIAL);
            ItemMeta meta = strengthItem.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add("Required souls: " + mineSoulsPlayer.calcSouls());
            meta.setLore(lore);
            meta.setDisplayName("Strength");
            strengthItem.setItemMeta(meta);
            strengthItem.setAmount(mineSoulsPlayer.getStrengthLevel() + 1);
            addButton(strengthItem, 4);
        }
        // Dexterity
        {
            ItemStack dexterityItem = new ItemStack(DEXTERITY_MATERIAL);
            ItemMeta meta = dexterityItem.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add("Required souls: " + mineSoulsPlayer.calcSouls());
            meta.setLore(lore);
            meta.setDisplayName("Dexterity");
            dexterityItem.setItemMeta(meta);
            dexterityItem.setAmount(mineSoulsPlayer.getDexterityLevel() + 1);
            addButton(dexterityItem, 6);
        }
        // Equip Load
        {
            ItemStack vitalityItem = new ItemStack(VITALITY_MATERIAL);
            ItemMeta meta = vitalityItem.getItemMeta();
            List<String> lore = new ArrayList<>();
            lore.add("Required souls: " + mineSoulsPlayer.calcSouls());
            meta.setLore(lore);
            meta.setDisplayName("Vitality");
            vitalityItem.setItemMeta(meta);
            vitalityItem.setAmount(mineSoulsPlayer.getVitalityLevel() + 1);
            addButton(vitalityItem, 8);
        }
        // Souls
        {
            ItemStack soulsItem = new ItemStack(Material.POISONOUS_POTATO);
            ItemMeta meta = soulsItem.getItemMeta();
            meta.setDisplayName("Souls: " + mineSoulsPlayer.getSouls());
            soulsItem.setItemMeta(meta);
            addButton(soulsItem, FIELDS - COLS);
        }
        //Soul level
        {
            ItemStack soulLevel = new ItemStack(Material.LEATHER);
            ItemMeta meta = soulLevel.getItemMeta();
            meta.setDisplayName("Soul level: " + mineSoulsPlayer.getSoulLevel());
            soulLevel.setItemMeta(meta);
            addButton(soulLevel, FIELDS - COLS + 1);
        }
    }

    @Override
    protected void onButtonPress(ItemStack itemStack, ClickType clickType) {

    }
}
