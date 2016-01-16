package GoldBigDragon_RPG.Effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Sound
{
    //해당 플레이어에게 소리를 내게하는 메소드//
	public void SP(Player player, org.bukkit.Sound sound, float volume,float pitch)
	{
    	if(Bukkit.getOfflinePlayer(player.getName()).isOnline() == true)
		player.playSound(player.getLocation(), sound ,volume, pitch);
	}
	
    //해당 플레이어에게 해당 위치에서 소리를 내게하는 메소드//
	public void SPL(Player player, Location loc, org.bukkit.Sound sound, float volume,float pitch)
	{
    	if(Bukkit.getOfflinePlayer(player.getName()).isOnline() == true)
		player.playSound(loc, sound ,volume, pitch);
	}
	
	//해당 위치에 사운드를 내게하는 메소드//
	public void SL(Location loc, org.bukkit.Sound sound, float volume,float pitch)
	{
		World world = loc.getWorld();
		world.playSound(loc, sound ,volume, pitch);
	}
}
