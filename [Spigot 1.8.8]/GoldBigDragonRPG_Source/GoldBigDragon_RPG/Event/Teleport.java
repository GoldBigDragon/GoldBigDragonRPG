package GoldBigDragon_RPG.Event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Teleport
{
	public GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	public GoldBigDragon_RPG.Effect.Potion p = new GoldBigDragon_RPG.Effect.Potion();
	
	public void TeleportPlayer (Player player, String world, double X, float Y, double Z, float NEWS, float eye)
	{
		Location loc = new Location(Bukkit.getServer().getWorld(world), X, Y,Z, NEWS, eye);
		player.teleport(loc);
		p.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
		s.SL(player.getLocation(), Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
		return;
	}
}
