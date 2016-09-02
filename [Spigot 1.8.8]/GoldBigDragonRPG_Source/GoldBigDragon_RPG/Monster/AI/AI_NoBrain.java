package GoldBigDragon_RPG.Monster.AI;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMonster;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.World;

import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

public class AI_NoBrain extends EntityMonster
{
	public AI_NoBrain(World world)
	{
		super(world);
		
		if(GoldBigDragon_RPG.Main.ServerOption.SpawnMobName != null)
		{
	        List goalB = (List)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
	        List goalC = (List)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
	        List targetB = (List)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
	        List targetC = (List)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
	        GoldBigDragon_RPG.Main.ServerOption.SpawnMobName = null;
		}
	}
	
	public static CraftMonster spawn(Location location)
	{
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final AI_NoBrain customEntity = new AI_NoBrain(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(),
				location.getZ(), location.getYaw(), location.getPitch());
		if(location.getWorld().getName().compareTo("Dungeon")==0)
			((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return (CraftMonster) customEntity.getBukkitEntity();
	}
	
}