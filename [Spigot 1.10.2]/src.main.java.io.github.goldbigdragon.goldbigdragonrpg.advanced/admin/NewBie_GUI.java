package admin;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;



public class NewBie_GUI extends Util_GUI
{
	public void NewBieGUIMain(Player player)
	{
		String UniqueCode = "§0§0§1§1§7§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0초심자 옵션");

		YamlLoader newbieYaml = new YamlLoader();
		newbieYaml.getConfig("ETC/NewBie.yml");
	
		Stack("§f§l기본 아이템", 54,0,1,Arrays.asList(ChatColor.GRAY + "첫 접속시 아이템을 지급합니다."), 2, inv);
		Stack("§f§l기본 퀘스트", 386,0,1,Arrays.asList(ChatColor.GRAY + "접속 하자마자 퀘스트를 줍니다.",ChatColor.GRAY+"(일종의 튜토리얼 입니다.)","",ChatColor.DARK_AQUA+"[   기본 퀘스트   ]",ChatColor.WHITE+""+ChatColor.BOLD+newbieYaml.getString("FirstQuest"),"",ChatColor.YELLOW+"[클릭시 퀘스트를 변경합니다.]",ChatColor.RED+"[Shift + 우 클릭시 퀘스트를 삭제합니다.]"), 3, inv);
		Stack("§f§l기본 시작 위치", 368,0,1,Arrays.asList(ChatColor.GRAY + "접속 하자마자 이동 시킵니다.","",ChatColor.DARK_AQUA+"[   시작 위치   ]",ChatColor.DARK_AQUA+" - 월드 : "+ChatColor.WHITE+""+ChatColor.BOLD+newbieYaml.getString("TelePort.World")
		,ChatColor.DARK_AQUA+" - 좌표 : "+ChatColor.WHITE+""+ChatColor.BOLD+newbieYaml.getInt("TelePort.X")+","+newbieYaml.getInt("TelePort.Y")+","+newbieYaml.getInt("TelePort.Z"),"",ChatColor.YELLOW+"[클릭시 현재 위치로 등록 됩니다.]"), 4, inv);
		Stack("§f§l가이드", 340,0,1,Arrays.asList(ChatColor.GRAY + "가이드창을 설정합니다.","",ChatColor.GRAY+"/기타",ChatColor.DARK_GRAY+"명령어를 통해 설정한",ChatColor.DARK_GRAY+"가이드를 확인하실 수 있습니다."), 5, inv);
		
		Stack("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 0, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 8, inv);
		player.openInventory(inv);
	}
	
