package area.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import effect.SoundEffect;
import util.GuiUtil;
import util.YamlLoader;

public class AreaBlockItemSettingGui extends GuiUtil {

	private String uniqueCode = "§1§0§2§0§6§r";
	
	public void areaBlockItemSettingGui(Player player,String areaName,String itemData)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0해당 블록을 캐면 나올 아이템");

		ItemStack item = areaYaml.getItemStack(areaName+".Mining."+itemData+".100");
		
		stackItem(item, 4, inv);

		removeFlagStack("§c§l[아이템 넣기>", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 0, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 1, inv);	
		removeFlagStack("§c§l[아이템 넣기>", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 2, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 3, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 5, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 6, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 7, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,11,1,Arrays.asList("§7[100% 확률로 나올 아이템]"), 8, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".90");
		stackItem(item, 13, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 9, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 10, inv);	
		removeFlagStack("§c§l[아이템 넣기>", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 11, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 12, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 14, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 15, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 16, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,9,1,Arrays.asList("§7[90% 확률로 나올 아이템]"), 17, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".50");
		stackItem(item, 22, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 18, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 19, inv);	
		removeFlagStack("§c§l[아이템 넣기>", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 20, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 21, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 23, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 24, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 25, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,4,1,Arrays.asList("§7[50% 확률로 나올 아이템]"), 26, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".10");
		stackItem(item, 31, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 27, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 28, inv);	
		removeFlagStack("§c§l[아이템 넣기>", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 29, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 30, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 32, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 33, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 34, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,1,1,Arrays.asList("§7[10% 확률로 나올 아이템]"), 35, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".1");
		stackItem(item, 40, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 36, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 37, inv);	
		removeFlagStack("§c§l[아이템 넣기>", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 38, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 39, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 41, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 42, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 43, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,14,1,Arrays.asList("§7[1% 확률로 나올 아이템]"), 44, inv);

		item = areaYaml.getItemStack(areaName+".Mining."+itemData+".0");
		stackItem(item, 49, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 46, inv);	
		removeFlagStack("§c§l[아이템 넣기>", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 47, inv);
		removeFlagStack("§c§l[아이템 넣기>", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 48, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 50, inv);
		removeFlagStack("§c§l<아이템 넣기]", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 51, inv);	
		removeFlagStack("§c§l<아이템 넣기]", 160,15,1,Arrays.asList("§7[0.1% 확률로 나올 아이템]"), 52, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다.","§0"+itemData), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ areaName), 53, inv);
		player.openInventory(inv);
		return;
	}
	

	public void areaBlockItemSettingClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		if(!event.getClickedInventory().getTitle().equals("container.inventory"))
		{
			if(slot==4||slot==13||slot==22||slot==31||slot==40||slot==49)
				event.setCancelled(false);
			else if(slot == 53)//나가기
			{
				SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
				player.closeInventory();
			}
			else if(slot == 45)//이전 목록
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				new AreaBlockSettingGui().areaBlockSettingGui(player, 0, areaName);
			}
		}
	}

	public void areaBlockItemSettingClose(InventoryCloseEvent event)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String itemData = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		if(event.getInventory().getItem(4) != null)
			areaYaml.set(areaName+".Mining."+itemData+".100", event.getInventory().getItem(4));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".100");
			areaYaml.set(areaName+".Mining."+itemData,"0:0");
		}
		if(event.getInventory().getItem(13) != null)
			areaYaml.set(areaName+".Mining."+itemData+".90", event.getInventory().getItem(13));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".90");
			areaYaml.set(areaName+".Mining."+itemData+".90","0:0");
		}
		if(event.getInventory().getItem(22) != null)
			areaYaml.set(areaName+".Mining."+itemData+".50", event.getInventory().getItem(22));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".50");
			areaYaml.set(areaName+".Mining."+itemData+".50","0:0");
		}
		if(event.getInventory().getItem(31) != null)
			areaYaml.set(areaName+".Mining."+itemData+".10", event.getInventory().getItem(31));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".10");
			areaYaml.set(areaName+".Mining."+itemData+".10","0:0");
		}
		if(event.getInventory().getItem(40) != null)
			areaYaml.set(areaName+".Mining."+itemData+".1", event.getInventory().getItem(40));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".1");
			areaYaml.set(areaName+".Mining."+itemData+".1","0:0");
		}
		if(event.getInventory().getItem(49) != null)
			areaYaml.set(areaName+".Mining."+itemData+".0", event.getInventory().getItem(49));
		else
		{
			areaYaml.removeKey(areaName+".Mining."+itemData+".0");
			areaYaml.set(areaName+".Mining."+itemData+".0","0:0");
		}
		areaYaml.saveConfig();
		return;
	}
}
