package at.minesouls.areas;

import at.jojokobi.mcutil.TypedMap;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class AreaMarker implements ConfigurationSerializable {

    private static final String AREA_KEY = "area";
    private static final String LOCATION_KEY = "location";

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
        map.put(AREA_KEY, area);
        map.put(LOCATION_KEY, location);
        return map;
    }

    public static AreaMarker deserialize(Map<String, Object> map) {
        TypedMap tMap = new TypedMap(map);
        AreaMarker marker = new AreaMarker();
        marker.setArea(tMap.get(AREA_KEY, Area.class, null));
        marker.setLocation(tMap.get(LOCATION_KEY, RoundedLocation.class, null));
        return marker;
    }
}
