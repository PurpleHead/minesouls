package at.minesouls.areas;

import at.jojokobi.mcutil.TypedMap;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RoundedLocation implements ConfigurationSerializable {
    private static final String X_KEY = "x";
    private static final String Y_KEY = "y";
    private static final String Z_KEY = "z";

    private int x;
    private int y;
    private int z;

    public RoundedLocation () {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public RoundedLocation (Location loc) {
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoundedLocation that = (RoundedLocation) o;
        return x == that.x &&
                y == that.y &&
                z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put(X_KEY, getX());
        map.put(Y_KEY, getY());
        map.put(Z_KEY, getZ());
        return map;
    }

    public static RoundedLocation deserialize(Map<String, Object> map) {
        TypedMap tMap = new TypedMap(map);
        RoundedLocation roundedLocation = new RoundedLocation();
        roundedLocation.setX(tMap.getInt(X_KEY));
        roundedLocation.setY(tMap.getInt(Y_KEY));
        roundedLocation.setZ(tMap.getInt(Z_KEY));
        return roundedLocation;
    }
}
