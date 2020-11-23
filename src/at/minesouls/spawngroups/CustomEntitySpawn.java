package at.minesouls.spawngroups;

import at.jojokobi.mcutil.JojokobiUtilPlugin;
import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.TypedMap;
import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.spawns.CustomSpawn;
import at.jojokobi.mcutil.entity.spawns.CustomSpawnsHandler;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CustomEntitySpawn extends EntitySpawn {

    private static final String ENTITY_KEY = "entities";

    private NamespacedEntry key;
    private List<CustomEntity<?>> entities = new LinkedList<>();

    public CustomEntitySpawn(Location loc, NamespacedEntry key) {
        super(loc);
        this.key = key;
    }

    @Override
    public void spawn() {
        CustomSpawn spawn = CustomSpawnsHandler.getInstance().getItem(key);
        if (spawn != null) {
            entities.addAll(spawn.spawn(getLocation()));
            entities.forEach(es -> JavaPlugin.getPlugin(JojokobiUtilPlugin.class).getEntityHandler().addEntity(es));
        }
    }

    @Override
    public void remove() {
        if(entities != null)
            entities.forEach(e -> e.delete());
        entities.clear();
    }

    public List<CustomEntity<?>> getEntities() {
        return entities;
    }

    public void setEntities(List<CustomEntity<?>> entities) {
        this.entities = entities;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("location", getLocation());
        map.put(ENTITY_KEY, getEntities());
        return map;
    }

    public static CustomEntitySpawn deserialize(Map<String, Object> map) {
        TypedMap tMap = new TypedMap(map);
        CustomEntitySpawn sp = new CustomEntitySpawn(tMap.get("location", Location.class, null), tMap.get("key", NamespacedEntry.class, null));
        return sp;
    }

}
