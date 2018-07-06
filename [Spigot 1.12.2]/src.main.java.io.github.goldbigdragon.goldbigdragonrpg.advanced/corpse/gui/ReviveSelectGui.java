package corpse.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import corpse.CorpseAPI;
import effect.SoundEffect;
import main.MainServerOption;
import util.GuiUtil;
import util.YamlLoader;

public class ReviveSelectGui extends GuiUtil{

	private String uniqueCode = "§0§0§9§0§0§r";
	private YamlLoader configYaml = new YamlLoader();
	
	public void openReviveSelectGui(Player player)
	{
		if(!player.getOpenInventory().getTitle().equals("§0§0§9§0§0§r§0행동불능 상태 -부활 방법 선택-"))
		{
			Inventory inv = Bukkit.createInventory(null, 27, uniqueCode + "§0행동불능 상태 -부활 방법 선택-");
		  	configYaml.getConfig("config.yml");

		  	setNormal("Death.Spawn_Home.SetHealth", "100%");
		  	setNormal("Death.Spawn_Home.PenaltyEXP", "10%");
		  	setNormal("Death.Spawn_Home.PenaltyMoney", "0%");

		  	setNormal("Death.Spawn_Here.SetHealth", "1%");
		  	setNormal("Death.Spawn_Here.PenaltyEXP", "15%");
		  	setNormal("Death.Spawn_Here.PenaltyMoney", "10%");
		  	
		  	setNormal("Death.Spawn_Help.SetHealth", "1%");
		  	setNormal("Death.Spawn_Help.PenaltyEXP", "5%");
		  	setNormal("Death.Spawn_Help.PenaltyMoney", "0%");
		  	
		  	setNormal("Death.Spawn_Item.SetHealth", "100%");
		  	setNormal("Death.Spawn_Item.PenaltyEXP", "0%");
		  	setNormal("Death.Spawn_Item.PenaltyMoney", "0%");
		  	
		  	try
		  	{
				if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited()==null||main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited().equals("null"))
					removeFlagStack("§6§l[가까운 마을에서 부활]", 345,0,1,Arrays.asList("§7최근 방문한 마을이 없습니다.","§7이 방법을 선택할 경우,","§8"+ player.getLocation().getWorld().getName()+"월드에 설정된","§7기본 스폰 지점에서 부활합니다.","","§a + "+configYaml.getString("Death.Spawn_Home.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Home.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Home.PenaltyMoney")+" 감소"), 10, inv);
				else
					removeFlagStack("§6§l[가까운 마을에서 부활]", 345,0,1,Arrays.asList("§e"+ main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited()+"§7에서 부활합니다.","","§a + "+configYaml.getString("Death.Spawn_Home.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Home.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Home.PenaltyMoney")+" 감소"), 10, inv);
		  	}
		  	catch(NullPointerException e)
		  	{
		  		removeFlagStack("§6§l[가까운 마을에서 부활]", 345,0,1,Arrays.asList("§7최근 방문한 마을이 없습니다.","§7이 방법을 선택할 경우,","§8"+ player.getLocation().getWorld().getName()+"월드에 설정된","§7기본 스폰 지점에서 부활합니다.","","§a + "+configYaml.getString("Death.Spawn_Home.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Home.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Home.PenaltyMoney")+" 감소"), 10, inv);
		  	}
			if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP()<0)
				removeFlagStack("§c§l[다시 일어선다]", 166,0,1,Arrays.asList("§7경험치가 부족하여 제자리","§7부활이 불가능 합니다."), 12, inv);
			else if(configYaml.getBoolean("Death.DistrictDirectRevive"))
				removeFlagStack("§c§l[다시 일어선다]", 166,0,1,Arrays.asList("§7제자리 부활이 불가능합니다."), 12, inv);
			else
				removeFlagStack("§c§l[다시 일어선다]", 2266,0,1,Arrays.asList("§7아픔을 참고 다시 일어섭니다.","§7좋은 상태는 기대하기 힘듭니다.","","§a + "+configYaml.getString("Death.Spawn_Here.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Here.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Here.PenaltyMoney")+" 감소"), 12, inv);
			
