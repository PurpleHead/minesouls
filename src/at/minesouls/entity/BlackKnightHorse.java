package at.minesouls.entity;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Horse;
import org.bukkit.plugin.java.JavaPlugin;
import at.jojokobi.mcutil.entity.BossBarComponent;
import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.entity.EntityMapData;
import at.jojokobi.mcutil.entity.HealthComponent;
import at.jojokobi.mcutil.entity.NMSEntityUtil;
import at.jojokobi.mcutil.entity.RealHealthAccessor;
import at.jojokobi.mcutil.entity.ai.RandomTask;
import at.jojokobi.mcutil.entity.ai.RidingTask;
import at.minesouls.MineSouls;

public class BlackKnightHorse extends CustomEntity<Horse> {

	private HealthComponent health;
	private BossBarComponent bossBar;
	
	public BlackKnightHorse(Location place, EntityHandler handler) {
		super(place, handler, null);
		health = new HealthComponent(new RealHealthAccessor());
		addComponent(health);
		bossBar = new BossBarComponent("Black Knight Horse", BarColor.RED, BarStyle.SEGMENTED_10);
		addComponent(bossBar);
		
		addEntityTask(new RidingTask());
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
		horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
		
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
		return 0.5;
	}

}
