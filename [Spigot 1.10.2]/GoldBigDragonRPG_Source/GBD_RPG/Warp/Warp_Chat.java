package GBD_RPG.Warp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;

public class Warp_Chat
{
	public void TeleportTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		Player player = event.getPlayer();
	    GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
	    event.setCancelled(true);
	    String message = ChatColor.stripColor(event.getMessage());
		switch(u.getString(player, (byte)1))
		{
		case "NW"://NewWarp
			message = message.replace(".", "");
			new GBD_RPG.Warp.Warp_Main().CreateNewTeleportSpot(player, message);
			u.clearAll(player);
			return;
		}
		return;
	}
}
