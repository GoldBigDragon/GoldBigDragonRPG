package warp;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import user.UserDataObject;
import util.GuiUtil;
import util.YamlLoader;

public class WarpGui extends GuiUtil
{
	public void warpListGUI(Player player, int page)
	{
		YamlLoader telePort = new YamlLoader();
		telePort.getConfig("Teleport/TeleportList.yml");
		String uniqueCode = "§0§0§c§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, uniqueCode + "§0워프 목록 : " + (page+1));

		Object[] telePortList= telePort.getKeys().toArray();

		int loc=0;
		String[] worldname= new String[Bukkit.getServer().getWorlds().size()];
		for(int count=0;count<Bukkit.getServer().getWorlds().size();count++)
			worldname[count] = Bukkit.getServer().getWorlds().get(count).getName();
		int a = 0;
		String telePortTitle = null;
		String world = null;
		int x = 0;
		int y = 0;
		int z = 0;
		int pitch = 0;
		int yaw = 0;
		boolean onlyOpUse = true;
		for(int count = (page*45); count < telePortList.length+Bukkit.getServer().getWorlds().size();count++)
		{
			if(loc >= 45) break;
			if(count < telePortList.length)
			{
				telePortTitle = telePortList[count].toString();
				world = telePort.getString(telePortTitle+".World");
				x = telePort.getInt(telePortTitle+".X");
				y = telePort.getInt(telePortTitle+".Y");
				z = telePort.getInt(telePortTitle+".Z");
				pitch = telePort.getInt(telePortTitle+".Pitch");
				yaw = telePort.getInt(telePortTitle+".Yaw");
				onlyOpUse = telePort.getBoolean(telePortTitle+".OnlyOpUse");

				if(player.isOp())
				{
					if(onlyOpUse)
						stack("§f"+telePortTitle, 345, 0, 1,Arrays.asList("§3월드 : §f§l"+world,
							"§3x 좌표 : §f§l"+x
							,"§3y 좌표 : §f§l"+y
							,"§3z 좌표 : §f§l"+z
							,"§8시선 : §7§l"+pitch
							,"§8방향 : §7§l"+yaw
							,""
							,"§9[오직 OP만 명령어로 이동 가능합니다.]","","§e[좌 클릭시 해당 위치로 워프합니다.]","§e[Shift + 좌 클릭시 권한을 변경합니다.]","§c[Shift + 우 클릭시 해당 워프를 삭제합니다.]"), loc, inv);
					else
						stack("§f"+telePortTitle, 345, 0, 1,Arrays.asList("§3월드 : §f§l"+world,
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
					if(!onlyOpUse)
						stack("§f"+telePortTitle, 345, 0, 1,Arrays.asList("§3월드 : §f§l"+world,
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
				if(player.isOp())
				{
					world = worldname[a];
					x = (int) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getX();
					y = (int) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getY();
					z = (int) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getZ();
					pitch = (int) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getPitch();
					yaw = (int) Bukkit.getServer().getWorld(worldname[a]).getSpawnLocation().getYaw();
					stack("§f"+world, 2, 0, 1,Arrays.asList("§3월드 : §f§l"+world,
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
		
		if(telePortList.length-(page*44)>45)
			stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		if(player.isOp())
			stack("§f§l새 워프", 339,0,1,Arrays.asList("§7새로운 워프 지점을 생성합니다."), 49, inv);
		stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void warpListGUIInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//닫기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
			if(slot == 45)
			{
				if(player.isOp())
					new admin.OPboxGui().opBoxGuiMain(player, 2);
				else
					new user.EtcGui().ETCGUI_Main(player);
			}
			else if(slot == 48)//이전 페이지
				warpListGUI(player, page-1);
			else if(slot == 50)//다음 페이지
				warpListGUI(player, page+1);
			else if(slot == 49 && player.isOp())//워프 생성
			{
				player.closeInventory();
				player.sendMessage("§3[워프] : 새 워프지점 이름을 적어 주세요!");
				UserDataObject u = new UserDataObject();
				u.setType(player, "Teleport");
				u.setString(player, (byte)1, "NW");
			}
			else
			{
				String teleportName = event.getCurrentItem().getItemMeta().getDisplayName().substring(2);
				if(event.getCurrentItem().getTypeId()==2)
					new warp.WarpMain().TeleportUser(player, teleportName);
				else
				{
					if(!event.isShiftClick()&&event.isLeftClick())
						new warp.WarpMain().TeleportUser(player, teleportName);
					else if(event.isShiftClick()&&event.isLeftClick()&&player.isOp())
					{
						new warp.WarpMain().setTeleportPermission(player, teleportName);
						warpListGUI(player, page);
					}
					else if(event.isShiftClick()&&event.isRightClick()&&player.isOp())
					{
						SoundEffect.playSound(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
						new warp.WarpMain().RemoveTeleportList(player, teleportName);
						warpListGUI(player, page);
					}
				}
			}
		}
	}
}
