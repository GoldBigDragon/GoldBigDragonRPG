package admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import main.Main_ServerOption;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;

public class Upgrade_GUI extends Util_GUI
{
	public void upgradeRecipeGui(Player player, int page)
	{
		YamlLoader upgradeYaml = new YamlLoader();
		upgradeYaml.getConfig("Item/Upgrade.yml");

		String uniqueCode = "§0§0§1§1§c§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0개조식 목록 : " + (page+1));

		String[] upgradeList= upgradeYaml.getKeys().toArray(new String[0]);
		byte loc=0;
		for(int count = page*45; count < upgradeList.length;count++)
		{
			if(count > upgradeList.length || loc >= 45) break;
			String itemName =upgradeList[count];
			String lore=null;
			if(upgradeYaml.getString(itemName+".Only").equals("null"))
				lore = "§f[모든 장비]%enter%%enter%";
			else
				lore = upgradeYaml.getString(itemName+".Only")+"%enter%%enter%";

			if(upgradeYaml.getInt(itemName+".MaxDurability") > 0)
				lore = lore+"§3 ▲ 최대 내구도 : "+upgradeYaml.getInt(itemName+".MaxDurability")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".MaxDurability") < 0)
				lore = lore+"§c ▼ 최대 내구도 : "+upgradeYaml.getInt(itemName+".MaxDurability")+"%enter%";
			if(upgradeYaml.getInt(itemName+".MinDamage") > 0)
				lore = lore+"§3 ▲ 최소 "+Main_ServerOption.damage+" : "+upgradeYaml.getInt(itemName+".MinDamage")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".MinDamage") < 0)
				lore = lore+"§c ▼ 최소 "+Main_ServerOption.damage+" : "+upgradeYaml.getInt(itemName+".MinDamage")+"%enter%";
			if(upgradeYaml.getInt(itemName+".MaxDamage") > 0)
				lore = lore+"§3 ▲ 최대 "+Main_ServerOption.damage+" : "+upgradeYaml.getInt(itemName+".MaxDamage")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".MaxDamage") < 0)
				lore = lore+"§c ▼ 최대 "+Main_ServerOption.damage+" : "+upgradeYaml.getInt(itemName+".MaxDamage")+"%enter%";
			if(upgradeYaml.getInt(itemName+".MinMaDamage") > 0)
				lore = lore+"§3 ▲ 최소 "+Main_ServerOption.magicDamage+" : "+upgradeYaml.getInt(itemName+".MinMaDamage")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".MinMaDamage") < 0)
				lore = lore+"§c ▼ 최소 "+Main_ServerOption.magicDamage+" : "+upgradeYaml.getInt(itemName+".MinMaDamage")+"%enter%";
			if(upgradeYaml.getInt(itemName+".MaxMaDamage") > 0)
				lore = lore+"§3 ▲ 최대 "+Main_ServerOption.magicDamage+" : "+upgradeYaml.getInt(itemName+".MaxMaDamage")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".MaxMaDamage") < 0)
				lore = lore+"§c ▼ 최대 "+Main_ServerOption.magicDamage+" : "+upgradeYaml.getInt(itemName+".MaxMaDamage")+"%enter%";
			if(upgradeYaml.getInt(itemName+".DEF") > 0)
				lore = lore+"§3 ▲ 방어 : "+upgradeYaml.getInt(itemName+".DEF")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".DEF") < 0)
				lore = lore+"§c ▼ 방어 : "+upgradeYaml.getInt(itemName+".DEF")+"%enter%";
			if(upgradeYaml.getInt(itemName+".Protect") > 0)
				lore = lore+"§3 ▲ 보호 : "+upgradeYaml.getInt(itemName+".Protect")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".Protect") < 0)
				lore = lore+"§c ▼ 보호 : "+upgradeYaml.getInt(itemName+".Protect")+"%enter%";
			if(upgradeYaml.getInt(itemName+".MaDEF") > 0)
				lore = lore+"§3 ▲ 마법 방어 : "+upgradeYaml.getInt(itemName+".MaDEF")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".MaDEF") < 0)
				lore = lore+"§c ▼ 마법 방어 : "+upgradeYaml.getInt(itemName+".MaDEF")+"%enter%";
			if(upgradeYaml.getInt(itemName+".MaProtect") > 0)
				lore = lore+"§3 ▲ 마법 보호 : "+upgradeYaml.getInt(itemName+".MaProtect")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".MaProtect") < 0)
				lore = lore+"§c ▼ 마법 보호 : "+upgradeYaml.getInt(itemName+".MaProtect")+"%enter%";
			if(upgradeYaml.getInt(itemName+".Balance") > 0)
				lore = lore+"§3 ▲ 밸런스 : "+upgradeYaml.getInt(itemName+".Balance")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".Balance") < 0)
				lore = lore+"§c ▼ 밸런스 : "+upgradeYaml.getInt(itemName+".Balance")+"%enter%";
			if(upgradeYaml.getInt(itemName+".Critical") > 0)
				lore = lore+"§3 ▲ 크리티컬 : "+upgradeYaml.getInt(itemName+".Critical")+"%enter%";
			else if(upgradeYaml.getInt(itemName+".Critical") < 0)
				lore = lore+"§c ▼ 크리티컬 : "+upgradeYaml.getInt(itemName+".Critical")+"%enter%";
			
			lore = lore+"%enter%"+upgradeYaml.getString(itemName+".Lore")+"%enter%%enter%";

			lore = lore+"§e ▶ 개조 횟수 : §f"+upgradeYaml.getInt(itemName+".UpgradeAbleLevel")+"§e 회째 개조 가능%enter%";
			lore = lore+"§e ▶ 필요 숙련도 : §f"+upgradeYaml.getInt(itemName+".DecreaseProficiency")+"%enter% ";

			lore = lore+"%enter%§e[좌 클릭시 개조식 수정]%enter%§c[Shift + 우 클릭시 개조식 삭제]%enter% ";

			String[] scriptA = lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			Stack2("§f"+itemName, 395, 0, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(upgradeList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 개조식", 339,0,1,Arrays.asList("§7새로운 개조식을 만듭니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void upgradeRecipeSettingGui(Player player, String recipeName)
	{
		YamlLoader upgradeYaml = new YamlLoader();
		upgradeYaml.getConfig("Item/Upgrade.yml");

		String uniqueCode = "§0§0§1§1§d§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0개조식 설정");

		String lore=null;
		if(upgradeYaml.getString(recipeName+".Only").equals("null"))
			lore = "§f[모든 장비]%enter%%enter%";
		else
			lore = upgradeYaml.getString(recipeName+".Only")+"%enter%%enter%";

		if(upgradeYaml.getInt(recipeName+".MaxDurability") > 0)
			lore = lore+"§3 ▲ 최대 내구도 : "+upgradeYaml.getInt(recipeName+".MaxDurability")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".MaxDurability") < 0)
			lore = lore+"§c ▼ 최대 내구도 : "+upgradeYaml.getInt(recipeName+".MaxDurability")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".MinDamage") > 0)
			lore = lore+"§3 ▲ 최소 "+Main_ServerOption.damage+" : "+upgradeYaml.getInt(recipeName+".MinDamage")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".MinDamage") < 0)
			lore = lore+"§c ▼ 최소 "+Main_ServerOption.damage+" : "+upgradeYaml.getInt(recipeName+".MinDamage")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".MaxDamage") > 0)
			lore = lore+"§3 ▲ 최대 "+Main_ServerOption.damage+" : "+upgradeYaml.getInt(recipeName+".MaxDamage")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".MaxDamage") < 0)
			lore = lore+"§c ▼ 최대 "+Main_ServerOption.damage+" : "+upgradeYaml.getInt(recipeName+".MaxDamage")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".MinMaDamage") > 0)
			lore = lore+"§3 ▲ 최소 "+Main_ServerOption.magicDamage+" : "+upgradeYaml.getInt(recipeName+".MinMaDamage")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".MinMaDamage") < 0)
			lore = lore+"§c ▼ 최소 "+Main_ServerOption.magicDamage+" : "+upgradeYaml.getInt(recipeName+".MinMaDamage")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".MaxMaDamage") > 0)
			lore = lore+"§3 ▲ 최대 "+Main_ServerOption.magicDamage+" : "+upgradeYaml.getInt(recipeName+".MaxMaDamage")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".MaxMaDamage") < 0)
			lore = lore+"§c ▼ 최대 "+Main_ServerOption.magicDamage+" : "+upgradeYaml.getInt(recipeName+".MaxMaDamage")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".DEF") > 0)
			lore = lore+"§3 ▲ 방어 : "+upgradeYaml.getInt(recipeName+".DEF")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".DEF") < 0)
			lore = lore+"§c ▼ 방어 : "+upgradeYaml.getInt(recipeName+".DEF")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".Protect") > 0)
			lore = lore+"§3 ▲ 보호 : "+upgradeYaml.getInt(recipeName+".Protect")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".Protect") < 0)
			lore = lore+"§c ▼ 보호 : "+upgradeYaml.getInt(recipeName+".Protect")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".MaDEF") > 0)
			lore = lore+"§3 ▲ 마법 방어 : "+upgradeYaml.getInt(recipeName+".MaDEF")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".MaDEF") < 0)
			lore = lore+"§c ▼ 마법 방어 : "+upgradeYaml.getInt(recipeName+".MaDEF")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".MaProtect") > 0)
			lore = lore+"§3 ▲ 마법 보호 : "+upgradeYaml.getInt(recipeName+".MaProtect")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".MaProtect") < 0)
			lore = lore+"§c ▼ 마법 보호 : "+upgradeYaml.getInt(recipeName+".MaProtect")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".Balance") > 0)
			lore = lore+"§3 ▲ 밸런스 : "+upgradeYaml.getInt(recipeName+".Balance")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".Balance") < 0)
			lore = lore+"§c ▼ 밸런스 : "+upgradeYaml.getInt(recipeName+".Balance")+"%enter%";
		if(upgradeYaml.getInt(recipeName+".Critical") > 0)
			lore = lore+"§3 ▲ 크리티컬 : "+upgradeYaml.getInt(recipeName+".Critical")+"%enter%";
		else if(upgradeYaml.getInt(recipeName+".Critical") < 0)
			lore = lore+"§c ▼ 크리티컬 : "+upgradeYaml.getInt(recipeName+".Critical")+"%enter%";
		
		lore = lore+"%enter%"+upgradeYaml.getString(recipeName+".Lore")+"%enter%%enter%";

		lore = lore+"§e ▶ 개조 횟수 : §f"+upgradeYaml.getInt(recipeName+".UpgradeAbleLevel")+"§e 회째 개조 가능%enter%";
		lore = lore+"§e ▶ 필요 숙련도 : §f"+upgradeYaml.getInt(recipeName+".DecreaseProficiency")+"%enter% ";

		
		String[] scriptA = lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];

		Stack2("§3[    결과물    ]", 265,0,1,null, 9, inv);
		Stack2("§3[    결과물    ]", 265,0,1,null, 10, inv);
		Stack2("§3[    결과물    ]", 265,0,1,null, 11, inv);
		Stack2("§3[    결과물    ]", 265,0,1,null, 18, inv);
		Stack2("§3[    결과물    ]", 265,0,1,null, 20, inv);
		Stack2("§3[    결과물    ]", 265,0,1,null, 27, inv);
		Stack2("§3[    결과물    ]", 265,0,1,null, 28, inv);
		Stack2("§3[    결과물    ]", 265,0,1,null, 29, inv);
		
		Stack2("§f"+recipeName, 395, 0, 1,Arrays.asList(scriptA), 19, inv);
		Stack2("§f[   설명 변경   ]", 421, 0, 1,Arrays.asList("§f개조식의 설명을 변경합니다."), 37, inv);
		
		Stack2("§3[    타입 변경    ]", 61,0,1,Arrays.asList("§f개조 가능한 타입을","§f변경합니다.","","§f[    현재 타입    ]",upgradeYaml.getString(recipeName+".Only"),""), 13, inv);
		Stack2("§3[       "+Main_ServerOption.damage+"       ]", 267,0,1,Arrays.asList("§f개조시 "+Main_ServerOption.damage+"를","§f상승 시킵니다.",""), 14, inv);
		Stack2("§3[     "+Main_ServerOption.magicDamage+"     ]", 403,0,1,Arrays.asList("§f개조시 "+Main_ServerOption.magicDamage+"를","§f상승 시킵니다.",""), 15, inv);
		Stack2("§3[       밸런스       ]", 262,0,1,Arrays.asList("§f개조시 밸런스를","§f상승 시킵니다.",""), 16, inv);
		Stack2("§3[        방어        ]", 307,0,1,Arrays.asList("§f개조시 방어력을","§f상승 시킵니다.",""), 22, inv);
		Stack2("§3[        보호        ]", 306,0,1,Arrays.asList("§f개조시 보호를","§f상승 시킵니다.",""), 23, inv);
		Stack2("§3[      마법 방어      ]", 311,0,1,Arrays.asList("§f개조시 마법 방어를","§f상승 시킵니다.",""), 24, inv);
		Stack2("§3[      마법 보호      ]", 310,0,1,Arrays.asList("§f개조시 마법 보호를","§f상승 시킵니다.",""), 25, inv);
		Stack2("§3[      크리티컬      ]", 377,0,1,Arrays.asList("§f개조시 크리티컬 확률을","§f상승 시킵니다.",""), 31, inv);
		Stack2("§3[       내구도       ]", 145,2,1,Arrays.asList("§f개조시 최대 내구도를","§f변경 합니다.",""), 32, inv);
		Stack2("§3[        개조        ]", 145,0,1,Arrays.asList("§f개조 가능한 개조 레벨을","§f설정 합니다.",""), 33, inv);
		Stack2("§3[       숙련도       ]", 416,0,1,Arrays.asList("§f개조에 필요한 숙련도를","§f설정 합니다.",""), 34, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+recipeName), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void upgradeRecipeGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new OPbox_GUI().opBoxGuiMain(player, (byte) 2);
			else if(slot == 48)//이전 페이지
				upgradeRecipeGui(player, page-1);
			else if(slot == 49)//새 개조식
			{
				player.closeInventory();
				player.sendMessage("§3[개조] : 새로운 개조식의 이름을 입력 하세요!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Upgrade");
				u.setString(player, (byte)1, "NUR");
			}
			else if(slot == 50)//다음 페이지
				upgradeRecipeGui(player, page+1);
			else
			{
				if(event.isLeftClick() == true&&event.isShiftClick()==false)
					upgradeRecipeSettingGui(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isRightClick()==true&&event.isShiftClick()==true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					YamlLoader upgradeYaml = new YamlLoader();
					upgradeYaml.getConfig("Item/Upgrade.yml");
					upgradeYaml.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					upgradeYaml.saveConfig();
					upgradeRecipeGui(player, page);
				}
			}
		}
	}
	
	public void upgradeRecipeSettingGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
		{
			if(slot == 53)//나가기
			{
				SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				String recipeName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
				if(slot == 45)//이전 목록
					upgradeRecipeGui(player, 0);
				else if(slot == 13)//타입 변경
				{
					YamlLoader upgradeYaml = new YamlLoader();
					upgradeYaml.getConfig("Item/Upgrade.yml");
					YamlLoader customTypeYaml = new YamlLoader();
					customTypeYaml.getConfig("Item/CustomType.yml");
				  	String[] Type = customTypeYaml.getKeys().toArray(new String[0]);
				  	if(Type.length == 0)
				  	{
						if(upgradeYaml.getString(recipeName+".Only").contains("[근접 무기]"))
							upgradeYaml.set(recipeName+".Only","§c[한손 검]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[한손 검]"))
							upgradeYaml.set(recipeName+".Only","§c[양손 검]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[양손 검]"))
							upgradeYaml.set(recipeName+".Only","§c[도끼]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[도끼]"))
							upgradeYaml.set(recipeName+".Only","§c[낫]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[낫]"))
							upgradeYaml.set(recipeName+".Only","§2[원거리 무기]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[원거리 무기]"))
							upgradeYaml.set(recipeName+".Only","§2[활]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[활]"))
							upgradeYaml.set(recipeName+".Only","§2[석궁]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[석궁]"))
							upgradeYaml.set(recipeName+".Only","§3[마법 무기]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[마법 무기]"))
							upgradeYaml.set(recipeName+".Only","§3[원드]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[원드]"))
							upgradeYaml.set(recipeName+".Only","§3[스태프]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[스태프]"))
							upgradeYaml.set(recipeName+".Only","§f[방어구]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[방어구]"))
							upgradeYaml.set(recipeName+".Only","§7[기타]");
						else
							upgradeYaml.set(recipeName+".Only","§c[근접 무기]");
				  	}
				  	else
				  	{
						if(upgradeYaml.getString(recipeName+".Only").contains("[근접 무기]"))
							upgradeYaml.set(recipeName+".Only","§c[한손 검]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[한손 검]"))
							upgradeYaml.set(recipeName+".Only","§c[양손 검]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[양손 검]"))
							upgradeYaml.set(recipeName+".Only","§c[도끼]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[도끼]"))
							upgradeYaml.set(recipeName+".Only","§c[낫]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[낫]"))
							upgradeYaml.set(recipeName+".Only","§2[원거리 무기]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[원거리 무기]"))
							upgradeYaml.set(recipeName+".Only","§2[활]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[활]"))
							upgradeYaml.set(recipeName+".Only","§2[석궁]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[석궁]"))
							upgradeYaml.set(recipeName+".Only","§3[마법 무기]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[마법 무기]"))
							upgradeYaml.set(recipeName+".Only","§3[원드]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[원드]"))
							upgradeYaml.set(recipeName+".Only","§3[스태프]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[스태프]"))
							upgradeYaml.set(recipeName+".Only","§f[방어구]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[방어구]"))
							upgradeYaml.set(recipeName+".Only","§7[기타]");
						else if(upgradeYaml.getString(recipeName+".Only").contains("[기타]"))
							upgradeYaml.set(recipeName+".Only","§f"+Type[0].toString());
				  		else
						{
							for(int count = 0; count < Type.length; count++)
							{
								if((upgradeYaml.getString(recipeName+".Only").contains(Type[count])))
								{
									if(count+1 == Type.length)
										upgradeYaml.set(recipeName+".Only","§c[근접 무기]");
									else
										upgradeYaml.set(recipeName+".Only","§f"+Type[(count+1)]);
									break;
								}
							}
						}
				  	}
					upgradeYaml.saveConfig();
					upgradeRecipeSettingGui(player,recipeName );
				}
				else
				{
					UserData_Object u = new UserData_Object();
					player.closeInventory();
					u.setType(player, "Upgrade");
					u.setString(player, (byte)6, recipeName);
					if(slot == 14)//데미지 설정
					{
						player.sendMessage("§3[개조] : 변화될 최소 공격력 수치를 입력하세요!");
						player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UMinD");
					}
					else if(slot == 15)//마법 대미지 설정
					{
						player.sendMessage("§3[개조] : 변화될 최소 마법 공격력 수치를 입력하세요!");
						player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UMMinD");
					}
					else if(slot == 16)//밸런스 설정
					{
						player.sendMessage("§3[개조] : 변화될 밸런스를 입력하세요!");
						player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UB");
					}
					else if(slot == 22)//방어 설정
					{
						player.sendMessage("§3[개조] : 변화될 방어력을 입력하세요!");
						player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UDEF");
					}
					else if(slot == 23)//보호 설정
					{
						player.sendMessage("§3[개조] : 변화될 보호를 입력하세요!");
						player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UP");
					}
					else if(slot == 24)//마법 방어 설정
					{
						player.sendMessage("§3[개조] : 변화될 마법 방어력을 입력하세요!");
						player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UMDEF");
					}
					else if(slot == 25)//마법 보호 설정
					{
						player.sendMessage("§3[개조] : 변화될 마법 보호를 입력하세요!");
						player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UMP");
					}
					else if(slot == 31)//크리티컬 설정
					{
						player.sendMessage("§3[개조] : 변화될 크리티컬을 입력하세요!");
						player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UC");
					}
					else if(slot == 32)//내구도 설정
					{
						player.sendMessage("§3[개조] : 변화될 최대 내구도를 입력하세요!");
						player.sendMessage("§a(§e"+ Integer.MIN_VALUE+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UMD");
					}
					else if(slot == 33)//개조 레벨 설정
					{
						player.sendMessage("§3[개조] : 이 개조를 하기 위한 개조 레벨을 입력하세요!");
						player.sendMessage("§a(§e"+0+"§a ~ §e"+Integer.MAX_VALUE+"§a)");
						u.setString(player, (byte)1, "UUL");
					}
					else if(slot == 34)//필요 숙련도 설정
					{
						player.sendMessage("§3[개조] : 개조 이후 차감될 숙련도를 입력하세요!");
						player.sendMessage("§a(§e"+0+"§a ~ §e100§a)");
						u.setString(player, (byte)1, "UDP");
					}
					else if(slot == 37)//설명 변경
					{
						player.sendMessage("§3[아이템] : 아이템의 설명을 입력해 주세요!");
						player.sendMessage("§6%enter%§f - 한줄 띄워 쓰기 -");
						player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
						"§3&3 §4&4 §5&5 " +
								"§6&6 §7&7 §8&8 " +
						"§9&9 §a&a §b&b §c&c " +
								"§d&d §e&e §f&f");
						u.setString(player, (byte)1, "ULC");
					}
				}
			}
		}
	}
}
