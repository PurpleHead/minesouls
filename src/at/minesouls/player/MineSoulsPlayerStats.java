package at.minesouls.player;

import at.jojokobi.mcutil.TypedMap;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MineSoulsPlayerStats implements ConfigurationSerializable {

    private static final String HEALTH_KEY = "health";
    private static final String STAMINA_KEY = "stamina";
    private static final String STRENGTH_KEY = "strength";
    private static final String DEXTERITY_KEY = "dexterity";
    private static final String VITALITY_KEY = "vitality";
    private static final String SOULS_LEVEL_KEY = "soulLevel";
    private static final String SOULS_KEY = "souls";
    private static final String UUID_KEY = "uuid";

    private int healthLevel = 0;
    private int staminaLevel = 0;
    private int dexterityLevel = 0;
    private int strengthLevel = 0;
    private int vitalityLevel = 0;
    private int soulsLevel = 12;
    private int souls = 0;

    private UUID uuid;

    public MineSoulsPlayerStats (UUID uuid) {
        this.uuid = uuid;
    }

    public void levelUp (StatsType type) {
        int reqSouls = calcSouls();
        if(getSouls() >= reqSouls) {
            setSouls(getSouls() - reqSouls);
            soulsLevel++;
            switch(type) {
                case HEALTH:
                    healthLevel++;
                    Bukkit.getPlayer(uuid).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20 + (healthLevel));
                    break;
                case STAMINA:
                    staminaLevel++;
                    break;
                case DEXTERITY:
                    dexterityLevel++;
                    break;
                case STRENGTH:
                    strengthLevel++;
                    break;
                case VITALITY:
                    vitalityLevel++;
                    break;
            }
        }
    }

    public int calcSouls () {
        return (int) (Math.pow(0.02* this.getSoulsLevel(), 3) + Math.pow(3.06* this.getSoulsLevel(), 2) + (105.6 * this.getSoulsLevel()) - 895);
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

    public void setStrengthLevel(int strengthLevel) {
        this.strengthLevel = strengthLevel;
    }

    public int getVitalityLevel() {
        return vitalityLevel;
    }

    public void setVitalityLevel(int vitalityLevel) {
        this.vitalityLevel = vitalityLevel;
    }

    public int getSoulsLevel() {
        return soulsLevel;
    }

    public void setSoulsLevel(int soulsLevel) {
        this.soulsLevel = soulsLevel;
    }

    public int getSouls() {
        return souls;
    }

    public void setSouls(int souls) {
        this.souls = souls;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put(HEALTH_KEY, getHealthLevel());
        map.put(STAMINA_KEY, getStaminaLevel());
        map.put(DEXTERITY_KEY, getDexterityLevel());
        map.put(STRENGTH_KEY, getStrengthLevel());
        map.put(VITALITY_KEY, getVitalityLevel());
        map.put(SOULS_LEVEL_KEY, getSoulsLevel());
        map.put(SOULS_KEY, getSouls());
        map.put(UUID_KEY, getUuid().toString());

        return map;
    }

    public static MineSoulsPlayerStats deserialize (Map<String, Object> map) {
        TypedMap t = new TypedMap(map);
        UUID uuid = UUID.fromString(t.getString(UUID_KEY));
        MineSoulsPlayerStats stats = new MineSoulsPlayerStats(uuid);

        stats.setHealthLevel(t.getInt(HEALTH_KEY));
        stats.setStaminaLevel(t.getInt(STAMINA_KEY));
        stats.setDexterityLevel(t.getInt(DEXTERITY_KEY));
        stats.setStrengthLevel(t.getInt(STRENGTH_KEY));
        stats.setVitalityLevel(t.getInt(VITALITY_KEY));
        stats.setSoulsLevel(t.getInt(SOULS_LEVEL_KEY));
        stats.setSouls(t.getInt(SOULS_KEY));

        return stats;
    }

    public enum StatsType {
        HEALTH,
        STAMINA,
        STRENGTH,
        DEXTERITY,
        VITALITY
    }
}
