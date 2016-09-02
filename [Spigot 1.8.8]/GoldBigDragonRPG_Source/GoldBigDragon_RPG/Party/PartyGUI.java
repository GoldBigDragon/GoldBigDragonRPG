package GoldBigDragon_RPG.Party;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.GUI.ETCGUI;
import GoldBigDragon_RPG.GUI.EquipGUI;
import GoldBigDragon_RPG.GUI.GUIutil;

public final class PartyGUI extends GUIutil
{
	private GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
	private ETCGUI EGUI = new ETCGUI();

	public void PartyGUI_Main(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "파티");
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 목록", 340,0,1,Arrays.asList(ChatColor.GRAY + "현재 개설된 파티 목록을 봅니다."), 12, inv);
		if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player)==false)
		{
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 개설", 323,0,1,Arrays.asList(ChatColor.GRAY + "새로운 파티를 개설합니다."), 10, inv);
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 참여", 386,0,1,Arrays.asList(ChatColor.GRAY + "생성되어 있는 파티에 참여합니다."), 12, inv);
		}
		else
		{
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 정보", 397,3,1,Arrays.asList(ChatColor.GRAY + "현재 파티의 정보를 알아봅니다.",ChatColor.GRAY+"리더의 경우, 파티 멤버를",ChatColor.GRAY+"강퇴 시킬 수도 있습니다."), 10, inv);
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 탈퇴", 52,0,1,Arrays.asList(ChatColor.GRAY + "파티에서 탈퇴합니다."), 14, inv);
			if(GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).getLeader().equalsIgnoreCase(player.getName()) == true)
			{
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "리더 변경", 386,0,1,Arrays.asList(ChatColor.GRAY + "파티의 리더를 변경합니다."), 28, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "인원 변경", 386,0,1,Arrays.asList(ChatColor.GRAY + "제한 인원을 변경합니다."), 30, inv);
				if(GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).getLock() == false) Stack2(ChatColor.BLUE +""+ ChatColor.BOLD + "파티 개방", 54,0,1,Arrays.asList(ChatColor.GRAY + "파티 가입 신청을 받습니다."), 34, inv);
				else Stack2(ChatColor.RED +""+ ChatColor.BOLD + "파티 잠금", 130,0,1,Arrays.asList(ChatColor.GRAY + "파티 가입 신청을 받지 않습니다."), 34, inv);
			}
		}
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "기타 창으로 돌아갑니다."), 36, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 44, inv);
		player.openInventory(inv);
	}
	
	public void PartyListGUI(Player player, short page)
	{
		Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "파티 목록 : " + (page+1));

		Object[] a = GoldBigDragon_RPG.Main.ServerOption.Party.keySet().toArray();
		if(GoldBigDragon_RPG.Main.ServerOption.Party.size() <= 0)
		{
			s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[파티] : 생성된 파티가 없습니다!");
			player.sendMessage(ChatColor.GOLD + "/파티 생성 <이름>");
			return;
		}
		else
		{
			byte loc=0;
			for(int count = page*45; count < GoldBigDragon_RPG.Main.ServerOption.Party.size();count++)
			{
				if(count > GoldBigDragon_RPG.Main.ServerOption.Party.size() || loc >= 45) break;
				if(GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getLock()==false)
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getTitle(), 54,0,1,Arrays.asList(ChatColor.GRAY + "파티 생성 시각 : "+ChatColor.DARK_GRAY+a[count],"",ChatColor.AQUA + "파티장 : "+ChatColor.GRAY+GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getLeader(),ChatColor.AQUA + "인원 : "+ChatColor.GRAY + GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getPartyMembers()+"/"+GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getCapacity()), loc, inv);
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getTitle(), 130,0,1,Arrays.asList(ChatColor.GRAY + "파티 생성 시각 : "+ChatColor.DARK_GRAY+a[count],"",ChatColor.AQUA + "파티장 : "+ChatColor.GRAY+GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getLeader(),ChatColor.AQUA + "인원 : "+ChatColor.GRAY + GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getPartyMembers()+"/"+GoldBigDragon_RPG.Main.ServerOption.Party.get(a[count]).getCapacity(),"",ChatColor.RED + "[잠김]",ChatColor.RED + "파티 가입을 하시려면",ChatColor.RED +"파티장에게 문의하세요!"), loc, inv);
				loc++;
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void PartyMemberInformation(Player player, short page, long PartyCreateTime, boolean isLeaderChange)
	{
		Inventory inv = null;
		if(isLeaderChange == false)
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "파티 멤버 : " + (page+1));
		else
			inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "파티 리더 교체 : " + (page+1));
			
		Player[] Member = GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getMember();
			byte loc=0;
			for(int count = page*45; count < Member.length;count++)
			{
				ItemStack ph = null;
				if(count > Member.length || loc >= 45) break;
				Damageable pl = Member[count];
				if(player.getName().equals(GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getLeader()))
				{
					if(isLeaderChange == false)
					{
						if(!Member[count].getName().equals(GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getLeader()))
							ph = getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.WHITE +"" + ChatColor.BOLD+ "[   일반 멤버   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ 좌 클릭시 장비 구경 ]",ChatColor.RED +"" + ChatColor.BOLD+ "[Shift + 우 클릭시 강퇴]"), Member[count].getName());
						else
							ph = getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[   파티 리더   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ 좌 클릭시 장비 구경 ]"), Member[count].getName());
					}
					else
					{
						if(!Member[count].getName().equals(GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getLeader()))
							ph = getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.WHITE +"" + ChatColor.BOLD+ "[   일반 멤버   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ 좌 클릭시 리더 변경 ]"), Member[count].getName());
						else
							ph = getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[   파티 리더   ]","",ChatColor.RED +"" + ChatColor.BOLD+ "[ 리더 변경 불가능 ]"), Member[count].getName());
					}
				}
				else
				{
					if(!Member[count].getName().equals(GoldBigDragon_RPG.Main.ServerOption.Party.get(PartyCreateTime).getLeader()))
						ph = getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
								"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.WHITE +"" + ChatColor.BOLD+ "[   일반 멤버   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ 좌 클릭시 장비 구경 ]"), Member[count].getName());
					else
						ph = getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
								"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[   파티 리더   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ 좌 클릭시 장비 구경 ]"), Member[count].getName());
				}

				ItemStackStack(ph, loc, inv);
				loc++;
			}
		if(Member.length-(page*44)>45)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "다음 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 페이지", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 페이지로 이동 합니다."), 48, inv);
		
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "이전 화면으로 돌아갑니다."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void PartyGUIClickRouter(InventoryClickEvent event, String InventoryName)
	{
	    if(InventoryName.compareTo("파티")==0)
	    	partyInventoryclick(event);
		else if(InventoryName.contains("목록"))
			PartyListInventoryclick(event);
		else if(InventoryName.contains("멤버")||InventoryName.contains("교체"))
			PartyMemberInformationClick(event);
		return;
	}
	
	
	public void partyInventoryclick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
		case "파티 개설":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			player.closeInventory();
			player.sendMessage(ChatColor.GOLD + "/파티 생성 <이름>");
			break;
		case "파티 참여":
		case "파티 목록":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			PartyListGUI(player, (short) 0);
			break;
		case "파티 탈퇴":
			player.closeInventory();
			GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).QuitParty(player);
			break;
		case "파티 정보":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			player.closeInventory();
			PartyMemberInformation(player, (short) 0, GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),false);
			break;
		case "리더 변경":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			PartyMemberInformation(player, (short) 0, GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),true);
			break;
		case "인원 변경":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			player.closeInventory();	player.sendMessage(ChatColor.GOLD + "/파티 인원 [인원 수]");
			break;
		case "강퇴":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.8F);
			player.closeInventory();
			player.sendMessage(ChatColor.GOLD + "/파티 강퇴 [닉네임]");
			break;
		case "파티 잠금":
		case "파티 개방":
			GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).ChangeLock(player);
			PartyGUI_Main(player);
			break;
		case "다음 목록":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			break;
		case "이전 목록":
			s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
			EGUI.ETCGUI_Main(player);
			break;
		case "닫기":
			s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
			player.closeInventory();
			break;
		}
			return;
	}
	
	public void PartyListInventoryclick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "이전 페이지":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
				break;
			case "다음 페이지":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
				break;
			case "이전 목록":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyGUI_Main(player);
				break;
			case "닫기":
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			default:
				if(GoldBigDragon_RPG.Main.ServerOption.Party.get(Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1]))).getLock())
				{
				  s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		    	  player.sendMessage(ChatColor.RED + "[파티] : 해당 파티는 잠긴 상태입니다! 리더에게 문의하세요!");
				}
				else
				{
					GoldBigDragon_RPG.Main.ServerOption.Party.get(Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1]))).JoinParty(player);
					PartyGUI_Main(player);
				}
				return;
		}
		return;
	}

	public void PartyMemberInformationClick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();
		boolean isLeaderChange = false;
		if(event.getInventory().getTitle().contains("교체"))
			isLeaderChange = true;
			
		switch ((ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())))
		{
			case "이전 페이지":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyMemberInformation(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2),GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),isLeaderChange);
				break;
			case "다음 페이지":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyMemberInformation(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]),GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),isLeaderChange);
				break;
			case "이전 목록":
				s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
				PartyGUI_Main(player);
				break;
			case "닫기":
				s.SP(player, Sound.PISTON_RETRACT, 0.8F, 1.8F);
				player.closeInventory();
				break;
			default:
				if(event.isLeftClick())
				{
					if(isLeaderChange == false)
					{
						s.SP(player, Sound.ITEM_PICKUP, 0.8F, 1.0F);
						new EquipGUI().EquipWatchGUI(player, Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					}
					else
					{
						GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).ChangeLeader(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
				}
				else if(event.isRightClick()&&event.isShiftClick())
				{
					if(isLeaderChange == false)
					{
						GoldBigDragon_RPG.Main.ServerOption.Party.get(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player)).KickPartyMember(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						PartyMemberInformation(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.get(player),isLeaderChange);
					}
				}
				return;
		}
		return;
		
	}


}
