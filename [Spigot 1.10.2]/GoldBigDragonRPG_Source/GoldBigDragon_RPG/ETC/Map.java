package GoldBigDragon_RPG.ETC;

import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import GoldBigDragon_RPG.Main.ServerOption;;

public class Map
{
	public void onMap(MapInitializeEvent event)
	{
		if(ServerOption.Mapping)
		{
			MapView MV = event.getMap();
			for(MapRenderer MR : MV.getRenderers())
				MV.removeRenderer(MR);
			MV.addRenderer(new MapList());
			return;
		}
	}
}
