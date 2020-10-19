package at.minesouls.entity;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import at.jojokobi.mcutil.entity.Attacker;
import at.jojokobi.mcutil.entity.BossBarComponent;
import at.jojokobi.mcutil.entity.CustomEntity;
import at.jojokobi.mcutil.entity.CustomEntityType;
import at.jojokobi.mcutil.entity.EntityHandler;
import at.jojokobi.mcutil.entity.EntityMapData;
import at.jojokobi.mcutil.entity.HealthComponent;
import at.jojokobi.mcutil.entity.NMSEntityUtil;
import at.jojokobi.mcutil.entity.RealHealthAccessor;
import at.jojokobi.mcutil.entity.ai.AttackTask;
import at.jojokobi.mcutil.entity.ai.RandomTask;
import at.minesouls.MineSouls;

public class BlackKnightBoss extends CustomEntity<WitherSkeleton> implements Attacker{

	private HealthComponent health;
	private BossBarComponent bossBar;
	
	public BlackKnightBoss(Location place, EntityHandler handler) {
		super(place, handler, null);
		health = new HealthComponent(new RealHealthAccessor());
		addComponent(health);
		bossBar = new BossBarComponent("Black Knight", BarColor.RED, BarStyle.SEGMENTED_10);
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
	protected WitherSkeleton createEntity(Location place) {
		WitherSkeleton knight = place.getWorld().spawn(place, WitherSkeleton.class);
		knight.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
		knight.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
		
		NMSEntityUtil.clearGoals(knight);
		
		return knight;
	}

	@Override
	protected void loadData(EntityMapData data) {
		
	}

	@Override
	protected EntityMapData saveData() {
		return new EntityMapData(new HashMap<>());
	}

	@Override
	public void attack(Damageable entity) {
		//Vehicle attack
		if (getEntity().getVehicle() != null) {
			
		}
		//Normal attack
		else {
			entity.damage(8);
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 7, 1));
			}
		}
		damageCount = 0;
	}

	@Override
	public int getAttackDelay() {
		return 14;
	}
	
	@Override
	protected double getWalkSpeed() {
		return 0.2;
	}
	
	@Override
	protected double getSprintSpeed() {
		return 0.35;
	}

}
