package monster.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import util.GuiUtil;
import util.YamlLoader;

public class MonsterEquipGui extends GuiUtil{

	private String uniqueCode = "§1§0§8§0§3§r";
	
	public void monsterEquipGui(Player player, String monsterName)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		Inventory inv = Bukkit.createInventory(null, 9, uniqueCode + "§0몬스터 장비 설정");
		monsterListYaml.getConfig("Monster/MonsterList.yml");

		String[] parts = {"Head", "Chest", "Leggings", "Boots", "Hand", "OffHand"};
		String[] partsName = {"§f머리", "§f갑옷", "§f바지", "§f부츠", "§f오른손", "§f왼손"};
		int[] partsId = {302, 303, 304, 305, 267, 267};
		ItemStack item = null;
		for(int count = 0; count < parts.length; count++)
		{
			item = monsterListYaml.getItemStack(monsterName + "." + parts[count] + ".Item");
			if(item != null && ! item.equals(new ItemStack(Material.AIR)))
					inv.setItem(count, item);
			else
				stack(partsName[count], partsId[count], 0, 1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), count, inv);
		}
		
		stack("§f"+ monsterName, 416, 0, 1,Arrays.asList("§8"+ monsterName+"의 현재 장비입니다." ),  8, inv);
		stack("§f", 30, 0, 1,Arrays.asList("§7이곳에는 아이템을","§7올려두지 마세요."),  7, inv);
		stack("§f", 30, 0, 1,Arrays.asList("§7이곳에는 아이템을","§7올려두지 마세요."),  6, inv);
		
		player.openInventory(inv);
		return;
	}

	public void monsterEquipClick(InventoryClickEvent event)
	{
		if(event.getSlot() >=6 && event.getSlot() <= 8)
			event.setCancelled(true);
		else if(event.getCurrentItem().hasItemMeta())
			if(event.getCurrentItem().getItemMeta().hasLore())
				if(event.getCurrentItem().getItemMeta().getLore().get(0).equals("§7이곳에 아이템을 넣어 주세요."))
					event.getInventory().remove(event.getCurrentItem());
		return;
	}
	
	public void monsterEquipClose(InventoryCloseEvent event)
	{
		YamlLoader monsterListYaml = new YamlLoader();

		monsterListYaml.getConfig("Monster/MonsterList.yml");
		String monsterName = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName().toString());

		String[] parts = {"Head", "Chest", "Leggings", "Boots", "Hand", "OffHand"};
		String[] partsName = {"§f머리", "§f갑옷", "§f바지", "§f부츠", "§f오른손", "§f왼손"};
		int[] partsId = {302, 303, 304, 305, 267, 267};
		for(int count = 0; count < parts.length; count++)
		{
			if(event.getInventory().getItem(count) == getItemStack(partsName[count], partsId[count],0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
				monsterListYaml.set(monsterName+"." + parts[count] + ".Item", null);
			else
				monsterListYaml.set(monsterName+"." + parts[count] + ".Item", event.getInventory().getItem(0));
		}
		monsterListYaml.saveConfig();
		event.getPlayer().sendMessage("§a[SYSTEM] : 아이템 설정이 저장되었습니다.");
		return;
	}
}
