package GoldBigDragon_RPG.ServerTick;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

public class ServerTask_Effect
{
	public void PlaySoundEffect(ServerTickScheduleObject STSO)
	{
		if(STSO.getCount()<STSO.getMaxCount())
		{
			Location loc = new Location(Bukkit.getServer().getWorld(STSO.getString((byte)1)),STSO.getInt((byte)0),STSO.getInt((byte)1),STSO.getInt((byte)2));
			switch(Integer.parseInt(STSO.getString((byte)0).charAt(STSO.getCount())+""))
			{
			case 0:
				new GoldBigDragon_RPG.Effect.Sound().SL(loc, Sound.IRONGOLEM_THROW, 2.0F, 0.5F);
				break;
			case 1:
				new GoldBigDragon_RPG.Effect.Sound().SL(loc, Sound.IRONGOLEM_HIT, 2.0F, 0.5F);
				break;
			}
			STSO.setCount(STSO.getCount()+1);
			STSO.copyThisScheduleObject(ServerTickMain.nowUTC+(STSO.getInt((byte)5)*100));
		}
	}
}
