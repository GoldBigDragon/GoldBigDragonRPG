package event;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.*;

import area.gui.AreaBlockItemSettingGui;
import customitem.CustomItemAPI;
import effect.SoundEffect;
import net.citizensnpcs.api.CitizensAPI;
import user.UserDataObject;
import util.YamlLoader;

public class EventInteract
{
	//블럭 우클/좌클 할 때//
	public void PlayerInteract(PlayerInteractEvent event)
	{
		if(event.getHand()==EquipmentSlot.HAND)
		{
			if(new corpse.CorpseAPI().deathCapture(event.getPlayer(),false))
				return;
			if(event.getAction()==Action.RIGHT_CLICK_AIR||event.getAction()==Action.RIGHT_CLICK_BLOCK)
				ClickTrigger(event);
			if(event.getPlayer().getInventory().getItemInMainHand()!=null&&event.getPlayer().isOp())
				AreaChecker(event);

			if(event.getPlayer().isOp())
				OPwork(event);

			if(event.getPlayer().getWorld().getName().equals("Dungeon"))
			{
				new dungeon.DungeonMain().dungeonInteract(event);
				return;
			}

			if(event.getAction()==Action.RIGHT_CLICK_BLOCK&&event.getClickedBlock()!=null)
			{
				short id = (short) event.getClickedBlock().getTypeId();
				if(id==54||id==58||id==61||id==84||id==116||id==120||id==130||id==145||id==146
					||id==321||id==355||id==389||id==416||id==23||id==25||id==84||id==69||id==84
					||id==46||id==77||id==84||id==96||id==107||id==143||id==151||id==154||id==158
					||id==167||id==84||(id>=183&&id<=187)||id==324||id==330||id==356||id==404||(id>=427&&id<=431)
					||id==138)
				{
					area.AreaAPI A = new area.AreaAPI();
					String[] Area = A.getAreaName(event.getClickedBlock());
					if(Area != null)
					{
						if(A.getAreaOption(Area[0], (char) 7) == false && event.getPlayer().isOp() == false)
						{
							event.setCancelled(true);
							SoundEffect.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
							event.getPlayer().sendMessage("§c[SYSTEM] : §e"+ Area[1] + "§c 지역에 있는 블록은 손 댈 수없습니다!");
							return;
						}
					}
				}
				if(event.getItem()!=null)
				if(event.getItem().getTypeId()>=325&&event.getItem().getTypeId()<=327)
				{
					area.AreaAPI A = new area.AreaAPI();
					String[] Area = A.getAreaName(event.getClickedBlock());
					if(Area != null)
					{
						if(A.getAreaOption(Area[0], (char) 7) == false && event.getPlayer().isOp() == false)
						{
							event.setCancelled(true);
							SoundEffect.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
							event.getPlayer().sendMessage("§c[SYSTEM] : §e"+ Area[1] + "§c 지역에서는 양동이를 사용할 수없습니다!");
							return;
						}
					}
				}
			}
			return;
		}
	}
	
	//NPC 및 액자
	public void PlayerInteractEntity (PlayerInteractEntityEvent event)
	{
		if(event.getHand()==EquipmentSlot.HAND)
		{
			Entity target = event.getRightClicked();
			Player player = event.getPlayer();

			if(target.getType() == EntityType.PLAYER)
			{
				if(main.MainServerOption.PlayerList.get(player.getUniqueId().toString()).isOption_SeeInventory())
				{
					Player t = (Player)target;
					if(t.isOnline())
					{
						user.EquipGui EGUI = new user.EquipGui();
						EGUI.EquipWatchGUI(player, t);
						return;
					}
				}
			}
		    if(player.isOp())
		    {
		    	String type = new UserDataObject().getType(player);
		    	if(type!=null && new UserDataObject().getType(player).equals("Quest"))
	    			new quest.QuestInteractEvent().EntityInteract(event, type);
		    }

			String[] area = new area.AreaAPI().getAreaName(target);
			if(area != null)
			{
				if( ! new area.AreaAPI().getAreaOption(area[0], (char) 7) && ! event.getPlayer().isOp())
				{
					if(target.getCustomName() == null || CitizensAPI.getNPCRegistry().getNPC(target) == null)
					{
						event.setCancelled(true);
						SoundEffect.playSound(event.getPlayer(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
						event.getPlayer().sendMessage("§c[SYSTEM] : §e"+ area[1] + "§c 지역에 있는 엔티티는 손 댈 수없습니다!");
						return;
					}
				}
			}
		    return;
		}
	}
	
	public void ClickTrigger(PlayerInteractEvent event)
	{
		if(event.getPlayer().getInventory().getItemInMainHand()!=null)
			ItemUse(event);
		if(event.getClickedBlock()!=null)
			SlotMachine(event);
	}
	
	private void OPwork(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		UserDataObject u = new UserDataObject();
		if(u.getType(player) != null)
		{
			if(u.getType(player).equals("Quest"))
				new quest.QuestInteractEvent().BlockInteract(event);
			else if(u.getType(player).equals("Area"))
			    OPwork_Area(event);
			else if(u.getType(player).equals("Gamble"))
			    OPwork_Gamble(event);
		}
		return;
	}
	
	private void ItemUse(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if(player.getInventory().getItemInMainHand()!=null)
		if(player.getInventory().getItemInMainHand().hasItemMeta())
		{
			if(player.getInventory().getItemInMainHand().getItemMeta().hasLore())
			{
				int itemID = player.getInventory().getItemInMainHand().getData().getItemTypeId();
				if(itemID == 383 && event.getItem().getData().getData() == 0 && event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
	    			if(main.MainServerOption.MonsterList.containsKey(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName())))
	    			{
	    				if(player.isOp())
	    					new monster.MonsterSpawn().SpawnMob(event.getClickedBlock().getLocation(), ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()), (byte)-1, null, (char) -1, false);
	    				else
	    				{
	    					SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	    			    	player.sendMessage("§c[SYSTEM] : 몬스터 스폰 권한이 없습니다!");
	    				}
	    			}
					else
					{
						SoundEffect.playSound(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				    	player.sendMessage("§c[SYSTEM] : 해당 이름의 몬스터가 존재하지 않습니다!");
					}
			    	return;
				}
				//지도 사용
				else if(itemID == 395 || itemID == 358)
				{
					Object[] lore = player.getInventory().getItemInMainHand().getItemMeta().getLore().toArray();
					for(int counter = 0; counter < lore.length; counter ++)
						if(lore[counter].toString().contains("내용"))
						{
							UserDataObject u = new UserDataObject();
							//지도에 이미지 넣는 작업
							if(player.isOp())
							{
								u.setType(player, "Map");
								u.setString(player, (byte)1, ChatColor.stripColor(lore[counter].toString()).split(" : ")[1]);
								main.MainServerOption.Mapping = true;
								return;
							}
						}
				}
				String LoreString = player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).toString();
				if(LoreString.contains("[귀환서]")||LoreString.contains("[주문서]")||
				   LoreString.contains("[스킬북]")||LoreString.contains("[소비]")||
				   LoreString.contains("[돈]")||LoreString.contains("[공구]"))
				{
					event.setCancelled(true);
					if(LoreString.contains("[소비]"))
						new CustomItemAPI().useAbleItemUse(player, "소비");
					else if(LoreString.contains("[귀환서]"))
						new CustomItemAPI().useAbleItemUse(player, "귀환서");
					else if(LoreString.contains("[주문서]"))
						new CustomItemAPI().useAbleItemUse(player, "주문서");
					else if(LoreString.contains("[스킬북]"))
						new CustomItemAPI().useAbleItemUse(player, "스킬북");
					else if(LoreString.contains("[돈]"))
						new CustomItemAPI().useAbleItemUse(player, "돈");
					else if(LoreString.contains("[공구]"))
						new CustomItemAPI().useAbleItemUse(player, "공구");
					return;
				}
			}
		}
		return;
	}
	
