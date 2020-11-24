package at.minesouls;
import at.minesouls.blocks.Bonfire;
import at.minesouls.commands.BonfireCommand;
import at.minesouls.commands.SpawnGroupToolsCommand;
import at.minesouls.entity.ZombieWarrior;
import at.minesouls.player.MineSoulsPlayer;
import at.minesouls.spawngroups.CustomEntitySpawn;
import at.minesouls.spawngroups.MinecraftEntitySpawn;
import at.minesouls.spawngroups.SpawnGroup;
import at.minesouls.spawngroups.SpawnGroupHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import at.jojokobi.mcutil.entity.spawns.CustomSpawnsHandler;
import at.jojokobi.mcutil.entity.spawns.FunctionSpawn;
import at.minesouls.entity.BlackKnightBoss;
import at.minesouls.entity.WalkingColossusBoss;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MineSouls extends JavaPlugin {

    public static final String PLUGIN_NAME = "MineSouls";

//    private static final String BONFIRE_KEY = "bonfire";
//    private static final String BONFIRE_FILENAME = "bonfires.yml";
//    private static final String BONFIRE_SUBFOLDER = "bonfires";

    @Override
    public void onEnable() {
        super.onEnable();
        ConfigurationSerialization.registerClass(Bonfire.class);
        ConfigurationSerialization.registerClass(MineSoulsPlayer.class);
        ConfigurationSerialization.registerClass(SpawnGroup.class);
        ConfigurationSerialization.registerClass(CustomEntitySpawn.class);
        ConfigurationSerialization.registerClass(MinecraftEntitySpawn.class);

        loadConfiguration();

        getServer().getPluginManager().registerEvents(new MineSoulsListener(), this);
        getCommand(BonfireCommand.COMMAND).setExecutor(new BonfireCommand());
        getCommand(SpawnGroupToolsCommand.COMMAND).setExecutor(new SpawnGroupToolsCommand());
        
        CustomSpawnsHandler.getInstance().addItem(new FunctionSpawn(getName().toLowerCase(), "walking_colossus", l -> new WalkingColossusBoss(l, null)));
        CustomSpawnsHandler.getInstance().addItem(new FunctionSpawn(getName().toLowerCase(), "black_knight", l -> new BlackKnightBoss(l, null)));
        CustomSpawnsHandler.getInstance().addItem(new FunctionSpawn(getName().toLowerCase(), "zombie_warrior", l -> new ZombieWarrior(l, null)));

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " [MINESOULS LOADED]");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        saveConfiguration();
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " [MINESOULS DISABLED]");
    }

    private void saveConfiguration () {
        // Bonfires
        Bonfire.disable();
        SpawnGroupHandler.getInstance().disable();
    }

    private void loadConfiguration () {
        Bonfire.enable();
        SpawnGroupHandler.getInstance().enable();
    }

    public static String stringRange (String[] string, int begin, int end) {
        String[] array = Arrays.copyOfRange(string, begin, end);
        return Arrays.stream(array).collect(Collectors.joining(" "));
    }

}
