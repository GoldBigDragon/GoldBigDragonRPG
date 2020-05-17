package user;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import battle.BattleCalculator;
import effect.SoundEffect;
import skill.UserSkillGui;
import util.GuiUtil;
import util.YamlLoader;

public class StatsGui extends GuiUtil
{
	//���� GUIâ�� 1 �������� ������ �ִ� �޼ҵ�//
	public void StatusGUI(Player player)
	{
		String UniqueCode = "��0��0��0��0��0��r";
	    YamlLoader Config = new YamlLoader();
	    Config.getConfig("config.yml");
		
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "��0����");

		removeFlagStack("��f����", 160,4,1,Arrays.asList("��7������ Ȯ���մϴ�."), 0, inv);
		removeFlagStack("��f��ų", 403,0,1,Arrays.asList("��7��ų�� Ȯ���մϴ�."), 9, inv);
		removeFlagStack("��f����Ʈ", 358,0,1,Arrays.asList("��7���� �������� ����Ʈ�� Ȯ���մϴ�."), 18, inv);
		removeFlagStack("��f�ɼ�", 145,0,1,Arrays.asList("��7��Ÿ ������ �մϴ�."), 27, inv);
		removeFlagStack("��f��Ÿ", 354,0,1,Arrays.asList("��7��Ÿ ������ Ȯ���մϴ�."), 36, inv);
		
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 1, inv);
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 7, inv);
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 10, inv);
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 16, inv);
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 19, inv);
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 25, inv);
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 28, inv);
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 34, inv);
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 37, inv);
		removeFlagStack("��c ", 66,0,1,Arrays.asList(""), 43, inv);
		
		ItemStack EXIT = new ItemStack(Material.WOOD_DOOR, 1);
		ItemMeta EXIT_BUTTON = EXIT.getItemMeta();
		EXIT_BUTTON.setDisplayName("��f��l�ݱ�");
		EXIT_BUTTON.setLore(Arrays.asList("��7â�� �ݽ��ϴ�."));
		EXIT.setItemMeta(EXIT_BUTTON);
		inv.setItem(26, EXIT);

		int statPoint = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint();
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System"))
		{
			removeFlagStack("��a    [��f��l���¡�a]", 397,3,1,
					Arrays.asList("��f[����] : ��l" + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level(),
							"��f[���� ����] : ��l" + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel(),
							"��f[����ġ] : ��l" + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP() + " / " + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP(),
							"��a[���� ����Ʈ] : ��f" + ChatColor.BOLD + statPoint,
							"��b[��ų ����Ʈ] : ��f" + ChatColor.BOLD + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint()), 13, inv);
		}
		else
		{
		    YamlLoader PlayerSkillYML = new YamlLoader();
			PlayerSkillYML.getConfig("Skill/PlayerData/"+player.getUniqueId()+".yml");
			removeFlagStack("��a       [��f��l���¡�a]", 397,3,1,
					Arrays.asList("��f[����] : ��l" + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level(),
							"��f[����] : ��l" + PlayerSkillYML.getString("Job.Type"),
							"��f[����ġ] : ��l" + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP() + " / " + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP(),
							"��a[���� ����Ʈ] : ��f" + ChatColor.BOLD + statPoint,
							"��b[��ų ����Ʈ] : ��f" + ChatColor.BOLD + main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint()), 13, inv);
		}
		
		int DefaultDamage = 0;
		if(player.getInventory().getItemInMainHand().hasItemMeta())
		{
			if(player.getInventory().getItemInMainHand().getItemMeta().hasLore())
			{
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains(main.MainServerOption.damage+" : ") == true)
				{
					switch(player.getInventory().getItemInMainHand().getType())
					{
					case WOOD_SPADE :
					case GOLD_SPADE :
					case WOOD_PICKAXE :
					case GOLD_PICKAXE:
						DefaultDamage += 2;
						break;
					case STONE_SPADE:
					case STONE_PICKAXE:
						DefaultDamage += 3;
						break;
					case IRON_SPADE:
					case WOOD_SWORD:
					case GOLD_SWORD:
					case IRON_PICKAXE:
						DefaultDamage += 4;
						break;
					case DIAMOND_SPADE:
					case STONE_SWORD:
					case DIAMOND_PICKAXE:
						DefaultDamage += 5;
						break;
					case IRON_SWORD:
						DefaultDamage += 6;
						break;
					case WOOD_AXE:
					case GOLD_AXE:
					case DIAMOND_AXE:
					case DIAMOND_SWORD:
						DefaultDamage += 7;
						break;
					case STONE_AXE:
					case IRON_AXE:
						DefaultDamage += 9;
						break;
					}
				}
			}
		}
		int EquipmentStat = BattleCalculator.getPlayerEquipmentStat(player, "STR", false, null, false)[0];
		int PlayerStat = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
		if(PlayerStat > main.MainServerOption.maxSTR)
			PlayerStat = main.MainServerOption.maxSTR;
		String Additional = "��c��l"+(BattleCalculator.getCombatDamage(player,DefaultDamage,PlayerStat, true)) + " ~ " + (BattleCalculator.getCombatDamage(player,DefaultDamage, PlayerStat, false));
		String CurrentStat;
		if(EquipmentStat == 0)
			CurrentStat = "��f��l"+ PlayerStat;
		else if(EquipmentStat > 0)
			CurrentStat = "��e��l"+ (PlayerStat + EquipmentStat) +"��f("+ PlayerStat +")";
		else
			CurrentStat = "��c��l"+(PlayerStat + EquipmentStat) +"��f("+ PlayerStat+")";
		String lore = main.MainServerOption.STR_Lore;
		lore = LineUp(CurrentStat, (byte) (main.MainServerOption.statSTR.length()+20))+"%enter%"+lore.replace("%stat%", main.MainServerOption.statSTR)
				+"%enter%��b��l[�߰� ���� ���ݷ�]%enter%"+LineUp(Additional, (byte) 24);
		
		removeFlagStack("��4"+ LineUp("��c[��f��l"+main.MainServerOption.statSTR+"��c]", (byte) 24), 267,0,1,
				Arrays.asList(lore.split("%enter%")), 20, inv);

		int DEX = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
		EquipmentStat=BattleCalculator.getPlayerEquipmentStat(player, "DEX", false, null, false)[0];
		if(DEX > main.MainServerOption.maxDEX)
			DEX = main.MainServerOption.maxDEX;
		Additional = "��c��l" + BattleCalculator.returnRangeDamageValue(player, DEX, 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(player, DEX, 0, false);
		if(EquipmentStat == 0)
			CurrentStat = "��f��l"+ DEX;
		else if(EquipmentStat > 0)
			CurrentStat = "��e��l"+ (DEX + EquipmentStat) +"��f("+ DEX+")";
		else
			CurrentStat = "��c��l"+(DEX + EquipmentStat) +"��f("+ DEX+")";

		lore = main.MainServerOption.DEX_Lore;
		lore = LineUp(CurrentStat, (byte) (main.MainServerOption.statDEX.length()+20))+"%enter%"+lore.replace("%stat%", main.MainServerOption.statDEX)
					+"%enter%��b��l[�߰� ���Ÿ� ���ݷ�]%enter%"+LineUp(Additional, (byte) 24);
			
		removeFlagStack(LineUp("��a[��f��l"+main.MainServerOption.statDEX+"��a]", (byte) 24), 261,0,1,
				Arrays.asList(lore.split("%enter%")), 21, inv);
		
		int INT = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
		if(INT > main.MainServerOption.maxINT)
			INT = main.MainServerOption.maxINT;
		EquipmentStat=BattleCalculator.getPlayerEquipmentStat(player, "INT", false, null, false)[0];
		Additional = "��c��l" + ((INT+BattleCalculator.getPlayerEquipmentStat(player, "INT", false, null, false)[0])*0.6+100) + " %";
		if(EquipmentStat == 0)
			CurrentStat = "��f��l"+ INT;
		else if(EquipmentStat > 0)
			CurrentStat = "��e��l"+ (INT + EquipmentStat) +"��f("+ INT+")";
		else
			CurrentStat = "��c��l"+(INT + EquipmentStat) +"��f("+ INT +")";

		lore = main.MainServerOption.INT_Lore;
		lore = LineUp(CurrentStat, (byte) (main.MainServerOption.statINT.length()+20))+"%enter%"+lore.replace("%stat%", main.MainServerOption.statINT)
					+"%enter%��b��l[�߰� ��ų ���ݷ�]%enter%"+LineUp(Additional, (byte) 24);
			
		removeFlagStack(LineUp("��b[��f��l"+main.MainServerOption.statINT+"��b]",(byte) 24), 369,0,1,
				Arrays.asList(lore.split("%enter%")), 22, inv);

		int WILL = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
		if(WILL > main.MainServerOption.maxWILL)
			WILL = main.MainServerOption.maxWILL;
		EquipmentStat=BattleCalculator.getPlayerEquipmentStat(player, "WILL", false, null, false)[0];
		Additional = "��c��l" + ((WILL+BattleCalculator.getPlayerEquipmentStat(player, "WILL", false, null, false)[0])*0.6+100) + " %";
		if(EquipmentStat == 0)
			CurrentStat = "��f��l"+ WILL;
		else if(EquipmentStat > 0)
			CurrentStat = "��e��l"+ (WILL + EquipmentStat) +"��f("+ WILL+")";
		else
			CurrentStat = "��c��l"+(WILL + EquipmentStat) +"��f("+ WILL+")";

		lore = main.MainServerOption.WILL_Lore;
		lore = LineUp(CurrentStat, (byte) (main.MainServerOption.statWILL.length()+20))+"%enter%"+lore.replace("%stat%", main.MainServerOption.statWILL)
					+"%enter%��b��l[�߰� ��ų ���ݷ�]%enter%"+LineUp(Additional, (byte) 24);
			
		removeFlagStack(LineUp("��7[��f��l"+main.MainServerOption.statWILL+"��7]",(byte) 24), 370,0,1,
				Arrays.asList(lore.split("%enter%")), 23, inv);
		
		int LUK = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
		if(LUK > main.MainServerOption.maxLUK)
			LUK = main.MainServerOption.maxLUK;
		EquipmentStat=BattleCalculator.getPlayerEquipmentStat(player, "LUK", false, null, false)[0];
		if(EquipmentStat == 0)
			CurrentStat = "��f��l"+ LUK;
		else if(EquipmentStat > 0)
			CurrentStat = "��e��l"+ (LUK + EquipmentStat) +"��f("+ LUK+")";
		else
			CurrentStat = "��c��l"+(LUK + EquipmentStat) +"��f("+ LUK+")";

		lore = main.MainServerOption.LUK_Lore;
		lore = LineUp(CurrentStat, (byte) (main.MainServerOption.statLUK.length()+20))+"%enter%"+lore.replace("%stat%", main.MainServerOption.statLUK)
					+"%enter%";
			
		removeFlagStack(LineUp("��e[��f��l"+main.MainServerOption.statLUK+"��e]",(byte) 24), 322,0,1,
				Arrays.asList(lore.split("%enter%")), 24, inv);


		if(statPoint > 0)
		{
			removeFlagStack("��6    [��f��l"+main.MainServerOption.statSTR+" ��¡�6]", 399,0,1,
					Arrays.asList("��7"+main.MainServerOption.statSTR+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.","��7���� ���� ����Ʈ : "+statPoint), 29, inv);
			removeFlagStack("��6    [��f��l"+main.MainServerOption.statDEX+" ��¡�6]", 399,0,1,
					Arrays.asList("��7"+main.MainServerOption.statDEX+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.","��7���� ���� ����Ʈ : "+statPoint), 30, inv);
			removeFlagStack("��6    [��f��l"+main.MainServerOption.statINT+" ��¡�6]", 399,0,1,
					Arrays.asList("��7"+main.MainServerOption.statINT+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.","��7���� ���� ����Ʈ : "+statPoint), 31, inv);
			removeFlagStack("��6    [��f��l"+main.MainServerOption.statWILL+" ��¡�6]", 399,0,1,
					Arrays.asList("��7"+main.MainServerOption.statWILL+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.","��7���� ���� ����Ʈ : "+statPoint), 32, inv);
			removeFlagStack("��6    [��f��l"+main.MainServerOption.statLUK+" ��¡�6]", 399,0,1,
					Arrays.asList("��7"+main.MainServerOption.statLUK+" ������ �Ѵܰ� ��� ��ŵ�ϴ�.","��7���� ���� ����Ʈ : "+statPoint), 33, inv);
		}
		removeFlagStack("��7    [��f��l����7]", 307,0,1,
				Arrays.asList("��f���� ��� : ��f" +(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEF()+BattleCalculator.getPlayerEquipmentStat(player, "���", false, null, false)[0]),
						"��7�߰� ���� ��ȣ : ��f" + (main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Protect()+BattleCalculator.getPlayerEquipmentStat(player, "��ȣ", false, null, false)[0]),
						"��b�߰� ���� ��� : ��f" + (main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_DEF()+BattleCalculator.getMagicDEF(player,INT)),
						"��3�߰� ���� ��ȣ : ��f" + (main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_Protect()+BattleCalculator.getMagicProtect(player, INT))), 38, inv);

		removeFlagStack("��c    [��f��l�����c]", 409,0,1,
				Arrays.asList("��c�߰� ���� ��� ���� : ��f" + (main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEFcrash()+BattleCalculator.getDEFcrash(player, DEX)),
						"��9�߰� ���� ��� ���� : ��f" + (main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MagicDEFcrash()+BattleCalculator.getMagicDEFcrash(player, INT))), 39, inv);
		
		removeFlagStack("��a    [��f��l��ȸ��a]", 377,0,1,
				Arrays.asList("��a�߰� �뷱�� : ��f" + BattleCalculator.getBalance(player, DEX, main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Balance())+"%",
						"��e�߰� ũ��Ƽ�� : ��f" + BattleCalculator.getCritical(player,LUK, WILL,main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Critical())+"%"), 42, inv);
		
		player.openInventory(inv);
	}
	
	//���� GUIâ ���� �������� ������ ��, �ش� �����ܿ� ����� �ִ� �޼ҵ�1   -���� GUI, ���ǹڽ�, Ŀ���� ����GUI-//
	public void StatusInventoryclick(InventoryClickEvent event)
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
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot >= 29 && slot <= 33)
			{
				boolean isOk = false;
				if(slot == 29)
					isOk = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() < main.MainServerOption.maxSTR;
				else if(slot == 30)
					isOk = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() < main.MainServerOption.maxDEX;
				else if(slot == 31)
					isOk = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() < main.MainServerOption.maxINT;
				else if(slot == 32)
					isOk = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() < main.MainServerOption.maxWILL;
				else if(slot == 33)
					isOk = main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() < main.MainServerOption.maxLUK;
				
				if(isOk)
				{
					main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
					if(slot == 29)
						main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(1);
					else if(slot == 30)
						main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(1);
					else if(slot == 31)
						main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(1);
					else if(slot == 32)
						main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(1);
					else if(slot == 33)
						main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(1);
					SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					new effect.SendPacket().sendActionBar(player, "��c��l[�ش� �ɷ��� �� �̻� ��½�ų �� �����ϴ�!]", false);
				}
				StatusGUI(player);
			}
			else if(slot == 9)
				new UserSkillGui().mainSkillsListGUI(player, (short) 0);
			else if(slot == 18)
				new quest.QuestGui().MyQuestListGUI(player, (short) 0);
			else if(slot == 27)
				new user.OptionGui().optionGUI(player);
			else if(slot == 36)
				new EtcGui().ETCGUI_Main(player);
		}
		return;
	}
	
	
	public String LineUp(String RawString,byte size)
	{
		if(RawString.length()>=size)
			return RawString;
		else
		{
			short spaceSize = (short) (size - RawString.length());
			StringBuffer TempString = new StringBuffer();
			for(int count = 0; count < spaceSize/2; count++)
				TempString.append(" ");
			TempString.append(RawString);
			for(int count = 0; count < spaceSize/2; count++)
				TempString.append(" ");
			return TempString.toString();
		}
	}
}
