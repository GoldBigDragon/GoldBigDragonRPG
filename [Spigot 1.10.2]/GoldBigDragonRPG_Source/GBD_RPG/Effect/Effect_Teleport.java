package GBD_RPG.Effect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Effect_Teleport
{
	public void TeleportPlayer (Player player, String world, double X, float Y, double Z, float NEWS, float eye)
	{
		Location loc = new Location(Bukkit.getServer().getWorld(world), X, Y,Z, NEWS, eye);
		player.teleport(loc);
		new GBD_RPG.Effect.Effect_Potion().givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
		new GBD_RPG.Effect.Effect_Sound().SL(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
		return;
	}
}
