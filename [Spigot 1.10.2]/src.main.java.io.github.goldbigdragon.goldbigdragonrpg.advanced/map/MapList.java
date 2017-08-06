package map;

import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import main.Main_ServerOption;
import user.UserData_Object;
import util.YamlLoader;

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

		  	YamlLoader mapYaml = new YamlLoader();
			mapYaml.getConfig("MapImageURL.yml");
			String Name = new UserData_Object().getString(player, (byte)1);
			URL = mapYaml.getString(Name+".URL");
			Xcenter = mapYaml.getInt(Name+".Xcenter");
			Ycenter = mapYaml.getInt(Name+".Ycenter");
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
