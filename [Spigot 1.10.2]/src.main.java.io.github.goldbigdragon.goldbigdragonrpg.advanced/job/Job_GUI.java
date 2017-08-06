package job;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import admin.OPbox_GUI;
import effect.SoundEffect;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Job_GUI extends Util_GUI
{
	public void ChooseSystemGUI(Player player)
	{
		String UniqueCode = "��0��0��6��0��0��r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "��0�ý��� ����");
		
		Stack2("��6��l������", 346,0,1,Arrays.asList(ChatColor.GRAY + "������ ���丮�ʹ� �ٸ���",ChatColor.GRAY + "�����Ӱ� ��ų�� ���� �� �� �ֽ��ϴ�.",ChatColor.GRAY+"���� ������ ���� ������",ChatColor.GRAY+"ī�װ������� ��ų�� �����ϴ�.","",ChatColor.GREEN+"������ : "+ChatColor.YELLOW+"||||||||||||||||||||",ChatColor.GREEN+"�밡�� : "+ChatColor.YELLOW+"||||||||||||||||||||","",ChatColor.RED+"[���� ������ �������� ��츸 ����]"), 12, inv);
		Stack2("��c��l������ ���丮", 40,0,1,Arrays.asList(ChatColor.GRAY + "������ʹ� �ٸ���",ChatColor.GRAY + "�������� ��ų�� �����Ǿ� �ֽ��ϴ�.",ChatColor.GRAY+"���� ������ �����ϱ� ������",ChatColor.GRAY+"������ �� �±޺��� ��ų�� �����ϴ�.","",ChatColor.GREEN+"������ : "+ChatColor.YELLOW+"||||||"+ChatColor.GRAY+"||||||||||||||",ChatColor.GREEN+"�밡�� : "+ChatColor.YELLOW+"|||||||||||"+ChatColor.GRAY+"|||||||||","",ChatColor.RED+"[���� ������ ������ ���丮�� ��츸 ����]"), 14, inv);
		
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 18, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "�۾� ������ â�� �ݽ��ϴ�."), 26, inv);
		player.openInventory(inv);
	}

	public void MapleStory_ChooseJob(Player player, short page)
	{
	  	YamlLoader jobYaml = new YamlLoader();
	  	YamlLoader configYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");
		configYaml.getConfig("config.yml");

		String UniqueCode = "��0��0��6��0��1��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0[MapleStory] ��ü ���� ��� : " + (page+1));

		String[] jobList = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < jobList.length;count++)
		{
			int jobMaxLevel = jobYaml.getConfigurationSection("MapleStory."+jobList[count]).getKeys(false).size();
			
			if(count > jobList.length || loc >= 45) break;
			
			if(jobList[count].equalsIgnoreCase(configYaml.getString("Server.DefaultJob")))
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + jobList[count], 403,0,1,Arrays.asList(ChatColor.DARK_AQUA+"�ִ� �±� : " + ChatColor.WHITE+jobMaxLevel+ChatColor.DARK_AQUA+"�� �±�","",ChatColor.YELLOW+"[��Ŭ���� �±� ����]","��e��l[���� �⺻ ����]"), loc, inv);
			else
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + jobList[count], 340,0,1,Arrays.asList(ChatColor.DARK_AQUA+"�ִ� �±� : " + ChatColor.WHITE+jobMaxLevel+ChatColor.DARK_AQUA+"�� �±�","",ChatColor.YELLOW+"[��Ŭ���� �±� ����]",ChatColor.YELLOW+"[Shift + ��Ŭ���� ���� �⺻ ���� ����]",ChatColor.RED+"[Shift + ��Ŭ���� ���� ����]",ChatColor.RED+"�÷��̾� ���� �������� ���� �������ϴ�."), loc, inv);
			
			loc++;
		}
		
		if(jobList.length-(page*44)>45)
		Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2("��f��l�� ����", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ������ �����մϴ�."), 49, inv);
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void MapleStory_JobSetting(Player player, String JobName)
	{
	  	YamlLoader jobYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");

		String UniqueCode = "��0��0��6��0��2��r";
		Inventory inv = Bukkit.createInventory(null, 27, UniqueCode + "��0[MapleStory] ���� ����");

		String[] jobList = jobYaml.getConfigurationSection("MapleStory."+JobName).getKeys(false).toArray(new String[0]);

		for(int count = 0; count < jobList.length;count++)
		{
			short JobSkillList= (short) jobYaml.getConfigurationSection("MapleStory."+JobName+"."+jobList[count]+".Skill").getKeys(false).size();
			
			short IconID = (short) jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".IconID");
			byte IconData = (byte) jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".IconData");
			byte IconAmount = (byte) jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".IconAmount");

			int NeedLevel = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedLV");
			int NeedSTR = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedSTR");
			int NeedDEX = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedDEX");
			int NeedINT = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedINT");
			int NeedWILL = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedWILL");
			int NeedLUK = jobYaml.getInt("MapleStory."+JobName+"."+jobList[count]+".NeedLUK");
			String PrevJob = jobYaml.getString("MapleStory."+JobName+"."+jobList[count]+".PrevJob");
			if(PrevJob.equalsIgnoreCase("null") == true)
				PrevJob = "����";
			if(count == 0)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + jobList[count] , IconID,IconData,IconAmount,Arrays.asList(ChatColor.DARK_AQUA+"��ϵ� ��ų �� : "+ChatColor.WHITE+JobSkillList+ ChatColor.DARK_AQUA+"��",ChatColor.GREEN+"�±� �ʿ� ���� : "+ChatColor.WHITE+NeedLevel+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.STR+" : "+ChatColor.WHITE+NeedSTR+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.DEX+" : "+ChatColor.WHITE+NeedDEX+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.INT+" : "+ChatColor.WHITE+NeedINT+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.WILL+" : "+ChatColor.WHITE+NeedWILL+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.LUK+" : "+ChatColor.WHITE+NeedLUK+ChatColor.GREEN+" �̻�"	,ChatColor.GREEN+"���� �±� �ܰ� : "+ChatColor.WHITE+PrevJob,"",ChatColor.YELLOW+"[�� Ŭ���� ���� ��ų ���]",ChatColor.YELLOW+"[�� Ŭ���� ���� ��ų Ȯ��]",ChatColor.YELLOW+"[Shift + �� Ŭ���� �±� ���� ����]","��e��l[�⺻ Ŭ����]"), count, inv);
			else
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + jobList[count], IconID,IconData,IconAmount,Arrays.asList(ChatColor.DARK_AQUA+"��ϵ� ��ų �� : "+ChatColor.WHITE+JobSkillList+ ChatColor.DARK_AQUA+"��",ChatColor.GREEN+"�±� �ʿ� ���� : "+ChatColor.WHITE+NeedLevel+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.STR+" : "+ChatColor.WHITE+NeedSTR+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.DEX+" : "+ChatColor.WHITE+NeedDEX+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.INT+" : "+ChatColor.WHITE+NeedINT+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.WILL+" : "+ChatColor.WHITE+NeedWILL+ChatColor.GREEN+" �̻�",ChatColor.GREEN+"�±� �ʿ� "+main.Main_ServerOption.LUK+" : "+ChatColor.WHITE+NeedLUK+ChatColor.GREEN+" �̻�"	,ChatColor.GREEN+"���� �±� �ܰ� : "+ChatColor.WHITE+PrevJob,"",ChatColor.YELLOW+"[�� Ŭ���� ���� ��ų ���]",ChatColor.YELLOW+"[�� Ŭ���� ���� ��ų Ȯ��]",ChatColor.YELLOW+"[Shift + �� Ŭ���� �±� ���� ����]",ChatColor.RED+"[Shift + ��Ŭ���� �±� ����]",ChatColor.RED+"�÷��̾ �������� ���� �������ϴ�."), count, inv);
		}
		
		Stack2("��f��l�� �±�", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� �±� Ŭ������ ����ϴ�."), 22, inv);
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 18, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+JobName), 26, inv);
		player.openInventory(inv);
	}
	
	public void Mabinogi_ChooseCategory(Player player, short page)
	{
	  	YamlLoader jobYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");

		String UniqueCode = "��0��0��6��0��3��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0[Mabinogi] ��ü ī�װ��� ��� : " + (page+1));

		String[] jobList = jobYaml.getConfigurationSection("Mabinogi").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < jobList.length;count++)
		{
			if(jobList[count].compareTo("Added")!=0)
			{
				if(count > jobList.length || loc >= 45) break;
				short SkillNumber = (short) jobYaml.getConfigurationSection("Mabinogi."+jobList[count]).getKeys(false).size();
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + jobList[count], 403,0,1,Arrays.asList(ChatColor.DARK_AQUA+"��ϵ� ��ų : " +ChatColor.WHITE+SkillNumber+ChatColor.DARK_AQUA+" ��","",ChatColor.YELLOW+"[�� Ŭ���� ��ų ���]",ChatColor.YELLOW+"[Shift + �� Ŭ���� ��ų ����]",ChatColor.RED+"[Shift + ��Ŭ���� ī�װ��� ����]"), loc, inv);
				loc++;
			}
		}
		
		if(jobList.length-(page*44)>45)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2("��f��l�� ī�װ���", 386,0,1,Arrays.asList(ChatColor.GRAY + "���ο� ī�װ����� �����մϴ�."), 49, inv);
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void Mabinogi_SkillSetting(Player player, short page, String CategoriName)
	{
	  	YamlLoader jobYaml = new YamlLoader();
	  	jobYaml.getConfig("Skill/JobList.yml");

		String UniqueCode = "��0��0��6��0��4��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0[Mabinogi] ��ų ���� ��� : " + (page+1));

	  	YamlLoader skillYaml = new YamlLoader();
		skillYaml.getConfig("Skill/SkillList.yml");
		String[] mabinogiCategoriList = jobYaml.getConfigurationSection("Mabinogi."+CategoriName).getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < mabinogiCategoriList.length;count++)
		{
			boolean SkillPublic = jobYaml.getBoolean("Mabinogi."+CategoriName+"."+mabinogiCategoriList[count]);
			if(count > mabinogiCategoriList.length || loc >= 45) break;

			short IconID = (short) skillYaml.getInt(mabinogiCategoriList[count]+".ID");
			byte IconData = (byte) skillYaml.getInt(mabinogiCategoriList[count]+".DATA");
			byte IconAmount = (byte) skillYaml.getInt(mabinogiCategoriList[count]+".Amount");
			
			if(SkillPublic == true)
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + mabinogiCategoriList[count], IconID,IconData,IconAmount,Arrays.asList("",ChatColor.DARK_AQUA+"[   �⺻ ��ų   ]",ChatColor.GRAY+"���� ���ӽ� �⺻������",ChatColor.GRAY+"�־����� ��ų�Դϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� ���� ��ų ��ȯ]",ChatColor.RED+"[Shift + ��Ŭ���� ��ų ����]","",ChatColor.RED+"     ��  ����  ��     ",ChatColor.RED+"���� ��ų���� ��ȯ �ϴ���",ChatColor.RED+"���� �ش� ��ų�� �˰� �ִ�",ChatColor.RED+"������ ��ų�� �������� ������,",ChatColor.RED+"�ű� �������� ��ų �ڵ� ���游",ChatColor.RED+"�Ұ��� �ϰ� �˴ϴ�."), loc, inv);
			else
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + mabinogiCategoriList[count], IconID,IconData,IconAmount,Arrays.asList("",ChatColor.DARK_PURPLE+"[   ���� ��ų   ]",ChatColor.GRAY+"å�� �аų� ����Ʈ ������ ���Ͽ�",ChatColor.GRAY+"���� �� �ִ� ��ų�Դϴ�.","",ChatColor.YELLOW+"[�� Ŭ���� �⺻ ��ų ��ȯ]",ChatColor.RED+"[Shift + ��Ŭ���� ��ų ����]","",ChatColor.RED+"     ��  ����  ��     ",ChatColor.RED+"�⺻ ��ų���� ��ȯ ��ų ���",ChatColor.RED+"���� ������ ��� �ο�����,",ChatColor.RED+"�׸��� ��ȯ ���Ŀ� ������ ���",ChatColor.RED+"�ű� �����鿡�� �ش� ��ų�� �־����ϴ�.",ChatColor.RED+"[���� ������ ���� ����� �� �߻�]"), loc, inv);
			loc++;
		}
		
		if(mabinogiCategoriList.length-(page*44)>45)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK + CategoriName), 53, inv);
		player.openInventory(inv);
	}
	
	public void AddedSkillsListGUI(Player player, int page, String JobName, String JobNick)
	{
		YamlLoader jobYaml = new YamlLoader();
		YamlLoader skillYaml = new YamlLoader();
	  	skillYaml.getConfig("Skill/SkillList.yml");
		jobYaml.getConfig("Skill/JobList.yml");

		String UniqueCode = "��0��0��6��0��5��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0��ϵ� ��ų ��� : " + (page+1));

		String[] skillList = jobYaml.getConfigurationSection("MapleStory."+JobName+"."+JobNick+".Skill").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < skillList.length;count++)
		{
			if(count > skillList.length || loc >= 45) break;
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + skillList[count].toString(),  skillYaml.getInt(skillList[count]+".ID"),skillYaml.getInt(skillList[count]+".DATA"),skillYaml.getInt(skillList[count]+".Amount"),Arrays.asList("",ChatColor.RED+"[Shift + �� Ŭ���� ��ų ����]"), loc, inv);

			loc++;
		}
		
		if(skillList.length-(page*44)>45)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2("��f��l���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�.",ChatColor.BLACK+JobNick), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�.",ChatColor.BLACK+JobName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	
	public void ChooseSystemGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)//������
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 18)//���� ���
				new OPbox_GUI().OPBoxGUI_Main(player,(byte) 2);
			else if(slot == 12)//������ Ÿ�� ī�װ��� ���
				Mabinogi_ChooseCategory(player,(short) 0);
			else if(slot == 14)//�����ý��丮 Ÿ�� ���� ���
				MapleStory_ChooseJob(player,(short) 0);
		}
	}

	public void MapleStory_ChooseJobClick(InventoryClickEvent event)
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
				ChooseSystemGUI(player);
			else if(slot == 48)//���� ������
				MapleStory_ChooseJob(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 49)//�� ����
			{
				player.closeInventory();
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[����] : ���ο� ���� �̸��� ������ �ּ���!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "CJ");
			}
			else if(slot == 50)//���� ������
				MapleStory_ChooseJob(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				if(event.isLeftClick()==true&&event.isRightClick()==false)
					MapleStory_JobSetting(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				if(event.getCurrentItem().getItemMeta().getLore().toString().contains("����")==true&&event.isShiftClick()==true
				&&event.isLeftClick()==true)
				{
					configYaml.set("Server.DefaultJob", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					configYaml.saveConfig();
					MapleStory_ChooseJob(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
				}
				else if(event.getCurrentItem().getItemMeta().getLore().toString().contains("����")==true&&event.isShiftClick()==true
				&&event.isRightClick()==true)
				{
					if(configYaml.getString("Server.DefaultJob").equalsIgnoreCase(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED + "[����] : ���� �⺻ ������ ������ �� �����ϴ�!");
					}
					else
					{
						YamlLoader jobYaml = new YamlLoader();
						SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						jobYaml.getConfig("Skill/JobList.yml");
						jobYaml.removeKey("MapleStory."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						jobYaml.saveConfig();
						MapleStory_ChooseJob(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1));
						configYaml.set("Time.LastSkillChanged", new util.Util_Number().RandomNum(0, 100000)-new util.Util_Number().RandomNum(0, 100000));
						configYaml.saveConfig();
						new job.Job_Main().AllPlayerFixAllSkillAndJobYML();
					}
				}
			}
		}
	}

	public void MapleStory_JobSettingClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)//������
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String JobName = ChatColor.stripColor(event.getInventory().getItem(26).getItemMeta().getLore().get(1));
			YamlLoader jobYaml = new YamlLoader();
			jobYaml.getConfig("Skill/JobList.yml");
			
			if(slot == 18)//���� ���
				MapleStory_ChooseJob(player, (short) 0);
			else if(slot == 22)//�� �±�
			{
				short NowJobLV = (short) jobYaml.getConfigurationSection("MapleStory."+JobName).getKeys(false).size();
				if(NowJobLV == 18)
				{
					SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED + "[����] : �ִ� �±��� 18�� ���� �Դϴ�!");
					return;
				}
				else
				{
					player.closeInventory();
					player.sendMessage(ChatColor.LIGHT_PURPLE+"[����] : "+ChatColor.YELLOW + JobName+ChatColor.LIGHT_PURPLE+"�� �� �±� ���� �̸��� ������ �ּ���!");
					UserData_Object u = new UserData_Object();
					u.setType(player, "Job");
					u.setString(player, (byte)1, "CJL");
					u.setString(player, (byte)2, JobName);
				}
			}
			else
			{
				String JobNick = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				if(event.isLeftClick()==true&&event.isShiftClick()==false)
				{
					UserData_Object u = new UserData_Object();
					u.setType(player, "Job");
					u.setString(player, (byte)2, JobNick);
					u.setString(player, (byte)3, JobName);
					new skill.OPboxSkill_GUI().AllSkillsGUI(player,(short) 0, true,"Maple");
				}
				else if(event.isShiftClick()==true&&event.isLeftClick()==true)
				{
					player.closeInventory();
					player.sendMessage(ChatColor.LIGHT_PURPLE+"[����] : "+ChatColor.YELLOW + JobNick+ChatColor.LIGHT_PURPLE+"�� �±� �ʿ� ������ �Է� �ϼ���!");

					UserData_Object u = new UserData_Object();
					u.setType(player, "Job");
					u.setString(player, (byte)1, "JNL");
					u.setString(player, (byte)2, JobName);
					u.setString(player, (byte)3, JobNick);
					
				}
				else if(event.isShiftClick()==false&&event.isRightClick()==true)
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					AddedSkillsListGUI(player, 0, JobName, JobNick);
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					if(event.getSlot() == 0)
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED + "[����] : �⺻ Ŭ������ ������ �� �����ϴ�!");
						return;
					}
					else
					{
						SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						jobYaml.removeKey("MapleStory."+JobName+"."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						jobYaml.saveConfig();
						MapleStory_JobSetting(player, JobName);
						YamlLoader configYaml = new YamlLoader();
						configYaml.getConfig("config.yml");
						configYaml.set("Time.LastSkillChanged", new util.Util_Number().RandomNum(0, 100000)-new util.Util_Number().RandomNum(0, 100000));
						configYaml.saveConfig();
						new job.Job_Main().AllPlayerFixAllSkillAndJobYML();
					}
				}
			}
		}
	}

	public void AddedSkillsListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//������
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String JobName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			String JobNick = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			
			if(slot == 45)//���� ���
				MapleStory_JobSetting(player, JobName);
			else if(slot == 48)//���� ������
				AddedSkillsListGUI(player, page-1, JobName, JobNick);
			else if(slot == 50)//���� ������
				AddedSkillsListGUI(player, page+1, JobName, JobNick);
			else
			{
				if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					YamlLoader jobYaml = new YamlLoader();
					jobYaml.getConfig("Skill/JobList.yml");
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					jobYaml.removeKey("MapleStory."+JobName+"."+JobNick+".Skill."+ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					jobYaml.saveConfig();
					AddedSkillsListGUI(player, page, JobName, JobNick);
					YamlLoader configYaml = new YamlLoader();
					configYaml.getConfig("config.yml");
					configYaml.set("Time.LastSkillChanged", new util.Util_Number().RandomNum(0, 100000)-new util.Util_Number().RandomNum(0, 100000));
					configYaml.saveConfig();
					new job.Job_Main().AllPlayerFixAllSkillAndJobYML();
				}
			}
		}
	}
	
	public void Mabinogi_ChooseCategoryClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//������
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//���� ���
				ChooseSystemGUI(player);
			else if(slot == 48)//���� ������
				Mabinogi_ChooseCategory(player,(short) (page-1));
			else if(slot == 50)//���� ������
				Mabinogi_ChooseCategory(player,(short) (page+1));
			else if(slot == 49)//�� ī�װ���
			{
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[ī�װ���] : �� ī�װ����� �̸��� ������ �ּ���!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Job");
				u.setString(player, (byte)1, "CC");
				player.closeInventory();
			}
			else
			{
				String CategoriName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				if(event.isLeftClick()==true)
				{
					if(event.isShiftClick() == false)
					{
						skill.OPboxSkill_GUI OGUI = new skill.OPboxSkill_GUI();
						OGUI.AllSkillsGUI(player, (short) 0, true, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
					else
						Mabinogi_SkillSetting(player, (short) 0, CategoriName);
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					YamlLoader jobYaml = new YamlLoader();
					jobYaml.getConfig("Skill/JobList.yml");
					String[] addedSkillList = jobYaml.getConfigurationSection("Mabinogi.Added").getKeys(false).toArray(new String[0]);
					for(int count = 0; count <addedSkillList.length;count++)
					{
						if(jobYaml.getString("Mabinogi.Added."+addedSkillList[count]).equals(CategoriName))
							jobYaml.removeKey("Mabinogi.Added."+addedSkillList[count]);
					}
					jobYaml.removeKey("Mabinogi."+CategoriName);
					jobYaml.saveConfig();
					Mabinogi_ChooseCategory(player,page);
					YamlLoader configYaml = new YamlLoader();
					configYaml.getConfig("config.yml");
					configYaml.set("Time.LastSkillChanged", new util.Util_Number().RandomNum(0, 100000)-new util.Util_Number().RandomNum(0, 100000));
					configYaml.saveConfig();
					new job.Job_Main().AllPlayerFixAllSkillAndJobYML();
				}
			}
		}
	}
	
	public void Mabinogi_SkillSettingClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		int slot = event.getSlot();
		
		if(slot == 53)//������
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String CategoriName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			if(slot == 45)//���� ���
				Mabinogi_ChooseCategory(player,(short) 0);
			else if(slot == 48)//���� ������
				Mabinogi_SkillSetting(player,(short) page,CategoriName);
			else if(slot == 50)//���� ������
				Mabinogi_SkillSetting(player,(short) page,CategoriName);
			else
			{
				String SkillName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlLoader jobYaml = new YamlLoader();
				YamlLoader configYaml = new YamlLoader();
				jobYaml.getConfig("Skill/JobList.yml");
				configYaml.getConfig("config.yml");
				if(event.isLeftClick()==true)
				{
					if(jobYaml.getBoolean("Mabinogi."+CategoriName+"."+SkillName) == true)
					{
						jobYaml.set("Mabinogi."+CategoriName+"."+SkillName, false);
						jobYaml.saveConfig();
						Mabinogi_SkillSetting(player,(short) page,CategoriName);
					}
					else
					{
						jobYaml.set("Mabinogi."+CategoriName+"."+SkillName, true);
						jobYaml.saveConfig();
						Mabinogi_SkillSetting(player,(short) page,CategoriName);
						configYaml.set("Time.LastSkillChanged", new util.Util_Number().RandomNum(0, 100000)-new util.Util_Number().RandomNum(0, 100000));
						configYaml.saveConfig();
						new job.Job_Main().AllPlayerFixAllSkillAndJobYML();
					}
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					jobYaml.removeKey("Mabinogi.Added."+SkillName);
					jobYaml.removeKey("Mabinogi."+CategoriName+"."+SkillName);
					jobYaml.saveConfig();
					Mabinogi_SkillSetting(player, (short) page, CategoriName);
					configYaml.set("Time.LastSkillChanged", new util.Util_Number().RandomNum(0, 100000)-new util.Util_Number().RandomNum(0, 100000));
					configYaml.saveConfig();
					new job.Job_Main().AllPlayerFixAllSkillAndJobYML();
				}
			}
		}
	}
	
}