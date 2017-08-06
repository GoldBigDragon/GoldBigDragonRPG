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
import servertick.ServerTick_Main;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;



public class Quest_GUI extends Util_GUI
{
	public void MyQuestListGUI(Player player, short page)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");
		YamlLoader PlayerQuestList = new YamlLoader();
		PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		String UniqueCode = "��0��0��5��0��0��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0����Ʈ ��� : " + (page+1));

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
					QuestType = "[�Ϲ�]";
					ItemID = 340;
					break;
				case "R" :
					QuestType = "[�ݺ�]";
					ItemID = 386;
					break;
				case "D" :
					QuestType = "[����]";
					ItemID = 403;
					break;
				case "W" :
					QuestType = "[�ְ�]";
					ItemID = 403;
					ItemAmount = 7;
					break;
				case "M" :
					QuestType = "[����]";
					ItemID = 403;
					ItemAmount = 31;
					break;
				}
				if(count > a.length || loc >= 45) break;

				switch(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".Type"))
				{
				case "Nevigation":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"ȭ��ǥ�� ������.",""), loc, inv);
					break;
				case "Choice":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"�ϰ����� ���� ��������.","",ChatColor.YELLOW+"[��Ŭ���� ������ Ȯ��.]"), loc, inv);
					break;
				case "Script" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count].toString() + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count].toString()+".Flow")+".NPCname")+ChatColor.WHITE+"�� ��ȭ�� �� ����."), loc, inv);
					break;
				case "PScript" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+"[��Ŭ���� ���� Ȯ��]"), loc, inv);
					break;
				case "Visit" :
					YamlLoader AreaList = new YamlLoader();
					AreaList.getConfig("Area/AreaList.yml");
					String AreaWorld = AreaList.getString(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".World");
					String AreaName = AreaList.getString(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".Name");
					int AreaX = AreaList.getInt(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".SpawnLocation.X");
					short AreaY = (short) AreaList.getInt(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".SpawnLocation.Y");
					int AreaZ = AreaList.getInt(QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".AreaName")+".SpawnLocation.Z");
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+AreaName+ChatColor.WHITE+" ������ �湮����."
							,ChatColor.YELLOW + "���� : "+ChatColor.WHITE+AreaWorld,ChatColor.YELLOW + "X ��ǥ : "+ChatColor.WHITE+""+AreaX,ChatColor.YELLOW + "Y ��ǥ : "+ChatColor.WHITE+""+AreaY,ChatColor.YELLOW + "Z ��ǥ : "+ChatColor.WHITE+""+AreaZ), loc, inv);
					break;
				case "Talk" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".TargetNPCname")+ChatColor.WHITE+"���� ���� �ɾ� ����."), loc, inv);
					break;
				case "Give" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".TargetNPCname")+ChatColor.WHITE+"�� ��Ź��",ChatColor.WHITE+"��ǰ�� ��������.","",ChatColor.YELLOW+"[��Ŭ���� ���� ǰ�� Ȯ��.]"), loc, inv);
					break;
				case "Hunt":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"��ǥ ����� óġ����.","",ChatColor.YELLOW+"[��Ŭ���� óġ ��� Ȯ��]"), loc, inv);
					break;
				case "Harvest":
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.WHITE+"������ ä������.","",ChatColor.YELLOW+"[��Ŭ���� ä�� ���� Ȯ��]"), loc, inv);
					break;
				case "Present" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], ItemID,0,ItemAmount,Arrays.asList(ChatColor.YELLOW+QuestList.getString(a[count] + ".FlowChart."+PlayerQuestList.getInt("Started."+a[count]+".Flow")+".TargetNPCname")+ChatColor.WHITE+"����",ChatColor.WHITE+"������ ����.","",ChatColor.YELLOW+"[��Ŭ���� ���� Ȯ��.]"), loc, inv);
					break;
				}
				loc++;
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void AllOfQuestListGUI(Player player, short page,boolean ChoosePrevQuest)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "��0��0��5��0��1��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0��ü ����Ʈ ��� : " + (page+1));

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
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 340,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ϲ� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case "R" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 386,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �ݺ� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case "D" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 403,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case "W" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 403,0,7,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case "M" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 403,0,31,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ѵ� ����Ʈ","",ChatColor.YELLOW+"[��Ŭ���� ���� ������ �մϴ�.]",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				}
			}
			else
			{
				switch(QuestList.getString(a[count].toString() + ".Type"))
				{
				case "N" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 340,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ϲ� ����Ʈ",""), loc, inv);
					break;
				case "R" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 386,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �ݺ� ����Ʈ",""), loc, inv);
					break;
				case "D" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 403,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ",""), loc, inv);
					break;
				case "W" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 403,0,7,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ",""), loc, inv);
					break;
				case "M" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count], 403,0,31,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlowSize+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ѵ� ����Ʈ",""), loc, inv);
					break;
				}
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		if(ChoosePrevQuest == false)
			Stack2("��f��l�� ����Ʈ", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ����Ʈ�� �����մϴ�."), 49, inv);

		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+ChoosePrevQuest), 53, inv);
		player.openInventory(inv);
	}
	
	public void FixQuestGUI(Player player, short page, String QuestName)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "��0��0��5��0��2��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode +"��0����Ʈ �帧�� : " + (page+1));
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
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 2:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 3:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 4:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 5:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 137,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.DARK_AQUA+"[     ��� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� �� "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				}
				break;
			case "IF":
				switch(QuestList.getInt(QuestName+".FlowChart."+count+".Comparison"))
				{
				case 1:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� == "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 2:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� != "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 3:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� > "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 4:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� < "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 5:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� >= "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 6:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+"�÷��̾� ���� <= "+QuestList.getInt(QuestName+".FlowChart."+count+".Value"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				case 7:
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 184,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : IF","",ChatColor.DARK_AQUA+"[     �� ��     ]",ChatColor.DARK_AQUA+""+QuestList.getInt(QuestName+".FlowChart."+count+".Min")+" <= �÷��̾� ���� <= "+QuestList.getInt(QuestName+".FlowChart."+count+".Max"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
					break;
				}
				break;
			case "QuestFail":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 166,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ����Ʈ ����","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "QuestReset":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 395,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ����Ʈ �ʱ�ȭ","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "ELSE":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 167,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ELSE","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "ENDIF":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 191,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ENDIF","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "VarChange":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 143,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���� ����",ChatColor.WHITE+"���� �� : " + QuestList.getInt(QuestName+".FlowChart."+count+".Value") ,"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Choice":
				int button = QuestList.getConfigurationSection(QuestName+".FlowChart."+count+".Choice").getKeys(false).size();
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 72,0,button,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ����",ChatColor.WHITE+"������ ���� : " +button+"��" ,"",ChatColor.YELLOW+"[��Ŭ���� ����â Ȯ��]","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
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
					
					String TimeS = ChatColor.DARK_AQUA+"<������ �� ���� ����>";
					String sensitiveS = ChatColor.BLUE+"<�ݰ� "+sensitive+"���� �̳��� �������� ����>";
					String ShowArrowS = ChatColor.DARK_AQUA+"<�⺻ ȭ��ǥ ���>";
					if(Time >= 0)
						TimeS = ChatColor.DARK_AQUA+"<"+Time+"�� ���� ����>";
					switch(ShowArrow)
					{
					default:
						ShowArrowS = ChatColor.DARK_AQUA+"<�⺻ ȭ��ǥ ���>";
						break;
					}
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 395,0,1,Arrays.asList(
					ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
					ChatColor.BLUE+"[���� ����]",ChatColor.BLUE+"���� : "+ChatColor.WHITE+world,
					ChatColor.BLUE+"��ǥ : " + ChatColor.WHITE+x+","+y+","+z,sensitiveS,"",
					ChatColor.DARK_AQUA+"[��Ÿ �ɼ�]",TimeS,ShowArrowS,""
					,ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				}
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + count, 166,0,1,Arrays.asList(ChatColor.RED+"[�׺���̼��� ã�� �� �����ϴ�!]","",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�.]"),loc,inv);
			}
				break;
			case "Whisper":
			{
				String script3 = ChatColor.WHITE+"Ÿ�� : �Ӹ�%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptA3 = script3.split("%enter%");
				for(int counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = ChatColor.WHITE + scriptA3[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 421,0,1,Arrays.asList(scriptA3), loc, inv);
			}
			break;
			case "BroadCast":
			{
				String script3 = ChatColor.WHITE+"Ÿ�� : ��ü%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptA3 = script3.split("%enter%");
				for(int counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = ChatColor.WHITE + scriptA3[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 138,0,1,Arrays.asList(scriptA3), loc, inv);
			}
			break;
			case "Script":
				String script = ChatColor.WHITE+"Ÿ�� : ���%enter%"+ChatColor.WHITE+"���ϴ� ��ü : "+QuestList.getString(QuestName+".FlowChart."+count+".NPCname")+"%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Script")+"%enter% %enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptA = script.split("%enter%");
				for(int counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] = ChatColor.WHITE + scriptA[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 323,0,1,Arrays.asList(scriptA), loc, inv);
			break;
			case "PScript":
				String script3 = ChatColor.WHITE+"Ÿ�� : ���%enter%"+ChatColor.WHITE+"���ϴ� ��ü : �÷��̾�%enter%%enter%"+QuestList.getString(QuestName+".FlowChart."+count+".Message")+"%enter% %enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptA3 = script3.split("%enter%");
				for(int counter = 0; counter < scriptA3.length; counter++)
					scriptA3[counter] = ChatColor.WHITE + scriptA3[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 323,0,1,Arrays.asList(scriptA3), loc, inv);
			break;
			case "Visit":
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 345,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : �湮",ChatColor.WHITE+"�湮 ���� : "+QuestList.getString(QuestName+".FlowChart."+count+".AreaName"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Give":
				String script2 = ChatColor.WHITE+"Ÿ�� : ����%enter%"+ChatColor.WHITE+"���� ��� : "+ChatColor.YELLOW+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname")+"%enter%"+ChatColor.WHITE+"NPC�� UUID%enter%"+ChatColor.DARK_AQUA + QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid")+"%enter%%enter%"+ChatColor.YELLOW+"[��Ŭ���� ���� ǰ�� Ȯ��]"+"%enter%"+ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]";
				String[] scriptB = script2.split("%enter%");
				for(int counter = 0; counter < scriptB.length; counter++)
					scriptB[counter] = ChatColor.WHITE + scriptB[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 388,0,1,Arrays.asList(scriptB), loc, inv);
				break;
			case "Hunt":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 267,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���","",ChatColor.YELLOW+"[��Ŭ���� óġ ��� Ȯ��]","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Talk":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 397,3,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ��ȭ","",ChatColor.WHITE+"������ �� NPC �̸�","",ChatColor.YELLOW+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname"),"",ChatColor.WHITE+"NPC�� UUID","",ChatColor.DARK_AQUA + QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Present":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 54,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ����",ChatColor.WHITE+"���� ��� : "+ChatColor.YELLOW+QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCname"),ChatColor.WHITE+"NPC�� UUID","",ChatColor.DARK_AQUA + QuestList.getString(QuestName+".FlowChart."+count+".TargetNPCuuid"),"","",ChatColor.YELLOW+"[��Ŭ���� ���� Ȯ��]",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "TelePort":
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 368,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : �̵�","",ChatColor.WHITE+"���� : "+QuestList.getString(QuestName+".FlowChart."+count+".World"),ChatColor.WHITE+"��ǥ : " + (int)QuestList.getDouble(QuestName+".FlowChart."+count+".X")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Y")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Z"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "Harvest":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 56,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ä��","",ChatColor.YELLOW+"[��Ŭ���� ä�� ���� Ȯ��]","",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			case "BlockPlace":
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD +""+count, 152,0,1,Arrays.asList(ChatColor.WHITE+"Ÿ�� : ���� ��ġ",ChatColor.WHITE+"���� : "+QuestList.getString(QuestName+".FlowChart."+count+".World"),ChatColor.WHITE+"��ǥ : " + (int)QuestList.getDouble(QuestName+".FlowChart."+count+".X")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Y")+","+ (int)QuestList.getDouble(QuestName+".FlowChart."+count+".Z"),ChatColor.WHITE+"���� Ÿ�� : " + QuestList.getInt(QuestName+".FlowChart."+count+".ID")+":"+ QuestList.getInt(QuestName+".FlowChart."+count+".DATA"),"",ChatColor.RED + "[Shift + ��Ŭ���� �����˴ϴ�.]"), loc, inv);
				break;
			}
			loc++;
		}
		
		if(flowChartSize-(page*44)>45)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2("��f��l�� ������Ʈ �߰�", 2,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ������Ʈ�� �߰��մϴ�."), 49, inv);
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 53, inv);
		player.openInventory(inv);
	}

	public void SelectObjectPage(Player player, byte page, String QuestName)
	{
		String UniqueCode = "��0��0��5��0��3��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0������Ʈ �߰�");

		Stack2("��f��l���", 323,0,1,Arrays.asList(ChatColor.GRAY + "��ȭâ�� ����, �ۼ���",ChatColor.GRAY+"��ũ��Ʈ�� �������� ���ϴ�.",ChatColor.GRAY+"(ȭ�� : NPC)"), 0, inv);
		Stack2("��f��l����", 323,0,1,Arrays.asList(ChatColor.GRAY + "��ȭâ�� ����, �ۼ���",ChatColor.GRAY+"��ũ��Ʈ�� �������� ���ϴ�.",ChatColor.GRAY+"(ȭ�� : ����)"), 1, inv);
		Stack2("��f��l�湮", 345,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� Ư�� ������",ChatColor.GRAY+"�湮�ϴ� ����Ʈ�� �ݴϴ�."), 2, inv);
		Stack2("��f��l����", 388,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾ Ư�� ��������",ChatColor.GRAY+"NPC���� ����ϴ� ����Ʈ�� �ݴϴ�."), 3, inv);
		Stack2("��f��l���", 267,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� Ư�� ���͸�",ChatColor.GRAY+"����ϴ� ����Ʈ�� �ݴϴ�."), 4, inv);
		Stack2("��f��l��ȭ", 397,3,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� Ư�� NPC����",ChatColor.GRAY+"���� �Ŵ� ����Ʈ�� �ݴϴ�."), 5, inv);
		Stack2("��f��l����", 54,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� ������ �ݴϴ�."), 6, inv);
		Stack2("��f��l�̵�", 368,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾ Ư�� ��ġ��",ChatColor.GRAY+"�̵� ��ŵ�ϴ�."), 7, inv);
		Stack2("��f��lä��", 56,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� Ư�� ������",ChatColor.GRAY+"ä���ϴ� ����Ʈ�� �ݴϴ�."), 8, inv);
		Stack2("��f��l����", 152,0,1,Arrays.asList(ChatColor.GRAY + "Ư�� ��ġ�� ������",ChatColor.GRAY+"������ �����մϴ�."), 9, inv);
		Stack2("��f��l�Ҹ�"+ ChatColor.RED + "[��� �Ұ�]", 84,0,1,Arrays.asList(ChatColor.GRAY + "Ư�� ��ġ�� �Ҹ��� ���� �մϴ�."), 10, inv);
		Stack2("��f��l�Ӹ�", 421,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ä��â�� �޽����� ��Ÿ���ϴ�."), 11, inv);
		Stack2("��f��l��ü", 138,0,1,Arrays.asList(ChatColor.GRAY + "���� ��ü�� �޽����� ��Ÿ���ϴ�."), 12, inv);
		Stack2("��f��l�׺�", 358,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾�� �׺���̼��� �۵� ��ŵ�ϴ�."), 13, inv);
		

		Stack2("��e��l����", 72,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾ ���ϴ� �����",ChatColor.GRAY+"���� �ϵ��� �մϴ�.",ChatColor.GRAY+"������ ��信 ����",ChatColor.GRAY+"�ٸ� �������� ���� �� �ֽ��ϴ�."), 36, inv);
		Stack2("��e��l����", 143,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ������ ������ �����մϴ�."), 37, inv);
		Stack2("��e��l���", 137,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ������ ������",ChatColor.GRAY+"����Ͽ� �����մϴ�."), 38, inv);
		Stack2("��e��lIF", 184,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ���� �������� Ȯ���Ͽ�",ChatColor.GRAY+"���� ���� ������ ���",ChatColor.GRAY+"IF�� ENDIFȤ�� IF�� ELSE",ChatColor.GRAY+"������ ������ �����ϰ� �˴ϴ�.","",ChatColor.RED+"[�ݵ�� IF�� ���� = ENDIF�� ����]"), 39, inv);
		Stack2("��e��lELSE", 167,0,1,Arrays.asList(ChatColor.GRAY + "�÷��̾��� ���� ��������",ChatColor.GRAY+"IF ������ ���� ���� ���",ChatColor.GRAY+"ELSE�� ENDIF ������ ������",ChatColor.GRAY+"�����ϰ� �˴ϴ�.",""), 40, inv);
		Stack2("��e��lENDIF", 191,0,1,Arrays.asList(ChatColor.GRAY + "IF�� �� �κ��� ��Ÿ���ϴ�.","",ChatColor.RED+"[�ݵ�� IF�� ���� = ENDIF�� ����]"), 41, inv);
		
		Stack2("��c��l����Ʈ �ʱ�ȭ", 395,0,1,Arrays.asList(ChatColor.GRAY + "����Ʈ�� �߰��� ���� �մϴ�.",ChatColor.GREEN+"����Ʈ�� �ٽ� ���� �� �ֽ��ϴ�."), 43, inv);
		Stack2("��c��l����Ʈ ����", 166,0,1,Arrays.asList(ChatColor.GRAY + "����Ʈ�� �߰��� ���� �մϴ�.",ChatColor.GRAY+"�Ϲ� ����Ʈ�� ��� �÷��̾��",ChatColor.RED+"����Ʈ�� �ٽ� ���� �� �����ϴ�."), 44, inv);
		
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 53, inv);
		player.openInventory(inv);
	}

	public void QuestScriptTypeGUI(Player player,String QuestName,String NPCname, short FlowChart, String[] script)
	{
		String UniqueCode = "��0��0��5��0��4��r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "��0[����Ʈ]");
		Stack2(ChatColor.BLACK + ChatColor.stripColor(QuestName), 160,8,1,null, 19, inv);
		
		for(int count=0;count < script.length; count++)
			script[count] = script[count].replace("%player%", player.getName());
		if(NPCname.equals(player.getName()))
			ItemStackStack(getPlayerSkull(ChatColor.YELLOW+NPCname, 1, Arrays.asList(script), player.getName()), 13, inv);
		else
			Stack2(ChatColor.YELLOW + NPCname, 386,0,1,Arrays.asList(script), 13, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void QuestOptionGUI(Player player, String QuestName)
	{
		String UniqueCode = "��0��0��5��0��5��r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "��0����Ʈ �ɼ�");

		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");
		
		switch(QuestList.getString(QuestName + ".Type"))
		{
		case "N" :
			Stack2("��f��l����Ʈ Ÿ��", 340,0,1,Arrays.asList(ChatColor.WHITE + "�Ϲ� ����Ʈ"), 4, inv);
			break;
		case "R" :
			Stack2("��f��l����Ʈ Ÿ��", 386,0,1,Arrays.asList(ChatColor.WHITE + "�ݺ� ����Ʈ"), 4, inv);
			break;
		case "D" :
			Stack2("��f��l����Ʈ Ÿ��", 403,0,1,Arrays.asList(ChatColor.WHITE + "���� ����Ʈ"), 4, inv);
			break;
		case "W" :
			Stack2("��f��l����Ʈ Ÿ��", 403,0,7,Arrays.asList(ChatColor.WHITE + "�ְ� ����Ʈ"), 4, inv);
			break;
		case "M" :
			Stack2("��f��l����Ʈ Ÿ��", 403,0,31,Arrays.asList(ChatColor.WHITE + "���� ����Ʈ"), 4, inv);
			break;
		}

		Stack2("��f��l���� ����", 384,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ��� ������ �����մϴ�.",ChatColor.GOLD+"������"+ChatColor.WHITE+" �ý����� ��� "+ChatColor.YELLOW+"��������"+ChatColor.WHITE+" �����̸�,",ChatColor.RED+"�����ý��丮"+ChatColor.WHITE+" �ý����� ��� "+ChatColor.YELLOW+"����"+ChatColor.WHITE+" �����Դϴ�.","",ChatColor.AQUA + "[�ʿ� ���� : " + QuestList.getInt(QuestName+".Need.LV")+"]"), 11, inv);
		Stack2("��f��lNPC ȣ���� ����", 38,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+"NPC���� ȣ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� ȣ���� : " + QuestList.getInt(QuestName+".Need.Love")+"]"), 13, inv);
		Stack2("��f��l��ų ��ũ ����", 403,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+"��ų ��ũ�� �����մϴ�.",""/*,ChatColor.AQUA + "[�ʿ� ��ų : " + QuestList.getString(QuestName+".Need.Skill.Name")+"]",ChatColor.AQUA+"[�ʿ� ��ũ : " + QuestList.getInt(QuestName+".Need.Skill.Rank")+"]"*/), 15, inv);
		Stack2("��f��l"+main.Main_ServerOption.STR+" ����", 267,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+main.Main_ServerOption.STR+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+main.Main_ServerOption.STR+" : " + QuestList.getInt(QuestName+".Need.STR")+"]"), 20, inv);
		Stack2("��f��l"+main.Main_ServerOption.DEX+" ����", 261,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+main.Main_ServerOption.DEX+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+main.Main_ServerOption.DEX+" : " + QuestList.getInt(QuestName+".Need.DEX")+"]"), 21, inv);
		Stack2("��f��l"+main.Main_ServerOption.INT+" ����", 369,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+main.Main_ServerOption.INT+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+main.Main_ServerOption.INT+" : " + QuestList.getInt(QuestName+".Need.INT")+"]"), 22, inv);
		Stack2("��f��l"+main.Main_ServerOption.WILL+" ����", 370,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+main.Main_ServerOption.WILL+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+main.Main_ServerOption.WILL+" : " + QuestList.getInt(QuestName+".Need.WILL")+"]"), 23, inv);
		Stack2("��f��l"+main.Main_ServerOption.LUK+" ����", 322,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���࿡ �ʿ���",ChatColor.WHITE+""+main.Main_ServerOption.LUK+" ������ �����մϴ�.","",ChatColor.AQUA + "[�ʿ� "+main.Main_ServerOption.LUK+" : " + QuestList.getInt(QuestName+".Need.LUK")+"]"), 24, inv);
		if(QuestList.getString(QuestName+".Need.PrevQuest").equalsIgnoreCase("null") == true)
			Stack2("��f��l�ʼ� �Ϸ� ����Ʈ", 386,0,1,Arrays.asList(ChatColor.WHITE+"���� ����Ʈ�� ������ ��",ChatColor.WHITE+"���� ����Ʈ�� ���� �ϵ��� �մϴ�.","",ChatColor.AQUA + "[���� ����Ʈ : ����]"),29, inv);
		else
			Stack2("��f��l�ʼ� �Ϸ� ����Ʈ", 386,0,1,Arrays.asList(ChatColor.WHITE+"���� ����Ʈ�� ������ ��",ChatColor.WHITE+"���� ����Ʈ�� ���� �ϵ��� �մϴ�.",ChatColor.RED+"[Shift + ��Ŭ���� �����˴ϴ�]","",ChatColor.AQUA + "[���� ����Ʈ : " +QuestList.getString(QuestName+".Need.PrevQuest")+"]"),29, inv);
		switch(QuestList.getInt(QuestName+".Server.Limit"))
		{
		case 0:
			Stack2("��f��l����Ʈ ����", 397,3,1,Arrays.asList(ChatColor.WHITE+"�������� �� �� ������",ChatColor.WHITE+"�� ����Ʈ�� ���� �� �� �ֽ��ϴ�.",ChatColor.WHITE+"�÷��̾ ����Ʈ�� ���� �� ���� 1�� ���̸�,",ChatColor.WHITE+"-1�� �� ��� ����Ʈ�� ���� �� �����ϴ�.",ChatColor.DARK_AQUA+"(0������ ������ ���, ������ ������ϴ�.)","",ChatColor.AQUA +"[���� ���� �÷��̾� �� : ���� ����]"), 33, inv);
			break;
		case -1:
			Stack2("��f��l����Ʈ ����", 397,3,1,Arrays.asList(ChatColor.WHITE+"�������� �� �� ������",ChatColor.WHITE+"�� ����Ʈ�� ���� �� �� �ֽ��ϴ�.",ChatColor.WHITE+"�÷��̾ ����Ʈ�� ���� �� ���� 1�� ���̸�,",ChatColor.WHITE+"-1�� �� ��� ����Ʈ�� ���� �� �����ϴ�.",ChatColor.DARK_AQUA+"(0������ ������ ���, ������ ������ϴ�.)","",ChatColor.RED +"[���̻� ���� �� ����]"), 33, inv);
			break;
		default:
			Stack2("��f��l����Ʈ ����", 397,3,1,Arrays.asList(ChatColor.WHITE+"�������� �� �� ������",ChatColor.WHITE+"�� ����Ʈ�� ���� �� �� �ֽ��ϴ�.",ChatColor.WHITE+"�÷��̾ ����Ʈ�� ���� �� ���� 1�� ���̸�,",ChatColor.WHITE+"-1�� �� ��� ����Ʈ�� ���� �� �����ϴ�.",ChatColor.DARK_AQUA+"(0������ ������ ���, ������ ������ϴ�.)","",ChatColor.AQUA +"[���� ���� �÷��̾� �� : "+QuestList.getInt(QuestName+".Server.Limit")+"]"), 33, inv);
			break;
		}
	
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 36, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 44, inv);
		player.openInventory(inv);
	}
	
	public void GetterItemSetingGUI(Player player, String QuestName)
	{
		String UniqueCode = "��1��0��5��0��6��r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "��0��ƾ� �� ������ ���");
		for(int count = 0;count<8;count++)
			Stack2(ChatColor.WHITE+ "[�������� �÷� �ּ���.]", 389,0,0,null, count, inv);
		
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 8, inv);
		player.openInventory(inv);
	}

	public void PresentItemSettingGUI(Player player, String QuestName)
	{
		YamlLoader QuestConfig = new YamlLoader();
		QuestConfig.getConfig("Quest/QuestList.yml");

		UserData_Object u = new UserData_Object();

		String UniqueCode = "��1��0��5��0��7��r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "��0���� ������ ���");
		if(u.getInt(player, (byte)1) == -1)
			u.setInt(player, (byte)1, 0);
		if(u.getInt(player, (byte)2) == -1)
			u.setInt(player, (byte)2, 0);
		if(u.getInt(player, (byte)3) == -1)
			u.setInt(player, (byte)3, 0);
			
		Stack2(ChatColor.WHITE+ "[����� �����ϱ�]", 266,0,1,Arrays.asList("��f��l"+u.getInt(player, (byte)1)+" "+main.Main_ServerOption.Money), 0, inv);
		Stack2(ChatColor.WHITE+ "[����ġ �����ϱ�]", 384,0,1,Arrays.asList("��f��l"+u.getInt(player, (byte)2)+ChatColor.AQUA+""+ChatColor.BOLD+ " EXP"), 1, inv);
		Stack2(ChatColor.WHITE+ "[NPC ȣ���� �����ϱ�]", 38,0,1,Arrays.asList("��f��l"+u.getInt(player, (byte)3)+ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+ " Love"), 2, inv);
		int ifItemExit = 0;
		for(int count = 3;count<8;count++)
		{
			if(QuestConfig.getItemStack(QuestName + ".FlowChart."+ u.getInt(player, (byte)5) +".Item."+ifItemExit) != null)
			{
				ItemStackStack(QuestConfig.getItemStack(QuestName + ".FlowChart."+ u.getInt(player, (byte)5) +".Item."+ifItemExit), count, inv);
				ifItemExit++;
			}
			else
				Stack2(ChatColor.WHITE+ "[�������� �÷� �ּ���.]", 389,0,0,null, count, inv);
		}
		
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 8, inv);
		u.setString(player, (byte)4,null);
		player.openInventory(inv);
	}
	
	public void ShowItemGUI(Player player, String QuestName, short Flow, boolean isOP, boolean type)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "��0��0��5��0��8��r";
		Inventory inv = null;
		
		if(QuestList.contains(QuestName+".FlowChart."+Flow+".Item") == true)
		{
			Object[] a =QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Item").getKeys(false).toArray();
			if(type == false)
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "��0��ƾ� �� ������ ���");
				for(int count = 0;count<a.length;count++)
					ItemStackStack(QuestList.getItemStack(QuestName+".FlowChart."+Flow+".Item." + a[count]),count+10,inv);
			}
			else
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "��0���� ���");
				Stack2(ChatColor.GOLD+ "[�����]", 266,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Money") +ChatColor.GOLD +" "+main.Main_ServerOption.Money), 3, inv);
				Stack2(ChatColor.AQUA+ "[����ġ]", 384,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".EXP") +ChatColor.AQUA + " EXP"), 4, inv);
				Stack2(ChatColor.LIGHT_PURPLE+ "[ȣ����]", 38,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Love") +ChatColor.LIGHT_PURPLE + " Love"), 5, inv);

				for(int count = 0;count<a.length;count++)
					ItemStackStack(QuestList.getItemStack(QuestName+".FlowChart."+Flow+".Item." + a[count]),count+11,inv);
				if(player.isOp())
				{
					UserData_Object u = new UserData_Object();
					if(u.getInt(player, (byte)1)!=-9)
					{
						u.clearAll(player);
						Stack2("��f��l���� �ޱ�", 54,0,1,Arrays.asList(ChatColor.GRAY + "������ �����մϴ�." ,ChatColor.BLACK +""+ Flow), 22, inv);
					}
				}
				else
					Stack2("��f��l���� �ޱ�", 54,0,1,Arrays.asList(ChatColor.GRAY + "������ �����մϴ�." ,ChatColor.BLACK +""+ Flow), 22, inv);
			}
		}
		else
		{
			if(type == false)
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "��0��ƾ� �� ������ ���");
			}
			else
			{
				inv = Bukkit.createInventory(null, 27, UniqueCode + "��0���� ���");
				Stack2(ChatColor.GOLD+ "[�����]", 266,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Money") +ChatColor.GOLD +" "+main.Main_ServerOption.Money), 3, inv);
				Stack2(ChatColor.AQUA+ "[����ġ]", 384,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".EXP") +ChatColor.AQUA + " EXP"), 4, inv);
				Stack2(ChatColor.LIGHT_PURPLE+ "[ȣ����]", 38,0,1,Arrays.asList("",ChatColor.WHITE + "" + ChatColor.BOLD + QuestList.getInt(QuestName+".FlowChart."+Flow+".Love") +ChatColor.LIGHT_PURPLE + " Love"), 5, inv);
				if(player.isOp())
				{
					UserData_Object u = new UserData_Object();
					if(u.getInt(player, (byte)1)!=-9)
					{
						u.clearAll(player);
						Stack2("��f��l���� �ޱ�", 54,0,1,Arrays.asList(ChatColor.GRAY + "������ �����մϴ�." ,ChatColor.BLACK +""+ Flow), 22, inv);
					}
				}
				else
					Stack2("��f��l���� �ޱ�", 54,0,1,Arrays.asList(ChatColor.GRAY + "������ �����մϴ�." ,ChatColor.BLACK +""+ Flow), 22, inv);
			}
		}
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK +""+ isOP), 18, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void KillMonsterGUI(Player player, String QuestName, short Flow, boolean isOP)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");
		YamlLoader PlayerQuestList = new YamlLoader();
		PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		String UniqueCode = "��0��0��5��0��8��r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "��0��� �ؾ� �� ���� ���");
		
		Object[] c = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Monster").getKeys(false).toArray();
		for(int counter = 0; counter < c.length; counter++)
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
		
		
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK +""+ isOP), 18, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void HarvestGUI(Player player, String QuestName, short Flow, boolean isOP)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");
		YamlLoader PlayerQuestList = new YamlLoader();
		PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		String UniqueCode = "��0��0��5��0��8��r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "��0ä�� �ؾ� �� ���� ���");
		
		Object[] c = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Block").getKeys(false).toArray();
		for(int counter = 0; counter < c.length; counter++)
		{
			int BlockID = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockID");
			byte BlockData = (byte) QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockData");
			int Amount = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".Amount");
			boolean DataEquals = QuestList.getBoolean(QuestName+".FlowChart."+Flow+".Block."+counter+".DataEquals");
			int PlayerHarvestAmount = PlayerQuestList.getInt("Started."+QuestName+".Block."+counter);
			event.Main_Interact IT = new event.Main_Interact();
			
			if(DataEquals == true)
				Stack(ChatColor.YELLOW+IT.SetItemDefaultName((short) BlockID,(byte)BlockData), BlockID, BlockData, 1, Arrays.asList(ChatColor.WHITE + "[" +PlayerHarvestAmount+"/"+ Amount + "]","",ChatColor.GRAY + "������ ID : " +BlockID,ChatColor.GRAY + "������ Data : " +BlockData), counter, inv);
			else
				Stack(ChatColor.YELLOW+"�ƹ��� "+IT.SetItemDefaultName((short) BlockID,(byte)BlockData)+ChatColor.YELLOW+" ����", BlockID, 0, 1, Arrays.asList(ChatColor.WHITE + "[" +PlayerHarvestAmount+"/"+ Amount + "]","",ChatColor.GRAY + "������ ID : " +BlockID), counter, inv);
		}
		
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK +""+ isOP), 18, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 26, inv);
		player.openInventory(inv);
	}
	
	public void KeepGoing(Player player, String QuestName, short Flow, short Mob, boolean Harvest)
	{
		String UniqueCode = "��0��0��5��0��9��r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "��0��� ��� �Ͻðڽ��ϱ�?");

		if(Harvest == false)
		{
			Stack2("��a��l��� ����ϱ�", 386,0,1,Arrays.asList(ChatColor.GRAY + "��� ����� �߰��� ����մϴ�.",ChatColor.BLACK +""+Flow,ChatColor.BLACK + ""+Mob), 10, inv);
			Stack2("��c��l��� �ߴ��ϱ�", 166,0,1,Arrays.asList(ChatColor.GRAY + "��� ��� �߰��� �����մϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 16, inv);
		}
		else
		{
			Stack2("��a��l��� ����ϱ�", 386,0,1,Arrays.asList(ChatColor.GRAY + "ä�� ����� �߰��� ����մϴ�.",ChatColor.BLACK +""+Flow,ChatColor.BLACK + ""+Mob), 10, inv);
			Stack2("��c��l��� �ߴ��ϱ�", 166,0,1,Arrays.asList(ChatColor.GRAY + "ä�� ��� �߰��� �����մϴ�.",ChatColor.BLACK + ChatColor.stripColor(QuestName)), 16, inv);
		}
		player.openInventory(inv);
	}
	
	public void Quest_NavigationListGUI(Player player, short page, String QuestName)
	{
		YamlLoader NavigationConfig = new YamlLoader();
		NavigationConfig.getConfig("Navigation/NavigationList.yml");

		String UniqueCode = "��0��0��5��0��a��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0����Ʈ �׺� ���� : " + (page+1));

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
			
			
			String TimeS = ChatColor.DARK_AQUA+"<������ �� ���� ����>";
			String PermitionS = ChatColor.DARK_AQUA+"<OP�� ��� ����>";
			String sensitiveS = ChatColor.BLUE+"<�ݰ� "+sensitive+"���� �̳��� �������� ����>";
			String ShowArrowS = ChatColor.DARK_AQUA+"<�⺻ ȭ��ǥ ���>";
			if(Permition == false)
				PermitionS = ChatColor.DARK_AQUA+"<��� ��� ����>";
			if(Time >= 0)
				TimeS = ChatColor.DARK_AQUA+"<"+Time+"�� ���� ����>";
			switch(ShowArrow)
			{
			default:
				ShowArrowS = ChatColor.DARK_AQUA+"<�⺻ ȭ��ǥ ���>";
				break;
			}
			Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + Navi[count].toString(), 395,0,1,Arrays.asList(
			ChatColor.YELLOW+""+ChatColor.BOLD+NaviName,"",
			ChatColor.BLUE+"[���� ����]",ChatColor.BLUE+"���� : "+ChatColor.WHITE+world,
			ChatColor.BLUE+"��ǥ : " + ChatColor.WHITE+x+","+y+","+z,sensitiveS,"",
			ChatColor.DARK_AQUA+"[��Ÿ �ɼ�]",TimeS,PermitionS,ShowArrowS,""
			,ChatColor.YELLOW+"[�� Ŭ���� �׺� ����]"), loc, inv);
			loc++;
		}
		
		if(Navi.length-(page*44)>45)
		Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+QuestName), 53, inv);
		player.openInventory(inv);
	}
	
	public void Quest_OPChoice(Player player,String QuestName, short Flow,short page)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "��0��0��5��0��b��r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "��0����Ʈ ���� Ȯ��");
		
		String[] script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.0.Lore")+"%enter%%enter%"+"��9��l������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.0.Var")).split("%enter%");
		
		switch(QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size())
		{
		case 1:
			Stack2("��f��l[����]", 72,0,1,Arrays.asList(script), 13, inv);
			break;
		case 2:
			Stack2("��f��l[����]", 72,0,1,Arrays.asList(script), 12, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%"+"��9��l������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			Stack2("��f��l[����]", 72,0,2,Arrays.asList(script), 14, inv);
			break;
		case 3:
			Stack2("��f��l[����]", 72,0,1,Arrays.asList(script), 11, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%"+"��9��l������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			Stack2("��f��l[����]", 72,0,2,Arrays.asList(script), 13, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore")+"%enter%%enter%"+"��9��l������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var")).split("%enter%");
			Stack2("��f��l[����]", 72,0,3,Arrays.asList(script), 15, inv);
			break;
		case 4:
			Stack2("��f��l[����]", 72,0,1,Arrays.asList(script), 10, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore")+"%enter%%enter%"+"��9��l������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.1.Var")).split("%enter%");
			Stack2("��f��l[����]", 72,0,2,Arrays.asList(script), 12, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore")+"%enter%%enter%"+"��9��l������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.2.Var")).split("%enter%");
			Stack2("��f��l[����]", 72,0,3,Arrays.asList(script), 14, inv);
			script = (QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.3.Lore")+"%enter%%enter%"+"��9��l������ ���� : " + ChatColor.WHITE+QuestList.getInt(QuestName+".FlowChart."+Flow+".Choice.3.Var")).split("%enter%");
			Stack2("��f��l[����]", 72,0,4,Arrays.asList(script), 16, inv);
			break;
		}
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+""+page), 18, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+QuestName), 26, inv);
		player.openInventory(inv);
	}
	
	public void Quest_UserChoice(Player player,String QuestName, short Flow)
	{
		YamlLoader QuestList = new YamlLoader();
		QuestList.getConfig("Quest/QuestList.yml");

		String UniqueCode = "��0��0��5��0��c��r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "��0����Ʈ ����");

		String lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.0.Lore").replace("%player%", player.getName());
		String[] script = lore.split("%enter%");
		
		switch(QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Choice").getKeys(false).size())
		{
		case 1:
			Stack2("��f��l[����]", 72,0,1,Arrays.asList(script), 13, inv);
			break;
		case 2:
			Stack2("��f��l[����]", 72,0,1,Arrays.asList(script), 12, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2("��f��l[����]", 72,0,2,Arrays.asList(script), 14, inv);
			break;
		case 3:
			Stack2("��f��l[����]", 72,0,1,Arrays.asList(script), 11, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2("��f��l[����]", 72,0,2,Arrays.asList(script), 13, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2("��f��l[����]", 72,0,3,Arrays.asList(script), 15, inv);
			break;
		case 4:
			Stack2("��f��l[����]", 72,0,1,Arrays.asList(script), 10, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.1.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2("��f��l[����]", 72,0,2,Arrays.asList(script), 12, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.2.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2("��f��l[����]", 72,0,3,Arrays.asList(script), 14, inv);
			lore = QuestList.getString(QuestName+".FlowChart."+Flow+".Choice.3.Lore").replace("%player%", player.getName());
			script = lore.split("%enter%");
			Stack2("��f��l[����]", 72,0,4,Arrays.asList(script), 16, inv);
			break;
		}
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+QuestName,ChatColor.BLACK+""+Flow), 26, inv);
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
			SoundEffect.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.8F);
			
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
						//������ ENDIF�� ELSE�� IF�� ã�� ���ϸ� ����Ʈ �Ϸ�� �Ѿ
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
						SoundEffect.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.8F);
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
						//������ ENDIF�� ã�� ���ϸ� ����Ʈ �Ϸ�� �Ѿ
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
						SoundEffect.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.8F);
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
					player.sendMessage(ChatColor.RED+"[����Ʈ] : ����Ʈ�� �ϼ����� ���Ͽ����ϴ�!");
					PlayerQuestList.set("Ended."+QuestName+".ClearTime", new util.ETC().getNowUTC());
					PlayerQuestList.removeKey("Started."+QuestName);
					PlayerQuestList.saveConfig();
					YamlLoader PlayerVarList = new YamlLoader();
					PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					PlayerVarList.removeKey(QuestName);
					PlayerVarList.saveConfig();
					player.closeInventory();
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
					SoundEffect.SP(player, Sound.ENTITY_WITHER_DEATH, 0.7F, 0.8F);
				}
				break;
				case "QuestReset":
				{
					player.sendMessage(ChatColor.YELLOW+"[����Ʈ] : ����Ʈ�� �����Ͽ����ϴ�!");
					PlayerQuestList.removeKey("Started."+QuestName);
					PlayerQuestList.saveConfig();
					YamlLoader PlayerVarList = new YamlLoader();
					PlayerVarList.getConfig("Quest/PlayerVar/"+player.getUniqueId()+".yml");
					PlayerVarList.removeKey(QuestName);
					PlayerVarList.saveConfig();
					player.closeInventory();
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.2F, 0.8F);
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
					ServerTick_Main.NaviUsingList.add(player.getName());
					player.closeInventory();
					SoundEffect.SP(player, Sound.BLOCK_NOTE_PLING, 1.0F, 1.0F);
					
					servertick.ServerTick_Object STSO = new servertick.ServerTick_Object(Long.parseLong(UTC), "NV");
					STSO.setCount(0);//Ƚ �� �ʱ�ȭ
					STSO.setMaxCount(NavigationConfig.getInt(UTC+".time"));//N�ʰ� �׺���̼�
					//-1�� ������, N�ʰ��� �ƴ�, ã�� �� �� ���� �׺���̼� ����
					STSO.setString((byte)1, NavigationConfig.getString(UTC+".world"));//������ ���� �̸� ����
					STSO.setString((byte)2, player.getName());//�÷��̾� �̸� ����
					
					STSO.setInt((byte)0, NavigationConfig.getInt(UTC+".x"));//������X ��ġ����
					STSO.setInt((byte)1, NavigationConfig.getInt(UTC+".y"));//������Y ��ġ����
					STSO.setInt((byte)2, NavigationConfig.getInt(UTC+".z"));//������Z ��ġ����
					STSO.setInt((byte)3, NavigationConfig.getInt(UTC+".sensitive"));//���� ���� ����
					STSO.setInt((byte)4, NavigationConfig.getInt(UTC+".ShowArrow"));//��ƼŬ ����
					
					servertick.ServerTick_Main.Schedule.put(Long.parseLong(UTC), STSO);
					player.sendMessage(ChatColor.YELLOW+"[�׺���̼�] : ��ã�� �ý����� �����˴ϴ�!");
					player.sendMessage(ChatColor.YELLOW+"(ȭ��ǥ�� ������ ���� ���, [ESC] �� [����] �� [���� ����] ���� [����]�� [���]�� ������ �ּ���!)");
					
				}
				else
				{
					SoundEffect.SP(player, Sound.BLOCK_NOTE_BASS, 1.0F, 1.0F);
					player.sendMessage(ChatColor.RED+"[�׺���̼�] : ��ϵ� �׺���̼��� ã�� �� �����ϴ�! �����ڿ��� �����ϼ���!");
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
				String script3 = ChatColor.WHITE+QuestList.getString(QuestName+".FlowChart."+FlowChart+".Message");
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
					scriptA[count] = ChatColor.WHITE + scriptA[count];
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
					SoundEffect.SL(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 0.8F, 1.0F);
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

		UserData_Object u = new UserData_Object();
		boolean ChooseQuestGUI = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		if(slot == 53)//�ݱ�
		{
			if(ChooseQuestGUI == true)
				u.clearAll(player);
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//���� ���
			{
				if(ChooseQuestGUI == true)
				{
					QuestOptionGUI(player, u.getString(player, (byte)1));
					u.clearAll(player);
				}
				else
					new admin.OPbox_GUI().OPBoxGUI_Main(player,(byte) 1);
			}
			else if(slot==48)//���� ������
				AllOfQuestListGUI(player,(short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-2),ChooseQuestGUI);
			else if(slot==50)//���� ������
				AllOfQuestListGUI(player, Short.parseShort(event.getInventory().getTitle().split(" : ")[1]),ChooseQuestGUI);
			else if(slot==49)//�� ����Ʈ
			{
				player.sendMessage(ChatColor.GOLD + "/����Ʈ ���� [Ÿ��] [�̸�]" );
				player.sendMessage(ChatColor.GREEN + "[Ÿ�� : �Ϲ� / �ݺ� / ���� / ���� / �Ѵ�]");
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
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.8F, 1.0F);
						player.sendMessage(ChatColor.RED+"[����Ʈ] : ���� ����Ʈ�� ����� �� �����ϴ�!");
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
		  						SoundEffect.SP(a[count], Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
		  						a[count].sendMessage(ChatColor.LIGHT_PURPLE + "[������] : "+ChatColor.YELLOW + player.getName()+ChatColor.LIGHT_PURPLE + "�Բ��� " + ChatColor.YELLOW + QuestName+ChatColor.LIGHT_PURPLE + "����Ʈ�� �����ϼ̽��ϴ�!");
		  	  		    	}	
		  	  		    }
		  	  			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + player.getName()+ChatColor.LIGHT_PURPLE + "�Բ��� " + ChatColor.YELLOW + QuestName+ChatColor.LIGHT_PURPLE + "����Ʈ�� �����ϼ̽��ϴ�!");
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
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			if(slot == 45)//���� ���
				AllOfQuestListGUI(player,(short) 0,false);
			else if(slot == 48)//���� ������
				FixQuestGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2),QuestName);
			else if(slot == 50)//���� ������
				FixQuestGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]),QuestName);
			else if(slot == 49)//�� ������Ʈ �߰�
				SelectObjectPage(player, (byte) 0, QuestName);
			else
			{
				short Flow = Short.parseShort(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				if(event.getClick().isLeftClick() == true)
				{
					if(event.getCurrentItem().getItemMeta().getLore().get(0).contains(" : "))
						switch(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1])
						{
							case "����":
								ShowItemGUI(player, QuestName, (short) Flow,true,false);
								break;
							case "����":
								{
									new UserData_Object().setInt(player, (byte)1,-9);
									ShowItemGUI(player, QuestName, (short) Flow,true,true);
								}
								break;
							case "���":
								KillMonsterGUI(player, QuestName, (short) Flow, player.isOp());
								break;
							case "ä��" :
								HarvestGUI(player, QuestName, (short) Flow, player.isOp());
								break;
							case "����":
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
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//���� ���
				new user.Stats_GUI().StatusGUI(player);
			else if(slot == 48)//���� ������
				MyQuestListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//���� ������
				MyQuestListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlLoader PlayerQuestList = new YamlLoader();
				PlayerQuestList.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
				int Flow = PlayerQuestList.getInt("Started."+QuestName+".Flow");
				if(event.getCurrentItem().getItemMeta().getLore().toString().contains("����") == true)
					ShowItemGUI(player, QuestName, (short) Flow,false,false);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("����") == true)
					ShowItemGUI(player, QuestName, (short) Flow,false,true);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("óġ") == true)
					KillMonsterGUI(player, QuestName, (short) Flow, player.isOp());
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("����") == true)
					QuestRouter(player, QuestName);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("ä��") == true)
					HarvestGUI(player, QuestName, (short) Flow, false);
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("������") == true)
					Quest_UserChoice(player, QuestName, (short) Flow);
			}
		}
	}

	public void SelectObjectPageClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		UserData_Object u = new UserData_Object();
		
		String QuestName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		YamlLoader QuestConfig = new YamlLoader();
		QuestConfig.getConfig("Quest/QuestList.yml");
		short size = (short) QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size();

		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "����":
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"CV");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : �÷����� �������� �� ���� �����ұ��?");
				player.sendMessage(ChatColor.GRAY + "(�ּ� -1000 ~ �ִ� 30000)");
				player.closeInventory();
				return;
			case "����":
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"CS");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : �� ���� �������� ���� �� �ǰ���?");
				player.sendMessage(ChatColor.GRAY + "(�ּ� 1�� ~ �ִ� 4��)");
				player.closeInventory();
				return;
			case "�׺�":
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				Quest_NavigationListGUI(player, (short) 0, QuestName);
				return;
			case "���":
			case "����":
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("���"))
					u.setString(player, (byte)1,"Script");
				else
					u.setString(player, (byte)1,"PScript");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ����� ��縦 ä��â�� �Է��ϼ���!");
				player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.GOLD + "%value%"+ChatColor.WHITE + " - �÷��̾��� ���� ����Ʈ ���� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
				return;
			case "�湮":
				YamlLoader AreaList = new YamlLoader();
				AreaList.getConfig("Area/AreaList.yml");
				Object[] arealist =AreaList.getConfigurationSection("").getKeys(false).toArray();

				if(arealist.length <= 0)
				{
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
					player.sendMessage(ChatColor.RED + "[����Ʈ] : ������ ������ �����ϴ�!");
					player.sendMessage(ChatColor.GOLD + "/���� <�̸�> ����"+ChatColor.YELLOW + " - ���ο� ������ �����մϴ�. -");
					player.closeInventory();
					return;
				}
				player.sendMessage(ChatColor.GREEN + "���������������������� ��Ϧ�����������������");
				for(int count =0; count <arealist.length;count++)
				{
					player.sendMessage(ChatColor.GREEN +"  "+ arealist[count].toString());
				}
				player.sendMessage(ChatColor.GREEN + "���������������������� ��Ϧ�����������������");
				player.sendMessage(ChatColor.DARK_AQUA + "[����Ʈ] : �湮�ؾ� �� ���� �̸��� ���� �ּ���!");
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Visit");
				u.setString(player, (byte)2,QuestName);
				player.closeInventory();
				return;
			case "����":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Give");
				u.setString(player, (byte)3,QuestName);
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+ChatColor.RED+"����ǰ�� ���� �غ��Ͻ� ��,"+ChatColor.GREEN+" ���� NPC�� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "���":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Hunt");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"N");
				u.setInt(player, (byte)2, -1);
				u.setInt(player, (byte)3, -1);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��� �ؾ� �� ���͸� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "��ȭ":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Talk");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��ȭ �ؾ� �� NPC�� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "����":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Present");
				u.setString(player, (byte)3,QuestName);
				u.setInt(player, (byte)5, -1);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+ChatColor.RED+"���� �������� ���� �غ��Ͻ� ��,"+ChatColor.GREEN+" ������ �� NPC�� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "�̵�":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"TelePort");
				u.setString(player, (byte)3,QuestName);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �÷��̾ �̵� ��ų ��ġ�� ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "ä��":
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Harvest");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"null");

				u.setInt(player, (byte)1, 0);//���� ID
				u.setInt(player, (byte)2, 0);//���� DATA
				u.setInt(player, (byte)3, -1);//������ ���
				u.setInt(player, (byte)4, -1);//������ ���
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ä���ؾ� �� ������ ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "����":
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"BlockPlace");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"null");
				
				u.setInt(player, (byte)1, 0);//���� ID
				u.setInt(player, (byte)2, 0);//���� DATA
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : ������ ��ġ�� ������ ��Ŭ�� �ϼ���!");
				player.closeInventory();
				return;
			case "�Ҹ�":
				return;
			case "�Ӹ�":
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Whisper");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : � �޽����� �����ϰ� �����Ű���?");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.GOLD + "%value%"+ChatColor.WHITE + " - �÷��̾��� ���� ����Ʈ ���� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
				return;
			case "��ü":
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"BroadCast");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : � �޽����� �����ϰ� �����Ű���?");
				player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.GOLD + "%value%"+ChatColor.WHITE + " - �÷��̾��� ���� ����Ʈ ���� ��Ī�ϱ� -");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				player.closeInventory();
				return;
			case "���� ���":
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				FixQuestGUI(player,(short) 0,QuestName);
				return;
			case "�ݱ�":
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
				return;
		}

		if(size != 0)
		{
			switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
			{
			case "���":
			{
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Cal");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : � ������ �Ͻð� ��������?");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"1. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���մϴ�.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"2. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���ϴ�.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"3. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���մϴ�.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"4. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���� ���� ���մϴ�.)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"5. "+ChatColor.WHITE + "A �� B (�÷��̾� ������ B�� ���� �������� ���մϴ�.)");
				player.sendMessage(ChatColor.GRAY + "(�ּ� 1 ~ �ִ� 5 ���� �� �Է�)");
				player.closeInventory();
			}
			return;
			case "����Ʈ �ʱ�ȭ":
			{
				
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "QuestReset");
		    	QuestConfig.saveConfig();
				FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			case "����Ʈ ����":
				{
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
					QuestConfig.set(QuestName+".FlowChart."+size+".Type", "QuestFail");
	    	    	QuestConfig.saveConfig();
	    			FixQuestGUI(player, (short) 0, QuestName);
				}
			break;
			case "IF":
			{
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"IFTS");
				u.setString(player, (byte)2,QuestName);
				player.sendMessage(ChatColor.GREEN + "[����Ʈ] : � �񱳸� �Ͻð� ��������?");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"1. "+ChatColor.WHITE + "A == B (�÷��̾� ������ B�� ������?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"2. "+ChatColor.WHITE + "A != B (�÷��̾� ������ B�� �ٸ���?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"3. "+ChatColor.WHITE + "A > B (�÷��̾� ������ B���� ū��?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"4. "+ChatColor.WHITE + "A < B (�÷��̾� ������ B���� ������?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"5. "+ChatColor.WHITE + "A >= B (�÷��̾� ������ B���� ũ�ų� ������?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"6. "+ChatColor.WHITE + "A <= B (�÷��̾� ������ B���� �۰ų� ������?)");
				player.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"7. "+ChatColor.WHITE + "C <= A <= B (�÷��̾� ������ �ּ� C ~ �ִ� B �����ΰ�?)");
				player.sendMessage(ChatColor.GRAY + "(�ּ� 1 ~ �ִ� 7 ���� �� �Է�)");
				player.closeInventory();
			}
			break;
			case "ELSE":
			{
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "ELSE");
    	    	QuestConfig.saveConfig();
    			FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			case "ENDIF":
			{
				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "ENDIF");
    	    	QuestConfig.saveConfig();
    			FixQuestGUI(player, (short) 0, QuestName);
			}
			break;
			}
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
			player.sendMessage(ChatColor.RED+ "[����Ʈ] : �ش� �׸��� ù ��° ���� ��ҷ� �� �� �����ϴ�!");
		}
		return;
	}

	public void QuestScriptTypeGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 13)
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
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
		if(event.getClickedInventory().getTitle().compareTo("container.inventory") != 0)
			if(event.getSlot() == 8)
				event.getWhoClicked().closeInventory();
	}
	
	public void ShowNeedGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
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
						SoundEffect.SP(player, org.bukkit.Sound.ENTITY_ITEM_PICKUP, 2.0F, 1.7F);
						player.sendMessage(ChatColor.YELLOW + "[����Ʈ] : ���� �÷��̾��� �κ��丮 ������ ������� �ʾ� ������ ���� �� �����ϴ�!");
						return;
					}
				}
				main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".Money"), 0, false);

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
	    			new util.Util_Player().addMoneyAndEXP(player, 0, QuestList.getLong(QuestName + ".FlowChart."+QuestFlow+".EXP"), null, false, false);
				
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
			if(event.getClickedInventory().getTitle().compareTo("container.inventory") != 0)
			{
				event.setCancelled(true);
				if(slot == 8)
				{
					SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
					player.closeInventory();
				}
				else
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
					UserData_Object u = new UserData_Object();
					u.setType(player, "Quest");
					if(slot == 0)
					{
						player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ������ ������� �Է� �� �ּ���. ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)4,"M");
					}
					else if(slot == 1)
					{
						u.setInt(player, (byte)2, 0);
						player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��½�ų ����ġ�� �Է� �� �ּ���. ("+ChatColor.YELLOW + "1"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)4,"E");
					}
					else if(slot == 2)
					{
						u.setInt(player, (byte)3, 0);
						player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��½�ų NPC�� ȣ������ �Է� �� �ּ���. ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
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
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			UserData_Object u = new UserData_Object();
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(16).getItemMeta().getLore().get(1));
			short Flow = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(10).getItemMeta().getLore().get(1)));
			short Mob = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(10).getItemMeta().getLore().get(2)));
			if(event.getCurrentItem().getItemMeta().getLore().get(0).contains("���"))
			{
				u.setType(player, "Quest");
				u.setString(player, (byte)1,"Hunt");
				u.setString(player, (byte)2,QuestName);
				u.setString(player, (byte)3,"N");
				u.setInt(player, (byte)2, Mob+1);
				u.setInt(player, (byte)3, Flow);
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ��� �ؾ� �� ���͸� ��Ŭ�� �ϼ���!");
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
				player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ä�� �ؾ� �� ������ ��Ŭ�� �ϼ���!");
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

			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
			YamlLoader QuestList = new YamlLoader();
			QuestList.getConfig("Quest/QuestList.yml");
			String QuestName = ChatColor.stripColor(event.getInventory().getItem(44).getItemMeta().getLore().get(1));
			if(slot == 36)
				AllOfQuestListGUI(player, (short) 0,false);
			else if(slot == 15)//��ų ��ũ ����
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.8F);
				//��ų ���� �� ��ų ��ũ �Է��ϴ� â ������Ʈ �ϱ�
			}
			else if(slot == 4)//����Ʈ Ÿ��
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
			else if(slot == 29)//�ʼ� �Ϸ� ����Ʈ
			{
				if(event.isLeftClick() == true && event.isShiftClick() == false)
				{
					UserData_Object u = new UserData_Object();
					u.setString(player, (byte)1,QuestName);
					AllOfQuestListGUI(player, (short) 0, true);
				}
				else if(event.isRightClick() == true && event.isShiftClick() == true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
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
				if(slot == 11)//���� ����
				{
					u.setString(player, (byte)1,"Level District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �� ���� ���� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"����)");
				}
				else if(slot == 13)//ȣ���� ����
				{
					u.setString(player, (byte)1,"Love District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ȣ���� �� �̻���� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 20)//STR ����
				{
					u.setString(player, (byte)1,"STR District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+main.Main_ServerOption.STR+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 21)//DEX ����
				{
					u.setString(player, (byte)1,"DEX District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+main.Main_ServerOption.DEX+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 22)//INT ����
				{
					u.setString(player, (byte)1,"INT District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+main.Main_ServerOption.INT+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 23)//WILL ����
				{
					u.setString(player, (byte)1,"WILL District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+main.Main_ServerOption.WILL+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 24)//LUK ����
				{
					u.setString(player, (byte)1,"LUK District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : "+main.Main_ServerOption.LUK+"�� �� �̻� �Ǿ�� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
				}
				else if(slot == 33)//����Ʈ ����
				{
					u.setString(player, (byte)1,"Accept District");
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �� ���� ���� �����ϰ� �Ͻðڽ��ϱ�? ("+ChatColor.YELLOW + "0"+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+"��)");
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
		case 45://���� ���
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			SelectObjectPage(player, (byte) 0, QuestName);
			return;
		case 53://������
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://���� ������
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			Quest_NavigationListGUI(player, (short) (page-1), QuestName);
			return;
		case 50://���� ������
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			Quest_NavigationListGUI(player, (short) (page+1), QuestName);
			return;
		default :
			if(event.isLeftClick() == true)
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				YamlLoader QuestConfig = new YamlLoader();
				QuestConfig.getConfig("Quest/QuestList.yml");
	    		int size = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).size();
	    		QuestConfig.set(QuestName+".FlowChart."+size+".Type", "Nevigation");
		    	QuestConfig.set(QuestName+".FlowChart."+size+".NeviUTC",ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
		    	QuestConfig.saveConfig();
				player.sendMessage(ChatColor.GREEN+"[����Ʈ] : �׺���̼��� ���������� ��ϵǾ����ϴ�!");
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
		case 18://���� ���
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			FixQuestGUI(player, page, QuestName);
			return;
		case 26://������
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
	public void Quest_UserChoiceClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		if(event.getSlot() == 26)
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
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
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			
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
		SoundEffect.SP((Player) event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
    	event.getPlayer().sendMessage(ChatColor.GREEN + "[SYSTEM] : ���������� ��ϵǾ����ϴ�!");
		u.clearAll(player);
		return;
	}

	public void PresentItemSettingGUIClose(InventoryCloseEvent event)
	{
		Player player = (Player)event.getPlayer();
		YamlLoader QuestConfig = new YamlLoader();
		QuestConfig.getConfig("Quest/QuestList.yml");
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
			SoundEffect.SP((Player) event.getPlayer(), org.bukkit.Sound.ENTITY_ITEM_PICKUP, 0.5F,1.2F);
	    	event.getPlayer().sendMessage(ChatColor.GREEN + "[SYSTEM] : ���������� �����Ǿ����ϴ�!");
	    	u.clearAll(player);
		}
		return;
	}
	
	public String SkullType(String s)
	{
		String re = s;
		switch(s)
		{
			case "Zombie" : re = ChatColor.DARK_GREEN+"����"; break;
			case "Skeleton" : re = ChatColor.GRAY+"���̷���"; break;
			case "Giant" : re = ChatColor.DARK_GREEN+"���̾�Ʈ"; break;
			case "Spider" : re = ChatColor.GRAY+"�Ź�"; break;
			case "Witch" : re = ChatColor.DARK_PURPLE+"����"; break;
			case "Creeper" : re = ChatColor.GREEN+"ũ����"; break;
			case "Slime" : re = ChatColor.GREEN+"������"; break;
			case "Ghast" : re = ChatColor.GRAY+"����Ʈ"; break;
			case "Enderman" : re = ChatColor.DARK_PURPLE+"������"; break;
			case "Zombie Pigman" : re = ChatColor.LIGHT_PURPLE+"���� �Ǳ׸�"; break;
			case "Cave Spider" : re = ChatColor.GRAY+"���� �Ź�"; break;
			case "Silverfish" : re = ChatColor.GRAY+"������"; break;
			case "Blaze" : re = ChatColor.YELLOW+"��������"; break;
			case "Magma Cube" : re = ChatColor.YELLOW+"���׸� ť��"; break;
			case "Bat" : re = ChatColor.GRAY+"����"; break;
			case "Endermite" : re = ChatColor.DARK_PURPLE+"���� �����"; break;
			case "Guardian" : re = ChatColor.DARK_AQUA+"�����"; break;
			case "Pig" : re = ChatColor.LIGHT_PURPLE+"����"; break;
			case "Sheep" : re = ChatColor.WHITE+"��"; break;
			case "Cow" : re = ChatColor.GRAY+"��"; break;
			case "Chicken" : re = ChatColor.WHITE+"��"; break;
			case "Squid" : re = ChatColor.GRAY+"��¡��"; break;
			case "Wolf" : re = ChatColor.WHITE+"����"; break;
			case "Mooshroom" : re = ChatColor.RED+"���� ��"; break;
			case "Ocelot" : re = ChatColor.YELLOW+"������"; break;
			case "Horse" : re = ChatColor.GOLD+"��"; break;
			case "Rabbit" : re = ChatColor.WHITE+"�䳢"; break;
			case "Villager" : re = ChatColor.GOLD+"�ֹ�"; break;
			case "Snow Golem" : re = ChatColor.WHITE+"�� ���"; break;
			case "Iron Golem" : re = ChatColor.WHITE+"ö ��"; break;
			case "Wither" : re = "��7��l����"; break;
			case "unknown" : re = ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"���� �巡��"; break;
			default :
				re = s; break;
		}
		return re;
	}
}