package admin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import user.UserDataObject;

public class WorldCreateChat 
{
	public void worldCreaterTypeChatting(AsyncPlayerChatEvent event)
	{
		event.setCancelled(true);
		Player player = event.getPlayer();
		UserDataObject u = new UserDataObject();
		String type = u.getString(player, 3);
		String biome = u.getString(player, 4);
		boolean createStructure = u.getBoolean(player, (byte)0);
		String message = ChatColor.stripColor(event.getMessage()).replace(" ", "_");
		u.clearAll(player);
		new WorldCreate().worldCreate(player, message, type, biome, createStructure);
		return;
	}
}
