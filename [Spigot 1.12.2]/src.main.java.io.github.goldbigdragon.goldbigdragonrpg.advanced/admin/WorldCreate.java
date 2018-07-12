package admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.World.Environment;

import effect.SoundEffect;
import util.YamlLoader;

public class WorldCreate {

	public void worldCreate(Player player, String worldName, String worldType, String worldBiome, boolean createStructure)
	{
		SoundEffect.playSound(player, Sound.BLOCK_ANVIL_USE,1.0F, 0.8F);
		player.sendMessage("∽6∽l[岿靛 积己] : 岿靛 积己 吝...");
		WorldCreator world = new WorldCreator(worldName);

		if(worldBiome.equals("NETHER"))
			world.environment(Environment.NETHER);
		else if(worldBiome.equals("THE_END"))
			world.environment(Environment.THE_END);
		else
			world.environment(Environment.NORMAL);
		
		if(worldType.equals("FLAT"))
			world.type(WorldType.FLAT);
		else if(worldType.equals("LARGE_BIOMES"))
			world.type(WorldType.LARGE_BIOMES);
		else
			world.type(WorldType.NORMAL);

		world.generateStructures(createStructure);
		try{
			Bukkit.createWorld(world);
		}
		catch(Exception e)
		{
		}
		
	  	YamlLoader worldYaml = new YamlLoader();
		worldYaml.getConfig("WorldList.yml");
		worldYaml.createSection(worldName);
		worldYaml.saveConfig();
		String[] worldname = worldYaml.getKeys().toArray(new String[0]);
		for(int count = 0; count < worldYaml.getKeys().size();count++)
			if(Bukkit.getWorld(worldname[count]) == null)
				WorldCreator.name(worldname[count]).createWorld();
    	SoundEffect.playSound(player, Sound.ENTITY_WOLF_AMBIENT,1.0F, 0.8F);
		player.sendMessage("∽6∽l[岿靛 积己] : 岿靛 积己 己傍!");
	}
}
