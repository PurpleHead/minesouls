package at.minesouls.entity;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

public class BlackKnightBoss extends CustomEntity<Skeleton> implements Attacker{

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
		getHandler().runTaskLater(() -> {
			BlackKnightHorse horse = new BlackKnightHorse(getEntity().getLocation(), null);
			getHandler().addEntity(horse);
			horse.getEntity().addPassenger(getEntity());
		}, 20);
	}

	@Override
	public Class<? extends JavaPlugin> getPlugin() {
		return MineSouls.class;
	}

	@Override
	protected Skeleton createEntity(Location place) {
		Skeleton knight = place.getWorld().spawn(place, WitherSkeleton.class);
		knight.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(400);
		knight.setHealth(400);
		knight.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
		
		knight.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		
		NMSEntityUtil.clearGoals(knight);
		
		return knight;
	}
	
	@Override
	protected void onDamage(EntityDamageEvent event) {
		super.onDamage(event);
		event.setCancelled(true);
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
		if (getEntity().getVehicle() == null) {
			entity.damage(8);
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 7, 1));
			}
		}
	}

	@Override
	public int getAttackDelay() {
		return 10;
	}
	
	@Override
	protected double getWalkSpeed() {
		return 0.2;
	}
	
	@Override
	protected double getSprintSpeed() {
		return 0.3;
	}

}
