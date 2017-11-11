package admin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import user.UserData_Object;
import util.Util_Chat;
import util.YamlLoader;



public class NewBie_Chat extends Util_Chat
{
	public void newBieTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
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
				admin.NewBie_GUI newbieGui = new admin.NewBie_GUI();
				newbieGui.newBieSupportItemGui(player);
				u.clearAll(player);
			}
		}
	}
}
