package GBD.GoldBigDragon_Advanced.Event;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import GBD.GoldBigDragon_Advanced.Main.Main;
import GBD.GoldBigDragon_Advanced.Util.YamlController;
import GBD.GoldBigDragon_Advanced.Util.YamlManager;

public class BlockBreak
{
	public void BlockBreaking(BlockBreakEvent event)
	{
		Player player = (Player) event.getPlayer();
		
		GBD.GoldBigDragon_Advanced.ETC.Area A = new GBD.GoldBigDragon_Advanced.ETC.Area();
		String[] Area = A.getAreaName(event.getBlock());
		if(Area != null)
		{
			YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
			YamlManager AreaConfig =GUI_YC.getNewConfig("Area/AreaList.yml");
			
			if(A.getAreaOption(Area[0], (char) 1) == false && event.getPlayer().isOp() == false)
			{
				event.setCancelled(true);
				new GBD.GoldBigDragon_Advanced.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				event.getPlayer().sendMessage(ChatColor.RED + "[SYSTEM] : " + ChatColor.YELLOW + Area[1] + ChatColor.RED + " 지역 에서는 블록 채집이 불가능합니다!");
				return;
			}
			if(AreaConfig.getInt(Area[0]+".RegenBlock")!=0)
			{
				Long UTC = (AreaConfig.getInt(Area[0]+".RegenBlock")*1000)+GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.nowUTC+new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(1, 1000);
				GBD.GoldBigDragon_Advanced.ServerTick.ServerTickScheduleObject STSO = new GBD.GoldBigDragon_Advanced.ServerTick.ServerTickScheduleObject(UTC, "A_RB");
				STSO.setMaxCount(-1);
				Block block =event.getBlock();
				STSO.setString((byte)1, block.getWorld().getName());//목적지 월드 이름 저장
				STSO.setInt((byte)0, block.getX());//블록X 위치저장
				STSO.setInt((byte)1, block.getY());//블록Y 위치저장
				STSO.setInt((byte)2, block.getZ());//블록Z 위치저장
				STSO.setInt((byte)3, block.getTypeId());//블록 ID저장
				STSO.setInt((byte)4, block.getData());//블록 DATA 저장
				
				GBD.GoldBigDragon_Advanced.ServerTick.ServerTickMain.Schedule.put(UTC, STSO);
			}
			if(player.getGameMode() != GameMode.CREATIVE)
			{
				String BlockData = event.getBlock().getTypeId()+":"+event.getBlock().getData();
				if(AreaConfig.contains(Area[0]+".Mining."+BlockData) == true)
				{
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					GBD.GoldBigDragon_Advanced.Event.ItemDrop ItemDrop = new GBD.GoldBigDragon_Advanced.Event.ItemDrop();
					Location loc = event.getBlock().getLocation();
					loc.setY(loc.getY()+1);
					loc.setX(loc.getX()+0.5);
					loc.setZ(loc.getZ()+0.5);
					if(AreaConfig.getString(Area[0]+".Mining."+BlockData).equals("0:0") == false)
						ItemDrop.CustomItemDrop(event.getBlock().getLocation(), AreaConfig.getItemStack(Area[0]+".Mining."+BlockData));
				}
			}
		}
		Quest(event, player);
		if(event.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("Dungeon")==true
				&&player.isOp()==false)
		{
			event.setCancelled(true);
			return;
		}
		
		int id = event.getBlock().getTypeId();
		if((id >= 14&&id <= 17)||id==21||id==56||id==129||id==73||id==153)
		{
			YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
			YamlManager EXPexceptionBlockList =GUI_YC.getNewConfig("EXPexceptionBlock.yml");
			Location loc = event.getBlock().getLocation();
			String Location = ((int)loc.getX()+"_"+(int)loc.getY()+"_"+(int)loc.getZ());
			if(EXPexceptionBlockList.contains(loc.getWorld().getName()+"."+id+"."+Location))
			{
				EXPexceptionBlockList.removeKey(loc.getWorld().getName()+"."+id+"."+Location);
				EXPexceptionBlockList.saveConfig();
			}
			else
			{
				if(player.getGameMode()!=GameMode.CREATIVE)
				{
					EXPadd(event);
					LuckyBonus(event);
				}
			}
		}
		new GBD.GoldBigDragon_Advanced.Event.Damage().decreaseDurabilityWeapon(player);
		return;
	}

