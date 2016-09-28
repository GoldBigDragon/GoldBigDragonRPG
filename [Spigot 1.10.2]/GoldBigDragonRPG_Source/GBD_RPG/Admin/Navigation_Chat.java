package GBD_RPG.Admin;

import GBD_RPG.Util.Util_Chat;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Navigation_Chat extends Util_Chat
{
	public void NaviTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();

	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

	    GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)0))
		{
		case "NN"://NewNavigation
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			long UTC = new GBD_RPG.Util.ETC().getNowUTC();
			NavigationConfig.set(UTC+".Name", event.getMessage());
			NavigationConfig.set(UTC+".world", player.getLocation().getWorld().getName());
			NavigationConfig.set(UTC+".x", (int)player.getLocation().getX());
			NavigationConfig.set(UTC+".y", (short)player.getLocation().getY());
			NavigationConfig.set(UTC+".z", (int)player.getLocation().getZ());
			NavigationConfig.set(UTC+".time", -1);
			NavigationConfig.set(UTC+".sensitive", 5);
			NavigationConfig.set(UTC+".onlyOPuse", true);
			NavigationConfig.set(UTC+".ShowArrow", 0);
			NavigationConfig.saveConfig();
			u.clearAll(player);
			new GBD_RPG.Admin.Navigation_GUI().NavigationOptionGUI(player,UTC+"");
			return;
		case "CNN"://ChangeNavigationName이름 변경
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			NavigationConfig.set(u.getString(player, (byte)1)+".Name", event.getMessage());
			NavigationConfig.saveConfig();
			new GBD_RPG.Admin.Navigation_GUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
			u.clearAll(player);
			return;
		case "CNT"://ChangeNavigationTimer지속 시간
			if(isIntMinMax(message, player, -1, 3600))
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				NavigationConfig.set(u.getString(player, (byte)1)+".time", Integer.parseInt(message));
				NavigationConfig.saveConfig();
				new GBD_RPG.Admin.Navigation_GUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
			return;
		case "CNS"://ChangeNavigationSensitive도착 반경
			if(isIntMinMax(message, player, 1, 1000))
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				NavigationConfig.set(u.getString(player, (byte)1)+".sensitive", Integer.parseInt(message));
				NavigationConfig.saveConfig();
				new GBD_RPG.Admin.Navigation_GUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
			return;
		case "CNA"://ChangeNavigationArrow네비 타입
			if(isIntMinMax(message, player, 0, 10))
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				NavigationConfig.set(u.getString(player, (byte)1)+".ShowArrow", Integer.parseInt(message));
				NavigationConfig.saveConfig();
				new GBD_RPG.Admin.Navigation_GUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
			return;
		}
		return;
	}
	
}
