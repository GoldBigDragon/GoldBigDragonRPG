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
	public void WorldCreaterTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		event.setCancelled(true);
		Player player = event.getPlayer();
		
		String Message = ChatColor.stripColor(event.getMessage());
		SoundEffect.SP(player, Sound.BLOCK_ANVIL_USE,1.0F, 0.8F);
		player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[岿靛 积己] : 岿靛 积己 吝...");
		WorldType TYPE = WorldType.FLAT;
		
		switch(u.getString(player, (byte)3))
		{
		case "NORMAL":
			TYPE = WorldType.NORMAL;
			break;
		case "FLAT":
			TYPE = WorldType.FLAT;
			break;
		case "LARGE_BIOMES":
			TYPE = WorldType.LARGE_BIOMES;
			break;
		}
		Message = Message.replace(" ", "_");
		WorldCreator world = new WorldCreator(Message);
		world.type(TYPE);
		world.generateStructures();
		Bukkit.createWorld(world);
		u.clearAll(player);
	  	YamlLoader worldYaml = new YamlLoader();
		worldYaml.getConfig("WorldList.yml");
		worldYaml.createSection(Message);
		worldYaml.saveConfig();
		String[] worldname = worldYaml.getKeys().toArray(new String[0]);
		for(int count = 0; count < worldYaml.getKeys().size();count++)
			if(Bukkit.getWorld(worldname[count]) == null)
				WorldCreator.name(worldname[count]).createWorld();
    	SoundEffect.SP(player, Sound.ENTITY_WOLF_AMBIENT,1.0F, 0.8F);
		player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[岿靛 积己] : 岿靛 积己 己傍!");
		return;
	}
	
}
