package GBD_RPG.User;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import GBD_RPG.Main_Main.Main_Main;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class UserData_Object
{
	public void UserDataInit(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Name", player.getName());
		UserData.removeKey("Data");
		UserData.saveConfig();
		UserData.createSection("Data.Type");
		UserData.createSection("Data.Temp");
		UserData.createSection("Data.NPCuuid");
		for(byte count = 0; count < 9; count++)
			UserData.createSection("Data.String."+count);
		for(byte count = 0; count < 6; count++)
			UserData.set("Data.Int."+count,0);
		for(byte count = 0; count < 2; count++)
			UserData.set("Data.Boolean."+count,false);
		UserData.saveConfig();
	}
	
	public void setType(Player player, String TypeName)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Type",TypeName);
		UserData.saveConfig();
	}

	public String getType(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		String Type = UserData.getString("Data.Type");
		if(Type != null && Type.compareTo("MemorySection[path='Data.Type', root='YamlConfiguration']")!=0)
			return Type;
		else
			return null;
	}
	
	public String getString(Player player, byte StringNumber)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		String string = UserData.getString("Data.String."+StringNumber);
		if(string != null)
			return string;
		else
			return null;
	}
	
	public int getInt(Player player, byte IntNumber)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getInt("Data.Int."+IntNumber);
	}

	public Boolean getBoolean(Player player, byte BooleanNumber)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getBoolean("Data.Boolean."+BooleanNumber);
	}

	public String getNPCuuid(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getString("Data.NPCuuid");
	}

	public ItemStack getItemStack(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getItemStack("Data.Item");
	}
	
	public void setNPCuuid(Player player, String Value)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.NPCuuid",Value);
		UserData.saveConfig();
	}
	
	public String getTemp(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		String Temp = UserData.getString("Data.Temp");
		if(Temp!=null && Temp.isEmpty() == false && Temp.compareTo("MemorySection[path='Data.Temp', root='YamlConfiguration']")!=0)
			return Temp;
		else
			return null;
	}
	
	public void setTemp(Player player, String Value)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Temp",Value);
		UserData.saveConfig();
	}
	
	public void initTemp(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Temp",null);
		UserData.saveConfig();
	}
	
	public void setString(Player player, byte StringNumber,String Value)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.String."+StringNumber,Value);
		UserData.saveConfig();
	}
	
	public void setInt(Player player, byte IntNumber,int Value)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Int."+IntNumber,Value);
		UserData.saveConfig();
	}
	
	public void setItemStack(Player player, ItemStack item)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Item",item);
		UserData.saveConfig();
	}
	
	public void setBoolean(Player player, byte BooleanNumber,boolean Value)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Boolean."+BooleanNumber,Value);
		UserData.saveConfig();
	}

	public void clearType(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Type",null);
		UserData.saveConfig();
	}

	public void clearAll(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		String NPCuuid = getNPCuuid(player);
		UserData.removeKey("Data");
		UserData.saveConfig();
		UserData.createSection("Data.Type");
		UserData.set("Data.Temp",null);
		UserData.set("Data.Item",null);
		UserData.set("Data.NPCuuid",NPCuuid);
		for(byte count = 0; count < 9; count++)
			UserData.createSection("Data.String."+count);
		for(byte count = 0; count < 6; count++)
			UserData.set("Data.Int."+count,0);
		for(byte count = 0; count < 2; count++)
			UserData.set("Data.Boolean."+count,false);
		UserData.saveConfig();
	}
}