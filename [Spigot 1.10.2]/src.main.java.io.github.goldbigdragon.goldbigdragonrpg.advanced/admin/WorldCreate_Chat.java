package admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import effect.SoundEffect;
import user.UserData_Object;
import util.YamlLoader;

public class WorldCreate_Chat 
{
	public void worldCreaterTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		event.setCancelled(true);
		Player player = event.getPlayer();
		
		String message = ChatColor.stripColor(event.getMessage());
		SoundEffect.SP(player, Sound.BLOCK_ANVIL_USE,1.0F, 0.8F);
		player.sendMessage("§6§l[岿靛 积己] : 岿靛 积己 吝...");
		WorldType type = WorldType.FLAT;
		
		switch(u.getString(player, (byte)3))
		{
		case "NORMAL":
			type = WorldType.NORMAL;
			break;
		case "FLAT":
			type = WorldType.FLAT;
			break;
		case "LARGE_BIOMES":
			type = WorldType.LARGE_BIOMES;
			break;
		}
		message = message.replace(" ", "_");
		WorldCreator world = new WorldCreator(message);
		world.type(type);
		world.generateStructures();
		Bukkit.createWorld(world);
		u.clearAll(player);
	  	YamlLoader worldYaml = new YamlLoader();
		worldYaml.getConfig("WorldList.yml");
		worldYaml.createSection(message);
		worldYaml.saveConfig();
		String[] worldname = worldYaml.getKeys().toArray(new String[0]);
		for(int count = 0; count < worldYaml.getKeys().size();count++)
			if(Bukkit.getWorld(worldname[count]) == null)
				WorldCreator.name(worldname[count]).createWorld();
    	SoundEffect.SP(player, Sound.ENTITY_WOLF_AMBIENT,1.0F, 0.8F);
		player.sendMessage("§6§l[岿靛 积己] : 岿靛 积己 己傍!");
		return;
	}
	
}
