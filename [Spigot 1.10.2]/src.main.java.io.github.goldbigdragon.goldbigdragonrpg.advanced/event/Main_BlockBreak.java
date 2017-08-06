package event;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import battle.Battle_Calculator;
import effect.SoundEffect;
import main.Main_ServerOption;
import util.YamlLoader;



public class Main_BlockBreak implements Listener
{
	@EventHandler
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
		Battle_Calculator.decreaseDurabilityWeapon(player);
		area.Area_Main A = new area.Area_Main();
		String[] Area = A.getAreaName(event.getBlock());
		if(Area != null)
		{
		  	YamlLoader areaYaml = new YamlLoader();
			areaYaml.getConfig("Area/AreaList.yml");

			if(A.getAreaOption(Area[0], (char) 1) == false && event.getPlayer().isOp() == false)
			{
				event.setCancelled(true);
				SoundEffect.SP(event.getPlayer(), org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				event.getPlayer().sendMessage(ChatColor.RED + "[SYSTEM] : " + ChatColor.YELLOW + Area[1] + ChatColor.RED + " 지역 에서는 블록 채집이 불가능합니다!");
				return;
			}
			if(areaYaml.getInt(Area[0]+".RegenBlock")!=0)
			{
				Long UTC = (areaYaml.getInt(Area[0]+".RegenBlock")*1000)+servertick.ServerTick_Main.nowUTC+new util.Util_Number().RandomNum(1, 1000);
				servertick.ServerTick_Object STSO = new servertick.ServerTick_Object(UTC, "A_RB");
				STSO.setMaxCount(-1);
				Block block =event.getBlock();
				STSO.setString((byte)1, block.getWorld().getName());//목적지 월드 이름 저장
				STSO.setInt((byte)0, block.getX());//블록X 위치저장
				STSO.setInt((byte)1, block.getY());//블록Y 위치저장
				STSO.setInt((byte)2, block.getZ());//블록Z 위치저장
				STSO.setInt((byte)3, block.getTypeId());//블록 ID저장
				STSO.setInt((byte)4, block.getData());//블록 DATA 저장
				
				servertick.ServerTick_Main.Schedule.put(UTC, STSO);
			}
			if(player.getGameMode() != GameMode.CREATIVE)
			{
				String BlockData = event.getBlock().getTypeId()+":"+event.getBlock().getData();
				if(areaYaml.contains(Area[0]+".Mining."+BlockData) == true)
				{
					event.setCancelled(true);
					event.getBlock().setType(Material.AIR);
					event.Main_ItemDrop ItemDrop = new event.Main_ItemDrop();
					Location loc = event.getBlock().getLocation();
					loc.setY(loc.getY()+0.4);
					loc.setX(loc.getX()+0.5);
					loc.setZ(loc.getZ()+0.5);
					if(areaYaml.contains(Area[0]+".Mining."+BlockData+".100"))
					{
						if(areaYaml.getString(Area[0]+".Mining."+BlockData+".100").compareTo("0:0")!=0)
							ItemDrop.CustomItemDrop(loc, areaYaml.getItemStack(Area[0]+".Mining."+BlockData+".100"));
						int random = new util.Util_Number().RandomNum(1, 1000);
						if(random<=1)
						{
							if(areaYaml.getString(Area[0]+".Mining."+BlockData+".0").compareTo("0:0")!=0)
								ItemDrop.CustomItemDrop(loc, areaYaml.getItemStack(Area[0]+".Mining."+BlockData+".0"));
						}
						else if(random<=10)
						{
							if(areaYaml.getString(Area[0]+".Mining."+BlockData+".1").compareTo("0:0")!=0)
								ItemDrop.CustomItemDrop(loc, areaYaml.getItemStack(Area[0]+".Mining."+BlockData+".1"));
						}
						else if(random<=100)
						{
							if(areaYaml.getString(Area[0]+".Mining."+BlockData+".10").compareTo("0:0")!=0)
								ItemDrop.CustomItemDrop(loc, areaYaml.getItemStack(Area[0]+".Mining."+BlockData+".10"));
						}
						else if(random<=500)
						{
							if(areaYaml.getString(Area[0]+".Mining."+BlockData+".50").compareTo("0:0")!=0)
								ItemDrop.CustomItemDrop(loc, areaYaml.getItemStack(Area[0]+".Mining."+BlockData+".50"));
						}
						else if(random<=900)
						{
							if(areaYaml.getString(Area[0]+".Mining."+BlockData+".90").compareTo("0:0")!=0)
								ItemDrop.CustomItemDrop(loc, areaYaml.getItemStack(Area[0]+".Mining."+BlockData+".90"));
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
		  	YamlLoader exceptionBlockYaml = new YamlLoader();
			exceptionBlockYaml.getConfig("EXPexceptionBlock.yml");
			Location loc = event.getBlock().getLocation();
			String Location = ((int)loc.getX()+"_"+(int)loc.getY()+"_"+(int)loc.getZ());
			if(exceptionBlockYaml.contains(loc.getWorld().getName()+"."+id+"."+Location))
			{
				exceptionBlockYaml.removeKey(loc.getWorld().getName()+"."+id+"."+Location);
				exceptionBlockYaml.saveConfig();
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
	  	YamlLoader questYaml = new YamlLoader();
		questYaml.getConfig("Quest/QuestList.yml");
	  	if(questYaml.isExit("Quest/PlayerData/"+player.getUniqueId()+".yml") == false)
	  		new quest.Quest_Config().CreateNewPlayerConfig(player);
	  	YamlLoader playerQuestYaml = new YamlLoader();
		playerQuestYaml.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");

		if(Main_ServerOption.PartyJoiner.containsKey(player)==false)
		{
			if(playerQuestYaml.contains("Started"))
			if(playerQuestYaml.getConfigurationSection("Started").getKeys(false).toArray().length >= 1)
			{
				String[] startedQuestList = playerQuestYaml.getConfigurationSection("Started").getKeys(false).toArray(new String[0]);
				for(int count = 0; count < startedQuestList.length; count++)
				{
					short Flow = (short) playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow");
					if(questYaml.contains(startedQuestList[count]+".FlowChart."+Flow+".Block")&&playerQuestYaml.getString("Started."+startedQuestList[count]+".Type").equalsIgnoreCase("Harvest"))
					{
						int blockSize = questYaml.getConfigurationSection(startedQuestList[count]+".FlowChart."+Flow+".Block").getKeys(false).size();
						short Finish = 0;
						for(int counter = 0; counter < blockSize; counter++)
						{
							short BlockID = (short) questYaml.getInt(startedQuestList[count]+".FlowChart."+Flow+".Block."+counter+".BlockID");
							byte BlockData = (byte) questYaml.getInt(startedQuestList[count]+".FlowChart."+Flow+".Block."+counter+".BlockData");
							int MAX = questYaml.getInt(startedQuestList[count]+".FlowChart."+Flow+".Block."+counter+".Amount");
							boolean DataEquals = questYaml.getBoolean(startedQuestList[count]+".FlowChart."+Flow+".Block."+counter+".DataEquals");
							if(BlockID == event.getBlock().getTypeId() && MAX > playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter))
							{
								if(DataEquals == false)
								{
									playerQuestYaml.set("Started."+startedQuestList[count]+".Block."+counter, playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter)+1);
									playerQuestYaml.saveConfig();
								}
								else
								{
									if(BlockData == event.getBlock().getData())
									{
										playerQuestYaml.set("Started."+startedQuestList[count]+".Block."+counter, playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter)+1);
										playerQuestYaml.saveConfig();
									}
								}
							}
							if(MAX == playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter))
								Finish++;
							if(Finish == blockSize)
							{
								playerQuestYaml.set("Started."+startedQuestList[count]+".Type",questYaml.getString(startedQuestList[count]+".FlowChart."+(playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1)+".Type"));
								playerQuestYaml.set("Started."+startedQuestList[count]+".Flow",playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1);
								playerQuestYaml.removeKey("Started."+startedQuestList[count]+".Harvest");
								playerQuestYaml.saveConfig();
								quest.Quest_GUI QGUI = new quest.Quest_GUI();
								QGUI.QuestRouter(player, startedQuestList[count]);
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
			YamlLoader configYaml = new YamlLoader();
			configYaml.getConfig("config.yml");
			int partyEXPShareDistance = configYaml.getInt("Party.EXPShareDistance");
			for(int counta = 0; counta < PartyMember.length; counta++)
			{
				player = PartyMember[counta];
				if(event.getBlock().getLocation().getWorld() == player.getLocation().getWorld())
				{
					if(event.getBlock().getLocation().distance(player.getLocation()) <= partyEXPShareDistance)
					{
						playerQuestYaml.getConfig("Quest/PlayerData/"+player.getUniqueId()+".yml");
						String[] startedQuestList = playerQuestYaml.getConfigurationSection("Started.").getKeys(false).toArray(new String[0]);
						for(int count = 0; count < startedQuestList.length; count++)
						{
							short Flow = (short) playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow");
							if(playerQuestYaml.getString("Started."+startedQuestList[count]+".Type").equalsIgnoreCase("Harvest"))
							{
								int blockSize = questYaml.getConfigurationSection(startedQuestList[count]+".FlowChart."+Flow+".Block").getKeys(false).size();
								short Finish = 0;
								for(int counter = 0; counter < blockSize; counter++)
								{
									short BlockID = (short) questYaml.getInt(startedQuestList[count]+".FlowChart."+Flow+".Block."+counter+".BlockID");
									byte BlockData = (byte) questYaml.getInt(startedQuestList[count]+".FlowChart."+Flow+".Block."+counter+".BlockData");
									int MAX = questYaml.getInt(startedQuestList[count]+".FlowChart."+Flow+".Block."+counter+".Amount");
									boolean DataEquals = questYaml.getBoolean(startedQuestList[count]+".FlowChart."+Flow+".Block."+counter+".DataEquals");
									if(BlockID == event.getBlock().getTypeId() && MAX > playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter))
									{
										if(DataEquals == false)
										{
											playerQuestYaml.set("Started."+startedQuestList[count]+".Block."+counter, playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter)+1);
											playerQuestYaml.saveConfig();
										}
										else
										{
											if(BlockData == event.getBlock().getData())
											{
												playerQuestYaml.set("Started."+startedQuestList[count]+".Block."+counter, playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter)+1);
												playerQuestYaml.saveConfig();
											}
										}
									}
									if(MAX == playerQuestYaml.getInt("Started."+startedQuestList[count]+".Block."+counter))
									{
										Finish++;
									}
									if(Finish == blockSize)
									{
										playerQuestYaml.set("Started."+startedQuestList[count]+".Type",questYaml.getString(startedQuestList[count]+".FlowChart."+(playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1)+".Type"));
										playerQuestYaml.set("Started."+startedQuestList[count]+".Flow",playerQuestYaml.getInt("Started."+startedQuestList[count]+".Flow")+1);
										playerQuestYaml.removeKey("Started."+startedQuestList[count]+".Harvest");
										playerQuestYaml.saveConfig();
										quest.Quest_GUI QGUI = new quest.Quest_GUI();
										QGUI.QuestRouter(player, startedQuestList[count]);
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
		int lucky = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_LUK()/30;
		if(lucky >= 150) lucky =150;
		if(lucky <= 0) lucky = 1;
		if(lucky >= new util.Util_Number().RandomNum(0, 1000))
		{
			effect.SendPacket t = new effect.SendPacket();
			byte amount = 0;
			byte luckysize = (byte) new util.Util_Number().RandomNum(0, 100);
			if(luckysize <= 80)
			{
				t.sendActionBar(player, ChatColor.YELLOW +""+ChatColor.BOLD+ "럭키 보너스!");
				amount = 1;
				SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 0.9F);
			}
			else if(luckysize <= 95)
			{
				t.sendActionBar(player, ChatColor.YELLOW +""+ChatColor.BOLD+ "빅 럭키 보너스!");amount = 5;
				SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 0.7F, 1.0F);
			}
			else
			{
				t.sendActionBar(player, ChatColor.YELLOW +""+ChatColor.BOLD+ "휴즈 럭키 보너스!");amount = 20;
				SoundEffect.SP(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.1F);
			}

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

	  	YamlLoader configYaml = new YamlLoader();
		configYaml.getConfig("config.yml");

		int id = event.getBlock().getTypeId();
		if(id==16)
			new util.Util_Player().addMoneyAndEXP(player, configYaml.getInt("Getting.Coal.Money"), configYaml.getLong("Getting.Coal.EXP"), player.getLocation(), true, false);
		else if(id == 15)
			new util.Util_Player().addMoneyAndEXP(player, configYaml.getInt("Getting.Iron.Money"), configYaml.getLong("Getting.Iron.EXP"), player.getLocation(), true, false);
		else if(id == 14)
			new util.Util_Player().addMoneyAndEXP(player, configYaml.getInt("Getting.Gold.Money"), configYaml.getLong("Getting.Gold.EXP"), player.getLocation(), true, false);
		else if(id == 56)
			new util.Util_Player().addMoneyAndEXP(player, configYaml.getInt("Getting.Diamond.Money"), configYaml.getLong("Getting.Diamond.EXP"), player.getLocation(), true, false);
		else if(id == 129)
			new util.Util_Player().addMoneyAndEXP(player, configYaml.getInt("Getting.Emerald.Money"), configYaml.getLong("Getting.Emerald.EXP"), player.getLocation(), true, false);
		else if(id == 73)
			new util.Util_Player().addMoneyAndEXP(player, configYaml.getInt("Getting.RedStone.Money"), configYaml.getLong("Getting.RedStone.EXP"), player.getLocation(), true, false);
		else if(id == 21)
			new util.Util_Player().addMoneyAndEXP(player, configYaml.getInt("Getting.Lapis.Money"), configYaml.getLong("Getting.Lapis.EXP"), player.getLocation(), true, false);
		else if(id == 17 || id == 162)
			new util.Util_Player().addMoneyAndEXP(player, configYaml.getInt("Getting.Wood.Money"), configYaml.getLong("Getting.Wood.EXP"), player.getLocation(), true, false);
		else if(id == 153)
			new util.Util_Player().addMoneyAndEXP(player, configYaml.getInt("Getting.NetherQuartz.Money"), configYaml.getLong("Getting.NetherQuartz.EXP"), player.getLocation(), true, false);

		return;
	}
}