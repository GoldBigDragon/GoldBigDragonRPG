package servertick;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

import effect.SoundEffect;

public class ServerTaskEffect
{
	public void PlaySoundEffect(ServerTickObject STSO)
	{
		if(STSO.getCount()<STSO.getMaxCount())
		{
			Location loc = new Location(Bukkit.getServer().getWorld(STSO.getString((byte)1)),STSO.getInt((byte)0),STSO.getInt((byte)1),STSO.getInt((byte)2));
			switch(Integer.parseInt(STSO.getString((byte)0).charAt(STSO.getCount())+""))
			{
			case 0:
				SoundEffect.SL(loc, Sound.ENTITY_IRONGOLEM_ATTACK, 2.0F, 0.5F);
				break;
			case 1:
				SoundEffect.SL(loc, Sound.ENTITY_IRONGOLEM_HURT, 2.0F, 0.5F);
				break;
			}
			STSO.setCount(STSO.getCount()+1);
			STSO.copyThisScheduleObject(ServerTickMain.nowUTC+(STSO.getInt((byte)5)*100));
		}
	}
}
