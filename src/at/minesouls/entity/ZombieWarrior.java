package at.minesouls.entity;

import at.jojokobi.mcutil.entity.*;
import at.minesouls.MineSouls;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class ZombieWarrior extends CustomEntity<ZombieVillager> {

    public ZombieWarrior(Location place, EntityHandler handler) {
        super(place, handler, null);
    }

    @Override
    public Class<? extends JavaPlugin> getPlugin() {
        return MineSouls.class;
    }

    @Override
    protected ZombieVillager createEntity(Location location) {
        ZombieVillager zombieVillager = location.getWorld().spawn(location, ZombieVillager.class);

        zombieVillager.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        zombieVillager.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(4);

        zombieVillager.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        zombieVillager.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SHOVEL));
        zombieVillager.getEquipment().setItemInMainHandDropChance(0.0f);
        zombieVillager.getEquipment().setHelmetDropChance(0.0f);

        return zombieVillager;
    }

    @Override
    protected void loadData(EntityMapData entityMapData) {

    }

    @Override
    protected EntityMapData saveData() {
        return new EntityMapData(new HashMap<>());
    }
}
