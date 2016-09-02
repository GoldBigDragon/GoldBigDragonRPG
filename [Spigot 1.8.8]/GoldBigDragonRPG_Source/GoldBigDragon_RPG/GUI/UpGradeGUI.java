package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class UpGradeGUI extends GUIutil
{
	public void UpgradeRecipeGUI(Player player, int page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager RecipeList = YC.getNewConfig("Item/Upgrade.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "개조식 목록 : " + (page+1));

		Object[] a= RecipeList.getKeys().toArray();

		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			String ItemName =a[count].toString();
			String Lore=null;
			if(RecipeList.getString(ItemName+".Only").equals("null"))
				Lore = ChatColor.WHITE+"[모든 장비]%enter%%enter%";
			else
				Lore = RecipeList.getString(ItemName+".Only")+"%enter%%enter%";

			if(RecipeList.getInt(ItemName+".MaxDurability") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 내구도 : "+RecipeList.getInt(ItemName+".MaxDurability")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxDurability") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최대 내구도 : "+RecipeList.getInt(ItemName+".MaxDurability")+"%enter%";
			if(RecipeList.getInt(ItemName+".MinDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최소 "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MinDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MinDamage") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최소 "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MinDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaxDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MaxDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxDamage") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최대 "+ServerOption.Damage+" : "+RecipeList.getInt(ItemName+".MaxDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MinMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최소 "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MinMaDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MinMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최소 "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MinMaDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaxMaDamage") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MaxMaDamage")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaxMaDamage") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 최대 "+ServerOption.MagicDamage+" : "+RecipeList.getInt(ItemName+".MaxMaDamage")+"%enter%";
			if(RecipeList.getInt(ItemName+".DEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 방어 : "+RecipeList.getInt(ItemName+".DEF")+"%enter%";
			else if(RecipeList.getInt(ItemName+".DEF") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 방어 : "+RecipeList.getInt(ItemName+".DEF")+"%enter%";
			if(RecipeList.getInt(ItemName+".Protect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 보호 : "+RecipeList.getInt(ItemName+".Protect")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Protect") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 보호 : "+RecipeList.getInt(ItemName+".Protect")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaDEF") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 마법 방어 : "+RecipeList.getInt(ItemName+".MaDEF")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaDEF") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 마법 방어 : "+RecipeList.getInt(ItemName+".MaDEF")+"%enter%";
			if(RecipeList.getInt(ItemName+".MaProtect") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 마법 보호 : "+RecipeList.getInt(ItemName+".MaProtect")+"%enter%";
			else if(RecipeList.getInt(ItemName+".MaProtect") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 마법 보호 : "+RecipeList.getInt(ItemName+".MaProtect")+"%enter%";
			if(RecipeList.getInt(ItemName+".Balance") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 밸런스 : "+RecipeList.getInt(ItemName+".Balance")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Balance") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 밸런스 : "+RecipeList.getInt(ItemName+".Balance")+"%enter%";
			if(RecipeList.getInt(ItemName+".Critical") > 0)
				Lore = Lore+ChatColor.DARK_AQUA+" ▲ 크리티컬 : "+RecipeList.getInt(ItemName+".Critical")+"%enter%";
			else if(RecipeList.getInt(ItemName+".Critical") < 0)
				Lore = Lore+ChatColor.RED+" ▼ 크리티컬 : "+RecipeList.getInt(ItemName+".Critical")+"%enter%";
			
			Lore = Lore+"%enter%"+RecipeList.getString(ItemName+".Lore")+"%enter%%enter%";

			Lore = Lore+ChatColor.YELLOW+" ▶ 개조 횟수 : "+ChatColor.WHITE+RecipeList.getInt(ItemName+".UpgradeAbleLevel")+ChatColor.YELLOW+" 회째 개조 가능%enter%";
			Lore = Lore+ChatColor.YELLOW+" ▶ 필요 숙련도 : "+ChatColor.WHITE+RecipeList.getInt(ItemName+".DecreaseProficiency")+"%enter% ";

			Lore = Lore+"%enter%"+ChatColor.YELLOW+"[좌 클릭시 개조식 수정]%enter%"+ChatColor.RED+"[Shift + 우 클릭시 개조식 삭제]%enter% ";

			String[] scriptA = Lore.split("%enter%");
			for(byte counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			
			Stack2(ChatColor.WHITE+ItemName, 395, 0, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "새 개조식", 339,0,1,Arrays.asList(ChatColor.GRAY + "새로운 개조식을 만듭니다."), 49, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void UpgradeRecipeSettingGUI(Player player, String RecipeName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager RecipeList = YC.getNewConfig("Item/Upgrade.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "개조식 설정");

		String Lore=null;
		if(RecipeList.getString(RecipeName+".Only").equals("null"))
			Lore = ChatColor.WHITE+"[모든 장비]%enter%%enter%";
		else
			Lore = RecipeList.getString(RecipeName+".Only")+"%enter%%enter%";

		if(RecipeList.getInt(RecipeName+".MaxDurability") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 내구도 : "+RecipeList.getInt(RecipeName+".MaxDurability")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaxDurability") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최대 내구도 : "+RecipeList.getInt(RecipeName+".MaxDurability")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MinDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최소 "+ServerOption.Damage+" : "+RecipeList.getInt(RecipeName+".MinDamage")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MinDamage") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최소 "+ServerOption.Damage+" : "+RecipeList.getInt(RecipeName+".MinDamage")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MaxDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 "+ServerOption.Damage+" : "+RecipeList.getInt(RecipeName+".MaxDamage")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaxDamage") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최대 "+ServerOption.Damage+" : "+RecipeList.getInt(RecipeName+".MaxDamage")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MinMaDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최소 "+ServerOption.MagicDamage+" : "+RecipeList.getInt(RecipeName+".MinMaDamage")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MinMaDamage") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최소 "+ServerOption.MagicDamage+" : "+RecipeList.getInt(RecipeName+".MinMaDamage")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MaxMaDamage") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 최대 "+ServerOption.MagicDamage+" : "+RecipeList.getInt(RecipeName+".MaxMaDamage")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaxMaDamage") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 최대 "+ServerOption.MagicDamage+" : "+RecipeList.getInt(RecipeName+".MaxMaDamage")+"%enter%";
		if(RecipeList.getInt(RecipeName+".DEF") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 방어 : "+RecipeList.getInt(RecipeName+".DEF")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".DEF") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 방어 : "+RecipeList.getInt(RecipeName+".DEF")+"%enter%";
		if(RecipeList.getInt(RecipeName+".Protect") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 보호 : "+RecipeList.getInt(RecipeName+".Protect")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".Protect") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 보호 : "+RecipeList.getInt(RecipeName+".Protect")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MaDEF") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 마법 방어 : "+RecipeList.getInt(RecipeName+".MaDEF")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaDEF") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 마법 방어 : "+RecipeList.getInt(RecipeName+".MaDEF")+"%enter%";
		if(RecipeList.getInt(RecipeName+".MaProtect") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 마법 보호 : "+RecipeList.getInt(RecipeName+".MaProtect")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".MaProtect") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 마법 보호 : "+RecipeList.getInt(RecipeName+".MaProtect")+"%enter%";
		if(RecipeList.getInt(RecipeName+".Balance") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 밸런스 : "+RecipeList.getInt(RecipeName+".Balance")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".Balance") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 밸런스 : "+RecipeList.getInt(RecipeName+".Balance")+"%enter%";
		if(RecipeList.getInt(RecipeName+".Critical") > 0)
			Lore = Lore+ChatColor.DARK_AQUA+" ▲ 크리티컬 : "+RecipeList.getInt(RecipeName+".Critical")+"%enter%";
		else if(RecipeList.getInt(RecipeName+".Critical") < 0)
			Lore = Lore+ChatColor.RED+" ▼ 크리티컬 : "+RecipeList.getInt(RecipeName+".Critical")+"%enter%";
		
		Lore = Lore+"%enter%"+RecipeList.getString(RecipeName+".Lore")+"%enter%%enter%";

		Lore = Lore+ChatColor.YELLOW+" ▶ 개조 횟수 : "+ChatColor.WHITE+RecipeList.getInt(RecipeName+".UpgradeAbleLevel")+ChatColor.YELLOW+" 회째 개조 가능%enter%";
		Lore = Lore+ChatColor.YELLOW+" ▶ 필요 숙련도 : "+ChatColor.WHITE+RecipeList.getInt(RecipeName+".DecreaseProficiency")+"%enter% ";

		
		String[] scriptA = Lore.split("%enter%");
		for(byte counter = 0; counter < scriptA.length; counter++)
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
		
		Stack2(ChatColor.DARK_AQUA + "[    타입 변경    ]", 61,0,1,Arrays.asList(ChatColor.WHITE+"개조 가능한 타입을",ChatColor.WHITE+"변경합니다.","",ChatColor.WHITE+"[    현재 타입    ]",RecipeList.getString(RecipeName+".Only"),""), 13, inv);
		Stack2(ChatColor.DARK_AQUA + "[       "+ServerOption.Damage+"       ]", 267,0,1,Arrays.asList(ChatColor.WHITE+"개조시 "+ServerOption.Damage+"를",ChatColor.WHITE+"상승 시킵니다.",""), 14, inv);
		Stack2(ChatColor.DARK_AQUA + "[     "+ServerOption.MagicDamage+"     ]", 403,0,1,Arrays.asList(ChatColor.WHITE+"개조시 "+ServerOption.MagicDamage+"를",ChatColor.WHITE+"상승 시킵니다.",""), 15, inv);
		Stack2(ChatColor.DARK_AQUA + "[       밸런스       ]", 262,0,1,Arrays.asList(ChatColor.WHITE+"개조시 밸런스를",ChatColor.WHITE+"상승 시킵니다.",""), 16, inv);
		Stack2(ChatColor.DARK_AQUA + "[        방어        ]", 307,0,1,Arrays.asList(ChatColor.WHITE+"개조시 방어력을",ChatColor.WHITE+"상승 시킵니다.",""), 22, inv);
		Stack2(ChatColor.DARK_AQUA + "[        보호        ]", 306,0,1,Arrays.asList(ChatColor.WHITE+"개조시 보호를",ChatColor.WHITE+"상승 시킵니다.",""), 23, inv);
		Stack2(ChatColor.DARK_AQUA + "[      마법 방어      ]", 311,0,1,Arrays.asList(ChatColor.WHITE+"개조시 마법 방어를",ChatColor.WHITE+"상승 시킵니다.",""), 24, inv);
		Stack2(ChatColor.DARK_AQUA + "[      마법 보호      ]", 310,0,1,Arrays.asList(ChatColor.WHITE+"개조시 마법 보호를",ChatColor.WHITE+"상승 시킵니다.",""), 25, inv);
		Stack2(ChatColor.DARK_AQUA + "[      크리티컬      ]", 377,0,1,Arrays.asList(ChatColor.WHITE+"개조시 크리티컬 확률을",ChatColor.WHITE+"상승 시킵니다.",""), 31, inv);
		Stack2(ChatColor.DARK_AQUA + "[       내구도       ]", 145,2,1,Arrays.asList(ChatColor.WHITE+"개조시 최대 내구도를",ChatColor.WHITE+"변경 합니다.",""), 32, inv);
		Stack2(ChatColor.DARK_AQUA + "[        개조        ]", 145,0,1,Arrays.asList(ChatColor.WHITE+"개조 가능한 개조 레벨을",ChatColor.WHITE+"설정 합니다.",""), 33, inv);
		Stack2(ChatColor.DARK_AQUA + "[       숙련도       ]", 416,0,1,Arrays.asList(ChatColor.WHITE+"개조에 필요한 숙련도를",ChatColor.WHITE+"설정 합니다.",""), 34, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다.",ChatColor.BLACK+RecipeName), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void UpgradeRecipeGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		switch (event.getSlot())
		{
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI OPGUI = new OPBoxGUI();
			OPGUI.OPBoxGUI_Main(player, (byte) 2);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UpgradeRecipeGUI(player, page-1);
			return;
		case 49://새 개조식
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 새로운 개조식의 이름을 입력 하세요!");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Upgrade");
				u.setString(player, (byte)1, "NUR");
			}
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UpgradeRecipeGUI(player, page+1);
			return;
		default :
			if(event.isLeftClick() == true&&event.isShiftClick()==false)
			{
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				UpgradeRecipeSettingGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
			else if(event.isRightClick()==true&&event.isShiftClick()==true)
			{
				s.SP(player, Sound.LAVA_POP, 0.8F, 1.0F);
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager ItemList = YC.getNewConfig("Item/Upgrade.yml");
				ItemList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				ItemList.saveConfig();
				UpgradeRecipeGUI(player, page);
			}
			return;
		}
	}
	
	public void UpgradeRecipeSettingGUIClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		Object_UserData u = new Object_UserData();
		
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager RecipeList = YC.getNewConfig("Item/Upgrade.yml");
		String RecipeName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		
		switch (event.getSlot())
		{
		case 13://타입 변경
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			YamlManager Target = YC.getNewConfig("Item/CustomType.yml");
		  	Object[] Type = Target.getKeys().toArray();
		  	if(Type.length == 0)
		  	{
				if(RecipeList.getString(RecipeName+".Only").contains("[근접 무기]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[한손 검]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[한손 검]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[양손 검]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[양손 검]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[도끼]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[도끼]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[낫]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[낫]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[원거리 무기]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[원거리 무기]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[활]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[활]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[석궁]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[석궁]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[마법 무기]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[마법 무기]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[원드]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[원드]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[스태프]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[스태프]"))
					RecipeList.set(RecipeName+".Only",ChatColor.WHITE+"[방어구]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[방어구]"))
					RecipeList.set(RecipeName+".Only",ChatColor.GRAY+"[기타]");
				else
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[근접 무기]");
		  	}
		  	else
		  	{

				if(RecipeList.getString(RecipeName+".Only").contains("[근접 무기]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[한손 검]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[한손 검]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[양손 검]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[양손 검]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[도끼]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[도끼]"))
					RecipeList.set(RecipeName+".Only",ChatColor.RED+"[낫]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[낫]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[원거리 무기]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[원거리 무기]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[활]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[활]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_GREEN+"[석궁]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[석궁]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[마법 무기]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[마법 무기]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[원드]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[원드]"))
					RecipeList.set(RecipeName+".Only",ChatColor.DARK_AQUA+"[스태프]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[스태프]"))
					RecipeList.set(RecipeName+".Only",ChatColor.WHITE+"[방어구]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[방어구]"))
					RecipeList.set(RecipeName+".Only",ChatColor.GRAY+"[기타]");
				else if(RecipeList.getString(RecipeName+".Only").contains("[기타]"))
					RecipeList.set(RecipeName+".Only",ChatColor.WHITE+Type[0].toString());
		  		else
				{
					for(short count = 0; count < Type.length; count++)
					{
						if((RecipeList.getString(RecipeName+".Only").contains(Type[count].toString())))
						{
							if(count+1 == Type.length)
								RecipeList.set(RecipeName+".Only",ChatColor.RED+"[근접 무기]");
							else
								RecipeList.set(RecipeName+".Only",ChatColor.WHITE+Type[(count+1)].toString());
							break;
						}
					}
				}
		  	}
			RecipeList.saveConfig();
			UpgradeRecipeSettingGUI(player,RecipeName );
			return;
		case 14://대미지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 최소 공격력 수치를 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMinD");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 15://마법 대미지
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 최소 마법 공격력 수치를 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMMinD");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 16://밸런스
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 밸런스를 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UB");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 22://방어
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 방어력을 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UDEF");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 23://보호
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 보호를 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UP");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 24://마법 방어
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 마법 방어력을 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMDEF");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 25://마법 보호
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 마법 방어력을 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMP");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 31://크리티컬
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 크리티컬을 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UC");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 32://내구도
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 변화될 최대 내구도를 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW + Integer.MIN_VALUE+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UMD");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 33://개조
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 이 개조를 하기 위한 개조 레벨을 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW +0+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+""+Integer.MAX_VALUE+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UUL");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 34://숙련도
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[개조] : 개조 이후 차감될 숙련도를 입력하세요!");
			player.sendMessage(ChatColor.GREEN + "("+ChatColor.YELLOW +0+ChatColor.GREEN+" ~ "+ChatColor.YELLOW+"100"+ChatColor.GREEN+")");

			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "UDP");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 37://설명 변경
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage(ChatColor.DARK_AQUA+"[아이템] : 아이템의 설명을 입력해 주세요!");
			player.sendMessage(ChatColor.GOLD + "%enter%"+ChatColor.WHITE + " - 한줄 띄워 쓰기 -");
			player.sendMessage(ChatColor.WHITE + ""+ChatColor.BOLD + "&l " + ChatColor.BLACK + "&0 "+ChatColor.DARK_BLUE+"&1 "+ChatColor.DARK_GREEN+"&2 "+
			ChatColor.DARK_AQUA + "&3 " +ChatColor.DARK_RED + "&4 " + ChatColor.DARK_PURPLE + "&5 " +
					ChatColor.GOLD + "&6 " + ChatColor.GRAY + "&7 " + ChatColor.DARK_GRAY + "&8 " +
			ChatColor.BLUE + "&9 " + ChatColor.GREEN + "&a " + ChatColor.AQUA + "&b " + ChatColor.RED + "&c " +
					ChatColor.LIGHT_PURPLE + "&d " + ChatColor.YELLOW + "&e "+ChatColor.WHITE + "&f");
			u.setType(player, "Upgrade");
			u.setString(player, (byte)1, "ULC");
			u.setString(player, (byte)6, RecipeName);
			return;
		case 45://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			UpgradeRecipeGUI(player, 0);
			return;
		case 53://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
	}
	
}
