package at.minesouls.player;

import at.jojokobi.mcutil.TypedMap;
import at.minesouls.areas.Area;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.LivingEntity;

import java.util.*;

public class MineSoulsPlayer implements ConfigurationSerializable {

    private static final String UUID_KEY = "uuid";
    private static final String LAST_INTERACT_KEY = "lastInteract";
    private static final String BONFIRES_KEY = "bonfires";
    private static final String CURRENT_AREA_KEY = "currentArea";
    private static final String HEALTH_KEY = "health";
    private static final String STAMINA_KEY = "stamina";
    private static final String STRENGTH_KEY = "strength";
    private static final String DEXTERITY_KEY = "dexterity";
    private static final String VITALITY_KEY = "vitality";
    private static final String SOULS_KEY = "souls";
    private static final String SOULS_LEVEL_KEY = "soulLevel";

    private static HashMap<UUID, MineSoulsPlayer> loadedPlayers = new HashMap<>();

    private List<Location> bonfires = new LinkedList<>();
    private long lastInteract = 0;
    private UUID uuid;
    private Area currentArea = null;

    private int healthLevel = 0;
    private int staminaLevel = 0;
    private int dexterityLevel = 0;
    private int strengthLevel = 0;
    private int vitalityLevel = 0;

    private int souls = 0;
    private int soulLevel = 0;

    private MineSoulsPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public static MineSoulsPlayer getPlayer(UUID uuid) {
        MineSoulsPlayer mineSoulsPlayer = loadedPlayers.get(uuid);

        if(mineSoulsPlayer == null) {
            mineSoulsPlayer = new MineSoulsPlayer(uuid);
            loadedPlayers.put(uuid, mineSoulsPlayer);
        }
        return mineSoulsPlayer;
    }

    public static MineSoulsPlayer getPlayer (org.bukkit.entity.Player player) {
        return getPlayer(player.getUniqueId());
    }

    public static void removeBonfireFromAll (Location bonfire) {
        loadedPlayers.forEach((u, p) -> {
            p.getBonfires().remove(bonfire);
        });
    }

    public static void removeAllBonfires() {
        Bukkit.broadcastMessage(ChatColor.RED + "Bonfires cleared!");
        loadedPlayers.forEach((k, v) -> {
            v.getBonfires().clear();
        });
    }

    public int calcSouls () {
        return (int) (Math.pow(0.02* this.getSoulLevel(), 3) + Math.pow(3.06* this.getSoulLevel(), 2) + (105.6 * this.getSoulLevel()) - 895);
    }

    public void addKill (LivingEntity e) {
        setSouls(getSouls() + 50);
    }

    public static HashMap<UUID, MineSoulsPlayer> getLoadedPlayers() {
        return loadedPlayers;
    }

    public static void setLoadedPlayers(HashMap<UUID, MineSoulsPlayer> loadedPlayers) {
        MineSoulsPlayer.loadedPlayers = loadedPlayers;
    }

    public List<Location> getBonfires() {
        return bonfires;
    }

    public void setBonfires(List<Location> bonfires) {
        this.bonfires = bonfires;
    }

    public long getLastInteract() {
        return lastInteract;
    }

    public void setLastInteract(long lastInteract) {
        this.lastInteract = lastInteract;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Area getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(Area currentArea) {
        this.currentArea = currentArea;
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(int healthLevel) {
        this.healthLevel = healthLevel;
    }

    public int getStaminaLevel() {
        return staminaLevel;
    }

    public void setStaminaLevel(int staminaLevel) {
        this.staminaLevel = staminaLevel;
    }

    public int getDexterityLevel() {
        return dexterityLevel;
    }

    public void setDexterityLevel(int dexterityLevel) {
        this.dexterityLevel = dexterityLevel;
    }

    public int getStrengthLevel() {
        return strengthLevel;
    }

    public int getSouls() {
        return souls;
    }

    public void setSouls(int souls) {
        this.souls = souls;
    }

    public void setStrengthLevel(int strengthLevel) {
        this.strengthLevel = strengthLevel;
    }

    public int getVitalityLevel() {
        return vitalityLevel;
    }

    public void setVitalityLevel(int vitalityLevel) {
        this.vitalityLevel = vitalityLevel;
    }

    public int getSoulLevel() {
        return soulLevel;
    }

    public void setSoulLevel(int soulLevel) {
        this.soulLevel = soulLevel;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put(LAST_INTERACT_KEY, getLastInteract());
        map.put(UUID_KEY, getUuid().toString());
        map.put(BONFIRES_KEY, List.copyOf(getBonfires()));
        map.put(CURRENT_AREA_KEY, getCurrentArea());
        map.put(HEALTH_KEY, getHealthLevel());
        map.put(STAMINA_KEY, getStaminaLevel());
        map.put(DEXTERITY_KEY, getDexterityLevel());
        map.put(STRENGTH_KEY, getStrengthLevel());
        map.put(VITALITY_KEY, getVitalityLevel());
        map.put(SOULS_KEY, getSouls());
        map.put(SOULS_LEVEL_KEY, getSoulLevel());

        return map;
    }

    public static MineSoulsPlayer valueOf (Map<String, Object> map) {
        UUID uuid = UUID.fromString((String) map.get(UUID_KEY));
        MineSoulsPlayer player = new MineSoulsPlayer(uuid);
        TypedMap t = new TypedMap(map);

        player.setLastInteract(t.getLong(LAST_INTERACT_KEY));
        player.setBonfires(t.getList(BONFIRES_KEY, Location.class));
        player.setCurrentArea(t.get(CURRENT_AREA_KEY, Area.class, null));
        player.setHealthLevel(t.getInt(HEALTH_KEY));
        player.setStaminaLevel(t.getInt(STAMINA_KEY));
        player.setDexterityLevel(t.getInt(DEXTERITY_KEY));
        player.setStrengthLevel(t.getInt(STRENGTH_KEY));
        player.setVitalityLevel(t.getInt(VITALITY_KEY));
        player.setSouls(t.getInt(SOULS_KEY));
        player.setSoulLevel(t.getInt(SOULS_LEVEL_KEY));

        return player;
    }
}
