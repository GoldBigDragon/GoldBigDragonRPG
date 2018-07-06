package event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import effect.SoundEffect;
import util.YamlLoader;



public class EventBlockPlace implements Listener
{
	@EventHandler
	public void blockPlace(BlockPlaceEvent event)
	{
		Player player = event.getPlayer();
		if(event.getItemInHand().hasItemMeta() && ! player.isOp())
		{
		  	YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			if( ! configYaml.getBoolean("Server.CustomBlockPlace"))
				event.setCancelled(true);
			return;
		}
		if(event.getBlock().getLocation().getWorld().getName().equals("Dungeon"))
		{
			if(event.getBlock().getType() != Material.TORCH)
				event.setCancelled(true);
			return;
		}

		area.AreaAPI area = new area.AreaAPI();
		String[] areaList = area.getAreaName(event.getBlock());
		if(areaList != null)
		if( ! area.getAreaOption(areaList[0], (char) 5) && ! player.isOp())
		{
			event.setCancelled(true);
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[SYSTEM] : §e"+ areaList[1] + "§c 지역 에서는 블록 설치가 불가능합니다!");
			return;
		}
		if(! player.isOp())
			EXPexceptionBlock(event.getBlock().getTypeId(),event.getBlock().getLocation());
		return;
	}

	public void EXPexceptionBlock(int id,Location loc)
	{
		if((id >= 14&&id <= 17)||id==21||id==56||id==129||id==73||id==153||id==86||id==83||id==338||id==103||id==199||id==200)
		{
		  	YamlLoader exceptionBlockYaml = new YamlLoader();
			exceptionBlockYaml.getConfig("EXPexceptionBlock.yml");
			String location = ((int)loc.getX()+"_"+(int)loc.getY()+"_"+(int)loc.getZ());
			exceptionBlockYaml.createSection(loc.getWorld().getName()+"."+id+"."+location);
			exceptionBlockYaml.saveConfig();
		}
		return;
	}

}