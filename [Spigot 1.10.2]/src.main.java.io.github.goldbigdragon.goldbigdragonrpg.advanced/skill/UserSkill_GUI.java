package skill;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.*;

import effect.SoundEffect;
import user.Stats_GUI;
import util.Util_GUI;
import util.YamlLoader;

import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UserSkill_GUI extends Util_GUI
{
	public void MainSkillsListGUI(Player player, short page)
	{
		YamlLoader Config = new YamlLoader();
		Config.getConfig("config.yml");
		YamlLoader PlayerSkillList = new YamlLoader();
		PlayerSkillList.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
		Inventory inv = null;
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
		{
			String UniqueCode = "§0§0§b§0§3§r";
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0직업군 선택 : " + (page+1));
			Object[] a= PlayerSkillList.getConfigurationSection("MapleStory").getKeys(false).toArray();

			byte loc=0;
			for(int count = page*45; count < a.length;count++)
			{
				String JobName = a[count].toString();
				short SkillAmount= (short) PlayerSkillList.getConfigurationSection("MapleStory."+a[count].toString()+".Skill").getKeys(false).size();
				if(count > a.length || loc >= 45) break;
			
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + JobName,  340,0,1,Arrays.asList(ChatColor.DARK_AQUA+"스킬 개수 : " + ChatColor.WHITE+SkillAmount +ChatColor.DARK_AQUA+" 개"), loc, inv);
				loc++;
			}
			if(a.length-(page*44)>45)
				Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
				Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		}
		else
		{
			String UniqueCode = "§0§0§b§0§4§r";
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0카테고리 선택 : " + (page+1));
			Object[] Categori= PlayerSkillList.getConfigurationSection("Mabinogi").getKeys(false).toArray();

			byte loc=0;
			for(int count = (short) (page*45); count < Categori.length;count++)
			{
				String CategoriName = Categori[count].toString();
				short SkillAmount= (short) PlayerSkillList.getConfigurationSection("Mabinogi."+Categori[count].toString()).getKeys(false).size();
				if(count > Categori.length || loc >= 45) break;
			
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + CategoriName,  340,0,1,Arrays.asList(ChatColor.DARK_AQUA+"스킬 개수 : " + ChatColor.WHITE+SkillAmount +ChatColor.DARK_AQUA+" 개"), loc, inv);
				loc++;
			}
			if(Categori.length-(page*44)>45)
				Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
				Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		}

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void SkillListGUI(Player player, short page,boolean isMabinogi, String CategoriName)
	{
		YamlLoader Config = new YamlLoader();
		Config.getConfig("config.yml");
		YamlLoader PlayerSkillList = new YamlLoader();
		PlayerSkillList.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
		YamlLoader AllSkillList = new YamlLoader();
		AllSkillList.getConfig("Skill/SkillList.yml");
		Inventory inv = null;
		String UniqueCode = "§0§0§b§0§5§r";
		if(Config.getBoolean("Server.Like_The_Mabinogi_Online_Stat_System") == false)
		{
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0보유 스킬 목록 : " + (page+1));
			Object[] a= PlayerSkillList.getConfigurationSection("MapleStory."+CategoriName+".Skill").getKeys(false).toArray();

			byte loc=0;
			for(int count = page*45; count < a.length;count++)
			{
				short SkillRank= (short) PlayerSkillList.getInt("MapleStory."+CategoriName+".Skill."+a[count]);
				short SkillMaxRank = (short) AllSkillList.getConfigurationSection(a[count]+".SkillRank").getKeys(false).size();
				int IConID = AllSkillList.getInt(a[count]+".ID");
				byte IConDATA = (byte) AllSkillList.getInt(a[count]+".DATA");
				byte IConAmount = (byte) AllSkillList.getInt(a[count]+".Amount");
				if(count > a.length || loc >= 45) break;
			
				String Skilllore = ChatColor.DARK_AQUA+"스킬 랭크 : " +SkillRank +" / "+SkillMaxRank+"%enter%"+AllSkillList.getString(a[count]+".SkillRank."+SkillRank+".Lore");
				Skilllore = Skilllore +"%enter%%enter%"+ChatColor.YELLOW + "[좌 클릭시 단축키 등록]";

				if(SkillRank<SkillMaxRank)
				{
					Skilllore = Skilllore +"%enter%"+ ChatColor.YELLOW + "[Shift + 좌 클릭시 랭크 업]%enter%";
					if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint"))
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "필요 스킬 포인트 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "현재 스킬 포인트 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint();
					}
					else
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "필요 스킬 포인트 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "현재 스킬 포인트 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint();
					}
					if(AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel") > 0)
						if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel"))
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "최소 레벨 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "현재 레벨 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
						}
						else
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "최소 레벨 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "현재 레벨 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
						}
					if(AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel") > 0)
						if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel"))
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "최소 누적 레벨 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "현재 누적 레벨 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
						}
						else
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "최소 누적 레벨 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "현재 누적 레벨 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
						}
				}
				
				String[] scriptA = Skilllore.split("%enter%");
				for(int counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  scriptA[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count],  IConID,IConDATA,IConAmount,Arrays.asList(scriptA), loc, inv);
				
				loc++;
			}
			if(a.length-(page*44)>45)
				Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
				Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		}
		else
		{
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0보유 스킬 목록 : " + (page+1));
			Object[] a= PlayerSkillList.getConfigurationSection("Mabinogi."+CategoriName).getKeys(false).toArray();

			byte loc=0;
			for(int count = page*45; count < a.length;count++)
			{
				short SkillRank= (short) PlayerSkillList.getInt("Mabinogi."+CategoriName+"."+a[count]);
				short SkillMaxRank = (short) AllSkillList.getConfigurationSection(a[count]+".SkillRank").getKeys(false).size();
				int IConID = AllSkillList.getInt(a[count]+".ID");
				byte IConDATA = (byte) AllSkillList.getInt(a[count]+".DATA");
				byte IConAmount = (byte) AllSkillList.getInt(a[count]+".Amount");
				if(count > a.length || loc >= 45) break;
			
				String Skilllore = ChatColor.DARK_AQUA+"스킬 랭크 : " +SkillRank +" / "+SkillMaxRank+"%enter%"+AllSkillList.getString(a[count]+".SkillRank."+SkillRank+".Lore");
				Skilllore = Skilllore +"%enter%%enter%"+ChatColor.YELLOW + "[좌 클릭시 단축키 등록]";

				if(SkillRank<SkillMaxRank)
				{
					Skilllore = Skilllore +"%enter%"+ ChatColor.YELLOW + "[Shift + 좌 클릭시 랭크 업]%enter%";
					if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint"))
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "필요 스킬 포인트 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
						Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "현재 스킬 포인트 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint();
					}
					else
					{
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "필요 스킬 포인트 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".SkillPoint");
						Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "현재 스킬 포인트 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint();
					}
					if(AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel") > 0)
						if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel"))
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "최소 레벨 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "현재 레벨 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
						}
						else
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "최소 레벨 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "현재 레벨 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level();
						}
					if(AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel") > 0)
						if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel() < AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel"))
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "최소 누적 레벨 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.RED + "현재 누적 레벨 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
						}
						else
						{
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "최소 누적 레벨 : "+AllSkillList.getInt(a[count]+".SkillRank."+(SkillRank+1)+".NeedRealLevel");
							Skilllore = Skilllore +"%enter%"+ ChatColor.GREEN + "현재 누적 레벨 : "+main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel();
						}
				}
				String[] scriptA = Skilllore.split("%enter%");
				for(int counter = 0; counter < scriptA.length; counter++)
					scriptA[counter] =  scriptA[counter];
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + a[count],  IConID,IConDATA,IConAmount,Arrays.asList(scriptA), loc, inv);
				
				loc++;
			}
			if(a.length-(page*44)>45)
				Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
			if(page!=0)
				Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		}
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK + CategoriName), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+isMabinogi), 53, inv);
		player.openInventory(inv);
	}
	
	public void AddQuickBarGUI(Player player,boolean isMabinogi,  String CategoriName, String Skillname)
	{
		String UniqueCode = "§0§0§b§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 18, UniqueCode + "§0퀵슬롯 등록");

		for(int count = 0;count < 9;count++)
		{
			if(player.getInventory().getItem(count) == null)
				Stack2("§3§l|||||||||||||||||||||[빈 슬롯]|||||||||||||||||||||", 160,9,1,Arrays.asList("",ChatColor.YELLOW +""+ChatColor.BOLD+ "[클릭시 핫바에 등록 됩니다]"),count, inv);
			else
				Stack2("§c§l||||||||||||||||||||||||||[사용중]||||||||||||||||||||||||||", 160,14,1,Arrays.asList("",ChatColor.RED + ""+ChatColor.BOLD+"[이 곳에는 등록할 수 없습니다]"),count, inv);
		}
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+""+isMabinogi), 9, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+Skillname,ChatColor.BLACK+CategoriName), 17, inv);
		player.openInventory(inv);
	}
	
	
	
	public void MapleStory_MainSkillsListGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				new Stats_GUI().StatusGUI(player);
			else if(slot == 48)//이전 페이지
				MainSkillsListGUI(player, (short) (page-1));
			else if(slot == 50)//다음 페이지
				MainSkillsListGUI(player, (short) (page+1));
			else
				SkillListGUI(player, (short) 0, false, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
		}
	}
	
	public void Mabinogi_MainSkillsListGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				new Stats_GUI().StatusGUI(player);
			else if(slot == 48)//이전 페이지
				MainSkillsListGUI(player, (short) (page-1));
			else if(slot == 50)//다음 페이지
				MainSkillsListGUI(player, (short) (page+1));
			else
				SkillListGUI(player, page, true, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
		}
	}
	
	
	public void SkillListGUIClick(InventoryClickEvent event)
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
			boolean isMabinogi = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			String CategoriName = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				MainSkillsListGUI(player, (short) 0);
			else if(slot == 48)//이전 페이지
				SkillListGUI(player, (short) (page-1), isMabinogi, CategoriName);
			else if(slot == 50)//다음 페이지
				SkillListGUI(player, (short) (page+1), isMabinogi, CategoriName);
			else
			{
				if(event.isLeftClick() == true && event.isShiftClick() == false)
					AddQuickBarGUI(player, isMabinogi,CategoriName,ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isLeftClick() == true && event.isShiftClick() == true)
				{
					String SkillName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
					YamlLoader PlayerSkillList = new YamlLoader();
					PlayerSkillList.getConfig("Skill/PlayerData/"+player.getUniqueId().toString()+".yml");
					YamlLoader AllSkillList = new YamlLoader();
					AllSkillList.getConfig("Skill/SkillList.yml");
					short SkillRank = 1;
					short SkillMaxRank = (short) AllSkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false).size();
					if(isMabinogi==false)
						SkillRank= (short) PlayerSkillList.getInt("MapleStory."+CategoriName+".Skill."+SkillName);
					else
						SkillRank= (short) PlayerSkillList.getInt("Mabinogi."+CategoriName+"."+SkillName);
					if(SkillRank<SkillMaxRank)
					{
						if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Level() < AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".NeedLevel"))
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[스킬] : 레벨이 부족합니다!");
							return;
						}
						else if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_RealLevel() < AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".NeedRealLevel"))
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[스킬] : 누적 레벨이 부족합니다!");
							return;
						}
						else if(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_SkillPoint() >= AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".SkillPoint"))
						{
							if(isMabinogi == false)
								PlayerSkillList.set("MapleStory."+CategoriName+".Skill."+SkillName, SkillRank+1);
							else
								PlayerSkillList.set("Mabinogi."+CategoriName+"."+SkillName, SkillRank+1);
							
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_SkillPoint(-1 * AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".SkillPoint"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxHP(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusHP"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_HP(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusHP"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MaxMP(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMP"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MP(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMP"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Balance(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusBAL"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Critical(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusCRI"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_STR(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusSTR"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEX(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusDEX"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_INT(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusINT"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_WILL(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusWILL"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_LUK(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusLUK"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_DEF(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusDEF"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Protect(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusPRO"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_DEF(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMDEF"));
							main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_Magic_Protect(AllSkillList.getInt(SkillName+".SkillRank."+(SkillRank+1)+".BonusMPRO"));
							PlayerSkillList.saveConfig();
							SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 0.8F, 1.7F);

							if(main.Main_ServerOption.MagicSpellsCatched == true)
							{
								otherplugins.SpellMain MS = new otherplugins.SpellMain();
								MS.setPlayerMaxAndNowMana(player);
							}
							Damageable p = player;
							p.setMaxHealth(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxHP());
							p.setHealth(main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_HP());
							
							if(SkillRank!=SkillMaxRank-1)
								player.sendMessage(ChatColor.GREEN + "[스킬] : "+ChatColor.YELLOW+SkillName+ChatColor.GREEN+" 스킬의 "+ChatColor.YELLOW+"랭크가 상승"+ChatColor.GREEN+"하였습니다!");
							else
								player.sendMessage(ChatColor.DARK_PURPLE + "[스킬] : "+ChatColor.YELLOW+SkillName+ChatColor.DARK_PURPLE+" 스킬을 "+ChatColor.YELLOW+"마스터"+ChatColor.DARK_PURPLE+"하였습니다!");
							
							SkillListGUI(player, page, isMabinogi, CategoriName);
						}
						else
						{
							SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
							player.sendMessage(ChatColor.RED + "[스킬] : 스텟 포인트가 부족합니다!");
						}
					}
					else
						SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 0.8F, 1.7F);
				}
			}
		}
	}
	
	public void AddQuickBarGUIClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 17)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			boolean isMabinogi = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(9).getItemMeta().getLore().get(1)));
			String CategoriName = ChatColor.stripColor(event.getInventory().getItem(17).getItemMeta().getLore().get(2));
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 9)//이전 목록
				SkillListGUI(player, (short) 0, isMabinogi, CategoriName);
			else
			{
				String Skillname = ChatColor.stripColor(event.getInventory().getItem(17).getItemMeta().getLore().get(1));
				if(player.getInventory().getItem(event.getSlot()) == null)
				{
					YamlLoader AllSkillList = new YamlLoader();
					AllSkillList.getConfig("Skill/SkillList.yml");
					int IconID = AllSkillList.getInt(Skillname+".ID");
					byte IconDATA = (byte) AllSkillList.getInt(Skillname+".DATA");
					byte IconAmount = (byte) AllSkillList.getInt(Skillname+".Amount");
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
					SkillListGUI(player, (short) 0, isMabinogi, CategoriName);
				}
				else
					AddQuickBarGUI(player, isMabinogi,CategoriName,Skillname);
			}
		}
	}
	
}
