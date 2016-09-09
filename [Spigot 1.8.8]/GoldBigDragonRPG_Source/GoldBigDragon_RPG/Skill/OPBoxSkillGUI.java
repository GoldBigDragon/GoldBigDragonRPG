package GoldBigDragon_RPG.Skill;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.GUI.GUIutil;
import GoldBigDragon_RPG.GUI.OPBoxGUI;
import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OPBoxSkillGUI extends GUIutil
{
	public void AllSkillsGUI(Player player, short page, boolean isJobGUI,String WhatJob)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "전체 스킬 목록 : " + (page+1));
		Object[] a = SkillList.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String SkillName = a[count].toString();
			short JobLevel=0;
			if(SkillList.contains(a[count].toString()+".SkillRank"))
				JobLevel= (short) SkillList.getConfigurationSection(a[count].toString()+".SkillRank").getKeys(false).size();
			if(count > a.length || loc >= 45) break;
			
			if(isJobGUI == true)
			{
				YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
				if(WhatJob.equals("Maple")==true)
				{
					Object_UserData u = new Object_UserData();
					if(JobList.contains("MapleStory."+u.getString(player, (byte)3)+"."+u.getString(player, (byte)2)+".Skill."+SkillName)==false)
					{
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList(ChatColor.DARK_AQUA+"최대 스킬 레벨 : " + ChatColor.WHITE+JobLevel,"",ChatColor.YELLOW+"[좌 클릭시 스킬 등록]"), loc, inv);
						loc++;	
					}
				}
				else
				{
					if(JobList.contains("Mabinogi.Added."+SkillName)==false)
					{
						Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList(ChatColor.DARK_AQUA+"최대 스킬 레벨 : " + ChatColor.WHITE+JobLevel,"",ChatColor.YELLOW+"[좌 클릭시 스킬 등록]"), loc, inv);
						loc++;	
					}
				}

			}
			else
			{
				Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList(ChatColor.DARK_AQUA+"최대 스킬 레벨 : " + ChatColor.WHITE+JobLevel,"",ChatColor.YELLOW+"[좌 클릭시 스킬 세부 설정]",ChatColor.YELLOW+"[Shift + 좌 클릭시 아이콘 변경]",ChatColor.RED+"[Shift + 우 클릭시 스킬 제거]"), loc, inv);
				loc++;	
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		if(isJobGUI == false)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 스킬", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 스킬을 생성합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+WhatJob), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+""+isJobGUI), 53, inv);
		player.openInventory(inv);
	}

	public void IndividualSkillOptionGUI(Player player, short page, String SkillName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "스킬 관리 : " + (page+1));

		Set<String> b= SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false);
		
		byte loc=0;
		for(int count = page*45; count < b.size();count++)
		{
			if(count > b.size() || loc >= 45) break;
			String lore = "";
			if(SkillList.getString(SkillName+".SkillRank."+(count+1)+".Command").equalsIgnoreCase("null"))
				lore = ChatColor.GRAY+"[지정된 커맨드 없음]%enter%";
			else
			{
				lore = ChatColor.DARK_AQUA+"커맨드 : "+ChatColor.WHITE+SkillList.getString(SkillName+".SkillRank."+(count+1)+".Command")+"%enter%";
				if(SkillList.getBoolean(SkillName+".SkillRank."+(count+1)+".BukkitPermission") == false)
					lore = lore + ChatColor.WHITE+"[개인 권한 사용]%enter%";
				else
					lore = lore + ChatColor.LIGHT_PURPLE+"[콘솔 권한 사용]%enter%";
			}
			
			if(SkillList.getString(SkillName+".SkillRank."+(count+1)+".MagicSpells").equalsIgnoreCase("null"))
				lore = lore + ChatColor.GRAY+"[지정 매직스펠 없음]%enter%";
			else
				lore = lore + ChatColor.DARK_AQUA+"매직스펠 : "+ChatColor.WHITE+SkillList.getString(SkillName+".SkillRank."+(count+1)+".MagicSpells")+"%enter%";

			if((page*45)+count != 0)
			{
				if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedLevel") >= 1)
					lore = lore + ChatColor.AQUA+"승급시 필요 레벨 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedLevel")+"%enter%";
				if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedRealLevel") >= 1)
					lore = lore + ChatColor.AQUA+"승급시 필요 누적 레벨 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedRealLevel")+"%enter%";

				lore = lore + ChatColor.AQUA+"승급시 필요 스킬 포인트 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".SkillPoint")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 생명력 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 생명력 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 마나 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 마나 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")+"%enter%";
			
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 "+ServerOption.STR+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 "+ServerOption.STR+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 "+ServerOption.DEX+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 "+ServerOption.DEX+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")+"%enter%";
				
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 "+ServerOption.INT+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 "+ServerOption.INT+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 "+ServerOption.WILL+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 "+ServerOption.WILL+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 "+ServerOption.LUK+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 "+ServerOption.LUK+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 밸런스 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 밸런스 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 크리티컬 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 크리티컬 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")+"%enter%";
			
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 방어 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 방어 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 보호 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 보호 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 마법 방어 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 마법 방어 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")<0)
				lore = lore + ChatColor.RED+"승급시 보너스 마법 보호 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")>0)
				lore = lore + ChatColor.AQUA+"승급시 보너스 마법 보호 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")+"%enter%";
			
			}
			if((page*45)+count ==0)
				lore = lore + "%enter%"+ChatColor.YELLOW+"[좌 클릭시 랭크 세부 설정]%enter%"+ChatColor.RED+"[1레벨 스킬은 삭제 불가능]";
			else if((count +1) == b.size())
				lore = lore + "%enter%"+ChatColor.YELLOW+"[좌 클릭시 랭크 세부 설정]%enter%"+ChatColor.RED+"[Shift + 우 클릭시 제거]";
			else
				lore = lore + "%enter%"+ChatColor.YELLOW+"[좌 클릭시 랭크 세부 설정]%enter%"+ChatColor.RED+"[상위 랭크 제거 후 제거 가능]";
			
			String[] scriptA = lore.split("%enter%");
			for(short counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  scriptA[counter];
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + SkillName +" 레벨 "+(count +1) , 340,0,1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(b.size()-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 랭크", 386,0,1,Arrays.asList(ChatColor.GRAY + "새로운 스킬 랭크를 생성 합니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+SkillName), 53, inv);
		player.openInventory(inv);
	}
	
	public void SkillRankOptionGUI(Player player, String SkillName, short SkillLevel)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "랭크 "+SkillLevel);
		String lore = "";
		if(SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Command").equalsIgnoreCase("null"))
			lore = ChatColor.GRAY+"[  없음  ]";
		else
			lore = ChatColor.WHITE+SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Command");
			
		Stack2(ChatColor.DARK_AQUA + "[커맨드 지정]", 137,0,1,Arrays.asList("",ChatColor.DARK_AQUA + "[현재 등록된 커맨드]",lore,"",ChatColor.YELLOW + "[좌 클릭시 커맨드 변경]"), 11, inv);
		if(SkillList.getBoolean(SkillName+".SkillRank."+SkillLevel+".BukkitPermission")==false)
			Stack2(ChatColor.DARK_AQUA + "[커맨드 권한]", 397,3,1,Arrays.asList("",ChatColor.DARK_AQUA + "[커맨드 실행시 사용될 권한]",ChatColor.GRAY+"[  개인  ]","",ChatColor.YELLOW + "[좌 클릭시 권한 변경]"), 13, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "[커맨드 권한]", 137,3,1,Arrays.asList("",ChatColor.DARK_AQUA + "[커맨드 실행시 사용될 권한]",ChatColor.LIGHT_PURPLE+"[  버킷  ]","",ChatColor.YELLOW + "[좌 클릭시 권한 변경]"), 13, inv);

		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
		{
			if(SkillList.getString(SkillName+".SkillRank."+SkillLevel+".MagicSpells").equalsIgnoreCase("null"))
				Stack2(ChatColor.DARK_AQUA + "[매직 스펠]", 280,0,1,Arrays.asList("",ChatColor.DARK_AQUA + "[현재 등록된 스펠]",ChatColor.GRAY + "[  없음  ]","",ChatColor.YELLOW + "[좌 클릭시 스펠 변경]"), 15, inv);
			else
				Stack2(ChatColor.DARK_AQUA + "[매직 스펠]", 369,0,1,Arrays.asList("",ChatColor.DARK_AQUA + "[현재 등록된 스펠]",ChatColor.WHITE+SkillList.getString(SkillName+".SkillRank."+SkillLevel+".MagicSpells"),"",ChatColor.YELLOW + "[좌 클릭시 스펠 변경]"), 15, inv);

			if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".AffectStat")==false)
			{
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "없음");
				SkillList.saveConfig();
			}
			String IncreaseDamage = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".AffectStat");
			
			if(IncreaseDamage.compareTo("없음")==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 공격력 상승]", 166,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "[스킬 고유 데미지로 사용]","",ChatColor.YELLOW + "[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.compareTo("생명력")==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 공격력 상승]", 351,1,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "[현재 생명력에 비례하여 대미지 상승]","",ChatColor.YELLOW + "[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.compareTo("마나")==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 공격력 상승]", 351,12,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "[현재 마나량에 비례하여 대미지 상승]","",ChatColor.YELLOW + "[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.STR)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 공격력 상승]", 267,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.STR+"에 비례하여 대미지 상승]","",ChatColor.YELLOW + "[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.DEX)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 공격력 상승]", 261,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.DEX+"에 비례하여 대미지 상승]","",ChatColor.YELLOW + "[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.INT)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 공격력 상승]", 369,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.INT+"에 비례하여 대미지 상승]","",ChatColor.YELLOW + "[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.WILL)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 공격력 상승]", 370,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.WILL+"에 비례하여 대미지 상승]","",ChatColor.YELLOW + "[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.compareTo(ServerOption.LUK)==0)
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 공격력 상승]", 322,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+IncreaseDamage+"  ]",ChatColor.RED + "["+ServerOption.LUK+"에 비례하여 대미지 상승]","",ChatColor.YELLOW + "[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 공격력 상승]", 322,0,1,Arrays.asList("",ChatColor.RED+"[재 설정이 필요합니다!]","",ChatColor.YELLOW + "[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
		}
		else
		{
			Stack2(ChatColor.RED + "[매직 스펠]", 166,0,1,Arrays.asList("",ChatColor.RED + "[MagicSpells 플러그인을 찾을 수 없음]",ChatColor.GRAY + "MagicSpells 플러그인이 있을 경우",ChatColor.GRAY + "사용할 수 있는 옵션입니다.",""), 15, inv);
			Stack2(ChatColor.RED + "[스킬 공격력 상승]", 166,0,1,Arrays.asList("",ChatColor.RED + "[MagicSpells 플러그인을 찾을 수 없음]",ChatColor.GRAY + "MagicSpells 플러그인이 있을 경우",ChatColor.GRAY + "사용할 수 있는 옵션입니다.",""), 21, inv);
		}

		if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon")==false)
		{
			SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "없음");
			SkillList.saveConfig();
		}
		String WeaponType = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon");
		switch(WeaponType)
		{
			case "없음":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 166,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.AQUA + "[제한 없이 스킬 발동 가능]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "근접 무기":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 267,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[해당되는 아이템 설명이 있어야 발동]",ChatColor.RED + "  한손 검",ChatColor.RED + "  양손 검",ChatColor.RED + "  도끼",ChatColor.RED + "  낫",ChatColor.RED + "  근접 무기",ChatColor.LIGHT_PURPLE + "  일반 검/도끼/괭이 아이템","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "한손 검":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 267,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[아이템 설명에 '한손 검'이 있어야 발동]",ChatColor.LIGHT_PURPLE + "[혹은 일반 검 아이템 장착]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "양손 검":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 267,0,2,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[아이템 설명에 '양손 검'이 있어야 발동]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "도끼":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 258,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[아이템 설명에 '도끼'가 있어야 발동]",ChatColor.LIGHT_PURPLE + "[혹은 일반 도끼 아이템 장착]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "낫":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 292,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[아이템 설명에 '낫'이 있어야 발동]",ChatColor.LIGHT_PURPLE + "[혹은 일반 괭이 아이템 장착]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "원거리 무기":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 261,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[해당되는 아이템 설명이 있어야 발동]",ChatColor.RED + "  활",ChatColor.RED + "  석궁",ChatColor.RED + "  원거리 무기",ChatColor.LIGHT_PURPLE + "  일반 활, 발사기 아이템","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "활":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 261,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[아이템 설명에 '활'이 있어야 발동]",ChatColor.LIGHT_PURPLE + "[혹은 일반 활 아이템 장착]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "석궁":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 23,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[아이템 설명에 '석궁'이 있어야 발동]",ChatColor.LIGHT_PURPLE + "[혹은 일반 발사기 아이템 장착]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "마법 무기":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 280,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[해당되는 아이템 설명이 있어야 발동]",ChatColor.RED + "  원드",ChatColor.RED + "  스태프",ChatColor.RED + "  마법 무기",ChatColor.LIGHT_PURPLE + "  일반 막대기/뼈/블레이즈 막대 아이템","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "원드":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 280,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[아이템 설명에 '원드'가 있어야 발동]",ChatColor.LIGHT_PURPLE + "[혹은 일반 막대기/뼈 아이템 장착]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "스태프":
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 369,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.RED + "[아이템 설명에 '스태프'가 있어야 발동]",ChatColor.LIGHT_PURPLE + "[혹은 일반 블레이즈 막대 아이템 장착]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			default :
				Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[무기 타입 제한]", 369,0,1,Arrays.asList("",ChatColor.WHITE+"[  "+WeaponType+"  ]",ChatColor.WHITE + "[아이템 설명에 '"+ChatColor.stripColor(WeaponType)+"'가 있어야 발동]","",ChatColor.YELLOW + "[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
		}

		if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".Lore")==false)
		{
			SkillList.set(SkillName+".SkillRank."+SkillLevel+".Lore", ChatColor.GRAY + "     [설명 없음]     ");
			SkillList.saveConfig();
		}
		
		String lore2 = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Lore");

		String[] scriptA = lore2.split("%enter%");
		for(short counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[스킬 설명]", 386,0,1,Arrays.asList(scriptA), 23, inv);
		
		if(SkillLevel != 1)
		{
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[필요 레벨]", 384,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급하는데 필요한 레벨]",ChatColor.WHITE +""+ChatColor.BOLD +" 레벨 "+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".NeedLevel")+" 이상",ChatColor.WHITE +""+ChatColor.BOLD +" 누적 레벨 "+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".NeedRealLevel") + " 이상","",ChatColor.YELLOW + "[좌 클릭시 레벨 변경]"), 3, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[필요 스킬 포인트]", 399,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급하는데 필요한 스킬 포인트]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".SkillPoint") +"포인트","",ChatColor.YELLOW + "[좌 클릭시 스킬 포인트 변경]"), 4, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 생명력]", 351,1,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 생명력]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusHP") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 28, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 마나]", 351,12,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 마나]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMP"),"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 29, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 "+ServerOption.STR+"]", 267,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 "+ServerOption.STR+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusSTR"),"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 30, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 "+ServerOption.DEX+"]", 261,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 "+ServerOption.DEX+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusDEX") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 31, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 "+ServerOption.INT+"]", 369,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 "+ServerOption.INT+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusINT"),"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 32, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 "+ServerOption.WILL+"]", 370,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 "+ServerOption.WILL+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusWILL") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 33, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 "+ServerOption.LUK+"]", 322,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 "+ServerOption.LUK+"]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusLUK") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 34, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 밸런스]", 283,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 밸런스]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusBAL") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 37, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 크리티컬]", 262,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 크리티컬]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusCRI") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 38, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 방어]", 307,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 방어]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusDEF") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 39, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 보호]", 306,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 보호]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusPRO") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 40, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 마법 방어]", 311,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 마법 방어]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMDEF") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 41, inv);
			Stack2(ChatColor.WHITE+""+ChatColor.BOLD + "[보너스 마법 보호]", 310,0,1,Arrays.asList("",ChatColor.AQUA + "["+SkillName+" "+(SkillLevel-1)+" 레벨에서",ChatColor.AQUA +"현재 레벨로 승급할 때 얻는 마법 보호]",ChatColor.WHITE +"     "+ChatColor.BOLD+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMPRO") ,"",ChatColor.YELLOW + "[좌 클릭시 보너스 스텟 변경]"), 42, inv);
		}
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다.",ChatColor.BLACK+""+SkillLevel), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+SkillName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	
	
	public void AllSkillsGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		String WhatJob = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		boolean isJobGUI = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));

		Object_UserData u = new Object_UserData();
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(isJobGUI==true)
			{
				if(WhatJob.equals("Maple"))
				{
					GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();
					JGUI.MapleStory_JobSetting(player, u.getString(player, (byte)3));
					u.clearAll(player);
				}
				else
				{
					GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();
					JGUI.Mabinogi_ChooseCategory(player, (short) 0);
					u.clearAll(player);
				}
			}
			else
			{
				OPBoxGUI OGUI = new OPBoxGUI();
				OGUI.OPBoxGUI_Main(player, (byte) 2);
				u.clearAll(player);
			}
			break;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			if(isJobGUI == true && WhatJob.equals("Maple"))
				u.clearAll(player);
			break;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AllSkillsGUI(player,(short) (page-1),isJobGUI,WhatJob);
			break;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AllSkillsGUI(player,(short) (page+1),isJobGUI,WhatJob);
			break;
		case 49://새 스킬
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 새로운 스킬 이름을 설정해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "CS");
			break;
		default :
			if(isJobGUI == true)
			{
				String SkillName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				if(WhatJob.equals("Maple"))
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
					JobList.set("MapleStory."+u.getString(player, (byte)3)+"."+u.getString(player, (byte)2)+".Skill."+SkillName+".1", null);
					JobList.saveConfig();
					GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();
					JGUI.MapleStory_JobSetting(player, u.getString(player, (byte)3));
					u.clearAll(player);
					YamlManager Config  = YC.getNewConfig("Config.yml");
					Config.set("Time.LastSkillChanged", new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000)-new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000));
					Config.saveConfig();
					new GoldBigDragon_RPG.ETC.Job().AllPlayerFixAllSkillAndJobYML();
				}
				else
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager JobList  = YC.getNewConfig("Skill/JobList.yml");
					JobList.set("Mabinogi.Added."+SkillName, WhatJob);
					JobList.set("Mabinogi."+WhatJob + "."+SkillName, false);
					JobList.saveConfig();
					AllSkillsGUI(player, page, isJobGUI, WhatJob);
				}
				return;
			}
			else
			{
				if(event.isShiftClick()==true&&event.isLeftClick()==true)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					player.closeInventory();
					player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 스킬 아이콘의 ID값을 입력 해 주세요!!");
					u.setType(player, "Skill");
					u.setString(player, (byte)1, "CSID");
					u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					break;
				}
				else if(event.isLeftClick()==true&&event.isRightClick()==false)
				{
					s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
					IndividualSkillOptionGUI(player, (short) 0, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true)
				{
					s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager Config  = YC.getNewConfig("Config.yml");
					Config.set("Time.LastSkillChanged", new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000)-new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000));
					Config.saveConfig();
					YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
					SkillList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					SkillList.saveConfig();
					AllSkillsGUI(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),false,"Maple");
					GoldBigDragon_RPG.ETC.Job J = new GoldBigDragon_RPG.ETC.Job();
					J.AllPlayerFixAllSkillAndJobYML();
				}
			}
			return;
		}
	}

	public void IndividualSkillOptionGUIClick(InventoryClickEvent event)
	{
		String SkillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		short size= (short) SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false).size();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			AllSkillsGUI(player, (short) 0,false,"Maple");
			break;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			IndividualSkillOptionGUI(player,(short) (page-1),SkillName);
			break;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			IndividualSkillOptionGUI(player,(short) (page+1),SkillName);
			break;
		case 49://새 랭크
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			SkillList.set(SkillName+".SkillRank."+(size+1)+".Command","null");
			SkillList.set(SkillName+".SkillRank."+(size+1)+".BukkitPermission",false);
			SkillList.set(SkillName+".SkillRank."+(size+1)+".MagicSpells","null");
			SkillList.set(SkillName+".SkillRank."+(size+1)+".SkillPoint",1000);
			SkillList.saveConfig();
			IndividualSkillOptionGUI(player,  page, SkillName);
			break;
		default :
			if(event.isLeftClick()==true&&event.isRightClick()==false)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				SkillRankOptionGUI(player,SkillName,(short) ((page*45)+event.getSlot()+1));
			}
			else if(event.isShiftClick()==true&&event.isRightClick()==true&&(page*45)+event.getSlot()!=0&&(page*45)+event.getSlot()+1==size)
			{
				YamlManager Config  = YC.getNewConfig("Config.yml");
				Config.set("Time.LastSkillChanged", new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000)-new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100000));
				Config.saveConfig();
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
				SkillList.removeKey(SkillName+".SkillRank."+(size));
				SkillList.saveConfig();
				IndividualSkillOptionGUI(player, page,SkillName);
				GoldBigDragon_RPG.ETC.Job J = new GoldBigDragon_RPG.ETC.Job();
				J.AllPlayerSkillRankFix();
			}
			return;
		}
	}
	
	public void SkillRankOptionGUIClick(InventoryClickEvent event)
	{
		String SkillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		short SkillLevel = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		Object_UserData u = new Object_UserData();
		
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 3://필요 레벨
		{
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 스킬을 배울 수 있는 레벨을 설정해 주세요!");
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[제한 없음 : 0] [최대 : "+Integer.MAX_VALUE+"]");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "NeedLV");
			u.setString(player, (byte)2, SkillName);
			u.setInt(player, (byte)4,SkillLevel);
		}
		return;
		case 4://필요 스킬 포인트
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 필요한 스킬 포인트를 설정해 주세요!");
				player.sendMessage(ChatColor.LIGHT_PURPLE+"[최소 : 0] [최대 : "+Byte.MAX_VALUE+"]");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "SP");
				u.setString(player, (byte)2, SkillName);
				u.setInt(player, (byte)4,SkillLevel);
			}
			return;
		case 11://커맨드 지정
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.sendMessage(ChatColor.DARK_AQUA+"[스킬] : /커맨드 [실행할 커맨드 입력]");
				player.sendMessage(ChatColor.LIGHT_PURPLE+"  /커맨드 없음"+ChatColor.WHITE+" 입력시 커맨드 해제");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "SKC");
				u.setString(player, (byte)2, SkillName);
				u.setInt(player, (byte)4,SkillLevel);
				player.closeInventory();
			}
			return;
		case 13://커맨드 권한
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(SkillList.getBoolean(SkillName+".SkillRank."+SkillLevel+".BukkitPermission") == true)
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".BukkitPermission", false);
			else
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".BukkitPermission", true);
			SkillList.saveConfig();
			SkillRankOptionGUI(player, SkillName, SkillLevel);
			return;
		case 15://매직 스펠
			if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
			{
				s.SP(player, Sound.HORSE_ARMOR, 0.8F, 1.9F);
				player.closeInventory();
				new OtherPlugins.SpellMain().ShowAllMaigcGUI(player, (short)0,SkillName,SkillLevel,(byte)0);
			}
			else
				s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.8F);
			return;
		case 19://무기 제한 변경
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			String DistrictWeapon = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon");
			YamlManager Target = YC.getNewConfig("Item/CustomType.yml");

		  	Object[] Type = Target.getKeys().toArray();
		  	if(Type.length==0)
		  	{
				switch(DistrictWeapon)
				{
					case "없음":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "근접 무기");
						break;
					case "근접 무기":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "한손 검");
						break;
					case "한손 검":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "양손 검");
						break;
					case "양손 검":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "도끼");
						break;
					case "도끼":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "낫");
						break;
					case "낫":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "원거리 무기");
						break;
					case "원거리 무기":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "활");
						break;
					case "활":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "석궁");
						break;
					case "석궁":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "마법 무기");
						break;
					case "마법 무기":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "원드");
						break;
					case "원드":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "스태프");
						break;
					case "스태프":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "없음");
						break;
					default:
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "없음");
						break;
				}
		  	}
		  	else
		  	{
				switch(DistrictWeapon)
				{
					case "없음":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "근접 무기");
						break;
					case "근접 무기":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "한손 검");
						break;
					case "한손 검":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "양손 검");
						break;
					case "양손 검":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "도끼");
						break;
					case "도끼":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "낫");
						break;
					case "낫":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "원거리 무기");
						break;
					case "원거리 무기":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "활");
						break;
					case "활":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "석궁");
						break;
					case "석궁":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "마법 무기");
						break;
					case "마법 무기":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "원드");
						break;
					case "원드":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "스태프");
						break;
					case "스태프":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", Type[0].toString());
						break;
					default:
						{
							boolean isExit = false;
							for(int count = 0; count < Type.length; count++)
							{
								if((SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon").contains(Type[count].toString())))
								{
									if(count+1 == Type.length)
										SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon","없음");
									else
										SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon",ChatColor.WHITE+Type[(count+1)].toString());
									isExit = true;
									break;
								}
							}
							if(isExit==false)
								SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon","없음");
						}
						break;
				}
		  	}
			SkillList.saveConfig();
			SkillRankOptionGUI(player, SkillName, SkillLevel);
			return;
		case 21://스킬 대미지 상승
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
			{
				String switchNeed = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".AffectStat");
				if(switchNeed.compareTo("없음")==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "생명력");
				else if(switchNeed.compareTo("생명력")==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "마나");
				else if(switchNeed.compareTo("마나")==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.STR);
				else if(switchNeed.compareTo(ServerOption.STR)==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.DEX);
				else if(switchNeed.compareTo(ServerOption.DEX)==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.INT);
				else if(switchNeed.compareTo(ServerOption.INT)==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.WILL);
				else if(switchNeed.compareTo(ServerOption.WILL)==0)
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", ServerOption.LUK);
				else
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "없음");

				SkillList.saveConfig();
				SkillRankOptionGUI(player, SkillName, SkillLevel);
			}
			else
			{
				s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.8F);
			}
			return;

		case 23://스킬 설명
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 스킬 설명을 작성 해 주세요!");
			player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄워 쓰기 -");
			player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
			ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
					ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
			ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c" +
					ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "SKL");
			u.setString(player, (byte)2, SkillName);
			u.setInt(player, (byte)4,SkillLevel);
			player.closeInventory();
			return;
		case 28://보너스 생명력
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 생명력 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BH");
			break;
		case 29://보너스 마나
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 마나 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BM");
			break;
		case 30://보너스 "+GoldBigDragon_RPG.ServerOption.STR+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 "+ServerOption.STR+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BSTR");
			break;
		case 31://보너스 "+GoldBigDragon_RPG.ServerOption.DEX+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 "+ServerOption.DEX+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BDEX");
			break;
		case 32://보너스 "+GoldBigDragon_RPG.ServerOption.INT+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 "+ServerOption.INT+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BINT");
			break;
		case 33://보너스 "+GoldBigDragon_RPG.ServerOption.WILL+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 "+ServerOption.WILL+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BWILL");
			break;
		case 34://보너스 "+GoldBigDragon_RPG.ServerOption.LUK+"
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 "+ServerOption.LUK+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BLUK");
			break;
		case 37://보너스 밸런스
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 밸런스 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BBAL");
			break;
		case 38://보너스 크리티컬
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 크리티컬 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BCRI");
			break;
		case 39://보너스 방어
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 방어 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BDEF");
			break;
		case 40://보너스 보호
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 보호 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BPRO");
			break;
		case 41://보너스 마법 방어
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 마법 방어 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BMDEF");
			break;
		case 42://보너스 마법 보호
			player.sendMessage(ChatColor.LIGHT_PURPLE+"[스킬] : 보너스 마법 보호 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BMPRO");
			break;
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			IndividualSkillOptionGUI(player, (short) 0, SkillName);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		player.closeInventory();
		player.sendMessage(ChatColor.LIGHT_PURPLE+"[최소 : "+Byte.MIN_VALUE+"] [최대 : "+Byte.MAX_VALUE+"]");
		s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
		u.setType(player, "Skill");
		u.setString(player, (byte)2, SkillName);
		u.setInt(player, (byte)4,SkillLevel);
	}

}
