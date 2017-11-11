package warp;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;

public class Warp_GUI extends Util_GUI
{
	public void WarpListGUI(Player player, int page)
	{
		YamlLoader TelePort = new YamlLoader();
		TelePort.getConfig("Teleport/TeleportList.yml");
		String UniqueCode = "§0§0§c§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0워프 목록 : " + (page+1));

		Object[] TelePortList= TelePort.getKeys().toArray();

		byte loc=0;
		String worldname[]= new String[Bukkit.getServer().getWorlds().size()];
		for(int count=0;count<Bukkit.getServer().getWorlds().size();count++)
			worldname[count] = Bukkit.getServer().getWorlds().get(count).getName();
		short a = 0;
		for(int count = (short) (page*45); count < TelePortList.length+Bukkit.getServer().getWorlds().size();count++)
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
						Stack("§f"+TelePortTitle, 345, 0, 1,Arrays.asList("§3월드 : §f§l"+world,
							"§3x 좌표 : §f§l"+x
							,"§3y 좌표 : §f§l"+y
							,"§3z 좌표 : §f§l"+z
							,"§8시선 : §7§l"+pitch
							,"§8방향 : §7§l"+yaw
							,""
							,"§9[오직 OP만 명령어로 이동 가능합니다.]","","§e[좌 클릭시 해당 위치로 워프합니다.]","§e[Shift + 좌 클릭시 권한을 변경합니다.]","§c[Shift + 우 클릭시 해당 워프를 삭제합니다.]"), loc, inv);
					else
						Stack("§f"+TelePortTitle, 345, 0, 1,Arrays.asList("§3월드 : §f§l"+world,
							"§3x 좌표 : §f§l"+x
							,"§3y 좌표 : §f§l"+y
							,"§3z 좌표 : §f§l"+z
							,"§8시선 : §7§l"+pitch
							,"§8방향 : §7§l"+yaw
							,""
							,"§a[일반 유저도 명령어로 이동 가능합니다.]","","§e[좌 클릭시 해당 위치로 워프합니다.]","§e[Shift + 좌 클릭시 권한을 변경합니다.]","§c[Shift + 우 클릭시 해당 워프를 삭제합니다.]"), loc, inv);
					loc++;
				}
				else
				{
					if(OnlyOpUse == false)
						Stack("§f"+TelePortTitle, 345, 0, 1,Arrays.asList("§3월드 : §f§l"+world,
							"§3x 좌표 : §f§l"+x
							,"§3y 좌표 : §f§l"+y
							,"§3z 좌표 : §f§l"+z
							,"§8시선 : §7§l"+pitch
							,"§8방향 : §7§l"+yaw
							,"","§e[좌 클릭시 해당 위치로 워프합니다.]"), loc, inv);
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
					Stack("§f"+world, 2, 0, 1,Arrays.asList("§3월드 : §f§l"+world,
							"§3x 스폰 좌표 : §f§l"+x
							,"§3y 스폰 좌표 : §f§l"+y
							,"§3z 스폰 좌표 : §f§l"+z
							,"§8시선 : §7§l"+pitch
							,"§8방향 : §7§l"+yaw
							,""
							,"§9[오직 OP만 명령어로 이동 가능합니다.]","","§e[좌 클릭시 해당 월드로 워프합니다.]"), loc, inv);
					a++;
					loc++;
				}
			}

		}
		
		if(TelePortList.length-(page*44)>45)
			Stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		if(player.isOp() == true)
			Stack("§f§l새 워프", 339,0,1,Arrays.asList("§7새로운 워프 지점을 생성합니다."), 49, inv);
		Stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void WarpListGUIInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)
			{
				if(player.isOp())
					new admin.OPbox_GUI().opBoxGuiMain(player, (byte) 2);
				else
					new user.ETC_GUI().ETCGUI_Main(player);
			}
			else if(slot == 48)//이전 페이지
				WarpListGUI(player, page-1);
			else if(slot == 50)//다음 페이지
				WarpListGUI(player, page+1);
			else if(slot == 49 && player.isOp())//워프 생성
			{
				player.closeInventory();
				player.sendMessage("§3[워프] : 새 워프지점 이름을 적어 주세요!");
				UserData_Object u = new UserData_Object();
				u.setType(player, "Teleport");
				u.setString(player, (byte)1, "NW");
			}
			else
			{
				if(event.getCurrentItem().getTypeId()==2)
					new warp.Warp_Main().TeleportUser(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else
				{
					if(event.isShiftClick()==false&&event.isLeftClick())
						new warp.Warp_Main().TeleportUser(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					else if(event.isShiftClick()&&event.isLeftClick()&&player.isOp())
					{
						new warp.Warp_Main().setTeleportPermission(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						WarpListGUI(player, page);
					}
					else if(event.isShiftClick()&&event.isRightClick()&&player.isOp())
					{
						SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
						new warp.Warp_Main().RemoveTeleportList(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						WarpListGUI(player, page);
					}
				}
			}
		}
	}
}
