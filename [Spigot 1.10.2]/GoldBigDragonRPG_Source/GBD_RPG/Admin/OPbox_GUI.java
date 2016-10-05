package GBD_RPG.Admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GBD_RPG.Area.Area_GUI;
import GBD_RPG.CustomItem.CustomItem_GUI;
import GBD_RPG.CustomItem.UseableItem_GUI;
import GBD_RPG.Job.Job_GUI;
import GBD_RPG.Main_Main.Main_ServerOption;
import GBD_RPG.Monster.Monster_GUI;
import GBD_RPG.Quest.Quest_GUI;
import GBD_RPG.Skill.OPboxSkill_GUI;
import GBD_RPG.Structure.Structure_GUI;
import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_GUI;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class OPbox_GUI extends Util_GUI
{
	public void OPBoxGUI_Main (Player player, byte page)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		String UniqueCode = "��0��0��1��0��0��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0������ ���� : "+page+" / 3");
		
		Stack2(ChatColor.BLACK +""+page, 160,11,1,null, 0, inv);
		Stack2(" ", 160,11,1,null, 1, inv);
		Stack2(ChatColor.RED +""+ ChatColor.BOLD + "[���� �ð� ���� -��-]", 160,4,1,Arrays.asList(ChatColor.WHITE +"���� ���� �ð��� ������",ChatColor.WHITE +"���� ��ŵ�ϴ�."), 2, inv);
		Stack2(ChatColor.GRAY +""+ ChatColor.BOLD + "[���� �ð� ���� -��-]", 160,4,1,Arrays.asList(ChatColor.WHITE +"���� ���� �ð��� ������",ChatColor.WHITE +"���� ��ŵ�ϴ�."), 3, inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "[���� ��ġ�� ��������]", 160,4,1,Arrays.asList(ChatColor.WHITE +"���� ��ġ�� ���� ��������",ChatColor.WHITE +"���� ��ŵ�ϴ�."), 4, inv);
		Stack2(ChatColor.DARK_AQUA +""+ ChatColor.BOLD + "[���� ���� ���� -����-]", 160,4,1,Arrays.asList(ChatColor.WHITE +"���� ���� ������ ����",ChatColor.WHITE +"���� ��ŵ�ϴ�."), 5, inv);
		Stack2(ChatColor.GRAY +""+ ChatColor.BOLD + "[���� ���� ���� -�帲-]", 160,4,1,Arrays.asList(ChatColor.WHITE +"���� ���� ������ �帮��",ChatColor.WHITE +"���� ��ŵ�ϴ�."), 6, inv);
		Stack2(" ", 160,11,1,null, 7, inv);
		Stack2(" ", 160,11,1,null, 8, inv);
		Stack2(" ", 160,11,1,null, 9, inv);
		Stack2(" ", 160,11,1,null, 18, inv);
		Stack2(" ", 160,11,1,null, 17, inv);
		Stack2(" ", 160,11,1,null, 26, inv);
		Stack2(" ", 160,11,1,null, 27, inv);
		Stack2(" ", 160,11,1,null, 36, inv);
		Stack2(" ", 160,11,1,null, 35, inv);
		Stack2(" ", 160,11,1,null, 36, inv);
		Stack2(" ", 160,11,1,null, 37, inv);
		Stack2(" ", 160,11,1,null, 38, inv);
		Stack2(" ", 160,11,1,null, 39, inv);
		Stack2(" ", 160,11,1,null, 40, inv);
		Stack2(" ", 160,11,1,null, 41, inv);
		Stack2(" ", 160,11,1,null, 42, inv);
		Stack2(" ", 160,11,1,null, 43, inv);
		Stack2(" ", 160,11,1,null, 44, inv);

		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�÷����� ���̵�", 340,0,1,Arrays.asList(ChatColor.YELLOW + "GoldBigDragonAdvanced",ChatColor.WHITE +"�÷����ο� ����",ChatColor.WHITE +"������ ���ϴ�."), 22, inv);
		
		switch(page)
		{
			case 1:
				ItemStackStack(getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+"GoldBigDragonRPG", 1, Arrays.asList("",ChatColor.YELLOW+"[����]",ChatColor.WHITE+""+ChatColor.BOLD+Main_ServerOption.serverVersion,"",ChatColor.YELLOW+"[��ġ]",ChatColor.WHITE+""+ChatColor.BOLD+Main_ServerOption.serverUpdate), "GoldBigDragon"), 10, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� ����", 137,0,1,Arrays.asList(ChatColor.GRAY +"������ ���� �������� ������ �մϴ�."), 12, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "����", 397,4,1,Arrays.asList(ChatColor.GRAY +"Ŀ���� ���͸� �����ϰų�",ChatColor.GRAY +"���� ���׸� ����ϴ�."), 14, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�̺�Ʈ", 401,0,1,Arrays.asList(ChatColor.GRAY +"�̺�Ʈ ���ֽ� ���ӵ� ���",ChatColor.GRAY +"�÷��̾�鿡�� �˷�����,",ChatColor.GRAY +"���� �����ϴ� ��� �÷��̾��",ChatColor.GRAY +"�̺�Ʈ ������ �˷��ݴϴ�."), 16, inv);
				if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
				{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ����", 346,0,1,Arrays.asList(ChatColor.WHITE + "[������]",ChatColor.GRAY+"ȯ���� ���������� �����ϸ�,",ChatColor.GRAY+"������ ���Ƿ� �ø� �� �����ϴ�.",ChatColor.RED+"�÷��̾��� ���� �����Ͱ� �̾����Ƿ�",ChatColor.RED+"����� ���ǰ� �ʿ��մϴ�."), 28, inv);}
				else
				{{Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ����", 40,0,1,Arrays.asList(ChatColor.GOLD + "[������ ���丮]",ChatColor.GRAY+"������ ���Ƿ� �ø� �� ������,",ChatColor.GRAY+"���������� ȯ���� �������� �ʽ��ϴ�.",ChatColor.RED+"�÷��̾��� ���� �����Ͱ� �̾����Ƿ�",ChatColor.RED+"����� ���ǰ� �ʿ��մϴ�."), 28, inv);}	}
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "Ŀ���� ������", 389,0,1,Arrays.asList(ChatColor.WHITE + "Ŀ���� �������� �����ϰų�",ChatColor.WHITE+"Ŭ���Ͽ� ���� �޽��ϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� ������ �ޱ�]",ChatColor.YELLOW+"[Shift + �� Ŭ���� ������ ����]",ChatColor.RED+"[Shift + �� Ŭ���� ������ ����]"), 30, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�׺���̼�", 358,120,1,Arrays.asList(ChatColor.WHITE + "��ã�� �ý����� �����մϴ�."), 32, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "����Ʈ", 403,0,1,Arrays.asList(ChatColor.WHITE + "����Ʈ�� ���� ����ų�",ChatColor.WHITE+"�����ϰų� �����մϴ�."), 34, inv);
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
				break;

			case 2:
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "��ų", 403,0,1,Arrays.asList(ChatColor.GRAY +"���� Ȥ�� ī�װ����� ��� ������",ChatColor.GRAY+"��ų���� ����մϴ�."), 10, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "ī�װ��� �� ����", 397,3,1,Arrays.asList(ChatColor.GRAY +"��ų�� ������ ���� ������ �մϴ�."), 12, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "�Һ� ������", 260,0,1,Arrays.asList(ChatColor.GRAY +"�� Ŭ�� Ȥ�� ����Ű�� ����",ChatColor.GRAY+"��� ������ �������� �����մϴ�."), 14, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "����", 395,0,1,Arrays.asList(ChatColor.GRAY +"���� ������ ���Ͽ� ��������",ChatColor.GRAY+"Ư���� �ɼ��� ������ �� �ֽ��ϴ�."), 16, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "������", 145,2,1,Arrays.asList(ChatColor.GRAY +"������ ���� ����� ���� ����ų�",ChatColor.GRAY+"������ �� �ֽ��ϴ�."), 28, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "�ʽ���", 54,0,1,Arrays.asList(ChatColor.GRAY +"������ ó�� ���� �÷��̾",ChatColor.GRAY+"���Ͽ� ������ �մϴ�."), 30, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� ����", 2,0,1,Arrays.asList(ChatColor.GRAY +"���ο� ���带 �����մϴ�."), 32, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "����", 345,0,1,Arrays.asList(ChatColor.GRAY +"���� ������ �����ϰų�, �̵��մϴ�."), 34, inv);

				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
				break;
			case 3:
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "����", 266,0,1,Arrays.asList(ChatColor.GRAY +"���� ���� ����� ���ϴ�."), 10, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "��ɼ� ��ü", 130,0,1,Arrays.asList(ChatColor.GRAY +"���� ����� ���� ��ü����",ChatColor.GRAY +"��ġ�ϰų� �����մϴ�."), 12, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "����", 52,0,1,Arrays.asList(ChatColor.GRAY +"�ν� �Ͻ� ������ ���� ������ �մϴ�."), 14, inv);
				Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "����", 58,0,1,Arrays.asList(ChatColor.GRAY +"���ۿ� ���� ������ �մϴ�."), 16, inv);
				
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
				break;
			
		}

		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "�۾� ������ â�� �ݽ��ϴ�."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_Setting(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		String UniqueCode = "��0��0��1��0��1��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0������ �ɼ�");
		
		Stack2(" ", 160,11,1,null, 0, inv);
		Stack2(" ", 160,11,1,null, 1, inv);
		Stack2(" ", 160,11,1,null, 2, inv);
		Stack2(" ", 160,11,1,null, 3, inv);
		Stack2(" ", 160,11,1,null, 4, inv);
		Stack2(" ", 160,11,1,null, 5, inv);
		Stack2(" ", 160,11,1,null, 6, inv);
		Stack2(" ", 160,11,1,null, 7, inv);
		Stack2(" ", 160,11,1,null, 8, inv);
		Stack2(" ", 160,11,1,null, 9, inv);
		Stack2(" ", 160,11,1,null, 18, inv);
		Stack2(" ", 160,11,1,null, 17, inv);
		Stack2(" ", 160,11,1,null, 26, inv);
		Stack2(" ", 160,11,1,null, 27, inv);
		Stack2(" ", 160,11,1,null, 36, inv);
		Stack2(" ", 160,11,1,null, 35, inv);
		Stack2(" ", 160,11,1,null, 36, inv);
		Stack2(" ", 160,11,1,null, 37, inv);
		Stack2(" ", 160,11,1,null, 38, inv);
		Stack2(" ", 160,11,1,null, 39, inv);
		Stack2(" ", 160,11,1,null, 40, inv);
		Stack2(" ", 160,11,1,null, 41, inv);
		Stack2(" ", 160,11,1,null, 42, inv);
		Stack2(" ", 160,11,1,null, 43, inv);
		Stack2(" ", 160,11,1,null, 44, inv);

		if(Config.getBoolean("Server.EntitySpawn") == true)
		{Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "��ƼƼ ����", 52,0,1,Arrays.asList(ChatColor.GREEN+"[Ȱ��ȭ]",ChatColor.GRAY +"�ش� �ɼ��� ��Ȱ��ȭ ������ ���",ChatColor.GRAY+"���̻� ��ƼƼ���� ��ȯ���� �ʽ��ϴ�."), 10, inv);}
		else
		{{Stack2(ChatColor.RED +""+ ChatColor.BOLD + "��ƼƼ ����", 166,0,1,Arrays.asList(ChatColor.RED+"[�� Ȱ��ȭ]",ChatColor.GRAY +"�ش� �ɼ��� ��Ȱ��ȭ ������ ���",ChatColor.GRAY+"���̻� ��ƼƼ���� ��ȯ���� �ʽ��ϴ�."), 10, inv);}	}
		
		if(Config.getBoolean("Server.PVP") == true)
		{Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "������ PVP", 276,0,1,Arrays.asList(ChatColor.GREEN+"[Ȱ��ȭ]",ChatColor.GRAY +"���� ������ ������ ��� �˴ϴ�."), 11, inv);}
		else
		{{Stack2(ChatColor.RED +""+ ChatColor.BOLD + "������ PVP", 166,0,1,Arrays.asList(ChatColor.RED+"[�� Ȱ��ȭ]",ChatColor.GRAY +"���� ������ ������ ������ �ʽ��ϴ�."), 11, inv);}	}
		switch(Config.getInt("Server.MonsterSpawnEffect"))
		{
			case 0 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ���� ȿ��", 383,0,1,Arrays.asList(ChatColor.WHITE + "[����]"), 12, inv); break;
			case 1 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ���� ȿ��", 383,66,1,Arrays.asList(ChatColor.BLUE + "[����]",ChatColor.RED + "�� ���� �� ������ ������ �˴ϴ�!"), 12, inv); break;
			case 2 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ���� ȿ��", 383,58,1,Arrays.asList(ChatColor.DARK_PURPLE + "[����]",ChatColor.RED + "�� ���� �� ������ ������ �˴ϴ�!"), 12, inv); break;
			case 3 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ���� ȿ��", 383,50,1,Arrays.asList(ChatColor.GREEN+ "[����]",ChatColor.RED + "�� ���� �� ������ ������ �˴ϴ�!"), 12, inv); break;
			case 4 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ���� ȿ��", 383,61,1,Arrays.asList(ChatColor.DARK_RED + "[ȭ��]",ChatColor.RED + "�� ���� �� ������ ������ �˴ϴ�!"), 12, inv); break;
			case 5 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ���� ȿ��", 383,120,1,Arrays.asList(ChatColor.RED + "[ȭ��]",ChatColor.RED + "�� ���� �� ������ ������ �˴ϴ�!"), 12, inv); break;
			case 6 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ���� ȿ��", 383,90,1,Arrays.asList(ChatColor.LIGHT_PURPLE + "[����]",ChatColor.RED + "�� ���� �� ������ ������ �˴ϴ�!"), 12, inv); break;
			case 7 : Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "���� ���� ȿ��", 50,0,1,Arrays.asList(ChatColor.GOLD + "[���]",ChatColor.RED + "�� ���� �� ������ ������ �˴ϴ�!"), 12, inv); break;
		}

		if(Config.getBoolean("Server.CustomWeaponBreak") == true)
		{Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "Ŀ���� ���� �ı�", 268,50,1,Arrays.asList(ChatColor.GREEN+"[Ȱ��ȭ]",ChatColor.GRAY +"Ŀ���� ������ �������� 0�� �� ���",ChatColor.GRAY+"�Ϲ� ����� ���� �ı��˴ϴ�."), 13, inv);}
		else
		{{Stack2(ChatColor.RED +""+ ChatColor.BOLD + "Ŀ���� ���� �ı�", 166,0,1,Arrays.asList(ChatColor.RED+"[�� Ȱ��ȭ]",ChatColor.GRAY +"Ŀ���� ����� �������� 0�� �Ǿ",ChatColor.GRAY+"�ı����� �ʽ��ϴ�."), 13, inv);}	}

		if(Config.getString("Server.JoinMessage") != null)
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� �޽���", 386,0,1,Arrays.asList(ChatColor.GREEN+"[����]",ChatColor.GRAY +Config.getString("Server.JoinMessage"),"",ChatColor.YELLOW+"[�� Ŭ���� ���� �޽��� ����]"), 14, inv);
		else
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� �޽���", 166,0,1,Arrays.asList(ChatColor.RED+"[����]",ChatColor.GRAY + "���� �޽����� �����ϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� ���� �޽��� ���]"), 14, inv);

		if(Config.getString("Server.QuitMessage") != null)
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� �޽���", 386,0,1,Arrays.asList(ChatColor.GREEN+"[����]",ChatColor.GRAY +Config.getString("Server.QuitMessage"),"",ChatColor.YELLOW+"[�� Ŭ���� ���� �޽��� ����]"), 15, inv);
		else
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� �޽���", 166,0,1,Arrays.asList(ChatColor.RED+"[����]",ChatColor.GRAY + "���� �޽����� �����ϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� ���� �޽��� ���]"), 15, inv);
		Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "��������", 323,0,1,Arrays.asList(ChatColor.GRAY + "������ ���� �ð�����",ChatColor.GRAY+"���������� �˸��ϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� �������� ����]"), 16, inv);

		if(Config.getString("Server.ChatPrefix")==null)
			ItemStackStack(getPlayerSkull(ChatColor.GREEN +""+ ChatColor.BOLD + "ä�� ����", 1, Arrays.asList(ChatColor.GRAY+"ä�� ���¸� �����մϴ�.",ChatColor.GRAY+"��, ���¸� ������ ���",ChatColor.GRAY+"��� �Ϲ� ä���� ��ε�",ChatColor.GRAY+"ĳ��Ʈ �������� ����ǹǷ�",ChatColor.GRAY+"�����ؾ� �մϴ�.","",ChatColor.DARK_AQUA+"[���� ä�� ����]",ChatColor.WHITE+"[   ����   ]","",ChatColor.YELLOW+"[�� Ŭ���� ���λ� ����]",ChatColor.RED+"[�� Ŭ���� ���λ� ����]","",ChatColor.GREEN+"[�ڵ� ����]",ChatColor.WHITE+""+ChatColor.BOLD+"B4TT3RY"), "B4TT3RY__"), 19, inv);
		else
		{
			String Prefix = Config.getString("Server.ChatPrefix");
			Prefix=Prefix.replace("%t%","[�ð�]");
			Prefix=Prefix.replace("%gm%","[���� ���]");
			Prefix=Prefix.replace("%ct%","[ä�� Ÿ��]");
			Prefix=Prefix.replace("%lv%","[����]");
			Prefix=Prefix.replace("%rlv%","[���� ����]");
			Prefix=Prefix.replace("%job%","[����]");
			Prefix=Prefix.replace("%player%","[�г���]");
			Prefix=Prefix.replace("%message%", "[����]");
			ItemStackStack(getPlayerSkull(ChatColor.GREEN +""+ ChatColor.BOLD + "ä�� ����", 1, Arrays.asList(ChatColor.GRAY+"ä�� ���¸� �����մϴ�.",ChatColor.GRAY+"��, ���¸� ������ ���",ChatColor.GRAY+"��� �Ϲ� ä���� ��ε�",ChatColor.GRAY+"ĳ��Ʈ �������� ����ǹǷ�",ChatColor.GRAY+"�����ؾ� �մϴ�.","",ChatColor.DARK_AQUA+"[���� ä�� ����]",ChatColor.WHITE+Prefix,"",ChatColor.YELLOW+"[�� Ŭ���� ���λ� ����]",ChatColor.RED+"[�� Ŭ���� ���λ� ����]","",ChatColor.GREEN+"[�ڵ� ����]",ChatColor.WHITE+""+ChatColor.BOLD+"B4TT3RY"), "B4TT3RY__"), 19, inv);
		}

		if(Config.getBoolean("Server.CustomBlockPlace") == true)
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "Ŀ���� ���� ��ġ", 1,0,1,Arrays.asList(ChatColor.GREEN+"[����]",ChatColor.GRAY +"�����ۿ� ������ �پ� �ְų�",ChatColor.GRAY+"�̸��� ����� ��������",ChatColor.GRAY+"��ġ�Ǵ� ���� ���� �ʽ��ϴ�."), 20, inv);
		else
			Stack2(ChatColor.RED +""+ ChatColor.BOLD + "Ŀ���� ���� ��ġ", 166,0,1,Arrays.asList(ChatColor.RED+"[�Ұ���]",ChatColor.GRAY +"�����ۿ� ������ �پ� �ְų�",ChatColor.GRAY+"�̸��� ����� ��������",ChatColor.GRAY+"��ġ�Ǵ� ���� �����ϴ�."), 20, inv);

		Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� �̸� ����", 340,0,1,Arrays.asList(ChatColor.GRAY + "������ ���� �̸���",ChatColor.GRAY+"������� �����մϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� ���� �̸� ����]","",ChatColor.RED+"[       ����       ]",ChatColor.RED+"���� �̸� ����� ������ �ѷ���",ChatColor.RED+"Ŀ���� �������� �ɷ�ġ��",ChatColor.RED+"��� ��ȿ ó���� �Ǹ�,",ChatColor.RED+"��ų�� ���� ���� �ɼ���",ChatColor.RED+"�� ���� �Ͽ��� �մϴ�."), 21, inv);

		Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� �ý��� ����", 266,0,1,Arrays.asList(ChatColor.GRAY + "ȭ�� ���õ� �ý�����",ChatColor.GRAY+"�����մϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� ȭ�� �ý���GUI �̵�]"), 22, inv);

		Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "��� �ý��� ����", 397,0,1,Arrays.asList(ChatColor.GRAY + "����� ���õ� �ý�����",ChatColor.GRAY+"�����մϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� ��� �ý���GUI �̵�]"), 23, inv);

		if(GBD_RPG.Main_Main.Main_ServerOption.AntiExplode)
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� ����", 46,0,1,Arrays.asList(ChatColor.GREEN+"[Ȱ��ȭ]",ChatColor.GRAY + "ũ����, TNT, ���� ũ����Ż�� ����",ChatColor.GRAY+"���� �ı��� �����մϴ�."), 24, inv);
		else
			Stack2(ChatColor.GREEN +""+ ChatColor.BOLD + "���� ����", 166,0,1,Arrays.asList(ChatColor.RED+"[�� Ȱ��ȭ]",ChatColor.GRAY + "ũ����, TNT, ���� ũ����Ż�� ����",ChatColor.GRAY+"���� �ı��� ��ġ�մϴ�."), 24, inv);

		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "�۾� ������ â�� �ݽ��ϴ�."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_BroadCast(Player player, byte page)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager BroadCast =YC.getNewConfig("BroadCast.yml");

		String UniqueCode = "��0��0��1��0��2��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0������ �������� : " + (page+1));

		if(BroadCast.contains("0"))
		{
			Object[] BroadCastList= BroadCast.getConfigurationSection("").getKeys(false).toArray();
			byte loc=0;
			for(short count = (short) (page*45); count < BroadCastList.length;count++)
			{				
				Stack2(ChatColor.BLACK + "" + ChatColor.BOLD + count, 340,0,1,Arrays.asList(BroadCast.getString(count+"")
						,"",ChatColor.RED+"[Shift + ��Ŭ���� �˸� ����]"), loc, inv);
				
				loc++;
			}

			if(BroadCastList.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
			if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);
		}
		YamlManager Config =YC.getNewConfig("config.yml");

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ����", 152,0,1,Arrays.asList(ChatColor.GRAY + "�� "+ChatColor.GOLD+Config.getInt("Server.BroadCastSecond")+ChatColor.GRAY+"�ʸ��� ����"), 46, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�� ����", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ���������� �����մϴ�."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_StatChange(Player player)
	{
		String UniqueCode = "��0��0��1��0��3��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0������ ���� ����");

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ü��", 267,0,1,Arrays.asList(ChatColor.GRAY + "[  ���� �̸�  ]",ChatColor.WHITE+Main_ServerOption.STR), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ؾ�", 261,0,1,Arrays.asList(ChatColor.GRAY + "[  ���� �̸�  ]",ChatColor.WHITE+Main_ServerOption.DEX), 1, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����", 369,0,1,Arrays.asList(ChatColor.GRAY + "[  ���� �̸�  ]",ChatColor.WHITE+Main_ServerOption.INT), 2, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "����", 370,0,1,Arrays.asList(ChatColor.GRAY + "[  ���� �̸�  ]",ChatColor.WHITE+Main_ServerOption.WILL), 3, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���", 322,0,1,Arrays.asList(ChatColor.GRAY + "[  ���� �̸�  ]",ChatColor.WHITE+Main_ServerOption.LUK), 4, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�����", 276,0,1,Arrays.asList(ChatColor.GRAY + "[  ���� �̸�  ]",ChatColor.WHITE+Main_ServerOption.Damage), 5, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� �����", 403,0,1,Arrays.asList(ChatColor.GRAY + "[  ���� �̸�  ]",ChatColor.WHITE+Main_ServerOption.MagicDamage), 6, inv);
		
		
		String lore = Main_ServerOption.STR_Lore;
		lore = lore.replace("%stat%", Main_ServerOption.STR);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ü�� ����", 323,0,1,Arrays.asList(lore.split("%enter%")), 9, inv);
		lore = Main_ServerOption.DEX_Lore;
		lore = lore.replace("%stat%", Main_ServerOption.DEX);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ؾ� ����", 323,0,1,Arrays.asList(lore.split("%enter%")), 10, inv);
		lore = Main_ServerOption.INT_Lore;
		lore = lore.replace("%stat%", Main_ServerOption.INT);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ����", 323,0,1,Arrays.asList(lore.split("%enter%")), 11, inv);
		lore = Main_ServerOption.WILL_Lore;
		lore = lore.replace("%stat%", Main_ServerOption.WILL);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ����", 323,0,1,Arrays.asList(lore.split("%enter%")), 12, inv);
		lore = Main_ServerOption.LUK_Lore;
		lore = lore.replace("%stat%", Main_ServerOption.LUK);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "��� ����", 323,0,1,Arrays.asList(lore.split("%enter%")), 13, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_Money(Player player)
	{
		String UniqueCode = "��0��0��1��0��4��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0������ ȭ�� ����");

		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "��� �ִ� �׼� ����", 166,0,1,Arrays.asList(ChatColor.GRAY + "��� ������ �ִ� �ݾ���",ChatColor.GRAY+"�����մϴ�.","",ChatColor.YELLOW + "[     ���� �ִ� ���     ]",ChatColor.GRAY +" "+Config.getInt("Server.Max_Drop_Money")), 11, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ȭ��", 421,0,1,Arrays.asList("",ChatColor.GRAY + "[     ���� �̸�     ]"," "+ChatColor.WHITE+Main_ServerOption.Money), 13, inv);
	
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ȭ�� ��� ����", Main_ServerOption.Money1ID,Main_ServerOption.Money1DATA,1,Arrays.asList(ChatColor.GRAY + "   [  50�� ����  ]  ",""), 28, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ȭ�� ��� ����", Main_ServerOption.Money2ID,Main_ServerOption.Money2DATA,1,Arrays.asList(ChatColor.GRAY + "   [ 100�� ���� ]  ",""), 29, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ȭ�� ��� ����", Main_ServerOption.Money3ID,Main_ServerOption.Money3DATA,1,Arrays.asList(ChatColor.GRAY + "   [1000�� ����]  ",""), 30, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ȭ�� ��� ����", Main_ServerOption.Money4ID,Main_ServerOption.Money4DATA,1,Arrays.asList(ChatColor.GRAY + "   [10000�� ����]  ",""), 31, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ȭ�� ��� ����", Main_ServerOption.Money5ID,Main_ServerOption.Money5DATA,1,Arrays.asList(ChatColor.GRAY + "   [50000�� ����]  ",""), 32, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ȭ�� ��� ����", Main_ServerOption.Money6ID,Main_ServerOption.Money6DATA,1,Arrays.asList(ChatColor.GRAY + "   [50000�� �ʰ�]  ",""), 33, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "ȭ�� ��� �ʱ�ȭ", 325,0,1,Arrays.asList(ChatColor.GRAY + "   [ �ʱ�ȭ ��ŵ�ϴ� ]  ",""), 34, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}

	public void OPBoxGUI_Death(Player player)
	{
		String UniqueCode = "��0��0��1��0��5��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0������ ��� ����");

		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
	  	YamlManager Config = YC.getNewConfig("config.yml");

		Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[����� �������� ��Ȱ]", 345,0,1,Arrays.asList("",ChatColor.GRAY +"[���� ���� ����]","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Home.SetHealth")+" ������",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Home.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Home.PenaltyMoney")+" ����","",ChatColor.YELLOW+"[�� Ŭ���� ����]"), 10, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[�ٽ� �Ͼ��]", 2266,0,1,Arrays.asList("",ChatColor.GRAY +"[���� ���� ����]","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Here.SetHealth")+" ������",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Here.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Here.PenaltyMoney")+" ����","",ChatColor.YELLOW+"[�� Ŭ���� ����]"), 12, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[������ ��ٸ���]", 397,3,1,Arrays.asList("",ChatColor.GRAY +"[���� ���� ����]","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Help.SetHealth")+" ������",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Help.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Help.PenaltyMoney")+" ����","",ChatColor.YELLOW+"[�� Ŭ���� ����]"), 14, inv);
		Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��Ȱ�� ���]", 399,0,1,Arrays.asList("",ChatColor.GRAY +"[���� ���� ����]","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Item.SetHealth")+" ������",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Item.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Item.PenaltyMoney")+" ����","",ChatColor.YELLOW+"[�� Ŭ���� ����]"), 16, inv);
		if(Config.getInt("Death.Track")==-1||Config.contains("Death.Track")==false)
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��� BGM]", 166,0,1,Arrays.asList(ChatColor.RED +"[����]",ChatColor.GRAY +"��� BGM�� �������� �ʾҽ��ϴ�."), 19, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��� BGM]", 2264,0,1,Arrays.asList(ChatColor.GREEN +""+Config.getInt("Death.Track")+"�� Ʈ�� ���"), 19, inv);
		
		if(Config.getBoolean("Death.DistrictDirectRevive"))
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[�ٽ� �Ͼ��]", 166,0,1,Arrays.asList(ChatColor.RED +"[�Ұ���]",ChatColor.GRAY +"���ڸ� ��Ȱ �ɼ��� ������� �ʽ��ϴ�."), 21, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[�ٽ� �Ͼ��]", 2264,0,1,Arrays.asList(ChatColor.GREEN +"[����]",ChatColor.GRAY +"���ڸ� ��Ȱ �ɼ��� ����մϴ�."), 21, inv);
		if(Config.getBoolean("Death.SystemOn")==false)
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��� �ý���]", 166,0,1,Arrays.asList(ChatColor.RED +"[�� Ȱ��ȭ]",ChatColor.GRAY +"��� �ý����� ������� �ʽ��ϴ�."), 31, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��� �ý���]", 397,0,1,Arrays.asList(ChatColor.GREEN +"[Ȱ��ȭ]",ChatColor.GRAY +"��� �ý����� ����մϴ�."), 31, inv);
		Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[���� ������]", 288,0,1,Arrays.asList(ChatColor.GRAY +"�ൿ �Ҵɵ� �÷��̾",ChatColor.GRAY +"�ٽ� ������ �����ִ�",ChatColor.GRAY +"ġ�� �������� �����մϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� Ȯ�� �� ����]"), 23, inv);
		Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��Ȱ ������]", 399,0,1,Arrays.asList(ChatColor.GRAY +"�ൿ �Ҵ��� �Ǿ��� ���",ChatColor.GRAY +"���ڸ����� ��Ȱ �� �� �ִ�",ChatColor.GRAY +"���ڸ� ��Ȱ �������� �����մϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� Ȯ�� �� ����]"), 25, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void OPBoxGUI_SettingReviveItem(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");

		String UniqueCode = "��1��0��1��0��6��r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "��0��Ȱ ������");

		ItemStack item = Config.getItemStack("Death.ReviveItem");
		
		if(item != null)
			ItemStackStack(item, 4, inv);
		
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[������ �ֱ�>", 166,0,1,null, 1, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[������ �ֱ�>", 166,0,1,null, 2, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[������ �ֱ�>", 166,0,1,null, 3, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<������ �ֱ�]", 166,0,1,null, 5, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<������ �ֱ�]", 166,0,1,null, 6, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<������ �ֱ�]", 166,0,1,null, 7, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 8, inv);
		player.openInventory(inv);
		return;
	}

	public void OPBoxGUI_SettingRescueItem(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");

		String UniqueCode = "��1��0��1��0��7��r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "��0���� ������");

		ItemStack item = Config.getItemStack("Death.RescueItem");
		
		if(item != null)
			ItemStackStack(item, 4, inv);
		
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[������ �ֱ�>", 166,0,1,null, 1, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[������ �ֱ�>", 166,0,1,null, 2, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[������ �ֱ�>", 166,0,1,null, 3, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<������ �ֱ�]", 166,0,1,null, 5, inv);
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<������ �ֱ�]", 166,0,1,null, 6, inv);	
		Stack2(ChatColor.RED + "" + ChatColor.BOLD + "<������ �ֱ�]", 166,0,1,null, 7, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 0, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 8, inv);
		player.openInventory(inv);
		return;
	}

	public void Guide_GUI (Player player)
	{
		String UniqueCode = "��0��0��1��0��8��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0������ ���̵�");
		
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "���� �ý���", 340,0,1,Arrays.asList(ChatColor.GRAY+ "�÷����ο��� 5���� ������ �ֽ��ϴ�.",ChatColor.RED +"["+Main_ServerOption.STR+"]",ChatColor.GRAY+""+Main_ServerOption.STR+"�� �÷��̾���",ChatColor.GRAY+"������ �������� �����մϴ�.",ChatColor.GREEN +  "["+Main_ServerOption.DEX+"]",ChatColor.GRAY+""+Main_ServerOption.DEX+"�� �÷��̾��� �뷱�� ��",ChatColor.GRAY+"���� �������� ���� ǰ��,",ChatColor.GRAY+"���Ÿ� �������� �����մϴ�.",ChatColor.BLUE+"["+Main_ServerOption.INT+"]",ChatColor.GRAY+""+Main_ServerOption.INT+"�� ������� �� ������ȣ,",ChatColor.GRAY+"���� ���ݷ¿� �����մϴ�.",ChatColor.WHITE+"["+Main_ServerOption.WILL+"]",ChatColor.GRAY + ""+Main_ServerOption.WILL+"�� �÷��̾���",ChatColor.GRAY + "ũ��Ƽ�ÿ� �����մϴ�.",ChatColor.YELLOW + "["+Main_ServerOption.LUK+"]",ChatColor.GRAY + ""+Main_ServerOption.LUK+"�� ũ��Ƽ�� ��",ChatColor.GRAY +"��Ű �ǴϽ�, ��Ű ���ʽ� ��",ChatColor.GRAY +"���� 'Ȯ��'�� �����մϴ�."), 0,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "��Ű �ǴϽ�", 340,0,1,Arrays.asList(ChatColor.GRAY+ "���͸� ����Ͽ��� ���",ChatColor.GRAY+"���� Ȯ���� ���� �� ������ �˴ϴ�."), 1,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "��Ű ���ʽ�", 340,0,1,Arrays.asList(ChatColor.GRAY+ "ä���� �� ��� ���� Ȯ����",ChatColor.GRAY+"ä�� ǰ���� �� ������ �˴ϴ�."), 2,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "Ŀ���� ������ [1]", 340,0,1,Arrays.asList(ChatColor.GRAY+ "���� Ŀ���� ��������",ChatColor.GRAY+"�����ϰų� ����Ͽ�",ChatColor.GRAY+"������ �ҷ��� �� �ֽ��ϴ�.","",ChatColor.GOLD+"[���ɾ�]",ChatColor.YELLOW+"/������"), 3,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "Ŀ���� ������ [2]", 340,0,1,Arrays.asList(ChatColor.GRAY+ Main_ServerOption.Damage+", ���, ��ȣ ����",ChatColor.GRAY+"Ư�� �ɼ��� ���� ��������",ChatColor.GRAY+"�������� 0�� �� �� ȿ����",ChatColor.GRAY+"������� �ʽ��ϴ�.",ChatColor.GRAY+"�׷� ���, ���� �����ϰų�",ChatColor.GRAY+"�������� NPC�� ����",ChatColor.GRAY+"���� �޾ƾ� �մϴ�.","",ChatColor.GOLD+"[���ɾ�]",ChatColor.YELLOW+"/������ ����"), 4,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "���� ����", 340,0,1,Arrays.asList(ChatColor.GRAY+ "������ �����Ͽ� ���� Ȥ��",ChatColor.GRAY+"PVP��, ���� ����� ���� ������",ChatColor.GRAY+"������ �� �ֽ��ϴ�.",ChatColor.GRAY+"���� ������ ���� �ֱ� �湮��",ChatColor.GRAY+"�������� ��Ȱ �ϰų�,",ChatColor.GRAY+"���� ����� Ư�� �޽�����",ChatColor.GRAY+"���� �� �ֽ��ϴ�.","",ChatColor.GOLD+"[���ɾ�]",ChatColor.YELLOW+"/����"), 5,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "NPC", 340,0,1,Arrays.asList(ChatColor.GRAY+ "NPC ������ "+ChatColor.YELLOW+ "Citizens2 "+ChatColor.GRAY+ "�÷�������",ChatColor.GRAY+ "����Ͻô� ���� �����մϴ�.",ChatColor.GRAY+ "NPC ���� ��, �ش� NPC�� ��Ŭ�� �Ͽ�",ChatColor.GRAY+ "NPC�� ���� ���� ������ �� �� �ֽ��ϴ�."), 6,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "����Ʈ", 340,0,1,Arrays.asList(ChatColor.GRAY+ "���ǹڽ����� ���� ���� ����Ʈ��",ChatColor.GRAY+"���� ������ �����Ͻ� �� �ֽ��ϴ�.","",ChatColor.GOLD+"[���ɾ�]",ChatColor.YELLOW+"/���ǹڽ�",ChatColor.YELLOW+"/����Ʈ ����"+ChatColor.DARK_AQUA+" [Ÿ��]"+ChatColor.YELLOW+" [����Ʈ �̸�]","",ChatColor.DARK_AQUA+"[�Ϲ�/�ݺ�/����/����/�Ѵ�]"), 7,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "�̺�Ʈ", 340,0,1,Arrays.asList(ChatColor.GRAY+ "���ǹڽ� 1�������� �ִ� "+ChatColor.YELLOW+"'�̺�Ʈ'",ChatColor.GRAY+ "�������� Ŭ���Ͽ� ���ٸ�",ChatColor.GRAY+ "������ Ư�� �̺�Ʈ��",ChatColor.GRAY+"���� �Ͻ� �� �ֽ��ϴ�.",ChatColor.GRAY+"�����鰣 �뷱���� �����ϸ�",ChatColor.GRAY+"������ �̺�Ʈ�� ���� �ݽô�."), 8,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "��ų [1]", 340,0,1,Arrays.asList(ChatColor.GRAY+ "���ǹڽ� 2�������� �ִ� "+ChatColor.YELLOW+"'��ų'",ChatColor.GRAY+ "�������� Ŭ���Ͽ� ���ٸ�",ChatColor.GRAY+ "���� ��ų�� �����ϰų� �����ϴ�",ChatColor.GRAY+"GUI ȭ���� ��Ÿ���ϴ�.",ChatColor.GRAY+"�̰����� ������ ���� ��ų����",ChatColor.GRAY+"������ �ֵ� �ý��ۿ� ���� �з��Ͽ�",ChatColor.GRAY+"���� ������ ��� �����ϰ� �Ǹ�",ChatColor.GRAY+"��ų�� "+ChatColor.LIGHT_PURPLE+"Ŀ�ǵ�"+ChatColor.GRAY+"�� ����� �� ������,",ChatColor.DARK_AQUA+"MagicSpells �÷�����"+ChatColor.GRAY+"��"+ChatColor.DARK_AQUA+" ����"+ChatColor.GRAY+"�մϴ�."), 9,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "��ų [2]", 340,0,1,Arrays.asList(ChatColor.GRAY+ "��ų�� ���� �ϼ̴ٸ�",ChatColor.GRAY+ "��ų ��ũ�� ����� �����Դϴ�.",ChatColor.GRAY+ "��ų ��ũ�� ����̴ٸ�",ChatColor.GRAY+ "������ ��ų ��ũ�� �´�",ChatColor.GRAY+ "Ŀ�ǵ� Ȥ�� ���������� ����,",ChatColor.GRAY+ "��ũ���� ��� ���� ���ʽ� ����,",ChatColor.GRAY+ "��ũ���� �ʿ��� ��ų ����Ʈ ��",ChatColor.GRAY+ "�پ��� �ɼ��� �����Ͻ� �� �ֽ��ϴ�."), 10,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "����, �׸��� �ý��� [1]", 340,0,1,Arrays.asList(ChatColor.YELLOW+ "GoldBigDragon_Advanced",ChatColor.GRAY+ "(���� GBD_A)",ChatColor.GRAY+ "�÷����ο��� �� ���� ���� �ý�����",ChatColor.GRAY+ "�����մϴ�. "+ChatColor.STRIKETHROUGH+"(���̺긮�� �÷�����)",ChatColor.GRAY+ "�� ���� Ư���� ����ϸ鼭��",ChatColor.GRAY+ "���� �ٸ��⿡, �ý��� �����",ChatColor.GRAY+ "���Ǹ� ���մϴ�."), 11,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "����, �׸��� �ý��� [2]", 340,0,1,Arrays.asList(ChatColor.RED+""+ChatColor.BOLD+ "[������ ���丮]",ChatColor.GRAY+ "ù ��°�� �˾ƺ��� �ý�����",ChatColor.GRAY+ "�����в� ģ���� ������",ChatColor.GRAY+ "�����ý��丮�� ������",ChatColor.GRAY+ "�����ý��丮�� �ý����Դϴ�.",ChatColor.GRAY+ "���� ���� ���� ���� Ŭ������",ChatColor.GRAY+ "�����Ͻ� �� ������,",ChatColor.GRAY+ "���� Ŭ���� ������ �Ǵٽ�",ChatColor.GRAY+ "2�� ����, 3�� ������ ����",ChatColor.GRAY+ "�±� ������ �����մϴ�.",ChatColor.GRAY+ "���� ���� �� �±޺��� ��������",ChatColor.GRAY+ "��ų�� �ο��� �� �ְ� �Ǹ�,",ChatColor.GRAY+ "������ ��, ���� ����Ʈ�� �����ϸ�",ChatColor.GRAY+ "������ ������ ���ϴ� ����",ChatColor.GRAY+ "���������ν� �������ϴ�.",ChatColor.GRAY+ "������ "+ChatColor.DARK_PURPLE+"Citizens �÷�����"+ChatColor.GRAY+"����",ChatColor.GRAY+ "NPC�� ������ ��, �� Ŭ����",ChatColor.GRAY+ "NPC���� â�� ������ �װ�����",ChatColor.GOLD+ "[���� ����]"+ChatColor.GRAY+"��ư�� ���� ����",ChatColor.GOLD+ "[���� ����]"+ChatColor.GRAY+"�� ������ ���",ChatColor.GRAY+ "�������� �ش� NPC�� ����",ChatColor.GRAY+ "������ Ŭ������ ������ ������ ���ϴ�."), 12,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "����, �׸��� �ý��� [3]", 340,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+ "[������]",ChatColor.GRAY+ "�� ��°�� �˾ƺ��� �ý�����",ChatColor.GRAY+ "���� ���� �����̾���",ChatColor.GRAY+ "������ ������ �ý����Դϴ�.",ChatColor.GRAY+ "������ �ý����� ���",ChatColor.GRAY+ "�����ý��丮 ó�� ������",ChatColor.GRAY+ "���� ����Ʈ ������ ������",ChatColor.GRAY+ "������ ������ ���� ����",ChatColor.GRAY+ "��� ��ų�� ��� �� �ִ�",ChatColor.GRAY+ "������ �������� ������ �ֽ��ϴ�.",ChatColor.GRAY+ "������ ������ ���丮ó��",ChatColor.GRAY+ "������ ������ ��ų�� ��� �ִ�",ChatColor.GRAY+ "������ �ƴ϶�, �ڽ��� ����",ChatColor.DARK_PURPLE+ "NPC�� ��ȭ"+ChatColor.GRAY+"�� �ϰų�",ChatColor.DARK_PURPLE+ "Ư���� å"+ChatColor.GRAY+"�� �������ν�",ChatColor.GRAY+ "��ų�� �͵��� �� �ֽ��ϴ�.",ChatColor.GRAY+ "��ų�� ���������� ������",ChatColor.GRAY+ "�����ǿ� ����� ��ų��",ChatColor.GRAY+ "�����ǿ� ����� �� �����ϴ�.",ChatColor.AQUA+ "ȯ��"+ChatColor.GRAY+" ��"+ChatColor.DARK_PURPLE+" Ư���� å"+ChatColor.GRAY+"�� ���� ������",ChatColor.GRAY+"���� ���̵忡�� �����ϰڽ��ϴ�."), 13,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "����, �׸��� �ý��� [4]", 340,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+"[������]",ChatColor.AQUA+ "<ȯ��>",ChatColor.GRAY+ "������ ���丮�� ������ ������ �ֽ��ϴ�.",ChatColor.GRAY+ "������ �����⿡�� ������ �ִٸ�",ChatColor.GRAY+ "��� ��ų�� ������ ���� ������.",ChatColor.GRAY+ "�̷� ���� �غ��ϱ� ���� ��ġ��",ChatColor.AQUA+ "'ȯ��'"+ChatColor.GRAY+"�Դϴ�.",ChatColor.GRAY+ "ȯ���� �� ���, ���ݱ��� ����",ChatColor.GRAY+ "��� ������ '���� ����'�� ��������,",ChatColor.GRAY+ "�Ϲ����� ������ 1�� �ʱ�ȭ �˴ϴ�.",ChatColor.GRAY+ "�̴� ������ �ƴ�, ��ų ����Ʈ��",ChatColor.GRAY+ "���� ������ ���� �� �ִ� ��ġ�Դϴ�.",ChatColor.GRAY+ "������ 1�� �ȴٸ� �ڽ��� ��ƾ� ��",ChatColor.GRAY+ "����ġ ���� �������״ϱ��.",ChatColor.GRAY+ "���� ĳ������ ���̰� 20���� �Ǹ�",ChatColor.GRAY+ "ȯ���� ����������, �� �÷����ο�����",ChatColor.GRAY+ "�Һ� �������� "+ChatColor.YELLOW+"ȯ�� ����"+ChatColor.GRAY+"��",ChatColor.GRAY+ "�����ϰ� �ֽ��ϴ�."), 14,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "����, �׸��� �ý��� [5]", 340,0,1,Arrays.asList(ChatColor.WHITE+""+ChatColor.BOLD+ "[������]",ChatColor.DARK_PURPLE+ "<Ư���� å>",ChatColor.GRAY+ "���ǹڽ� 2�������� �ִ�",ChatColor.GREEN+ "�Һ� ������"+ChatColor.GRAY + " �������� Ŭ���ϸ�",ChatColor.GRAY+ "��ų ����Ʈ �ֹ������� ����",ChatColor.GRAY+ "ȯ������ ������ ����",ChatColor.GRAY+ "��κ��� �Һ� �������� ����ų�",ChatColor.GRAY+ "������ �� ������, ���� �����帱",ChatColor.DARK_PURPLE+ "Ư���� å"+ChatColor.GRAY+" ���� ������ �����մϴ�.",ChatColor.GRAY+ "Ư����å�� ���, ������ �ٷ� �Ҹ� ������",ChatColor.GRAY+ "�÷��̾ �𸣴� ��ų��",ChatColor.GRAY+ "���� �� �ֵ��� �ϰų�,",ChatColor.GRAY+ "Ư�� ������ �������ִ�",ChatColor.GRAY+ "�ɷ��� ������ �ֽ��ϴ�.",ChatColor.GRAY+ "�׷��⿡ ���� ������ �翬",ChatColor.GRAY+ "�߿� �ŷ� ǰ���� �� ���ɼ���",ChatColor.GRAY+ "���� �������̹Ƿ�, �����",ChatColor.GRAY+ "������ �뷱�� �������� �ʿ���",ChatColor.GRAY+ "������ 1������� ���Ƶ�",ChatColor.GRAY+ "������ �ƴմϴ�.",ChatColor.GRAY+ "�뷱���� ���� �����,",ChatColor.GRAY+ "�̺�Ʈ�� ���� ����,",ChatColor.GRAY+ "���� ���� ������ ������",ChatColor.GRAY+ "������ ���Դϴ�."), 15,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "����", 340,0,1,Arrays.asList(ChatColor.GRAY+ "Ŀ���� ���������� ����",ChatColor.GRAY+ "������ ��, ���� �ɼ���",ChatColor.GRAY+ "���� �������� ��츸",ChatColor.GRAY+ "������ �����ϸ�, ������",ChatColor.DARK_AQUA+ "[���� ����]"+ChatColor.GRAY+"�� �������� ����",ChatColor.GRAY+ "NPC���� ���� �� �ֽ��ϴ�.",ChatColor.GRAY+ "���� ���ο��� ���ǹڽ����� ����",ChatColor.GRAY+ "�������� ��Ͻ�Ű��",ChatColor.GRAY+ "�ش� NPC���Լ� �ش� ������",ChatColor.GRAY+ "�����ϰ� �˴ϴ�."), 16,inv);
		Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "��", 340,0,1,Arrays.asList(ChatColor.GRAY+ "������ ����� ����������",ChatColor.GRAY+ "���� Ȯ���� ������,",ChatColor.BLUE+ "��"+ChatColor.GRAY+" �̶�� �������� �ʿ��մϴ�.",ChatColor.GRAY+ "���� ���ǹڽ� 2����������",ChatColor.GRAY+ "���� ���� �� �ֽ��ϴ�."), 17,inv);
		//Stack2(ChatColor.YELLOW +""+ ChatColor.BOLD + "", 340,0,1,Arrays.asList(ChatColor.GRAY+ ""), 18,inv);
		Stack2(ChatColor.RED +"            "+ChatColor.BOLD +"��"+ChatColor.BLUE +""+ChatColor.BOLD +"��"+ChatColor.GREEN +""+ChatColor.BOLD +"��"+ChatColor.YELLOW +""+ChatColor.BOLD +"��"+ChatColor.WHITE +""+ChatColor.BOLD +"Ʈ            ", 403,0,1,Arrays.asList("",
				ChatColor.GRAY+ "�ɰ��� : "+ChatColor.DARK_RED+"|||||||||||||||||||||||||||||||||||||||| [�ְ�]",ChatColor.RED+ "�� ������ �������� ���� ������ȣ ���� ��",ChatColor.RED+ "���� ���/��ȣ�� �������� Pain ��ų",ChatColor.RED+ "Ŭ������ ������� ������","",
				ChatColor.GRAY+ "�ɰ��� : "+ChatColor.GOLD+"||||||||||||||||||||"+ChatColor. GRAY+"||||||||||||||||||||"+ChatColor.GOLD+" [����]",ChatColor.YELLOW+"�� ���� ���� �𸣴� ������ ��. ����","",
				ChatColor.GRAY+ "�ɰ��� : "+ChatColor.DARK_GREEN+"||||||||||"+ChatColor. GRAY+"||||||||||||||||||||||||||||||"+ChatColor.DARK_GREEN+" [����]",ChatColor.GREEN+"�� ������ ������ ���ʽ��� ����",ChatColor.GREEN+"�ٷιٷ� ������Ʈ�� ���� ������",ChatColor.GREEN+"�°ų� �ֹٸ� �����̸� ���ΰ�����."), 22,inv);

		Stack2(ChatColor.DARK_RED +""+ ChatColor.BOLD + "[������ ���̵�]", 389,0,1,Arrays.asList("",ChatColor.AQUA+ "[Ŭ���� ������ URL�� �������ϴ�.]"), 36,inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "�۾� ������ â�� �ݽ��ϴ�."), 53, inv);
		
		player.openInventory(inv);
	}
	
	
	//���� GUIâ ���� �������� ������ ��, �ش� �����ܿ� ����� �ִ� �޼ҵ�1   -���� GUI, ���ǹڽ�, Ŀ���� ����GUI-//
	public void OPBoxGUIInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		int slot = event.getSlot();
		
		if((slot >= 0 && slot <= 8)||(slot >= 45 && slot <= 53))
		{
			if(slot == 2)//���� �ð� ������
			{
				s.SP(player, Sound.ENTITY_CHICKEN_AMBIENT, 0.8F, 1.0F);
				Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setTime(0);
				player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" ���� �ð��� ������ �����Ͽ����ϴ�!");
			}
			else if(slot == 3)//���� �ð� ������
			{
				s.SP(player, Sound.ENTITY_WOLF_HOWL, 0.8F, 1.0F);
				Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setTime(14000);
				player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" ���� �ð��� ������ �����Ͽ����ϴ�!");
			}
			else if(slot == 4)//���� ���� ���� ����
			{
				s.SP(player, Sound.ENTITY_VILLAGER_YES, 0.8F, 1.0F);
				Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setSpawnLocation((int)player.getLocation().getX(), (int)player.getLocation().getY(), (int)player.getLocation().getZ());
				player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" ������ ���� ������ "+(int)player.getLocation().getX()+","+(int)player.getLocation().getY()+","+(int)player.getLocation().getZ()+" �� �����Ͽ����ϴ�!");
			}
			else if(slot == 53)//�ݱ�
			{
				s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(slot == 5)//���� ���� ����
				{
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setStorm(false);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setWeatherDuration(180000);
					player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" ���� ������ �������� �����Ͽ����ϴ�!");
				}
				else if(slot == 6)//���� ���� �帲
				{
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setStorm(true);
					Bukkit.getServer().getWorld(player.getLocation().getWorld().getName()).setWeatherDuration(180000);
					player.sendMessage(ChatColor.GOLD+"[System] : "+player.getLocation().getWorld().getName()+" ���� ������ �帲���� �����Ͽ����ϴ�!");
				}
				else if(slot == 48)//���� ������
					OPBoxGUI_Main(player,(byte) (Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getDisplayName()))-1));
				else if(slot == 50)//���� ������
					OPBoxGUI_Main(player,(byte) (Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(0).getItemMeta().getDisplayName()))+1));
			}
		}
		else
		{
			String DisplayName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(DisplayName.compareTo("Ŀ���� ������") == 0)
				new CustomItem_GUI().ItemListGUI(player, 0);
			else if(DisplayName.compareTo("����") == 0)
				new Monster_GUI().MonsterListGUI(player, 0);
			else if(DisplayName.compareTo("���� ����") == 0)
				OPBoxGUI_Setting(player);
			else if(DisplayName.compareTo("����Ʈ") == 0)
				new Quest_GUI().AllOfQuestListGUI(player, (short) 0,false);
			else if(DisplayName.compareTo("��ų") == 0)
				new OPboxSkill_GUI().AllSkillsGUI(player,(short) 0,false,null);
			else if(DisplayName.compareTo("ī�װ��� �� ����") == 0)
				new Job_GUI().ChooseSystemGUI(player);
			else if(DisplayName.compareTo("�Һ� ������") == 0)
				new UseableItem_GUI().UseableItemListGUI(player, 0);
			else if(DisplayName.compareTo("����") == 0)
				new Area_GUI().AreaListGUI(player, (short) 0);
			else if(DisplayName.compareTo("����") == 0)
				new GBD_RPG.Dungeon.Dungeon_GUI().DungeonListMainGUI(player, 0, 52);
			else if(DisplayName.compareTo("��ɼ� ��ü") == 0)
				new Structure_GUI().StructureListGUI(player, 0);
			else if(DisplayName.compareTo("����") == 0)
				new Gamble_GUI().GambleMainGUI(player);
			else if(DisplayName.compareTo("�׺���̼�") == 0)
				new Navigation_GUI().NavigationListGUI(player,(short) 0);
			else if(DisplayName.compareTo("����") == 0)
				new GBD_RPG.Warp.Warp_GUI().WarpListGUI(player, 0);
			else if(DisplayName.compareTo("���� ����") == 0)
				new GBD_RPG.Admin.WorldCreate_GUI().WorldCreateGUIMain(player);
			else if(DisplayName.compareTo("����") == 0)
				new Monster_GUI().MonsterListGUI(player, 0);
			else if(DisplayName.compareTo("�ʽ���") == 0)
				new NewBie_GUI().NewBieGUIMain(player);
			else if(DisplayName.compareTo("������") == 0)
				new Upgrade_GUI().UpgradeRecipeGUI(player,0);
			else if(DisplayName.compareTo("�̺�Ʈ") == 0)
				new Event_GUI().EventGUI_Main(player);
			else if(DisplayName.compareTo("���� ����") == 0)
			{
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager Config =YC.getNewConfig("config.yml");
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				Config.set("Time.LastSkillChanged", new GBD_RPG.Util.Util_Number().RandomNum(0, 100000)-new GBD_RPG.Util.Util_Number().RandomNum(0, 100000));
				if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System")==true) {Config.set("Server.Like_The_Mabinogi_Online_Stat_System", false);}
				else{Config.set("Server.Like_The_Mabinogi_Online_Stat_System", true);}
				Config.saveConfig();
				OPBoxGUI_Main(player,(byte) 1);
				new GBD_RPG.Job.Job_Main().AllPlayerFixAllSkillAndJobYML();
			}
			else if(DisplayName.compareTo("�÷����� ���̵�") == 0)
				Guide_GUI(player);
			else if(DisplayName.compareTo("GoldBigDragonRPG") == 0)
			{
				if(Main_ServerOption.serverVersion.compareTo(Main_ServerOption.currentServerVersion)==0&&Main_ServerOption.serverUpdate.compareTo(Main_ServerOption.currentServerUpdate)==0)
				{
					s.SP(player,Sound.BLOCK_ANVIL_USE, 0.8F, 1.0F);
					player.sendMessage(ChatColor.YELLOW + "[���� üũ] : ���� GoldBigDragonRPG�� �ֽ� �����Դϴ�!");
				}
				else
				{
					s.SP(player,Sound.BLOCK_ANVIL_USE, 0.8F, 1.0F);
					player.sendMessage(ChatColor.RED + "[���� üũ] : ���� GoldBigDragonRPG�� ������Ʈ�� �ʿ��մϴ�!");
					player.sendMessage(ChatColor.RED + "[���� ����] : "+Main_ServerOption.serverVersion);
					player.sendMessage(ChatColor.RED + "[�ֽ� ����] : "+Main_ServerOption.currentServerVersion);
					player.sendMessage(ChatColor.RED + "[���� ��ġ] : "+Main_ServerOption.serverUpdate);
					player.sendMessage(ChatColor.RED + "[�ֽ� ��ġ] : "+Main_ServerOption.currentServerUpdate);
				}
			}
			else if(DisplayName.compareTo("����") == 0)
				new Event_GUI().EventGUI_Main(player);
			
		}
		return;
	}
	
	public void OPBoxGUI_SettingInventoryClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");

		if(slot == 53)//�ݱ�
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if((slot >= 10 && slot <= 13) || slot == 20 || slot == 24)
			{
				if(slot == 10)//��ƼƼ ����
				{
					if(Config.getBoolean("Server.EntitySpawn") == true)
						Config.set("Server.EntitySpawn", false);
					else
						Config.set("Server.EntitySpawn", true);
				}
				else if(slot == 11)//PVP
				{
					if(Config.getBoolean("Server.PVP")==true)
					{
						Config.set("Server.PVP", false);
						GBD_RPG.Main_Main.Main_ServerOption.PVP = false;
					}
					else
					{
						Config.set("Server.PVP", true);
						GBD_RPG.Main_Main.Main_ServerOption.PVP = true;
					}
				}
				else if(slot == 12)//���� ���� ȿ��
				{
					if(Config.getInt("Server.MonsterSpawnEffect")<7)
						Config.set("Server.MonsterSpawnEffect", Config.getInt("Server.MonsterSpawnEffect")+1);
					else
						Config.set("Server.MonsterSpawnEffect", 0);
				}
				else if(slot == 13)//Ŀ���� ���� �ı�
				{
					if(Config.getBoolean("Server.CustomWeaponBreak") == true)
						Config.set("Server.CustomWeaponBreak", false);
					else
						Config.set("Server.CustomWeaponBreak", true);
				}
				else if(slot == 20)//Ŀ���� ���� ��ġ/��ġ ����
				{
					if(Config.getBoolean("Server.CustomBlockPlace") == true)
						Config.set("Server.CustomBlockPlace", false);
					else
						Config.set("Server.CustomBlockPlace", true);
				}
				else if(slot == 24)//���� ���� ����
				{
					if(Config.getBoolean("Server.AntiExplode") == true)
					{
						Config.set("Server.AntiExplode", false);
						GBD_RPG.Main_Main.Main_ServerOption.AntiExplode = false;
					}
					else
					{
						Config.set("Server.AntiExplode", true);
						GBD_RPG.Main_Main.Main_ServerOption.AntiExplode = true;
					}
				}
				Config.saveConfig();
				OPBoxGUI_Setting(player);
			}
			else
			{
				if(slot == 45)//���� ���
					OPBoxGUI_Main(player, (byte) 1);
				else if(slot == 16)//��������
					OPBoxGUI_BroadCast(player, (byte) 0);
				else if(slot == 21)//���� �̸� ����
					OPBoxGUI_StatChange(player);
				else if(slot == 22)//���� �ý��� ����
					OPBoxGUI_Money(player);
				else if(slot == 23)//��� �ý��� ����
					OPBoxGUI_Death(player);
				else
				{
					player.closeInventory();
					UserData_Object u = new UserData_Object();
					if(slot == 14)//���� �޽��� ����
					{
						u.setString(player, (byte)1,"JM");
						player.sendMessage(ChatColor.GREEN+"[SYSTEM] : ���� �޽����� �Է� �� �ּ���! ("+ChatColor.WHITE+"����"+ChatColor.GREEN+" �Է½� ���� �޽��� ����)");
					}
					else if(slot == 15)//���� �޽��� ����
					{
						u.setString(player, (byte)1,"QM");
						player.sendMessage(ChatColor.GREEN+"[SYSTEM] : ���� �޽����� �Է� �� �ּ���! ("+ChatColor.WHITE+"����"+ChatColor.GREEN+" �Է½� ���� �޽��� ����)");
					}
					else if(slot == 19)//ä�� ���� ����
					{
						if(event.isRightClick())
						{
							s.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
							Config.removeKey("Server.ChatPrefix");
							Config.saveConfig();
							player.sendMessage(ChatColor.RED+"[SYSTEM] : ���λ縦 �����Ͽ����ϴ�!");
							OPBoxGUI_Setting(player);
							return;
						}
						u.setString(player, (byte)1,"CCP");
						player.sendMessage(ChatColor.GREEN+"[SYSTEM] : ä�� ���¸� �Է� �� �ּ���!");
						player.sendMessage(ChatColor.GOLD + "%t%"+ChatColor.WHITE + " - ���� �ð� ��Ī�ϱ� -");
						player.sendMessage(ChatColor.GOLD + "%gm%"+ChatColor.WHITE + " - ���Ӹ�� ��Ī�ϱ� -");
						player.sendMessage(ChatColor.GOLD + "%ct%"+ChatColor.WHITE + " - ä�� Ÿ�� ��Ī�ϱ� -");
						player.sendMessage(ChatColor.GOLD + "%lv%"+ChatColor.WHITE + " - ���� ��Ī�ϱ� -");
						player.sendMessage(ChatColor.GOLD + "%rlv%"+ChatColor.WHITE + " - ���� ���� ��Ī�ϱ� - (���� ������ �����ý��丮�� ��� ���� ����.)");
						player.sendMessage(ChatColor.GOLD + "%job%"+ChatColor.WHITE + " - ���� ��Ī�ϱ� - (���� ������ �������� ��� ���� ����.)");
						player.sendMessage(ChatColor.GOLD + "%message%"+ChatColor.WHITE + " - ä�� ���� ��Ī�ϱ� -");
					}
					u.setType(player, "System");
					player.sendMessage(ChatColor.GOLD + "%player%"+ChatColor.WHITE + " - �÷��̾� ��Ī�ϱ� -");
					player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
					ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
							ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
					ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
							ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				}
			}
		}
		return;
	}
	
	public void OPBoxGuideInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		int slot = event.getSlot();

		if(slot==53)//�ݱ�
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot==36)//������ ���̵�
			{
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_RED+""+ChatColor.BOLD+"[YouTube] "+ChatColor.WHITE+""+ChatColor.BOLD+": "+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"https://www.youtube.com/playlist?list=PLxqihkJXVJABIlxU3n6bNhhC8x6xPbORP   "+ChatColor.YELLOW+""+ChatColor.BOLD+"[Ŭ���� ���̵� �������� ����˴ϴ�]");
			}
			if(slot==45)//���� ���
				OPBoxGUI_Main(player,(byte) 1);
		}
	}

	public void OPBoxGUI_BroadCastClick(InventoryClickEvent event)
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
			if(slot == 45)//���� ���
				OPBoxGUI_Setting(player);
			else if(slot == 46)//���� ����
			{
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[����] : �� �ʸ��� ������ �����?");
				player.sendMessage(ChatColor.YELLOW+"(�ּ� 1�� ~ �ִ� 3600��(1�ð�) ���� ���ڸ� �Է� �ϼ���!)");
				UserData_Object u = new UserData_Object();
				u.setType(player, "System");
				u.setString(player, (byte)1, "BMT");
			}
			else if(slot == 48)//���� ������
				OPBoxGUI_BroadCast(player, (byte) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 49)//�� ����
			{
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager BroadCast =YC.getNewConfig("BroadCast.yml");
				int BCnumber = BroadCast.getConfigurationSection("").getKeys(false).size();
				BroadCast.set(BCnumber+"", ChatColor.YELLOW+"[���ο� �������� ���� ��]");
				BroadCast.saveConfig();
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[����] : ���ο� ���� ������ �Է� �� �ּ���!");
				player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
				ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
						ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
				ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
						ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
				UserData_Object u = new UserData_Object();
				u.setType(player, "System");
				u.setString(player, (byte)1, "NBM");
				u.setInt(player, (byte)0, BCnumber);
			}
			else if(slot == 50)//���� ������
				OPBoxGUI_BroadCast(player, (byte) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])));
			else if(event.isShiftClick() == true && event.isRightClick() == true)
			{
				s.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager BroadCast =YC.getNewConfig("BroadCast.yml");
				int Acount =  BroadCast.getConfigurationSection("").getKeys(false).toArray().length-1;
				int number = (((Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1)*45)+event.getSlot());
				for(int counter = number;counter <Acount;counter++)
					BroadCast.set(counter+"", BroadCast.get((counter+1)+""));
				BroadCast.removeKey(Acount+"");
				BroadCast.saveConfig();
				OPBoxGUI_BroadCast(player, (byte) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
			}
		}
	}

	public void OPBoxGUI_StatChangeClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		event.setCancelled(true);
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
			if(slot == 45)//���� ���
				OPBoxGUI_Setting(player);
			else
			{
				player.closeInventory();
	
				if(slot >=9 && slot <=13)
				{
					player.sendMessage(ChatColor.DARK_AQUA+"[System] : ���ο� "+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+"�� �Է� �� �ּ���!");
					player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - ���� ��� ���� -");
					player.sendMessage(ChatColor.GOLD + "%stat%"+ChatColor.WHITE + " - ���� �̸� ���� -");
				}
				else
				{
					player.sendMessage(ChatColor.DARK_AQUA+"[System] : ���ο� "+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())+" ���� �̸��� �Է� �� �ּ���!");
					player.sendMessage(ChatColor.GRAY+"(��� ���� �� ��ȣ ��� �Ұ�)");
				}
				UserData_Object u = new UserData_Object();
				u.setType(player, "System");
				u.setString(player, (byte)1, "CSN");
				u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
		}
	}

	public void OPBoxGUI_MoneyClick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		
		int slot = event.getSlot();
		
		if(slot == 45)//���� ���
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI_Setting(player);
		}
		else if(slot == 53)//������
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 34)//ȭ�� ��� �ʱ�ȭ
		{
			YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager Config =YC.getNewConfig("config.yml");
			
			s.SP(player, Sound.ENTITY_IRONGOLEM_ATTACK, 0.8F, 1.0F);
			player.sendMessage(ChatColor.DARK_AQUA+"[System] : ȭ�� ����� �ʱ�ȭ �Ǿ����ϴ�!");

			Config.set("Server.Money.1.ID",348);
			Main_ServerOption.Money1ID = 348;
			Config.set("Server.Money.2.ID",371);
			Main_ServerOption.Money2ID = 371;
			Config.set("Server.Money.3.ID",147);
			Main_ServerOption.Money3ID = 147;
			Config.set("Server.Money.4.ID",266);
			Main_ServerOption.Money4ID = 266;
			Config.set("Server.Money.5.ID",41);
			Main_ServerOption.Money5ID = 41;
			Config.set("Server.Money.6.ID",41);
			Main_ServerOption.Money6ID = 41;
			Config.set("Server.Money.1.DATA",0);
			Main_ServerOption.Money1DATA = 0;
			Config.set("Server.Money.2.DATA",0);
			Main_ServerOption.Money2DATA = 0;
			Config.set("Server.Money.3.DATA",0);
			Main_ServerOption.Money3DATA = 0;
			Config.set("Server.Money.4.DATA",0);
			Main_ServerOption.Money4DATA = 0;
			Config.set("Server.Money.5.DATA",0);
			Main_ServerOption.Money5DATA = 0;
			Config.set("Server.Money.6.DATA",0);
			Main_ServerOption.Money6DATA = 0;

			Config.saveConfig();
			OPBoxGUI_Money(player);
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			UserData_Object u = new UserData_Object();
			u.setType(player, "System");
			if(slot >= 28 && slot <= 33)
			{
				if(slot == 28)
					u.setInt(player, (byte)1,50);
				else if(slot == 29)
					u.setInt(player, (byte)1,100);
				else if(slot == 30)
					u.setInt(player, (byte)1,1000);
				else if(slot == 31)
					u.setInt(player, (byte)1,10000);
				else if(slot == 32)
					u.setInt(player, (byte)1,50000);
				else if(slot == 33)
					u.setInt(player, (byte)1,-1);
				player.sendMessage(ChatColor.DARK_AQUA+"[System] : ȭ�� ������� ������ ������ ID�� �Է� �� �ּ���!");
				u.setString(player, (byte)1, "CMID");
			}
			else
			{
				if(slot == 11)
				{
					player.sendMessage(ChatColor.DARK_AQUA+"[System] : �ִ� �󸶱����� ��� �����ϵ��� �ұ��?");
					player.sendMessage(ChatColor.GRAY + "(�ּ� 1000(1õ)�� �̻�, �ִ� 1000000000(1��)�� ����)");
					u.setString(player, (byte)1, "CDML");
				}
				if(slot == 13)
				{
					player.sendMessage(ChatColor.DARK_AQUA+"[System] : ���ο� ȭ�� ������ �Է� �� �ּ���!");
					player.sendMessage(ChatColor.GRAY+"(��� ���� �� ��ȣ ��� �Ұ�)");
					u.setString(player, (byte)1, "CSN");
					u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
			}
			player.closeInventory();
		}
		return;
	}

	public void OPBoxGUI_DeathClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();

		if(slot == 53)//������
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 19)//��� BGM
			{
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			  	YamlManager Config = YC.getNewConfig("config.yml");
				if(Config.getInt("Death.Track")==-1||Config.contains("Death.Track")==false)
					new GBD_RPG.Area.Area_GUI().AreaMusicSettingGUI(player, 0, "DeathBGM��");
				else
				{
					Config.set("Death.Track", -1);
					Config.saveConfig();
					OPBoxGUI_Death(player);
				}
			}
			else if(slot == 21)//���ڸ� ��Ȱ �ɼ� �ѱ�/����
			{
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			  	YamlManager Config = YC.getNewConfig("config.yml");
				if(Config.getBoolean("Death.DistrictDirectRevive"))
					Config.set("Death.DistrictDirectRevive",false);
				else
					Config.set("Death.DistrictDirectRevive",true);
				Config.saveConfig();
				OPBoxGUI_Death(player);
			}
			else if(slot == 23)//���� ������ ����
				OPBoxGUI_SettingRescueItem(player);
			else if(slot == 25)//��Ȱ ������ ����
				OPBoxGUI_SettingReviveItem(player);
			else if(slot == 31)//��� �ý��� ��/����
			{
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			  	YamlManager Config = YC.getNewConfig("config.yml");
				if(Config.getBoolean("Death.SystemOn"))
					Config.set("Death.SystemOn",false);
				else
					Config.set("Death.SystemOn",true);
				Config.saveConfig();
				OPBoxGUI_Death(player);
			}
			else if(slot == 45)//���� ���
				OPBoxGUI_Setting(player);
			else
			{
				UserData_Object u = new UserData_Object();
				u.setType(player, "System");
				if(slot == 10)//����� �������� ��Ȱ
				{
					u.setString(player, (byte)1, "RO_S_H");
					player.sendMessage(ChatColor.GREEN+"[��Ȱ] : ������ �������� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"������"+ChatColor.GREEN+"�� ������ ��Ȱ �ϵ��� �ϰڽ��ϱ�?");
				}
				else if(slot == 12)//���ڸ����� ��Ȱ
				{
					u.setString(player, (byte)1, "RO_T_H");
					player.sendMessage(ChatColor.GREEN+"[��Ȱ] : ���ڸ����� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"������"+ChatColor.GREEN+"�� ������ ��Ȱ �ϵ��� �ϰڽ��ϱ�?");
				}
				else if(slot == 14)//���� ��û
				{
					u.setString(player, (byte)1, "RO_H_H");
					player.sendMessage(ChatColor.GREEN+"[��Ȱ] : ������ �޾� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"������"+ChatColor.GREEN+"�� ������ ��Ȱ �ϵ��� �ϰڽ��ϱ�?");
				}
				else if(slot == 16)//������ ���
				{
					u.setString(player, (byte)1, "RO_I_H");
					player.sendMessage(ChatColor.GREEN+"[��Ȱ] : �������� ����Ͽ� ��Ȱ�� ���, �� %�� "+ChatColor.YELLOW+"������"+ChatColor.GREEN+"�� ������ ��Ȱ �ϵ��� �ϰڽ��ϱ�?");
				}
				player.sendMessage(ChatColor.GRAY + "(�ּ� 1 ~ �ִ� 100)");
				player.closeInventory();
			}
		}
	}

	public void OPBoxGUI_RescueOrReviveClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();

		if(event.getClickedInventory().getTitle().compareTo("container.inventory") != 0)
		{
			if(slot != 4)
				event.setCancelled(true);
			if(slot == 8)//������
			{
				s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				if(slot == 0)//���� ���
					OPBoxGUI_Death(player);
			}
		}
	}
	
	
	public void OPBoxGUI_RescueOrReviveClose(InventoryCloseEvent event, String SubjectCode)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config =YC.getNewConfig("config.yml");
		if(event.getInventory().getItem(4) != null)
		{
			if(SubjectCode.compareTo("06") == 0)//��Ȱ
			{
				Config.set("Death.ReviveItem", event.getInventory().getItem(4));
				Main_ServerOption.DeathRevive = event.getInventory().getItem(4);
			}
			else
			{
				Config.set("Death.RescueItem", event.getInventory().getItem(4));
				Main_ServerOption.DeathRescue = event.getInventory().getItem(4);
			}
		}
		else
		{
			if(SubjectCode.compareTo("06") == 0)//��Ȱ
			{
				Config.set("Death.ReviveItem", null);
				Main_ServerOption.DeathRevive = null;
			}
			else
			{
				Config.set("Death.RescueItem", null);
				Main_ServerOption.DeathRescue = null;
			}
		}
		if(SubjectCode.compareTo("06") == 0)//��Ȱ
			event.getPlayer().sendMessage(ChatColor.GREEN+"[SYSTEM] : ��Ȱ ������ ������ �Ϸ�Ǿ����ϴ�!");
		else
			event.getPlayer().sendMessage(ChatColor.GREEN+"[SYSTEM] : ���� ������ ������ �Ϸ�Ǿ����ϴ�!");
		Config.saveConfig();
		new GBD_RPG.Effect.Effect_Sound().SP((Player)event.getPlayer(), Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
		return;
	}
}