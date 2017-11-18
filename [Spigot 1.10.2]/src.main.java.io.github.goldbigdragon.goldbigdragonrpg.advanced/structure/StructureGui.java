package structure;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import admin.OPboxGui;
import effect.SoundEffect;
import util.UtilGui;
import util.YamlLoader;

public class StructureGui extends UtilGui
{
	public void StructureListGUI(Player player, int page)
	{
		YamlLoader StructureConfig = new YamlLoader();
		StructureConfig.getConfig("Structure/StructureList.yml");

		String UniqueCode = "§0§0§d§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0전체 개체 목록 : " + (page+1));

		Object[] StructureList= StructureConfig.getConfigurationSection("").getKeys(false).toArray();
		
		byte loc=0;
		for(int count = page*45; count < StructureList.length;count++)
		{
			String StructureCode = StructureList[count].toString();
			short StructureType = (short)StructureConfig.getInt(StructureCode+".Type");
			int ID = 1;
			byte DATA = 0;
			
			if(StructureType==0)//우편함
				ID=386;
			else if(StructureType==1)//게시판
				ID=323;
			else if(StructureType==2)//거래 게시판
				ID=389;
			else if(StructureType==3)//모닥불
				ID=17;
			else if(StructureType==101)//제단
				ID=120;
			
			if(StructureType==0)
				Stack2("§0"+StructureCode, ID,DATA,1,Arrays.asList("","§3월드 : §f"+StructureConfig.getString(StructureCode+".World"),"§3좌표 : §f"+StructureConfig.getInt(StructureCode+".X")+","+StructureConfig.getInt(StructureCode+".Y")+","+StructureConfig.getInt(StructureCode+".Z"),"","§c[Shift + 우클릭시 개체 삭제]"), loc, inv);
			else
				Stack2("§0"+StructureCode, ID,DATA,1,Arrays.asList("","§3월드 : §f"+StructureConfig.getString(StructureCode+".World"),"§3좌표 : §f"+StructureConfig.getInt(StructureCode+".X")+","+StructureConfig.getInt(StructureCode+".Y")+","+StructureConfig.getInt(StructureCode+".Z"),"","§e[좌 클릭시 세부 설정]","§c[Shift + 우클릭시 개체 삭제]"), loc, inv);
			loc++;
		}
		
