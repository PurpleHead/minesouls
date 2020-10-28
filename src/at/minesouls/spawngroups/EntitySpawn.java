package at.minesouls.spawngroups;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public abstract class EntitySpawn implements ConfigurationSerializable {

    private Location location;

    public EntitySpawn(Location location) {
        this.location = location;
    }

    public abstract void spawn();

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
