package at.minesouls.spawngroups;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.mcutil.entity.spawns.CustomSpawn;
import at.jojokobi.mcutil.entity.spawns.CustomSpawnsHandler;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class CustomEntitySpawn extends EntitySpawn {

    private NamespacedEntry key;

    public CustomEntitySpawn(Location loc, NamespacedEntry key) {
        super(loc);
        this.key = key;
    }

    @Override
    public void spawn() {
        CustomSpawn spawn = CustomSpawnsHandler.getInstance().getItem(key);
        if (spawn != null) {
            spawn.spawn(getLocation());
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("location", getLocation());
        return map;
    }

    public static CustomEntitySpawn deserialize(Map<String, Object> map) {
        TypedMap tMap = new TypedMap(map);
        return new CustomEntitySpawn(tMap.get("location", Location.class, null), tMap.get("key", NamespacedEntry.class, null));
    }

}
