package at.minesouls.spawngroups;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.TypedMap;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class MinecraftEntitySpawn extends EntitySpawn {

    private EntityType type;

    public MinecraftEntitySpawn (Location loc, EntityType type) {
        super(loc);
        this.type = type;
    }

    @Override
    public void spawn() {
        Location loc = getLocation();
        loc.getWorld().spawnEntity(loc, type);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type + "");
        map.put("location", getLocation());
        return map;
    }

    public static MinecraftEntitySpawn deserialize(Map<String, Object> map) {
        TypedMap tMap = new TypedMap(map);
        return new MinecraftEntitySpawn(tMap.get("location", Location.class, null), tMap.getEnum("type", EntityType.class, EntityType.ZOMBIE));
    }

}
