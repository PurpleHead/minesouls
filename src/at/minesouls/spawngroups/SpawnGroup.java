package at.minesouls.spawngroups;

import at.jojokobi.mcutil.NamespacedEntry;
import at.jojokobi.mcutil.TypedMap;
import at.minesouls.MineSouls;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;

import java.util.*;

public class SpawnGroup implements ConfigurationSerializable {

    private static final String UUID_KEY = "uuid";
    private static final String ENTITY_SPAWNS_KEY = "entitySpawns";
    private static final String HAS_RESTED_KEY = "hasRested";

    private UUID uuid;
    private List<EntitySpawn> entitySpawns = new LinkedList<>();
    private boolean rested = true;

    public SpawnGroup(UUID uuid) {
        this.uuid = uuid;
    }

    public void spawnAll () {
        entitySpawns.forEach(es -> {
            Bukkit.getConsoleSender().sendMessage(es.toString());
            es.spawn();
        });
    }

    public void removeAll () {
        entitySpawns.forEach(es -> {
            es.remove();
        });
    }

    public void addSpawn (String key, Location loc) {
        String[] split = key.split(":");
        Bukkit.getConsoleSender().sendMessage(Arrays.toString(split));
        if (split[0].equals(MineSouls.PLUGIN_NAME.toLowerCase())) {
            entitySpawns.add(new CustomEntitySpawn(loc, new NamespacedEntry(split[0], split[1])));
        } else if (split[0].equals("minecraft")) {
            entitySpawns.add(new MinecraftEntitySpawn(loc, getEntityByName(split[1])));
        }
    }

    public EntityType getEntityByName(String name) {
        for (EntityType type : EntityType.values()) {
            if(type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean hasRested() {
        return rested;
    }

    public void setRested(boolean rested) {
        this.rested = rested;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put(UUID_KEY, uuid.toString());
        map.put(ENTITY_SPAWNS_KEY, entitySpawns);
        map.put(HAS_RESTED_KEY, hasRested());
        return map;
    }

    public static SpawnGroup deserialize(Map<String, Object> map) {
        TypedMap tMap = new TypedMap(map);
        SpawnGroup group = new SpawnGroup(UUID.fromString(tMap.getString(UUID_KEY)));
        group.entitySpawns.addAll(tMap.getList(ENTITY_SPAWNS_KEY, EntitySpawn.class));
        group.setRested(tMap.getBoolean(HAS_RESTED_KEY));
        return group;
    }

}
