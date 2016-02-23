package GoldBigDragon_RPG.Event;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import GoldBigDragon_RPG.Main.Main;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class BlockBreak
{
	public void BlockBreaking(BlockBreakEvent event)
	{
		if(event.isCancelled())
			return;
		Player player = (Player) event.getPlayer();
		new Damage().decreaseDurabilityWeapon(player);
		GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
		String[] Area = A.getAreaName(event.getBlock());
		if(Area != null)
		{
			YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
			YamlManager AreaConfig =YC_2.getNewConfig("Area/AreaList.yml");

			if(A.getAreaOption(Area[0], (char) 1) == false && event.getPlayer().isOp() == false)
			{
				event.setCancelled(true);
				new GoldBigDragon_RPG.Effect.Sound().SP(event.getPlayer(), org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				event.getPlayer().sendMessage(ChatColor.RED + "[SYSTEM] : " + ChatColor.YELLOW + Area[1] + ChatColor.RED + " 지역 에서는 블록 채집이 불가능합니다!");
				return;
			}
			if(AreaConfig.getInt(Area[0]+".RegenBlock")!=0)
			{
				Long UTC = (AreaConfig.getInt(Area[0]+".RegenBlock")*1000)+GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC+new GoldBigDragon_RPG.Util.Number().RandomNum(1, 1000);
				GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(UTC, "A_RB");
				STSO.setMaxCount(-1);
				Block block =event.getBlock();
				STSO.setString((byte)1, block.getWorld().getName());//목적지 월드 이름 저장
				STSO.setInt((byte)0, block.getX());//블록X 위치저장
				STSO.setInt((byte)1, block.getY());//블록Y 위치저장
				STSO.setInt((byte)2, block.getZ());//블록Z 위치저장
				STSO.setInt((byte)3, block.getTypeId());//블록 ID저장
				STSO.setInt((byte)4, block.getData());//블록 DATA 저장
				
				GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(UTC, STSO);
			}
			if(player.getGameMode() != GameMode.CREATIVE)
			{
				String BlockData = event.getBlock().getTypeId()+":"+event.getBlock().getData();
				if(AreaConfig.contains(Area[0]+".Mining."+BlockData) == true)
				{
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					GoldBigDragon_RPG.Event.ItemDrop ItemDrop = new GoldBigDragon_RPG.Event.ItemDrop();
					Location loc = event.getBlock().getLocation();
					loc.setY(loc.getY()+0.4);
					loc.setX(loc.getX()+0.5);
					loc.setZ(loc.getZ()+0.5);
					if(AreaConfig.contains(Area[0]+".Mining."+BlockData+".100"))
					{
						if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".100").equals("0:0") == false)
							ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".100"));
						int random = new GoldBigDragon_RPG.Util.Number().RandomNum(1, 1000);
						if(random<=1)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".0").equals("0:0") == false)
								ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".0"));
						}
						else if(random<=10)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".1").equals("0:0") == false)
								ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".1"));
						}
						else if(random<=100)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".10").equals("0:0") == false)
								ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".10"));
						}
						else if(random<=500)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".50").equals("0:0") == false)
								ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".50"));
						}
						else if(random<=900)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".90").equals("0:0") == false)
								ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".90"));
						}
					}
				}
			}
		}
		Quest(event, player);
		if(event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon")==0
				&&player.isOp()==false)
		{
			event.setCancelled(true);
			return;
		}
		
		int id = event.getBlock().getTypeId();
		if((id >= 14&&id <= 17)||id==21||id==56||id==129||id==73||id==153)
		{
			YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
			YamlManager EXPexceptionBlockList =YC_2.getNewConfig("EXPexceptionBlock.yml");
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
		return;
	}

	private void Quest(BlockBreakEvent event, Player player)
	{
		YamlController Party_YC = GoldBigDragon_RPG.Main.Main.YC_1;
	  	if(Party_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		new GoldBigDragon_RPG.Config.StatConfig().CreateNewStats(player);

	  	YamlController YC_2 = GoldBigDragon_RPG.Main.Main.YC_2;
		YamlManager QuestList  = YC_2.getNewConfig("Quest/QuestList.yml");
	  	if(Party_YC.isExit("Quest/PlayerData/"+player.getUniqueId()+".yml") == false)
	  		new GoldBigDragon_RPG.Config.QuestConfig().CreateNewPlayerConfig(player);
		YamlManager PlayerQuestList  = YC_2.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

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
								GoldBigDragon_RPG.GUI.QuestGUI QGUI = new GoldBigDragon_RPG.GUI.QuestGUI();
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
					YamlManager Config = YC_2.getNewConfig("config.yml");
					if(event.getBlock().getLocation().distance(player.getLocation()) <= Config.getInt("Party.EXPShareDistance"))
					{
						PlayerQuestList  = YC_2.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
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
										GoldBigDragon_RPG.GUI.QuestGUI QGUI = new GoldBigDragon_RPG.GUI.QuestGUI();
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

		YamlController Event_YC = GoldBigDragon_RPG.Main.Main.YC_1;
	  	if(Event_YC.isExit("Stats/" + player.getUniqueId()+".yml") == false)
	  		new GoldBigDragon_RPG.Config.StatConfig().CreateNewStats(player);
	  	YamlManager YM = Event_YC.getNewConfig("Stats/" + player.getUniqueId()+".yml");

		int lucky = YM.getInt("Stat.LUK")/30;
		if(lucky >= 150) lucky =150;
		if(lucky <= 0) lucky = 1;
		if(lucky >= new GoldBigDragon_RPG.Util.Number().RandomNum(0, 1000))
		{
			GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
			GoldBigDragon_RPG.Effect.PacketSender t = new GoldBigDragon_RPG.Effect.PacketSender();
			int amount = 0;
			int luckysize = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 100);
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
		GoldBigDragon_RPG.Event.Level LV = new GoldBigDragon_RPG.Event.Level();
		GoldBigDragon_RPG.Event.ItemDrop ID = new GoldBigDragon_RPG.Event.ItemDrop();

		YamlController Event_YC = GoldBigDragon_RPG.Main.Main.YC_1;
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