package user;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import util.YamlLoader;

public class UserData_Object
{
	public void UserDataInit(Player player)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Name", player.getName());
		UserData.removeKey("Data");
		UserData.saveConfig();
		UserData.createSection("Data.Type");
		UserData.createSection("Data.Temp");
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
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Type",TypeName);
		UserData.saveConfig();
	}

	public String getType(Player player)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		String Type = UserData.getString("Data.Type");
		if(Type != null && !Type.equals("MemorySection[path='Data.Type', root='YamlConfiguration']"))
			return Type;
		else
			return null;
	}
	
	public String getString(Player player, byte StringNumber)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		String string = UserData.getString("Data.String."+StringNumber);
		if(string != null)
			return string;
		else
			return null;
	}
	
	public int getInt(Player player, byte IntNumber)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getInt("Data.Int."+IntNumber);
	}

	public Boolean getBoolean(Player player, byte BooleanNumber)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getBoolean("Data.Boolean."+BooleanNumber);
	}

	public String getNPCuuid(Player player)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getString("Data.NPCuuid");
	}

	public ItemStack getItemStack(Player player)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		return UserData.getItemStack("Data.Item");
	}
	
	public void setNPCuuid(Player player, String Value)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.NPCuuid",Value);
		UserData.saveConfig();
	}
	
	public String getTemp(Player player)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		String Temp = UserData.getString("Data.Temp");
		if(Temp!=null && Temp.isEmpty() == false && !Temp.equals("MemorySection[path='Data.Temp', root='YamlConfiguration']"))
			return Temp;
		else
			return null;
	}
	
	public void setTemp(Player player, String Value)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Temp",Value);
		UserData.saveConfig();
	}
	
	public void initTemp(Player player)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Temp",null);
		UserData.saveConfig();
	}
	
	public void setString(Player player, byte StringNumber,String Value)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.String."+StringNumber,Value);
		UserData.saveConfig();
	}
	
	public void setInt(Player player, byte IntNumber,int Value)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Int."+IntNumber,Value);
		UserData.saveConfig();
	}
	
	public void setItemStack(Player player, ItemStack item)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Item",item);
		UserData.saveConfig();
	}
	
	public void setBoolean(Player player, byte BooleanNumber,boolean Value)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Boolean."+BooleanNumber,Value);
		UserData.saveConfig();
	}

	public void clearType(Player player)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.set("Data.Type",null);
		UserData.saveConfig();
	}

	public void clearAll(Player player)
	{
		YamlLoader UserData = new YamlLoader();
		UserData.getConfig("UserData/"+ player.getUniqueId()+".yml");
		String NPCuuid = getNPCuuid(player);
		UserData.removeKey("Data");
		UserData.saveConfig();
		UserData.createSection("Data.Type");
		UserData.set("Data.Temp",null);
		UserData.set("Data.Item",null);
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