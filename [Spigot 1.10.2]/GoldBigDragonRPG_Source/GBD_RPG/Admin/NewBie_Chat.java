package GBD_RPG.Admin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_Chat;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class NewBie_Chat extends Util_Chat
{
	public void NewBieTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		event.setCancelled(true);
		String Message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "NSM"://NewbieSupportMoney
			if(isIntMinMax(Message, player, 0, Integer.MAX_VALUE))
			{
				YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
				NewBieYM.set("SupportMoney", Integer.parseInt(Message));
				NewBieYM.saveConfig();
				GBD_RPG.Admin.NewBie_GUI NGUI = new GBD_RPG.Admin.NewBie_GUI();
				NGUI.NewBieSupportItemGUI(player);
				u.clearAll(player);
			}
			return;
		}
		return;
	}
}
