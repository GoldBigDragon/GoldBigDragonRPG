package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.Object_UserData;

public class WorldCreateGUI extends GUIutil
{
	public void WorldCreateGUIMain(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 9, ChatColor.BLACK + "월드 선택");

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "일반 월드", 6,0,1,Arrays.asList(ChatColor.GRAY + "일반적인 월드를 생성합니다."), 2, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "완전한 평지", 2,0,1,Arrays.asList(ChatColor.GRAY + "완전한 평지를 가진 월드를 생성합니다."), 4, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "넓은 바이옴", 175,4,1,Arrays.asList(ChatColor.GRAY + "바이옴이 넓은 월드를 생성합니다."), 6, inv);
		
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 8, inv);
		player.openInventory(inv);
	}
	
	public void WorldCreateGUIClick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Object_UserData u = new Object_UserData();
		switch (event.getSlot())
		{
		case 0://이전 목록
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			OPBoxGUI OPGUI = new OPBoxGUI();
			OPGUI.OPBoxGUI_Main(player, (byte) 2);
			return;
		case 8://나가기
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		case 2 :
			player.closeInventory();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "WorldCreater");
			u.setString(player, (byte)2, "WorldCreate");
			u.setString(player, (byte)3, "NORMAL");
			player.sendMessage(ChatColor.GOLD+"[월드 생성] : 새로운 월드 이름을 작성 해 주세요!");
			break;
		case 4 :
			player.closeInventory();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "WorldCreater");
			u.setString(player, (byte)2, "WorldCreate");
			u.setString(player, (byte)3, "FLAT");
			player.sendMessage(ChatColor.GOLD+"[월드 생성] : 새로운 월드 이름을 작성 해 주세요!");
			break;
		case 6 :
			player.closeInventory();
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			u.setType(player, "WorldCreater");
			u.setString(player, (byte)2, "WorldCreate");
			u.setString(player, (byte)3, "LARGE_BIOMES");
			player.sendMessage(ChatColor.GOLD+"[월드 생성] : 새로운 월드 이름을 작성 해 주세요!");
			break;
		}
	}
}
