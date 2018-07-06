package area.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import util.GuiUtil;
import util.YamlLoader;

public class AreaFishSettingGui extends GuiUtil {

	private String uniqueCode = "§1§0§2§0§4§r";
	
	public void areaFishSettingGui(Player player, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 추가 어류");
		
		removeFlagStack("§a§l[     54%     ]", 160,5,1,Arrays.asList("§7이 줄에는 54% 확률로 낚일 아이템을 올리세요."), 0, inv);
		removeFlagStack("§e§l[     30%     ]", 160,4,1,Arrays.asList("§7이 줄에는 30% 확률로 낚일 아이템을 올리세요."), 9, inv);
		removeFlagStack("§6§l[     10%     ]", 160,1,1,Arrays.asList("§7이 줄에는 10% 확률로 낚일 아이템을 올리세요."), 18, inv);
		removeFlagStack("§c§l[      5%      ]", 160,14,1,Arrays.asList("§7이 줄에는 5% 확률로 낚일 아이템을 올리세요."), 27, inv);
		removeFlagStack("§7§l[      1%      ]", 160,10,1,Arrays.asList("§7이 줄에는 1% 확률로 낚일 아이템을 올리세요."), 36, inv);

		String[] fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.54").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			stackItem(areaYaml.getItemStack(areaName+".Fishing.54."+fishingItemList[count]), count+1, inv);
		fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.30").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			stackItem(areaYaml.getItemStack(areaName+".Fishing.30."+fishingItemList[count]), count+10, inv);
		fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.10").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			stackItem(areaYaml.getItemStack(areaName+".Fishing.10."+fishingItemList[count]), count+19, inv);
		fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.5").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			stackItem(areaYaml.getItemStack(areaName+".Fishing.5."+fishingItemList[count]), count+28, inv);
		fishingItemList = areaYaml.getConfigurationSection(areaName+".Fishing.1").getKeys(false).toArray(new String[0]);
		for(int count = 0; count < fishingItemList.length; count++)
			stackItem(areaYaml.getItemStack(areaName+".Fishing.1."+fishingItemList[count]), count+37, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ areaName), 53, inv);
		player.openInventory(inv);
	}

	public void areaFishSettingClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		if(event.getClickedInventory().getType() != InventoryType.PLAYER && (slot == 0 || slot == 9 || slot == 18 || slot == 27 || slot == 36 || slot >= 45))
			event.setCancelled(true);
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 45)//이전 목록
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			new AreaSettingGui().areaSettingGui(player, areaName);
		}
	}
	

	public void areafishingSettingClose(InventoryCloseEvent event)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		byte loc = 0;
		for(int count = 1; count < 9; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.54."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.54."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 10; count < 18; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.30."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.30."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 19; count < 27; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.10."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.10."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 28; count < 36; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.5."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.5."+loc);
			areaYaml.saveConfig();
		}
		loc = 0;
		for(int count = 37; count < 45; count++)
		{
			if(event.getInventory().getItem(count)!=null)
			{
				areaYaml.set(areaName + ".Fishing.1."+loc, event.getInventory().getItem(count));
				loc++;
			}
			else
				areaYaml.removeKey(areaName+".Fishing.1."+loc);
			areaYaml.saveConfig();
		}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.54."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.54."+(counter), areaYaml.getItemStack(areaName+".Fishing.54."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.54."+(counter+1));
				}
				areaYaml.saveConfig();
			}
	
		byte line1 = 0;
		byte line2 = 0;
		byte line3 = 0;
		byte line4 = 0;
		byte line5 = 0;
		for(int count = 0; count < 45; count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(count>0&&count<9 )
					line1 = (byte) (line1+1);
				else if(count>9&&count<18 )
					line2 = (byte)(line2 +1);
				else if(count>18&&count<27 )
					line3 = (byte)(line3 +1);
				else if(count>27&&count<36 )
					line4 = (byte)(line4 +1);
				else if(count>36&&count<45 )
					line5 =(byte) (line5 +1);
			}
		}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.54."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.54."+(counter), areaYaml.getItemStack(areaName+".Fishing.54."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.54."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.30."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.30."+(counter), areaYaml.getItemStack(areaName+".Fishing.30."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.30."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.10."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.10."+(counter), areaYaml.getItemStack(areaName+".Fishing.10."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.10."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.5."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.5."+(counter), areaYaml.getItemStack(areaName+".Fishing.5."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.5."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = 0; count <7;count++)
			if(areaYaml.getItemStack(areaName+".Fishing.1."+count) == null)
			{
				for(int counter = count; counter <7; counter++)
				{
					areaYaml.set(areaName+".Fishing.1."+(counter), areaYaml.getItemStack(areaName+".Fishing.1."+(counter+1)));
					areaYaml.removeKey(areaName+".Fishing.1."+(counter+1));
				}
				areaYaml.saveConfig();
			}
		for(int count = line1; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.54."+count))
				areaYaml.removeKey(areaName+".Fishing.54."+count);
		for(int count = line2; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.30."+count))
				areaYaml.removeKey(areaName+".Fishing.30."+count);
		for(int count = line3; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.10."+count))
				areaYaml.removeKey(areaName+".Fishing.10."+count);
		for(int count = line4; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.5."+count))
				areaYaml.removeKey(areaName+".Fishing.5."+count);
		for(int count = line5; count <7;count++)
			if(areaYaml.contains(areaName+".Fishing.1."+count))
				areaYaml.removeKey(areaName+".Fishing.1."+count);
		areaYaml.saveConfig();
		return;
	}
}
