package GBD.GoldBigDragon_Advanced.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.*;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;

import GBD.GoldBigDragon_Advanced.Main.*;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class PlayerSkillGUI extends GUIutil
{
	public void MainSkillsListGUI(Player player, int page)
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager Config = GUI_YC.getNewConfig("config.yml");
		YamlManager PlayerSkillList  = GUI_YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
		Inventory inv = null;
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "직업군 선택 : " + (page+1));
			Object[] a= PlayerSkillList.getConfigurationSection("MapleStory").getKeys(false).toArray();

			int loc=0;
			for(int count = page*45; count < a.length;count++)
			{
				String JobName = a[count].toString();
				int SkillAmount= PlayerSkillList.getConfigurationSection("MapleStory."+a[count].toString()+".Skill").getKeys(false).size();
				if(count > a.length || loc >= 45) break;
			
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + JobName,  340,0,1,Arrays.asList(ChatColor.DARK_AQUA+"스킬 개수 : " + ChatColor.WHITE+SkillAmount +ChatColor.DARK_AQUA+" 개"), loc, inv);
				loc=loc+1;
			}
			if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		}
		else
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "카테고리 선택 : " + (page+1));
			Object[] Categori= PlayerSkillList.getConfigurationSection("Mabinogi").getKeys(false).toArray();

			int loc=0;
			for(int count = page*45; count < Categori.length;count++)
			{
				String CategoriName = Categori[count].toString();
				int SkillAmount= PlayerSkillList.getConfigurationSection("Mabinogi."+Categori[count].toString()).getKeys(false).size();
				if(count > Categori.length || loc >= 45) break;
			
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + CategoriName,  340,0,1,Arrays.asList(ChatColor.DARK_AQUA+"스킬 개수 : " + ChatColor.WHITE+SkillAmount +ChatColor.DARK_AQUA+" 개"), loc, inv);
				loc=loc+1;
			}
			if(Categori.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
			
		}

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void SkillListGUI(Player player, int page,boolean isMabinogi, String CategoriName)
	{
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager Config = GUI_YC.getNewConfig("config.yml");
		YamlManager PlayerSkillList  = GUI_YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
		YamlManager AllSkillList  = GUI_YC.getNewConfig("Skill/SkillList.yml");
		YamlManager PlayerStats  = GUI_YC.getNewConfig("Stats/"+player.getUniqueId().toString()+".yml");
		Inventory inv = null;
		int MySkillPoint = PlayerStats.getInt("Stat.SkillPoint");
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "보유 스킬 목록 : " + (page+1));
			Object[] a= PlayerSkillList.getConfigurationSection("MapleStory."+CategoriName+".Skill").getKeys(false).toArray();

			int loc=0;
			for(int count = page*45; count < a.length;count++)
			{
				int SkillRank= PlayerSkillList.getInt("MapleStory."+CategoriName+".Skill."+a[count]);
				int SkillMaxRank = AllSkillList.getConfigurationSection(a[count]+".SkillRank").getKeys(false).size();
				int NeedSkillPoint = 0;
				if(SkillRank < SkillMaxRank)
					NeedSkillPoint = AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
				int IConID = AllSkillList.getInt(a[count]+".ID");
				int IConDATA = AllSkillList.getInt(a[count]+".DATA");
				int IConAmount = AllSkillList.getInt(a[count]+".Amount");
				if(count > a.length || loc >= 45) break;
			
				String Skilllore = ChatColor.DARK_AQUA+"스킬 랭크 : " +SkillRank +" / "+SkillMaxRank+"%enter%"+AllSkillList.getString(a[count]+".SkillRank."+SkillRank+".Lore");
				Skilllore = Skilllore +"%enter%%enter%"+ChatColor.YELLOW + "[좌 클릭시 단축키 등록]";

				if(SkillRank<SkillMaxRank)
				{
					Skilllore = Skilllore +"%enter%"+ ChatColor.YELLOW + "[Shift + 좌 클릭시 랭크 업]%enter%";
					if(MySkillPoint < NeedSkillPoint)
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "필요 스킬 포인트 : "+NeedSkillPoint;
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "현재 스킬 포인트 : "+MySkillPoint;
					}
					else
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "필요 스킬 포인트 : "+NeedSkillPoint;
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "현재 스킬 포인트 : "+MySkillPoint;
					}
				}
				
				String[] scriptA = Skilllore.split("%enter%");
				for(int counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  scriptA[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count],  IConID,IConDATA,IConAmount,Arrays.asList(scriptA), loc, inv);
				
				loc=loc+1;
			}
			if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		}
		else
		{
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "보유 스킬 목록 : " + (page+1));
			Object[] a= PlayerSkillList.getConfigurationSection("Mabinogi."+CategoriName).getKeys(false).toArray();

			int loc=0;
			for(int count = page*45; count < a.length;count++)
			{
				int SkillRank= PlayerSkillList.getInt("Mabinogi."+CategoriName+"."+a[count]);
				int SkillMaxRank = AllSkillList.getConfigurationSection(a[count]+".SkillRank").getKeys(false).size();
				int IConID = AllSkillList.getInt(a[count]+".ID");
				int IConDATA = AllSkillList.getInt(a[count]+".DATA");
				int IConAmount = AllSkillList.getInt(a[count]+".Amount");
				int NeedSkillPoint = 0;
				if(SkillRank < SkillMaxRank)
					NeedSkillPoint = AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
				if(count > a.length || loc >= 45) break;
			
				String Skilllore = ChatColor.DARK_AQUA+"스킬 랭크 : " +SkillRank +" / "+SkillMaxRank+"%enter%"+AllSkillList.getString(a[count]+".SkillRank."+SkillRank+".Lore");
				Skilllore = Skilllore +"%enter%%enter%"+ChatColor.YELLOW + "[좌 클릭시 단축키 등록]";

				if(SkillRank<SkillMaxRank)
				{
					Skilllore = Skilllore +"%enter%"+ ChatColor.YELLOW + "[Shift + 좌 클릭시 랭크 업]%enter%";
					if(MySkillPoint < NeedSkillPoint)
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "필요 스킬 포인트 : "+NeedSkillPoint;
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "현재 스킬 포인트 : "+MySkillPoint;
					}
					else
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "필요 스킬 포인트 : "+NeedSkillPoint;
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "현재 스킬 포인트 : "+MySkillPoint;
					}
				}


				
				String[] scriptA = Skilllore.split("%enter%");
				for(int counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  scriptA[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count],  IConID,IConDATA,IConAmount,Arrays.asList(scriptA), loc, inv);
				
				loc=loc+1;
			}
			if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		}

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK + CategoriName), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+isMabinogi), 53, inv);
		player.openInventory(inv);
	}
	
	public void AddQuickBarGUI(Player player,boolean isMabinogi,  String CategoriName, String Skillname)
	{
		Inventory inv = Bukkit.createInventory(null, 18, ChatColor.BLACK + "퀵슬롯 등록");

		for(int count = 0;count < 9;count++)
		{
			if(player.getInventory().getItem(count) == null)
				Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "|||||||||||||||||||||[빈 슬롯]|||||||||||||||||||||", 160,9,1,Arrays.asList("",ChatColor.YELLOW +""+ChatColor.BOLD+ "[클릭시 핫바에 등록 됩니다]"),count, inv);
			else
				Stack2(ChatColor.RED + "" + ChatColor.BOLD + "||||||||||||||||||||||||||[사용중]||||||||||||||||||||||||||", 160,14,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[이 곳에는 등록할 수 없습니다]"),count, inv);
		}
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+""+isMabinogi), 9, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+Skillname,ChatColor.BLACK+CategoriName), 17, inv);
		player.openInventory(inv);
	}
	
	
	
	public void MapleStory_MainSkillsListGUIClick(InventoryClickEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			StatsGUI SGUI = new StatsGUI();
			SGUI.StatusGUI(player);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, page-1);
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, page+1);
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, 0, false, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			return;
		}
	}
	
	public void Mabinogi_MainSkillsListGUIClick(InventoryClickEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			GBD.GoldBigDragon_Advanced.GUI.StatsGUI SGUI = new GBD.GoldBigDragon_Advanced.GUI.StatsGUI();
			SGUI.StatusGUI(player);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, page-1);
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, page+1);
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, page, true, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			return;
		}
	}
	
	
	public void SkillListGUIClick(InventoryClickEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		boolean isMabinogi = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		String CategoriName = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			MainSkillsListGUI(player, 0);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, page-1, isMabinogi, CategoriName);
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, page+1, isMabinogi, CategoriName);
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(event.isLeftClick() == true && event.isShiftClick() == false)
				AddQuickBarGUI(player, isMabinogi,CategoriName,ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			else if(event.isLeftClick() == true && event.isShiftClick() == true)
			{
				YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
				String SkillName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlManager PlayerStats  = GUI_YC.getNewConfig("Stats/"+player.getUniqueId().toString()+".yml");
				YamlManager PlayerSkillList  = GUI_YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
				YamlManager AllSkillList  = GUI_YC.getNewConfig("Skill/SkillList.yml");
				int SkillRank = 1;
				int SkillMaxRank = AllSkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false).size();
				if(isMabinogi==false)
					SkillRank= PlayerSkillList.getInt("MapleStory."+CategoriName+".Skill."+SkillName);
				else
					SkillRank= PlayerSkillList.getInt("Mabinogi."+CategoriName+"."+SkillName);
				if(SkillRank<SkillMaxRank)
				{
					if(PlayerStats.getInt("Stat.SkillPoint") >= AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".SkillPoint"))
					{
						if(isMabinogi == false)
							PlayerSkillList.set("MapleStory."+CategoriName+".Skill."+SkillName, SkillRank+1);
						else
							PlayerSkillList.set("Mabinogi."+CategoriName+"."+SkillName, SkillRank+1);
							
						PlayerStats.set("Stat.SkillPoint", PlayerStats.getInt("Stat.SkillPoint")-AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".SkillPoint"));
						PlayerStats.set("Stat.MAXHP", PlayerStats.getInt("Stat.MAXHP")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusHP"));
						PlayerStats.set("Stat.HP", PlayerStats.getInt("Stat.HP")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusHP"));
						PlayerStats.set("Stat.MAXMP", PlayerStats.getInt("Stat.MAXMP")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMP"));
						PlayerStats.set("Stat.Balance", PlayerStats.getInt("Stat.Balance")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusBAL"));
						PlayerStats.set("Stat.Critical", PlayerStats.getInt("Stat.Critical")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusCRI"));
						PlayerStats.set("Stat.MP", PlayerStats.getInt("Stat.MP")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMP"));
						PlayerStats.set("Stat.STR", PlayerStats.getInt("Stat.STR")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusSTR"));
						PlayerStats.set("Stat.DEX", PlayerStats.getInt("Stat.DEX")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusDEX"));
						PlayerStats.set("Stat.INT", PlayerStats.getInt("Stat.INT")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusINT"));
						PlayerStats.set("Stat.WILL", PlayerStats.getInt("Stat.WILL")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusWILL"));
						PlayerStats.set("Stat.LUK", PlayerStats.getInt("Stat.LUK")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusLUK"));
						PlayerStats.set("Stat.DEF", PlayerStats.getInt("Stat.DEF")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusDEF"));
						PlayerStats.set("Stat.Protect", PlayerStats.getInt("Stat.Protect")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusPRO"));
						PlayerStats.set("Stat.Magic_DEF", PlayerStats.getInt("Stat.Magic_DEF")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMDEF"));
						PlayerStats.set("Stat.Magic_Protect", PlayerStats.getInt("Stat.Magic_Protect")+AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMPRO"));
						PlayerSkillList.saveConfig();
						PlayerStats.saveConfig();
						s.SP(player, Sound.LEVEL_UP, 0.8F, 1.7F);

						if(Main.MagicSpellsCatched == true)
						{
							OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
							MS.setPlayerMaxAndNowMana(player);
						}
						Damageable p = player;
						p.setMaxHealth(PlayerStats.getInt("Stat.MAXHP"));
						p.setHealth(PlayerStats.getInt("Stat.HP"));
						
						if(SkillRank!=SkillMaxRank-1)
							player.sendMessage(ChatColor.GREEN + "[스킬] : "+ChatColor.YELLOW+SkillName+ChatColor.GREEN+" 스킬의 "+ChatColor.YELLOW+"랭크가 상승"+ChatColor.GREEN+"하였습니다!");
						else
							player.sendMessage(ChatColor.DARK_PURPLE + "[스킬] : "+ChatColor.YELLOW+SkillName+ChatColor.DARK_PURPLE+" 스킬을 "+ChatColor.YELLOW+"마스터"+ChatColor.DARK_PURPLE+"하였습니다!");
						
						SkillListGUI(player, page, isMabinogi, CategoriName);
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED + "[스킬] : 스텟 포인트가 부족합니다!");
					}
				}
				else
				{
					s.SP(player, Sound.ANVIL_LAND, 0.8F, 1.7F);
				}
			}
			return;
		}
	}
	
	public void AddQuickBarGUIClick(InventoryClickEvent event)
	{
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		boolean isMabinogi = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(9).getItemMeta().getLore().get(1)));
		String Skillname = ChatColor.stripColor(event.getInventory().getItem(17).getItemMeta().getLore().get(1));
		String CategoriName = ChatColor.stripColor(event.getInventory().getItem(17).getItemMeta().getLore().get(2));
		
		switch (event.getSlot())
		{
		case 9://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillListGUI(player, 0, isMabinogi, CategoriName);
			return;
		case 17://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		default :
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(player.getInventory().getItem(event.getSlot()) == null)
			{
				YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
				YamlManager Config = GUI_YC.getNewConfig("config.yml");
				YamlManager PlayerSkillList  = GUI_YC.getNewConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
				YamlManager AllSkillList  = GUI_YC.getNewConfig("Skill/SkillList.yml");
				int IconID = AllSkillList.getInt(Skillname+".ID");
				int IconDATA = AllSkillList.getInt(Skillname+".DATA");
				int IconAmount = AllSkillList.getInt(Skillname+".Amount");
				String lore = ChatColor.WHITE + ""+CategoriName+"%enter%"+ChatColor.WHITE + ""+Skillname+"%enter%%enter%"+ChatColor.YELLOW + "[클릭시 퀵슬롯에서 삭제]%enter%";
				String[] scriptA = lore.split("%enter%");
				for(int counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  scriptA[counter];
				
				
				ItemStack Icon = new MaterialData(IconID, (byte) IconDATA).toItemStack(IconAmount);
				ItemMeta Icon_Meta = Icon.getItemMeta();
				Icon_Meta.setDisplayName(ChatColor.GREEN + "     [스킬 단축키]     ");
				Icon_Meta.setLore(Arrays.asList(scriptA));
				Icon.setItemMeta(Icon_Meta);
				player.getInventory().setItem(event.getSlot(), Icon);
				SkillListGUI(player, 0, isMabinogi, CategoriName);
			}
			else
			{
				AddQuickBarGUI(player, isMabinogi,CategoriName,Skillname);
			}
			return;
		}
	}
	
}
