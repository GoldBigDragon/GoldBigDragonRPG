package GBD_RPG.Main_Event;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftItem;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Main_Fishing implements Listener
{
	@EventHandler
	public void PlayerFishing(PlayerFishEvent event)
	{
		if(event.getState() == State.CAUGHT_FISH)
		{
			GBD_RPG.Area.Area_Main A = new GBD_RPG.Area.Area_Main();
			if(A.getAreaName(event.getPlayer()) != null)
			{
				String Area = A.getAreaName(event.getPlayer())[0];
				if(event.getCaught().getType() == EntityType.DROPPED_ITEM)
				{
					CraftItem fish = (CraftItem) event.getCaught();
					
					GBD_RPG.Util.Util_Number etc = new GBD_RPG.Util.Util_Number();
				  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");
					byte randomnum = (byte) etc.RandomNum(1, 100);
					byte size = 0;
					ItemStack item = fish.getItemStack();
					if(randomnum <= 54)
					{
						size = (byte) AreaConfig.getConfigurationSection(Area+".Fishing.54").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = AreaConfig.getItemStack(Area+".Fishing.54."+randomnum);
						}
					}
					else if(randomnum <= 84)
					{
						size = (byte) AreaConfig.getConfigurationSection(Area+".Fishing.30").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = AreaConfig.getItemStack(Area+".Fishing.30."+randomnum);
						}
					}
					else if(randomnum <= 94)
					{
						size = (byte) AreaConfig.getConfigurationSection(Area+".Fishing.10").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = AreaConfig.getItemStack(Area+".Fishing.10."+randomnum);
						}
					}
					else if(randomnum <= 99)
					{
						size = (byte) AreaConfig.getConfigurationSection(Area+".Fishing.5").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = AreaConfig.getItemStack(Area+".Fishing.5."+randomnum);
						}
					}
					else
					{
						size = (byte) AreaConfig.getConfigurationSection(Area+".Fishing.1").getKeys(false).size();
						if(size !=0)
						{
							randomnum = (byte) (etc.RandomNum(1, size)-1);
							item = AreaConfig.getItemStack(Area+".Fishing.1."+randomnum);
						}
					}
					fish.setItemStack(item);
				}
			}
		}
		return;
	}
}
