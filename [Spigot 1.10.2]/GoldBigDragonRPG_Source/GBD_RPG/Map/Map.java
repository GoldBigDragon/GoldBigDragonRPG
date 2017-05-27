package GBD_RPG.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import GBD_RPG.Main_Main.Main_ServerOption;;

public class Map implements Listener
{
	@EventHandler
	public void onMap(MapInitializeEvent event)
	{
		if(Main_ServerOption.Mapping)
		{
			MapView MV = event.getMap();
			for(MapRenderer MR : MV.getRenderers())
				MV.removeRenderer(MR);
			MV.addRenderer(new MapList());
			return;
		}
	}
}
