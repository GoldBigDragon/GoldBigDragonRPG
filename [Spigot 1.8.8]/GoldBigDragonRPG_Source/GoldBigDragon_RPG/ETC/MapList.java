package GoldBigDragon_RPG.ETC;

import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;

public class MapList extends MapRenderer
{
	@Override
	public void render(MapView MV, MapCanvas MC, Player player)
	{
		if(ServerOption.Mapping==true)
		{
			ServerOption.Mapping = false;
			String URL = "null";
			int Xcenter = 0;
			int Ycenter  = 0;

		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager MapConfig=YC.getNewConfig("MapImageURL.yml");
			if(YC.isExit("MapImageURL.yml") == false)
				new GoldBigDragon_RPG.Config.configConfig().CreateMapImageConfig(YC);
			String Name = new Object_UserData().getString(player, (byte)1);
			URL = MapConfig.getString(Name+".URL");
			Xcenter = MapConfig.getInt(Name+".Xcenter");
			Ycenter = MapConfig.getInt(Name+".Ycenter");
			new Object_UserData().clearAll(player);
			if(URL=="null")
				return;
			else
			{
				try
				{
					MC.drawImage(Xcenter, Ycenter, ImageIO.read(new URL(URL)));
					return;
				}
				catch (MalformedURLException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
