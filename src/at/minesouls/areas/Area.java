package at.minesouls.areas;

import at.jojokobi.mcutil.TypedMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Area implements ConfigurationSerializable {

    private static final String UUID_KEY = "uuid";
    private static final String NAME_KEY = "name";

    private static HashMap<UUID, Area> areas = new HashMap<>();

    private final UUID uuid;
    private String name;

    public Area () {
        this.uuid = UUID.randomUUID();
    }

    public Area(String name, UUID uuid){
        this.uuid = uuid;
        this.name = name;
    }

    public static Area getByUUID (UUID uuid) {
        return areas.get(uuid);
    }

    public static void addArea (UUID uuid, Area area) {
        areas.put(uuid, area);
    }

    public static UUID getUUIDbyName (String name) {
        AtomicReference<UUID> uuid = new AtomicReference<>();
        areas.forEach((k, v) -> {
            if (v.getName().equals(name)) {
                uuid.set(v.getUuid());
            }
        });

        if(uuid.get() == null) {
            uuid.set(UUID.randomUUID());
            addArea(uuid.get(), new Area(name, uuid.get()));
        }
        return uuid.get();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static HashMap<UUID, Area> getAreas() {
        return areas;
    }

    public static void setAreas(HashMap<UUID, Area> areas) {
        Area.areas = areas;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put(UUID_KEY, uuid.toString());
        map.put(NAME_KEY, name);
        return map;
    }

    public static Area deserialize(Map<String, Object> map) {
        TypedMap tMap = new TypedMap(map);
        Area area = new Area(tMap.getString(NAME_KEY), UUID.fromString(tMap.getString(UUID_KEY)));
        return area;
    }
}
