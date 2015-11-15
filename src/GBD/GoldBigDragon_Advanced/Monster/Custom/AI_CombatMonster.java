package GBD.GoldBigDragon_Advanced.Monster.Custom;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMonster;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.World;

import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

public class AI_CombatMonster extends EntityMonster
{
	public AI_CombatMonster(World world)
	{
		super(world);
		
		if(Main.SpawnMobName != null)
		{
			YamlController Monster_YC = GBD.GoldBigDragon_Advanced.Main.Main.Monster_YC;
			YamlManager Monster  = Monster_YC.getNewConfig("Monster/MonsterList.yml");

			String AI = Monster.getString(Main.SpawnMobName + ".AI");

	        List targetB = (List)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
	        List targetC = (List)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();

	        this.goalSelector.a(0, new PathfinderGoalFloat(this));
	        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.4D));
	        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 0.4D));
	        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
	        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

	        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 0.4D, true));
	        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
	        
	        if(AI.equals("근접 선공"))
		        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true, true));

			Main.SpawnMobName = null;
		}
		return;
	}
	
	public static CraftMonster spawn(Location location)
	{
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final AI_CombatMonster customEntity = new AI_CombatMonster(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(),
				location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity())
				.setRemoveWhenFarAway(true);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return (CraftMonster) customEntity.getBukkitEntity();
	}
	
}