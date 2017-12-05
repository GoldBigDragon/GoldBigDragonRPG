package admin;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserDataObject;
import util.UtilChat;
import util.YamlLoader;

public class NavigationChat extends UtilChat
{
	public void naviTypeChatting(PlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
		Player player = event.getPlayer();

	  	YamlLoader navigationYaml = new YamlLoader();
	  	navigationYaml.getConfig("Navigation/NavigationList.yml");

	    
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
	    String type = u.getString(player, (byte)0);
	    //NewNavigation
	    if(type.equals("NN"))
	    {
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
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
			new admin.NavigationGui().navigationOptionGui(player,UTC+"");
	    }
	    //ChangeNavigationName이름 변경
	    else if(type.equals("CNN"))
	    {
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			navigationYaml.set(u.getString(player, (byte)1)+".Name", event.getMessage());
			navigationYaml.saveConfig();
			new admin.NavigationGui().navigationOptionGui(player,u.getString(player, (byte)1));
			u.clearAll(player);
	    }
	    //ChangeNavigationTimer지속 시간
	    else if(type.equals("CNT"))
	    {
	    	if(isIntMinMax(message, player, -1, 3600))
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				navigationYaml.set(u.getString(player, (byte)1)+".time", Integer.parseInt(message));
				navigationYaml.saveConfig();
				new admin.NavigationGui().navigationOptionGui(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
	    }
	    //ChangeNavigationSensitive도착 반경
	    else if(type.equals("CNS"))
	    {
			if(isIntMinMax(message, player, 1, 1000))
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				navigationYaml.set(u.getString(player, (byte)1)+".sensitive", Integer.parseInt(message));
				navigationYaml.saveConfig();
				new admin.NavigationGui().navigationOptionGui(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
	    }
	    //ChangeNavigationArrow네비 타입
	    else if(type.equals("CNA"))
	    {
	    	if(isIntMinMax(message, player, 0, 10))
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				navigationYaml.set(u.getString(player, (byte)1)+".ShowArrow", Integer.parseInt(message));
				navigationYaml.saveConfig();
				new admin.NavigationGui().navigationOptionGui(player,u.getString(player, (byte)1));
				u.clearAll(player);
			}
	    }
	}
}
