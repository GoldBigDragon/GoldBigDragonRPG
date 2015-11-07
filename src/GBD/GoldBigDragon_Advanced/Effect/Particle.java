package GBD.GoldBigDragon_Advanced.Effect;

import org.bukkit.Location;
import org.bukkit.World;

public class Particle
{
	public void PL(Location loc, org.bukkit.Effect effect, int Direction)
	{
		World world = loc.getWorld();
		world.playEffect(loc.add(0.5, 0.5, 0.5), effect, Direction);
	}
	public void PLC(Location loc, org.bukkit.Effect effect, int Direction)
	{
		World world = loc.getWorld();
		world.playEffect(loc, effect, Direction);
	}
}