	private void Quest(BlockBreakEvent event, Player player)
	{
		YamlController Party_YC = GBD.GoldBigDragon_Advanced.Main.Main.Party_YC;
	  	if(Party_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		new GBD.GoldBigDragon_Advanced.Config.StatConfig().CreateNewStats(player);

	  	YamlController GUI_YC = GBD.GoldBigDragon_Advanced.Main.Main.GUI_YC;
		YamlManager QuestList  = GUI_YC.getNewConfig("Quest/QuestList.yml");
	  	if(Party_YC.isExit("Quest/PlayerData/"+player.getUniqueId()+".yml") == false)
	  		new GBD.GoldBigDragon_Advanced.Config.QuestConfig().CreateNewPlayerConfig(player);
		YamlManager PlayerQuestList  = GUI_YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		if(Main.PartyJoiner.containsKey(player)==false)
		{
			if(PlayerQuestList.contains("Started"))
			if(PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray().length >= 1)
			{
				Object[] a = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
				for(int count = 0; count < a.length; count++)
				{
					String QuestName = (String) a[count];
					int Flow = PlayerQuestList.getInt("Started."+QuestName+".Flow");
					if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Harvest"))
					{
						Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Block").getKeys(false).toArray();
						int Finish = 0;
						for(int counter = 0; counter < MobList.length; counter++)
						{
							int BlockID = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockID");
							int BlockData = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockData");
							int MAX = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".Amount");
							boolean DataEquals = QuestList.getBoolean(QuestName+".FlowChart."+Flow+".Block."+counter+".DataEquals");
							if(BlockID == event.getBlock().getTypeId() && MAX > PlayerQuestList.getInt("Started."+QuestName+".Block."+counter))
							{
								if(DataEquals == false)
								{
									PlayerQuestList.set("Started."+QuestName+".Block."+counter, PlayerQuestList.getInt("Started."+QuestName+".Block."+counter)+1);
									PlayerQuestList.saveConfig();
								}
								else
								{
									if(BlockData == event.getBlock().getData())
									{
										PlayerQuestList.set("Started."+QuestName+".Block."+counter, PlayerQuestList.getInt("Started."+QuestName+".Block."+counter)+1);
										PlayerQuestList.saveConfig();
									}
								}
							}
							if(MAX == PlayerQuestList.getInt("Started."+QuestName+".Block."+counter))
								Finish = Finish+1;
							if(Finish == MobList.length)
							{
								PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
								PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
								PlayerQuestList.removeKey("Started."+QuestName+".Harvest");
								PlayerQuestList.saveConfig();
								GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
								QGUI.QuestTypeRouter(player, QuestName);
								//퀘스트 완료 메시지//
								break;
							}
						}
					}
				}
			}
		}
		else
		{
			Player[] PartyMember = Main.Party.get(Main.PartyJoiner.get(player)).getMember();
			for(int counta = 0; counta < PartyMember.length; counta++)
			{
				player = PartyMember[counta];
				if(event.getBlock().getLocation().getWorld() == player.getLocation().getWorld())
				{
					YamlManager Config = GUI_YC.getNewConfig("config.yml");
					if(event.getBlock().getLocation().distance(player.getLocation()) <= Config.getInt("Party.EXPShareDistance"))
					{
						PlayerQuestList  = GUI_YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						Object[] a = PlayerQuestList.getConfigurationSection("Started.").getKeys(false).toArray();
						for(int count = 0; count < a.length; count++)
						{
							String QuestName = (String) a[count];
							int Flow = PlayerQuestList.getInt("Started."+QuestName+".Flow");
							if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Harvest"))
							{
								Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Block").getKeys(false).toArray();
								int Finish = 0;
								for(int counter = 0; counter < MobList.length; counter++)
								{
									int BlockID = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockID");
									int BlockData = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockData");
									int MAX = QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".Amount");
									boolean DataEquals = QuestList.getBoolean(QuestName+".FlowChart."+Flow+".Block."+counter+".DataEquals");
									if(BlockID == event.getBlock().getTypeId() && MAX > PlayerQuestList.getInt("Started."+QuestName+".Block."+counter))
									{
										if(DataEquals == false)
										{
											PlayerQuestList.set("Started."+QuestName+".Block."+counter, PlayerQuestList.getInt("Started."+QuestName+".Block."+counter)+1);
											PlayerQuestList.saveConfig();
										}
										else
										{
											if(BlockData == event.getBlock().getData())
											{
												PlayerQuestList.set("Started."+QuestName+".Block."+counter, PlayerQuestList.getInt("Started."+QuestName+".Block."+counter)+1);
												PlayerQuestList.saveConfig();
											}
										}
									}
									if(MAX == PlayerQuestList.getInt("Started."+QuestName+".Block."+counter))
									{
										Finish = Finish+1;
									}
									if(Finish == MobList.length)
									{
										PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
										PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
										PlayerQuestList.removeKey("Started."+QuestName+".Harvest");
										PlayerQuestList.saveConfig();
										GBD.GoldBigDragon_Advanced.GUI.QuestGUI QGUI = new GBD.GoldBigDragon_Advanced.GUI.QuestGUI();
										QGUI.QuestTypeRouter(player, QuestName);
										//퀘스트 완료 메시지//
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return;
	}

	private void LuckyBonus(BlockBreakEvent event)
	{
		Player player = event.getPlayer();

		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
	  	if(Event_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		new GBD.GoldBigDragon_Advanced.Config.StatConfig().CreateNewStats(player);
	  	YamlManager YM = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");

		int lucky = YM.getInt("Stat.LUK")/30;
		if(lucky >= 150) lucky =150;
		if(lucky <= 0) lucky = 1;
		if(lucky >= new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(0, 1000))
		{
			GBD.GoldBigDragon_Advanced.Effect.Sound sound = new GBD.GoldBigDragon_Advanced.Effect.Sound();
			GBD.GoldBigDragon_Advanced.Effect.PacketSender t = new GBD.GoldBigDragon_Advanced.Effect.PacketSender();
			int amount = 0;
			int luckysize = new GBD.GoldBigDragon_Advanced.Util.Number().RandomNum(0, 100);
			if(luckysize <= 80){t.sendActionBar(player, ChatColor.YELLOW +""+ChatColor.BOLD+ "럭키 보너스!");amount = 1;	sound.SP(player, Sound.LEVEL_UP, 0.5F, 0.9F);}
			else if(luckysize <= 95){t.sendActionBar(player, ChatColor.YELLOW +""+ChatColor.BOLD+ "빅 럭키 보너스!");amount = 5;	sound.SP(player, Sound.LEVEL_UP, 0.7F, 1.0F);}
			else{t.sendActionBar(player, ChatColor.YELLOW +""+ChatColor.BOLD+ "휴즈 럭키 보너스!");amount = 20;	sound.SP(player, Sound.LEVEL_UP, 1.0F, 1.1F);}
			switch(event.getBlock().getType())
			{
				case COAL_ORE:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), 263,0,amount);}
					break;
				case IRON_ORE:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), 15,0,amount);}
					break;
				case GOLD_ORE:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), 14,0,amount);}
					break;
				case DIAMOND_ORE:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), 264,0,amount);}
					break;
				case EMERALD_ORE:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), 388,0,amount);}
					break;
				case REDSTONE_ORE:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), 331,0,amount);}
					break;
				case LAPIS_ORE:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), 351,4,amount);}
					break;
				case SEA_LANTERN:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), 410,0,amount);}
					break;
				case LOG:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), event.getBlock().getTypeId(),event.getBlock().getData(),amount);}
					break;
				case LOG_2:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), event.getBlock().getTypeId(),event.getBlock().getData(),amount);}
					break;
				case DIRT:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), event.getBlock().getTypeId(),event.getBlock().getData(),amount);}
					break;
				case STONE:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), event.getBlock().getTypeId(),event.getBlock().getData(),amount);}
					break;
				case SAND:
					{new ItemDrop().PureItemNaturalDrop(event.getBlock().getLocation(), event.getBlock().getTypeId(),event.getBlock().getData(),amount);}
					break;
				default:
					break;	
			}
		}
		return;
	}

	private void EXPadd(BlockBreakEvent event)
	{
		Player player = event.getPlayer();
		GBD.GoldBigDragon_Advanced.Event.Level LV = new GBD.GoldBigDragon_Advanced.Event.Level();
		GBD.GoldBigDragon_Advanced.Event.ItemDrop ID = new GBD.GoldBigDragon_Advanced.Event.ItemDrop();

		YamlController Event_YC = GBD.GoldBigDragon_Advanced.Main.Main.Event_YC;
		YamlManager Config = Event_YC.getNewConfig("config.yml");

		switch(event.getBlock().getType())
		{
			case COAL_ORE:
			{	LV.EXPadd(player, Config.getLong("Getting.Coal.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.Coal.Money"));
			}	break;
			case IRON_ORE:
			{	LV.EXPadd(player, Config.getLong("Getting.Iron.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.Iron.Money"));
			}	break;
			case GOLD_ORE:
			{	LV.EXPadd(player, Config.getLong("Getting.Gold.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.Gold.Money"));
			}	break;
			case DIAMOND_ORE:
			{	LV.EXPadd(player, Config.getLong("Getting.Diamond.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.Diamond.Money"));
			}	break;
			case EMERALD_ORE:
			{	LV.EXPadd(player, Config.getLong("Getting.Emerald.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.Emerald.Money"));
			}	break;
			case REDSTONE_ORE:
			{	LV.EXPadd(player, Config.getLong("Getting.RedStone.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.RedStone.Money"));
			}	break;
			case LAPIS_ORE:
			{	LV.EXPadd(player, Config.getLong("Getting.Lapis.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.Lapis.Money"));
			}	break;
			case LOG:
			{	LV.EXPadd(player, Config.getLong("Getting.Wood.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.Wood.Money"));
			}	break;
			case LOG_2:
			{	LV.EXPadd(player, Config.getLong("Getting.Wood.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.Wood.Money"));
			}	break;
			case QUARTZ_ORE:
			{	LV.EXPadd(player, Config.getLong("Getting.NetherQuartz.EXP"), player.getLocation());
				ID.MoneyDrop(player.getLocation(), Config.getInt("Getting.NetherQuartz.Money"));
			}	break;
			default:
				break;	
		}
		return;
	}
}