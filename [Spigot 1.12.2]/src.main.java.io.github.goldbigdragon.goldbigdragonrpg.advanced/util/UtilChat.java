package util;

import org.bukkit.entity.Player;

import effect.SoundEffect;

public class UtilChat
{
	public boolean isIntMinMax(String message,Player player, int min, int max)
	{
		try
		{
			if(message.split(" ").length <= 1 && Integer.parseInt(message) >= min&& Integer.parseInt(message) <= max)
				return true;
			else
			{
				player.sendMessage("§c[SYSTEM] : 최소 §e"+min+"§c, 최대 §e"+max+"§c 이하의 숫자를 입력하세요!");
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
		}
		catch(NumberFormatException e)
		{
			player.sendMessage("§c[SYSTEM] : 정수 형태의 값(숫자)을 입력하세요. (§e"+min+"§c ~ §e"+max+"§c)");
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		}
		return false;
	}
	
	public byte askOX(String message,Player player)
	{
		if(message.split(" ").length <= 1)
		{
			if(message.equals("x")||message.equals("X")||message.equals("아니오"))
				return 0;
			else if(message.equals("o")||message.equals("O")||message.equals("네"))
				return 1;
			else
			{
				player.sendMessage("§c[SYSTEM] : O 혹은 X를 입력 해 주세요!");
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			}
			
		}
		else
		{
			player.sendMessage("§c[SYSTEM] : O 혹은 X를 입력 해 주세요!");
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		}
		return -1;
	}
}
