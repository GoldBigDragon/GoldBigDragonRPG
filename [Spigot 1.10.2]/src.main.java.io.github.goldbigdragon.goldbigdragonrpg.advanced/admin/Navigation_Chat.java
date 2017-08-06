package admin;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_Chat;
import util.YamlLoader;



public class Navigation_Chat extends Util_Chat
{
	public void NaviTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();

	  	YamlLoader navigationYaml = new YamlLoader();
	  	navigationYaml.getConfig("Navigation/NavigationList.yml");

	    
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)0))
		{
		case "NN"://NewNavigation
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			long UTC = new util.ETC().getNowUTC();
			navigationYaml.set(UTC+".Name", event.getMessage());
			navigationYaml.set(UTC+".world", player.getLocation().getWorld().getName());
			navigationYaml.set(UTC+".x", (int)player.getLocation().getX());
			navigationYaml.set(UTC+".y", (short)player.getLocation().getY());
			navigationYaml.set(UTC+".z", (int)player.getLocation().getZ());
			navigationYaml.set(UTC+".time", -1);
			navigationYaml.set(UTC+".sensitive", 5);
			navigationYaml.set(UTC+".onlyOPuse", true);
			navigationYaml.set(UTC+".ShowArrow", 0);
			navigationYaml.saveConfig();
			u.clearAll(player);
			new admin.Navigation_GUI().NavigationOptionGUI(player,UTC+"");
			return;
		case "CNN"://ChangeNavigationName이름 변경
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			navigationYaml.set(u.getString(player, (byte)1)+".Name", event.getMessage());
			navigationYaml.saveConfig();
			new admin.Navigation_GUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
			u.clearAll(player);
			return;
		case "CNT"://ChangeNavigationTimer지속 시간
			if(isIntMinMax(message, player, -1, 3600))
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				navigationYaml.set(u.getString(player, (byte)1)+".time", Integer.parseInt(message));
				navigationYaml.saveConfig();
				new admin.Navigation_GUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
			return;
		case "CNS"://ChangeNavigationSensitive도착 반경
			if(isIntMinMax(message, player, 1, 1000))
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				navigationYaml.set(u.getString(player, (byte)1)+".sensitive", Integer.parseInt(message));
				navigationYaml.saveConfig();
				new admin.Navigation_GUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
			return;
		case "CNA"://ChangeNavigationArrow네비 타입
			if(isIntMinMax(message, player, 0, 10))
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				navigationYaml.set(u.getString(player, (byte)1)+".ShowArrow", Integer.parseInt(message));
				navigationYaml.saveConfig();
				new admin.Navigation_GUI().NavigationOptionGUI(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
			return;
		}
		return;
	}
	
}
