package GoldBigDragon_RPG.Effect;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

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
	
	public void PPL(Player player,Location loc, org.bukkit.Effect effect, int Direction)
	{
		player.playEffect(loc, effect, Direction);
	}
	
	//relativeLocation - Player Location
	public void RLPL(Player player,double relativeX,double relativeY,double relativeZ, org.bukkit.Effect effect, int Direction)
	{
		player.playEffect(player.getLocation().add(relativeX, relativeY, relativeZ), effect, Direction);
	}
	//RelativeLocation - Player Location Rotate
	public void RLPLR(Player player,double relativeX,double relativeY,double relativeZ, org.bukkit.Effect effect, int Direction, int Rotate)
	{
		switch(Rotate)
		{
		case 0://0도 회전(있는 그대로)
			player.playEffect(player.getLocation().add(relativeX, relativeY, relativeZ), effect, Direction);
			return;
		case 1://90도 회전(시계 방향으로 90도 회전)
			player.playEffect(player.getLocation().add(relativeZ, relativeY, relativeX), effect, Direction);
			return;
		case 2://180도 회전(시계 방향으로 180도 회전)
			player.playEffect(player.getLocation().add(relativeX, relativeY, -1*relativeZ), effect, Direction);
			return;
		case 3://270도 회전(시계 방향으로 270도 회전)
			player.playEffect(player.getLocation().add(-1*relativeZ, relativeY, relativeX), effect, Direction);
			return;
		case 4://대각선 형태의 파티클일 경우 - 360도 회전(시계 방향으로 360도 회전)
			player.playEffect(player.getLocation().add(-1*relativeZ, relativeY, -1*relativeX), effect, Direction);
			return;
		}
	}

	//RelativeLocation - Player Location Reflect
	public void RLPLRR(Player player,double relativeX,double relativeY,double relativeZ, org.bukkit.Effect effect, int Direction, int Reflect)
	{
		switch(Reflect)
		{
		case 0://비대칭
			player.playEffect(player.getLocation().add(-1*relativeX, relativeY, relativeZ), effect, Direction);
			return;
		case 1://좌우 대칭
			player.playEffect(player.getLocation().add(relativeZ, relativeY, relativeX), effect, Direction);
			return;
		case 2://전후 대칭
			player.playEffect(player.getLocation().add(relativeX, relativeY, -1*relativeZ), effect, Direction);
			return;
		case 3://전후 대칭
			player.playEffect(player.getLocation().add(-1*relativeZ, relativeY, -1*relativeX), effect, Direction);
			return;
		}
	}
}
