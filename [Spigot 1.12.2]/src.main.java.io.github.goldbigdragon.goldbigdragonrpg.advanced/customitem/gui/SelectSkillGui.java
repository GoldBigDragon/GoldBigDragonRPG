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

public class SelectSkillGui extends GuiUtil{

	public void selectSkillGui(Player player, int page, int itemNumber)
	{
	  	YamlLoader skillYaml = new YamlLoader();
		skillYaml.getConfig("Skill/SkillList.yml");

		String uniqueCode = "§0§0§3§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0등록 가능 스킬 목록 : " + (page+1));

		String[] skillList= skillYaml.getKeys().toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count < skillList.length;count++)
		{
			short jobLevel= (short) skillYaml.getConfigurationSection(skillList[count].toString()+".SkillRank").getKeys(false).size();
			if(count > skillList.length || loc >= 45) break;

		  	YamlLoader jobYaml = new YamlLoader();
			jobYaml.getConfig("Skill/JobList.yml");
			if(jobYaml.contains("Mabinogi.Added."+skillList[count])==true)
			{
				removeFlagStack("§f§l" + skillList[count],  skillYaml.getInt(skillList[count].toString()+".ID"),skillYaml.getInt(skillList[count].toString()+".DATA"),skillYaml.getInt(skillList[count].toString()+".Amount"),Arrays.asList("§3최대 스킬 레벨 : §f"+jobLevel,"","§e[좌 클릭시 스킬 등록]"), loc, inv);
				loc++;	
			}
		}
		
		if(skillList.length-(page*44)>45)
		removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+itemNumber), 53, inv);
		player.openInventory(inv);
	}
	
	public void selectSkillGuiClick(InventoryClickEvent event)
	{
		
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			int number = Integer.parseInt(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				new UseableItemMakingGui().useableItemMakingGui(player, number);
			else if(slot == 48)//이전 페이지
				selectSkillGui(player, page-1, number);
			else if(slot == 50)//다음 페이지
				selectSkillGui(player, page, number);
			else
			{
			  	YamlLoader useableItemYaml = new YamlLoader();
				useableItemYaml.getConfig("Item/Consume.yml");
				useableItemYaml.set(number+".Skill", ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				useableItemYaml.saveConfig();
				new UseableItemMakingGui().useableItemMakingGui(player, number);
			}
		}
	}
	
}
