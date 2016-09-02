package GoldBigDragon_RPG.Effect;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Potion
{
	public void givePotionEffect(Player player, PotionEffectType type, int duration, int amplifier)
	{
		PotionEffect pe = new org.bukkit.potion.PotionEffect(type, duration*20, amplifier, false, false);
		player.addPotionEffect(pe);
	}
	
	public PotionEffect getPotionEffect(PotionEffectType type, int duration, int amplifier)
	{
		return new org.bukkit.potion.PotionEffect(type, duration*20, amplifier, false, false);
	}
}