			ItemStack item = configYaml.getItemStack("Death.RescueItem");
			
			if(item == null)
				removeFlagStack("§c§l[구조를 기다린다]", 397,3,1,Arrays.asList("§7다른 사람의 도움을 요청합니다.","§7주위에 사람이 있는지 살펴보세요.","","§a + "+configYaml.getString("Death.Spawn_Help.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Help.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Help.PenaltyMoney")+" 감소"), 14, inv);
			else
				removeFlagStack("§c§l[구조를 기다린다]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList("§7다른 사람의 도움을 요청합니다.","§7주위에 사람이 있는지 살펴보세요.","","§a + "+configYaml.getString("Death.Spawn_Help.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Help.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Help.PenaltyMoney")+" 감소"), 14, inv);
			
			item = configYaml.getItemStack("Death.ReviveItem");
			if(item == null)
				removeFlagStack("§3§l[부활석 사용]", 399,0,1,Arrays.asList("§7제자리 부활 아이템을 사용합니다.","","§a + "+configYaml.getString("Death.Spawn_Item.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Item.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Item.PenaltyMoney")+" 감소"), 16, inv);
			else if(!item.hasItemMeta())
				removeFlagStack("§3§l[부활석 사용]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList("§7제자리 부활 아이템을 사용합니다.","","§a + "+configYaml.getString("Death.Spawn_Item.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Item.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Item.PenaltyMoney")+" 감소"), 16, inv);
			else if(!item.getItemMeta().hasDisplayName())
				removeFlagStack("§3§l[부활석 사용]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList("§7제자리 부활 아이템을 사용합니다.","","§a + "+configYaml.getString("Death.Spawn_Item.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Item.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Item.PenaltyMoney")+" 감소"), 16, inv);
			else
				removeFlagStack("§3§l["+item.getItemMeta().getDisplayName()+"§3§l 사용]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList("§7제자리 부활 아이템을 사용합니다.","","§a + "+configYaml.getString("Death.Spawn_Item.SetHealth")+" 생명력","§c - 경험치 "+configYaml.getString("Death.Spawn_Item.PenaltyEXP")+" 감소","§c - 소지금 "+configYaml.getString("Death.Spawn_Item.PenaltyMoney")+" 감소"), 16, inv);
			player.openInventory(inv);	
		}
	}
	
	private void setNormal(String path, String normalValue)
	{
	  	if(configYaml.getString(path)==null)
	  	{
  			configYaml.set(path, normalValue);
  			configYaml.saveConfig();
  			return;
	  	}
	  	if(configYaml.getString(path).contains("%"))
	  	{
	  		try
	  		{
	  			byte value = Byte.parseByte(configYaml.getString(path).replace("%", ""));
	  			if(value > 100||value<0)
	  			{
		  			configYaml.set(path, normalValue);
		  			configYaml.saveConfig();
	  			}
	  		}
	  		catch(Exception e)
	  		{
	  			configYaml.set(path, normalValue);
	  			configYaml.saveConfig();
	  		}
	  	}
	}
	
	public void reviveSelectClick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		CorpseAPI capi = new CorpseAPI();
		if(slot == 10)//마을에서 부활
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			capi.reviveAtLastVisitedArea(player);
			capi.removeCorpse(player.getName());
    		new otherplugins.NoteBlockApiMain().Stop(player);
		}
		else if(slot == 12)//제자리에서 부활
		{
			if(event.getCurrentItem().getTypeId()==166)
			{
				SoundEffect.playSound(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
				return;
			}
			else
			{
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				capi.removeCorpse(player.getName());
				capi.reviveAtDeadPoint(player);
	    		new otherplugins.NoteBlockApiMain().Stop(player);
			}
		}
		else if(slot == 14)//도움 요청
		{
			if(MainServerOption.partyJoiner.containsKey(player))
			{
				Player[] partyMember = MainServerOption.party.get(MainServerOption.partyJoiner.get(player)).getMember();
				for(int count = 0; count < partyMember.length; count++)
					if(player != partyMember[count])
					{
						SoundEffect.playSound(partyMember[count], Sound.ENTITY_VILLAGER_DEATH, 0.4F, 0.5F);
						partyMember[count].sendMessage("§d[구조 요청] : §e"+player.getName()+"§d님으로 부터 구조 요청이 들어왔습니다! (월드 : "+player.getLocation().getWorld().getName() + ", XYZ : " + (int)(player.getLocation().getX())+","+(int)(player.getLocation().getY())+","+(int)(player.getLocation().getZ())+")");
					}
				SoundEffect.playSound(player, Sound.ENTITY_WITHER_SKELETON_STEP, 1.0F, 1.0F);
				player.sendMessage("§d[구조 요청] : 파티 멤버들에게 구조 요청 신호를 보냈습니다!");
			}
			else
			{
			  	YamlLoader friendYaml = new YamlLoader();
				friendYaml.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
				if( ! friendYaml.contains("Name"))
				{
					SoundEffect.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
					player.sendMessage("§d[구조 요청] : 구조 요청 신호를 보낼 수 있는 친구가 없습니다!");
				}
				else
				{
					String[] friendsList = friendYaml.getConfigurationSection("Friends").getKeys(false).toArray(new String[0]);
					if(friendsList.length == 0)
					{
						SoundEffect.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
						player.sendMessage("§d[구조 요청] : 구조 요청 신호를 보낼 수 있는 친구가 없습니다!");
					}
					else
					{
						boolean exitFriend = false;
						for(int count = 0; count < friendsList.length; count++)
						{
							Player friend = Bukkit.getPlayer(friendsList[count]);
							if(friend!=null)
								if(friend.isOnline())
								{
									SoundEffect.playSound(friend, Sound.ENTITY_VILLAGER_DEATH, 0.4F, 0.5F);
									friend.sendMessage("§d[구조 요청] : §e"+player.getName()+"§d님으로 부터 구조 요청이 들어왔습니다! (월드 : "+player.getLocation().getWorld().getName() + ", XYZ : " + (int)(player.getLocation().getX())+","+(int)(player.getLocation().getY())+","+(int)(player.getLocation().getZ())+")");
									exitFriend = true;
								}
						}
						if(exitFriend)
						{
							SoundEffect.playSound(player, Sound.ENTITY_SKELETON_STEP, 1.0F, 1.0F);
							player.sendMessage("§d[구조 요청] : 접속한 친구들에게 구조 요청 신호를 보냈습니다!");
						}
						else
						{
							SoundEffect.playSound(player, Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
							player.sendMessage("§d[구조 요청] : 구조 요청 신호를 보낼 수 있는 친구가 없습니다!");
						}
					}
				}
			}
		}
		else if(slot == 16)//부활석 사용
		{
			configYaml.getConfig("config.yml");
			ItemStack item = configYaml.getItemStack("Death.ReviveItem");
			if(item == null)
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c[SYSTEM] : 부활 아이템이 없어 부활할 수 없습니다!");
				openReviveSelectGui(player);
			}
			else
			{
				if(new util.PlayerUtil().deleteItem(player, item, item.getAmount()))
				{
					capi.removeCorpse(player.getName());
					player.setGameMode(GameMode.SURVIVAL);
					player.closeInventory();
					Location l = player.getLocation();
					l.add(0, 1, 0);
					player.teleport(l);
					for(int countta=0;countta<210;countta++)
						new effect.ParticleEffect().PL(player.getLocation(), org.bukkit.Effect.SMOKE, new util.NumericUtil().RandomNum(0, 14));
					SoundEffect.playSoundLocation(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.5F, 1.8F);
		    		new otherplugins.NoteBlockApiMain().Stop(player);
		    		capi.penalty(player, configYaml.getString("Death.Spawn_Item.SetHealth"), configYaml.getString("Death.Spawn_Item.PenaltyEXP"), configYaml.getString("Death.Spawn_Item.PenaltyMoney"));
					return;
				}
				else
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[SYSTEM] : 부활 아이템이 부족하여 부활할 수 없습니다!");
					return;
				}
			}
		}
		player.closeInventory();
		return;
	}
	
}
