package warp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import user.UserDataObject;

public class WarpChat
{
	public void teleportTypeChatting(AsyncPlayerChatEvent event)
	{
		UserDataObject u = new UserDataObject();
		Player player = event.getPlayer();
	    
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "NW"://NewWarp
			message = message.replace(".", "");
			new warp.WarpMain().CreateNewTeleportSpot(player, message);
			u.clearAll(player);
			return;
		}
		return;
	}
}