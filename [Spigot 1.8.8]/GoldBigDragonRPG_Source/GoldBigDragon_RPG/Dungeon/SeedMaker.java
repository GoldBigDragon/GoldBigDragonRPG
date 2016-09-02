package GoldBigDragon_RPG.Dungeon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeedMaker
{
	int size;//던전의 크기
	int Maze_Level;//던전 꼬임 수치
	int TotalRoom;//총 열쇠를 얻을 수 있는 방 개수
	List<Integer> RightPass;//시뮬레이션시 사용 가능한 열쇠를 저장하는 변수.
	List<Integer> PassedRoom;//시뮬레이션시 사용 가능한 열쇠를 저장하는 변수.
	char whenIChoosed;//시뮬레이션시 저장된 방을 거칠 때 이동한 방향 저장 (북/동/남/서 순으로 찾음)
	int whenIsaved;//시뮬레이션시 길이 2갈래 이상으로 갈라진 방 위치를 저장
	boolean isNoExit=false;//시뮬레이션시, 이어지지 않은 방을 이어 진 것으로 계산하는 것을 방지하기 위한 변수.
	int Tryed = 0;
	int StartPoint = 0;
	int BossRoom;
	int[] LoopBreaker = {0,0,0,0,0,0,0,0,0,0};
	
	boolean PerfectDungeon = false;//시뮬 실패시 다시 하도록 하는 변수.
	
	String[] Grid;//던전 배치 그리드를 뜻함.
	String[] KeyGrid;//닫힌 문 배치 그리드를 뜻함.
	int[] RoomCounter;//던전에 방이 생길 때 마다 방이 생기는 순서대로 해당 위치를 저장함.
	
	public int Randomnum(int min, int max)
    {
		return new Random().nextInt(max-min+1)+min;
    }

/*
01. 맵 전체를 땅으로 채운다.
02. 보스방 부터 놓는다.
03. 보스방 입구에서 부터 출발한다.
04. 어떤 새로운 요소를 지을 것인가에 대하여 결정한다.
05. 선택한 사이드(방향) 쪽에 해당 요소를 추가시킬 수 있는 공간이 있는지 확인한다.
06. 만일 추가시킬 공간이 있다면 계속하고, 없다면 3번 항목으로 다시 돌아간다.
07. 선택한 사이드에 해당 요소를 추가 시킨다.
08. 던전이 완성될 때 까지 3번 항목으로 돌아간다.
09. 지도 속 임의 위치에 출발 지점을 생성한다.
10. 출발 지점을 가장 가까운 방과 연결 시킨다.
 */	

	//던전 생성기//
	public String CreateSeed(int DungeonSize, String[] grid, int[] roomcounter, int Maze_Level, String DungeonType)
	{
		this.size = DungeonSize;
		this.Maze_Level = Maze_Level;
		this.Grid = grid;
		this.RoomCounter = roomcounter;
		KeyGrid = new String[size*size];
	    int spot;
	    for(;;)
	    {
	    	TotalRoom = 0;
	    	for(int count = 0; count < size*size;count++)
	    	{
	    		KeyGrid[count] = null;
	    		Grid[count] = null;
	    		RoomCounter[count] = -1;
	    	}
		    BossRoom = setBossRoom();
		    spot = BossRoom;
		    
		    //보스 방 까지 가는 하나의 주된 스트림
	    	setRoom(BossRoom,searchEmptyWall(spot), false);
		    for(int count = 0; count<Maze_Level*3;count ++)
		    {
		    	for(int count2 = size*size-1; count2 >= 0 ;count2--)
		    	{
		    		if(RoomCounter[count2] != -1)
		    		{
		    			spot = RoomCounter[count2];
		    			break;
		    		}
		    	}
		    	setRoom(spot,searchEmptyWall(spot), false);
		    }
	    	
	    	if(TotalRoom>=size-(size/2)-1)
	    		break;
	    }
	    
    	for(int count = (size*size)-1; count >= 0 ;count--)
    	{
    		if(RoomCounter[count] != -1)
    		{
    			Grid[RoomCounter[count]] = "＠";
    			TotalRoom = TotalRoom-1;//열쇠를 얻을 수 있는 방 수를 1줄임. 이유는 스폰 방으로 지정 했기 때문.
    			StartPoint = RoomCounter[count];
    			whenIChoosed = 'V';//V는 갈림길을 만난 적이 없음을 뜻함.
    			whenIsaved = RoomCounter[count];//마찬가지로 -1은 아직 갈림길을 만나지 않았음을 뜻한다.
    			RightPass = new ArrayList<Integer>();
    			PassedRoom = new ArrayList<Integer>();
    			RightPass.add(0,whenIsaved);
	    		DungeonClearSimulation(whenIsaved, whenIChoosed, false);
	    		//던전을 모의로 돌아 클리어 가능하게 방이 이어 졌는지 검사
	    		if(PerfectDungeon==true)
	    			if(DoorCreator()==true)//열쇠방 및 구슬방 구축
	    				return createMap();
	    		return "null";
    		}
    	}
    	return "null";
	}

	public int setBossRoom()
	{
		int bossEnter = 0;
		if(size%2==1)
			bossEnter=bossEnter+size+1;
		for(int count = 0; count < size/2;count++)
			bossEnter = bossEnter + size;
		bossEnter = bossEnter-(size/2)+(size*2)-1;
	    Grid[bossEnter] = "◎";
	    Grid[bossEnter-size+1] = "◆";
	    Grid[bossEnter-size] = "◆";
	    Grid[bossEnter-size-1] = "◆";
	    Grid[bossEnter-(size*2)+1] = "◆";
	    Grid[bossEnter-(size*2)] = "■";
	    Grid[bossEnter-(size*2)-1] = "◆";
	    Grid[bossEnter-(size*3)+1] = "◆";
	    Grid[bossEnter-(size*3)] = "◆";
	    Grid[bossEnter-(size*3)-1] = "◆";
	    Grid[bossEnter-(size*4)] = "★";
		return bossEnter;
	}
	
	public char searchEmptyWall(int location)
	{
		boolean isEmptyN = false;
		boolean isEmptyE = false;
		boolean isEmptyW = false;
		boolean isEmptyS = false;
		if(location+size <= size*size-1)
    		if(Grid[location+size] == null)
    			isEmptyS = true;
		if(location-size >=0)
    		if(Grid[location-size] == null)
    			isEmptyN = true;
		if(location%size == 0)
	    {
    		if(location+1 <= size*size-1)
	    		if(Grid[location+1] == null)
	    			isEmptyE = true;
	    }
    	else if(location%size == size-1)
	    {
    		if(location-1 >=0)
	    		if(Grid[location-1] == null)
	    			isEmptyW = true;
	    }
    	else
    	{
    		if(location+1 <= size*size-1)
	    		if(Grid[location+1] == null)
	    			isEmptyE = true;
    		if(location-1 >= 0)
	    		if(Grid[location-1] == null)
	    			isEmptyW = true;
    	}
		
		
		if(isEmptyN==false&&isEmptyE==false&&isEmptyW==false&&isEmptyS==false)
			return 'V';
		for(int count =0;count < 100;count++)
		{
			char randomnum = (char) Randomnum(0, 3);
				switch(randomnum)
				{
				case 0:
					if(isEmptyE == true)
						return 'E';
					break;
				case 1:
					if(isEmptyW == true)
						return 'W';
					break;
				case 2:
					if(isEmptyN == true)
						return 'N';
					break;
				case 3:
					if(isEmptyS == true)
						return 'S';
					break;
				}
		}
	return 'V';
	}
	
	public boolean EnableContinuouslyRoom_SearchSouth(int location)
	{
		if(location+size <= size*size-1)
		{
			if(Grid[location+size] != "│"&&Grid[location+size] != "┘"&&
			Grid[location+size] != "└"&&Grid[location+size] != "├"&&
			Grid[location+size] != "┤"&&Grid[location+size] != "┴"&&
					Grid[location+size] != "＠"&&Grid[location+size] != "□"&&Grid[location+size] != "◇"&&Grid[location+size] != "┼")
    			return true;
			else
				return false;
		}
		else
			return true;
	}
	
	public boolean EnableContinuouslyRoom_SearchNorth(int location)
	{
		if(location-size >= 0)
		{
			if(Grid[location-size] != "│"&&Grid[location-size] != "┌"&&Grid[location-size] != "┐"&&Grid[location-size] != "├"&&
					Grid[location-size] != "＠"&&Grid[location-size] != "□"&&Grid[location-size] != "◇"&&Grid[location-size] != "┬"&&Grid[location-size] != "┤"&&Grid[location-size] != "┼")
    			return true;
			else
				return false;
		}
		else
			return true;
	}
	
	public boolean EnableContinuouslyRoom_SearchEast(int location)
	{
		if(location+1 <= size*size-1)
		{
			if(Grid[location+1] != "─"&&Grid[location+1] != "┐"&&Grid[location+1] != "┘"&&Grid[location+1] != "┬"&&
					Grid[location+1] != "＠"&&Grid[location+1] != "□"&&Grid[location+1] != "◇"&&Grid[location+1] != "┤"&&Grid[location+1] != "┴"&&Grid[location+1] != "┼")
    			return true;
			else
				return false;
		}
		else
			return true;
	}
	
	public boolean EnableContinuouslyRoom_SearchWest(int location)
	{
		if(location-1 >= 0)
		{
			if(Grid[location-1] != "─"&&Grid[location-1] != "┌"&&Grid[location-1] != "└"&&Grid[location-1] != "├"&&
					Grid[location-1] != "＠"&&Grid[location-1] != "□"&&Grid[location-1] != "◇"&&Grid[location-1] != "┬"&&Grid[location-1] != "┴"&&Grid[location-1] != "┼")
    			return true;
			else
				return false;
		}
		else
			return true;
	}
	
	public boolean EnableContinuouslyRoom(int location, char Direction)
	{
		boolean isEmptyN = false;
		boolean isEmptyE = false;
		boolean isEmptyW = false;
		boolean isEmptyS = false;
		switch(Direction)
		{
		case 'E':isEmptyW=true;break;
		case 'W':isEmptyE=true;break;
		case 'S':isEmptyN=true;break;
		case 'N':isEmptyS=true;break;
		}
		if(location/size == 0)
		{
    		if(EnableContinuouslyRoom_SearchSouth(location))
    			isEmptyS = true;
    		if(EnableContinuouslyRoom_SearchNorth(location))
    			isEmptyN = true;
    		if(EnableContinuouslyRoom_SearchEast(location))
    			isEmptyE = true;
			isEmptyW = true;
		}
    	else if(location/size == size-1)
    	{
    		if(EnableContinuouslyRoom_SearchSouth(location))
    			isEmptyS = true;
    		if(EnableContinuouslyRoom_SearchNorth(location))
    			isEmptyN = true;
    		if(EnableContinuouslyRoom_SearchWest(location))
    			isEmptyW = true;
    		isEmptyE = true;
    	}
    	else
    	{
    		if(EnableContinuouslyRoom_SearchSouth(location))
    			isEmptyS = true;
    		if(EnableContinuouslyRoom_SearchNorth(location))
    			isEmptyN = true;
    		if(EnableContinuouslyRoom_SearchWest(location))
    			isEmptyE = true;
    		if(EnableContinuouslyRoom_SearchWest(location))
    			isEmptyW = true;
    	}
		
		
		if(isEmptyN==true&&isEmptyE==true&&isEmptyW==true&&isEmptyS==true)
			return true;
		else
			return false;
	}
	

	public String randomRoom()
	{
		byte number = (byte) Randomnum(0, 3);
		TotalRoom=TotalRoom+1;
		if(number==0)
			return "◇";//미믹방
		return "□";
	}
	
	public int setRoom(int location, char side, boolean randomRoadConnect)
	{
		char type;
		switch(side)
		{
		case 'N' :
			if(location-size >=0&& location <= size*size-1)
			{
				type = searchEmptyWall(location-size);
				if(type == 'N')
					RoomCreate(location-size, "│",type);
				else if(type == 'E')
					RoomCreate(location-size, "┌",type);
				else if(type == 'W')
					RoomCreate(location-size, "┐",type);
				setRoom(location-size, type, randomRoadConnect);
				return location-size;
			}
			break;
		case 'E' :
			if(location+1 <= size*size-1&& location+1 >= 0)
			{
				type = searchEmptyWall(location+1);
				if(type == 'N')
					RoomCreate(location+1, "┘",type);
				else if(type == 'S')
					RoomCreate(location+1, "┐",type);
				else if(type == 'E')
					RoomCreate(location+1, "─",type);
				setRoom(location+1, type, randomRoadConnect);
				return location+1;
			}
			break;
		case 'W' :
			if(location-1 >= 0 && location <= size*size-1)
			{
				type = searchEmptyWall(location-1);
				if(type == 'N')
					RoomCreate(location-1, "└",type);
				else if(type == 'S')
					RoomCreate(location-1, "┌",type);
				else if(type == 'W')
					RoomCreate(location-1, "─",type);
				setRoom(location-1, type, randomRoadConnect);
				return location-1;
			}
			break;
		case 'S' :
			if(location+size <= size*size-1&& location+size >= 0)
			{
				type = searchEmptyWall(location+size);
				if(type == 'S')
					RoomCreate(location+size, "│",type);
				else if(type == 'E')
					RoomCreate(location+size, "└",type);
				else if(type == 'W')
					RoomCreate(location+size, "┘",type);
				setRoom(location+size, type, randomRoadConnect);
				return location+size;
			}
			break;
		}
		return -1;
	}
	
	public void randomRoadConnect()
	{
		int randomroad;
		char side;
		for(int count = 0; count < 100; count ++)
		{
			randomroad = Randomnum(0, (size*size)-1);
			if(Grid[randomroad] != null&&Grid[randomroad] != "◆"&&Grid[randomroad] != "■"&&Grid[randomroad] != "★"&&Grid[randomroad] != "◎"
					&&Grid[randomroad] != "＠")
			{
				side = searchEmptyWall(randomroad);
				if(side != 'V')
				{
					switch(Grid[randomroad])
					{
					case "─" :
						switch(side)
						{
						case 'N' : Grid[randomroad] = "┴"; setRoom(randomroad, 'N', true);break;
						case 'S' : Grid[randomroad] = "┬"; setRoom(randomroad, 'S', true);break;
						}
						break;
					case "│" :
						switch(side)
						{
						case 'E' : Grid[randomroad] = "├"; setRoom(randomroad, 'E', true);break;
						case 'W' : Grid[randomroad] = "┤"; setRoom(randomroad, 'W', true);break;
						}
						break;
					case "┌" :
						switch(side)
						{
						case 'N' : Grid[randomroad] = "├"; setRoom(randomroad, 'N', true);break;
						case 'W' : Grid[randomroad] = "┬"; setRoom(randomroad, 'W', true);break;
						}
						break;
					case "┐" :
						switch(side)
						{
						case 'N' : Grid[randomroad] = "┤"; setRoom(randomroad, 'N', true);break;
						case 'E' : Grid[randomroad] = "┬"; setRoom(randomroad, 'E', true);break;
						}
						break;
					case "┘" :
						switch(side)
						{
						case 'S' : Grid[randomroad] = "┤"; setRoom(randomroad, 'S', true);break;
						case 'E' : Grid[randomroad] = "┴"; setRoom(randomroad, 'E', true);break;
						}
						break;
					case "└" :
						switch(side)
						{
						case 'S' : Grid[randomroad] = "├"; setRoom(randomroad, 'S', true);break;
						case 'W' : Grid[randomroad] = "┴"; setRoom(randomroad, 'W', true);break;
						}
						break;
					case "├" :
						switch(side)
						{
						case 'W' : Grid[randomroad] = "┼"; setRoom(randomroad, 'W', true);break;
						}
						break;
					case "┬" :
						switch(side)
						{
						case 'N' : Grid[randomroad] = "┼"; setRoom(randomroad, 'N', true);break;
						}
						break;
					case "┤" :
						switch(side)
						{
						case 'E' : Grid[randomroad] = "┼"; setRoom(randomroad, 'E', true);break;
						}
						break;
					case "┴" :
						switch(side)
						{
						case 'S' : Grid[randomroad] = "┼"; setRoom(randomroad, 'S', true);break;
						}
						break;
					}
				}
			}
		}
	}
	
	public void RoomCreate(int location, String elseput, char Direction)
	{
		if(Randomnum(0, 4)==0)
		{
			if(EnableContinuouslyRoom(location, Direction))
			{
				RoomCounter[TotalRoom] = location;
				Grid[location] = randomRoom();
			}
			else
				Grid[location] = elseput;
		}
		else
			Grid[location] = elseput;
	}

	public boolean SearchRoad(int location, char Direction)
	{
		String Shape = null;
		String NowShape = Grid[location];
		switch(Direction)
		{
		case 'N':
			if(location-size >= 0)
			{
				Shape = Grid[location-size];
				if(isNoExit)
				{
					if(Shape == "◎"||Shape == "◇"||Shape == "□"||Shape == "│"||Shape == "┌"||Shape == "┐"||
					Shape == "├"||Shape == "┬"||Shape == "┤"||Shape == "┼"||Shape == "△"||Shape == "▲")
						if(NowShape == "＠"||NowShape=="│"||NowShape=="┘"||NowShape=="└"||NowShape=="├"||
						NowShape=="┤"||NowShape=="┴"||NowShape=="┼"||NowShape=="◇"
						||NowShape=="□"||NowShape=="◎")
							return true;
				}
				else
					if(Shape == "│"||Shape == "┌"||Shape == "┐"||
					Shape == "├"||Shape == "┬"||Shape == "┤"||Shape == "┼"||Shape == "△"||Shape == "▲")
						return true;
			}
			return false;
		case 'E':
			if(location%size == 0||location%size != size-1)
			{
				if(location+1 <= size*size-1)
				{
					Shape = Grid[location+1];
					if(isNoExit)
					{
						if(Shape == "◎"||Shape == "◇"||Shape == "□"||Shape == "─"||Shape == "┐"||Shape == "┘"||
	    				Shape == "┬"||Shape == "┤"||Shape == "┴"||Shape == "┼"||Shape == "▷"||Shape == "▶")
							if(NowShape == "＠"||NowShape == "◎"||NowShape == "◇"||NowShape == "□"||NowShape == "─"||NowShape == "┌"||
									NowShape == "└"||NowShape == "├"||NowShape == "┬"||NowShape == "┴"||NowShape == "┼")
								return true;
					}
					else
						if(Shape == "─"||Shape == "┐"||Shape == "┘"||
	    				Shape == "┬"||Shape == "┤"||Shape == "┴"||Shape == "┼"||Shape == "▷"||Shape == "▶")
							return true;
				}
			}
			return false;
		case 'S':
    		if(location+size <= size*size-1)
    		{
    			Shape = Grid[location+size];
				if(isNoExit)
				{
					if(Shape == "◎"||Shape == "◇"||Shape == "□"||Shape == "│"||Shape == "┘"||Shape == "└"||
	        			Shape == "├"||Shape == "┤"||Shape == "┴"||Shape == "┼"||Shape == "▽"||Shape == "▼")
						if(NowShape == "＠"||NowShape == "◎"||NowShape == "◇"||NowShape == "□"||NowShape == "│"||NowShape == "┌"||
								NowShape == "┐"||NowShape == "├"||NowShape == "┬"||NowShape == "┤"||NowShape == "┼")
							return true;
				}
				else
	        		if(Shape == "│"||Shape == "┘"||Shape == "└"||
	        			Shape == "├"||Shape == "┤"||Shape == "┴"||Shape == "┼"||Shape == "▽"||Shape == "▼")
	        			return true;
    		}
			return false;
		case 'W':
			if(location%size == size-1||location%size != 0)
			{
	    		if(location-1 >=0)
	    		{
	    			Shape = Grid[location-1];
					if(isNoExit)
					{
			    		if(Shape == "◎"||Shape == "◇"||Shape == "□"||Shape == "─"||Shape == "┌"||Shape == "└"||
			    		Shape == "├"||Shape == "┬"||Shape == "┴"||Shape == "┼"||Shape == "◁"||Shape == "◀")
			    			if(NowShape == "＠"||NowShape == "◎"||NowShape == "◇"||NowShape == "□"||NowShape == "─"||NowShape == "┐"||
			    					NowShape == "┘"||NowShape == "┬"||NowShape == "┤"||NowShape == "┴"||NowShape == "┼")
			    				return true;
					}
					else
			    		if(Shape == "─"||Shape == "┌"||Shape == "└"||
			    		Shape == "├"||Shape == "┬"||Shape == "┴"||Shape == "┼"||Shape == "◁"||Shape == "◀")
			    			return true;
	    		}
			}
			return false;
		}
		return false;
	}
	
	public void DungeonClearSimulation(int loc, char WhereIcome, boolean NoClear)
	{
		//한번 간 길은 PassedRoom에 저장하여 다시는 지나치지 않는다.
		//내가 다음 이동할 방을 찾는다.
		//이동 가능한 방향이 많다면, 북/동/남/서 순서로 돌아본다.
		//이때, 현재 내가 간 방향을 NowDirection 변수에 저장해 놓는다.
		//방을 만나면 우선 가지 않는다.
		//방 옆에 방이 있을수도 있기 때문이다.
		//방을 제외한 방향으로 길이 있다면 그쪽으로 간다.
		//방을 만나면 위치를 whenIsaved에 저장 해 놓고,
		//그 방에서 지나온 길은 whenIChoosed에 저장한다.
		//만일 이동 할 방이 없다면,
		//whenIsaved 위치로 돌아간다.
		//던전 보스룸 입구까지 오면 클리어 가능한 던전이라고 판명.
		//열쇠방 구축에 들어간다.
		
		boolean isEmptyN = false;
		boolean isEmptyS = false;
		boolean isEmptyE = false;
		boolean isEmptyW = false;

		isEmptyN=(SearchRoad(loc, 'N'));
		isEmptyS=(SearchRoad(loc, 'S'));
		isEmptyW=(SearchRoad(loc, 'W'));
		isEmptyE=(SearchRoad(loc, 'E'));

		if(isNoExit==false)
		{
			switch(WhereIcome)
			{
				case 'N':
					isEmptyN = false;break;
				case 'E':
					isEmptyE = false;break;
				case 'S':
					isEmptyS = false;break;
				case 'W':
					isEmptyW = false;break;
			}
		}
		else
		{
			switch(WhereIcome)
			{
				case 'N':
					isEmptyN = false;break;
				case 'E':
					isEmptyE = false;break;
				case 'S':
					isEmptyS = false;break;
				case 'W':
					isEmptyW = false;break;
			}
			if(isEmptyN)
				for(int count = 0; count <PassedRoom.size();count++)
					if(PassedRoom.get(count)==loc-size)
					{
						isEmptyN = false;break;
					}
			if(isEmptyE)
				for(int count = 0; count <PassedRoom.size();count++)
					if(PassedRoom.get(count)==loc+1)
					{
						isEmptyE = false;break;
					}
			if(isEmptyS)
				for(int count = 0; count <PassedRoom.size();count++)
					if(PassedRoom.get(count)==loc+size)
					{
						isEmptyS = false;break;
					}
			if(isEmptyW)
				for(int count = 0; count <PassedRoom.size();count++)
					if(PassedRoom.get(count)==loc-1)
					{
						isEmptyW = false;break;
					}
		}
		if(Grid[loc]=="◎")
		{
			PerfectDungeon = true;
			return;
		}
		boolean PassAdded = false;
		for(int count2 = 0; count2 <RightPass.size();count2++)
			if(RightPass.get(count2)==loc)
				PassAdded = true;
		if(PassAdded==false)
			RightPass.add(0,loc);
		PassedRoom.add(0, loc);
		if(Grid[loc]=="◇"||Grid[loc]=="□"||Grid[loc]=="＠")
		{
			whenIChoosed = (char) 'V';
			whenIsaved = loc;
			if(NoClear==false)
				isNoExit = false;
			NoClear = true;
		}
		
		if(isEmptyN==false&&isEmptyE==false&&isEmptyW==false&&isEmptyS==false)
		{
			//모든 길이 막혔을 경우 마지막 저장 위치에서 다시 시작함.
			if(isNoExit==false)
			{
				isNoExit = true;
				if(LoopBreakerUse(0))
					return;
				DungeonClearSimulation(loc, WhereIcome, false);
				return;
			}
			if(LoopBreakerUse(1))
				return;
			for(int count = 0; count < RightPass.size(); count++)
				if(RightPass.get(0)!=whenIsaved)
					RightPass.remove(0);
				else
				{
					for(int count2 = 0; count2 < RightPass.size(); count2++)
					{
						if(RightPass.get(count2)==StartPoint)
							for(int count3 = count2+1; count3 < RightPass.size(); count3++)
								RightPass.remove((count2+1));
					}
					break;
				}
			DungeonClearSimulation(whenIsaved, whenIChoosed, true);
			return;
		}
		
		char counter = 0;
		if(isEmptyN)
			counter++;
		if(isEmptyE)
			counter++;
		if(isEmptyS)
			counter++;
		if(isEmptyW)
			counter++;
		if(counter >=2)//길이 2개 이상 나 있을 경우
		{
			if(isEmptyN)//언제나 그렇듯 북쪽부터 조사한다.
			{
				if(LoopBreakerUse(2))
					return;
				whenIChoosed = 'N';
				DungeonClearSimulation(loc-size, 'S', false);
			}
			else if(isEmptyE)//다음은 동쪽
			{
				if(LoopBreakerUse(3))
					return;
				whenIChoosed = 'E';
				DungeonClearSimulation(loc+1, 'W', false);
			}
			else if(isEmptyS)//다음은 남쪽
			{
				if(LoopBreakerUse(4))
					return;
				whenIChoosed = 'S';
				DungeonClearSimulation(loc+size, 'N', false);
			}
			else if(isEmptyW)//마지막 서쪽
			{
				if(LoopBreakerUse(5))
					return;
				whenIChoosed = 'W';
				DungeonClearSimulation(loc-1, 'E', false);
			}
			return;
		}
		else//길이 하나만 있을 경우
		{
			
			if(isEmptyN)//언제나 그렇듯 북쪽부터 조사한다.
			{
				if(LoopBreakerUse(6))
					return;
				DungeonClearSimulation(loc-size, 'S', NoClear);
			}
			else if(isEmptyE)//다음은 동쪽
			{
				if(LoopBreakerUse(7))
					return;
				DungeonClearSimulation(loc+1, 'W', NoClear);
			}
			else if(isEmptyS)//다음은 남쪽
			{
				if(LoopBreakerUse(8))
					return;
				DungeonClearSimulation(loc+size, 'N', NoClear);
			}
			else if(isEmptyW)//마지막 서쪽
			{
				if(LoopBreakerUse(9))
					return;
				DungeonClearSimulation(loc-1, 'E', NoClear);
			}
			return;
		}
	}
	
	public boolean DoorCreator()
	{
		for(;;)
		{
			int LastNum = RightPass.get(0);
			List<Integer> Wired = new ArrayList<Integer>();
			for(int count = 0; count < RightPass.size(); count++)
			{
				int Gap = LastNum-RightPass.get(count);
				if(Gap==0||Gap==1||Gap==-1||Gap==size||Gap==-1*size)
					LastNum = RightPass.get(count);
				else
					Wired.add(RightPass.get(count));
			}
			if(Wired.size()==0)
				break;
			else
			{
				int counter = Wired.size()-1;
				for(int count = 0; counter >= 0; count++)
				{
					if(RightPass.get(count)==Wired.get(counter))
					{
						RightPass.remove(count);
						counter--;
						count = 0;
					}
				}
			}
		}
		/*열쇠방 구축
		열쇠를 얻을 수 있는 ◇와 □ 방을 지날 때 마다
		RightPass 변수에 저장해 둔 위치값을 불러온다.
		순서에 맞게 이동 시뮬을 돌려 1Way 루트를 만든다.
		루트 대로만 움직이면서 중간 중간 열쇠방을 만들어 준다.
		열쇠방 개수가 RightPass 개수와 동일할 경우,
		그 다음부터는 모두 구슬방으로 대체한다.
		
		확률에 따라 ⊙(구슬방)으로 교체한다.
		열쇠는 모자라도 되지만 1개 이상 남으면 안된다.
		(1개 남는다면 보스방 열쇠. 안남으면 보스방도 구슬방)
		
		구슬방 - 문 열리는 구슬은 아래 아랫 부분을 레드스톤 블록으로 변경.
		일반 방 - 상자 위위위 부분에 표지판 설치. 던전 이름 및 생성 UTC 설정
		 */
		boolean NotFoundKey = true;
		char NextDirection = 'V';
		int TotalKeyRoom = 0;
		int key = 0;
		for(int count = RightPass.size()-1; count >= 0; count--)
		{
			String Shape = Grid[RightPass.get(count)];
			String KShape = KeyGrid[RightPass.get(count)];
			if(Shape.compareTo("□")==0||Shape.compareTo("◇")==0)
				key=key+1;
			else if(Shape.compareTo("□")!=0&&Shape.compareTo("◇")!=0
				&&Shape.compareTo("＠")!=0)
			{
				if(count-1 >= 0)
				{
					if(RightPass.get(count) - RightPass.get(count-1) == -1)
						NextDirection = 'E';
					else if(RightPass.get(count) - RightPass.get(count-1) == 1)
						NextDirection = 'W';
					else if(RightPass.get(count) - RightPass.get(count-1) == size)
						NextDirection = 'N';
					else if(RightPass.get(count) - RightPass.get(count-1) == -1*size)
						NextDirection = 'S';
					else
						NextDirection = 'V';
				}
				else
					break;
				if(key>0)
				{
					NotFoundKey = false;
					//현재 까지 갈림길을 만나지 않고 진행 중이었다면
					//길일 경우를 판단한 다음, 열쇠로 열어야 할 방이 남아 있을 시
					//현재 내 진행 방향대로 열쇠방을 만들어 줌.
					switch(NextDirection)
					{
					case 'N':
						KeyGrid[RightPass.get(count)] = "▲";break;
					case 'E':
						KeyGrid[RightPass.get(count)] = "▶";break;
					case 'S':
						KeyGrid[RightPass.get(count)] = "▼";break;
					case 'W':
						KeyGrid[RightPass.get(count)] = "◀";break;
					}
					TotalKeyRoom++;
					key--;
				}
				else if(NotFoundKey==false&&key==0)
				{
					if(Maze_Level >= Randomnum(0, 20))
					{
						if(count-1 >= 0)
						{
							boolean CanCreateThat = false;
							int loc = RightPass.get(count);
							if(KShape!=null)
							{
								Shape = Grid[RightPass.get(count-1)];
								if(Shape.compareTo("□")!=0&&Shape.compareTo("◇")!=0
									&&Shape.compareTo("＠")!=0&&KShape.compareTo("▲")!=0
									&&KShape.compareTo("▼")!=0&&KShape.compareTo("◀")!=0
									&&KShape.compareTo("▶")!=0)
									CanCreateThat=true;
							}
							else
								CanCreateThat=true;
							if(CanCreateThat)
							{
								switch(NextDirection)
								{
								case 'N':
									KeyGrid[loc] = "△";break;
								case 'E':
									KeyGrid[loc] = "▷";break;
								case 'S':
									KeyGrid[loc] = "▽";break;
								case 'W':
									KeyGrid[loc] = "◁";break;
								}
							}
						}
					}
				}
			}
		}
		if(!(TotalKeyRoom == TotalRoom-1||TotalKeyRoom == TotalRoom))
			for(int count = RightPass.size()-1; count >= 0; count--)
			{
				String Shape = KeyGrid[RightPass.get(count)];
				if(Shape!=null)
					if(Shape.compareTo("▲")==0&&Shape.compareTo("▼")==0
					&&Shape.compareTo("◀")==0&&Shape.compareTo("▶")==0)
					{
						TotalKeyRoom--;
						KeyGrid[RightPass.get(count)]=null;
						if(TotalKeyRoom == TotalRoom-1||TotalKeyRoom == TotalRoom)
							break;
					}
			}
		if(!(TotalKeyRoom == TotalRoom-1||TotalKeyRoom==TotalRoom))
			return false;
		if(TotalKeyRoom==TotalRoom-1)//보스방을 열쇠로 열게
			KeyGrid[BossRoom] = "♠";
		else if(TotalKeyRoom==TotalRoom)//보스방은 구슬 방
			KeyGrid[BossRoom] = "♥";
		else
			return false;
		return true;
	}
	
	public boolean LoopBreakerUse(int index)
	{
		for(int count = 0; count <LoopBreaker.length;count++)
			if(count!=index)
				LoopBreaker[count] = 0;
		LoopBreaker[index]++;
		if(LoopBreaker[index] >= 55)
			return true;
		else
			return false;
	}
	
	
	public String createMap()
	{
		String seed = size+"_"+BossRoom+"_"+StartPoint+"_"+TotalRoom;
		File filename = new File("plugins/GoldBigDragonRPG/Dungeon/Seed/" + seed + ".txt");
	    try
	    {
	      if (!filename.exists())
	      {
	  	    File F1 = new File("plugins/GoldBigDragonRPG");
		    File F2 = new File("plugins/GoldBigDragonRPG/Dungeon");
		    File F3 = new File("plugins/GoldBigDragonRPG/Dungeon/Seed");
		    F1.mkdir();
		    F2.mkdir();
		    F3.mkdir();
	        filename.createNewFile();
	      }
	      BufferedWriter Write = new BufferedWriter(new FileWriter(filename));
	      String s = "";
		  int counter = 0;
		  for(int count=0;count<(size*size);count++)
		  {
			  counter=counter+1;
			  if(Grid[count]==null)
			  {
				  Grid[count]= "Ｘ";
			  }
			  s = s+Grid[count];
			  if(counter==size)
			  {
				  s = s+"\r\n";
				  counter=0;
			  }
		  }		  
		  Write.append(s);
	      Write.flush();
	      Write.close();
	    }
	    catch (IOException localIOException){}

		filename = new File("plugins/GoldBigDragonRPG/Dungeon/Seed/" + seed + "_KeyGrid.txt");
	    try
	    {
	      if (!filename.exists())
	      {
	  	    File F1 = new File("plugins/GoldBigDragonRPG");
		    File F2 = new File("plugins/GoldBigDragonRPG/Dungeon");
		    File F3 = new File("plugins/GoldBigDragonRPG/Dungeon/Seed");
		    F1.mkdir();
		    F2.mkdir();
		    F3.mkdir();
	        filename.createNewFile();
	      }
	      BufferedWriter Write = new BufferedWriter(new FileWriter(filename));
	      String s = "";
		  int counter = 0;
		  for(int count=0;count<(size*size);count++)
		  {
			  counter=counter+1;
			  if(KeyGrid[count]==null)
				  KeyGrid[count]= "Ｘ";
			  s = s+KeyGrid[count];
			  if(counter==size)
			  {
				  s = s+"\r\n";
				  counter=0;
			  }
		  }		  
		  Write.append(s);
	      Write.flush();
	      Write.close();
	    }
	    catch (IOException localIOException){}
	    return seed;
	}
}
