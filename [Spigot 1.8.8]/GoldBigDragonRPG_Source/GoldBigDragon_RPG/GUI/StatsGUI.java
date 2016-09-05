package GoldBigDragon_RPG.GUI;

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

import GoldBigDragon_RPG.Attack.Damage;
import GoldBigDragon_RPG.Skill.PlayerSkillGUI;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class StatsGUI extends GUIutil
{
	//스텟 GUI창의 1 페이지를 구성해 주는 메소드//
	public void StatusGUI(Player player)
	{
		Damage dam = new Damage();
	    YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	    YamlManager Config = YC.getNewConfig("config.yml");
		
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "스텟");

		int MaxStats = new GoldBigDragon_RPG.Main.ServerOption().MaxStats;
		
		Stack2(ChatColor.WHITE + "스텟", 160,4,1,Arrays.asList(ChatColor.GRAY + "스텟을 확인합니다."), 0, inv);
		Stack2(ChatColor.WHITE + "스킬", 403,0,1,Arrays.asList(ChatColor.GRAY + "스킬을 확인합니다."), 9, inv);
		Stack2(ChatColor.WHITE + "퀘스트", 358,0,1,Arrays.asList(ChatColor.GRAY + "현재 진행중인 퀘스트를 확인합니다."), 18, inv);
		Stack2(ChatColor.WHITE + "옵션", 145,0,1,Arrays.asList(ChatColor.GRAY + "기타 설정을 합니다."), 27, inv);
		Stack2(ChatColor.WHITE + "기타", 354,0,1,Arrays.asList(ChatColor.GRAY + "기타 내용을 확인합니다."), 36, inv);
		
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 1, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 7, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 10, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 16, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 19, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 25, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 28, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 34, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 37, inv);
		Stack2(ChatColor.RED + " ", 66,0,1,Arrays.asList(""), 43, inv);
		
		ItemStack EXIT = new ItemStack(Material.WOOD_DOOR, 1);
		ItemMeta EXIT_BUTTON = EXIT.getItemMeta();
		EXIT_BUTTON.setDisplayName(ChatColor.WHITE  + "" + ChatColor.BOLD + "닫기");
		EXIT_BUTTON.setLore(Arrays.asList(ChatColor.GRAY + "창을 닫습니다."));
		EXIT.setItemMeta(EXIT_BUTTON);
		inv.setItem(26, EXIT);

		int StatPoint = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint();
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == true)
		{
			Stack2(ChatColor.GREEN + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + "상태"+ChatColor.GREEN + "]", 397,3,1,
					Arrays.asList(ChatColor.WHITE + "[레벨] : " +ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level(),
							ChatColor.WHITE + "[누적 레벨] : " +ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel(),
							ChatColor.WHITE + "[경험치] : " + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP() + " / " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP(),
							ChatColor.AQUA + "[스킬 포인트] : " + ChatColor.WHITE + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint()), 13, inv);
		}
		else
		{
			YamlManager PlayerSkillYML = YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId()+".yml");
			Stack2(ChatColor.GREEN + "       [" + ChatColor.WHITE +""+ChatColor.BOLD + "상태"+ChatColor.GREEN + "]", 397,3,1,
					Arrays.asList(ChatColor.WHITE + "[레벨] : " +ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level(),
							ChatColor.WHITE + "[직업] : " +ChatColor.BOLD + PlayerSkillYML.getString("Job.Type"),
							ChatColor.WHITE + "[경험치] : " + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP() + " / " + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP(),
							ChatColor.GREEN + "[스텟 포인트] : " + ChatColor.WHITE + ChatColor.BOLD + StatPoint,
							ChatColor.AQUA + "[스킬 포인트] : " + ChatColor.WHITE + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint()), 13, inv);
		}
		
		int DefaultDamage = 0;
		if(player.getItemInHand().hasItemMeta() == true)
		{
			if(player.getItemInHand().getItemMeta().hasLore() == true)
			{
				if(player.getItemInHand().getItemMeta().getLore().toString().contains(GoldBigDragon_RPG.Main.ServerOption.Damage+" : ") == true)
				{
					switch(player.getItemInHand().getType())
					{
					case WOOD_SPADE :
					case GOLD_SPADE :
						DefaultDamage = 1;
						break;
					case WOOD_PICKAXE :
					case GOLD_PICKAXE:
					case STONE_SPADE:
						DefaultDamage = 2;
						break;
					case WOOD_AXE:
					case GOLD_AXE:
					case STONE_PICKAXE:
					case IRON_SPADE:
						DefaultDamage = 3;
						break;
					case WOOD_SWORD:
					case GOLD_SWORD:
					case STONE_AXE:
					case IRON_PICKAXE:
					case DIAMOND_SPADE:
						DefaultDamage = 4;
						break;
					case STONE_SWORD:
					case IRON_AXE:
					case DIAMOND_PICKAXE:
						DefaultDamage = 5;
						break;
					case IRON_SWORD:
					case DIAMOND_AXE:
						DefaultDamage = 6;
						break;
					case DIAMOND_SWORD:
						DefaultDamage = 7;
						break;
					}
				}
			}
		}
		int EquipmentStat = dam.getPlayerEquipmentStat(player, "STR", null, false)[0];
		int PlayerStat = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR();
		if(PlayerStat > MaxStats)
			PlayerStat = MaxStats;
		String Additional = ChatColor.RED +""+ChatColor.BOLD+(dam.CombatMinDamageGet(player,DefaultDamage,PlayerStat)) + " ~ " + (dam.CombatMaxDamageGet(player,DefaultDamage, PlayerStat));
		String CurrentStat;
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ PlayerStat;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (PlayerStat + EquipmentStat) +ChatColor.WHITE + "("+ PlayerStat +")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(PlayerStat + EquipmentStat) +ChatColor.WHITE + "("+ PlayerStat+")";
		String lore = GoldBigDragon_RPG.Main.ServerOption.STR_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.STR.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.STR)
				+"%enter%"+ChatColor.AQUA + "" + ChatColor.BOLD +"[추가 근접 공격력]%enter%"+LineUp(Additional, (byte) 24);
		
		Stack2(ChatColor.DARK_RED + LineUp(ChatColor.RED+"[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.STR+""+ChatColor.DARK_RED + "]", (byte) 24), 267,0,1,
				Arrays.asList(lore.split("%enter%")), 20, inv);

		int DEX = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX();
		EquipmentStat=dam.getPlayerEquipmentStat(player, "DEX", null, false)[0];
		if(DEX > MaxStats)
			DEX = MaxStats;
		Additional = ChatColor.RED + "" + ChatColor.BOLD + "" + dam.returnRangeDamageValue(player, DEX, 0, true) + " ~ " + dam.returnRangeDamageValue(player, DEX, 0, false);
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ DEX;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (DEX + EquipmentStat) +ChatColor.WHITE + "("+ DEX+")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(DEX + EquipmentStat) +ChatColor.WHITE + "("+ DEX+")";

		lore = GoldBigDragon_RPG.Main.ServerOption.DEX_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.DEX.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.DEX)
					+"%enter%"+ChatColor.AQUA + "" + ChatColor.BOLD +"[추가 원거리 공격력]%enter%"+LineUp(Additional, (byte) 24);
			
		Stack2(LineUp(ChatColor.GREEN+"[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.DEX+""+ChatColor.GREEN + "]", (byte) 24), 261,0,1,
				Arrays.asList(lore.split("%enter%")), 21, inv);
		
		int INT = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT();
		if(INT > MaxStats)
			INT = MaxStats;
		EquipmentStat=dam.getPlayerEquipmentStat(player, "INT", null, false)[0];
		Additional = ChatColor.RED + "" + ChatColor.BOLD + "" + ((INT+dam.getPlayerEquipmentStat(player, "INT", null, false)[0])*0.6+100) + " %";
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ INT;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (INT + EquipmentStat) +ChatColor.WHITE + "("+ INT+")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(INT + EquipmentStat) +ChatColor.WHITE + "("+ INT +")";

		lore = GoldBigDragon_RPG.Main.ServerOption.INT_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.INT.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.INT)
					+"%enter%"+ChatColor.AQUA + "" + ChatColor.BOLD +"[추가 스킬 공격력]%enter%"+LineUp(Additional, (byte) 24);
			
		Stack2(LineUp(ChatColor.AQUA + "[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.INT+""+ChatColor.AQUA + "]",(byte) 24), 369,0,1,
				Arrays.asList(lore.split("%enter%")), 22, inv);

		int WILL = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL();
		if(WILL > MaxStats)
			WILL = MaxStats;
		EquipmentStat=dam.getPlayerEquipmentStat(player, "WILL", null, false)[0];
		Additional = ChatColor.RED + "" + ChatColor.BOLD + "" + ((WILL+dam.getPlayerEquipmentStat(player, "WILL", null, false)[0])*0.6+100) + " %";
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ WILL;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (WILL + EquipmentStat) +ChatColor.WHITE + "("+ WILL+")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(WILL + EquipmentStat) +ChatColor.WHITE + "("+ WILL+")";

		lore = GoldBigDragon_RPG.Main.ServerOption.WILL_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.WILL.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.WILL)
					+"%enter%"+ChatColor.AQUA + "" + ChatColor.BOLD +"[추가 스킬 공격력]%enter%"+LineUp(Additional, (byte) 24);
			
		Stack2(LineUp(ChatColor.GRAY + "[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.WILL+""+ChatColor.GRAY + "]",(byte) 24), 370,0,1,
				Arrays.asList(lore.split("%enter%")), 23, inv);
		
		int LUK = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK();
		if(LUK > MaxStats)
			LUK = MaxStats;
		EquipmentStat=dam.getPlayerEquipmentStat(player, "LUK", null, false)[0];
		if(EquipmentStat == 0)
			CurrentStat = ChatColor.WHITE +""+ChatColor.BOLD +""+ LUK;
		else if(EquipmentStat > 0)
			CurrentStat = ChatColor.YELLOW +""+ChatColor.BOLD +""+ (LUK + EquipmentStat) +ChatColor.WHITE + "("+ LUK+")";
		else
			CurrentStat = ChatColor.RED +""+ChatColor.BOLD +""+(LUK + EquipmentStat) +ChatColor.WHITE + "("+ LUK+")";

		lore = GoldBigDragon_RPG.Main.ServerOption.LUK_Lore;
		lore = LineUp(CurrentStat, (byte) (GoldBigDragon_RPG.Main.ServerOption.LUK.length()+20))+"%enter%"+lore.replace("%stat%", GoldBigDragon_RPG.Main.ServerOption.LUK)
					+"%enter%";
			
		Stack2(LineUp(ChatColor.YELLOW + "[" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.LUK+""+ChatColor.YELLOW + "]",(byte) 24), 322,0,1,
				Arrays.asList(lore.split("%enter%")), 24, inv);


		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
		{
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.STR+" 상승"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.STR+" 스텟을 한단계 상승 시킵니다.",ChatColor.GRAY + "남은 스텟 포인트 : "+StatPoint), 29, inv);
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.DEX+" 상승"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.DEX+" 스텟을 한단계 상승 시킵니다.",ChatColor.GRAY + "남은 스텟 포인트 : "+StatPoint), 30, inv);
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.INT+" 상승"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.INT+" 스텟을 한단계 상승 시킵니다.",ChatColor.GRAY + "남은 스텟 포인트 : "+StatPoint), 31, inv);
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.WILL+" 상승"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.WILL+" 스텟을 한단계 상승 시킵니다.",ChatColor.GRAY + "남은 스텟 포인트 : "+StatPoint), 32, inv);
			Stack2(ChatColor.GOLD + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + ""+GoldBigDragon_RPG.Main.ServerOption.LUK+" 상승"+ChatColor.GOLD + "]", 399,0,1,
					Arrays.asList(ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.LUK+" 스텟을 한단계 상승 시킵니다.",ChatColor.GRAY + "남은 스텟 포인트 : "+StatPoint), 33, inv);
		}
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Stack2(ChatColor.GRAY + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + "방어"+ChatColor.GRAY + "]", 307,0,1,
				Arrays.asList(ChatColor.WHITE + "물리 방어 : "+ChatColor.WHITE +(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEF()+d.getPlayerEquipmentStat(player, "방어", null, false)[0]),
						ChatColor.GRAY + "추가 물리 보호 : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Protect()+d.getPlayerEquipmentStat(player, "보호", null, false)[0]),
						ChatColor.AQUA + "추가 마법 방어 : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_DEF()+d.getMagicDEF(player,INT)),
						ChatColor.DARK_AQUA + "추가 마법 보호 : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Magic_Protect()+d.getMagicProtect(player, INT))), 38, inv);

		Stack2(ChatColor.RED + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + "관통"+ChatColor.RED + "]", 409,0,1,
				Arrays.asList(ChatColor.RED + "추가 물리 방어 관통 : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEFcrash()+d.getDEFcrash(player, DEX)),
						ChatColor.BLUE + "추가 마법 방어 관통 : "+ChatColor.WHITE + (GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MagicDEFcrash()+d.getMagicDEFcrash(player, INT))), 39, inv);
		
		Stack2(ChatColor.GREEN + "    [" + ChatColor.WHITE +""+ChatColor.BOLD + "기회"+ChatColor.GREEN + "]", 377,0,1,
				Arrays.asList(ChatColor.GREEN + "추가 밸런스 : "+ChatColor.WHITE + d.getBalance(player, DEX, GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Balance())+"%",
						ChatColor.YELLOW + "추가 크리티컬 : "+ChatColor.WHITE + d.getCritical(player,LUK, WILL,GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Critical())+"%"), 42, inv);
		
		player.openInventory(inv);
	}
	
	//각종 GUI창 속의 아이콘을 눌렸을 때, 해당 아이콘에 기능을 넣는 메소드1   -스텟 GUI, 오피박스, 커스텀 몬스터GUI-//
	public void StatusInventoryclick(InventoryClickEvent event)
	{
	    YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		YamlManager Config = YC.getNewConfig("config.yml");
		int MaxStats = new GoldBigDragon_RPG.Main.ServerOption().MaxStats;
		switch (event.getSlot())
		{
		case 36:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			ETCGUI EGUI = new ETCGUI();
			EGUI.ETCGUI_Main(player);
			break;
		case 9:
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			PlayerSkillGUI PGUI = new PlayerSkillGUI();
			PGUI.MainSkillsListGUI(player, (short) 0);
			break;
		case 18:
			GoldBigDragon_RPG.Quest.QuestGUI QGUI = new GoldBigDragon_RPG.Quest.QuestGUI();
			QGUI.MyQuestListGUI(player, (short) 0);
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			break;
		case 27:
			GoldBigDragon_RPG.GUI.OptionGUI oGUI = new GoldBigDragon_RPG.GUI.OptionGUI();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			oGUI.optionGUI(player);
			break;
		case 29:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_STR() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[해당 능력은 더 이상 상승시킬 수 없습니다!]");
					}
				}
			StatusGUI(player);
			break;
		case 30:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_DEX() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[해당 능력은 더 이상 상승시킬 수 없습니다!]");
					}
				}
			StatusGUI(player);
			break;
		case 31:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_INT() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[해당 능력은 더 이상 상승시킬 수 없습니다!]");
					}

				}
			StatusGUI(player);
			break;
		case 32:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_WILL() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[해당 능력은 더 이상 상승시킬 수 없습니다!]");
					}
				}
			StatusGUI(player);
			break;
		case 33:
			if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
				if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_StatPoint() >= 1)
				{
					if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK() < MaxStats)
					{
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_StatPoint(-1);
						GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(1);
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[해당 능력은 더 이상 상승시킬 수 없습니다!]");
					}
				}
			StatusGUI(player);
			break;
		case 26:
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
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
			for(short count = 0; count < spaceSize/2; count++)
				TempString.append(" ");
			TempString.append(RawString);
			for(short count = 0; count < spaceSize/2; count++)
				TempString.append(" ");
			return TempString.toString();
		}
	}
}
