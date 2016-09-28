package GBD_RPG.ServerTick;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

public class ServerTask_Effect
{
	public void PlaySoundEffect(ServerTick_Object STSO)
	{
		if(STSO.getCount()<STSO.getMaxCount())
		{
			Location loc = new Location(Bukkit.getServer().getWorld(STSO.getString((byte)1)),STSO.getInt((byte)0),STSO.getInt((byte)1),STSO.getInt((byte)2));
			switch(Integer.parseInt(STSO.getString((byte)0).charAt(STSO.getCount())+""))
			{
			case 0:
				new GBD_RPG.Effect.Effect_Sound().SL(loc, Sound.ENTITY_IRONGOLEM_ATTACK, 2.0F, 0.5F);
				break;
			case 1:
				new GBD_RPG.Effect.Effect_Sound().SL(loc, Sound.ENTITY_IRONGOLEM_HURT, 2.0F, 0.5F);
				break;
			}
			STSO.setCount(STSO.getCount()+1);
			STSO.copyThisScheduleObject(ServerTick_Main.nowUTC+(STSO.getInt((byte)5)*100));
		}
	}
}
