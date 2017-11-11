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

import battle.Battle_Calculator;
import effect.SoundEffect;
import skill.UserSkill_GUI;
import util.Util_GUI;
import util.YamlLoader;

public class Stats_GUI extends Util_GUI
{
	//스텟 GUI창의 1 페이지를 구성해 주는 메소드//
	public void StatusGUI(Player player)
	{
		String UniqueCode = "§0§0§0§0§0§r";
	    YamlLoader Config = new YamlLoader();
	    Config.getConfig("config.yml");
		
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§0스텟");

		Stack2("§f스텟", 160,4,1,Arrays.asList("§7스텟을 확인합니다."), 0, inv);
		Stack2("§f스킬", 403,0,1,Arrays.asList("§7스킬을 확인합니다."), 9, inv);
		Stack2("§f퀘스트", 358,0,1,Arrays.asList("§7현재 진행중인 퀘스트를 확인합니다."), 18, inv);
		Stack2("§f옵션", 145,0,1,Arrays.asList("§7기타 설정을 합니다."), 27, inv);
		Stack2("§f기타", 354,0,1,Arrays.asList("§7기타 내용을 확인합니다."), 36, inv);
		
		Stack2("§c ", 66,0,1,Arrays.asList(""), 1, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 7, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 10, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 16, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 19, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 25, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 28, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 34, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 37, inv);
		Stack2("§c ", 66,0,1,Arrays.asList(""), 43, inv);
		
		ItemStack EXIT = new ItemStack(Material.WOOD_DOOR, 1);
		ItemMeta EXIT_BUTTON = EXIT.getItemMeta();
		EXIT_BUTTON.setDisplayName("§f§l닫기");
		EXIT_BUTTON.setLore(Arrays.asList("§7창을 닫습니다."));
		EXIT.setItemMeta(EXIT_BUTTON);
		inv.setItem(26, EXIT);

		int StatPoint = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint();
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			Stack2("§a    [§f§l상태§a]", 397,3,1,
					Arrays.asList("§f[레벨] : §l" + main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level(),
							"§f[누적 레벨] : §l" + main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel(),
							"§f[경험치] : §l" + main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP() + " / " + main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP(),
							"§b[스킬 포인트] : §f" + ChatColor.BOLD + main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint()), 13, inv);
		}
		else
		{
		    YamlLoader PlayerSkillYML = new YamlLoader();
			PlayerSkillYML.getConfig("Skill/PlayerData/"+player.getUniqueId()+".yml");
			Stack2("§a       [§f§l상태§a]", 397,3,1,
					Arrays.asList("§f[레벨] : §l" + main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level(),
							"§f[직업] : §l" + PlayerSkillYML.getString("Job.Type"),
							"§f[경험치] : §l" + main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP() + " / " + main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP(),
							"§a[스텟 포인트] : §f" + ChatColor.BOLD + StatPoint,
							"§b[스킬 포인트] : §f" + ChatColor.BOLD + main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint()), 13, inv);
		}
		
