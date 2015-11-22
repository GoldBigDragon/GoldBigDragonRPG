package GBD.GoldBigDragon_Advanced.Event;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.*;

import GBD.GoldBigDragon_Advanced.Main.*;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class Interact
{
	//블럭 우클/좌클 할 때//
	public void PlayerInteract(PlayerInteractEvent event)
	{
		if(event.getAction()==Action.RIGHT_CLICK_AIR||event.getAction()==Action.RIGHT_CLICK_BLOCK)
		{ItemUse(event);}
		if(event.getPlayer().isOp())
			AreaChecker(event);
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			CustomMonsterSpawnEggUse(event);
			if(event.getClickedBlock().getLocation().getWorld().getName().equals("Dungeon"))
				Dungeon(event);
		}
		if(Main.UserData.containsKey(event.getPlayer()))
		{OPwork(event);}
		return;
	}
	
	//NPC 및 액자
	public void PlayerInteractEntity (PlayerInteractEntityEvent event)
	{
		Entity target = event.getRightClicked();
		Player player = event.getPlayer();

		if(target.getType() == EntityType.PLAYER)
		{
			Player t = (Player)target;
			if(t.isOnline()==true)
			{
				GBD.GoldBigDragon_Advanced.GUI.EquipGUI EGUI = new GBD.GoldBigDragon_Advanced.GUI.EquipGUI();
				EGUI.EquipWatchGUI(player, t);
			}
		}
	    if(Main.UserData.containsKey(player))
		{
		    if(Main.UserData.get(player).getType()=="Quest")
			{
		    	GBD.GoldBigDragon_Advanced.ETC.Quest Q = new GBD.GoldBigDragon_Advanced.ETC.Quest();
		    	Q.EntityInteract(event);
			}
		}
	    return;
	}
	
	private void OPwork(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if(Main.UserData.get(player).getType()=="Quest")
			OPwork_Quest(event);
		if(Main.UserData.get(player).getType()=="Area")
			OPwork_Area(event);
		return;
	}
	
	private void ItemUse(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		//아이템 사용
		if(player.getItemInHand() != null)
		{
			if(player.getItemInHand().hasItemMeta() == true)
			{
				if(player.getItemInHand().getItemMeta().hasLore()==true)
				{
					if(player.getItemInHand().getTypeId() == 395
						|| player.getItemInHand().getTypeId() == 358)
					{
						Object[] lore = player.getItemInHand().getItemMeta().getLore().toArray();
							for(int counter = 0; counter < lore.length; counter ++)
								if(lore[counter].toString().contains("내용") == true)
									//지도에 이미지 넣는 작업
									if(Main.UserData.containsKey(player))
									{
										Main.UserData.get(player).setType("Map");
										Main.UserData.get(player).setString((byte)1, ChatColor.stripColor(lore[counter].toString()).split(" : ")[1]);
										Main.Mapping = true;
										return;
									}
					}
					String LoreString = player.getItemInHand().getItemMeta().getLore().toString();
					if(LoreString.contains("귀환서")||LoreString.contains("주문서")||LoreString.contains("스킬북")||LoreString.contains("소비"))
					{
						String type = null;
						for(int counter = 0; counter < player.getItemInHand().getItemMeta().getLore().size(); counter++)
						{
							if(type != null)
							{
								event.setCancelled(true);
								UseUseableItem UU = new UseUseableItem();
								UU.UseAbleItemUse(player, type);
								return;
							}
							if(player.getItemInHand().getItemMeta().getLore().get(counter).contains("귀환서"))
								type = "귀환서";
							else if(player.getItemInHand().getItemMeta().getLore().get(counter).contains("주문서"))
								type = "주문서";
							else if(player.getItemInHand().getItemMeta().getLore().get(counter).contains("스킬북"))
								type = "스킬북";
							else if(player.getItemInHand().getItemMeta().getLore().get(counter).contains("소비"))
								type = "소비";
						}
					}
				}
			}
		}
		return;
	}
	
	private void CustomMonsterSpawnEggUse(PlayerInteractEvent event)
	{
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
		Player player = event.getPlayer();
		if(player.getItemInHand() != null && player.getItemInHand().hasItemMeta() == true)
		{
			if(event.getItem().getTypeId() == 383 && event.getItem().getData().getData() == 0)
			{
				YamlManager Monster  = Event_YC.getNewConfig("Monster/MonsterList.yml");

				Object[] monsterlist = Monster.getConfigurationSection("").getKeys(false).toArray();
				
				for(int count = 0; count < monsterlist.length;count++)
		    	{
		    		if(monsterlist[count].toString().equalsIgnoreCase(ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName())) == true)
		    		{
		    			new GBD.GoldBigDragon_Advanced.ETC.Monster().SpawnMob(event.getClickedBlock().getLocation(), ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()));
						return;
		    		}
		    	}
				new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		    	player.sendMessage(ChatColor.RED+"[SYSTEM] : 해당 이름의 몬스터가 존재하지 않습니다!");
		    	return;
			}
		}
		return;
	}
	
	private void Dungeon(PlayerInteractEvent event)
	{
		Block block = event.getClickedBlock();
		GBD.GoldBigDragon_Advanced.ETC.Monster MC = new GBD.GoldBigDragon_Advanced.ETC.Monster();
		GBD.GoldBigDragon_Advanced.Effect.Sound s = new GBD.GoldBigDragon_Advanced.Effect.Sound();
		GBD.GoldBigDragon_Advanced.Effect.Particle p = new GBD.GoldBigDragon_Advanced.Effect.Particle();
		if(block.getType().getId()==146)
		{
			MC.SpawnMob(block.getLocation(), "골바기");
			s.SL(block.getLocation(), org.bukkit.Sound.CHEST_OPEN, 1.0F, 0.5F);
            block.setTypeIdAndData(0, (byte)0, true);
		}
		if(block.getType().getId()==95)
		{
			if(block.getData()==15||block.getData()==14||block.getData()==13||
				block.getData()==11||block.getData()==8)
			{
				switch(block.getData())
				{
	    			case 15: block.setTypeIdAndData(95, (byte)7, true);break;
	    			case 14: block.setTypeIdAndData(95, (byte)6, true);break;
	    			case 13: block.setTypeIdAndData(95, (byte) 5, true);break;
	    			case 11: block.setTypeIdAndData(95, (byte)3, true);break;
	    			case 8: block.setTypeIdAndData(95, (byte)0, true);break;
				}
				for(int counter=0;counter<50;counter++)
					p.PL(block.getLocation(), Effect.MAGIC_CRIT, 0);
				s.SL(block.getLocation(), org.bukkit.Sound.SUCCESSFUL_HIT, 1.0F, 0.5F);
			}
			else if(block.getData()==0||block.getData()==3||block.getData()==5||block.getData()==6||block.getData()==7)
			{
				for(int counter=0;counter<31;counter++)
					p.PL(block.getLocation(), Effect.CRIT, 0);
				s.SL(block.getLocation(), org.bukkit.Sound.HURT_FLESH, 0.5F, 0.5F);
			}
		}
		return;
	}
	
	private void OPwork_Quest(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		event.setCancelled(true);
		if(event.getAction()==Action.LEFT_CLICK_BLOCK)
		{
			if(Main.UserData.get(player).getString((byte)1)=="TelePort")
			{
				String QuestName = Main.UserData.get(player).getString((byte)3);
		    	GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();

				YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
				YamlManager QuestConfig=Config_YC.getNewConfig("Quest/QuestList.yml");
				int	size = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length;
				
				QuestConfig.set(QuestName+".FlowChart."+size+".Type", "TelePort");
				QuestConfig.set(QuestName+".FlowChart."+size+".World", block.getLocation().getWorld().getName());
				QuestConfig.set(QuestName+".FlowChart."+size+".X", block.getLocation().getX());
				QuestConfig.set(QuestName+".FlowChart."+size+".Y", block.getLocation().getY());
				QuestConfig.set(QuestName+".FlowChart."+size+".Z", block.getLocation().getZ());
				QuestConfig.set(QuestName+".FlowChart."+size+".NPCname", " ");
				QuestConfig.saveConfig();
		    	QGUI.FixQuestGUI(player, 0, QuestName);
		    	Main.UserData.get(player).clearAll();
		    	new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
		    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : 성공적으로 등록되었습니다!");
		    	return;
			}
		}
		if(event.getAction() ==Action.RIGHT_CLICK_BLOCK)
		{
			if(Main.UserData.get(player).getString((byte)1)!=null&&
				Main.UserData.get(player).getString((byte)3)!=null)
			{
				if(Main.UserData.get(player).getString((byte)1)=="Harvest")
				{
					Main.UserData.get(player).setInt((byte)1, block.getTypeId());
					Main.UserData.get(player).setInt((byte)2,(int)block.getData());
			    	player.sendMessage(ChatColor.GREEN + "[SYSTEM] : " + ChatColor.GREEN + "블록의 데이터까지 완벽히 같아야 하나요? ("+ChatColor.RED + "X"+ChatColor.GREEN +" 혹은 "+ChatColor.DARK_AQUA +"O"+ChatColor.GREEN +")");
			    	player.closeInventory();
			    	return;
				}
				else if(Main.UserData.get(player).getString((byte)1)=="BlockPlace")
				{
					String QuestName = Main.UserData.get(player).getString((byte)2);
					YamlController Config_YC = GBD.GoldBigDragon_Advanced.Main.Main.Config_YC;
					YamlManager QuestConfig=Config_YC.getNewConfig("Quest/QuestList.yml");
					int	size = QuestConfig.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length;
					
					QuestConfig.set(QuestName+".FlowChart."+size+".Type", "BlockPlace");
					QuestConfig.set(QuestName+".FlowChart."+size+".World", block.getLocation().getWorld().getName());
					QuestConfig.set(QuestName+".FlowChart."+size+".X", block.getLocation().getX());
					QuestConfig.set(QuestName+".FlowChart."+size+".Y", block.getLocation().getY());
					QuestConfig.set(QuestName+".FlowChart."+size+".Z", block.getLocation().getZ());
					QuestConfig.set(QuestName+".FlowChart."+size+".ID", 1);
					QuestConfig.set(QuestName+".FlowChart."+size+".DATA", 0);
					QuestConfig.saveConfig();
			    	Main.UserData.get(player).setString((byte)3, null);
			    	Main.UserData.get(player).setString((byte)1, "BPID");
			    	Main.UserData.get(player).setInt((byte)1, size);
			    	new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
			    	player.sendMessage(ChatColor.GREEN + "[퀘스트] : 설치 될 블록 ID를 입력 해 주세요!");
					return;
				}
			}
		}
		return;
	}
	
	private void OPwork_Area(PlayerInteractEvent event)
	{
		event.setCancelled(true);
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager AreaConfig =GUI_YC.getNewConfig("Area/AreaList.yml");
		String AreaName = Main.UserData.get(player).getString((byte)2);
		if(event.getAction()==Action.LEFT_CLICK_BLOCK)
		{
			if(Main.UserData.get(player).getString((byte)3)=="ANBI")
			{
				String BlockData = block.getTypeId()+":"+block.getData();
				ItemStack item = new MaterialData(block.getTypeId(), (byte) block.getData()).toItemStack(1);
				AreaConfig.set(AreaName+".Mining."+BlockData,item);
				AreaConfig.saveConfig();
				GBD.GoldBigDragon_Advanced.GUI.AreaGUI AGUI = new GBD.GoldBigDragon_Advanced.GUI.AreaGUI();
				new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, Sound.HORSE_SADDLE, 1.0F, 1.8F);
				AGUI.AreaBlockItemSettingGUI(player, AreaName, BlockData);
		    	Main.UserData.get(player).clearAll();
			}
		}
		else if(event.getAction()==Action.RIGHT_CLICK_BLOCK)
		{
			if(Main.UserData.get(player).getString((byte)3)=="MLS")//MonsterLocationSetting
			{
				String count = Main.UserData.get(player).getString((byte)1);
				AreaConfig.set(AreaName+".MonsterSpawnRule."+count+".loc.world", block.getLocation().getWorld().getName());
				AreaConfig.set(AreaName+".MonsterSpawnRule."+count+".loc.x", (int)block.getLocation().getX());
				AreaConfig.set(AreaName+".MonsterSpawnRule."+count+".loc.y", (int)block.getLocation().getY()+1);
				AreaConfig.set(AreaName+".MonsterSpawnRule."+count+".loc.z", (int)block.getLocation().getZ());
				AreaConfig.saveConfig();
				new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(player, Sound.ITEM_PICKUP, 1.0F, 1.8F);
		    	Main.UserData.get(player).clearAll();
				Main.UserData.get(player).setType("Area");
				Main.UserData.get(player).setString((byte)1, count);
				Main.UserData.get(player).setString((byte)2, "AMSC");
				Main.UserData.get(player).setString((byte)3, AreaName);
				player.sendMessage(ChatColor.GREEN+"[영역] : 한 번에 몇 마리 씩 스폰 할까요?");
				player.sendMessage(ChatColor.YELLOW+"(최소 1마리 ~ 최대 100마리)");
			}
		}
		return;
	}
	
	private void AreaChecker(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
		YamlManager Config = Event_YC.getNewConfig("config.yml");
		if(player.getInventory().getItemInHand().getTypeId() == Config.getInt("Server.AreaSettingWand"))
		{
			if(event.getAction()==Action.LEFT_CLICK_BLOCK)
			{
				event.setCancelled(true);
				Main.catchedLocation1.put(player, block.getLocation());
				player.sendMessage(ChatColor.YELLOW + "[SYSTEM] : 첫 번째 지점 설정 완료! (" + block.getLocation().getBlockX() + ","
						+block.getLocation().getBlockY()+","+block.getLocation().getBlockZ()+")");
				return;
			}
			else if(event.getAction()==Action.RIGHT_CLICK_BLOCK)
			{
				event.setCancelled(true);
				Main.catchedLocation2.put(player, event.getClickedBlock().getLocation());
				player.sendMessage(ChatColor.YELLOW + "[SYSTEM] : 두 번째 지점 설정 완료! (" + event.getClickedBlock().getLocation().getBlockX() + ","
						+event.getClickedBlock().getLocation().getBlockY()+","+event.getClickedBlock().getLocation().getBlockZ()+")");
				return;
			}
		}
		return;
	}
	
	public void PlayerGetItem(PlayerPickupItemEvent event)
	{
		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
	  	if(Event_YC.isExit("Stats/" + event.getPlayer().getUniqueId()+".yml") == false)
	  		new GBD.GoldBigDragon_Advanced.Config.StatConfig().CreateNewStats(event.getPlayer());
		YamlManager YM = Event_YC.getNewConfig("Stats/" + event.getPlayer().getUniqueId()+".yml");
		String ItemName = null;
		if((event.getItem().getItemStack().hasItemMeta() == true && event.getItem().getItemStack().getItemMeta().hasDisplayName()))
		{
			ItemName = event.getItem().getItemStack().getItemMeta().getDisplayName();
			if(ItemName.contains(GBD.GoldBigDragon_Advanced.Main.ServerOption.Money) == true && event.getItem().getItemStack().getEnchantmentLevel(Enchantment.LUCK) == 500)
			{
			  	if(Event_YC.isExit("Stats/" + event.getPlayer().getUniqueId()+".yml") == false)
			  		new GBD.GoldBigDragon_Advanced.Config.StatConfig().CreateNewStats(event.getPlayer());

				YM = Event_YC.getNewConfig("Stats/" + event.getPlayer().getUniqueId()+".yml");
				
				YM.set("Stat.Money", YM.getLong("Stat.Money") + Long.parseLong(ItemName.split("§f§f§f§l")[1]));
				YM.saveConfig();
				event.setCancelled(true);
				event.getItem().remove();
				new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ITEM_PICKUP, 0.5F,1.2F);
			}
		}
		else
		{
			ItemName = SetItemDefaultName(event.getItem().getItemStack().getTypeId(),event.getItem().getItemStack().getData().getData());
		}
	  	if(YM.getBoolean("Alert.ItemPickUp") == true)
		{
			if(ItemName.contains(GBD.GoldBigDragon_Advanced.Main.ServerOption.Money) == true)
				new GBD.GoldBigDragon_Advanced.Effect.PacketSender().sendActionBar(event.getPlayer(), ChatColor.GRAY+""+ChatColor.BOLD+""+""+ChatColor.BOLD+ChatColor.BOLD+""+ChatColor.BOLD+"("+""+ChatColor.BOLD+ItemName+""+ChatColor.GRAY+""+ChatColor.BOLD+")");
			else
				new GBD.GoldBigDragon_Advanced.Effect.PacketSender().sendActionBar(event.getPlayer(), ChatColor.GRAY+""+ChatColor.BOLD+""+""+ChatColor.BOLD+ChatColor.BOLD+""+ChatColor.BOLD+"("+""+ChatColor.BOLD+ItemName+""+" "+ChatColor.GRAY+""+ChatColor.BOLD+event.getItem().getItemStack().getAmount()+"개)");
		}
	  	return;
	}

	public String SetItemDefaultName(int itemCode,byte itemData)
	{
		String name = "지정되지 않은 아이템";
		switch (itemCode)
		{
			case 1:
				switch(itemData)
				{
				case 0:
					return ChatColor.GRAY+""+ChatColor.BOLD+"돌";
				case 1:
					return ChatColor.RED+""+ChatColor.BOLD+"화강암";
				case 2:
					return ChatColor.RED+""+ChatColor.BOLD+"부드러운 화강암";
				case 3:
					return ChatColor.WHITE+""+ChatColor.BOLD+"섬록암";
				case 4:
					return ChatColor.WHITE+""+ChatColor.BOLD+"부드러운 섬록암";
				case 5:
					return ChatColor.GRAY+""+ChatColor.BOLD+"안산암";
				case 6:
					return ChatColor.GRAY+""+ChatColor.BOLD+"부드러운 안산암";
				}
				break;
			case 2: return ChatColor.GREEN+""+ChatColor.BOLD+"잔디 블록";
			case 3:
				switch(itemData)
				{
				case 0:
					return ChatColor.GOLD+""+ChatColor.BOLD+"흙";
				case 1:
					return ChatColor.GOLD+""+ChatColor.BOLD+"거친 흙";
				case 2:
					return ChatColor.GOLD+""+ChatColor.BOLD+"회백토";
				}
				break;
			case 4: return ChatColor.GRAY+""+ChatColor.BOLD+"조약돌";
			case 5:
				switch(itemData)
				{
				case 0:
					return ChatColor.GOLD+""+ChatColor.BOLD+"참나무 목재";
				case 1:
					return ChatColor.BLACK+""+ChatColor.BOLD+"가문비나무 목재";
				case 2:
					return ChatColor.WHITE+""+ChatColor.BOLD+"자작나무 목재";
				case 3:
					return ChatColor.GOLD+""+ChatColor.BOLD+"정글 나무 목재";
				case 4:
					return ChatColor.GOLD+""+ChatColor.BOLD+"아카시아나무 목재";
				case 5:
					return ChatColor.BLACK+""+ChatColor.BOLD+"짙은 참나무 목재";
				}
				break;
			case 6:
				switch(itemData)
				{
				case 0:
					return ChatColor.GOLD+""+ChatColor.BOLD+"참나무 묘목";
				case 1:
					return ChatColor.BLACK+""+ChatColor.BOLD+"가문비나무 묘목";
				case 2:
					return ChatColor.WHITE+""+ChatColor.BOLD+"자작나무 묘목";
				case 3:
					return ChatColor.GOLD+""+ChatColor.BOLD+"정글 나무 묘목";
				case 4:
					return ChatColor.GOLD+""+ChatColor.BOLD+"아카시아나무 묘목";
				case 5:
					return ChatColor.BLACK+""+ChatColor.BOLD+"짙은 참나무 묘목";
				}
				break;
			case 7: return ChatColor.BLACK+""+ChatColor.BOLD+"기반암";
			case 8: return ChatColor.BLUE+""+ChatColor.BOLD+"물";
			case 9: return ChatColor.BLUE+""+ChatColor.BOLD+"흐르지 않는 물";
			case 10: return ChatColor.RED+""+ChatColor.BOLD+"용암";
			case 11: return ChatColor.RED+""+ChatColor.BOLD+"흐르지 않는 용암";
			case 12:
				switch(itemData)
				{
				case 0:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"모래";
				case 1:
					return ChatColor.RED+""+ChatColor.BOLD+"붉은 모래";
				}
				break;
			case 13: return ChatColor.GRAY+""+ChatColor.BOLD+"자갈";
			case 14: return ChatColor.YELLOW+""+ChatColor.BOLD+"금광석";
			case 15: return ChatColor.GRAY+""+ChatColor.BOLD+"철광석";
			case 16: return ChatColor.BLACK+""+ChatColor.BOLD+"석탄 광석";
			case 17:
				switch(itemData%4)
				{
				case 0:
					return ChatColor.GOLD+""+ChatColor.BOLD+"참나무";
				case 1:
					return ChatColor.BLACK+""+ChatColor.BOLD+"가문비나무";
				case 2:
					return ChatColor.WHITE+""+ChatColor.BOLD+"자작나무";
				case 3:
					return ChatColor.GOLD+""+ChatColor.BOLD+"정글 나무";
				}
				break;
			case 18:
				switch(itemData)
				{
				case 0:
					return ChatColor.GREEN+""+ChatColor.BOLD+"참나무 잎";
				case 1:
					return ChatColor.GREEN+""+ChatColor.BOLD+"가문비나무 잎";
				case 2:
					return ChatColor.GREEN+""+ChatColor.BOLD+"자작나무 잎";
				case 3:
					return ChatColor.GREEN+""+ChatColor.BOLD+"정글 나무 잎";
				}
				break;
			case 19:
				switch(itemData)
				{
				case 0:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"스펀지";
				case 1:
					return ChatColor.DARK_AQUA+""+ChatColor.BOLD+"젖은 스펀지";
				}
				break;
			case 20: return ChatColor.WHITE+""+ChatColor.BOLD+"유리";
			case 21: return ChatColor.BLUE+""+ChatColor.BOLD+"청금석 원석";
			case 22: return ChatColor.BLUE+""+ChatColor.BOLD+"청금석 블록";
			case 23: return ChatColor.GRAY+""+ChatColor.BOLD+"발사기";
			case 24:
				switch(itemData)
				{
				case 0:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"사암";
				case 1:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"조각된 사암";
				case 2:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"부드러운 사암";
				}
				break;
			case 25: return ChatColor.GOLD+""+ChatColor.BOLD+"노트 블록";
			case 26: return ChatColor.RED+""+ChatColor.BOLD+"반토막 침대";
			case 27: return ChatColor.YELLOW+""+ChatColor.BOLD+"파워 레일";
			case 28: return ChatColor.GRAY+""+ChatColor.BOLD+"디텍터 레일";
			case 29: return ChatColor.GREEN+""+ChatColor.BOLD+"끈끈이 피스톤";
			case 30: return ChatColor.WHITE+""+ChatColor.BOLD+"거미줄";
			case 31:
				switch(itemData)
				{
				case 1:
					return ChatColor.GREEN+""+ChatColor.BOLD+"잔디";
				case 2:
					return ChatColor.GREEN+""+ChatColor.BOLD+"고사리";
				}
				break;
			case 32: return ChatColor.GREEN+""+ChatColor.BOLD+"마른 덤불";
			case 33: return ChatColor.GOLD+""+ChatColor.BOLD+"피스톤";
			case 34: return ChatColor.GOLD+""+ChatColor.BOLD+"피스톤 머리";
			case 35:
				switch(itemData)
				{
				case 0:
					return ChatColor.WHITE+""+ChatColor.BOLD+"흰 양털";
				case 1:
					return ChatColor.GOLD+""+ChatColor.BOLD+"주황색 양털";
				case 2:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"자홍색 양털";
				case 3:
					return ChatColor.AQUA+""+ChatColor.BOLD+"하늘색 양털";
				case 4:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"노란색 양털";
				case 5:
					return ChatColor.GREEN+""+ChatColor.BOLD+"연두색 양털";
				case 6:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"분홍색 양털";
				case 7:
					return ChatColor.DARK_GRAY+""+ChatColor.BOLD+"회색 양털";
				case 8:
					return ChatColor.GRAY+""+ChatColor.BOLD+"밝은 회색 양털";
				case 9:
					return ChatColor.DARK_AQUA+""+ChatColor.BOLD+"청록색 양털";
				case 10:
					return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"보라색 양털";
				case 11:
					return ChatColor.DARK_BLUE+""+ChatColor.BOLD+"파란색 양털";
				case 12:
					return ChatColor.GOLD+""+ChatColor.BOLD+"갈색 양털";
				case 13:
					return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"초록색 양털";
				case 14:
					return ChatColor.DARK_RED+""+ChatColor.BOLD+"빨간색 양털";
				case 15:
					return ChatColor.BLACK+""+ChatColor.BOLD+"검은색 양털";
				}
				break;
			case 37: return ChatColor.YELLOW+""+ChatColor.BOLD+"민들레";
			case 38:
				switch(itemData)
				{
				case 0:
					return ChatColor.RED+""+ChatColor.BOLD+"양귀비";
				case 1:
					return ChatColor.AQUA+""+ChatColor.BOLD+"파란 난초";
				case 2:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"파꽃";
				case 3:
					return ChatColor.WHITE+""+ChatColor.BOLD+"푸른 삼백초";
				case 4:
					return ChatColor.RED+""+ChatColor.BOLD+"빨간색 튤립";
				case 5:
					return ChatColor.GOLD+""+ChatColor.BOLD+"주황색 튤립";
				case 6:
					return ChatColor.WHITE+""+ChatColor.BOLD+"하얀색 튤립";
				case 7:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"분홍색 튤립";
				case 8:
					return ChatColor.WHITE+""+ChatColor.BOLD+"데이지";
				}
				break;
			case 39: return ChatColor.GOLD+""+ChatColor.BOLD+"갈색 버섯";
			case 40: return ChatColor.RED+""+ChatColor.BOLD+"붉은 버섯";
			case 41: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 블록";
			case 42: return ChatColor.WHITE+""+ChatColor.BOLD+"철 블록";
			case 43: return ChatColor.GRAY+""+ChatColor.BOLD+"겹쳐진 돌 반 블록";
			case 44:
				switch(itemData%7)
				{
				case 0:
					return ChatColor.GRAY+""+ChatColor.BOLD+"돌 반 블록";
				case 1:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"사암 반 블록";
				case 2:
					return ChatColor.GRAY+""+ChatColor.BOLD+"조약돌 반 블록";
				case 3:
					return ChatColor.RED+""+ChatColor.BOLD+"벽돌 반 블록";
				case 4:
					return ChatColor.GRAY+""+ChatColor.BOLD+"석재 벽돌 반 블록";
				case 5:
					return ChatColor.DARK_RED+""+ChatColor.BOLD+"네더 벽돌 반 블록";
				case 6:
					return ChatColor.WHITE+""+ChatColor.BOLD+"석영 반 블록";
				}
				break;
			case 45: return ChatColor.RED+""+ChatColor.BOLD+"벽돌";
			case 46: return ChatColor.DARK_RED+""+ChatColor.BOLD+"TNT";
			case 47: return ChatColor.GOLD+""+ChatColor.BOLD+"책장";
			case 48: return ChatColor.GREEN+""+ChatColor.BOLD+"이끼 낀 돌";
			case 49: return ChatColor.BLACK+""+ChatColor.BOLD+"흑요석";
			case 50: return ChatColor.YELLOW+""+ChatColor.BOLD+"횃불";
			case 51: return ChatColor.RED+""+ChatColor.BOLD+"불";
			case 52: return ChatColor.GRAY+""+ChatColor.BOLD+"몬스터 스포너";
			case 53: return ChatColor.GOLD+""+ChatColor.BOLD+"참나무 계단";
			case 54: return ChatColor.GOLD+""+ChatColor.BOLD+"상자";
			case 55: return ChatColor.RED+""+ChatColor.BOLD+"레드스톤";
			case 56: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 원석";
			case 57: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 블록";
			case 58: return ChatColor.GOLD+""+ChatColor.BOLD+"작업대";
			case 59: return ChatColor.GREEN+""+ChatColor.BOLD+"농작물";
			case 60: return ChatColor.GOLD+""+ChatColor.BOLD+"밭 블록";
			case 61: return ChatColor.GRAY+""+ChatColor.BOLD+"화로";
			case 62: return ChatColor.YELLOW+""+ChatColor.BOLD+"불타는 화로";
			case 63: return ChatColor.GOLD+""+ChatColor.BOLD+"표지판";
			case 64: return ChatColor.GOLD+""+ChatColor.BOLD+"문짝 반";
			case 65: return ChatColor.GOLD+""+ChatColor.BOLD+"사다리";
			case 66: return ChatColor.GRAY+""+ChatColor.BOLD+"레일";
			case 67: return ChatColor.GRAY+""+ChatColor.BOLD+"석재 계단";
			case 68: return ChatColor.GOLD+""+ChatColor.BOLD+"붙어있는 표지판";
			case 69: return ChatColor.GRAY+""+ChatColor.BOLD+"레버";
			case 70: return ChatColor.GRAY+""+ChatColor.BOLD+"돌 감압판";
			case 71: return ChatColor.WHITE+""+ChatColor.BOLD+"철 문짝 반";
			case 72: return ChatColor.GOLD+""+ChatColor.BOLD+"나무 감압판";
			case 73: return ChatColor.RED+""+ChatColor.BOLD+"레드스톤 원석";
			case 74: return ChatColor.RED+""+ChatColor.BOLD+"빛나는 레드스톤 원석";
			case 75: return ChatColor.GRAY+""+ChatColor.BOLD+"빛 잃은 레드스톤 횃불";
			case 76: return ChatColor.RED+""+ChatColor.BOLD+"레드스톤 횃불";
			case 77: return ChatColor.GRAY+""+ChatColor.BOLD+"돌 버튼";
			case 78: return ChatColor.WHITE+""+ChatColor.BOLD+"눈";
			case 79: return ChatColor.AQUA+""+ChatColor.BOLD+"얼음";
			case 80: return ChatColor.WHITE+""+ChatColor.BOLD+"눈 블록";
			case 81: return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"선인장";
			case 82: return ChatColor.GRAY+""+ChatColor.BOLD+"점토";
			case 83: return ChatColor.GREEN+""+ChatColor.BOLD+"사탕 수수";
			case 84: return ChatColor.GOLD+""+ChatColor.BOLD+"주크 박스";
			case 85: return ChatColor.GOLD+""+ChatColor.BOLD+"참나무 울타리";
			case 86: return ChatColor.GOLD+""+ChatColor.BOLD+"호박";
			case 87: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"네더랙";
			case 88: return ChatColor.GOLD+""+ChatColor.BOLD+"소울 샌드";
			case 89: return ChatColor.YELLOW+""+ChatColor.BOLD+"발광석";
			case 90: return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"네더 포탈";
			case 91: return ChatColor.YELLOW+""+ChatColor.BOLD+"잭 오 랜턴";
			case 92: return ChatColor.WHITE+""+ChatColor.BOLD+"케이크 블록";
			case 93: return ChatColor.GRAY+""+ChatColor.BOLD+"빛 잃은 레드스톤 중계기";
			case 94: return ChatColor.RED+""+ChatColor.BOLD+"레드스톤 중계기";
			case 95:
				switch(itemData)
				{
				case 0:
					return ChatColor.WHITE+""+ChatColor.BOLD+"하얀색 염색된 유리";
				case 1:
					return ChatColor.GOLD+""+ChatColor.BOLD+"주황색 염색된 유리";
				case 2:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"자홍색 염색된 유리";
				case 3:
					return ChatColor.AQUA+""+ChatColor.BOLD+"하늘색 염색된 유리";
				case 4:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"노란색 염색된 유리";
				case 5:
					return ChatColor.GREEN+""+ChatColor.BOLD+"연두색 염색된 유리";
				case 6:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"분홍색 염색된 유리";
				case 7:
					return ChatColor.DARK_GRAY+""+ChatColor.BOLD+"회색 염색된 유리";
				case 8:
					return ChatColor.GRAY+""+ChatColor.BOLD+"밝은 회색 염색된 유리";
				case 9:
					return ChatColor.DARK_AQUA+""+ChatColor.BOLD+"청록색 염색된 유리";
				case 10:
					return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"보라색 염색된 유리";
				case 11:
					return ChatColor.DARK_BLUE+""+ChatColor.BOLD+"파란색 염색된 유리";
				case 12:
					return ChatColor.GOLD+""+ChatColor.BOLD+"갈색 염색된 유리";
				case 13:
					return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"초록색 염색된 유리";
				case 14:
					return ChatColor.DARK_RED+""+ChatColor.BOLD+"빨간색 염색된 유리";
				case 15:
					return ChatColor.BLACK+""+ChatColor.BOLD+"검정색 염색된 유리";
				}
				break;
			case 96: return ChatColor.GOLD+""+ChatColor.BOLD+"다락문";
			case 97:
				switch(itemData)
				{
				case 0:
					return ChatColor.GRAY+""+ChatColor.BOLD+"돌 몬스터 알";
				case 1:
					return ChatColor.GRAY+""+ChatColor.BOLD+"조약돌 몬스터 알";
				case 2:
					return ChatColor.GRAY+""+ChatColor.BOLD+"석재 벽돌 몬스터 알";
				case 3:
					return ChatColor.GRAY+""+ChatColor.BOLD+"이끼 낀 석재 벽돌 몬스터 알";
				case 4:
					return ChatColor.GRAY+""+ChatColor.BOLD+"금 간 석재 벽돌 몬스터 알";
				case 5:
					return ChatColor.GRAY+""+ChatColor.BOLD+"조각된 석재 벽돌 몬스터 알";
				}
				break;
			case 98:
				switch(itemData)
				{
				case 0:
					return ChatColor.GRAY+""+ChatColor.BOLD+"석재 벽돌";
				case 1:
					return ChatColor.GRAY+""+ChatColor.BOLD+"이끼 낀 석재 벽돌";
				case 2:
					return ChatColor.GRAY+""+ChatColor.BOLD+"금 간 석재 벽돌";
				case 3:
					return ChatColor.GRAY+""+ChatColor.BOLD+"조각된 석재 벽돌";
				}
				break;
			case 99: return ChatColor.GOLD+""+ChatColor.BOLD+"대형 갈색 버섯 갓 블록";
			case 100: return ChatColor.RED+""+ChatColor.BOLD+"대형 붉은 버섯 갓 블록";
			case 101: return ChatColor.GRAY+""+ChatColor.BOLD+"철창";
			case 102: return ChatColor.WHITE+""+ChatColor.BOLD+"유리판";
			case 103: return ChatColor.GREEN+""+ChatColor.BOLD+"수박 블록";
			
			
			case 106: return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"덩굴";
			case 107: return ChatColor.GOLD+""+ChatColor.BOLD+"참나무 울타리 문";
			case 108: return ChatColor.RED+""+ChatColor.BOLD+"벽돌 계단";
			case 109: return ChatColor.GRAY+""+ChatColor.BOLD+"석재 벽돌 계단";
			case 110: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"균사체";
			case 111: return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"연꽃잎";
			case 112: return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"네더 벽돌";
			case 113: return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"네더 벽돌 울타리";
			case 114: return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"네더 벽돌 계단";
			case 115: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"네더 와트";
			case 116: return ChatColor.AQUA+""+ChatColor.BOLD+"인챈트 테이블";
			case 117: return ChatColor.YELLOW+""+ChatColor.BOLD+"양조기";
			case 118: return ChatColor.GRAY+""+ChatColor.BOLD+"가마솥";
			case 119: return ChatColor.BLACK+""+ChatColor.BOLD+"엔더월드 포탈";
			case 120: return ChatColor.YELLOW+""+ChatColor.BOLD+"엔더 포탈";
			case 121: return ChatColor.YELLOW+""+ChatColor.BOLD+"엔드 스톤";
			case 122: return ChatColor.BLACK+""+ChatColor.BOLD+"드래곤 알";
			case 123: return ChatColor.GOLD+""+ChatColor.BOLD+"레드스톤 조명";
			case 124: return ChatColor.YELLOW+""+ChatColor.BOLD+"빛나는 레드스톤 조명";
			case 125: return ChatColor.GOLD+""+ChatColor.BOLD+"겹쳐진 나무 반 블록";
			case 126:
				switch(itemData%6)
				{
				case 0:
					return ChatColor.GOLD+""+ChatColor.BOLD+"참나무 반 블록";
				case 1:
					return ChatColor.BLACK+""+ChatColor.BOLD+"가문비나무 반 블록";
				case 2:
					return ChatColor.WHITE+""+ChatColor.BOLD+"자작나무 반 블록";
				case 3:
					return ChatColor.GOLD+""+ChatColor.BOLD+"정글 나무 반 블록";
				case 4:
					return ChatColor.GOLD+""+ChatColor.BOLD+"아카시아나무 반 블록";
				case 5:
					return ChatColor.BLACK+""+ChatColor.BOLD+"짙은 참나무 반 블록";
				}
				break;
			case 127: return ChatColor.GOLD+""+ChatColor.BOLD+"코코아 열매";
			case 128: return ChatColor.YELLOW+""+ChatColor.BOLD+"사암 계단";
			case 129: return ChatColor.GREEN+""+ChatColor.BOLD+"에메랄드 원석";
			case 130: return ChatColor.BLACK+""+ChatColor.BOLD+"엔더 상자";
			case 131: return ChatColor.GRAY+""+ChatColor.BOLD+"철사 덫 갈고리";
			case 132: return ChatColor.GRAY+""+ChatColor.BOLD+"철사 덫 갈고리 실";
			case 133: return ChatColor.GREEN+""+ChatColor.BOLD+"에메랄드 블록";
			case 134: return ChatColor.GOLD+""+ChatColor.BOLD+"가문비 나무 계단";
			case 135: return ChatColor.GOLD+""+ChatColor.BOLD+"자작 나무 계단";
			case 136: return ChatColor.GOLD+""+ChatColor.BOLD+"정글 나무 계단";
			case 137: return ChatColor.RED+""+ChatColor.BOLD+"커"+""+ChatColor.BOLD+ChatColor.GOLD+""+ChatColor.BOLD+"맨"+""+ChatColor.BOLD+ChatColor.YELLOW+""+ChatColor.BOLD+"드"+""+ChatColor.BOLD+ChatColor.DARK_GREEN+""+ChatColor.BOLD+" 블"+""+ChatColor.BOLD+ChatColor.BLUE+""+ChatColor.BOLD+"록";
			case 138: return ChatColor.AQUA+""+ChatColor.BOLD+"신호기";
			case 139: return ChatColor.GRAY+""+ChatColor.BOLD+"조약돌 담장";
			case 140: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"화분";
			case 141: return ChatColor.GREEN+""+ChatColor.BOLD+"농작물";
			case 142: return ChatColor.GREEN+""+ChatColor.BOLD+"농작물";
			case 143: return ChatColor.GOLD+""+ChatColor.BOLD+"나무 버튼";
			case 144: return ChatColor.WHITE+""+ChatColor.BOLD+"미스테리 해골";
			case 145:
				switch(itemData)
				{
				case 0:
					return ChatColor.GRAY+""+ChatColor.BOLD+"모루";
				case 1:
					return ChatColor.GRAY+""+ChatColor.BOLD+"약간 손상된 모루";
				case 2:
					return ChatColor.GRAY+""+ChatColor.BOLD+"심각하게 손상된 모루";
				}
				break;
			case 146: return ChatColor.RED+""+ChatColor.BOLD+"덫 상자";
			case 147: return ChatColor.GOLD+""+ChatColor.BOLD+"무게 감압판(경형)";
			case 148: return ChatColor.WHITE+""+ChatColor.BOLD+"무게 감압판(중형)";
			case 149: return ChatColor.RED+""+ChatColor.BOLD+"레드스톤 비교기";
			case 150: return ChatColor.YELLOW+""+ChatColor.BOLD+"빛나는 레드스톤 비교기";
			case 151: return ChatColor.RED+""+ChatColor.BOLD+"햇빛 감지기";
			case 152: return ChatColor.RED+""+ChatColor.BOLD+"레드스톤 블록";
			case 153: return ChatColor.WHITE+""+ChatColor.BOLD+"네더 석영 원석";
			case 154: return ChatColor.GRAY+""+ChatColor.BOLD+"깔때기";
			case 155:
				switch(itemData)
				{
				case 0:
					return ChatColor.WHITE+""+ChatColor.BOLD+"석영 블록";
				case 1:
					return ChatColor.WHITE+""+ChatColor.BOLD+"조각된 석영 블록";
				default:
					return ChatColor.WHITE+""+ChatColor.BOLD+"석영 기둥 블록";
				}
			case 156: return ChatColor.WHITE+""+ChatColor.BOLD+"석영 계단";
			case 157: return ChatColor.GRAY+""+ChatColor.BOLD+"활성화 레일";
			case 158: return ChatColor.GRAY+""+ChatColor.BOLD+"공급기";
			case 159:
				switch(itemData)
				{
				case 0:
					return ChatColor.WHITE+""+ChatColor.BOLD+"흰색 염색된 점토";
				case 1:
					return ChatColor.GOLD+""+ChatColor.BOLD+"주황색 염색된 점토";
				case 2:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"자홍색 염색된 점토";
				case 3:
					return ChatColor.AQUA+""+ChatColor.BOLD+"하늘색 염색된 점토";
				case 4:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"노란색 염색된 점토";
				case 5:
					return ChatColor.GREEN+""+ChatColor.BOLD+"연두색 염색된 점토";
				case 6:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"분홍색 염색된 점토";
				case 7:
					return ChatColor.DARK_GRAY+""+ChatColor.BOLD+"회색 염색된 점토";
				case 8:
					return ChatColor.GRAY+""+ChatColor.BOLD+"밝은 회색 염색된 점토";
				case 9:
					return ChatColor.DARK_AQUA+""+ChatColor.BOLD+"청록색 염색된 점토";
				case 10:
					return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"보라색 염색된 점토";
				case 11:
					return ChatColor.DARK_BLUE+""+ChatColor.BOLD+"파란색 염색된 점토";
				case 12:
					return ChatColor.GOLD+""+ChatColor.BOLD+"갈색 염색된 점토";
				case 13:
					return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"초록색 염색된 점토";
				case 14:
					return ChatColor.DARK_RED+""+ChatColor.BOLD+"빨간색 염색된 점토";
				case 15:
					return ChatColor.BLACK+""+ChatColor.BOLD+"검은색 염색된 점토";
				}
				break;
			case 160:
				switch(itemData)
				{
				case 0:
					return ChatColor.WHITE+""+ChatColor.BOLD+"하얀색 염색된 유리판";
				case 1:
					return ChatColor.GOLD+""+ChatColor.BOLD+"주황색 염색된 유리판";
				case 2:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"자홍색 염색된 유리판";
				case 3:
					return ChatColor.AQUA+""+ChatColor.BOLD+"하늘색 염색된 유리판";
				case 4:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"노란색 염색된 유리판";
				case 5:
					return ChatColor.GREEN+""+ChatColor.BOLD+"연두색 염색된 유리판";
				case 6:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"분홍색 염색된 유리판";
				case 7:
					return ChatColor.DARK_GRAY+""+ChatColor.BOLD+"회색 염색된 유리판";
				case 8:
					return ChatColor.GRAY+""+ChatColor.BOLD+"밝은 회색 염색된 유리판";
				case 9:
					return ChatColor.DARK_AQUA+""+ChatColor.BOLD+"청록색 염색된 유리판";
				case 10:
					return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"보라색 염색된 유리판";
				case 11:
					return ChatColor.DARK_BLUE+""+ChatColor.BOLD+"파란색 염색된 유리판";
				case 12:
					return ChatColor.GOLD+""+ChatColor.BOLD+"갈색 염색된 유리판";
				case 13:
					return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"초록색 염색된 유리판";
				case 14:
					return ChatColor.DARK_RED+""+ChatColor.BOLD+"빨간색 염색된 유리판";
				case 15:
					return ChatColor.BLACK+""+ChatColor.BOLD+"검은색 염색된 유리판";
				}
				break;
			case 161:
				switch(itemData)
				{
				case 0:
					return ChatColor.GREEN+""+ChatColor.BOLD+"아카시아 잎";
				case 1:
					return ChatColor.GREEN+""+ChatColor.BOLD+"짙은 참나무 잎";
				}
				break;
			case 162:
				switch(itemData%2)
				{
				case 0:
					return ChatColor.GOLD+""+ChatColor.BOLD+"아카시아 나무";
				case 1:
					return ChatColor.BLACK+""+ChatColor.BOLD+"짙은 참나무";
				}
				break;
			case 163: return ChatColor.GOLD+""+ChatColor.BOLD+"아카시아 나무 계단";
			case 164: return ChatColor.GOLD+""+ChatColor.BOLD+"짙은 참나무 계단";
			case 165: return ChatColor.GREEN+""+ChatColor.BOLD+"슬라임 블록";
			case 166: return ChatColor.WHITE+""+ChatColor.BOLD+"바리게이트(투명블록)";
			case 167: return ChatColor.WHITE+""+ChatColor.BOLD+"철 다락문";
			case 168:
				switch(itemData)
				{
				case 0:
					return ChatColor.AQUA+""+ChatColor.BOLD+"프리즈마린";
				case 1:
					return ChatColor.AQUA+""+ChatColor.BOLD+"프리즈마린 벽돌";
				case 2:
					return ChatColor.DARK_AQUA+""+ChatColor.BOLD+"어두운 프리즈마린 벽돌";
				}
				break;
			case 169: return ChatColor.AQUA+""+ChatColor.BOLD+"바다 랜턴";
			case 170: return ChatColor.GOLD+""+ChatColor.BOLD+"건초 더미";
			case 171:
				switch(itemData)
				{
				case 0:
					return ChatColor.WHITE+""+ChatColor.BOLD+"흰색 양탄자";
				case 1:
					return ChatColor.GOLD+""+ChatColor.BOLD+"주황색 양탄자";
				case 2:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"자홍색 양탄자";
				case 3:
					return ChatColor.AQUA+""+ChatColor.BOLD+"하늘색 양탄자";
				case 4:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"노란색 양탄자";
				case 5:
					return ChatColor.GREEN+""+ChatColor.BOLD+"연두색 양탄자";
				case 6:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"분홍색 양탄자";
				case 7:
					return ChatColor.DARK_GRAY+""+ChatColor.BOLD+"회색 양탄자";
				case 8:
					return ChatColor.GRAY+""+ChatColor.BOLD+"밝은 회색 양탄자";
				case 9:
					return ChatColor.DARK_AQUA+""+ChatColor.BOLD+"청록색 양탄자";
				case 10:
					return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"보라색 양탄자";
				case 11:
					return ChatColor.DARK_BLUE+""+ChatColor.BOLD+"파란색 양탄자";
				case 12:
					return ChatColor.GOLD+""+ChatColor.BOLD+"갈색 양탄자";
				case 13:
					return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"초록색 양탄자";
				case 14:
					return ChatColor.DARK_RED+""+ChatColor.BOLD+"빨간색 양탄자";
				case 15:
					return ChatColor.BLACK+""+ChatColor.BOLD+"검은색 양탄자";
				}
				break;
			case 172: return ChatColor.GRAY+""+ChatColor.BOLD+"굳은 점토";
			case 173: return ChatColor.BLACK+""+ChatColor.BOLD+"석탄 블록";
			case 174: return ChatColor.MAGIC+""+ChatColor.BOLD+"단단한 얼음";
			case 175:
				switch(itemData)
				{
				case 0:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"해바라기";
				case 1:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"라일락";
				case 2:
					return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"큰 잔디";
				case 3:
					return ChatColor.AQUA+""+ChatColor.BOLD+"큰 고사리";
				case 4:
					return ChatColor.RED+""+ChatColor.BOLD+"장미 덤불";
				case 5:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"모란";
				}
				break;
			case 176: return ChatColor.WHITE+""+ChatColor.BOLD+"현수막(아래 블록)";
			case 177: return ChatColor.WHITE+""+ChatColor.BOLD+"현수막(윗 블록)";
			case 178: return ChatColor.GRAY+""+ChatColor.BOLD+"빛 잃은 햇빛 감지기";
			case 179:
				switch(itemData)
				{
				case 0:
					return ChatColor.RED+""+ChatColor.BOLD+"붉은 사암";
				case 1:
					return ChatColor.RED+""+ChatColor.BOLD+"조각된 붉은 사암";
				case 2:
					return ChatColor.RED+""+ChatColor.BOLD+"부드러운 붉은 사암";
				}
				break;
			case 180: return ChatColor.RED+""+ChatColor.BOLD+"붉은 사암 계단";
			case 181: return ChatColor.RED+""+ChatColor.BOLD+"겹쳐진 붉은 사암 반 블록";
			case 182: return ChatColor.RED+""+ChatColor.BOLD+"붉은 사암 반 블록";
			case 183: return ChatColor.GOLD+""+ChatColor.BOLD+"가문비 나무 울타리 문";
			case 184: return ChatColor.GOLD+""+ChatColor.BOLD+"자작 나무 울타리 문";
			case 185: return ChatColor.GOLD+""+ChatColor.BOLD+"정글 나무 울타리 문";
			case 186: return ChatColor.GOLD+""+ChatColor.BOLD+"짙은 참나무 울타리 문";
			case 187: return ChatColor.GOLD+""+ChatColor.BOLD+"아카시아 나무 울타리 문";
			case 188: return ChatColor.GOLD+""+ChatColor.BOLD+"가문비 나무 울타리";
			case 189: return ChatColor.GOLD+""+ChatColor.BOLD+"자작 나무 울타리";
			case 190: return ChatColor.GOLD+""+ChatColor.BOLD+"정글 나무 울타리";
			case 191: return ChatColor.GOLD+""+ChatColor.BOLD+"짙은 참나무 울타리";
			case 192: return ChatColor.GOLD+""+ChatColor.BOLD+"아카시아 나무 울타리";
			case 193: return ChatColor.GOLD+""+ChatColor.BOLD+"가문비 나무 문짝 반";
			case 194: return ChatColor.GOLD+""+ChatColor.BOLD+"자작 나무 문짝 반";
			case 195: return ChatColor.GOLD+""+ChatColor.BOLD+"정글 나무 문짝 반";
			case 196: return ChatColor.GOLD+""+ChatColor.BOLD+"아카시아 나무 문짝 반";
			case 197: return ChatColor.GOLD+""+ChatColor.BOLD+"짙은 참나무 문짝 반";
			
			case 256: return ChatColor.WHITE+""+ChatColor.BOLD+"철 삽";
			case 257: return ChatColor.WHITE+""+ChatColor.BOLD+"철 곡괭이";
			case 258: return ChatColor.WHITE+""+ChatColor.BOLD+"철 도끼";
			case 259: return ChatColor.WHITE+""+ChatColor.BOLD+"라이터";
			case 260: return ChatColor.RED+""+ChatColor.BOLD+"사과";
			case 261: return ChatColor.GOLD+""+ChatColor.BOLD+"활";
			case 262: return ChatColor.GOLD+""+ChatColor.BOLD+"화살";
			case 263:
				switch(itemData)
				{
				case 0:
					return ChatColor.BLACK+""+ChatColor.BOLD+"석탄";
				case 1:
					return ChatColor.BLACK+""+ChatColor.BOLD+"목탄";
				}
				break;
			case 264: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드";
			case 265: return ChatColor.WHITE+""+ChatColor.BOLD+"철괴";
			case 266: return ChatColor.YELLOW+""+ChatColor.BOLD+"금괴";
			case 267: return ChatColor.WHITE+""+ChatColor.BOLD+"철 검";
			case 268: return ChatColor.GOLD+""+ChatColor.BOLD+"나무 검";
			case 269: return ChatColor.GOLD+""+ChatColor.BOLD+"나무 삽";
			case 270: return ChatColor.GOLD+""+ChatColor.BOLD+"나무 곡괭이";
			case 271: return ChatColor.GOLD+""+ChatColor.BOLD+"나무 도끼";
			case 272: return ChatColor.GRAY+""+ChatColor.BOLD+"돌 검";
			case 273: return ChatColor.GRAY+""+ChatColor.BOLD+"돌 삽";
			case 274: return ChatColor.GRAY+""+ChatColor.BOLD+"돌 곡괭이";
			case 275: return ChatColor.GRAY+""+ChatColor.BOLD+"돌 도끼";
			case 276: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 검";
			case 277: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 삽";
			case 278: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 곡괭이";
			case 279: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 도끼";
			case 280: return ChatColor.GOLD+""+ChatColor.BOLD+"막대기";
			case 281: return ChatColor.GOLD+""+ChatColor.BOLD+"그릇";
			case 282: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"버섯 스튜";
			case 283: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 검";
			case 284: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 삽";
			case 285: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 곡괭이";
			case 286: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 도끼";
			case 287: return ChatColor.WHITE+""+ChatColor.BOLD+"실";
			case 288: return ChatColor.WHITE+""+ChatColor.BOLD+"깃털";
			case 289: return ChatColor.GRAY+""+ChatColor.BOLD+"화약";
			case 290: return ChatColor.GOLD+""+ChatColor.BOLD+"나무 괭이";
			case 291: return ChatColor.GRAY+""+ChatColor.BOLD+"돌 괭이";
			case 292: return ChatColor.WHITE+""+ChatColor.BOLD+"철 괭이";
			case 293: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 괭이";
			case 294: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 괭이";
			case 295: return ChatColor.GREEN+""+ChatColor.BOLD+"씨앗";
			case 296: return ChatColor.GOLD+""+ChatColor.BOLD+"밀";
			case 297: return ChatColor.GOLD+""+ChatColor.BOLD+"빵";
			case 298: return ChatColor.GOLD+""+ChatColor.BOLD+"가죽 모자";
			case 299: return ChatColor.GOLD+""+ChatColor.BOLD+"가죽 튜닉";
			case 300: return ChatColor.GOLD+""+ChatColor.BOLD+"가죽 바지";
			case 301: return ChatColor.GOLD+""+ChatColor.BOLD+"가죽 장화";
			case 302: return ChatColor.WHITE+""+ChatColor.BOLD+"사슬 투구";
			case 303: return ChatColor.WHITE+""+ChatColor.BOLD+"사슬 갑옷";
			case 304: return ChatColor.WHITE+""+ChatColor.BOLD+"사슬 레깅스";
			case 305: return ChatColor.WHITE+""+ChatColor.BOLD+"사슬 부츠";
			case 306: return ChatColor.WHITE+""+ChatColor.BOLD+"철 투구";
			case 307: return ChatColor.WHITE+""+ChatColor.BOLD+"철 갑옷";
			case 308: return ChatColor.WHITE+""+ChatColor.BOLD+"철 레깅스";
			case 309: return ChatColor.WHITE+""+ChatColor.BOLD+"철 부츠";
			case 310: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 투구";
			case 311: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 갑옷";
			case 312: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 레깅스";
			case 313: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 부츠";
			case 314: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 투구";
			case 315: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 갑옷";
			case 316: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 레깅스";
			case 317: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 부츠";
			case 318: return ChatColor.BLACK+""+ChatColor.BOLD+"부싯돌";
			case 319: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"익히지 않은 돼지고기";
			case 320: return ChatColor.GOLD+""+ChatColor.BOLD+"구운 돼지고기";
			case 321: return ChatColor.GRAY+""+ChatColor.BOLD+"그림";
			case 322:
				switch(itemData)
				{
				case 0:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"황금사과";
				case 1:
					return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"인챈트된 황금사과";
				}
				break;
			case 323: return ChatColor.GOLD+""+ChatColor.BOLD+"표지판";
			case 324: return ChatColor.GOLD+""+ChatColor.BOLD+"나무 문";
			case 325: return ChatColor.GRAY+""+ChatColor.BOLD+"양동이";
			case 326: return ChatColor.BLUE+""+ChatColor.BOLD+"물 양동이";
			case 327: return ChatColor.RED+""+ChatColor.BOLD+"용암 양동이";
			case 328: return ChatColor.GRAY+""+ChatColor.BOLD+"마인카트";
			case 329: return ChatColor.GOLD+""+ChatColor.BOLD+"안장";
			case 330: return ChatColor.WHITE+""+ChatColor.BOLD+"철 문";
			case 331: return ChatColor.RED+""+ChatColor.BOLD+"레드스톤";
			case 332: return ChatColor.WHITE+""+ChatColor.BOLD+"눈덩이";
			case 333: return ChatColor.GOLD+""+ChatColor.BOLD+"보트";
			case 334: return ChatColor.GOLD+""+ChatColor.BOLD+"가죽";
			case 335: return ChatColor.WHITE+""+ChatColor.BOLD+"우유";
			case 336: return ChatColor.RED+""+ChatColor.BOLD+"벽돌";
			case 337: return ChatColor.GRAY+""+ChatColor.BOLD+"점토";
			case 338: return ChatColor.GREEN+""+ChatColor.BOLD+"사탕수수";
			case 339: return ChatColor.WHITE+""+ChatColor.BOLD+"종이";
			case 340: return ChatColor.GOLD+""+ChatColor.BOLD+"책";
			case 341: return ChatColor.GREEN+""+ChatColor.BOLD+"슬라임 볼";
			case 342: return ChatColor.GOLD+""+ChatColor.BOLD+"상자 마인카트";
			case 343: return ChatColor.GRAY+""+ChatColor.BOLD+"화로 마인카트";
			case 344: return ChatColor.WHITE+""+ChatColor.BOLD+"달걀";
			case 345: return ChatColor.GRAY+""+ChatColor.BOLD+"나침반";
			case 346: return ChatColor.GOLD+""+ChatColor.BOLD+"낚싯대";
			case 347: return ChatColor.YELLOW+""+ChatColor.BOLD+"시계";
			case 348: return ChatColor.YELLOW+""+ChatColor.BOLD+"발광석 가루";
			case 349:
				switch(itemData)
				{
				case 0:
					return ChatColor.AQUA+""+ChatColor.BOLD+"날 생선";
				case 1:
					return ChatColor.RED+""+ChatColor.BOLD+"날 연어";
				case 2:
					return ChatColor.GOLD+""+ChatColor.BOLD+"흰동가리";
				case 3:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"복어";
				}
				break;
			case 350:
				switch(itemData)
				{
				case 0:
					return ChatColor.GRAY+""+ChatColor.BOLD+"익힌 생선";
				case 1:
					return ChatColor.RED+""+ChatColor.BOLD+"익힌 연어";
				}
				break;
			case 351:
				switch(itemData)
				{
				case 0:
					return ChatColor.BLACK+""+ChatColor.BOLD+"먹물";
				case 1:
					return ChatColor.RED+""+ChatColor.BOLD+"붉은 장미 염료";
				case 2:
					return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"초록 선인장 염료";
				case 3:
					return ChatColor.GOLD+""+ChatColor.BOLD+"코코아 콩";
				case 4:
					return ChatColor.DARK_BLUE+""+ChatColor.BOLD+"청금석";
				case 5:
					return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"보라색 염료";
				case 6:
					return ChatColor.DARK_AQUA+""+ChatColor.BOLD+"청록색 염료";
				case 7:
					return ChatColor.GRAY+""+ChatColor.BOLD+"밝은 회색 염료";
				case 8:
					return ChatColor.DARK_GRAY+""+ChatColor.BOLD+"회색 염료";
				case 9:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"분홍색 염료";
				case 10:
					return ChatColor.GREEN+""+ChatColor.BOLD+"연두색 염료";
				case 11:
					return ChatColor.YELLOW+""+ChatColor.BOLD+"노랑 민들레 염료";
				case 12:
					return ChatColor.AQUA+""+ChatColor.BOLD+"하늘색 염료";
				case 13:
					return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"자홍색 염료";
				case 14:
					return ChatColor.GOLD+""+ChatColor.BOLD+"주황색 염료";
				case 15:
					return ChatColor.WHITE+""+ChatColor.BOLD+"뼛가루";
				}
				break;
			case 352: return ChatColor.WHITE+""+ChatColor.BOLD+"뼈";
			case 353: return ChatColor.WHITE+""+ChatColor.BOLD+"설탕";
			case 354: return ChatColor.WHITE+""+ChatColor.BOLD+"케이크";
			case 355: return ChatColor.RED+""+ChatColor.BOLD+"침대";
			case 356: return ChatColor.RED+""+ChatColor.BOLD+"레드스톤 중계기";
			case 357: return ChatColor.GOLD+""+ChatColor.BOLD+"쿠키";
			
			case 359: return ChatColor.WHITE+""+ChatColor.BOLD+"가위";
			case 360: return ChatColor.GREEN+""+ChatColor.BOLD+"수박";
			case 361: return ChatColor.WHITE+""+ChatColor.BOLD+"호박씨";
			case 362: return ChatColor.BLACK+""+ChatColor.BOLD+"수박씨";
			case 363: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"익히지 않은 소고기";
			case 364: return ChatColor.GOLD+""+ChatColor.BOLD+"스테이크";
			case 365: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"익히지 않은 닭고기";
			case 366: return ChatColor.GOLD+""+ChatColor.BOLD+"구운 닭고기";
			case 367: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"썩은 고기";
			case 368: return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"엔더 진주";
			case 369: return ChatColor.YELLOW+""+ChatColor.BOLD+"블레이즈 막대";
			case 370: return ChatColor.WHITE+""+ChatColor.BOLD+"가스트의 눈물";
			case 371: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 조각";
			case 372: return ChatColor.RED+""+ChatColor.BOLD+"네더 와트";
			case 373: return ChatColor.BLUE+""+ChatColor.BOLD+"포션";
			case 374: return ChatColor.WHITE+""+ChatColor.BOLD+"유리병";
			case 375: return ChatColor.DARK_RED+""+ChatColor.BOLD+"거미 눈";
			case 376: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"발효된 거미 눈";
			case 377: return ChatColor.YELLOW+""+ChatColor.BOLD+"블레이즈 가루";
			case 378: return ChatColor.YELLOW+""+ChatColor.BOLD+"마그마 크림";
			case 379: return ChatColor.YELLOW+""+ChatColor.BOLD+"양조기";
			case 380: return ChatColor.GRAY+""+ChatColor.BOLD+"가마솥";
			case 381: return ChatColor.DARK_GREEN+""+ChatColor.BOLD+"엔더의 눈";
			case 382: return ChatColor.YELLOW+""+ChatColor.BOLD+"반짝이는 수박";
			case 383: return ChatColor.GRAY+""+ChatColor.BOLD+"몬스터 스폰 에그";
			case 384: return ChatColor.GREEN+""+ChatColor.BOLD+"경험치 병";
			case 385: return ChatColor.BLACK+""+ChatColor.BOLD+"화염구";
			case 386: return ChatColor.WHITE+""+ChatColor.BOLD+"책과 깃펜";
			case 388: return ChatColor.GREEN+""+ChatColor.BOLD+"에메랄드";
			case 389: return ChatColor.GOLD+""+ChatColor.BOLD+"아이템 액자";
			case 390: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"화분";
			case 391: return ChatColor.GOLD+""+ChatColor.BOLD+"당근";
			case 392: return ChatColor.GOLD+""+ChatColor.BOLD+"감자";
			case 393: return ChatColor.GOLD+""+ChatColor.BOLD+"구운 감자";
			case 394: return ChatColor.GREEN+""+ChatColor.BOLD+"독이 든 감자";
			case 395: return ChatColor.WHITE+""+ChatColor.BOLD+"빈 지도";
			case 396: return ChatColor.YELLOW+""+ChatColor.BOLD+"황금 당근";
			case 397: return ChatColor.WHITE+""+ChatColor.BOLD+"머리";
			case 398: return ChatColor.GOLD+""+ChatColor.BOLD+"당근 낚싯대";
			case 399: return ChatColor.AQUA+""+ChatColor.BOLD+"네더의 별";
			case 400: return ChatColor.GOLD+""+ChatColor.BOLD+"호박 파이";
			case 402: return ChatColor.GRAY+""+ChatColor.BOLD+"불꽃놀이 탄약";
			case 403: return ChatColor.YELLOW+""+ChatColor.BOLD+"마법이 부여된 책";
			case 404: return ChatColor.RED+""+ChatColor.BOLD+"레드스톤 비교기";
			case 405: return ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"네더 벽돌";
			case 406: return ChatColor.WHITE+""+ChatColor.BOLD+"네더 석영";
			case 407: return ChatColor.DARK_RED+""+ChatColor.BOLD+"TNT 마인카트";
			case 408: return ChatColor.GRAY+""+ChatColor.BOLD+"깔때기 마인카트";
			case 409: return ChatColor.AQUA+""+ChatColor.BOLD+"프리즈마린 조각";
			case 410: return ChatColor.AQUA+""+ChatColor.BOLD+"프리즈마린 수정";
			case 411: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"익히지 않은 토끼고기";
			case 412: return ChatColor.GOLD+""+ChatColor.BOLD+"구운 토끼고기";
			case 413: return ChatColor.GOLD+""+ChatColor.BOLD+"토끼 스튜";
			case 414: return ChatColor.GOLD+""+ChatColor.BOLD+"토끼 발";
			case 415: return ChatColor.GOLD+""+ChatColor.BOLD+"토끼 가죽";
			case 416: return ChatColor.WHITE+""+ChatColor.BOLD+"갑옷 거치대";
			case 417: return ChatColor.WHITE+""+ChatColor.BOLD+"철 말 갑옷";
			case 418: return ChatColor.YELLOW+""+ChatColor.BOLD+"금 말 갑옷";
			case 419: return ChatColor.AQUA+""+ChatColor.BOLD+"다이아몬드 말 갑옷";
			case 420: return ChatColor.GOLD+""+ChatColor.BOLD+"끈";
			case 421: return ChatColor.GOLD+""+ChatColor.BOLD+"이름표";
			case 423: return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"익히지 않은 양고기";
			case 424: return ChatColor.GOLD+""+ChatColor.BOLD+"구운 양고기";
			case 425: return ChatColor.WHITE+""+ChatColor.BOLD+"현수막";
			case 427: return ChatColor.GOLD+""+ChatColor.BOLD+"가문비 나무 문";
			case 428: return ChatColor.GOLD+""+ChatColor.BOLD+"자작 나무 문";
			case 429: return ChatColor.GOLD+""+ChatColor.BOLD+"정글 나무 문";
			case 430: return ChatColor.GOLD+""+ChatColor.BOLD+"아카시아 나무 문";
			case 431: return ChatColor.GOLD+""+ChatColor.BOLD+"짙은 참나무 문";
			case 2256: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.YELLOW+""+ChatColor.BOLD+"13"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2257: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.GREEN+""+ChatColor.BOLD+"cat"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2258: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.RED+""+ChatColor.BOLD+"blocks"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2259: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.DARK_RED+""+ChatColor.BOLD+"chirp"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2260: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.GREEN+""+ChatColor.BOLD+"far"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2261: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.MAGIC+""+ChatColor.BOLD+"mall"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2262: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"mellohi"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2263: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.BLACK+""+ChatColor.BOLD+"stal"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2264: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.WHITE+""+ChatColor.BOLD+"strad"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2265: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.DARK_GREEN+""+ChatColor.BOLD+"ward"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2266: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.BLACK+""+ChatColor.BOLD+"11"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
			case 2267: return ChatColor.GRAY+""+ChatColor.BOLD+"[음반] [곡 명 : "+""+ChatColor.BOLD+ChatColor.AQUA+""+ChatColor.BOLD+"wait"+""+ChatColor.BOLD+ChatColor.GRAY+""+ChatColor.BOLD+"]";
		}
		return name;
	}
}
