package GBD_RPG.Main_Event;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import GBD_RPG.Battle.Battle_Calculator;
import GBD_RPG.Main_Main.Main_ServerOption;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class Main_BlockBreak
{
	public void BlockBreaking(BlockBreakEvent event)
	{
		if(event.isCancelled())
			return;
		if(event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			if(event.getBlock().getTypeId() != 50)
				event.setCancelled(true);
			return;
		}
		Player player = event.getPlayer();
		new Battle_Calculator().decreaseDurabilityWeapon(player);
		GBD_RPG.Area.Area_Main A = new GBD_RPG.Area.Area_Main();
		String[] Area = A.getAreaName(event.getBlock());
		if(Area != null)
		{
		  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager AreaConfig =YC.getNewConfig("Area/AreaList.yml");

			if(A.getAreaOption(Area[0], (char) 1) == false && event.getPlayer().isOp() == false)
			{
				event.setCancelled(true);
				new GBD_RPG.Effect.Effect_Sound().SP(event.getPlayer(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				event.getPlayer().sendMessage(ChatColor.RED + "[SYSTEM] : " + ChatColor.YELLOW + Area[1] + ChatColor.RED + " 지역 에서는 블록 채집이 불가능합니다!");
				return;
			}
			if(AreaConfig.getInt(Area[0]+".RegenBlock")!=0)
			{
				Long UTC = (AreaConfig.getInt(Area[0]+".RegenBlock")*1000)+GBD_RPG.ServerTick.ServerTick_Main.nowUTC+new GBD_RPG.Util.Util_Number().RandomNum(1, 1000);
				GBD_RPG.ServerTick.ServerTick_Object STSO = new GBD_RPG.ServerTick.ServerTick_Object(UTC, "A_RB");
				STSO.setMaxCount(-1);
				Block block =event.getBlock();
				STSO.setString((byte)1, block.getWorld().getName());//목적지 월드 이름 저장
				STSO.setInt((byte)0, block.getX());//블록X 위치저장
				STSO.setInt((byte)1, block.getY());//블록Y 위치저장
				STSO.setInt((byte)2, block.getZ());//블록Z 위치저장
				STSO.setInt((byte)3, block.getTypeId());//블록 ID저장
				STSO.setInt((byte)4, block.getData());//블록 DATA 저장
				
				GBD_RPG.ServerTick.ServerTick_Main.Schedule.put(UTC, STSO);
			}
			if(player.getGameMode() != GameMode.CREATIVE)
			{
				String BlockData = event.getBlock().getTypeId()+":"+event.getBlock().getData();
				if(AreaConfig.contains(Area[0]+".Mining."+BlockData) == true)
				{
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					GBD_RPG.Main_Event.Main_ItemDrop ItemDrop = new GBD_RPG.Main_Event.Main_ItemDrop();
					Location loc = event.getBlock().getLocation();
					loc.setY(loc.getY()+0.4);
					loc.setX(loc.getX()+0.5);
					loc.setZ(loc.getZ()+0.5);
					if(AreaConfig.contains(Area[0]+".Mining."+BlockData+".100"))
					{
						if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".100").compareTo("0:0")!=0)
							ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".100"));
						int random = new GBD_RPG.Util.Util_Number().RandomNum(1, 1000);
						if(random<=1)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".0").compareTo("0:0")!=0)
								ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".0"));
						}
						else if(random<=10)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".1").compareTo("0:0")!=0)
								ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".1"));
						}
						else if(random<=100)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".10").compareTo("0:0")!=0)
								ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".10"));
						}
						else if(random<=500)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".50").compareTo("0:0")!=0)
								ItemDrop.CustomItemDrop(loc, AreaConfig.getItemStack(Area[0]+".Mining."+BlockData+".50"));
						}
						else if(random<=900)
						{
							if(AreaConfig.getString(Area[0]+".Mining."+BlockData+".90").compareTo("0:0")!=0)
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
		
		short id = (short) event.getBlock().getTypeId();
		if((id >= 14&&id <= 17)||id==21||id==56||id==129||id==73||id==153)
		{
		  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
			YamlManager EXPexceptionBlockList =YC.getNewConfig("EXPexceptionBlock.yml");
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
					if((id>=14&&id<=16)||id==56||id==129||id==73||id==21||id==17||id==162||id==153||id==89||id==169)
						LuckyBonus(player, event.getBlock());
				}
			}
		}
		return;
	}

	private void Quest(BlockBreakEvent event, Player player)
	{
	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
	  	if(YC.isExit("Quest/PlayerData/"+player.getUniqueId()+".yml") == false)
	  		new GBD_RPG.Quest.Quest_Config().CreateNewPlayerConfig(player);
		YamlManager PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		if(Main_ServerOption.PartyJoiner.containsKey(player)==false)
		{
			if(PlayerQuestList.contains("Started"))
			if(PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray().length >= 1)
			{
				Object[] a = PlayerQuestList.getConfigurationSection("Started").getKeys(false).toArray();
				for(int count = 0; count < a.length; count++)
				{
					String QuestName = (String) a[count];
					short Flow = (short) PlayerQuestList.getInt("Started."+QuestName+".Flow");
					if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Harvest"))
					{
						Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Block").getKeys(false).toArray();
						short Finish = 0;
						for(short counter = 0; counter < MobList.length; counter++)
						{
							short BlockID = (short) QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockID");
							byte BlockData = (byte) QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockData");
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
								Finish++;
							if(Finish == MobList.length)
							{
								PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
								PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
								PlayerQuestList.removeKey("Started."+QuestName+".Harvest");
								PlayerQuestList.saveConfig();
								GBD_RPG.Quest.Quest_GUI QGUI = new GBD_RPG.Quest.Quest_GUI();
								QGUI.QuestRouter(player, QuestName);
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
			Player[] PartyMember = Main_ServerOption.Party.get(Main_ServerOption.PartyJoiner.get(player)).getMember();
			for(byte counta = 0; counta < PartyMember.length; counta++)
			{
				player = PartyMember[counta];
				if(event.getBlock().getLocation().getWorld() == player.getLocation().getWorld())
				{
					YamlManager Config = YC.getNewConfig("config.yml");
					if(event.getBlock().getLocation().distance(player.getLocation()) <= Config.getInt("Party.EXPShareDistance"))
					{
						PlayerQuestList  = YC.getNewConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						Object[] a = PlayerQuestList.getConfigurationSection("Started.").getKeys(false).toArray();
						for(short count = 0; count < a.length; count++)
						{
							String QuestName = (String) a[count];
							short Flow = (short) PlayerQuestList.getInt("Started."+QuestName+".Flow");
							if(PlayerQuestList.getString("Started."+QuestName+".Type").equalsIgnoreCase("Harvest"))
							{
								Object[] MobList = QuestList.getConfigurationSection(QuestName+".FlowChart."+Flow+".Block").getKeys(false).toArray();
								short Finish = 0;
								for(short counter = 0; counter < MobList.length; counter++)
								{
									short BlockID = (short) QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockID");
									byte BlockData = (byte) QuestList.getInt(QuestName+".FlowChart."+Flow+".Block."+counter+".BlockData");
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
										Finish++;
									}
									if(Finish == MobList.length)
									{
										PlayerQuestList.set("Started."+QuestName+".Type",QuestList.getString(QuestName+".FlowChart."+(PlayerQuestList.getInt("Started."+QuestName+".Flow")+1)+".Type"));
										PlayerQuestList.set("Started."+QuestName+".Flow",PlayerQuestList.getInt("Started."+QuestName+".Flow")+1);
										PlayerQuestList.removeKey("Started."+QuestName+".Harvest");
										PlayerQuestList.saveConfig();
										GBD_RPG.Quest.Quest_GUI QGUI = new GBD_RPG.Quest.Quest_GUI();
										QGUI.QuestRouter(player, QuestName);
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

	private void LuckyBonus(Player player, Block block)
	{
		int lucky = GBD_RPG.Main_Main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()/30;
		if(lucky >= 150) lucky =150;
		if(lucky <= 0) lucky = 1;
		if(lucky >= new GBD_RPG.Util.Util_Number().RandomNum(0, 1000))
		{
			GBD_RPG.Effect.Effect_Sound sound = new GBD_RPG.Effect.Effect_Sound();
			GBD_RPG.Effect.Effect_Packet t = new GBD_RPG.Effect.Effect_Packet();
			byte amount = 0;
			byte luckysize = (byte) new GBD_RPG.Util.Util_Number().RandomNum(0, 100);
			if(luckysize <= 80){t.sendActionBar(player, ChatColor.YELLOW +""+ChatColor.BOLD+ "럭키 보너스!");amount = 1;	sound.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.9F);}
			else if(luckysize <= 95){t.sendActionBar(player, ChatColor.YELLOW +""+ChatColor.BOLD+ "빅 럭키 보너스!");amount = 5;	sound.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 0.7F, 1.0F);}
			else{t.sendActionBar(player, ChatColor.YELLOW +""+ChatColor.BOLD+ "휴즈 럭키 보너스!");amount = 20;	sound.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.1F);}

			int id = block.getTypeId();
			if(id==16)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)263, (byte)0,amount);
			else if(id == 15)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)15, (byte)0,amount);
			else if(id == 14)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)14, (byte)0,amount);
			else if(id == 56)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)264, (byte)0,amount);
			else if(id == 129)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)388, (byte)0,amount);
			else if(id == 73)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)331, (byte)0,amount);
			else if(id == 21)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)351, (byte)4,amount);
			else if(id == 17 || id == 162)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)block.getTypeId(), (byte)block.getData(),amount);
			else if(id == 153)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)406, (byte)0,amount);
			else if(id == 89)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)348, (byte)0, amount);
			else if(id == 169)
				new Main_ItemDrop().PureItemNaturalDrop(block.getLocation(), (short)410, (byte)0, amount);
		}
		return;
	}

	private void EXPadd(BlockBreakEvent event)
	{
		Player player = event.getPlayer();

	  	YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager Config = YC.getNewConfig("config.yml");

		int id = event.getBlock().getTypeId();
		if(id==16)
			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, Config.getInt("Getting.Coal.Money"), Config.getLong("Getting.Coal.EXP"), player.getLocation(), true, false);
		else if(id == 15)
			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, Config.getInt("Getting.Iron.Money"), Config.getLong("Getting.Iron.EXP"), player.getLocation(), true, false);
		else if(id == 14)
			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, Config.getInt("Getting.Gold.Money"), Config.getLong("Getting.Gold.EXP"), player.getLocation(), true, false);
		else if(id == 56)
			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, Config.getInt("Getting.Diamond.Money"), Config.getLong("Getting.Diamond.EXP"), player.getLocation(), true, false);
		else if(id == 129)
			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, Config.getInt("Getting.Emerald.Money"), Config.getLong("Getting.Emerald.EXP"), player.getLocation(), true, false);
		else if(id == 73)
			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, Config.getInt("Getting.RedStone.Money"), Config.getLong("Getting.RedStone.EXP"), player.getLocation(), true, false);
		else if(id == 21)
			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, Config.getInt("Getting.Lapis.Money"), Config.getLong("Getting.Lapis.EXP"), player.getLocation(), true, false);
		else if(id == 17 || id == 162)
			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, Config.getInt("Getting.Wood.Money"), Config.getLong("Getting.Wood.EXP"), player.getLocation(), true, false);
		else if(id == 153)
			new GBD_RPG.Util.Util_Player().addMoneyAndEXP(player, Config.getInt("Getting.NetherQuartz.Money"), Config.getLong("Getting.NetherQuartz.EXP"), player.getLocation(), true, false);

		return;
	}
}