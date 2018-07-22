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

public class AreaMonsterListGui extends GuiUtil{

	private String uniqueCode = "§0§0§2§0§7§r";
	
	public void areaMonsterListGui(Player player, int page, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
	  	YamlLoader monsterYaml = new YamlLoader();
		monsterYaml.getConfig("Monster/MonsterList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 몬스터 선택 : " + (page+1));

		String[] monsterList= monsterYaml.getKeys().toArray(new String[0]);
		String[] monsterNameList= areaYaml.getConfigurationSection(areaName+".Monster").getKeys(false).toArray(new String[0]);

		String lore = null;
		byte loc=0;
		String monsterName = null;
		for(int count = page*45; count < monsterList.length;count++)
		{
			if(count > monsterList.length || loc >= 45) break;
			monsterName = monsterList[count];
			boolean isExit = false;
			for(int count2 = 0; count2 < monsterNameList.length; count2++)
			{
				if(monsterNameList[count2].equals(monsterName))
				{
					isExit=true;
					break;
				}
			}
			
			if(!isExit)
			{
				lore = null;
				lore = "%enter%§f§l 이름 : §f"+monsterYaml.getString(monsterName+".Name")+"%enter%";
				lore = lore+"§f§l 타입 : §f"+monsterYaml.getString(monsterName+".Type")+"%enter%";
				lore = lore+"§f§l 스폰 바이옴 : §f"+monsterYaml.getString(monsterName+".Biome")+"%enter%";
				lore = lore+"§c§l 생명력 : §f"+monsterYaml.getInt(monsterName+".HP")+"%enter%";
				lore = lore+"§b§l 경험치 : §f"+monsterYaml.getInt(monsterName+".EXP")+"%enter%";
				lore = lore+"§e§l 드랍 금액 : §f"+monsterYaml.getInt(monsterName+".MIN_Money")+" ~ "+monsterYaml.getInt(monsterName+".MAX_Money")+"%enter%";
				lore = lore+"§c§l "+MainServerOption.statSTR+" : §f"+monsterYaml.getInt(monsterName+".STR")
				+"§7 [물공 : " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), true) + " ~ " + BattleCalculator.getCombatDamage(null, 0, monsterYaml.getInt(monsterName+".STR"), false) + "]%enter%";
				lore = lore+"§a§l "+MainServerOption.statDEX+" : §f"+monsterYaml.getInt(monsterName+".DEX")
				+"§7 [활공 : " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(null, monsterYaml.getInt(monsterName+".DEX"), 0, false) + "]%enter%";
				lore = lore+"§9§l "+MainServerOption.statINT+" : §f"+monsterYaml.getInt(monsterName+".INT")
				+"§7 [폭공 : " + (monsterYaml.getInt(monsterName+".INT")/4)+ " ~ "+(int)(monsterYaml.getInt(monsterName+".INT")/2.5)+"]%enter%";
				lore = lore+"§7§l "+MainServerOption.statWILL+" : §f"+monsterYaml.getInt(monsterName+".WILL")
				+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
				lore = lore+"§e§l "+MainServerOption.statLUK+" : §f"+monsterYaml.getInt(monsterName+".LUK")
				+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterYaml.getInt(monsterName+".LUK"), (int)monsterYaml.getInt(monsterName+".WILL"),0) + " %]%enter%";
				lore = lore+"§7§l 방어 : §f"+monsterYaml.getInt(monsterName+".DEF")+"%enter%";
				lore = lore+"§b§l 보호 : §f"+monsterYaml.getInt(monsterName+".Protect")+"%enter%";
				lore = lore+"§9§l 마법 방어 : §f"+monsterYaml.getInt(monsterName+".Magic_DEF")+"%enter%";
				lore = lore+"§1§l 마법 보호 : §f"+monsterYaml.getInt(monsterName+".Magic_Protect")+"%enter%";
				lore = lore+"%enter%§e§l[좌 클릭시 몬스터 등록]";

				String[] scriptA = lore.split("%enter%");
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
		}
		
		if(monsterList.length-(page*44)>45)
			stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+areaName), 53, inv);
		player.openInventory(inv);
	}
	

	public void areaMonsterListGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		String areaName = event.getInventory().getItem(53).getItemMeta().getLore().get(1).substring(2);
		int page = Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new AreaMonsterSettingGui().areaMonsterSettingGui(player, 0, areaName);
			else if(slot == 48)//이전 페이지
				areaMonsterListGui(player, page-1, areaName);
			else if(slot == 50)//다음 페이지
				areaMonsterListGui(player, page+1, areaName);
			else
			{
				String mobName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				SoundEffect.playSound(player, Sound.ENTITY_WOLF_AMBIENT, 0.8F, 1.0F);
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				areaYaml.createSection(areaName+".Monster."+mobName);
				areaYaml.saveConfig();
				areaMonsterListGui(player, page, areaName);
			}
		}
	}

}
