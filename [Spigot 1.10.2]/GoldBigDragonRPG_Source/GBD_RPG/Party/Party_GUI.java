package GBD_RPG.Party;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GBD_RPG.User.Equip_GUI;
import GBD_RPG.Util.Util_GUI;

public final class Party_GUI extends Util_GUI
{
	public void PartyGUI_Main(Player player)
	{
		String UniqueCode = "§0§0§4§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§0파티");
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 목록", 340,0,1,Arrays.asList(ChatColor.GRAY + "현재 개설된 파티 목록을 봅니다."), 12, inv);
		if(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.containsKey(player)==false)
		{
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 개설", 323,0,1,Arrays.asList(ChatColor.GRAY + "새로운 파티를 개설합니다."), 10, inv);
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 참여", 386,0,1,Arrays.asList(ChatColor.GRAY + "생성되어 있는 파티에 참여합니다."), 12, inv);
		}
		else
		{
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 정보", 397,3,1,Arrays.asList(ChatColor.GRAY + "현재 파티의 정보를 알아봅니다.",ChatColor.GRAY+"리더의 경우, 파티 멤버를",ChatColor.GRAY+"강퇴 시킬 수도 있습니다."), 10, inv);
			Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "파티 탈퇴", 52,0,1,Arrays.asList(ChatColor.GRAY + "파티에서 탈퇴합니다."), 14, inv);
			if(GBD_RPG.Main_Main.Main_ServerOption.Party.get(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player)).getLeader().equalsIgnoreCase(player.getName()) == true)
			{
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "리더 변경", 386,0,1,Arrays.asList(ChatColor.GRAY + "파티의 리더를 변경합니다."), 28, inv);
				Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "인원 변경", 386,0,1,Arrays.asList(ChatColor.GRAY + "제한 인원을 변경합니다."), 30, inv);
				if(GBD_RPG.Main_Main.Main_ServerOption.Party.get(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player)).getLock() == false) Stack2(ChatColor.BLUE +""+ ChatColor.BOLD + "파티 개방", 54,0,1,Arrays.asList(ChatColor.GRAY + "파티 가입 신청을 받습니다."), 34, inv);
				else Stack2(ChatColor.RED +""+ ChatColor.BOLD + "파티 잠금", 130,0,1,Arrays.asList(ChatColor.GRAY + "파티 가입 신청을 받지 않습니다."), 34, inv);
			}
		}
		Stack2(ChatColor.WHITE  + "" + ChatColor.BOLD + "이전 목록", 323,0,1,Arrays.asList(ChatColor.GRAY + "기타 창으로 돌아갑니다."), 36, inv);
		Stack2(ChatColor.WHITE +""+ ChatColor.BOLD + "닫기", 324,0,1,Arrays.asList(ChatColor.GRAY + "작업 관리자 창을 닫습니다."), 44, inv);
		player.openInventory(inv);
	}
	
	public void PartyListGUI(Player player, short page)
	{
		String UniqueCode = "§0§0§4§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0파티 목록 : " + (page+1));

		Object[] a = GBD_RPG.Main_Main.Main_ServerOption.Party.keySet().toArray();
		if(GBD_RPG.Main_Main.Main_ServerOption.Party.size() <= 0)
		{
			new GBD_RPG.Effect.Effect_Sound().SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[파티] : 생성된 파티가 없습니다!");
			player.sendMessage(ChatColor.GOLD + "/파티 생성 <이름>");
			return;
		}
		else
		{
			byte loc=0;
			for(int count = page*45; count < GBD_RPG.Main_Main.Main_ServerOption.Party.size();count++)
			{
				if(count > GBD_RPG.Main_Main.Main_ServerOption.Party.size() || loc >= 45) break;
				if(GBD_RPG.Main_Main.Main_ServerOption.Party.get(a[count]).getLock()==false)
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + GBD_RPG.Main_Main.Main_ServerOption.Party.get(a[count]).getTitle(), 54,0,1,Arrays.asList(ChatColor.GRAY + "파티 생성 시각 : "+ChatColor.DARK_GRAY+a[count],"",ChatColor.AQUA + "파티장 : "+ChatColor.GRAY+GBD_RPG.Main_Main.Main_ServerOption.Party.get(a[count]).getLeader(),ChatColor.AQUA + "인원 : "+ChatColor.GRAY + GBD_RPG.Main_Main.Main_ServerOption.Party.get(a[count]).getPartyMembers()+"/"+GBD_RPG.Main_Main.Main_ServerOption.Party.get(a[count]).getCapacity()), loc, inv);
				else
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + GBD_RPG.Main_Main.Main_ServerOption.Party.get(a[count]).getTitle(), 130,0,1,Arrays.asList(ChatColor.GRAY + "파티 생성 시각 : "+ChatColor.DARK_GRAY+a[count],"",ChatColor.AQUA + "파티장 : "+ChatColor.GRAY+GBD_RPG.Main_Main.Main_ServerOption.Party.get(a[count]).getLeader(),ChatColor.AQUA + "인원 : "+ChatColor.GRAY + GBD_RPG.Main_Main.Main_ServerOption.Party.get(a[count]).getPartyMembers()+"/"+GBD_RPG.Main_Main.Main_ServerOption.Party.get(a[count]).getCapacity(),"",ChatColor.RED + "[잠김]",ChatColor.RED + "파티 가입을 하시려면",ChatColor.RED +"파티장에게 문의하세요!"), loc, inv);
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
	
	public void PartyMemberInformationGUI(Player player, short page, long PartyCreateTime, boolean isLeaderChange)
	{
		Inventory inv = null;
		String UniqueCode = "§0§0§4§0§2§r";
		if(isLeaderChange == false)
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0파티 멤버 : " + (page+1));
		else
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0파티 리더 교체 : " + (page+1));
			
		Player[] Member = GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyCreateTime).getMember();
			byte loc=0;
			for(int count = page*45; count < Member.length;count++)
			{
				ItemStack ph = null;
				if(count > Member.length || loc >= 45) break;
				Damageable pl = Member[count];
				if(player.getName().equals(GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyCreateTime).getLeader()))
				{
					if(isLeaderChange == false)
					{
						if(!Member[count].getName().equals(GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyCreateTime).getLeader()))
							ph = getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.WHITE +"" + ChatColor.BOLD+ "[   일반 멤버   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ 좌 클릭시 장비 구경 ]",ChatColor.RED +"" + ChatColor.BOLD+ "[Shift + 우 클릭시 강퇴]"), Member[count].getName());
						else
							ph = getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[   파티 리더   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ 좌 클릭시 장비 구경 ]"), Member[count].getName());
					}
					else
					{
						if(!Member[count].getName().equals(GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyCreateTime).getLeader()))
							ph = getPlayerSkull(ChatColor.WHITE+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.WHITE +"" + ChatColor.BOLD+ "[   일반 멤버   ]","",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[ 좌 클릭시 리더 변경 ]"), Member[count].getName());
						else
							ph = getPlayerSkull(ChatColor.YELLOW+""+ChatColor.BOLD+Member[count].getName(), 1, Arrays.asList("",ChatColor.GRAY +""+ChatColor.BOLD+ "[   생명   ]",ChatColor.RED+""+ChatColor.BOLD+(int)pl.getHealth()+ChatColor.GRAY +""+ChatColor.BOLD+ " / "+ChatColor.RED+""+ChatColor.BOLD+(int)pl.getMaxHealth(),
									"",ChatColor.GRAY+""+ChatColor.BOLD+"[   위치   ]",ChatColor.DARK_AQUA+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"",ChatColor.YELLOW +"" + ChatColor.BOLD+ "[   파티 리더   ]","",ChatColor.RED +"" + ChatColor.BOLD+ "[ 리더 변경 불가능 ]"), Member[count].getName());
					}
				}
				else
				{
					if(!Member[count].getName().equals(GBD_RPG.Main_Main.Main_ServerOption.Party.get(PartyCreateTime).getLeader()))
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
	
	
	public void PartyGUI_MainClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();

		if(slot == 44)//나가기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 36)//이전 목록
				new GBD_RPG.User.ETC_GUI().ETCGUI_Main(player);
			else if(slot == 10)//파티 개설 / 파티 정보
			{
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).compareTo("파티 개설")==0)
				{
					player.closeInventory();
					player.sendMessage(ChatColor.GOLD + "/파티 생성 <이름>");
				}
				else
					PartyMemberInformationGUI(player, (short) 0, GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player),false);
			}
			else if(slot == 12)//파티 목록 / 파티 참여
				PartyListGUI(player, (short) 0);
			else if(slot == 14)//파티 탈퇴
			{
				player.closeInventory();
				GBD_RPG.Main_Main.Main_ServerOption.Party.get(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player)).QuitParty(player);
			}
			else if(slot == 28)//리더 변경
				PartyMemberInformationGUI(player, (short) 0, GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player),true);
			else if(slot == 30)//인원 변경
			{
				player.closeInventory();
				player.sendMessage(ChatColor.GOLD + "/파티 인원 [인원 수]");
			}
			else if(slot == 34)//인원 변경
			{
				GBD_RPG.Main_Main.Main_ServerOption.Party.get(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player)).ChangeLock(player);
				PartyGUI_Main(player);
			}
		}
	}
	
	public void PartyListGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		if(slot == 53)//닫기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				PartyGUI_Main(player);
			else if(slot == 48)//이전 페이지
				PartyListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//다음 페이지
				PartyListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				if(GBD_RPG.Main_Main.Main_ServerOption.Party.get(Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1]))).getLock())
				{
				  s.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		    	  player.sendMessage(ChatColor.RED + "[파티] : 해당 파티는 잠긴 상태입니다! 리더에게 문의하세요!");
				}
				else
				{
					GBD_RPG.Main_Main.Main_ServerOption.Party.get(Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1]))).JoinParty(player);
					PartyGUI_Main(player);
				}
			}
		}
	}

	public void PartyMemberInformationGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		if(slot == 53)//닫기
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			boolean isLeaderChange = event.getInventory().getTitle().contains("교체");
			if(slot == 45)//이전 목록
				PartyGUI_Main(player);
			else if(slot == 48)//이전 페이지
				PartyMemberInformationGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2),GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player),isLeaderChange);
			else if(slot == 50)//다음 페이지
				PartyMemberInformationGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]),GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player),isLeaderChange);
			else
			{
				if(event.isLeftClick())
				{
					if(isLeaderChange == false)
						new Equip_GUI().EquipWatchGUI(player, Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					else
						GBD_RPG.Main_Main.Main_ServerOption.Party.get(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player)).ChangeLeader(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
				else if(event.isRightClick()&&event.isShiftClick())
				{
					if(isLeaderChange == false)
					{
						GBD_RPG.Main_Main.Main_ServerOption.Party.get(GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player)).KickPartyMember(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						PartyMemberInformationGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),GBD_RPG.Main_Main.Main_ServerOption.PartyJoiner.get(player), isLeaderChange);
					}
				}
			}
		}
	}
}
