package at.minesouls.spawngroups;

import at.jojokobi.mcutil.TypedMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.world.SpawnChangeEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SpawnGroup implements ConfigurationSerializable {

    private final String name;
    private List<EntitySpawn> entitySpawns = new LinkedList<>();

    public SpawnGroup(String name) {
        this.name = name;
    }

    public void spawnAll () {
        entitySpawns.forEach(es -> {
            es.spawn();
        });
    }

    public void removeAll () {
        entitySpawns.forEach(es -> {

        });
    }

    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("entitySpawns", entitySpawns);
        return map;
    }

    public static SpawnGroup deserialize(Map<String, Object> map) {
        TypedMap tMap = new TypedMap(map);
        SpawnGroup group = new SpawnGroup(tMap.getString("name"));
        group.entitySpawns.addAll(tMap.getList("entitySpawns", EntitySpawn.class));
        return group;
    }

}