		int DefaultDamage = 0;
		if(player.getInventory().getItemInMainHand().hasItemMeta() == true)
		{
			if(player.getInventory().getItemInMainHand().getItemMeta().hasLore() == true)
			{
				if(player.getInventory().getItemInMainHand().getItemMeta().getLore().toString().contains(main.Main_ServerOption.damage+" : ") == true)
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
		int EquipmentStat = Battle_Calculator.getPlayerEquipmentStat(player, "STR", false, null)[0];
		int PlayerStat = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
		if(PlayerStat > main.Main_ServerOption.maxSTR)
			PlayerStat = main.Main_ServerOption.maxSTR;
		String Additional = "§c§l"+(Battle_Calculator.CombatDamageGet(player,DefaultDamage,PlayerStat, true)) + " ~ " + (Battle_Calculator.CombatDamageGet(player,DefaultDamage, PlayerStat, false));
		String CurrentStat;
		if(EquipmentStat == 0)
			CurrentStat = "§f§l"+ PlayerStat;
		else if(EquipmentStat > 0)
			CurrentStat = "§e§l"+ (PlayerStat + EquipmentStat) +"§f("+ PlayerStat +")";
		else
			CurrentStat = "§c§l"+(PlayerStat + EquipmentStat) +"§f("+ PlayerStat+")";
		String lore = main.Main_ServerOption.STR_Lore;
		lore = LineUp(CurrentStat, (byte) (main.Main_ServerOption.statSTR.length()+20))+"%enter%"+lore.replace("%stat%", main.Main_ServerOption.statSTR)
				+"%enter%§b§l[추가 근접 공격력]%enter%"+LineUp(Additional, (byte) 24);
		
		Stack2("§4"+ LineUp("§c[§f§l"+main.Main_ServerOption.statSTR+"§4]", (byte) 24), 267,0,1,
				Arrays.asList(lore.split("%enter%")), 20, inv);

		int DEX = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
		EquipmentStat=Battle_Calculator.getPlayerEquipmentStat(player, "DEX", false, null)[0];
		if(DEX > main.Main_ServerOption.maxDEX)
			DEX = main.Main_ServerOption.maxDEX;
		Additional = "§c§l" + Battle_Calculator.returnRangeDamageValue(player, DEX, 0, true) + " ~ " + Battle_Calculator.returnRangeDamageValue(player, DEX, 0, false);
		if(EquipmentStat == 0)
			CurrentStat = "§f§l"+ DEX;
		else if(EquipmentStat > 0)
			CurrentStat = "§e§l"+ (DEX + EquipmentStat) +"§f("+ DEX+")";
		else
			CurrentStat = "§c§l"+(DEX + EquipmentStat) +"§f("+ DEX+")";

		lore = main.Main_ServerOption.DEX_Lore;
		lore = LineUp(CurrentStat, (byte) (main.Main_ServerOption.statDEX.length()+20))+"%enter%"+lore.replace("%stat%", main.Main_ServerOption.statDEX)
					+"%enter%§b§l[추가 원거리 공격력]%enter%"+LineUp(Additional, (byte) 24);
			
		Stack2(LineUp("§a[§f§l"+main.Main_ServerOption.statDEX+"§a]", (byte) 24), 261,0,1,
				Arrays.asList(lore.split("%enter%")), 21, inv);
		
		int INT = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
		if(INT > main.Main_ServerOption.maxINT)
			INT = main.Main_ServerOption.maxINT;
		EquipmentStat=Battle_Calculator.getPlayerEquipmentStat(player, "INT", false, null)[0];
		Additional = "§c§l" + ((INT+Battle_Calculator.getPlayerEquipmentStat(player, "INT", false, null)[0])*0.6+100) + " %";
		if(EquipmentStat == 0)
			CurrentStat = "§f§l"+ INT;
		else if(EquipmentStat > 0)
			CurrentStat = "§e§l"+ (INT + EquipmentStat) +"§f("+ INT+")";
		else
			CurrentStat = "§c§l"+(INT + EquipmentStat) +"§f("+ INT +")";

		lore = main.Main_ServerOption.INT_Lore;
		lore = LineUp(CurrentStat, (byte) (main.Main_ServerOption.statINT.length()+20))+"%enter%"+lore.replace("%stat%", main.Main_ServerOption.statINT)
					+"%enter%§b§l[추가 스킬 공격력]%enter%"+LineUp(Additional, (byte) 24);
			
		Stack2(LineUp("§b[§f§l"+main.Main_ServerOption.statINT+"§b]",(byte) 24), 369,0,1,
				Arrays.asList(lore.split("%enter%")), 22, inv);

		int WILL = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
		if(WILL > main.Main_ServerOption.maxWILL)
			WILL = main.Main_ServerOption.maxWILL;
		EquipmentStat=Battle_Calculator.getPlayerEquipmentStat(player, "WILL", false, null)[0];
		Additional = "§c§l" + ((WILL+Battle_Calculator.getPlayerEquipmentStat(player, "WILL", false, null)[0])*0.6+100) + " %";
		if(EquipmentStat == 0)
			CurrentStat = "§f§l"+ WILL;
		else if(EquipmentStat > 0)
			CurrentStat = "§e§l"+ (WILL + EquipmentStat) +"§f("+ WILL+")";
		else
			CurrentStat = "§c§l"+(WILL + EquipmentStat) +"§f("+ WILL+")";

		lore = main.Main_ServerOption.WILL_Lore;
		lore = LineUp(CurrentStat, (byte) (main.Main_ServerOption.statWILL.length()+20))+"%enter%"+lore.replace("%stat%", main.Main_ServerOption.statWILL)
					+"%enter%§b§l[추가 스킬 공격력]%enter%"+LineUp(Additional, (byte) 24);
			
		Stack2(LineUp("§7[§f§l"+main.Main_ServerOption.statWILL+"§7]",(byte) 24), 370,0,1,
				Arrays.asList(lore.split("%enter%")), 23, inv);
		
		int LUK = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
		if(LUK > main.Main_ServerOption.maxLUK)
			LUK = main.Main_ServerOption.maxLUK;
		EquipmentStat=Battle_Calculator.getPlayerEquipmentStat(player, "LUK", false, null)[0];
		if(EquipmentStat == 0)
			CurrentStat = "§f§l"+ LUK;
		else if(EquipmentStat > 0)
			CurrentStat = "§e§l"+ (LUK + EquipmentStat) +"§f("+ LUK+")";
		else
			CurrentStat = "§c§l"+(LUK + EquipmentStat) +"§f("+ LUK+")";

		lore = main.Main_ServerOption.LUK_Lore;
		lore = LineUp(CurrentStat, (byte) (main.Main_ServerOption.statLUK.length()+20))+"%enter%"+lore.replace("%stat%", main.Main_ServerOption.statLUK)
					+"%enter%";
			
		Stack2(LineUp("§e[§f§l"+main.Main_ServerOption.statLUK+"§e]",(byte) 24), 322,0,1,
				Arrays.asList(lore.split("%enter%")), 24, inv);


		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
		{
			Stack2("§6    [§f§l"+main.Main_ServerOption.statSTR+" 상승§6]", 399,0,1,
					Arrays.asList("§7"+main.Main_ServerOption.statSTR+" 스텟을 한단계 상승 시킵니다.","§7남은 스텟 포인트 : "+StatPoint), 29, inv);
			Stack2("§6    [§f§l"+main.Main_ServerOption.statDEX+" 상승§6]", 399,0,1,
					Arrays.asList("§7"+main.Main_ServerOption.statDEX+" 스텟을 한단계 상승 시킵니다.","§7남은 스텟 포인트 : "+StatPoint), 30, inv);
			Stack2("§6    [§f§l"+main.Main_ServerOption.statINT+" 상승§6]", 399,0,1,
					Arrays.asList("§7"+main.Main_ServerOption.statINT+" 스텟을 한단계 상승 시킵니다.","§7남은 스텟 포인트 : "+StatPoint), 31, inv);
			Stack2("§6    [§f§l"+main.Main_ServerOption.statWILL+" 상승§6]", 399,0,1,
					Arrays.asList("§7"+main.Main_ServerOption.statWILL+" 스텟을 한단계 상승 시킵니다.","§7남은 스텟 포인트 : "+StatPoint), 32, inv);
			Stack2("§6    [§f§l"+main.Main_ServerOption.statLUK+" 상승§6]", 399,0,1,
					Arrays.asList("§7"+main.Main_ServerOption.statLUK+" 스텟을 한단계 상승 시킵니다.","§7남은 스텟 포인트 : "+StatPoint), 33, inv);
		}
		Stack2("§7    [§f§l방어§7]", 307,0,1,
				Arrays.asList("§f물리 방어 : §f" +(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEF()+Battle_Calculator.getPlayerEquipmentStat(player, "방어", false, null)[0]),
						"§7추가 물리 보호 : §f" + (main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Protect()+Battle_Calculator.getPlayerEquipmentStat(player, "보호", false, null)[0]),
						"§b추가 마법 방어 : §f" + (main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_DEF()+Battle_Calculator.getMagicDEF(player,INT)),
						"§3추가 마법 보호 : §f" + (main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_Protect()+Battle_Calculator.getMagicProtect(player, INT))), 38, inv);

		Stack2("§c    [§f§l관통§c]", 409,0,1,
				Arrays.asList("§c추가 물리 방어 관통 : §f" + (main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEFcrash()+Battle_Calculator.getDEFcrash(player, DEX)),
						"§9추가 마법 방어 관통 : §f" + (main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MagicDEFcrash()+Battle_Calculator.getMagicDEFcrash(player, INT))), 39, inv);
		
		Stack2("§a    [§f§l기회§a]", 377,0,1,
				Arrays.asList("§a추가 밸런스 : §f" + Battle_Calculator.getBalance(player, DEX, main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Balance())+"%",
						"§e추가 크리티컬 : §f" + Battle_Calculator.getCritical(player,LUK, WILL,main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Critical())+"%"), 42, inv);
		
		player.openInventory(inv);
	}
	
	//각종 GUI창 속의 아이콘을 눌렸을 때, 해당 아이콘에 기능을 넣는 메소드1   -스텟 GUI, 오피박스, 커스텀 몬스터GUI-//
	public void StatusInventoryclick(InventoryClickEvent event)
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
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot >= 29 && slot <= 33)
			{
			    YamlLoader Config = new YamlLoader();
				Config.getConfig("config.yml");
				if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
					if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
					{
						boolean isOk = false;
						if(slot == 29)
							isOk = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() < main.Main_ServerOption.maxSTR;
						else if(slot == 30)
							isOk = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() < main.Main_ServerOption.maxDEX;
						else if(slot == 31)
							isOk = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() < main.Main_ServerOption.maxINT;
						else if(slot == 32)
							isOk = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() < main.Main_ServerOption.maxWILL;
						else if(slot == 33)
							isOk = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() < main.Main_ServerOption.maxLUK;
						
						if(isOk)
						{
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
							if(slot == 29)
								main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(1);
							else if(slot == 30)
								main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(1);
							else if(slot == 31)
								main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(1);
							else if(slot == 32)
								main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(1);
							else if(slot == 33)
								main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(1);
							SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							new effect.SendPacket().sendActionBar(player, "§c§l[해당 능력은 더 이상 상승시킬 수 없습니다!]");
						}
					}
				StatusGUI(player);
			}
			else if(slot == 9)
				new UserSkill_GUI().MainSkillsListGUI(player, (short) 0);
			else if(slot == 18)
				new quest.Quest_GUI().MyQuestListGUI(player, (short) 0);
			else if(slot == 27)
				new user.Option_GUI().optionGUI(player);
			else if(slot == 36)
				new ETC_GUI().ETCGUI_Main(player);
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
