package admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import user.UserDataObject;
import util.GuiUtil;

public class WorldCreateGui extends GuiUtil
{
	public void worldCreateGuiMain(Player player)
	{
		String uniqueCode = "§0§0§1§1§b§r";
		Inventory inv = Bukkit.createInventory(null, 36, uniqueCode + "§0월드 선택");

		UserDataObject u = new UserDataObject();
		String type = u.getString(player, 3);
		String biome = u.getString(player, 4);
		boolean createStructure = u.getBoolean(player, (byte)0);
		
		if(type.equals("FLAT"))
			stack("§f§l완전한 평지", 2,0,1,Arrays.asList("§7완전한 평지를 가진 지형을 생성합니다.", "", "§8일반 지형", " §7§l완전한 평지", "§8넓은 바이옴", ""), 11, inv);
		else if(type.equals("LARGE_BIOMES"))
			stack("§f§l넓은 바이옴", 175,4,1,Arrays.asList("§7바이옴이 넓은 지형을 생성합니다.", "", "§8일반 지형", "§8완전한 평지", " §7§l넓은 바이옴", ""), 11, inv);
		else
			stack("§f§l일반 지형", 6,0,1,Arrays.asList("§7일반적인 지형을 생성합니다.", "", " §7§l일반 지형", "§8완전한 평지", "§8넓은 바이옴", ""), 11, inv);

		if(biome.equals("NETHER"))
		{
			stack("§c§l네더", 87,0,1,Arrays.asList("§7네더 월드를 생성합니다.", "", "§8일반 월드", " §7§l네더 월드", "§8엔드 월드", ""), 13, inv);
			inv.setItem(11, null);
		}
		else if(biome.equals("THE_END"))
		{
			stack("§e§l엔드", 121,0,1,Arrays.asList("§7엔드 월드를 생성합니다.", "", "§8일반 월드", "§8네더 월드", " §7§l엔드 월드", ""), 13, inv);
			inv.setItem(11, null);
		}
		else
			stack("§a§l일반", 2,0,1,Arrays.asList("§7일반 월드를 생성합니다.", "", " §7§l일반 월드", "§8네더 월드", "§8엔드 월드", ""), 13, inv);
		
		if(createStructure)
			stack("§9구조물 생성", 98,0,1,Arrays.asList("§7월드에 맞는 구조물을 생성합니다.", ""), 15, inv);
		else
			stack("§c구조물 미생성", 166,0,1,Arrays.asList("§7구조물을 생성하지 않습니다.", ""), 15, inv);

		stack("§f§l월드 생성", 2,0,1,Arrays.asList("§7클릭 시 월드 이름을 짓습니다."), 31, inv);
		
		stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 27, inv);
		stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 35, inv);
		player.openInventory(inv);
	}
	
	public void worldCreateGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 35)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 27)//이전 목록
				new OPboxGui().opBoxGuiMain(player, (byte) 2);
			else
			{
				UserDataObject u = new UserDataObject();
				if(slot == 11)
				{
					String type = u.getString(player, 3);
					if(type.equals("FLAT"))
						u.setString(player, 3, "LARGE_BIOMES");
					else if(type.equals("LARGE_BIOMES"))
						u.setString(player, 3, "NORMAL");
					else
						u.setString(player, 3, "FLAT");
					worldCreateGuiMain(player);
				}
				else if(slot == 13)
				{
					String biome = u.getString(player, 4);
					if(biome.equals("NETHER"))
						u.setString(player, 4, "THE_END");
					else if(biome.equals("THE_END"))
						u.setString(player, 4, "NORMAL");
					else
						u.setString(player, 4, "NETHER");
					worldCreateGuiMain(player);
				}
				else if(slot == 15)
				{
					boolean createStructure = u.getBoolean(player, (byte)0);
					u.setBoolean(player, (byte)0, ! createStructure);
					worldCreateGuiMain(player);
				}
				else if(slot == 31)
				{
					u.setType(player, "WorldCreater");
					u.setString(player, (byte)2, "WorldCreate");
					player.closeInventory();
					player.sendMessage("§6[월드 생성] : 새로운 월드 이름을 작성 해 주세요!");
				}
			}
		}
	}
}
