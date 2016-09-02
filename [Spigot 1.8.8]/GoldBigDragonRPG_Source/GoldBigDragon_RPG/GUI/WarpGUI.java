package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import GoldBigDragon_RPG.Main.Object_UserData;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class WarpGUI extends GUIutil
{
	public void WarpListGUI(Player player, int page)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager TelePort  = YC.getNewConfig("Teleport/TeleportList.yml");
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "워프 목록 : " + (page+1));

		Object[] TelePortList= TelePort.getKeys().toArray();

		byte loc=0;
		String worldname[]= new String[Bukkit.getServer().getWorlds().size()];
		for(short count=0;count<Bukkit.getServer().getWorlds().size();count++)
			worldname[count] = Bukkit.getServer().getWorlds().get(count).getName();
		short a = 0;
		for(short count = (short) (page*45); count < TelePortList.length+Bukkit.getServer().getWorlds().size();count++)
		{
			if(loc >= 45) break;
			if(count < TelePortList.length)
			{
				String TelePortTitle =TelePortList[count].toString();
				String world = TelePort.getString(TelePortTitle+".World");
				int x = TelePort.getInt(TelePortTitle+".X");
				short y = (short) TelePort.getInt(TelePortTitle+".Y");
				int z = TelePort.getInt(TelePortTitle+".Z");
				short pitch = (short) TelePort.getInt(TelePortTitle+".Pitch");
				short yaw = (short) TelePort.getInt(TelePortTitle+".Yaw");
				boolean OnlyOpUse = TelePort.getBoolean(TelePortTitle+".OnlyOpUse");

				if(player.isOp() == true)
				{
					if(OnlyOpUse == true)
						Stack(ChatColor.WHITE+TelePortTitle, 345, 0, 1,Arrays.asList(ChatColor.DARK_AQUA+"월드 : "+ChatColor.WHITE+""+ChatColor.BOLD+world,
							ChatColor.DARK_AQUA+"x 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+x
							,ChatColor.DARK_AQUA+"y 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+y
							,ChatColor.DARK_AQUA+"z 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+z
							,ChatColor.DARK_GRAY+"시선 : "+ChatColor.GRAY+""+ChatColor.BOLD+pitch
							,ChatColor.DARK_GRAY+"방향 : "+ChatColor.GRAY+""+ChatColor.BOLD+yaw
							,""
							,ChatColor.BLUE+"[오직 OP만 명령어로 이동 가능합니다.]","",ChatColor.YELLOW+"[좌 클릭시 해당 위치로 워프합니다.]",ChatColor.YELLOW+"[Shift + 좌 클릭시 권한을 변경합니다.]",ChatColor.RED+"[Shift + 우 클릭시 해당 워프를 삭제합니다.]"), loc, inv);
					else
						Stack(ChatColor.WHITE+TelePortTitle, 345, 0, 1,Arrays.asList(ChatColor.DARK_AQUA+"월드 : "+ChatColor.WHITE+""+ChatColor.BOLD+world,
							ChatColor.DARK_AQUA+"x 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+x
							,ChatColor.DARK_AQUA+"y 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+y
							,ChatColor.DARK_AQUA+"z 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+z
							,ChatColor.DARK_GRAY+"시선 : "+ChatColor.GRAY+""+ChatColor.BOLD+pitch
							,ChatColor.DARK_GRAY+"방향 : "+ChatColor.GRAY+""+ChatColor.BOLD+yaw
							,""
							,ChatColor.GREEN+"[일반 유저도 명령어로 이동 가능합니다.]","",ChatColor.YELLOW+"[좌 클릭시 해당 위치로 워프합니다.]",ChatColor.YELLOW+"[Shift + 좌 클릭시 권한을 변경합니다.]",ChatColor.RED+"[Shift + 우 클릭시 해당 워프를 삭제합니다.]"), loc, inv);
					loc++;
				}
				else
				{
					if(OnlyOpUse == false)
						Stack(ChatColor.WHITE+TelePortTitle, 345, 0, 1,Arrays.asList(ChatColor.DARK_AQUA+"월드 : "+ChatColor.WHITE+""+ChatColor.BOLD+world,
							ChatColor.DARK_AQUA+"x 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+x
							,ChatColor.DARK_AQUA+"y 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+y
							,ChatColor.DARK_AQUA+"z 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+z
							,ChatColor.DARK_GRAY+"시선 : "+ChatColor.GRAY+""+ChatColor.BOLD+pitch
							,ChatColor.DARK_GRAY+"방향 : "+ChatColor.GRAY+""+ChatColor.BOLD+yaw
							,"",ChatColor.YELLOW+"[좌 클릭시 해당 위치로 워프합니다.]"), loc, inv);
					loc++;
				}
			}
			else
			{
				if(player.isOp() == true)
				{
					String world = worldname[a];
					int x = (int) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getX();
					short y = (short) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getY();
					int z = (int) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getZ();
					short pitch = (short) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getPitch();
					short yaw = (short) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getYaw();
					Stack(ChatColor.WHITE+world, 2, 0, 1,Arrays.asList(ChatColor.DARK_AQUA+"월드 : "+ChatColor.WHITE+""+ChatColor.BOLD+world,
							ChatColor.DARK_AQUA+"x 스폰 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+x
							,ChatColor.DARK_AQUA+"y 스폰 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+y
							,ChatColor.DARK_AQUA+"z 스폰 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+z
							,ChatColor.DARK_GRAY+"시선 : "+ChatColor.GRAY+""+ChatColor.BOLD+pitch
							,ChatColor.DARK_GRAY+"방향 : "+ChatColor.GRAY+""+ChatColor.BOLD+yaw
							,""
							,ChatColor.BLUE+"[오직 OP만 명령어로 이동 가능합니다.]","",ChatColor.YELLOW+"[좌 클릭시 해당 월드로 워프합니다.]"), loc, inv);
					a++;
					loc++;
				}
			}

		}
		
		if(TelePortList.length-(page*44)>45)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		if(player.isOp() == true)
			Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "새 워프", 339,0,1,Arrays.asList(ChatColor.GRAY + "새로운 워프 지점을 생성합니다."), 49, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void WarpListGUIInventoryclick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		switch (event.getSlot())
		{
		case 45:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			if(player.isOp() == true)
				new GoldBigDragon_RPG.GUI.OPBoxGUI().OPBoxGUI_Main(player, (byte) 2);
			else
				new GoldBigDragon_RPG.GUI.ETCGUI().ETCGUI_Main(player);
			return;
		case 48://이전 페이지
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			WarpListGUI(player, page-1);
			return;
		case 49://워프 생성
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				player.closeInventory();
				player.sendMessage(ChatColor.DARK_AQUA+"[워프] : 새 워프지점 이름을 적어 주세요!");
				Object_UserData u = new Object_UserData();
				u.setType(player, "Teleport");
				u.setString(player, (byte)1, "NW");
			}
			return;
		case 50://다음 페이지
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			WarpListGUI(player, page+1);
			return;
		case 54:
			s.SP(player, Sound.PISTON_RETRACT, 1.0F, 1.0F);
			player.closeInventory();
			return;
		default:
			if(event.getCurrentItem().getTypeId()==2)
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				new GoldBigDragon_RPG.ETC.Teleport().TeleportUser(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
			}
			else
			{
				if(event.isShiftClick()==false&&event.isLeftClick())
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					new GoldBigDragon_RPG.ETC.Teleport().TeleportUser(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
				else if(event.isShiftClick()&&event.isLeftClick()&&player.isOp())
				{
					s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
					new GoldBigDragon_RPG.ETC.Teleport().setTeleportPermission(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					WarpListGUI(player, page);
				}
				else if(event.isShiftClick()&&event.isRightClick()&&player.isOp())
				{
					s.SP(player, Sound.LAVA_POP, 1.0F, 1.0F);
					new GoldBigDragon_RPG.ETC.Teleport().RemoveTeleportList(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					WarpListGUI(player, page);
				}
			}
			return;
		}
	}
}
