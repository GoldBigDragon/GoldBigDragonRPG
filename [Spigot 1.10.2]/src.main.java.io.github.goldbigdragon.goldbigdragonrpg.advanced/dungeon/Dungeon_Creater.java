package dungeon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import util.YamlLoader;




public class Dungeon_Creater
{
	public long[] getStartPoint()
	{
		long[] XYZ = new long[3];
	  	YamlLoader dungeonDataYaml = new YamlLoader();
		dungeonDataYaml.getConfig("Dungeon/DungeonData.yml");
		if(dungeonDataYaml.contains("StartPoint.X")==false)
		{
			dungeonDataYaml.set("StartPoint.X", 1000);
			dungeonDataYaml.set("StartPoint.Y", 30);
			dungeonDataYaml.set("StartPoint.Z", 1000);
			dungeonDataYaml.saveConfig();
		}
		XYZ[0]=dungeonDataYaml.getLong("StartPoint.X");
		XYZ[1]=dungeonDataYaml.getLong("StartPoint.Y");
		XYZ[2]=dungeonDataYaml.getLong("StartPoint.Z");
		return XYZ;
	}
	
	public void setStartPoint(int DungeonSize)
	{
		long totalSize = DungeonSize*21;
	  	YamlLoader dungeonDataYaml = new YamlLoader();
		dungeonDataYaml.getConfig("Dungeon/DungeonData.yml");
		if(dungeonDataYaml.contains("StartPoint.X")==false)
		{
			dungeonDataYaml.set("StartPoint.X", 0+totalSize);
			dungeonDataYaml.set("StartPoint.Z", 0+totalSize);
		}
		else
		{
			dungeonDataYaml.set("StartPoint.X", dungeonDataYaml.getLong("StartPoint.X")+totalSize);
			dungeonDataYaml.set("StartPoint.Z", dungeonDataYaml.getLong("StartPoint.Z")+totalSize);
		}
		dungeonDataYaml.saveConfig();
		return;
	}

	
	public void CreateTestSeed(Player player, int DungeonSize, int Maze_Level,String DungeonType)
	{
		String seed =null;
		for(int count = 0;count<3;count++)
		{
			seed = new Dungeon_SeedMaker().CreateSeed(DungeonSize, new String[DungeonSize*DungeonSize], new int[DungeonSize*DungeonSize], Maze_Level, DungeonType);
			if(seed.compareTo("null")!=0)
				break;
		}
		if(seed.compareTo("null")==0||seed==null)
			player.sendMessage(ChatColor.RED+"[SYSTEM] : �õ� ���� ����!");
		else
		{
			player.sendMessage(ChatColor.GREEN+"[SYSTEM] : �õ� ���� �Ϸ�!");
			player.sendMessage(ChatColor.YELLOW+"[�õ�] "+ChatColor.WHITE+seed);
		}
	}

