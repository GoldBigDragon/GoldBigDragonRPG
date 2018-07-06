package area.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

public class AreaBlockSettingGui extends GuiUtil {

	private String uniqueCode = "§0§0§2§0§5§r";
	
	public void areaBlockSettingGui(Player player, int page, String areaName)
	{
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		event.EventInteract I = new event.EventInteract();

		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0영역 특산품 : " + (page+1));

		String[] blockIdDataList= areaYaml.getConfigurationSection(areaName+".Mining").getKeys(false).toArray(new String[0]);
		
		byte loc=0;
		for(int count = page*45; count <blockIdDataList.length ;count++)
		{
			short id = Short.parseShort(blockIdDataList[count].split(":")[0]);
			byte data = Byte.parseByte(blockIdDataList[count].split(":")[1]);

			removeFlagStack(I.setItemDefaultName(id, data), id,data,1,Arrays.asList(
					"","§c[Shift + 우클릭시 등록 해제]"), loc, inv);
				loc++;
		}
		
		if(blockIdDataList.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		removeFlagStack("§f§l특산물 추가", 52,0,1,Arrays.asList("§7새로운 블록을 설정합니다."), 49, inv);
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+ areaName), 53, inv);
		player.openInventory(inv);
	}

	public void areaBlockSettingClick(InventoryClickEvent event)
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
			String areaName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				new AreaSettingGui().areaSettingGui(player, areaName);
			else if(slot == 48)//이전 페이지
				areaBlockSettingGui(player, (short) (page-1), areaName);
			else if(slot == 49)//특산물 추가
			{
				player.closeInventory();
				player.sendMessage("§3[영역] : 설정할 블록을 좌클릭 하세요!");

				UserDataObject u = new UserDataObject();
				u.setType(player, "Area");
				u.setString(player, (byte)2, areaName);
				u.setString(player, (byte)3, "ANBI");
			}
			else if(slot == 50)//다음 페이지
				areaBlockSettingGui(player, (short) (page+1), areaName);
			else
			{
				String blockName = event.getCurrentItem().getTypeId()+":"+event.getCurrentItem().getData().getData();
				if(!event.isShiftClick()&&event.isLeftClick())
					new AreaBlockItemSettingGui().areaBlockItemSettingGui(player, areaName, blockName);
				else if(event.isShiftClick() && event.isRightClick())
				{
					SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
				  	YamlLoader areaYaml = new YamlLoader();
					areaYaml.getConfig("Area/AreaList.yml");
					areaYaml.removeKey(areaName+".Mining."+blockName);
					areaYaml.saveConfig();
					areaBlockSettingGui(player, page, areaName);
				}
			}
		}
	}
}
