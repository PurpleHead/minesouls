package at.minesouls.entity;

import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.entity.EntityMapData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class WhiteKnight extends CustomEntity<Skeleton> {

    public WhiteKnight(Location place, EntityHandler handler) {
        super(place, handler, null);
    }

    @Override
    public Class<? extends JavaPlugin> getPlugin() {
        return null;
    }

    @Override
    protected Skeleton createEntity(Location location) {
        Skeleton skeleton = location.getWorld().spawn(location, Skeleton.class);
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        skull.setItemMeta(meta);

        skeleton.getEquipment().setHelmet(skull);

        return skeleton;
    }

    @Override
    protected void loadData(EntityMapData entityMapData) {

    }

    @Override
    protected EntityMapData saveData() {
        return new EntityMapData(new HashMap<>());
    }
}