		if(StructureList.length-(page*44)>45)
			Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l새 개체", 386,0,1,Arrays.asList("§7현재 서 있는 위치에","§7새로운 개체를 생성합니다."), 49, inv);
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
		return;
	}

	public void SelectStructureTypeGUI(Player player, byte page)
	{
		String UniqueCode = "§0§0§d§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0개체 타입 선택 : " + (page+1));
		switch(page)
		{
		case 0:
			Stack2("§c§l[우편함]", 386,0,1,Arrays.asList("§7우체통을 통하여 플레이어간","§7아이템을 주고 받거나, 메시지를","§7주고 받을 수 있습니다."), 0, inv);
			Stack2("§a§l[게시판]", 323,0,1,Arrays.asList("§7게시판을 통하여 플레이어가","§7글을 남길 수 있습니다.","","§e[세부 설정 항목이 존재합니다]"), 1, inv);
			Stack2("§9§l[거래 게시판]", 389,0,1,Arrays.asList("§7거래판을 통하여 플레이어끼리","§7아이템을 사고 팔 수 있습니다.","","§e[세부 설정 항목이 존재합니다]"), 2, inv);
			Stack2("§c§l[모닥불]", 17,0,1,Arrays.asList("§7불을 이용한 요리가 가능해 집니다.","§7활을 쏠 경우 불화살이 나갑니다.","§7화살의 공격력이 50% 증가합니다."), 3, inv);
			break;

		}
		
		/*
		if(page!=최대 페이지)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=최소 페이지)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		 */
		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
		return;
	}

	public void SelectStructureDirectionGUI(Player player, short StructureID)
	{
		String UniqueCode = "§0§0§d§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0개체 방향");

		Stack2("§9§l[동]", 345,0,1,Arrays.asList("§7개체가 동쪽 방향을 바라봅니다."), 1, inv);
		Stack2("§9§l[서]", 345,0,1,Arrays.asList("§7개체가 서쪽 방향을 바라봅니다."), 3, inv);
		Stack2("§9§l[남]", 345,0,1,Arrays.asList("§7개체가 남쪽 방향을 바라봅니다."), 5, inv);
		Stack2("§9§l[북]", 345,0,1,Arrays.asList("§7개체가 북쪽 방향을 바라봅니다."), 7, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 0, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+StructureID), 8, inv);
		player.openInventory(inv);
		return;
	}
	
	
	
	public void StructureListGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		
		if(slot == 53)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			byte page = (byte) (Byte.parseByte(event.getInventory().getTitle().split(" : ")[1])-1);
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				new OPboxGui().opBoxGuiMain(player, (byte) 3);
			else if(slot == 48)//이전 페이지
				StructureListGUI(player, page-1);
			else if(slot == 50)//다음 페이지
				StructureListGUI(player, page+1);
			else if(slot == 49)//새 개체
				SelectStructureTypeGUI(player, (byte) 0);
			else
			{
				String StructureName = event.getCurrentItem().getItemMeta().getDisplayName();
				StructureName=StructureName.substring(2, StructureName.length());
				if(event.isLeftClick() == true)
				{
					if(StructureName.contains("게시판"))
					{
						if(StructureName.contains("거래"))
							new structure.StructTradeBoard().TradeBoardSettingGUI(player);
						else
							new structure.StructBoard().BoardSettingGUI(player, StructureName);
					}
					//기능 개체 세부내용(player, StructureName);
				}
				else if(event.isShiftClick() == true && event.isRightClick() == true)
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					YamlLoader StructureConfig = new YamlLoader();
					StructureConfig.getConfig("Structure/StructureList.yml");

					Location loc = new Location(Bukkit.getServer().getWorld(StructureConfig.getString(StructureName+".World")), StructureConfig.getInt(StructureName+".X"), StructureConfig.getInt(StructureName+".Y"), StructureConfig.getInt(StructureName+".Z"));
					byte radius = 10;
					switch(StructureConfig.getInt(StructureName+".Type"))
					{
						case 0 :
							radius = 2; break;
						case 1 :
						case 2 :
							radius = 3; break;
						case 3 :
						{
							radius = 3;
							loc.setX(loc.getX()-1);
							loc.setZ(loc.getZ()-1);
							Block b = loc.getBlock();
							if(b.getType()==Material.TORCH)
								b.setType(Material.AIR);
							loc.setY(loc.getY()-2);
							b = loc.getBlock();
							if(b.getType()==Material.STATIONARY_LAVA||
								b.getType()==Material.LAVA)
								b.setType(Material.STONE);
							loc.setY(loc.getY()+1);
							break;
						}
					}
					Object[] EntitiList = Bukkit.getServer().getWorld(StructureConfig.getString(StructureName+".World")).getNearbyEntities(loc, radius, radius, radius).toArray();
					for(int count=0; count<EntitiList.length;count++)
						if(((Entity)EntitiList[count]).getCustomName()!=null)
							if(((Entity)EntitiList[count]).getCustomName().equals(StructureName))
								((Entity)EntitiList[count]).remove();
					StructureConfig.removeKey(StructureName);
					StructureConfig.saveConfig();
					File file = new File("plugins/GoldBigDragonRPG/Structure/"+Changer(StructureName)+".yml");
					if(file.exists())
						file.delete();
					StructureListGUI(player, page);
				}
			}
		}
	}
	
	public void SelectStructureTypeGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			byte page = (byte) (Byte.parseByte(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				StructureListGUI(player, 0);
			else if(slot == 48)//이전 페이지
				SelectStructureTypeGUI(player, (byte) (page-1));
			else if(slot == 50)//다음 페이지
				SelectStructureTypeGUI(player, (byte) (page+1));
			else
			{
				int StructureID = event.getSlot()+(page*45);
				SelectStructureDirectionGUI(player, (short) StructureID);
			}
		}
	}	
	
	public void SelectStructureDirectionGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		
		if(slot == 8)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)//이전 목록
				SelectStructureTypeGUI(player, (byte) 0);
			else if(slot == 1||slot == 3||slot == 5||slot == 7)
			{
				short StructureID =  (short)(Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getLore().get(1))));
				String Code = "§0§l";
				switch(StructureID)
				{
					case 0:
						Code = Code+"§c[우편함]"; break;
					case 1:
						Code = Code+"§a[게시판]"; break;
					case 2:
						Code = Code+"§9[거래 게시판]"; break;
					case 3:
						Code = Code+"§c[모닥불]"; break;
					case 101:
						Code = Code+"§f[제단]"; break;
				}
				player.closeInventory();
				YamlLoader StructureConfig = new YamlLoader();
				StructureConfig.getConfig("Structure/StructureList.yml");
				String Salt = Code;
				for(;;)
				{
					for(int count=0;count < 6; count++)
						Salt = Salt+getRandomCode();
					if(StructureConfig.contains(Salt)==false)
						break;
					Salt = Code;
				}
				StructureConfig.set(Salt+".Type", StructureID);
				StructureConfig.set(Salt+".World", player.getLocation().getWorld().getName());
				StructureConfig.set(Salt+".X", (int)player.getLocation().getX());
				StructureConfig.set(Salt+".Y", (int)player.getLocation().getY());
				StructureConfig.set(Salt+".Z", (int)player.getLocation().getZ());
				StructureConfig.saveConfig();
				new structure.StructureMain().CreateSturcture(player, Salt, StructureID, event.getSlot());
			}
		}
	}
	
	
	
	public String getRandomCode()
	{
		byte randomNum = (byte) new util.UtilNumber().RandomNum(0, 15);
		switch(randomNum)
		{
			case 0:
				return "§0";
			case 1:
				return "§1";
			case 2:
				return "§2";
			case 3:
				return "§3";
			case 4:
				return "§4";
			case 5:
				return "§5";
			case 6:
				return "§6";
			case 7:
				return "§7";
			case 8:
				return "§8";
			case 9:
				return "§9";
			case 10:
				return "§a";
			case 11:
				return "§b";
			case 12:
				return "§c";
			case 13:
				return "§d";
			case 14:
				return "§e";
			case 15:
				return "§f";
		}
		return "§0";
	}

	public String Changer(String StructureCode)
	{
		StructureCode=StructureCode.replaceAll("§l", "§l");
		StructureCode=StructureCode.replaceAll("§0", "§0");
		StructureCode=StructureCode.replaceAll("§1", "§1");
		StructureCode=StructureCode.replaceAll("§2", "§2");
		StructureCode=StructureCode.replaceAll("§3", "§3");
		StructureCode=StructureCode.replaceAll("§4", "§4");
		StructureCode=StructureCode.replaceAll("§5", "§5");
		StructureCode=StructureCode.replaceAll("§6", "§6");
		StructureCode=StructureCode.replaceAll("§7", "§7");
		StructureCode=StructureCode.replaceAll("§8", "§7");
		StructureCode=StructureCode.replaceAll("§9", "§9");
		StructureCode=StructureCode.replaceAll("§a", "§a");
		StructureCode=StructureCode.replaceAll("§b", "§b");
		StructureCode=StructureCode.replaceAll("§c", "§c");
		StructureCode=StructureCode.replaceAll("§d", "§d");
		StructureCode=StructureCode.replaceAll("§e", "§e");
		StructureCode=StructureCode.replaceAll("§f", "§f");
		return StructureCode;
	}
}