	private void SlotMachine(PlayerInteractEvent event)
	{
		Block block = event.getClickedBlock();
	  	YamlLoader gambleYaml = new YamlLoader();
		gambleYaml.getConfig("ETC/SlotMachine.yml");
		String BlockLocation = block.getLocation().getWorld().getName()+"_"+(int)block.getLocation().getX()+","+(short)block.getLocation().getY()+","+(int)block.getLocation().getZ();
		if(gambleYaml.contains(BlockLocation))
		{
			event.setCancelled(true);
			new admin.GambleGui().slotMachinePlayGui(event.getPlayer(), BlockLocation);
		}
		return;
	}
	
	
	private void OPwork_Area(PlayerInteractEvent event)
	{
		event.setCancelled(true);
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
	  	YamlLoader areaYaml = new YamlLoader();
		areaYaml.getConfig("Area/AreaList.yml");
		
		UserDataObject u = new UserDataObject();

		String AreaName = u.getString(player, (byte)2);
		if(event.getAction()==Action.LEFT_CLICK_BLOCK)
		{
			if(u.getString(player, (byte)3).equals("ANBI"))
			{
				String BlockData = block.getTypeId()+":"+block.getData();
				ItemStack item = new MaterialData(block.getTypeId(), (byte) block.getData()).toItemStack(1);
				areaYaml.set(AreaName+".Mining."+BlockData+".100",item);
				areaYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_HORSE_SADDLE, 1.0F, 1.8F);
				new AreaBlockItemSettingGui().areaBlockItemSettingGui(player, AreaName, BlockData);
		    	u.clearAll(player);
			}
		}
		else if(event.getAction()==Action.RIGHT_CLICK_BLOCK)
		{
			if(u.getString(player, (byte)3).equals("MLS"))//MonsterLocationSetting
			{
				String count = u.getString(player, (byte)1);
				areaYaml.set(AreaName+".MonsterSpawnRule."+count+".loc.world", block.getLocation().getWorld().getName());
				areaYaml.set(AreaName+".MonsterSpawnRule."+count+".loc.x", (int)block.getLocation().getX());
				areaYaml.set(AreaName+".MonsterSpawnRule."+count+".loc.y", (short)block.getLocation().getY()+1);
				areaYaml.set(AreaName+".MonsterSpawnRule."+count+".loc.z", (int)block.getLocation().getZ());
				areaYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F);
		    	u.clearAll(player);
				u.setType(player, "Area");
				u.setString(player, (byte)1, count);
				u.setString(player, (byte)2, "AMSC");
				u.setString(player, (byte)3, AreaName);
				player.sendMessage("§a[영역] : 한 번에 몇 마리 씩 스폰 할까요?");
				player.sendMessage("§e(최소 1마리 ~ 최대 100마리)");
			}
		}
		return;
	}
	
	private void OPwork_Gamble(PlayerInteractEvent event)
	{
		event.setCancelled(true);
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
	  	YamlLoader gambleYaml = new YamlLoader();
		gambleYaml.getConfig("ETC/SlotMachine.yml");
		
		UserDataObject u = new UserDataObject();

		String AreaName = u.getString(player, (byte)2);
		if(event.getAction()==Action.LEFT_CLICK_BLOCK)
		{
			/*
			if(u.getString(player, (byte)3).equals("ANBI"))
			{
				String BlockData = block.getTypeId()+":"+block.getData();
				ItemStack item = new MaterialData(block.getTypeId(), (byte) block.getData()).toItemStack(1);
				AreaConfig.set(AreaName+".Mining."+BlockData,item);
				AreaConfig.saveConfig();
				GoldBigDragon_RPG.GUI.AreaGUI AGUI = new GoldBigDragon_RPG.GUI.AreaGUI();
				new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				AGUI.AreaBlockItemSettingGUI(player, AreaName, BlockData);
		    	u.clearAll(player);
			}
			*/
		}
		else if(event.getAction()==Action.RIGHT_CLICK_BLOCK)
		{
			if(u.getString(player, (byte)0).equals("NSM"))//NewSlotMachine
			{
				String Name = block.getLocation().getWorld().getName()+"_"+(int)block.getLocation().getX()+","+(short)block.getLocation().getY()+","+(int)block.getLocation().getZ();
				if(gambleYaml.contains(Name))
				{
					SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage("§c[도박] : 해당 블록에는 이미 다른 도박 기기가 설치되어 있습니다!");
					return;
				}
				gambleYaml.set(Name+".0", "null");
				gambleYaml.set(Name+".1", "null");
				gambleYaml.set(Name+".2", "null");
				gambleYaml.set(Name+".3", "null");
				gambleYaml.set(Name+".4", "null");
				gambleYaml.set(Name+".5", "null");
				gambleYaml.set(Name+".6", "null");
				gambleYaml.set(Name+".8", "null");
				gambleYaml.set(Name+".9", "null");
				gambleYaml.set(Name+".10", "null");
				gambleYaml.set(Name+".11", "null");
				gambleYaml.set(Name+".12", "null");
				gambleYaml.set(Name+".13", "null");
				gambleYaml.set(Name+".14", "null");
				gambleYaml.set(Name+".15", "null");
				gambleYaml.saveConfig();
				SoundEffect.playSound(player, Sound.ENTITY_IRONGOLEM_DEATH, 1.0F, 1.8F);
		    	u.clearAll(player);
				player.sendMessage("§a[도박] : 기계가 설치 되었습니다!");
				new admin.GambleGui().slotMachineDetailGUI(player, Name);
			}
		}
		return;
	}
	
	private void AreaChecker(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");
		if(player.getInventory().getItemInMainHand().getTypeId() == configYaml.getInt("Server.AreaSettingWand"))
		{
			event.setCancelled(true);
			if(event.getAction()==Action.LEFT_CLICK_BLOCK)
			{
				main.MainServerOption.catchedLocation1.put(player, block.getLocation());
				player.sendMessage("§e[SYSTEM] : 첫 번째 지점 설정 완료! (" + block.getLocation().getBlockX() + ","
						+block.getLocation().getBlockY()+","+block.getLocation().getBlockZ()+")");
				return;
			}
			else if(event.getAction()==Action.RIGHT_CLICK_BLOCK)
			{
				main.MainServerOption.catchedLocation2.put(player, event.getClickedBlock().getLocation());
				player.sendMessage("§e[SYSTEM] : 두 번째 지점 설정 완료! (" + event.getClickedBlock().getLocation().getBlockX() + ","
						+event.getClickedBlock().getLocation().getBlockY()+","+event.getClickedBlock().getLocation().getBlockZ()+")");
				return;
			}
		}
		return;
	}
	
	public void PlayerGetItem(PlayerPickupItemEvent event)
	{
	  	if(main.MainServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).isAlert_ItemPickUp())
	  	{
			String ItemName = null;
	  		if(event.getItem().getItemStack().hasItemMeta()&&event.getItem().getItemStack().getItemMeta().hasDisplayName())
	  			ItemName = event.getItem().getItemStack().getItemMeta().getDisplayName();
	  		else
				ItemName = setItemDefaultName((short) event.getItem().getItemStack().getTypeId(),event.getItem().getItemStack().getData().getData());
	  		new effect.SendPacket().sendActionBar(event.getPlayer(), "§7§l(§l"+ItemName+" §7§l"+event.getItem().getItemStack().getAmount()+"개)", false);
	  	}
	  	return;
	}

	public String setItemDefaultName(int itemCode, int itemData)
	{
		String name = "지정되지 않은 아이템";
		switch (itemCode)
		{
			case 1:
				switch(itemData)
				{
				case 0:
					return "§7§l돌";
				case 1:
					return "§c§l화강암";
				case 2:
					return "§c§l부드러운 화강암";
				case 3:
					return "§f§l섬록암";
				case 4:
					return "§f§l부드러운 섬록암";
				case 5:
					return "§7§l안산암";
				case 6:
					return "§7§l부드러운 안산암";
				}
				break;
			case 2: return "§a§l잔디 블록";
			case 3:
				switch(itemData)
				{
				case 0:
					return "§6§l흙";
				case 1:
					return "§6§l거친 흙";
				case 2:
					return "§6§l회백토";
				}
				break;
			case 4: return "§7§l조약돌";
			case 5:
				switch(itemData)
				{
				case 0:
					return "§6§l참나무 목재";
				case 1:
					return "§0§l가문비나무 목재";
				case 2:
					return "§f§l자작나무 목재";
				case 3:
					return "§6§l정글 나무 목재";
				case 4:
					return "§6§l아카시아나무 목재";
				case 5:
					return "§0§l짙은 참나무 목재";
				}
				break;
			case 6:
				switch(itemData)
				{
				case 0:
					return "§6§l참나무 묘목";
				case 1:
					return "§0§l가문비나무 묘목";
				case 2:
					return "§f§l자작나무 묘목";
				case 3:
					return "§6§l정글 나무 묘목";
				case 4:
					return "§6§l아카시아나무 묘목";
				case 5:
					return "§0§l짙은 참나무 묘목";
				}
				break;
			case 7: return "§0§l기반암";
			case 8: return "§9§l물";
			case 9: return "§9§l흐르지 않는 물";
			case 10: return "§c§l용암";
			case 11: return "§c§l흐르지 않는 용암";
			case 12:
				switch(itemData)
				{
				case 0:
					return "§e§l모래";
				case 1:
					return "§c§l붉은 모래";
				}
				break;
			case 13: return "§7§l자갈";
			case 14: return "§e§l금광석";
			case 15: return "§7§l철광석";
			case 16: return "§0§l석탄 광석";
			case 17:
				switch(itemData%4)
				{
				case 0:
					return "§6§l참나무";
				case 1:
					return "§0§l가문비나무";
				case 2:
					return "§f§l자작나무";
				case 3:
					return "§6§l정글 나무";
				}
				break;
			case 18:
				switch(itemData)
				{
				case 0:
					return "§a§l참나무 잎";
				case 1:
					return "§a§l가문비나무 잎";
				case 2:
					return "§a§l자작나무 잎";
				case 3:
					return "§a§l정글 나무 잎";
				}
				break;
			case 19:
				switch(itemData)
				{
				case 0:
					return "§e§l스펀지";
				case 1:
					return "§9§l젖은 스펀지";
				}
				break;
			case 20: return "§f§l유리";
			case 21: return "§9§l청금석 원석";
			case 22: return "§9§l청금석 블록";
			case 23: return "§7§l발사기";
			case 24:
				switch(itemData)
				{
				case 0:
					return "§e§l사암";
				case 1:
					return "§e§l조각된 사암";
				case 2:
					return "§e§l부드러운 사암";
				}
				break;
			case 25: return "§6§l노트 블록";
			case 26: return "§c§l반토막 침대";
			case 27: return "§e§l파워 레일";
			case 28: return "§7§l디텍터 레일";
			case 29: return "§a§l끈끈이 피스톤";
			case 30: return "§f§l거미줄";
			case 31:
				switch(itemData)
				{
				case 1:
					return "§a§l잔디";
				case 2:
					return "§a§l고사리";
				}
				break;
			case 32: return "§a§l마른 덤불";
			case 33: return "§6§l피스톤";
			case 34: return "§6§l피스톤 머리";
			case 35:
				switch(itemData)
				{
				case 0:
					return "§f§l흰 양털";
				case 1:
					return "§6§l주황색 양털";
				case 2:
					return "§d§l자홍색 양털";
				case 3:
					return "§b§l하늘색 양털";
				case 4:
					return "§e§l노란색 양털";
				case 5:
					return "§a§l연두색 양털";
				case 6:
					return "§d§l분홍색 양털";
				case 7:
					return "§8§l회색 양털";
				case 8:
					return "§7§l밝은 회색 양털";
				case 9:
					return "§9§l청록색 양털";
				case 10:
					return "§5§l보라색 양털";
				case 11:
					return "§1§l파란색 양털";
				case 12:
					return "§6§l갈색 양털";
				case 13:
					return "§2§l초록색 양털";
				case 14:
					return "§4§l빨간색 양털";
				case 15:
					return "§0§l검은색 양털";
				}
				break;
			case 37: return "§e§l민들레";
			case 38:
				switch(itemData)
				{
				case 0:
					return "§c§l양귀비";
				case 1:
					return "§b§l파란 난초";
				case 2:
					return "§d§l파꽃";
				case 3:
					return "§f§l푸른 삼백초";
				case 4:
					return "§c§l빨간색 튤립";
				case 5:
					return "§6§l주황색 튤립";
				case 6:
					return "§f§l하얀색 튤립";
				case 7:
					return "§d§l분홍색 튤립";
				case 8:
					return "§f§l데이지";
				}
				break;
			case 39: return "§6§l갈색 버섯";
			case 40: return "§c§l붉은 버섯";
			case 41: return "§e§l금 블록";
			case 42: return "§f§l철 블록";
			case 43: return "§7§l겹쳐진 돌 반 블록";
			case 44:
				switch(itemData%7)
				{
				case 0:
					return "§7§l돌 반 블록";
				case 1:
					return "§e§l사암 반 블록";
				case 3:
					return "§7§l조약돌 반 블록";
				case 4:
					return "§c§l벽돌 반 블록";
				case 5:
					return "§7§l석재 벽돌 반 블록";
				case 6:
					return "§4§l네더 벽돌 반 블록";
				case 7:
					return "§f§l석영 반 블록";
				}
				break;
			case 45: return "§c§l벽돌";
			case 46: return "§4§lTNT";
			case 47: return "§6§l책장";
			case 48: return "§a§l이끼 낀 돌";
			case 49: return "§0§l흑요석";
			case 50: return "§e§l횃불";
			case 51: return "§c§l불";
			case 52: return "§7§l몬스터 스포너";
			case 53: return "§6§l참나무 계단";
			case 54: return "§6§l상자";
			case 55: return "§c§l레드스톤";
			case 56: return "§b§l다이아몬드 원석";
			case 57: return "§b§l다이아몬드 블록";
			case 58: return "§6§l작업대";
			case 59: return "§a§l농작물";
			case 60: return "§6§l밭 블록";
			case 61: return "§7§l화로";
			case 62: return "§e§l불타는 화로";
			case 63: return "§6§l표지판";
			case 64: return "§6§l문짝 반";
			case 65: return "§6§l사다리";
			case 66: return "§7§l레일";
			case 67: return "§7§l석재 계단";
			case 68: return "§6§l붙어있는 표지판";
			case 69: return "§7§l레버";
			case 70: return "§7§l돌 감압판";
			case 71: return "§f§l철 문짝 반";
			case 72: return "§6§l나무 감압판";
			case 73: return "§c§l레드스톤 원석";
			case 74: return "§c§l빛나는 레드스톤 원석";
			case 75: return "§7§l빛 잃은 레드스톤 횃불";
			case 76: return "§c§l레드스톤 횃불";
			case 77: return "§7§l돌 버튼";
			case 78: return "§f§l눈";
			case 79: return "§b§l얼음";
			case 80: return "§f§l눈 블록";
			case 81: return "§2§l선인장";
			case 82: return "§7§l점토";
			case 83: return "§a§l사탕 수수";
			case 84: return "§6§l주크 박스";
			case 85: return "§6§l참나무 울타리";
			case 86: return "§6§l호박";
			case 87: return "§d§l네더랙";
			case 88: return "§6§l소울 샌드";
			case 89: return "§e§l발광석";
			case 90: return "§5§l네더 포탈";
			case 91: return "§e§l잭 오 랜턴";
			case 92: return "§f§l케이크 블록";
			case 93: return "§7§l빛 잃은 레드스톤 중계기";
			case 94: return "§c§l레드스톤 중계기";
			case 95:
				switch(itemData)
				{
				case 0:
					return "§f§l하얀색 염색된 유리";
				case 1:
					return "§6§l주황색 염색된 유리";
				case 2:
					return "§d§l자홍색 염색된 유리";
				case 3:
					return "§b§l하늘색 염색된 유리";
				case 4:
					return "§e§l노란색 염색된 유리";
				case 5:
					return "§a§l연두색 염색된 유리";
				case 6:
					return "§d§l분홍색 염색된 유리";
				case 7:
					return "§8§l회색 염색된 유리";
				case 8:
					return "§7§l밝은 회색 염색된 유리";
				case 9:
					return "§9§l청록색 염색된 유리";
				case 10:
					return "§5§l보라색 염색된 유리";
				case 11:
					return "§1§l파란색 염색된 유리";
				case 12:
					return "§6§l갈색 염색된 유리";
				case 13:
					return "§2§l초록색 염색된 유리";
				case 14:
					return "§4§l빨간색 염색된 유리";
				case 15:
					return "§0§l검정색 염색된 유리";
				}
				break;
			case 96: return "§6§l다락문";
			case 97:
				switch(itemData)
				{
				case 0:
					return "§7§l돌 몬스터 알";
				case 1:
					return "§7§l조약돌 몬스터 알";
				case 2:
					return "§7§l석재 벽돌 몬스터 알";
				case 3:
					return "§7§l이끼 낀 석재 벽돌 몬스터 알";
				case 4:
					return "§7§l금 간 석재 벽돌 몬스터 알";
				case 5:
					return "§7§l조각된 석재 벽돌 몬스터 알";
				}
				break;
			case 98:
				switch(itemData)
				{
				case 0:
					return "§7§l석재 벽돌";
				case 1:
					return "§7§l이끼 낀 석재 벽돌";
				case 2:
					return "§7§l금 간 석재 벽돌";
				case 3:
					return "§7§l조각된 석재 벽돌";
				}
				break;
			case 99: return "§6§l대형 갈색 버섯 갓 블록";
			case 100: return "§c§l대형 붉은 버섯 갓 블록";
			case 101: return "§7§l철창";
			case 102: return "§f§l유리판";
			case 103: return "§a§l수박 블록";
			case 104: return "§a§l호박 넝쿨";
			case 105: return "§a§l수박 넝쿨";
			case 106: return "§2§l덩굴";
			case 107: return "§6§l참나무 울타리 문";
			case 108: return "§c§l벽돌 계단";
			case 109: return "§7§l석재 벽돌 계단";
			case 110: return "§d§l균사체";
			case 111: return "§2§l연꽃잎";
			case 112: return "§5§l네더 벽돌";
			case 113: return "§5§l네더 벽돌 울타리";
			case 114: return "§5§l네더 벽돌 계단";
			case 115: return "§d§l네더 와트";
			case 116: return "§b§l인챈트 테이블";
			case 117: return "§e§l양조기";
			case 118: return "§7§l가마솥";
			case 119: return "§0§l엔더월드 포탈";
			case 120: return "§e§l엔더 포탈";
			case 121: return "§e§l엔드 스톤";
			case 122: return "§0§l드래곤 알";
			case 123: return "§6§l레드스톤 조명";
			case 124: return "§e§l빛나는 레드스톤 조명";
			case 125: return "§6§l겹쳐진 나무 반 블록";
			case 126:
				switch(itemData%6)
				{
				case 0:
					return "§6§l참나무 반 블록";
				case 1:
					return "§0§l가문비나무 반 블록";
				case 2:
					return "§f§l자작나무 반 블록";
				case 3:
					return "§6§l정글 나무 반 블록";
				case 4:
					return "§6§l아카시아나무 반 블록";
				case 5:
					return "§0§l짙은 참나무 반 블록";
				}
				break;
			case 127: return "§6§l코코아 열매";
			case 128: return "§e§l사암 계단";
			case 129: return "§a§l에메랄드 원석";
			case 130: return "§0§l엔더 상자";
			case 131: return "§7§l철사 덫 갈고리";
			case 132: return "§7§l철사 덫 갈고리 실";
			case 133: return "§a§l에메랄드 블록";
			case 134: return "§6§l가문비 나무 계단";
			case 135: return "§6§l자작 나무 계단";
			case 136: return "§6§l정글 나무 계단";
			case 137: return "§c§l커§l§6§l맨§l§e§l드§l§2§l 블§l§9§l록";
			case 138: return "§b§l신호기";
			case 139: return "§7§l조약돌 담장";
			case 140: return "§d§l화분";
			case 141: return "§a§l농작물";
			case 142: return "§a§l농작물";
			case 143: return "§6§l나무 버튼";
			case 144: return "§f§l미스테리 해골";
			case 145:
				switch(itemData)
				{
				case 0:
					return "§7§l모루";
				case 1:
					return "§7§l약간 손상된 모루";
				case 2:
					return "§7§l심각하게 손상된 모루";
				}
				break;
			case 146: return "§c§l덫 상자";
			case 147: return "§6§l무게 감압판(경형)";
			case 148: return "§f§l무게 감압판(중형)";
			case 149: return "§c§l레드스톤 비교기";
			case 150: return "§e§l빛나는 레드스톤 비교기";
			case 151: return "§c§l햇빛 감지기";
			case 152: return "§c§l레드스톤 블록";
			case 153: return "§f§l네더 석영 원석";
			case 154: return "§7§l깔때기";
			case 155:
				switch(itemData)
				{
				case 0:
					return "§f§l석영 블록";
				case 1:
					return "§f§l조각된 석영 블록";
				default:
					return "§f§l석영 기둥 블록";
				}
			case 156: return "§f§l석영 계단";
			case 157: return "§7§l활성화 레일";
			case 158: return "§7§l공급기";
			case 159:
				switch(itemData)
				{
				case 0:
					return "§f§l흰색 염색된 점토";
				case 1:
					return "§6§l주황색 염색된 점토";
				case 2:
					return "§d§l자홍색 염색된 점토";
				case 3:
					return "§b§l하늘색 염색된 점토";
				case 4:
					return "§e§l노란색 염색된 점토";
				case 5:
					return "§a§l연두색 염색된 점토";
				case 6:
					return "§d§l분홍색 염색된 점토";
				case 7:
					return "§8§l회색 염색된 점토";
				case 8:
					return "§7§l밝은 회색 염색된 점토";
				case 9:
					return "§9§l청록색 염색된 점토";
				case 10:
					return "§5§l보라색 염색된 점토";
				case 11:
					return "§1§l파란색 염색된 점토";
				case 12:
					return "§6§l갈색 염색된 점토";
				case 13:
					return "§2§l초록색 염색된 점토";
				case 14:
					return "§4§l빨간색 염색된 점토";
				case 15:
					return "§0§l검은색 염색된 점토";
				}
				break;
			case 160:
				switch(itemData)
				{
				case 0:
					return "§f§l하얀색 염색된 유리판";
				case 1:
					return "§6§l주황색 염색된 유리판";
				case 2:
					return "§d§l자홍색 염색된 유리판";
				case 3:
					return "§b§l하늘색 염색된 유리판";
				case 4:
					return "§e§l노란색 염색된 유리판";
				case 5:
					return "§a§l연두색 염색된 유리판";
				case 6:
					return "§d§l분홍색 염색된 유리판";
				case 7:
					return "§8§l회색 염색된 유리판";
				case 8:
					return "§7§l밝은 회색 염색된 유리판";
				case 9:
					return "§9§l청록색 염색된 유리판";
				case 10:
					return "§5§l보라색 염색된 유리판";
				case 11:
					return "§1§l파란색 염색된 유리판";
				case 12:
					return "§6§l갈색 염색된 유리판";
				case 13:
					return "§2§l초록색 염색된 유리판";
				case 14:
					return "§4§l빨간색 염색된 유리판";
				case 15:
					return "§0§l검은색 염색된 유리판";
				}
				break;
			case 161:
				switch(itemData)
				{
				case 0:
					return "§a§l아카시아 잎";
				case 1:
					return "§a§l짙은 참나무 잎";
				}
				break;
			case 162:
				switch(itemData%2)
				{
				case 0:
					return "§6§l아카시아 나무";
				case 1:
					return "§0§l짙은 참나무";
				}
				break;
			case 163: return "§6§l아카시아 나무 계단";
			case 164: return "§6§l짙은 참나무 계단";
			case 165: return "§a§l슬라임 블록";
			case 166: return "§f§l바리게이트(투명블록)";
			case 167: return "§f§l철 다락문";
			case 168:
				switch(itemData)
				{
				case 0:
					return "§b§l프리즈마린";
				case 1:
					return "§b§l프리즈마린 벽돌";
				case 2:
					return "§9§l어두운 프리즈마린 벽돌";
				}
				break;
			case 169: return "§b§l바다 랜턴";
			case 170: return "§6§l건초 더미";
			case 171:
				switch(itemData)
				{
				case 0:
					return "§f§l흰색 양탄자";
				case 1:
					return "§6§l주황색 양탄자";
				case 2:
					return "§d§l자홍색 양탄자";
				case 3:
					return "§b§l하늘색 양탄자";
				case 4:
					return "§e§l노란색 양탄자";
				case 5:
					return "§a§l연두색 양탄자";
				case 6:
					return "§d§l분홍색 양탄자";
				case 7:
					return "§8§l회색 양탄자";
				case 8:
					return "§7§l밝은 회색 양탄자";
				case 9:
					return "§9§l청록색 양탄자";
				case 10:
					return "§5§l보라색 양탄자";
				case 11:
					return "§1§l파란색 양탄자";
				case 12:
					return "§6§l갈색 양탄자";
				case 13:
					return "§2§l초록색 양탄자";
				case 14:
					return "§4§l빨간색 양탄자";
				case 15:
					return "§0§l검은색 양탄자";
				}
				break;
			case 172: return "§7§l굳은 점토";
			case 173: return "§0§l석탄 블록";
			case 174: return ChatColor.MAGIC+"§l단단한 얼음";
			case 175:
				switch(itemData)
				{
				case 0:
					return "§e§l해바라기";
				case 1:
					return "§d§l라일락";
				case 2:
					return "§2§l큰 잔디";
				case 3:
					return "§b§l큰 고사리";
				case 4:
					return "§c§l장미 덤불";
				case 5:
					return "§d§l모란";
				}
				break;
			case 176: return "§f§l현수막(아래 블록)";
			case 177: return "§f§l현수막(윗 블록)";
			case 178: return "§7§l빛 잃은 햇빛 감지기";
			case 179:
				switch(itemData)
				{
				case 0:
					return "§c§l붉은 사암";
				case 1:
					return "§c§l조각된 붉은 사암";
				case 2:
					return "§c§l부드러운 붉은 사암";
				}
				break;
			case 180: return "§c§l붉은 사암 계단";
			case 181: return "§c§l겹쳐진 붉은 사암 반 블록";
			case 182: return "§c§l붉은 사암 반 블록";
			case 183: return "§6§l가문비 나무 울타리 문";
			case 184: return "§6§l자작 나무 울타리 문";
			case 185: return "§6§l정글 나무 울타리 문";
			case 186: return "§6§l짙은 참나무 울타리 문";
			case 187: return "§6§l아카시아 나무 울타리 문";
			case 188: return "§6§l가문비 나무 울타리";
			case 189: return "§6§l자작 나무 울타리";
			case 190: return "§6§l정글 나무 울타리";
			case 191: return "§6§l짙은 참나무 울타리";
			case 192: return "§6§l아카시아 나무 울타리";
			case 193: return "§6§l가문비 나무 문짝 반";
			case 194: return "§6§l자작 나무 문짝 반";
			case 195: return "§6§l정글 나무 문짝 반";
			case 196: return "§6§l아카시아 나무 문짝 반";
			case 197: return "§6§l짙은 참나무 문짝 반";
			case 198: return "§5§l엔드 막대";
			case 199: return "§5§l후렴 나무";
			case 200: return "§5§l후렴화";
			case 201: return "§5§l퍼퍼 블록";
			case 202: return "§5§l퍼퍼 기둥";
			case 203: return "§5§l퍼퍼 계단";
			case 204: return "§5§l퍼퍼 겹친 반 블록";
			case 205: return "§5§l퍼퍼 반 블록";
			case 206: return "§f§l엔드 석재 벽돌";
			case 207: return "§e§l사탕무 블록";
			case 208: return "§6§l잔디 길";
			case 209: return "§8§l엔드 게이트";
			case 210: return "§5§l반복 커맨드 블록";
			case 211: return "§a§l체인 커맨드 블록";
			case 212: return "§b§l단단한 얼음";
			case 213: return "§c§l마그마 블록";
			case 214: return "§4§l네더 와트 블록";
			case 215: return "§4§l붉은 네더 벽돌";
			case 216: return "§f§l뼈 블록";
			case 217: return "§2§l빈 구조물";
			case 218: return "§7§l감시 블록";
			case 219: return "§7§l하얀색 셜커 상자";
			case 220: return "§6§l주황색 셜커 상자";
			case 221: return "§d§l자홍색 셜커 상자";
			case 222: return "§b§l하늘색 셜커 상자";
			case 223: return "§e§l노란색 셜커 상자";
			case 224: return "§a§l연두색 셜커 상자";
			case 225: return "§d§l분홍색 셜커 상자";
			case 226: return "§8§l회색 셜커 상자";
			case 227: return "§7§l밝은 회색 셜커 상자";
			case 228: return "§9§l청록색 셜커 상자";
			case 229: return "§5§l보라색 셜커 상자";
			case 230: return "§1§l파란색 셜커 상자";
			case 231: return "§6§l갈색 셜커 상자";
			case 232: return "§2§l초록색 셜커 상자";
			case 233: return "§4§l빨간색 셜커 상자";
			case 234: return "§0§l검은색 셜커 상자";
			case 235: return "§7§l하얀색 테라코타";
			case 236: return "§6§l주황색 테라코타";
			case 237: return "§d§l자홍색 테라코타";
			case 238: return "§b§l하늘색 테라코타";
			case 239: return "§e§l노란색 테라코타";
			case 240: return "§a§l연두색 테라코타";
			case 241: return "§d§l분홍색 테라코타";
			case 242: return "§8§l회색 테라코타";
			case 243: return "§7§l밝은 회색 테라코타";
			case 244: return "§9§l청록색 테라코타";
			case 245: return "§5§l보라색 테라코타";
			case 246: return "§1§l파란색 테라코타";
			case 247: return "§6§l갈색 테라코타";
			case 248: return "§2§l초록색 테라코타";
			case 249: return "§4§l빨간색 테라코타";
			case 250: return "§0§l검은색 테라코타";
			case 251:
			{
				switch(itemData)
				{
					case 0: return "§7§l하얀색 콘크리트";
					case 1: return "§6§l주황색 콘크리트";
					case 2: return "§d§l자홍색 콘크리트";
					case 3: return "§b§l하늘색 콘크리트";
					case 4: return "§e§l노란색 콘크리트";
					case 5: return "§a§l연두색 콘크리트";
					case 6: return "§d§l분홍색 콘크리트";
					case 7: return "§8§l회색 콘크리트";
					case 8: return "§7§l밝은 회색 콘크리트";
					case 9: return "§9§l청록색 콘크리트";
					case 10: return "§5§l보라색 콘크리트";
					case 11: return "§1§l파란색 콘크리트";
					case 12: return "§6§l갈색 콘크리트";
					case 13: return "§2§l초록색 콘크리트";
					case 14: return "§4§l빨간색 콘크리트";
					case 15: return "§0§l검은색 콘크리트";
				}
			}break;
			case 252:
			{
				switch(itemData)
				{
					case 0: return "§7§l하얀색 콘크리트 가루";
					case 1: return "§6§l주황색 콘크리트 가루";
					case 2: return "§d§l자홍색 콘크리트 가루";
					case 3: return "§b§l하늘색 콘크리트 가루";
					case 4: return "§e§l노란색 콘크리트 가루";
					case 5: return "§a§l연두색 콘크리트 가루";
					case 6: return "§d§l분홍색 콘크리트 가루";
					case 7: return "§8§l회색 콘크리트 가루";
					case 8: return "§7§l밝은 회색 콘크리트 가루";
					case 9: return "§9§l청록색 콘크리트 가루";
					case 10: return "§5§l보라색 콘크리트 가루";
					case 11: return "§1§l파란색 콘크리트 가루";
					case 12: return "§6§l갈색 콘크리트 가루";
					case 13: return "§2§l초록색 콘크리트 가루";
					case 14: return "§4§l빨간색 콘크리트 가루";
					case 15: return "§0§l검은색 콘크리트 가루";
				}
			}break;

			case 255: return "§8§l구조물 블록";
			case 256: return "§f§l철 삽";
			case 257: return "§f§l철 곡괭이";
			case 258: return "§f§l철 도끼";
			case 259: return "§f§l라이터";
			case 260: return "§c§l사과";
			case 261: return "§6§l활";
			case 262: return "§6§l화살";
			case 263:
				switch(itemData)
				{
				case 0:
					return "§0§l석탄";
				case 1:
					return "§0§l목탄";
				}
				break;
			case 264: return "§b§l다이아몬드";
			case 265: return "§f§l철괴";
			case 266: return "§e§l금괴";
			case 267: return "§f§l철 검";
			case 268: return "§6§l나무 검";
			case 269: return "§6§l나무 삽";
			case 270: return "§6§l나무 곡괭이";
			case 271: return "§6§l나무 도끼";
			case 272: return "§7§l돌 검";
			case 273: return "§7§l돌 삽";
			case 274: return "§7§l돌 곡괭이";
			case 275: return "§7§l돌 도끼";
			case 276: return "§b§l다이아몬드 검";
			case 277: return "§b§l다이아몬드 삽";
			case 278: return "§b§l다이아몬드 곡괭이";
			case 279: return "§b§l다이아몬드 도끼";
			case 280: return "§6§l막대기";
			case 281: return "§6§l그릇";
			case 282: return "§d§l버섯 스튜";
			case 283: return "§e§l금 검";
			case 284: return "§e§l금 삽";
			case 285: return "§e§l금 곡괭이";
			case 286: return "§e§l금 도끼";
			case 287: return "§f§l실";
			case 288: return "§f§l깃털";
			case 289: return "§7§l화약";
			case 290: return "§6§l나무 괭이";
			case 291: return "§7§l돌 괭이";
			case 292: return "§f§l철 괭이";
			case 293: return "§b§l다이아몬드 괭이";
			case 294: return "§e§l금 괭이";
			case 295: return "§a§l씨앗";
			case 296: return "§6§l밀";
			case 297: return "§6§l빵";
			case 298: return "§6§l가죽 모자";
			case 299: return "§6§l가죽 튜닉";
			case 300: return "§6§l가죽 바지";
			case 301: return "§6§l가죽 장화";
			case 302: return "§f§l사슬 투구";
			case 303: return "§f§l사슬 갑옷";
			case 304: return "§f§l사슬 레깅스";
			case 305: return "§f§l사슬 부츠";
			case 306: return "§f§l철 투구";
			case 307: return "§f§l철 갑옷";
			case 308: return "§f§l철 레깅스";
			case 309: return "§f§l철 부츠";
			case 310: return "§b§l다이아몬드 투구";
			case 311: return "§b§l다이아몬드 갑옷";
			case 312: return "§b§l다이아몬드 레깅스";
			case 313: return "§b§l다이아몬드 부츠";
			case 314: return "§e§l금 투구";
			case 315: return "§e§l금 갑옷";
			case 316: return "§e§l금 레깅스";
			case 317: return "§e§l금 부츠";
			case 318: return "§0§l부싯돌";
			case 319: return "§d§l익히지 않은 돼지고기";
			case 320: return "§6§l구운 돼지고기";
			case 321: return "§7§l그림";
			case 322:
				switch(itemData)
				{
				case 0:
					return "§e§l황금사과";
				case 1:
					return "§5§l인챈트된 황금사과";
				}
				break;
			case 323: return "§6§l표지판";
			case 324: return "§6§l나무 문";
			case 325: return "§7§l양동이";
			case 326: return "§9§l물 양동이";
			case 327: return "§c§l용암 양동이";
			case 328: return "§7§l마인카트";
			case 329: return "§6§l안장";
			case 330: return "§f§l철 문";
			case 331: return "§c§l레드스톤";
			case 332: return "§f§l눈덩이";
			case 333: return "§6§l보트";
			case 334: return "§6§l가죽";
			case 335: return "§f§l우유";
			case 336: return "§c§l벽돌";
			case 337: return "§7§l점토";
			case 338: return "§a§l사탕수수";
			case 339: return "§f§l종이";
			case 340: return "§6§l책";
			case 341: return "§a§l슬라임 볼";
			case 342: return "§6§l상자 마인카트";
			case 343: return "§7§l화로 마인카트";
			case 344: return "§f§l달걀";
			case 345: return "§7§l나침반";
			case 346: return "§6§l낚싯대";
			case 347: return "§e§l시계";
			case 348: return "§e§l발광석 가루";
			case 349:
				switch(itemData)
				{
				case 0:
					return "§b§l날 생선";
				case 1:
					return "§c§l날 연어";
				case 2:
					return "§6§l흰동가리";
				case 3:
					return "§e§l복어";
				}
				break;
			case 350:
				switch(itemData)
				{
				case 0:
					return "§7§l익힌 생선";
				case 1:
					return "§c§l익힌 연어";
				}
				break;
			case 351:
				switch(itemData)
				{
				case 0:
					return "§0§l먹물";
				case 1:
					return "§c§l붉은 장미 염료";
				case 2:
					return "§2§l초록 선인장 염료";
				case 3:
					return "§6§l코코아 콩";
				case 4:
					return "§1§l청금석";
				case 5:
					return "§5§l보라색 염료";
				case 6:
					return "§9§l청록색 염료";
				case 7:
					return "§7§l밝은 회색 염료";
				case 8:
					return "§8§l회색 염료";
				case 9:
					return "§d§l분홍색 염료";
				case 10:
					return "§a§l연두색 염료";
				case 11:
					return "§e§l노랑 민들레 염료";
				case 12:
					return "§b§l하늘색 염료";
				case 13:
					return "§d§l자홍색 염료";
				case 14:
					return "§6§l주황색 염료";
				case 15:
					return "§f§l뼛가루";
				}
				break;
			case 352: return "§f§l뼈";
			case 353: return "§f§l설탕";
			case 354: return "§f§l케이크";
			case 355: return "§c§l침대";
			case 356: return "§c§l레드스톤 중계기";
			case 357: return "§6§l쿠키";
			case 358: return "§e§l지도";
			case 359: return "§f§l가위";
			case 360: return "§a§l수박";
			case 361: return "§f§l호박씨";
			case 362: return "§0§l수박씨";
			case 363: return "§d§l익히지 않은 소고기";
			case 364: return "§6§l스테이크";
			case 365: return "§d§l익히지 않은 닭고기";
			case 366: return "§6§l구운 닭고기";
			case 367: return "§d§l썩은 고기";
			case 368: return "§2§l엔더 진주";
			case 369: return "§e§l블레이즈 막대";
			case 370: return "§f§l가스트의 눈물";
			case 371: return "§e§l금 조각";
			case 372: return "§c§l네더 와트";
			case 373: return "§9§l포션";
			case 374: return "§f§l유리병";
			case 375: return "§4§l거미 눈";
			case 376: return "§d§l발효된 거미 눈";
			case 377: return "§e§l블레이즈 가루";
			case 378: return "§e§l마그마 크림";
			case 379: return "§e§l양조기";
			case 380: return "§7§l가마솥";
			case 381: return "§2§l엔더의 눈";
			case 382: return "§e§l반짝이는 수박";
			case 383: return "§7§l몬스터 스폰 에그";
			case 384: return "§a§l경험치 병";
			case 385: return "§0§l화염구";
			case 386: return "§f§l책과 깃펜";
			case 388: return "§a§l에메랄드";
			case 389: return "§6§l아이템 액자";
			case 390: return "§d§l화분";
			case 391: return "§6§l당근";
			case 392: return "§6§l감자";
			case 393: return "§6§l구운 감자";
			case 394: return "§a§l독이 든 감자";
			case 395: return "§f§l빈 지도";
			case 396: return "§e§l황금 당근";
			case 397: return "§f§l머리";
			case 398: return "§6§l당근 낚싯대";
			case 399: return "§b§l네더의 별";
			case 400: return "§6§l호박 파이";
			case 401: return "§f§l폭죽";
			case 402: return "§7§l불꽃놀이 탄약";
			case 403: return "§e§l마법이 부여된 책";
			case 404: return "§c§l레드스톤 비교기";
			case 405: return "§5§l네더 벽돌";
			case 406: return "§f§l네더 석영";
			case 407: return "§4§lTNT 마인카트";
			case 408: return "§7§l깔때기 마인카트";
			case 409: return "§b§l프리즈마린 조각";
			case 410: return "§b§l프리즈마린 수정";
			case 411: return "§d§l익히지 않은 토끼고기";
			case 412: return "§6§l구운 토끼고기";
			case 413: return "§6§l토끼 스튜";
			case 414: return "§6§l토끼 발";
			case 415: return "§6§l토끼 가죽";
			case 416: return "§f§l갑옷 거치대";
			case 417: return "§f§l철 말 갑옷";
			case 418: return "§e§l금 말 갑옷";
			case 419: return "§b§l다이아몬드 말 갑옷";
			case 420: return "§6§l끈";
			case 421: return "§6§l이름표";
			case 423: return "§d§l익히지 않은 양고기";
			case 424: return "§6§l구운 양고기";
			case 425: return "§f§l현수막";
			case 426: return "§d§l엔드 수정";
			case 427: return "§6§l가문비 나무 문";
			case 428: return "§6§l자작 나무 문";
			case 429: return "§6§l정글 나무 문";
			case 430: return "§6§l아카시아 나무 문";
			case 431: return "§6§l짙은 참나무 문";
			case 432: return "§d§l후렴과";
			case 433: return "§d§l튀긴 후렴과";
			case 434: return "§c§l사탕무";
			case 435: return "§f§l사탕무 씨앗";
			case 436: return "§c§l사탕무 수프";
			case 437: return "§d§l드래곤의 숨결";
			case 438: return "§f§l투척용 포션";
			case 439: return "§e§l분광 화살";
			case 440: return "§f§l포션 바른 화살";
			case 441: return "§f§l잔류형 포션";
			case 442: return "§f§l방패";
			case 443: return "§f§l겉날개";
			case 444: return "§6§l가문비나무 보트";
			case 445: return "§6§l자작나무 보트";
			case 446: return "§6§l정글나무 보트";
			case 447: return "§6§l아카시아나무 보트";
			case 448: return "§6§l짙은 참나무 보트";
			case 449: return "§e§l불사의 토템";
			case 450: return "§d§l셜커 껍데기";
			case 452: return "§f§l철 조각";
			case 2256: return "§7§l[음반] [곡 명 : §l§e§l13§l§7§l]";
			case 2257: return "§7§l[음반] [곡 명 : §l§a§lcat§l§7§l]";
			case 2258: return "§7§l[음반] [곡 명 : §l§c§lblocks§l§7§l]";
			case 2259: return "§7§l[음반] [곡 명 : §l§4§lchirp§l§7§l]";
			case 2260: return "§7§l[음반] [곡 명 : §l§a§lfar§l§7§l]";
			case 2261: return "§7§l[음반] [곡 명 : §l"+ChatColor.MAGIC+"§lmall§l§7§l]";
			case 2262: return "§7§l[음반] [곡 명 : §l§5§lmellohi§l§7§l]";
			case 2263: return "§7§l[음반] [곡 명 : §l§0§lstal§l§7§l]";
			case 2264: return "§7§l[음반] [곡 명 : §l§f§lstrad§l§7§l]";
			case 2265: return "§7§l[음반] [곡 명 : §l§2§lward§l§7§l]";
			case 2266: return "§7§l[음반] [곡 명 : §l§0§l11§l§7§l]";
			case 2267: return "§7§l[음반] [곡 명 : §l§b§lwait§l§7§l]";
		}
		return name;
	}
}