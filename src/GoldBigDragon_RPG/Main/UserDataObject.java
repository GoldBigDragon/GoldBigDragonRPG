package GoldBigDragon_RPG.Main;

import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlManager;

public class UserDataObject
{
	public void UserDataInit(Player player)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Name", player.getName());
		UserData.removeKey("Data");
		UserData.saveConfig();
		UserData.createSection("Data.Type");
		UserData.set("Data.Temp",null);
		UserData.createSection("Data.NPCuuid");
		for(int count = 0; count < 9; count++)
			UserData.createSection("Data.String."+count);
		for(int count = 0; count < 6; count++)
			UserData.set("Data.Int."+count,0);
		for(int count = 0; count < 2; count++)
			UserData.set("Data.Boolean."+count,false);
		UserData.saveConfig();
	}
	
	public void setType(Player player, String TypeName)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Type",TypeName);
		UserData.saveConfig();
	}

	public String getType(Player player)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getString("Data.Type");
	}
	
	public String getString(Player player, byte StringNumber)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getString("Data.String."+StringNumber);
	}
	
	public int getInt(Player player, byte IntNumber)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getInt("Data.Int."+IntNumber);
	}

	public Boolean getBoolean(Player player, byte BooleanNumber)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getBoolean("Data.Boolean."+BooleanNumber);
	}

	public String getNPCuuid(Player player)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getString("Data.NPCuuid");
	}
	
	public void setNPCuuid(Player player, String Value)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.NPCuuid",Value);
		UserData.saveConfig();
	}
	
	public String getTemp(Player player)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getString("Data.Temp");
	}
	
	public void setTemp(Player player, String Value)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Temp",Value);
		UserData.saveConfig();
	}
	
	public void initTemp(Player player)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Temp",null);
		UserData.saveConfig();
	}
	
	public void setString(Player player, byte StringNumber,String Value)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.String."+StringNumber,Value);
		UserData.saveConfig();
	}
	
	public void setInt(Player player, byte IntNumber,int Value)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Int."+IntNumber,Value);
		UserData.saveConfig();
	}
	
	public void setBoolean(Player player, byte BooleanNumber,boolean Value)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Boolean."+BooleanNumber,Value);
		UserData.saveConfig();
	}

	public void clearType(Player player)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Type",null);
		UserData.saveConfig();
	}

	public void clearAll(Player player)
	{
		YamlManager UserData = Main.YC_3.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		String NPCuuid = getNPCuuid(player);
		UserData.removeKey("Data");
		UserData.saveConfig();
		UserData.createSection("Data.Type");
		UserData.set("Data.Temp",null);
		UserData.set("Data.NPCuuid",NPCuuid);
		for(int count = 0; count < 9; count++)
			UserData.createSection("Data.String."+count);
		for(int count = 0; count < 6; count++)
			UserData.set("Data.Int."+count,0);
		for(int count = 0; count < 2; count++)
			UserData.set("Data.Boolean."+count,false);
		UserData.saveConfig();
	}
}