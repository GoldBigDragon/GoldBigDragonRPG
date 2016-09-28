package GBD_RPG.Admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class WorldCreate_Chat 
{
	public void WorldCreaterTypeChatting(PlayerChatEvent event)
	{
		UserData_Object u = new UserData_Object();
		event.setCancelled(true);
		Player player = event.getPlayer();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		String Message = ChatColor.stripColor(event.getMessage());
		s.SP(player, Sound.BLOCK_ANVIL_USE,1.0F, 0.8F);
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
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager WorldConfig =YC.getNewConfig("WorldList.yml");
		WorldConfig.createSection(Message);
		WorldConfig.saveConfig();
		Object[] worldname = WorldConfig.getKeys().toArray();
		for(short count = 0; count < WorldConfig.getKeys().size();count++)
			if(Bukkit.getWorld(worldname[count].toString()) == null)
				WorldCreator.name(worldname[count].toString()).createWorld();
    	s.SP(player, Sound.ENTITY_WOLF_AMBIENT,1.0F, 0.8F);
		player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"[岿靛 积己] : 岿靛 积己 己傍!");
		return;
	}
	
}
