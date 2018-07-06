package area.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import battle.BattleCalculator;
import effect.SoundEffect;
import main.MainServerOption;
import util.GuiUtil;
import util.YamlLoader;

public class AreaSpawnSpecialMonsterListGui extends GuiUtil{

	private String uniqueCode = "§0§0§2§0§8§r";
	
	public void areaSpawnSpecialMonsterListGui(Player player, int page,String areaName,String ruleCount)
	{
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 특수 몬스터  : " + (page+1));

		String[] monsterList= monsterYaml.getKeys().toArray(new String[0]);

		byte loc=0;
		String monsterName = null;
		StringBuilder sb = new StringBuilder();
		for(int count = page*45; count < monsterList.length;count++)
		{
			if(count > monsterList.length || loc >= 45) break;
			monsterName = monsterList[count];
			sb = new StringBuilder();
			sb.append("%enter%§f§l 이름 : §f");
			sb.append(monsterYaml.getString(monsterName+".Name"));
			sb.append("%enter%");
			sb.append("§f§l 타입 : §f");
			sb.append(monsterYaml.getString(monsterName+".Type"));
			sb.append("%enter%");
			sb.append("§f§l 스폰 바이옴 : §f");
			sb.append(monsterYaml.getString(monsterName+".Biome"));
			sb.append("%enter%");
			sb.append("§c§l 생명력 : §f");
			sb.append(monsterYaml.getInt(monsterName+".HP"));
			sb.append("%enter%");
			sb.append("§b§l 경험치 : §f");
			sb.append(monsterYaml.getInt(monsterName+".EXP"));
			sb.append("%enter%");
			sb.append("§e§l 드랍 금액 : §f");
			sb.append(monsterYaml.getInt(monsterName+".MIN_Money"));
			sb.append(" ~ ");
			sb.append(monsterYaml.getInt(monsterName+".MAX_Money"));
			sb.append("%enter%");
			sb.append("§c§l ");
			sb.append(MainServerOption.statSTR);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".STR"));
			sb.append("§7 [물공 : ");
			sb.append(BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), true));
			sb.append(" ~ ");
			sb.append(BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), false));
			sb.append("]%enter%");
			sb.append("§a§l ");
			sb.append(MainServerOption.statDEX);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".DEX"));
			sb.append("§7 [활공 : ");
			sb.append(BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, true));
			sb.append(" ~ ");
			sb.append(BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, false));
			sb.append("]%enter%");
			sb.append("§9§l ");
			sb.append(MainServerOption.statINT);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".INT"));
			sb.append("§7 [폭공 : ");
			sb.append(monsterYaml.getInt(monsterName+".INT")/4);
			sb.append(" ~ ");
			sb.append((int)(monsterYaml.getInt(monsterName+".INT")/2.5));
			sb.append("]%enter%");
			sb.append("§7§l ");
			sb.append(MainServerOption.statWILL);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".WILL"));
			sb.append("§7 [크리 : ");
			sb.append(BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0));
			sb.append(" %]%enter%");
			sb.append("§e§l ");
			sb.append(MainServerOption.statLUK);
			sb.append(" : §f");
			sb.append(monsterYaml.getInt(monsterName+".LUK"));
			sb.append("§7 [크리 : ");
			sb.append(BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0));
			sb.append(" %]%enter%");

			sb.append("§7§l 방어 : §f");
			sb.append(monsterYaml.getInt(monsterName+".DEF"));
			sb.append("%enter%");
			sb.append("§b§l 보호 : §f");
			sb.append(monsterYaml.getInt(monsterName+".Protect"));
			sb.append("%enter%");
			sb.append("§9§l 마법 방어 : §f");
			sb.append(monsterYaml.getInt(monsterName+".Magic_DEF"));
			sb.append("%enter%");
			sb.append("§1§l 마법 보호 : §f");
			sb.append(monsterYaml.getInt(monsterName+".Magic_Protect"));
			sb.append("%enter%");
			sb.append("%enter%§e§l[좌 클릭시 몬스터 등록]%enter%");

			String[] scriptA = sb.toString().split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			short id = 383;
			byte data = 0;
			switch(monsterYaml.getString(monsterName+".Type"))
			{
				case "번개크리퍼" : case "크리퍼" : data=50; break;
				case "네더스켈레톤" : case "스켈레톤" : data=51; break;
				case "거미" : data=52; break;
				case "좀비" :case "자이언트" : data=54; break;
				case "초대형슬라임" :case "특대슬라임" : case "큰슬라임" :case "보통슬라임" : case "작은슬라임" : data=55; break;
				case "가스트" : data=56; break;
				case "좀비피그맨" : data=57; break;
				case "엔더맨" : data=58; break;
				case "동굴거미" : data=59; break;
				case "좀벌레" : data=60; break;
				case "블레이즈" : data=61; break;
				case "큰마그마큐브" :case "특대마그마큐브" : case "보통마그마큐브": case "마그마큐브" : case "작은마그마큐브" : data=62; break;
				case "박쥐" : data=65; break;
				case "마녀" : data=66; break;
				case "엔더진드기" : data=67; break;
				case "수호자" : data=68; break;
				case "돼지" : data=90; break;
				case "양" : data=91; break;
				case "소" : data=92; break;
				case "닭" : data=93; break;
				case "오징어" : data=94; break;
				case "늑대" : data=95; break;
				case "버섯소" : data=96; break;
				case "오셀롯" : data=98; break;
				case "말" : data=100; break;
				case "토끼" : data=101; break;
				case "주민" : data=120; break;
				case "위더" : id=399; break;
				case "엔더드래곤" : id=122; break;
				case "엔더크리스탈" : id=46; break;
			}
			
			stack("§f"+monsterName, id, data, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(monsterList.length-(page*44)>45)
			stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		stack("§f§l취소", 166,0,1,Arrays.asList("§7지정 몬스터 스폰대신","§7영역에 등록 된 몬스터를","§7랜덤하게 스폰 합니다.","§0"+areaName,"§0"+ruleCount), 49, inv);
		player.openInventory(inv);
	}


	public void areaSpawnSpecialMonsterListGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		String areaName = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(3));
		String ruleCounter = ChatColor.stripColor(event.getInventory().getItem(49).getItemMeta().getLore().get(4));
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		if(slot == 49)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			new AreaMonsterSpawnSettingGui().areaMonsterSpawnSettingGui(player, 0, areaName);
			new area.AreaAPI().areaMonsterSpawnAdd(areaName, ruleCounter);
		}
		else if(slot == 48)//이전 페이지
			areaSpawnSpecialMonsterListGui(player, page-1, areaName, ruleCounter);
		else if(slot == 50)//다음 페이지
			areaSpawnSpecialMonsterListGui(player, page+1, areaName, ruleCounter);
		else
		{
			String mobName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			SoundEffect.playSound(player, Sound.BLOCK_ANVIL_LAND, 0.8F, 1.0F);
		  	YamlLoader areaYaml = new YamlLoader();
			areaYaml.getConfig("Area/AreaList.yml");
			areaYaml.set(areaName+".MonsterSpawnRule."+ruleCounter+".Monster", mobName);
			areaYaml.saveConfig();
			new AreaMonsterSpawnSettingGui().areaMonsterSpawnSettingGui(player, 0, areaName);
			
			new area.AreaAPI().areaMonsterSpawnAdd(areaName, ruleCounter);
		}
	}
}
