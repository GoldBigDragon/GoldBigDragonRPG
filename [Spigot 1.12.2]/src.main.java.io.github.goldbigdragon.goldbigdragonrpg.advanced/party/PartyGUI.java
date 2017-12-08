package party;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import effect.SoundEffect;
import user.EquipGui;
import util.UtilGui;

public final class PartyGUI extends UtilGui
{
	public void PartyGUI_Main(Player player)
	{
		String UniqueCode = "§0§0§4§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "§0파티");
		removeFlagStack("§f§l파티 목록", 340,0,1,Arrays.asList("§7현재 개설된 파티 목록을 봅니다."), 12, inv);
		if(main.MainServerOption.partyJoiner.containsKey(player)==false)
		{
			removeFlagStack("§f§l파티 개설", 323,0,1,Arrays.asList("§7새로운 파티를 개설합니다."), 10, inv);
			removeFlagStack("§f§l파티 참여", 386,0,1,Arrays.asList("§7생성되어 있는 파티에 참여합니다."), 12, inv);
		}
		else
		{
			removeFlagStack("§f§l파티 정보", 397,3,1,Arrays.asList("§7현재 파티의 정보를 알아봅니다.","§7리더의 경우, 파티 멤버를","§7강퇴 시킬 수도 있습니다."), 10, inv);
			removeFlagStack("§f§l파티 탈퇴", 52,0,1,Arrays.asList("§7파티에서 탈퇴합니다."), 14, inv);
			if(main.MainServerOption.party.get(main.MainServerOption.partyJoiner.get(player)).getLeader().equalsIgnoreCase(player.getName()) == true)
			{
				removeFlagStack("§f§l리더 변경", 386,0,1,Arrays.asList("§7파티의 리더를 변경합니다."), 28, inv);
				removeFlagStack("§f§l인원 변경", 386,0,1,Arrays.asList("§7제한 인원을 변경합니다."), 30, inv);
				if(main.MainServerOption.party.get(main.MainServerOption.partyJoiner.get(player)).getLock() == false) removeFlagStack("§9§l파티 개방", 54,0,1,Arrays.asList("§7파티 가입 신청을 받습니다."), 34, inv);
				else removeFlagStack("§c§l파티 잠금", 130,0,1,Arrays.asList("§7파티 가입 신청을 받지 않습니다."), 34, inv);
			}
		}
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7기타 창으로 돌아갑니다."), 36, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7작업 관리자 창을 닫습니다."), 44, inv);
		player.openInventory(inv);
	}
	
	public void PartyListGUI(Player player, short page)
	{
		String UniqueCode = "§0§0§4§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0파티 목록 : " + (page+1));

		Object[] a = main.MainServerOption.party.keySet().toArray();
		if(main.MainServerOption.party.size() <= 0)
		{
			SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage("§c[파티] : 생성된 파티가 없습니다!");
			player.sendMessage("§6/파티 생성 <이름>");
			return;
		}
		else
		{
			byte loc=0;
			for(int count = page*45; count < main.MainServerOption.party.size();count++)
			{
				if(count > main.MainServerOption.party.size() || loc >= 45) break;
				if(main.MainServerOption.party.get(a[count]).getLock()==false)
					removeFlagStack("§f§l" + main.MainServerOption.party.get(a[count]).getTitle(), 54,0,1,Arrays.asList("§7파티 생성 시각 : "+ChatColor.DARK_GRAY+a[count],"","§b파티장 : §8"+main.MainServerOption.party.get(a[count]).getLeader(),"§b인원 : §8"+ main.MainServerOption.party.get(a[count]).getPartyMembers()+"/"+main.MainServerOption.party.get(a[count]).getCapacity()), loc, inv);
				else
					removeFlagStack("§f§l" + main.MainServerOption.party.get(a[count]).getTitle(), 130,0,1,Arrays.asList("§7파티 생성 시각 : "+ChatColor.DARK_GRAY+a[count],"","§b파티장 : §8"+main.MainServerOption.party.get(a[count]).getLeader(),"§b인원 : §8"+ main.MainServerOption.party.get(a[count]).getPartyMembers()+"/"+main.MainServerOption.party.get(a[count]).getCapacity(),"","§c[잠김]","§c파티 가입을 하시려면","§c파티장에게 문의하세요!"), loc, inv);
				loc++;
			}
		}
		
		if(a.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void PartyMemberInformationGUI(Player player, short page, long PartyCreateTime, boolean isLeaderChange)
	{
		Inventory inv = null;
		String UniqueCode = "§0§0§4§0§2§r";
		if(!isLeaderChange)
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0파티 멤버 : " + (page+1));
		else
			inv = Bukkit.createInventory(null, 54, UniqueCode + "§0파티 리더 교체 : " + (page+1));
			
		Player[] Member = main.MainServerOption.party.get(PartyCreateTime).getMember();
			byte loc=0;
			for(int count = page*45; count < Member.length;count++)
			{
				ItemStack ph = null;
				if(count > Member.length || loc >= 45) break;
				Damageable pl = Member[count];
				if(player.getName().equals(main.MainServerOption.party.get(PartyCreateTime).getLeader()))
				{
					if(!isLeaderChange)
					{
						if(!Member[count].getName().equals(main.MainServerOption.party.get(PartyCreateTime).getLeader()))
							ph = getPlayerSkull("§f§l"+Member[count].getName(), 1, Arrays.asList("","§7§l[   생명   ]","§c§l"+(int)pl.getHealth()+"§7§l / §c§l"+(int)pl.getMaxHealth(),
									"","§7§l[   위치   ]","§3"+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"","§f§l[   일반 멤버   ]","","§e§l[ 좌 클릭시 장비 구경 ]","§c§l[Shift + 우 클릭시 강퇴]"), Member[count].getName());
						else
							ph = getPlayerSkull("§e§l"+Member[count].getName(), 1, Arrays.asList("","§7§l[   생명   ]","§c§l"+(int)pl.getHealth()+"§7§l / §c§l"+(int)pl.getMaxHealth(),
									"","§7§l[   위치   ]","§3"+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"","§e§l[   파티 리더   ]","","§e§l[ 좌 클릭시 장비 구경 ]"), Member[count].getName());
					}
					else
					{
						if(!Member[count].getName().equals(main.MainServerOption.party.get(PartyCreateTime).getLeader()))
							ph = getPlayerSkull("§f§l"+Member[count].getName(), 1, Arrays.asList("","§7§l[   생명   ]","§c§l"+(int)pl.getHealth()+"§7§l / §c§l"+(int)pl.getMaxHealth(),
									"","§7§l[   위치   ]","§3"+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"","§f§l[   일반 멤버   ]","","§e§l[ 좌 클릭시 리더 변경 ]"), Member[count].getName());
						else
							ph = getPlayerSkull("§e§l"+Member[count].getName(), 1, Arrays.asList("","§7§l[   생명   ]","§c§l"+(int)pl.getHealth()+"§7§l / §c§l"+(int)pl.getMaxHealth(),
									"","§7§l[   위치   ]","§3"+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"","§e§l[   파티 리더   ]","","§c§l[ 리더 변경 불가능 ]"), Member[count].getName());
					}
				}
				else
				{
					if(!Member[count].getName().equals(main.MainServerOption.party.get(PartyCreateTime).getLeader()))
						ph = getPlayerSkull("§f§l"+Member[count].getName(), 1, Arrays.asList("","§7§l[   생명   ]","§c§l"+(int)pl.getHealth()+"§7§l / §c§l"+(int)pl.getMaxHealth(),
								"","§7§l[   위치   ]","§3"+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"","§f§l[   일반 멤버   ]","","§e§l[ 좌 클릭시 장비 구경 ]"), Member[count].getName());
					else
						ph = getPlayerSkull("§e§l"+Member[count].getName(), 1, Arrays.asList("","§7§l[   생명   ]","§c§l"+(int)pl.getHealth()+"§7§l / §c§l"+(int)pl.getMaxHealth(),
								"","§7§l[   위치   ]","§3"+pl.getLocation().getWorld().getName()+" "+(int)pl.getLocation().getX()+","+(int)pl.getLocation().getY()+","+(int)pl.getLocation().getZ(),"","§e§l[   파티 리더   ]","","§e§l[ 좌 클릭시 장비 구경 ]"), Member[count].getName());
				}

				stackItem(ph, loc, inv);
				loc++;
			}
		if(Member.length-(page*44)>45)
			removeFlagStack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);
		
		removeFlagStack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		removeFlagStack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void PartyGUI_MainClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		

		if(slot == 44)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 36)//이전 목록
				new user.EtcGui().ETCGUI_Main(player);
			else if(slot == 10)//파티 개설 / 파티 정보
			{
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("파티 개설"))
				{
					player.closeInventory();
					player.sendMessage("§6/파티 생성 <이름>");
				}
				else
					PartyMemberInformationGUI(player, (short) 0, main.MainServerOption.partyJoiner.get(player),false);
			}
			else if(slot == 12)//파티 목록 / 파티 참여
				PartyListGUI(player, (short) 0);
			else if(slot == 14)//파티 탈퇴
			{
				player.closeInventory();
				main.MainServerOption.party.get(main.MainServerOption.partyJoiner.get(player)).QuitParty(player);
			}
			else if(slot == 28)//리더 변경
				PartyMemberInformationGUI(player, (short) 0, main.MainServerOption.partyJoiner.get(player),true);
			else if(slot == 30)//인원 변경
			{
				player.closeInventory();
				player.sendMessage("§6/파티 인원 [인원 수]");
			}
			else if(slot == 34)//인원 변경
			{
				main.MainServerOption.party.get(main.MainServerOption.partyJoiner.get(player)).ChangeLock(player);
				PartyGUI_Main(player);
			}
		}
	}
	
	public void PartyListGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		
		if(slot == 53)//닫기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				PartyGUI_Main(player);
			else if(slot == 48)//이전 페이지
				PartyListGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//다음 페이지
				PartyListGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]));
			else
			{
				if(main.MainServerOption.party.get(Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1]))).getLock())
				{
				  SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
		    	  player.sendMessage("§c[파티] : 해당 파티는 잠긴 상태입니다! 리더에게 문의하세요!");
				}
				else
				{
					main.MainServerOption.party.get(Long.parseLong(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0).split(" : ")[1]))).JoinParty(player);
					PartyGUI_Main(player);
				}
			}
		}
	}

	public void PartyMemberInformationGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 53)//닫기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			boolean isLeaderChange = event.getInventory().getTitle().contains("교체");
			if(slot == 45)//이전 목록
				PartyGUI_Main(player);
			else if(slot == 48)//이전 페이지
				PartyMemberInformationGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-2),main.MainServerOption.partyJoiner.get(player),isLeaderChange);
			else if(slot == 50)//다음 페이지
				PartyMemberInformationGUI(player,(short) Integer.parseInt(event.getInventory().getTitle().split(" : ")[1]),main.MainServerOption.partyJoiner.get(player),isLeaderChange);
			else
			{
				if(event.isLeftClick())
				{
					if(!isLeaderChange)
						new EquipGui().EquipWatchGUI(player, Bukkit.getServer().getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
					else
						main.MainServerOption.party.get(main.MainServerOption.partyJoiner.get(player)).ChangeLeader(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				}
				else if(event.isRightClick()&&event.isShiftClick())
				{
					if(!isLeaderChange)
					{
						main.MainServerOption.party.get(main.MainServerOption.partyJoiner.get(player)).KickPartyMember(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						PartyMemberInformationGUI(player,(short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),main.MainServerOption.partyJoiner.get(player), isLeaderChange);
					}
				}
			}
		}
	}
}
