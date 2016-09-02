package GoldBigDragon_RPG.Monster.AI;

import net.minecraft.server.v1_8_R3.EntityBat;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftBat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Bat;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.util.Vector;

public class AI_Spector extends EntityBat
{

	public AI_Spector(World world) {
		super(world);
	}

	@Override
	public void g(double x, double y, double z) {
		Vector vector = this.getBukkitEntity().getVelocity();
		super.g(vector.getX(), vector.getY(), vector.getZ());
	}

	public static Bat spawn(Location location) {
		World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
		final AI_Spector customEntity = new AI_Spector(
				mcWorld);
		customEntity.setLocation(location.getX(), location.getY(),
				location.getZ(), location.getYaw(), location.getPitch());
		if(location.getWorld().getName().compareTo("Dungeon")==0)
			((CraftLivingEntity) customEntity.getBukkitEntity()).setRemoveWhenFarAway(false);
		mcWorld.addEntity(customEntity, SpawnReason.CUSTOM);
		return (CraftBat) customEntity.getBukkitEntity();
	}
}