package GoldBigDragon_RPG.Monster.AI;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMonster;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import GoldBigDragon_RPG.Main.ServerOption;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R3.World;

import net.minecraft.server.v1_8_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

public class AI_Animal extends EntityMonster
{
	public AI_Animal(World world)
	{
		super(world);
		
		if(GoldBigDragon_RPG.Main.ServerOption.SpawnMobName != null)
		{
	        this.goalSelector.a(0, new PathfinderGoalFloat(this));
	        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.4D));
	        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 0.4D));
	        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
	        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

	        List targetB = (List)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
	        List targetC = (List)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
	        this.goalSelector.a(2, new PathfinderGoalPanic(this, 0.6D));

	        ServerOption.SpawnMobName = null;
		}
		return;
	}
	
	public static CraftMonster spawn(Location location)
	{
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final AI_Animal customEntity = new AI_Animal(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(),location.getZ(), location.getYaw(), location.getPitch());
		if(location.getWorld().getName().compareTo("Dungeon")==0)
			((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return (CraftMonster) customEntity.getBukkitEntity();
	}
	
}