	public boolean CreateDungeon(Player player, int DungeonSize, int Maze_Level,String DungeonType,String DungeonName, ArrayList<Player> nearPartyMember, String AlterName, ItemStack putedItem)
	{
    	File dt = new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType);
    	if (!dt.exists())
      	{
	  	    File F1 = new File("plugins/GoldBigDragonRPG/Dungeon");
		    File F2 = new File("plugins/GoldBigDragonRPG/Dungeon/Schematic");
		    File F3 = new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType);
		    F1.mkdir();
		    F2.mkdir();
		    F3.mkdir();
			player.sendMessage(ChatColor.RED+"["+ChatColor.YELLOW+DungeonType+ChatColor.RED+"���� Ÿ�� �����Ͱ� �����ϴ�! �����ڿ��� �����ϼ���!]");
			player.sendMessage(ChatColor.GRAY+"(plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+" ��� �Ʒ��� �׿� �´� schematic ������ �־� �ּ���!)");
			return false;
      	}
    	if(fileCheck(DungeonType)==false)
    	{
			player.sendMessage(ChatColor.RED+"["+ChatColor.YELLOW+DungeonType+ChatColor.RED+"���� Ÿ�� �����Ͱ� �����ϴ�! �����ڿ��� �����ϼ���!]");
			player.sendMessage(ChatColor.GRAY+"(plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+" ��� �Ʒ��� �׿� �´� schematic ������ �־� �ּ���!)");
			return false;
    	}
    	
		String seed = null;
		for(int count = 0;count<3;count++)
		{
			seed = new Dungeon_SeedMaker().CreateSeed(DungeonSize, new String[DungeonSize*DungeonSize], new int[DungeonSize*DungeonSize], Maze_Level, DungeonType);
			if(seed.compareTo("null")!=0)
				break;
		}
		if(seed.compareTo("null")==0)
		{
			player.sendMessage(ChatColor.WHITE+"(�������� �ƹ� �ϵ� �Ͼ�� �ʾҴ�... �ٽ� �õ��� ����.)");
			return false;
		}

	  	ArrayList<String> list = seedGet(seed, DungeonSize, false);
	  	if(list == null)
	  	{
			player.sendMessage(ChatColor.WHITE+"(�������� �ƹ� �ϵ� �Ͼ�� �ʾҴ�... �ٽ� �õ��� ����.)");
			return false;
	  	}
	  	
    	dungeon.Dungeon_ScheduleObject DSO = new dungeon.Dungeon_ScheduleObject("D_RC");
    	
    	for(int count2=0; count2 < list.size(); count2++)
    	{
    		String ListPiece = list.get(count2);
        	for(int count=0; count < ListPiece.length(); count++)
        	{
        		if(ListPiece.charAt(count)!='��')
        		{
        			DSO.addGrid(ListPiece.charAt(count));
        			DSO.addGridLoc((short) (count+(DungeonSize*count2)));
        		}
        	}
    	}
    	list = seedGet(seed, DungeonSize, true);
	  	if(list == null)
	  	{
			player.sendMessage(ChatColor.WHITE+"(�������� �ƹ� �ϵ� �Ͼ�� �ʾҴ�... �ٽ� �õ��� ����.)");
			return false;
	  	}
    	for(int count2=0; count2 < list.size(); count2++)
    	{
    		String ListPiece = list.get(count2);
        	for(int count=0; count < ListPiece.length(); count++)
        	{
        		if(ListPiece.charAt(count)!='��')
        		{
        			DSO.addKeyGrid(ListPiece.charAt(count));
        			DSO.addKeyGridLoc((short) (count+(DungeonSize*count2)));
        		}
        	}
    	}
    	DSO.setSize((byte) DungeonSize);
    	DSO.setDungeonType(DungeonType);
    	DSO.setDungeonName(DungeonName);
	    long config[] = getStartPoint();//���� ������ � X, Z�� ���� �������� ������
    	DSO.setStartX(config[0]);
    	DSO.setStartY((short)config[1]);
    	DSO.setStartZ(config[2]);
    	DSO.setLeader(player);
    	DSO.setItem(putedItem);
    	long UTC = servertick.ServerTick_Main.nowUTC;
	  	YamlLoader dungeonYaml = new YamlLoader();
	  	YamlLoader dungeonMonsterYaml = new YamlLoader();
	  	dungeonMonsterYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
		dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Entered/"+UTC+".yml");
    	if(nearPartyMember!=null)
    	{
    		//��Ƽ ã�Ƽ� ��Ƽ���� ��� ����ϱ�
    		for(int count = 0; count < nearPartyMember.size(); count++)
    		{
    			new dungeon.Dungeon_Main().EraseAllDungeonKey(nearPartyMember.get(count), false);
    			new effect.SendPacket().sendTitle(nearPartyMember.get(count),"\'"+ChatColor.WHITE+"����\'", (byte)1, (byte)0, (byte)1);
				nearPartyMember.get(count).teleport(new Location(Bukkit.getWorld("Dungeon"), -87, 31, -87));
    			DSO.addDungeonMaker(nearPartyMember.get(count).getName());
    			main.Main_ServerOption.PlayerList.get(nearPartyMember.get(count).getUniqueId().toString()).setDungeon_Enter(DungeonName);
    			main.Main_ServerOption.PlayerList.get(nearPartyMember.get(count).getUniqueId().toString()).setDungeon_UTC(UTC);
    		}
    		for(int count = 0; count < nearPartyMember.size(); count++)
    			dungeonYaml.set("EnteredPlayer."+nearPartyMember.get(count).getName(), 0);
    		dungeonYaml.set("EnteredAlter", AlterName);
    	}
    	else
    	{
			new dungeon.Dungeon_Main().EraseAllDungeonKey(player, false);
			player.teleport(new Location(Bukkit.getWorld("Dungeon"), -87, 31, -87));
			new effect.SendPacket().sendTitle(player,"\'"+ChatColor.WHITE+"����\'", (byte)1, (byte)0, (byte)1);
    		DSO.addDungeonMaker(player.getName());
			main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setDungeon_Enter(DungeonName);
			main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).setDungeon_UTC(UTC);
			dungeonYaml.set("EnteredPlayer."+player.getName(), 0);
			dungeonYaml.set("EnteredAlter", AlterName);
    	}
		int BossSize = dungeonMonsterYaml.getConfigurationSection("Boss").getKeys(false).size();
		for(int count = 0; count < BossSize; count++)
			dungeonYaml.set("Boss."+count,dungeonMonsterYaml.getString("Boss."+count));
		dungeonYaml.saveConfig();
		
	    setStartPoint(DungeonSize);//���� ���� ��, ������ X,Z�� ����
	    servertick.ServerTick_Main.DungeonSchedule.put(servertick.ServerTick_Main.nowUTC, DSO);
    	return true;
	}
	
	public boolean fileCheck(String DungeonType)
	{
		File file = new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Room.schematic");
		if(!file.exists())
			return false;
		file = new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/CrossRoad.schematic");
		if(!file.exists())
			return false;
		for(int count = 0; count < 4; count++)
		{
			file = new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/LRoad"+count+".schematic");
			if(!file.exists())
				return false;
			file = new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/TRoad"+count+".schematic");
			if(!file.exists())
				return false;
		}
		for(int count = 0; count < 2; count++)
		{
			file = new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Road"+count+".schematic");
			if(!file.exists())
				return false;
		}
		file = new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Boss.schematic");
		if(!file.exists())
			return false;
		return true;
	}
	
	public ArrayList<String> seedGet(String seed, int DungeonSize, boolean isKeyseed)
	{
		File filename = null;
		if(isKeyseed)
			filename = new File("plugins/GoldBigDragonRPG/Dungeon/Seed/"+seed+"_KeyGrid.txt");
		else
			filename = new File("plugins/GoldBigDragonRPG/Dungeon/Seed/"+seed+".txt");
	  	BufferedReader Read;
		try
		{Read = new BufferedReader(new FileReader(filename));}
		catch (FileNotFoundException e)
		{return null;}
	  	ArrayList<String> list = new ArrayList<String>();
	  	String s;
	  	try
	  	{
			while ((s = Read.readLine()) != null)
				list.add(s);
			Read.close();
		}
	  	catch (IOException e1)
	  	{return null;}
	  	return list;
	}

}