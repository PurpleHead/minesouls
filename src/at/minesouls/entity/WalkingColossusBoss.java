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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

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

public class WalkingColossusBoss extends CustomEntity<IronGolem> implements Attacker {
	
	private HealthComponent health;
	private BossBarComponent bossBar;
	
	private int damageCount = 0;
	private boolean jumping = false;

	public WalkingColossusBoss(Location place, EntityHandler handler) {
		super(place, handler, null);
		health = new HealthComponent(new RealHealthAccessor());
		addComponent(health);
		bossBar = new BossBarComponent("Walking Colossus", BarColor.RED, BarStyle.SEGMENTED_6);
		addComponent(bossBar);
		
		addEntityTask(new AttackTask(Player.class));
		addEntityTask(new RandomTask());
	}
	
	@Override
	protected void spawn() {
		super.spawn();
	}
	
	@Override
	public void loop() {
		//Perform jump
		if (jumping && getEntity().isOnGround()) {
			jumping = false;
			//Damage
			for (Entity entity : getEntity().getNearbyEntities(5, 2, 5)) {
				if (entity instanceof Player) {
					((Player) entity).damage(10);
				}
			}
			//Spread
			for (int i = 0; i < 50; i++) {
				getEntity().getWorld().spawnParticle(Particle.BLOCK_CRACK, getEntity().getLocation().add(Math.random() * 10 - 5, 1, Math.random() * 10 - 5), 10, Material.DIRT.createBlockData());
			}
		}
		super.loop();
		if (jumping) {
			Vector vel = getEntity().getVelocity();
			vel.setX(0);
			vel.setZ(0);
			getEntity().setVelocity(vel);
		}
	}
	
	@Override
	protected void onDamage(EntityDamageEvent event) {
		super.onDamage(event);
		if (event instanceof EntityDamageByEntityEvent) {
			damageCount++;
		}
	}

	@Override
	public Class<? extends JavaPlugin> getPlugin() {
		return MineSouls.class;
	}

	@Override
	protected IronGolem createEntity(Location place) {
		IronGolem golem = place.getWorld().spawn(place, IronGolem.class);
		golem.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(120);
		golem.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
		
		NMSEntityUtil.clearGoals(golem);
		
		return golem;
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
		if (damageCount >= 3) {
			//Super attack
			jump();
			jumping = true;
		}
		else {
			entity.damage(5);
			entity.setVelocity(entity.getVelocity().setY(0.6));
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
		return 0.5;
	}
	
	@Override
	protected double getJumpSpeed() {
		return 1.0;
	}

}
