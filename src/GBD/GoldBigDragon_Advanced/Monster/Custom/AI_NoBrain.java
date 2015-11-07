package GBD.GoldBigDragon_Advanced.Monster.Custom;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMonster;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import GBD.GoldBigDragon_Advanced.Main.Main;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.World;

import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

public class AI_NoBrain extends EntityMonster
{
	public AI_NoBrain(World world)
	{
		super(world);
		
		if(Main.SpawnMobName != null)
		{
	        List goalB = (List)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
	        List goalC = (List)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
	        List targetB = (List)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
	        List targetC = (List)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
			Main.SpawnMobName = null;
		}
	}
	
	public static CraftMonster spawn(Location location)
	{
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final AI_NoBrain customEntity = new AI_NoBrain(mcWorld);
		customEntity.setLocation(location.getX(), location.getY(),
				location.getZ(), location.getYaw(), location.getPitch());
		((CraftLivingEntity) customEntity.getBukkitEntity())
				.setRemoveWhenFarAway(true);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return (CraftMonster) customEntity.getBukkitEntity();
	}
	
}