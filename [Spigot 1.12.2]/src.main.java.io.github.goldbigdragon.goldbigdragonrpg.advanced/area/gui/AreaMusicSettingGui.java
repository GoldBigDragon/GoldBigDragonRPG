package area.gui;

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

public class AreaMusicSettingGui extends GuiUtil
{
	private String uniqueCode = "§0§0§2§0§9§r";
	
	public void areaMusicSettingGui(Player player, int page, String areaName)
	{
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 배경음 : " + (page+1));
		byte loc=0;
		byte model = (byte) new util.NumericUtil().RandomNum(0, 11);
		for(int count = page*45; count < otherplugins.NoteBlockApiMain.Musics.size();count++)
		{
			if(model<11)
				model++;
			else
				model=0;
			String lore = " %enter%§3[트랙] §9"+ count+"%enter%"
			+"§3[제목] §9"+ new otherplugins.NoteBlockApiMain().getTitle(count)+"%enter%"
			+"§3[저자] §9"+new otherplugins.NoteBlockApiMain().getAuthor(count)+"%enter%§3[설명] ";
			String description = new otherplugins.NoteBlockApiMain().getDescription(count);
			String lore2="";
			short a = 0;
			for(int counter = 0; counter <description.toCharArray().length; counter++)
			{
				lore2 = lore2+"§9"+description.toCharArray()[counter];
				a++;
				if(a >= 15)
				{
					a = 0;
					lore2 = lore2+"%enter%      ";
				}
			}
			lore = lore + lore2+"%enter% %enter%§e[좌 클릭시 배경음 설정]";
			if(count > otherplugins.NoteBlockApiMain.Musics.size() || loc >= 45) break;
				removeFlagStack("§f§l" + count, 2256+model,0,1,Arrays.asList(lore.split("%enter%")), loc, inv);
			
			loc++;
		}
		
		if(otherplugins.NoteBlockApiMain.Musics.size()-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+areaName), 53, inv);
		player.openInventory(inv);
	}

	public void areaMusicSettingGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));

		int slot = event.getSlot();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);

		if(slot == 53)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(areaName.equals("DeathBGM¡"))
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)
				new admin.OPboxGui().opBoxGuiDeath(player);
			else if(slot == 48)
				areaMusicSettingGui(player, page-1,areaName);
			else if(slot == 50)
				areaMusicSettingGui(player, page+1,areaName);
			else
			{
			  	YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				configYaml.set("Death.Track", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
				configYaml.saveConfig();
				new admin.OPboxGui().opBoxGuiDeath(player);
			}
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new AreaSettingGui().areaSettingGui(player, areaName);
			else if(slot == 48)//이전 페이지
				areaMusicSettingGui(player, page-1,areaName);
			else if(slot == 50)//다음 페이지
				areaMusicSettingGui(player, page+1,areaName);
			else
			{
			  	YamlLoader areaYaml = new YamlLoader();
				areaYaml.getConfig("Area/AreaList.yml");
				areaYaml.set(areaName+".BGM", Integer.parseInt(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
				areaYaml.saveConfig();
				new AreaSettingGui().areaSettingGui(player, areaName);
			}
		}
	}
}