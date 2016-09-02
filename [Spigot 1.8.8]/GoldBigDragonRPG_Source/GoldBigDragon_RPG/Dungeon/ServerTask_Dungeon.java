package GoldBigDragon_RPG.Dungeon;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.ServerTick.DungeonScheduleObject;
import GoldBigDragon_RPG.ServerTick.ServerTickMain;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class ServerTask_Dungeon
{
	private void CreateOptionSign(Block block, int count, int MaxCount, char GridImage, String DungeonName, int Direction)
	{
		Location loc = new Location(block.getWorld(), block.getX(), block.getY()-1, block.getZ());
		if(loc.getBlock()==null||loc.getBlock().getType()==Material.AIR)
			loc.getBlock().setType(Material.BARRIER);
        block.setType(Material.WALL_SIGN);
        Sign s = (Sign) block.getState();
        s.setLine(0, count+"/"+MaxCount);//스폰되는 몬스터 난이도를 위한 것
        s.setLine(1, GridImage+"");//해당 방의 설명
        s.setLine(2, DungeonName);//던전 콘피그를 열기 위한 것
        s.setLine(3, Direction+"");//현재 어느 위치의 물체인지 나타냄.
        /*
        Direction : 0 = 북
        Direction : 1 = 북동
        Direction : 2 = 동
        Direction : 3 = 남동
        Direction : 4 = 남
        Direction : 5 = 남서
        Direction : 6 = 서
        Direction : 7 = 북서
        Direction : 8 = 중앙
         */
        s.update();
	}
	
	public void CreateRoom(DungeonScheduleObject DSO)
	{
		int count = DSO.getCount();
		if(count <= -5)
			return;
		World w = Bukkit.getServer().getWorld("Dungeon");
		String DungeonType = DSO.getDungeonType();
		int Yloc = DSO.getStartY();
		int size = DSO.getSize();
		
		char KeyGridImage = (char) -1;
		char GridImage = DSO.getGrid(count);
		int GridLocation = DSO.getGridLoc(count);
		long Zloc = GridLocation/DSO.getSize();
		long Xloc = GridLocation-(Zloc*size);
		Xloc = (Xloc*21)+DSO.getStartX();
		Zloc = (Zloc*21)+DSO.getStartZ();
		Location loc = new Location(w,Xloc,Yloc,Zloc);
		if(DSO.isBossRoomAdded()==false)
		{
			for(int counter = 0; counter < DSO.getGridSize(); counter++)
			{
				if(DSO.getGrid(counter)=='■')
				{
					w = Bukkit.getServer().getWorld("Dungeon");
					DungeonType = DSO.getDungeonType();
					GridLocation = DSO.getGridLoc(counter);
					Zloc = GridLocation/DSO.getSize();
					Xloc = GridLocation-(Zloc*size);
					Xloc = (Xloc*21)+DSO.getStartX();
					Zloc = (Zloc*21)+DSO.getStartZ();
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Boss.schematic"), new Location(w,Xloc-21,Yloc,Zloc-42));
					break;
				}
			}
			DSO.setBossRoomAdded(true);
        	DSO.copyThisScheduleObject(ServerTickMain.nowUTC+600);
		}
		else
		{
			DSO.SendCreatingRate(count);
			if(GridImage!='◎'&&DSO.containsKeyImage(GridLocation))
			{
				for(int counter = 0; counter < DSO.getKeyGridSize(); counter++)
				{
					if(DSO.getKeyGridLoc(counter)==GridLocation)
						KeyGridImage = DSO.getKeyGrid(counter);
				}
				if(KeyGridImage=='△'||KeyGridImage=='▲'||KeyGridImage=='▽'||KeyGridImage=='▷'
				||KeyGridImage=='▼'||KeyGridImage=='◁'||KeyGridImage=='◀'||KeyGridImage=='▶')
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Room.schematic"),loc);
			}
			else
			{
				switch(GridImage)
				{
		    	case '★' : 
			    	{	//귀환석 위치 지정하기
		                Block block = new Location(w, Xloc+10,Yloc+1,Zloc+10).getBlock();
		                block.setTypeIdAndData(138, (byte)0, true);
		                CreateOptionSign(new Location(w,10+Xloc,Yloc+13,10+Zloc).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),8);
		                
	                	block = new Location(w, Xloc+10,Yloc+1,Zloc+15).getBlock();
		                block.setTypeIdAndData(130, (byte)2, true);
		                CreateOptionSign(new Location(w,10+Xloc,Yloc+13,Zloc+15).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),4);
	                	block = new Location(w, Xloc+10,Yloc+1,Zloc+5).getBlock();
		                block.setTypeIdAndData(130, (byte)2, true);
		                CreateOptionSign(new Location(w,10+Xloc,Yloc+13,Zloc+5).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),0);
	                	block = new Location(w, Xloc+15,Yloc+1,Zloc+10).getBlock();
		                block.setTypeIdAndData(130, (byte)2, true);
		                CreateOptionSign(new Location(w,15+Xloc,Yloc+13,Zloc+10).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),2);
	                	block = new Location(w, Xloc+5,Yloc+1,Zloc+10).getBlock();
		                block.setTypeIdAndData(130, (byte)2, true);
		                CreateOptionSign(new Location(w,5+Xloc,Yloc+13,Zloc+10).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),6);
		                
	                	block = new Location(w, Xloc+15,Yloc+1,Zloc+5).getBlock();
		                block.setTypeIdAndData(130, (byte)2, true);
		                CreateOptionSign(new Location(w,15+Xloc,Yloc+13,Zloc+5).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),1);
	                	block = new Location(w, Xloc+5,Yloc+1,Zloc+15).getBlock();
		                block.setTypeIdAndData(130, (byte)2, true);
		                CreateOptionSign(new Location(w,5+Xloc,Yloc+13,Zloc+15).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),5);

	                	block = new Location(w, Xloc+15,Yloc+1,Zloc+15).getBlock();
		                block.setTypeIdAndData(130, (byte)2, true);
		                CreateOptionSign(new Location(w,15+Xloc,Yloc+13,Zloc+15).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),3);
	                	block = new Location(w, Xloc+5,Yloc+1,Zloc+5).getBlock();
		                block.setTypeIdAndData(130, (byte)2, true);
		                CreateOptionSign(new Location(w,5+Xloc,Yloc+13,Zloc+5).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),7);
			    	}
		    	case '□' : 
		    	case '◎' : 
		    	case '＠' : 
		    	case '◇' : 
			    		if(GridImage=='＠'||GridImage=='□'||GridImage=='◇')
			    		{
			    			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Room.schematic"),loc);
				    		if(GridImage=='＠')
				    		{
				    			DSO.setSpawnX(Xloc+10);
				    			DSO.setSpawnZ(Zloc+12);
				    			//귀환석 위치 지정하기
				                Block block = new Location(w, Xloc+10,Yloc+1,Zloc+10).getBlock();
				                block.setTypeIdAndData(138, (byte)0, true);
				                CreateOptionSign(new Location(w,10+Xloc,Yloc+13,Zloc+10).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),8);
				    		}
			    		}
			    		if(GridImage!='★')
			    		{
			    			DoorCreate(DSO.getDirection(count, 'N'), DSO.getDirection(count, 'E'), DSO.getDirection(count, 'W'), DSO.getDirection(count, 'S'), Xloc, Yloc, Zloc, size, w, DSO.getDungeonType());
			    		}
			    		if(GridImage=='□')
			    		{
			    			//상자
			                Block block = new Location(w,10+Xloc,Yloc+1,10+Zloc).getBlock();
			                block.setTypeIdAndData(146, (byte)0, true);
			                CreateOptionSign(new Location(w,10+Xloc,Yloc+13,10+Zloc).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),8);
			    		}
			    		if(GridImage=='◇')
			    		{
			    			//미믹 상자
			                int[] randomchest = new int[4];
			                for(char c=0;c<4;c++)
			                	randomchest[c]=146;
			                byte randomnum = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 3);
			                randomchest[randomnum] = 54;
			                Block block = new Location(w, 16+Xloc, Yloc+1, 16+Zloc).getBlock();
			                block.setTypeIdAndData(randomchest[0], (byte)0, true);
			                block = new Location(w, 4+Xloc, Yloc+1, 4+Zloc).getBlock();
			                block.setTypeIdAndData(randomchest[1], (byte)0, true);
			                block = new Location(w, 16+Xloc, Yloc+1, 4+Zloc).getBlock();
			                block.setTypeIdAndData(randomchest[2], (byte)0, true);
			                block = new Location(w, 4+Xloc, Yloc+1, 16+Zloc).getBlock();
			                block.setTypeIdAndData(randomchest[3], (byte)0, true);
		                	CreateOptionSign(new Location(w,16+Xloc,Yloc+13,16+Zloc).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),3);
		                	CreateOptionSign(new Location(w,4+Xloc,Yloc+13,4+Zloc).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),7);
		                	CreateOptionSign(new Location(w,16+Xloc,Yloc+13,4+Zloc).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),1);
		                	CreateOptionSign(new Location(w,4+Xloc,Yloc+13,16+Zloc).getBlock(), count, DSO.getGridSize(), GridImage, DSO.getDungeonName(),5);
			    		}
			    		break;
		    	case '└' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/LRoad0.schematic"),loc);
					break;
		    	case '┌' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/LRoad1.schematic"),loc);
					break;
		    	case '┐' :
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/LRoad2.schematic"),loc);
					break;
		    	case '┘' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/LRoad3.schematic"),loc);
					break;
		    	case '─' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Road1.schematic"),loc);
					break;
		    	case '│' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Road0.schematic"),loc);
					break;
		    	case '┬' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/TRoad0.schematic"),loc);
					break;
		    	case '┤' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/TRoad1.schematic"),loc);
					break;
		    	case '┴' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/TRoad2.schematic"),loc);
					break;
		    	case '├' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/TRoad3.schematic"),loc);
					break;
		    	case '┼' : 
					filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/CrossRoad.schematic"),loc);
					break;
				}
			}
	    	if(GridImage=='┼'||GridImage=='├'||GridImage=='┴'||GridImage=='┤'||GridImage=='┬'||GridImage=='─'||GridImage=='│'||
			GridImage=='└'||GridImage=='┌'||GridImage=='┐'||GridImage=='┘')
	    		WallCreate(GridImage, DSO.getDirection(count, 'N'), DSO.getDirection(count, 'E'), DSO.getDirection(count, 'W'), DSO.getDirection(count, 'S'), Xloc, Yloc, Zloc, size, w, DSO.getDungeonType());

	    	if(DSO.getGridSize()-1>DSO.getCount())
	    	{
	        	DSO.setCount(DSO.getCount()+1);
	        	DSO.copyThisScheduleObject(ServerTickMain.nowUTC+600);
	    	}
	    	else
	    	{
	    		ServerTickMain.DungeonSchedule.remove(DSO);
	    		DSO.setType("D_KRC");
	        	DSO.setCount(0);
	        	DSO.copyThisScheduleObject(ServerTickMain.nowUTC+600);
	    	}
		}
	}
	
	public void CreateKeyRoom(DungeonScheduleObject DSO)
	{
		World w = Bukkit.getServer().getWorld("Dungeon");
		String DungeonType = DSO.getDungeonType();
		int Yloc = DSO.getStartY();
		int count = DSO.getCount();

		char KeyGridImage = DSO.getKeyGrid(count);
		int KeyGridLocation = DSO.getKeyGridLoc(count);
		char GridImage = DSO.getUnderKeyImage(KeyGridLocation);
		long Zloc = KeyGridLocation/DSO.getSize();
		long Xloc = KeyGridLocation-(Zloc*DSO.getSize());
		Xloc = (Xloc*21)+DSO.getStartX();
		Zloc = (Zloc*21)+DSO.getStartZ();
		Location loc = new Location(w,Xloc,Yloc,Zloc);
		DSO.SendCreatingRate(count+(DSO.getGridSize()));
		switch(KeyGridImage)
		{
    	case '△' : 
    	case '▽' : 
    	case '◁' : 
    	case '▷' : 
    	case '♥' : 
			byte color = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(1, 5);
			switch(color)
			{
    			case 1: color=15;break;
    			case 2: color=14;break;
    			case 3: color=13;break;
    			case 4: color=11;break;
    			case 5: color=8;break;
			}
            Block block = new Location(w, 16+Xloc, Yloc+2, 16+Zloc).getBlock();
            block.setTypeIdAndData(95, color, true);
            block = new Location(w, 4+Xloc, Yloc+2, 4+Zloc).getBlock();
            block.setTypeIdAndData(95, color, true);
            block = new Location(w, 16+Xloc, Yloc+2, 4+Zloc).getBlock();
            block.setTypeIdAndData(95,color, true);
            block = new Location(w, 4+Xloc, Yloc+2, 16+Zloc).getBlock();
            block.setTypeIdAndData(95,color, true);
            
            block = new Location(w, 16+Xloc, Yloc+1, 16+Zloc).getBlock();
            block.setTypeIdAndData(7, (byte)0, true);
            block = new Location(w, 4+Xloc, Yloc+1, 4+Zloc).getBlock();
            block.setTypeIdAndData(7, (byte)0, true);
            block = new Location(w, 16+Xloc, Yloc+1, 4+Zloc).getBlock();
            block.setTypeIdAndData(7, (byte)0, true);
            block = new Location(w, 4+Xloc, Yloc+1, 16+Zloc).getBlock();
            block.setTypeIdAndData(7, (byte)0, true);

            byte randomnum = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 3);
            int checker[] = {3,3,3,3};
            checker[randomnum] = 1;
            block = new Location(w, 16+Xloc, Yloc, 16+Zloc).getBlock();
            block.setTypeIdAndData(checker[0], (byte)0, true);
            block = new Location(w, 4+Xloc, Yloc, 4+Zloc).getBlock();
            block.setTypeIdAndData(checker[1], (byte)0, true);
            block = new Location(w, 16+Xloc, Yloc, 4+Zloc).getBlock();
            block.setTypeIdAndData(checker[2], (byte)0, true);
            block = new Location(w, 4+Xloc, Yloc, 16+Zloc).getBlock();
            block.setTypeIdAndData(checker[3], (byte)0, true);
			CreateOptionSign(new Location(w,16+Xloc,Yloc+13,Zloc+16).getBlock(), count, DSO.getGridSize(), KeyGridImage, DSO.getDungeonName(),3);
			CreateOptionSign(new Location(w,4+Xloc,Yloc+13,Zloc+4).getBlock(), count, DSO.getGridSize(), KeyGridImage, DSO.getDungeonName(),7);
			CreateOptionSign(new Location(w,16+Xloc,Yloc+13,Zloc+4).getBlock(), count, DSO.getGridSize(), KeyGridImage, DSO.getDungeonName(),1);
			CreateOptionSign(new Location(w,4+Xloc,Yloc+13,Zloc+16).getBlock(), count, DSO.getGridSize(), KeyGridImage, DSO.getDungeonName(),5);
			break;
		}

		switch(KeyGridImage)
		{
		//문 위의 표지판 생성이 잘 되는지 확인하고,
		//문 우클릭시 일어날 이벤트 정하기.
    	case '△' : 
    	case '▲' : 
    	case '♠' : 
    	case '♥' : 
    		if(KeyGridImage=='♠'||KeyGridImage=='♥')
    		{
    		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
    		  	Location BossRoomDoorLoc = new Location(w,Xloc+10,Yloc+3,Zloc);
    			YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DSO.getDungeonName()+"/Entered/"+DSO.getUTC()+".yml");
    			DungeonConfig.set("BossRoomDoor.X", BossRoomDoorLoc.getX());
    			DungeonConfig.set("BossRoomDoor.Y", BossRoomDoorLoc.getY());
    			DungeonConfig.set("BossRoomDoor.Z", BossRoomDoorLoc.getZ());
    			DungeonConfig.saveConfig();
    		}
    		LockedRoomCreate(GridImage, w, Xloc, Yloc, Zloc, DungeonType, 'N');
			CreateOptionSign(new Location(w,Xloc+10,Yloc+13,Zloc).getBlock(), count, DSO.getGridSize(), KeyGridImage, DSO.getDungeonName(),-1);
			break;
    	case '▽' : 
    	case '▼' : 
    		LockedRoomCreate(GridImage, w, Xloc, Yloc, Zloc, DungeonType, 'S');
			CreateOptionSign(new Location(w,Xloc+10,Yloc+13,Zloc+20).getBlock(), count, DSO.getGridSize(), KeyGridImage, DSO.getDungeonName(),-1);
			break;
    	case '◁' : 
    	case '◀' : 
    		LockedRoomCreate(GridImage, w, Xloc, Yloc, Zloc, DungeonType, 'W');
			CreateOptionSign(new Location(w,Xloc,Yloc+13,Zloc+10).getBlock(), count, DSO.getGridSize(), KeyGridImage, DSO.getDungeonName(),-1);
			break;
    	case '▷' : 
    	case '▶' : 
    		LockedRoomCreate(GridImage, w, Xloc, Yloc, Zloc, DungeonType, 'E');
			CreateOptionSign(new Location(w,Xloc+20,Yloc+13,Zloc+10).getBlock(), count, DSO.getGridSize(), KeyGridImage, DSO.getDungeonName(),-1);
			break;
		}
    	if(DSO.getKeyGridSize()-1>DSO.getCount())
    	{
        	DSO.setCount(DSO.getCount()+1);
        	DSO.copyThisScheduleObject(ServerTickMain.nowUTC+600);
    	}
    	else
    	{
    		loc.setX(DSO.getSpawnX());
    		loc.setZ(DSO.getSpawnZ());
    		loc.setY(DSO.getStartY()+5);
    		loc.add(0, 1, 0);
    		for(int count2 = 0; count2 < DSO.getDungeonMakerSize(); count2++)
    		{
    			Player player = Bukkit.getPlayer(DSO.getDungeonMaker(count2));
    			if(player!=null)
    			{
    				if(player.getLocation().getWorld().getName().compareTo("Dungeon")==0)
    				{
        				player.teleport(loc);
        				new GoldBigDragon_RPG.Dungeon.DungeonWork().EraseAllDungeonKey(player, false);
    				}
    			}
    		}
    		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
    		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+DSO.getDungeonName()+"/Option.yml");
    		int SoundTrack = DungeonConfig.getInt("BGM.Normal");
    		
    		if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
    		{
        		for(int count2 = 0; count2 < DSO.getDungeonMakerSize(); count2++)
        		{
        			if(Bukkit.getPlayer(DSO.getDungeonMaker(count2))!=null)
        			{
            			Player player = Bukkit.getPlayer(DSO.getDungeonMaker(count2));
        				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
        				{
            				if(player.getLocation().getWorld().getName().compareTo("Dungeon")==0)
            				{
                    			new OtherPlugins.NoteBlockAPIMain().Stop(Bukkit.getPlayer(DSO.getDungeonMaker(count2)));
                    			new OtherPlugins.NoteBlockAPIMain().Play(Bukkit.getPlayer(DSO.getDungeonMaker(count2)),SoundTrack);		
            				}
        				}
        			}
        		}
    		}
    		ServerTickMain.DungeonSchedule.remove(DSO);
    	}
	}
	

	public void filePaste(File filename, Location loc)
	{
		try
		{Schematic.pasteSchematic(loc, Schematic.loadSchematic(filename));}
		catch (IOException e)
		{}
	}
	
	public void WallCreate(char GridImage, char UpGridImage, char RightGridImage, char LeftGridImage, char DownGridImage,
	long Xloc, long Yloc, long Zloc, int size, World world, String type)
	{
		boolean noRoadN = true;
		boolean noRoadE = true;
		boolean noRoadS = true;
		boolean noRoadW = true;
		
		boolean wallneedN = false;
		boolean wallneedE = false;
		boolean wallneedS = false;
		boolean wallneedW = false;
		if(UpGridImage=='□'||UpGridImage=='◇'||UpGridImage=='＠'||UpGridImage=='■'||
		UpGridImage=='◆'||UpGridImage=='★'||UpGridImage=='◎')
			noRoadN = false;
		else
			switch(UpGridImage)
			{
			case '│':
			case '┌':
			case '┐':
			case '├':
			case '┬':
			case '┤':
			case '┼':
				noRoadN = false;
				break;
			}
		if(RightGridImage=='□'||RightGridImage=='◇'||RightGridImage=='＠'||RightGridImage=='■'||
				RightGridImage=='◆'||RightGridImage=='★'||RightGridImage=='◎')
			noRoadE = false;
		else
			switch(RightGridImage)
			{
			case '─':
			case '┐':
			case '┘':
			case '┬':
			case '┤':
			case '┴':
			case '┼':
				noRoadE = false;
				break;
			}

		if(LeftGridImage=='□'||LeftGridImage=='◇'||LeftGridImage=='＠'||LeftGridImage=='■'||
		LeftGridImage=='◆'||LeftGridImage=='★'||LeftGridImage=='◎')
			noRoadW = false;
		else
			switch(LeftGridImage)
			{
			case '─':
			case '┌':
			case '└':
			case '├':
			case '┬':
			case '┴':
			case '┼':
				noRoadW = false;
				break;
			}
		if(DownGridImage=='□'||DownGridImage=='◇'||DownGridImage=='＠'||DownGridImage=='■'||
		DownGridImage=='◆'||DownGridImage=='★'||DownGridImage=='◎')
			noRoadS = false;
		else
			switch(DownGridImage)
			{
			case '│':
			case '└':
			case '┘':
			case '├':
			case '┤':
			case '┴':
			case '┼':
				noRoadS = false;
				break;
			}
		
		switch(GridImage)
		{
		case '─':
			if(noRoadE)
				wallneedE = true;
			if(noRoadW)
				wallneedW = true;
			break;
		case '│':
			if(noRoadN)
				wallneedN = true;
			if(noRoadS)
				wallneedS = true;
			break;
		case '┌':
			if(noRoadE)
				wallneedE = true;
			if(noRoadS)
				wallneedS = true;
			break;
		case '┐':
			if(noRoadW)
				wallneedW = true;
			if(noRoadS)
				wallneedS = true;
			break;
		case '┘':
			if(noRoadW)
				wallneedW = true;
			if(noRoadN)
				wallneedN = true;
			break;
		case '└':
			if(noRoadE)
				wallneedE = true;
			if(noRoadN)
				wallneedN = true;
			break;
		case '├':
			if(noRoadN)
				wallneedN = true;
			if(noRoadE)
				wallneedE = true;
			if(noRoadS)
				wallneedS = true;
			break;
		case '┬':
			if(noRoadW)
				wallneedW = true;
			if(noRoadE)
				wallneedE = true;
			if(noRoadS)
				wallneedS = true;
			break;
		case '┤':
			if(noRoadW)
				wallneedW = true;
			if(noRoadN)
				wallneedN = true;
			if(noRoadS)
				wallneedS = true;
			break;
		case '┴':
			if(noRoadW)
				wallneedW = true;
			if(noRoadN)
				wallneedN = true;
			if(noRoadE)
				wallneedE = true;
			break;
		case '┼':
			if(noRoadS)
				wallneedS = true;
			if(noRoadW)
				wallneedW = true;
			if(noRoadN)
				wallneedN = true;
			if(noRoadE)
				wallneedE = true;
			break;
		}
		
		if(wallneedN)
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+type+"/Wall0.schematic"),new Location(world, Xloc+7, Yloc, Zloc));
		if(wallneedE)
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+type+"/Wall1.schematic"),new Location(world, Xloc+19, Yloc, Zloc+7));
		if(wallneedS)
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+type+"/Wall2.schematic"),new Location(world, Xloc+7,Yloc, Zloc+19));
		if(wallneedW)
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+type+"/Wall3.schematic"),new Location(world, Xloc, Yloc, Zloc+7));
	}
	
	public void DoorCreate(char UpGridImage, char RightGridImage, char LeftGridImage, char DownGridImage,
	long Xloc, int Yloc, long Zloc, int size, World world, String type)
	{
		boolean DoorneedN = false;
		boolean DoorneedE = false;
		boolean DoorneedS = false;
		boolean DoorneedW = false;
		if(UpGridImage!=-1)
			if(UpGridImage=='□'||UpGridImage=='◇'||UpGridImage=='＠'||UpGridImage=='◎')
				DoorneedN = true;
			else
				switch(UpGridImage)
				{
				case '│':
				case '┌':
				case '┐':
				case '├':
				case '┬':
				case '┤':
				case '┼':
					DoorneedN = true;
					break;
				}
		if(RightGridImage!=-1)
			if(RightGridImage=='□'||RightGridImage=='◇'||RightGridImage=='＠'||RightGridImage=='◎')
				DoorneedE = true;
			else
				switch(RightGridImage)
				{
				case '─':
				case '┐':
				case '┘':
				case '┬':
				case '┤':
				case '┴':
				case '┼':
					DoorneedE = true;
					break;
				}

		if(LeftGridImage!=-1)
			if(LeftGridImage=='□'||LeftGridImage=='◇'||LeftGridImage=='＠'||LeftGridImage=='◎')
				DoorneedW = true;
			else
				switch(LeftGridImage)
				{
				case '─':
				case '┌':
				case '└':
				case '├':
				case '┬':
				case '┴':
				case '┼':
					DoorneedW = true;
					break;
				}
		if(DownGridImage!=-1)
			if(DownGridImage=='□'||DownGridImage=='◇'||DownGridImage=='＠'||DownGridImage=='◎')
				DoorneedS = true;
			else
				switch(DownGridImage)
				{
				case '│':
				case '└':
				case '┘':
				case '├':
				case '┤':
				case '┴':
				case '┼':
					DoorneedS = true;
					break;
				}
		
		if(DoorneedN)
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+type+"/Door0.schematic"),new Location(world, Xloc+6, Yloc, Zloc));
		if(DoorneedE)
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+type+"/Door1.schematic"),new Location(world, Xloc+18, Yloc,Zloc+6));
		if(DoorneedS)
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+type+"/Door2.schematic"),new Location(world, Xloc+6,Yloc, Zloc+18));
		if(DoorneedW)
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+type+"/Door3.schematic"),new Location(world, Xloc, Yloc, Zloc+6));
	}

	public void LockedRoomCreate(char GridImage, World world, long Xloc, int Yloc, long Zloc, String DungeonType, char Direction)
	{
		switch(GridImage)
		{
    	case '─' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door1.schematic"),new Location(world, Xloc+18, Yloc,Zloc+6));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door3.schematic"),new Location(world, Xloc, Yloc, Zloc+6));
    		break;
    	case '│' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door0.schematic"),new Location(world, Xloc+6, Yloc, Zloc));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door2.schematic"),new Location(world, Xloc+6,Yloc, Zloc+18));
			break;
    	case '└' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door0.schematic"),new Location(world, Xloc+6, Yloc, Zloc));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door1.schematic"),new Location(world, Xloc+18, Yloc,Zloc+6));
    		break;
    	case '┌' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door1.schematic"),new Location(world, Xloc+18, Yloc,Zloc+6));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door2.schematic"),new Location(world, Xloc+6,Yloc, Zloc+18));
			break;
    	case '┐' :
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door2.schematic"),new Location(world, Xloc+6,Yloc, Zloc+18));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door3.schematic"),new Location(world, Xloc, Yloc, Zloc+6));
			break;
    	case '┘' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door0.schematic"),new Location(world, Xloc+6, Yloc, Zloc));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door3.schematic"),new Location(world, Xloc, Yloc, Zloc+6));
			break;
    	case '┬' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door1.schematic"),new Location(world, Xloc+18, Yloc,Zloc+6));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door2.schematic"),new Location(world, Xloc+6,Yloc, Zloc+18));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door3.schematic"),new Location(world, Xloc, Yloc, Zloc+6));
			break;
    	case '┤' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door0.schematic"),new Location(world, Xloc+6, Yloc, Zloc));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door2.schematic"),new Location(world, Xloc+6,Yloc, Zloc+18));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door3.schematic"),new Location(world, Xloc, Yloc, Zloc+6));
			break;
    	case '┴' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door0.schematic"),new Location(world, Xloc+6, Yloc, Zloc));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door1.schematic"),new Location(world, Xloc+18, Yloc,Zloc+6));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door3.schematic"),new Location(world, Xloc, Yloc, Zloc+6));
			break;
    	case '├' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door0.schematic"),new Location(world, Xloc+6, Yloc, Zloc));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door1.schematic"),new Location(world, Xloc+18, Yloc,Zloc+6));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door2.schematic"),new Location(world, Xloc+6,Yloc, Zloc+18));
			break;
    	case '┼' : 
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door0.schematic"),new Location(world, Xloc+6, Yloc, Zloc));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door1.schematic"),new Location(world, Xloc+18, Yloc,Zloc+6));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door2.schematic"),new Location(world, Xloc+6,Yloc, Zloc+18));
    		filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Door3.schematic"),new Location(world, Xloc, Yloc, Zloc+6));
			break;
		}
		switch(Direction)
		{
		case'N':
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Closed_Door0.schematic"),new Location(world, Xloc+6, Yloc, Zloc));
			break;
		case'E':
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Closed_Door1.schematic"),new Location(world, Xloc+18, Yloc,Zloc+6));
			break;
		case'S':
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Closed_Door2.schematic"),new Location(world, Xloc+6,Yloc, Zloc+18));
			break;
		case'W':
			filePaste(new File("plugins/GoldBigDragonRPG/Dungeon/Schematic/"+DungeonType+"/Closed_Door3.schematic"),new Location(world, Xloc, Yloc, Zloc+6));
			break;
		}
		return;
	}
}
