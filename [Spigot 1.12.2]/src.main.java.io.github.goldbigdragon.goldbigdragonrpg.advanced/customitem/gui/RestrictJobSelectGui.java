package customitem.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import util.GuiUtil;
import util.YamlLoader;

public class RestrictJobSelectGui extends GuiUtil {

	private String uniqueCode = "§0§0§3§0§2§r";
	
	public void restrictJobSelectGui(Player player, int page, int number)
	{
	  	YamlLoader jobYaml = new YamlLoader();
		jobYaml.getConfig("Skill/JobList.yml");
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0아이템 직업 제한 : " + (page+1));

		String[] jobList = jobYaml.getConfigurationSection("MapleStory").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < jobList.length;count++)
		{
			int jobLevel= jobYaml.getConfigurationSection("MapleStory."+jobList[count]).getKeys(false).size();
			
			if(count > jobList.length || loc >= 45) break;
			
			if(jobList[count].equalsIgnoreCase(configYaml.getString("Server.DefaultJob")))
				removeFlagStack("§f§l" + jobList[count], 403,0,1,Arrays.asList("§3최대 승급 : §f"+jobLevel+"§3차 승급","","§e[좌클릭시 전용 설정]","§e§l[서버 기본 직업]"), loc, inv);
			else
				removeFlagStack("§f§l" + jobList[count], 340,0,1,Arrays.asList("§3최대 승급 : §f"+jobLevel+"§3차 승급","","§e[좌클릭시 전용 설정]"), loc, inv);
			
			loc++;
		}
		
		if(jobList.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+number), 53, inv);
		player.openInventory(inv);
	}
	
	public void restrictJobSelectClick(InventoryClickEvent event)
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
			int itemnumber = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			if(slot == 45)//이전 목록
				new EquipItemForgingGui().itemForgingGui(player, itemnumber);
			else
			{
			  	YamlLoader temYaml = new YamlLoader();
				temYaml.getConfig("Item/ItemList.yml");
				temYaml.set(itemnumber+".JOB", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				temYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ITEM_SHIELD_BREAK, 0.8F, 1.0F);
				new EquipItemForgingGui().itemForgingGui(player, itemnumber);
			}
		}
	}
}
