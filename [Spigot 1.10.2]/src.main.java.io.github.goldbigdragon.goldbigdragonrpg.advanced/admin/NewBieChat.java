package admin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import user.UserDataObject;
import util.UtilChat;
import util.YamlLoader;



public class NewBieChat extends UtilChat
{
	public void newBieTypeChatting(PlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
		Player player = event.getPlayer();
	  	YamlLoader newbieYaml = new YamlLoader();
		event.setCancelled(true);
		String Message = ChatColor.stripColor(event.getMessage());
		String type = u.getString(player, (byte)1);
		//NewbieSupportMoney
		if(type.equals("NSM"))
		{
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				newbieYaml.getConfig("ETC/NewBie.yml");
				newbieYaml.set("SupportMoney", Integer.parseInt(Message));
				newbieYaml.saveConfig();
				admin.NewBieGui newbieGui = new admin.NewBieGui();
				newbieGui.newBieSupportItemGui(player);
				u.clearAll(player);
			}
		}
	}
}
