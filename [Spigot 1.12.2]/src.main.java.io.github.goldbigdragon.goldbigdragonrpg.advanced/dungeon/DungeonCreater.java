package dungeon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import util.YamlLoader;

public class DungeonCreater
{
	public long[] getStartPoint()
	{
		long[] xyz = new long[3];
	  	YamlLoader dungeonDataYaml = new YamlLoader();
		dungeonDataYaml.getConfig("Dungeon/DungeonData.yml");
		if(!dungeonDataYaml.contains("StartPoint.X"))
		{
			dungeonDataYaml.set("StartPoint.X", 1000);
			dungeonDataYaml.set("StartPoint.Y", 30);
			dungeonDataYaml.set("StartPoint.Z", 1000);
			dungeonDataYaml.saveConfig();
		}
		xyz[0]=dungeonDataYaml.getLong("StartPoint.X");
		xyz[1]=dungeonDataYaml.getLong("StartPoint.Y");
		xyz[2]=dungeonDataYaml.getLong("StartPoint.Z");
		return xyz;
	}
	
	public void setStartPoint(int dungeonSize)
	{
		long totalSize = dungeonSize*21;
	  	YamlLoader dungeonDataYaml = new YamlLoader();
		dungeonDataYaml.getConfig("Dungeon/DungeonData.yml");
		if(!dungeonDataYaml.contains("StartPoint.X"))
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

	
	public void createTestSeed(Player player, int dungeonSize, int mazeLevel,String dungeonType)
	{
		String seed =null;
		for(int count = 0;count<3;count++)
		{
			seed = new DungeonSeedMaker().CreateSeed(dungeonSize, new String[dungeonSize*dungeonSize], new int[dungeonSize*dungeonSize], mazeLevel, dungeonType);
			if(!seed.equals("null"))
				break;
		}
		if(seed.equals("null")||seed==null)
			player.sendMessage("§c[SYSTEM] : 시드 생성 실패!");
		else
		{
			player.sendMessage("§a[SYSTEM] : 시드 생성 완료!");
			player.sendMessage("§e[시드] §f"+seed);
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
			player.sendMessage("§c[§e"+DungeonType+"§c던전 타입 데이터가 없습니다! 관리자에게 문의하세요!]");
			player.sendMessage("§7(plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+" 경로 아래에 그에 맞는 schematic 파일을 넣어 주세요!)");
			return false;
      	}
    	if(!fileCheck(DungeonType))
    	{
			player.sendMessage("§c[§e"+DungeonType+"§c던전 타입 데이터가 없습니다! 관리자에게 문의하세요!]");
			player.sendMessage("§7(plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+" 경로 아래에 그에 맞는 schematic 파일을 넣어 주세요!)");
			return false;
    	}
    	
		String seed = null;
		for(int count = 0;count<3;count++)
		{
			seed = new DungeonSeedMaker().CreateSeed(DungeonSize, new String[DungeonSize*DungeonSize], new int[DungeonSize*DungeonSize], Maze_Level, DungeonType);
			if(!seed.equals("null"))
				break;
		}
		if(seed.equals("null"))
		{
			player.sendMessage("§f(왠일인지 아무 일도 일어나지 않았다... 다시 시도해 보자.)");
			return false;
		}

	  	ArrayList<String> list = seedGet(seed, DungeonSize, false);
	  	if(list == null)
	  	{
			player.sendMessage("§f(왠일인지 아무 일도 일어나지 않았다... 다시 시도해 보자.)");
			return false;
	  	}
	  	
    	dungeon.DungeonScheduleObject DSO = new dungeon.DungeonScheduleObject("D_RC");
    	
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
			player.sendMessage("§f(왠일인지 아무 일도 일어나지 않았다... 다시 시도해 보자.)");
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
    	long UTC = servertick.ServerTickMain.nowUTC;
	  	YamlLoader dungeonYaml = new YamlLoader();
	  	YamlLoader dungeonMonsterYaml = new YamlLoader();
	  	dungeonMonsterYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
		dungeonYaml.getConfig("Dungeon/Dungeon/"+DungeonName+"/Entered/"+UTC+".yml");
    	if(nearPartyMember!=null)
    	{
    		//파티 찾아서 파티원들 모두 등록하기
    		for(int count = 0; count < nearPartyMember.size(); count++)
    		{
    			new dungeon.DungeonMain().eraseAllDungeonKey(nearPartyMember.get(count), false);
    			new effect.SendPacket().sendTitle(nearPartyMember.get(count),"§f대기실", null, 1, 0, 1);
				nearPartyMember.get(count).teleport(new Location(Bukkit.getWorld("Dungeon"), -87, 31, -87));
    			DSO.addDungeonMaker(nearPartyMember.get(count).getName());
    			main.MainServerOption.PlayerList.get(nearPartyMember.get(count).getUniqueId().toString()).setDungeon_Enter(DungeonName);
    			main.MainServerOption.PlayerList.get(nearPartyMember.get(count).getUniqueId().toString()).setDungeon_UTC(UTC);
    		}
    		for(int count = 0; count < nearPartyMember.size(); count++)
    			dungeonYaml.set("EnteredPlayer."+nearPartyMember.get(count).getName(), 0);
    		dungeonYaml.set("EnteredAlter", AlterName);
    	}
    	else
    	{
			new dungeon.DungeonMain().eraseAllDungeonKey(player, false);
			player.teleport(new Location(Bukkit.getWorld("Dungeon"), -87, 31, -87));
			new effect.SendPacket().sendTitle(player,"§f대기실", null, 1, 0, 1);
    		DSO.addDungeonMaker(player.getName());
			main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setDungeon_Enter(DungeonName);
			main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).setDungeon_UTC(UTC);
			dungeonYaml.set("EnteredPlayer."+player.getName(), 0);
			dungeonYaml.set("EnteredAlter", AlterName);
    	}
		int BossSize = dungeonMonsterYaml.getConfigurationSection("Boss").getKeys(false).size();
		for(int count = 0; count < BossSize; count++)
			dungeonYaml.set("Boss."+count,dungeonMonsterYaml.getString("Boss."+count));
		dungeonYaml.saveConfig();
		
	    setStartPoint(DungeonSize);//던전 생성 후, 벌어진 X,Z값 저장
	    servertick.ServerTickMain.DungeonSchedule.put(servertick.ServerTickMain.nowUTC, DSO);
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
	  	ArrayList<String> list = new ArrayList<>();
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
