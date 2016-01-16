package GoldBigDragon_RPG.Main;

import org.bukkit.ChatColor;
import org.bukkit.material.MaterialData;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class ServerOption
{
	public static String STR = "체력";
	public static String DEX = "솜씨";
	public static String INT = "지력";
	public static String WILL = "의지";
	public static String LUK = "행운";
	public static String Money = ChatColor.YELLOW+""+ChatColor.BOLD+"Gold";

	public static String STR_Lore = "%enter%"+ChatColor.GRAY+" "+GoldBigDragon_RPG.Main.ServerOption.STR+"은 플레이어의%enter%"+ChatColor.GRAY + " 물리적 공격력을%enter%"+ChatColor.GRAY + " 상승시켜 줍니다.%enter%";
	public static String DEX_Lore = "%enter%"+ChatColor.GRAY+" "+GoldBigDragon_RPG.Main.ServerOption.DEX+"는 플레이어의%enter%"+ChatColor.GRAY + " 원거리 공격력을%enter%"+ChatColor.GRAY + " 상승시켜 줍니다.%enter%";
	public static String INT_Lore = "%enter%"+ChatColor.GRAY+" "+GoldBigDragon_RPG.Main.ServerOption.INT+"은 플레이어가%enter%"+ChatColor.GRAY + " 사용하는 스킬 중%enter%"+ChatColor.GRAY+" "+GoldBigDragon_RPG.Main.ServerOption.INT+" 영향을 받는%enter%"+ChatColor.GRAY+" 스킬 공격력을%enter%"+ChatColor.GRAY + " 상승시켜 줍니다.%enter%";
	public static String WILL_Lore = "%enter%"+ChatColor.GRAY+" "+GoldBigDragon_RPG.Main.ServerOption.WILL+"는 플레이어의%enter%"+ChatColor.GRAY + " 크리티컬 및 스킬 중%enter%"+ChatColor.GRAY+" "+GoldBigDragon_RPG.Main.ServerOption.WILL+" 영향을 받는%enter%"+ChatColor.GRAY + " 스킬 공격력을%enter%"+ChatColor.GRAY+" 상승시켜 줍니다.%enter%";
	public static String LUK_Lore = "%enter%"+ChatColor.GRAY+" "+GoldBigDragon_RPG.Main.ServerOption.LUK+"은 플레이어에게%enter%"+ChatColor.GRAY+" 뜻하지 않은 일이 일어날%enter%"+ChatColor.GRAY + " 확률을 증가시킵니다.%enter%";

	public static boolean MoneySystem = false;
	public static int Money1ID = 348;
	public static int Money1DATA = 0;
	public static int Money2ID = 371;
	public static int Money2DATA = 0;
	public static int Money3ID = 147;
	public static int Money3DATA = 0;
	public static int Money4ID = 266;
	public static int Money4DATA = 0;
	public static int Money5ID = 41;
	public static int Money5DATA = 0;
	public static int Money6ID = 41;
	public static int Money6DATA = 0;
	
	public void Initialize()
	{
		YamlController GUI_YC = GoldBigDragon_RPG.Main.Main.YC_2;
		YamlManager Config =GUI_YC.getNewConfig("config.yml");
		if(Config.contains("Server.MabinogiMoneySystem"))
			MoneySystem = Config.getBoolean("Server.MabinogiMoneySystem");
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
		if(Config.contains("Server.STR_Lore"))
			STR_Lore = Config.getString("Server.STR_Lore");
		if(Config.contains("Server.DEX_Lore"))
			DEX_Lore = Config.getString("Server.DEX_Lore");
		if(Config.contains("Server.INT_Lore"))
			INT_Lore = Config.getString("Server.INT_Lore");
		if(Config.contains("Server.WILL_Lore"))
			WILL_Lore = Config.getString("Server.WILL_Lore");
		if(Config.contains("Server.LUK_Lore"))
			LUK_Lore = Config.getString("Server.LUK_Lore");
		if(Config.contains("Server.Money.1.ID"))
			Money1ID = Config.getInt("Server.Money.1.ID");
		if(Config.contains("Server.Money.2.ID"))
			Money2ID = Config.getInt("Server.Money.2.ID");
		if(Config.contains("Server.Money.3.ID"))
			Money3ID = Config.getInt("Server.Money.3.ID");
		if(Config.contains("Server.Money.4.ID"))
			Money4ID = Config.getInt("Server.Money.4.ID");
		if(Config.contains("Server.Money.5.ID"))
			Money5ID = Config.getInt("Server.Money.5.ID");
		if(Config.contains("Server.Money.6.ID"))
			Money6ID = Config.getInt("Server.Money.6.ID");
		if(Config.contains("Server.Money.1.DATA"))
			Money1DATA = Config.getInt("Server.Money.1.DATA");
		if(Config.contains("Server.Money.2.DATA"))
			Money2DATA = Config.getInt("Server.Money.2.DATA");
		if(Config.contains("Server.Money.3.DATA"))
			Money3DATA = Config.getInt("Server.Money.3.DATA");
		if(Config.contains("Server.Money.4.DATA"))
			Money4DATA = Config.getInt("Server.Money.4.DATA");
		if(Config.contains("Server.Money.5.DATA"))
			Money5DATA = Config.getInt("Server.Money.5.DATA");
		if(Config.contains("Server.Money.6.DATA"))
			Money6DATA = Config.getInt("Server.Money.6.DATA");
		return;
	}
}
