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
	public void UpgradeRecipeGUI(Player player, int page)
	{
		YamlLoader upgradeYaml = new YamlLoader();
		upgradeYaml.getConfig("Item/Upgrade.yml");

		String UniqueCode = "§0§0§1§1§c§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0개조식 목록 : " + (page+1));

		String[] upgradeList= upgradeYaml.getKeys().toArray(new String[0]);
		byte loc=0;
		for(int count = page*45; count < upgradeList.length;count++)
		{
			if(count > upgradeList.length || loc >= 45) break;
			String ItemName =upgradeList[count];
			String Lore=null;
			if(upgradeYaml.getString(ItemName+".Only").compareTo("null")==0)
				Lore = ChatColor.WHITE+"[모든 장비]%enter%%enter%";
			else
				Lore = upgradeYaml.getString(ItemName+".Only")+"%enter%%enter%";

			if(upgradeYaml.getInt(ItemName+".MaxDurability") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 내구도 : "+upgradeYaml.getInt(ItemName+".MaxDurability")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".MaxDurability") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최대 내구도 : "+upgradeYaml.getInt(ItemName+".MaxDurability")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".MinDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최소 "+Main_ServerOption.Damage+" : "+upgradeYaml.getInt(ItemName+".MinDamage")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".MinDamage") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최소 "+Main_ServerOption.Damage+" : "+upgradeYaml.getInt(ItemName+".MinDamage")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".MaxDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 "+Main_ServerOption.Damage+" : "+upgradeYaml.getInt(ItemName+".MaxDamage")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".MaxDamage") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최대 "+Main_ServerOption.Damage+" : "+upgradeYaml.getInt(ItemName+".MaxDamage")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".MinMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최소 "+Main_ServerOption.MagicDamage+" : "+upgradeYaml.getInt(ItemName+".MinMaDamage")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".MinMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최소 "+Main_ServerOption.MagicDamage+" : "+upgradeYaml.getInt(ItemName+".MinMaDamage")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".MaxMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 "+Main_ServerOption.MagicDamage+" : "+upgradeYaml.getInt(ItemName+".MaxMaDamage")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".MaxMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최대 "+Main_ServerOption.MagicDamage+" : "+upgradeYaml.getInt(ItemName+".MaxMaDamage")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".DEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 방어 : "+upgradeYaml.getInt(ItemName+".DEF")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".DEF") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 방어 : "+upgradeYaml.getInt(ItemName+".DEF")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".Protect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 보호 : "+upgradeYaml.getInt(ItemName+".Protect")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".Protect") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 보호 : "+upgradeYaml.getInt(ItemName+".Protect")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".MaDEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 마법 방어 : "+upgradeYaml.getInt(ItemName+".MaDEF")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".MaDEF") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 마법 방어 : "+upgradeYaml.getInt(ItemName+".MaDEF")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".MaProtect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 마법 보호 : "+upgradeYaml.getInt(ItemName+".MaProtect")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".MaProtect") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 마법 보호 : "+upgradeYaml.getInt(ItemName+".MaProtect")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".Balance") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 밸런스 : "+upgradeYaml.getInt(ItemName+".Balance")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".Balance") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 밸런스 : "+upgradeYaml.getInt(ItemName+".Balance")+"%enter%";
			if(upgradeYaml.getInt(ItemName+".Critical") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 크리티컬 : "+upgradeYaml.getInt(ItemName+".Critical")+"%enter%";
			else if(upgradeYaml.getInt(ItemName+".Critical") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 크리티컬 : "+upgradeYaml.getInt(ItemName+".Critical")+"%enter%";
			
			Lore = Lore+"%enter%"+upgradeYaml.getString(ItemName+".Lore")+"%enter%%enter%";

			Lore = Lore+ChatColor.YELLOW+" ▶ 개조 횟수 : "+ChatColor.WHITE+upgradeYaml.getInt(ItemName+".UpgradeAbleLevel")+ChatColor.YELLOW+" 회째 개조 가능%enter%";
			Lore = Lore+ChatColor.YELLOW+" ▶ 필요 숙련도 : "+ChatColor.WHITE+upgradeYaml.getInt(ItemName+".DecreaseProficiency")+"%enter% ";

			Lore = Lore+"%enter%"+ChatColor.YELLOW+"[좌 클릭시 개조식 수정]%enter%"+ChatColor.RED+"[Shift + 우 클릭시 개조식 삭제]%enter% ";

			String[] scriptA = Lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			Stack2(ChatColor.WHITE+ItemName, 395, 0, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(upgradeList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 개조식", 339,0,1,Arrays.asList(ChatColor.GRAY + "새로운 개조식을 만듭니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void UpgradeRecipeSettingGUI(Player player, String RecipeName)
	{
		YamlLoader upgradeYaml = new YamlLoader();
		upgradeYaml.getConfig("Item/Upgrade.yml");

		String UniqueCode = "§0§0§1§1§d§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0개조식 설정");

		String Lore=null;
		if(upgradeYaml.getString(RecipeName+".Only").compareTo("null")==0)
			Lore = ChatColor.WHITE+"[모든 장비]%enter%%enter%";
		else
			Lore = upgradeYaml.getString(RecipeName+".Only")+"%enter%%enter%";

		if(upgradeYaml.getInt(RecipeName+".MaxDurability") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 내구도 : "+upgradeYaml.getInt(RecipeName+".MaxDurability")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".MaxDurability") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최대 내구도 : "+upgradeYaml.getInt(RecipeName+".MaxDurability")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".MinDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최소 "+Main_ServerOption.Damage+" : "+upgradeYaml.getInt(RecipeName+".MinDamage")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".MinDamage") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최소 "+Main_ServerOption.Damage+" : "+upgradeYaml.getInt(RecipeName+".MinDamage")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".MaxDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 "+Main_ServerOption.Damage+" : "+upgradeYaml.getInt(RecipeName+".MaxDamage")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".MaxDamage") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최대 "+Main_ServerOption.Damage+" : "+upgradeYaml.getInt(RecipeName+".MaxDamage")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".MinMaDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최소 "+Main_ServerOption.MagicDamage+" : "+upgradeYaml.getInt(RecipeName+".MinMaDamage")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".MinMaDamage") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최소 "+Main_ServerOption.MagicDamage+" : "+upgradeYaml.getInt(RecipeName+".MinMaDamage")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".MaxMaDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 "+Main_ServerOption.MagicDamage+" : "+upgradeYaml.getInt(RecipeName+".MaxMaDamage")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".MaxMaDamage") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최대 "+Main_ServerOption.MagicDamage+" : "+upgradeYaml.getInt(RecipeName+".MaxMaDamage")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".DEF") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 방어 : "+upgradeYaml.getInt(RecipeName+".DEF")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".DEF") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 방어 : "+upgradeYaml.getInt(RecipeName+".DEF")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".Protect") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 보호 : "+upgradeYaml.getInt(RecipeName+".Protect")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".Protect") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 보호 : "+upgradeYaml.getInt(RecipeName+".Protect")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".MaDEF") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 마법 방어 : "+upgradeYaml.getInt(RecipeName+".MaDEF")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".MaDEF") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 마법 방어 : "+upgradeYaml.getInt(RecipeName+".MaDEF")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".MaProtect") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 마법 보호 : "+upgradeYaml.getInt(RecipeName+".MaProtect")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".MaProtect") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 마법 보호 : "+upgradeYaml.getInt(RecipeName+".MaProtect")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".Balance") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 밸런스 : "+upgradeYaml.getInt(RecipeName+".Balance")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".Balance") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 밸런스 : "+upgradeYaml.getInt(RecipeName+".Balance")+"%enter%";
		if(upgradeYaml.getInt(RecipeName+".Critical") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 크리티컬 : "+upgradeYaml.getInt(RecipeName+".Critical")+"%enter%";
		else if(upgradeYaml.getInt(RecipeName+".Critical") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 크리티컬 : "+upgradeYaml.getInt(RecipeName+".Critical")+"%enter%";
		
		Lore = Lore+"%enter%"+upgradeYaml.getString(RecipeName+".Lore")+"%enter%%enter%";

		Lore = Lore+ChatColor.YELLOW+" ▶ 개조 횟수 : "+ChatColor.WHITE+upgradeYaml.getInt(RecipeName+".UpgradeAbleLevel")+ChatColor.YELLOW+" 회째 개조 가능%enter%";
		Lore = Lore+ChatColor.YELLOW+" ▶ 필요 숙련도 : "+ChatColor.WHITE+upgradeYaml.getInt(RecipeName+".DecreaseProficiency")+"%enter% ";

		
		String[] scriptA = Lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];

		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 265,0,1,null, 9, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 265,0,1,null, 10, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 265,0,1,null, 11, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 265,0,1,null, 18, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 265,0,1,null, 20, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 265,0,1,null, 27, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 265,0,1,null, 28, inv);
		Stack2(ChatColor.DARK_AQUA + "[    결과물    ]", 265,0,1,null, 29, inv);
		
		Stack2(ChatColor.WHITE+RecipeName, 395, 0, 1,Arrays.asList(scriptA), 19, inv);
		Stack2(ChatColor.WHITE+"[   설명 변경   ]", 421, 0, 1,Arrays.asList(ChatColor.WHITE+"개조식의 설명을 변경합니다."), 37, inv);
		
		Stack2(ChatColor.DARK_AQUA + "[    타입 변경    ]", 61,0,1,Arrays.asList(ChatColor.WHITE+"개조 가능한 타입을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 타입    ]",upgradeYaml.getString(RecipeName+".Only"),""), 13, inv);
		Stack2(ChatColor.DARK_AQUA + "[       "+Main_ServerOption.Damage+"       ]", 267,0,1,Arrays.asList(ChatColor.WHITE+"개조시 "+Main_ServerOption.Damage+"를",ChatColor.WHITE+"상승 시킵니다.",""), 14, inv);
		Stack2(ChatColor.DARK_AQUA + "[     "+Main_ServerOption.MagicDamage+"     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"개조시 "+Main_ServerOption.MagicDamage+"를",ChatColor.WHITE+"상승 시킵니다.",""), 15, inv);
		Stack2(ChatColor.DARK_AQUA + "[       밸런스       ]", 262,0,1,Arrays.asList(ChatColor.WHITE+"개조시 밸런스를",ChatColor.WHITE+"상승 시킵니다.",""), 16, inv);
		Stack2(ChatColor.DARK_AQUA + "[        방어        ]", 307,0,1,Arrays.asList(ChatColor.WHITE+"개조시 방어력을",ChatColor.WHITE+"상승 시킵니다.",""), 22, inv);
		Stack2(ChatColor.DARK_AQUA + "[        보호        ]", 306,0,1,Arrays.asList(ChatColor.WHITE+"개조시 보호를",ChatColor.WHITE+"상승 시킵니다.",""), 23, inv);
		Stack2(ChatColor.DARK_AQUA + "[      마법 방어      ]", 311,0,1,Arrays.asList(ChatColor.WHITE+"개조시 마법 방어를",ChatColor.WHITE+"상승 시킵니다.",""), 24, inv);
		Stack2(ChatColor.DARK_AQUA + "[      마법 보호      ]", 310,0,1,Arrays.asList(ChatColor.WHITE+"개조시 마법 보호를",ChatColor.WHITE+"상승 시킵니다.",""), 25, inv);
		Stack2(ChatColor.DARK_AQUA + "[      크리티컬      ]", 377,0,1,Arrays.asList(ChatColor.WHITE+"개조시 크리티컬 확률을",ChatColor.WHITE+"상승 시킵니다.",""), 31, inv);
		Stack2(ChatColor.DARK_AQUA + "[       내구도       ]", 145,2,1,Arrays.asList(ChatColor.WHITE+"개조시 최대 내구도를",ChatColor.WHITE+"변경 합니다.",""), 32, inv);
		Stack2(ChatColor.DARK_AQUA + "[        개조        ]", 145,0,1,Arrays.asList(ChatColor.WHITE+"개조 가능한 개조 레벨을",ChatColor.WHITE+"설정 합니다.",""), 33, inv);
		Stack2(ChatColor.DARK_AQUA + "[       숙련도       ]", 416,0,1,Arrays.asList(ChatColor.WHITE+"개조에 필요한 숙련도를",ChatColor.WHITE+"설정 합니다.",""), 34, inv);
		
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+RecipeName), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void UpgradeRecipeGUIClick(InventoryClickEvent event)
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
				new OPbox_GUI().OPBoxGUI_Main(player, (byte) 2);
			else if(slot == 48)//이전 페이지
				UpgradeRecipeGUI(player, page-1);
			else if(slot == 49)//새 개조식
			{
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 새로운 개조식의 이름을 입력 하세요!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Upgrade");
				u.setString(player, (byte)1, "NUR");
			}
			else if(slot == 50)//다음 페이지
				UpgradeRecipeGUI(player, page+1);
			else
			{
				if(event.isLeftClick() == true&&event.isShiftClick()==false)
					UpgradeRecipeSettingGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isRightClick()==true&&event.isShiftClick()==true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					YamlLoader upgradeYaml = new YamlLoader();
					upgradeYaml.getConfig("Item/Upgrade.yml");
					upgradeYaml.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					upgradeYaml.saveConfig();
					UpgradeRecipeGUI(player, page);
				}
			}
		}
	}
	
	public void UpgradeRecipeSettingGUIClick(InventoryClickEvent event)
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
				String RecipeName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
				if(slot == 45)//이전 목록
					UpgradeRecipeGUI(player, 0);
				else if(slot == 13)//타입 변경
				{
					YamlLoader upgradeYaml = new YamlLoader();
					upgradeYaml.getConfig("Item/Upgrade.yml");
					YamlLoader customTypeYaml = new YamlLoader();
					customTypeYaml.getConfig("Item/CustomType.yml");
				  	String[] Type = customTypeYaml.getKeys().toArray(new String[0]);
				  	if(Type.length == 0)
				  	{
						if(upgradeYaml.getString(RecipeName+".Only").contains("[근접 무기]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[한손 검]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[한손 검]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[양손 검]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[양손 검]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[도끼]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[도끼]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[낫]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[낫]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[원거리 무기]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[원거리 무기]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[활]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[활]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[석궁]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[석궁]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[마법 무기]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[마법 무기]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[원드]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[원드]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[스태프]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[스태프]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.WHITE+"[방어구]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[방어구]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.GRAY+"[기타]");
						else
							upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[근접 무기]");
				  	}
				  	else
				  	{
						if(upgradeYaml.getString(RecipeName+".Only").contains("[근접 무기]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[한손 검]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[한손 검]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[양손 검]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[양손 검]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[도끼]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[도끼]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[낫]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[낫]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[원거리 무기]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[원거리 무기]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[활]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[활]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[석궁]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[석궁]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[마법 무기]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[마법 무기]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[원드]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[원드]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[스태프]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[스태프]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.WHITE+"[방어구]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[방어구]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.GRAY+"[기타]");
						else if(upgradeYaml.getString(RecipeName+".Only").contains("[기타]"))
							upgradeYaml.set(RecipeName+".Only",ChatColor.WHITE+Type[0].toString());
				  		else
						{
							for(int count = 0; count < Type.length; count++)
							{
								if((upgradeYaml.getString(RecipeName+".Only").contains(Type[count])))
								{
									if(count+1 == Type.length)
										upgradeYaml.set(RecipeName+".Only",ChatColor.RED+"[근접 무기]");
									else
										upgradeYaml.set(RecipeName+".Only",ChatColor.WHITE+Type[(count+1)]);
									break;
								}
							}
						}
				  	}
					upgradeYaml.saveConfig();
					UpgradeRecipeSettingGUI(player,RecipeName );
				}
				else
				{
					UserData_Object u = new UserData_Object();
					player.closeInventory();
					u.setType(player, "Upgrade");
					u.setString(player, (byte)6, RecipeName);
					if(slot == 14)//데미지 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 최소 공격력 수치를 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UMinD");
					}
					else if(slot == 15)//마법 대미지 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 최소 마법 공격력 수치를 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UMMinD");
					}
					else if(slot == 16)//밸런스 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 밸런스를 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UB");
					}
					else if(slot == 22)//방어 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 방어력을 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UDEF");
					}
					else if(slot == 23)//보호 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 보호를 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UP");
					}
					else if(slot == 24)//마법 방어 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 마법 방어력을 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UMDEF");
					}
					else if(slot == 25)//마법 보호 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 마법 보호를 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UMP");
					}
					else if(slot == 31)//크리티컬 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 크리티컬을 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UC");
					}
					else if(slot == 32)//내구도 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 최대 내구도를 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UMD");
					}
					else if(slot == 33)//개조 레벨 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 이 개조를 하기 위한 개조 레벨을 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW +0+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UUL");
					}
					else if(slot == 34)//필요 숙련도 설정
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 개조 이후 차감될 숙련도를 입력하세요!");
						player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW +0+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+"100"+ChatColor.GREEN+")");
						u.setString(player, (byte)1, "UDP");
					}
					else if(slot == 37)//설명 변경
					{
						player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 설명을 입력해 주세요!");
						player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄워 쓰기 -");
						player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
						ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
								ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
						ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
								ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
						u.setString(player, (byte)1, "ULC");
					}
				}
			}
		}
	}
	
}
