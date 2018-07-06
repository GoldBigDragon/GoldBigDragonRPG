package customitem.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import admin.OPboxGui;
import customitem.CustomItemAPI;
import effect.SoundEffect;
import util.GuiUtil;
import util.YamlLoader;

public class EquipItemListGui extends GuiUtil{

	private String uniqueCode = "§0§0§3§0§0§r";
	
	public void itemListGui(Player player, int page)
	{
	  	YamlLoader itemYaml = new YamlLoader();
		itemYaml.getConfig("Item/ItemList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0아이템 목록 : " + (page+1));

		int itemAmounts = itemYaml.getKeys().size();

		byte loc=0;
		CustomItemAPI ciapi = new CustomItemAPI();
		for(int count = page*45; count < itemAmounts;count++)
		{
			int number = ((page*45)+loc);
			if(count > itemAmounts || loc >= 45) break;
			String itemName = itemYaml.getString(number+".DisplayName");
			int itemId = itemYaml.getInt(number+".ID");
			int itemData = itemYaml.getInt(number+".Data");
			removeFlagStack(itemName, itemId, itemData, 1,Arrays.asList(ciapi.loreCreater(number)), loc, inv);
			
			loc++;
		}
		
		if(itemAmounts-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l새 아이템", 145,0,1,Arrays.asList("§7새로운 아이템을 생성합니다."), 49, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void itemListClick(InventoryClickEvent event)
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
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page = (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				new OPboxGui().opBoxGuiMain(player,(byte) 1);
			else if(slot == 48)//이전 페이지
				itemListGui(player,page-1);
			else if(slot == 50)//다음 페이지
				itemListGui(player,page+1);
			else
			{
			  	YamlLoader itemYaml = new YamlLoader();
				itemYaml.getConfig("Item/ItemList.yml");
				if(slot == 49)//새 아이템
				{
					int itemCounter = itemYaml.getKeys().size();
					itemYaml.set(itemCounter+".ShowType","§f[깔끔]");
					itemYaml.set(itemCounter+".ID",267);
					itemYaml.set(itemCounter+".Data",0);
					itemYaml.set(itemCounter+".DisplayName","§f방금 만든 따끈한 검");
					itemYaml.set(itemCounter+".Lore","§f방금 만들어진 무기이다.%enter%§f조금은 허접할지도...");
					itemYaml.set(itemCounter+".Type","§c[근접 무기]");
					itemYaml.set(itemCounter+".Grade","§f[일반]");
					itemYaml.set(itemCounter+".MinDamage",1);
					itemYaml.set(itemCounter+".MaxDamage",7);
					itemYaml.set(itemCounter+".MinMaDamage",0);
					itemYaml.set(itemCounter+".MaxMaDamage",0);
					itemYaml.set(itemCounter+".DEF",0);
					itemYaml.set(itemCounter+".Protect",0);
					itemYaml.set(itemCounter+".MaDEF",0);
					itemYaml.set(itemCounter+".MaProtect",0);
					itemYaml.set(itemCounter+".Durability",256);
					itemYaml.set(itemCounter+".MaxDurability",256);
					itemYaml.set(itemCounter+".STR",0);
					itemYaml.set(itemCounter+".DEX",0);
					itemYaml.set(itemCounter+".INT",0);
					itemYaml.set(itemCounter+".WILL",0);
					itemYaml.set(itemCounter+".LUK",0);
					itemYaml.set(itemCounter+".Balance",10);
					itemYaml.set(itemCounter+".Critical",5);
					itemYaml.set(itemCounter+".MaxUpgrade",5);
					itemYaml.set(itemCounter+".MaxSocket",3);
					itemYaml.set(itemCounter+".HP",0);
					itemYaml.set(itemCounter+".MP",0);
					itemYaml.set(itemCounter+".JOB","공용");
					itemYaml.set(itemCounter+".MinLV",0);
					itemYaml.set(itemCounter+".MinRLV",0);
					itemYaml.set(itemCounter+".MinSTR",0);
					itemYaml.set(itemCounter+".MinDEX",0);
					itemYaml.set(itemCounter+".MinINT",0);
					itemYaml.set(itemCounter+".MinWILL",0);
					itemYaml.set(itemCounter+".MinLUK",0);
					itemYaml.saveConfig();
					new EquipItemForgingGui().itemForgingGui(player, itemCounter);
				}
				else
				{
					int number = ((page*45)+event.getSlot());
					if(event.isLeftClick()&&!event.isShiftClick())
					{
						player.sendMessage("§a[SYSTEM] : 클릭한 아이템을 지급 받았습니다!");
						player.getInventory().addItem(event.getCurrentItem());
					}
					if(event.isLeftClick()&&event.isShiftClick())
						new EquipItemForgingGui().itemForgingGui(player, number);
					else if(event.isRightClick()&&event.isShiftClick())
					{
						short acount =  (short) (itemYaml.getKeys().toArray().length-1);
						for(int counter = (short) number;counter <acount;counter++)
							itemYaml.set(counter+"", itemYaml.get((counter+1)+""));
						itemYaml.removeKey(acount+"");
						itemYaml.saveConfig();
						itemListGui(player, page);
					}
				}
			}
		}
	}
}
