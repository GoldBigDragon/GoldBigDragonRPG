package admin;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import user.UserDataObject;
import util.UtilGui;

public class WorldCreateGui extends UtilGui
{
	public void worldCreateGuiMain(Player player)
	{
		String uniqueCode = "§0§0§1§1§b§r";
		Inventory inv = Bukkit.createInventory(null, 9, uniqueCode + "§0월드 선택");

		stack("§f§l일반 월드", 6,0,1,Arrays.asList("§7일반적인 월드를 생성합니다."), 2, inv);
		stack("§f§l완전한 평지", 2,0,1,Arrays.asList("§7완전한 평지를 가진 월드를 생성합니다."), 4, inv);
		stack("§f§l넓은 바이옴", 175,4,1,Arrays.asList("§7바이옴이 넓은 월드를 생성합니다."), 6, inv);
		
		stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);
		stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 8, inv);
		player.openInventory(inv);
	}
	
	public void worldCreateGuiClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 8)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)//이전 목록
				new OPboxGui().opBoxGuiMain(player, (byte) 2);
			else
			{
				UserDataObject u = new UserDataObject();
				u.setType(player, "WorldCreater");
				u.setString(player, (byte)2, "WorldCreate");
				player.closeInventory();
				if(slot == 2)//일반 월드 생성
					u.setString(player, (byte)3, "NORMAL");
				else if(slot == 4)//평지 월드 생성
					u.setString(player, (byte)3, "FLAT");
				else if(slot == 6)//넓은 바이옴 월드 생성
					u.setString(player, (byte)3, "LARGE_BIOMES");
				
				player.sendMessage("§6[월드 생성] : 새로운 월드 이름을 작성 해 주세요!");
			}
		}
	}
}
