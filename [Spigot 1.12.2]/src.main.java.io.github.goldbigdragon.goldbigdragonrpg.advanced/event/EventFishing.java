package event;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftItem;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import util.YamlLoader;




public class EventFishing implements Listener
{
	@EventHandler
	public void PlayerFishing(PlayerFishEvent event)
	{
		if(event.getState() == State.CAUGHT_FISH)
		{
			area.AreaAPI A = new area.AreaAPI();
			if(A.getAreaName(event.getPlayer()) != null)
			{
				String Area = A.getAreaName(event.getPlayer())[0];
				if(event.getCaught().getType() == EntityType.DROPPED_ITEM)
				{
					CraftItem fish = (CraftItem) event.getCaught();
					
					util.NumericUtil etc = new util.NumericUtil();
				  	YamlLoader areaYaml = new YamlLoader();
					areaYaml.getConfig("Area/AreaList.yml");
					byte randomnum = (byte) etc.RandomNum(1, 100);
					byte size = 0;
					ItemStack item = fish.getItemStack();
					if(randomnum <= 54)
					{
						size = (byte) areaYaml.getConfigurationSection(Area+".Fishing.54").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = areaYaml.getItemStack(Area+".Fishing.54."+randomnum);
						}
					}
					else if(randomnum <= 84)
					{
						size = (byte) areaYaml.getConfigurationSection(Area+".Fishing.30").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = areaYaml.getItemStack(Area+".Fishing.30."+randomnum);
						}
					}
					else if(randomnum <= 94)
					{
						size = (byte) areaYaml.getConfigurationSection(Area+".Fishing.10").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = areaYaml.getItemStack(Area+".Fishing.10."+randomnum);
						}
					}
					else if(randomnum <= 99)
					{
						size = (byte) areaYaml.getConfigurationSection(Area+".Fishing.5").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = areaYaml.getItemStack(Area+".Fishing.5."+randomnum);
						}
					}
					else
					{
						size = (byte) areaYaml.getConfigurationSection(Area+".Fishing.1").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = areaYaml.getItemStack(Area+".Fishing.1."+randomnum);
						}
					}
					fish.setItemStack(item);
				}
			}
		}
		return;
	}
}
