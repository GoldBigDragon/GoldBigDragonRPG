package skill;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import admin.OPboxGui;
import effect.SoundEffect;
import main.MainServerOption;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OPboxSkillGui extends GuiUtil
{
	public void AllSkillsGUI(Player player, short page, boolean isJobGUI,String WhatJob)
	{
		YamlLoader SkillList = new YamlLoader();
		SkillList.getConfig("Skill/SkillList.yml");
		String UniqueCode = "§0§0§b§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0전체 스킬 목록 : " + (page+1));
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
				YamlLoader JobList = new YamlLoader();
				JobList.getConfig("Skill/JobList.yml");
				if(WhatJob.equals("Maple"))
				{
					UserDataObject u = new UserDataObject();
					if(JobList.contains("MapleStory."+u.getString(player, (byte)3)+"."+u.getString(player, (byte)2)+".Skill."+SkillName)==false)
					{
						removeFlagStack("§f§l" + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList("§3최대 스킬 레벨 : §f"+JobLevel,"","§e[좌 클릭시 스킬 등록]"), loc, inv);
						loc++;	
					}
				}
				else
				{
					if(JobList.contains("Mabinogi.Added."+SkillName)==false)
					{
						removeFlagStack("§f§l" + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList("§3최대 스킬 레벨 : §f"+JobLevel,"","§e[좌 클릭시 스킬 등록]"), loc, inv);
						loc++;	
					}
				}

			}
			else
			{
				removeFlagStack("§f§l" + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList("§3최대 스킬 레벨 : §f"+JobLevel,"","§e[좌 클릭시 스킬 세부 설정]","§e[Shift + 좌 클릭시 아이콘 변경]","§c[Shift + 우 클릭시 스킬 제거]"), loc, inv);
				loc++;	
			}
		}
		
		if(a.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		if(isJobGUI == false)
			removeFlagStack("§f§l새 스킬", 386,0,1,Arrays.asList("§7새로운 스킬을 생성합니다."), 49, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+WhatJob), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+isJobGUI), 53, inv);
		player.openInventory(inv);
	}

	public void IndividualSkillOptionGUI(Player player, short page, String SkillName)
	{
		YamlLoader SkillList = new YamlLoader();
		SkillList.getConfig("Skill/SkillList.yml");

		String UniqueCode = "§0§0§b§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0스킬 관리 : " + (page+1));

		Set<String> b= SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false);
		
		byte loc=0;
		for(int count = page*45; count < b.size();count++)
		{
			if(count > b.size() || loc >= 45) break;
			String lore = "";
			if(SkillList.getString(SkillName+".SkillRank."+(count+1)+".Command").equalsIgnoreCase("null"))
				lore = "§7[지정된 커맨드 없음]%enter%";
			else
			{
				lore = "§3커맨드 : §f"+SkillList.getString(SkillName+".SkillRank."+(count+1)+".Command")+"%enter%";
				if(SkillList.getBoolean(SkillName+".SkillRank."+(count+1)+".BukkitPermission") == false)
					lore = lore + "§f[개인 권한 사용]%enter%";
				else
					lore = lore + "§d[콘솔 권한 사용]%enter%";
			}
			
			if(SkillList.getString(SkillName+".SkillRank."+(count+1)+".MagicSpells").equalsIgnoreCase("null"))
				lore = lore + "§7[지정 매직스펠 없음]%enter%";
			else
				lore = lore + "§3매직스펠 : §f"+SkillList.getString(SkillName+".SkillRank."+(count+1)+".MagicSpells")+"%enter%";

			if((page*45)+count != 0)
			{
				if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedLevel") >= 1)
					lore = lore + "§b승급시 필요 레벨 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedLevel")+"%enter%";
				if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedRealLevel") >= 1)
					lore = lore + "§b승급시 필요 누적 레벨 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedRealLevel")+"%enter%";

				lore = lore + "§b승급시 필요 스킬 포인트 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".SkillPoint")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")<0)
				lore = lore + "§c승급시 보너스 생명력 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")>0)
				lore = lore + "§b승급시 보너스 생명력 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")<0)
				lore = lore + "§c승급시 보너스 마나 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")>0)
				lore = lore + "§b승급시 보너스 마나 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")+"%enter%";
			
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")<0)
				lore = lore + "§c승급시 보너스 "+MainServerOption.statSTR+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")>0)
				lore = lore + "§b승급시 보너스 "+MainServerOption.statSTR+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")<0)
				lore = lore + "§c승급시 보너스 "+MainServerOption.statDEX+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")>0)
				lore = lore + "§b승급시 보너스 "+MainServerOption.statDEX+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")+"%enter%";
				
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")<0)
				lore = lore + "§c승급시 보너스 "+MainServerOption.statINT+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")>0)
				lore = lore + "§b승급시 보너스 "+MainServerOption.statINT+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")<0)
				lore = lore + "§c승급시 보너스 "+MainServerOption.statWILL+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")>0)
				lore = lore + "§b승급시 보너스 "+MainServerOption.statWILL+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")<0)
				lore = lore + "§c승급시 보너스 "+MainServerOption.statLUK+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")>0)
				lore = lore + "§b승급시 보너스 "+MainServerOption.statLUK+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")<0)
				lore = lore + "§c승급시 보너스 밸런스 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")>0)
				lore = lore + "§b승급시 보너스 밸런스 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")<0)
				lore = lore + "§c승급시 보너스 크리티컬 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")>0)
				lore = lore + "§b승급시 보너스 크리티컬 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")+"%enter%";
			
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")<0)
				lore = lore + "§c승급시 보너스 방어 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")>0)
				lore = lore + "§b승급시 보너스 방어 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")<0)
				lore = lore + "§c승급시 보너스 보호 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")>0)
				lore = lore + "§b승급시 보너스 보호 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")<0)
				lore = lore + "§c승급시 보너스 마법 방어 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")>0)
				lore = lore + "§b승급시 보너스 마법 방어 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")<0)
				lore = lore + "§c승급시 보너스 마법 보호 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")>0)
				lore = lore + "§b승급시 보너스 마법 보호 : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")+"%enter%";
			
			}
			if((page*45)+count ==0)
				lore = lore + "%enter%§e[좌 클릭시 랭크 세부 설정]%enter%§c[1레벨 스킬은 삭제 불가능]";
			else if((count +1) == b.size())
				lore = lore + "%enter%§e[좌 클릭시 랭크 세부 설정]%enter%§c[Shift + 우 클릭시 제거]";
			else
				lore = lore + "%enter%§e[좌 클릭시 랭크 세부 설정]%enter%§c[상위 랭크 제거 후 제거 가능]";
			
			String[] scriptA = lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  scriptA[counter];
			removeFlagStack("§f§l" + SkillName +" 레벨 "+(count +1) , 340,0,1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(b.size()-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		removeFlagStack("§f§l새 랭크", 386,0,1,Arrays.asList("§7새로운 스킬 랭크를 생성 합니다."), 49, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+SkillName), 53, inv);
		player.openInventory(inv);
	}
	
	public void SkillRankOptionGUI(Player player, String SkillName, short SkillLevel)
	{
		YamlLoader SkillList = new YamlLoader();
		SkillList.getConfig("Skill/SkillList.yml");
		String UniqueCode = "§0§0§b§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0랭크 "+SkillLevel);
		String lore = "";
		if(SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Command").equalsIgnoreCase("null"))
			lore = "§7[  없음  ]";
		else
			lore = "§f"+SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Command");
			
		removeFlagStack("§3[커맨드 지정]", 137,0,1,Arrays.asList("","§3[현재 등록된 커맨드]",lore,"","§e[좌 클릭시 커맨드 변경]"), 11, inv);
		if(SkillList.getBoolean(SkillName+".SkillRank."+SkillLevel+".BukkitPermission")==false)
			removeFlagStack("§3[커맨드 권한]", 397,3,1,Arrays.asList("","§3[커맨드 실행시 사용될 권한]","§7[  개인  ]","","§e[좌 클릭시 권한 변경]"), 13, inv);
		else
			removeFlagStack("§3[커맨드 권한]", 137,3,1,Arrays.asList("","§3[커맨드 실행시 사용될 권한]","§d[  버킷  ]","","§e[좌 클릭시 권한 변경]"), 13, inv);

		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
		{
			if(SkillList.getString(SkillName+".SkillRank."+SkillLevel+".MagicSpells").equalsIgnoreCase("null"))
				removeFlagStack("§3[매직 스펠]", 280,0,1,Arrays.asList("","§3[현재 등록된 스펠]","§7[  없음  ]","","§e[좌 클릭시 스펠 변경]"), 15, inv);
			else
				removeFlagStack("§3[매직 스펠]", 369,0,1,Arrays.asList("","§3[현재 등록된 스펠]","§f"+SkillList.getString(SkillName+".SkillRank."+SkillLevel+".MagicSpells"),"","§e[좌 클릭시 스펠 변경]"), 15, inv);

			if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".AffectStat")==false)
			{
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "없음");
				SkillList.saveConfig();
			}
			String IncreaseDamage = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".AffectStat");
			
			if(IncreaseDamage.equals("없음"))
				removeFlagStack("§f§l[스킬 공격력 상승]", 166,0,1,Arrays.asList("","§f[  "+IncreaseDamage+"  ]","§c[스킬 고유 데미지로 사용]","","§e[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.equals("생명력"))
				removeFlagStack("§f§l[스킬 공격력 상승]", 351,1,1,Arrays.asList("","§f[  "+IncreaseDamage+"  ]","§c[현재 생명력에 비례하여 대미지 상승]","","§e[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.equals("마나"))
				removeFlagStack("§f§l[스킬 공격력 상승]", 351,12,1,Arrays.asList("","§f[  "+IncreaseDamage+"  ]","§c[현재 마나량에 비례하여 대미지 상승]","","§e[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.equals(MainServerOption.statSTR))
				removeFlagStack("§f§l[스킬 공격력 상승]", 267,0,1,Arrays.asList("","§f[  "+IncreaseDamage+"  ]","§c["+MainServerOption.statSTR+"에 비례하여 대미지 상승]","","§e[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.equals(MainServerOption.statDEX))
				removeFlagStack("§f§l[스킬 공격력 상승]", 261,0,1,Arrays.asList("","§f[  "+IncreaseDamage+"  ]","§c["+MainServerOption.statDEX+"에 비례하여 대미지 상승]","","§e[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.equals(MainServerOption.statINT))
				removeFlagStack("§f§l[스킬 공격력 상승]", 369,0,1,Arrays.asList("","§f[  "+IncreaseDamage+"  ]","§c["+MainServerOption.statINT+"에 비례하여 대미지 상승]","","§e[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.equals(MainServerOption.statWILL))
				removeFlagStack("§f§l[스킬 공격력 상승]", 370,0,1,Arrays.asList("","§f[  "+IncreaseDamage+"  ]","§c["+MainServerOption.statWILL+"에 비례하여 대미지 상승]","","§e[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else if(IncreaseDamage.equals(MainServerOption.statLUK))
				removeFlagStack("§f§l[스킬 공격력 상승]", 322,0,1,Arrays.asList("","§f[  "+IncreaseDamage+"  ]","§c["+MainServerOption.statLUK+"에 비례하여 대미지 상승]","","§e[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
			else
				removeFlagStack("§f§l[스킬 공격력 상승]", 322,0,1,Arrays.asList("","§c[재 설정이 필요합니다!]","","§e[좌 클릭시 영향 주는 스텟 변경]"), 21, inv);
		}
		else
		{
			removeFlagStack("§c[매직 스펠]", 166,0,1,Arrays.asList("","§c[MagicSpells 플러그인을 찾을 수 없음]","§7MagicSpells 플러그인이 있을 경우","§7사용할 수 있는 옵션입니다.",""), 15, inv);
			removeFlagStack("§c[스킬 공격력 상승]", 166,0,1,Arrays.asList("","§c[MagicSpells 플러그인을 찾을 수 없음]","§7MagicSpells 플러그인이 있을 경우","§7사용할 수 있는 옵션입니다.",""), 21, inv);
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
				removeFlagStack("§f§l[무기 타입 제한]", 166,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§b[제한 없이 스킬 발동 가능]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "근접 무기":
				removeFlagStack("§f§l[무기 타입 제한]", 267,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[해당되는 아이템 설명이 있어야 발동]","§c  한손 검","§c  양손 검","§c  도끼","§c  낫","§c  근접 무기","§d  일반 검/도끼/괭이 아이템","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "한손 검":
				removeFlagStack("§f§l[무기 타입 제한]", 267,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[아이템 설명에 '한손 검'이 있어야 발동]","§d[혹은 일반 검 아이템 장착]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "양손 검":
				removeFlagStack("§f§l[무기 타입 제한]", 267,0,2,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[아이템 설명에 '양손 검'이 있어야 발동]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "도끼":
				removeFlagStack("§f§l[무기 타입 제한]", 258,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[아이템 설명에 '도끼'가 있어야 발동]","§d[혹은 일반 도끼 아이템 장착]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "낫":
				removeFlagStack("§f§l[무기 타입 제한]", 292,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[아이템 설명에 '낫'이 있어야 발동]","§d[혹은 일반 괭이 아이템 장착]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "원거리 무기":
				removeFlagStack("§f§l[무기 타입 제한]", 261,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[해당되는 아이템 설명이 있어야 발동]","§c  활","§c  석궁","§c  원거리 무기","§d  일반 활, 발사기 아이템","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "활":
				removeFlagStack("§f§l[무기 타입 제한]", 261,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[아이템 설명에 '활'이 있어야 발동]","§d[혹은 일반 활 아이템 장착]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "석궁":
				removeFlagStack("§f§l[무기 타입 제한]", 23,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[아이템 설명에 '석궁'이 있어야 발동]","§d[혹은 일반 발사기 아이템 장착]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "마법 무기":
				removeFlagStack("§f§l[무기 타입 제한]", 280,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[해당되는 아이템 설명이 있어야 발동]","§c  원드","§c  스태프","§c  마법 무기","§d  일반 막대기/뼈/블레이즈 막대 아이템","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "원드":
				removeFlagStack("§f§l[무기 타입 제한]", 280,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[아이템 설명에 '원드'가 있어야 발동]","§d[혹은 일반 막대기/뼈 아이템 장착]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			case "스태프":
				removeFlagStack("§f§l[무기 타입 제한]", 369,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§c[아이템 설명에 '스태프'가 있어야 발동]","§d[혹은 일반 블레이즈 막대 아이템 장착]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
			default :
				removeFlagStack("§f§l[무기 타입 제한]", 369,0,1,Arrays.asList("","§f[  "+WeaponType+"  ]","§f[아이템 설명에 '"+ChatColor.stripColor(WeaponType)+"'가 있어야 발동]","","§e[좌 클릭시 사용 무기 변경]"), 19, inv);
				break;
		}

		if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".Lore")==false)
		{
			SkillList.set(SkillName+".SkillRank."+SkillLevel+".Lore", "§7     [설명 없음]     ");
			SkillList.saveConfig();
		}
		
		String lore2 = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Lore");

		String[] scriptA = lore2.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		removeFlagStack("§f§l[스킬 설명]", 386,0,1,Arrays.asList(scriptA), 23, inv);
		
		if(SkillLevel != 1)
		{
			removeFlagStack("§f§l[필요 레벨]", 384,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급하는데 필요한 레벨]","§f§l 레벨 "+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".NeedLevel")+" 이상","§f§l 누적 레벨 "+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".NeedRealLevel") + " 이상","","§e[좌 클릭시 레벨 변경]"), 3, inv);
			removeFlagStack("§f§l[필요 스킬 포인트]", 399,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급하는데 필요한 스킬 포인트]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".SkillPoint") +"포인트","","§e[좌 클릭시 스킬 포인트 변경]"), 4, inv);
			removeFlagStack("§f§l[보너스 생명력]", 351,1,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 생명력]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusHP") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 28, inv);
			removeFlagStack("§f§l[보너스 마나]", 351,12,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 마나]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMP"),"","§e[좌 클릭시 보너스 스텟 변경]"), 29, inv);
			removeFlagStack("§f§l[보너스 "+MainServerOption.statSTR+"]", 267,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 "+MainServerOption.statSTR+"]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusSTR"),"","§e[좌 클릭시 보너스 스텟 변경]"), 30, inv);
			removeFlagStack("§f§l[보너스 "+MainServerOption.statDEX+"]", 261,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 "+MainServerOption.statDEX+"]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusDEX") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 31, inv);
			removeFlagStack("§f§l[보너스 "+MainServerOption.statINT+"]", 369,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 "+MainServerOption.statINT+"]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusINT"),"","§e[좌 클릭시 보너스 스텟 변경]"), 32, inv);
			removeFlagStack("§f§l[보너스 "+MainServerOption.statWILL+"]", 370,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 "+MainServerOption.statWILL+"]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusWILL") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 33, inv);
			removeFlagStack("§f§l[보너스 "+MainServerOption.statLUK+"]", 322,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 "+MainServerOption.statLUK+"]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusLUK") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 34, inv);
			removeFlagStack("§f§l[보너스 밸런스]", 283,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 밸런스]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusBAL") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 37, inv);
			removeFlagStack("§f§l[보너스 크리티컬]", 262,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 크리티컬]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusCRI") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 38, inv);
			removeFlagStack("§f§l[보너스 방어]", 307,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 방어]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusDEF") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 39, inv);
			removeFlagStack("§f§l[보너스 보호]", 306,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 보호]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusPRO") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 40, inv);
			removeFlagStack("§f§l[보너스 마법 방어]", 311,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 마법 방어]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMDEF") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 41, inv);
			removeFlagStack("§f§l[보너스 마법 보호]", 310,0,1,Arrays.asList("","§b["+SkillName+" "+(SkillLevel-1)+" 레벨에서","§b현재 레벨로 승급할 때 얻는 마법 보호]","§f     §l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMPRO") ,"","§e[좌 클릭시 보너스 스텟 변경]"), 42, inv);
		}
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+SkillLevel), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+SkillName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void AllSkillsGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		

		boolean isJobGUI = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		String WhatJob = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		UserDataObject u = new UserDataObject();
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			if(isJobGUI == true && WhatJob.equals("Maple"))
				u.clearAll(player);
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
			{
				if(isJobGUI==true)
				{
					if(WhatJob.equals("Maple"))
					{
						new job.JobGUI().MapleStory_JobSetting(player, u.getString(player, (byte)3));
						u.clearAll(player);
					}
					else
					{
						new job.JobGUI().Mabinogi_ChooseCategory(player, (short) 0);
						u.clearAll(player);
					}
				}
				else
				{
					new OPboxGui().opBoxGuiMain(player, (byte) 2);
					u.clearAll(player);
				}
			}
			else if(slot == 48)//이전 페이지
				AllSkillsGUI(player,(short) (page-1),isJobGUI,WhatJob);
			else if(slot == 50)//다음 페이지
				AllSkillsGUI(player,(short) (page+1),isJobGUI,WhatJob);
			else if(slot == 49)//새 스킬
			{
				player.closeInventory();
				player.sendMessage("§d[스킬] : 새로운 스킬 이름을 설정해 주세요!");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "CS");
			}
			else
			{
				if(isJobGUI == true)
				{
					String SkillName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
					if(WhatJob.equals("Maple"))
					{
						SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						YamlLoader JobList = new YamlLoader();
						JobList.getConfig("Skill/JobList.yml");
						JobList.createSection("MapleStory."+u.getString(player, (byte)3)+"."+u.getString(player, (byte)2)+".Skill."+SkillName);
						JobList.saveConfig();
						job.JobGUI JGUI = new job.JobGUI();
						JGUI.MapleStory_JobSetting(player, u.getString(player, (byte)3));
						u.clearAll(player);
						YamlLoader Config = new YamlLoader();
						Config.getConfig("Config.yml");
						Config.set("Time.LastSkillChanged", new util.NumericUtil().RandomNum(0, 100000)-new util.NumericUtil().RandomNum(0, 100000));
						Config.saveConfig();
						new job.JobMain().AllPlayerFixAllSkillAndJobYML();
					}
					else
					{
						SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						YamlLoader JobList = new YamlLoader();
						JobList.getConfig("Skill/JobList.yml");
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
						SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						player.closeInventory();
						player.sendMessage("§d[스킬] : 스킬 아이콘의 ID값을 입력 해 주세요!!");
						u.setType(player, "Skill");
						u.setString(player, (byte)1, "CSID");
						u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
					else if(event.isLeftClick()==true&&event.isRightClick()==false)
					{
						SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						IndividualSkillOptionGUI(player, (short) 0, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
					else if(event.isShiftClick()==true&&event.isRightClick()==true)
					{
						SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						YamlLoader Config = new YamlLoader();
						Config.getConfig("Config.yml");
						Config.set("Time.LastSkillChanged", new util.NumericUtil().RandomNum(0, 100000)-new util.NumericUtil().RandomNum(0, 100000));
						Config.saveConfig();
						YamlLoader SkillList = new YamlLoader();
						SkillList.getConfig("Skill/SkillList.yml");
						SkillList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						SkillList.saveConfig();
						AllSkillsGUI(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),false,"Maple");
						job.JobMain J = new job.JobMain();
						J.AllPlayerFixAllSkillAndJobYML();
					}
				}
			}
		}
	}

	public void IndividualSkillOptionGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			String SkillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				AllSkillsGUI(player, (short) 0,false,"Maple");
			else if(slot == 48)//이전 페이지
				IndividualSkillOptionGUI(player,(short) (page-1),SkillName);
			else if(slot == 50)//다음 페이지
				IndividualSkillOptionGUI(player,(short) (page+1),SkillName);
			else if(slot == 49)//새 랭크
			{
				YamlLoader SkillList = new YamlLoader();
				SkillList.getConfig("Skill/SkillList.yml");
				short size= (short) SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false).size();
				SkillList.set(SkillName+".SkillRank."+(size+1)+".Command","null");
				SkillList.set(SkillName+".SkillRank."+(size+1)+".BukkitPermission",false);
				SkillList.set(SkillName+".SkillRank."+(size+1)+".MagicSpells","null");
				SkillList.set(SkillName+".SkillRank."+(size+1)+".SkillPoint",1000);
				SkillList.saveConfig();
				IndividualSkillOptionGUI(player,  page, SkillName);
			}
			else
			{
				YamlLoader Config = new YamlLoader();
				Config.getConfig("Config.yml");
				YamlLoader SkillList = new YamlLoader();
				SkillList.getConfig("Skill/SkillList.yml");
				short size= (short) SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false).size();
				if(event.isLeftClick()==true&&event.isRightClick()==false)
				{
					SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					SkillRankOptionGUI(player,SkillName,(short) ((page*45)+event.getSlot()+1));
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true&&(page*45)+event.getSlot()!=0&&(page*45)+event.getSlot()+1==size)
				{
					Config.set("Time.LastSkillChanged", new util.NumericUtil().RandomNum(0, 100000)-new util.NumericUtil().RandomNum(0, 100000));
					Config.saveConfig();
					SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					SkillList.removeKey(SkillName+".SkillRank."+(size));
					SkillList.saveConfig();
					IndividualSkillOptionGUI(player, page,SkillName);
					job.JobMain J = new job.JobMain();
					J.AllPlayerSkillRankFix();
				}
			}
		}
	}
	
	public void SkillRankOptionGUIClick(InventoryClickEvent event)
	{
		String SkillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		short SkillLevel = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
		
		YamlLoader SkillList = new YamlLoader();
		SkillList.getConfig("Skill/SkillList.yml");
		

		UserDataObject u = new UserDataObject();
		
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 3://필요 레벨
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage("§d[스킬] : 스킬을 배울 수 있는 레벨을 설정해 주세요!");
			player.sendMessage("§d[제한 없음 : 0] [최대 : "+Integer.MAX_VALUE+"]");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "NeedLV");
			u.setString(player, (byte)2, SkillName);
			u.setInt(player, (byte)4,SkillLevel);
		}
		return;
		case 4://필요 스킬 포인트
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage("§d[스킬] : 필요한 스킬 포인트를 설정해 주세요!");
				player.sendMessage("§d[최소 : 0] [최대 : "+Byte.MAX_VALUE+"]");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "SP");
				u.setString(player, (byte)2, SkillName);
				u.setInt(player, (byte)4,SkillLevel);
			}
			return;
		case 11://커맨드 지정
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				player.sendMessage("§3[스킬] : /커맨드 [실행할 커맨드 입력]");
				player.sendMessage("§d  /커맨드 없음§f 입력시 커맨드 해제");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "SKC");
				u.setString(player, (byte)2, SkillName);
				u.setInt(player, (byte)4,SkillLevel);
				player.closeInventory();
			}
			return;
		case 13://커맨드 권한
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
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
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.9F);
				player.closeInventory();
				new otherplugins.SpellMain().ShowAllMaigcGUI(player, (short)0,SkillName,SkillLevel,(byte)0);
			}
			else
				SoundEffect.playSound(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.8F);
			return;
		case 19://무기 제한 변경
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String DistrictWeapon = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon");
			YamlLoader Target = new YamlLoader();
			Target.getConfig("Item/CustomType.yml");

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
										SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon","§f"+Type[(count+1)].toString());
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
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
			{
				String switchNeed = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".AffectStat");
				if(switchNeed.equals("없음"))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "생명력");
				else if(switchNeed.equals("생명력"))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "마나");
				else if(switchNeed.equals("마나"))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", MainServerOption.statSTR);
				else if(switchNeed.equals(MainServerOption.statSTR))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", MainServerOption.statDEX);
				else if(switchNeed.equals(MainServerOption.statDEX))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", MainServerOption.statINT);
				else if(switchNeed.equals(MainServerOption.statINT))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", MainServerOption.statWILL);
				else if(switchNeed.equals(MainServerOption.statWILL))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", MainServerOption.statLUK);
				else
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "없음");

				SkillList.saveConfig();
				SkillRankOptionGUI(player, SkillName, SkillLevel);
			}
			else
			{
				SoundEffect.playSound(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.8F);
			}
			return;

		case 23://스킬 설명
			player.sendMessage("§d[스킬] : 스킬 설명을 작성 해 주세요!");
			player.sendMessage("§6%enter%§f - 한줄 띄워 쓰기 -");
			player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
			"§3&3 §4&4 §5&5 " +
					"§6&6 §7&7 §8&8 " +
			"§9&9 §a&a §b&b §c&c" +
					"§d&d §e&e §f&f");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "SKL");
			u.setString(player, (byte)2, SkillName);
			u.setInt(player, (byte)4,SkillLevel);
			player.closeInventory();
			return;
		case 28://보너스 생명력
			player.sendMessage("§d[스킬] : 보너스 생명력 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BH");
			break;
		case 29://보너스 마나
			player.sendMessage("§d[스킬] : 보너스 마나 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BM");
			break;
		case 30://보너스 "+GoldBigDragon_RPG.ServerOption.STR+"
			player.sendMessage("§d[스킬] : 보너스 "+MainServerOption.statSTR+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BSTR");
			break;
		case 31://보너스 "+GoldBigDragon_RPG.ServerOption.DEX+"
			player.sendMessage("§d[스킬] : 보너스 "+MainServerOption.statDEX+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BDEX");
			break;
		case 32://보너스 "+GoldBigDragon_RPG.ServerOption.INT+"
			player.sendMessage("§d[스킬] : 보너스 "+MainServerOption.statINT+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BINT");
			break;
		case 33://보너스 "+GoldBigDragon_RPG.ServerOption.WILL+"
			player.sendMessage("§d[스킬] : 보너스 "+MainServerOption.statWILL+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BWILL");
			break;
		case 34://보너스 "+GoldBigDragon_RPG.ServerOption.LUK+"
			player.sendMessage("§d[스킬] : 보너스 "+MainServerOption.statLUK+" 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BLUK");
			break;
		case 37://보너스 밸런스
			player.sendMessage("§d[스킬] : 보너스 밸런스 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BBAL");
			break;
		case 38://보너스 크리티컬
			player.sendMessage("§d[스킬] : 보너스 크리티컬 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BCRI");
			break;
		case 39://보너스 방어
			player.sendMessage("§d[스킬] : 보너스 방어 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BDEF");
			break;
		case 40://보너스 보호
			player.sendMessage("§d[스킬] : 보너스 보호 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BPRO");
			break;
		case 41://보너스 마법 방어
			player.sendMessage("§d[스킬] : 보너스 마법 방어 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BMDEF");
			break;
		case 42://보너스 마법 보호
			player.sendMessage("§d[스킬] : 보너스 마법 보호 수치를 입력해 주세요!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BMPRO");
			break;
		case 45://이전 목록
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			IndividualSkillOptionGUI(player, (short) 0, SkillName);
			return;
		case 53://나가기
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		player.closeInventory();
		player.sendMessage("§d[최소 : "+Byte.MIN_VALUE+"] [최대 : "+Byte.MAX_VALUE+"]");
		SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
		u.setType(player, "Skill");
		u.setString(player, (byte)2, SkillName);
		u.setInt(player, (byte)4,SkillLevel);
	}
}
