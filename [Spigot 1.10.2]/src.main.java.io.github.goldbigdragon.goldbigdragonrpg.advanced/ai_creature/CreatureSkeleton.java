package ai_creature;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.google.common.collect.Sets;

import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.EntitySkeleton;
import net.minecraft.server.v1_10_R1.World;

import net.minecraft.server.v1_10_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_10_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_10_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_10_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_10_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_10_R1.PathfinderGoalPanic;
import net.minecraft.server.v1_10_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_10_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_10_R1.PathfinderGoalSelector;

public class CreatureSkeleton extends EntitySkeleton
{
	public CreatureSkeleton(World world, String ai)
	{
		super(world);
	    try
	    {
		    Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
		    bField.setAccessible(true);
		    Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
		    cField.setAccessible(true);
		    bField.set(this.goalSelector, Sets.newLinkedHashSet());
		    bField.set(this.targetSelector, Sets.newLinkedHashSet());
		    cField.set(this.goalSelector, Sets.newLinkedHashSet());
		    cField.set(this.targetSelector, Sets.newLinkedHashSet());
		    if(!ai.equals("무뇌아"))
		    {
			    this.goalSelector.a(0, new PathfinderGoalFloat(this));
			    this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
			    this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
			    this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
			    this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
			    if(!ai.equals("동물"))
			    {
			        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
				    if(ai.equals("선공"))
				    	this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true, true));
			    }
			    else
			        this.goalSelector.a(2, new PathfinderGoalPanic(this, 1.3D));
		    }
	    }
	    catch (Exception exc) {exc.printStackTrace();}
		return;
	}
	
	public static Object spawn(Location location, String ai)
	{
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final CreatureSkeleton customEntity = new CreatureSkeleton(mcWorld, ai);
		customEntity.setLocation(location.getX(), location.getY(),location.getZ(), location.getYaw(), location.getPitch());
		if(location.getWorld().getName().equals("Dungeon"))
			((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return customEntity.getBukkitEntity();
	}
}