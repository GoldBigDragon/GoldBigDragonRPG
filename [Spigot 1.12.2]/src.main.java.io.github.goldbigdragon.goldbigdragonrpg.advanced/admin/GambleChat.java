package admin;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserDataObject;
import util.YamlLoader;



public class GambleChat
{
	public void gambleChatting(PlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
		Player player = event.getPlayer();

	  	YamlLoader gambleYML = new YamlLoader();
	  	gambleYML.getConfig("Item/GamblePresent.yml");
	    
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage().replace(".",""));
	    
	    //New Package
	    if(u.getString(player, (byte)0).equals("NP"))
		{
			if(gambleYML.contains(message))
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c[도박] : 해당 이름의 상품은 이미 존재합니다!");
				return;
			}
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			gambleYML.set(message+".Grade", "§f[일반]");
			gambleYML.createSection(message+".Present");
			gambleYML.saveConfig();
			u.clearAll(player);
			new admin.GambleGui().gamblePresentGui(player, (short)0, (byte)0, (short)-1, null);
		}
	}

}
