package GBD.GoldBigDragon_Advanced.Main;

import org.bukkit.ChatColor;

import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class ServerOption
{
	public static String STR = "Ã¼·Â";
	public static String DEX = "¼Ø¾¾";
	public static String INT = "Áö·Â";
	public static String WILL = "ÀÇÁö";
	public static String LUK = "Çà¿î";
	public static String Money = ChatColor.YELLOW+""+ChatColor.BOLD+"Gold";

	public void Initialize()
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager Config =GUI_YC.getNewConfig("config.yml");
		if(Config.contains("Server.STR"))
			STR = Config.getString("Server.STR");
		if(Config.contains("Server.DEX"))
			DEX = Config.getString("Server.DEX");
		if(Config.contains("Server.INT"))
			INT = Config.getString("Server.INT");
		if(Config.contains("Server.WILL"))
			WILL = Config.getString("Server.WILL");
		if(Config.contains("Server.LUK"))
			LUK = Config.getString("Server.LUK");
		if(Config.contains("Server.MoneyName"))
			Money = Config.getString("Server.MoneyName");
		return;
	}
}