	public void NewBieSupportItemGUI(Player player)
	{
		String UniqueCode = "§1§0§1§1§8§r";
		YamlLoader newbieYaml = new YamlLoader();
		newbieYaml.getConfig("ETC/NewBie.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0초심자 지원");

		int newbieSupportItemAmount = newbieYaml.getConfigurationSection("SupportItem").getKeys(false).size();

		byte num = 0;
		for(int count = 0; count < newbieSupportItemAmount;count++)
			if(newbieYaml.getItemStack("SupportItem."+count) != null)
			{
				ItemStackStack(newbieYaml.getItemStack("SupportItem."+count), num, inv);
				num++;
			}

		Stack("§f§l-", 166,0,1,null, 46, inv);
		Stack("§f§l-", 166,0,1,null, 47, inv);
		Stack("§f§l-", 166,0,1,null, 48, inv);
		Stack("§f§l기본 지원금", 266,0,1,Arrays.asList(ChatColor.GRAY + "기본적으로 지원해 주는 돈을 설정합니다.","",ChatColor.YELLOW+"[현재 지원금]","§f§l"+newbieYaml.getInt("SupportMoney")),49, inv);
		Stack("§f§l-", 166,0,1,null, 50, inv);
		Stack("§f§l-", 166,0,1,null, 51, inv);
		Stack("§f§l-", 166,0,1,null, 52, inv);
		
		Stack("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void NewBieQuestGUI(Player player, short page)
	{
		String UniqueCode = "§0§0§1§1§9§r";
		YamlLoader questYaml = new YamlLoader();
		questYaml.getConfig("Quest/QuestList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0초심자 기본퀘 선택 : " + (page+1));

		Object[] a = questYaml.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String QuestName = a[count].toString();
			Set<String> QuestFlow= questYaml.getConfigurationSection(QuestName+".FlowChart").getKeys(false);
			if(count > a.length || loc >= 45) break;
			switch(questYaml.getString(a[count].toString() + ".Type"))
			{
				case "N" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 340,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일반 퀘스트",""), loc, inv);
					break;
				case "R" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 386,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 반복 퀘스트",""), loc, inv);
					break;
				case "D" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일일 퀘스트",""), loc, inv);
					break;
				case "W" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 일주 퀘스트",""), loc, inv);
					break;
				case "M" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(ChatColor.WHITE+"퀘스트 구성 요소 : "+QuestFlow.size()+"개",ChatColor.DARK_AQUA+"퀘스트 타입 : 한달 퀘스트",""), loc, inv);
					break;
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
		Stack2("§f§l다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
		Stack2("§f§l이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void NewBieGuideGUI(Player player)
	{
		YamlLoader newbieYaml = new YamlLoader();
		newbieYaml.getConfig("ETC/NewBie.yml");

		if(newbieYaml.contains("Guide")==false)
		{
			newbieYaml.createSection("Guide");
			newbieYaml.saveConfig();
		}

		String UniqueCode = "§1§0§1§1§a§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0초심자 가이드");

		int newbieGuideItemSize= newbieYaml.getConfigurationSection("Guide").getKeys(false).size();

		byte num = 0;
		for(int count = 0; count < newbieGuideItemSize; count++)
			if(newbieYaml.getItemStack("Guide."+count) != null)
			{
				ItemStackStack(newbieYaml.getItemStack("Guide."+count), num, inv);
				num++;
			}

		Stack("§f§l-", 166,0,1,null, 46, inv);
		Stack("§f§l-", 166,0,1,null, 47, inv);
		Stack("§f§l-", 166,0,1,null, 48, inv);
		Stack("§f§l-", 166,0,1,null, 49, inv);
		Stack("§f§l-", 166,0,1,null, 50, inv);
		Stack("§f§l-", 166,0,1,null, 51, inv);
		Stack("§f§l-", 166,0,1,null, 52, inv);
		
		Stack("§f§l이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		
		player.openInventory(inv);
	}
	
	
	public void NewBieGUIMainInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		
		if(slot == 8)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			if(slot == 3)//기본 퀘스트
			{
				if(event.isLeftClick())
					NewBieQuestGUI(player, (short) 0);
				else if(event.isRightClick()&&event.isShiftClick())
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
					YamlLoader newbieYaml = new YamlLoader();
					newbieYaml.getConfig("ETC/NewBie.yml");
					newbieYaml.set("FirstQuest", "null");
					newbieYaml.saveConfig();
					NewBieGUIMain(player);
				}
			}
			else
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				if(slot == 0)//이전 목록
					new admin.OPbox_GUI().OPBoxGUI_Main(player, (byte) 2);
				else if(slot == 2)//기본 아이템
					NewBieSupportItemGUI(player);
				else if(slot == 4)//기본 시작 위치
				{
					YamlLoader newbieYaml = new YamlLoader();
					newbieYaml.getConfig("ETC/NewBie.yml");
					newbieYaml.set("TelePort.World", player.getLocation().getWorld().getName());
					newbieYaml.set("TelePort.X", player.getLocation().getX());
					newbieYaml.set("TelePort.Y", player.getLocation().getY());
					newbieYaml.set("TelePort.Z", player.getLocation().getZ());
					newbieYaml.set("TelePort.Pitch", player.getLocation().getPitch());
					newbieYaml.set("TelePort.Yaw", player.getLocation().getYaw());
					newbieYaml.saveConfig();
					player.sendMessage(ChatColor.GREEN+"[뉴비 텔레포트지점] : 접속시 이동 위치 변경 완료!");
					NewBieGUIMain(player);
				}
				else if(slot == 5)//가이드
					NewBieGuideGUI(player);
			}
		}
	}
	
	public void NewBieSupportItemGUIInventoryclick(InventoryClickEvent event, String SubjectCode)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		if(slot >= 45)
			event.setCancelled(true);
		if(slot == 53)//닫기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(slot == 45)//이전 목록
				NewBieGUIMain(player);
			else if(slot == 49)//기본 지원
			{
				if(SubjectCode.compareTo("1a")!=0)//초심자 가이드 설정창이 아닐 경우
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					player.closeInventory();
					player.sendMessage(ChatColor.DARK_AQUA+"[뉴비 지원금] : 얼마를 가지고 시작하도록 하시겠습니까?");
					player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
					UserData_Object u = new UserData_Object();
					u.setType(player, "NewBie");
					u.setString(player, (byte)1, "NSM");
				}
			}
		}
	}
	
	public void NewBieQuestGUIInventoryclick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 53)
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(slot == 45)//이전 목록
				NewBieGUIMain(player);
			else if(slot == 48)//이전 페이지
				NewBieQuestGUI(player, (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//다음 페이지
				NewBieQuestGUI(player, (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])));
			else
			{
				String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlLoader newbieYaml = new YamlLoader();
				newbieYaml.getConfig("ETC/NewBie.yml");
				YamlLoader questYaml = new YamlLoader();
				questYaml.getConfig("Quest/QuestList.yml");
				
				if(questYaml.contains(QuestName)==true)
				{
					if(questYaml.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length != 0)
					{
						newbieYaml.set("FirstQuest", QuestName);
						newbieYaml.saveConfig();
						NewBieGUIMain(player);
					}
					else
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[뉴비 퀘스트] : 해당 퀘스트는 퀘스트 오브젝트가 존재하지 않습니다!");
					}
				}
				else
				{
					SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[뉴비 퀘스트] : 해당 퀘스트는 존재하지 않습니다!");
				}
			}
		}
	}

	public void InventoryClose_NewBie(InventoryCloseEvent event, String SubjectCode)
	{
		YamlLoader newbieYaml = new YamlLoader();
		newbieYaml.getConfig("ETC/NewBie.yml");
		short num = 0;
		for(int count = 0; count < 45;count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(SubjectCode.compareTo("1a")==0)//가이드
					newbieYaml.set("Guide."+num, event.getInventory().getItem(count));
				else//if(SubjectCode.compareTo("18")==0)//지원 아이템
					newbieYaml.set("SupportItem."+num, event.getInventory().getItem(count));
				num++;
			}
			else
				if(SubjectCode.compareTo("1a")==0)//가이드
					newbieYaml.removeKey("Guide."+count);
				else//if(SubjectCode.compareTo("18")==0)//지원 아이템
					newbieYaml.removeKey("SupportItem."+count);
		}
		newbieYaml.saveConfig();

		if(SubjectCode.compareTo("1a")==0)//가이드
			event.getPlayer().sendMessage(ChatColor.GREEN + "[뉴비 가이드] : 성공적으로 저장 되었습니다!");
		else//if(SubjectCode.compareTo("18")==0)//지원 아이템
			event.getPlayer().sendMessage(ChatColor.GREEN + "[뉴비 아이템] : 성공적으로 저장 되었습니다!");
		SoundEffect.SP((Player) event.getPlayer(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
	}
}
