package GBD_RPG.CustomItem;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import GBD_RPG.Admin.OPbox_GUI;
import GBD_RPG.Main_Main.Main_ServerOption;
import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_GUI;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UseableItem_GUI extends Util_GUI
{
	public void UseableItemListGUI(Player player, int page)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager ItemList  = YC.getNewConfig("Item/Consume.yml");

		String UniqueCode = "��0��0��3��0��3��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0�Ҹ� ������ ��� : " + (page+1));

		Object[] a = ItemList.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String ItemName = ItemList.getString(a[count].toString()+".DisplayName");
			short ID = (short) ItemList.getInt(a[count].toString()+".ID");
			byte Data = (byte) ItemList.getInt(a[count].toString()+".Data");
			
			if(count > a.length || loc >= 45) break;
			Stack2(ItemName, ID,Data,1,Arrays.asList(LoreCreater(Integer.parseInt(a[count].toString()))), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�� ������", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� �������� �����մϴ�."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void ChooseUseableItemTypeGUI(Player player, int number)
	{
		String UniqueCode = "��0��0��3��0��4��r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "��0�Ҹ� ������ Ÿ��");

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��ȯ��]", 340,0,1,Arrays.asList(ChatColor.GRAY + "Ư�� ��ġ�� �ż��� �̵��� �� �ִ�",ChatColor.GRAY+"��ȯ���� �����մϴ�."), 2, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[�ֹ���]", 339,0,1,Arrays.asList(ChatColor.GRAY + "Ư���� ����� ���",ChatColor.GRAY+"�ֹ����� �����մϴ�."), 3, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��ų ��]", 403,0,1,Arrays.asList(ChatColor.GRAY + "Ư�� ��ų�� ��� �� �ִ�",ChatColor.GRAY+"��ų ���� �����մϴ�.","",ChatColor.RED+"[���� �ý����� '������'���� �մϴ�.]"), 4, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[����, ����]", 297,0,1,Arrays.asList(ChatColor.GRAY + "���������� ����� ������",ChatColor.GRAY+"���� �� ���� ���� �����մϴ�."), 5, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "[��]", 381,0,1,Arrays.asList(ChatColor.GRAY + "������ �ɷ��� �÷��ִ�",ChatColor.GRAY+"�ź��� ���� �����մϴ�."), 6, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK +""+ number), 8, inv);
		player.openInventory(inv);
	}
	
	public void NewUseableItemGUI(Player player, int number)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");

		String UniqueCode = "��0��0��3��0��5��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0�Ҹ� ������ �� ����");
		String ItemName = ItemList.getString(number+".DisplayName");
		short ItemID = (short) ItemList.getInt(number+".ID");
		byte ItemData = (byte) ItemList.getInt(number+".Data");

		String Type = "";
		String Grade = "";
		for(short counter =0;counter < 13 - ItemList.getString(number+".Type").length();counter++ )
			Type = Type +" ";
		Type = Type +ItemList.getString(number+".Type");
		Grade = ItemList.getString(number+".Grade");
		
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 58,0,1,null, 9, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 58,0,1,null, 10, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 58,0,1,null, 11, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 58,0,1,null, 18, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 58,0,1,null, 20, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 58,0,1,null, 27, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 58,0,1,null, 28, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �����    ]", 58,0,1,null, 29, inv);
		
		Stack2(ItemName, ItemID,ItemData,1,Arrays.asList(LoreCreater(number)), 19, inv);
		
		Stack2(ChatColor.DARK_AQUA + "[    ���� ����    ]", 421,0,1,Arrays.asList(ChatColor.WHITE+"������ ����â��",ChatColor.WHITE+"�����մϴ�.","",ChatColor.WHITE+"[    ���� ����    ]","       "+ ItemList.getString(number+".ShowType"),""), 37, inv);
		Stack2(ChatColor.DARK_AQUA + "[    �̸� ����    ]", 421,0,1,Arrays.asList(ChatColor.WHITE+"�������� �̸���",ChatColor.WHITE+"�����մϴ�.",""), 13, inv);
		Stack2(ChatColor.DARK_AQUA + "[    ���� ����    ]", 386,0,1,Arrays.asList(ChatColor.WHITE+"�������� ������",ChatColor.WHITE+"�����մϴ�.",""), 14, inv);
		Stack2(ChatColor.DARK_AQUA + "[    Ÿ�� ����    ]", 166,0,1,Arrays.asList("",ChatColor.RED+"[Ÿ�� ������ �Ұ��� �մϴ�]",""), 15, inv);
		Stack2(ChatColor.DARK_AQUA + "[    ��� ����    ]", 266,0,1,Arrays.asList(ChatColor.WHITE+"�������� �����",ChatColor.WHITE+"�����մϴ�.","",ChatColor.WHITE+"[    ���� ���    ]","       "+Grade,""), 16, inv);
		Stack2(ChatColor.DARK_AQUA + "[        ID        ]", 405,0,1,Arrays.asList(ChatColor.WHITE+"�������� ID����",ChatColor.WHITE+"�����մϴ�.",""), 22, inv);
		Stack2(ChatColor.DARK_AQUA + "[       DATA       ]", 336,0,1,Arrays.asList(ChatColor.WHITE+"�������� DATA����",ChatColor.WHITE+"�����մϴ�.",""), 23, inv);

		switch(ChatColor.stripColor(ItemList.getString(number+".Type")))
		{
		case "[��ȯ��]":
			Stack(ChatColor.DARK_AQUA + "[    ��ġ ����    ]", 386,0,1,Arrays.asList(ChatColor.WHITE+"���� �� �ִ� ��Ҹ�",ChatColor.WHITE+"���� �������� ��� �մϴ�.","",ChatColor.BLUE+"[���� ��ϵ� ��ġ]",ChatColor.BLUE+"���� : "+ItemList.getString(number+".World"),ChatColor.BLUE+"��ǥ : "+ItemList.getInt(number+".X")+","+ItemList.getInt(number+".Y")+","+ItemList.getInt(number+".Z"),"",ChatColor.YELLOW+"[�� Ŭ���� ���� ���� ���]",""), 25, inv);
			break;
		case "[�ֹ���]":
			Stack(ChatColor.DARK_AQUA + "[     ��ų ����Ʈ     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"�ֹ��� ���� ���",ChatColor.WHITE+"��ų ����Ʈ�� ����ϴ�.",""), 24, inv);
			Stack(ChatColor.DARK_AQUA + "[     ���� ����Ʈ     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"�ֹ��� ���� ���",ChatColor.WHITE+"���� ����Ʈ�� ����ϴ�.",""), 25, inv);
			Stack(ChatColor.DARK_AQUA + "[        ���        ]", 307,0,1,Arrays.asList(ChatColor.WHITE+"�ֹ��� ���� ������",ChatColor.WHITE+"��� ���� �ݴϴ�.",""), 31, inv);
			Stack(ChatColor.DARK_AQUA + "[        ��ȣ        ]", 306,0,1,Arrays.asList(ChatColor.WHITE+"�ֹ��� ���� ��ȣ��",ChatColor.WHITE+"��� ���� �ݴϴ�.",""), 32, inv);
			Stack(ChatColor.DARK_AQUA + "[      ���� ���      ]", 311,0,1,Arrays.asList(ChatColor.WHITE+"�ֹ��� ���� ���� ��",ChatColor.WHITE+"��� ���� �ݴϴ�.",""), 33, inv);
			Stack(ChatColor.DARK_AQUA + "[      ���� ��ȣ      ]", 310,0,1,Arrays.asList(ChatColor.WHITE+"�ֹ��� ���� ���� ��ȣ��",ChatColor.WHITE+"��� ���� �ݴϴ�.",""), 34, inv);
			Stack(ChatColor.DARK_AQUA + "[        ����        ]", 399,0,1,Arrays.asList(ChatColor.WHITE+"�ֹ��� ���� ������ ����������",ChatColor.WHITE+"��� ���� �ݴϴ�.",""), 40, inv);
			break;
		case "[��ų��]":
			if(ItemList.getString(number+".Skill").compareTo("null")==0)
				Stack(ChatColor.DARK_AQUA + "[        ��ų        ]", 340,0,1,Arrays.asList(ChatColor.WHITE+"��ų �� ����",ChatColor.WHITE+"�Ʒ� ��ų�� �����մϴ�.","",ChatColor.BLUE+"[���� ��ϵ� ��ų]",ChatColor.WHITE+"      ����"), 25, inv);
			else
				Stack(ChatColor.DARK_AQUA + "[        ��ų        ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"��ų �� ����",ChatColor.WHITE+"�Ʒ� ��ų�� �����մϴ�.","",ChatColor.BLUE+"[���� ��ϵ� ��ų]",ChatColor.WHITE+ItemList.getString(number+".Skill")), 25, inv);
			break;
		case "[�Һ�]":
			Stack(ChatColor.DARK_AQUA + "[       ������       ]", 364,0,1,Arrays.asList(ChatColor.WHITE+"������ ���� ��⸦",ChatColor.WHITE+"���� ���� �ݴϴ�.",""), 31, inv);
			Stack(ChatColor.DARK_AQUA + "[       ������       ]", 373,8261,1,Arrays.asList(ChatColor.WHITE+"������ ���� ��������",ChatColor.WHITE+"��� ���� �ݴϴ�.",""), 32, inv);
			Stack(ChatColor.DARK_AQUA + "[        ����        ]", 373,8230,1,Arrays.asList(ChatColor.WHITE+"������ ���� ������",ChatColor.WHITE+"��� ���� �ݴϴ�.",""), 33, inv);
			Stack(ChatColor.DARK_AQUA + "[        ȯ��        ]", 399,0,1,Arrays.asList(ChatColor.WHITE+"������ ���� �÷��̾���",ChatColor.WHITE+"������ �ʱ�ȭ ���� �ݴϴ�.","",ChatColor.RED+"[���� �ý����� �������� ��츸 ��� �����մϴ�.]",""), 34, inv);
			break;
		case "[��]":
			Stack(ChatColor.DARK_AQUA + "[       �����       ]", 267,0,1,Arrays.asList(ChatColor.WHITE+"�� ������ "+Main_ServerOption.Damage+"��",ChatColor.WHITE+"���� ���� �ݴϴ�.",""), 24, inv);
			Stack(ChatColor.DARK_AQUA + "[     ���� �����     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"�� ������ "+Main_ServerOption.MagicDamage+"��",ChatColor.WHITE+"���� ���� �ݴϴ�.",""), 25, inv);
			Stack(ChatColor.DARK_AQUA + "[        ���        ]", 307,0,1,Arrays.asList(ChatColor.WHITE+"�� ������ ������",ChatColor.WHITE+"���� ���� �ݴϴ�.",""), 31, inv);
			Stack(ChatColor.DARK_AQUA + "[        ��ȣ        ]", 306,0,1,Arrays.asList(ChatColor.WHITE+"�� ������ ��ȣ��",ChatColor.WHITE+"���� ���� �ݴϴ�.",""), 32, inv);
			Stack(ChatColor.DARK_AQUA + "[      ���� ���      ]", 311,0,1,Arrays.asList(ChatColor.WHITE+"�� ������ ���� ��",ChatColor.WHITE+"���� ���� �ݴϴ�.",""), 33, inv);
			Stack(ChatColor.DARK_AQUA + "[      ���� ��ȣ      ]", 310,0,1,Arrays.asList(ChatColor.WHITE+"�� ������ ���� ��ȣ��",ChatColor.WHITE+"���� ���� �ݴϴ�.",""), 34, inv);
			Stack(ChatColor.DARK_AQUA + "[        ����        ]", 399,0,1,Arrays.asList(ChatColor.WHITE+"�� ������ ������ ����������",ChatColor.WHITE+"���� ���� �ݴϴ�.",""), 40, inv);
			Stack(ChatColor.DARK_AQUA + "[       ������       ]", 145,2,1,Arrays.asList(ChatColor.WHITE+"�� ������ �������� ��������",ChatColor.WHITE+"���� ���� �ݴϴ�.","",ChatColor.RED+"[�Ϲ� ������ �Ұ���]",""), 41, inv);
			//Stack(ChatColor.DARK_AQUA + "[        ����        ]", 145,0,1,Arrays.asList(ChatColor.WHITE+"�� ������ �ִ� ���� Ƚ����",ChatColor.WHITE+"���� ���� �ݴϴ�.",""), 42, inv);
			break;
		}
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+Type), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+number), 53, inv);
		player.openInventory(inv);
	}
	
	public void SelectSkillGUI(Player player, short page, int ItemNumber)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");

		String UniqueCode = "��0��0��3��0��6��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0��� ���� ��ų ��� : " + (page+1));

		Object[] a= SkillList.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String SkillName = a[count].toString();
			short JobLevel= (short) SkillList.getConfigurationSection(a[count].toString()+".SkillRank").getKeys(false).size();
			if(count > a.length || loc >= 45) break;
			
			YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
			if(JobList.contains("Mabinogi.Added."+SkillName)==true)
			{
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList(ChatColor.DARK_AQUA+"�ִ� ��ų ���� : " + ChatColor.WHITE+JobLevel,"",ChatColor.YELLOW+"[�� Ŭ���� ��ų ���]"), loc, inv);
				loc++;	
			}
		}
		
		if(a.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+""+ItemNumber), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void UseableItemListGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		if(slot == 53)//������
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot==45)//���� ���
				new OPbox_GUI().OPBoxGUI_Main(player, (byte) 2);
			else if(slot == 48)//���� ������
				UseableItemListGUI(player, page-1);
			else if(slot == 49)//�� ������
				ChooseUseableItemTypeGUI(player, ((page*45)+event.getSlot()));
			else if(slot == 50)//���� ������
				UseableItemListGUI(player, page+1);
			else
			{
				int number = ((page*45)+event.getSlot());
				if(event.isLeftClick() == true&&event.isShiftClick()==false)
				{
					player.sendMessage(ChatColor.GREEN+"[SYSTEM] : Ŭ���� �������� ���� �޾ҽ��ϴ�!");
					player.getInventory().addItem(event.getCurrentItem());
				}
				if(event.isLeftClick() == true&&event.isShiftClick()==true)
					NewUseableItemGUI(player, number);
				else if(event.isRightClick()==true&&event.isShiftClick()==true)
				{
				  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
					short Acount =  (short) (ItemList.getConfigurationSection("").getKeys(false).toArray().length-1);

					for(int counter = number;counter <Acount;counter++)
						ItemList.set(counter+"", ItemList.get((counter+1)+""));
					ItemList.removeKey(Acount+"");
					ItemList.saveConfig();
					UseableItemListGUI(player, page);
				}
			}
		}
	}
	
	public void ChooseUseableItemTypeGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 8)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)
				UseableItemListGUI(player, 0);
			else
			{
				String Type = "";
				String Lore = "";
				String DisplayName = "";
				short ID = 267;
				short Data = 0;
				
				if(slot == 2)
				{
					Type = ChatColor.WHITE + "[��ȯ��]";
					Lore = ChatColor.WHITE+"��ü�� �ս� ���� ������ ������%enter%"+ChatColor.WHITE+"������ �̵��� �� �ִ� �ź���%enter%"+ChatColor.WHITE+"��ȯ �ֹ����̴�.";
					DisplayName = ChatColor.WHITE+"0�� ��ȯ �ֹ���";
					ID = 340;
				}
				else if(slot == 3)
				{
					Type = ChatColor.GOLD + "[�ֹ���]";
					Lore = ChatColor.WHITE+"���� ��ų ����Ʈ��%enter%"+ChatColor.WHITE+"5��ŭ ��½��� �ش�.";
					DisplayName =ChatColor.WHITE+ "��ų ����Ʈ 5 ���� �ֹ���";
					ID = 339;
				}
				else if(slot == 4)
				{
					Type = ChatColor.DARK_PURPLE + "[��ų��]";
					Lore = ChatColor.WHITE+"���� �ƹ��͵� �������� ����%enter%"+ChatColor.WHITE+"�� ������ ��ų ���̴�.%enter% %enter%"+ChatColor.WHITE+"(�ƹ��͵� ���� �� ���� �� ����.)";
					DisplayName = ChatColor.WHITE+"�� ��ų ��";
					ID = 403;
				}
				else if(slot == 5)
				{
					Type = ChatColor.LIGHT_PURPLE + "[�Һ�]";
					Lore = ChatColor.WHITE+"�� ���Կ� ����, ���޽�%enter%"+ChatColor.WHITE+"����� ���, ��������%enter%"+ChatColor.WHITE+"10 ġ���� �ִ� �����̴�.";
					DisplayName = ChatColor.WHITE+"�û��� ����";
					ID = 373;
					Data = 8261;
				}
				else if(slot == 6)
				{
					Type = ChatColor.BLUE + "[��]";
					Lore = ChatColor.WHITE+"������ ����� ���̴�.%enter%"+ChatColor.WHITE+"������ �뷱���� �÷��ִ� �� �ϴ�.";
					DisplayName =ChatColor.WHITE+ "��� ��";
					ID = 351;
					Data = 10;
				}

			  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager UseableItemList = YC.getNewConfig("Item/Consume.yml");
				
				int ItemCounter = UseableItemList.getConfigurationSection("").getKeys(false).size();
				UseableItemList.set(ItemCounter+".ShowType",ChatColor.WHITE+"[���]");
				UseableItemList.set(ItemCounter+".ID",ID);
				UseableItemList.set(ItemCounter+".Data",Data);
				UseableItemList.set(ItemCounter+".DisplayName",DisplayName);
				UseableItemList.set(ItemCounter+".Lore",Lore);
				UseableItemList.set(ItemCounter+".Type",Type);
				UseableItemList.set(ItemCounter+".Grade",ChatColor.WHITE+"[�Ϲ�]");
				
				if(slot == 2)
				{
					UseableItemList.set(ItemCounter+".World",player.getLocation().getWorld().getName().toString());
					UseableItemList.set(ItemCounter+".X",0);
					UseableItemList.set(ItemCounter+".Y",0);
					UseableItemList.set(ItemCounter+".Z",0);
					UseableItemList.set(ItemCounter+".Pitch",0);
					UseableItemList.set(ItemCounter+".Yaw",0);
				}
				else if(slot ==3)
				{
					UseableItemList.set(ItemCounter+".DEF",0);
					UseableItemList.set(ItemCounter+".Protect",0);
					UseableItemList.set(ItemCounter+".MaDEF",0);
					UseableItemList.set(ItemCounter+".MaProtect",0);
					UseableItemList.set(ItemCounter+".STR",0);
					UseableItemList.set(ItemCounter+".DEX",0);
					UseableItemList.set(ItemCounter+".INT",0);
					UseableItemList.set(ItemCounter+".WILL",0);
					UseableItemList.set(ItemCounter+".LUK",0);
					UseableItemList.set(ItemCounter+".Balance",0);
					UseableItemList.set(ItemCounter+".Critical",0);
					UseableItemList.set(ItemCounter+".HP",0);
					UseableItemList.set(ItemCounter+".MP",0);
					UseableItemList.set(ItemCounter+".SkillPoint",5);
					UseableItemList.set(ItemCounter+".StatPoint",0);
				}
				else if(slot ==4)
					UseableItemList.set(ItemCounter+".Skill","null");
				else if(slot ==5)
				{
					UseableItemList.set(ItemCounter+".HP",10);
					UseableItemList.set(ItemCounter+".MP",0);
					UseableItemList.set(ItemCounter+".Saturation",0);
					UseableItemList.set(ItemCounter+".Rebirth",false);
				}
				else if(slot ==6)
				{
					UseableItemList.set(ItemCounter+".MinDamage",0);
					UseableItemList.set(ItemCounter+".MaxDamage",0);
					UseableItemList.set(ItemCounter+".MinMaDamage",0);
					UseableItemList.set(ItemCounter+".MaxMaDamage",0);
					UseableItemList.set(ItemCounter+".DEF",0);
					UseableItemList.set(ItemCounter+".Protect",0);
					UseableItemList.set(ItemCounter+".MaDEF",0);
					UseableItemList.set(ItemCounter+".MaProtect",0);
					UseableItemList.set(ItemCounter+".Durability",0);
					UseableItemList.set(ItemCounter+".MaxDurability",0);
					UseableItemList.set(ItemCounter+".STR",0);
					UseableItemList.set(ItemCounter+".DEX",0);
					UseableItemList.set(ItemCounter+".INT",0);
					UseableItemList.set(ItemCounter+".WILL",0);
					UseableItemList.set(ItemCounter+".LUK",0);
					UseableItemList.set(ItemCounter+".Balance",10);
					UseableItemList.set(ItemCounter+".Critical",0);
					UseableItemList.set(ItemCounter+".HP",0);
					UseableItemList.set(ItemCounter+".MP",0);
				}
				UseableItemList.saveConfig();
				NewUseableItemGUI(player,ItemCounter);
			}
		}
	}

	public void NewUseableItemGUIclick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		String IconName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
		
		int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		if(IconName.compareTo("[        ��ų        ]")==0)
		{
		  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager Config = YC.getNewConfig("config.yml");
			
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true)
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				SelectSkillGUI(player, (short) 0, itemnumber);
			}
			else
			{
				s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage(ChatColor.RED+ "[��ų �� ����] : ���� ���� �ý����� "+ChatColor.YELLOW+"'������'"+ChatColor.RED+"�� �ƴմϴ�!");
			}
		}
		else if(IconName.compareTo("[    ��ġ ����    ]")==0)
		{
		  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			ItemList.set(itemnumber+".World", player.getLocation().getWorld().getName().toString());
			ItemList.set(itemnumber+".X", player.getLocation().getX());
			ItemList.set(itemnumber+".Y", player.getLocation().getY());
			ItemList.set(itemnumber+".Z", player.getLocation().getZ());
			ItemList.set(itemnumber+".Pitch", player.getLocation().getPitch());
			ItemList.set(itemnumber+".Yaw", player.getLocation().getYaw());
			ItemList.saveConfig();
			NewUseableItemGUI(player, itemnumber);
		}
		else if(IconName.compareTo("[        ȯ��        ]")==0)
		{
		  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(ItemList.getBoolean(itemnumber+".Rebirth") == false)
				ItemList.set(itemnumber+".Rebirth", true);
			else
				ItemList.set(itemnumber+".Rebirth", false);
			ItemList.saveConfig();
			NewUseableItemGUI(player, itemnumber);
		}
		else if(IconName.compareTo("[    ���� ����    ]")==0)
		{
		  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(ItemList.getString(itemnumber+".ShowType").contains("[���]"))
				ItemList.set(itemnumber+".ShowType",ChatColor.YELLOW+"[�÷�]");
			else if(ItemList.getString(itemnumber+".ShowType").contains("[�÷�]"))
				ItemList.set(itemnumber+".ShowType",ChatColor.LIGHT_PURPLE+"[��ȣ]");
			else if(ItemList.getString(itemnumber+".ShowType").contains("[��ȣ]"))
				ItemList.set(itemnumber+".ShowType",ChatColor.BLUE+"[�з�]");
			else if(ItemList.getString(itemnumber+".ShowType").contains("[�з�]"))
				ItemList.set(itemnumber+".ShowType",ChatColor.WHITE+"[���]");
			ItemList.saveConfig();
			NewUseableItemGUI(player, itemnumber);
		}
		else if(IconName.compareTo("[    Ÿ�� ����    ]")==0)
			s.SP(player, Sound.BLOCK_ANVIL_LAND, 0.8F, 1.8F);
		else if(IconName.compareTo("[    ��� ����    ]")==0)
		{
		  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(ItemList.getString(itemnumber+".Grade").contains("[�Ϲ�]"))
				ItemList.set(itemnumber+".Grade",ChatColor.GREEN+"[���]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[���]"))
				ItemList.set(itemnumber+".Grade",ChatColor.BLUE+"[����]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[����]"))
				ItemList.set(itemnumber+".Grade",ChatColor.YELLOW+"[����]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[����]"))
				ItemList.set(itemnumber+".Grade",ChatColor.DARK_PURPLE+"[����]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[����]"))
				ItemList.set(itemnumber+".Grade",ChatColor.GOLD+"[����]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[����]"))
				ItemList.set(itemnumber+".Grade",ChatColor.DARK_RED+""+ChatColor.BOLD+"["+ChatColor.GOLD+""+ChatColor.BOLD+"��"+ChatColor.DARK_GREEN+""+ChatColor.BOLD+"��"+ChatColor.DARK_BLUE+""+ChatColor.BOLD+"]");
			else if(ItemList.getString(itemnumber+".Grade").contains("��"))
				ItemList.set(itemnumber+".Grade",ChatColor.GRAY+"[�ϱ�]");
			else if(ItemList.getString(itemnumber+".Grade").contains("[�ϱ�]"))
				ItemList.set(itemnumber+".Grade",ChatColor.WHITE+"[�Ϲ�]");
			ItemList.saveConfig();
			NewUseableItemGUI(player, itemnumber);
		}
		else if(IconName.compareTo("���� ���")==0)
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			UseableItemListGUI(player, 0);
		}
		else if(IconName.compareTo("�ݱ�")==0)
		{
				s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
		}
		else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
		{
			UserData_Object u = new UserData_Object();
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "UseableItem");
			u.setInt(player, (byte)3, itemnumber);
			u.setInt(player, (byte)4, -1);
			player.closeInventory();
			if(IconName.compareTo("[       ������       ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : ȸ���� �������� �Է��� �ּ���!");
				u.setString(player, (byte)1, "Saturation");
			}
			else if(IconName.compareTo("[       ������       ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : ȸ���� �������� �Է��� �ּ���!");
				u.setString(player, (byte)1, "HP");
				u.setInt(player, (byte)4, -8);
			}
			else if(IconName.compareTo("[        ����        ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : ȸ���� ������ �Է��� �ּ���!");
				u.setString(player, (byte)1, "MP");
				u.setInt(player, (byte)4, -8);
			}
			else if(IconName.compareTo("[     ��ų ����Ʈ     ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �ְ��� �ϴ� ��ų ����Ʈ�� ���� �Է��� �ּ���!");
				u.setString(player, (byte)1, "SkillPoint");
			}
			else if(IconName.compareTo("[     ���� ����Ʈ     ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �ְ��� �ϴ� ���� ����Ʈ�� ���� �Է��� �ּ���!");
				u.setString(player, (byte)1, "StatPoint");
			}
			else if(IconName.compareTo("[        ID        ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ID ���� �Է��� �ּ���!");
				u.setString(player, (byte)1, "ID");
			}
			else if(IconName.compareTo("[       DATA       ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� DATA ���� �Է��� �ּ���!");
				u.setString(player, (byte)1, "Data");
			}
			else if(IconName.compareTo("[       �����       ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� �ּ� "+Main_ServerOption.Damage+"�� �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MinDamage");
			}
			else if(IconName.compareTo("[     ���� �����     ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� �ּ� "+Main_ServerOption.MagicDamage+"�� �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MinMaDamage");
			}
			else if(IconName.compareTo("[        ���        ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ������ �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "DEF");
			}
			else if(IconName.compareTo("[        ��ȣ        ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ��ȣ�� �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "Protect");
			}
			else if(IconName.compareTo("[      ���� ���      ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���� ������ �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MaDEF");
			}
			else if(IconName.compareTo("[      ���� ��ȣ      ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ���� ��ȣ�� �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MaProtect");
			}
			else if(IconName.compareTo("[        ����        ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ������ ���ʽ��� �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(-127 ~ 127)");
				u.setString(player, (byte)1, "HP");
			}
			else if(IconName.compareTo("[       ������       ]")==0)
			{
				player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� �ִ� �������� �Է��� �ּ���!");
				player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
				u.setString(player, (byte)1, "MaxDurability");
			}
			else
			{
				if(IconName.compareTo("[    �̸� ����    ]")==0)
				{
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� �̸��� �Է��� �ּ���!");
					u.setString(player, (byte)1, "DisplayName");
				}
				else if(IconName.compareTo("[    ���� ����    ]")==0)
				{
					player.sendMessage(ChatColor.DARK_AQUA+"[������] : �������� ������ �Է��� �ּ���!");
					player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
					u.setString(player, (byte)1, "Lore");
				}
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			}
			
		}
	}
	
	public void SelectSkillGUIClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 53)//������
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//���� ���
				NewUseableItemGUI(player, itemnumber);
			else if(slot == 48)//���� ������
				SelectSkillGUI(player, (short) (page-1), itemnumber);
			else if(slot == 50)//���� ������
				SelectSkillGUI(player, (short) (page-1), itemnumber);
			else
			{
			  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
				ItemList.set(itemnumber+".Skill", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				ItemList.saveConfig();
				NewUseableItemGUI(player, itemnumber);
			}
		}
	}
	
	
	public String[] LoreCreater(int ItemNumber)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager ItemList = YC.getNewConfig("Item/Consume.yml");
		String lore = "";
		String Type = ChatColor.stripColor(ItemList.getString(ItemNumber+".ShowType"));

		lore = lore+ItemList.getString(ItemNumber+".Type");
		for(short count = 0; count<20-ItemList.getString(ItemNumber+".Type").length();count++)
			lore = lore+" ";
		lore = lore+ItemList.getString(ItemNumber+".Grade")+"%enter% %enter%";
		
		switch(Type)
		{
		case "[�з�]":
		{
			switch(ChatColor.stripColor(ItemList.getString(ItemNumber+".Type")))
			{
			case "[��ȯ��]":
				lore = lore+ChatColor.BLUE + " �� ���� : " +ChatColor.WHITE + ItemList.getString(ItemNumber+".World")+"%enter%";
				lore = lore+ChatColor.BLUE + " �� X ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".X")+"%enter%";
				lore = lore+ChatColor.BLUE + " �� Y ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Y")+"%enter%";
				lore = lore+ChatColor.BLUE + " �� Z ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Z")+"%enter%";
				break;
			case "[�ֹ���]":
				if(ItemList.getInt(ItemNumber+".StatPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ����Ʈ : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".StatPoint")<0)
					lore = lore+ChatColor.RED + " - ���� ����Ʈ : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".SkillPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��ų ����Ʈ : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".SkillPoint")<0)
					lore = lore+ChatColor.RED + " - ��ų ����Ʈ : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " - ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " - ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " - ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " - ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " - �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " - ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " - ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " - ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
				break;
			case "[��ų��]":
				if(ItemList.getString(ItemNumber+".Skill").compareTo("null")==0)
					lore = lore+"%enter%"+ChatColor.RED + " [�ƹ��͵� ���� �� å]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [�� Ŭ���� �Ʒ� ��ų ȹ��]%enter%"+ChatColor.DARK_AQUA+" + "+ChatColor.WHITE+ItemList.getString(ItemNumber+".Skill")+"%enter%";
				break;
			case "[�Һ�]":
				if(ItemList.getInt(ItemNumber+".HP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".HP") < 0)
					lore = lore+ChatColor.RED + " - ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MP") < 0)
					lore = lore+ChatColor.RED + " - ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Saturation") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + ������ : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Saturation") < 0)
					lore = lore+ChatColor.RED + " - ������ : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				if(ItemList.getBoolean(ItemNumber+".Rebirth") == true)
					lore = lore+ChatColor.GOLD +""+ChatColor.BOLD+ " + ȯ��%enter%";
				break;
			case "[��]":
				if(ItemList.getInt(ItemNumber+".MinDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ּ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinDamage")<0)
					lore = lore+ChatColor.RED + " - �ּ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ִ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDamage")<0)
					lore = lore+ChatColor.RED + " - �ִ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MinMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ּ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinMaDamage")<0)
					lore = lore+ChatColor.RED + " - �ּ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ִ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxMaDamage")<0)
					lore = lore+ChatColor.RED + " - �ִ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";

				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " - ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " - ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " - ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " - ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " - �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " - ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";

				if(ItemList.getInt(ItemNumber+".Durability")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ������ ���� : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Durability")<0)
					lore = lore+ChatColor.RED + " - ���� ������ ���� : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDurability")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ִ� ������ ���� : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDurability")<0)
					lore = lore+ChatColor.RED + " - �ִ� ������ ���� : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";


				if(ItemList.getInt(ItemNumber+".HP")!=0||ItemList.getInt(ItemNumber+".MP")!=0||
				ItemList.getInt(ItemNumber+".STR")!=0||ItemList.getInt(ItemNumber+".DEX")!=0||
				ItemList.getInt(ItemNumber+".INT")!=0||ItemList.getInt(ItemNumber+".WILL")!=0||
				ItemList.getInt(ItemNumber+".LUK")!=0)
				{
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " - ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " - ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") > 0)
						lore = lore+ChatColor.DARK_PURPLE + " + ���� : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") < 0)
						lore = lore+ChatColor.RED + " - ���� : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			}

			lore = lore+"%enter%"+ChatColor.GOLD+"___--------------------___%enter%"+ChatColor.GOLD+"       [������ ����]";
			lore = lore+"%enter%"+ ItemList.getString(ItemNumber+".Lore")+"%enter%";
			lore = lore+ChatColor.GOLD+"---____________________---%enter%";
		}
		break;
		case "[��ȣ]":
		{
			switch(ChatColor.stripColor(ItemList.getString(ItemNumber+".Type")))
			{
			case "[��ȯ��]":
				lore = lore+ChatColor.BLUE + " �� ���� : " +ChatColor.WHITE + ItemList.getString(ItemNumber+".World")+"%enter%";
				lore = lore+ChatColor.BLUE + " �� X ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".X")+"%enter%";
				lore = lore+ChatColor.BLUE + " �� Y ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Y")+"%enter%";
				lore = lore+ChatColor.BLUE + " �� Z ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Z")+"%enter%";
				break;
			case "[�ֹ���]":
				if(ItemList.getInt(ItemNumber+".StatPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ����Ʈ : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".StatPoint")<0)
					lore = lore+ChatColor.RED + " - ���� ����Ʈ : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".SkillPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��ų ����Ʈ : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".SkillPoint")<0)
					lore = lore+ChatColor.RED + " - ��ų ����Ʈ : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " - ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " - ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " - ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " - ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " - �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " - ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " - ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " - ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
				break;
			case "[��ų��]":
				if(ItemList.getString(ItemNumber+".Skill").compareTo("null")==0)
					lore = lore+"%enter%"+ChatColor.RED + " [�ƹ��͵� ���� �� å]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [�� Ŭ���� �Ʒ� ��ų ȹ��]%enter%"+ChatColor.DARK_AQUA+" + "+ChatColor.WHITE+ItemList.getString(ItemNumber+".Skill")+"%enter%";
				break;
			case "[�Һ�]":
				if(ItemList.getInt(ItemNumber+".HP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".HP") < 0)
					lore = lore+ChatColor.RED + " - ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MP") < 0)
					lore = lore+ChatColor.RED + " - ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Saturation") > 0)
					lore = lore+ChatColor.DARK_AQUA + " + ������ : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Saturation") < 0)
					lore = lore+ChatColor.RED + " - ������ : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				if(ItemList.getBoolean(ItemNumber+".Rebirth") == true)
					lore = lore+ChatColor.GOLD +""+ChatColor.BOLD+ " + ȯ��%enter%";
				break;
			case "[��]":
				if(ItemList.getInt(ItemNumber+".MinDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ּ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinDamage")<0)
					lore = lore+ChatColor.RED + " - �ּ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ִ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDamage")<0)
					lore = lore+ChatColor.RED + " - �ִ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MinMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ּ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinMaDamage")<0)
					lore = lore+ChatColor.RED + " - �ּ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ִ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxMaDamage")<0)
					lore = lore+ChatColor.RED + " - �ִ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";

				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " - ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " - ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " - ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " - ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " - �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " - ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Durability")>0)
					lore = lore+ChatColor.DARK_AQUA + " + ���� ������ ���� : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Durability")<0)
					lore = lore+ChatColor.RED + " - ���� ������ ���� : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDurability")>0)
					lore = lore+ChatColor.DARK_AQUA + " + �ִ� ������ ���� : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDurability")<0)
					lore = lore+ChatColor.RED + " - �ִ� ������ ���� : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";

				if(ItemList.getInt(ItemNumber+".HP")!=0||ItemList.getInt(ItemNumber+".MP")!=0||
				ItemList.getInt(ItemNumber+".STR")!=0||ItemList.getInt(ItemNumber+".DEX")!=0||
				ItemList.getInt(ItemNumber+".INT")!=0||ItemList.getInt(ItemNumber+".WILL")!=0||
				ItemList.getInt(ItemNumber+".LUK")!=0)
				{
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " - ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " - ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " + "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " - "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") > 0)
						lore = lore+ChatColor.DARK_PURPLE + " + ���� : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") < 0)
						lore = lore+ChatColor.RED + " - ���� : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
				}
				break;
			}			
			
			lore = lore+"%enter%"+ ItemList.getString(ItemNumber+".Lore")+"%enter%%enter%";

		}
		break;
		case "[�÷�]":
		{
			switch(ChatColor.stripColor(ItemList.getString(ItemNumber+".Type")))
			{
			case "[��ȯ��]":
				lore = lore+ChatColor.BLUE + " ���� : " +ChatColor.WHITE + ItemList.getString(ItemNumber+".World")+"%enter%";
				lore = lore+ChatColor.BLUE + " X ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".X")+"%enter%";
				lore = lore+ChatColor.BLUE + " Y ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Y")+"%enter%";
				lore = lore+ChatColor.BLUE + " Z ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Z")+"%enter%";
				break;
			case "[�ֹ���]":
				if(ItemList.getInt(ItemNumber+".StatPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " ���� ����Ʈ : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".StatPoint")<0)
					lore = lore+ChatColor.RED + " ���� ����Ʈ : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".SkillPoint")>0)
					lore = lore+ChatColor.DARK_AQUA + " ��ų ����Ʈ : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".SkillPoint")<0)
					lore = lore+ChatColor.RED + " ��ų ����Ʈ : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
				break;
			case "[��ų��]":
				if(ItemList.getString(ItemNumber+".Skill").compareTo("null")==0)
					lore = lore+"%enter%"+ChatColor.RED + " [�ƹ��͵� ���� �� å]%enter%";
				else
					lore = lore+"%enter%"+ChatColor.DARK_AQUA  +" [�� Ŭ���� �Ʒ� ��ų ȹ��]%enter%"+ChatColor.DARK_AQUA+" + "+ChatColor.WHITE+ItemList.getString(ItemNumber+".Skill")+"%enter%";
				break;
			case "[�Һ�]":
				if(ItemList.getInt(ItemNumber+".HP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".HP") < 0)
					lore = lore+ChatColor.RED + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MP") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MP") < 0)
					lore = lore+ChatColor.RED + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Saturation") > 0)
					lore = lore+ChatColor.DARK_AQUA + " ������ : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Saturation") < 0)
					lore = lore+ChatColor.RED + " ������ : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
				if(ItemList.getBoolean(ItemNumber+".Rebirth") == true)
					lore = lore+ChatColor.GOLD +""+ChatColor.BOLD+ " + ȯ��%enter%";
				break;
			case "[��]":
				if(ItemList.getInt(ItemNumber+".MinDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " �ּ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinDamage")<0)
					lore = lore+ChatColor.RED + " �ּ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " �ִ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDamage")<0)
					lore = lore+ChatColor.RED + " �ִ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MinMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " �ּ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MinMaDamage")<0)
					lore = lore+ChatColor.RED + " �ּ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxMaDamage")>0)
					lore = lore+ChatColor.DARK_AQUA + " �ִ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxMaDamage")<0)
					lore = lore+ChatColor.RED + " �ִ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";

				if(ItemList.getInt(ItemNumber+".DEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".DEF")<0)
					lore = lore+ChatColor.RED + " ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")>0)
					lore = lore+ChatColor.DARK_AQUA + " ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Protect")<0)
					lore = lore+ChatColor.RED + " ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")>0)
					lore = lore+ChatColor.DARK_AQUA + " ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaDEF")<0)
					lore = lore+ChatColor.RED + " ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")>0)
					lore = lore+ChatColor.DARK_AQUA + " ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaProtect")<0)
					lore = lore+ChatColor.RED + " ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")>0)
					lore = lore+ChatColor.DARK_AQUA + " �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Balance")<0)
					lore = lore+ChatColor.RED + " �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")>0)
					lore = lore+ChatColor.DARK_AQUA + " ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
				if(ItemList.getInt(ItemNumber+".Critical")<0)
					lore = lore+ChatColor.RED + " ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";

				if(ItemList.getInt(ItemNumber+".Durability")>0)
					lore = lore+ChatColor.DARK_AQUA + " ���� ������ ���� : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".Durability")<0)
					lore = lore+ChatColor.RED + " ���� ������ ���� : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
				if(ItemList.getInt(ItemNumber+".MaxDurability")>0)
					lore = lore+ChatColor.DARK_AQUA + " �ִ� ������ ���� : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
				else if(ItemList.getInt(ItemNumber+".MaxDurability")<0)
					lore = lore+ChatColor.RED + " �ִ� ������ ���� : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";


					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".STR") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".STR") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEX") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEX") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
					if(ItemList.getInt(ItemNumber+".INT") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".INT") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
					if(ItemList.getInt(ItemNumber+".WILL") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".WILL") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
					if(ItemList.getInt(ItemNumber+".LUK") > 0)
						lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".LUK") < 0)
						lore = lore+ChatColor.RED + " "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") > 0)
						lore = lore+ChatColor.DARK_PURPLE + " ���� : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxUpgrade") < 0)
						lore = lore+ChatColor.RED + " ���� : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
				break;
			}
			
			lore = lore+"%enter%"+ ItemList.getString(ItemNumber+".Lore")+"%enter%%enter%";


		}
		break;
		
			case "[���]":
			{
				switch(ChatColor.stripColor(ItemList.getString(ItemNumber+".Type")))
				{
				case "[��ȯ��]":
					lore = lore+ChatColor.WHITE + " ���� : " +ChatColor.WHITE + ItemList.getString(ItemNumber+".World")+"%enter%";
					lore = lore+ChatColor.WHITE + " X ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".X")+"%enter%";
					lore = lore+ChatColor.WHITE + " Y ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Y")+"%enter%";
					lore = lore+ChatColor.WHITE + " Z ��ǥ : " +ChatColor.WHITE + ItemList.getInt(ItemNumber+".Z")+"%enter%";
					break;
				case "[�ֹ���]":
					if(ItemList.getInt(ItemNumber+".StatPoint")>0)
						lore = lore+ChatColor.DARK_AQUA + " ���� ����Ʈ : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".StatPoint")<0)
						lore = lore+ChatColor.RED + " ���� ����Ʈ : " + ItemList.getInt(ItemNumber+".StatPoint")+"%enter%";
					if(ItemList.getInt(ItemNumber+".SkillPoint")>0)
						lore = lore+ChatColor.DARK_AQUA + " ��ų ����Ʈ : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".SkillPoint")<0)
						lore = lore+ChatColor.RED + " ��ų ����Ʈ : " + ItemList.getInt(ItemNumber+".SkillPoint")+"%enter%";
					if(ItemList.getInt(ItemNumber+".DEF")>0)
						lore = lore+ChatColor.DARK_AQUA + " ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEF")<0)
						lore = lore+ChatColor.RED + " ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Protect")>0)
						lore = lore+ChatColor.DARK_AQUA + " ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Protect")<0)
						lore = lore+ChatColor.RED + " ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaDEF")>0)
						lore = lore+ChatColor.DARK_AQUA + " ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaDEF")<0)
						lore = lore+ChatColor.RED + " ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaProtect")>0)
						lore = lore+ChatColor.DARK_AQUA + " ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaProtect")<0)
						lore = lore+ChatColor.RED + " ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Balance")>0)
						lore = lore+ChatColor.DARK_AQUA + " �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Balance")<0)
						lore = lore+ChatColor.RED + " �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Critical")>0)
						lore = lore+ChatColor.DARK_AQUA + " ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Critical")<0)
						lore = lore+ChatColor.RED + " ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
					
						if(ItemList.getInt(ItemNumber+".HP") > 0)
							lore = lore+ChatColor.DARK_AQUA + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".HP") < 0)
							lore = lore+ChatColor.RED + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
						if(ItemList.getInt(ItemNumber+".MP") > 0)
							lore = lore+ChatColor.DARK_AQUA + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".MP") < 0)
							lore = lore+ChatColor.RED + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
						if(ItemList.getInt(ItemNumber+".STR") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".STR") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
						if(ItemList.getInt(ItemNumber+".DEX") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".DEX") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
						if(ItemList.getInt(ItemNumber+".INT") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".INT") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
						if(ItemList.getInt(ItemNumber+".WILL") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".WILL") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
						if(ItemList.getInt(ItemNumber+".LUK") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".LUK") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
					break;
				case "[��ų��]":
					if(ItemList.getString(ItemNumber+".Skill").compareTo("null")==0)
						lore = lore+"%enter%"+ChatColor.WHITE + " [�ƹ��͵� ���� �� å]%enter%";
					else
						lore = lore+"%enter%"+ChatColor.WHITE  +" [�� Ŭ���� �Ʒ� ��ų ȹ��]%enter%"+ChatColor.WHITE+" + "+ChatColor.WHITE+ItemList.getString(ItemNumber+".Skill")+"%enter%";
					break;
				case "[�Һ�]":
					if(ItemList.getInt(ItemNumber+".HP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".HP") < 0)
						lore = lore+ChatColor.RED + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MP") > 0)
						lore = lore+ChatColor.DARK_AQUA + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MP") < 0)
						lore = lore+ChatColor.RED + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Saturation") > 0)
						lore = lore+ChatColor.DARK_AQUA + " ������ : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".Saturation") < 0)
						lore = lore+ChatColor.RED + " ������ : " + ItemList.getInt(ItemNumber+".Saturation")+"%enter%";
					if(ItemList.getBoolean(ItemNumber+".Rebirth") == true)
						lore = lore+ChatColor.GOLD +""+ChatColor.BOLD+ " + ȯ��%enter%";
					break;
				case "[��]":
					if(ItemList.getInt(ItemNumber+".MinDamage")>0)
						lore = lore+ChatColor.DARK_AQUA + " �ּ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MinDamage")<0)
						lore = lore+ChatColor.RED + " �ּ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinDamage")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxDamage")>0)
						lore = lore+ChatColor.DARK_AQUA + " �ִ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MaxDamage")<0)
						lore = lore+ChatColor.RED + " �ִ� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxDamage")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MinMaDamage")>0)
						lore = lore+ChatColor.DARK_AQUA + " �ּ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MinMaDamage")<0)
						lore = lore+ChatColor.RED + " �ּ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MinMaDamage")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxMaDamage")>0)
						lore = lore+ChatColor.DARK_AQUA + " �ִ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MaxMaDamage")<0)
						lore = lore+ChatColor.RED + " �ִ� ���� ���ݷ� : " + ItemList.getInt(ItemNumber+".MaxMaDamage")+"%enter%";
					
					
						
					if(ItemList.getInt(ItemNumber+".DEF")>0)
						lore = lore+ChatColor.DARK_AQUA + " ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".DEF")<0)
						lore = lore+ChatColor.RED + " ��� : " + ItemList.getInt(ItemNumber+".DEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Protect")>0)
						lore = lore+ChatColor.DARK_AQUA + " ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Protect")<0)
						lore = lore+ChatColor.RED + " ��ȣ : " + ItemList.getInt(ItemNumber+".Protect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaDEF")>0)
						lore = lore+ChatColor.DARK_AQUA + " ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaDEF")<0)
						lore = lore+ChatColor.RED + " ���� ��� : " + ItemList.getInt(ItemNumber+".MaDEF")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaProtect")>0)
						lore = lore+ChatColor.DARK_AQUA + " ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaProtect")<0)
						lore = lore+ChatColor.RED + " ���� ��ȣ : " + ItemList.getInt(ItemNumber+".MaProtect")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Balance")>0)
						lore = lore+ChatColor.DARK_AQUA + " �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Balance")<0)
						lore = lore+ChatColor.RED + " �뷱�� : " + ItemList.getInt(ItemNumber+".Balance")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Critical")>0)
						lore = lore+ChatColor.DARK_AQUA + " ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Critical")<0)
						lore = lore+ChatColor.RED + " ũ��Ƽ�� : " + ItemList.getInt(ItemNumber+".Critical")+"%enter%";
					if(ItemList.getInt(ItemNumber+".Durability")>0)
						lore = lore+ChatColor.DARK_AQUA + " ���� ������ ���� : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".Durability")<0)
						lore = lore+ChatColor.RED + " ���� ������ ���� : " + ItemList.getInt(ItemNumber+".Durability")+"%enter%";
					if(ItemList.getInt(ItemNumber+".MaxDurability")>0)
						lore = lore+ChatColor.DARK_AQUA + " �ִ� ������ ���� : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
					else if(ItemList.getInt(ItemNumber+".MaxDurability")<0)
						lore = lore+ChatColor.RED + " �ִ� ������ ���� : " + ItemList.getInt(ItemNumber+".MaxDurability")+"%enter%";
					
						if(ItemList.getInt(ItemNumber+".HP") > 0)
							lore = lore+ChatColor.DARK_AQUA + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".HP") < 0)
							lore = lore+ChatColor.RED + " ������ : " + ItemList.getInt(ItemNumber+".HP")+"%enter%";
						if(ItemList.getInt(ItemNumber+".MP") > 0)
							lore = lore+ChatColor.DARK_AQUA + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".MP") < 0)
							lore = lore+ChatColor.RED + " ���� : " + ItemList.getInt(ItemNumber+".MP")+"%enter%";
						if(ItemList.getInt(ItemNumber+".STR") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".STR") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.STR+" : " + ItemList.getInt(ItemNumber+".STR")+"%enter%";
						if(ItemList.getInt(ItemNumber+".DEX") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".DEX") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.DEX+" : " + ItemList.getInt(ItemNumber+".DEX")+"%enter%";
						if(ItemList.getInt(ItemNumber+".INT") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".INT") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.INT+" : " + ItemList.getInt(ItemNumber+".INT")+"%enter%";
						if(ItemList.getInt(ItemNumber+".WILL") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".WILL") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.WILL+" : " + ItemList.getInt(ItemNumber+".WILL")+"%enter%";
						if(ItemList.getInt(ItemNumber+".LUK") > 0)
							lore = lore+ChatColor.DARK_AQUA + " "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
						else if(ItemList.getInt(ItemNumber+".LUK") < 0)
							lore = lore+ChatColor.RED + " "+Main_ServerOption.LUK+" : " + ItemList.getInt(ItemNumber+".LUK")+"%enter%";
						if(ItemList.getInt(ItemNumber+".MaxUpgrade") > 0)
							lore = lore+ChatColor.DARK_PURPLE + " ���� : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
						if(ItemList.getInt(ItemNumber+".MaxUpgrade") < 0)
							lore = lore+ChatColor.DARK_AQUA + " ���� : " +ItemList.getInt(ItemNumber+".MaxUpgrade")+"%enter%";
					break;
				}
				
				lore = lore+"%enter%"+ ItemList.getString(ItemNumber+".Lore")+"%enter%%enter%";

			}
			break;
		}

		String[] scriptA = lore.split("%enter%");
		for(byte counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		return scriptA;
	}
	
}