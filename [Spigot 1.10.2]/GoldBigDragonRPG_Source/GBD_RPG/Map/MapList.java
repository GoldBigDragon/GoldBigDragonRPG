package GBD_RPG.Map;

import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import GBD_RPG.Main_Main.Main_ServerOption;
import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;

public class MapList extends MapRenderer
{
	@Override
	public void render(MapView MV, MapCanvas MC, Player player)
	{
		if(Main_ServerOption.Mapping==true)
		{
			Main_ServerOption.Mapping = false;
			String URL = "null";
			int Xcenter = 0;
			int Ycenter  = 0;

		  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager MapConfig=YC.getNewConfig("MapImageURL.yml");
			String Name = new UserData_Object().getString(player, (byte)1);
			URL = MapConfig.getString(Name+".URL");
			Xcenter = MapConfig.getInt(Name+".Xcenter");
			Ycenter = MapConfig.getInt(Name+".Ycenter");
			new UserData_Object().clearAll(player);
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
