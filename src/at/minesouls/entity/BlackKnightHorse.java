package at.minesouls.entity;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Horse.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import at.jojokobi.mcutil.entity.Attacker;
import at.jojokobi.mcutil.entity.BossBarComponent;
import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.entity.EntityMapData;
import at.jojokobi.mcutil.entity.HealthComponent;
import at.jojokobi.mcutil.entity.NMSEntityUtil;
import at.jojokobi.mcutil.entity.RealHealthAccessor;
import at.jojokobi.mcutil.entity.ai.AttackTask;
import at.jojokobi.mcutil.entity.ai.RandomTask;
import at.minesouls.MineSouls;

public class BlackKnightHorse extends CustomEntity<Horse> implements Attacker{

	private HealthComponent health;
	private BossBarComponent bossBar;
	
	public BlackKnightHorse(Location place, EntityHandler handler) {
		super(place, handler, null);
		health = new HealthComponent(new RealHealthAccessor());
		addComponent(health);
		bossBar = new BossBarComponent("Black Knight Horse", BarColor.GREEN, BarStyle.SEGMENTED_10);
		addComponent(bossBar);
		
		addEntityTask(new AttackTask(Player.class));
		addEntityTask(new RandomTask());
	}
	
	@Override
	protected void spawn() {
		super.spawn();

	}

	@Override
	public Class<? extends JavaPlugin> getPlugin() {
		return MineSouls.class;
	}

	@Override
	protected Horse createEntity(Location place) {
		Horse horse = place.getWorld().spawn(place, Horse.class);
		horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(150);
		horse.setHealth(150);
		horse.setTamed(true);
		horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		horse.setColor(Color.BLACK);
		
		NMSEntityUtil.clearGoals(horse);
		
		return horse;
	}

	@Override
	protected void loadData(EntityMapData data) {
		
	}

	@Override
	protected EntityMapData saveData() {
		return new EntityMapData(new HashMap<>());
	}

	@Override
	protected double getWalkSpeed() {
		return 0.2;
	}
	
	@Override
	protected double getSprintSpeed() {
		return 0.7;
	}
	
	@Override
	public void attack(Damageable entity) {
		entity.damage(6);
		getEntity().setVelocity(getEntity().getLocation().getDirection().multiply(-1));
	}

	@Override
	public int getAttackDelay() {
		return 10;
	}

}
