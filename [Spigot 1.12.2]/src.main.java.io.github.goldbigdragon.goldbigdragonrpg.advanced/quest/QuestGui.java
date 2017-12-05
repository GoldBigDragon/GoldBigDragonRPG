package quest;

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

import effect.PottionBuff;
import effect.SoundEffect;
import servertick.ServerTickMain;
import user.UserDataObject;
import util.UtilGui;
import util.YamlLoader;

public class QuestGui extends UtilGui
{
	public void MyQuestListGUI(Player player, short page)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");
		YamlLoader PlayerQuestList = new YamlLoader();
		PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		String UniqueCode = "§0§0§5§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0퀘스트 목록 : " + (page+1));

		String[] a = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray(new String[0]);
		String QuestType = "";
		int ItemID = 0;
		byte ItemAmount = 1;
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(QuestList.contains(a[count])==false)
			{
				PlayerQuestList.removeKey("Started."+a[count]);
				PlayerQuestList.saveConfig();
			}
			else
			{
				switch(QuestList.getString(a[count] + ".Type"))
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

				switch(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".Type"))
				{
				case "Nevigation":
					removeFlagStack("§f§l" + a[count], ItemID,0,ItemAmount,Arrays.asList("§f화살표를 따라가자.",""), loc, inv);
					break;
				case "Choice":
					removeFlagStack("§f§l" + a[count], ItemID,0,ItemAmount,Arrays.asList("§f하고싶은 말을 선택하자.","","§e[좌클릭시 선택지 확인.]"), loc, inv);
					break;
				case "Script" :
					removeFlagStack("§f§l" + a[count], ItemID,0,ItemAmount,Arrays.asList("§e"+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".NPCname")+"§f와 대화를 해 보자."), loc, inv);
					break;
				case "PScript" :
					removeFlagStack("§f§l" + a[count], ItemID,0,ItemAmount,Arrays.asList("§e[좌클릭시 독백 확인]"), loc, inv);
					break;
				case "Visit" :
					YamlLoader AreaList = new YamlLoader();
					AreaList.getConfig("Area/AreaList.yml");
					String AreaWorld = AreaList.getString(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".World");
					String AreaName = AreaList.getString(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".Name");
					int AreaX = AreaList.getInt(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".SpawnLocation.X");
					short AreaY = (short) AreaList.getInt(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".SpawnLocation.Y");
					int AreaZ = AreaList.getInt(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".SpawnLocation.Z");
					removeFlagStack("§f§l" + a[count], ItemID,0,ItemAmount,Arrays.asList("§e"+AreaName+"§f 지역을 방문하자."
							,"§e월드 : §f"+AreaWorld,"§eX 좌표 : §f"+AreaX,"§eY 좌표 : §f"+AreaY,"§eZ 좌표 : §f"+AreaZ), loc, inv);
					break;
				case "Talk" :
					removeFlagStack("§f§l" + a[count], ItemID,0,ItemAmount,Arrays.asList("§e"+QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".TargetNPCname")+"§f에게 말을 걸어 보자."), loc, inv);
					break;
				case "Give" :
					removeFlagStack("§f§l" + a[count], ItemID,0,ItemAmount,Arrays.asList("§e"+QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".TargetNPCname")+"§f가 부탁한","§f물품을 전달하자.","","§e[좌클릭시 전달 품목 확인.]"), loc, inv);
					break;
				case "Hunt":
					removeFlagStack("§f§l" +a[count], ItemID,0,ItemAmount,Arrays.asList("§f목표 대상을 처치하자.","","§e[좌클릭시 처치 대상 확인]"), loc, inv);
					break;
				case "Harvest":
					removeFlagStack("§f§l" +a[count], ItemID,0,ItemAmount,Arrays.asList("§f블록을 채집하자.","","§e[좌클릭시 채집 블록 확인]"), loc, inv);
					break;
				case "Present" :
					removeFlagStack("§f§l" + a[count], ItemID,0,ItemAmount,Arrays.asList("§e"+QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".TargetNPCname")+"§f에게","§f보상을 받자.","","§e[좌클릭시 보상 확인.]"), loc, inv);
					break;
				}
				loc++;
			}
		}
		
		if(a.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void AllOfQuestListGUI(Player player, short page,boolean ChoosePrevQuest)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0전체 퀘스트 목록 : " + (page+1));

		String[] a = QuestList.getKeys().toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			int QuestFlowSize = QuestList.getConfigurationSection(a[count]+".FlowChart").getKeys(false).size();
			if(count > a.length || loc >= 45) break;
			if(ChoosePrevQuest == false)
			{
				switch(QuestList.getString(a[count].toString() + ".Type"))
				{
				case "N" :
					removeFlagStack("§f§l" + a[count], 340,0,1,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 일반 퀘스트","","§e[우클릭시 세부 설정을 합니다.]","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case "R" :
					removeFlagStack("§f§l" + a[count], 386,0,1,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 반복 퀘스트","","§e[우클릭시 세부 설정을 합니다.]","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case "D" :
					removeFlagStack("§f§l" + a[count], 403,0,1,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 일일 퀘스트","","§e[우클릭시 세부 설정을 합니다.]","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case "W" :
					removeFlagStack("§f§l" + a[count], 403,0,7,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 일주 퀘스트","","§e[우클릭시 세부 설정을 합니다.]","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case "M" :
					removeFlagStack("§f§l" + a[count], 403,0,31,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 한달 퀘스트","","§e[우클릭시 세부 설정을 합니다.]","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				}
			}
			else
			{
				switch(QuestList.getString(a[count].toString() + ".Type"))
				{
				case "N" :
					removeFlagStack("§f§l" + a[count], 340,0,1,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 일반 퀘스트",""), loc, inv);
					break;
				case "R" :
					removeFlagStack("§f§l" + a[count], 386,0,1,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 반복 퀘스트",""), loc, inv);
					break;
				case "D" :
					removeFlagStack("§f§l" + a[count], 403,0,1,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 일일 퀘스트",""), loc, inv);
					break;
				case "W" :
					removeFlagStack("§f§l" + a[count], 403,0,7,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 일주 퀘스트",""), loc, inv);
					break;
				case "M" :
					removeFlagStack("§f§l" + a[count], 403,0,31,Arrays.asList("§f퀘스트 구성 요소 : "+QuestFlowSize+"개","§3퀘스트 타입 : 한달 퀘스트",""), loc, inv);
					break;
				}
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		if(ChoosePrevQuest == false)
			removeFlagStack("§f§l새 퀘스트", 386,0,1,Arrays.asList("§7새로운 퀘스트를 생성합니다."), 49, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ChoosePrevQuest), 53, inv);
		player.openInventory(inv);
	}
	
	public void FixQuestGUI(Player player, short page, String QuestName)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode +"§0퀘스트 흐름도 : " + (page+1));
		int flowChartSize = QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size();
		
		byte loc=0;
		for(int count = page*45; count < flowChartSize;count++)
		{
			if(count > flowChartSize || loc >= 45) break;
			switch(QuestList.getString(QuestName+".FlowChart."+count+".Type"))
			{
				case "Cal":
				switch(QuestList.getInt(QuestName+".FlowChart."+count+".Comparison"))
				{
				case 1:
					removeFlagStack("§f§l"+count, 137,0,1,Arrays.asList("§f타입 : 계산","","§3[     계산 식     ]","§3플레이어 변수 ＋ "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 2:
					removeFlagStack("§f§l"+count, 137,0,1,Arrays.asList("§f타입 : 계산","","§3[     계산 식     ]","§3플레이어 변수 － "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 3:
					removeFlagStack("§f§l"+count, 137,0,1,Arrays.asList("§f타입 : 계산","","§3[     계산 식     ]","§3플레이어 변수 × "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 4:
					removeFlagStack("§f§l"+count, 137,0,1,Arrays.asList("§f타입 : 계산","","§3[     계산 식     ]","§3플레이어 변수 ÷ "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 5:
					removeFlagStack("§f§l"+count, 137,0,1,Arrays.asList("§f타입 : 계산","","§3[     계산 식     ]","§3플레이어 변수 ％ "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				}
				break;
			case "IF":
				switch(QuestList.getInt(QuestName+".FlowChart."+count+".Comparison"))
				{
				case 1:
					removeFlagStack("§f§l"+count, 184,0,1,Arrays.asList("§f타입 : IF","","§3[     비교 식     ]","§3플레이어 변수 == "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 2:
					removeFlagStack("§f§l"+count, 184,0,1,Arrays.asList("§f타입 : IF","","§3[     비교 식     ]","§3플레이어 변수 != "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 3:
					removeFlagStack("§f§l"+count, 184,0,1,Arrays.asList("§f타입 : IF","","§3[     비교 식     ]","§3플레이어 변수 > "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 4:
					removeFlagStack("§f§l"+count, 184,0,1,Arrays.asList("§f타입 : IF","","§3[     비교 식     ]","§3플레이어 변수 < "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 5:
					removeFlagStack("§f§l"+count, 184,0,1,Arrays.asList("§f타입 : IF","","§3[     비교 식     ]","§3플레이어 변수 >= "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 6:
					removeFlagStack("§f§l"+count, 184,0,1,Arrays.asList("§f타입 : IF","","§3[     비교 식     ]","§3플레이어 변수 <= "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				case 7:
					removeFlagStack("§f§l"+count, 184,0,1,Arrays.asList("§f타입 : IF","","§3[     비교 식     ]","§3"+QuestList.getInt(QuestName+".FlowChart."+count+".Min")+" <= 플레이어 변수 <= "+QuestList.getInt(QuestName+".FlowChart."+count+".Max"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
					break;
				}
				break;
			case "QuestFail":
				removeFlagStack("§f§l"+count, 166,0,1,Arrays.asList("§f타입 : 퀘스트 실패","","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "QuestReset":
				removeFlagStack("§f§l"+count, 395,0,1,Arrays.asList("§f타입 : 퀘스트 초기화","","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "ELSE":
				removeFlagStack("§f§l"+count, 167,0,1,Arrays.asList("§f타입 : ELSE","","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "ENDIF":
				removeFlagStack("§f§l"+count, 191,0,1,Arrays.asList("§f타입 : ENDIF","","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "VarChange":
				removeFlagStack("§f§l"+count, 143,0,1,Arrays.asList("§f타입 : 변수 변경","§f변경 값 : " + QuestList.getInt(QuestName+".FlowChart."+count+".Value") ,"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Choice":
				int button = QuestList.getConfigurationSection(QuestName+".FlowChart."+count+".Choice").getKeys(false).size();
				removeFlagStack("§f§l"+count, 72,0,button,Arrays.asList("§f타입 : 선택","§f선택지 개수 : " +button+"개" ,"","§e[좌클릭시 선택창 확인]","","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Nevigation":
			{
				String UTC = QuestList.getString(QuestName+".FlowChart."+count+".NeviUTC");
				YamlLoader NavigationConfig = new YamlLoader();
				NavigationConfig.getConfig("Navigation/NavigationList.yml");
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
					
					String TimeS = "§3<도착할 때 까지 유지>";
					String sensitiveS = "§9<반경 "+sensitive+"블록 이내를 도착지로 판정>";
					String ShowArrowS = "§3<기본 화살표 모양>";
					if(Time >= 0)
						TimeS = "§3<"+Time+"초 동안 유지>";
					switch(ShowArrow)
					{
					default:
						ShowArrowS = "§3<기본 화살표 모양>";
						break;
					}
					removeFlagStack("§f§l" + count, 395,0,1,Arrays.asList(
					"§e§l"+NaviName,"",
					"§9[도착 지점]","§9월드 : §f"+world,
					"§9좌표 : §f"+x+","+y+","+z,sensitiveS,"",
					"§3[기타 옵션]",TimeS,ShowArrowS,""
					,"§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				}
				else
					removeFlagStack("§f§l" + count, 166,0,1,Arrays.asList("§c[네비게이션을 찾을 수 없습니다!]","","§c[Shift + 우클릭시 삭제됩니다.]"),loc,inv);
			}
				break;
			case "Whisper":
			{
				String script3 = "§f타입 : 귓말%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%§c[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptA3 = script3.split("%enter%");
				for(int counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = "§f"+ scriptA3[counter];
				removeFlagStack("§f§l"+count, 421,0,1,Arrays.asList(scriptA3), loc, inv);
			}
			break;
			case "BroadCast":
			{
				String script3 = "§f타입 : 전체%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%§c[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptA3 = script3.split("%enter%");
				for(int counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = "§f"+ scriptA3[counter];
				removeFlagStack("§f§l"+count, 138,0,1,Arrays.asList(scriptA3), loc, inv);
			}
			break;
			case "Script":
				String script = "§f타입 : 대사%enter%§f말하는 주체 : "+QuestList.getString(QuestName+".FlowChart."+count+".NPCname")+"%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Script")+"%enter% %enter%§c[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptA = script.split("%enter%");
				for(int counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] = "§f"+ scriptA[counter];
			removeFlagStack("§f§l"+count, 323,0,1,Arrays.asList(scriptA), loc, inv);
			break;
			case "PScript":
				String script3 = "§f타입 : 대사%enter%§f말하는 주체 : 플레이어%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%§c[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptA3 = script3.split("%enter%");
				for(int counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = "§f"+ scriptA3[counter];
			removeFlagStack("§f§l"+count, 323,0,1,Arrays.asList(scriptA3), loc, inv);
			break;
			case "Visit":
			removeFlagStack("§f§l"+count, 345,0,1,Arrays.asList("§f타입 : 방문","§f방문 지점 : "+QuestList.getString(QuestName+".FlowChart."+count+".AreaName"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Give":
				String script2 = "§f타입 : 전달%enter%§f전달 대상 : §e"+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname")+"%enter%§fNPC의 UUID%enter%§3"+ QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid")+"%enter%%enter%§e[좌클릭시 전달 품목 확인]%enter%§c[Shift + 우클릭시 삭제됩니다.]";
				String[] scriptB = script2.split("%enter%");
				for(int counter = 0; counter < scriptB.length; counter++)
					scriptB[counter] = "§f"+ scriptB[counter];
			removeFlagStack("§f§l"+count, 388,0,1,Arrays.asList(scriptB), loc, inv);
				break;
			case "Hunt":
				removeFlagStack("§f§l"+count, 267,0,1,Arrays.asList("§f타입 : 사냥","","§e[좌클릭시 처치 대상 확인]","","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Talk":
				removeFlagStack("§f§l"+count, 397,3,1,Arrays.asList("§f타입 : 대화","","§f만나야 할 NPC 이름","","§e"+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname"),"","§fNPC의 UUID","","§3"+ QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Present":
				removeFlagStack("§f§l"+count, 54,0,1,Arrays.asList("§f타입 : 보상","§f수령 대상 : §e"+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname"),"§fNPC의 UUID","","§3"+ QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid"),"","","§e[좌클릭시 보상 확인]","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "TelePort":
			removeFlagStack("§f§l"+count, 368,0,1,Arrays.asList("§f타입 : 이동","","§f월드 : "+QuestList.getString(QuestName+".FlowChart."+count+".World"),"§f좌표 : " + (int)QuestList.getDouble(QuestName+".FlowChart."+count+".X")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Y")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Z"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "Harvest":
				removeFlagStack("§f§l"+count, 56,0,1,Arrays.asList("§f타입 : 채집","","§e[좌클릭시 채집 블록 확인]","","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			case "BlockPlace":
				removeFlagStack("§f§l"+count, 152,0,1,Arrays.asList("§f타입 : 블록 설치","§f월드 : "+QuestList.getString(QuestName+".FlowChart."+count+".World"),"§f좌표 : " + (int)QuestList.getDouble(QuestName+".FlowChart."+count+".X")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Y")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Z"),"§f블록 타입 : " + QuestList.getInt(QuestName+".FlowChart."+count+".ID")+":"+ QuestList.getInt(QuestName+".FlowChart."+count+".DATA"),"","§c[Shift + 우클릭시 삭제됩니다.]"), loc, inv);
				break;
			}
			loc++;
		}
		
		if(flowChartSize-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l새 오브젝트 추가", 2,0,1,Arrays.asList("§7새로운 오브젝트를 추가합니다."), 49, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ ChatColor.stripColor(QuestName)), 53, inv);
		player.openInventory(inv);
	}

	public void SelectObjectPage(Player player, byte page, String QuestName)
	{
		String UniqueCode = "§0§0§5§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0오브젝트 추가");

		removeFlagStack("§f§l대사", 323,0,1,Arrays.asList("§7대화창을 띄우고, 작성된","§7스크립트를 유저에게 띄웁니다.","§7(화자 : NPC)"), 0, inv);
		removeFlagStack("§f§l독백", 323,0,1,Arrays.asList("§7대화창을 띄우고, 작성된","§7스크립트를 유저에게 띄웁니다.","§7(화자 : 유저)"), 1, inv);
		removeFlagStack("§f§l방문", 345,0,1,Arrays.asList("§7플레이어에게 특정 영역에","§7방문하는 퀘스트를 줍니다."), 2, inv);
		removeFlagStack("§f§l전달", 388,0,1,Arrays.asList("§7플레이어가 특정 아이템을","§7NPC에게 줘야하는 퀘스트를 줍니다."), 3, inv);
		removeFlagStack("§f§l사냥", 267,0,1,Arrays.asList("§7플레이어에게 특정 몬스터를","§7사냥하는 퀘스트를 줍니다."), 4, inv);
		removeFlagStack("§f§l대화", 397,3,1,Arrays.asList("§7플레이어에게 특정 NPC에게","§7말을 거는 퀘스트를 줍니다."), 5, inv);
		removeFlagStack("§f§l보상", 54,0,1,Arrays.asList("§7플레이어에게 보상을 줍니다."), 6, inv);
		removeFlagStack("§f§l이동", 368,0,1,Arrays.asList("§7플레이어를 특정 위치로","§7이동 시킵니다."), 7, inv);
		removeFlagStack("§f§l채집", 56,0,1,Arrays.asList("§7플레이어에게 특정 블록을","§7채취하는 퀘스트를 줍니다."), 8, inv);
		removeFlagStack("§f§l블록", 152,0,1,Arrays.asList("§7특정 위치에 정해진","§7블록을 생성합니다."), 9, inv);
		removeFlagStack("§f§l소리§c[사용 불가]", 84,0,1,Arrays.asList("§7특정 위치에 소리가 나게 합니다."), 10, inv);
		removeFlagStack("§f§l귓말", 421,0,1,Arrays.asList("§7플레이어의 채팅창에 메시지가 나타납니다."), 11, inv);
		removeFlagStack("§f§l전체", 138,0,1,Arrays.asList("§7서버 전체에 메시지가 나타납니다."), 12, inv);
		removeFlagStack("§f§l네비", 358,0,1,Arrays.asList("§7플레이어에게 네비게이션을 작동 시킵니다."), 13, inv);
		

		removeFlagStack("§e§l선택", 72,0,1,Arrays.asList("§7플레이어가 원하는 대답을","§7선택 하도록 합니다.","§7선택한 대답에 따라","§7다른 변수값을 가질 수 있습니다."), 36, inv);
		removeFlagStack("§e§l변수", 143,0,1,Arrays.asList("§7플레이어의 변수를 강제로 수정합니다."), 37, inv);
		removeFlagStack("§e§l계산", 137,0,1,Arrays.asList("§7플레이어의 변수를 계산식을","§7사용하여 수정합니다."), 38, inv);
		removeFlagStack("§e§lIF", 184,0,1,Arrays.asList("§7플레이어의 현재 변수값을 확인하여","§7비교한 값과 동일할 경우","§7IF와 ENDIF혹은 IF와 ELSE","§7사이의 구문을 실행하게 됩니다.","","§c[반드시 IF의 개수 = ENDIF의 개수]"), 39, inv);
		removeFlagStack("§e§lELSE", 167,0,1,Arrays.asList("§7플레이어의 현재 변수값이","§7IF 논리에 맞지 않을 경우","§7ELSE와 ENDIF 사이의 구문을","§7실행하게 됩니다.",""), 40, inv);
		removeFlagStack("§e§lENDIF", 191,0,1,Arrays.asList("§7IF의 끝 부분을 나타냅니다.","","§c[반드시 IF의 개수 = ENDIF의 개수]"), 41, inv);
		
		removeFlagStack("§c§l퀘스트 초기화", 395,0,1,Arrays.asList("§7퀘스트를 중간에 포기 합니다.","§a퀘스트를 다시 받을 수 있습니다."), 43, inv);
		removeFlagStack("§c§l퀘스트 실패", 166,0,1,Arrays.asList("§7퀘스트를 중간에 포기 합니다.","§7일반 퀘스트일 경우 플레이어는","§c퀘스트를 다시 받을 수 없습니다."), 44, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ ChatColor.stripColor(QuestName)), 53, inv);
		player.openInventory(inv);
	}

	public void QuestScriptTypeGUI(Player player,String QuestName,String NPCname, short FlowChart, String[] script)
	{
		String UniqueCode = "§0§0§5§0§4§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0[퀘스트]");
		removeFlagStack("§0"+ ChatColor.stripColor(QuestName), 160,8,1,null, 19, inv);
		
		for(int count=0;count < script.length; count++)
			script[count] = script[count].replace("%player%", player.getName());
		if(NPCname.equals(player.getName()))
			stackItem(getPlayerSkull("§e"+NPCname, 1, Arrays.asList(script), player.getName()), 13, inv);
		else
			removeFlagStack("§e"+ NPCname, 386,0,1,Arrays.asList(script), 13, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void QuestOptionGUI(Player player, String QuestName)
	{
		String UniqueCode = "§0§0§5§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§0퀘스트 옵션");

		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");
		
		switch(QuestList.getString(QuestName + ".Type"))
		{
		case "N" :
			removeFlagStack("§f§l퀘스트 타입", 340,0,1,Arrays.asList("§f일반 퀘스트"), 4, inv);
			break;
		case "R" :
			removeFlagStack("§f§l퀘스트 타입", 386,0,1,Arrays.asList("§f반복 퀘스트"), 4, inv);
			break;
		case "D" :
			removeFlagStack("§f§l퀘스트 타입", 403,0,1,Arrays.asList("§f일일 퀘스트"), 4, inv);
			break;
		case "W" :
			removeFlagStack("§f§l퀘스트 타입", 403,0,7,Arrays.asList("§f주간 퀘스트"), 4, inv);
			break;
		case "M" :
			removeFlagStack("§f§l퀘스트 타입", 403,0,31,Arrays.asList("§f월간 퀘스트"), 4, inv);
			break;
		}

		removeFlagStack("§f§l레벨 제한", 384,0,1,Arrays.asList("§f퀘스트 수행에 필요한 레벨을 설정합니다.","§6마비노기§f 시스템일 경우 §e누적레벨§f 기준이며,","§c메이플스토리§f 시스템일 경우 §e레벨§f 기준입니다.","","§b[필요 레벨 : " + QuestList.getInt(QuestName+".Need.LV")+"]"), 11, inv);
		removeFlagStack("§f§lNPC 호감도 제한", 38,0,1,Arrays.asList("§f퀘스트 수행에 필요한","§fNPC와의 호감도를 설정합니다.","","§b[필요 호감도 : " + QuestList.getInt(QuestName+".Need.Love")+"]"), 13, inv);
		removeFlagStack("§f§l스킬 랭크 제한", 403,0,1,Arrays.asList("§f퀘스트 수행에 필요한","§f스킬 랭크를 설정합니다.",""/*,"§b[필요 스킬 : " + QuestList.getString(QuestName+".Need.Skill.Name")+"]","§b[필요 랭크 : " + QuestList.getInt(QuestName+".Need.Skill.Rank")+"]"*/), 15, inv);
		removeFlagStack("§f§l"+main.MainServerOption.statSTR+" 제한", 267,0,1,Arrays.asList("§f퀘스트 수행에 필요한","§f"+main.MainServerOption.statSTR+" 스텟을 설정합니다.","","§b[필요 "+main.MainServerOption.statSTR+" : " + QuestList.getInt(QuestName+".Need.STR")+"]"), 20, inv);
		removeFlagStack("§f§l"+main.MainServerOption.statDEX+" 제한", 261,0,1,Arrays.asList("§f퀘스트 수행에 필요한","§f"+main.MainServerOption.statDEX+" 스텟을 설정합니다.","","§b[필요 "+main.MainServerOption.statDEX+" : " + QuestList.getInt(QuestName+".Need.DEX")+"]"), 21, inv);
		removeFlagStack("§f§l"+main.MainServerOption.statINT+" 제한", 369,0,1,Arrays.asList("§f퀘스트 수행에 필요한","§f"+main.MainServerOption.statINT+" 스텟을 설정합니다.","","§b[필요 "+main.MainServerOption.statINT+" : " + QuestList.getInt(QuestName+".Need.INT")+"]"), 22, inv);
		removeFlagStack("§f§l"+main.MainServerOption.statWILL+" 제한", 370,0,1,Arrays.asList("§f퀘스트 수행에 필요한","§f"+main.MainServerOption.statWILL+" 스텟을 설정합니다.","","§b[필요 "+main.MainServerOption.statWILL+" : " + QuestList.getInt(QuestName+".Need.WILL")+"]"), 23, inv);
		removeFlagStack("§f§l"+main.MainServerOption.statLUK+" 제한", 322,0,1,Arrays.asList("§f퀘스트 수행에 필요한","§f"+main.MainServerOption.statLUK+" 스텟을 설정합니다.","","§b[필요 "+main.MainServerOption.statLUK+" : " + QuestList.getInt(QuestName+".Need.LUK")+"]"), 24, inv);
		if(QuestList.getString(QuestName+".Need.PrevQuest").equalsIgnoreCase("null") == true)
			removeFlagStack("§f§l필수 완료 퀘스트", 386,0,1,Arrays.asList("§f이전 퀘스트를 수행한 뒤","§f현재 퀘스트를 수행 하도록 합니다.","","§b[이전 퀘스트 : 없음]"),29, inv);
		else
			removeFlagStack("§f§l필수 완료 퀘스트", 386,0,1,Arrays.asList("§f이전 퀘스트를 수행한 뒤","§f현재 퀘스트를 수행 하도록 합니다.","§c[Shift + 우클릭시 삭제됩니다]","","§b[이전 퀘스트 : " +QuestList.getString(QuestName+".Need.PrevQuest")+"]"),29, inv);
		switch(QuestList.getInt(QuestName+".Server.Limit"))
		{
		case 0:
			removeFlagStack("§f§l퀘스트 한정", 397,3,1,Arrays.asList("§f서버에서 단 몇 명만이","§f이 퀘스트를 수행 할 수 있습니다.","§f플레이어가 퀘스트를 받을 때 마다 1씩 깎이며,","§f-1이 될 경우 퀘스트를 받을 수 없습니다.","§3(0명으로 설정할 경우, 제한이 사라집니다.)","","§b[수행 가능 플레이어 수 : 제한 없음]"), 33, inv);
			break;
		case -1:
			removeFlagStack("§f§l퀘스트 한정", 397,3,1,Arrays.asList("§f서버에서 단 몇 명만이","§f이 퀘스트를 수행 할 수 있습니다.","§f플레이어가 퀘스트를 받을 때 마다 1씩 깎이며,","§f-1이 될 경우 퀘스트를 받을 수 없습니다.","§3(0명으로 설정할 경우, 제한이 사라집니다.)","","§c[더이상 받을 수 없음]"), 33, inv);
			break;
		default:
			removeFlagStack("§f§l퀘스트 한정", 397,3,1,Arrays.asList("§f서버에서 단 몇 명만이","§f이 퀘스트를 수행 할 수 있습니다.","§f플레이어가 퀘스트를 받을 때 마다 1씩 깎이며,","§f-1이 될 경우 퀘스트를 받을 수 없습니다.","§3(0명으로 설정할 경우, 제한이 사라집니다.)","","§b[수행 가능 플레이어 수 : "+QuestList.getInt(QuestName+".Server.Limit")+"]"), 33, inv);
			break;
		}
	
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 36, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ ChatColor.stripColor(QuestName)), 44, inv);
		player.openInventory(inv);
	}
	
	public void GetterItemSetingGUI(Player player, String QuestName)
	{
		String UniqueCode = "§1§0§5§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0모아야 할 아이템 등록");
		for(int count = 0;count<8;count++)
			removeFlagStack("§f[아이템을 올려 주세요.]", 389,0,0,null, count, inv);
		
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ ChatColor.stripColor(QuestName)), 8, inv);
		player.openInventory(inv);
	}

	public void PresentItemSettingGUI(Player player, String QuestName)
	{
		YamlLoader QuestConfig = new YamlLoader();
		QuestConfig.getConfig("Quest/QuestList.yml");

		UserDataObject u = new UserDataObject();

		String UniqueCode = "§1§0§5§0§7§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0보상 아이템 등록");
		if(u.getInt(player, (byte)1) == -1)
			u.setInt(player, (byte)1, 0);
		if(u.getInt(player, (byte)2) == -1)
			u.setInt(player, (byte)2, 0);
		if(u.getInt(player, (byte)3) == -1)
			u.setInt(player, (byte)3, 0);
			
		removeFlagStack("§f[보상금 설정하기]", 266,0,1,Arrays.asList("§f§l"+u.getInt(player, (byte)1)+" "+main.MainServerOption.money), 0, inv);
		removeFlagStack("§f[경험치 설정하기]", 384,0,1,Arrays.asList("§f§l"+u.getInt(player, (byte)2)+"§b§l EXP"), 1, inv);
		removeFlagStack("§f[NPC 호감도 설정하기]", 38,0,1,Arrays.asList("§f§l"+u.getInt(player, (byte)3)+"§d§l Love"), 2, inv);
		int ifItemExit = 0;
		for(int count = 3;count<8;count++)
		{
			if(QuestConfig.getItemStack(QuestName + ".FlowChart."+ u.getInt(player, (byte)5) +".Item."+ifItemExit) != null)
			{
				stackItem(QuestConfig.getItemStack(QuestName + ".FlowChart."+ u.getInt(player, (byte)5) +".Item."+ifItemExit), count, inv);
				ifItemExit++;
			}
			else
				removeFlagStack("§f[아이템을 올려 주세요.]", 389,0,0,null, count, inv);
		}
		
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ ChatColor.stripColor(QuestName)), 8, inv);
		u.setString(player, (byte)4,null);
		player.openInventory(inv);
	}
	
	public void ShowItemGUI(Player player, String QuestName, short Flow, boolean isOP, boolean type)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§8§r";
		Inventory inv = null;
		
		if(QuestList.contains(QuestName+".FlowChart."+Flow+".Item") == true)
		{
			Object[] a =QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Item").getKeys(false).toArray();
			if(type == false)
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "§0모아야 할 아이템 목록");
				for(int count = 0;count<a.length;count++)
					stackItem(QuestList.getItemStack(QuestName+".FlowChart."+Flow+".Item." + a[count]),count+10,inv);
			}
			else
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "§0보상 목록");
				removeFlagStack("§6[보상금]", 266,0,1,Arrays.asList("","§f§l" + QuestList.getInt(QuestName+".FlowChart."+Flow+".Money") +"§6 "+main.MainServerOption.money), 3, inv);
				removeFlagStack("§b[경험치]", 384,0,1,Arrays.asList("","§f§l" + QuestList.getInt(QuestName+".FlowChart."+Flow+".EXP") +"§b EXP"), 4, inv);
				removeFlagStack("§d[호감도]", 38,0,1,Arrays.asList("","§f§l" + QuestList.getInt(QuestName+".FlowChart."+Flow+".Love") +"§d Love"), 5, inv);

				for(int count = 0;count<a.length;count++)
					stackItem(QuestList.getItemStack(QuestName+".FlowChart."+Flow+".Item." + a[count]),count+11,inv);
				if(player.isOp())
				{
					UserDataObject u = new UserDataObject();
					if(u.getInt(player, (byte)1)!=-9)
					{
						u.clearAll(player);
						removeFlagStack("§f§l보상 받기", 54,0,1,Arrays.asList("§7보상을 수령합니다." ,"§0"+ Flow), 22, inv);
					}
				}
				else
					removeFlagStack("§f§l보상 받기", 54,0,1,Arrays.asList("§7보상을 수령합니다." ,"§0"+ Flow), 22, inv);
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
				removeFlagStack("§6[보상금]", 266,0,1,Arrays.asList("","§f§l" + QuestList.getInt(QuestName+".FlowChart."+Flow+".Money") +"§6 "+main.MainServerOption.money), 3, inv);
				removeFlagStack("§b[경험치]", 384,0,1,Arrays.asList("","§f§l" + QuestList.getInt(QuestName+".FlowChart."+Flow+".EXP") +"§b EXP"), 4, inv);
				removeFlagStack("§d[호감도]", 38,0,1,Arrays.asList("","§f§l" + QuestList.getInt(QuestName+".FlowChart."+Flow+".Love") +"§d Love"), 5, inv);
				if(player.isOp())
				{
					UserDataObject u = new UserDataObject();
					if(u.getInt(player, (byte)1)!=-9)
					{
						u.clearAll(player);
						removeFlagStack("§f§l보상 받기", 54,0,1,Arrays.asList("§7보상을 수령합니다." ,"§0"+ Flow), 22, inv);
					}
				}
				else
					removeFlagStack("§f§l보상 받기", 54,0,1,Arrays.asList("§7보상을 수령합니다." ,"§0"+ Flow), 22, inv);
			}
		}
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+ isOP), 18, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void KillMonsterGUI(Player player, String QuestName, short Flow, boolean isOP)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");
		YamlLoader PlayerQuestList = new YamlLoader();
		PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		String UniqueCode = "§0§0§5§0§8§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0사냥 해야 할 몬스터 목록");
		
		Object[] c = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
		for(int counter = 0; counter < c.length; counter++)
		{
			String MobName = QuestList.getString(QuestName+".FlowChart."+Flow+".Monster."+counter+".MonsterName");
			int Amount = QuestList.getInt(QuestName+".FlowChart."+Flow+".Monster."+counter+".Amount");
			int PlayerKillAmount = PlayerQuestList.getInt("Started."+QuestName+".Hunt."+counter);
			
	        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1);
	        SkullMeta meta = (SkullMeta) skull.getItemMeta();
	        meta.setOwner(MobName);
	        meta.setDisplayName("§6"+ SkullType(MobName));
	        meta.setLore(Arrays.asList("§f[" +PlayerKillAmount+"/"+ Amount + "]"));
	        skull.setItemMeta(meta);
	        stackItem(skull, counter, inv);
			//Stack2("§6"+ MobName, 266,0,1,Arrays.asList("§f[" +PlayerKillAmount+"/"+ Amount + "]"), counter, inv);
		}
		
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+ isOP), 18, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void HarvestGUI(Player player, String QuestName, short Flow, boolean isOP)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");
		YamlLoader PlayerQuestList = new YamlLoader();
		PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		String UniqueCode = "§0§0§5§0§8§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0채집 해야 할 블록 목록");
		
		Object[] c = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Block").getKeys(false).toArray();
		for(int counter = 0; counter < c.length; counter++)
		{
			int BlockID = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockID");
			byte BlockData = (byte) QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockData");
			int Amount = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".Amount");
			boolean DataEquals = QuestList.getBoolean(QuestName+".FlowChart."+Flow+".Block."+counter+".DataEquals");
			int PlayerHarvestAmount = PlayerQuestList.getInt("Started."+QuestName+".Block."+counter);
			event.EventInteract IT = new event.EventInteract();
			
			if(DataEquals == true)
				stack("§e"+IT.setItemDefaultName((short) BlockID,(byte)BlockData), BlockID, BlockData, 1, Arrays.asList("§f[" +PlayerHarvestAmount+"/"+ Amount + "]","","§7아이템 ID : " +BlockID,"§7아이템 Data : " +BlockData), counter, inv);
			else
				stack("§e아무런 "+IT.setItemDefaultName((short) BlockID,(byte)BlockData)+"§e 종류", BlockID, 0, 1, Arrays.asList("§f[" +PlayerHarvestAmount+"/"+ Amount + "]","","§7아이템 ID : " +BlockID), counter, inv);
		}
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+ isOP), 18, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void KeepGoing(Player player, String QuestName, short Flow, short Mob, boolean Harvest)
	{
		String UniqueCode = "§0§0§5§0§9§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0계속 등록 하시겠습니까?");

		if(Harvest == false)
		{
			removeFlagStack("§a§l계속 등록하기", 386,0,1,Arrays.asList("§7사냥 대상을 추가로 등록합니다.","§0"+Flow,"§0"+Mob), 10, inv);
			removeFlagStack("§c§l등록 중단하기", 166,0,1,Arrays.asList("§7사냥 대상 추가를 종료합니다.","§0"+ ChatColor.stripColor(QuestName)), 16, inv);
		}
		else
		{
			removeFlagStack("§a§l계속 등록하기", 386,0,1,Arrays.asList("§7채집 대상을 추가로 등록합니다.","§0"+Flow,"§0"+Mob), 10, inv);
			removeFlagStack("§c§l등록 중단하기", 166,0,1,Arrays.asList("§7채집 대상 추가를 종료합니다.","§0"+ ChatColor.stripColor(QuestName)), 16, inv);
		}
		player.openInventory(inv);
	}
	
	public void Quest_NavigationListGUI(Player player, short page, String QuestName)
	{
		YamlLoader NavigationConfig = new YamlLoader();
		NavigationConfig.getConfig("Navigation/NavigationList.yml");

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
			
			
			String TimeS = "§3<도착할 때 까지 유지>";
			String PermitionS = "§3<OP만 사용 가능>";
			String sensitiveS = "§9<반경 "+sensitive+"블록 이내를 도착지로 판정>";
			String ShowArrowS = "§3<기본 화살표 모양>";
			if(Permition == false)
				PermitionS = "§3<모두 사용 가능>";
			if(Time >= 0)
				TimeS = "§3<"+Time+"초 동안 유지>";
			switch(ShowArrow)
			{
			default:
				ShowArrowS = "§3<기본 화살표 모양>";
				break;
			}
			removeFlagStack("§0§l" + Navi[count].toString(), 395,0,1,Arrays.asList(
			"§e§l"+NaviName,"",
			"§9[도착 지점]","§9월드 : §f"+world,
			"§9좌표 : §f"+x+","+y+","+z,sensitiveS,"",
			"§3[기타 옵션]",TimeS,PermitionS,ShowArrowS,""
			,"§e[좌 클릭시 네비 선택]"), loc, inv);
			loc++;
		}
		
		if(Navi.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+QuestName), 53, inv);
		player.openInventory(inv);
	}
	
	public void Quest_OPChoice(Player player,String QuestName, short Flow,short page)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§b§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0퀘스트 선택 확인");
		
		String[] script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.0.Lore")+"%enter%%enter%§9§l변수값 변경 : §f"+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.0.Var")).split("%enter%");
		
		switch(QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size())
		{
		case 1:
			removeFlagStack("§f§l[선택]", 72,0,1,Arrays.asList(script), 13, inv);
			break;
		case 2:
			removeFlagStack("§f§l[선택]", 72,0,1,Arrays.asList(script), 12, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%§9§l변수값 변경 : §f"+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,2,Arrays.asList(script), 14, inv);
			break;
		case 3:
			removeFlagStack("§f§l[선택]", 72,0,1,Arrays.asList(script), 11, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%§9§l변수값 변경 : §f"+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,2,Arrays.asList(script), 13, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore")+"%enter%%enter%§9§l변수값 변경 : §f"+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var")).split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,3,Arrays.asList(script), 15, inv);
			break;
		case 4:
			removeFlagStack("§f§l[선택]", 72,0,1,Arrays.asList(script), 10, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%§9§l변수값 변경 : §f"+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,2,Arrays.asList(script), 12, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore")+"%enter%%enter%§9§l변수값 변경 : §f"+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var")).split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,3,Arrays.asList(script), 14, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.3.Lore")+"%enter%%enter%§9§l변수값 변경 : §f"+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.3.Var")).split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,4,Arrays.asList(script), 16, inv);
			break;
		}
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+page), 18, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+QuestName), 26, inv);
		player.openInventory(inv);
	}
	
	public void Quest_UserChoice(Player player,String QuestName, short Flow)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "§0§0§5§0§c§r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "§0퀘스트 선택");

		String lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.0.Lore").replace("%player%", player.getName());
		String[] script = lore.split("%enter%");
		
		switch(QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size())
		{
		case 1:
			removeFlagStack("§f§l[선택]", 72,0,1,Arrays.asList(script), 13, inv);
			break;
		case 2:
			removeFlagStack("§f§l[선택]", 72,0,1,Arrays.asList(script), 12, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,2,Arrays.asList(script), 14, inv);
			break;
		case 3:
			removeFlagStack("§f§l[선택]", 72,0,1,Arrays.asList(script), 11, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,2,Arrays.asList(script), 13, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,3,Arrays.asList(script), 15, inv);
			break;
		case 4:
			removeFlagStack("§f§l[선택]", 72,0,1,Arrays.asList(script), 10, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,2,Arrays.asList(script), 12, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,3,Arrays.asList(script), 14, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.3.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			removeFlagStack("§f§l[선택]", 72,0,4,Arrays.asList(script), 16, inv);
			break;
		}
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+QuestName,"§0"+Flow), 26, inv);
		player.openInventory(inv);
	}
	
	
	public void QuestRouter(Player player,String QuestName)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");
		YamlLoader PlayerQuestList = new YamlLoader();
		PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
		
		String QuestType = QuestList.getString(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Type");
		short FlowChart = (short) PlayerQuestList.getInt("Started."+QuestName+".Flow");
		String NPCname = QuestList.getString(QuestName+".FlowChart."+FlowChart+".NPCname");
		if(QuestType == null)
		{
			util.ETC ETC = new util.ETC();
			YamlLoader Config = new YamlLoader();
			Config.getConfig("config.yml");
			String message = Config.getString("Quest.ClearMessage").replace("%QuestName%", QuestName);
			player.sendMessage(message);
			PlayerQuestList.set("Ended."+QuestName+".ClearTime", ETC.getNowUTC());
			PlayerQuestList.removeKey("Started."+QuestName+".Flow");
			PlayerQuestList.removeKey("Started."+QuestName+".Type");
			PlayerQuestList.removeKey("Started."+QuestName);
			PlayerQuestList.saveConfig();
			YamlLoader PlayerVarList = new YamlLoader();
			PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
			PlayerVarList.removeKey(QuestName);
			PlayerVarList.saveConfig();
			player.closeInventory();
			SoundEffect.playSound(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.8F);
			
		}
		else
		{
			PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart." + FlowChart+".Type") );
			PlayerQuestList.saveConfig();
			switch(QuestType)
			{
				case "Cal":
				{
					YamlLoader PlayerVarList = new YamlLoader();
					PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
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
					YamlLoader PlayerVarList = new YamlLoader();
					PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
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
							if(QuestList.getString(QuestName+".FlowChart."+count+".Type").equals("IF"))
								MeetTheIF = MeetTheIF+1;
							else if(QuestList.getString(QuestName+".FlowChart."+count+".Type").equals("ENDIF"))
								if(MeetTheIF!=0)
									MeetTheIF = MeetTheIF-1;
							if(MeetTheIF==0&&(QuestList.getString(QuestName+".FlowChart."+count+".Type").equals("ENDIF")))
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
							if(MeetTheIF==0&&(QuestList.getString(QuestName+".FlowChart."+count+".Type").equals("ELSE")))
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
						util.ETC ETC = new util.ETC();
						YamlLoader Config = new YamlLoader();
						Config.getConfig("config.yml");
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
						SoundEffect.playSound(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.8F);
					}
				}
				break;
				case "ELSE":
				{
					YamlLoader PlayerVarList = new YamlLoader();
					PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					if(PlayerVarList.getInt(QuestName+".MeetELSE")==0)
					{
						QuestList.getString(QuestName+".FlowChart."+PlayerQuestList.getInt("Started."+QuestName+".Flow")+".Type");
						short MeetTheIF = 0;
						for(int count = FlowChart+1; count < QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size(); count++)
						{
							if(QuestList.getString(QuestName+".FlowChart."+count+".Type").equals("IF"))
								MeetTheIF++;
							else if(MeetTheIF!=0&&QuestList.getString(QuestName+".FlowChart."+count+".Type").equals("ENDIF"))
								if(MeetTheIF!=0)
									MeetTheIF--;
							if(MeetTheIF==0&&(QuestList.getString(QuestName+".FlowChart."+count+".Type").equals("ENDIF")))
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
						util.ETC ETC = new util.ETC();
						YamlLoader Config = new YamlLoader();
						Config.getConfig("config.yml");
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
						SoundEffect.playSound(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.8F);
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
					YamlLoader PlayerVarList = new YamlLoader();
					PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
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
					player.sendMessage("§c[퀘스트] : 퀘스트를 완수하지 못하였습니다!");
					PlayerQuestList.set("Ended."+QuestName+".ClearTime", new util.ETC().getNowUTC());
					PlayerQuestList.removeKey("Started."+QuestName);
					PlayerQuestList.saveConfig();
					YamlLoader PlayerVarList = new YamlLoader();
					PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					PlayerVarList.removeKey(QuestName);
					PlayerVarList.saveConfig();
					player.closeInventory();
					SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
					SoundEffect.playSound(player, Sound.ENTITY_WITHER_DEATH, 0.7F, 0.8F);
				}
				break;
				case "QuestReset":
				{
					player.sendMessage("§e[퀘스트] : 퀘스트를 포기하였습니다!");
					PlayerQuestList.removeKey("Started."+QuestName);
					PlayerQuestList.saveConfig();
					YamlLoader PlayerVarList = new YamlLoader();
					PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					PlayerVarList.removeKey(QuestName);
					PlayerVarList.saveConfig();
					player.closeInventory();
					SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 1.2F, 0.8F);
				}
			break;
			case "VarChange":
			{
				YamlLoader PlayerVarList = new YamlLoader();
				PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
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
				YamlLoader NavigationConfig = new YamlLoader();
				NavigationConfig.getConfig("Navigation/NavigationList.yml");
				if(NavigationConfig.contains(UTC))
				{
					ServerTickMain.NaviUsingList.add(player.getName());
					player.closeInventory();
					SoundEffect.playSound(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.0F);
					
					servertick.ServerTickObject STSO = new servertick.ServerTickObject(Long.parseLong(UTC), "NV");
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
					
					servertick.ServerTickMain.Schedule.put(Long.parseLong(UTC), STSO);
					player.sendMessage("§e[네비게이션] : 길찾기 시스템이 가동됩니다!");
					player.sendMessage("§e(화살표가 보이지 않을 경우, [ESC] → [설정] → [비디오 설정] 속의 [입자]를 [모두]로 변경해 주세요!)");
					
				}
				else
				{
					SoundEffect.playSound(player, Sound.BLOCK_NOTE_BASS, 1.0F, 1.0F);
					player.sendMessage("§c[네비게이션] : 등록된 네비게이션을 찾을 수 없습니다! 관리자에게 문의하세요!");
				}
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestRouter(player, QuestName);
			}
			break;
			case "Whisper":
			{
				String script3 = "§f"+QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
				script3 = script3.replace("%player%", player.getName());
				YamlLoader PlayerVarList = new YamlLoader();
				PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script3 = script3.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				player.sendMessage(script3);
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				QuestRouter(player, QuestName);
			}
			break;
			case "BroadCast":
			{
				String script3 = "§f"+QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
				script3 = script3.replace("%player%", player.getName());
				YamlLoader PlayerVarList = new YamlLoader();
				PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
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
				YamlLoader PlayerVarList = new YamlLoader();
				PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script = script.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				String[] scriptA = script.split("%enter%");
				for(int count = 0; count < scriptA.length; count++)
					scriptA[count] = "§f"+ scriptA[count];
				QuestScriptTypeGUI(player, QuestName, NPCname, FlowChart, scriptA);
			}
			break;
			case "PScript": 
			{
				String script2 = QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
				script2 = script2.replace("%player%", player.getName());
				YamlLoader PlayerVarList = new YamlLoader();
				PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
				script2 = script2.replace("%value%", ""+PlayerVarList.getInt(QuestName));
				String[] scriptA2 = script2.split("%enter%");
				for(int count = 0; count < scriptA2.length; count++)
					scriptA2[count] = "§f"+ scriptA2[count];
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
				for(int counter = 0; counter < MobList.length; counter++)
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
					PottionBuff.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
					SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
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
				for(int counter = 0; counter < BlockList.length; counter++)
					PlayerQuestList.set("Started."+QuestName+".Block."+counter,0);
				PlayerQuestList.saveConfig();
				HarvestGUI(player, QuestName, FlowChart, false);
				break;
			}
		}
	}
	
	
	
	public void AllOfQuestListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		UserDataObject u = new UserDataObject();
		boolean ChooseQuestGUI = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		if(slot == 53)//닫기
		{
			if(ChooseQuestGUI == true)
				u.clearAll(player);
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
			{
				if(ChooseQuestGUI == true)
				{
					QuestOptionGUI(player, u.getString(player, (byte)1));
					u.clearAll(player);
				}
				else
					new admin.OPboxGui().opBoxGuiMain(player,(byte) 1);
			}
			else if(slot==48)//이전 페이지
				AllOfQuestListGUI(player,(short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-2),ChooseQuestGUI);
			else if(slot==50)//다음 페이지
				AllOfQuestListGUI(player, Short.parseShort(event.getInventory().getTitle().split(" : ")[1]),ChooseQuestGUI);
			else if(slot==49)//새 퀘스트
			{
				player.sendMessage("§6/퀘스트 생성 [타입] [이름]" );
				player.sendMessage("§a[타입 : 일반 / 반복 / 일일 / 일주 / 한달]");
				player.closeInventory();
			}
			else
			{
				if(ChooseQuestGUI == true)
				{
					String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
					YamlLoader YC = new YamlLoader();
					YamlLoader QuestList = new YamlLoader();
					QuestList.getConfig("Quest/QuestList.yml");
					if(QuestName.equalsIgnoreCase(u.getString(player, (byte)1)))
					{
						SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.8F, 1.0F);
						player.sendMessage("§c[퀘스트] : 같은 퀘스트는 등록할 수 없습니다!");
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
						YamlLoader YC = new YamlLoader();
						YamlLoader QuestList = new YamlLoader();
						QuestList.getConfig("Quest/QuestList.yml");
						QuestList.removeKey(QuestName);
						QuestList.saveConfig();
				    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
				    	Player[] a = new Player[playerlist.size()];
				    	playerlist.toArray(a);
		  	  			for(int count = 0; count<a.length;count++)
		  	  			{
		  	  		    	if(a[count].isOnline() == true)
		  	  		    	{
		  						SoundEffect.playSound(a[count], Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
		  						a[count].sendMessage("§d[관리자] : §e"+ player.getName()+"§d님께서 §e"+ QuestName+"§d퀘스트를 삭제하셨습니다!");
		  	  		    	}	
		  	  		    }
		  	  			Bukkit.getConsoleSender().sendMessage("§e"+ player.getName()+"§d님께서 §e"+ QuestName+"§d퀘스트를 삭제하셨습니다!");
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
		
		
		if(slot == 53)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
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
									new UserDataObject().setInt(player, (byte)1,-9);
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
					YamlLoader YC = new YamlLoader();
					YamlLoader QuestList = new YamlLoader();
					QuestList.getConfig("Quest/QuestList.yml");

					Object[] b = QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();

					for(int count = Flow; count <= b.length-1;count++)
						QuestList.set(QuestName+".FlowChart."+count,QuestList.get(QuestName+".FlowChart."+(count+1)));
					QuestList.saveConfig();
					FixQuestGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),QuestName);
				}
			}
		}
	}
	
	public void MyQuestListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new user.StatsGui().StatusGUI(player);
			else if(slot == 48)//이전 페이지
				MyQuestListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//다음 페이지
				MyQuestListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlLoader PlayerQuestList = new YamlLoader();
				PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
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
		
		Player player = (Player) event.getWhoClicked();

		UserDataObject u = new UserDataObject();
		
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		YamlLoader QuestConfig = new YamlLoader();
		QuestConfig.getConfig("Quest/QuestList.yml");
		short size = (short) QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size();

		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "변수":
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"CV");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage("§a[퀘스트] : 플레어의 변수값을 몇 으로 변경할까요?");
				player.sendMessage("§7(최소 -1000 ~ 최대 30000)");
				player.closeInventory();
				return;
			case "선택":
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"CS");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage("§a[퀘스트] : 몇 가지 선택지를 보여 줄 건가요?");
				player.sendMessage("§7(최소 1개 ~ 최대 4개)");
				player.closeInventory();
				return;
			case "네비":
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				Quest_NavigationListGUI(player, (short) 0, QuestName);
				return;
			case "대사":
			case "독백":
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("대사"))
					u.setString(player, (byte)1,"Script");
				else
					u.setString(player, (byte)1,"PScript");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage("§a[SYSTEM] : 등록할 대사를 채팅창에 입력하세요!");
				player.sendMessage("§6%enter%§f - 한줄 띄어 쓰기 -");
				player.sendMessage("§6%player%§f - 플레이어 지칭하기 -");
				player.sendMessage("§6%value%§f - 플레이어의 현재 퀘스트 변수 지칭하기 -");
				player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
				"§3&3 §4&4 §5&5 " +
						"§6&6 §7&7 §8&8 " +
				"§9&9 §a&a §b&b §c&c" +
						"§d&d §e&e §f&f");
				player.closeInventory();
				return;
			case "방문":
				YamlLoader AreaList = new YamlLoader();
				AreaList.getConfig("Area/AreaList.yml");
				Object[] arealist =AreaList.getConfigurationSection("").getKeys(false).toArray();

				if(arealist.length <= 0)
				{
					SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
					player.sendMessage("§c[퀘스트] : 생성된 영역이 없습니다!");
					player.sendMessage("§6/영역 <이름> 생성§e - 새로운 영역을 생성합니다. -");
					player.closeInventory();
					return;
				}
				player.sendMessage("§a┌────────영역 목록────────┐");
				for(int count =0; count <arealist.length;count++)
				{
					player.sendMessage("§a  "+ arealist[count].toString());
				}
				player.sendMessage("§a└────────영역 목록────────┘");
				player.sendMessage("§3[퀘스트] : 방문해야 할 영역 이름을 적어 주세요!");
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Visit");
				u.setString(player, (byte)2,QuestName);
				player.closeInventory();
				return;
			case "전달":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Give");
				u.setString(player, (byte)3,QuestName);
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				player.sendMessage("§a[SYSTEM] : §c수집품을 먼저 준비하신 후,§a 받을 NPC를 우클릭 하세요!");
				player.closeInventory();
				return;
			case "사냥":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Hunt");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"N");
				u.setInt(player, (byte)2, -1);
				u.setInt(player, (byte)3, -1);
				player.sendMessage("§a[SYSTEM] : 사냥 해야 할 몬스터를 우클릭 하세요!");
				player.closeInventory();
				return;
			case "대화":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Talk");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage("§a[SYSTEM] : 대화 해야 할 NPC를 우클릭 하세요!");
				player.closeInventory();
				return;
			case "보상":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Present");
				u.setString(player, (byte)3,QuestName);
				u.setInt(player, (byte)5, -1);
				player.sendMessage("§a[SYSTEM] : §c보상 아이템을 먼저 준비하신 후,§a 보상을 줄 NPC를 우클릭 하세요!");
				player.closeInventory();
				return;
			case "이동":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"TelePort");
				u.setString(player, (byte)3,QuestName);
				player.sendMessage("§a[SYSTEM] : 플레이어를 이동 시킬 위치에 좌클릭 하세요!");
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
				player.sendMessage("§a[SYSTEM] : 채집해야 할 블록을 우클릭 하세요!");
				player.closeInventory();
				return;
			case "블록":
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"BlockPlace");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"null");
				
				u.setInt(player, (byte)1, 0);//블록 ID
				u.setInt(player, (byte)2, 0);//블록 DATA
				player.sendMessage("§a[퀘스트] : 블록이 설치될 지점을 우클릭 하세요!");
				player.closeInventory();
				return;
			case "소리":
				return;
			case "귓말":
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Whisper");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage("§a[퀘스트] : 어떤 메시지를 전달하고 싶으신가요?");
				player.sendMessage("§6%player%§f - 플레이어 지칭하기 -");
				player.sendMessage("§6%value%§f - 플레이어의 현재 퀘스트 변수 지칭하기 -");
				player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
				"§3&3 §4&4 §5&5 " +
						"§6&6 §7&7 §8&8 " +
				"§9&9 §a&a §b&b §c&c" +
						"§d&d §e&e §f&f");
				player.closeInventory();
				return;
			case "전체":
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"BroadCast");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage("§a[퀘스트] : 어떤 메시지를 전달하고 싶으신가요?");
				player.sendMessage("§6%player%§f - 플레이어 지칭하기 -");
				player.sendMessage("§6%value%§f - 플레이어의 현재 퀘스트 변수 지칭하기 -");
				player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
				"§3&3 §4&4 §5&5 " +
						"§6&6 §7&7 §8&8 " +
				"§9&9 §a&a §b&b §c&c" +
						"§d&d §e&e §f&f");
				player.closeInventory();
				return;
			case "이전 목록":
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				FixQuestGUI(player,(short) 0,QuestName);
				return;
			case "닫기":
				SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
		}

		if(size != 0)
		{
			switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
			{
			case "계산":
			{
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Cal");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage("§a[퀘스트] : 어떤 연산을 하시고 싶은가요?");
				player.sendMessage("§6§l1. §fA ＋ B (플레이어 변수에 B를 더합니다.)");
				player.sendMessage("§6§l2. §fA － B (플레이어 변수에 B를 뺍니다.)");
				player.sendMessage("§6§l3. §fA × B (플레이어 변수를 B로 곱합니다.)");
				player.sendMessage("§6§l4. §fA ÷ B (플레이어 변수를 B로 나눈 몫을 구합니다.)");
				player.sendMessage("§6§l5. §fA ％ B (플레이어 변수를 B로 나눈 나머지를 구합니다.)");
				player.sendMessage("§7(최소 1 ~ 최대 5 사이 값 입력)");
				player.closeInventory();
			}
			return;
			case "퀘스트 초기화":
			{
				
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "QuestReset");
		    	QuestConfig.saveConfig();
				FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			case "퀘스트 실패":
				{
					SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
					QuestConfig.set(QuestName+".FlowChart."+size+".Type", "QuestFail");
	    	    	QuestConfig.saveConfig();
	    			FixQuestGUI(player, (short) 0, QuestName);
				}
			break;
			case "IF":
			{
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"IFTS");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage("§a[퀘스트] : 어떤 비교를 하시고 싶은가요?");
				player.sendMessage("§6§l1. §fA == B (플레이어 변수와 B가 같은가?)");
				player.sendMessage("§6§l2. §fA != B (플레이어 변수와 B가 다른가?)");
				player.sendMessage("§6§l3. §fA > B (플레이어 변수가 B보다 큰가?)");
				player.sendMessage("§6§l4. §fA < B (플레이어 변수가 B보다 작은가?)");
				player.sendMessage("§6§l5. §fA >= B (플레이어 변수가 B보다 크거나 같은가?)");
				player.sendMessage("§6§l6. §fA <= B (플레이어 변수가 B보다 작거나 같은가?)");
				player.sendMessage("§6§l7. §fC <= A <= B (플레이어 변수가 최소 C ~ 최대 B 사이인가?)");
				player.sendMessage("§7(최소 1 ~ 최대 7 사이 값 입력)");
				player.closeInventory();
			}
			break;
			case "ELSE":
			{
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "ELSE");
    	    	QuestConfig.saveConfig();
    			FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			case "ENDIF":
			{
				SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "ENDIF");
    	    	QuestConfig.saveConfig();
    			FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			}
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
			player.sendMessage("§c[퀘스트] : 해당 항목은 첫 번째 구성 요소로 올 수 없습니다!");
		}
		return;
	}

	public void QuestScriptTypeGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 13)
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName());
			YamlLoader PlayerQuestList = new YamlLoader();
			PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
			PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
			PlayerQuestList.saveConfig();
			player.closeInventory();
			QuestRouter(player, QuestName);
		}
	}
	
	public void GetterItemSetingGUIClick(InventoryClickEvent event)
	{
		if(!event.getClickedInventory().getTitle().equals("container.inventory"))
			if(event.getSlot() == 8)
				event.getWhoClicked().closeInventory();
	}
	
	public void ShowNeedGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			if(slot == 18)
			{
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(1)).equalsIgnoreCase("false"))
					MyQuestListGUI(player, (short) 0);
				else
					FixQuestGUI(player, (short) 0, ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1)));
			}
			else if(slot == 22)
			{
				YamlLoader PlayerQuestList = new YamlLoader();
				YamlLoader QuestList = new YamlLoader();
				QuestList.getConfig("Quest/QuestList.yml");
				PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
				
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
						for(int counter = 0;counter < p.length; counter++)
							player.getInventory().addItem(item[counter]);
					}
					else
					{
						SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
						player.sendMessage("§e[퀘스트] : 현재 플레이어의 인벤토리 공간이 충분하지 않아 보상을 받을 수 없습니다!");
						return;
					}
				}
				main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".Money"), 0, false);

				YamlLoader playerDataYaml = new YamlLoader();
		    	if(playerDataYaml.isExit("NPC/PlayerData/"+player.getUniqueId()+".yml")==false)
		    	{
		    		playerDataYaml.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    		playerDataYaml.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love", QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Love"));
		    		playerDataYaml.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career", 0);
		    		playerDataYaml.saveConfig();
		    	}
		    	else
		    	{
		    		playerDataYaml.getConfig("NPC/PlayerData/"+player.getUniqueId()+".yml");
		    		int ownlove = playerDataYaml.getInt(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love");
		    		int owncareer = playerDataYaml.getInt(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career");
		    		playerDataYaml.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".love", ownlove +QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Love"));
		    		playerDataYaml.set(QuestList.getString(QuestName + ".FlowChart."+QuestFlow+".TargetNPCuuid")+".Career", owncareer +QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".Career"));
		    		playerDataYaml.saveConfig();
		    	}
	    		if(QuestList.getInt(QuestName + ".FlowChart."+QuestFlow+".EXP") != 0)
	    			new util.UtilPlayer().addMoneyAndEXP(player, 0, QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".EXP"), null, false, false);
				
				PlayerQuestList.set("Started."+QuestName+".Flow", PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
				PlayerQuestList.saveConfig();
				player.closeInventory();
				QuestRouter(player, QuestName);
			}
		}
	}
	
	public void PresentItemSettingGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		if(slot <= 2 || slot == 8)
		{
			if(!event.getClickedInventory().getTitle().equals("container.inventory"))
			{
				event.setCancelled(true);
				if(slot == 8)
				{
					SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
					player.closeInventory();
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
					UserDataObject u = new UserDataObject();
					u.setType(player, "Quest");
					if(slot == 0)
					{
						player.sendMessage("§a[SYSTEM] : 지급할 포상금을 입력 해 주세요. (§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)4,"M");
					}
					else if(slot == 1)
					{
						u.setInt(player, (byte)2, 0);
						player.sendMessage("§a[SYSTEM] : 상승시킬 경험치를 입력 해 주세요. (§e1§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)4,"E");
					}
					else if(slot == 2)
					{
						u.setInt(player, (byte)3, 0);
						player.sendMessage("§a[SYSTEM] : 상승시킬 NPC의 호감도를 입력 해 주세요. (§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
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
		
		
		if(slot == 16)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			UserDataObject u = new UserDataObject();
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
				player.sendMessage("§a[SYSTEM] : 사냥 해야 할 몬스터를 우클릭 하세요!");
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
				player.sendMessage("§a[SYSTEM] : 채집 해야 할 블록을 우클릭 하세요!");
			}
			player.closeInventory();
		}
	}
	
	public void QuestOptionGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		
		if(slot == 44)
		{

			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			YamlLoader QuestList = new YamlLoader();
			QuestList.getConfig("Quest/QuestList.yml");
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(44).getItemMeta().getLore().get(1));
			if(slot == 36)
				AllOfQuestListGUI(player, (short) 0,false);
			else if(slot == 15)//스킬 랭크 제한
			{
				SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
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
					UserDataObject u = new UserDataObject();
					u.setString(player, (byte)1,QuestName);
					AllOfQuestListGUI(player, (short) 0, true);
				}
				else if(event.isRightClick() == true && event.isShiftClick() == true)
				{
					SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
					QuestList.set(QuestName+".Need.PrevQuest", "null");
					QuestList.saveConfig();
					QuestOptionGUI(player, QuestName);
				}
			}
			else
			{
				UserDataObject u = new UserDataObject();
				u.setType(player, "Quest");
				u.setString(player, (byte)2,QuestName);
				player.closeInventory();
				if(slot == 11)//레벨 제한
				{
					u.setString(player, (byte)1,"Level District");
					player.sendMessage("§a[SYSTEM] : 몇 레벨 부터 수행 가능하게 하시겠습니까? (§e0§a ~ §e"+Integer.MAX_VALUE+"§a레벨)");
				}
				else if(slot == 13)//호감도 제한
				{
					u.setString(player, (byte)1,"Love District");
					player.sendMessage("§a[SYSTEM] : 호감도 몇 이상부터 수행 가능하게 하시겠습니까? (§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
				}
				else if(slot == 20)//STR 제한
				{
					u.setString(player, (byte)1,"STR District");
					player.sendMessage("§a[SYSTEM] : "+main.MainServerOption.statSTR+"이 몇 이상 되어야 수행 가능하게 하시겠습니까? (§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
				}
				else if(slot == 21)//DEX 제한
				{
					u.setString(player, (byte)1,"DEX District");
					player.sendMessage("§a[SYSTEM] : "+main.MainServerOption.statDEX+"가 몇 이상 되어야 수행 가능하게 하시겠습니까? (§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
				}
				else if(slot == 22)//INT 제한
				{
					u.setString(player, (byte)1,"INT District");
					player.sendMessage("§a[SYSTEM] : "+main.MainServerOption.statINT+"이 몇 이상 되어야 수행 가능하게 하시겠습니까? (§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
				}
				else if(slot == 23)//WILL 제한
				{
					u.setString(player, (byte)1,"WILL District");
					player.sendMessage("§a[SYSTEM] : "+main.MainServerOption.statWILL+"가 몇 이상 되어야 수행 가능하게 하시겠습니까? (§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
				}
				else if(slot == 24)//LUK 제한
				{
					u.setString(player, (byte)1,"LUK District");
					player.sendMessage("§a[SYSTEM] : "+main.MainServerOption.statLUK+"이 몇 이상 되어야 수행 가능하게 하시겠습니까? (§e0§a ~ §e"+Integer.MAX_VALUE+"§a)");
				}
				else if(slot == 33)//퀘스트 한정
				{
					u.setString(player, (byte)1,"Accept District");
					player.sendMessage("§a[SYSTEM] : 몇 명만 수행 가능하게 하시겠습니까? (§e0§a ~ §e"+Integer.MAX_VALUE+"§a명)");
				}
			}
		}
	}
	
	public void Quest_NavigationListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		switch (event.getSlot())
		{
		case 45://이전 목록
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			SelectObjectPage(player, (byte) 0, QuestName);
			return;
		case 53://나가기
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			Quest_NavigationListGUI(player, (short) (page-1), QuestName);
			return;
		case 50://다음 페이지
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			Quest_NavigationListGUI(player, (short) (page+1), QuestName);
			return;
		default :
			if(event.isLeftClick() == true)
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				YamlLoader QuestConfig = new YamlLoader();
				QuestConfig.getConfig("Quest/QuestList.yml");
	    		int size = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size();
	    		QuestConfig.set(QuestName+".FlowChart."+size+".Type", "Nevigation");
		    	QuestConfig.set(QuestName+".FlowChart."+size+".NeviUTC",ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
		    	QuestConfig.saveConfig();
				player.sendMessage("§a[퀘스트] : 네비게이션이 성공적으로 등록되었습니다!");
				FixQuestGUI(player, (short) 0, QuestName);
			}
			return;
		}
	}
	
	public void Quest_OPChoiceClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		short page = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(18).getItemMeta().getLore().get(1)));
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
		switch (event.getSlot())
		{
		case 18://이전 목록
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			FixQuestGUI(player, page, QuestName);
			return;
		case 26://나가기
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void Quest_UserChoiceClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		if(event.getSlot() == 26)
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		else
		{
			YamlLoader QuestList = new YamlLoader();
			QuestList.getConfig("Quest/QuestList.yml");
			YamlLoader PlayerQuestList = new YamlLoader();
			PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
			YamlLoader PlayerVarList = new YamlLoader();
			PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
			
			short Flow = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(2)));
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
			int ChoiceLevel = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size();
			byte Slot = (byte) event.getSlot();
			
			if(event.getCurrentItem()!= null)
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			
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
		UserDataObject u = new UserDataObject();
		u.setBoolean(player, (byte)1, false);
		
		YamlLoader QuestConfig = new YamlLoader();
		QuestConfig.getConfig("Quest/QuestList.yml");
		
		String QuestName = u.getString(player, (byte)3);
		Object[] b = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
		QuestConfig.set(QuestName+".FlowChart."+b.length+".Type", "Give");
		QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCname", u.getString(player, (byte)2));
		QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCuuid", u.getString(player, (byte)1));
		byte itemacount = 0;
		for(int count = 0; count <8; count++)
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
		SoundEffect.playSound((Player) event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
    	event.getPlayer().sendMessage("§a[SYSTEM] : 성공적으로 등록되었습니다!");
		u.clearAll(player);
		return;
	}

	public void PresentItemSettingGUIClose(InventoryCloseEvent event)
	{
		Player player = (Player)event.getPlayer();
		YamlLoader QuestConfig = new YamlLoader();
		QuestConfig.getConfig("Quest/QuestList.yml");
		UserDataObject u = new UserDataObject();
		String QuestName = u.getString(player, (byte)3);
		
		if(u.getInt(player, (byte)5) == -1)
		{
			Object[] b = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray();
			u.setInt(player, (byte)5, b.length);
			QuestConfig.set(QuestName+".FlowChart."+b.length+".Type", "Present");
			QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCname", u.getString(player, (byte)2));
			QuestConfig.set(QuestName+".FlowChart."+b.length+".TargetNPCuuid", u.getString(player, (byte)1));
			byte itemacount = 0;
			for(int count = 3; count <8; count++)
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
			for(int count = 3; count <8; count++)
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
			SoundEffect.playSound((Player) event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
	    	event.getPlayer().sendMessage("§a[SYSTEM] : 성공적으로 설정되었습니다!");
	    	u.clearAll(player);
		}
		return;
	}
	
	public String SkullType(String s)
	{
		String re = s;
		switch(s)
		{
			case "Zombie" : re = "§2좀비"; break;
			case "Skeleton" : re = "§7스켈레톤"; break;
			case "Giant" : re = "§2자이언트"; break;
			case "Spider" : re = "§7거미"; break;
			case "Witch" : re = "§5마녀"; break;
			case "Creeper" : re = "§a크리퍼"; break;
			case "Slime" : re = "§a슬라임"; break;
			case "Ghast" : re = "§7가스트"; break;
			case "Enderman" : re = "§5엔더맨"; break;
			case "Zombie Pigman" : re = "§d좀비 피그맨"; break;
			case "Cave Spider" : re = "§7동굴 거미"; break;
			case "Silverfish" : re = "§7좀벌레"; break;
			case "Blaze" : re = "§e블레이즈"; break;
			case "Magma Cube" : re = "§e마그마 큐브"; break;
			case "Bat" : re = "§7박쥐"; break;
			case "Endermite" : re = "§5엔더 진드기"; break;
			case "Guardian" : re = "§3가디언"; break;
			case "Pig" : re = "§d돼지"; break;
			case "Sheep" : re = "§f양"; break;
			case "Cow" : re = "§7소"; break;
			case "Chicken" : re = "§f닭"; break;
			case "Squid" : re = "§7오징어"; break;
			case "Wolf" : re = "§f늑대"; break;
			case "Mooshroom" : re = "§c버섯 소"; break;
			case "Ocelot" : re = "§e오셀롯"; break;
			case "Horse" : re = "§6말"; break;
			case "Rabbit" : re = "§f토끼"; break;
			case "Villager" : re = "§6주민"; break;
			case "Snow Golem" : re = "§f눈 사람"; break;
			case "Iron Golem" : re = "§f철 골렘"; break;
			case "Wither" : re = "§7§l위더"; break;
			case "unknown" : re = "§5§l엔더 드래곤"; break;
			default :
				re = s; break;
		}
		return re;
	}
}
