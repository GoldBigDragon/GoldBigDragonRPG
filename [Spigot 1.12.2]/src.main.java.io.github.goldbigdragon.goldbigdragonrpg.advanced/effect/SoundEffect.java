package effect;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SoundEffect
{
    //해당 플레이어에게 소리를 내게하는 메소드//
	public static void playSound(Player player, org.bukkit.Sound sound, float volume,float pitch)
	{
    	if(player.isOnline())
    		player.playSound(player.getLocation(), sound ,volume, pitch);
	}
	
    //해당 플레이어에게 해당 위치에서 소리를 내게하는 메소드//
	public static void playSound(Player player, Location loc, org.bukkit.Sound sound, float volume,float pitch)
	{
    	if(player.isOnline())
    		player.playSound(loc, sound ,volume, pitch);
	}
	
	//해당 위치에 사운드를 내게하는 메소드//
	public static void playSoundLocation(Location loc, org.bukkit.Sound sound, float volume,float pitch)
	{
		World world = loc.getWorld();
		world.playSound(loc, sound ,volume, pitch);
	}

}
