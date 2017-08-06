package warp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import user.UserData_Object;

public class Warp_Chat
{
	public void TeleportTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();
	    
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "NW"://NewWarp
			message = message.replace(".", "");
			new warp.Warp_Main().CreateNewTeleportSpot(player, message);
			u.clearAll(player);
			return;
		}
		return;
	}
}
