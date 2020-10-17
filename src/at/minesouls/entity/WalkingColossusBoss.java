package at.minesouls.entity;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.IronGolem;
import org.bukkit.plugin.java.JavaPlugin;

import at.jojokobi.mcutil.entity.BossBarComponent;
import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.entity.EntityMapData;
import at.jojokobi.mcutil.entity.HealthComponent;
import at.jojokobi.mcutil.entity.RealHealthAccessor;
import at.minesouls.MineSouls;

public class WalkingColossusBoss extends CustomEntity<IronGolem> {
	
	private HealthComponent health;
	private BossBarComponent bossBar;

	public WalkingColossusBoss(Location place, EntityHandler handler) {
		super(place, handler, null);
		health = new HealthComponent(new RealHealthAccessor());
		addComponent(health);
		bossBar = new BossBarComponent("Walking Colossus", BarColor.RED, BarStyle.SEGMENTED_6);
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
	protected IronGolem createEntity(Location place) {
		IronGolem golem = place.getWorld().spawn(place, IronGolem.class);
		golem.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
		
		return golem;
	}

	@Override
	protected void loadData(EntityMapData data) {
		
	}

	@Override
	protected EntityMapData saveData() {
		return new EntityMapData(new HashMap<>());
	}

}
