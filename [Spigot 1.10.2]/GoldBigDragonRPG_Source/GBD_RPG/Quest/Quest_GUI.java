package GBD_RPG.Quest;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;

import GBD_RPG.ServerTick.ServerTick_Main;
import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_GUI;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Quest_GUI extends Util_GUI
{
	public void MyQuestListGUI(Player player, short page)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		String UniqueCode = "§0§0§5§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0퀘스트 목록 : " + (page+1));

		Object[] a = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
		String QuestType = "";
		int ItemID = 0;
		byte ItemAmount = 1;
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(QuestList.contains(a[count].toString())==false)
			{
				PlayerQuestList.removeKey("Started."+a[count].toString());
				PlayerQuestList.saveConfig();
			}
			else
			{
				switch(QuestList.getString(a[count].toString() + ".Type"))
				{
				case "N" :
					QuestType = "[일반]";
					ItemID = 340;
					break;
				case "R" :
					QuestType = "[반복]";
					ItemID = 386;
					break;
				case "D" :
					QuestType = "[일일]";
					ItemID = 403;
					break;
				case "W" :
					QuestType = "[주간]";
					ItemID = 403;
					ItemAmount = 7;
					break;
				case "M" :
					QuestType = "[월간]";
					ItemID = 403;
					ItemAmount = 31;
					break;
				}
				if(count > a.length || loc >= 45) break;

				switch(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".Type"))
				{
				case "Nevigation":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"화살표를 따라가자.",""), loc, inv);
					break;
				case "Choice":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"하고싶은 말을 선택하자.","",ChatColor.YELLOW+"[좌클릭시 선택지 확인.]"), loc, inv);
					break;
				case "Script" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".NPCname")+ChatColor.WHITE+"와 대화를 해 보자."), loc, inv);
					break;
				case "PScript" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+"[좌클릭시 독백 확인]"), loc, inv);
					break;
				case "Visit" :
					YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
					String AreaWorld = AreaList.getString(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".World");
					String AreaName = AreaList.getString(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".Name");
					int AreaX = AreaList.getInt(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".SpawnLocation.X");
					short AreaY = (short) AreaList.getInt(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".SpawnLocation.Y");
					int AreaZ = AreaList.getInt(QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".AreaName")+".SpawnLocation.Z");
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+AreaName+ChatColor.WHITE+" 지역을 방문하자."
							,ChatColor.YELLOW + "월드 : "+ChatColor.WHITE+AreaWorld,ChatColor.YELLOW + "X 좌표 : "+ChatColor.WHITE+""+AreaX,ChatColor.YELLOW + "Y 좌표 : "+ChatColor.WHITE+""+AreaY,ChatColor.YELLOW + "Z 좌표 : "+ChatColor.WHITE+""+AreaZ), loc, inv);
					break;
				case "Talk" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".TargetNPCname")+ChatColor.WHITE+"에게 말을 걸어 보자."), loc, inv);
					break;
				case "Give" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".TargetNPCname")+ChatColor.WHITE+"가 부탁한",ChatColor.WHITE+"물품을 전달하자.","",ChatColor.YELLOW+"[좌클릭시 전달 품목 확인.]"), loc, inv);
					break;
				case "Hunt":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"목표 대상을 처치하자.","",ChatColor.YELLOW+"[좌클릭시 처치 대상 확인]"), loc, inv);
					break;
				case "Harvest":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"블록을 채집하자.","",ChatColor.YELLOW+"[좌클릭시 채집 블록 확인]"), loc, inv);
					break;
				case "Present" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count].toString(), ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".TargetNPCname")+ChatColor.WHITE+"에게",ChatColor.WHITE+"보상을 받자.","",ChatColor.YELLOW+"[좌클릭시 보상 확인.]"), loc, inv);
					break;
				}
				loc++;
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void AllOfQuestListGUI(Player player, short page,boolean ChoosePrevQuest)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0전체 퀘스트 목록 : " + (page+1));

		Object[] a = QuestList.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String QuestName = a[count].toString();
			Object[] QuestFlow= QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
			if(count > a.length || loc >= 45) break;
			if(ChoosePrevQuest == false)
			{
				switch(QuestList.getString(a[count].toString() + ".Type"))
				{
				case "N" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 340,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일반 퀘스트","",ChatColor.YELLOW+"[우클릭시 세부 설정을 합니다.]",ChatColor.RED+"[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case "R" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 386,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 반복 퀘스트","",ChatColor.YELLOW+"[우클릭시 세부 설정을 합니다.]",ChatColor.RED+"[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case "D" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일일 퀘스트","",ChatColor.YELLOW+"[우클릭시 세부 설정을 합니다.]",ChatColor.RED+"[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case "W" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일주 퀘스트","",ChatColor.YELLOW+"[우클릭시 세부 설정을 합니다.]",ChatColor.RED+"[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case "M" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 한달 퀘스트","",ChatColor.YELLOW+"[우클릭시 세부 설정을 합니다.]",ChatColor.RED+"[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				}
			}
			else
			{
				switch(QuestList.getString(a[count].toString() + ".Type"))
				{
				case "N" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 340,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일반 퀘스트",""), loc, inv);
					break;
				case "R" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 386,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 반복 퀘스트",""), loc, inv);
					break;
				case "D" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일일 퀘스트",""), loc, inv);
					break;
				case "W" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일주 퀘스트",""), loc, inv);
					break;
				case "M" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.length+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 한달 퀘스트",""), loc, inv);
					break;
				}
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		if(ChoosePrevQuest == false)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 퀘스트", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 퀘스트를 생성합니다."), 49, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+ChoosePrevQuest), 53, inv);
		player.openInventory(inv);
	}
	
	public void FixQuestGUI(Player player, short page, String QuestName)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode +"§0퀘스트 흐름도 : " + (page+1));
		Object[] a = QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			switch(QuestList.getString(QuestName+".FlowChart."+count+".Type"))
			{
				case "Cal":
				switch(QuestList.getInt(QuestName+".FlowChart."+count+".Comparison"))
				{
				case 1:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 계산","",ChatColor.DARK_AQUA+"[     계산 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 ＋ "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 2:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 계산","",ChatColor.DARK_AQUA+"[     계산 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 － "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 3:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 계산","",ChatColor.DARK_AQUA+"[     계산 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 × "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 4:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 계산","",ChatColor.DARK_AQUA+"[     계산 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 ÷ "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 5:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 계산","",ChatColor.DARK_AQUA+"[     계산 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 ％ "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				}
				break;
			case "IF":
				switch(QuestList.getInt(QuestName+".FlowChart."+count+".Comparison"))
				{
				case 1:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"타입 : IF","",ChatColor.DARK_AQUA+"[     비교 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 == "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 2:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"타입 : IF","",ChatColor.DARK_AQUA+"[     비교 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 != "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 3:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"타입 : IF","",ChatColor.DARK_AQUA+"[     비교 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 > "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 4:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"타입 : IF","",ChatColor.DARK_AQUA+"[     비교 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 < "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 5:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"타입 : IF","",ChatColor.DARK_AQUA+"[     비교 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 >= "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 6:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"타입 : IF","",ChatColor.DARK_AQUA+"[     비교 식     ]",ChatColor.DARK_AQUA+"플레이어 변수 <= "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 7:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"타입 : IF","",ChatColor.DARK_AQUA+"[     비교 식     ]",ChatColor.DARK_AQUA+""+QuestList.getInt(QuestName+".FlowChart."+count+".Min")+" <= 플레이어 변수 <= "+QuestList.getInt(QuestName+".FlowChart."+count+".Max"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				}
				break;
			case "QuestFail":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 166,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 퀘스트 실패","",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "QuestReset":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 395,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 퀘스트 초기화","",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "ELSE":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 167,0,1,Arrays.asList(ChatColor.WHITE+"타입 : ELSE","",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "ENDIF":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 191,0,1,Arrays.asList(ChatColor.WHITE+"타입 : ENDIF","",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "VarChange":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 143,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 변수 변경",ChatColor.WHITE+"변경 값 : " + QuestList.getInt(QuestName+".FlowChart."+count+".Value") ,"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Choice":
				int button = QuestList.getConfigurationSection(QuestName+".FlowChart."+count+".Choice").getKeys(false).size();
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 72,0,button,Arrays.asList(ChatColor.WHITE+"타입 : 선택",ChatColor.WHITE+"선택지 개수 : " +button+"개" ,"",ChatColor.YELLOW+"[좌클릭시 선택창 확인]","",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Nevigation":
			{
				String UTC = QuestList.getString(QuestName+".FlowChart."+count+".NeviUTC");
				YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
				if(NavigationConfig.contains(UTC))
				{
					String NaviName = NavigationConfig.getString(UTC+".Name");
					String world = NavigationConfig.getString(UTC+".world");
					int x = NavigationConfig.getInt(UTC+".x");
					short y = (short) NavigationConfig.getInt(UTC+".y");
					int z = NavigationConfig.getInt(UTC+".z");
					int Time = NavigationConfig.getInt(UTC+".time");
					short sensitive = (short) NavigationConfig.getInt(UTC+".sensitive");
					byte ShowArrow = (byte) NavigationConfig.getInt(UTC+".ShowArrow");
					
					String TimeS = ChatColor.DARK_AQUA+"<도착할 때 까지 유지>";
					String sensitiveS = ChatColor.BLUE+"<반경 "+sensitive+"블록 이내를 도착지로 판정>";
					String ShowArrowS = ChatColor.DARK_AQUA+"<기본 화살표 모양>";
					if(Time >= 0)
						TimeS = ChatColor.DARK_AQUA+"<"+Time+"초 동안 유지>";
					switch(ShowArrow)
					{
					default:
						ShowArrowS = ChatColor.DARK_AQUA+"<기본 화살표 모양>";
						break;
					}
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 395,0,1,Arrays.asList(
					ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
					ChatColor.BLUE+"[도착 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,
					ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,sensitiveS,"",
					ChatColor.DARK_AQUA+"[기타 옵션]",TimeS,ShowArrowS,""
					,ChatColor.RED+"[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				}
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 166,0,1,Arrays.asList(ChatColor.RED+"[네비게이션을 찾을 수 없습니다!]","",ChatColor.RED+"[Shift + 우클릭시 삭제됩니다.]"),loc,inv);
			}
				break;
			case "Whisper":
			{
				String script3 = ChatColor.WHITE+"타입 : 귓말%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%"+ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptA3 = script3.split("%enter%");
				for(byte counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = ChatColor.WHITE + scriptA3[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 421,0,1,Arrays.asList(scriptA3), loc, inv);
			}
			break;
			case "BroadCast":
			{
				String script3 = ChatColor.WHITE+"타입 : 전체%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%"+ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptA3 = script3.split("%enter%");
				for(byte counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = ChatColor.WHITE + scriptA3[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 138,0,1,Arrays.asList(scriptA3), loc, inv);
			}
			break;
			case "Script":
				String script = ChatColor.WHITE+"타입 : 대사%enter%"+ChatColor.WHITE+"말하는 주체 : "+QuestList.getString(QuestName+".FlowChart."+count+".NPCname")+"%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Script")+"%enter% %enter%"+ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptA = script.split("%enter%");
				for(byte counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] = ChatColor.WHITE + scriptA[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 323,0,1,Arrays.asList(scriptA), loc, inv);
			break;
			case "PScript":
				String script3 = ChatColor.WHITE+"타입 : 대사%enter%"+ChatColor.WHITE+"말하는 주체 : 플레이어%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%"+ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptA3 = script3.split("%enter%");
				for(byte counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = ChatColor.WHITE + scriptA3[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 323,0,1,Arrays.asList(scriptA3), loc, inv);
			break;
			case "Visit":
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 345,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 방문",ChatColor.WHITE+"방문 지점 : "+QuestList.getString(QuestName+".FlowChart."+count+".AreaName"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Give":
				String script2 = ChatColor.WHITE+"타입 : 전달%enter%"+ChatColor.WHITE+"전달 대상 : "+ChatColor.YELLOW+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname")+"%enter%"+ChatColor.WHITE+"NPC의 UUID%enter%"+ChatColor.DARK_AQUA + QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid")+"%enter%%enter%"+ChatColor.YELLOW+"[좌클릭시 전달 품목 확인]"+"%enter%"+ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptB = script2.split("%enter%");
				for(byte counter = 0; counter < scriptB.length; counter++)
					scriptB[counter] = ChatColor.WHITE + scriptB[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 388,0,1,Arrays.asList(scriptB), loc, inv);
				break;
			case "Hunt":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 267,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 사냥","",ChatColor.YELLOW+"[좌클릭시 처치 대상 확인]","",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Talk":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 397,3,1,Arrays.asList(ChatColor.WHITE+"타입 : 대화","",ChatColor.WHITE+"만나야 할 NPC 이름","",ChatColor.YELLOW+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname"),"",ChatColor.WHITE+"NPC의 UUID","",ChatColor.DARK_AQUA + QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Present":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 54,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 보상",ChatColor.WHITE+"수령 대상 : "+ChatColor.YELLOW+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname"),ChatColor.WHITE+"NPC의 UUID","",ChatColor.DARK_AQUA + QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid"),"","",ChatColor.YELLOW+"[좌클릭시 보상 확인]",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "TelePort":
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 368,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 이동","",ChatColor.WHITE+"월드 : "+QuestList.getString(QuestName+".FlowChart."+count+".World"),ChatColor.WHITE+"좌표 : " + (int)QuestList.getDouble(QuestName+".FlowChart."+count+".X")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Y")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Z"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Harvest":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 56,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 채집","",ChatColor.YELLOW+"[좌클릭시 채집 블록 확인]","",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "BlockPlace":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 152,0,1,Arrays.asList(ChatColor.WHITE+"타입 : 블록 설치",ChatColor.WHITE+"월드 : "+QuestList.getString(QuestName+".FlowChart."+count+".World"),ChatColor.WHITE+"좌표 : " + (int)QuestList.getDouble(QuestName+".FlowChart."+count+".X")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Y")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Z"),ChatColor.WHITE+"블록 타입 : " + QuestList.getInt(QuestName+".FlowChart."+count+".ID")+":"+ QuestList.getInt(QuestName+".FlowChart."+count+".DATA"),"",ChatColor.RED + "[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 오브젝트 추가", 2,0,1,Arrays.asList(ChatColor.GRAY + "새로운 오브젝트를 추가합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 53, inv);
		player.openInventory(inv);
	}

	public void SelectObjectPage(Player player, byte page, String QuestName)
	{
		String UniqueCode = "§0§0§5§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0오브젝트 추가");

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "대사", 323,0,1,Arrays.asList(ChatColor.GRAY + "대화창을 띄우고, 작성된",ChatColor.GRAY+"스크립트를 유저에게 띄웁니다.",ChatColor.GRAY+"(화자 : NPC)"), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "독백", 323,0,1,Arrays.asList(ChatColor.GRAY + "대화창을 띄우고, 작성된",ChatColor.GRAY+"스크립트를 유저에게 띄웁니다.",ChatColor.GRAY+"(화자 : 유저)"), 1, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "방문", 345,0,1,Arrays.asList(ChatColor.GRAY + "플레이어에게 특정 영역에",ChatColor.GRAY+"방문하는 퀘스트를 줍니다."), 2, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "전달", 388,0,1,Arrays.asList(ChatColor.GRAY + "플레이어가 특정 아이템을",ChatColor.GRAY+"NPC에게 줘야하는 퀘스트를 줍니다."), 3, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "사냥", 267,0,1,Arrays.asList(ChatColor.GRAY + "플레이어에게 특정 몬스터를",ChatColor.GRAY+"사냥하는 퀘스트를 줍니다."), 4, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "대화", 397,3,1,Arrays.asList(ChatColor.GRAY + "플레이어에게 특정 NPC에게",ChatColor.GRAY+"말을 거는 퀘스트를 줍니다."), 5, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "보상", 54,0,1,Arrays.asList(ChatColor.GRAY + "플레이어에게 보상을 줍니다."), 6, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이동", 368,0,1,Arrays.asList(ChatColor.GRAY + "플레이어를 특정 위치로",ChatColor.GRAY+"이동 시킵니다."), 7, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "채집", 56,0,1,Arrays.asList(ChatColor.GRAY + "플레이어에게 특정 블록을",ChatColor.GRAY+"채취하는 퀘스트를 줍니다."), 8, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "블록", 152,0,1,Arrays.asList(ChatColor.GRAY + "특정 위치에 정해진",ChatColor.GRAY+"블록을 생성합니다."), 9, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "소리"+ ChatColor.RED + "[사용 불가]", 84,0,1,Arrays.asList(ChatColor.GRAY + "특정 위치에 소리가 나게 합니다."), 10, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "귓말", 421,0,1,Arrays.asList(ChatColor.GRAY + "플레이어의 채팅창에 메시지가 나타납니다."), 11, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "전체", 138,0,1,Arrays.asList(ChatColor.GRAY + "서버 전체에 메시지가 나타납니다."), 12, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "네비", 358,0,1,Arrays.asList(ChatColor.GRAY + "플레이어에게 네비게이션을 작동 시킵니다."), 13, inv);
		

		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "선택", 72,0,1,Arrays.asList(ChatColor.GRAY + "플레이어가 원하는 대답을",ChatColor.GRAY+"선택 하도록 합니다.",ChatColor.GRAY+"선택한 대답에 따라",ChatColor.GRAY+"다른 변수값을 가질 수 있습니다."), 36, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "변수", 143,0,1,Arrays.asList(ChatColor.GRAY + "플레이어의 변수를 강제로 수정합니다."), 37, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "계산", 137,0,1,Arrays.asList(ChatColor.GRAY + "플레이어의 변수를 계산식을",ChatColor.GRAY+"사용하여 수정합니다."), 38, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "IF", 184,0,1,Arrays.asList(ChatColor.GRAY + "플레이어의 현재 변수값을 확인하여",ChatColor.GRAY+"비교한 값과 동일할 경우",ChatColor.GRAY+"IF와 ENDIF혹은 IF와 ELSE",ChatColor.GRAY+"사이의 구문을 실행하게 됩니다.","",ChatColor.RED+"[반드시 IF의 개수 = ENDIF의 개수]"), 39, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "ELSE", 167,0,1,Arrays.asList(ChatColor.GRAY + "플레이어의 현재 변수값이",ChatColor.GRAY+"IF 논리에 맞지 않을 경우",ChatColor.GRAY+"ELSE와 ENDIF 사이의 구문을",ChatColor.GRAY+"실행하게 됩니다.",""), 40, inv);
		Stack2(ChatColor.YELLOW + "" + ChatColor.BOLD + "ENDIF", 191,0,1,Arrays.asList(ChatColor.GRAY + "IF의 끝 부분을 나타냅니다.","",ChatColor.RED+"[반드시 IF의 개수 = ENDIF의 개수]"), 41, inv);
		
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "퀘스트 초기화", 395,0,1,Arrays.asList(ChatColor.GRAY + "퀘스트를 중간에 포기 합니다.",ChatColor.GREEN+"퀘스트를 다시 받을 수 있습니다."), 43, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "퀘스트 실패", 166,0,1,Arrays.asList(ChatColor.GRAY + "퀘스트를 중간에 포기 합니다.",ChatColor.GRAY+"일반 퀘스트일 경우 플레이어는",ChatColor.RED+"퀘스트를 다시 받을 수 없습니다."), 44, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 53, inv);
		player.openInventory(inv);
	}

	public void QuestScriptTypeGUI(Player player,String QuestName,String NPCname, short FlowChart, String[] script)
	{
		String UniqueCode = "§0§0§5§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0[퀘스트]");
		Stack2(ChatColor.BLACK + ChatColor.stripColor(QuestName), 160,8,1,null, 19, inv);
		
		for(byte count=0;count < script.length; count++)
			script[count] = script[count].replace("%player%", player.getName());
		if(NPCname.equals(player.getName()))
			ItemStackStack(getPlayerSkull(ChatColor.YELLOW+NPCname, 1, Arrays.asList(script), player.getName()), 13, inv);
		else
			Stack2(ChatColor.YELLOW + NPCname, 386,0,1,Arrays.asList(script), 13, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void QuestOptionGUI(Player player, String QuestName)
	{
		String UniqueCode = "§0§0§5§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§0퀘스트 옵션");

		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		
		switch(QuestList.getString(QuestName + ".Type"))
		{
		case "N" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "퀘스트 타입", 340,0,1,Arrays.asList(ChatColor.WHITE + "일반 퀘스트"), 4, inv);
			break;
		case "R" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "퀘스트 타입", 386,0,1,Arrays.asList(ChatColor.WHITE + "반복 퀘스트"), 4, inv);
			break;
		case "D" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "퀘스트 타입", 403,0,1,Arrays.asList(ChatColor.WHITE + "일일 퀘스트"), 4, inv);
			break;
		case "W" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "퀘스트 타입", 403,0,7,Arrays.asList(ChatColor.WHITE + "주간 퀘스트"), 4, inv);
			break;
		case "M" :
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "퀘스트 타입", 403,0,31,Arrays.asList(ChatColor.WHITE + "월간 퀘스트"), 4, inv);
			break;
		}

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "레벨 제한", 384,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 수행에 필요한 레벨을 설정합니다.",ChatColor.GOLD+"마비노기"+ChatColor.WHITE+" 시스템일 경우 "+ChatColor.YELLOW+"누적레벨"+ChatColor.WHITE+" 기준이며,",ChatColor.RED+"메이플스토리"+ChatColor.WHITE+" 시스템일 경우 "+ChatColor.YELLOW+"레벨"+ChatColor.WHITE+" 기준입니다.","",ChatColor.AQUA + "[필요 레벨 : " + QuestList.getInt(QuestName+".Need.LV")+"]"), 11, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "NPC 호감도 제한", 38,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 수행에 필요한",ChatColor.WHITE+"NPC와의 호감도를 설정합니다.","",ChatColor.AQUA + "[필요 호감도 : " + QuestList.getInt(QuestName+".Need.Love")+"]"), 13, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "스킬 랭크 제한", 403,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 수행에 필요한",ChatColor.WHITE+"스킬 랭크를 설정합니다.",""/*,ChatColor.AQUA + "[필요 스킬 : " + QuestList.getString(QuestName+".Need.Skill.Name")+"]",ChatColor.AQUA+"[필요 랭크 : " + QuestList.getInt(QuestName+".Need.Skill.Rank")+"]"*/), 15, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GBD_RPG.Main_Main.Main_ServerOption.STR+" 제한", 267,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 수행에 필요한",ChatColor.WHITE+""+GBD_RPG.Main_Main.Main_ServerOption.STR+" 스텟을 설정합니다.","",ChatColor.AQUA + "[필요 "+GBD_RPG.Main_Main.Main_ServerOption.STR+" : " + QuestList.getInt(QuestName+".Need.STR")+"]"), 20, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GBD_RPG.Main_Main.Main_ServerOption.DEX+" 제한", 261,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 수행에 필요한",ChatColor.WHITE+""+GBD_RPG.Main_Main.Main_ServerOption.DEX+" 스텟을 설정합니다.","",ChatColor.AQUA + "[필요 "+GBD_RPG.Main_Main.Main_ServerOption.DEX+" : " + QuestList.getInt(QuestName+".Need.DEX")+"]"), 21, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GBD_RPG.Main_Main.Main_ServerOption.INT+" 제한", 369,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 수행에 필요한",ChatColor.WHITE+""+GBD_RPG.Main_Main.Main_ServerOption.INT+" 스텟을 설정합니다.","",ChatColor.AQUA + "[필요 "+GBD_RPG.Main_Main.Main_ServerOption.INT+" : " + QuestList.getInt(QuestName+".Need.INT")+"]"), 22, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GBD_RPG.Main_Main.Main_ServerOption.WILL+" 제한", 370,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 수행에 필요한",ChatColor.WHITE+""+GBD_RPG.Main_Main.Main_ServerOption.WILL+" 스텟을 설정합니다.","",ChatColor.AQUA + "[필요 "+GBD_RPG.Main_Main.Main_ServerOption.WILL+" : " + QuestList.getInt(QuestName+".Need.WILL")+"]"), 23, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + ""+GBD_RPG.Main_Main.Main_ServerOption.LUK+" 제한", 322,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 수행에 필요한",ChatColor.WHITE+""+GBD_RPG.Main_Main.Main_ServerOption.LUK+" 스텟을 설정합니다.","",ChatColor.AQUA + "[필요 "+GBD_RPG.Main_Main.Main_ServerOption.LUK+" : " + QuestList.getInt(QuestName+".Need.LUK")+"]"), 24, inv);
		if(QuestList.getString(QuestName+".Need.PrevQuest").equalsIgnoreCase("null") == true)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "필수 완료 퀘스트", 386,0,1,Arrays.asList(ChatColor.WHITE+"이전 퀘스트를 수행한 뒤",ChatColor.WHITE+"현재 퀘스트를 수행 하도록 합니다.","",ChatColor.AQUA + "[이전 퀘스트 : 없음]"),29, inv);
		else
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "필수 완료 퀘스트", 386,0,1,Arrays.asList(ChatColor.WHITE+"이전 퀘스트를 수행한 뒤",ChatColor.WHITE+"현재 퀘스트를 수행 하도록 합니다.",ChatColor.RED+"[Shift + 우클릭시 삭제됩니다]","",ChatColor.AQUA + "[이전 퀘스트 : " +QuestList.getString(QuestName+".Need.PrevQuest")+"]"),29, inv);
		switch(QuestList.getInt(QuestName+".Server.Limit"))
		{
		case 0:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "퀘스트 한정", 397,3,1,Arrays.asList(ChatColor.WHITE+"서버에서 단 몇 명만이",ChatColor.WHITE+"이 퀘스트를 수행 할 수 있습니다.",ChatColor.WHITE+"플레이어가 퀘스트를 받을 때 마다 1씩 깎이며,",ChatColor.WHITE+"-1이 될 경우 퀘스트를 받을 수 없습니다.",ChatColor.DARK_AQUA+"(0명으로 설정할 경우, 제한이 사라집니다.)","",ChatColor.AQUA +"[수행 가능 플레이어 수 : 제한 없음]"), 33, inv);
			break;
		case -1:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "퀘스트 한정", 397,3,1,Arrays.asList(ChatColor.WHITE+"서버에서 단 몇 명만이",ChatColor.WHITE+"이 퀘스트를 수행 할 수 있습니다.",ChatColor.WHITE+"플레이어가 퀘스트를 받을 때 마다 1씩 깎이며,",ChatColor.WHITE+"-1이 될 경우 퀘스트를 받을 수 없습니다.",ChatColor.DARK_AQUA+"(0명으로 설정할 경우, 제한이 사라집니다.)","",ChatColor.RED +"[더이상 받을 수 없음]"), 33, inv);
			break;
		default:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "퀘스트 한정", 397,3,1,Arrays.asList(ChatColor.WHITE+"서버에서 단 몇 명만이",ChatColor.WHITE+"이 퀘스트를 수행 할 수 있습니다.",ChatColor.WHITE+"플레이어가 퀘스트를 받을 때 마다 1씩 깎이며,",ChatColor.WHITE+"-1이 될 경우 퀘스트를 받을 수 없습니다.",ChatColor.DARK_AQUA+"(0명으로 설정할 경우, 제한이 사라집니다.)","",ChatColor.AQUA +"[수행 가능 플레이어 수 : "+QuestList.getInt(QuestName+".Server.Limit")+"]"), 33, inv);
			break;
		}
	
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 36, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 44, inv);
		player.openInventory(inv);
	}
	
	public void GetterItemSetingGUI(Player player, String QuestName)
	{
		String UniqueCode = "§1§0§5§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0모아야 할 아이템 등록");
		for(int count = 0;count<8;count++)
			Stack2(ChatColor.WHITE+ "[아이템을 올려 주세요.]", 389,0,0,null, count, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 8, inv);
		player.openInventory(inv);
	}

	public void PresentItemSettingGUI(Player player, String QuestName)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");

		UserData_Object u = new UserData_Object();

		String UniqueCode = "§1§0§5§0§7§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0보상 아이템 등록");
		if(u.getInt(player, (byte)1) == -1)
			u.setInt(player, (byte)1, 0);
		if(u.getInt(player, (byte)2) == -1)
			u.setInt(player, (byte)2, 0);
		if(u.getInt(player, (byte)3) == -1)
			u.setInt(player, (byte)3, 0);
			
		Stack2(ChatColor.WHITE+ "[보상금 설정하기]", 266,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+""+u.getInt(player, (byte)1)+" "+GBD_RPG.Main_Main.Main_ServerOption.Money), 0, inv);
		Stack2(ChatColor.WHITE+ "[경험치 설정하기]", 384,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+""+u.getInt(player, (byte)2)+ChatColor.AQUA+""+ChatColor.BOLD+ " EXP"), 1, inv);
		Stack2(ChatColor.WHITE+ "[NPC 호감도 설정하기]", 38,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+""+u.getInt(player, (byte)3)+ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+ " Love"), 2, inv);
		int ifItemExit = 0;
		for(int count = 3;count<8;count++)
		{
			if(QuestConfig.getItemStack(QuestName + ".FlowChart."+ u.getInt(player, (byte)5) +".Item."+ifItemExit) != null)
			{
				ItemStackStack(QuestConfig.getItemStack(QuestName + ".FlowChart."+ u.getInt(player, (byte)5) +".Item."+ifItemExit), count, inv);
				ifItemExit++;
			}
			else
				Stack2(ChatColor.WHITE+ "[아이템을 올려 주세요.]", 389,0,0,null, count, inv);
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 8, inv);
		u.setString(player, (byte)4,null);
		player.openInventory(inv);
	}
	
	public void ShowItemGUI(Player player, String QuestName, short Flow, boolean isOP, boolean type)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§8§r";
		Inventory inv = null;
		
		if(QuestList.contains(QuestName+".FlowChart."+Flow+".Item") == true)
		{
			Object[] a =QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Item").getKeys(false).toArray();
			if(type == false)
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "§0모아야 할 아이템 목록");
				for(short count = 0;count<a.length;count++)
					ItemStackStack(QuestList.getItemStack(QuestName+".FlowChart."+Flow+".Item." + a[count]),count+10,inv);
			}
			else
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "§0보상 목록");
				Stack2(ChatColor.GOLD+ "[보상금]", 266,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Money") +ChatColor.GOLD +" "+GBD_RPG.Main_Main.Main_ServerOption.Money), 3, inv);
				Stack2(ChatColor.AQUA+ "[경험치]", 384,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".EXP") +ChatColor.AQUA + " EXP"), 4, inv);
				Stack2(ChatColor.LIGHT_PURPLE+ "[호감도]", 38,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Love") +ChatColor.LIGHT_PURPLE + " Love"), 5, inv);

				for(short count = 0;count<a.length;count++)
					ItemStackStack(QuestList.getItemStack(QuestName+".FlowChart."+Flow+".Item." + a[count]),count+11,inv);
				if(player.isOp())
				{
					UserData_Object u = new UserData_Object();
					if(u.getInt(player, (byte)1)!=-9)
					{
						u.clearAll(player);
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "보상 받기", 54,0,1,Arrays.asList(ChatColor.GRAY + "보상을 수령합니다." ,ChatColor.BLACK +""+ Flow), 22, inv);
					}
				}
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "보상 받기", 54,0,1,Arrays.asList(ChatColor.GRAY + "보상을 수령합니다." ,ChatColor.BLACK +""+ Flow), 22, inv);
			}
		}
		else
		{
			if(type == false)
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "§0모아야 할 아이템 목록");
			}
			else
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "§0보상 목록");
				Stack2(ChatColor.GOLD+ "[보상금]", 266,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Money") +ChatColor.GOLD +" "+GBD_RPG.Main_Main.Main_ServerOption.Money), 3, inv);
				Stack2(ChatColor.AQUA+ "[경험치]", 384,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".EXP") +ChatColor.AQUA + " EXP"), 4, inv);
				Stack2(ChatColor.LIGHT_PURPLE+ "[호감도]", 38,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Love") +ChatColor.LIGHT_PURPLE + " Love"), 5, inv);
				if(player.isOp())
				{
					UserData_Object u = new UserData_Object();
					if(u.getInt(player, (byte)1)!=-9)
					{
						u.clearAll(player);
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "보상 받기", 54,0,1,Arrays.asList(ChatColor.GRAY + "보상을 수령합니다." ,ChatColor.BLACK +""+ Flow), 22, inv);
					}
				}
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "보상 받기", 54,0,1,Arrays.asList(ChatColor.GRAY + "보상을 수령합니다." ,ChatColor.BLACK +""+ Flow), 22, inv);
			}
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK +""+ isOP), 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void KillMonsterGUI(Player player, String QuestName, short Flow, boolean isOP)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		String UniqueCode = "§0§0§5§0§8§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0사냥 해야 할 몬스터 목록");
		
		Object[] c = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
		for(short counter = 0; counter < c.length; counter++)
		{
			String MobName = QuestList.getString(QuestName+".FlowChart."+Flow+".Monster."+counter+".MonsterName");
			int Amount = QuestList.getInt(QuestName+".FlowChart."+Flow+".Monster."+counter+".Amount");
			int PlayerKillAmount = PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter);
			
	        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1);
	        SkullMeta meta = (SkullMeta) skull.getItemMeta();
	        meta.setOwner(MobName);
	        meta.setDisplayName(ChatColor.GOLD + SkullType(MobName));
	        meta.setLore(Arrays.asList(ChatColor.WHITE + "[" +PlayerKillAmount+"/"+ Amount + "]"));
	        skull.setItemMeta(meta);
	        ItemStackStack(skull, counter, inv);
			//Stack2(ChatColor.GOLD+ MobName, 266,0,1,Arrays.asList(ChatColor.WHITE + "[" +PlayerKillAmount+"/"+ Amount + "]"), counter, inv);
		}
		
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK +""+ isOP), 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void HarvestGUI(Player player, String QuestName, short Flow, boolean isOP)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		String UniqueCode = "§0§0§5§0§8§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0채집 해야 할 블록 목록");
		
		Object[] c = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Block").getKeys(false).toArray();
		for(short counter = 0; counter < c.length; counter++)
		{
			int BlockID = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockID");
			byte BlockData = (byte) QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockData");
			int Amount = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".Amount");
			boolean DataEquals = QuestList.getBoolean(QuestName+".FlowChart."+Flow+".Block."+counter+".DataEquals");
			int PlayerHarvestAmount = PlayerQuestList.getInt("Started."+QuestName+".Block."+counter);
			GBD_RPG.Main_Event.Main_Interact IT = new GBD_RPG.Main_Event.Main_Interact();
			
			if(DataEquals == true)
				Stack(ChatColor.YELLOW+IT.SetItemDefaultName((short) BlockID,(byte)BlockData), BlockID, BlockData, 1, Arrays.asList(ChatColor.WHITE + "[" +PlayerHarvestAmount+"/"+ Amount + "]","",ChatColor.GRAY + "아이템 ID : " +BlockID,ChatColor.GRAY + "아이템 Data : " +BlockData), counter, inv);
			else
				Stack(ChatColor.YELLOW+"아무런 "+IT.SetItemDefaultName((short) BlockID,(byte)BlockData)+ChatColor.YELLOW+" 종류", BlockID, 0, 1, Arrays.asList(ChatColor.WHITE + "[" +PlayerHarvestAmount+"/"+ Amount + "]","",ChatColor.GRAY + "아이템 ID : " +BlockID), counter, inv);
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK +""+ isOP), 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void KeepGoing(Player player, String QuestName, short Flow, short Mob, boolean Harvest)
	{
		String UniqueCode = "§0§0§5§0§9§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0계속 등록 하시겠습니까?");

		if(Harvest == false)
		{
			Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "계속 등록하기", 386,0,1,Arrays.asList(ChatColor.GRAY + "사냥 대상을 추가로 등록합니다.",ChatColor.BLACK +""+Flow,ChatColor.BLACK + ""+Mob), 10, inv);
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "등록 중단하기", 166,0,1,Arrays.asList(ChatColor.GRAY + "사냥 대상 추가를 종료합니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 16, inv);
		}
		else
		{
			Stack2(ChatColor.GREEN + "" + ChatColor.BOLD + "계속 등록하기", 386,0,1,Arrays.asList(ChatColor.GRAY + "채집 대상을 추가로 등록합니다.",ChatColor.BLACK +""+Flow,ChatColor.BLACK + ""+Mob), 10, inv);
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "등록 중단하기", 166,0,1,Arrays.asList(ChatColor.GRAY + "채집 대상 추가를 종료합니다.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 16, inv);
		}
		player.openInventory(inv);
	}
	
	public void Quest_NavigationListGUI(Player player, short page, String QuestName)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");

		String UniqueCode = "§0§0§5§0§a§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0퀘스트 네비 설정 : " + (page+1));

		Object[] Navi= NavigationConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < Navi.length;count++)
		{
			if(count > Navi.length || loc >= 45) break;
			String NaviName = NavigationConfig.getString(Navi[count]+".Name");
			String world = NavigationConfig.getString(Navi[count]+".world");
			int x = NavigationConfig.getInt(Navi[count]+".x");
			short y = (short) NavigationConfig.getInt(Navi[count]+".y");
			int z = NavigationConfig.getInt(Navi[count]+".z");
			int Time = NavigationConfig.getInt(Navi[count]+".time");
			short sensitive = (short) NavigationConfig.getInt(Navi[count]+".sensitive");
			boolean Permition = NavigationConfig.getBoolean(Navi[count]+".onlyOPuse");
			byte ShowArrow = (byte) NavigationConfig.getInt(Navi[count]+".ShowArrow");
			
			
			String TimeS = ChatColor.DARK_AQUA+"<도착할 때 까지 유지>";
			String PermitionS = ChatColor.DARK_AQUA+"<OP만 사용 가능>";
			String sensitiveS = ChatColor.BLUE+"<반경 "+sensitive+"블록 이내를 도착지로 판정>";
			String ShowArrowS = ChatColor.DARK_AQUA+"<기본 화살표 모양>";
			if(Permition == false)
				PermitionS = ChatColor.DARK_AQUA+"<모두 사용 가능>";
			if(Time >= 0)
				TimeS = ChatColor.DARK_AQUA+"<"+Time+"초 동안 유지>";
			switch(ShowArrow)
			{
			default:
				ShowArrowS = ChatColor.DARK_AQUA+"<기본 화살표 모양>";
				break;
			}
			Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395,0,1,Arrays.asList(
			ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
			ChatColor.BLUE+"[도착 지점]",ChatColor.BLUE+"월드 : "+ChatColor.WHITE+world,
			ChatColor.BLUE+"좌표 : " + ChatColor.WHITE+x+","+y+","+z,sensitiveS,"",
			ChatColor.DARK_AQUA+"[기타 옵션]",TimeS,PermitionS,ShowArrowS,""
			,ChatColor.YELLOW+"[좌 클릭시 네비 선택]"), loc, inv);
			loc++;
		}
		
		if(Navi.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+QuestName), 53, inv);
		player.openInventory(inv);
	}
	
	public void Quest_OPChoice(Player player,String QuestName, short Flow,short page)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§b§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0퀘스트 선택 확인");
		
		String[] script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.0.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"변수값 변경 : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.0.Var")).split("%enter%");
		
		switch(QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size())
		{
		case 1:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,1,Arrays.asList(script), 13, inv);
			break;
		case 2:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,1,Arrays.asList(script), 12, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"변수값 변경 : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,2,Arrays.asList(script), 14, inv);
			break;
		case 3:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,1,Arrays.asList(script), 11, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"변수값 변경 : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,2,Arrays.asList(script), 13, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"변수값 변경 : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,3,Arrays.asList(script), 15, inv);
			break;
		case 4:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,1,Arrays.asList(script), 10, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"변수값 변경 : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,2,Arrays.asList(script), 12, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"변수값 변경 : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,3,Arrays.asList(script), 14, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.3.Lore")+"%enter%%enter%"+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"변수값 변경 : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.3.Var")).split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,4,Arrays.asList(script), 16, inv);
			break;
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+""+page), 18, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+QuestName), 26, inv);
		player.openInventory(inv);
	}
	
	public void Quest_UserChoice(Player player,String QuestName, short Flow)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§c§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0퀘스트 선택");

		String lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.0.Lore").replace("%player%", player.getName());
		String[] script = lore.split("%enter%");
		
		switch(QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size())
		{
		case 1:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,1,Arrays.asList(script), 13, inv);
			break;
		case 2:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,1,Arrays.asList(script), 12, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,2,Arrays.asList(script), 14, inv);
			break;
		case 3:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,1,Arrays.asList(script), 11, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,2,Arrays.asList(script), 13, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,3,Arrays.asList(script), 15, inv);
			break;
		case 4:
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,1,Arrays.asList(script), 10, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,2,Arrays.asList(script), 12, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,3,Arrays.asList(script), 14, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.3.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[선택]", 72,0,4,Arrays.asList(script), 16, inv);
			break;
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+QuestName,ChatColor.BLACK+""+Flow), 26, inv);
		player.openInventory(inv);
	}
	
	
	public void QuestRouter(Player player,String QuestName)
	{
		GBD_RPG.Effect.Effect_Potion p = new GBD_RPG.Effect.Effect_Potion();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
		
		String QuestType = QuestList.getString(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Type");
		short FlowChart = (short) PlayerQuestList.getInt("Started."+QuestName+".Flow");
		String NPCname = QuestList.getString(QuestName+".FlowChart."+FlowChart+".NPCname");
		if(QuestType == null)
		{
			GBD_RPG.Util.ETC ETC = new GBD_RPG.Util.ETC();
			YamlManager Config = YC.getNewConfig("config.yml");
			String message = Config.getString("Quest.ClearMessage").replace("%QuestName%", QuestName);
			player.sendMessage(message);
			PlayerQuestList.set("Ended."+QuestName+".ClearTime", ETC.getNowUTC());
			PlayerQuestList.removeKey("Started."+QuestName+".Flow");
			PlayerQuestList.removeKey("Started."+QuestName+".Type");
			PlayerQuestList.removeKey("Started."+QuestName);
			PlayerQuestList.saveConfig();
			YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
			PlayerVarList.removeKey(QuestName);
			PlayerVarList.saveConfig();
			player.closeInventory();
			s.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.8F);
			
		}
		else
		{
			PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart." + FlowChart+".Type") );
			PlayerQuestList.saveConfig();
			switch(QuestType)
			{
				case "Cal":
				{
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					int PlayerValue = PlayerVarList.getInt(QuestName);
					int SideValue = QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Value");
					int total = 0;
					switch(QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Comparison"))
					{
					case 1:
						total = PlayerValue+SideValue;
						break;
					case 2:
						total = PlayerValue-SideValue;
						break;
					case 3:
						total = PlayerValue*SideValue;
						break;
					case 4:
						total = PlayerValue/SideValue;
						break;
					case 5:
						total = PlayerValue%SideValue;
						break;
					}
					if(total > 40000)
						total = 40000;
					if(total < -2000)
						total = -2000;
					PlayerVarList.set(QuestName,total);
					PlayerVarList.saveConfig();
					PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
					PlayerQuestList.saveConfig();
					QuestRouter(player, QuestName);
					return;
				}
				case "IF":
				{
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					int PlayerValue = PlayerVarList.getInt(QuestName);
					int SideValue = QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Value");
					boolean isMatch = false;
					switch(QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Comparison"))
					{
					case 1:
						if(PlayerValue==SideValue)
							isMatch = true;
						break;
					case 2:
						if(PlayerValue!=SideValue)
							isMatch = true;
						break;
					case 3:
						if(PlayerValue>SideValue)
							isMatch = true;
						break;
					case 4:
						if(PlayerValue<SideValue)
							isMatch = true;
						break;
					case 5:
						if(PlayerValue>=SideValue)
							isMatch = true;
						break;
					case 6:
						if(PlayerValue<=SideValue)
							isMatch = true;
						break;
					case 7:
						SideValue = QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Max");
						if(QuestList.getInt(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Min")<=PlayerValue&&PlayerValue<=SideValue)
							isMatch = true;
						break;
					}
					if(isMatch)
					{
						PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
						PlayerQuestList.saveConfig();
						QuestRouter(player, QuestName);
					}
					else
					{
						QuestList.getString(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Type");
						int MeetTheIF = 0;
						for(int count = FlowChart+1; count < QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size(); count++)
						{
							if(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("IF")==0)
								MeetTheIF = MeetTheIF+1;
							else if(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ENDIF")==0)
								if(MeetTheIF!=0)
									MeetTheIF = MeetTheIF-1;
							if(MeetTheIF==0&&(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ENDIF")==0))
							{
								if(PlayerVarList.getInt(QuestName+".MeetELSE")!=0)
								{
									PlayerVarList.set(QuestName+".MeetELSE",PlayerVarList.getInt(QuestName+".MeetELSE")-1);
									PlayerVarList.saveConfig();
								}
								PlayerQuestList.set("Started."+QuestName+".Flow",count);
								PlayerQuestList.saveConfig();
								QuestRouter(player, QuestName);
								return;
							}
							if(MeetTheIF==0&&(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ELSE")==0))
							{
								PlayerVarList.set(QuestName+".MeetELSE",PlayerVarList.getInt(QuestName+".MeetELSE")+1);
								PlayerVarList.saveConfig();
								PlayerQuestList.set("Started."+QuestName+".Flow",count);
								PlayerQuestList.saveConfig();
								QuestRouter(player, QuestName);
								return;
							}
						}
						//끝까지 ENDIF나 ELSE나 IF를 찾지 못하면 퀘스트 완료로 넘어감
						GBD_RPG.Util.ETC ETC = new GBD_RPG.Util.ETC();
						YamlManager Config = YC.getNewConfig("config.yml");
						String message = Config.getString("Quest.ClearMessage").replace("%QuestName%", QuestName);
						player.sendMessage(message);
						PlayerQuestList.set("Ended."+QuestName+".ClearTime", ETC.getNowUTC());
						PlayerQuestList.removeKey("Started."+QuestName+".Flow");
						PlayerQuestList.removeKey("Started."+QuestName+".Type");
						PlayerQuestList.removeKey("Started."+QuestName);
						PlayerQuestList.saveConfig();
						PlayerVarList.removeKey(QuestName);
						PlayerVarList.saveConfig();
						player.closeInventory();
						s.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.8F);
					}
				}
				break;
				case "ELSE":
				{
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					if(PlayerVarList.getInt(QuestName+".MeetELSE")==0)
					{
						QuestList.getString(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Type");
						short MeetTheIF = 0;
						for(int count = FlowChart+1; count < QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size(); count++)
						{
							if(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("IF")==0)
								MeetTheIF++;
							else if(MeetTheIF!=0&&QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ENDIF")==0)
								if(MeetTheIF!=0)
									MeetTheIF--;
							if(MeetTheIF==0&&(QuestList.getString(QuestName+".FlowChart."+count+".Type").compareTo("ENDIF")==0))
							{
								PlayerVarList.set(QuestName+".MeetElse",0);
								PlayerVarList.saveConfig();
								PlayerQuestList.set("Started."+QuestName+".Flow",count);
								PlayerQuestList.saveConfig();
								QuestRouter(player, QuestName);
								return;
							}
						}
						//끝까지 ENDIF를 찾지 못하면 퀘스트 완료로 넘어감
						GBD_RPG.Util.ETC ETC = new GBD_RPG.Util.ETC();
						YamlManager Config = YC.getNewConfig("config.yml");
						String message = Config.getString("Quest.ClearMessage").replace("%QuestName%", QuestName);
						player.sendMessage(message);
						PlayerQuestList.set("Ended."+QuestName+".ClearTime", ETC.getNowUTC());
						PlayerQuestList.removeKey("Started."+QuestName+".Flow");
						PlayerQuestList.removeKey("Started."+QuestName+".Type");
						PlayerQuestList.removeKey("Started."+QuestName);
						PlayerQuestList.saveConfig();
						PlayerVarList.removeKey(QuestName);
						PlayerVarList.saveConfig();
						player.closeInventory();
						s.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.8F);
					}
					else
					{
						PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
						PlayerQuestList.saveConfig();
						QuestRouter(player, QuestName);
					}
				}
				break;
				case "ENDIF":
				{
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					if(PlayerVarList.getInt(QuestName+".MeetELSE")!=0)
					{
						PlayerVarList.set(QuestName+".MeetELSE",PlayerVarList.getInt(QuestName+".MeetELSE")-1);
						PlayerVarList.saveConfig();
					}
					PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
					PlayerQuestList.saveConfig();
					QuestRouter(player, QuestName);
				}
				break;
				case "QuestFail":
				{
					player.sendMessage(ChatColor.RED+"[퀘스트] : 퀘스트를 완수하지 못하였습니다!");
					PlayerQuestList.set("Ended."+QuestName+".ClearTime", new GBD_RPG.Util.ETC().getNowUTC());
					PlayerQuestList.removeKey("Started."+QuestName);
					PlayerQuestList.saveConfig();
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					PlayerVarList.removeKey(QuestName);
					PlayerVarList.saveConfig();
					player.closeInventory();
					s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
					s.SP(player, Sound.ENTITY_WITHER_DEATH, 0.7F, 0.8F);
				}
				break;
				case "QuestReset":
				{
					player.sendMessage(ChatColor.YELLOW+"[퀘스트] : 퀘스트를 포기하였습니다!");
					PlayerQuestList.removeKey("Started."+QuestName);
					PlayerQuestList.saveConfig();
					YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					PlayerVarList.removeKey(QuestName);
					PlayerVarList.saveConfig();
					player.closeInventory();
					s.SP(player, Sound.BLOCK_LAVA_POP, 1.2F, 0.8F);
				}
			break;
			case "VarChange":
			{
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+FlowChart+".Value"));
				PlayerVarList.saveConfig();
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestRouter(player, QuestName);
			}
			break;
			case "Choice":
				Quest_UserChoice(player, QuestName,FlowChart);
				break;
			case "Nevigation":
			{
				String UTC = QuestList.getString(QuestName+".FlowChart."+FlowChart+".NeviUTC");
				YamlManager NavigationConfig =YC.getNewConfig("Navigation/NavigationList.yml");
				if(NavigationConfig.contains(UTC))
				{
					ServerTick_Main.NaviUsingList.add(player.getName());
					player.closeInventory();
					s.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.0F);
					
					GBD_RPG.ServerTick.ServerTick_Object STSO = new GBD_RPG.ServerTick.ServerTick_Object(Long.parseLong(UTC), "NV");
					STSO.setCount(0);//횟 수 초기화
					STSO.setMaxCount(NavigationConfig.getInt(UTC+".time"));//N초간 네비게이션
					//-1초 설정시, N초간이 아닌, 찾아 갈 때 까지 네비게이션 지원
					STSO.setString((byte)1, NavigationConfig.getString(UTC+".world"));//목적지 월드 이름 저장
					STSO.setString((byte)2, player.getName());//플레이어 이름 저장
					
					STSO.setInt((byte)0, NavigationConfig.getInt(UTC+".x"));//목적지X 위치저장
					STSO.setInt((byte)1, NavigationConfig.getInt(UTC+".y"));//목적지Y 위치저장
					STSO.setInt((byte)2, NavigationConfig.getInt(UTC+".z"));//목적지Z 위치저장
					STSO.setInt((byte)3, NavigationConfig.getInt(UTC+".sensitive"));//판정 범위 저장
					STSO.setInt((byte)4, NavigationConfig.getInt(UTC+".ShowArrow"));//파티클 설정
					
					GBD_RPG.ServerTick.ServerTick_Main.Schedule.put(Long.parseLong(UTC), STSO);
					player.sendMessage(ChatColor.YELLOW+"[네비게이션] : 길찾기 시스템이 가동됩니다!");
					player.sendMessage(ChatColor.YELLOW+"(화살표가 보이지 않을 경우, [ESC] → [설정] → [비디오 설정] 속의 [입자]를 [모두]로 변경해 주세요!)");
					
				}
				else
				{
					s.SP(player, Sound.BLOCK_NOTE_BASS, 1.0F, 1.0F);
					player.sendMessage(ChatColor.RED+"[네비게이션] : 등록된 네비게이션을 찾을 수 없습니다! 관리자에게 문의하세요!");
				}
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestRouter(player, QuestName);
			}
			break;
			case "Whisper":
			{
				String script3 = ChatColor.WHITE+QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
				script3 = script3.replace("%player%", player.getName());
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script3 = script3.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				player.sendMessage(script3);
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestRouter(player, QuestName);
			}
			break;
			case "BroadCast":
			{
				String script3 = ChatColor.WHITE+QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
				script3 = script3.replace("%player%", player.getName());
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script3 = script3.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				Bukkit.getServer().broadcastMessage(script3);
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestRouter(player, QuestName);
			}
			break;
			case "Script": 
			{
				String script = QuestList.getString(QuestName+".FlowChart."+FlowChart+".Script");
				script = script.replace("%player%", player.getName());
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script = script.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				String[] scriptA = script.split("%enter%");
				for(byte count = 0; count < scriptA.length; count++)
					scriptA[count] = ChatColor.WHITE + scriptA[count];
				QuestScriptTypeGUI(player, QuestName, NPCname, FlowChart, scriptA);
			}
			break;
			case "PScript": 
			{
				String script2 = QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
				script2 = script2.replace("%player%", player.getName());
				YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script2 = script2.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				String[] scriptA2 = script2.split("%enter%");
				for(byte count = 0; count < scriptA2.length; count++)
					scriptA2[count] = ChatColor.WHITE + scriptA2[count];
				QuestScriptTypeGUI(player, QuestName, player.getName(), FlowChart, scriptA2);
			}
			break;
			case "Visit":
				PlayerQuestList.set("Started."+QuestName+".AreaName",QuestList.getString(QuestName+".FlowChart."+FlowChart+".AreaName"));
				PlayerQuestList.saveConfig();
				break;
			case "Give":
				ShowItemGUI(player, QuestName, FlowChart, false,false);
				break;
			case "Hunt":
				Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+FlowChart+".Monster").getKeys(false).toArray();
				for(short counter = 0; counter < MobList.length; counter++)
					PlayerQuestList.set("Started."+QuestName+".Hunt."+counter,0);
				PlayerQuestList.saveConfig();
				KillMonsterGUI(player, QuestName, FlowChart, false);
				break;
			case "Talk":
				break;
			case "Present":
				ShowItemGUI(player, QuestName, FlowChart, false,true);
				break;
			case "TelePort":
				{
					Location l = new Location(Bukkit.getServer().getWorld(QuestList.getString(QuestName+".FlowChart."+FlowChart+".World")), QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".X"),
						QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".Y")+1, QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".Z"));
					player.teleport(l);
					p.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
					s.SL(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
					PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
					PlayerQuestList.saveConfig();
					QuestRouter(player, QuestName);
				}
				break;
			case "BlockPlace":
				{
					Location l = new Location(Bukkit.getServer().getWorld(QuestList.getString(QuestName+".FlowChart."+FlowChart+".World")), QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".X"),
						QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".Y"), QuestList.getDouble(QuestName+".FlowChart."+FlowChart+".Z"));
					Block block = Bukkit.getWorld(QuestList.getString(QuestName+".FlowChart."+FlowChart+".World")).getBlockAt(l);
					block.setTypeId(QuestList.getInt(QuestName+".FlowChart."+FlowChart+".ID"));
					block.setData((byte)QuestList.getInt(QuestName+".FlowChart."+FlowChart+".DATA"));
					PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
					PlayerQuestList.saveConfig();
					QuestRouter(player, QuestName);
				}
				break;
			case "Harvest":
				Object[] BlockList = QuestList.getConfigurationSection(QuestName+".FlowChart."+FlowChart+".Block").getKeys(false).toArray();
				for(short counter = 0; counter < BlockList.length; counter++)
					PlayerQuestList.set("Started."+QuestName+".Block."+counter,0);
				PlayerQuestList.saveConfig();
				HarvestGUI(player, QuestName, FlowChart, false);
				break;
			}
		}
	}
	
	
	
	public void AllOfQuestListGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		UserData_Object u = new UserData_Object();
		boolean ChooseQuestGUI = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		if(slot == 53)//닫기
		{
			if(ChooseQuestGUI == true)
				u.clearAll(player);
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
			{
				if(ChooseQuestGUI == true)
				{
					QuestOptionGUI(player, u.getString(player, (byte)1));
					u.clearAll(player);
				}
				else
					new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Main(player,(byte) 1);
			}
			else if(slot==48)//이전 페이지
				AllOfQuestListGUI(player,(short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-2),ChooseQuestGUI);
			else if(slot==50)//다음 페이지
				AllOfQuestListGUI(player, Short.parseShort(event.getInventory().getTitle().split(" : ")[1]),ChooseQuestGUI);
			else if(slot==49)//새 퀘스트
			{
				player.sendMessage(ChatColor.GOLD + "/퀘스트 생성 [타입] [이름]" );
				player.sendMessage(ChatColor.GREEN + "[타입 : 일반 / 반복 / 일일 / 일주 / 한달]");
				player.closeInventory();
			}
			else
			{
				if(ChooseQuestGUI == true)
				{
					String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
					if(QuestName.equalsIgnoreCase(u.getString(player, (byte)1)))
					{
						s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.8F, 1.0F);
						player.sendMessage(ChatColor.RED+"[퀘스트] : 같은 퀘스트는 등록할 수 없습니다!");
					}
					else
					{
						QuestList.set(u.getString(player, (byte)1)+".Need.PrevQuest", QuestName);
						QuestList.saveConfig();
						QuestOptionGUI(player, u.getString(player, (byte)1));
						u.clearAll(player);
					}
				}
				else
				{
					if(event.getClick().isLeftClick() == true)
						FixQuestGUI(player, (short) 0, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					else if(event.getClick().isRightClick() == true && event.isShiftClick() == true)
					{
						String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
						YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
						YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
						QuestList.removeKey(QuestName);
						QuestList.saveConfig();
				    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
				    	Player[] a = new Player[playerlist.size()];
				    	playerlist.toArray(a);
		  	  			for(short count = 0; count<a.length;count++)
		  	  			{
		  	  		    	if(a[count].isOnline() == true)
		  	  		    	{
		  						s.SP(a[count], Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
		  						a[count].sendMessage(ChatColor.LIGHT_PURPLE + "[관리자] : "+ChatColor.YELLOW + player.getName()+ChatColor.LIGHT_PURPLE + "님께서 " + ChatColor.YELLOW + QuestName+ChatColor.LIGHT_PURPLE + "퀘스트를 삭제하셨습니다!");
		  	  		    	}	
		  	  		    }
		  	  			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName()+ChatColor.LIGHT_PURPLE + "님께서 " + ChatColor.YELLOW + QuestName+ChatColor.LIGHT_PURPLE + "퀘스트를 삭제하셨습니다!");
						AllOfQuestListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),false);
					}
					else if(event.getClick().isRightClick() == true)
						QuestOptionGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
			}
		}
		
	}

	public void FixQuestGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		if(slot == 53)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			if(slot == 45)//이전 목록
				AllOfQuestListGUI(player,(short) 0,false);
			else if(slot == 48)//이전 페이지
				FixQuestGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2),QuestName);
			else if(slot == 50)//다음 페이지
				FixQuestGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]),QuestName);
			else if(slot == 49)//새 오브젝트 추가
				SelectObjectPage(player, (byte) 0, QuestName);
			else
			{
				short Flow = Short.parseShort(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				if(event.getClick().isLeftClick() == true)
				{
					if(event.getCurrentItem().getItemMeta().getLore().get(0).contains(" : "))
						switch(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1])
						{
							case "전달":
								ShowItemGUI(player, QuestName, (short) Flow,true,false);
								break;
							case "보상":
								{
									new UserData_Object().setInt(player, (byte)1,-9);
									ShowItemGUI(player, QuestName, (short) Flow,true,true);
								}
								break;
							case "사냥":
								KillMonsterGUI(player, QuestName, (short) Flow, player.isOp());
								break;
							case "채집" :
								HarvestGUI(player, QuestName, (short) Flow, player.isOp());
								break;
							case "선택":
								Quest_OPChoice(player, QuestName, (short) Flow, (short) page);
								break;
						}
				}
				else if(event.getClick().isRightClick() == true && event.isShiftClick() == true)
				{
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");

					Object[] b = QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();

					for(short count = Flow; count <= b.length-1;count++)
						QuestList.set(QuestName+".FlowChart."+count,QuestList.get(QuestName+".FlowChart."+(count+1)));
					QuestList.saveConfig();
					FixQuestGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),QuestName);
				}
			}
		}
	}
	
	public void MyQuestListGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new GBD_RPG.User.Stats_GUI().StatusGUI(player);
			else if(slot == 48)//이전 페이지
				MyQuestListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//다음 페이지
				MyQuestListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
				int Flow = PlayerQuestList.getInt("Started."+QuestName+".Flow");
				if(event.getCurrentItem().getItemMeta().getLore().toString().contains("전달") == true)
					ShowItemGUI(player, QuestName, (short) Flow,false,false);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("보상") == true)
					ShowItemGUI(player, QuestName, (short) Flow,false,true);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("처치") == true)
					KillMonsterGUI(player, QuestName, (short) Flow, player.isOp());
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("독백") == true)
					QuestRouter(player, QuestName);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("채집") == true)
					HarvestGUI(player, QuestName, (short) Flow, false);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("선택지") == true)
					Quest_UserChoice(player, QuestName, (short) Flow);
			}
		}
	}

	public void SelectObjectPageClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();

		UserData_Object u = new UserData_Object();
		
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
		short size = (short) QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size();

		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "변수":
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"CV");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : 플레어의 변수값을 몇 으로 변경할까요?");
				player.sendMessage(ChatColor.GRAY + "(최소 -1000 ~ 최대 30000)");
				player.closeInventory();
				return;
			case "선택":
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"CS");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : 몇 가지 선택지를 보여 줄 건가요?");
				player.sendMessage(ChatColor.GRAY + "(최소 1개 ~ 최대 4개)");
				player.closeInventory();
				return;
			case "네비":
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				Quest_NavigationListGUI(player, (short) 0, QuestName);
				return;
			case "대사":
			case "독백":
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("대사"))
					u.setString(player, (byte)1,"Script");
				else
					u.setString(player, (byte)1,"PScript");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 등록할 대사를 채팅창에 입력하세요!");
				player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄어 쓰기 -");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
				player.sendMessage(ChatColor.GOLD + "%value%"+ChatColor.WHITE + " - 플레이어의 현재 퀘스트 변수 지칭하기 -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
				return;
			case "방문":
				YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
				Object[] arealist =AreaList.getConfigurationSection("").getKeys(false).toArray();

				if(arealist.length <= 0)
				{
					s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
					player.sendMessage(ChatColor.RED + "[퀘스트] : 생성된 영역이 없습니다!");
					player.sendMessage(ChatColor.GOLD + "/영역 <이름> 생성"+ChatColor.YELLOW + " - 새로운 영역을 생성합니다. -");
					player.closeInventory();
					return;
				}
				player.sendMessage(ChatColor.GREEN + "┌────────영역 목록────────┐");
				for(short count =0; count <arealist.length;count++)
				{
					player.sendMessage(ChatColor.GREEN +"  "+ arealist[count].toString());
				}
				player.sendMessage(ChatColor.GREEN + "└────────영역 목록────────┘");
				player.sendMessage(ChatColor.DARK_AQUA + "[퀘스트] : 방문해야 할 영역 이름을 적어 주세요!");
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Visit");
				u.setString(player, (byte)2,QuestName);
				player.closeInventory();
				return;
			case "전달":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Give");
				u.setString(player, (byte)3,QuestName);
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+ChatColor.RED+"수집품을 먼저 준비하신 후,"+ChatColor.GREEN+" 받을 NPC를 우클릭 하세요!");
				player.closeInventory();
				return;
			case "사냥":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Hunt");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"N");
				u.setInt(player, (byte)2, -1);
				u.setInt(player, (byte)3, -1);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 사냥 해야 할 몬스터를 우클릭 하세요!");
				player.closeInventory();
				return;
			case "대화":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Talk");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 대화 해야 할 NPC를 우클릭 하세요!");
				player.closeInventory();
				return;
			case "보상":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Present");
				u.setString(player, (byte)3,QuestName);
				u.setInt(player, (byte)5, -1);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+ChatColor.RED+"보상 아이템을 먼저 준비하신 후,"+ChatColor.GREEN+" 보상을 줄 NPC를 우클릭 하세요!");
				player.closeInventory();
				return;
			case "이동":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"TelePort");
				u.setString(player, (byte)3,QuestName);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 플레이어를 이동 시킬 위치에 좌클릭 하세요!");
				player.closeInventory();
				return;
			case "채집":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Harvest");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"null");

				u.setInt(player, (byte)1, 0);//블록 ID
				u.setInt(player, (byte)2, 0);//블록 DATA
				u.setInt(player, (byte)3, -1);//여러개 등록
				u.setInt(player, (byte)4, -1);//여러개 등록
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 채집해야 할 블록을 우클릭 하세요!");
				player.closeInventory();
				return;
			case "블록":
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"BlockPlace");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"null");
				
				u.setInt(player, (byte)1, 0);//블록 ID
				u.setInt(player, (byte)2, 0);//블록 DATA
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : 블록이 설치될 지점을 우클릭 하세요!");
				player.closeInventory();
				return;
			case "소리":
				return;
			case "귓말":
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Whisper");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : 어떤 메시지를 전달하고 싶으신가요?");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
				player.sendMessage(ChatColor.GOLD + "%value%"+ChatColor.WHITE + " - 플레이어의 현재 퀘스트 변수 지칭하기 -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
				return;
			case "전체":
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"BroadCast");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : 어떤 메시지를 전달하고 싶으신가요?");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - 플레이어 지칭하기 -");
				player.sendMessage(ChatColor.GOLD + "%value%"+ChatColor.WHITE + " - 플레이어의 현재 퀘스트 변수 지칭하기 -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
				return;
			case "이전 목록":
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				FixQuestGUI(player,(short) 0,QuestName);
				return;
			case "닫기":
				s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
		}

		if(size != 0)
		{
			switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
			{
			case "계산":
			{
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Cal");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : 어떤 연산을 하시고 싶은가요?");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"1. "+ChatColor.WHITE + "A ＋ B (플레이어 변수에 B를 더합니다.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"2. "+ChatColor.WHITE + "A － B (플레이어 변수에 B를 뺍니다.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"3. "+ChatColor.WHITE + "A × B (플레이어 변수를 B로 곱합니다.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"4. "+ChatColor.WHITE + "A ÷ B (플레이어 변수를 B로 나눈 몫을 구합니다.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"5. "+ChatColor.WHITE + "A ％ B (플레이어 변수를 B로 나눈 나머지를 구합니다.)");
				player.sendMessage(ChatColor.GRAY + "(최소 1 ~ 최대 5 사이 값 입력)");
				player.closeInventory();
			}
			return;
			case "퀘스트 초기화":
			{
				
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "QuestReset");
		    	QuestConfig.saveConfig();
				FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			case "퀘스트 실패":
				{
					s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
					QuestConfig.set(QuestName+".FlowChart."+size+".Type", "QuestFail");
	    	    	QuestConfig.saveConfig();
	    			FixQuestGUI(player, (short) 0, QuestName);
				}
			break;
			case "IF":
			{
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"IFTS");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[퀘스트] : 어떤 비교를 하시고 싶은가요?");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"1. "+ChatColor.WHITE + "A == B (플레이어 변수와 B가 같은가?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"2. "+ChatColor.WHITE + "A != B (플레이어 변수와 B가 다른가?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"3. "+ChatColor.WHITE + "A > B (플레이어 변수가 B보다 큰가?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"4. "+ChatColor.WHITE + "A < B (플레이어 변수가 B보다 작은가?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"5. "+ChatColor.WHITE + "A >= B (플레이어 변수가 B보다 크거나 같은가?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"6. "+ChatColor.WHITE + "A <= B (플레이어 변수가 B보다 작거나 같은가?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"7. "+ChatColor.WHITE + "C <= A <= B (플레이어 변수가 최소 C ~ 최대 B 사이인가?)");
				player.sendMessage(ChatColor.GRAY + "(최소 1 ~ 최대 7 사이 값 입력)");
				player.closeInventory();
			}
			break;
			case "ELSE":
			{
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "ELSE");
    	    	QuestConfig.saveConfig();
    			FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			case "ENDIF":
			{
				s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "ENDIF");
    	    	QuestConfig.saveConfig();
    			FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			}
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
			player.sendMessage(ChatColor.RED+ "[퀘스트] : 해당 항목은 첫 번째 구성 요소로 올 수 없습니다!");
		}
		return;
	}

	public void QuestScriptTypeGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 13)
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName());
			YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
			PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
			PlayerQuestList.saveConfig();
			player.closeInventory();
			QuestRouter(player, QuestName);
		}
	}
	
	public void GetterItemSetingGUIClick(InventoryClickEvent event)
	{
		if(event.getClickedInventory().getTitle().compareTo("container.inventory") != 0)
			if(event.getSlot() == 8)
				event.getWhoClicked().closeInventory();
	}
	
	public void ShowNeedGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			if(slot == 18)
			{
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(1)).equalsIgnoreCase("false"))
					MyQuestListGUI(player, (short) 0);
				else
					FixQuestGUI(player, (short) 0, ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1)));
			}
			else if(slot == 22)
			{
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager YM,QuestList  = YC.getNewConfig("Quest/QuestList.yml");
				YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
				
				String QuestName =  ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
				short QuestFlow =  Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(22).getItemMeta().getLore().get(1)));

				if(QuestList.contains(QuestName+".FlowChart."+QuestFlow+".Item") == true)
				{
					Object[] p =QuestList.getConfigurationSection(QuestName+".FlowChart."+QuestFlow+".Item").getKeys(false).toArray();
					short emptySlot = 0;
					ItemStack item[] = new ItemStack[p.length];
					
					for(int counter = 0; counter < p.length; counter++)
						item[counter] = QuestList.getItemStack(QuestName+".FlowChart."+QuestFlow+".Item."+counter);
					
					for(int counter = 0; counter < player.getInventory().getSize(); counter++)
					{
						if(player.getInventory().getItem(counter) == null)
							emptySlot++;
					}
					
					if(emptySlot >= item.length)
					{
						for(short counter = 0;counter < p.length; counter++)
							player.getInventory().addItem(item[counter]);
					}
					else
					{
						s.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
						player.sendMessage(ChatColor.YELLOW + "[퀘스트] : 현재 플레이어의 인벤토리 공간이 충분하지 않아 보상을 받을 수 없습니다!");
						return;
					}
				}
				GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".Money"), 0, false);

		    	if(YC.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
		    	{
		    		YM=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love", QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Love"));
		    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career", 0);
		    		YM.saveConfig();
		    	}
		    	else
		    	{
		    		YM=YC.getNewConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    		int ownlove = YM.getInt(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love");
		    		int owncareer = YM.getInt(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career");
		    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love", ownlove +QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Love"));
		    		YM.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career", owncareer +QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Career"));
		    		YM.saveConfig();
		    	}
	    		if(QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".EXP") != 0)
	    			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, 0, QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".EXP"), null, false, false);
				
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				player.closeInventory();
				QuestRouter(player, QuestName);
			}
		}
	}
	
	public void PresentItemSettingGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		if(slot <= 2 || slot == 8)
		{
			if(event.getClickedInventory().getTitle().compareTo("container.inventory") != 0)
			{
				event.setCancelled(true);
				if(slot == 8)
				{
					s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
					player.closeInventory();
				}
				else
				{
					s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
					UserData_Object u = new UserData_Object();
					u.setType(player, "Quest");
					if(slot == 0)
					{
						player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 지급할 포상금을 입력 해 주세요. ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)4,"M");
					}
					else if(slot == 1)
					{
						u.setInt(player, (byte)2, 0);
						player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 상승시킬 경험치를 입력 해 주세요. ("+ChatColor.YELLOW + "1"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)4,"E");
					}
					else if(slot == 2)
					{
						u.setInt(player, (byte)3, 0);
						player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 상승시킬 NPC의 호감도를 입력 해 주세요. ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)4,"L");
					}
					player.closeInventory();
				}
			}
		}
	}
	
	public void KeepGoingClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		if(slot == 16)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			UserData_Object u = new UserData_Object();
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(16).getItemMeta().getLore().get(1));
			short Flow = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(10).getItemMeta().getLore().get(1)));
			short Mob = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(10).getItemMeta().getLore().get(2)));
			if(event.getCurrentItem().getItemMeta().getLore().get(0).contains("사냥"))
			{
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Hunt");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"N");
				u.setInt(player, (byte)2, Mob+1);
				u.setInt(player, (byte)3, Flow);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 사냥 해야 할 몬스터를 우클릭 하세요!");
			}
			else
			{
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Harvest");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"null");
				u.setInt(player, (byte)1, 0);
				u.setInt(player, (byte)2, 0);
				u.setInt(player, (byte)3, Flow);
				u.setInt(player, (byte)4, Mob+1);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 채집 해야 할 블록을 우클릭 하세요!");
			}
			player.closeInventory();
		}
	}
	
	public void QuestOptionGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		if(slot == 44)
		{

			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(44).getItemMeta().getLore().get(1));
			if(slot == 36)
				AllOfQuestListGUI(player, (short) 0,false);
			else if(slot == 15)//스킬 랭크 제한
			{
				s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
				//스킬 선택 및 스킬 랭크 입력하는 창 업데이트 하기
			}
			else if(slot == 4)//퀘스트 타입
			{
				switch(QuestList.getString(QuestName + ".Type"))
				{
				case "N" :
					QuestList.set(QuestName+".Type", "R");
					break;
				case "R" :
					QuestList.set(QuestName+".Type", "D");
					break;
				case "D" :
					QuestList.set(QuestName+".Type", "W");
					break;
				case "W" :
					QuestList.set(QuestName+".Type", "M");
					break;
				case "M" :
					QuestList.set(QuestName+".Type", "N");
					break;
				}
				QuestList.saveConfig();
				QuestOptionGUI(player, QuestName);
			}
			else if(slot == 29)//필수 완료 퀘스트
			{
				if(event.isLeftClick() == true && event.isShiftClick() == false)
				{
					UserData_Object u = new UserData_Object();
					u.setString(player, (byte)1,QuestName);
					AllOfQuestListGUI(player, (short) 0, true);
				}
				else if(event.isRightClick() == true && event.isShiftClick() == true)
				{
					s.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
					QuestList.set(QuestName+".Need.PrevQuest", "null");
					QuestList.saveConfig();
					QuestOptionGUI(player, QuestName);
				}
			}
			else
			{
				UserData_Object u = new UserData_Object();
				u.setType(player, "Quest");
				u.setString(player, (byte)2,QuestName);
				player.closeInventory();
				if(slot == 11)//레벨 제한
				{
					u.setString(player, (byte)1,"Level District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 몇 레벨 부터 수행 가능하게 하시겠습니까? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"레벨)");
				}
				else if(slot == 13)//호감도 제한
				{
					u.setString(player, (byte)1,"Love District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 호감도 몇 이상부터 수행 가능하게 하시겠습니까? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 20)//STR 제한
				{
					u.setString(player, (byte)1,"STR District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GBD_RPG.Main_Main.Main_ServerOption.STR+"이 몇 이상 되어야 수행 가능하게 하시겠습니까? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 21)//DEX 제한
				{
					u.setString(player, (byte)1,"DEX District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GBD_RPG.Main_Main.Main_ServerOption.DEX+"가 몇 이상 되어야 수행 가능하게 하시겠습니까? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 22)//INT 제한
				{
					u.setString(player, (byte)1,"INT District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GBD_RPG.Main_Main.Main_ServerOption.INT+"이 몇 이상 되어야 수행 가능하게 하시겠습니까? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 23)//WILL 제한
				{
					u.setString(player, (byte)1,"WILL District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GBD_RPG.Main_Main.Main_ServerOption.WILL+"가 몇 이상 되어야 수행 가능하게 하시겠습니까? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 24)//LUK 제한
				{
					u.setString(player, (byte)1,"LUK District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+GBD_RPG.Main_Main.Main_ServerOption.LUK+"이 몇 이상 되어야 수행 가능하게 하시겠습니까? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 33)//퀘스트 한정
				{
					u.setString(player, (byte)1,"Accept District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 몇 명만 수행 가능하게 하시겠습니까? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"명)");
				}
			}
		}
	}
	
	public void Quest_NavigationListGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			SelectObjectPage(player, (byte) 0, QuestName);
			return;
		case 53://나가기
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			Quest_NavigationListGUI(player, (short) (page-1), QuestName);
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			Quest_NavigationListGUI(player, (short) (page+1), QuestName);
			return;
		default :
			if(event.isLeftClick() == true)
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
	    		int size = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size();
	    		QuestConfig.set(QuestName+".FlowChart."+size+".Type", "Nevigation");
		    	QuestConfig.set(QuestName+".FlowChart."+size+".NeviUTC",ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
		    	QuestConfig.saveConfig();
				player.sendMessage(ChatColor.GREEN+"[퀘스트] : 네비게이션이 성공적으로 등록되었습니다!");
				FixQuestGUI(player, (short) 0, QuestName);
			}
			return;
		}
	}
	
	public void Quest_OPChoiceClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();

		short page = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(18).getItemMeta().getLore().get(1)));
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
		switch (event.getSlot())
		{
		case 18://이전 목록
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			FixQuestGUI(player, page, QuestName);
			return;
		case 26://나가기
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void Quest_UserChoiceClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		if(event.getSlot() == 26)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		else
		{
			YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
			YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
			YamlManager PlayerVarList  = YC.getNewConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
			
			short Flow = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(2)));
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
			int ChoiceLevel = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size();
			byte Slot = (byte) event.getSlot();
			
			if(event.getCurrentItem()!= null)
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			
			if((ChoiceLevel==1&&Slot==13)||(ChoiceLevel==2&&Slot==12)||(ChoiceLevel==3&&Slot==11)||(ChoiceLevel==4&&Slot==10))
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.0.Var"));
			else if((ChoiceLevel==2&&Slot==14)||(ChoiceLevel==3&&Slot==13)||(ChoiceLevel==4&&Slot==12))
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var"));
			else if((ChoiceLevel==3&&Slot==15)||(ChoiceLevel==4&&Slot==14))
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var"));
			else if(ChoiceLevel==4&&Slot==16)
				PlayerVarList.set(QuestName, QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.3.Var"));
			PlayerVarList.saveConfig();
			PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
			PlayerQuestList.saveConfig();
			player.closeInventory();
			QuestRouter(player, QuestName);
		}
	}
	
	
	public void GetterItemSetingGUIClose(InventoryCloseEvent event)
	{
		Player player = (Player)event.getPlayer();
		UserData_Object u = new UserData_Object();
		u.setBoolean(player, (byte)1, false);
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
		
		String QuestName = u.getString(player, (byte)3);
		Object[] b = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
		QuestConfig.set(QuestName+".FlowChart."+b.length+".Type", "Give");
		QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCname", u.getString(player, (byte)2));
		QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCuuid", u.getString(player, (byte)1));
		byte itemacount = 0;
		for(byte count = 0; count <8; count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(event.getInventory().getItem(count).getAmount() > 0)
				{
					QuestConfig.set(QuestName+".FlowChart."+b.length+".Item."+itemacount, event.getInventory().getItem(count));
					itemacount++;
				}
			}
		}
		QuestConfig.saveConfig();
		s.SP((Player) event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
    	event.getPlayer().sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
		u.clearAll(player);
		return;
	}

	public void PresentItemSettingGUIClose(InventoryCloseEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player)event.getPlayer();
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestConfig=YC.getNewConfig("Quest/QuestList.yml");
		UserData_Object u = new UserData_Object();
		String QuestName = u.getString(player, (byte)3);
		
		if(u.getInt(player, (byte)5) == -1)
		{
			Object[] b = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
			u.setInt(player, (byte)5, b.length);
			QuestConfig.set(QuestName+".FlowChart."+b.length+".Type", "Present");
			QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCname", u.getString(player, (byte)2));
			QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCuuid", u.getString(player, (byte)1));
			byte itemacount = 0;
			for(byte count = 3; count <8; count++)
			{
				if(event.getInventory().getItem(count) != null)
				{
					if(event.getInventory().getItem(count).getAmount() > 0)
					{
						QuestConfig.set(QuestName+".FlowChart."+b.length+".Item."+itemacount, event.getInventory().getItem(count));
						itemacount++;
					}
				}
			}
			QuestConfig.saveConfig();
		}
		else
		{
			if(u.getInt(player, (byte)1) == -1)
				u.setInt(player, (byte)1, 0);
			if(u.getInt(player, (byte)2) == -1)
				u.setInt(player, (byte)2, 0);
			if(u.getInt(player, (byte)3) == -1)
				u.setInt(player, (byte)3, 0);
			
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".Type", "Present");
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".TargetNPCname", u.getString(player, (byte)2));
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".TargetNPCuuid", u.getString(player, (byte)1));
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".Money", u.getInt(player, (byte)1));
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".EXP", u.getInt(player, (byte)2));
			QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".Love", u.getInt(player, (byte)3));
			byte itemacount = 0;
			for(byte count = 3; count <8; count++)
			{
				if(event.getInventory().getItem(count) != null)
				{
					if(event.getInventory().getItem(count).getAmount() > 0)
					{
						QuestConfig.set(QuestName+".FlowChart."+u.getInt(player, (byte)5)+".Item."+itemacount, event.getInventory().getItem(count));
						itemacount++;
					}
				}
			}
			QuestConfig.saveConfig();
		}
		if(u.getString(player, (byte)4)==null)
		{
			QuestConfig.saveConfig();
			s.SP((Player) event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
	    	event.getPlayer().sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 설정되었습니다!");
	    	u.clearAll(player);
		}
		return;
	}
	
	public String SkullType(String s)
	{
		String re = s;
		switch(s)
		{
			case "Zombie" : re = ChatColor.DARK_GREEN+"좀비"; break;
			case "Skeleton" : re = ChatColor.GRAY+"스켈레톤"; break;
			case "Giant" : re = ChatColor.DARK_GREEN+"자이언트"; break;
			case "Spider" : re = ChatColor.GRAY+"거미"; break;
			case "Witch" : re = ChatColor.DARK_PURPLE+"마녀"; break;
			case "Creeper" : re = ChatColor.GREEN+"크리퍼"; break;
			case "Slime" : re = ChatColor.GREEN+"슬라임"; break;
			case "Ghast" : re = ChatColor.GRAY+"가스트"; break;
			case "Enderman" : re = ChatColor.DARK_PURPLE+"엔더맨"; break;
			case "Zombie Pigman" : re = ChatColor.LIGHT_PURPLE+"좀비 피그맨"; break;
			case "Cave Spider" : re = ChatColor.GRAY+"동굴 거미"; break;
			case "Silverfish" : re = ChatColor.GRAY+"좀벌레"; break;
			case "Blaze" : re = ChatColor.YELLOW+"블레이즈"; break;
			case "Magma Cube" : re = ChatColor.YELLOW+"마그마 큐브"; break;
			case "Bat" : re = ChatColor.GRAY+"박쥐"; break;
			case "Endermite" : re = ChatColor.DARK_PURPLE+"엔더 진드기"; break;
			case "Guardian" : re = ChatColor.DARK_AQUA+"가디언"; break;
			case "Pig" : re = ChatColor.LIGHT_PURPLE+"돼지"; break;
			case "Sheep" : re = ChatColor.WHITE+"양"; break;
			case "Cow" : re = ChatColor.GRAY+"소"; break;
			case "Chicken" : re = ChatColor.WHITE+"닭"; break;
			case "Squid" : re = ChatColor.GRAY+"오징어"; break;
			case "Wolf" : re = ChatColor.WHITE+"늑대"; break;
			case "Mooshroom" : re = ChatColor.RED+"버섯 소"; break;
			case "Ocelot" : re = ChatColor.YELLOW+"오셀롯"; break;
			case "Horse" : re = ChatColor.GOLD+"말"; break;
			case "Rabbit" : re = ChatColor.WHITE+"토끼"; break;
			case "Villager" : re = ChatColor.GOLD+"주민"; break;
			case "Snow Golem" : re = ChatColor.WHITE+"눈 사람"; break;
			case "Iron Golem" : re = ChatColor.WHITE+"철 골렘"; break;
			case "Wither" : re = ChatColor.GRAY+""+ChatColor.BOLD+"위더"; break;
			case "unknown" : re = ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"엔더 드래곤"; break;
			default :
				re = s; break;
		}
		return re;
	}
}
