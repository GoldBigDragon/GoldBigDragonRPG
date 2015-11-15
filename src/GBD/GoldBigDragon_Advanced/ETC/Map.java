package GBD.GoldBigDragon_Advanced.ETC;

import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import GBD.GoldBigDragon_Advanced.Main.Main;

public class Map
{
	public void onMap(MapInitializeEvent event)
	{
		if(Main.Mapping)
		{
			MapView MV = event.getMap();
			for(MapRenderer MR : MV.getRenderers())
				MV.removeRenderer(MR);
			MV.addRenderer(new MapList());
			return;
		}
	}
}
