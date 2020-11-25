package at.minesouls.areas;

import at.jojokobi.mcutil.TypedMap;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class AreaMarker implements ConfigurationSerializable {

    private Area area;
    private RoundedLocation location;

    public AreaMarker () {

    }

    public AreaMarker (Area area, Location loc) {
        this.area = area;
        this.location = new RoundedLocation(loc);
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public RoundedLocation getLocation() {
        return location;
    }

    public void setLocation(RoundedLocation location) {
        this.location = location;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("area", area);
        map.put("location", location);
        return map;
    }

    public static AreaMarker deserialize(Map<String, Object> map) {
        TypedMap tMap = new TypedMap(map);
        AreaMarker marker = new AreaMarker();
        marker.setArea(tMap.get("area", Area.class, null));
        marker.setLocation(tMap.get("location", RoundedLocation.class, null));
        return marker;
    }
}
