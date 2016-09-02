package GoldBigDragon_RPG.Dungeon;

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

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class DungeonCreater
{
	public long[] getStartPoint()
	{
		long[] XYZ = new long[3];
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonData = YC.getNewConfig("Dungeon/DungeonData.yml");
		if(DungeonData.contains("StartPoint.X")==false)
		{
			DungeonData.set("StartPoint.X", 1000);
			DungeonData.set("StartPoint.Y", 30);
			DungeonData.set("StartPoint.Z", 1000);
			DungeonData.saveConfig();
		}
		XYZ[0]=DungeonData.getLong("StartPoint.X");
		XYZ[1]=DungeonData.getLong("StartPoint.Y");
		XYZ[2]=DungeonData.getLong("StartPoint.Z");
		return XYZ;
	}
	
	public void setStartPoint(int DungeonSize)
	{
		long totalSize = DungeonSize*21;
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonData = YC.getNewConfig("Dungeon/DungeonData.yml");
		if(DungeonData.contains("StartPoint.X")==false)
		{
			DungeonData.set("StartPoint.X", 0+totalSize);
			DungeonData.set("StartPoint.Z", 0+totalSize);
		}
		else
		{
			DungeonData.set("StartPoint.X", DungeonData.getLong("StartPoint.X")+totalSize);
			DungeonData.set("StartPoint.Z", DungeonData.getLong("StartPoint.Z")+totalSize);
		}
		DungeonData.saveConfig();
		return;
	}

	
	public void CreateTestSeed(Player player, int DungeonSize, int Maze_Level,String DungeonType)
	{
		String seed =null;
		for(int count = 0;count<3;count++)
		{
			seed = new SeedMaker().CreateSeed(DungeonSize, new String[DungeonSize*DungeonSize], new int[DungeonSize*DungeonSize], Maze_Level, DungeonType);
			if(seed.compareTo("null")!=0)
				break;
		}
		if(seed.compareTo("null")==0||seed==null)
			player.sendMessage(ChatColor.RED+"[SYSTEM] : 시드 생성 실패!");
		else
		{
			player.sendMessage(ChatColor.GREEN+"[SYSTEM] : 시드 생성 완료!");
			player.sendMessage(ChatColor.YELLOW+"[시드] "+ChatColor.WHITE+seed);
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
			player.sendMessage(ChatColor.RED+"["+ChatColor.YELLOW+DungeonType+ChatColor.RED+"던전 타입 데이터가 없습니다! 관리자에게 문의하세요!]");
			player.sendMessage(ChatColor.GRAY+"(plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+" 경로 아래에 그에 맞는 schematic 파일을 넣어 주세요!)");
			return false;
      	}
    	if(fileCheck(DungeonType)==false)
    	{
			player.sendMessage(ChatColor.RED+"["+ChatColor.YELLOW+DungeonType+ChatColor.RED+"던전 타입 데이터가 없습니다! 관리자에게 문의하세요!]");
			player.sendMessage(ChatColor.GRAY+"(plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+" 경로 아래에 그에 맞는 schematic 파일을 넣어 주세요!)");
			return false;
    	}
    	
		String seed = null;
		for(int count = 0;count<3;count++)
		{
			seed = new SeedMaker().CreateSeed(DungeonSize, new String[DungeonSize*DungeonSize], new int[DungeonSize*DungeonSize], Maze_Level, DungeonType);
			if(seed.compareTo("null")!=0)
				break;
		}
		if(seed.compareTo("null")==0)
		{
			player.sendMessage(ChatColor.WHITE+"(왠일인지 아무 일도 일어나지 않았다... 다시 시도해 보자.)");
			return false;
		}

	  	ArrayList<String> list = seedGet(seed, DungeonSize, false);
	  	if(list == null)
	  	{
			player.sendMessage(ChatColor.WHITE+"(왠일인지 아무 일도 일어나지 않았다... 다시 시도해 보자.)");
			return false;
	  	}
	  	
    	GoldBigDragon_RPG.ServerTick.DungeonScheduleObject DSO = new GoldBigDragon_RPG.ServerTick.DungeonScheduleObject("D_RC");
    	
    	for(int count2=0; count2 < list.size(); count2++)
    	{
    		String ListPiece = list.get(count2);
        	for(int count=0; count < ListPiece.length(); count++)
        	{
        		if(ListPiece.charAt(count)!='Ｘ')
        		{
        			DSO.addGrid(ListPiece.charAt(count));
        			DSO.addGridLoc((short) (count+(DungeonSize*count2)));
        		}
        	}
    	}
    	list = seedGet(seed, DungeonSize, true);
	  	if(list == null)
	  	{
			player.sendMessage(ChatColor.WHITE+"(왠일인지 아무 일도 일어나지 않았다... 다시 시도해 보자.)");
			return false;
	  	}
    	for(int count2=0; count2 < list.size(); count2++)
    	{
    		String ListPiece = list.get(count2);
        	for(int count=0; count < ListPiece.length(); count++)
        	{
        		if(ListPiece.charAt(count)!='Ｘ')
        		{
        			DSO.addKeyGrid(ListPiece.charAt(count));
        			DSO.addKeyGridLoc((short) (count+(DungeonSize*count2)));
        		}
        	}
    	}
    	DSO.setSize((byte) DungeonSize);
    	DSO.setDungeonType(DungeonType);
    	DSO.setDungeonName(DungeonName);
	    long config[] = getStartPoint();//던전 생성시 어떤 X, Z값 부터 시작할지 가져옴
    	DSO.setStartX(config[0]);
    	DSO.setStartY((short)config[1]);
    	DSO.setStartZ(config[2]);
    	DSO.setLeader(player);
    	DSO.setItem(putedItem);
    	long UTC = GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC;
    	if(nearPartyMember!=null)
    	{
    		//파티 찾아서 파티원들 모두 등록하기
    		for(int count = 0; count < nearPartyMember.size(); count++)
    		{
    			new GoldBigDragon_RPG.Dungeon.DungeonWork().EraseAllDungeonKey(nearPartyMember.get(count), false);
    			new GoldBigDragon_RPG.Effect.PacketSender().sendTitle(nearPartyMember.get(count),"\'"+ChatColor.WHITE+"대기실\'", (byte)1, (byte)0, (byte)1);
				nearPartyMember.get(count).teleport(new Location(Bukkit.getWorld("Dungeon"), -87, 31, -87));
    			DSO.addDungeonMaker(nearPartyMember.get(count).getName());
    			GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(nearPartyMember.get(count).getUniqueId().toString()).setDungeon_Enter(DungeonName);
    			GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(nearPartyMember.get(count).getUniqueId().toString()).setDungeon_UTC(UTC);
    		}
    	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
    		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Entered/"+UTC+".yml");
    		for(int count = 0; count < nearPartyMember.size(); count++)
    			DungeonConfig.set("EnteredPlayer."+nearPartyMember.get(count).getName(), 0);
    		DungeonConfig.set("EnteredAlter", AlterName);
    		int BossSize = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml").getConfigurationSection("Boss").getKeys(false).size();
    		for(int count = 0; count < BossSize; count++)
    			DungeonConfig.set("Boss."+count, YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml").getString("Boss."+count));
    		DungeonConfig.saveConfig();
    	}
    	else
    	{
			new GoldBigDragon_RPG.Dungeon.DungeonWork().EraseAllDungeonKey(player, false);
			player.teleport(new Location(Bukkit.getWorld("Dungeon"), -87, 31, -87));
			new GoldBigDragon_RPG.Effect.PacketSender().sendTitle(player,"\'"+ChatColor.WHITE+"대기실\'", (byte)1, (byte)0, (byte)1);
    		DSO.addDungeonMaker(player.getName());
			GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setDungeon_Enter(DungeonName);
			GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setDungeon_UTC(UTC);
    	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Entered/"+UTC+".yml");
			DungeonConfig.set("EnteredPlayer."+player.getName(), 0);
			DungeonConfig.set("EnteredAlter", AlterName);

    		int BossSize = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml").getConfigurationSection("Boss").getKeys(false).size();
    		for(int count = 0; count < BossSize; count++)
    			DungeonConfig.set("Boss."+count, YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml").getString("Boss."+count));
    		DungeonConfig.saveConfig();
    	}

	    setStartPoint(DungeonSize);//던전 생성 후, 벌어진 X,Z값 저장
	    GoldBigDragon_RPG.ServerTick.ServerTickMain.DungeonSchedule.put(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC, DSO);
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
